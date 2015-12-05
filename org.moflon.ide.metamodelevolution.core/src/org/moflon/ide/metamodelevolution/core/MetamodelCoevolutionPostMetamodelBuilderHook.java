package org.moflon.ide.metamodelevolution.core;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.injection.JavaFileInjectionExtractor;
import org.moflon.ide.core.runtime.builders.hooks.PostMetamodelBuilderHook;
import org.moflon.ide.core.runtime.builders.hooks.PostMetamodelBuilderHookDTO;
import org.moflon.ide.metamodelevolution.core.impl.CoreFactoryImpl;
import org.moflon.ide.metamodelevolution.core.impl.RenameChangeImpl;
import org.moflon.ide.metamodelevolution.core.processing.RenameClassRefactoring;
import org.moflon.ide.metamodelevolution.core.processing.RenameMethodRefactoring;
import org.moflon.ide.metamodelevolution.core.processing.RenameRefactoring;
import org.moflon.util.plugins.MetamodelProperties;

import MocaTree.Attribute;
import MocaTree.Node;
import MocaTree.Text;

public class MetamodelCoevolutionPostMetamodelBuilderHook implements PostMetamodelBuilderHook
{
   private static final Logger logger = Logger.getLogger(MetamodelCoevolutionPostMetamodelBuilderHook.class);

   @Override
   public IStatus run(final PostMetamodelBuilderHookDTO postMetamodelBuilderHookDTO)
   {
      logger.debug("Performing post-build step for meta-model co-evolution support");

      Node mocaTree = getMocaTree(postMetamodelBuilderHookDTO.metamodelproject);
      Node changesTree = (Node) mocaTree.getChildren().get(0);
      IProject repositoryProject = getRepositoryProject(changesTree, postMetamodelBuilderHookDTO);
      //TODO@settl: als generische Schnittstelle mit einem MetamodelDeltaCalculator auslagern
      ChangeSequence delta = parseTree(mocaTree);
      if (repositoryProject != null)
      {
         processDelta(delta, repositoryProject);
      }

      return Status.OK_STATUS;
   }
   //TODO@settl: als generische Schnittstelle mit einem Changeprocessor implementieren
   /**
    * This method processes changes according to the change metamodel
    */
   private void processDelta(ChangeSequence delta, IProject project)
   {
      for (EModelElementChange change : delta.getEModelElementChange())
      {
         if (change instanceof RenameChangeImpl)
         {
            RenameChange renaming = (RenameChange) change;
            if (renaming.arePreviousAndCurrentValueDifferent())
            {
               logger.debug("Change detected for old value: " + renaming.getPreviousValue() + ". New value is: " + renaming.getCurrentValue());
               // todo@settl: Impl und factory method infos aus dem
               // genmodel laden
               // adapt java code
               // rename class and "Impl" class
               // TODO@settl: If possible, leave RenameClassRefactoring as
               // dumb as possible -> invoke it twice for both classes (I
               // guess this is what you already planned to do) (RK)
               RenameRefactoring processor = new RenameClassRefactoring();
               processor.refactor(project, renaming);

               // rename factory method
               // TODO@settl: Create a new MethodRenamingChange here:
               // "createOldClass -> createNewClass in class XYZFactory"
               GenModel genModel = eMoflonEMFUtil.extractGenModelFromProject(project);
               // TODO@settl: This is how you can get the implementation
               // class name
               genModel.getAllGenPackagesWithClassifiers().get(0).getGenClasses().get(0).getClassName();
               // TODO@settl: This is how you can get the interface name
               genModel.getAllGenPackagesWithClassifiers().get(0).getGenClasses().get(0).getInterfaceName();
               genModel.getModelDirectory(); // path to gen folder
               genModel.getAllGenPackagesWithClassifiers().get(0).getFactoryInterfaceName();
               genModel.getAllGenPackagesWithClassifiers().get(0).getPackageInterfaceName();
               genModel.getAllGenPackagesWithClassifiers().get(0).getSwitchClassName();
               genModel.getAllGenPackagesWithClassifiers().get(0).getUtilitiesPackageName();
               genModel.getAllGenPackagesWithClassifiers().get(0).getClassPackageName();
               EClass cls = EcoreFactory.eINSTANCE.createEClass(); // If
               // possible,
               // use
               // the
               // EClasses...
               cls.setName("Topology");
               // genModel.findGenClassifier(cls);

               RenameRefactoring methodRenaming = new RenameMethodRefactoring();
               methodRenaming.refactor(project, renaming);

            }
         }
      }
      // processInjections(project);
   }

   private Node getMocaTree(IProject metamodelProject)
   {
      IFile mocaFile = WorkspaceHelper.getChangesMocaTree(metamodelProject);
      if (mocaFile.exists())
      {
         ResourceSet set = CodeGeneratorPlugin.createDefaultResourceSet();
         eMoflonEMFUtil.installCrossReferencers(set);

         URI mocaFileURI = URI.createPlatformResourceURI(mocaFile.getFullPath().toString(), true);
         Resource mocaTreeResource = set.getResource(mocaFileURI, true);
         Node mocaTree = (Node) mocaTreeResource.getContents().get(0);
         return mocaTree;
      }
      return null;
   }

   /**
    * This method returns the corresponding repository project
    */
   private IProject getRepositoryProject(Node rootNode, PostMetamodelBuilderHookDTO postMetamodelBuilderHookDTO)
   {
      try
      {
         EList<Text> rootPackages = rootNode.getChildren();
         String repositoryProjectName = null;
         for (final Text rootText : rootPackages)
         {
            final Node rootPackage = (Node) rootText;
            repositoryProjectName = postMetamodelBuilderHookDTO.mocaTreeReader.getValueForProperty("Moflon::PluginID", rootPackage);
         }

         if (repositoryProjectName != null)
         {
            Map<String, MetamodelProperties> properties = postMetamodelBuilderHookDTO.extractRepositoryProjectProperties();
            MetamodelProperties repositoryProperties = properties.get(repositoryProjectName);
            IProject repositoryProject = repositoryProperties.getRepositoryProject();
            return repositoryProject;
         }

      } catch (CoreException e)
      {
         e.printStackTrace();
      }
      return null;
   }

   private ChangeSequence parseTree(final Node tree)
   {
      ChangeSequence delta = CoreFactoryImpl.eINSTANCE.createChangeSequence();

      parseChangesTree(tree, delta);

      return delta;
   }

   /**
    * This method recursively extracts the Metamodel changes from the MocaChangesTree and maps it to the ChangeMetamodel
    */
   private ChangeSequence parseChangesTree(final Node tree, ChangeSequence delta)
   {
      if (tree.getName() != null && tree.getName().equals("EClass"))
      {
         RenameChange renaming = CoreFactoryImpl.eINSTANCE.createRenameChange();

         EList<Attribute> attributes2 = tree.getAttribute();
         for (Attribute attr : attributes2)
         {
            if (attr.getName().equals("name"))
               renaming.setCurrentValue(attr.getValue());
            if (attr.getName().equals("previousName"))
               renaming.setPreviousValue(attr.getValue());
            if (attr.getName().equals("packageName"))
               renaming.setPackageName(attr.getValue());
         }
         delta.getEModelElementChange().add(renaming);
         return delta;
      } else
      {
         final EList<Text> children = tree.getChildren();
         for (Text text : children)
         {
            Node node = (Node) text;
            parseChangesTree(node, delta);
         }
         return delta;
      }
   }

   /**
    * This method deletes and reextracts all injection files for a given project
    */
   @SuppressWarnings("unused")
   @Deprecated // TODO@settl: Remove if code is no longer needed (RK)
   private void processInjections(IProject project)
   {
      try
      {

         JavaFileInjectionExtractor extractor = new JavaFileInjectionExtractor();
         IFolder genFolder = project.getFolder(WorkspaceHelper.GEN_FOLDER);

         if (genFolder.members().length != 0)
         {

            WorkspaceHelper.clearFolder(project, WorkspaceHelper.INJECTION_FOLDER, new NullProgressMonitor());

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

         // IFolder genFolder = WorkspaceHelper.addFolder(project,
         // WorkspaceHelper.GEN_FOLDER, new NullProgressMonitor());

      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
