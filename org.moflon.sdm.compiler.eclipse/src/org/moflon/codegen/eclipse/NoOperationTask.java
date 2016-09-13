package org.moflon.codegen.eclipse;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.gervarro.eclipse.task.ITask;

public final class NoOperationTask implements ITask {
	
	private final String taskName;
	
	public NoOperationTask(final String taskName) {
		this.taskName = taskName;
	}
	
	@Override
	public IStatus run(final IProgressMonitor monitor) {
		try {
         monitor.beginTask(taskName, 1);
			monitor.worked(1);
			return new Status(IStatus.OK, CodeGeneratorPlugin.getModuleID(), taskName + " succeeded");
		} finally {
			monitor.done();
		}
	}

	@Override
	public final String getTaskName() {
		return taskName;
	}
}
