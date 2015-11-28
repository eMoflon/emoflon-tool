/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * VisibleNode.java,v 1.6 2004/05/19 14:41:52 kookaburra Exp $ Created on
 * 14.10.2003
 */
package de.fhg.iao.matrixbrowser;

import java.awt.Color;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import de.fhg.iao.matrixbrowser.model.ICommand;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * The representation of a Node as shown in the MatrixBrowser.
 * 
 * @author roehrich
 * @author <a href=mailto:cs@christian-schott.de>Christian Schott</a>
 */
public interface VisibleNode extends MutableTreeNode, Cloneable {
	/**
	 * Gets this Node's Context Menu as a Collection of <code>ICommand</code>s.
	 * 
	 * @return the Context Menu as a collection of <code>ICommand</code>s
	 */
	Collection<ICommand> getContextMenu();

	/**
	 * Gets the BackgroundColor of this node.
	 * 
	 * @return this node's BackgroundColor.
	 */
	Color getNodeBGColor();

	/**
	 * Gets the children of this node.
	 * 
	 * @return this node's children.
	 */
	Vector<VisibleNode> getChildren();

	/**
	 * Adds a Vector of TreeNodes as children to this node.
	 * 
	 * @param aChildVector
	 *            the child nodes to add
	 */
	void setChildren(final Vector<VisibleNode> aChildVector);

	/**
	 * Gets the tree depth in the tree of this Visible Node.
	 * 
	 * @return the node's tree depth
	 */
	int getTreeDepth();

	/**
	 * Gets a FlatteningTreeIterator from this node iterating over all the nodes
	 * in the tree rooted at this node.
	 * 
	 * @return the iterator iterating over the tree rooted at this node.
	 */
	Iterator<VisibleNode> flatteningTreeIterator();

	/**
	 * Sets the ContextMenu of this node to the given ContextMenu.
	 * 
	 * @param nodeContextMenu
	 *            a Collection of ICommands holding the Menu Entries.
	 */
	void setNodeContextMenu(Collection<ICommand> nodeContextMenu);

	/**
	 * Sets the Node BackgroundColor to the specified Value.
	 * 
	 * @param nodeBGColor
	 *            the color of this VisibleNode
	 */
	void setNodeBGColor(Color nodeBGColor);

	/**
	 * Gets the Node which is encapsulated in this Node.
	 * 
	 * @return the Node which has been used to create this node.
	 */
	Node getNestedNode();

	/**
	 * Gets the ID of this VisibleNode.
	 * 
	 * @return the Visible Node's ID
	 */
	ID getID();

	/**
	 * Sets the ID of this VisibleNode.
	 * 
	 * @param id
	 *            The ID to set
	 */
	void setID(ID id);

	/**
	 * Adds the given VisibleNode to this Node.
	 * 
	 * @param node
	 *            the VisibleNode to add.
	 */
	void add(final MutableTreeNode node);

	/**
	 *@see DefaultMutableTreeNode#getNextSibling()
	 * @return the next Sibling of this VisibleNode
	 */
	DefaultMutableTreeNode getNextSibling();

	/**
	 * {@inheritDoc} Gets the <code>TreePath</code> to this Node.
	 * 
	 * @return the <code>VisibleNode</code>'s <code>TreePath</code>
	 */
	TreeNode[] getPath();

	/**
	 * traverses the children of this VisibleNode until it finds the node with
	 * ID parentID. Then the new Node node is added to the VisibleNode with ID
	 * parentID.
	 * 
	 * @param tempNode
	 *            the node to add
	 * @param id
	 *            the ID of the node's parent
	 */
	void add(VisibleNode tempNode, ID id);

	/**
	 * A Breadth first enumeration of the <code>VisibleNodes</code> being the
	 * children of this node.
	 * 
	 * @param <T>
	 *            a <code>VisibleNode</code> or an extension of it
	 * @return an Enumeration of <code>VisibleNode</code>s or extensions of it
	 *         representing the children of this <code>VisibleNode</code>
	 *         Implementation.
	 */
	Enumeration<VisibleNode> breadthFirstEnumeration();

	/**
	 * Clone method for this <code>VisibleNode</code>.
	 * 
	 * @param <T>
	 *            The Type of this <code>VisibleNode</code>
	 * @return a shallow copy of this <code>VisibleNode</code>
	 */
	VisibleNode clone();
	// <T extends DefaultMutableTreeNode> T clone();
}
