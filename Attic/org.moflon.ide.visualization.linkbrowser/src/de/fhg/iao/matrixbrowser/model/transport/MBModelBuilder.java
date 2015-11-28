/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * MBModelBuilder.java,v 1.5 2004/04/07 13:52:33 roehrijn Exp $ Created on
 * 13.11.2003
 */
package de.fhg.iao.matrixbrowser.model.transport;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import de.fhg.iao.matrixbrowser.model.DefaultMBModel;
import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MBModelBuilder implements Builder {

	/**
	 * The backend which will be filled during the transformation
	 */
	protected MBModel myModel = null;

	/**
	 * Placeholder for the root node id becaus it is supplied before any nodes
	 * ared added.
	 */
	protected ID myRootNodeID = null;

	/**
	 * Construcs an empty MBModelBuilder
	 */
	public MBModelBuilder() {
		super();
		myModel = new DefaultMBModel();
	}

	public MBModelBuilder(final MBModel aModel) {
		super();
		myModel = aModel;
		myModel.clear(); // clears the whole model. (Nodes, Relations,
							// RelationTypes,...)
	}

	/**
	 * @see de.fhg.iao.matrixbrowser.model.transport.Builder#setRootNodeID(de.fhg.iao.swt.util.uniqueidentifier.ID)
	 */
	@Override
	public void setRootNodeID(final ID id) throws TransportException {
		myRootNodeID = id;
	}

	/**
	 * Add a node to the model built by this <code>ModelBuilder</code>. This
	 * can, by design only be done before relation types are added. schotti has
	 * commented the test for already added relation types to see why this
	 * shouldn't be done...?
	 * 
	 * @param node
	 *            the node to be added
	 * @author roehrich
	 * @author schotti
	 * @see de.fhg.iao.matrixbrowser.model.transport.Builder#addNode(de.fhg.iao.matrixbrowser.model.elements.Node)
	 */
	@Override
	public void addNode(final Node node) throws TransportException {
		// nodes can only be added if not allready relation types availiable
		if (myModel.getRelationTypes().isEmpty()) {
			if (myModel.getNode(node.getID()) == null) {
				myModel.addNode(node);
			} else {
				// node with this id is allready present
				throw new TransportException(
						"Duplicate node or node id. Node's ids must be unique or NULL");
			}
		} else {
			throw new TransportException(
					"Wrong order. It is not allowed to add nodes after relation types have been added");
		}
	}

	/**
	 * @see de.fhg.iao.matrixbrowser.model.transport.Builder#addRelation(de.fhg.iao.matrixbrowser.model.elements.Relation)
	 */
	@Override
	public void addRelation(final Relation relation) throws TransportException {

		if (myModel.getRelationTypes().contains(relation.getRelationType())) {

			if ((myModel.getNode(relation.getSourceNodeID()) != null)
					&& (myModel.getNode(relation.getTargetNodeID()) != null)) {

				myModel.addRelation(relation);

			} else {

				throw new TransportException(
						"Source node or target node of relation not availiable.");
			}
		} else {

			throw new TransportException(
					"Non existent relation type. Relation's relation types must be added before adding relations");
		}
	}

	/**
	 * @see de.fhg.iao.matrixbrowser.model.transport.Builder#addRelationType(de.fhg.iao.matrixbrowser.model.elements.RelationType,
	 *      boolean)
	 */
	@Override
	public void addRelationType(final RelationType type,
			final boolean isTreeSpanningRelationType) throws TransportException {
		if (!myModel.getRelationTypes().contains(type)) {

			myModel.addRelationType(type);

			if (isTreeSpanningRelationType) {

				myModel.getTreeSpanningRelationTypes().add(type);
			}
		} else {

			throw new TransportException(
					"Duplicate relation type. Relation types must be unique");
		}
	}

	/**
	 * @see de.fhg.iao.matrixbrowser.model.transport.Builder#initialize()
	 */
	@Override
	public void initialize() throws TransportException {
	}

	/**
	 * @see de.fhg.iao.matrixbrowser.model.transport.Builder#complete()
	 */
	@Override
	public void complete() throws TransportException {
		// the backend must contain at least 2 nodes
		if (myModel.getNodes().size() < 2) {
			// too less nodes

			throw new TransportException(
					"Too less nodes. At least two nodes must be supplied");
		}

		// there must be at least one relation type
		Map<RelationType, Collection<Relation>> relationTypes = myModel.getRelations();

		if (relationTypes.isEmpty()) {

			throw new TransportException(
					"No relation types supplied. At least one relation type must be supplied");
		}

		// each relation type must contain at least one relation
		Iterator<RelationType> iter = relationTypes.keySet().iterator();

		while (iter.hasNext()) {
			Collection<Relation> relations = relationTypes.get(iter.next());

			if (relations.isEmpty()) {

				throw new TransportException(
						"Empty relation type. Thre must be at least one relation of each relation type");
			}
		}
		if (myModel.getRootNode() == null) {
			myModel.setRootNode(myModel.getNode(myRootNodeID));
		}
	}

	/**
	 * @return The backend which was generated during the transformation process
	 */
	public MBModel getModel() {
		return myModel;
	}
}