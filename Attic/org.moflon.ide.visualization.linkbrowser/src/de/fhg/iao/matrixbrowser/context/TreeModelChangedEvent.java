/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications.
 *  
 * Please read carefully the IAO_License.txt and/or contact the authors. 
 *   File : $Id: TreeModelChangedEvent.java,v 1.4 2009-03-11 12:31:38 srose Exp $
 *  
 *   Created on 28.11.2003
 */
package de.fhg.iao.matrixbrowser.context;

import java.util.EventObject;

import javax.swing.tree.TreeModel;

import de.fhg.iao.matrixbrowser.EncapsulatingVisibleNode;
import de.fhg.iao.matrixbrowser.VisibleNode;

/**
 * Event for notification about changes in the <code>TreeModel</code>s of the
 * trees.
 * 
 * @author kunz
 */
public class TreeModelChangedEvent extends EventObject {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private VisibleNode myChangedNode = null;

	private boolean myIsModelChanged = false;

	private boolean myIsNodeChanged = false;

	private TreeModel myNewModel = null;

	private TreeModel myOldModelModel = null;

	public TreeModelChangedEvent(Object source) {
		super(source);
	}

	/**
	 * Constructor of a <code>TreeModelChangedEvent</code> in the case that the
	 * complete model changed.
	 * 
	 * @param source
	 *            the source object, which created the event
	 * @param aOldModel
	 *            the old model
	 * @param aNewModel
	 *            the new model
	 */
	public TreeModelChangedEvent(Object source, TreeModel aOldModel,
			TreeModel aNewModel) {
		super(source);
		myOldModelModel = aOldModel;
		myNewModel = aNewModel;
		myIsModelChanged = true;
	}

	/**
	 * Constructor of a <code>TreeModelChangedEvent</code> in the case that the
	 * complete model changed.
	 * 
	 * @param source
	 *            the source object, which created the event
	 * @param aChangedNode
	 *            the node, under which nodes have changed
	 */
	public TreeModelChangedEvent(Object source,
			EncapsulatingVisibleNode aChangedNode) {
		super(source);
		myChangedNode = aChangedNode;
		myIsNodeChanged = true;
	}

	/**
	 * @return Returns the changedNode.
	 */
	public VisibleNode getChangedNode() {
		return myChangedNode;
	}

	/**
	 * @return the new <code>TreeModel</code>
	 */
	public TreeModel getNewModel() {
		return myNewModel;
	}

	/**
	 * @return the <code>TreeModel</code>, which has been (ex)changed by the new
	 *         model
	 * @see getNewModel
	 */
	public TreeModel getOldModel() {
		return myOldModelModel;
	}

	/**
	 * @return Returns the oldModelModel.
	 */
	public TreeModel getOldModelModel() {
		return myOldModelModel;
	}

	/**
	 * @return Returns the isModelChanged.
	 */
	public boolean isModelChanged() {
		return myIsModelChanged;
	}

	/**
	 * @return if nodes have changed in the model
	 */
	public boolean isNodeChanged() {
		return myIsNodeChanged;
	}

	/**
	 * @param aChangedNode
	 *            the node, under which nodes have changed
	 */
	public void setChangedNode(EncapsulatingVisibleNode aChangedNode) {
		myChangedNode = aChangedNode;
		myIsNodeChanged = true;
	}

	/**
	 * @param isModelChanged
	 *            The Boolean to set to whether the Model is changed or not.
	 */
	public void setModelChanged(boolean isModelChanged) {
		myIsModelChanged = isModelChanged;
	}

	/**
	 * @param newModel
	 *            The newModel to set.
	 */
	public void setNewModel(TreeModel newModel) {
		myNewModel = newModel;
	}

	/**
	 * @param isNodeChanged
	 *            The isNodeChanged to set.
	 */
	public void setNodeChanged(boolean isNodeChanged) {
		myIsNodeChanged = isNodeChanged;
	}

	/**
	 * @param oldModelModel
	 *            The oldModelModel to set.
	 */
	public void setOldModelModel(TreeModel oldModelModel) {
		myOldModelModel = oldModelModel;
	}
}