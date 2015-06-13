/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * UniqueModelElement.java,v 1.5 2004/04/07 13:52:31 roehrijn Exp $ Created on
 * 07.10.2003
 */
package de.fhg.iao.matrixbrowser.model.elements;

import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.swt.util.uniqueidentifier.ID;
import de.fhg.iao.swt.util.uniqueidentifier.IDPool;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UniqueModelElement implements UniqueModelElementInterface {

	/**
	 * the unique identifier of the Element.
	 */
	private ID id;

	/**
	 * The Element's MBModel.
	 */
	private MBModel model = null;

	/**
	 * Null Constructor. This constructor builds a new
	 * <code>UniqueModelElement</code> and sets its ID to a generated one.
	 */
	public UniqueModelElement() {
		this.id = IDPool.getIDPool().getID();
	}

	/**
	 * Constructor specifying an ID for this <code>UniqueModelElement</code>.
	 * 
	 * @param id
	 *            the ID to assign to this new Element
	 */
	public UniqueModelElement(final ID id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueModelElementInterface#getID
	 * ()
	 */
	public final ID getID() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueModelElementInterface#setID
	 * (de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	public final void setID(final ID id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueModelElementInterface#setModel
	 * (de.fhg.iao.matrixbrowser.model.MBModel)
	 */
	public final void setModel(final MBModel model) {
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueModelElementInterface#getModel
	 * ()
	 */
	public final MBModel getModel() {
		return model;
	}
}
