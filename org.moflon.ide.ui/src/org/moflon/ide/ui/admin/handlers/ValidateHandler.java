package org.moflon.ide.ui.admin.handlers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.gervarro.eclipse.task.ITask;
import org.moflon.compiler.sdm.democles.eclipse.MonitoredSDMValidator;
import org.moflon.core.preferences.EMoflonPreferencesActivator;

/**
 * This handler triggers an SDM validation
 *
 * @author Gergely Varró
 */
public class ValidateHandler extends AbstractValidateHandler {

	@Override
	protected void validateFile(final IFile ecoreFile, final IProgressMonitor monitor) {

		final SubMonitor subMon = SubMonitor.convert(monitor, "Validating " + ecoreFile.getName(), 1);

		final ITask validationTask = new MonitoredSDMValidator(ecoreFile,
				EMoflonPreferencesActivator.getDefault().getPreferencesStorage());
		if (validationTask != null) {
			runValidation(validationTask);
		}

		subMon.worked(1);
	}
}
