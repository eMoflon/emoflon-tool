/*
 * Created on 13.08.2003 To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package de.fhg.iao.matrixbrowser.model.elements;

import java.awt.Color;
import java.util.Collection;

import de.fhg.iao.matrixbrowser.model.ICommand;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 * @author schotti
 */
public class Relation extends UniqueMBModelElement {

   public final static int EMPTY_NODE_ID = 0;
   
	/**
	 * The <code>RelationType</code> of this relation. Relation Types are
	 * identifiers for relations classifying relations by adding a textual
	 * description of a RelationType to a Relation.
	 */
	private RelationType type;

	/**
	 * The ID of the Relation's <code>sourceNode</code>.
	 */
	private ID sourceNode;

	/**
	 * The ID of the Relation's <code>targetNode</code>.
	 */
	private ID targetNode;

	/**
	 * The Relation's <code>relClass</code>.
	 * 
	 * @see RelationClass
	 */
	// private int relClass;
	private RelationClass relClass;

	/**
	 * The relation's direction <code>direction</code> as defined in
	 * {@link RelationDirection}.
	 */
	private RelationDirection direction;

	/**
	 * The relation's <code>relDescritpion</code>.
	 * 
	 * @deprecated this is defined in the superclass as the description.
	 *             private, unused in this class, remove it.
	 */
	// private String relDescritpion = null;
	/**
	 * private, unused in this class, remove it. The relation's
	 * <code>relContextMenu</code>.
	 * 
	 * @deprecated this is defined in the superclass as the contextMenu
	 * 
	 */
	// private Collection relContextMenu = null;
	/**
	 * This relation's <code>color</code>.
	 * 
	 * @deprecated this is defined in the superclass as the color.
	 */
	private Color color = null;

	/**
	 * private, defined in superclass, not used here, remove it. A JComponent
	 * that can be rendered onto this Relation.
	 * 
	 * @deprecated this is defined in the superclass as the jComponent.
	 */
	// private JComponent component = null;
	/**
	 * Null constructor.
	 */
	public Relation() {
		super();
	}

	/**
	 * Constructor for a <code>Relation</code>. This Constructor sets Source,
	 * Target, RelationType, RelationClass and Direction to the given values,
	 * and assigns an ID to the newly created Relation.
	 * 
	 * @param aSource
	 *            The ID of the Source Node
	 * @param aTarget
	 *            The ID of the Target Node
	 * @param aType
	 *            The Type of the Relation to construct
	 * @param aRelationClass
	 *            The RelationClass of the Relation to be constructed.
	 * @param relationDirection
	 *            The Direction of the Relation {@link RelationClass}
	 */
	public Relation(final ID aSource, final ID aTarget,
			final RelationType aType, final RelationClass aRelationClass,
			final RelationDirection relationDirection) {
		super();
		this.type = aType;
		this.sourceNode = aSource;
		this.targetNode = aTarget;
		this.relClass = aRelationClass;
		this.direction = relationDirection;
		// this.setID( IDPool.getIDPool().getID() );
		// schotti: is now set by UniqueModelElement constructor.
	}

	/**
	 * Constructs a new <code>Relation</code> setting its RelationID to the
	 * given one.
	 * 
	 * This Constructor sets RelationID, Source, Target, RelationType,
	 * RelationClass and Direction to the given values.
	 * 
	 * @param aRelationID
	 *            The ID of the Relation
	 * @param aSourceNode
	 *            The ID of the Source Node
	 * @param aTargetNode
	 *            The ID of the Target Node
	 * @param aType
	 *            The Type of the Relation to construct
	 * @param aRelationClass
	 *            The RelationClass of the Relation to be constructed.
	 * @param aDirection
	 *            The Direction of the Relation {@link RelationClass}
	 */
	public Relation(final ID aRelationID, final ID aSourceNode,
			final ID aTargetNode, final RelationType aType,
			final RelationClass aRelationClass,
			final RelationDirection aDirection) {
		super(aRelationID);
		this.type = aType;
		this.sourceNode = aSourceNode;
		this.targetNode = aTargetNode;
		this.relClass = aRelationClass;
		this.direction = aDirection;
	}

	/**
	 * Constructor for a <code>Relation</code>. This Constructor sets Source,
	 * Target, RelationType, RelationClass and Direction to the given values,
	 * and assigns an ID to the newly created Relation.
	 * 
	 * @param aSourceNodeID
	 *            the ID of the <code>SourceNode</code>
	 * @param aTargetNodeID
	 *            the ID of the <code>TargetNode</code>
	 * @param aRelationType
	 *            the Type of the <code>Relation</code> to be constructed.
	 * @param aRelationClass
	 *            the <code>RelationClass</code> of the <code>Relation</code> to
	 *            be constructed.
	 * @param aDirection
	 *            the <code>RelationDirection</code> of the
	 *            <code>Relation</code> to be constructed.
	 * @param aDescription
	 *            the <code>Description</code> of the <code>Relation</code> to
	 *            be constructed.
	 */
	public Relation(final ID aSourceNodeID, final ID aTargetNodeID,
			final RelationType aRelationType,
			final RelationClass aRelationClass,
			final RelationDirection aDirection, final String aDescription) {
		super(aDescription); // assigns a generated ID, also
		this.type = aRelationType;
		this.sourceNode = aSourceNodeID;
		this.targetNode = aTargetNodeID;
		this.relClass = aRelationClass;
		this.direction = aDirection;
	}

	/**
	 * Gets the RelationDescription of this Relation.
	 * 
	 * @return this Relation's description
	 * @deprecated use the getDescription Method of the superclass.
	 */
	public final String getRelDescription() {
		return getDescription();
	}

	/**
	 * Sets the context menu of this Relation.
	 * 
	 * @param contextMenu
	 *            the ContexMenu to set
	 * @deprecated use the setContextMentu Method of the superclass.
	 */
	public final void setRelContextMenu(final Collection<ICommand> contextMenu) {
		setContextMenu(contextMenu);
	}

	/**
	 * Gets the context menu of this relation.
	 * 
	 * @return this Relation's context menu
	 * @deprecated use the getContextMenu of the superclass.
	 */
	public final Collection<ICommand> getRelContextMenu() {
		return getContextMenu();
	}

	/**
	 * Sets the color of this relation.
	 * 
	 * @param aColor
	 *            this relation's color
	 * @deprecated use the setColor method of the superclass.
	 */
	public final void setRelationColor(final Color aColor) {
		setColor(aColor);
	}

	/**
	 * Gets the color of this relation.
	 * 
	 * @return this relation's color
	 * @deprecated use the getColor method of the superclass.
	 */
	public final Color getRelationColor() {
		return color;
	}

	/*
	 * This has been moved up into the superclass UniqueMBModelElement. schotti
	 * 
	 * public void setJComponent( JComponent component ) { this.component =
	 * component;
	 * 
	 * }
	 */
	/*
	 * This has been moved up into the superclass UniqueMBModelElement. schotti
	 * 
	 * public JComponent getJComponent() { return component; }
	 */
	/**
	 * Gets the relation type of this Relation.
	 * 
	 * @return this relation's <code>RelationType</code>
	 */
	public final RelationType getRelationType() {
		return this.type;
	}

	/**
	 * Sets the given <code>RelationType</code> on this relation.
	 * 
	 * @param aRelationType
	 *            the type of this relation
	 */
	public final void setRelationType(final RelationType aRelationType) {
		this.type = aRelationType;
	}

	/**
	 * Gets the ID of this Relation's source node.
	 * 
	 * @return the relation's source node ID
	 */
	public final ID getSourceNodeID() {
		return this.sourceNode;
	}

	/**
	 * Gets the ID of this Relation's target node.
	 * 
	 * @return the relation's target node ID
	 */
	public final ID getTargetNodeID() {
		return this.targetNode;
	}

	/**
	 * Sets the ID of this Relation's source node.
	 * 
	 * @param aSourceNodeID
	 *            the relations source node ID
	 */
	public final void setSourceNodeID(final ID aSourceNodeID) {
		this.sourceNode = aSourceNodeID;
	}

	/**
	 * Sets the ID of this Relation's target node.
	 * 
	 * @param aTargetNodeID
	 *            the relation's target node ID
	 */
	public final void setTargetNodeID(final ID aTargetNodeID) {
		this.targetNode = aTargetNodeID;
	}

	/**
	 * Returns the direction of this relation. Directions are identified by an
	 * integer value defined in {@link RelationDirection}
	 * 
	 * @see RelationDirection
	 * @return the relation's direction
	 */
	public final RelationDirection getDirection() {
		return direction;
	}

	/**
	 * Returns the class of this relation. The Relation's class is identified by
	 * an integer value defined in {@link RelationClass}
	 * 
	 * @see RelationClass
	 * @return this relation's <code>RelationClass</code>
	 */
	public final RelationClass getRelationClass() {
		return relClass;
	}

	/**
	 * Sets the direction of this relation. The direction is identified by an
	 * integer value defined in {@link RelationDirection}
	 * 
	 * @see RelationDirection
	 * @param aDirection
	 *            the int describing this relation's direction
	 */
	public final void setDirection(final RelationDirection aDirection) {
		direction = aDirection;
	}

	/**
	 * Sets the RelationClass of this Relation. The relation class is identified
	 * by an integer value defined in {@link RelationClass}
	 * 
	 * @see RelationClass
	 * @param aRelationClass
	 *            the int describing this Relation's class
	 */
	public final void setRelationClass(final RelationClass aRelationClass) {
		relClass = aRelationClass;
	}

	/**
	 * Generates a Transferable Relation from this Relation.
	 * 
	 * @return the relation's <code>TransferableRelation</code>
	 */
	public final RelationTransferable createTransferableRelation() {
		RelationTransferable tRel = new RelationTransferable();
		tRel.setRelationID(this.getID());
		return tRel;
	}
}
