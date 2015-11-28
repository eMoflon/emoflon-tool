/*
 * Created on 23.03.2004 To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.fhg.iao.matrixbrowser.model.elements;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author roehrich A RelationBoundary covers all Relations between a specific
 *         pair of Nodes. This must be understood as a set of relations with
 *         same source and target nodes. But be careful: There is no information
 *         about the Relation's directions stored in this boundary. For example
 *         we have to nodes <b>a </b> and <b>b </b>. So RelationBoundary(a,
 *         b).equals(new RelationBoundary(b, a) = true. This information is only
 *         stored within the relations.
 */
public class RelationBoundary {

	/**
	 * This Boundary's Source Node.
	 */
	private Node myNodeX1;

	/**
	 * This Boundary's target Node.
	 */
	private Node myNodeX2;

	/**
	 * The Collection holding all the Relation between Source and Target Node of
	 * this Boundary.
	 */
	private Collection<Relation> myRelations = new LinkedList<Relation>();

	/**
	 * Constructor of a new Boundary between the two given Nodes.
	 * 
	 * @param aSourceNode
	 *            the Boundary's Source Node
	 * @param aTargetNode
	 *            the Boundary's Target Node
	 */
	public RelationBoundary(Node aSourceNode, Node aTargetNode) {
		setSourceNode(aSourceNode);
		setTargetNode(aTargetNode);
	}

	/**
	 * Gets this Boundary's source Node.
	 * 
	 * @return Returns the sourceNode.
	 */
	public final Node getSourceNode() {
		return myNodeX1;
	}

	/**
	 * Sets the Source Node of this Boundary.
	 * 
	 * @param aSourceNode
	 *            The sourceNode to set.
	 */
	public final void setSourceNode(Node aSourceNode) {
		myNodeX1 = aSourceNode;
	}

	/**
	 * Gets the Boundary's targetNode.
	 * 
	 * @return the targetNode.
	 */
	public final Node getTargetNode() {
		return myNodeX2;
	}

	/**
	 * Sets the target Node of this Boundary.
	 * 
	 * @param aTargetNode
	 *            The targetNode to set.
	 */
	public final void setTargetNode(Node aTargetNode) {
		myNodeX2 = aTargetNode;
	}

	/**
	 * Adds a Relation to the Boundary between the two nodes.
	 * 
	 * @param aRelation
	 *            the Relation to be added.
	 * @return true if the Boundary's Collection of Relations has been changed
	 *         by this addition.
	 */
	public final boolean add(Relation aRelation) {
		return myRelations.add(aRelation);
	}

	/**
	 * Tells whether the given Relation is part of the Relations between the two
	 * nodes of this Boundary.
	 * 
	 * @param aRelation
	 *            relation this boundary is asked whether it contains it.
	 * @return <cc>true</cc> if this Relation is a Relation between the two
	 *         nodes of this boundary.
	 */
	public final boolean contains(Relation aRelation) {
		return myRelations.contains(aRelation);
	}

	/**
	 * @return true if no Relations exist between the two <code>Nodes</code> of
	 *         this Boundary.
	 */
	public final boolean isEmpty() {
		return myRelations.isEmpty();
	}

	/**
	 * Gets an iterator over all the Relations having this Boundary.
	 * 
	 * @return an iterator over the relations between the Nodes degined in this
	 *         Boundary.
	 */
	public final Iterator<Relation> iterator() {
		return myRelations.iterator();
	}

	/**
	 * Removes a Relation from the Boundary. This should be called whenever a
	 * Relation is removed from the model to keep the information in the model
	 * and this boundary consistent.
	 * 
	 * @param aRelation
	 *            the relation to remove
	 * @return <tt>true</tt> if this Boundary's collection changed as a result
	 *         of the call
	 */
	public final boolean remove(Relation aRelation) {
		return myRelations.remove(aRelation);
	}

	/**
	 * The number of relations between the two nodes.
	 * 
	 * @return the number of relations between the two nodes.
	 */
	public final int size() {
		return myRelations.size();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public final boolean equals(Object aObject) {
		if (aObject instanceof RelationBoundary) {
			RelationBoundary boundary = (RelationBoundary) aObject;

			return (((myNodeX1 == boundary.myNodeX1) && (myNodeX2 == boundary.myNodeX2)) || ((myNodeX1 == boundary.myNodeX2) && (myNodeX2 == boundary.myNodeX1)));
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public final int hashCode() {
		return (myNodeX1.hashCode() + myNodeX2.hashCode()) / 2;
	}
}
