package org.moflon.compiler.sdm.democles.eclipse;

/**
 * This exception class serves to 'label' exceptions that caused the validation
 * process to crash.
 */
public class DemoclesValidationException extends RuntimeException {
	private static final long serialVersionUID = 3213803509142282550L;

	public DemoclesValidationException(final Throwable cause) {
		super(cause);
	}
}
