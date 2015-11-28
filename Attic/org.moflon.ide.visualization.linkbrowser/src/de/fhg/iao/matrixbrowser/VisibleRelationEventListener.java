/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * VisibleRelationEventListener.java,v 1.2 2004/04/07 13:52:32 roehrijn Exp $
 * Created on 15.03.02
 */
/**
 * File :<br>
 * VisibleRelationEventListener.java <br>
 * Description :<br>
 * <br>
 * 
 * @author Christoph Kunz <br>
 * @version 1.0 <br>
 *          15.03.02
 */
package de.fhg.iao.matrixbrowser;

import java.util.EventListener;

/**
 * Interface for an event listener getting notified by
 * <code>VisibleRelationEvent</code>s.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.3 $
 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
 */
public interface VisibleRelationEventListener extends EventListener {
	/**
	 * Recieves a <code>VisibleRelationEvent</code> when a
	 * <code>VisibleRelation</code> was selected.
	 * 
	 * @param aEvent
	 *            the VisibleRelationEvent to process
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 */
	public void onRelationClicked(VisibleRelationEvent aEvent);

	/**
	 * Recieves a <code>VisibleRelationEvent</code> when a
	 * <code>VisibleRelation</code> was clicked.
	 * 
	 * @param aEvent
	 *            the VisibleRelationEvent to process
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 */
	public void onRelationSelected(VisibleRelationEvent aEvent);

	/**
	 * Recieves an <code>VisibleRelationEvent</code> when the mouse was over a
	 * <code>VisibleRelation</code>
	 * 
	 * @param aEvent
	 *            the <code>VisibleRelationEvent</code> to process
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 */
	public void onRelationOver(VisibleRelationEvent aEvent);

	/**
	 * Recieves an <code>VisibleRelationEvent</code> when a
	 * <code>VisibleRelation</code> was clicked in editing mode.
	 * 
	 * @param aEvent
	 *            the <code>VisibleRelationEvent</code> to process
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 */
	public void onRelationEdit(VisibleRelationEvent aEvent);
}