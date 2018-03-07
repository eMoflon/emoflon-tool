package org.moflon.tgg.mosl.builder;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.moflon.ide.core.runtime.ProjectDependencyAnalyzer;

/**
 * Project dependency analysis should be carried out on a locked workspace in a
 * separate thread to avoid inconsistency in the build configuration caused by
 * the analysis process
 *
 */
class ProjectDependencyAnalyzerWorkspaceJob extends WorkspaceJob {
	private final ProjectDependencyAnalyzer projectDependencyAnalyzer;

	ProjectDependencyAnalyzerWorkspaceJob(ProjectDependencyAnalyzer projectDependencyAnalyzer) {
		super(projectDependencyAnalyzer.getTaskName());
		this.projectDependencyAnalyzer = projectDependencyAnalyzer;
	}

	@Override
	public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
		return projectDependencyAnalyzer.run(monitor);
	}
}