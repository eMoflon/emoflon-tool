package org.moflon.ide.ui.admin.handlers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobGroup;
import org.eclipse.ui.PlatformUI;
import org.gervarro.eclipse.task.ITask;
import org.gervarro.eclipse.workspace.util.WorkspaceTaskJob;
import org.moflon.compiler.sdm.democles.eclipse.MonitoredSDMValidatorWithDumping;
import org.moflon.core.preferences.EMoflonPreferencesActivator;
import org.moflon.ide.ui.preferences.EMoflonPreferenceInitializer;

/**
 * This handler triggers an SDM validation that additionally dumps intermediate models
 * @author Roland Kluge - Initial implementation
 *
 */
public class ValidateWithDumpingHandler extends AbstractValidateHandler
{
   @Override
   protected void validateFile(final IFile ecoreFile, final IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, "Validating " + ecoreFile.getName(), 1);

      final ITask validationTask = new MonitoredSDMValidatorWithDumping(ecoreFile, EMoflonPreferencesActivator.getDefault().getPreferencesStorage());

      if (validationTask != null)
      {
         removedOldDumpFiles(ecoreFile);

         runValidation(validationTask);
      }

      subMon.worked(1);
   }

   @SuppressWarnings("deprecation")
   private void runValidation(final ITask validationTask)
   {
      final long validationTimeoutMillis = EMoflonPreferenceInitializer.getValidationTimeoutMillis();
      final String validationTimeoutMessage = "Validation took longer than " + validationTimeoutMillis / 1000
            + "s. This could(!) mean that some of your patterns have no valid search plan. You may increase the timeout value using the eMoflon property page";

      final Job job = new Job(validationTask.getTaskName()) {
         @Override
         protected IStatus run(final IProgressMonitor monitor)
         {
            return validationTask.run(monitor);
         }
      };

      final boolean runInBackground = PlatformUI.getPreferenceStore().getBoolean("RUN_IN_BACKGROUND");
      if (!runInBackground)
      {
         PlatformUI.getWorkbench().getProgressService().showInDialog(null, job);
      }

      final JobGroup jobGroup = new JobGroup("Validation job group", 1, 1);
      job.setJobGroup(jobGroup);
      job.schedule();
      try
      {
         boolean completed = jobGroup.join(validationTimeoutMillis, new NullProgressMonitor());
         if (!completed)
         {
            job.getThread().stop();
            logger.error(validationTimeoutMessage);
            throw new OperationCanceledException(validationTimeoutMessage);
         }
      } catch (InterruptedException e)
      {
         throw new OperationCanceledException(validationTimeoutMessage);
      }
   }

   private void removedOldDumpFiles(final IFile ecoreFile) throws CoreException
   {
      final WorkspaceTaskJob taskJob = new WorkspaceTaskJob(new RemoveDumpedValidationFilesJob(ecoreFile));
      taskJob.runInWorkspace(new NullProgressMonitor());
   }
}
