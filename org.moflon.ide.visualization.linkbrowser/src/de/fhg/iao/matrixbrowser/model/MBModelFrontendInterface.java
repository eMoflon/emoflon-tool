/** 
 * Created on Aug 4, 2008
 */
package de.fhg.iao.matrixbrowser.model;

import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;

/**
 * The interface to be implemented by Frontends for external programs that can
 * change the model. The defined methods exist to change the model and cause the
 * View to be changed accordingly.
 * 
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.2 $
 */
public interface MBModelFrontendInterface {
	/**
	 * Adds a Node to the MBModel. If AutoAddTreeRelation is set, this adds a
	 * new TreeRelation from the Model's topmost root-Node to the newly added
	 * Node. This TreeRelation is defined in the <code>autoAddRelation</code>.
	 * If <code>autoAddTreeRelation</code> is not set, the View will only be
	 * updated when the <code>MBModel</code> is known to be consistent, meaning
	 * as soon as a TreeSpanningRelation is added for this node. To be able to
	 * save processing resources, the view will not update if
	 * <code>AutoUpdate</code> is set to false, even if the model to show is
	 * known to be in a consistent state. Updating the view can be forced, by
	 * calling update().
	 * 
	 * @param node
	 *            the <code>Node</code> to add.
	 */
	void addNode(final Node node);

	/**
	 * Adds a Node to the model, given its Parent Node. This function adds the
	 * given Node as a child node of the given parent Node. A default Tree
	 * Relation to connect the Parent and the child is automatically added to
	 * the Model, and returned for further processing, if needed.
	 * 
	 * @param parentNode
	 *            The parent to the new node
	 * @param newNode
	 *            The new node to be created as a child node
	 * @return The automatically added default Relation between the given Parent
	 *         and the newly created child Node.
	 */
	Relation addChildNode(final Node parentNode, final Node newNode);

	/**
	 * Adds a node to the Model, as Parent of a given child node. This method
	 * will add a new parent node to the given child, taking its place in the
	 * hierarchy and connecting the newly created node to it. The Relation that
	 * had connected the child to its former parent will now connect the newly
	 * created node to the new parent, and a new relation connecting the former
	 * Parent and the new parent will be created automatically.
	 * <code>AutoAddRelation</code> defines the newly created Relation. Both
	 * relations are returned, in an Array of Relation, from top to bottom,
	 * meaning the first relation returned is the relation connecting the newly
	 * created node to its parent and the second is the relation connecting the
	 * newly created node to the child.
	 * 
	 * @param newNode
	 *            the Node to create
	 * @param parentNode
	 *            the parent node of the node to be created.
	 * @return the two relations connecting the newly created Node.
	 */
	Relation[] addParentNode(final Node newNode, final Node parentNode);

	/**
	 * Adds a Relation to the Model. The Relation should contain the Start and
	 * Destination Node ID of the Relation to be created.
	 * 
	 * @param relation
	 *            the relation to add.
	 */
	void addRelation(final Relation relation);

	/**
	 * Updates the View of the model. This method should be called when the
	 * Model should be displayed in the LinkBrowser, especially if Auto-Updating
	 * the View is inactive.
	 * */
	void update();

	/**
	 * Sets AutoUpdate to true. The View will be automatically updated whenever
	 * a change in the model occurs and the Model is in a consistent state.
	 */
	void setAutoUpdate();

	/**
	 * Sets AutoUpdate to false. The View will not be updated automatically when
	 * changes to the Model occur. To update the view, call update().
	 */
	void unsetAutoUpdate();

	/**
	 * Set AutoUpdate to the specified Value. If set to true, this causes the
	 * View to be updated whenever the Model is changed, as soon as the Model is
	 * in a consistent state. If set to false, the View will not be updated
	 * until update() is called.
	 * 
	 * @param autoUpdate
	 *            the boolean to set AutoUpdate to.
	 */
	void setAutoUpdate(boolean autoUpdate);
}
