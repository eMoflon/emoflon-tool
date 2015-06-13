/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * NodeCloseUpViewCirclePanel.java,v 1.7 2004/04/13 12:36:35 kookaburra Exp $
 * Created on 18.03.02
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.fhg.iao.matrixbrowser.EncapsulatingVisibleNode;
import de.fhg.iao.matrixbrowser.HorizontalTreeView;
import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.NodeCloseUpView;
import de.fhg.iao.matrixbrowser.VerticalTreeView;
import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.matrixbrowser.VisibleNodeEvent;
import de.fhg.iao.matrixbrowser.VisibleNodeEventListener;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.Relation;

/**
 * Implementation for a NodeCloseUpView, showing directly neighboring nodes of
 * node around in a circle.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.6 $
 */
public class NodeCloseUpViewCirclePanel extends JPanel
		implements
			NodeCloseUpView,
			ActionListener {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/** Holds the actual center x coordinate */
	protected int myCenterX = 0;

	/** Holds the actual center y coordinate */
	protected int myCenterY = 0;

	/** Holds the actual radius coordinate */
	protected int myRadius = 0;

	/** Constant for the height of a label */
	protected final int labelHeight = 20;

	/** holds a list of IAONodeListeners */
	protected Vector<VisibleNodeEventListener> myNodeListeners = new Vector<VisibleNodeEventListener>();

	/** Holds the active (centered) node */
	protected VisibleNode myActiveNode = null;

	/** Holds a reference to the vertical tree */
	protected VerticalTreeView myVerticalHierachyView = null;

	/** Holds the horizontal tree */
	protected HorizontalTreeView myHorizontalHierachyView = null;

	/** Reference to the main <code>MatrixBrowserPanel</code> */
	protected MatrixBrowserPanel myMatrixBrowserPanel = null;

	/**
	 * Constructs a <code>TreeView</code> given the main
	 * <code>MatrixBrowserPanel</code>.
	 * 
	 * @param aMatrixBrowserPanel
	 *            reference to the <code>MatrixBrowserPanel</code>, which is
	 *            responsible for this <code>TreeView</code>
	 */
	public NodeCloseUpViewCirclePanel(final MatrixBrowserPanel aMatrixBrowserPanel) {
		super();
		myMatrixBrowserPanel = aMatrixBrowserPanel;
		setBackground(Color.white);
		setBorder(new ResizeBorder(Color.DARK_GRAY));
		setLayout(null);
		calculate();
	}

	/**
	 * Sets the Node for which to show the connections. It will be displayed as
	 * the middle node of the circle, with its connected nodes around it. This
	 * function is usually called by the <code>TreeView</code> or any other View
	 * where Nodes may be clicked on in the event of a click on that node.
	 * 
	 * @param aNode
	 *            the clicked node in an <code>TreeView</code>
	 */
	@Override
	public void setNode(VisibleNode aNode) {
		if (aNode != null) {
			myActiveNode = aNode;
			this.removeAll();
		} else if (getMatrixBrowserPanel().getTreeManager() != null) {
			myActiveNode = getMatrixBrowserPanel().getTreeManager()
					.getDefaultTreeSynthesizer().getVisibleTree();
			aNode = myActiveNode;
		}
		if (myActiveNode != null) {
			if (!(aNode instanceof EncapsulatingVisibleNode)) {
				Label notSupportedLabel = new Label("Currently not supported.");
				add(notSupportedLabel);
				return;
			}
			Node nestedNode = ((EncapsulatingVisibleNode) aNode)
					.getNestedNode();
			// NetViewRenderer aRenderer = new NetViewRenderer(aNode, null,
			// true);
			NetViewRenderer aRenderer = new NetViewRenderer(aNode, null);
			aRenderer.setLocation(myCenterX, myCenterY);
			add(createLabel(aRenderer, aNode.toString(), myCenterX, myCenterY
					+ labelHeight + 5, 0));
			aRenderer.addActionListener(this);

			add(aRenderer);

			Collection<Relation> relations = getMatrixBrowserPanel()
					.getTreeManager().getDefaultTreeSynthesizer()
					.getRelations(nestedNode);

			double deg = (2 * Math.PI) / relations.size();
			int i = 0;
			Iterator<Relation> it = relations.iterator();

			while (it.hasNext()) {
				Relation rel = it.next();
				Node destNode;

				if (nestedNode.getID() == rel.getSourceNodeID()) {
					// This node is Source -> use Target
					destNode = getMatrixBrowserPanel().getTreeManager()
							.getModel().getNode(rel.getTargetNodeID());
				} else {
					// This node is Target -> use Source
					destNode = getMatrixBrowserPanel().getTreeManager()
							.getModel().getNode(rel.getSourceNodeID());
				}

				// is key in a current TreeView visible ?
				// answer schotti: Not necessary as far as I can tell.
				// Listeners will have to open the tree at that point, if it was
				// for that.
				boolean isInVisibleTreeView = true;

				/*
				 * getMatrixBrowserPanel().getHorizontalTreeView().
				 * containsVisibleNode ( destNode) ||
				 * getMatrixBrowserPanel().getVerticalTreeView
				 * ().containsVisibleNode (
				 */

				// No need to check if node visible, thus.
				// aRenderer = new NetViewRenderer(new VisibleNode(destNode),
				// rel, isInVisibleTreeView);
				aRenderer = new NetViewRenderer(new EncapsulatingVisibleNode(
						destNode), rel);
				int y = myCenterY - (int) (myRadius * Math.cos(i * deg));
				aRenderer.setLocation(
						(myCenterX) + (int) (myRadius * Math.sin(i * deg)), y);
				aRenderer.addActionListener(this);
				add(aRenderer);
				add(createLabel(
						aRenderer,
						destNode.toString(),
						(myCenterX)
								+ (int) ((myRadius + 10) * Math.sin(i * deg)),
						myCenterY - (int) ((myRadius + 10) * Math.cos(i * deg)),
						i * deg));

				i++;
			}

			repaint();
		}
	}

	/**
	 * Paints the NodeCloseUpView with the active node in the middle and related
	 * nodes around
	 * 
	 * @param g
	 *            - a Graphics context
	 * @see java.awt.graphics#Graphics
	 * @version 1.0
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		// Rectangle myBounds = getBounds();
		Component[] myComponentList = getComponents();

		super.paintComponent(g);

		for (int i = 0; i < myComponentList.length; i++) {
			if (myComponentList[i].getClass().equals(NetViewRenderer.class)) {
				g.setColor(Color.black);

				NetViewRenderer aRenderer = (NetViewRenderer) myComponentList[i];
				int centerX = (int) aRenderer.getBounds().getCenterX();
				int centerY = (int) aRenderer.getBounds().getCenterY();

				g.drawLine(myCenterX, myCenterY, centerX, centerY);
			}
		}
	}

	/**
	 * Set�s the bounds of this NodeCloseUpView implementation
	 * 
	 * @param x
	 *            - x position y - y position width - the new width height - the
	 *            new height
	 */
	@Override
	public void setBounds(final int x, final int y, final int width, final int height) {
		super.setBounds(x, y, width, height);
		calculate();
		setNode(myActiveNode);
		repaint();
	}

	/**
	 * Creates a JLabel for the NetViewRenderer�s
	 * 
	 * @param text
	 *            - the labels text x - x position y - y position deg - the
	 *            degree of rotation for label alignement
	 */
	protected JLabel createLabel(final JComponent aComp, final String text, final int x, final int y,
			final double deg) {
		JLabel aLabel = new JLabel();
		aLabel.setLabelFor(aComp);
		aLabel.setText(text);
		// aLabel.setToolTipText(text);
		aLabel.setFont(new java.awt.Font("Courier", 0, 8));
		aLabel.setOpaque(false);

		int labelWidth = text.length() * 5;

		if (labelWidth > (myCenterX / 2)) {
			labelWidth = (myCenterX / 2);
		}

		aLabel.setSize(labelWidth, labelHeight);

		int ideg = (int) Math.round((deg * 180) / Math.PI);

		if ((ideg > 0) && (ideg < 90)) {
			aLabel.setLocation(x, y - labelHeight);
		}

		if ((ideg > 90) && (ideg < 180)) {
			aLabel.setLocation(x, y);
		}

		if ((ideg > 180) && (ideg < 270)) {
			aLabel.setLocation(x - labelWidth, y);
		}

		if ((ideg > 270) && (ideg < 360)) {
			aLabel.setLocation(x - labelWidth, y - labelHeight);
		}

		if (ideg == 0) {
			aLabel.setLocation(x - (labelWidth / 2), y - labelHeight);
		}

		if (ideg == 90) {
			aLabel.setLocation(x, y - (labelHeight / 2));
		}

		if (ideg == 180) {
			aLabel.setLocation(x - (labelWidth / 2), y);
		}

		if (ideg == 270) {
			aLabel.setLocation(x - labelWidth, y - (labelHeight / 2));
		}

		return aLabel;
	}

	/**
	 * Calculates the center and the radius of this component
	 * 
	 * @param text
	 *            - the labels text x - x position y - y position deg - the
	 *            degree of rotation for label alignement
	 */
	private void calculate() {
		Rectangle bounds = getBounds();
		myCenterX = (int) bounds.getCenterX();
		myCenterY = (int) bounds.getCenterY();
		myRadius = (int) bounds.getWidth() / 4;
	}

	/**
	 * Implementation of the ActionListener interface. Receives an ActionEvent
	 * from a NetViewRenderer
	 * 
	 * @param e
	 *            - the ActionEvent
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		NetViewRenderer aRenderer = (NetViewRenderer) e.getSource();
		fireNodeClickedEvent(new VisibleNodeEvent(this, aRenderer.getNode(),
				myActiveNode));
	}

	/**
	 * Adds a VisibleNodeEventListener to this view.
	 * 
	 * @param aListener
	 *            - the target listener
	 */
	@Override
	public void addVisibleNodeEventListener(final VisibleNodeEventListener aListener) {
		myNodeListeners.add(aListener);
	}

	/**
	 * Removes a VisibleNodeEventListener to this view.
	 * 
	 * @param aListener
	 *            - the target listener
	 */
	@Override
	public void removeVisibleNodeEventListener(
			final VisibleNodeEventListener aListener) {
		myNodeListeners.remove(aListener);
	}

	/**
	 * Fires a IAONodeEvent when a node was selected.
	 * 
	 * @param e
	 *            - the IAONodeEvent
	 */
	protected void fireNodeClickedEvent(final VisibleNodeEvent e) {
		for (int i = 0; i < myNodeListeners.size(); i++) {
			myNodeListeners.get(i)
					.onNodeClicked(e);
		}
	}

	/**
	 * Just a customized JButton with overwritten paintComponend method.
	 */
	public class NetViewRenderer extends JButton {

		/**
		 * Comment for <code>serialVersionUID</code>
		 */
		private static final long serialVersionUID = 1L;

		static protected final int SIZE = 16;

		private VisibleNode myOriginPath = null;

		private boolean isExpanded = false;

		/**
		 * Tells whether the displayed node is in the VisibleTree of one of the
		 * <code>TreeViews</code>. This is not necessary to know for any
		 * function, though. Comment for <code>myIsInVisibleTreeView</code>
		 * 
		 * @deprecated should not be necessary to use.
		 */
		@Deprecated
		private boolean myIsInVisibleTreeView;

		Relation myRelation = null;

		/**
		 * Method NetViewRenderer.
		 * 
		 * @param aNode
		 *            the node to render
		 * @param aDescr
		 *            the relation description
		 * @param isInVisibleTreeView
		 *            - is this node currently visible
		 * @param in
		 *            one of the actual IAOTreeViews.
		 * @deprecated Please use the version without the boolean
		 *             isInVisibleTree as of now.
		 */
		@Deprecated
		public NetViewRenderer(final EncapsulatingVisibleNode aNode,
				final Relation aRelation, final boolean isInVisibleTreeView) {
			super();
			this.setBackground(Color.white);
			this.setFont(new java.awt.Font("Dialog", 0, 10));
			this.setBorder(null);
			this.setSize(SIZE, SIZE);
			myOriginPath = aNode;
			myRelation = aRelation;
			// we don't need to know this as the listener itself knows about it.
			// myIsInVisibleTreeView = isInVisibleTreeView;
			this.setToolTipText(aNode.toString());

		}

		public NetViewRenderer(final VisibleNode node, final Relation aRelation) {
			super();
			this.setBackground(Color.white);
			this.setFont(new java.awt.Font("Dialog", 0, 10));
			this.setBorder(null);
			this.setSize(SIZE, SIZE);
			myOriginPath = node;
			myRelation = aRelation;
			this.setToolTipText(node.toString());
		}

		public VisibleNode getNode() {
			return myOriginPath;
		}

		/**
		 * @see javax.swing.JComponent#paintComponent(Graphics)
		 */
		/**
		 * Paints the Renderer
		 * 
		 * @param g
		 *            - a Graphics context
		 * @see java.awt.graphics#Graphics
		 */
		@Override
		protected void paintComponent(final Graphics g) {
			Rectangle bounds = this.getBounds();
			g.setColor(Color.white);
			g.fillRect(0, 0, SIZE - 1, SIZE - 1);

			if (myRelation != null) {
				switch (myRelation.getRelationClass()) {
					case COLLAPSED :
						g.setColor(Color.black);

						break;

					case INFERRED :
						g.setColor(new Color(206, 156, 206));

						break;

					case REAL :
						g.setColor(Color.lightGray);

						break;
					case IDENTITY :
						break;
					default :
						break;
					
				}
			} else {
				g.setColor(Color.lightGray);
			}

			int startx = (int) ((bounds.getWidth() - SIZE) / 2);
			int starty = (int) ((bounds.getHeight() - SIZE) / 2);
			g.fillOval(startx, starty, SIZE - 1, SIZE - 1);
			g.setColor(Color.black);

			if (!myIsInVisibleTreeView) {
				Rectangle strBounds = g.getFontMetrics()
						.getStringBounds("H", g).getBounds();
				g.drawString("H",
						Math.round((bounds.width - strBounds.width) / 2),
						Math.round((bounds.height + 8) / 2));
			}

			if (getModel().isRollover()) {
				g.setColor(Color.red);
			}

			g.drawOval(startx, starty, SIZE - 1, SIZE - 1);
		}
		@Override
		public void setLocation(final int x, final int y) {
			super.setLocation(x - (int) (getBounds().getWidth() / 2), y
					- (int) (getBounds().getHeight() / 2));
		}
	}

	// Renderer

	/**
	 * @return the <code>MatrixBrowserPanel</code>, which created this
	 *         <code>TreeView</code>
	 */
	public MatrixBrowserPanel getMatrixBrowserPanel() {
		return myMatrixBrowserPanel;
	}

	@Override
	public VisibleNode getNode() {
		return myActiveNode;
	}
}