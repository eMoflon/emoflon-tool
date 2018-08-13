package org.moflon.ide.core.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.moflon.TGGLanguageActivator;
import org.moflon.core.build.MoflonProjectCreator;
import org.moflon.core.build.nature.MoflonProjectConfigurator;
import org.moflon.core.plugins.PluginProperties;
import org.moflon.core.propertycontainer.MetaModelProject;
import org.moflon.core.propertycontainer.MoflonPropertiesContainer;
import org.moflon.core.propertycontainer.MoflonPropertiesContainerHelper;
import org.moflon.core.propertycontainer.PropertycontainerFactory;
import org.moflon.core.propertycontainer.SDMCodeGeneratorIds;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.ide.core.runtime.builders.IntegrationBuilder;
import org.moflon.ide.core.runtime.natures.IntegrationNature;
import org.moflon.sdm.language.SDMLanguagePlugin;
import org.moflon.tgg.runtime.TGGRuntimePlugin;

import MocaTree.MocaTreeFactory;

public class IntegrationProjectCreator extends MoflonProjectCreator {

	private static final List<String> GITIGNORE_LINES = Arrays.asList(//
			"/bin", //
			"/gen/*", //
			"/model/*.ecore", "/model/*.genmodel", "/model/*.xmi", //
			"# The file AttrCondDefLibrary.tgg is not meant to be edited", //
			"/**/AttrCondDefLibrary.tgg", "!/**/.keep*");

	/**
	 * Pass-through constructor to {@link MoflonProjectCreator}
	 *
	 * @param project
	 *            the project to create
	 * @param projectProperties
	 *            the metadata to use
	 * @param projectConfigurator
	 *            the project configurator
	 */
	public IntegrationProjectCreator(final IProject project, final PluginProperties projectProperties,
			final MoflonProjectConfigurator projectConfigurator) {
		super(project, projectProperties, projectConfigurator);
		addIntegrationSpecificDependencies(projectProperties);
	}

	private void addIntegrationSpecificDependencies(PluginProperties projectProperties) {

		List<String> integrationSpecificDependencies = Arrays.asList(new String[] { //
				WorkspaceHelper.getPluginId(eMoflonEMFUtil.class), //
				WorkspaceHelper.getPluginId(MocaTreeFactory.class), //
				WorkspaceHelper.PLUGIN_ID_ECLIPSE_RUNTIME, //
				WorkspaceHelper.getPluginId(SDMLanguagePlugin.class), //
				WorkspaceHelper.getPluginId(TGGLanguageActivator.class), //
				WorkspaceHelper.getPluginId(TGGRuntimePlugin.class) //
		});

		List<String> allDependencies = new ArrayList<>();
		allDependencies.addAll(integrationSpecificDependencies);

		projectProperties.setDependencies(allDependencies);

	}

	/**
	 * The generated code of integration projects contains a lot of unused code.
	 * Therefore, we ignore the resulting warnings.
	 */
	@Override
	protected boolean shallIgnoreGenWarnings() {
		return true;
	}

	@Override
	protected SDMCodeGeneratorIds getCodeGeneratorHandler() {
		return SDMCodeGeneratorIds.DEMOCLES_REVERSE_NAVI;
	}

	@Override
	protected List<String> getGitignoreLines() {
		return GITIGNORE_LINES;
	}

	@Override
	protected String getNatureId() throws CoreException {
		return IntegrationNature.getId();
	}

	@Override
	protected String getBuilderId() throws CoreException {
		return IntegrationBuilder.getId();
	}

	@Override
	protected void initializeMoflonProperties(MoflonPropertiesContainer moflonProperties) {
		super.initializeMoflonProperties(moflonProperties);
		final PropertycontainerFactory factory = PropertycontainerFactory.eINSTANCE;

		if (moflonProperties.getTGGBuildMode() == null) {
			moflonProperties.setTGGBuildMode(factory.createTGGBuildMode());
		}

		if(moflonProperties.getMetaModelProject() == null) {
			MetaModelProject metaModelProject = PropertycontainerFactory.eINSTANCE.createMetaModelProject();
			metaModelProject.setMetaModelProjectName(MoflonPropertiesContainerHelper.UNDEFINED_METAMODEL_NAME);
			moflonProperties.setMetaModelProject(metaModelProject);
		}
	}

}
