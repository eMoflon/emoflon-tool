/*
 * Created on 14.01.2004 To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.fhg.iao.matrixbrowser.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.uniqueidentifier.ID;
import de.fhg.iao.swt.util.uniqueidentifier.IDPool;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RelationCachedModel extends DefaultMBModel{

	/**
	 * The RelationCachedModel keeps this list for all relations the nodes have
	 * with one another. Lists of each node's relations are stored as value. The
	 * nodes' ids are used as key.
	 */
	private Map<ID, List<Relation>> myNodeRelations = new WeakHashMap<ID, List<Relation>>();

	/**
	 * A RelationCachedModel keeps a list, <code>myNodeRelations</code> of the
	 * relations every node has with another one. This method adds a relation to
	 * the list of relations for the relation's source and target node. The
	 * method calls the superclasse's method
	 * {@link de.fhg.iao.matrixbrowser.model#DefaultMBModel
	 * DefaultMBModel.addRelation} after having completed.
	 * 
	 * @param relation
	 *            the relation to add
	 * 
	 */
	public final void addRelation(final Relation relation) {
		// get the relations of the relation's source node
		List<Relation> list = myNodeRelations.get(relation.getSourceNodeID());
		if (list == null) { // the node had no relations beforehand
			list = new LinkedList<Relation>(); // create a new list of relations
			myNodeRelations.put(relation.getSourceNodeID(), list); /*
																	 * set the
																	 * newly
																	 * created
																	 * list to
																	 * be the
																	 * relation
																	 * list of
																	 * the
																	 * treated
																	 * source
																	 * node
																	 */
		}
		list.add(relation); /*
							 * add the relation to the relation listof the
							 * source node
							 */

		list = myNodeRelations.get(relation.getTargetNodeID()); /*
																 * get relations
																 * ofrelation's
																 * target node
																 */

		if (list == null) { // the node had no relations beforehand
			list = new LinkedList<Relation>(); // create a new list of relations
			myNodeRelations.put(relation.getTargetNodeID(), list);
			/*
			 * set the newly created list to be the relation list of the treated
			 * target node
			 */
		}

		list.add(relation); /*
							 * add the relation to the relation list of the
							 * target node
							 */
		super.addRelation(relation); /*
									 * call the superclasse's (DefaultMBModel)
									 * relation adding method
									 */
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.DefaultMBModel#getRelations
	 *      (de.fhg.iao.matrixbrowser.model.elements.Node)
	 */
	public final Collection<Relation> getRelations(final Node node) {
		if (node == null) {
			return null;
		}
		List<Relation> list = myNodeRelations.get(node.getID());

		if (list == null) {
			return new LinkedList<Relation>();
		} else {
			return new LinkedList<Relation>(list);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.DefaultMBModel#removeRelation
	 *      (de.fhg.iao.matrixbrowser.model.elements.Relation)
	 */
	public final void removeRelation(final Relation relation) {
		List<Relation> list = myNodeRelations.get(relation.getSourceNodeID());

		if (list != null) {
			list.remove(relation);
		}

		list = myNodeRelations.get(relation.getTargetNodeID());

		if (list != null) {
			list.remove(relation);
		}

		super.removeRelation(relation);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.DefaultMBModel#getRelationsByType
	 *      (de.fhg.iao.matrixbrowser.model.elements.Node,
	 *      de.fhg.iao.matrixbrowser.model.elements.RelationType)
	 */
	public final Collection<Relation> getRelationsByType(final Node node,
			final RelationType type) {
		Collection<Relation> result = this.getRelations(node);
		if (result != null) {
			/*
			 * result == null means the node has no relations (happens if it's a
			 * new node.)
			 */
			Iterator<Relation> iter = result.iterator();
			while (iter.hasNext()) {
				if (((Relation) iter.next()).getRelationType() != type) {
					iter.remove();
				}
			}
		}
		return result;
	}

	/**
	 * Add a Node and a Relation at the same time.
	 * 
	 * @param node
	 *            the Node to add
	 * @param relation
	 *            the Relation to add
	 */
	public final void addNodeAndRelation(final Node node,
			final Relation relation) {
		if (node.getID() == null) {
			node.setID(IDPool.getIDPool().getID());
		}
		if (node.getModel() == null) {
			node.setModel(this);
		}
		super.addNode(node);
		this.addRelation(relation);
		// super.fireModelChangedEvent(new
		// ModelChangedEvent((RelationCachedModel)
		// this, (RelationCachedModel)this, relation));
	}
}
