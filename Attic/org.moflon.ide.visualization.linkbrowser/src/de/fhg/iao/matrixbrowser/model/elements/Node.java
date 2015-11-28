/*
 * Created on 13.08.2003 To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package de.fhg.iao.matrixbrowser.model.elements;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collection;

import de.fhg.iao.matrixbrowser.model.ICommand;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * The basic Element of the Model holding the variables to be stored and shown
 * in the Matrixbrowser.
 * 
 * @TODO schotti: think about whether this should implement TreeNode...
 * @author roehrich
 * @author Christian Schott
 * @version $Revision: 1.6 $
 */
public interface Node extends UniqueMBModelElementInterface, Serializable {
	/**
	 * Sets this node's state to reflect that it is a recently added node that
	 * is yet to be included in the model. for debugging purposes. Might get
	 * removed.
	 * 
	 * @deprecated schotti: finally, I think this is not needed.
	 */
	@Deprecated
	void setNew();

	/**
	 * Once this node is included in the model, unsetNew is called to know the
	 * node is included in the model. for debugging purposes. Might get removed.
	 * 
	 * @deprecated again by schotti: finally I think this is not needed.
	 */
	@Deprecated
	void unsetNew();

	/**
	 * Tells whether the node is a newly added one not included in the model
	 * yet. for debugging purposes. Might get removed.
	 * 
	 * @return the boolean representing the 'new' state of the node
	 * @deprecated again by schotti: finally I don't think this is needed.
	 */
	@Deprecated
	boolean isNew();

	/**
	 * 
	 * schotti : is now defined in the superclass
	 * 
	 * Function to query this node's description. If the description is not yet
	 * set, the method returns the node's name.
	 * 
	 * @return the description of the node usually shown in the tooltip, or its
	 *         name if no description is not set.
	 */
	String getDescription();

	/**
	 * schotti: Is now defined in the superclass.
	 * 
	 * Adds a context menu to the node
	 * 
	 * @param contextMenu
	 *            A collection of items shown in the context menu
	 */
	public void setContextMenu(Collection<ICommand> contextMenu);

	/**
	 * Returns the context menu of the node. schotti: Is now defined in the
	 * superclass
	 * 
	 * @return a Collection containing the context menu entries
	 * 
	 */
	public Collection<ICommand> getContextMenu();

	/**
	 * sets the background Colour of the node This is just another name for the
	 * color setter. Calls setColor in the superclass.
	 * 
	 * @param color
	 *            the BackgroundColor to set
	 * @deprecated use the superclass's method <code>setColor</code>, instead.
	 */
	void setNodeBGColor(final Color color);

	/**
	 * Queries the background colour of the node This is just another name for
	 * the color getter method.
	 * 
	 * @return the node's background color
	 * @deprecated use the superclass's method <code>getColor</code>, instead.
	 */
	Color getNodeBGColor();

	/**
	 * Gets this node's <code>Relations</code>.
	 * 
	 * @return A Set of all the node's relations
	 */
	Collection<Relation> getMyRelations();

	/**
	 * Creates a <code>TransferableNode</code> out of this <code>node</code>.
	 * 
	 * @return the <code>TranseferableNode</code> created from this node.
	 */
	TransferableNode createTransferableNode();

	/**
	 * Sets the <code>UserData</code> parameter.
	 * 
	 * @see TreeNode#
	 * @param object
	 *            the Object to set the UserData to.
	 */
	void setUserData(final byte[] object);

	/**
	 * Gets the <code>UserData</code> parameter.
	 * 
	 * @return the UserData
	 */
	byte[] getUserData();

	/**
	 * Sets the ID of this node.
	 * 
	 * @param id
	 *            the ID to set for this node.
	 */
	void setID(ID id);

	/**
	 * Gets the ID of this node.
	 * 
	 * @return this node's ID
	 */
	ID getID();
}
