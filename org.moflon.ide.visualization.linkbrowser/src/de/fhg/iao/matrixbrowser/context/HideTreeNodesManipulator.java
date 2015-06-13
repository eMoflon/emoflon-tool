/*
 * Created on 18.06.2004
 */
package de.fhg.iao.matrixbrowser.context;

import java.util.Enumeration;
import java.util.HashSet;

import javax.swing.tree.TreeNode;

import de.fhg.iao.matrixbrowser.EncapsulatingVisibleNode;
import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * @author schaal
 * */
public class HideTreeNodesManipulator extends AbstractTreeManipulator {

	/** see superclass implementation */
	private String myName = "Hide nodes with no relations";

	/** the TreeProxyManager in which this manipulator is situated */
	private TreeProxyManager verticalTreeProxyManager = null;

	/** the TreeProxyManager in which this manipulator is NOT situated */
	private TreeProxyManager horizontalTreeProxyManager = null;

	/** static to indicate if a HideTreeNodesManipulator is already in use */
	// private static boolean isUsed = false; //THIS is not used ;-)
	/** TreeProxyManager of this Manipulator */
	private TreeProxyManager myTreeProxyManager = null;

	/**
	 * constructor
	 * 
	 * @param aManager
	 *            the new TreeProxyManager
	 */
	public HideTreeNodesManipulator(TreeProxyManager aManager) {
		myTreeProxyManager = aManager;
		super.setName(myName);
	}

	/** see superclass implementation */
	public void manipulate() {
		if (getVisibleTree() == null)
			return;

		if (isEnabled()/* && !isUsed */) {
			// in the if-statement above.
			// isUsed = true; //schotti: Nope, not used. I mean the field....

			// set the right treeproxymanagers to vertical and horizontal
			// treeproxymanager
			// this is needed for finding empty nodes
			if (getTreeProxyManager().getTreeManager().getTreeCombinator() != null) {
				if (getTreeProxyManager().equals(
						getTreeProxyManager().getTreeManager()
								.getTreeCombinator()
								.getVerticalTreeSynthesizer())) {
					verticalTreeProxyManager = getTreeProxyManager();
					horizontalTreeProxyManager = (TreeProxyManager) getTreeProxyManager()
							.getTreeManager().getTreeCombinator()
							.getHorizontalTreeSynthesizer();
				} else {
					horizontalTreeProxyManager = getTreeProxyManager();
					verticalTreeProxyManager = (TreeProxyManager) getTreeProxyManager()
							.getTreeManager().getTreeCombinator()
							.getVerticalTreeSynthesizer();
				}
				setVisibleTree(hide(getVisibleTree()));

			}// else
			// System.out
			// .println("HideTreeNodesManipulator no TreeCombinator");
			// isUsed = false; //The variable is not in use
		}
	}

	/**
	 * helper class that traverses the tree and gets rid of nodes that do not
	 * have a relation
	 */
	protected VisibleNode hide(VisibleNode aTree) {
		VisibleNode result = null;
		Enumeration<VisibleNode> verticalEnumeration = null;
		Enumeration<VisibleNode> horizontalEnumeration = null;
		VisibleNode verticalNode = null;
		VisibleNode horizontalNode = null;
		HashSet<ID> verticalSet = new HashSet<ID>();
		HashSet<ID> horizontalSet = new HashSet<ID>();
		// set the right enumerations
		if (getTreeProxyManager().equals(getVerticalTreeProxyManager())) {
			verticalEnumeration = aTree.breadthFirstEnumeration();
			horizontalEnumeration = getHorizontalTreeProxyManager()
					.getVisibleTree().breadthFirstEnumeration();
		} else {
			verticalEnumeration = getVerticalTreeProxyManager()
					.getVisibleTree().breadthFirstEnumeration();
			horizontalEnumeration = aTree.breadthFirstEnumeration();
		}

		while (verticalEnumeration.hasMoreElements()) {
			verticalNode = (VisibleNode) verticalEnumeration.nextElement();
			horizontalEnumeration = getHorizontalTreeProxyManager()
					.getVisibleTree().breadthFirstEnumeration();
			while (horizontalEnumeration.hasMoreElements()) {
				horizontalNode = (VisibleNode) horizontalEnumeration
						.nextElement();

				// we found a relation, so add the whole path
				// of the two nodes to the different sets
				if ((getTreeProxyManager().getTreeManager().getTreeCombinator()
						.getRelation(verticalNode, horizontalNode) != null)) {

					TreeNode tempNode[] = verticalNode.getPath();
					for (int t = 0; t < tempNode.length; t++) {
						if (tempNode[t] instanceof EncapsulatingVisibleNode) {
							verticalSet
									.add(((EncapsulatingVisibleNode) tempNode[t])
											.getNestedNode().getID());
						}
					}
					tempNode = horizontalNode.getPath();
					for (int t = 0; t < tempNode.length; t++) {
						if (tempNode[t] instanceof EncapsulatingVisibleNode) {
							horizontalSet
									.add(((EncapsulatingVisibleNode) tempNode[t])
											.getNestedNode().getID());
						}
					}
				} // end if
			} // end while
		} // end while

		if (getTreeProxyManager().equals(getVerticalTreeProxyManager())) {
			result = buildTree(getVerticalTreeProxyManager(), verticalSet);
		} else {
			result = buildTree(getHorizontalTreeProxyManager(), horizontalSet);
		}

		return result;
	}

	/**
	 * @return Returns the TreeProxyManager.
	 */
	protected TreeProxyManager getTreeProxyManager() {
		return myTreeProxyManager;
	}

	/**
	 * @param aTreeProxyManager
	 *            The TreeProxyManager to set.
	 */
	protected void setTreeProxyManager(TreeProxyManager aTreeProxyManager) {
		this.myTreeProxyManager = aTreeProxyManager;
	}

	/**
	 * @return Returns the horizontalTreeProxyManager.
	 */
	protected TreeProxyManager getHorizontalTreeProxyManager() {
		return horizontalTreeProxyManager;
	}

	/**
	 * @param aHorizontalTreeProxyManager
	 *            The horizontalTreeProxyManager to set.
	 */
	protected void setHorizontalTreeProxyManager(
			TreeProxyManager aHorizontalTreeProxyManager) {
		this.horizontalTreeProxyManager = aHorizontalTreeProxyManager;
	}

	/**
	 * @return Returns the verticalTreeProxyManager.
	 */
	protected TreeProxyManager getVerticalTreeProxyManager() {
		return verticalTreeProxyManager;
	}

	/**
	 * @param aVerticalTreeProxyManager
	 *            The verticalTreeProxyManager to set.
	 */
	protected void setVerticalTreeProxyManager(
			TreeProxyManager aVerticalTreeProxyManager) {
		this.verticalTreeProxyManager = aVerticalTreeProxyManager;
	}
}