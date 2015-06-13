package org.moflon.eclipse.job;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

public interface IMonitoredJob {
   public IStatus run(IProgressMonitor monitor);
   public String getTaskName();
}
