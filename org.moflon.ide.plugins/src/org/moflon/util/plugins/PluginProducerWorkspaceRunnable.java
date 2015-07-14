package org.moflon.util.plugins;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Manifest;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.util.plugins.manifest.ManifestFileUpdater;
import org.moflon.util.plugins.manifest.ManifestFileUpdater.AttributeUpdatePolicy;
import org.moflon.util.plugins.manifest.PluginManifestConstants;

public class PluginProducerWorkspaceRunnable implements IWorkspaceRunnable
{
   private static final Logger logger = Logger.getLogger(PluginProducerWorkspaceRunnable.class);

   private static final String DEFAULT_BUNDLE_MANIFEST_VERSION = "2";

   private static final String DEFAULT_MANIFEST_VERSION = "1.0";

   private static final String DEFAULT_BUNDLE_VENDOR = "TU Darmstadt";

   private static final String DEFAULT_BUNDLE_VERSION = "1.0.0.qualifier";

   private static final String SCHEMA_BUILDER_NAME = "org.eclipse.pde.SchemaBuilder";

   private static final String MANIFEST_BUILDER_NAME = "org.eclipse.pde.ManifestBuilder";

   private ManifestFileUpdater manifestFileBuilder = new ManifestFileUpdater();

   private BuildPropertiesFileBuilder buildPropertiesFileBuilder = new BuildPropertiesFileBuilder();

   private IProject project;

   private MetamodelProperties projectProperties;

   public PluginProducerWorkspaceRunnable(final IProject project, final MetamodelProperties projectProperties)
   {
      this.project = project;
      this.projectProperties = projectProperties;
   }

   @Override
   public void run(final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         addPluginFeatures(project, projectProperties, monitor);
      } catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   private void addPluginFeatures(final IProject currentProject, final MetamodelProperties properties, final IProgressMonitor monitor) throws CoreException,
         IOException
   {
      try
      {
         monitor.beginTask("Creating/updating plugin project " + currentProject.getName(), 2);

         registerPluginBuildersAndAddNature(currentProject, WorkspaceHelper.createSubmonitorWith1Tick(monitor));

         logger.debug("Adding MANIFEST.MF to " + currentProject.getName());
         manifestFileBuilder.processManifest(
               currentProject,
               manifest -> {
                  boolean changed = false;
                  changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.MANIFEST_VERSION, DEFAULT_MANIFEST_VERSION,
                        AttributeUpdatePolicy.KEEP);
                  changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_MANIFEST_VERSION, DEFAULT_BUNDLE_MANIFEST_VERSION,
                        AttributeUpdatePolicy.KEEP);
                  changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_NAME, properties.get(MetamodelProperties.NAME_KEY),
                        AttributeUpdatePolicy.KEEP);
                  changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_SYMBOLIC_NAME,
                        properties.get(MetamodelProperties.PLUGIN_ID_KEY) + ";singleton:=true", AttributeUpdatePolicy.KEEP);
                  changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VERSION, DEFAULT_BUNDLE_VERSION,
                        AttributeUpdatePolicy.KEEP);
                  changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VENDOR, DEFAULT_BUNDLE_VENDOR,
                        AttributeUpdatePolicy.KEEP);
                  changed |= ManifestFileUpdater
                        .updateAttribute(manifest, PluginManifestConstants.BUNDLE_ACTIVATION_POLICY, "lazy", AttributeUpdatePolicy.KEEP);
                  changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_EXECUTION_ENVIRONMENT,
                        properties.get(MetamodelProperties.JAVA_VERION), AttributeUpdatePolicy.KEEP);

                  changed |= ManifestFileUpdater.updateDependencies(manifest, Arrays.asList(new String[] { WorkspaceHelper.PLUGIN_ID_ECORE,
                        WorkspaceHelper.PLUGIN_ID_ECORE_XMI, MoflonUtilitiesActivator.PLUGIN_ID }));

                  changed |= ManifestFileUpdater.updateDependencies(manifest,
                        ManifestFileUpdater.extractDependencies(properties.get(MetamodelProperties.DEPENDENCIES_KEY)));

                  try
                  {
                     if (currentProject.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID))
                        changed |= ManifestFileUpdater.updateDependencies(
                              manifest,
                              Arrays.asList(new String[] { WorkspaceHelper.PLUGIN_ID_LOG4J, WorkspaceHelper.PLUGIN_ID_MOCATREE,
                                    WorkspaceHelper.PLUGIN_ID_ECLIPSE_RUNTIME, WorkspaceHelper.PLUGIN_ID_SDMLANGUAGE, WorkspaceHelper.PLUGIN_ID_TGGLANGUAGE,
                                    WorkspaceHelper.PLUGIN_ID_TGGRUNTIME, WorkspaceHelper.PLUGIN_ID_DEBUGLANGUAGE }));
                  } catch (Exception e)
                  {
                     e.printStackTrace();
                  }

                  changed |= migrateOldManifests(manifest);

                  return changed;
               });

         logger.debug("Adding build.properties " + currentProject.getName());
         buildPropertiesFileBuilder.createBuildProperties(currentProject, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
      } finally
      {
         monitor.done();
      }
   }

   private boolean migrateOldManifests(final Manifest manifest)
   {
      boolean changed = false;
      
      // Old ID of the "Moflon Utilities" plugin
      changed |= ManifestFileUpdater.removeDependency(manifest, "org.moflon.dependencies");
      
      return changed;
   }

   private static void registerPluginBuildersAndAddNature(final IProject currentProject, final IProgressMonitor monitor) throws CoreException
   {
      try
      {
         monitor.beginTask("Registering plugin builders and add plugin nature", 2);

         IProjectDescription description = WorkspaceHelper.getDescriptionWithAddedNature(currentProject, WorkspaceHelper.PLUGIN_NATURE_ID,
               WorkspaceHelper.createSubmonitorWith1Tick(monitor));

         List<ICommand> oldBuilders = new ArrayList<>();
         oldBuilders.addAll(Arrays.asList(description.getBuildSpec()));

         List<ICommand> newBuilders = new ArrayList<>();

         if (!containsBuilder(oldBuilders, MANIFEST_BUILDER_NAME))
         {
            final ICommand manifestBuilder = description.newCommand();
            manifestBuilder.setBuilderName(MANIFEST_BUILDER_NAME);
            newBuilders.add(manifestBuilder);
         }

         if (!containsBuilder(oldBuilders, SCHEMA_BUILDER_NAME))
         {
            final ICommand schemaBuilder = description.newCommand();
            schemaBuilder.setBuilderName(SCHEMA_BUILDER_NAME);
            newBuilders.add(schemaBuilder);
         }

         // Add old builders after the plugin builders
         newBuilders.addAll(oldBuilders);

         description.setBuildSpec(newBuilders.toArray(new ICommand[newBuilders.size()]));
         currentProject.setDescription(description, WorkspaceHelper.createSubmonitorWith1Tick(monitor));
      } finally
      {
         monitor.done();
      }
   }

   private static boolean containsBuilder(final List<ICommand> builders, final String name)
   {
      return builders.stream().anyMatch(c -> c.getBuilderName().equals(name));
   }
}
