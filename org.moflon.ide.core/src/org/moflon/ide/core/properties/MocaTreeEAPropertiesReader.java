package org.moflon.ide.core.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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
import org.moflon.ide.core.CoreActivator;
import org.moflon.util.plugins.MetamodelProperties;

import MocaTree.Attribute;
import MocaTree.Node;
import MocaTree.Text;

public class MocaTreeEAPropertiesReader
{
   private static final Logger logger = Logger.getLogger(MocaTreeEAPropertiesReader.class);

   private ResourceSet set;

   private Node mocaTree;

   /**
    * Extracts the specification metadata from the MOCA tree in the .temp folder
    */
   public Map<String, MetamodelProperties> getProperties(final IProject metamodelProject) throws CoreException
   {
      IFile mocaFile = WorkspaceHelper.getExportedMocaTree(metamodelProject);

      if (mocaFile.exists())
      {
         // Create and initialize resource set
         set = CodeGeneratorPlugin.createDefaultResourceSet();
         eMoflonEMFUtil.installCrossReferencers(set);
         
         // Load Moca tree in read-only mode
         URI mocaFileURI = URI.createPlatformResourceURI(mocaFile.getFullPath().toString(), true);
         Resource mocaTreeResource = set.getResource(mocaFileURI, true);
         mocaTree = (Node) mocaTreeResource.getContents().get(0);
         Map<String, MetamodelProperties> properties = getProperties(mocaTree);
         properties.keySet().forEach(p->properties.get(p).setMetamodelProjectName(metamodelProject.getName()));
         return properties;
      } else
      {
         throw new CoreException(new Status(IStatus.ERROR, CoreActivator.getModuleID(), "Cannot extract project properties, since Moca tree is missing."));
      }
   }

   public Map<String, MetamodelProperties> getProperties(final Node rootNode) throws CoreException
   {
      Map<String, MetamodelProperties> propertiesMap = new HashMap<>();
      Node exportedTree = (Node) rootNode.getChildren().get(0);

      assert "exportedTree".equals(exportedTree.getName());

      EList<Text> rootPackages = exportedTree.getChildren();
      for (final Text rootText : rootPackages)
      {
         final Node rootPackage = (Node) rootText;
         MetamodelProperties properties = getProjectProperties(rootPackage);
         propertiesMap.put(properties.get(MetamodelProperties.PLUGIN_ID_KEY), properties);
      }

      return propertiesMap;
   }

   public MetamodelProperties getProjectProperties(final Node rootNode) throws CoreException
   {
      final MetamodelProperties properties = new MetamodelProperties();

      properties.put(MetamodelProperties.NAME_KEY, getValueForProperty("Moflon::Name", rootNode));
      properties.put(MetamodelProperties.NAME_KEY, getValueForProperty("Moflon::NsPrefix", rootNode));
      properties.put(MetamodelProperties.NS_URI_KEY, getValueForProperty("Moflon::NsUri", rootNode));
      properties.put(MetamodelProperties.PLUGIN_ID_KEY, getValueForProperty("Moflon::PluginID", rootNode));
      properties.put(MetamodelProperties.EXPORT_FLAG_KEY, getValueForProperty("Moflon::Export", rootNode));
      properties.put(MetamodelProperties.VALIDATED_FLAG_KEY, getValueForProperty("Moflon::Validated", rootNode));
      properties.put(MetamodelProperties.WORKING_SET_KEY, getValueForProperty("Moflon::WorkingSet", rootNode));
      properties.setHasAttributeConstraints(containsAttributeConstraintsNode(rootNode));

      switch (rootNode.getName())
      {
      case "EPackage":
         properties.put(MetamodelProperties.TYPE_KEY, MetamodelProperties.REPOSITORY_KEY);
         break;
      case "TGG":
         properties.put(MetamodelProperties.TYPE_KEY, MetamodelProperties.INTEGRATION_KEY);
         break;
      default:
         logger.warn("Unknown node type in Moca tree: " + rootNode.getName());
      }

      properties.setDependencies(extractDependencies(rootNode));

      properties.setDefaultValues();

      return properties;
   }

   private boolean containsAttributeConstraintsNode(Node rootNode)
   {
      final Queue<Node> unvisitedNodes = new LinkedList<>();
      unvisitedNodes.add(rootNode);
      while(!unvisitedNodes.isEmpty())
      {
         Node nextNode = unvisitedNodes.poll();
         if ("AttributeConstraints".equals(nextNode.getName()))
            return true;
         
         nextNode.getChildren().stream().filter(t -> t instanceof Node).map(t -> (Node)t).forEach(n -> unvisitedNodes.add(n));
      }        
      return false;
   }

   public List<String> extractDependencies(final Node rootPackage) throws CoreException
   {
      Collection<Text> dependenciesNodes = rootPackage.getChildren("dependencies");

      if (dependenciesNodes.size() != 1)
      {
         throw new CoreException(new Status(IStatus.ERROR, CoreActivator.getModuleID(), "Missing dependencies nodes for project " + rootPackage.getName()));
      }

      final List<String> dependencies = new ArrayList<>();
      final Node dependenciesNode = (Node) dependenciesNodes.iterator().next();

      for (final Text text : dependenciesNode.getChildren())
      {
         final String dependency = ((Node) text).getName();
         dependencies.add(dependency);
      }

      return dependencies;
   }

   public String getValueForProperty(final String property, final Node rootPackage) throws CoreException
   {
      String value;
      Collection<Attribute> attributes = rootPackage.getAttribute(property);
      if (!attributes.isEmpty())
      {
         value = attributes.iterator().next().getValue();
      } else
      {
         throw new CoreException(new Status(IStatus.ERROR, CoreActivator.getModuleID(), "Missing property " + property + " for project " + rootPackage.getName()));
      }
      return value;
   }

   public ResourceSet getResourceSet()
   {
      return set;
   }

   public Node getMocaTree()
   {
      return mocaTree;
   }

   public Resource getMocaTreeResource()
   {
      return mocaTree.eResource();
   }

}
