package org.moflon.ide.ui.admin.handlers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.gervarro.eclipse.task.ITask;
import org.gervarro.eclipse.workspace.util.WorkspaceTaskJob;
import org.moflon.compiler.sdm.democles.eclipse.MonitoredSDMValidatorWithDumping;
import org.moflon.core.preferences.EMoflonPreferencesActivator;

/**
 * This handler triggers an SDM validation that additionally dumps intermediate
 * models
 *
 * @author Roland Kluge - Initial implementation
 *
 */
public class ValidateWithDumpingHandler extends AbstractValidateHandler {
	@Override
	protected void validateFile(final IFile ecoreFile, final IProgressMonitor monitor) throws CoreException {
		final SubMonitor subMon = SubMonitor.convert(monitor, "Validating " + ecoreFile.getName(), 1);

		final ITask validationTask = new MonitoredSDMValidatorWithDumping(ecoreFile,
				EMoflonPreferencesActivator.getDefault().getPreferencesStorage());

		if (validationTask != null) {
			removedOldDumpFiles(ecoreFile);

			runValidation(validationTask);
		}

		subMon.worked(1);
	}

	private void removedOldDumpFiles(final IFile ecoreFile) throws CoreException {
		final WorkspaceTaskJob taskJob = new WorkspaceTaskJob(new RemoveDumpedValidationFilesJob(ecoreFile));
		taskJob.runInWorkspace(new NullProgressMonitor());
	}
}
