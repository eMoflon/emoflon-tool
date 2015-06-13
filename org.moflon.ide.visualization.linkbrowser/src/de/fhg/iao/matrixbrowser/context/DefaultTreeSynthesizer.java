/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * DefaultTreeSynthesizer.java,v 1.5 2004/04/07 14:37:06 roehrijn Exp $ Created
 * on 28.11.2003
 */
package de.fhg.iao.matrixbrowser.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

import de.fhg.iao.matrixbrowser.EncapsulatingVisibleNode;
import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.matrixbrowser.model.ModelChangedEvent;
import de.fhg.iao.matrixbrowser.model.elements.MBModelNode;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;

/**
 * @author roehrich
 * @author <a href=mailto:cs@christian-schott.de>Christian Schott</a>
 */
public class DefaultTreeSynthesizer extends AbstractTreeSynthesizer {

   /**
	 * This map caches Vectors, which contains the children of each Node. If the
	 * cache doesn't contain the the desired Vector, it is created and added to
	 * this cache.
	 */
	protected Map<Node, Collection<Node>> myChildCache = new HashMap<Node, Collection<Node>>();

	/**
	 * Contains the TreeModel this TreeSynthesizer is responsible for.
	 */
	protected VisibleNode myVisibleTree = null;

	protected Node myRootNode = null;

	protected int myVisibleNodeCount = 0;

	/**
	 * Constructor for a new DefaultTreeSynthesizer setting the TreeManager.
	 * 
	 * @param manager
	 *            The <code>TreeManager</code> for the created
	 *            <code>DefaultTreeSynthesizer</code>
	 */
	protected DefaultTreeSynthesizer(final TreeManager manager) {
		this.setTreeManager(manager);
	}

	/**
	 * null constructor.
	 */
	protected DefaultTreeSynthesizer() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.context.AbstractTreeSynthesizer#getSynthesizerName
	 * ()
	 */
	@Override
	public String getSynthesizerName() {
		return "Default Synthesizer";
	}

	/**
	 * Returns the node which has the given id. Therefore a huge hashtable in
	 * the backend model is used to save the mapping between ids an nodes. Ids
	 * are used for Drag&Drop because serialization and deserialization changes
	 * the object's reference but the id remains.
	 * 
	 * @param id
	 *            - the ID object of the desired node <br>
	 * @return the node wich has the given id <br>
	 * @author Jan Roehrich <br>
	 * @see de.fhg.iao.matrixbrowser.model.MBModel#getNode(de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */

	/*
	 * public Node getNode(ID id) { return
	 * this.getTreeManager().getModel().getNode(id); }
	 */

	/**
	 * Returns all children (regarding to the actual TreeSpanningRelations) of
	 * the given node.
	 * 
	 * @param node
	 *            the node of which the children will be returned.
	 * @return A Vector with all nodes which are children of tNode. An empty
	 *         Vector <b>(and not null)</b> if tNode has no children. <br>
	 */
	@Override
	public Collection<Node> getChildren(final Node node) {
		Collection<Node> tmpVec = this.myChildCache
				.get(node);
		// VisibleNode temp; //never used

		if (tmpVec == null) {
			Collection<Relation> relSet = new LinkedList<Relation>();
			Collection<RelationType> treeSpanningRelationTypes = getTreeManager()
					.getModel().getTreeSpanningRelationTypes();
			Iterator<RelationType> typeIter = treeSpanningRelationTypes
					.iterator();

			while (typeIter.hasNext()) {
				relSet.addAll(this.getTreeManager().getModel()
						.getRelationsByType(node,
								typeIter.next()));
			}

			// now all relations of one of the treeSpanningRelationTypes which
			// affect
			// the given node are in the set
			// now we have to check the direction of the relations. Only
			// relations
			// with tNode as source will remain
			tmpVec = new ArrayList<Node>(relSet.size());

			Iterator<Relation> iter = relSet.iterator();

			while (iter.hasNext()) {
				Relation tmpRel = iter.next();

				if (tmpRel.getSourceNodeID().equals(node.getID())) {
					if (!tmpVec.contains(myTreeManager.getModel().getNode(
							tmpRel.getTargetNodeID()))) {
						tmpVec.add(myTreeManager.getModel().getNode(
								tmpRel.getTargetNodeID()));
					}
				}
			}

			this.myChildCache.put(node, tmpVec);
		}

		return tmpVec;
	}

	/**
	 * Creates a <code>TreeModel</code> containing <code>VisibleNode</code>s.
	 * This model corresponds to a subtree in the Backend model but has its own
	 * model.
	 * 
	 * @param root
	 *            The <code>Node</code> which will be the root of the
	 *            <code>TreeModel<code>.
	 * @return The new TreeModel
	 */
	protected void createVisibleTree() {
		// log.debug("createVisibleTree called by class: "+sun.reflect.Reflection.getCallerClass(2).getName());
		// log.debug("method: "+Thread.currentThread().getStackTrace()[2].getMethodName());
		// log.debug("which was called by class: "+sun.reflect.Reflection.getCallerClass(3).getName());
		// log.debug("method: "+Thread.currentThread().getStackTrace()[3].getMethodName());
		myVisibleNodeCount = 0;
		this.myVisibleTree = this.transformToVisible(myRootNode);
		fireTreeModelChangedEvent(new TreeModelChangedEvent(this));
	}

	/**
	 * Creates a new hierarchy of VisibleNodes out of Nodes in the backend.
	 * (recursive)
	 * 
	 * @param node
	 *            The node from where on the transformation will be started
	 * @return The new hierarchy
	 */
	protected VisibleNode transformToVisible(final Node node) {
		VisibleNode result;
		/*
		 * if (node!=null && node.isNew()){
		 * log.debug("The node"+node.toString()+"is a new node."); //we should
		 * be able to skip the transformation in the case of the node // being
		 * already included in the model. return new VisibleNode(node); }else
		 */
		if (node != null) {
			try {
				result = new EncapsulatingVisibleNode(node);
				Collection<Node> childs = this.getChildren(node);
				Iterator<Node> iter = childs.iterator();

				myVisibleNodeCount++;

				while (iter.hasNext()) {
					result.add(transformToVisible(iter.next()));
				}
				return result;
			} catch (NullPointerException e) {
				log
						.debug("DefaultTreeSynthesizer.transformToVisible: Null Pointer exception eingefangen.");
				result = new EncapsulatingVisibleNode(new MBModelNode("null"));
				return result;
			} catch (Exception e) {
				log.debug("Andere Exception: " + e.getMessage());
				e.printStackTrace();
				result = new EncapsulatingVisibleNode(new MBModelNode("null"));
				return result;
			}
		} else {
			log.debug("Node is null. returning null as new hierarchy.");
			return null;
		}
	}

	/**
	 * Returns all relations regarding one node except the treeSpanningRelations
	 * 
	 * @param node
	 *            the node the relations will be searched for
	 * @return A Set containing all found relations
	 */
	@Override
	public Collection<Relation> getRelations(final Node node) {
		Collection<Relation> relations = this.myTreeManager.getModel()
				.getRelations(node);
		Iterator<Relation> iter = relations.iterator();

		while (iter.hasNext()) {
			Relation rel = iter.next();

			if (this.myTreeManager.getModel().getTreeSpanningRelationTypes()
					.contains(rel.getRelationType())) {
				iter.remove();
			}
		}

		return relations;
	}

	/**
	 * Set a new root node. This causes a new TreeModel to be generated which
	 * may take some time
	 * 
	 * @param node
	 *            The Node taken as new root
	 */
	@Override
	public void setRootNode(final Node node) {
		this.myRootNode = node;
		this.createVisibleTree();
	}

	/**
	 * @return
	 */
	@Override
	public VisibleNode getVisibleTree() {
		return myVisibleTree;
	}
	
	  /**
    * @return
    */
   public VisibleNode getVisibleSubTree(final Node subNode) {
      VisibleNode curVisibleNode = null;
      VisibleNode subTreeRoot = null;
      Vector<VisibleNode> nodes = new Vector<VisibleNode>();
      nodes.add(myVisibleTree);
      for(int i = 0; i<nodes.size()&& subTreeRoot == null; i++){
         curVisibleNode = nodes.get(i);
         if(curVisibleNode.getNestedNode().equals(subNode)){
            subTreeRoot = curVisibleNode;
         }else{
            Collection<VisibleNode> children = curVisibleNode.getChildren();
            if (children != null){
               nodes.addAll(children);
            }
         }
      }
      return subTreeRoot;
   }
	
	@Override
	public void onModelChanged(final ModelChangedEvent aEvent) {
		myChildCache.clear();
		if (this.getTreeManager() == null) {
			log.debug("no TreeManager set!!!");
		}
		if (aEvent.getNode() != null && aEvent.getNode().isNew()) {
			log.debug("New node " + aEvent.getNode().toString() + "added");
			// in this case we do not want to transform the node to a visible
			// node yet as it has no relations.
		} else if (aEvent.getNode() != null && aEvent.getRelation() == null) {
			log.debug("node deleted");
			this.createVisibleTree();
		} else if (this.myRootNode == null && aEvent.getRelation() == null
				&& aEvent.getNode() != null) {
			log
					.debug("there has been no root node before. Using the given node "
							+ aEvent.getNode());
			this.setRootNode(aEvent.getNode());
			this.createVisibleTree();
		} else if (aEvent.getRelation() != null) {
			log
					.debug("DefaultTreeSynthesizer.onModelChanged firingTreeModelChangedEvent because of added Relation");
			this.createVisibleTree();
			fireTreeModelChangedEvent(new TreeModelChangedEvent(this));
		} else {
			this.createVisibleTree();
		}
	}

	@Override
	public int getVisibleNodeCount() {
		return myVisibleNodeCount;
	}
	/**
	 * @return Returns the concatenatedVisibleTree.
	 */

}