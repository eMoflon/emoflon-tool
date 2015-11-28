/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * VisibleNodeEventListener.java,v 1.2 2004/04/07 13:52:32 roehrijn Exp $
 * Created on 15.03.02
 */
package de.fhg.iao.matrixbrowser;

import java.util.EventListener;

/**
 * Interface for a <code>VisibleNodeEventListener</code>, which is notified if
 * something happend with a <code>VisibleNode</code>. Eg it was selected, had a
 * mouse over or was edited.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.3 $
 */
public interface VisibleNodeEventListener extends EventListener {

	/**
	 * Recieves an <code>VisibleNodeEvent</code> when a Node was clicked. <br>
	 * 
	 * @param e
	 *            the <code>VisibleNodeEvent</code>
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 *      onNodeSelected/clicked
	 */
	public void onNodeClicked(VisibleNodeEvent e);

	/**
	 * Recieves an <code>VisibleNodeEvent</code> when the cursor is over a Node. <br>
	 * 
	 * @param e
	 *            the <code>VisibleNodeEvent</code>
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	public void onNodeOver(VisibleNodeEvent e);

	/**
	 * Recieves an <code>VisibleNodeEvent</code> when a node is clicked and
	 * wants to be edited.
	 * 
	 * @param e
	 *            the <code>VisibleNodeEvent</code>
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	public void onNodeEdit(VisibleNodeEvent e);

	/**
	 * Receives a <code>VisibleNodeEvent</code> when the user has clicked on a
	 * Node's PopupMenu.
	 * 
	 * @param e
	 */
	void onNodeMenu(VisibleNodeEvent e);
}