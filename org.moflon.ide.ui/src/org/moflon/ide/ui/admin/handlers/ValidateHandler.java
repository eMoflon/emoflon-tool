package org.moflon.ide.ui.admin.handlers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobGroup;
import org.eclipse.ui.PlatformUI;
import org.gervarro.eclipse.task.ITask;
import org.moflon.compiler.sdm.democles.eclipse.MonitoredSDMValidator;
import org.moflon.core.preferences.EMoflonPreferencesActivator;
import org.moflon.ide.ui.preferences.EMoflonPreferenceInitializer;

public class ValidateHandler extends AbstractValidateHandler
{

   @SuppressWarnings("deprecation")
   @Override
   protected void validateFile(final IFile ecoreFile, final IProgressMonitor monitor)
   {
      final long validationTimeoutMillis = EMoflonPreferenceInitializer.getValidationTimeoutMillis();
      final String validationTimeoutMessage = "Validation took longer than " + validationTimeoutMillis/1000
            + "s. This could(!) mean that some of your patterns have no valid search plan. You may increase the timeout value using the eMoflon property page";
      try
      {
         final SubMonitor subMon = SubMonitor.convert(monitor, "Validating " + ecoreFile.getName(), 1);

			final ITask validationTask = new MonitoredSDMValidator(ecoreFile, EMoflonPreferencesActivator.getDefault().getPreferencesStorage());
			if (validationTask != null) {
				final Job job = new Job(validationTask.getTaskName()) {
					@Override
					protected IStatus run(final IProgressMonitor monitor) {
						return validationTask.run(monitor);
					}
				};
				final boolean runInBackground = PlatformUI.getPreferenceStore().getBoolean("RUN_IN_BACKGROUND");
				if (!runInBackground) {
					// Run in foreground
					PlatformUI.getWorkbench().getProgressService().showInDialog(null, job);
				}
				JobGroup jobGroup = new JobGroup("Validation job group", 1, 1);
            job.setJobGroup(jobGroup);
            job.schedule();
            boolean completed = jobGroup.join(validationTimeoutMillis, new NullProgressMonitor());
            if (!completed)
            {
               // TODO@rkluge: This is a really ugly hack that should be removed as soon as a more elegant solution is
               // available
               job.getThread().stop();
               logger.error(validationTimeoutMessage);
               throw new OperationCanceledException(validationTimeoutMessage);
            }
         }

			subMon.worked(1);
      } catch (InterruptedException e)
      {
         throw new OperationCanceledException(validationTimeoutMessage);
      }
   }
}
