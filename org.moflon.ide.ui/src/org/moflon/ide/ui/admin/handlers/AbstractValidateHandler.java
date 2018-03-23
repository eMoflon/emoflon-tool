package org.moflon.ide.ui.admin.handlers;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
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
import org.moflon.core.ui.AbstractCommandHandler;
import org.moflon.core.utilities.MoflonConventions;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.ui.preferences.EMoflonPreferenceInitializer;

/**
 * Implementation base for validation with and without dumping the intermediate
 * models
 *
 * @author Roland Kluge - Initial implementation
 *
 */
public abstract class AbstractValidateHandler extends AbstractMoflonToolCommandHandler {

	/**
	 * Invokes {@link #validateProjects(Collection, IProgressMonitor)} for all
	 * projects than can be found in the selection of the given event
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final Collection<IFile> files = this.extractSelectedFiles(event);
		final Collection<IProject> projects = this.extractSelectedProjects(event);
		try {
			this.validateFiles(files, new NullProgressMonitor());
			this.validateProjects(projects, new NullProgressMonitor());
		} catch (CoreException e) {
			throw new ExecutionException(e.getMessage(), e);
		}
		return AbstractCommandHandler.DEFAULT_HANDLER_RESULT;
	}

	/**
	 * Validates the given Ecore file, is known to exist
	 *
	 * @param ecoreFile
	 *            the Ecore file
	 * @param monitor
	 *            the progress monitor
	 * @throws CoreException
	 *             in case of any problem
	 */
	protected abstract void validateFile(final IFile ecoreFile, final IProgressMonitor monitor) throws CoreException;

	/**
	 * Invokes {@link #validateFile(IFile, IProgressMonitor)} for each file in the given list
	 * @param files the files to validate
	 * @param monitor the progress monitor
	 * @throws CoreException if validation crashes
	 */
	protected final void validateFiles(final Collection<IFile> files, final IProgressMonitor monitor) throws CoreException {
		final SubMonitor subMon = SubMonitor.convert(monitor, files.size());
		for (final IFile file : files) {
			validateFile(file, subMon.split(1));
		}
	}
	/**
	 * Runs the given validation task using {@link #runWithTimeout(Job)}
	 *
	 * @param validationTask
	 *            the validation task to run
	 */
	protected void runValidation(final ITask validationTask) {
		final Job job = new ValidationTaskJob(validationTask);

		configureBackOrForeground(job);

		runWithTimeout(job);
	}

	/**
	 * Runs the given {@link Job} and limits its execution time via the validation
	 * timeout configured via the {@link EMoflonPreferenceInitializer}
	 *
	 * @param job
	 */
	private void runWithTimeout(final Job job) {
		final long validationTimeoutMillis = EMoflonPreferenceInitializer.getValidationTimeoutMillis();
		final JobGroup jobGroup = new JobGroup("Validation job group", 1, 1);
		job.setJobGroup(jobGroup);
		job.schedule();
		try {
			final boolean completed = jobGroup.join(validationTimeoutMillis, new NullProgressMonitor());
			if (!completed) {
				stopJob(job);
				logger.error(getValidationTimeoutMessage(validationTimeoutMillis));
				throw new OperationCanceledException(getValidationTimeoutMessage(validationTimeoutMillis));
			}
		} catch (final InterruptedException e) {
			throw new OperationCanceledException(getValidationTimeoutMessage(validationTimeoutMillis));
		}
	}

	/**
	 * Prepares the job to run in background if required
	 *
	 * @param job
	 *            the job to configure
	 */
	private static void configureBackOrForeground(final Job job) {
		final boolean runInBackground = PlatformUI.getPreferenceStore().getBoolean("RUN_IN_BACKGROUND");
		if (!runInBackground) {
			PlatformUI.getWorkbench().getProgressService().showInDialog(null, job);
		}
	}

	/**
	 * Formats the error message for the timeout message
	 *
	 * @param validationTimeoutMillis
	 *            the timeout in millis
	 * @return the formatted message
	 */
	private static String getValidationTimeoutMessage(final long validationTimeoutMillis) {
		final String validationTimeoutMessage = String.format(
				"Validation took longer than %ds. This could(!) mean that some of your patterns have no valid search plan. You may increase the timeout value using the eMoflon property page",
				validationTimeoutMillis / 1000);
		return validationTimeoutMessage;
	}

	/**
	 * Stops the thread running the given {@link Job}
	 *
	 * @param job
	 *            the job to stop
	 */
	@SuppressWarnings("deprecation")
	private void stopJob(final Job job) {
		job.getThread().stop();
	}

	/**
	 * Invokes {@link #validateProject(IProject, IProgressMonitor)} for each project
	 *
	 * @param projects
	 * @param monitor
	 * @throws CoreException
	 */
	private void validateProjects(final Collection<IProject> projects, final IProgressMonitor monitor)
			throws CoreException {
		final SubMonitor subMon = SubMonitor.convert(monitor, "Validating projects", projects.size());

		for (final IProject project : projects) {
			this.validateProject(project, subMon.split(1));
		}
	}

	/**
	 * Invokes {@link #validateFile(IFile, IProgressMonitor)} for each project that
	 * is a Moflon project
	 *
	 * @param project
	 * @param monitor
	 * @throws CoreException
	 */
	private void validateProject(final IProject project, final IProgressMonitor monitor) throws CoreException {
		if (CoreActivator.isMoflonProject(project)) {
			final IFile ecoreFile = MoflonConventions.getDefaultEcoreFile(project);
			validateFile(ecoreFile, monitor);
		}
	}

	/**
	 * This {@link Job} runs the configured task during its
	 * {@link #run(IProgressMonitor)} method
	 *
	 * @author Roland Kluge - Initial implementation
	 */
	private static final class ValidationTaskJob extends Job {
		private final ITask validationTask;

		private ValidationTaskJob(final ITask validationTask) {
			super(validationTask.getTaskName());
			this.validationTask = validationTask;
		}

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			return validationTask.run(monitor);
		}
	}
}
