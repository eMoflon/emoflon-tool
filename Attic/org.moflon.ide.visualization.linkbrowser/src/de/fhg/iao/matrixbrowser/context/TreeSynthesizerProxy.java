/*
 * Created on 09.03.2004 To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.fhg.iao.matrixbrowser.context;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.fhg.iao.matrixbrowser.EncapsulatingVisibleNode;
import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.matrixbrowser.model.elements.MBModelNode;
import de.fhg.iao.matrixbrowser.model.elements.Node;

/**
 * @author roehrich To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TreeSynthesizerProxy extends DefaultTreeSynthesizer implements
		TreeModelChangedListener {
	protected Node myRootNode = new MBModelNode("tmp");

	protected List<AbstractTreeSynthesizer> mySubTreeSynthesizers = new LinkedList<>();

	@Override
	public VisibleNode getVisibleTree() {
		EncapsulatingVisibleNode tmpNode = new EncapsulatingVisibleNode(
				myRootNode);
		Iterator<AbstractTreeSynthesizer> iter = mySubTreeSynthesizers.iterator();

		while (iter.hasNext()) {
			AbstractTreeSynthesizer ts = iter.next();
			// only if ts is enabled add its visibletree to tmpNode
			if (ts.isEnabled())
				tmpNode.add(ts.getVisibleTree());
		}

		return tmpNode;
	}

	/**
	 * Sets a new TreeSynthesizer which is used inside this proxy. Deletes the
	 * old ones even if there were more than one.
	 * 
	 * @param aTreeSynthesizer
	 *            The new TreeSysnthesizer used inside
	 */
	public void setSubTreeSynthesizer(final AbstractTreeSynthesizer aTreeSynthesizer) {
		removeAllSubTreeSynthesizers();
		aTreeSynthesizer.setParent(this);
		addSubTreeSynthesizer(aTreeSynthesizer);
	}

	public void addSubTreeSynthesizer(final AbstractTreeSynthesizer aTreeSynthesizer) {
		aTreeSynthesizer.setParent(this);
		mySubTreeSynthesizers.add(aTreeSynthesizer);
		aTreeSynthesizer.addTreeModelChangedListener(this);
	}

	public void removeAllSubTreeSynthesizers() {
		mySubTreeSynthesizers.clear();
	}

	@Override
	public int getVisibleNodeCount() {
		int count = 1;
		Iterator<AbstractTreeSynthesizer> iter = mySubTreeSynthesizers.iterator();

		while (iter.hasNext()) {
			count += iter.next()
					.getVisibleNodeCount();
		}

		return count;
	}

	protected TreeSynthesizerProxy() {
		super();
	}

	@Override
	public void onTreeModelChanged(final TreeModelChangedEvent aE) {
		fireTreeModelChangedEvent(aE);
	}
}