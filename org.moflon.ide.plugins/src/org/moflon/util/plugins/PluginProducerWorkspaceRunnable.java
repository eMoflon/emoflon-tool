package org.moflon.util.plugins;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.jar.Manifest;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.moflon.TGGLanguageActivator;
import org.moflon.core.moca.tree.MocaTreePlugin;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.sdm.language.SDMLanguagePlugin;
import org.moflon.tgg.runtime.TGGRuntimePlugin;
import org.moflon.util.plugins.manifest.ManifestFileUpdater;
import org.moflon.util.plugins.manifest.ManifestFileUpdater.AttributeUpdatePolicy;
import org.moflon.util.plugins.manifest.PluginManifestConstants;

public class PluginProducerWorkspaceRunnable implements IWorkspaceRunnable
{
   private static final Logger logger = Logger.getLogger(PluginProducerWorkspaceRunnable.class);

   private static final String DEFAULT_BUNDLE_MANIFEST_VERSION = "2";

   private static final String DEFAULT_MANIFEST_VERSION = "1.0";

   private static final String DEFAULT_BUNDLE_VENDOR = "";

   private static final String DEFAULT_BUNDLE_VERSION = "0.0.1.qualifier";

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
         addContainerToBuildPath(project, "org.eclipse.pde.core.requiredPlugins");
      } catch (IOException e)
      {
         LogUtils.error(logger, e);
      }
   }

   private void addPluginFeatures(final IProject currentProject, final MetamodelProperties properties, final IProgressMonitor monitor)
         throws CoreException, IOException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Creating/updating plugin project " + currentProject.getName(), 2);

      registerPluginBuildersAndAddNature(currentProject, subMon.split(1));

      logger.debug("Adding MANIFEST.MF to " + currentProject.getName());
      manifestFileBuilder.processManifest(currentProject, manifest -> {
         boolean changed = false;
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.MANIFEST_VERSION, DEFAULT_MANIFEST_VERSION,
               AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_MANIFEST_VERSION, DEFAULT_BUNDLE_MANIFEST_VERSION,
               AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_NAME, properties.get(MetamodelProperties.NAME_KEY),
               AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_SYMBOLIC_NAME,
               properties.get(MetamodelProperties.PLUGIN_ID_KEY) + ";singleton:=true", AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VERSION, DEFAULT_BUNDLE_VERSION, AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VENDOR, DEFAULT_BUNDLE_VENDOR, AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_ACTIVATION_POLICY, "lazy", AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_EXECUTION_ENVIRONMENT,
               properties.get(MetamodelProperties.JAVA_VERION), AttributeUpdatePolicy.KEEP);

         changed |= ManifestFileUpdater.updateDependencies(manifest, Arrays.asList(new String[] { WorkspaceHelper.PLUGIN_ID_ECORE,
               WorkspaceHelper.PLUGIN_ID_ECORE_XMI, WorkspaceHelper.getPluginId(MoflonUtilitiesActivator.class) }));

         changed |= ManifestFileUpdater.updateDependencies(manifest,
               ManifestFileUpdater.extractDependencies(properties.get(MetamodelProperties.DEPENDENCIES_KEY)));

         try
         {
            if (currentProject.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID))
               changed |= ManifestFileUpdater.updateDependencies(manifest,
                     Arrays.asList(new String[] { WorkspaceHelper.DEFAULT_LOG4J_DEPENDENCY, WorkspaceHelper.getPluginId(MocaTreePlugin.class),
                           WorkspaceHelper.PLUGIN_ID_ECLIPSE_RUNTIME, WorkspaceHelper.getPluginId(SDMLanguagePlugin.class),
                           WorkspaceHelper.getPluginId(TGGLanguageActivator.class), WorkspaceHelper.getPluginId(TGGRuntimePlugin.class) }));
         } catch (Exception e)
         {
            LogUtils.error(logger, e);
         }

         changed |= migrateOldManifests(manifest);

         return changed;
      });

      logger.debug("Adding build.properties " + currentProject.getName());
      buildPropertiesFileBuilder.createBuildProperties(currentProject, subMon.split(1));
   }

   /**
    * This method removes old dependencies and replaces old plugin ids with new ones.
    * 
    */
   private boolean migrateOldManifests(final Manifest manifest)
   {
      boolean changed = false;

      // Old ID of the "Moflon Utilities" plugin
      changed |= ManifestFileUpdater.removeDependency(manifest, "org.moflon.dependencies");

      // [Dec 2015] Remove TGG debugger
      changed |= ManifestFileUpdater.removeDependency(manifest, "org.moflon.tgg.debug.language");

      // Refactoring of plugin IDs in August 2015
      // Map<String, String> replacementMap = new HashMap<>();
      // replacementMap.put("org.moflon.testframework",
      // "org.moflon.testing.testframework");
      // replacementMap.put("org.moflon.validation",
      // "org.moflon.validation.validationplugin");
      // changed |= ManifestFileUpdater.replaceDependencies(manifest,
      // replacementMap);

      return changed;
   }

   private static void registerPluginBuildersAndAddNature(final IProject currentProject, final IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Registering plugin builders and add plugin nature", 2);

      IProjectDescription description = WorkspaceHelper.getDescriptionWithAddedNature(currentProject, WorkspaceHelper.PLUGIN_NATURE_ID, subMon.split(1));

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
      currentProject.setDescription(description, subMon.split(1));
   }

   private static boolean containsBuilder(final List<ICommand> builders, final String name)
   {
      return builders.stream().anyMatch(c -> c.getBuilderName().equals(name));
   }

   /**
    * Adds the given container to the list of build path entries (if not included, yet)
    */
   private static void addContainerToBuildPath(final Collection<IClasspathEntry> classpathEntries, final String container)
   {
      IClasspathEntry entry = JavaCore.newContainerEntry(new Path(container));
      for (IClasspathEntry iClasspathEntry : classpathEntries)
      {
         if (iClasspathEntry.getPath().equals(entry.getPath()))
         {
            // No need to add variable - already on classpath
            return;
         }
      }

      classpathEntries.add(entry);
   }

   /**
    * Adds the given container to the build path of the given project if it contains no entry with the same name, yet.
    */
   private static void addContainerToBuildPath(final IProject project, final String container)
   {
      final IJavaProject iJavaProject = JavaCore.create(project);
      try
      {
         // Get current entries on the classpath
         Collection<IClasspathEntry> classpathEntries = new ArrayList<>(Arrays.asList(iJavaProject.getRawClasspath()));
      
         addContainerToBuildPath(classpathEntries, container);
      
         setBuildPath(iJavaProject, classpathEntries);
      } catch (JavaModelException e)
      {
         LogUtils.error(logger, e, "Unable to set classpath variable");
      }
   }

   private static void setBuildPath(final IJavaProject javaProject, final Collection<IClasspathEntry> entries) throws JavaModelException
   {
      final SubMonitor subMon = SubMonitor.convert(new NullProgressMonitor(), "Set build path", 1);
      // Create new buildpath
      IClasspathEntry[] newEntries = new IClasspathEntry[entries.size()];
      entries.toArray(newEntries);
      
      // Set new classpath with added entries
      javaProject.setRawClasspath(newEntries, subMon.split(1));
   }
}
