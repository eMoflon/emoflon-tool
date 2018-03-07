package org.moflon.ide.core.runtime.natures;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.moflon.core.build.nature.MoflonProjectConfigurator;
import org.moflon.ide.core.runtime.builders.IntegrationBuilder;

public class IntegrationNature extends MoflonProjectConfigurator {

	private static final String NATURE_ID = "org.moflon.ide.core.runtime.natures.IntegrationNature";

	/**
	 * Returns whether the project is a an integration project, that is, if it
	 * contains generated code of a TGG project.
	 *
	 * @param project
	 *            the project. May be null.
	 */
	public static boolean isIntegrationProject(final IProject project) throws CoreException {
		return project.hasNature("org.moflon.ide.core.runtime.natures.IntegrationNature");
	}

	/**
	 * A wrapper around {@link #isIntegrationProject(IProject)}, which returns false
	 * if the original method throws an exception
	 */
	public static boolean isIntegrationProjectNoThrow(IProject project) {
		try {
			return isIntegrationProject(project);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	protected String getBuilderId() {
		return IntegrationBuilder.getId();
	}

	@Override
	protected String getNatureId() {
		return getId();
	}

	public static String getId() {
		return NATURE_ID;
	}

}
