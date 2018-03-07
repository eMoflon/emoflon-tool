package org.moflon.ide.core.runtime.natures;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.moflon.core.build.nature.MoflonProjectConfigurator;
import org.moflon.ide.core.runtime.builders.RepositoryBuilder;

public class RepositoryNature extends MoflonProjectConfigurator {

	private static final String NATURE_ID = "org.moflon.ide.core.runtime.natures.RepositoryNature";

	/**
	 * Returns whether the project is a a repository project.
	 *
	 * @param project
	 *            the project. May be null.
	 */
	public static boolean isRepositoryProject(final IProject project) throws CoreException {
		return project != null && project.hasNature(RepositoryNature.getId());
	}

	/**
	 * Returns whether the project is a a repository project, if an exception is
	 * thrown, the method return false.
	 *
	 * @param project
	 *            the project. May be null.
	 */
	public static boolean isRepositoryProjectNoThrow(IProject project) {
		try {
			return isRepositoryProject(project);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	protected String getBuilderId() {
		return RepositoryBuilder.getId();
	}

	@Override
	protected String getNatureId() {
		return getId();
	}

	public static String getId() {
		return NATURE_ID;
	}
}
