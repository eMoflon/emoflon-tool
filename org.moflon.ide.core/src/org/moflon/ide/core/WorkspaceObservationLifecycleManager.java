package org.moflon.ide.core;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.gervarro.eclipse.workspace.util.ProjectStateObserver;
import org.gervarro.eclipse.workspace.util.WorkspaceTask;

public final class WorkspaceObservationLifecycleManager extends WorkspaceTask {
	private final IWorkspace workspace;
	private final ProjectStateObserver newConfig;
	private final boolean add;

	public WorkspaceObservationLifecycleManager(
			final IWorkspace workspace,
			final ProjectStateObserver newConfig,
			final boolean add) {
		this.workspace = workspace;
		this.newConfig = newConfig;
		this.add = add;
	}
	
	@Override
	public final String getTaskName() {
		return "Workspace lifecycle manager";
	}

	@Override
	public final ISchedulingRule getRule() {
		return workspace.getRoot();
	}

	@Override
	public final void run(final IProgressMonitor monitor) throws CoreException {
		if (add) {
			workspace.getRoot().accept(newConfig, IResource.DEPTH_ONE, IResource.NONE);
			workspace.addResourceChangeListener(newConfig);
		} else {
			workspace.removeResourceChangeListener(newConfig);
		}
	}
}
