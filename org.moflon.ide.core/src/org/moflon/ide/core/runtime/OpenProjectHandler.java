package org.moflon.ide.core.runtime;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

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
import org.gervarro.eclipse.workspace.autosetup.ProjectNatureAndBuilderConfiguratorTask;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.moflon.TGGLanguageActivator;
import org.moflon.core.build.MoflonProjectCreator;
import org.moflon.core.build.nature.MoflonProjectConfigurator;
import org.moflon.core.plugins.BuildPropertiesFileBuilder;
import org.moflon.core.plugins.PluginProperties;
import org.moflon.core.plugins.manifest.ManifestFileUpdater;
import org.moflon.core.plugins.manifest.ManifestFileUpdater.AttributeUpdatePolicy;
import org.moflon.core.plugins.manifest.PluginManifestConstants;
import org.moflon.core.propertycontainer.Dependencies;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.PropertiesValue;
import org.moflon.core.propertycontainer.PropertycontainerFactory;
import org.moflon.core.utilities.LogUtils;
import org.moflon.core.utilities.MoflonConventions;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.project.ProjectCreatorFactory;
import org.moflon.ide.core.properties.PluginPropertiesHelper;
import org.moflon.ide.core.runtime.natures.IntegrationNature;
import org.moflon.sdm.language.SDMLanguagePlugin;
import org.moflon.tgg.runtime.TGGRuntimePlugin;

import MocaTree.MocaTreeFactory;

/**
 * This handler is invoked during the build process to update/configure open
 * projects
 *
 * @author Gergely Varró
 * @author Anthony Anjorin
 * @author Roland Kluge
 *
 * @see #run(IProgressMonitor)
 */
public class OpenProjectHandler extends WorkspaceTask {
	private static final Logger logger = Logger.getLogger(OpenProjectHandler.class);

	private static final String DEFAULT_BUNDLE_MANIFEST_VERSION = "2";

	private static final String DEFAULT_MANIFEST_VERSION = "1.0";

	private static final String DEFAULT_BUNDLE_VENDOR = "";

	private static final String DEFAULT_BUNDLE_VERSION = "0.0.1.qualifier";

	private IProject project;

	private PluginProperties metamodelProperties;

	private MoflonPropertiesContainer moflonProperties;

	private BuildPropertiesFileBuilder buildPropertiesFileBuilder = new BuildPropertiesFileBuilder();

	private MoflonProjectConfigurator projectConfigurator;

	public OpenProjectHandler(final IProject project, final PluginProperties metamodelProperties,
			final MoflonPropertiesContainer moflonProperties, MoflonProjectConfigurator projectConfigurator) {
		this.project = project;
		this.metamodelProperties = metamodelProperties;
		this.moflonProperties = moflonProperties;
		this.projectConfigurator = projectConfigurator;
	}

	@Override
	public void run(final IProgressMonitor monitor) throws CoreException {
		SubMonitor subMon = SubMonitor.convert(monitor, "Configure open project", 5);

		final JavaProjectConfigurator javaProjectConfigurator = new JavaProjectConfigurator();
		final MoflonProjectConfigurator moflonProjectConfigurator = this.projectConfigurator;
		final PluginProjectConfigurator pluginProjectConfigurator = new PluginProjectConfigurator();
		final ProjectNatureAndBuilderConfiguratorTask natureAndBuilderConfiguratorTask = new ProjectNatureAndBuilderConfiguratorTask(
				project, false);
		natureAndBuilderConfiguratorTask.updateNatureIDs(javaProjectConfigurator, true);
		natureAndBuilderConfiguratorTask.updateBuildSpecs(javaProjectConfigurator, true);
		natureAndBuilderConfiguratorTask.updateNatureIDs(moflonProjectConfigurator, true);
		natureAndBuilderConfiguratorTask.updateBuildSpecs(moflonProjectConfigurator, true);
		natureAndBuilderConfiguratorTask.updateNatureIDs(pluginProjectConfigurator, true);
		natureAndBuilderConfiguratorTask.updateBuildSpecs(pluginProjectConfigurator, true);
		WorkspaceTask.executeInCurrentThread(natureAndBuilderConfiguratorTask, IWorkspace.AVOID_UPDATE,
				subMon.split(1));
		// Last line to be removed

		subMon.worked(1);

		try {
			MoflonProjectCreator projectCreator = ProjectCreatorFactory.getProjectCreator(project, metamodelProperties,
					moflonProjectConfigurator);
			projectCreator.createFoldersIfNecessary(project, subMon.split(1));
		} catch (final CoreException e) {
			logger.warn("Failed to create folders: " + e.getMessage());
		}

		try {

			updatePluginDependencies(monitor, project);

			moflonProperties.getDependencies().clear();
			for (final URI dependencyURI : metamodelProperties.getDependenciesAsURIs()) {
				addMetamodelDependency(moflonProperties, dependencyURI);
			}

			addMetamodelDependency(moflonProperties,
					MoflonConventions.getDefaultPluginDependencyUri("org.eclipse.emf.ecore"));

			if (PluginPropertiesHelper.isIntegrationProject(metamodelProperties)) {
				addMetamodelDependency(moflonProperties, MoflonConventions
						.getDefaultPluginDependencyUri(WorkspaceHelper.getPluginId(TGGRuntimePlugin.class)));
				addMetamodelDependency(moflonProperties, MoflonConventions
						.getDefaultPluginDependencyUri(WorkspaceHelper.getPluginId(SDMLanguagePlugin.class)));
				addMetamodelDependency(moflonProperties, MoflonConventions
						.getDefaultPluginDependencyUri(WorkspaceHelper.getPluginId(TGGLanguageActivator.class)));
				addMetamodelDependency(moflonProperties, MoflonConventions
						.getDefaultPluginDependencyUri(WorkspaceHelper.getPluginId(MocaTreeFactory.class)));
			}
		} catch (final IOException e) {
			LogUtils.error(logger, e);
		}
	}

	private void updatePluginDependencies(final IProgressMonitor monitor, IProject project)
			throws CoreException, IOException {
		final SubMonitor subMon = SubMonitor.convert(monitor, "Updating plugin project " + project.getName(), 2);

		logger.debug("Updating MANIFEST.MF in " + project.getName());
		final ManifestFileUpdater manifestFileBuilder = new ManifestFileUpdater();
		manifestFileBuilder.processManifest(project, manifest -> {
			final String bundleName = metamodelProperties.get(PluginProperties.NAME_KEY);
			final String pluginId = metamodelProperties.get(PluginProperties.PLUGIN_ID_KEY) + ";singleton:=true";
			boolean changed = false;
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.MANIFEST_VERSION,
					DEFAULT_MANIFEST_VERSION, AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_MANIFEST_VERSION,
					DEFAULT_BUNDLE_MANIFEST_VERSION, AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_NAME, bundleName,
					AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_SYMBOLIC_NAME,
					pluginId, AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VERSION,
					DEFAULT_BUNDLE_VERSION, AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_VENDOR,
					DEFAULT_BUNDLE_VENDOR, AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest, PluginManifestConstants.BUNDLE_ACTIVATION_POLICY,
					"lazy", AttributeUpdatePolicy.KEEP);
			changed |= ManifestFileUpdater.updateAttribute(manifest,
					PluginManifestConstants.BUNDLE_EXECUTION_ENVIRONMENT, "JavaSE-1.8", AttributeUpdatePolicy.KEEP);

			changed |= ManifestFileUpdater.updateDependencies(manifest, Arrays
					.asList(new String[] { WorkspaceHelper.PLUGIN_ID_ECORE, WorkspaceHelper.PLUGIN_ID_ECORE_XMI }));

			changed |= ManifestFileUpdater.updateDependencies(manifest, ManifestFileUpdater
					.extractDependencies(metamodelProperties.get(PluginProperties.DEPENDENCIES_KEY)));

			try {
				if (IntegrationNature.isIntegrationProject(project))
					changed |= ManifestFileUpdater.updateDependencies(manifest, Arrays.asList(new String[] { //
							WorkspaceHelper.DEFAULT_LOG4J_DEPENDENCY, //
							WorkspaceHelper.getPluginId(MocaTreeFactory.class), //
							WorkspaceHelper.PLUGIN_ID_ECLIPSE_RUNTIME, //
							WorkspaceHelper.getPluginId(SDMLanguagePlugin.class), //
							WorkspaceHelper.getPluginId(TGGLanguageActivator.class), //
							WorkspaceHelper.getPluginId(TGGRuntimePlugin.class) //
					}));
			} catch (final Exception e) {
				LogUtils.error(logger, e);
			}

			return changed;
		});
		subMon.worked(1);

		logger.debug("Adding build.properties " + project.getName());
		buildPropertiesFileBuilder.createBuildProperties(project, subMon.split(1));
	}

	public void addMetamodelDependency(final MoflonPropertiesContainer moflonProperties, final URI metamodelUri) {
		final Dependencies dep = PropertycontainerFactory.eINSTANCE.createDependencies();
		dep.setValue(metamodelUri.toString());
		if (!alreadyContainsDependency(moflonProperties.getDependencies(), dep)) {
			moflonProperties.getDependencies().add(dep);
		}
	}

	private boolean alreadyContainsDependency(final Collection<? extends PropertiesValue> dependencies,
			final PropertiesValue addDep) {
		return dependencies.stream().anyMatch(d -> d.getValue().equals(addDep.getValue()));
	}

	@Override
	public String getTaskName() {
		return "Handling open Moflon project";
	}

	@Override
	public ISchedulingRule getRule() {
		return project;
	}
}
