/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 *     $Id: NodeCloseUpViewProperties.java,v 1.2 2009-03-11 12:31:38 srose Exp $
 * 
 * Created on Jul 7, 2008
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.NodeCloseUpView;
import de.fhg.iao.matrixbrowser.UIResourceRegistry;
import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.matrixbrowser.VisibleNodeEventListener;

/**
 * Shows the Properties of a given node in a form.
 * 
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.2 $
 */
public class NodeCloseUpViewProperties extends JPanel implements
		NodeCloseUpView {
	protected MatrixBrowserPanel myMatrixBrowserPanel;
	protected JLabel myNodeNameLabel = null;
	protected JTextField myNodeDescriptionTextField = null;

	public NodeCloseUpViewProperties(final MatrixBrowserPanel myMatrixBrowserPanel) {
		this.myMatrixBrowserPanel = myMatrixBrowserPanel;
		myNodeNameLabel = new JLabel("nothing to see here yet");
		myNodeDescriptionTextField = new JTextField();
		initialize();
	}

	private void initialize() {
		setUIProperties();
		layoutContent();
	}

	private void setUIProperties() {
		setBorder(new ResizeBorder(UIResourceRegistry.getInstance().getColor(
				UIResourceRegistry.getInstance().NODECLOSEUPVIEW_BORDERCOLOR)));

		if (UIResourceRegistry.getInstance().getColor(
				UIResourceRegistry.getInstance().NODECLOSEUPVIEW_BACKGROUND) != null) {
			setBackground(UIResourceRegistry
					.getInstance()
					.getColor(
							UIResourceRegistry.getInstance().NODECLOSEUPVIEW_BACKGROUND));

		} else {
			setOpaque(false);
		}

		setLayout(new BorderLayout());
		myNodeNameLabel.setText("DisplayedNode");
		// this.getHeight()/2-5);

	}

	private void layoutContent() {
		this.add(myNodeNameLabel);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.NodeCloseUpView#addVisibleNodeEventListener(
	 * de.fhg.iao.matrixbrowser.VisibleNodeEventListener)
	 */
	@Override
	public void addVisibleNodeEventListener(
			final VisibleNodeEventListener visibleNodeEventListener) {}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.NodeCloseUpView#removeVisibleNodeEventListener
	 * (de.fhg.iao.matrixbrowser.VisibleNodeEventListener)
	 */
	@Override
	public void removeVisibleNodeEventListener(
			final VisibleNodeEventListener visibleNodeEventListener) {}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.NodeCloseUpView#setNode(de.fhg.iao.matrixbrowser
	 * .VisibleNode)
	 */
	@Override
	public void setNode(final VisibleNode node) {
		myNodeNameLabel.setText(node.toString());

	}

	@Override
	public VisibleNode getNode() {
		return this.getNode();
	}


	private static final long serialVersionUID = 6639083294042700012L;
}
