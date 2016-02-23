package org.moflon.ide.metamodelevolution.core.processing;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.moflon.core.mocatomoflon.MocaToMoflonUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.injection.JavaFileInjectionExtractor;
import org.moflon.ide.metamodelevolution.core.ChangeSequence;
import org.moflon.ide.metamodelevolution.core.EModelElementChange;
import org.moflon.ide.metamodelevolution.core.MetamodelCoevolutionPlugin;
import org.moflon.ide.metamodelevolution.core.RenameChange;
import org.moflon.ide.metamodelevolution.core.TaggedValueChange;
import org.moflon.ide.metamodelevolution.core.impl.RenameChangeImpl;
import org.moflon.ide.metamodelevolution.core.processing.refactoring.SequenceRefactoring;

public class JavaRefactorProcessor implements MetamodelDeltaProcessor
{
   private static final Logger logger = Logger.getLogger(MetamodelDeltaProcessor.class);

   private String implementationFileSuffix;

   private IProject project;

   @Override
   public IStatus processDelta(IProject project, ChangeSequence delta)
   {
      this.project = project;
      final GenModel genModel = eMoflonEMFUtil.extractGenModelFromProject(project);
      this.implementationFileSuffix = this.getImplementationFileSuffix(genModel);

      for (EModelElementChange change : delta.getEModelElementChange())
      {
         if (change instanceof RenameChangeImpl)
         {
            RenameChange renameChange = (RenameChange) change;
            if (renameChange.arePreviousAndCurrentValueDifferent())
            {
               logger.debug("Change detected for old value: " + renameChange.getPreviousValue() + ". New value is: " + renameChange.getCurrentValue());
               // todo@settl: Impl und factory method infos aus dem
               // genmodel laden
               // adapt java code
               // rename class and "Impl" class
               // TODO@settl: If possible, leave RenameClassRefactoring as
               // dumb as possible -> invoke it twice for both classes (I
               // guess this is what you already planned to do) (RK)

               // rename factory method
               // TODO@settl: Create a new MethodRenamingChange here:
               // "createOldClass -> createNewClass in class XYZFactory"
               /*
                * GenModel genModel = eMoflonEMFUtil.extractGenModelFromProject(project); // class name
                * genModel.getAllGenPackagesWithClassifiers().get(0).getGenClasses().get(0).getClassName();
                * genModel.getAllGenPackagesWithClassifiers().get(0).getGenClasses().get(0).getInterfaceName(); String
                * 
                * String genFolder = genModel.getModelDirectory(); // path to gen folder String factoryInterfaceName =
                * genModel.getAllGenPackagesWithClassifiers().get(0).getFactoryInterfaceName();
                * 
                * String factoryInterfacePCkgName =
                * genModel.getAllGenPackagesWithClassifiers().get(0).getPackageInterfaceName();
                * genModel.getAllGenPackagesWithClassifiers().get(0).getSwitchClassName();
                * genModel.getAllGenPackagesWithClassifiers().get(0).getUtilitiesPackageName();
                * genModel.getAllGenPackagesWithClassifiers().get(0).getClassPackageName(); EClass cls =
                * EcoreFactory.eINSTANCE.createEClass(); // If // possible, // use // the // EClasses...
                * cls.setName("Topology");
                */
               // genModel.findGenClassifier(cls);
               GenModel genModel2 = eMoflonEMFUtil.extractGenModelFromProject(project);
               String className = genModel2.getAllGenPackagesWithClassifiers().get(0).getGenClasses().get(0).getClassName();
               String interfaceName = genModel2.getAllGenPackagesWithClassifiers().get(0).getGenClasses().get(0).getInterfaceName();
               if (renameChange.getElementType().equals(MocaToMoflonUtils.ECLASS_NODE_NAME))
               {
                  if (createClassRefactorings(renameChange).isOK())
                  {
                     Set<IProject> projectsToReextract = findReferences(renameChange);
                     for (IProject proj : projectsToReextract)
                     {
                        processInjections(proj);
                     }
                  }

               } else if (renameChange.getElementType().equals(MocaToMoflonUtils.EPACKAGE_NODE_NAME))
               {
                  createPackageRefactorings(renameChange);
               }
            }
         }
         else if (change instanceof TaggedValueChange) // other types of changes can be called like this
         {
            // process changes in a tagged value
         }
      }
      return new Status(IStatus.OK, MetamodelCoevolutionPlugin.getDefault().getPluginId(), "Refactoring successfull");
   }
   
   private IStatus createPackageRefactorings(RenameChange renameChange)
   {
      SequenceRefactoring sequenceRefactoring = new SequenceRefactoring(renameChange);
      return sequenceRefactoring.createPackageRefactorings(project, implementationFileSuffix);
   }
   
   private IStatus createClassRefactorings(RenameChange renameChange)
   {
      SequenceRefactoring sequenceRefactoring = new SequenceRefactoring(renameChange);
      return sequenceRefactoring.createClassRefactorings(project, implementationFileSuffix);
   }
   
   private String getImplementationFileSuffix(GenModel genModel)
   {
      String namePattern = genModel.getClassNamePattern();
      if (namePattern != null)
         return namePattern.substring(namePattern.lastIndexOf("}") + 1);
      else
         return "Impl";
   }

   /**
    * This method finds all references to the refactored element in the workspace. The references are used to identify
    * injection files that have to be reextracted (in all projects).
    */
   private Set<IProject> findReferences(RenameChange rename)
   {
      Set<IProject> projects = new HashSet<IProject>();

      SearchPattern pattern = SearchPattern.createPattern(rename.getCurrentValue(), IJavaSearchConstants.INTERFACE, IJavaSearchConstants.REFERENCES,
            SearchPattern.R_EXACT_MATCH);
      IJavaSearchScope scope = SearchEngine.createWorkspaceScope();
      SearchRequestor requestor = new SearchRequestor() {
         @Override
         public void acceptSearchMatch(SearchMatch match)
         {
            // System.out.println("project: " + match.getResource().getProject());
            projects.add(match.getResource().getProject());
         }
      };
      SearchEngine engine = new SearchEngine();
      try
      {
         engine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, requestor, null);
      } catch (CoreException e)
      {
         e.printStackTrace();
      }

      return projects;
   }

   /**
    * This method reextracts all injection files for a given project.
    */
   private void processInjections(IProject project)
   {
      try
      {
         IFolder injFolder = project.getFolder(WorkspaceHelper.INJECTION_FOLDER);
         if (injFolder.members().length == 0)
         {
            System.out.println("No injections founds for project " + project.getName());
            logger.debug("No injections founds for project " + project.getName());
            return;
         }

         JavaFileInjectionExtractor extractor = new JavaFileInjectionExtractor();
         IFolder genFolder = project.getFolder(WorkspaceHelper.GEN_FOLDER);

         if (genFolder.members().length != 0)
         {
            genFolder.accept(new IResourceVisitor() {
               @Override
               public boolean visit(IResource resource) throws CoreException
               {
                  if (resource.getType() == (IResource.FILE))
                     extractor.extractInjectionNonInteractively((IFile) resource);
                  return true;
               }
            });
         }

      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }

}
