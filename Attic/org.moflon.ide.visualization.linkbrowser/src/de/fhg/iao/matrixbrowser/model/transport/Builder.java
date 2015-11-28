/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * Builder.java,v 1.2 2004/04/07 13:52:33 roehrijn Exp $ Created on 11.11.2003
 */
package de.fhg.iao.matrixbrowser.model.transport;

import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * Import and export in the MatrixBrowser complies to the Builder design
 * pattern. The <code>Builder</code> interface is used by
 * <code>AbstractDirectors</code> to build the target context.
 * 
 * @author roehrich
 */
public interface Builder {

	/**
	 * Sets the given id as the root node ones.
	 * 
	 * @param id
	 *            The id of the root node
	 */
	public void setRootNodeID(ID id) throws TransportException;

	/**
	 * Adds the given node to the target context
	 * 
	 * @param node
	 *            The node which will be added
	 */
	public void addNode(Node node) throws TransportException;

	/**
	 * Adds the given relation to the target context. The affected nodes and the
	 * relation type of the relation must have allready been added.
	 * 
	 * @param relation
	 *            The relation which will be added
	 */
	public void addRelation(Relation relation) throws TransportException;

	/**
	 * Adds the given relation type to the target context.
	 * 
	 * @param type
	 *            The relation type which will be added <br>
	 * @param isTreeSpanningRelationType
	 *            True if relation type is part of the tree spanning relations
	 */
	public void addRelationType(RelationType type,
			boolean isTreeSpanningRelationType) throws TransportException;

	/**
	 * This method will be called at the start of the transformation process
	 * before any object is added to the context.
	 */
	public void initialize() throws TransportException;

	/**
	 * This method will be called at the end of the transformation process after
	 * all objects have been added.
	 */
	public void complete() throws TransportException;
}