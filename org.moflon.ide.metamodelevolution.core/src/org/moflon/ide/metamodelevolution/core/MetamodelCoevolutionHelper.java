package org.moflon.ide.metamodelevolution.core;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.runtime.builders.hooks.MetamodelBuilderHookDTO;
import org.moflon.util.plugins.MetamodelProperties;

import MocaTree.Node;
import MocaTree.Text;

public class MetamodelCoevolutionHelper
{
   private static final Logger logger = Logger.getLogger(MetamodelCoevolutionHelper.class);

   /**
    * This method returns the Changes Moca tree
    */
   public static Node getMocaTree(IProject metamodelProject)
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
    * 
    * @deprecated Use preMetamodelBuilderHookDTO.extractRepositoryProjectProperties()
    */
   @Deprecated
   public static IProject getRepositoryProject(Node rootNode, MetamodelBuilderHookDTO postMetamodelBuilderHookDTO)
   {
      try
      {
         EList<Text> rootPackages = rootNode.getChildren();
         String repositoryProjectName = null;
         for (final Text rootText : rootPackages)
         {
            final Node rootPackage = (Node) rootText;
            // String previousProjectName =
            // postMetamodelBuilderHookDTO.mocaTreeReader.getValueForProperty("Changes::PreviousName", rootPackage);
            repositoryProjectName = postMetamodelBuilderHookDTO.mocaTreeReader.getValueForProperty("Moflon::PluginID", rootPackage);
            /*
             * if (previousProjectName != null && !previousProjectName.equals(repositoryProjectName)) {
             * repositoryProjectName = previousProjectName; }
             */
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
         LogUtils.error(logger, e);
      }
      return null;
   }
}
