/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * VisibleRelationEvent.java,v 1.2 2004/04/07 13:52:32 roehrijn Exp $ Created on
 * 15.03.02
 */
package de.fhg.iao.matrixbrowser;

import java.util.EventObject;

/**
 * Event that occours if something happened with a <code>VisibleRelation</code>.
 * Eg. a <code>VisibleRelation</code> was clicked, mouse overed or edited.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.2 $
 */
public class VisibleRelationEvent extends EventObject {

	/**
	 * Holds the <code>VisibleRelation</code>, which is the subject of the
	 * event.
	 */
	private VisibleRelation myVisibleRelation[] = null;

	/**
	 * Constructor of a <code>VisibleRelationEvent</code>.
	 * 
	 * @param aSource
	 *            the source object, where this event occured
	 * @param aVisibleRelation
	 *            a VisibleRelation object
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	public VisibleRelationEvent(Object aSource, VisibleRelation aVisibleRelation) {
		super(aSource);
		myVisibleRelation = new VisibleRelation[1];
		myVisibleRelation[0] = aVisibleRelation;
	}

	public VisibleRelationEvent(Object aSource,
			VisibleRelation aVisibleRelation[]) {
		super(aSource);
		myVisibleRelation = new VisibleRelation[aVisibleRelation.length];
		myVisibleRelation = aVisibleRelation;
	}

	/**
	 * @return a <code>VisibleRelation</code> object, which is subject of this
	 *         event
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	public VisibleRelation getFirstVisibleRelation() {
		return myVisibleRelation[0];
	}

	/**
	 * @return a <code>VisibleRelation</code> object, which is subject of this
	 *         event
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	public VisibleRelation[] getVisibleRelations() {
		return myVisibleRelation;
	}

	/**
	 * @return a <code>VisibleRelation</code> object, which is subject of this
	 *         event
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	public VisibleRelation getVisibleRelation(int i) {
		if (i >= 0 && i < myVisibleRelation.length)
			return myVisibleRelation[i];
		else
			return null;
	}
}