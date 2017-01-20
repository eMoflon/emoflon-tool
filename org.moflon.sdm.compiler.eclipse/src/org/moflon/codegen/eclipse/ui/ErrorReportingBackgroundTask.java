package org.moflon.codegen.eclipse.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.statushandlers.StatusManager;

// TODO gervarro: Look up the functionality of ISafeRunnable!!!
public class ErrorReportingBackgroundTask implements Runnable
{
   private final String pluginID;

   private final Runnable runnable;

   public ErrorReportingBackgroundTask(String pluginID, Runnable runnable)
   {
      this.pluginID = pluginID;
      this.runnable = runnable;
   }

   @Override
   public void run()
   {
      try
      {
         runnable.run();
      } catch (final Exception exception)
      {
         Display.getDefault().asyncExec(new Runnable() {
            public void run()
            {
               Status errorStatus = new Status(IStatus.ERROR, pluginID, exception.getMessage(), exception);
               StatusManager.getManager().handle(errorStatus, StatusManager.SHOW);
            }
         });
      }
   }
}
