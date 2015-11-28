/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 *     $Id: Eclipse\040Code\040Comment\040Templates.xml,v 1.1 2005/04/11 
 *      16:36:50 koenigs Exp $
 * 
 * Created on Aug 7, 2008
 */
package de.fhg.iao.matrixbrowser;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import de.fhg.iao.matrixbrowser.context.FlatteningTreeIterator;
import de.fhg.iao.matrixbrowser.model.ICommand;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * An EncapsulatingVisibleNode is a VisibleNode encapsulating a node in the
 * backend Model. The node defines the displaying Type.
 * 
 * @author <a href=mailto:jan.roehrich@iao.fhg.de>Jan Roehrich</a>
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.7 $
 */
public class EncapsulatingVisibleNode extends DefaultMutableTreeNode implements
		VisibleNode {
	/** <code>serialVersionUID</code>. */
	private static final long serialVersionUID = 1L;
	/**
	 * The <code>VisibleNode</code>'s context Menu. The
	 * <code>nodeContextMenu</code> is the Menu popping up when right-clicking
	 * on this node.
	 * 
	 * @see #getContextMenu()
	 * @see Node#getContextMenu()
	 * @see Node#setContextMenu(Collection)
	 */
	private Collection<ICommand> nodeContextMenu = null;
	/**
	 * The Background Color of this node. <code>nodeBGColor</code>
	 */
	private Color color = null;
	/** Holds the <code>id</code> for this node. */
	private ID id = null;

	/**
	 * Constructor for a VisibleNode containing a UserObject. Whether the node
	 * allows children or not may be set by the boolean.
	 * 
	 * @param userObject
	 *            the UserObject
	 * @param allowsChildren
	 *            the boolean defining whether this node allows children.
	 */
	public EncapsulatingVisibleNode(final Object userObject,
			final boolean allowsChildren) {
		super(userObject, allowsChildren);
	}

	public EncapsulatingVisibleNode clone() {
		return (EncapsulatingVisibleNode) super.clone();
	}

	// @SuppressWarnings("unchecked")
	/*
	 * public <T extends DefaultMutableTreeNode> T clone() { T clone = (T)
	 * super.clone(); return clone; }
	 */
	/**
	 * Constructor for a VisibleNode containing a UserObject.
	 * 
	 * @param userObject
	 *            the UserObject
	 */
	public EncapsulatingVisibleNode(final Object userObject) {
		super(userObject);
	}

	/**
	 * Constructs a visible Node encapsulating the given node and a UserObject.
	 * This Constructor also defines whether it is allowed to add children to
	 * this node.
	 * 
	 * @param node
	 *            the encapsulated node
	 * @param userObject
	 *            the userObject to add to this node.
	 * @param allowsChildren
	 *            the boolean specifying whether child nodes may be added to
	 *            this node.
	 * @author <a href=mailto:jan.roehrich@iao.fhg.de>Jan Roehrich</a>
	 * @version 1.0 <br>
	 */
	public EncapsulatingVisibleNode(final Node node, final Object userObject,
			final boolean allowsChildren) {
		super(userObject, allowsChildren);
		this.setNode(node);
		this.id = node.getID();
		this.nodeContextMenu = node.getContextMenu();
		setNodeBGColor(node.getColor());
	}

	/**
	 * Constructor for a VisibleNode encapsulating a node and a userObject. The
	 * VisibleNode constructed will reference the node given and calls the
	 * constructor of its superclass implementation
	 * {@link DefaultMutableTreeNode DefaultMutableTreeNode} to set the the
	 * reference to the user-specified Object.
	 * 
	 * @param node
	 *            the node referenced by this VisibleNode
	 * @param userObject
	 *            an Object provided by the user that constitutes the node's
	 *            data
	 * @author <a href=mailto:jan.roehrich@iao.fhg.de>Jan Roehrich</a>
	 * @version 1.0
	 */
	public EncapsulatingVisibleNode(final Node node, final Object userObject) {
		super(userObject);
		this.setNode(node);
		this.id = node.getID();
		this.nodeContextMenu = node.getContextMenu();
		setNodeBGColor(node.getColor());
	}

	/**
	 * Constructs an empty VisibleNode.
	 */
	public EncapsulatingVisibleNode() {
	}

	/**
	 * Constructs a Visible Node from the given Node.
	 * 
	 * @param node
	 *            the corresponding <code>Node</code> object in the backend
	 * @author <a href=mailto:jan.roehrich@iao.fhg.de>Jan Roehrich</a>
	 * @version 1.0 <br>
	 */
	public EncapsulatingVisibleNode(final Node node) {
		super();
		this.setNode(node);
		if (node != null) {
			if (node.getContextMenu() != null && nodeContextMenu != null) {
				this.nodeContextMenu.addAll(node.getContextMenu());
			} else {
				this.nodeContextMenu = node.getContextMenu();
			}
			this.id = node.getID();
			setNodeBGColor(node.getColor());
		}
	}

	/** Reference to the corresponding node in the model. */
	private Node myCorrespondingNode = null;

	/**
	 * Gets the corresponding <code>Node</code> to this <code>VisibleNode</code>
	 * . <code>VisibleNode</code>s represent a specific Node of the underlying
	 * model. The corresponding Node encapsulated in this VisibleNode is
	 * returned by this method.
	 * 
	 * @return the encapsulated Node
	 */
	public final Node getNestedNode() {
		return this.myCorrespondingNode;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.swing.tree.DefaultMutableTreeNode#toString()
	 */
	public final String toString() {
		return this.getNestedNode().toString();
	}

	/**
	 * Sets this VisibleNode's corresponding (encapsulated) node to the one
	 * specified.
	 * 
	 * @param node
	 *            the Node this VisibleNode encapsulates.
	 */
	public final void setNode(final Node node) {
		this.myCorrespondingNode = node;
	}

	/**
	 * Gets this Node's Context Menu as a Collection of <code>ICommand</code>s.
	 * 
	 * @return the Context Menu as a collection of <code>ICommand</code>s
	 */
	public final Collection<ICommand> getContextMenu() {
		if (nodeContextMenu == null) {
			nodeContextMenu = myCorrespondingNode.getContextMenu();
		}
		return nodeContextMenu;
	}

	/**
	 * traverses the children of this EncapsulatingVisibleNode until it finds
	 * the node with ID parentID. Then the new Node node is added to the
	 * VisibleNode with ID parentID.
	 * 
	 * @param node
	 *            the node to add
	 * @param parentID
	 *            the ID of the node's parent
	 */
	public final void add(final VisibleNode node, final ID parentID) {
		Iterator<VisibleNode> treeIterator = flatteningTreeIterator();
		while (treeIterator.hasNext()) {
			VisibleNode tempNode = treeIterator.next();
			// found node with matching ID
			if (tempNode instanceof VisibleNode) {
				if (((VisibleNode) tempNode).getNestedNode().getID().equals(
						parentID)) {
					// add child, i.e. removes node from its parent and adds
					// it to tempNode (also sets new parent)
					tempNode.add((DefaultMutableTreeNode) node);
				}
			}
		}
	}

	/**
	 * Gets the BackgroundColor of this node.
	 * 
	 * @return this node's BackgroundColor.
	 */
	public final Color getNodeBGColor() {
		return color;
	}

	/**
	 * Gets the children of this node.
	 * 
	 * @return this node's children.
	 */
	@SuppressWarnings("unchecked")
	public final Vector<VisibleNode> getChildren() {
		return this.children;
	}

	/**
	 * Adds a Vector of TreeNodes as children to this node.
	 * 
	 * @param aChildVector
	 *            the child nodes to add
	 */
	public final void setChildren(final Vector<VisibleNode> aChildVector) {
		children = new Vector<VisibleNode>(aChildVector);
	}

	/**
	 * Gets the tree depth in the tree of this Visible Node.
	 * 
	 * @return the node's tree depth
	 */
	public final int getTreeDepth() {
		TreeNode currentNode = this;
		int i = 0;
		while ((currentNode = currentNode.getParent()) != null) {
			i++;
		}
		return i;
	}

	/**
	 * Gets a FlatteningTreeIterator from this node iterating over all the nodes
	 * in the tree rooted at this node.
	 * 
	 * @return the iterator iterating over the tree rooted at this node.
	 */
	public final Iterator<VisibleNode> flatteningTreeIterator() {
		return new FlatteningTreeIterator<VisibleNode>(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.VisibleNode#setNodeContextMenu
	 *      (java.util.Collection)
	 */
	public final void setNodeContextMenu(
			final Collection<ICommand> nodeContextMenu) {
		this.nodeContextMenu = nodeContextMenu;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.VisibleNode#setNodeBGColor(java.awt.Color)
	 */
	public final void setNodeBGColor(final Color nodeBGColor) {
		this.color = nodeBGColor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.VisibleNode#getID()
	 */
	public final ID getID() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.VisibleNode#setID
	 *      (de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	public final void setID(final ID id) {
		this.id = id;
	}
}
