/*
 * Created on 09.03.2004 To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.fhg.iao.matrixbrowser.context;

import java.util.Iterator;

import de.fhg.iao.matrixbrowser.VisibleNode;

/**
 * Depth-First Iterator iterating over the given Tree.
 * 
 * @author roehrich
 * @author <a href=mailto:cs@christian-schott.de>Christian Schott</a>
 */
public class FlatteningTreeIterator<E extends VisibleNode> implements
		Iterator<E> {

	E myCurrentNode;

	E myStartNode;

	public FlatteningTreeIterator(E startNode) {
		myCurrentNode = startNode;
		myStartNode = startNode;
	}

	public boolean hasNext() {
		return myCurrentNode != null;
	}

	/**
	 * Gets the next Element in from the Iterator. This method returns an
	 * Element of some subclass of DefaultMutableTreeNode (the one that this
	 * FaltteningTreeIterator holds). If the next Element's class doesn't
	 * correspond to the awaited return type, the method skips this Element.
	 * 
	 * @see java.util.Iterator#next()
	 * @return the next Element the Iterator finds, skipping elements not
	 *         conforming to the type the Iterator has been initialized to.
	 */
	@SuppressWarnings("unchecked")
	// Assume children are of the correct class as parents
	public E next() {
		E currentNode = myCurrentNode;
		try {
			if (myCurrentNode.getChildCount() > 0) {
				// if node has children, take the first one
				myCurrentNode = (E) myCurrentNode.getChildAt(0);
			} else if (myCurrentNode == myStartNode) {
				myCurrentNode = null;
			} else {
				// if node has no children, take its first right neighbor
				// therefore check if there is a direct neighbor
				myCurrentNode = (E) myCurrentNode.getNextSibling();

				if (myCurrentNode == null) {
					// there were no more direct neighbors -> we must go
					// upstairs
					VisibleNode tmp = currentNode;

					do {
						tmp = (VisibleNode) tmp.getParent();

						if ((tmp == myStartNode) || (tmp == null)) {
							break;
						}

						myCurrentNode = (E) tmp.getNextSibling();
					} while ((myCurrentNode == null) && (tmp != myStartNode));
				}
			}
			return currentNode;
		} catch (ClassCastException e) {
			System.err
					.println("The child node of node \""
							+ currentNode.toString()
							+ "\" is not of the same class as itself. The chosen FlatteningTreeIterator cannot handle this. The child will be skipped.");
			return next();
		}
	}

	public void remove() {
	}
}