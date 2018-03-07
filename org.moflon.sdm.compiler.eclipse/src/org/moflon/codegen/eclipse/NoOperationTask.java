package org.moflon.codegen.eclipse;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.gervarro.eclipse.task.ITask;
import org.moflon.core.utilities.WorkspaceHelper;

public final class NoOperationTask implements ITask {

	private final String taskName;

	public NoOperationTask(final String taskName) {
		this.taskName = taskName;
	}

	@Override
	public IStatus run(final IProgressMonitor monitor) {
		final SubMonitor subMon = SubMonitor.convert(monitor, taskName, 1);
		subMon.worked(1);
		return new Status(IStatus.OK, WorkspaceHelper.getPluginId(getClass()), taskName + " succeeded");
	}

	@Override
	public final String getTaskName() {
		return taskName;
	}
}
