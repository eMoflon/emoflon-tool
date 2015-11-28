/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 *     $Id: AddNodeAction.java,v 1.4 2010-03-17 13:04:38 smueller Exp $
 * 
 * Created on Aug 4, 2008
 */
package de.fhg.iao.matrixbrowser.controller;

import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.matrixbrowser.model.elements.MBModelNode;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;

/** 
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.4 $
 */
@Deprecated
public class AddNodeAction {
	MBModel model;
	Node newNode;
	Relation relation;
	RelationType relationType;

	RelationType myTreeSpanningRelationType = new RelationType(
			"Automatically Added Tree Relation Type for new Node");

	public AddNodeAction() {
		Node node = new MBModelNode("New");
	}

	public void perform() {
		// Model is given by parameter
		// MBModel model = browserPanel.getTreeManager().getModel();
		// Node is given by parameter
		// Node newNode = new Node("hello");
		Relation newRelation = new Relation(model.getRootNode().getID(),
				newNode.getID(), relationType, RelationClass.REAL,
				RelationDirection.DIRECTED);
		model.addNode(newNode);
		model.addRelation(newRelation);
	}

}
