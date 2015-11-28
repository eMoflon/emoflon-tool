/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * MBModel.java,v 1.3 2004/04/07 13:52:33 roehrijn Exp $ Created on
 * 17.11.2003
 */
package de.fhg.iao.matrixbrowser.model;

import java.util.Collection;
import java.util.Map;

import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationBoundary;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * This is an interface describing each method outline which may be used to
 * access graph models and so must be implemented by concrete models.
 * 
 * @author <a href=mailto:jan.roehrich@iao.fraunhofer.de>Jan Roehrich</a>
 * @author <a href=mailto:cs@christian-schott.de>Christian Schott</a>
 */
public interface MBModel {

	/**
	 * Adds a <code>Node</code> to this <code>MBModel</code>.
	 * 
	 * @param node
	 *            The <code>Node</code> to add
	 */
	void addNode(Node node);

	/**
	 * Add a new <code>RelationType</code> with which trees can be build up.
	 * 
	 * @param aRelationType
	 *            the <code>RelationType</code> with which a tree can be build
	 * @see de.fhg.iao.matrixbrowser.model.elements.RelationType
	 */
	void addTreeSpanningRelationType(RelationType aRelationType);

	/**
	 * Add a <code>RelationType</code>, used for relations between Nodes that
	 * are not TreeSpanningRelationTypes.
	 * 
	 * @param type
	 *            the <code>RelationType</code> to add to this model.
	 */
	void addRelationType(RelationType type);

	/**
	 * Add a <code>Relation</code> to this model. This method should probably
	 * fire a ModelChangedEvent
	 * 
	 * @param rel
	 *            The relation to add.
	 */
	void addRelation(Relation rel);

	/**
	 * Remove the given <code>Relation</code> from this model. This method
	 * should probably fire a ModelChangedEvent.
	 * 
	 * @param rel
	 *            the <code>Relation</code> to remove from the model.
	 */
	void removeRelation(Relation rel);

	/**
	 * Clear this whole model of all Relations, RelationTypes and Nodes.
	 */
	void clear();

	/**
	 * Gets the specified <code>Node</code> from the <code>MBModel</code>.
	 * 
	 * @param id
	 *            the ID of the Node to get
	 * @return the node having the given ID
	 */
	Node getNode(ID id);

	/**
	 * All Relations of the given Node in this Model as a Collection of
	 * Relations.
	 * 
	 * @param node
	 *            This model's node to get the Relations from
	 * @return the node's relations
	 */
	Collection<Relation> getRelations(Node node);

	/**
	 * Gets the <code>node</code>'s relations of the given
	 * <code>RelationType</code>.
	 * 
	 * @param node
	 *            The node in the model to query for Relations
	 * @param type
	 *            The relation type to query for
	 * @return a Collection of type Relation containing all the node's relations
	 *         of the given type, null if there are none.
	 */
	Collection<Relation> getRelationsByType(Node node, RelationType type);

	/**
	 * Get all <code>RelationType</code>s registered for this model in a
	 * <code>Collection</code> of <code>RelationType</code>.
	 * 
	 * @return a Collection containing all the RelationTypes in this model.
	 */
	Collection<RelationType> getRelationTypes();

	/**
	 * Removes the given <code>Node</code> from this <code>MBModel</code>. The
	 * implementation should probably issue a ModelChangedEvent.
	 * 
	 * @param node
	 *            The <code>Node</code> to remove from this model.
	 */
	void removeNode(Node node);

	/**
	 * Get all <code>Node</code>s contained in this <code>MBModel</code>.
	 * 
	 * @return all nodes in this <code>MBModel</code> as a Map of the
	 *         <code>Node</code>s' <code>ID</code>s and the corresponding
	 *         <code>Node</code>.
	 */
	Map<ID, Node> getNodes();

	/**
	 * Gets a <code>Relation</code> from this <code>MBModel</code> given its
	 * <code>ID</code>.
	 * 
	 * @param id
	 *            the Relation's ID
	 * @return this <code>MBModel</code>'s <code>Relation</code> having the
	 *         given <code>ID</code>
	 */
	Relation getRelation(ID id);

	/**
	 * Get a <code>Map</code> of all <code>Relation</code>s ordered by their
	 * <code>RelationType</code>s.
	 * 
	 * @return all <code>RelationType</code>s with their associated
	 *         <code>Relations</code> between <code>Nodes</code>
	 */
	Map<RelationType, Collection<Relation>> getRelations();

	/**
	 * Set this <code>MBModel</code> to contain the given <code>Node</code>s
	 * with their associated <code>ID</code>s.
	 * 
	 * @TODO schotti: does that make sense? The Node Type already contains the
	 *       IDs
	 * @param map
	 *            A Map of all the Node-<code>ID</code>s associated to their
	 *            Nodes
	 */
	void setNodes(Map<ID, Node> map);

	/**
	 * Set this <code>MBModel</code> to contain the given <code>Relations</code>
	 * s.
	 * 
	 * @param map
	 *            A Map of <code>RelationType</code>s and their associated
	 *            <code>RelationType</code>s.
	 */
	void setRelations(Map<RelationType, Collection<Relation>> map);

	/**
	 * Gets a <code>Collection</code> of all Tree Spanning
	 * <code>RelationType</code>s.
	 * 
	 * @return The set containing all relation types which are intended to span
	 *         up the tree
	 */
	Collection<RelationType> getTreeSpanningRelationTypes();

	/**
	 * @param set
	 *            The set containing all relation types which are intended to
	 *            span up the tree
	 */
	void setTreeSpanningRelationTypes(Collection<RelationType> set);

	/**
	 * Gets The Root node of this Model.
	 * 
	 * @return The node which is considered as rootNode in context of the
	 *         tree-spanning relations.
	 */
	Node getRootNode();

	/**
	 * Tells the model which node should be considered as root of the spanning
	 * tree. This node must already has been added by calling MBModel#addNode().
	 * 
	 * @param node
	 *            The node which will further on considered as root node.
	 */
	void setRootNode(Node node);

	/**
	 * Adds a <code>ModelChangedListener</code> that will be notified about
	 * changes in the model.
	 * 
	 * @param aListener
	 *            ModelChangedListener
	 */
	void addModelChangedListener(ModelChangedListener aListener);

	/**
	 * Removes a <code>ModelChangedListener</code> from the model's notification
	 * list. If the listener isn't in the list nothing happens.
	 * 
	 * @param aListener
	 *            The ModelChangedListener which will be removed
	 */
	void removeModelChangedListener(ModelChangedListener aListener);

	/**
	 * Returns an instance of RelationBoundary which can be interpreted as a set
	 * of all relations between the two nodes x1 and x2.
	 * 
	 * @param x1
	 *            First end-point of the boundary
	 * @param x2
	 *            Second end-point of the boundary
	 * @return The boundary belonging to the given end-points
	 */
	RelationBoundary getBoundary(Node x1, Node x2);

	void fireModelChangedEvent(final ModelChangedEvent event);
	
	/**
    * Return true if the model listeners are activated, false otherwise
	 * @return if the model listeners are activated
	 */
	boolean areModelListenersActivated();
	
	/**
    * Sets the model listeners activated
	 * @param activated set true, if the model listeners should be activated, false otherwise
	 */
	void setModelListenersActivated(boolean activated);
}
