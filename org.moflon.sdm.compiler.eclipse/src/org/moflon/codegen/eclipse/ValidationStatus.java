package org.moflon.codegen.eclipse;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.sdm.compiler.democles.validation.result.ErrorMessage;
import org.moflon.sdm.compiler.democles.validation.result.Severity;

public class ValidationStatus implements IStatus {
	private final ErrorMessage errorMessage;
	private final int severity;

	private ValidationStatus(final ErrorMessage errorMessage, final int severity) {
		this.errorMessage = errorMessage;
		this.severity = severity;
	}

	public final ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	@Override
	public IStatus[] getChildren() {
		return new IStatus[0];
	}

	@Override
	public int getCode() {
		return 0;
	}

	@Override
	public Throwable getException() {
		return null;
	}

	@Override
	public String getMessage() {
		return errorMessage.getId();
	}

	@Override
	public String getPlugin() {
		return "org.moflon.compiler.sdm.democles";
	}

	@Override
	public int getSeverity() {
		return severity;
	}

	@Override
	public boolean isMultiStatus() {
		return false;
	}

	@Override
	public boolean isOK() {
		return severity == OK;
	}

	@Override
	public boolean matches(int severityMask) {
		return (severity & severityMask) != 0;
	}

	public String toString() {
		return getMessage();
	}

	public static final IStatus createValidationStatus(final ErrorMessage errorMessage) {
		try {
			return new ValidationStatus(errorMessage, convertToStatusSeverity(errorMessage.getSeverity()));
		} catch (CoreException e) {
			return e.getStatus();
		}
	}

	public static final int convertToStatusSeverity(final Severity severity) throws CoreException {
		int value = severity.getValue();
		if (value >= Severity.ERROR_VALUE) {
			return IStatus.ERROR;
		} else if (value >= Severity.WARNING_VALUE) {
			return IStatus.WARNING;
		} else if (value >= Severity.INFO_VALUE) {
			return IStatus.INFO;
		} else if (value == Severity.OK_VALUE) {
			return IStatus.OK;
		}
		IStatus invalidSeverityConversion = new Status(IStatus.ERROR,
				WorkspaceHelper.getPluginId(ValidationStatus.class),
				"Cannot convert " + severity.getLiteral() + " severity to a marker");
		throw new CoreException(invalidSeverityConversion);
	}
}
