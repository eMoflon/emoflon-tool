/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * TreeManager.java,v 1.9 2004/05/19 14:41:50 kookaburra Exp $ Created on
 * 28.11.2003
 */
package de.fhg.iao.matrixbrowser.context;

import javax.swing.event.EventListenerList;

import de.fhg.iao.matrixbrowser.model.MBModel;
import de.fhg.iao.matrixbrowser.model.ModelChangedEvent;
import de.fhg.iao.matrixbrowser.model.ModelChangedListener;

/**
 * The TreeManager manages the TreeSynthesizers of each frontend component.
 * Therefore it maintains a list of all TreeSynthesizers which maps to the
 * component using it. So each component is able to use it's own specialized
 * Synthesizer.
 * 
 * @author roehrich
 */
public class TreeManager implements ModelChangedListener {

	/** The model managed by this TreeManager. */
	private MBModel myModel = null;

	/**
	 * The TreeSynthesizer which is used for default. In the case that no
	 * component object is supplied.
	 */
	private AbstractTreeSynthesizer myDefaultTreeSynthesizer = null;

	/** The <code>myTreeCombinator</code> used by this TreeManager. */
	private TreeCombinator myTreeCombinator = null;

	/**
	 * This map maps pairs of TreeSynthesizers to their corresponding
	 * TreeCombinators
	 */
	private EventListenerList myModelChangedListeners = new EventListenerList();

	public TreeManager(MBModel model) {

		this.setModel(model);

		this.setDefaultTreeSynthesizer(new DefaultTreeSynthesizer(this));

		this.getDefaultTreeSynthesizer().setRootNode(myModel.getRootNode());

		myModel.addModelChangedListener(this);

	}

	/**
	 * @return The model this TreeManager is currently working on
	 */
	public MBModel getModel() {
		return myModel;
	}

	/**
	 * Sets the Model used by this TreeManager.
	 * 
	 * @param model
	 *            The model this TreeManager will work on
	 */
	public void setModel(MBModel model) {
		this.myModel = model;
	}

	/**
	 * Gets the AbstractTreeSynthesizer for this TreeManager.
	 * 
	 * @return AbstractTreeSynthesizer
	 */
	public AbstractTreeSynthesizer getDefaultTreeSynthesizer() {
		return myDefaultTreeSynthesizer;
	}

	/**
	 * @param AbstractTreeSynthesizer
	 *            The Synthesizer to set as new defaultTreeSynthesizer
	 */
	public void setDefaultTreeSynthesizer(AbstractTreeSynthesizer synthesizer) {
		myDefaultTreeSynthesizer = synthesizer;
	}

	/**
	 * @return a new <code>DefaultTreeSynthesizer</code>
	 */
	public DefaultTreeSynthesizer createDefaultTreeSynthesizer() {
		return new DefaultTreeSynthesizer(this);
	}

	public SortingTreeSynthesizer createSortingTreeSynthesizer() {
		return new SortingTreeSynthesizer(this);
	}

	public TreeSynthesizerProxy createTreeSynthesizerProxy() {
		return new TreeSynthesizerProxy();
	}

	public TreeProxyManager createTreeProxyManager() {
		return new TreeProxyManager(this);
	}

	/**
	 * creates a TreeCombinator for a given pair of TreeSynthesizers.
	 * 
	 * @param ts1
	 *            The first TreeSynthesizer
	 * @param ts2
	 *            The second TreeSynthesizer
	 * @return The corresponding TreeCombinator
	 */
	public TreeCombinator createTreeCombinator(
			AbstractTreeSynthesizer verticalTS,
			AbstractTreeSynthesizer horizontalTS) {
		TreeCombinator newCombinator = new TreeCombinator(verticalTS,
				horizontalTS, myModel);
		myTreeCombinator = newCombinator;
		return newCombinator;
	}

	public void onModelChanged(ModelChangedEvent aEvent) {
		fireModelChangedEvent(aEvent);
	}

	public void addModelChangedListener(ModelChangedListener aListener) {
		myModelChangedListeners.add(ModelChangedListener.class, aListener);
	}

	public void removeModelChangedListener(ModelChangedListener aListener) {
		myModelChangedListeners.remove(ModelChangedListener.class, aListener);
	}

	protected void fireModelChangedEvent(ModelChangedEvent event) {
		Object[] listeners = myModelChangedListeners.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ModelChangedListener.class) {
				((ModelChangedListener) listeners[i + 1]).onModelChanged(event);
			}
		}
	}

	/**
	 * @return Returns the myTreeCombinator.
	 */
	public TreeCombinator getTreeCombinator() {
		return myTreeCombinator;
	}
}

/**
 * @author roehrich
 */

class Pair {

	private Object myX1 = null;

	private Object myX2 = null;

	public Pair(Object x1, Object x2) {
		myX1 = x1;
		myX2 = x2;
	}

	/**
	 * A pair is equals another object only of the object is an instance of Pair
	 * and has the same content. The order doesn't matter.
	 */
	public boolean equals(Object aObj) {
		if (aObj instanceof Pair) {
			Pair aPair = (Pair) aObj;

			if ((myX1.equals(aPair.getX1())) && (myX2.equals(aPair.getX2()))) {
				return true;
			}

			if ((myX2.equals(aPair.getX1())) && (myX1.equals(aPair.getX2()))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @return
	 */
	public Object getX1() {
		return myX1;
	}

	/**
	 * @return
	 */
	public Object getX2() {
		return myX2;
	}

	/**
	 * @param aObject
	 */
	public void setX1(Object aObject) {
		myX1 = aObject;
	}

	/**
	 * @param aObject
	 */
	public void setX2(Object aObject) {
		myX2 = aObject;
	}

	public int hashCode() {
		return (myX1.hashCode() + myX2.hashCode()) % Integer.MAX_VALUE;
	}
}