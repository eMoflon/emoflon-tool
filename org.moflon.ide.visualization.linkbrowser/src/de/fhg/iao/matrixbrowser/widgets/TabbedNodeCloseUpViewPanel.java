package de.fhg.iao.matrixbrowser.widgets;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.NodeCloseUpView;
import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.matrixbrowser.VisibleNodeEventListener;

public class TabbedNodeCloseUpViewPanel extends JTabbedPane implements
		NodeCloseUpView {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	private DefaultNodeCloseUpViewPanel _nodeCloseUpView;
	private NodeCloseUpViewCirclePanel _nodeCloseUpCircleView;

	// private NodeCloseUpViewProperties _nodeCloseUpViewProperties;
	public TabbedNodeCloseUpViewPanel(MatrixBrowserPanel aMatrixBrowserPanel,
			int width, int height) {
		_nodeCloseUpView = new DefaultNodeCloseUpViewPanel(aMatrixBrowserPanel);
		_nodeCloseUpCircleView = new NodeCloseUpViewCirclePanel(
				aMatrixBrowserPanel);
		// _nodeCloseUpViewProperties = new
		// NodeCloseUpViewProperties(aMatrixBrowserPanel);
		((JComponent) _nodeCloseUpView).setMinimumSize(new Dimension(width,
				height));
		((JComponent) _nodeCloseUpCircleView).setMinimumSize(new Dimension(
				width, height));
		// ((JComponent) _nodeCloseUpViewProperties).setMinimumSize(new
		// Dimension(width, height));
		this.addTab("Close Up View", _nodeCloseUpView);
		this.addTab("CircleView", _nodeCloseUpCircleView);
		// this.addTab("Properites", _nodeCloseUpViewProperties);
	}

	public void addVisibleNodeEventListener(
			VisibleNodeEventListener aVisibleNodeEventListener) {
		_nodeCloseUpView.addVisibleNodeEventListener(aVisibleNodeEventListener);
		_nodeCloseUpCircleView
				.addVisibleNodeEventListener(aVisibleNodeEventListener);
		// _nodeCloseUpViewProperties.addVisibleNodeEventListener(aVisibleNodeEventListener);
	}

	public void removeVisibleNodeEventListener(
			VisibleNodeEventListener aVisibleNodeEventListener) {
		_nodeCloseUpView
				.removeVisibleNodeEventListener(aVisibleNodeEventListener);
		_nodeCloseUpCircleView
				.removeVisibleNodeEventListener(aVisibleNodeEventListener);
		// _nodeCloseUpViewProperties.addVisibleNodeEventListener(aVisibleNodeEventListener);
	}

	public void setNode(VisibleNode aNode) {
		_nodeCloseUpView.setNode(aNode);
		_nodeCloseUpCircleView.setNode(aNode);
		// _nodeCloseUpViewProperties.setNode(aNode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fhg.iao.matrixbrowser.NodeCloseUpView#getNode()
	 * 
	 * @deprecated
	 */
	public VisibleNode getNode() {
		return _nodeCloseUpView.myDetailNode;
	}

}
