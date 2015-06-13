package org.moflon.ide.debug.ui.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.moflon.validation.EnterpriseArchitectValidationHelper;

/**
 * This class represents a long running operation
 * 
 * @see http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/JFacesProgressMonitorDialog.htm
 */
public class EALaunchProgressMonitor implements IRunnableWithProgress
{
   // The total sleep time
   private static final int TOTAL_TIME = 40000;

   // The increment sleep time
   private static final int INCREMENT = 1000;

   private boolean indeterminate;

   private EnterpriseArchitectValidationHelper helper;

   /**
    * LongRunningOperation constructor
    * 
    * @param indeterminate
    *           whether the animation is unknown
    */
   public EALaunchProgressMonitor(EnterpriseArchitectValidationHelper helper, boolean indeterminate)
   {
      this.indeterminate = indeterminate;
      this.helper = helper;
   }

   /**
    * Runs the long running operation
    * 
    * @param monitor
    *           the progress monitor
    */
   public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
   {
      monitor.beginTask("I'm opening Enterprise Architect for you...", indeterminate ? IProgressMonitor.UNKNOWN : TOTAL_TIME);
      for (int total = 0; total < TOTAL_TIME && !monitor.isCanceled(); total += INCREMENT)
      {
         System.out.println("wait");
         Thread.sleep(INCREMENT);
         if (helper.isEARunning())
         {
            System.out.println("break");
            break;
         } else
         {
            helper.reset();
            monitor.worked(INCREMENT);
            if (total == TOTAL_TIME / 2)
               monitor.subTask("It looks like EA does not start.");
            if (total == TOTAL_TIME * 0.75)
               monitor.subTask("I stop waiting. Please start EA manually and try again.");
         }
      }
      monitor.done();
      if (monitor.isCanceled())
         throw new InterruptedException("The long running operation was cancelled");
   }
}
