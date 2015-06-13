/*
 * Created on 14.08.2003 To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package de.fhg.iao.matrixbrowser.model;

import java.util.EventObject;

import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ModelChangedEvent extends EventObject {

	/**
	 * Comment for <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to the new Model.
	 */
	private MBModel myNewModel;

	/**
	 * The changed model.
	 */
	private MBModel myChangedModel;

	/**
	 * The <code>Node</code> on which the model Change occurred.
	 */
	private Node myNode;

	/**
	 * The <code>Relation</code> on which the model change occured.
	 */
	private Relation myRelation;

	/**
	 * The boolean defining whether the whole model has changed or just a small
	 * Change to just a Node or a Relation occurred.
	 */
	private boolean myModelChanged = false;

	/**
	 * Constructor for a <code>ModelChangedEvent</code> defining the Source
	 * Object.
	 * 
	 * @param source
	 *            the Object which caused the Event.
	 */
	public ModelChangedEvent(final Object source) {
		super(source);
	}

	/**
	 * Constructor of a <code>ModelChangedEvent</code> in the case that a
	 * <code>Relation</code> was added or removed.
	 * 
	 * @param aSource
	 *            the source instance, which created the event
	 * @param aMBModel
	 *            the model
	 * @param aRelation
	 *            the new or deleted relation
	 */
	public ModelChangedEvent(final Object aSource, final MBModel aMBModel,
			final Relation aRelation) {
		super(aSource);
		myChangedModel = aMBModel;
		this.myRelation = aRelation;
	}

	/**
	 * Constructor of a <code>ModelChangedEvent</code> in the case that a
	 * <code>Node</code> was added or removed.
	 * 
	 * @param aSource
	 *            the source instance, which created the event
	 * @param aMBModel
	 *            the model
	 * @param aNode
	 *            the new or deleted node
	 */
	public ModelChangedEvent(final Object aSource, final MBModel aMBModel,
			final Node aNode) {
		super(aSource);
		myChangedModel = aMBModel;
		this.myNode = aNode;
	}

	/**
	 * Constructor of a <code>ModelChangedEvent</code> in the case that the
	 * complete model changed.
	 * 
	 * @param aSource
	 *            the source instance, which created the event
	 * @param aChangedModel
	 *            the old model
	 * @param aNewModel
	 *            the new model
	 */
	public ModelChangedEvent(final Object aSource, final MBModel aChangedModel,
			final MBModel aNewModel) {
		super(aSource);
		myChangedModel = aChangedModel;
		myNewModel = aNewModel;
		myModelChanged = true;
	}

	/**
	 * Tells whether this Event is caused by a changed Node.
	 * 
	 * @return a boolean indicating whether a Node has changed.
	 */
	public final boolean isNodeChanged() {
		return myNode != null;
	}

	/**
	 * Tells whether this event has been caused by a changed Relation.
	 * 
	 * @return a <code>boolean</code> indicating whether a Relation has changed.
	 */
	public final boolean isRelationChanged() {
		return myRelation != null;
	}

	/**
	 * Gets the changed model.
	 * 
	 * @return the <code>AbstractTreeSynthesizer</code>, which has been
	 *         (ex)changed
	 */
	public final MBModel getChangedModel() {
		return myChangedModel;
	}

	/**
	 * Gets the new Model.
	 * 
	 * @return the new <code>AbstractTreeSynthesizer</code>
	 */
	public final MBModel getNewModel() {
		return myNewModel;
	}

	/**
	 * Returns the Node on which the event occurred.
	 * 
	 * @return the <code>Node</code> on which the event occurred.
	 */
	public final Node getNode() {
		return myNode;
	}

	/**
	 * @return true, if the whole model was exchanged
	 */
	public final boolean isModelChanged() {
		return myModelChanged;
	}

	/**
	 * Gets the Relation the Event has been initialized with.
	 * 
	 * @return the Relation on which the event occured.
	 */
	public final Relation getRelation() {
		return myRelation;
	}
}
