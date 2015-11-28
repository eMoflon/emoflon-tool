package de.fhg.iao.matrixbrowser.context;

import java.util.Collection;

import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.matrixbrowser.model.ModelChangedEvent;
import de.fhg.iao.matrixbrowser.model.ModelChangedListener;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class AbstractTreeSynthesizer implements ModelChangedListener {

	Logger log = Logger.getLogger(AbstractTreeSynthesizer.class);
	/**
	 * flag to indicate whether TreeSynthesizer is used in horizontal or
	 * vertical TreeView.
	 */
	public static final int HORIZONTAL = 0;

	public static final int VERTICAL = 1;

	private AbstractTreeSynthesizer myParent = null;

	/**
	 * type of TreeSynthesizer, i.e. whether it is used in horizontal or
	 * vertical TreeView.
	 */
	private int type = 0;

	/**
	 * Holds a list of TreeModelChangedEvent listeners of this
	 * <code>TreeSynthesizer</code>.
	 */
	protected EventListenerList myTreeModelChangedListenerList = new EventListenerList();

	/**
	 * Returns all children (regarding to the actual TreeSpaningRelations) of
	 * the given node.
	 * 
	 * @param node
	 *            the node of which the children will be returned <br>
	 * @return A Vector with all nodes which are childs of tNode. An empty
	 *         Vector <b>(and not null)</b> if tNode has no children. <br>
	 */
	public abstract Collection<Node> getChildren(final Node node);

	/**
	 * Returns all relations regarding one node except the
	 * treeSpanningRelations.
	 * 
	 * @param node
	 *            the node the relations will be searched for
	 * @return A Set containing all found relations
	 */
	public abstract Collection<Relation> getRelations(Node node);

	/**
	 * The TreeManager this TreeSynthesizer belongs to.
	 */
	protected TreeManager myTreeManager = null;

	/**
	 * returns the name of the synthesizer.
	 * 
	 * @return the name of this Synthesizer
	 */
	public abstract String getSynthesizerName();

	/**
	 * Set a new root node. This causes a new TreeModel to be generated which
	 * may take some time
	 * 
	 * @param node
	 *            The Node taken as new root
	 */
	public abstract void setRootNode(Node node);

	/**
	 * @return
	 */
	public abstract VisibleNode getVisibleTree();

	/**
	 * Returns the number of VisibleNodes in the tree.
	 */
	public abstract int getVisibleNodeCount();

	/** flag to see if synthesizer is enabled or not. */
	private boolean enabled = true;

	/**
	 * returns true if this synthesizer is enabled, false otherwise.
	 * 
	 * @return enabled the enabled state
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled attribute of this <code>TreeSynthesizer</code> to the
	 * given boolean.
	 * 
	 * @param enabled
	 *            the enabled state to set
	 */
	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Adds a <code>TreeModelChangedListener</code> that will be notified about
	 * changes in the model.
	 * 
	 * @param aListener
	 *            TreeModelChangedListener
	 */
	public void addTreeModelChangedListener(
			final TreeModelChangedListener aListener) {
		myTreeModelChangedListenerList.add(TreeModelChangedListener.class,
				aListener);
	}

	/**
	 * Removes a <code>TreeModelChangedListener</code>.
	 * 
	 * @param aListener
	 *            TreeModelChangedListener
	 */
	public void removeTreeModelChangedListener(
			final TreeModelChangedListener aListener) {
		myTreeModelChangedListenerList.remove(TreeModelChangedListener.class,
				aListener);
	}

	public abstract void onModelChanged(final ModelChangedEvent aEvent);

	/**
	 * Fires a <code>TreeModelChangedEvent</code> by calling
	 * 'onTreeModelChanged' at the listener.
	 * 
	 * @param e
	 *            the <code>TreeModelChangedEvent</code> to fire
	 */
	protected void fireTreeModelChangedEvent(final TreeModelChangedEvent e) {
		Object[] listeners = myTreeModelChangedListenerList.getListenerList();
		log.debug("the AbstractTreeSynthesizer fired a "
				+ "TreeModelChangedEvent!");
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelChangedListener.class) {
				((TreeModelChangedListener) listeners[i + 1])
						.onTreeModelChanged(e);
			}
		}
	}

	/**
	 * Sets a new TreeManager. This also removes the old ModelChangedListener
	 * and installs a new one for the new TreeManager.
	 * 
	 * @param manager
	 *            the treemanager that will be set
	 */
	public void setTreeManager(final TreeManager manager) {
		if (myTreeManager != null) {
			myTreeManager.removeModelChangedListener(this);
		}
		this.myTreeManager = manager;
		myTreeManager.addModelChangedListener(this);
	}

	/**
	 * @return the TreeManager
	 */
	public TreeManager getTreeManager() {
		return myTreeManager;
	}

	/**
	 * Constructor for the AbstractTreeSynthesizer.
	 */
	protected AbstractTreeSynthesizer() {
		super();
	}

	/**
	 * @return Returns the type.
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(final int type) {
		this.type = type;
	}

	/**
	 * @return Returns the myParent.
	 */
	protected AbstractTreeSynthesizer getParent() {
		return myParent;
	}

	/**
	 * @param myParent
	 *            The myParent to set.
	 */
	protected void setParent(final AbstractTreeSynthesizer myParent) {
		this.myParent = myParent;
	}
}