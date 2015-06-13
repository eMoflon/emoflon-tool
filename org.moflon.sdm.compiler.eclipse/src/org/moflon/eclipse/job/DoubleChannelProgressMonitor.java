package org.moflon.eclipse.job;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ProgressMonitorWrapper;

public class DoubleChannelProgressMonitor extends ProgressMonitorWrapper
{
   private final IProgressMonitor secondaryMonitor;

   protected DoubleChannelProgressMonitor(IProgressMonitor primaryMonitor, IProgressMonitor secondaryMonitor)
   {
      super(primaryMonitor);
      Assert.isNotNull(secondaryMonitor);
      this.secondaryMonitor = secondaryMonitor;
   }

   public void beginTask(String name, int totalWork)
   {
      super.beginTask(name, totalWork);
      secondaryMonitor.beginTask(name, totalWork);
   }

   public void clearBlocked()
   {
      super.clearBlocked();
      if (secondaryMonitor instanceof IProgressMonitorWithBlocking)
         ((IProgressMonitorWithBlocking) secondaryMonitor).clearBlocked();
   }

   public void done()
   {
      super.done();
      secondaryMonitor.done();
   }

   public void internalWorked(double work)
   {
      super.internalWorked(work);
      secondaryMonitor.internalWorked(work);
   }

   public void setBlocked(IStatus reason)
   {
      super.setBlocked(reason);
      if (secondaryMonitor instanceof IProgressMonitorWithBlocking)
         ((IProgressMonitorWithBlocking) secondaryMonitor).setBlocked(reason);
   }

   public void setCanceled(boolean b)
   {
      super.setCanceled(b);
      secondaryMonitor.setCanceled(b);
   }

   public void setTaskName(String name)
   {
      super.setTaskName(name);
      secondaryMonitor.setTaskName(name);
   }

   public void subTask(String name)
   {
      super.subTask(name);
      secondaryMonitor.subTask(name);
   }

   public void worked(int work)
   {
      super.worked(work);
      secondaryMonitor.worked(work);
   }
}
