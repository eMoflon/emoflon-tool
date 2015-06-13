/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 *     $Id: Action.java,v 1.3 2010-03-17 13:04:38 smueller Exp $
 * 
 * Created on Aug 3, 2008
 */
package de.fhg.iao.matrixbrowser.controller;

import java.awt.event.ActionEvent;
import java.util.EventListener;

import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * This class defines all the actions possible with a MatrixBrowser.
 * 
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.3 $
 */
@Deprecated
public abstract class Action {
	/**
	 * Add a Node. This is the Action to perform for adding a given node to the
	 * Model.
	 * 
	 * @param node
	 *            the <code>Node</code> to add.
	 */
	public abstract void addNode(final Node node, final MBModel model);

	public abstract Action deleteNode(Node node);

	public abstract Action deleteNode(ID nodeID);

	public abstract Action addRelation(Relation relation);

	public abstract Action addRelationType(RelationType relationType);

	public abstract Action addEventListener(EventListener listener);

	public abstract Action removeEventListener(EventListener listener);

	public abstract Action onActionPerformed(ActionEvent e);
}
