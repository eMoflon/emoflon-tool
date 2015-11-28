/*
 * Created on 02.07.2004
 */
package de.fhg.iao.matrixbrowser.context;

import java.util.HashSet;

import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * This class follows the builder paradigm, it builds a custom tree manipulator,
 * making nodes draggable.
 * 
 * @author schaal
 * @author <a href=mailto:cs@christian-schott.de>Christian Schott</a>
 */
public class BuildCustomTreeManipulator extends HideTreeNodesManipulator {

	private HashSet<ID> myHashSet = new HashSet<ID>();

	/**
	 * constructor
	 * 
	 * @param aManager
	 *            the new TreeProxyManager
	 */
	public BuildCustomTreeManipulator(TreeProxyManager aManager) {
		super(aManager);
		super.setName("NodeDragSelectionSupport");
	}

	public void manipulate() {
		if (getVisibleTree() == null)
			return; // same

		if (isEnabled() && getHashSet() != null && getHashSet().size() != 0) {
			// set the right treeproxymanagers to vertical and horizontal
			// treeproxymanager
			// this is needed for finding empty nodes
			if (getTreeProxyManager().getTreeManager().getTreeCombinator() != null) {
				if (getTreeProxyManager().equals(
						getTreeProxyManager().getTreeManager()
								.getTreeCombinator()
								.getVerticalTreeSynthesizer())) {
					setVerticalTreeProxyManager(getTreeProxyManager());
					setHorizontalTreeProxyManager((TreeProxyManager) getTreeProxyManager()
							.getTreeManager().getTreeCombinator()
							.getHorizontalTreeSynthesizer());
				} else {
					setHorizontalTreeProxyManager(getTreeProxyManager());
					setVerticalTreeProxyManager((TreeProxyManager) getTreeProxyManager()
							.getTreeManager().getTreeCombinator()
							.getVerticalTreeSynthesizer());
				}
			}
			if (buildTree(getTreeProxyManager(), getHashSet()) != null)
				setVisibleTree(buildTree(getTreeProxyManager(), getHashSet()));
		}
	}

	/**
	 * @return Returns the myHashSet.
	 */
	public HashSet<ID> getHashSet() {
		return myHashSet;
	}

	/**
	 * @param myHashSet
	 *            The myHashSet to set.
	 */
	public void setHashSet(HashSet<ID> myHashSet) {
		this.myHashSet = myHashSet;
	}
}