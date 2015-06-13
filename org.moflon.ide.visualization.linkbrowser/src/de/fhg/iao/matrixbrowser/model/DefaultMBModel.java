/*
 * Created on 12.08.2003 To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package de.fhg.iao.matrixbrowser.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationBoundary;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.uniqueidentifier.ID;
import de.fhg.iao.swt.util.uniqueidentifier.IDPool;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DefaultMBModel implements MBModel {

	private static final Logger log = Logger.getLogger(DefaultMBModel.class);

	private boolean listenersActivated = true;
	
	/**
	 * This map contains instances of RelationType as keys and instances of Set
	 * as values. The value set contains instances of Relation with the actual
	 * relation type.
	 * 
	 * @TODO make the default initial capacity be set by the controller
	 */
	private Map<RelationType, Collection<ID>> relationTypes = new LinkedHashMap<RelationType, Collection<ID>>(
			10);

	/**
	 * A Map which map IDs to instances of Node, where ID and Node must be
	 * unique.
	 * 
	 * @TODO make the default initial capacity be set by the controller
	 */
	private Map<ID, Node> nodes = new LinkedHashMap<ID, Node>(20);

	/**
	 * For increasing access speed all relations are additionally mapped as ID
	 * -> Relation.
	 * 
	 * @TODO make the default initial capacity be set by the controller
	 */
	private Map<ID, Relation> relations = new LinkedHashMap<ID, Relation>(100);

	/**
	 * This Set contains all relation types which are intended to span up a tree
	 * out of the underlying graph. This is only a default value and can be
	 * overridden by TreeSythsizer objects.
	 * 
	 * @TODO make the default initial capacity be set by the controller
	 */
	private Collection<RelationType> myTreeSpanningRelationTypes = new Vector<RelationType>(
			5);

	/**
	 * A reference to the node which is intended to be the origin for tree
	 * building. This reference may be NULL. If it is, its not possible to build
	 * trees through the TreeSynthesizer.
	 */
	private Node rootNode = null;

	/** Keeps a Map of associated <code>RelationBoudaries</code>. */
	private Map<RelationBoundary, RelationBoundary> myRelationBoundaryMap = new HashMap<RelationBoundary, RelationBoundary>();

	/** Keeps a List of EventListeners registered for ModelChangedEvents. */
	private EventListenerList myModelChangedListeners = new EventListenerList();

	/**
	 * Adds a Node without firing a ModelChangedEvent for the Model is in an
	 * inconsistent State as long as no Relations have been added for this node.
	 * 
	 * @deprecated This should not be necessary any more. The normal addNode
	 *             should work.
	 * @param {@inheritDoc}
	 */
	@Deprecated
	public final void addNodeNoNotify(final Node node) {
		if (node.getID() == null) {
			node.setID(IDPool.getIDPool().getID());
		}

		if (node.getModel() == null) {
			node.setModel(this);
		}

		nodes.put(node.getID(), node);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.MBModel
	 *      #addNode(de.fhg.iao.matrixbrowser.model.elements.Node)
	 */
	public final void addNode(final Node node) {
		if (node.getID() == null) {
			node.setID(IDPool.getIDPool().getID());
		}

		if (node.getModel() == null) {
			node.setModel(this);
		}
		node.setNew();
		nodes.put(node.getID(), node);
		fireModelChangedEvent(new ModelChangedEvent(this, this, node));
	}

	/**
	 * Add a new <code>RelationType</code> with which trees can be build up.
	 * 
	 * @param aRelationType
	 *            the <code>RelationType</code> with which a tree can be build
	 * @see de.fhg.iao.matrixbrowser.model.elements.RelationType
	 */
	public final void addTreeSpanningRelationType(
			final RelationType aRelationType) {
		myTreeSpanningRelationTypes.add(aRelationType);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.MBModel
	 *      #addRelationType(de.fhg.iao.matrixbrowser.model.elements.RelationType)
	 */
	public final void addRelationType(final RelationType type) {
		if (!this.relationTypes.containsKey(type)) {
			this.relationTypes.put(type, new LinkedList<ID>());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.MBModel
	 *      #addRelation(de.fhg.iao.matrixbrowser.model.elements.Relation)
	 */
	public void addRelation(final Relation aRelation) {
		// add Relation in Map grouped by type
		if (aRelation.getID() == null) {
			aRelation.setID(IDPool.getIDPool().getID());
		}

		Collection<ID> relationSet = null;

		if (!relationTypes.containsKey(aRelation.getRelationType())) {
			// This RelationType doesn't exist -> create it
			relationSet = new LinkedList<ID>();
			relationTypes.put(aRelation.getRelationType(), relationSet);
		} else {
			relationSet = relationTypes.get(aRelation.getRelationType());
		}

		relationSet.add(aRelation.getID());
		this.relations.put(aRelation.getID(), aRelation);

		// add Relation in Map grouped by boundary
		RelationBoundary boundary = getBoundary(getNode(aRelation
				.getSourceNodeID()), getNode(aRelation.getTargetNodeID()));

		if (boundary == null) {
			boundary = new RelationBoundary(
					getNode(aRelation.getSourceNodeID()), getNode(aRelation
							.getTargetNodeID()));
			myRelationBoundaryMap.put(boundary, boundary);
         boundary.add(aRelation);
		}
		fireModelChangedEvent(new ModelChangedEvent(this, this, aRelation));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.MBModel
	 *      #removeRelation(de.fhg.iao.matrixbrowser.model.elements.Relation)
	 */
	public void removeRelation(final Relation aRelation) {
		if(aRelation != null){
          RelationType type = aRelation.getRelationType();
            /*
          * first remove the Relation from the Collection of Relations sharing
          * the same RelationType.
          */
         Collection<ID> relationsOfSameType = this.relationTypes.get(type);
         if (relationsOfSameType != null) {
            relationsOfSameType.remove(aRelation.getID());
            Node targetNode = getNode(aRelation.getTargetNodeID());
            Node sourceNode = getNode(aRelation.getSourceNodeID());
            RelationBoundary tmpBoundary = new RelationBoundary(targetNode,
                  sourceNode);
            RelationBoundary boundary = (RelationBoundary) myRelationBoundaryMap
                  .get(tmpBoundary);
            if(boundary != null){
               boundary.remove(aRelation);
               if (boundary.size() == 0) {
                  myRelationBoundaryMap.remove(boundary);
               }
            }
            //Then remove it from the relations map
            relations.remove(aRelation.getID());
            fireModelChangedEvent(new ModelChangedEvent(this, this, aRelation));
         }
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.MBModel#clear()
	 */
	public final void clear() {
		relationTypes.clear();
		nodes.clear();
		relations.clear();
		setRootNode(null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.MBModel
	 *      #getNode(de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	public final Node getNode(final ID id) {
		return (Node) nodes.get(id);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.MBModel
	 *      #getRelations(de.fhg.iao.matrixbrowser.model.elements.Node)
	 */
	public Collection<Relation> getRelations(final Node node) {
		Collection<Relation> relSet = new LinkedList<Relation>();

		Iterator<RelationType> iter = this.relationTypes.keySet().iterator();

		while (iter.hasNext()) {
			Collection<ID> tmpIDSet = this.relationTypes.get(iter.next());
			Iterator<ID> iDIter = tmpIDSet.iterator();

			while (iDIter.hasNext()) {
			   Relation relation = getRelation(iDIter.next());
			   if(relation != null){
	           ID tmpSourceID =  relation.getSourceNodeID();
	           ID tmpTargetID =  relation.getTargetNodeID();
	           if((tmpSourceID.equals(node.getID())) || (tmpTargetID.equals(node.getID()))) {
	              // The node is involved in this relation either as source or
	              // as target
	              // -> add Relation to the set
	              relSet.add(relation);
	           }  
			   }
			}
		}

		return relSet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.MBModel#getRelationsByType
	 *      (de.fhg.iao.matrixbrowser.model.elements.Node,
	 *      de.fhg.iao.matrixbrowser.model.elements.RelationType)
	 */
	public Collection<Relation> getRelationsByType(final Node aNode,
			final RelationType aRelationType) {
		Collection<Relation> relSet = new LinkedList<Relation>();
		Collection<ID> tmpIDSet = relationTypes.get(aRelationType);

		if (tmpIDSet != null) {
			Iterator<ID> iter = tmpIDSet.iterator();

			while (iter.hasNext()) {
				ID tmpID = iter.next();
				Relation tempRelation = relations.get(tmpID);
				if(tempRelation != null){
		           ID sourceID = tempRelation.getSourceNodeID();
		           ID targetID = tempRelation.getTargetNodeID();
		           if (sourceID != null &&
		                 (sourceID.equals(aNode.getID()))
		                 || targetID != null
		                 && (targetID.equals(aNode.getID()))) {
		              relSet.add(tempRelation);
		           }
				}
			}
		}

		return relSet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.MBModel#getRelationTypes()
	 */
	public final Collection<RelationType> getRelationTypes() {
		return relationTypes.keySet();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param node
	 *            {@inheritDoc}
	 * @see de.fhg.iao.matrixbrowser.model.MBModel
	 *      #removeNode(de.fhg.iao.matrixbrowser.model.elements.Node)
	 */
	public final void removeNode(final Node node) {
		// All relations with this object must be deleted
		Collection<Relation> relSet = this.getRelations(node);
		Iterator<Relation> iter = relSet.iterator();
		while (iter.hasNext()) {
			this.removeRelation((Relation) iter.next());
		}
		// now the object itself can be deleted
		this.nodes.remove(node.getID());
		fireModelChangedEvent(new ModelChangedEvent(this, this, node));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return all nodes
	 */
	public final Map<ID, Node> getNodes() {
		return nodes;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.MBModel
	 *      #getRelation(de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	public final Relation getRelation(final ID id) {
		return (Relation) this.relations.get(id);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return all relations between nodes
	 */
	public final Map<RelationType, Collection<Relation>> getRelations() {
	   Map<RelationType, Collection<Relation>> curRelationTypesMap = new HashMap<RelationType, Collection<Relation>>();
	   Collection<RelationType> curRelationTypes = relationTypes.keySet();
	   for(RelationType curRelationType : curRelationTypes){
	      
	      Collection<Relation> curRelations = new ArrayList<Relation>();
	      curRelationTypesMap.put(curRelationType,curRelations);
	      
	      Collection<ID> curIDs = relationTypes.get(curRelationType);
	      for(ID curID : curIDs){
	         curRelations.add(relations.get(curID));
	      }
	   }
	   return curRelationTypesMap;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setNodes(final Map<ID, Node> map) {
		nodes = map;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void setRelations(final Map<RelationType, Collection<Relation>> map) {
		Map<RelationType,Collection<ID>> newRelationTypes = new LinkedHashMap<RelationType, Collection<ID>>(10); 
	   Collection <RelationType> relationTypeCollection = map.keySet();
		for(RelationType curRelationType : relationTypeCollection){
		   Collection<ID> IDs = new LinkedList<ID>();
		   Collection<Relation> curRelations = map.get(curRelationType);
		   for(Relation curRelation : curRelations){
		      IDs.add(curRelation.getID());
		   }
	      newRelationTypes.put(curRelationType, IDs);
		}
	   relationTypes = newRelationTypes;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return The set containing all relation types which are intended to span
	 *         up the tree
	 */
	public final Collection<RelationType> getTreeSpanningRelationTypes() {
		return myTreeSpanningRelationTypes;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param aCollection
	 *            The set containing all relation types which are intended to
	 *            span up the tree
	 */
	public final void setTreeSpanningRelationTypes(
			final Collection<RelationType> aCollection) {
		myTreeSpanningRelationTypes = aCollection;
	}

	/** {@inheritDoc} */
	public final Node getRootNode() {
		log.debug("rootNode is " + rootNode);
		return rootNode;
	}

	/** {@inheritDoc} */
	public final void setRootNode(final Node node) {
		rootNode = node;
	}

	/** {@inheritDoc} */
	public final void addModelChangedListener(
			final ModelChangedListener aListener) {
		myModelChangedListeners.add(ModelChangedListener.class, aListener);
	}

	/** {@inheritDoc} */
	public final void removeModelChangedListener(
			final ModelChangedListener aListener) {
		myModelChangedListeners.remove(ModelChangedListener.class, aListener);
	}

	/** {@inheritDoc} */
	public final void fireModelChangedEvent(final ModelChangedEvent event) {
		if(areModelListenersActivated()){
		      Object[] listeners = myModelChangedListeners.getListenerList();
   	      for (int i = listeners.length - 2; i >= 0; i -= 2) {
   	         if (listeners[i] == ModelChangedListener.class) {
   	            ((ModelChangedListener) listeners[i + 1]).onModelChanged(event);
   	         }
   	      }
		}
	}

	/* (non-Javadoc)
	 * @see de.fhg.iao.matrixbrowser.model.MBModel#areModelListenersActivated()
	 */
	public boolean areModelListenersActivated()
   {
      return listenersActivated;
   }

   /**
	 * Returns an instance of RelationBoundary which can be interpreted as a set
	 * of all relations between the two nodes x1 and x2. This RelationBoundaries
	 * are hashed internally so retrieving all relations between two specific
	 * nodes should be a very fast operation.
	 * 
	 * @param aX1
	 *            First end-point of the boundary
	 * @param aX2
	 *            Second end-point of the boundary
	 * @return The boundary belonging to the given end-points
	 */
	public final RelationBoundary getBoundary(final Node aX1, final Node aX2) {
		RelationBoundary tmpBoundary = new RelationBoundary(aX1, aX2);

		return (RelationBoundary) myRelationBoundaryMap.get(tmpBoundary);
	}

   /* (non-Javadoc)
    * @see de.fhg.iao.matrixbrowser.model.MBModel#setModelListenersActivated(boolean)
    */
   public void setModelListenersActivated(boolean activated)
   {
      listenersActivated = activated;
   }
}
