/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * VisibleNodeEvent.java,v 1.4 2004/04/07 13:52:32 roehrijn Exp $ Created on
 * 15.03.02
 */
package de.fhg.iao.matrixbrowser;

import java.util.EventObject;

import javax.swing.tree.TreePath;

/**
 * Event that occours if something interaction happened with a
 * <code>VisibleNode</code> in a <code>TreeView</code>.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.3 $
 */
public class VisibleNodeEvent extends EventObject {

	/**
	 * Comment for <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;

	/** Path to store. */
	private TreePath myPath = null;

	/** Node to store. */
	private VisibleNode myNode = null;

	/** A possible second Node to store. */
	private VisibleNode mySecondNode = null;

	/** The click count, how often the node was clicked. */
	private int myClickCount = 0;

	/**
	 * Constructor of a IAONodeEvent with a TreePath as parameter.
	 * 
	 * @param aSource
	 *            the source object, where this event occured
	 * @param aPath
	 *            a TreePath object, that was clicked in a TreeView
	 */
	public VisibleNodeEvent(final Object aSource, final TreePath aPath) {
		super(aSource);
		myPath = aPath;

		if (myPath != null) {
			myNode = (VisibleNode) aPath.getLastPathComponent();
		}
	}

	/**
	 * Constructor of a <code>IAONodeEvent</code> with a TreePath as parameter.
	 * 
	 * @param aSource
	 *            the source object, where this event occured
	 * @param aNode
	 *            a <code>VisibleNode</code> object, that was clicked in a
	 *            <code>TreeView</code>
	 * @see de.fhg.iao.matrixbrowser.VisibleNode
	 */
	public VisibleNodeEvent(final Object aSource, final VisibleNode aNode) {
		super(aSource);
		myNode = aNode;
	}

	/**
	 * Constructor of a <code>IAONodeEvent</code> with a two somehow related
	 * <code>VisibleNodes</code> as parameter <br>
	 * .
	 * 
	 * @param aSource
	 *            the source object, where this event occured
	 * @param aNode
	 *            a <code>VisibleNode</code> object, that was clicked in a
	 *            <code>TreeView</code>
	 * @param aSecondNode
	 *            a <code>VisibleNode</code> object, that is related to the
	 *            first node <br>
	 * @see de.fhg.iao.matrixbrowser.VisibleNode
	 */
	public VisibleNodeEvent(final Object aSource, final VisibleNode aNode,
			final VisibleNode aSecondNode) {
		super(aSource);
		myNode = aNode;
		mySecondNode = aSecondNode;
	}

	/**
	 * Getter method of the <code>TreePath</code> that was clicked.
	 * 
	 * @return a <code>TreePath</code> object that was clicked in a
	 *         <code>TreeView</code>
	 */
	public final TreePath getPath() {
		return myPath;
	}

	/**
	 * Getter method of the <code>VisibleNode</code> that was clicked.
	 * 
	 * @return a <code>VisibleNode</code> object, that was clicked in a
	 *         <code>TreeView</code>
	 * @see de.fhg.iao.matrixbrowser.VisibleNode
	 */
	public final VisibleNode getNode() {
		return myNode;
	}

	/**
	 * Getter method of the <code>VisibleNode</code> object, that is related to
	 * the first node.
	 * 
	 * @return a <code>VisibleNode</code> object, that is related to the first
	 *         node
	 * @see de.fhg.iao.matrixbrowser.VisibleNode
	 */
	public final VisibleNode getSecondNode() {
		return mySecondNode;
	}

	/**
	 * @return the number of mouse clicks which triggered this event.
	 */
	public final int getClickCount() {
		return myClickCount;
	}

	/**
	 * Sets the number of mouse clicks which triggered this event.
	 * 
	 * @param aClickCount
	 *            The number of mouse clicks to set
	 */
	public final void setClickCount(final int aClickCount) {
		myClickCount = aClickCount;
	}
}
