/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * MBModelDirector.java,v 1.3 2004/04/07 13:52:33 roehrijn Exp $ Created on
 * 12.11.2003
 */
package de.fhg.iao.matrixbrowser.model.transport;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * @author roehrich
 */
public class MBModelDirector extends AbstractDirector {

	/**
	 * The model which will be transported
	 */
	MBModel myModel = null;

	/**
	 * Builds an empty MBModelDirector
	 */
	public MBModelDirector(final Builder builder) {
		super(builder);
	}

	/**
	 * Constructs a MBModelDirector and sets the model which will be exported
	 * 
	 * @param builder
	 *            The Builder object which will be used for transformation
	 * @param model
	 *            The mode which will be exported
	 */
	public MBModelDirector(final Builder builder, final MBModel model) {
		super(builder);
		this.setModel(model);
	}

	/**
	 * @see de.fhg.iao.matrixbrowser.model.transport.AbstractDirector#startTransport()
	 */
	@Override
	public void startTransport() throws TransportException {
		// initialize the transfer process
		myBuilder.initialize();

		// set root node id
		myBuilder.setRootNodeID(myModel.getRootNode().getID());

		// add all nodes
		Map<ID, Node> nodes = myModel.getNodes();
		Iterator<ID> nodesIter = nodes.keySet().iterator();

		while (nodesIter.hasNext()) {
			myBuilder.addNode(nodes.get(nodesIter.next()));
		}

		// add all relation types
		Map<RelationType, Collection<Relation>> relationTypes = myModel.getRelations();
		Iterator<RelationType> typeIter = relationTypes.keySet().iterator();

		while (typeIter.hasNext()) {
			RelationType type = typeIter.next();
			myBuilder.addRelationType(type, myModel
					.getTreeSpanningRelationTypes().contains(type));

			// add all relations of current type
			Collection<Relation> relations = relationTypes.get(type);
			Iterator<Relation> relationIter = relations.iterator();

			while (relationIter.hasNext()) {
				myBuilder.addRelation(relationIter.next());
			}
		}

		// finish the transfer process
		myBuilder.complete();
	}

	/**
	 * @return The model which will be exported during the transformation
	 *         process
	 */
	public MBModel getModel() {
		return myModel;
	}

	/**
	 * @param The
	 *            model which will be exported during the transformation process
	 */
	public void setModel(final MBModel model) {
		myModel = model;
	}
}