package org.moflon.ide.ui.admin.handlers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobGroup;
import org.eclipse.ui.PlatformUI;
import org.gervarro.eclipse.task.ITask;
import org.gervarro.eclipse.workspace.util.IWorkspaceTask;
import org.gervarro.eclipse.workspace.util.WorkspaceTaskJob;
import org.moflon.core.preferences.EMoflonPreferencesActivator;
import org.moflon.ide.ui.preferences.EMoflonPreferenceInitializer;

public class ValidateWithDumpingHandler extends AbstractValidateHandler
{

   @SuppressWarnings("deprecation")
   @Override
   protected void validateFile(final IFile ecoreFile, final IProgressMonitor monitor) throws CoreException
   {
      final long validationTimeoutMillis = EMoflonPreferenceInitializer.getValidationTimeoutMillis();
      final String validationTimeoutMessage = "Validation took longer than " + validationTimeoutMillis/1000
            + "s. This could(!) mean that some of your patterns have no valid search plan. You may increase the timeout value using the eMoflon property page";
      try
      {
         final SubMonitor subMon = SubMonitor.convert(monitor, "Validating " + ecoreFile.getName(), 1);

			final ITask validationTask = new org.moflon.compiler.sdm.democles.eclipse.MonitoredSDMValidatorWithDumping(ecoreFile, EMoflonPreferencesActivator.getDefault().getPreferencesStorage());

			if (validationTask != null) {

			   new WorkspaceTaskJob(new IWorkspaceTask() {

			      final IFolder modelFolder = ecoreFile.getProject().getFolder("model");

               @Override
               public void run(IProgressMonitor monitor) throws CoreException
               {
                  modelFolder.accept(new IResourceVisitor() {

                     @Override
                     public boolean visit(IResource resource) throws CoreException
                     {
                        final String trimmedResourceName = resource.getName().replaceAll(".xmi", "");
                        if (resource instanceof IFile &&
                              (trimmedResourceName.endsWith("red") // DemoclesMethodBodyHandler.RED_FILE_EXTENSION
                              || trimmedResourceName.endsWith("green") // DemoclesMethodBodyHandler.GREEN_FILE_EXTENSION
                              || trimmedResourceName.endsWith("binding") // DemoclesMethodBodyHandler.BINDING_FILE_EXTENSION
                              || trimmedResourceName.endsWith("bindingAndBlack") // DemoclesMethodBodyHandler.BINDING_AND_BLACK_FILE_EXTENSION
                              || trimmedResourceName.endsWith("black") // DemoclesMethodBodyHandler.BLACK_FILE_EXTENSION
                              || trimmedResourceName.endsWith("expression") // DemoclesMethodBodyHandler.EXPRESSION_FILE_EXTENSION
                              || trimmedResourceName.endsWith("cf") // DemoclesMethodBodyHandler.CONTROL_FLOW_FILE_EXTENSION
                              || trimmedResourceName.endsWith("dfs") // DemoclesMethodBodyHandler.DFS_FILE_EXTENSION
                              || trimmedResourceName.endsWith("sdm")) // DemoclesMethodBodyHandler.SDM_FILE_EXTENSION
                        )
                        {
                           resource.delete(true, new NullProgressMonitor());
                        }
                        return false;
                     }
                  });
               }

               @Override
               public String getTaskName()
               {
                  return "Remove old dumped files";
               }

               @Override
               public ISchedulingRule getRule()
               {
                  return modelFolder;
               }
            }).runInWorkspace(new NullProgressMonitor());

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
