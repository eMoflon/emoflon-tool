package org.moflon.ide.core.runtime.natures;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.gervarro.eclipse.workspace.autosetup.ProjectConfigurator;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;
import org.moflon.ide.core.runtime.ProjectNatureAndBuilderConfiguratorTask;

abstract public class ProjectConfiguratorNature implements IProjectNature, ProjectConfigurator {
	private IProject project;

	@Override
	public String[] updateNatureIDs(final String[] natureIDs, final boolean added) throws CoreException {
		return natureIDs;
	}

	@Override
	public ICommand[] updateBuildSpecs(final IProjectDescription description, final ICommand[] buildSpecs, final boolean added)
			throws CoreException {
		return buildSpecs;
	}

	@Override
	public void configure() throws CoreException {
		final ProjectNatureAndBuilderConfiguratorTask configuratorTask =
				new ProjectNatureAndBuilderConfiguratorTask(project, false);
		configuratorTask.updateBuildSpecs(this, true);
		WorkspaceTask.execute(configuratorTask, false);
	}

	@Override
	public void deconfigure() throws CoreException {
		final ProjectNatureAndBuilderConfiguratorTask configuratorTask =
				new ProjectNatureAndBuilderConfiguratorTask(project, false);
		configuratorTask.updateBuildSpecs(this, false);
		WorkspaceTask.execute(configuratorTask, false);
	}

	@Override
	public final IProject getProject() {
		return project;
	}

	@Override
	public final void setProject(final IProject project) {
		this.project = project;
	}
}
