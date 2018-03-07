package org.moflon.ide.core.project;

import org.eclipse.core.resources.IProject;
import org.moflon.core.build.MoflonProjectCreator;
import org.moflon.core.build.nature.MoflonProjectConfigurator;
import org.moflon.core.plugins.PluginProperties;
import org.moflon.ide.core.properties.PluginPropertiesHelper;

public class ProjectCreatorFactory {

	public static MoflonProjectCreator getProjectCreator(final IProject project,
			final PluginProperties projectProperties, final MoflonProjectConfigurator projectConfigurator) {
		final String projectType = projectProperties.get(PluginProperties.TYPE_KEY);
		switch (projectType) {
		case PluginPropertiesHelper.REPOSITORY_PROJECT:
			return new RepositoryProjectCreator(project, projectProperties, projectConfigurator);
		case PluginPropertiesHelper.INTEGRATION_PROJECT:
			return new IntegrationProjectCreator(project, projectProperties, projectConfigurator);
		}
		return null;
	}

}
