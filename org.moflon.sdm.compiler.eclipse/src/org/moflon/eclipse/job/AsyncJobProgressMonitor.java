package org.moflon.eclipse.job;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ProgressMonitorWrapper;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;

public class AsyncJobProgressMonitor extends ProgressMonitorWrapper implements IJobChangeListener
{
   private final int ticks;

   public AsyncJobProgressMonitor(IProgressMonitor monitor, int ticks)
   {
      super(monitor);
      this.ticks = ticks;
   }

   @Override
   public void aboutToRun(IJobChangeEvent event)
   {
      // Do nothing
   }

   @Override
   public void awake(IJobChangeEvent event)
   {
      // Do nothing
   }

   @Override
   public void done(IJobChangeEvent event)
   {
      event.getJob().removeJobChangeListener(this);
      if (event.getJob().getResult().isOK())
      {
         getWrappedProgressMonitor().worked(ticks);
      }
   }

   @Override
   public void running(IJobChangeEvent event)
   {
      // Do nothing
   }

   @Override
   public void scheduled(IJobChangeEvent event)
   {
      // Do nothing
   }

   @Override
   public void sleeping(IJobChangeEvent event)
   {
      // Do nothing
   }
}
