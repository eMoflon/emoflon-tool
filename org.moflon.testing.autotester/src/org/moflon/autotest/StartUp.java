package org.moflon.autotest;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IStartup;

/**
 * Triggers the autostart method of the test activator
 */
public class StartUp implements IStartup
{

   @Override
   public void earlyStartup()
   {
      final Job job = new Job("Moflon: Autostart ...") {
         @Override
         protected IStatus run(final IProgressMonitor monitor)
         {
            final SubMonitor subMon = SubMonitor.convert(monitor, "eMoflon Autostart", 100);
            AutoTestActivator.getDefault().autoStart(subMon.split(100));
            return new Status(IStatus.OK, AutoTestActivator.getModuleID(), IStatus.OK, "", null);

         }
      };
      job.schedule();
   }

}
