/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * MatrixBrowserPanel.java,v 1.19 2004/04/07 14:37:06 roehrijn Exp $ Created on
 * 19.03.02
 */
package de.fhg.iao.matrixbrowser;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.context.TreeCombinator;
import de.fhg.iao.matrixbrowser.context.TreeManager;
import de.fhg.iao.matrixbrowser.context.TreeModelChangedEvent;
import de.fhg.iao.matrixbrowser.context.TreeModelChangedListener;
import de.fhg.iao.matrixbrowser.context.TreeProxyManager;
import de.fhg.iao.matrixbrowser.context.TreeSynthesizerProxy;
import de.fhg.iao.matrixbrowser.model.ModelChangedListener;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.matrixbrowser.widgets.AbstractTreeView;
import de.fhg.iao.matrixbrowser.widgets.DefaultHorizontalTreeView;
import de.fhg.iao.matrixbrowser.widgets.DefaultRelationPane;
import de.fhg.iao.matrixbrowser.widgets.DefaultVerticalTreeView;
import de.fhg.iao.matrixbrowser.widgets.TabbedNodeCloseUpViewPanel;
import de.fhg.iao.swt.util.uniqueidentifier.ID;
import de.fhg.iao.swt.xswing.JContextMenu;

/**
 * The 'main' panel of the IAO MatrixBrowser. This is a swing component for an
 * interactive adjacency matrix with two <code>IAOTreeView</code> s for
 * displaying hierachies along the matrix axes and a <code>RelationPane</code>
 * for the relations in the matrix itself. From outside you can register for
 * <code>IAONodeEvent</code>s, and <code>VisibleRelationEvent</code> s or
 * set/get/parametrize the different views used.
 * 
 * <code>MatrixBrowserPanel</code> uses the folowing the following keys in the
 * <code>UIResourceRegistry</code>:<br>
 * MatrixBrowser.background - background color of the whole panel<br>
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @author <a href=mailto:cs@christian-schott.de>Christian Schott</a>
 * @version $Revision: 1.12 $
 */
public class MatrixBrowserPanel extends JLayeredPane
		implements
			TreeModelChangedListener {

	Logger log = Logger.getLogger(MatrixBrowserPanel.class);

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/** Minumum height of the <code>NodeCloseUpView</code> when resizing */
	protected static final int NODECLOSEUPVIEW_MINHEIGHT = 108;

	/** Minumum width of the <code>NodeCloseUpView</code> when resizing */
	protected static final int NODECLOSEUPVIEW_MINWIDTH = 108;

	/** Minumum height of the <code>RelationPane</code> when resizing */
	protected static final int RELATIONPANE_MINHEIGHT = 100;

	/** Minumum width of the <code>RelationPane</code> when resizing */
	protected static final int RELATIONPANE_MINWIDTH = 100;

	/** The component controller used */
	protected ComponentCommander myCommunicator = null;

	/** Holds the editable state of this <code>MatrixBrowserPanel</code> */
	protected boolean myEditableState = false;

	/** Holds a list of event listeners of this <code>MatrixBrowserPanel</code> */
	protected transient EventListenerList myEventListenerList = null;

	/** The <code>HorizontalTreeView</code> implementation used */
	protected HorizontalTreeView myHorizontalTreeView = null;

	/**
	 * The <code>TreeManager</code> used for building trees out of the
	 * underlying graph
	 */
	protected TreeManager myManager = null;

	/** The handler for mouse events used */
	protected MouseEventHandler myMouseHandler = null;

	/** The <code>NodeCloseUpView</code> implementation used */
	protected NodeCloseUpView myNodeCloseUpView = null;

	/** The <code>RelationPane</code> implementation used */
	protected RelationPane myRelationPane = null;

	/** The <code>VerticalTreeView</code> implementation used */
	protected VerticalTreeView myVerticalTreeView = null;

	/**
	 * The <code>TreeCombinator</code> used for removing empty nodes and for use
	 * in DefaultRelationPane
	 */
	private TreeCombinator treeCombinator = null;

	/**
	 * The <code>TreeCombinator</code> used for switching the hide nodes
	 * mechanism on and off
	 */
	// JPopupMenu myPopupMenu = null; //schotti: This is never used!
	private HistoryMenu mHistoryMenu;

	// private int currentHistoryEntry = -1;
	// private int historyMaxEntry=0;
	/**
	 * Constructs a empty <code>MatrixBrowserPanel</code> without a model. a
	 * <code>TreeManager</code> can be set by using the .setTreeManager method.
	 * 
	 * @see MatrixBrowserPanel#setTreeManager(TreeManager)
	 */
	public MatrixBrowserPanel() {
		super();

		// First of all check if UIResourceRegistry is empty and load mb
		// defaults
		if (UIResourceRegistry.getInstance().isEmpty()) {
			UIResourceRegistry.getInstance().loadLaFDefaults();
			UIResourceRegistry.getInstance().loadMBDefaultColors();
			UIResourceRegistry.getInstance().loadMBDefaultIcons();
			UIResourceRegistry.getInstance().loadMBDefaultLocalization();
		}

		setLayout(new MatrixBrowserPanelLayout());
		setOpaque(true);

		setBackground(UIResourceRegistry.getInstance().getColor(
				UIResourceRegistry.getInstance().MATRIXBROWSER_BACKGROUND));

		ToolTipManager.sharedInstance().setInitialDelay(500);

		myEventListenerList = new EventListenerList();

		// create the object shown in the panel
		myHorizontalTreeView = new DefaultHorizontalTreeView(this);
		myVerticalTreeView = new DefaultVerticalTreeView(this);
		myRelationPane = new DefaultRelationPane(this);

		myNodeCloseUpView = new TabbedNodeCloseUpViewPanel(this,
				NODECLOSEUPVIEW_MINWIDTH, NODECLOSEUPVIEW_MINHEIGHT); // DefaultNodeCloseUpViewPanel(this);

		// new DefaultNodeCloseUpViewPanel(this);
		((JComponent) myNodeCloseUpView).setMinimumSize(new Dimension(
				NODECLOSEUPVIEW_MINWIDTH, NODECLOSEUPVIEW_MINHEIGHT));

		// add the object to the different layers (important for mouse and
		// paint behavior)
		// the relation pane has the greatest depth so it is painted first
		// then, the the closeup view is painted
		// and, at the end treeviews are painted
		// this also fixes bugs with displaying components wrong while dragging
		this.add((JComponent) myRelationPane, JLayeredPane.FRAME_CONTENT_LAYER);
		this.add((JComponent) myNodeCloseUpView, JLayeredPane.MODAL_LAYER);
		this.add((JComponent) myVerticalTreeView, JLayeredPane.DRAG_LAYER);
		this.add((JComponent) myHorizontalTreeView, JLayeredPane.DRAG_LAYER);

		// create handlers
		myCommunicator = createComponentCommander();
		myMouseHandler = createMouseEventHandler();

		// install all necessary Listeners
		establishInitialComponentCommunication();
		validate();

	}

	/**
	 * Constructs a <code>MatrixBrowserPanel</code> with the given
	 * <code>TreeManager</code>.
	 * 
	 * @param aTreeManager
	 *            the <code>TreeManager</code> used for tree synthesizing
	 * @see MatrixBrowserPanel#setTreeManager(TreeManager)
	 * @see de.fhg.iao.matrixbrowser.context.TreeManager
	 */
	public MatrixBrowserPanel(final TreeManager aTreeManager) {
		this();
		setTreeManager(aTreeManager);
	}

	/**
	 * Adds a <code>TreeExpansionListener</code>, that will be notified about
	 * tree expansions in this <code>TreeView</code>.
	 * 
	 * @param aTreeExpansionListener
	 *            the <code>TreeExpansionListener</code> to be notified of
	 *            <code>TeeExpansionEvent</code> s
	 * @see javax.swing.event.TreeExpansionListener
	 * @see javax.swing.event.TreeExpansionEvent
	 * @see javax.swing.JTree#addTreeExpansionListener(javax.swing.event.TreeExpansionListener)
	 */
	public void addTreeExpansionListener(
			final TreeExpansionListener aTreeExpansionListener) {
		myVerticalTreeView.addTreeExpansionListener(aTreeExpansionListener);
		myHorizontalTreeView.addTreeExpansionListener(aTreeExpansionListener);
	}

	/**
	 * Adds a <code>TreeModelChangedListener</code> that will be notified about
	 * changes in the model.
	 * 
	 * @param aListener
	 *            TreeModelChangedListener
	 */
	public void addTreeModelChangedListener(final TreeModelChangedListener aListener) {
		log.debug("MatrixBrowsePanel has added TreeModelChangedListener");
		myEventListenerList.add(TreeModelChangedListener.class, aListener);
	}

	/**
	 * Adds a <code>VisibleNodeEventListener</code>, that will be notified if
	 * someone clicks on a node in the <code>TreeView<\code>s
	 * or in the <code>NodeCloseUpView<\code>.
	 *     *
	 * 
	 * @param aVisibleNodeEventListener
	 *            the <code>VisibleNodeEventListener</code> to be notified of
	 *            <code>NodeEvent</code>s
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	public void addVisibleNodeEventListener(
			final VisibleNodeEventListener aVisibleNodeEventListener) {
		myEventListenerList.add(VisibleNodeEventListener.class,
				aVisibleNodeEventListener);
	}

	/**
	 * Adds a <code>VisibleRelationEventListener</code>, that will be notified
	 * if someone clicks on the relation pane.
	 * 
	 * @param aVisibleRelationEventListener
	 *            the <code>VisibleRelationEventListener</code> to be notified
	 *            of <code>VisibleRelationEvent</code> s
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 */
	public void addVisibleRelationEventListener(
			final VisibleRelationEventListener aVisibleRelationEventListener) {
		myEventListenerList.add(VisibleRelationEventListener.class,
				aVisibleRelationEventListener);
	}

	/**
	 * Factory method for the <code>ComponentCommander</code> used.
	 * 
	 * @return a default implementation for a <code>ComponentCommander</code>
	 * @see MatrixBrowserPanel.ComponentCommander
	 */
	protected ComponentCommander createComponentCommander() {
		return new ComponentCommander();
	}

	/**
	 * Factory method for the <code>MouseEventHandler</code> used.
	 * 
	 * @return a default implementation for a <code>MouseEventHandler</code>
	 * @see MatrixBrowserPanel.MouseEventHandler
	 */
	protected MouseEventHandler createMouseEventHandler() {
		return new MouseEventHandler();
	}

	/**
	 * Install all needed listeners and connect them to the various sub
	 * components.
	 */
	protected void establishInitialComponentCommunication() {
		myHorizontalTreeView.addVisibleNodeEventListener(myCommunicator);
		myHorizontalTreeView.addTreeExpansionListener(myCommunicator);
		myHorizontalTreeView.addAdjustmentListener(myCommunicator);

		myVerticalTreeView.addVisibleNodeEventListener(myCommunicator);
		myVerticalTreeView.addTreeExpansionListener(myCommunicator);
		myVerticalTreeView.addAdjustmentListener(myCommunicator);

		myNodeCloseUpView.addVisibleNodeEventListener(myCommunicator);

		myRelationPane.addVisibleRelationEventListener(myCommunicator);
		myRelationPane.addMouseListener(myMouseHandler);
		myRelationPane.addMouseMotionListener(myMouseHandler);

	}

	/**
	 * Fires a <code>VisibleNodeEvent</code> by calling 'onNodeEdit' at the
	 * listener. This should happen when a node was clicked in the
	 * <code>TreeView</code> s and the <code>MatrixBrowserPanel</code> is in
	 * editing mode.
	 * 
	 * @param aVisibleNodeEvent
	 *            the <code>VisibleNodeEvent</code> to fire
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	private void fireNodeEditEvent(final VisibleNodeEvent aVisibleNodeEvent) {
		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleNodeEventListener.class) {
				((VisibleNodeEventListener) listeners[i + 1])
						.onNodeEdit(aVisibleNodeEvent);
			}
		}
	}

	/**
	 * Fires a <code>VisibleNodeEvent</code> by calling 'onNodeOver' at the
	 * listener. This should happen when a node was mouse overed in the
	 * <code>TreeView</code>s.
	 * 
	 * @param aVisibleNodeEvent
	 *            the <code>VisibleNodeEvent</code> to fire
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	private void fireNodeOverEvent(final VisibleNodeEvent aVisibleNodeEvent) {
		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleNodeEventListener.class) {
				((VisibleNodeEventListener) listeners[i + 1])
						.onNodeOver(aVisibleNodeEvent);
			}
		}
	}

	/**
	 * Fires a <code>VisibleNodeEvent</code> by calling 'onNodeSelected' at the
	 * listener. This should happen when a node was clicked in the
	 * <code>TreeView</code> s and therefor selected.
	 * 
	 * @param aVisibleNodeEvent
	 *            the <code>VisibleNodeEvent</code> to fire
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	private void fireNodeClickedEvent(final VisibleNodeEvent aVisibleNodeEvent) {
		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleNodeEventListener.class) {
				((VisibleNodeEventListener) listeners[i + 1])
						.onNodeClicked(aVisibleNodeEvent);
			}
		}
	}

	/**
	 * Fires a <code>VisibleNodeEvent</code> by calling 'onNodeSelected' at the
	 * listener. This should happen when a node was clicked in the
	 * <code>TreeView</code> s and therefor selected.
	 * 
	 * @param aVisibleNodeEvent
	 *            the <code>VisibleNodeEvent</code> to fire
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	private void fireNodeMenuEvent(final VisibleNodeEvent aVisibleNodeEvent) {
		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleNodeEventListener.class) {
				((VisibleNodeEventListener) listeners[i + 1])
						.onNodeMenu(aVisibleNodeEvent);
			}
		}
	}

	/**
	 * Fires an <code>VisibleRelationEvent</code>by calling 'onRelationClicked'
	 * at the listener. This should happen when a relation was clicked in the
	 * <code>RelationPane</code>. *
	 * 
	 * @param aVisibleRelationEvent
	 *            the <code>VisibleRelationEvent</code> to fire
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEventListener
	 */
	private void fireRelationClickedEvent(
			final VisibleRelationEvent aVisibleRelationEvent) {
		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleRelationEventListener.class) {
				((VisibleRelationEventListener) listeners[i + 1])
						.onRelationClicked(aVisibleRelationEvent);
			}
		}
	}

	/**
	 * Fires an <code>VisibleRelationEvent</code>by calling 'onRelationSelected'
	 * at the listener. This should happen when a relation was selected in the
	 * <code>RelationPane</code>. *
	 * 
	 * @param aVisibleRelationEvent
	 *            the <code>VisibleRelationEvent</code> to fire
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEventListener
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void fireRelationSelectedEvent(
			final VisibleRelationEvent aVisibleRelationEvent) {
		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleRelationEventListener.class) {
				((VisibleRelationEventListener) listeners[i + 1])
						.onRelationSelected(aVisibleRelationEvent);
			}
		}
	}

	/**
	 * Fires an <code>VisibleRelationEvent</code>by calling 'onRelationEdit' at
	 * the listener. This should happen when a relation was clicked in the
	 * <code>RelationPane</code> and the <code>MatrixBrowserPanel</code> is in
	 * editing mode. *
	 * 
	 * @param aVisibleRelationEvent
	 *            the <code>VisibleRelationEvent</code> to fire
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEventListener
	 */
	private void fireRelationEditEvent(
			final VisibleRelationEvent aVisibleRelationEvent) {
		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleRelationEventListener.class) {
				((VisibleRelationEventListener) listeners[i + 1])
						.onRelationEdit(aVisibleRelationEvent);
			}
		}
	}

	/**
	 * Fires an <code>VisibleRelationEvent</code>by calling 'onRelationOver' at
	 * the listener. This should happen when a relation was mouse overed in the
	 * <code>RelationPane</code>.
	 * 
	 * @param e
	 *            aVisibleRelationEvent <code>VisibleRelationEvent</code> to
	 *            fire
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEventListener
	 */
	private void fireRelationOverEvent(final VisibleRelationEvent e) {
		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleRelationEventListener.class) {
				((VisibleRelationEventListener) listeners[i + 1])
						.onRelationOver(e);
			}
		}
	}

	/**
	 * Fires a <code>TreeModelChangedEvent</code> by calling
	 * 'onTreeModelChanged' at the listener.
	 * 
	 * @param e
	 *            the <code>TreeModelChangedEvent</code> to fire
	 */
	private void fireTreeModelChangedEvent(final TreeModelChangedEvent e) {
		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TreeModelChangedListener.class) {
				((TreeModelChangedListener) listeners[i + 1])
						.onTreeModelChanged(e);
			}
		}
	}

	/**
	 * Gets the editable state <code>MatrixBrowserPanel</code>.
	 * 
	 * @return true if its editable, false if not
	 */
	public boolean getEditable() {
		return myEditableState;
	}

	/**
	 * Gets the the row in the <code>HorizontalTreeView</code>for an x
	 * coordinate in the <code>MatrixBrowserPanel</code>.
	 * 
	 * @param aX
	 *            x coordinate in the <code>MatrixBrowserPanel</code>
	 * @return the row number in the <code>HorizontalTreeView</code>
	 * @see de.fhg.iao.matrixbrowser.HorizontalTreeView
	 */
	public int getHorizontalRowForX(final int aX) {
		return myRelationPane.getHorizontalRowForX(aX);
	}

	/**
	 * @return the current <code>HorizontalTreeView</code> instance used. This
	 *         is always also an instance of <code>JComponent</code>.
	 * @see de.fhg.iao.matrixbrowser.HorizontalTreeView
	 */
	public HorizontalTreeView getHorizontalTreeView() {
		return myHorizontalTreeView;
	}

	/**
	 * @return the current <code>JContextMenu</code> of the current
	 *         <code>HorizontalTreeView</code>
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 * @deprecated is never used!
	 */
	@Deprecated
	public JContextMenu getHorizontalTreeViewContextMenu() {
		log.debug("Call to a deprecated function. This has never been used up to now!");
		return myHorizontalTreeView.getContextMenu();
	}

	/**
	 * @return the current <code>NodeCloseUpView</code> instance used. This is
	 *         always also an instance of <code>JComponent</code>.
	 * @see de.fhg.iao.matrixbrowser.NodeCloseUpView
	 */
	public NodeCloseUpView getNodeCloseUpView() {
		return myNodeCloseUpView;
	}

	/**
	 * @return the current <code>RelationPane</code> instance used. This is
	 *         always also an instance of <code>JComponent</code>.
	 * @see de.fhg.iao.matrixbrowser.RelationPane
	 */
	public RelationPane getRelationPane() {
		return myRelationPane;
	}

	/**
	 * @return the current <code>JContextMenu</code> of the current
	 *         <code>RelationPane</code>
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 * @deprecated never used!
	 */
	@Deprecated
	public JContextMenu getRelationPaneContextMenu() {
		log.debug("Call to MatrixBrowserPanel.getRelationPaneContextMenu. This had never been used before!");
		return myRelationPane.getContextMenu();
	}

	/**
	 * @return the current <code>TreeManager</code> used for synthesizing trees
	 *         out of the graph model of the matrix browser.
	 * @see de.fhg.iao.matrixbrowser.context.TreeManager
	 */
	public TreeManager getTreeManager() {
		return this.myManager;
	}

	/**
	 * Gets the the row in the <code>VerticalTreeView</code>for an y coordinate
	 * in the <code>MatrixBrowserPanel</code>.
	 * 
	 * @param aY
	 *            y coordinate in the <code>MatrixBrowserPanel</code>
	 * @return the row number in the <code>VerticalTreeView</code>
	 * @see de.fhg.iao.matrixbrowser.VerticalTreeView
	 */
	public int getVerticalRowForY(final int aY) {
		return myRelationPane.getVerticalRowForY(aY);
	}

	/**
	 * @return the current <code>VerticalTreeView</code> instance used. This is
	 *         always also an instance of <code>JComponent</code>.
	 * @see de.fhg.iao.matrixbrowser.VerticalTreeView
	 */
	public VerticalTreeView getVerticalTreeView() {
		return myVerticalTreeView;
	}

	/**
	 * @return the current <code>JContextMenu</code> of the current
	 *         <code>VerticalTreeView</code>
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 * @deprecated This is never used!
	 */
	@Deprecated
	public JContextMenu getVerticalTreeViewContextMenu() {
		log.debug("MatrixBrowserPanel.getVerticalTreeViewContextMenu had never been used before.");
		return myVerticalTreeView.getContextMenu();
	}

	/**
	 * @return the actual <code>Set</code> of visible <code>RelationType</code>
	 *         s.
	 * @see de.fhg.iao.matrixbrowser.model.elements.RelationType
	 */
	public Collection<RelationType> getVisibleRelationTypes() {
		return myRelationPane.getVisibleRelationTypes();
	}

	/**
	 * Removes a <code>ModelChangedListener</code> event listener list.
	 * 
	 * @param aModelChangedListener
	 *            the <code>ModelChangedListener</code> to be removed
	 */
	public void removeModelChangedListener(
			final ModelChangedListener aModelChangedListener) {
		myEventListenerList.add(ModelChangedListener.class,
				aModelChangedListener);
	}

	/**
	 * Removes a <code>TreeExpansionListener</code> from the event listener
	 * list.
	 * 
	 * @param aTreeExpansionListener
	 *            the <code>TreeExpansionListener</code> to be removed
	 * @see javax.swing.event.TreeExpansionListener
	 * @see javax.swing.event.TreeExpansionEvent
	 */
	public void removeTreeExpansionListener(
			final TreeExpansionListener aTreeExpansionListener) {
		myVerticalTreeView.removeTreeExpansionListener(aTreeExpansionListener);
		myHorizontalTreeView
				.removeTreeExpansionListener(aTreeExpansionListener);
	}

	/**
	 * Removes a <code>VisibleNodeEventListener</code> from the event listener
	 * list.
	 * 
	 * @param aVisibleNodeEventListener
	 *            the <code>VisibleNodeEventListener</code> to be removed
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	public void removeVisibleNodeEventListener(
			final VisibleNodeEventListener aVisibleNodeEventListener) {
		myEventListenerList.remove(VisibleNodeEventListener.class,
				aVisibleNodeEventListener);
	}

	/**
	 * Removes a <code>VisibleRelationEventListener</code> from the event
	 * listener list.
	 * 
	 * @param aVisibleRelationEventListener
	 *            the <code>VisibleRelationEventListener</code> to be removed
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 */
	public void removeVisibleRelationEventListener(
			final VisibleRelationEventListener aVisibleRelationEventListener) {
		myEventListenerList.add(VisibleRelationEventListener.class,
				aVisibleRelationEventListener);
	}

	/**
	 * Sets this <code>MatrixBrowserPanel</code> editable state.
	 * 
	 * @param aEditableState
	 *            the editable state. 'true' to make it editable
	 */
	public void setEditable(final boolean aEditableState) {
		log.debug("editable state set on MatrixBrowserPanel");
		myEditableState = aEditableState;
		myHorizontalTreeView.setEditable(aEditableState);
		myVerticalTreeView.setEditable(aEditableState);
	}

	/**
	 * Sets a new <code>HorizontalTreeView</code> implementation on this
	 * <code>MatrixBrowserPanel</code>. *
	 * 
	 * @param aHorizontalTreeView
	 *            the new <code>HorizontalTreeView</code>
	 * @see de.fhg.iao.matrixbrowser.HorizontalTreeView
	 */
	public void setHorizontalTreeView(
			final HorizontalTreeView aHorizontalTreeView) {
		myHorizontalTreeView.removeVisibleNodeEventListener(myCommunicator);
		myHorizontalTreeView.removeTreeExpansionListener(myCommunicator);
		myHorizontalTreeView.removeAdjustmentListener(myCommunicator);

		myHorizontalTreeView.removeVisibleNodeEventListener(myCommunicator);
		this.remove((JComponent) myHorizontalTreeView);

		aHorizontalTreeView.addVisibleNodeEventListener(myCommunicator);
		aHorizontalTreeView.setTreeSynthesizer(myHorizontalTreeView
				.getTreeSynthesizer());
		aHorizontalTreeView.addTreeExpansionListener(myCommunicator);
		aHorizontalTreeView.addAdjustmentListener(myCommunicator);
		aHorizontalTreeView.getTreeSynthesizer().addTreeModelChangedListener(
				this);
		myHorizontalTreeView = aHorizontalTreeView;

		this.add((JComponent) myHorizontalTreeView, JLayeredPane.PALETTE_LAYER);
	}

	/**
	 * Sets a new <code>JContextMenu</code> for the current
	 * <code>HorizontalTreeView</code>. If the <code>JContextMenu</code> of the
	 * <code>HorizontalTreeView</code> is non <code>null</code> it is displayed
	 * if the user clicks on the right mous button.
	 * 
	 * @param aContextMenu
	 *            a new <code>JContextMenu</code> for the current
	 *            <code>RelationPane</code>
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 * @deprecated because whenever this is called, the method setContextMenu is
	 *             directly called on the @link{AbstractTreeView}.
	 */
	// Schotti: This method is never called in the version I got.
	// Throughout the program, the ContextMenu is set by directly calling the
	// AbstractTreeView's method directly.
	@Deprecated
	public void setHorizontalTreeViewContextMenu(final JContextMenu aContextMenu) {
		log.debug("MatrixBrowserPanel.HorizontalTreeViewContextMenu "
				+ "called.This should not be the case.");
		myHorizontalTreeView.setContextMenu(aContextMenu);
	}

	/**
	 * Sets a new <code>NodeCloseUpView</code> implementation on this
	 * <code>MatrixBrowserPanel</code>.
	 * 
	 * @param aNodeCloseUpView
	 *            the new <code>NodeCloseUpView</code>
	 * @see de.fhg.iao.matrixbrowser.NodeCloseUpView
	 */
	public void setNodeCloseUpView(final NodeCloseUpView aNodeCloseUpView) {
		aNodeCloseUpView.addVisibleNodeEventListener(myCommunicator);
		myNodeCloseUpView = aNodeCloseUpView;
		this.add((JComponent) myNodeCloseUpView, JLayeredPane.MODAL_LAYER);
		this.revalidate();
	}

	/**
	 * Sets a new <code>RelationPane</code> on this
	 * <code>MatrixBrowserPanel</code>. *
	 * 
	 * @param aRelationPane
	 *            the new <code>RelationPane</code>
	 * @see de.fhg.iao.matrixbrowser.RelationPane
	 */
	public void setRelationPane(final RelationPane aRelationPane) {
		myRelationPane.removeVisibleRelationEventListener(myCommunicator);
		myRelationPane.removeMouseListener(myMouseHandler);
		myRelationPane.removeMouseMotionListener(myMouseHandler);

		this.remove((JComponent) myRelationPane);

		aRelationPane.addVisibleRelationEventListener(myCommunicator);
		aRelationPane.addMouseListener(myMouseHandler);
		aRelationPane.addMouseMotionListener(myMouseHandler);

		this.add((JComponent) aRelationPane, JLayeredPane.PALETTE_LAYER);
		myRelationPane = aRelationPane;
	}

	/**
	 * Sets a new <code>JContextMenu</code> for the current
	 * <code>RelationPane</code>. If the <code>JContextMenu</code> of the
	 * <code>RelationPane</code> is non <code>null</code> it is displayed if the
	 * user clicks on the right mouse button.
	 * 
	 * @param aContextMenu
	 *            a new <code>JContextMenu</code> for the current
	 *            <code>RelationPane</code>
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 * @deprecated This is never used!
	 */
	@Deprecated
	public void setRelationPaneContextMenu(final JContextMenu aContextMenu) {
		log.debug("MatrixBrowserPanel.setRelationPaneContextMenu called."
				+ "This had not been used before.");
		myRelationPane.setContextMenu(aContextMenu);
	}

	/**
	 * Sets a new <code>JTree</code> to be displayed. The given Tree will be
	 * parsed and the first two nodes will be taken for the horizontal TreeView
	 * and the vertical TreeView. If there are less than two nodes in the given
	 * tree, the root node will be chosen to display instead.
	 * 
	 */
	public void setTree(final JTree tree) {
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree
				.getModel().getRoot();
		DefaultMutableTreeNode verticalRoot = null;
		DefaultMutableTreeNode horizontalRoot = null;
		int numchildren = rootNode.getChildCount();
		switch (numchildren) {
			case 0 :
				// fall through//
			case 1 :
				verticalRoot = rootNode;
				horizontalRoot = rootNode;
				break;
			default :
				verticalRoot = (DefaultMutableTreeNode) rootNode.getChildAt(0);
				horizontalRoot = (DefaultMutableTreeNode) rootNode
						.getChildAt(1);
				break;
		}
		log.debug("creating a Tree for " + horizontalRoot);
		JTree newTree = (new JTree(horizontalRoot));
		log.debug("the tree is: " + newTree + ".");
		log.debug("With model: " + newTree.getModel());
		log.debug("and root node: " + newTree.getModel().getRoot());
		myHorizontalTreeView.setTree(newTree);
		log.debug("HorizontalTreeView rootnode is: "
				+ myHorizontalTreeView.getTree().getModel().getRoot());
		myVerticalTreeView.setTree(new JTree(verticalRoot));
		this.repaint();
	}

	/**
	 * Sets a new <code>TreeManager</code> on this
	 * <code>MatrixBrowserPanel</code> and notifies all listernes.
	 * 
	 * @param aTreeManager
	 *            the new <code>TreeManager</code> used.
	 * @see de.fhg.iao.matrixbrowser.context.TreeManager
	 */
	public void setTreeManager(final TreeManager aTreeManager) {
		this.myManager = aTreeManager;

		Node rootNode = myManager.getModel().getRootNode();
		Node verticalRoot = null;
		Node horizontalRoot = null;
		if (rootNode != null) {
			List<Node> children = (List<Node>) aTreeManager
					.getDefaultTreeSynthesizer().getChildren(rootNode);

			switch (children.size()) {
				case 0 :
				case 1 :
					verticalRoot = rootNode;
					horizontalRoot = rootNode;

					break;

				default :
					verticalRoot = children.get(0);
					horizontalRoot = children.get(1);

					break;
			}
		}
		TreeProxyManager verticalTSproxy = myManager.createTreeProxyManager();
		verticalTSproxy.setRootNode(verticalRoot);
		aTreeManager.getDefaultTreeSynthesizer().addTreeModelChangedListener(
				verticalTSproxy);
		TreeSynthesizerProxy horizontalTSproxy = myManager
				.createTreeProxyManager();
		horizontalTSproxy.setRootNode(horizontalRoot);
		aTreeManager.getDefaultTreeSynthesizer().addTreeModelChangedListener(
				horizontalTSproxy);
		treeCombinator = myManager.createTreeCombinator(verticalTSproxy,
				horizontalTSproxy);
		// set the treeview's treeSynthesizer
		myVerticalTreeView.setTreeSynthesizer(verticalTSproxy);

		// set the treeview's treeSynthesizer
		myHorizontalTreeView.setTreeSynthesizer(horizontalTSproxy);
		// at this stage, both trees are built and empty nodes (nodes with no
		// relations)
		// can be found!!!!
		/*
		 * Now both TreeSynthesizers need to share information about their
		 * visible Trees so all nodes that do not have relations are removed!
		 */

		// removeEmptyNodes();
		// reset the History after a new TreeManager was set
		resetHistory();
		// first history entry
		// if(verticalRoot != null && horizontalRoot !=null){
		fireTreeModelChangedEvent(new TreeModelChangedEvent(this));
		// } else {
		// validate();
		// }
	}

	/**
	 * resets the history entries, i.e. clears the myHistory Vector and sets
	 * currentHistoryEntry and historyMaxEntry to zero then, adds the first
	 * history entry
	 */
	public void resetHistory() {
		if (mHistoryMenu != null) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					mHistoryMenu.reset();
					mHistoryMenu.addEntry(getMemento(),
							((DefaultHorizontalTreeView) myHorizontalTreeView)
									.getMemento(),
							((DefaultVerticalTreeView) myVerticalTreeView)
									.getMemento(),
							((DefaultRelationPane) getRelationPane())
									.getMemento());
				}
			});
		}
	}

	/*
	 * public void addHistoryEntry(HistoryMenu aMenu){
	 * aMenu.addEntry(((DefaultHorizontalTreeView
	 * )myHorizontalTreeView).getMemento
	 * (),((DefaultVerticalTreeView)myVerticalTreeView
	 * ).getMemento(),((DefaultRelationPane) getRelationPane()).getMemento()); }
	 */
	public void setHistoryMenu(final HistoryMenu menu) {
		mHistoryMenu = menu;
	}

	/**
	 * Sets a new <code>VerticalTreeView<\code> implementation on this
	 * <code>MatrixBrowserPanel</code>. *
	 * 
	 * @param aVerticalTreeView
	 *            the new <code>aVerticalTreeView<\code> used
	 * @see de.fhg.iao.matrixbrowser.widgets#aVerticalTreeView
	 */
	public void setVerticalTreeView(final VerticalTreeView aVerticalTreeView) {
		myVerticalTreeView.removeVisibleNodeEventListener(myCommunicator);
		myVerticalTreeView.removeTreeExpansionListener(myCommunicator);
		myVerticalTreeView.removeAdjustmentListener(myCommunicator);
		this.remove((JComponent) myVerticalTreeView);

		aVerticalTreeView.addVisibleNodeEventListener(myCommunicator);
		aVerticalTreeView.setTreeSynthesizer(myVerticalTreeView
				.getTreeSynthesizer());
		aVerticalTreeView.addTreeExpansionListener(myCommunicator);
		aVerticalTreeView.addAdjustmentListener(myCommunicator);
		myVerticalTreeView = aVerticalTreeView;

		this.add((JComponent) aVerticalTreeView, JLayeredPane.PALETTE_LAYER);
	}

	/**
	 * Sets a new <code>JContextMenu</code> for the current
	 * <code>VerticalTreeView</code>. If the <code>JContextMenu</code> of the
	 * <code>VerticalTreeView</code> is non <code>null</code> it is displayed if
	 * the user clicks on the right mous button.
	 * 
	 * @param aContextMenu
	 *            a new <code>JContextMenu</code> for the current
	 *            <code>RelationPane</code>
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 * @deprecated You are the first person wanting to use this _ever_!
	 */
	@Deprecated
	public void setVerticalTreeViewContextMenu(final JContextMenu aContextMenu) {
		log.debug("MatrixBrowserPanel.setVerticalTreeViewContextMenu called. This is deprecated because it had never been used up to today.");
		myVerticalTreeView.setContextMenu(aContextMenu);
	}

	/**
	 * The communication handler of this <code>MatrixBrowserPanel</code>, which
	 * handles events and creates actions of/for the sub components of the
	 * matrix browser.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.12 $
	 */
	protected class ComponentCommander
			implements
				VisibleNodeEventListener,
				VisibleRelationEventListener,
				TreeExpansionListener,
				AdjustmentListener {

		/**
		 * @see java.awt.event.AdjustmentListener#adjustmentValueChanged(java.awt.event.AdjustmentEvent)
		 */
		@Override
		public void adjustmentValueChanged(final AdjustmentEvent e) {
			((JComponent) myRelationPane).repaint();
		}

		/**
		 * Recieves a <code>VisibleNodeEvent</code>
		 * 
		 * @param aEvent
		 *            the <code>VisibleNodeEvent</code>
		 */
		@Override
		public void onNodeEdit(final VisibleNodeEvent aEvent) {
			fireNodeEditEvent(aEvent);
		}

		/**
		 * Recieves a <code>VisibleNodeEvent</code>
		 * 
		 * @param aEvent
		 *            the <code>VisibleNodeEvent</code>
		 */
		@Override
		public void onNodeOver(final VisibleNodeEvent e) {
			if (e.getSource() instanceof HorizontalTreeView) {
				TreePath aPath = e.getPath();
				VisibleRelation aVisRel = myRelationPane.getRollOverRelation();

				if (aVisRel != null) {
					aVisRel = myRelationPane.getVisibleRelationForPath(aPath,
							aVisRel.getVerticalPath());

					if (aVisRel != null) {
						myRelationPane.setRollOverRelation(aVisRel);
						myHorizontalTreeView.setSelectionPath(aPath);
					}
				}

				// myHorizontalTreeView.setSelectionPath(aPath);
				// myNetView.setActiveNode((IAOTreeNode)
				// aPath.getLastPathComponent());
			}

			if (e.getSource() instanceof VerticalTreeView) {
				TreePath aPath = e.getPath();
				VisibleRelation aVisRel = myRelationPane.getRollOverRelation();

				if (aVisRel != null) {
					aVisRel = myRelationPane.getVisibleRelationForPath(
							aVisRel.getHorizontalPath(), aPath);

					if (aVisRel != null) {
						myRelationPane.setRollOverRelation(aVisRel);
						myVerticalTreeView.setSelectionPath(aPath);
					}
				}
			}

			fireNodeOverEvent(e);
		}

		/**
		 * Recieves a <code>VisibleNodeEvent</code>
		 * 
		 * @param aEvent
		 *            the <code>VisibleNodeEvent</code>
		 */
		@Override
		public void onNodeClicked(final VisibleNodeEvent e) {
			if (e.getSource() instanceof TreeView) {
				VisibleNode sourceNode = e.getNode();
				myNodeCloseUpView.setNode(sourceNode);
			}

			if (e.getSource() instanceof NodeCloseUpView) {
				// Neuer Versuch schotti
				((NodeCloseUpView) e.getSource()).setNode(e.getNode());
			}
			/*
			 * VisibleNode sourceNode = (VisibleNode) e.getNode(); // index 0 is
			 * root node if (myVerticalTreeView.containsVisibleNode(sourceNode))
			 * { TreePath thePath = new
			 * TreePath(sourceNode.getPathUpTo((VisibleNode)
			 * myVerticalTreeView.getTreeModel().getRoot())); //TreePath thePath
			 * = new
			 * TreePath(((VisibleNode)e.getNode().getNestedNode().getModel(
			 * ).getRootNode()).pathFromAncestorEnumeration(sourceNode));
			 * //originalLine: TreePath thePath = new
			 * TreePath(sourceNode.getPathTo
			 * (myVerticalTreeView.getTreeModel().getRoot())); //schotti: I
			 * suppose, the previous line should get the path upwards from the
			 * event's source node back to its root node. //the next line does
			 * this correctly... //TreeNode[] aPath = sourceNode.getPath();//get
			 * the path from root to source node //List<TreeNode> reversepath =
			 * (Arrays.asList(aPath)); //make a collection out of it.
			 * //Collections.reverse(reversepath);//flip it over to get the path
			 * from source to root. //TreePath thePath = new
			 * TreePath(reversepath);//transform it back into a new treePath.
			 * myVerticalTreeView.expandPath(thePath);
			 * myVerticalTreeView.setSelectionPath(thePath); } else if
			 * (myHorizontalTreeView.containsVisibleNode(sourceNode)) { TreePath
			 * thePath = new
			 * TreePath(sourceNode.getPathUpTo((VisibleNode)myHorizontalTreeView
			 * .getTreeModel().getRoot()));
			 * myHorizontalTreeView.expandPath(thePath);
			 * myHorizontalTreeView.setSelectionPath(thePath); } else {
			 * VisibleNode topMostHierarchyNode = (VisibleNode)
			 * sourceNode.getPath()[1]; VisibleNode targetNode = (VisibleNode)
			 * e.getSecondNode(); TreePath thePath = new
			 * TreePath(sourceNode.getPathUpTo(topMostHierarchyNode)); if
			 * (myVerticalTreeView.containsVisibleNode(targetNode)) {
			 * myHorizontalTreeView
			 * .getTreeSynthesizer().setRootNode(topMostHierarchyNode
			 * .getNestedNode()); The line before was supposed to be sth. like:
			 * myHorizontalTreeView.setTreeModel(topMostHierarchyNode);
			 * myHorizontalTreeView.expandPath(thePath);
			 * myHorizontalTreeView.setSelectionPath(thePath);
			 * myRelationPane.updateVisibleRelations(); } else if
			 * (myHorizontalTreeView.containsVisibleNode(targetNode)) {
			 * myVerticalTreeView
			 * .getTreeSynthesizer().setRootNode(topMostHierarchyNode
			 * .getNestedNode()); the line before was supposed to be sth. like:
			 * myVerticalTreeView.setTreeModel(topMostHierarchyNode);
			 * myVerticalTreeView.expandPath(thePath);
			 * myVerticalTreeView.setSelectionPath(thePath);
			 * myRelationPane.updateVisibleRelations(); } else {
			 * myVerticalTreeView
			 * .getTreeSynthesizer().setRootNode(topMostHierarchyNode
			 * .getNestedNode()); the line before was supposed to be sth. like:
			 * myVerticalTreeView.setTreeModel(topMostHierarchyNode);
			 * myVerticalTreeView.expandPath(thePath);
			 * myVerticalTreeView.setSelectionPath(thePath);
			 * myRelationPane.updateVisibleRelations(); } } }
			 */

			fireNodeClickedEvent(e);
		}

		/**
		 * Recieves an <code>VisibleRelationEvent</code> and sets the
		 * selectionPath. Sets the selection path in both, the horizontal and
		 * the vertical tree view and fires a new Relation Edit Event.
		 * 
		 * @param aEvent
		 *            the <code>VisibleRelationEvent</code>
		 */
		@Override
		public void onRelationEdit(final VisibleRelationEvent e) {
			VisibleRelation aRelation = e.getFirstVisibleRelation();
			myHorizontalTreeView
					.setSelectionPath(aRelation.getHorizontalPath());
			myVerticalTreeView.setSelectionPath(aRelation.getVerticalPath());
			fireRelationEditEvent(e);
		}

		/**
		 * Recieves a <code>VisibleRelationEvent</code>
		 * 
		 * @param aEvent
		 *            the <code>VisibleRelationEvent</code>
		 */
		@Override
		public void onRelationOver(final VisibleRelationEvent e) {
			VisibleRelation aRelation = e.getFirstVisibleRelation();
			myHorizontalTreeView
					.setSelectionPath(aRelation.getHorizontalPath());
			myVerticalTreeView.setSelectionPath(aRelation.getVerticalPath());

			fireRelationOverEvent(e);
		}

		/**
		 * Recieves an <code>VisibleRelationEvent</code>
		 * 
		 * @param aEvent
		 *            the <code>VisibleRelationEvent</code>
		 */
		@Override
		public void onRelationClicked(final VisibleRelationEvent aEvent) {

			TreePath vertPath = aEvent.getFirstVisibleRelation()
					.getVerticalPath();
			TreePath horizPath = aEvent.getFirstVisibleRelation()
					.getHorizontalPath();
			// boolean isex = myHorizontalTreeView.isExpanded(horizPath);
			// boolean iscol = myHorizontalTreeView.isCollapsed(horizPath);

			if (aEvent.getFirstVisibleRelation().getType() != RelationClass.REAL) {
				if ((myVerticalTreeView.isExpanded(vertPath) || myVerticalTreeView
						.getTreeModel().isLeaf(vertPath.getLastPathComponent())) == (myHorizontalTreeView
						.isExpanded(horizPath) || myHorizontalTreeView
						.getTreeModel()
						.isLeaf(horizPath.getLastPathComponent()))) {
					// toggle expand state of vertical path
					myVerticalTreeView.toggleExpandStateOfPath(vertPath);

					// toggle expand state of horizontal path
					myHorizontalTreeView.toggleExpandStateOfPath(horizPath);
				} else {
					if (myVerticalTreeView.isCollapsed(vertPath)) {
						myVerticalTreeView.toggleExpandStateOfPath(vertPath);
					}

					if (myHorizontalTreeView.isCollapsed(horizPath)) {
						myHorizontalTreeView.toggleExpandStateOfPath(horizPath);
					}
				}
			}
			fireRelationClickedEvent(aEvent);
		}

		/**
		 * Recieves an <code>VisibleRelationEvent</code>
		 * 
		 * @param aEvent
		 *            the <code>VisibleRelationEvent</code>
		 */
		@Override
		public void onRelationSelected(final VisibleRelationEvent aEvent) {
			// final VisibleRelation selectedRelations[] =
			// aEvent.getVisibleRelations();

			try {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						mHistoryMenu
								.addEntry(
										getMemento(),
										((DefaultHorizontalTreeView) myHorizontalTreeView)
												.getMemento(),
										((DefaultVerticalTreeView) myVerticalTreeView)
												.getMemento(),
										((DefaultRelationPane) getRelationPane())
												.getMemento());
					}
				});
			} catch (Exception ie) {

			}
		}

		/**
		 * Recieves an <code>TreeExpansionEvent</code> when a
		 * <code>TreeView</code> was collapsed.
		 * 
		 * @param e
		 *            the <code>TreeExpansionEvent</code>
		 * @see javax.swing.event#TreeExpansionEvent
		 * @see de.fhg.iao.matrixbrowser.widgets#TreeView
		 */
		@Override
		public void treeCollapsed(final TreeExpansionEvent e) {
			myRelationPane.updateVisibleRelations();
		}

		/**
		 * Recieves an <code>TreeExpansionEvent</code> when a
		 * <code>TreeView</code> was expanded.
		 * 
		 * @param e
		 *            the <code>TreeExpansionEvent</code>
		 * @see javax.swing.event#TreeExpansionEvent
		 * @see de.fhg.iao.matrixbrowser.widgets#TreeView
		 */
		@Override
		public void treeExpanded(final TreeExpansionEvent e) {
			myRelationPane.updateVisibleRelations();
		}

		@Override
		public void onNodeMenu(final VisibleNodeEvent e) {
			fireNodeMenuEvent(e);
		}
	}

	/**
	 * <code>LayoutManager</code> for the various views of a
	 * <code>MatrixBrowserPanel</code>.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.12 $
	 * @see java.awt.LayoutManager
	 */
	protected class MatrixBrowserPanelLayout implements LayoutManager {

		/** Holds the last layouted size */
		protected Dimension myLastSize = null;

		/** The minimum size for the <code>MatrixBrowserPanel</code> */
		protected Dimension myMinimumSize = null;

		/**
		 * Null constructor.
		 */
		protected MatrixBrowserPanelLayout() {
			myMinimumSize = new Dimension(0, 0);
		}

		/**
		 * Does nothing.
		 * 
		 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
		 *      java.awt.Component)
		 */
		@Override
		public void addLayoutComponent(final String name, final Component comp) {
		}

		/**
		 * Lays out the <code>MatrixBrowserPanel</code>
		 * 
		 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
		 */
		@Override
		public void layoutContainer(final Container parent) {
			Dimension parentSize = parent.getSize();
			Insets insets = parent.getInsets();
			parentSize.width -= (insets.left + insets.right);
			parentSize.height -= (insets.top + insets.bottom);

			if (!parentSize.equals(myLastSize)) {
				Dimension newCloseUpViewSize = null;

				if ((myLastSize == null) || (myLastSize.width <= 0)) {
					// first time we were called -> set initial closeup view
					// size
					newCloseUpViewSize = new Dimension(
							Math.round(parentSize.width / 4),
							Math.round((2 * parentSize.height) / 7));
				} else {
					// resize the closeup view relatively
					newCloseUpViewSize = ((JComponent) myNodeCloseUpView)
							.getSize();
					newCloseUpViewSize.width = Math
							.round((newCloseUpViewSize.width * parentSize.width)
									/ myLastSize.width);
					newCloseUpViewSize.height = Math
							.round((newCloseUpViewSize.height * parentSize.height)
									/ myLastSize.height);
				}

				int virtBorder = myRelationPane.getVirtualBorderWidth();

				((JComponent) myRelationPane).setBounds(0, 0, parentSize.width,
						parentSize.height);
				((JComponent) myNodeCloseUpView).setBounds(0, 0,
						newCloseUpViewSize.width, newCloseUpViewSize.height);
				((JComponent) myHorizontalTreeView).setBounds(
						newCloseUpViewSize.width, 0, parentSize.width
								- newCloseUpViewSize.width,
						newCloseUpViewSize.height - virtBorder);
				((JComponent) myVerticalTreeView).setBounds(0,
						newCloseUpViewSize.height, newCloseUpViewSize.width
								- virtBorder, parentSize.height
								- newCloseUpViewSize.height);
				myRelationPane.updateVisibleRelations();

				myLastSize = parentSize;
			}
		}

		/**
		 * @return the minimum layout size
		 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
		 */
		@Override
		public Dimension minimumLayoutSize(final Container parent) {
			return myMinimumSize;
		}

		/**
		 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
		 */
		@Override
		public Dimension preferredLayoutSize(final Container parent) {
			return myMinimumSize;
		}

		/**
		 * Does nothing.
		 * 
		 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
		 */
		@Override
		public void removeLayoutComponent(final Component comp) {
		}
	}

	/**
	 * The <code>MouseEvent</code> handler of the
	 * <code>MatrixBrowserPanel</code>
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.12 $
	 */
	protected class MouseEventHandler
			implements
				MouseListener,
				MouseMotionListener {

		/** holds the actual cursor type */
		private Cursor myStoreCurrentCursor = null;

		/** Constant for no resizing */
		private final byte NORESIZE = -1;

		/** Constant for resizing in east direction */
		private final byte RESIZEMODEEAST = 1;

		/** Constant for resizing in south direction */
		private final byte RESIZEMODESOUTH = 2;

		/** Constant for resizing in southeast direction */
		private final byte RESIZEMODESOUTHEAST = 3;

		/**
		 * The current resize mode eg. RESIZEMODESOUTHEAST, RESIZEMODESOUTH
		 * etc..
		 */
		private byte myResizeMode = NORESIZE;

		/** Is the user currently dragging */
		private boolean myIsDragging = false;

		/**
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(final MouseEvent e) {
		}

		/**
		 * Resizes the <code>NodeCloseUpView</code> when dragging.
		 * 
		 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseDragged(final MouseEvent e) {
			// Mouse was dragged, so look if the panel should be resized.
			// Also check the min/max constraints and layout all its components.
			myIsDragging = true;
			if (myResizeMode != NORESIZE) {

				int newWidth = ((JComponent) myNodeCloseUpView).getBounds().width;
				int newHeight = ((JComponent) myNodeCloseUpView).getBounds().height;

				switch (myResizeMode) {
					case RESIZEMODEEAST :
						newWidth = e.getX();

						break;

					case RESIZEMODESOUTH :
						newHeight = e.getY();

						break;

					case RESIZEMODESOUTHEAST :
						newWidth = e.getX();
						newHeight = e.getY();

						break;
				}

				// check minimum size of the new width/height
				if (newWidth < ((JComponent) myNodeCloseUpView)
						.getMinimumSize().width) {
					newWidth = NODECLOSEUPVIEW_MINWIDTH;
				}

				if (newHeight < ((JComponent) myNodeCloseUpView)
						.getMinimumSize().height) {
					newHeight = NODECLOSEUPVIEW_MINHEIGHT;
				}

				// check maximum size of the new width/height
				if (newWidth > (MatrixBrowserPanel.this.getSize().width - RELATIONPANE_MINWIDTH)) {
					newWidth = MatrixBrowserPanel.this.getSize().width
							- RELATIONPANE_MINWIDTH;
				}

				if (newHeight > (MatrixBrowserPanel.this.getSize().height - RELATIONPANE_MINHEIGHT)) {
					newHeight = MatrixBrowserPanel.this.getSize().height
							- RELATIONPANE_MINHEIGHT;
				}

				int vertnewWidth = newWidth
						- MatrixBrowserPanel.this.myRelationPane
								.getVirtualBorderWidth();
				int horiznewHeigth = newHeight
						- MatrixBrowserPanel.this.myRelationPane
								.getVirtualBorderWidth();

				((JComponent) myNodeCloseUpView).setBounds(0, 0, newWidth,
						newHeight);
				((JComponent) myHorizontalTreeView).setBounds(newWidth, 0,
						MatrixBrowserPanel.this.getBounds().width - newWidth,
						horiznewHeigth);
				((JComponent) myVerticalTreeView).setBounds(0, newHeight,
						vertnewWidth,
						MatrixBrowserPanel.this.getBounds().height - newHeight);
				MatrixBrowserPanel.this.validate();
			} else {

			}
		}

		/**
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseEntered(final MouseEvent e) {
			// mouseMoved(e);
		}

		/**
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseExited(final MouseEvent e) {
			// if no dragging occured, set the cursor to the default cursor
			// because the application has lost the mouse focus
			if ((!myIsDragging) && (myStoreCurrentCursor != null)) {
				setCursor(myStoreCurrentCursor);
				myStoreCurrentCursor = null;
				myResizeMode = NORESIZE;
			}
		}

		/**
		 * Checks if the mouse is in the resizing areas of the
		 * <code>NodeCloseUpView</code> and sets specific cursors.
		 * 
		 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseMoved(final MouseEvent e) {
			// look if mouse moved in a critical section which is used for
			// resizing
			// the
			// net view
			final int RESIZE_AREA = 8;
			int x = e.getX();
			int lineX = ((JComponent) myNodeCloseUpView).getBounds().width;
			int y = e.getY();
			int lineY = ((JComponent) myNodeCloseUpView).getBounds().height;

			if ((x < (lineX + RESIZE_AREA)) && (x > (lineX - RESIZE_AREA))
					&& (y < (lineY + RESIZE_AREA))
					&& (y > (lineY - RESIZE_AREA))) {
				if (myStoreCurrentCursor == null) {
					myStoreCurrentCursor = getCursor();
				}

				setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
				myResizeMode = RESIZEMODESOUTHEAST;
			} else if ((x < (lineX + RESIZE_AREA))
					&& (x > (lineX - RESIZE_AREA)) && (y < lineY)) {
				if (myStoreCurrentCursor == null) {
					myStoreCurrentCursor = getCursor();
				}

				setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				myResizeMode = RESIZEMODEEAST;
			} else if ((x < lineX)
					&& ((y < (lineY + RESIZE_AREA)) && (y > (lineY - RESIZE_AREA)))) {
				if (myStoreCurrentCursor == null) {
					myStoreCurrentCursor = getCursor();
				}

				setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
				myResizeMode = RESIZEMODESOUTH;
			} else if (myStoreCurrentCursor != null) {
				setCursor(myStoreCurrentCursor);
				myStoreCurrentCursor = null;
				myResizeMode = NORESIZE;
			}

		}

		/**
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(final MouseEvent e) {
			myIsDragging = false;
			setCursor(myStoreCurrentCursor);
			myStoreCurrentCursor = null;
			myResizeMode = NORESIZE;
		}

	}

	@Override
	public void onTreeModelChanged(final TreeModelChangedEvent aE) {
		myRelationPane.updateVisibleRelations();
	}

	public void removeEmptyNodes() {
		/*
		 * treeCombinator.removeEmptyNodes(getShowEmptyNodesInVerticalTree(),
		 * getShowEmptyNodesInHorizontalTree()); fireTreeModelChangedEvent(new
		 * TreeModelChangedEvent(this));
		 * myRelationPane.updateVisibleRelations();
		 */
	}

	public TreeCombinator getTreeCombinator() {
		return treeCombinator;
	}

	/**
	 * gets a screenshot of the currently displayed relations
	 * 
	 * @return the captured offScreebRelationBuffer of DefaultRelationPane
	 */
	public BufferedImage getCapturedImage() {
		double scale = 1.0;
		// schotti: never read.
		// Rectangle rect = ((DefaultRelationPane)
		// getRelationPane()).getBounds();
		BufferedImage thumbImage;
		Image buffer;
		buffer = ((DefaultRelationPane) getRelationPane()).capture();
		thumbImage = new BufferedImage((int) (scale * buffer.getWidth(null)),
				(int) (scale * buffer.getHeight(null)),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = thumbImage.createGraphics();

		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(buffer, 0, 0, thumbImage.getWidth(),
				thumbImage.getHeight(), null);
		thumbImage.flush();
		graphics2D.dispose();
		return thumbImage;
	}

	/**
	 * gets the offScreenRelationBuffer of DefaultRelationPane and saves its
	 * content to a jpeg file called "CAPTURED"+i+".jpeg"
	 * 
	 * @param i
	 *            the captured image's number
	 */
	/*
	 * public void capture(int i) { log.debug("start"); double scale = 0.75;
	 * Rectangle rect = ((DefaultRelationPane) getRelationPane()).getBounds();
	 * BufferedImage thumbImage; Image buffer = ((DefaultRelationPane)
	 * getRelationPane()).capture(); thumbImage = new BufferedImage((int) (scale
	 * buffer.getWidth(null)), (int) (scale buffer.getHeight(null)),
	 * BufferedImage.TYPE_INT_RGB); Graphics2D graphics2D =
	 * thumbImage.createGraphics();
	 * graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	 * RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	 * graphics2D.drawImage(buffer, 0, 0, thumbImage.getWidth(), thumbImage
	 * .getHeight(), null); try { BufferedOutputStream out = new
	 * BufferedOutputStream( new FileOutputStream("CAPTURED"+i+".jpeg"));
	 * JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	 * JPEGEncodeParam param = encoder .getDefaultJPEGEncodeParam(thumbImage);
	 * param.setQuality(1.0f, false); encoder.setJPEGEncodeParam(param);
	 * encoder.encode(thumbImage); } catch (Exception e) { e.printStackTrace();
	 * } log.debug("stop"); }
	 */

	public MatrixBrowserPanelMemento getMemento() {
		return new MatrixBrowserPanelMemento(myManager, myHorizontalTreeView,
				myVerticalTreeView, myEditableState);
	}

	public void setMemento(final MatrixBrowserPanelMemento memento) {
		setEditable(memento.myEditableState);
		((TreeProxyManager) getHorizontalTreeView().getTreeSynthesizer())
				.setRootNode(memento.horizontalRootNode);
		((TreeProxyManager) getVerticalTreeView().getTreeSynthesizer())
				.setRootNode(memento.verticalRootNode);
		((AbstractTreeView) myVerticalTreeView)
				.resetExpandedPaths(memento.verticalExpandedPath);
		((AbstractTreeView) myHorizontalTreeView)
				.resetExpandedPaths(memento.horizontalExpandedPath);
	}

	/**
	 * @author schaal Memento for this class - all relevant class variables for
	 *         the history are saved in this class (used by getMemento and
	 *         setMemento)
	 */
	public class MatrixBrowserPanelMemento {

		TreeManager myTreeManager;

		VerticalTreeView myVerticalTreeView;

		HorizontalTreeView myHorizontalTreeView;

		Node horizontalRootNode[], verticalRootNode[];

		HashSet<ID> verticalExpandedPath, horizontalExpandedPath;

		boolean myEditableState;

		public MatrixBrowserPanelMemento(final TreeManager aTreeManager,
				final HorizontalTreeView aHorizontalTreeView,
				final VerticalTreeView aVerticalTreeView, final boolean anEditableState) {
			myTreeManager = aTreeManager;
			myHorizontalTreeView = aHorizontalTreeView;
			myVerticalTreeView = aVerticalTreeView;
			horizontalRootNode = ((TreeProxyManager) myHorizontalTreeView
					.getTreeSynthesizer()).getRootNode().clone();
			verticalRootNode = ((TreeProxyManager) myVerticalTreeView
					.getTreeSynthesizer()).getRootNode().clone();
			myEditableState = anEditableState;
			verticalExpandedPath = (HashSet<ID>) ((AbstractTreeView) myVerticalTreeView)
					.saveExpandedPaths().clone();
			horizontalExpandedPath = (HashSet<ID>) ((AbstractTreeView) myHorizontalTreeView)
					.saveExpandedPaths().clone();
		}
	}
}