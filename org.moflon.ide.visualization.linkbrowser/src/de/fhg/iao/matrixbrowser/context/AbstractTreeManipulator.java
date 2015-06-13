/*
 * Created on 16.06.2004
 */
package de.fhg.iao.matrixbrowser.context;

import java.util.Enumeration;
import java.util.HashSet;

import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * The tree Manipulators implement each an action to perform on a tree. (like
 * moving nodes)
 * 
 * 
 * @author schaal
 */
public abstract class AbstractTreeManipulator {

	/**
	 * The name of this TreeManipulator
	 */
	private String name;

	/** flag to indicate whether AbstractTreeManipulator is enabled or not */
	private boolean myEnabled = true;

	/** the visible tree of this AbstractTreeManipulator */
	private VisibleNode myVisibleTree = null;

	/**
	 * sets the enabled status
	 * 
	 * @param enabled
	 */
	public void setEnabled(final boolean enabled) {
		myEnabled = enabled;
	}

	/**
	 * gets the enabled status
	 * 
	 * @return myEnabled
	 */
	public boolean isEnabled() {
		return myEnabled;
	}

	/**
	 * returns the visible tree of this AbstractTreeManipulator
	 * 
	 * @return myVisibleTree
	 */
	public VisibleNode getVisibleTree() {
		return myVisibleTree;
	}

	/**
	 * sets the visible tree of this AbstractTreeManipulator
	 * 
	 * @param visibleTree
	 *            the new visible tree that is set
	 */
	public void setVisibleTree(final VisibleNode visibleTree) {
		myVisibleTree = visibleTree;
	}

	/**
	 * returns the name of this AbstractTreeManipulator
	 * 
	 * @return myName
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this <code>TreeManipulator</code>. Should be called by
	 * implementations to specify the name of the instance.
	 * 
	 * @param name
	 *            the name of this TreeManipulator instance.
	 */
	protected void setName(final String name) {
		this.name = name;
	}

	/**
	 * manipulates the visible tree of this AbstractTreeManipulator
	 */
	public abstract void manipulate();

	/**
	 * builds a new tree using the TreeNodesSynthesizerProxy myProxy and the
	 * HashSet nodeIDSet containing the nodes id's.
	 * 
	 * @param newTree
	 * @param aProxy
	 * @param aNodeIDSet
	 */
	protected VisibleNode buildTree(final AbstractTreeSynthesizer aProxy,
			final HashSet<ID> aNodeIDSet) {
		VisibleNode newTree = null;

		// enumeration over myProxy's visible tree
		// this has to be a breadthFirstEnumeration since
		// all parent nodes have to be built before their children
		Enumeration<VisibleNode> myEnumeration = aProxy.getVisibleTree()
				.breadthFirstEnumeration();
		VisibleNode tempNode = null;
		// loop over all enumeration elements
		while (myEnumeration.hasMoreElements()) {
			VisibleNode enumerationNode = myEnumeration.nextElement();
			// is verticalNode the proxy's tempNode?
			// yes
			if (enumerationNode.toString().equals("tmp")) {
				// this is the first node in newTree, so don't add it,
				// just set it
				newTree = enumerationNode.clone();
				newTree.getNestedNode().setID(
						enumerationNode.getNestedNode().getID());
			} else {
				// check if the correct id was found
				if (aNodeIDSet
						.contains(enumerationNode.getNestedNode().getID())) {
					// clone the tempnode
					tempNode = enumerationNode.clone();
					// and set it's new id
					tempNode.getNestedNode().setID(
							enumerationNode.getNestedNode().getID());
					// traverse newTree until the
					// supplied id is found, then add tempNode
					newTree.add(tempNode, ((VisibleNode) enumerationNode
							.getParent()).getNestedNode().getID());
				} 
			}
		}
		return newTree;
	}
}