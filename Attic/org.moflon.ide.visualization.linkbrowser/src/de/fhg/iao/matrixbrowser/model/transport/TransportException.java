/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * TransportException.java,v 1.2 2004/04/07 13:52:32 roehrijn Exp $ Created on
 * 11.11.2003
 */
package de.fhg.iao.matrixbrowser.model.transport;

/**
 * @author roehrich This Exception is thrown whenever an error occurs during a
 *         transformation process.
 */
public class TransportException extends Exception {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct an empty <code>TransportException</code>
	 */
	public TransportException() {
		super();
	}

	/**
	 * Constructs an exception parameterized by the exception message.
	 * 
	 * @param message
	 *            The message of the exception
	 */
	public TransportException(String message) {
		super(message);
	}

	/**
	 * Constructs an exception with a message and a nested exception which
	 * caused the problem.
	 * 
	 * @param message
	 *            The message of the exception <br>
	 * @param cause
	 *            The nested exception
	 */
	public TransportException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs an epmty exception but with a nested exceptio which caused the
	 * problem.
	 * 
	 * @param cause
	 *            The nested exception
	 */
	public TransportException(Throwable cause) {
		super(cause);
	}
}