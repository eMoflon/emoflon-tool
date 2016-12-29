package org.moflon.ide.core.runtime;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.common.util.URI;
import org.gervarro.eclipse.workspace.autosetup.JavaProjectConfigurator;
import org.gervarro.eclipse.workspace.autosetup.PluginProjectConfigurator;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.moflon.TGGLanguageActivator;
import org.moflon.core.moca.tree.MocaTreePlugin;
import org.moflon.core.propertycontainer.Dependencies;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.PropertiesValue;
import org.moflon.core.propertycontainer.PropertycontainerFactory;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.runtime.natures.IntegrationNature;
import org.moflon.ide.core.runtime.natures.MoflonProjectConfigurator;
import org.moflon.ide.core.runtime.natures.RepositoryNature;
import org.moflon.sdm.language.SDMLanguagePlugin;
import org.moflon.tgg.runtime.TGGRuntimePlugin;
import org.moflon.util.plugins.BuildPropertiesFileBuilder;
import org.moflon.util.plugins.MetamodelProperties;
import org.moflon.util.plugins.manifest.ManifestFileUpdater;
import org.moflon.util.plugins.manifest.ManifestFileUpdater.AttributeUpdatePolicy;
import org.moflon.util.plugins.manifest.PluginManifestConstants;

/**
 * This handler is invoked during the build process to update/configure open projects
 * 
 * @author Gergely Varro
 * @author Anthony Anjorin
 * @author Roland Kluge 
 *
 * @see #run(IProgressMonitor)
 */
public class OpenProjectHandler extends WorkspaceTask
{
   private static final Logger logger = Logger.getLogger(OpenProjectHandler.class);

   private static final String DEFAULT_BUNDLE_MANIFEST_VERSION = "2";

   private static final String DEFAULT_MANIFEST_VERSION = "1.0";

   private static final String DEFAULT_BUNDLE_VENDOR = "TU Darmstadt";

   private static final String DEFAULT_BUNDLE_VERSION = "1.0.0.qualifier";

   private IProject project;

   private MetamodelProperties metamodelProperties;

   private MoflonPropertiesContainer moflonProperties;

   private BuildPropertiesFileBuilder buildPropertiesFileBuilder = new BuildPropertiesFileBuilder();

   public OpenProjectHandler(final IProject project, final MetamodelProperties metamodelProperties, final MoflonPropertiesContainer moflonProperties)
   {
      this.project = project;
      this.metamodelProperties = metamodelProperties;
      this.moflonProperties = moflonProperties;
   }

   @Override
   public void run(final IProgressMonitor monitor) throws CoreException
   {
      SubMonitor subMon = SubMonitor.convert(monitor, "Configure open project", 5);
      
      final JavaProjectConfigurator javaProjectConfigurator = new JavaProjectConfigurator();
      final MoflonProjectConfigurator moflonProjectConfigurator = getProjectConfigurator(this.metamodelProperties);
      final PluginProjectConfigurator pluginProjectConfigurator = new PluginProjectConfigurator();
      final ProjectNatureAndBuilderConfiguratorTask natureAndBuilderConfiguratorTask =
    		  new ProjectNatureAndBuilderConfiguratorTask(project, false);
      natureAndBuilderConfiguratorTask.updateNatureIDs(javaProjectConfigurator, true);
      natureAndBuilderConfiguratorTask.updateBuildSpecs(javaProjectConfigurator, true);
      natureAndBuilderConfiguratorTask.updateNatureIDs(moflonProjectConfigurator, true);
      natureAndBuilderConfiguratorTask.updateBuildSpecs(moflonProjectConfigurator, true);
      natureAndBuilderConfiguratorTask.updateNatureIDs(pluginProjectConfigurator, true);
      natureAndBuilderConfiguratorTask.updateBuildSpecs(pluginProjectConfigurator, true);
      WorkspaceTask.executeInCurrentThread(natureAndBuilderConfiguratorTask, IWorkspace.AVOID_UPDATE, subMon.split(1));
      // Last line to be removed

      subMon.worked(1);
      
      try
      {
         MoflonProjectCreator.createFoldersIfNecessary(project, subMon.split(1));
         MoflonProjectCreator.addGitignoreFileForRepositoryProject(project, subMon.split(1));
         MoflonProjectCreator.addGitKeepFiles(project, subMon.split(1));
      } catch (final CoreException e)
      {
         logger.warn("Failed to create folders: " + e.getMessage());
      }
      
      try
      {

         // TODO Plugin dependency handling should be updated by Repository/IntegrationBuilders
         updatePluginDependencies(monitor, project);

         moflonProperties.getDependencies().clear();
         for (final URI dependencyURI : metamodelProperties.getDependenciesAsURIs())
         {
            addMetamodelDependency(moflonProperties, dependencyURI);
         }

         // These two metamodels are usually used directly or indirectly by most projects
         addMetamodelDependency(moflonProperties, MoflonUtil.getDefaultURIToEcoreFileInPlugin(WorkspaceHelper.PLUGIN_ID_ECORE));

         if (metamodelProperties.isIntegrationProject())
         {
            addMetamodelDependency(moflonProperties, MoflonUtil.getDefaultURIToEcoreFileInPlugin(WorkspaceHelper.getPluginId(TGGRuntimePlugin.class)));
            addMetamodelDependency(moflonProperties, MoflonUtil.getDefaultURIToEcoreFileInPlugin(WorkspaceHelper.getPluginId(SDMLanguagePlugin.class)));
            addMetamodelDependency(moflonProperties, MoflonUtil.getDefaultURIToEcoreFileInPlugin(WorkspaceHelper.getPluginId(TGGLanguageActivator.class)));
            addMetamodelDependency(moflonProperties, MoflonUtil.getDefaultURIToEcoreFileInPlugin(WorkspaceHelper.getPluginId(MocaTreePlugin.class)));
         }
      } catch (final IOException e)
      {
         LogUtils.error(logger, e);
      }
   }
   
   private MoflonProjectConfigurator getProjectConfigurator(final MetamodelProperties metamodelProperties)
   {
      switch(metamodelProperties.getType())
      {
      case MetamodelProperties.INTEGRATION_KEY:
         return new IntegrationNature();
      case MetamodelProperties.REPOSITORY_KEY:
         return new RepositoryNature();
      default:
         return null;
      }
   }

   private void updatePluginDependencies(final IProgressMonitor monitor, IProject project) throws CoreException, IOException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Updating plugin project " + project.getName(), 2);

      logger.debug("Updating MANIFEST.MF in " + project.getName());
      final ManifestFileUpdater manifestFileBuilder = new ManifestFileUpdater();
      manifestFileBuilder.processManifest(project, manifest -> {
         boolean changed = false;
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.MANIFEST_VERSION, DEFAULT_MANIFEST_VERSION,
               AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_MANIFEST_VERSION, DEFAULT_BUNDLE_MANIFEST_VERSION,
               AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_NAME, metamodelProperties.get(MetamodelProperties.NAME_KEY),
               AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_SYMBOLIC_NAME,
               metamodelProperties.get(MetamodelProperties.PLUGIN_ID_KEY) + ";singleton:=true", AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VERSION, DEFAULT_BUNDLE_VERSION, AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VENDOR, DEFAULT_BUNDLE_VENDOR, AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_ACTIVATION_POLICY, "lazy", AttributeUpdatePolicy.KEEP);
         changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_EXECUTION_ENVIRONMENT,
               metamodelProperties.get(MetamodelProperties.JAVA_VERION), AttributeUpdatePolicy.KEEP);

         changed |= ManifestFileUpdater.updateDependencies(manifest, Arrays.asList(
               new String[] { WorkspaceHelper.PLUGIN_ID_ECORE, WorkspaceHelper.PLUGIN_ID_ECORE_XMI, WorkspaceHelper.getPluginId(MoflonUtilitiesActivator.class) }));

         changed |= ManifestFileUpdater.updateDependencies(manifest,
               ManifestFileUpdater.extractDependencies(metamodelProperties.get(MetamodelProperties.DEPENDENCIES_KEY)));

         try
         {
            if (project.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID))
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
      subMon.worked(1);

      logger.debug("Adding build.properties " + project.getName());
      buildPropertiesFileBuilder.createBuildProperties(project, subMon.split(1));
   }

   /**
    * This method removes old dependencies and replaces old plugin ids with new ones.
    */
   private boolean migrateOldManifests(final Manifest manifest)
   {
      boolean changed = false;

      // Old ID of the "Moflon Utilities" plugin
      changed |= ManifestFileUpdater.removeDependency(manifest, "org.moflon.dependencies");

      // [Dec 2015] Remove TGG debugger
      changed |= ManifestFileUpdater.removeDependency(manifest, "org.moflon.tgg.debug.language");

      // Refactoring of plugin IDs in August 2015
      Map<String, String> replacementMap = new HashMap<>();
      replacementMap.put("MoflonPropertyContainer", "org.moflon.core.propertycontainer");
      // replacementMap.put("org.moflon.testframework",
      // "org.moflon.testing.testframework");
      // replacementMap.put("org.moflon.validation",
      // "org.moflon.validation.validationplugin");
      // changed |= ManifestFileUpdater.replaceDependencies(manifest,
      // replacementMap);
      changed |= ManifestFileUpdater.replaceDependencies(manifest, replacementMap);

      return changed;
   }

   public void addMetamodelDependency(final MoflonPropertiesContainer moflonProperties, final URI metamodelUri)
   {
      Dependencies dep = PropertycontainerFactory.eINSTANCE.createDependencies();
      dep.setValue(metamodelUri.toString());
      if (!alreadyContainsDependency(moflonProperties.getDependencies(), dep))
      {
         moflonProperties.getDependencies().add(dep);
      }
   }

   private boolean alreadyContainsDependency(final Collection<? extends PropertiesValue> dependencies, final PropertiesValue addDep)
   {
      return dependencies.stream().anyMatch(d -> d.getValue().equals(addDep.getValue()));
   }

   @Override
   public String getTaskName()
   {
      return "Handling open Moflon project";
   }

   @Override
   public ISchedulingRule getRule()
   {
      return project;
   }
}
