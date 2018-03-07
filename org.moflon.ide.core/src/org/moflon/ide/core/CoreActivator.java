package org.moflon.ide.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Plugin;
import org.moflon.ide.core.runtime.natures.IntegrationNature;
import org.moflon.ide.core.runtime.natures.RepositoryNature;

/**
 * The Activator controls the plug-in life cycle and contains state and
 * functionality that can be used throughout the plugin. Constants used in
 * various places in the plugin should also be defined in the Activator.
 *
 * Core (non-GUI) functionality that can be useful for other Moflon eclipse
 * plugins should be implemented here.
 */
public class CoreActivator extends Plugin {
	/**
	 * Returns whether the given project is (1) a repository project or (2) an
	 * integration project or (3) a MOSL-GT project
	 */
	public static boolean isMoflonProject(final IProject project) throws CoreException {
		return RepositoryNature.isRepositoryProject(project) || IntegrationNature.isIntegrationProject(project);
	}

	/**
	 * Returns whether the given project is (1) a repository project or (2) an
	 * integration project.
	 *
	 * Returns also false if an exception would be thrown.
	 */
	public static boolean isMoflonProjectNoThrow(final IProject project) {
		return RepositoryNature.isRepositoryProjectNoThrow(project)
				|| IntegrationNature.isIntegrationProjectNoThrow(project);
	}
}
