package org.moflon.eclipse.job;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Usage scenarios:<br/><br/>
 * <b>1a) Asynchronous task invocation (with 1 monitor)</b><br/>
 * 		ProgressMonitoringJob internalJob = new ProgressMonitoringJob(pluginID, jobName, job);<br/>
 * 		internalJob.schedule();<br/><br/>
 * <b>1b) Asynchronous subtask invocation (with 1 monitor)</b><br/>
 * 		AsyncJobProgressMonitor asyncMonitor = new AsyncJobProgressMonitor(monitor, ticks);<br/>
 * 		ProgressMonitoringJob internalJob = new ProgressMonitoringJob(pluginID, jobName, job);<br/>
 * 		internalJob.addJobChangeListener(asyncMonitor);<br/>
 * 		internalJob.schedule();<br/><br/>
 * <b>2a) Emulated synchronous task invocation</b><br/>
 * 		ProgressMonitoringJob internalJob = new ProgressMonitoringJob(pluginID, jobName, job);<br/>
 * 		internalJob.run(monitor);<br/><br/>
 * <b>2b) Emulated synchronous subtask invocation (with 2 monitors)</b><br/>
 * 		SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, ticks);<br/>
 * 		ProgressMonitoringJob internalJob = new ProgressMonitoringJob(pluginID, jobName, job);<br/>
 * 		internalJob.run(subMonitor);<br/><br/>
 * <b>3) Real synchronous subtask invocation</b><br/>
 * 		SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, ticks);<br/>
 * 		job.run(subMonitor);
 */
public final class ProgressMonitoringJob extends Job implements IMonitoredJob {

	private final String pluginID;

	private final IMonitoredJob runnable;

	private IProgressMonitor secondaryMonitor;

	private IStatus returnStatus;

	public ProgressMonitoringJob(String pluginID, IMonitoredJob runnable) {
		super(runnable.getTaskName());
		this.pluginID = pluginID;
		this.runnable = runnable;
	}

	// 1) Asynchronous invocation with 1 monitor
	// 2) Emulated synchronous invocation with 1 or 2 monitors
	// 3) Real synchronous invocation: invoke IMonitoredJob directly
	@Override
	public final IStatus run(IProgressMonitor monitor) {
		final Job invokingJob = Job.getJobManager().currentJob();
		if (invokingJob == this) {
			// Creates a new combinedMonitor that reports to the original monitor AND to the secondaryMonitor
			IProgressMonitor combinedMonitor = secondaryMonitor != null ? new DoubleChannelProgressMonitor(monitor, secondaryMonitor) : monitor;
			try {
				return runnable.run(combinedMonitor);
			} catch (Throwable throwable) {
				this.returnStatus = new Status(IStatus.ERROR, pluginID, throwable.getMessage(), throwable);
				return this.returnStatus;
			}
		} else {
			// Emulated synchronous invocation
			this.secondaryMonitor = monitor;
			this.schedule();
			try {
				this.join();
				final IStatus returnStatus = this.returnStatus != null ? this.returnStatus : new Status(IStatus.OK, pluginID, "Job " + getName()
						+ " returns successfully from an emulated synchronous invocation");
				this.returnStatus = null;
				return returnStatus;
			} catch (InterruptedException e) {
				this.cancel();
				return Status.CANCEL_STATUS;
			}
		}
	}

	@Override
	public final String getTaskName() {
		return getName();
	}
}
