/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * AbstractDirector.java,v 1.2 2004/04/07 13:52:33 roehrijn Exp $ Created on
 * 11.11.2003
 */
package de.fhg.iao.matrixbrowser.model.transport;

/**
 * Import and export in the MatrixBrowser complies to the Builder design
 * pattern. The <code>AbstractDirector</code> class is superclass of all
 * Directors regarding import and export.
 * 
 * @author roehrich
 */
public abstract class AbstractDirector {

	/**
	 * the Builder which will be used in the transport process.
	 */
	Builder myBuilder = null;

	/**
	 * @param <br>
	 * @author Jan Roehrich <jan.roehrich@iao.fhg.de><br>
	 * @version 1.0 <br>
	 */
	public AbstractDirector(Builder builder) {
		this.setBuilder(builder);
	}

	/**
	 * retrieves the Builder used in this build process.
	 */
	public Builder getBuilder() {
		return myBuilder;
	}

	/**
	 * Sets the Builder used in this build process.
	 */
	public void setBuilder(Builder builder) {
		myBuilder = builder;
	}

	public abstract void startTransport() throws TransportException;
}