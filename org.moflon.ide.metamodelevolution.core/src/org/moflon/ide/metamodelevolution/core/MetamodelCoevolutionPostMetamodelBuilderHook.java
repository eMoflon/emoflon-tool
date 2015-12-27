package org.moflon.ide.metamodelevolution.core;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
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
         
         if (delta.getEModelElementChange().size() > 0) 
         {
             MetamodelDeltaProcessor processor = new JavaRefactorProcessor();
             processor.processDelta(repositoryProject, delta);
         }
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
            //String previousProjectName = postMetamodelBuilderHookDTO.mocaTreeReader.getValueForProperty("Changes::PreviousName", rootPackage);
            repositoryProjectName = postMetamodelBuilderHookDTO.mocaTreeReader.getValueForProperty("Moflon::PluginID", rootPackage);
            /*if (previousProjectName != null && !previousProjectName.equals(repositoryProjectName))
            {
            	repositoryProjectName = previousProjectName;	
            }*/
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
}
