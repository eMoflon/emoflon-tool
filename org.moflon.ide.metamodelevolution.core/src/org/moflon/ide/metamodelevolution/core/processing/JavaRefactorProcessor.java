package org.moflon.ide.metamodelevolution.core.processing;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
import org.moflon.ide.core.injection.JavaFileInjectionExtractor;
import org.moflon.ide.metamodelevolution.core.ChangeSequence;
import org.moflon.ide.metamodelevolution.core.CoreFactory;
import org.moflon.ide.metamodelevolution.core.EModelElementChange;
import org.moflon.ide.metamodelevolution.core.MetamodelCoevolutionPlugin;
import org.moflon.ide.metamodelevolution.core.RenameChange;
import org.moflon.ide.metamodelevolution.core.TaggedValueChange;
import org.moflon.ide.metamodelevolution.core.impl.RenameChangeImpl;
import org.moflon.ide.metamodelevolution.core.processing.refactoring.SequenceRefactoring;

public class JavaRefactorProcessor extends MetamodelDeltaProcessor_ImplBase
{
   private static final Logger logger = Logger.getLogger(JavaRefactorProcessor.class);

   private String implementationFileSuffix;

   private IProject project;

   private List<RenameChange> postChanges;

   public JavaRefactorProcessor(GenModel genModel)
   {
      super(genModel);
      postChanges = new LinkedList<>();
   }

   /*
    * This is the Entry point to modify for Future Work
    */
   @Override
   public IStatus processDelta(IProject project, ChangeSequence delta)
   {
      this.project = project;
      this.implementationFileSuffix = this.getImplementationFileSuffix();

      for (EModelElementChange change : delta.getEModelElementChange())
      {
         if (change instanceof RenameChangeImpl)
         {
            RenameChange renameChange = (RenameChange) change;
            if (renameChange.arePreviousAndCurrentValueDifferent())
            {
               logger.debug("Change detected for old value: " + renameChange.getPreviousValue() + ". New value is: " + renameChange.getCurrentValue());

               if (renameChange.getElementType().equals(MocaToMoflonUtils.ECLASS_NODE_NAME))
               {
                  // TODO@fstallmeyer: Check why this "triangle exchange" does not work. Maybe even remove it.
                  String tmpName = createTmpName(renameChange);
                  RenameChange firstRC = createNewRenameChange(renameChange.getPreviousValue(), tmpName, renameChange);
                  RenameChange secondRC = createNewRenameChange(tmpName, renameChange.getCurrentValue(), renameChange);

                  postChanges.add(secondRC);
                  if (createClassRefactorings(firstRC).isOK())
                  {
                     findAndProcessInjections(firstRC);
                  }

               } else if (renameChange.getElementType().equals(MocaToMoflonUtils.EPACKAGE_NODE_NAME))
               {
                  if (createPackageRefactorings(renameChange).isOK())
                  {
                     findAndProcessInjections(renameChange);
                  }
               }
            }
         }
         // other types of changes can be called like this
         else if (change instanceof TaggedValueChange)
         {
            // process changes in a tagged value
         }
      }

      for (RenameChange renameChange : postChanges)
      {
         if (createClassRefactorings(renameChange).isOK())
         {
            findAndProcessInjections(renameChange);
         }
      }
      return new Status(IStatus.OK, MetamodelCoevolutionPlugin.getDefault().getPluginId(), "Refactoring successfull");
   }

   private RenameChange createNewRenameChange(String previous, String current, RenameChange old)
   {
      RenameChange newRenameChange = CoreFactory.eINSTANCE.createRenameChange();
      newRenameChange.setPackageName(old.getPackageName());
      newRenameChange.setCurrentValue(current);
      newRenameChange.setPreviousValue(previous);
      newRenameChange.setElementType(old.getElementType());
      return newRenameChange;
   }

   private String createTmpName(RenameChange renameChange)
   {
      StringBuilder sb = new StringBuilder();
      sb.append(renameChange.getElementType()).append(createHash(renameChange.getPreviousValue(), renameChange.getCurrentValue()));
      return sb.toString();
   }

   private String createHash(String previousName, String newName)
   {
      StringBuilder sb = new StringBuilder();
      sb.append(previousName).append(newName);
      return sha1(sb.toString());
   }

   private String sha1(String s)
   {
      try
      {
         MessageDigest md = MessageDigest.getInstance("SHA1");
         md.update(s.getBytes());
         return new BigInteger(1, md.digest()).toString(16);
      } catch (NoSuchAlgorithmException e)
      {
         logger.error("An Exception has been Thrown", e);
         return s;
      }
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

   private String getImplementationFileSuffix()
   {
      String namePattern = genModel.getClassNamePattern();
      if (namePattern != null)
         return namePattern.substring(namePattern.lastIndexOf("}") + 1);
      else
         return "Impl";
   }

   private void findAndProcessInjections(RenameChange renameChange)
   {
      Set<IProject> projectsToReextract = findReferences(renameChange);
      for (IProject proj : projectsToReextract)
      {
         processInjections(proj);
      }
   }

   /**
    * This method finds all references to the refactored element in the workspace. The references are used to identify
    * injection files that have to be reextracted (in all projects).
    */
   private Set<IProject> findReferences(RenameChange renameChange)
   {
      Set<IProject> projects = new HashSet<IProject>();
      SearchPattern pattern = null;

      if (renameChange.getElementType().equals(MocaToMoflonUtils.ECLASS_NODE_NAME))
      {
         pattern = SearchPattern.createPattern(renameChange.getCurrentValue(), IJavaSearchConstants.INTERFACE, IJavaSearchConstants.REFERENCES,
               SearchPattern.R_EXACT_MATCH);
      } else if (renameChange.getElementType().equals(MocaToMoflonUtils.EPACKAGE_NODE_NAME))
      {
         pattern = SearchPattern.createPattern(renameChange.getPackageName(), IJavaSearchConstants.PACKAGE, IJavaSearchConstants.REFERENCES,
               SearchPattern.R_EXACT_MATCH);
      }

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
         if (pattern != null)
         {
            engine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, requestor, null);
         }
      } catch (CoreException e)
      {
         logger.error("An Exception has been Thrown", e);
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
         logger.error("An Exception has been Thrown", e);
      }
   }

}
