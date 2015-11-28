/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 *     $Id: UniqueModelElementInterface.java,v 1.2 2009-03-11 12:31:38 srose Exp $
 * 
 * Created on Aug 8, 2008
 */
package de.fhg.iao.matrixbrowser.model.elements;

import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

public interface UniqueModelElementInterface {

	/**
	 * gets the Identificator of this <code>UniqueModelElement</code>.
	 * 
	 * @return the element's ID
	 */
	public abstract ID getID();

	/**
	 * Sets the Identificator of this <code>UniqueModelElement</code> to the
	 * specified one.
	 * 
	 * @param id
	 *            the ID to assign to this <code>UniqueModelElement</code>
	 */
	public abstract void setID(final ID id);

	/**
	 * Sets the model of this element.
	 * 
	 * @param model
	 *            the model to assign
	 */
	public abstract void setModel(final MBModel model);

	/**
	 * Gets the model of this element.
	 * 
	 * @return the element's model
	 */
	public abstract MBModel getModel();

}