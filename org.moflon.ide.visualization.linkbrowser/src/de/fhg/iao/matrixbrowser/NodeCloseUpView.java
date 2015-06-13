/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * NodeCloseUpView.java,v 1.2 2004/04/07 13:52:32 roehrijn Exp $ Created on
 * 15.03.02
 */
package de.fhg.iao.matrixbrowser;

/**
 * Interface for a "close up view", which should visualize all direct
 * neighnoring nodes of the in the <code>TreeView</code> s currently selected
 * one.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.3 $
 */
public interface NodeCloseUpView {

	/**
	 * Adds a <code>VisibleNodeEventListener</code>, that will be notified if
	 * someone clicks on a node in the <code>NodeCloseUpView<\code>s
	 * 
	 * @param aVisibleNodeEventListener
	 *            the <code>VisibleNodeEventListener</code> to be notified of
	 *            <code>NodeEvent</code>s
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	public void addVisibleNodeEventListener(
			VisibleNodeEventListener aVisibleNodeEventListener);

	/**
	 * Removes a <code>VisibleNodeEventListener</code>.
	 * 
	 * @param aVisibleNodeEventListener
	 *            the <code>VisibleNodeEventListener</code> to be removed
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	public void removeVisibleNodeEventListener(
			VisibleNodeEventListener aVisibleNodeEventListener);

	/**
	 * Sets the node to show details, which was clicked in a
	 * <code>TreeView</code> of the matrix.
	 * 
	 * @param aNode
	 *            the clicked node in an <code>TreeView</code>
	 * @see de.fhg.iao.matrixbrowser.VisibleNode
	 */
	public void setNode(VisibleNode aNode);

	/**
	 * Returns the currently shown node. This is a helper function for a
	 * proof-of-concept that will disappear as soon as I don't need it any more.
	 * This is why it is marked deprecated from the time it appeared on.
	 * 
	 * @deprecated should not be used, just for proof of concept, will
	 *             disappear!!!
	 * @return the last selected node.
	 */
	public VisibleNode getNode();

}
