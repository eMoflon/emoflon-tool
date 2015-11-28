/*
 * Created on 13.08.2003 To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package de.fhg.iao.matrixbrowser.model.elements;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collection;

import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * The basic Element of the Model holding the variables to be stored and shown
 * in the Matrixbrowser.
 * 
 * @TODO schotti: think about whether this should implement TreeNode...
 * @author roehrich
 * @author Christian Schott
 * @version $Revision: 1.5 $
 */
public class MBModelNode extends UniqueMBModelElement implements Node,
		Serializable {
	/**
	 * schotti: is now defined in the superclass.
	 * 
	 * Description of this <code>Node</code> to be shown in the tooltip. This
	 * description String is shown once this node is transformed to a
	 * <code>VisibleNode</code> in the Matrixbrowser. As long as it is not set,
	 * getters for this property will return the Node's name.
	 */
	// private String description;
	/** default <code>serialVersionUID</code>. */
	private static final long serialVersionUID = 1L;

	/**
	 * schotti: Is now defined in the superclass.
	 * 
	 * The name of the Node. Once this Node is displayed, this
	 * <code>description</code> should appear in its graphical representation.
	 */
	// private String name; // the Name of the Node
	/**
	 * This holds <code>userData</code>, as the TreeNode has this field and we
	 * want to stay consistent with that.
	 */
	private byte[] userData;

	/**
	 * The <code>weight</code> of the node.
	 * 
	 * @deprecated schotti: I can't yet figure out whether this is needed.
	 */
	@Deprecated
	protected float weight = 0;

	/**
	 * Representing whether the node is a new node. Set when this node is added
	 * to the model. Once it is included in the model, it is reset to false. for
	 * debugging purposes. Might get removed.
	 * 
	 * @deprecated by schotti to see where it's used.
	 */
	@Deprecated
	private boolean iAmANewNode = false;

	/**
	 * schotti : is now a method of the superclass.
	 * 
	 * Holds the Context Menu of this node. The <code>contextMenu</code> makes
	 * some functions available handling this node. They are shown for the
	 * corresponding {@link de.fhg.iao.matrixbrowser. VisibleNode VisibleNode}
	 * as soon as this node is transformed to such a <code>VisibleNode</code>
	 * The reason why we define the contextMenu in here is that we want it to be
	 * saved during model export.
	 */
	// private Collection contextMenu;
	/**
	 * schotti : Is now defined in the superclass.
	 * 
	 * The color of this node. In this node's graphical representation it should
	 * be appear in this color.
	 * 
	 * Now a parameter of the superclass, UniqueMBModelElement
	 */
	// private Color color = null;
	/**
	 * Null constructor.
	 */
	public MBModelNode() {
		super(); // assigns an ID
	}

	/**
	 * Sets this node's state to reflect that it is a recently added node that
	 * is yet to be included in the model.
	 * 
	 * @deprecated schotti: finally, I think this is not needed.
	 */
	@Deprecated
	public final void setNew() {
		this.iAmANewNode = true;
	}

	/**
	 * Once this node is included in the model, unsetNew is called to know the
	 * node is included in the model.
	 * 
	 * @deprecated again by schotti: finally I think this is not needed.
	 */
	@Deprecated
	public final void unsetNew() {
		this.iAmANewNode = false;
	}

	/**
	 * Tells whether the node is a newly added one not included in the model
	 * yet.
	 * 
	 * @return the boolean representing the 'new' state of the node
	 * @deprecated again by schotti: finally I don't think this is needed.
	 */
	@Deprecated
	public final boolean isNew() {
		return this.iAmANewNode;
	}

	/**
	 * Constructor for a Node having a name and a description.
	 * 
	 * @param name
	 *            The name of the node
	 * @param description
	 *            A description of the node shown in the tooltip
	 */
	public MBModelNode(final String name, final String description) {
		super(description);
		super.setName(name);
	}

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
	/*
	 * public String getDescription() { if (description != null){ return
	 * description; }else{ return name; } }
	 */
	/**
	 * schotti: Is now defined in the superclass.
	 * 
	 * Adds a context menu to the node
	 * 
	 * @param contextMenu
	 *            A collection of items shown in the context menu
	 */
	/*
	 * public void setContextMenu( Collection contextMenu ) { this.contextMenu =
	 * contextMenu; }/ / schotti: Is now defined in the superclass Returns the
	 * context menu of the node
	 * 
	 * @return a Collection containing the context menu entries
	 */
	/*
	 * public Collection getContextMenu() { return getContextMenu(); }
	 */

	/**
	 * sets the background Colour of the node This is just another name for the
	 * color setter. Calls setColor in the superclass.
	 * 
	 * @param color
	 *            the BackgroundColor to set
	 * @deprecated use the superclass's method <code>setColor</code>, instead.
	 */
	public final void setNodeBGColor(final Color color) {
		this.setColor(color);
	}

	/**
	 * Queries the background colour of the node This is just another name for
	 * the color getter method.
	 * 
	 * @return the node's background color
	 * @deprecated use the superclass's method <code>getColor</code>, instead.
	 */
	public final Color getNodeBGColor() {
		return getColor();
	}

	/**
	 * Constructor for a Node having only a Name. The Node's description is set
	 * to the Name, also.
	 * 
	 * @param name
	 *            a string representation of the node
	 */
	public MBModelNode(final String name) {
		super(); // assigns an ID
		super.setName(name);
	}

	/**
	 * Creates an empty Node just having an ID.
	 * 
	 * @param id
	 *            the ID for the newly created Node
	 */
	public MBModelNode(final ID id) {
		super(id);
	}

	/**
	 * Constructor for a Node having a name and an ID Sets the description of
	 * the node to the name, also.
	 * 
	 * @param name
	 *            a String representing the node
	 * @param id
	 *            a unique identifier for the node
	 */
	public MBModelNode(final String name, final ID id) {
		super(id);
		super.setName(name);
	}

	/**
	 * Gets this node's <code>Relations</code>.
	 * 
	 * @return A Set of all the node's relations
	 */
	public final Collection<Relation> getMyRelations() {
		return this.getModel().getRelations((Node) this);
	}

	/**
	 * Creates a <code>TransferableNode</code> out of this <code>node</code>.
	 * 
	 * @return the <code>TranseferableNode</code> created from this node.
	 */
	public final TransferableNode createTransferableNode() {
		return new TransferableNode(this.getID(), this.getName());
	}

	/**
	 * Sets the <code>UserData</code> parameter.
	 * 
	 * @see TreeNode#
	 * @param object
	 *            the Object to set the UserData to.
	 */
	public final void setUserData(final byte[] object) {
		this.userData = object;
	}

	/**
	 * Gets the <code>UserData</code> parameter.
	 * 
	 * @return the UserData
	 */
	public final byte[] getUserData() {
		return userData;
	}
}
