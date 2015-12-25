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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.injection.JavaFileInjectionExtractor;
import org.moflon.ide.core.runtime.builders.hooks.PostMetamodelBuilderHook;
import org.moflon.ide.core.runtime.builders.hooks.PostMetamodelBuilderHookDTO;
import org.moflon.ide.metamodelevolution.core.changes.ChangesTreeCalculator;
import org.moflon.ide.metamodelevolution.core.changes.MetamodelChangeCalculator;
import org.moflon.ide.metamodelevolution.core.processing.JavaRefactorProcessor;
import org.moflon.ide.metamodelevolution.core.processing.MetamodelDeltaProcessor;
import org.moflon.util.plugins.MetamodelProperties;

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
           
      if (repositoryProject != null)
      {
         MetamodelChangeCalculator changeCalculator = new ChangesTreeCalculator();
         ChangeSequence delta = changeCalculator.parseTree(mocaTree);
         
         MetamodelDeltaProcessor processor = new JavaRefactorProcessor();
         processor.processDelta(repositoryProject, delta);
      }

      return Status.OK_STATUS;
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
