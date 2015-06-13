package org.moflon.autotest;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IStartup;
import org.moflon.core.utilities.WorkspaceHelper;

/**
 * Triggers the autostart method of the test activator
 */
public class StartUp implements IStartup
{

   @Override
   public void earlyStartup()
   {
      Job job = new Job("Moflon: Autostart ...") {
         @Override
         protected IStatus run(final IProgressMonitor monitor)
         {
            try
            {
               monitor.beginTask("eMoflon Autostart", 100);
               AutoTestActivator.getDefault().autoStart(WorkspaceHelper.createSubMonitor(monitor, 100));
            } finally
            {
               monitor.done();
            }
            return new Status(IStatus.OK, AutoTestActivator.getModuleID(), IStatus.OK, "", null);

         }
      };
      job.schedule();
   }

}
