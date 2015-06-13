/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 *     $Id: SimpleModelConsistencyChecker.java,v 1.1 2005/04/11 16:36:51 
 *     koenigs Exp $
 * 
 * Created on 14.01.2005
 */
package de.fhg.iao.matrixbrowser.model;

import java.util.Collection;
import java.util.Iterator;

import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;

/**
 * Checks a MBModel against some consistency constraints. They are:
 * <ul>
 * <li>Does the model contain a node with the rootNodeID?</li>
 * <li>Does the model contain at least one RelationType is part of the
 * treespanning RelationTypes?</li>
 * <li>Is there a Relation of a treespanning RelationType that connects at least
 * the rootNode with one other node?</li>
 * </ul>
 * 
 * <code>    
 * MBModel model = new DefaultMBModel();<br/>
 * ... some model population code ...<br/>
 * AbstractModelConsistencyChecker checker = new SimpleModelConsistencyChecker(
 * model);<br/><br/>
 * if (!checker.check()) {<br/>
 *   log.debug("The model's consistency check failed. No export 
 *   possible.");<br/> }<br/>
 * </code>
 * 
 * @author <a href=mailto:jan.roehrich@iao.fraunhofer.de>Jan Roehrich</a>
 * @version $Revision: 1.5 $
 * 
 *          This consistency checker can be applied like this:
 */
public class SimpleModelConsistencyChecker extends
		AbstractModelConsistencyChecker {

	/**
	 * @param model
	 *            The model which will be checked
	 */
	public SimpleModelConsistencyChecker(final MBModel model) {
		super(model);
	}

	/**
	 * Checks against the consistency constraints listed above.
	 * 
	 * @return true if the constraints are fulfilled, false if not.
	 * @see de.fhg.iao.matrixbrowser.model.AbstractModelConsistencyChecker#check()
	 */
	public final boolean check() {
		MBModel model = getModel();

		// Does the model contain a node with the rootNodeID?
		Node rootNode = model.getNode(model.getRootNode().getID());
		if (rootNode == null) {
			return false;
		}

		/*
		 * Does the model contain at least one RelationType is part of the
		 * treespanning RelationTypes?
		 */
		Collection<RelationType> treeSpanningRelationTypes = model
				.getTreeSpanningRelationTypes();
		if (treeSpanningRelationTypes == null) {
			return false;
		}
		if (treeSpanningRelationTypes.size() < 1) {
			return false;
		}

		/*
		 * Is there a Relation of a treespanning RelationType that connects at
		 * least the rootNode with one other node?
		 */
		boolean foundRelation = false;
		Iterator<RelationType> typeIter = treeSpanningRelationTypes.iterator();
		while (typeIter.hasNext()) {
			RelationType type = (RelationType) typeIter.next();
			if (model.getRelationsByType(rootNode, type).size() > 0) {
				foundRelation = true;
			}
		}
		return foundRelation;
	}
}
