package org.moflon.properties;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.util.plugins.xml.XMLUtils;
import org.w3c.dom.Document;

import MoflonPropertyContainer.Dependencies;
import MoflonPropertyContainer.MoflonPropertiesContainer;
import MoflonPropertyContainer.MoflonPropertyContainerFactory;
import MoflonPropertyContainer.MoflonPropertyContainerPackage;
import MoflonPropertyContainer.PropertiesMapping;
import MoflonPropertyContainer.PropertiesValue;

public class MoflonPropertiesContainerHelper
{
   public static final String MOFLON_CONFIG_FILE = "moflon.properties.xmi";

   private static final Logger logger = Logger.getLogger(MoflonPropertiesContainerHelper.class);

   // This is the list of XML element tagnames that are no longer supported
   private static final List<String> OBSOLETE_TAGNAMES = Arrays.asList("buildFilter", "core", "debugMode", "genSdmRpCoverageInstrumentation",
         "genTracingInstrumentation", "injectionErrorHandling", "listShuffling", "skipValidation", "strictSDMConditionalBranching");

   public static MoflonPropertiesContainer load(final IProject project, final IProgressMonitor monitor)
   {
      try
      {
         monitor.beginTask("Load properties.", 1);

         removeObsoleteTags(project);
         
         final MoflonPropertiesContainer moflonPropertiesCont = loadOrCreatePropertiesContainer(project, project.getFile(MOFLON_CONFIG_FILE));
         final String projectName = project.getName();
         moflonPropertiesCont.checkForMissingDefaults();
         
         // The TGG build mode is currently set during checkForMissingDefaults, where we cannot distinguish between TGG and SDM projects
         if (!WorkspaceHelper.isIntegrationProjectNoThrow(project))
         {
            moflonPropertiesCont.setTGGBuildMode(null);
         }
         
         if (!projectName.equals(moflonPropertiesCont.getProjectName()))
         {
            logger.warn("Project name in Moflon properties file ('" + moflonPropertiesCont.getProjectName()
                  + "') does not match Project. Setting correct project name to '" + projectName + "'.");
            moflonPropertiesCont.setProjectName(projectName);
         }

         MoflonPropertiesContainerHelper.save(moflonPropertiesCont, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         return moflonPropertiesCont;
      } finally
      {
         monitor.done();
      }
   }

   private static void removeObsoleteTags(final IProject project)
   {
      IFile propertiesFile = project.getFile(MOFLON_CONFIG_FILE);
      try
      {
         String content = IOUtils.toString(propertiesFile.getContents());
         // Make sure that we need to do anything at all
         if (OBSOLETE_TAGNAMES.stream().anyMatch(tagname -> {
            return content.contains(tagname.toString());
         }))
         {
            final Document doc = XMLUtils.parseXmlDocument(content);

            for (final String obsoleteTagname : OBSOLETE_TAGNAMES)
            {
               removeAllChildrenByTagname(doc, obsoleteTagname);
            }

            String newContent = XMLUtils.formatXmlString(doc, new NullProgressMonitor());
            if (!newContent.equals(content))
            {
               propertiesFile.setContents(new ByteArrayInputStream(newContent.getBytes()), true, true, new NullProgressMonitor());
            }
         }
      } catch (IOException | CoreException e)
      {
         logger.error("Failed to remove obsolete tags from " + propertiesFile + ". Reason: " + e.getMessage());
      }
   }

   private static void removeAllChildrenByTagname(final Document doc, final String tagname)
   {
      for (int i = 0; i < doc.getElementsByTagName(tagname).getLength(); ++i)
         doc.getDocumentElement().removeChild(doc.getElementsByTagName(tagname).item(i));
   }

   private static MoflonPropertiesContainer loadOrCreatePropertiesContainer(final IProject project, final IFile propertyFile)
   {
      MoflonPropertiesContainer moflonPropertiesCont;
      if (propertyFile.exists())
      {
         MoflonPropertyContainerPackage.eINSTANCE.getClass();
         moflonPropertiesCont = (MoflonPropertiesContainer) eMoflonEMFUtil.getResourceFromFileIntoDefaultResourceSet(propertyFile).getContents().get(0);

      } else
      {
         logger.error("Moflon property file '" + propertyFile + "' not found in project '" + project.getName()
               + "'. Generating default properties file. Unable to set MetamodelProject. Has to be fixed manually");
         moflonPropertiesCont = MoflonPropertyContainerFactory.eINSTANCE.createMoflonPropertiesContainer();

      }
      return moflonPropertiesCont;
   }

   public static MoflonPropertiesContainer createDefaultPropertiesContainer(final IProject project, final IProject metamodelProject)
   {
      String metaModelProjectName = metamodelProject.getName();

      return createDefaultPropertiesContainer(project.getName(), metaModelProjectName);
   }

   public static MoflonPropertiesContainer createDefaultPropertiesContainer(final String projectName, final String metaModelProjectName)
   {
      MoflonPropertiesContainer container = MoflonPropertyContainerFactory.eINSTANCE.createMoflonPropertiesContainer();
      container.setProjectName(projectName);

      container.updateMetamodelProjectName(metaModelProjectName);
      container.checkForMissingDefaults();

      return container;
   }

   public static void save(final MoflonPropertiesContainer properties, final IProgressMonitor monitor)
   {
      try
      {
         monitor.beginTask("Saving eMoflon properties", 1);
         IWorkspaceRoot workspace = ResourcesPlugin.getWorkspace().getRoot();
         IProject project = workspace.getProject(properties.getProjectName());
         if (project == null)
         {
            logger.error("Unable to save property file '" + MOFLON_CONFIG_FILE + "' for project \"" + properties.getProjectName() + "\".");
         } else
         {
            IFile projectFile = project.getFile(MOFLON_CONFIG_FILE);

            ResourceSet set = eMoflonEMFUtil.createDefaultResourceSet();
            URI fileURI = eMoflonEMFUtil.createFileURI(projectFile.getLocation().toString(), false);
            Resource resource = set.createResource(fileURI);
            resource.getContents().add(normalize(properties));
            resource.save(null);
            projectFile.refreshLocal(IResource.DEPTH_ZERO, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
         }
      } catch (Exception e)
      {
         e.printStackTrace();
         logger.error("Unable to save property file '" + MOFLON_CONFIG_FILE + "' for project \"" + properties.getProjectName() + "\".");
      } finally
      {
         monitor.done();
      }

   }

   private static EObject normalize(final MoflonPropertiesContainer properties)
   {
      // Normalize properties to avoid unnecessary nondeterminism
      List<Dependencies> sortedDependencies = new ArrayList<>(properties.getDependencies());
      sortedDependencies.sort((d1, d2) -> d1.getValue().compareTo(d2.getValue()));
      properties.getDependencies().clear();
      properties.getDependencies().addAll(sortedDependencies);

      return properties;
   }

   public static Map<String, String> mappingsToMap(final List<? extends PropertiesMapping> mappings)
   {
      Map<String, String> map = new HashMap<String, String>();

      for (PropertiesMapping mapping : mappings)
         map.put(mapping.getKey(), mapping.getValue());

      return map;
   }

   public static Collection<String> mapToValues(final Collection<? extends PropertiesValue> values)
   {
      List<String> list = new LinkedList<String>();
      for (PropertiesValue value : values)
         list.add(value.getValue());
      return list;
   }

   public static MoflonPropertiesContainer createEmptyContainer()
   {
      return MoflonPropertyContainerFactory.eINSTANCE.createMoflonPropertiesContainer();
   }
   

}