/*
 * Created on 12.01.2004 To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.fhg.iao.matrixbrowser.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationBoundary;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TreeCombinator {

	private AbstractTreeSynthesizer myVerticalTreeSynthesizer = null;

	private AbstractTreeSynthesizer myHorizontalTreeSynthesizer = null;
	
	public static int VERTICAL_COLLAPSED = 1;
	public static int HORIZONTAL_COLLAPSED = 2;
	public static int BOTH_COLLAPSED = 3;

	/**
	 * The matrix where the relations between the nodes are stored.
	 */
	private MBModel myModel = null;

	/**
	 * flag to indicate wether original tree data is used or changed treedata is
	 * used which is saved in the treesynthesizers.
	 */
	private boolean useOriginalTreeData = false;

	public TreeCombinator(final AbstractTreeSynthesizer verticalTS,
			final AbstractTreeSynthesizer horizontalTS, final MBModel aModel) {
		myVerticalTreeSynthesizer = verticalTS;
		myHorizontalTreeSynthesizer = horizontalTS;
		myModel = aModel;

		myVerticalTreeSynthesizer.setType(TreeProxyManager.VERTICAL);
		myHorizontalTreeSynthesizer.setType(TreeProxyManager.HORIZONTAL);
	}

	/**
	 * This method recieves a set of Relations and returns the Relation with
	 * highest priority which is not part of the tree spanning relations of both
	 * TreeSynthesizers
	 * 
	 * @param relations
	 *            The Set of Relations
	 * @return The Relation with highest priority
	 */
	private Relation getPriorityRelation(final RelationBoundary relations) {
		Relation result = null;

		if (relations != null) {
			Iterator<Relation> iter = relations.iterator();

			while (iter.hasNext()) {
				Relation relation = iter.next();

				// getTreeSpanningRelationTypes() returns all relations that
				// have
				// a starting node in one and the ending node in another tree
				// (tree spanning), that means it is a link. Relations, that are
				// settled inside a single tree are parent-child relations to
				// build a tree from nodes that are just stored in a list.
				if (!(myModel.getTreeSpanningRelationTypes().contains(relation
						.getRelationType()))) {
					// The relation is not part of any tree spanning relations
					if (result == null) {
						result = relation;
					} else if ((result.getRelationClass() == RelationClass.INFERRED)
							&& (relation.getRelationClass() == RelationClass.REAL)) {
						result = relation;
					}
				}
			}
		}

		return result;
	}

	public Relation getRelation(final VisibleNode aVerticalNode,
			final VisibleNode aHorizontalNode) {
		return getPriorityRelation(this.myModel.getBoundary(
				aVerticalNode.getNestedNode(),
				aHorizontalNode.getNestedNode()));
	}
	
	public Collection<Relation> getRelations() {
	      return this.myModel.getRelations().get(RelationDirection.BIDIRECTIONAL);
	   }

	protected int getHorizontalPosition(final VisibleNode aNode) {
		Iterator<VisibleNode> iter = myHorizontalTreeSynthesizer.getVisibleTree()
				.flatteningTreeIterator();

		for (int i = 0; iter.hasNext(); i++) {
			if (iter.next() == aNode) {
				return i;
			}
		}

		return -1;
	}

	protected int getVerticalPosition(final VisibleNode aNode) {
		Iterator<VisibleNode> iter = myVerticalTreeSynthesizer.getVisibleTree()
				.flatteningTreeIterator();

		for (int i = 0; iter.hasNext(); i++) {
			if (iter.next() == aNode) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Check if a collapsed Relation exists between the specified horizontal and vertical node
	 * and considering the specified collapsedType in the following way:
	 * <ul>
	 *   <li>if collapsedType == {@link TreeCombinator#VERTICAL_COLLAPSED}, then the nodes of vertical node subtree with the horizontal node will be compared
	 *   <li>if collapsedType == {@link TreeCombinator#HORIZONTAL_COLLAPSED}, then the nodes of horizontal node subtree with the vertical node will be compared
	 *   <li>if collapsedType == {@link TreeCombinator#BOTH_COLLAPSED}, then the nodes of vertical node subtree with the nodes of horizontal node subtree will be compared
	 *   <li>otherwise false will be returned.
	 * <ul>  
	 * @param aVerticalNode the vertical node to check
	 * @param aHorizontalNode the horizontal node to check
	 * @param collapsedType to consider
	 * @return true if a collapsed relation exists, otherwise null
	 */
	public boolean hasCollapsedRelation(final VisibleNode aVerticalNode,
			final VisibleNode aHorizontalNode, final int collapsedType) {
	   if(collapsedType < 1 || collapsedType > 3){
	      return false;
	   }
	   Iterator<VisibleNode> verticalIterator;
	   //check if the vertical node is collapsed
	   if(collapsedType != HORIZONTAL_COLLAPSED){
	      verticalIterator = aVerticalNode
         .flatteningTreeIterator();
	   }
	   else{
	      ArrayList<VisibleNode> visibleNodes = new ArrayList<VisibleNode>();
	      visibleNodes.add(aVerticalNode);
	      verticalIterator = visibleNodes.iterator();
	   }
	   
		while (verticalIterator.hasNext()) {
			VisibleNode verticalNode = verticalIterator.next();
			Iterator<VisibleNode> horizontalIterator = aHorizontalNode
					.flatteningTreeIterator();
		   //check if the horizontal node is collapsed
			if(collapsedType != VERTICAL_COLLAPSED){
	         horizontalIterator = aHorizontalNode
            .flatteningTreeIterator();
	      }
	      else{
	         ArrayList<VisibleNode> visibleNodes = new ArrayList<VisibleNode>();
	         visibleNodes.add(aHorizontalNode);
	         horizontalIterator = visibleNodes.iterator();
	      }
			
			while (horizontalIterator.hasNext()) {
				VisibleNode horizontalNode = horizontalIterator
						.next();
				RelationBoundary boundary = myModel.getBoundary(verticalNode
						.getNestedNode(), horizontalNode.getNestedNode());

				if (boundary != null && ! (verticalNode == aVerticalNode && horizontalNode == aHorizontalNode)) {
					Iterator<Relation> boundaryIterator = boundary.iterator();
					while (boundaryIterator.hasNext()) {
						Relation r = boundaryIterator.next();
						if (!myModel.getTreeSpanningRelationTypes().contains(
								r.getRelationType())) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * removes nodes with no relations from both trees
	 * @deprecated: Not in use anymore. No function.
	 */
	@Deprecated
	public void removeEmptyNodes(final boolean showEmptyNodesInVerticalTree,
			final boolean showEmptyNodesInHorizontalTree) {
		/*
		 * // if both trees should be shown with all nodes, // remove the
		 * visible trees that were saved if (showEmptyNodesInVerticalTree ==
		 * true && showEmptyNodesInHorizontalTree == true) { // remove old
		 * visible tree to retrieve the original data
		 * ((HideTreeNodesSynthesizer) myHorizontalTreeSynthesizer)
		 * .setVisibleTree(null); // remove old visible tree to retrieve the
		 * original data ((HideTreeNodesSynthesizer) myVerticalTreeSynthesizer)
		 * .setVisibleTree(null); return; } // check if the two treeSynthesizer
		 * are of the correct type if (!(myHorizontalTreeSynthesizer instanceof
		 * HideTreeNodesSynthesizer) || !(myVerticalTreeSynthesizer instanceof
		 * HideTreeNodesSynthesizer)) return; VisibleNode verticalNode = null,
		 * horizontalNode = null; VisibleNode myVerticalTree = null,
		 * myHorizontalTree = null; Enumeration horizontalEnumeration = null; //
		 * breadth first enumeration over vertical tree Enumeration
		 * verticalEnumeration = null; // hashset that contains all visible
		 * nodes that have a visible relation // including nodes with children
		 * that have relations HashSet verticalSet = new HashSet(); HashSet
		 * horizontalSet = new HashSet(); // should we build the tree using the
		 * original tree data or the // changed tree data if
		 * (isUseOriginalTreeData() == true) { // remove old visible tree to
		 * retrieve the original data ((HideTreeNodesSynthesizer)
		 * myHorizontalTreeSynthesizer) .setVisibleTree(null); // remove old
		 * visible tree to retrieve the original data
		 * ((HideTreeNodesSynthesizer) myVerticalTreeSynthesizer)
		 * .setVisibleTree(null); } // first loop through all nodes to find out
		 * // which nodes have relations; the horizontal nodes id's are stored
		 * in // horizontalSet, and the vertical nodes id's are stored in
		 * verticalSet. // This need to be done with the ids since different
		 * nodes can have the // same names. verticalEnumeration =
		 * ((HideTreeNodesSynthesizer) myVerticalTreeSynthesizer)
		 * .getVisibleTree().breadthFirstEnumeration(); while
		 * (verticalEnumeration.hasMoreElements()) { verticalNode =
		 * (VisibleNode) verticalEnumeration.nextElement();
		 */
		/*
		 * // get all empty nodes except tmp in the vertical view if
		 * (verticalNode.toString().equals("tmp")) { //myVerticalTree =
		 * (VisibleNode) verticalNode.clone(); }
		 */
		/*
		 * horizontalEnumeration = ((HideTreeNodesSynthesizer)
		 * myHorizontalTreeSynthesizer)
		 * .getVisibleTree().breadthFirstEnumeration(); while
		 * (horizontalEnumeration.hasMoreElements()) { horizontalNode =
		 * (VisibleNode) horizontalEnumeration.nextElement();
		 */
		/*
		 * // get all empty nodes except tmp if
		 * (horizontalNode.toString().equals("tmp")) { //myHorizontalTree =
		 * (VisibleNode) horizontalNode.clone(); }
		 */
		// we found a relation, so add the whole path
		// of the two nodes to the different sets
		/*
		 * if ((getRelation(verticalNode, horizontalNode) != null)) { TreeNode
		 * tempNode[] = verticalNode.getPath(); for (int t = 0; t <
		 * tempNode.length; t++) { verticalSet .add(((VisibleNode)
		 * tempNode[t]).getNestedNode().getID()); } tempNode =
		 * horizontalNode.getPath(); for (int t = 0; t < tempNode.length; t++) {
		 * horizontalSet.add(((VisibleNode) tempNode[t]).getNestedNode()
		 * .getID()); } } // end if } // end while } // end while // build new
		 * trees using the hashSets from above. // if a node's id is found in
		 * these sets, the node definately is // in the new tree, so add it.
		 * myVerticalTree = buildTree( ((HideTreeNodesSynthesizer)
		 * myVerticalTreeSynthesizer), verticalSet); myHorizontalTree =
		 * buildTree( ((HideTreeNodesSynthesizer) myHorizontalTreeSynthesizer),
		 * horizontalSet); // set the new constructed tree if
		 * (showEmptyNodesInHorizontalTree == false) ((HideTreeNodesSynthesizer)
		 * myHorizontalTreeSynthesizer) .setVisibleTree(myHorizontalTree); if
		 * (showEmptyNodesInVerticalTree == false) ((HideTreeNodesSynthesizer)
		 * myVerticalTreeSynthesizer) .setVisibleTree(myVerticalTree); //
		 * log.debug("end removeEmptyNodes");
		 */
	}

	/**
	 * @return Returns the useOriginalTreeData.
	 */
	public boolean isUseOriginalTreeData() {
		return useOriginalTreeData;
	}

	/**
	 * @param useOriginalTreeData
	 *            The useOriginalTreeData to set.
	 */
	public void setUseOriginalTreeData(final boolean useOriginalTreeData) {
		this.useOriginalTreeData = useOriginalTreeData;
	}

	/**
	 * @return Returns the myHorizontalTreeSynthesizer.
	 */
	public AbstractTreeSynthesizer getHorizontalTreeSynthesizer() {
		return myHorizontalTreeSynthesizer;
	}

	/**
	 * @return Returns the myVerticalTreeSynthesizer.
	 */
	public AbstractTreeSynthesizer getVerticalTreeSynthesizer() {
		return myVerticalTreeSynthesizer;
	}
}