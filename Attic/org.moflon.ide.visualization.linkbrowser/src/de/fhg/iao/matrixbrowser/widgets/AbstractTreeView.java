/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * AbstractTreeView.java,v 1.21 2004/05/19 14:41:56 kookaburra Exp $ Created on
 * 26.02.02
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.EncapsulatingVisibleNode;
import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.TreeView;
import de.fhg.iao.matrixbrowser.UIResourceRegistry;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEvent;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEventListener;
import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.matrixbrowser.VisibleNodeEvent;
import de.fhg.iao.matrixbrowser.VisibleNodeEventListener;
import de.fhg.iao.matrixbrowser.context.AbstractTreeSynthesizer;
import de.fhg.iao.matrixbrowser.context.TreeModelChangedEvent;
import de.fhg.iao.matrixbrowser.context.TreeModelChangedListener;
import de.fhg.iao.matrixbrowser.context.TreeProxyManager;
import de.fhg.iao.matrixbrowser.context.TreeSynthesizerProxy;
import de.fhg.iao.matrixbrowser.model.ExternalICommandImplementation;
import de.fhg.iao.matrixbrowser.model.ICommand;
import de.fhg.iao.matrixbrowser.model.elements.MBModelNode;
import de.fhg.iao.matrixbrowser.model.elements.Node;
import de.fhg.iao.matrixbrowser.model.elements.NodeTransferable;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.matrixbrowser.model.elements.TransferableNode;
import de.fhg.iao.swt.util.uniqueidentifier.ID;
import de.fhg.iao.swt.util.uniqueidentifier.IDPool;
import de.fhg.iao.swt.xswing.JContextMenu;

/**
 * Default abstract implementation for the <code>TreeView</code>s. Subclasses
 * are only implementing special features, which are needed to define behaviour
 * of horizontal and vertical views.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @author <a href=mailto:cs@christian-schott.de>Christian Schott</a>
 * @version $Revision: 1.15 $
 */
public abstract class AbstractTreeView extends JPanel implements TreeView,
		UIResourceRegistryEventListener {

	private static final Logger log = Logger.getLogger(AbstractTreeView.class);
	/**
	 * Comment for <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The height of horizontal TreeView Rows.
	 */
	private static final int HORIZONTAL_ROW_HEIGHT = 25;
	/**
	 * the height of the vertical TreeView Rows.
	 */
	private static final int VERTICAL_ROW_HEIGHT = 18;
	/**
	 * Maximum number of characters to show in a Message Box informing of an
	 * exception.
	 */
	private static final int MAX_ERROR_DSP_LENGTH = 500;
	/** The <code>JContextMenu </code> property. */
	private JContextMenu myContextMenu = null;

	/** Holds the editable state. */
	private boolean myEditableState = false;

	/** Saves the last mouse position, when clicked. */
	private Point myLastMouseClickPosition = null;

	/** A list of event listeners. */
	private EventListenerList myListenerList = new EventListenerList();

	/** Reference to the main <code>MatrixBrowserPanel</code>. */
	private MatrixBrowserPanel myMatrixBrowserPanel = null;

	/** The mouse handler of this <code>TreeView</code>. */
	private MouseEventHandler myMouseHandler = null;

	/** The underlying tree implementation. */
	private JTree myTree = null;

	/** A special <code>JScrollBar</code> for the tree. */
	protected JScrollBar myTreeScrollBar = new JScrollBar();

	/** A <code>JScrollPane </code> which enables scrolling of the tree. */
	protected JScrollPane myTreeScrollPane = new JScrollPane();

	/**
	 * A <code>TreeModelChangedHandler</code> handling all
	 * TreeModelChangedEvents.
	 */
	private TreeModelChangedHandler myTreeModelChangedHandler = null;

	/** The <code>AbstractTreeSynthesizer</code> for this <code>TreeView</code>. */
	private AbstractTreeSynthesizer myTreeSynthesizer = null;

	/** A private <code>DefaultTreeModel</code> used for caching. */
	private DefaultTreeModel myTreeModel = null;

	/**
	 * Reference to this <code>TreeView</code>'s DefaultRelationRenderer.
	 */
	private DefaultRelationRenderer as = new DefaultRelationRenderer();
	/**
	 * A Boolean to indicate whether the TreeView has to be regenerated from the
	 * model.
	 */
	private boolean reload = false;

	/**
	 * Constructs a <code>TreeView</code> given the main
	 * <code>MatrixBrowserPanel</code>.
	 * 
	 * @param aMatrixBrowserPanel
	 *            reference to the <code>MatrixBrowserPanel</code>, which is
	 *            responsible for this <code>TreeView</code>
	 */
	public AbstractTreeView(final MatrixBrowserPanel aMatrixBrowserPanel) {
		this(aMatrixBrowserPanel, null);
		UIResourceRegistry.getInstance().addUIResourceRegistryEventListener(
				this);

	}

	/**
	 * Constructs a <code>TreeView</code> given the main
	 * <code>MatrixBrowserPanel</code> and a underlying <code>JTree</code>.
	 * 
	 * @param aMatrixBrowserPanel
	 *            reference to the <code>MatrixBrowserPanel</code>, which is
	 *            responsible for this <code>TreeView</code>
	 * @param aTree
	 *            The underlying tree
	 */
	public AbstractTreeView(final MatrixBrowserPanel aMatrixBrowserPanel,
			final JTree aTree) {
		super();
		myMatrixBrowserPanel = aMatrixBrowserPanel;
		myTreeModelChangedHandler = new TreeModelChangedHandler();
		// the next row is needed for updating
		// the hidden nodes
		myMatrixBrowserPanel
				.addTreeModelChangedListener(myTreeModelChangedHandler);
		initialize(aTree);
		myTree.setRootVisible(false);
		myTree.setShowsRootHandles(true);
		myTree.addMouseListener(new MouseEventHandler());
		myTree.setTransferHandler(new TreeTransferHandler() {

			/**
			 * Comment for <code>serialVersionUID</code>
			 */
			private static final long serialVersionUID = 1L;

			public boolean importData(final JComponent aComponent,
					final Transferable aTransferable) {
				TreePath[] tempPath = getExpandedPaths();

				// clear old hashsets of treeproxymanager
				if (getMatrixBrowserPanel().getHorizontalTreeView()
						.getTreeSynthesizer() instanceof TreeProxyManager) {
					((TreeProxyManager) getMatrixBrowserPanel()
							.getHorizontalTreeView().getTreeSynthesizer())
							.setHashSet(null);
				}
				if (getMatrixBrowserPanel().getVerticalTreeView()
						.getTreeSynthesizer() instanceof TreeProxyManager) {
					((TreeProxyManager) getMatrixBrowserPanel()
							.getVerticalTreeView().getTreeSynthesizer())
							.setHashSet(null);
				}
				if (aComponent instanceof JTree) {
					JTree tree = (JTree) aComponent;

					try {
						TransferableNode[] transferableNodes = (TransferableNode[]) aTransferable
								.getTransferData(NodeTransferable.transferableNodeFlavor);
						// transferableNodes is greater than one only if
						// multiple nodes
						// were selected using STRG, not if just one node (and
						// its children
						// not explicitly selected) is dragged
						if (transferableNodes.length > 1) {
							// multiple DnD
							if (!(myTreeSynthesizer instanceof TreeProxyManager)) {
								myTreeSynthesizer = getMatrixBrowserPanel()
										.getTreeManager()
										.createTreeProxyManager();
							}
							setContextMenu(null);
							setContextMenu(createDefaultContextMenu());
							TreeProxyManager proxy = (TreeProxyManager) myTreeSynthesizer;
							// proxy.removeAllSubTreeSynthesizers();
							// proxy.removeAllSubTreeSynthesizers();
							/*
							 * for (int i = 0; i < transferableNodes.length;
							 * i++) { AbstractTreeSynthesizer treeSynthesizer =
							 * getMatrixBrowserPanel() .getTreeManager()
							 * .createSortingTreeSynthesizer();
							 * treeSynthesizer.setRootNode
							 * (getMatrixBrowserPanel()
							 * .getTreeManager().getModel().
							 * getNode(transferableNodes[i].getID()));
							 * proxy.addSubTreeSynthesizer(TreeSynthesizer); }
							 */
							// change transferable nodes to normal nodes
							// to use them in TreeProxyManager
							Node[] tempNode = new Node[transferableNodes.length];
							for (int i = 0; i < transferableNodes.length; i++) {
								tempNode[i] = new MBModelNode();
								tempNode[i] = getMatrixBrowserPanel()
										.getTreeManager().getModel().getNode(
												transferableNodes[i].getID());
							}
							proxy.setRootNode(tempNode);
							setTreeSynthesizer(proxy);
							getTree().setRootVisible(false);
						} else {
							// single DnD
							if (myTreeSynthesizer instanceof TreeSynthesizerProxy) {
								TreeSynthesizerProxy aProxy = (TreeSynthesizerProxy) myTreeSynthesizer;
								if (myTreeSynthesizer instanceof TreeProxyManager) {
									((TreeProxyManager) aProxy)
											.setRootNode(getMatrixBrowserPanel()
													.getTreeManager()
													.getModel()
													.getNode(
															transferableNodes[0]
																	.getID()));
								} else {
									AbstractTreeSynthesizer aTreeSynthesizer = getMatrixBrowserPanel()
											.getTreeManager()
											.createSortingTreeSynthesizer();
									aTreeSynthesizer
											.setRootNode(getMatrixBrowserPanel()
													.getTreeManager()
													.getModel()
													.getNode(
															transferableNodes[0]
																	.getID()));
									aProxy
											.setSubTreeSynthesizer(aTreeSynthesizer);
								}
							} else {
								Node origNode = getMatrixBrowserPanel()
										.getTreeManager().getModel().getNode(
												transferableNodes[0].getID());
								myTreeSynthesizer.setRootNode(origNode);
								getTree().setRootVisible(true);
							}
						}
						if (myTreeModel == null) {
							myTreeModel = new DefaultTreeModel(
									myTreeSynthesizer.getVisibleTree());
							tree.setModel(myTreeModel);
						} else {
							myTreeModel.setRoot(myTreeSynthesizer
									.getVisibleTree());
						}

						tree.expandRow(0);
						for (int i = 0; i < tempPath.length; i++) {
							expandPath(tempPath[i]);
						}
						getMatrixBrowserPanel().resetHistory();
						getMatrixBrowserPanel().getRelationPane()
								.updateVisibleRelations();
						return true;
					} catch (UnsupportedFlavorException ufe) {
						Runnable except = new DisplayException(ufe, aComponent);
						SwingUtilities.invokeLater(except);
					} catch (IOException ioe) {
						Runnable except = new DisplayException(ioe, aComponent);
						SwingUtilities.invokeLater(except);
					}
				}

				return false;
			}
		});
		myTree.addTreeExpansionListener(new JCompListener()); // //////////Marwan
		myTree.setAutoscrolls(true);
		myTree.setDragEnabled(true);
	}

	/**
	 * Adds a <code>AdjustmentListener</code> to this <code>TreeView</code>.
	 * 
	 * @param aListener
	 *            the <code>AdjustmentListener</code> to add
	 * @see javax.swing.JScrollBar
	 *      #addAdjustmentListener(java.awt.event.AdjustmentListener)
	 */
	public final void addAdjustmentListener(final AdjustmentListener aListener) {
		myTreeScrollBar.addAdjustmentListener(aListener);
	}

	/**
	 * Adds a <code>VisibleNodeEventListener</code>, that will be notified if
	 * someone clicks on a node in this <code>TreeView</code>.
	 * 
	 * @param aVisibleNodeEventListener
	 *            the <code>VisibleNodeEventListener</code> to be notified of
	 *            <code>NodeEvent</code>s
	 * @see de.fhg.iao.matrixbrowser.EncapsulatingVisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.EncapsulatingVisibleNodeEvent
	 */
	public final void addVisibleNodeEventListener(
			final VisibleNodeEventListener aVisibleNodeEventListener) {
		myListenerList.add(VisibleNodeEventListener.class,
				aVisibleNodeEventListener);
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
	 * @see javax.swing.JTree
	 *      #addTreeExpansionListener(javax.swing.event.TreeExpansionListener)
	 */

	public final void addTreeExpansionListener(
			final TreeExpansionListener aTreeExpansionListener) {

		myTree.addTreeExpansionListener(aTreeExpansionListener);
	}

	/**
	 * Checks if the <code>TreeView</code> currently shows a specific
	 * <code>VisibleNode</code>.
	 * 
	 * @param aNode
	 *            the <code>VisibleNode</code> for which to check whether it is
	 *            shown by this <code>TreeView</code>
	 * @return true if the given <code>VisibleNode</code> is actually shown
	 * @see de.fhg.iao.matrixbrowser.VisibleNode
	 */
	public final boolean containsVisibleNode(final VisibleNode aNode) {
		TreeModel model = this.getTreeModel();
		EncapsulatingVisibleNode root = (EncapsulatingVisibleNode) model
				.getRoot();

		return root.getPath() != null;
	}

	/**
	 * @return the default context menu
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 */
	protected final JContextMenu createDefaultContextMenu() {
		JContextMenu contextMenu = new JContextMenu(UIResourceRegistry
				.getInstance().TREEVIEW_CONTEXTMENU_TITLE, this); //$NON-NLS-1$

		if (getEditable()) {
			contextMenu.add(new AddNodeAction());
			contextMenu.add(new AddParentNodeAction());
			contextMenu.add(new DeleteNodeAction());
			contextMenu.add(new EditNodeAction());
		}
		return contextMenu;
	}

	/**
	 * Expands the given <code>TreePath</code> in the tree.
	 * 
	 * @param aPath
	 *            the <code>TreePath</code> to expand
	 * @see javax.swing.tree#TreePath
	 * @see javax.swing.JTree#expandPath(javax.swing.tree.TreePath)
	 */
	public final void expandPath(final TreePath aPath) {
		myTree.expandPath(aPath);
	}

	private void fireNodeMenuEvent(final VisibleNodeEvent e) {
		Object[] listeners = myListenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleNodeEventListener.class) {
				((VisibleNodeEventListener) listeners[i + 1]).onNodeMenu(e);
			}
		}
	}

	/**
	 * Fires a <code>IAONodeEvent</code> by calling 'onNodeEditEvent'.
	 * 
	 * @param e
	 *            a <code>IAONodeEvent</code> to fire
	 * @see de.fhg.iao.IAONodeEvent
	 */
	private void fireNodeEditEvent(final VisibleNodeEvent e) {
		Object[] listeners = myListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleNodeEventListener.class) {
				((VisibleNodeEventListener) listeners[i + 1]).onNodeEdit(e);
			}
		}
	}

	/**
	 * Fires a <code>IAONodeEvent</code> by calling 'onNodeOverEvent'.
	 * 
	 * @param e
	 *            a <code>IAONodeEvent</code> to fire
	 * @see de.fhg.iao.IAONodeEvent
	 */
	private void fireNodeOverEvent(final VisibleNodeEvent e) {
		Object[] listeners = myListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleNodeEventListener.class) {
				((VisibleNodeEventListener) listeners[i + 1]).onNodeOver(e);
			}
		}
	}

	/**
	 * Fires a <code>IAONodeEvent</code> by calling 'onNodeSelectedEvent'.
	 * 
	 * @param e
	 *            a <code>IAONodeEvent</code> to fire
	 * @see de.fhg.iao.IAONodeEvent
	 */
	private void fireNodeClickedEvent(final VisibleNodeEvent e) {
		Object[] listeners = myListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleNodeEventListener.class) {

				((VisibleNodeEventListener) listeners[i + 1]).onNodeClicked(e);
			}
		}
	}

	/**
	 * Gets the trees <code>TreeCellEditor</code>.
	 * 
	 * @return the actual <code>TreeCellEditor</code>
	 * @see javax.swing.JTree#getCellEditor()
	 */
	public final TreeCellEditor getCellEditor() {
		return myTree.getCellEditor();
	}

	/**
	 * Gets the trees <code>TreeCellRenderer</code>.
	 * 
	 * @return the actual <code>TreeCellRenderer</code>
	 * @see javax.swing.JTree#getCellRenderer()
	 */
	public final TreeCellRenderer getCellRenderer() {
		return myTree.getCellRenderer();
	}

	/**
	 * Gets the closest <code>TreePath</code> to the given position.
	 * 
	 * @param aX
	 *            x position
	 * @param aY
	 *            y position
	 * @return the closest <code>TreePath</code> to the given position
	 * @see javax.swing.JTree#getClosestPathForLocation(int, int)
	 */
	public abstract TreePath getClosestPathForLocation(final int aX,
			final int aY);

	/*
	 * { return myTree.getClosestPathForLocation(aX, aY -
	 * myTreeScrollPane.getViewport().getViewPosition().y); }
	 */

	/**
	 * Returns the <code>JContextMenu</code> of this <code>TreeView</code>.
	 * Returns a default menu, if the actual one is <code>null</code>.
	 * 
	 * @return the actual <code>JContextMenu</code> of this
	 *         <code>TreeView</code>
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 */
	public final JContextMenu getContextMenu() {
		if (myContextMenu == null) {
			myContextMenu = createDefaultContextMenu();
		}

		return myContextMenu;
	}

	/**
	 * Gets this <code>TreeView</code> editable state.
	 * 
	 * @return <code>true</code> if its editable/ <code>false</code> if not
	 */
	public final boolean getEditable() {
		return myEditableState;
	}

	// /**
	// * @return an array with expanded <code>TreePath</code>
	// */
	// public void restoreExpandedPaths(Vector anExpandedPathVector) {
	// if (anExpandedPathVector == null) return;
	// myTree.setExpandsSelectedPaths(true);
	// for (int i = 0, size = anExpandedPathVector.size(); i < size; i++) {
	// TreePath path = (TreePath) anExpandedPathVector.get(i);
	// myTree.expandPath(path);
	// /*
	// * log.debug("Row: " + myTree.getRowForPath(path));
	// * for (int j =
	// * 0; j < path.getPathCount(); j++) { log.debug("Row: \t" +
	// * ((VisibleNode) path.getPath()[j]).toString()); }
	// */
	// }
	//
	// }

	/**
	 * @return an array with expanded <code>TreePath</code>
	 */
	public final TreePath[] getExpandedPaths() {
		ArrayList<TreePath> expandedPaths = new ArrayList<TreePath>();
		for (int i = 0; i < myTree.getVisibleRowCount(); i++) {
			if (myTree.isExpanded(i)) {
				expandedPaths.add(myTree.getPathForRow(i));
			}
		}

		TreePath[] result = new TreePath[expandedPaths.size()];
		expandedPaths.toArray(result);

		return result;
	}

	/**
	 * @return a <code>Point</code> containing the last position of the mouse,
	 *         when it was clicked.
	 * @see java.awt.Point
	 */
	public final Point getLastMouseClickPosition() {
		return myLastMouseClickPosition;
	}

	/**
	 * @return the <code>MatrixBrowserPanel</code>, which created/controls this
	 *         <code>TreeView</code>
	 */
	public final MatrixBrowserPanel getMatrixBrowserPanel() {
		return myMatrixBrowserPanel;
	}

	/**
	 * Returns the Bounds of the <code>TreePath</code> position.
	 * 
	 * @param aPath
	 *            <code>TreePath</code> of the position
	 * @return the bounds of the given <code>TreePath</code>
	 */
	public final Rectangle getPathBounds(final TreePath aPath) {
		Rectangle bounds = myTree.getPathBounds(aPath);

		if (bounds == null) {
			return null;
		}

		bounds.x = bounds.x
				- myTreeScrollPane.getViewport().getViewPosition().x;
		bounds.y = bounds.y
				- myTreeScrollPane.getViewport().getViewPosition().y;

		return bounds;
	}

	/**
	 * Returns the <code>TreePath</code> for the given row.
	 * 
	 * @param aRow
	 *            the Row
	 * @return the <code>TreePath</code> of the given row
	 * @see javax.swing.JTree#getPathForRow(int)
	 */
	public final TreePath getPathForRow(final int aRow) {
		return myTree.getPathForRow(aRow);
	}

	/**
	 * Returns the row height of the tree cells.
	 * 
	 * @return the current row height
	 * @see javax.swing.JTree#getRowHeight()
	 */
	public final int getRowHeight() {
		return myTree.getRowHeight();
	}

	/**
	 * Returns the y offset from possible y scrolling.
	 * 
	 * @return the y offset from x scrolling operations
	 */
	public final int getScrollOffsetX() {
		return myTreeScrollPane.getViewport().getViewPosition().x;
	}

	/**
	 * Returns the x offset from possible x scrolling.
	 * 
	 * @return the x offset from x scrolling operations
	 */
	public final int getScrollOffsetY() {
		return myTreeScrollPane.getViewport().getViewPosition().y;
	}

	/**
	 * Gets the <code>ScrollPane</code> for this <code>TreeView</code>.
	 * 
	 * @return the <code>TreeView</code>'s <code>JScrollPane</code>.
	 */
	public final JScrollPane getScrollPane() {
		return myTreeScrollPane;
	}

	/**
	 * Sets the x offset of possible x scrolling.
	 * 
	 * @param x
	 *            the x offset to set
	 */
	public final void setScrollOffsetX(final int x) {
		myTreeScrollPane.getHorizontalScrollBar().setValue(x);
		repaint();
	}

	/**
	 * Sets the y offset of possible y scrolling.
	 * 
	 * @param y
	 *            the y offset to set
	 */
	public final void setScrollOffsetY(final int y) {
		myTreeScrollPane.getVerticalScrollBar().setValue(y);
		repaint();
	}

	/**
	 * Get the JTree shown in this <code>TreeView</code>.
	 * 
	 * @return the underlying tree
	 */
	public final JTree getTree() {
		return myTree;
	}

	/**
	 * Gets the underlying <code>TreeModel</code>.
	 * 
	 * @return the underlying <code>TreeModel</code>
	 * @see javax.swing.tree.TreeModel
	 * @see javax.swing.JTree#getModel()
	 */
	public final TreeModel getTreeModel() {
		return myTree.getModel();
	}

	/**
	 * Initializes this <code>TreeView</code>, with the given tree. If the tree
	 * is <code>null</code>, a default implementation for the tree is chosen.
	 * Implement this method to parameterize the <code>TreeView</code> for
	 * horizontal vertical alignment.
	 * 
	 * @param aTree
	 *            the underlying tree, which can be <code>null</code>
	 */
	protected abstract void initialize(JTree aTree);

	/**
	 * Gets the collapsed state of a <code>TreePath</code>.
	 * 
	 * @param aPath
	 *            The TreePath for which to check whether it is collapsed.
	 * @return <code>true</code> if given Path is collapsed.
	 * @see javax.swing.tree.TreePath
	 * @see javax.swing.JTree#isCollapsed(javax.swing.tree.TreePath)
	 */
	public final boolean isCollapsed(final TreePath aPath) {
		return myTree.isCollapsed(aPath);
	}

	/**
	 * Gets the expanded state of a <code>TreePath</code>.
	 * 
	 * @param aPath
	 *            TheTreePath for which to check whether it is expanded.
	 * @return <code>true</code> if given path is expanded.
	 * @see javax.swing.tree.TreePath
	 * @see javax.swing.JTree#isExpanded(javax.swing.tree.TreePath)
	 */
	public final boolean isExpanded(final TreePath aPath) {
		return myTree.isExpanded(aPath);
	}

	/**
	 * Removes a <code>AdjustmentListener</code> from the event listener list.
	 * 
	 * @param aListener
	 *            the listener to remove
	 * @see javax.swing.JScrollBar
	 *      #addAdjustmentListener(java.awt.event.AdjustmentListener)
	 */
	public final void removeAdjustmentListener(
			final AdjustmentListener aListener) {
		myTreeScrollBar.removeAdjustmentListener(aListener);
	}

	/**
	 * Removes a <code>VisibleNodeEventListener</code> from the event listener
	 * list.
	 * 
	 * @param aListener
	 *            the <code>VisibleNodeEventListener</code> to be removed
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	public final void removeVisibleNodeEventListener(
			final VisibleNodeEventListener aListener) {
		myListenerList.remove(VisibleNodeEventListener.class, aListener);
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
	public final void removeTreeExpansionListener(
			final TreeExpansionListener aTreeExpansionListener) {
		myTree.removeTreeExpansionListener(aTreeExpansionListener);
	}

	/**
	 * {@inheritDoc} Sets the Bounds of the tree view and updates the increment
	 * values of the scrollbars.
	 * 
	 * @see java.awt.Component#setBounds(int, int, int, int)
	 */
	public final void setBounds(final int x, final int y, final int width,
			final int height) {
		super.setBounds(x, y, width, height);

		if (myTree.getRowHeight() == 0) {
			return;
		}

		int temp = (height / myTree.getRowHeight()) * myTree.getRowHeight();
		myTreeScrollBar.setBlockIncrement(temp);
	}

	/**
	 * Sets the Bounds of the tree view and updates the increment values of the
	 * vertical scrollbar.
	 * 
	 * @param r
	 *            the Rectangle defining the bounds of the TreeView.
	 * @see java.awt.Component#setBounds(java.awt.Rectangle)
	 */
	public final void setBounds(final Rectangle r) {
		setBounds(r.x, r.y, r.width, r.height);
	}

	/**
	 * Sets the trees <code>TreeCellEditor</code>.
	 * 
	 * @param aCellEditor
	 *            the new <code>TreeCellEditor</code>.
	 * @see javax.swing.JTree#setCellEditor(javax.swing.tree.TreeCellEditor)
	 */
	public final void setCellEditor(final TreeCellEditor aCellEditor) {
		myTree.setCellEditor(aCellEditor);
	}

	/**
	 * Sets the trees <code>TreeCellRenderer</code> to visualize rows.
	 * 
	 * @param aCellRenderer
	 *            the new <code>TreeCellRenderer</code>
	 * @see javax.swing.JTree#setCellRenderer(javax.swing.tree.TreeCellRenderer)
	 */
	public final void setCellRenderer(final TreeCellRenderer aCellRenderer) {
		myTree.setCellRenderer(aCellRenderer);
	}

	/**
	 * Sets the context menu.
	 * 
	 * @param aContextMenu
	 *            the new <code>JContextMenu</code> to set
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 */
	public final void setContextMenu(final JContextMenu aContextMenu) {
		if (aContextMenu != null) {
			aContextMenu.setParentComponent(this);
		}
		this.myContextMenu = aContextMenu;
	}

	/**
	 * Sets this <code>TreeView</code> s editable state.
	 * 
	 * @param aEditableState
	 *            the editable state. <code>ture</code> to make it editable.
	 * @see javax.swing.JTree#setEditable(boolean)
	 */
	public final void setEditable(final boolean aEditableState) {
		myEditableState = aEditableState;
		myTree.setEditable(aEditableState);
		// if (myContextMenu == null) {
		setContextMenu(createDefaultContextMenu());
		// }

		if (getTreeSynthesizer() instanceof TreeProxyManager) {
			// loop through found context menu in reverse order
			// (TreeProxyManager.MENU_NAME Menu
			// is usually added to the end)
			// and search for TreeProxyManager.MENU_NAME then remove it
			for (int i = getContextMenu().getComponentCount() - 1; i >= 0; i--) {
				JMenuItem c = (JMenuItem) getContextMenu().getComponent(i);
				if (c.getText() != null
						&& c.getText().equals(TreeProxyManager.MENU_NAME)) {
					getContextMenu().remove(i);
					break;
				}

			}
			// add a new TreeProxyManager.MENU_NAME Menu
			getContextMenu().add(
					((TreeProxyManager) getTreeSynthesizer())
							.createManagerContextMenu());
		}

	}

	/**
	 * Expands all given <code>TreePath</code>s.
	 * 
	 * @param aExpandedPaths
	 *            an array with <code>TreePath</code> to expand
	 */
	public final void setExpandedPaths(final TreePath[] aExpandedPaths) {
		for (int i = 0; i < aExpandedPaths.length; i++) {
			myTree.expandPath(aExpandedPaths[i]);
		}
	}

	/**
	 * Expands all given <code>TreePath</code>s.
	 * 
	 * @param aExpandedPaths
	 *            an array with <code>TreePath</code> to expand
	 */
	public final void setExpandedRows(final Integer[] aExpandedPaths) {
		for (int i = 0; i < aExpandedPaths.length; i++) {
			myTree.expandRow(Integer.parseInt(aExpandedPaths[i].toString()));
		}
	}

	/**
	 * Sets the row height of the tree cells.
	 * 
	 * @param aNewHeight
	 *            the new row height
	 * @see javax.swing.JTree#setRowHeight(int)
	 */
	public final void setRowHeight(final int aNewHeight) {
		myTree.setRowHeight(aNewHeight);
		myTreeScrollBar.setUnitIncrement(aNewHeight);
	}

	/**
	 * Sets the selection to the given <code>TreePath</code>.
	 * 
	 * @param aPath
	 *            <code>TreePath</code> to be selected
	 * @see javax.swing.JTree#setSelectionPath(javax.swing.tree.TreePath)
	 */
	public final void setSelectionPath(final TreePath aPath) {
		myTree.setSelectionPath(aPath);
	}

	/**
	 * Sets the JTree for this <code>TreeView</code>.
	 * 
	 * @param aTree
	 *            the new JTree
	 */
	public final void setTree(final JTree aTree) {
		TreeModel model = null;
		if (myTree != null) {
			myTreeScrollPane.getViewport().remove(myTree);
			myTree.removeMouseListener(myMouseHandler);
			myTree.removeMouseMotionListener(myMouseHandler);
			ToolTipManager.sharedInstance().unregisterComponent(myTree);
			model = myTree.getModel();
		}

		myTree = aTree;
		if (model != null) {
			myTree.setModel(model);
		}
		myTree.setOpaque(false);
		myTree.addMouseListener(myMouseHandler);
		myTree.addMouseMotionListener(myMouseHandler);
		myTreeScrollPane.getViewport().add(myTree);
		myTreeScrollBar.setUnitIncrement(myTree.getRowHeight());
		ToolTipManager.sharedInstance().registerComponent(myTree);
		myTree.setModel(aTree.getModel());
	}

	/**
	 * Toggles the expand state of the given <code>TreePath</code>.
	 * 
	 * @param aPath
	 *            the <code>TreePath</code> to be toggled
	 */
	public final void toggleExpandStateOfPath(final TreePath aPath) {
		if (myTree.isExpanded(aPath)) {
			myTree.collapsePath(aPath);
		} else {
			myTree.expandPath(aPath);
		}
	}

	/**
	 * Mouse handler for the <code>TreeView</code> s.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.15 $
	 */
	protected class MouseEventHandler implements MouseListener,
			MouseMotionListener {

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public final void mouseClicked(final MouseEvent e) {
			boolean aNodeIsSelected = true;
			myLastMouseClickPosition = e.getPoint();

			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {

				if (getMatrixBrowserPanel().getTreeManager() != null) {
					TreePath originPath = myTree.getPathForLocation(e.getX(), e
							.getY());
					if (originPath != null) {
						VisibleNodeEvent ne = new VisibleNodeEvent(
								AbstractTreeView.this, originPath);
						ne.setClickCount(e.getClickCount());
						fireNodeClickedEvent(ne);
					}
				}
			} else if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {

				// //////////////Marwan ab hier wird den ContextMenu
				// implementiert
				if (getMatrixBrowserPanel().getTreeManager() != null) {

					TreePath originPath = myTree.getPathForLocation(e.getX(), e
							.getY());

					if (originPath != null) {

						aNodeIsSelected = false;
						VisibleNodeEvent ne = new VisibleNodeEvent(
								AbstractTreeView.this, originPath);
						VisibleNode vs = ne.getNode();
						createNodeContextMenu(vs).show(e.getComponent(),
								e.getX(), e.getY());

					}
				}

				if (myContextMenu != null && aNodeIsSelected) {

					myContextMenu.show(e.getComponent(), e.getX(), e.getY());
				}

			}
		}

		// /////////////////////////////////////////////////////////////////////////

		/**
		 * Creates a Context Menu for a given visible Node. This method also
		 * adds a handler for Node Context Menu Events.
		 * 
		 * @param vs
		 *            the VisibleNode this Context Menu is defined for.
		 * @return the ContextMenu of this Node.
		 */
		public final JContextMenu createNodeContextMenu(final VisibleNode vs) {
			JContextMenu menu = new JContextMenu();
			List<ICommand> allFunctions = (List<ICommand>) vs.getContextMenu();
			if (allFunctions != null) {
				for (int i = 0; i < allFunctions.size(); i++) {
					JMenuItem g = new JMenuItem(allFunctions.get(i).getName());
					NodeHandler handler = new NodeHandler(vs, i);
					g.addActionListener(handler);
					menu.add(g);
				}
			}
			return menu;
		}

		/**
		 * A Handler for Menu Events issued by VisibleNode Popu-Menus.
		 * 
		 * @author unknown
		 * @author Marwan (probably)
		 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
		 * @version $Revision: 1.15 $
		 */
		protected class NodeHandler implements ActionListener {

			/**
			 * The number of items in the Popup Menu this listener listens on.
			 */
			private int itemNumber;

			/**
			 * A Vector of <code>ICommand</code>s, the entries of this
			 * Popup-Menu.
			 */
			private List<ICommand> allItems;

			/**
			 * The <code>VisibleNode</code> this ActionListener listens on.
			 */
			private VisibleNode visibleNode;

			/**
			 * Constructor for a NodeHandler for a given VisibleNode. The
			 * NodeHandler reacts on Menu Events.
			 * 
			 * @param vs
			 *            the VisibleNode this Listener listens on for
			 *            menuEvents.
			 * @param i
			 *            the element in the ContextMenu this Handler is
			 *            registered on.-.
			 */
			public NodeHandler(final VisibleNode visibleNode, final int i) {
				this.visibleNode = visibleNode;
				this.itemNumber = i;
				allItems = (List<ICommand>) visibleNode.getContextMenu();
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see java.awt.event.ActionListener
			 *      #actionPerformed(java.awt.event.ActionEvent)
			 */
			public final void actionPerformed(final ActionEvent e) {
				if (allItems.get(itemNumber) instanceof ExternalICommandImplementation) {
					((ExternalICommandImplementation) allItems.get(itemNumber))
							.execute(e, visibleNode);
					VisibleNodeEvent event = new VisibleNodeEvent(
							e.getSource(), visibleNode);
					fireNodeMenuEvent(event);
				} else {
					allItems.get(itemNumber).execute();
				}
			}
		}

		// ////////////////////////////////////////////////////
		/**
		 * {@inheritDoc}
		 * 
		 * @see java.awt.event.MouseMotionListener
		 *      #mouseDragged(java.awt.event.MouseEvent)
		 */
		public void mouseDragged(final MouseEvent e) {

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		public void mouseEntered(final MouseEvent e) {
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		public void mouseExited(final MouseEvent e) {
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.awt.event.MouseMotionListener
		 *      #mouseMoved(java.awt.event.MouseEvent)
		 */
		public final void mouseMoved(final MouseEvent e) {
			if (getMatrixBrowserPanel().getTreeManager().getModel() != null) {
				TreePath originPath = myTree.getClosestPathForLocation(
						e.getX(), e.getY());

				if (originPath != null) {
					VisibleNodeEvent ne = new VisibleNodeEvent(
							AbstractTreeView.this, originPath);
					fireNodeOverEvent(ne);
				}
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		public void mousePressed(final MouseEvent e) {

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.awt.event.MouseListener
		 *      #mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased(final MouseEvent e) {

		}
	}

	/**
	 * Model change handler for the <code>TreeView</code>s.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @author Marwan
	 * @author <a href=mailto:cs@christian-schott.de>Christian Schott</a>
	 * @version $Revision: 1.15 $
	 */
	// //////////Marwan hier wurde die Klasse Tel f�r den Listener
	// implementiert
	protected class JCompListener implements TreeExpansionListener {
		/**
		 * {@inheritDoc}
		 * 
		 * @see javax.swing.event.TreeExpansionListener
		 *      #treeExpanded(javax.swing.event.TreeExpansionEvent)
		 */
		public final void treeExpanded(final TreeExpansionEvent e) {
			if (reload && as.getRelationPane() != null) {
				as.getRelationPane().setRepainter();

			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see javax.swing.event.TreeExpansionListener
		 *      #treeCollapsed(javax.swing.event.TreeExpansionEvent)
		 */
		public final void treeCollapsed(final TreeExpansionEvent e) {
			reload = true;
			if (as.getRelationPane() != null) {
				as.getRelationPane().removeAll();
			} // else the RelationPane has not been initialized yet because yet
			// there are no relations
			as.resetJComponentSizeCalculating();
			if (as.getRelationPane() != null) {
				as.getRelationPane().setRepainter();
			} // same as above.
		}

	}

	// ////////////////////////////////////////////////////////////////////////////
	/**
	 * The <code>TreeModelChangedHandler</code> reacts on
	 * <code>TreeModelChangedEvents</code>.
	 * 
	 * @author Marwan
	 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
	 * @version $Revision: 1.15 $
	 */
	protected class TreeModelChangedHandler implements TreeModelChangedListener {

		/**
		 * . Recieves a <code>ModelChangedEvent</code>.
		 * 
		 * @param e
		 *            a <code>ModelChangedEvent</code>
		 */

		/**
		 * {@inheritDoc}
		 * 
		 * @see de.fhg.iao.matrixbrowser.context.TreeModelChangedListener
		 *      #onTreeModelChanged
		 *      (de.fhg.iao.matrixbrowser.context.TreeModelChangedEvent)
		 */
		public final void onTreeModelChanged(final TreeModelChangedEvent e) {
			as.resetJComponentSizeCalculating();
			// ///////////Marwan wenn nur collapsed Links gezeigt werden
			if (e.isModelChanged() && as.getRelationPane() != null) { // false
				as.getRelationPane().getMatrixBrowserPanel()
						.getHorizontalTreeView().setRowHeight(
								HORIZONTAL_ROW_HEIGHT);
				as.getRelationPane().getMatrixBrowserPanel()
						.getVerticalTreeView()
						.setRowHeight(VERTICAL_ROW_HEIGHT);
				as.getRelationPane().removeAll();
			}
			// ///////////////////////////////////////////////////////////////////////

			TreePath[] selectionPaths = myTree.getSelectionPaths();
			// saves old expanded paths beginning from the rootPath

			// TreePath rootPath = new TreePath(getTreeModel().getRoot());
			/*
			 * schotti: Diese Zeilen waren auskommentiert. Wir benoetigen sie
			 * aber, um die Tree View nach einer Modell-Aenderung zu
			 * aktualisieren, glaube ich.
			 */
			// Enumeration expandedPathsEnumeration
			// = myTree.getExpandedDescendants(rootPath);
			// if (expandedPathsEnumeration == null) return;
			// schotti: ende zuvor auskommentierte Zeilen
			// save expanded paths
			HashSet<ID> id = saveExpandedPaths();

			// set new TreeModel
			if (myTreeModel == null) {
				myTreeModel = new DefaultTreeModel(myTreeSynthesizer
						.getVisibleTree());
				myTree.setModel(myTreeModel);
			} else {
				myTreeModel.setRoot(myTreeSynthesizer.getVisibleTree());
			}

			// reset saved expanded paths
			resetExpandedPaths(id);

			if (e.isNodeChanged()) {
				// just a node has changed -> restore old selections and
				// expanded states
				myTree.setSelectionPaths(selectionPaths);
			} else {
				myTree.expandRow(0);
			}
		}
	}

	/**
	 * The Action implementing the Addition of nodes to the AbstractTreeView.
	 * 
	 * @author unknown
	 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
	 * @version $Revision: 1.15 $
	 */
	protected class AddNodeAction extends AbstractAction {
		/**
		 * Comment for <code>serialVersionUID</code>.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor for an AddNodeAction.
		 */
		public AddNodeAction() {
			super("Add Node");
		}

		/**
		 * Adds a Node to a visible Tree. The place where the MouseButton was
		 * clicked sets the Parent Node for the to be created one. The new Node
		 * gets initialized with the Name "empty".
		 * 
		 * @param e
		 *            the ActionEvent triggering that had triggered this action.
		 */
		public final void actionPerformed(final ActionEvent e) {
			/*
			 * As of now, the new node is added to the Model, as well as a
			 * relation with its parent and only then we add the node to its
			 * visible ParentNode to include it in the visible tree. This should
			 * be easier. Especially for future additions of special nodes,
			 * relations,...
			 */
			VisibleNode node = null;
			try {
				node = (VisibleNode) myTree.getClosestPathForLocation(
						myLastMouseClickPosition.x, myLastMouseClickPosition.y)
						.getLastPathComponent();
			} catch (NullPointerException exception) {
				log
						.debug("Could not recognize where you wanted to add "
								+ "this node. Please try again by clicking somewhere near the"
								+ " new node's parent node.");
				return;
			}
			Node tgNode = new MBModelNode("empty");
			VisibleNode visTGNode = new EncapsulatingVisibleNode(tgNode);
			getMatrixBrowserPanel().getTreeManager().getModel().addNode(tgNode);
			log.debug("got back to AbstractTreeView.AddNode");
			RelationType relType = new RelationType("myRelationType");
			getMatrixBrowserPanel().getTreeManager().getModel()
					.addTreeSpanningRelationType(relType);
			// Iterator<RelationType> it =
			// getMatrixBrowserPanel().getTreeManager()
			// .getModel().getTreeSpanningRelationTypes().iterator();
			// RelationType relType = it.next();
			getMatrixBrowserPanel().getTreeManager().getModel().addRelation(
					new Relation(node.getNestedNode().getID(), tgNode.getID(),
							relType, RelationClass.REAL,
							RelationDirection.DIRECTED));
			node.add(visTGNode);
		}
	}

	/**
	 * <code>Action</code> handling the creation of parent nodes.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.15 $
	 */
	protected class AddParentNodeAction extends AbstractAction {
		/**
		 * Comment for <code>serialVersionUID</code>.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor for an <code>AddParentNodeAction</code>.
		 */
		public AddParentNodeAction() {
			super("Add Parent Node");
		}

		/*
		 * War auskommentiert. Schotti Typen geändert von Node auf
		 * DefaultMutableTreeNode, damit die methoden verfügbar sind. Weiß
		 * nicht, ob es damit funktioniert.
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @see java.awt.event.ActionListener
		 *      #actionPerformed(java.awt.event.ActionEvent)
		 */
		public final void actionPerformed(final ActionEvent e) {

			VisibleNode theParent = null;
			ID aID = IDPool.getIDPool().getID();
			VisibleNode theNewParent = new EncapsulatingVisibleNode(
					new MBModelNode("empty" + aID.toString(), aID));
			if (myTree.getSelectionCount() == 1) {
				DefaultMutableTreeNode theNode = (DefaultMutableTreeNode) myTree
						.getClosestPathForLocation(myLastMouseClickPosition.x,
								myLastMouseClickPosition.y)
						.getLastPathComponent();
				theParent = (VisibleNode) theNode.getParent();
				int index = theParent.getIndex(theNode);
				theNode.removeFromParent();
				theParent.insert(theNewParent, index);
				theNewParent.add(theNode);
				theNode.setParent(theNewParent);
			} else {
				TreePath[] thePaths = myTree.getSelectionPaths();
				VisibleNode theNode = (VisibleNode) thePaths[0]
						.getLastPathComponent();
				theParent = (VisibleNode) theNode.getParent();
				int index = theParent.getIndex(theNode);
				theParent.insert(theNewParent, index);
				for (int i = 0; i < thePaths.length; i++) {
					theNode = (VisibleNode) thePaths[i].getLastPathComponent();
					theNode.removeFromParent();
					theNewParent.add(theNode);
				}
			}
			((DefaultTreeModel) myTree.getModel())
					.nodeStructureChanged(theParent);
		}
	}

	/**
	 * <code>Action</code> handling the deletion of nodes.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.15 $
	 */
	protected class DeleteNodeAction extends AbstractAction {
		/**
		 * Comment for <code>serialVersionUID</code>.
		 */
		private static final long serialVersionUID = 1L;

		/** The action definition for deleting Nodes. */
		public DeleteNodeAction() {
			super("Delete Node");
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.awt.event.ActionListener
		 *      #actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent e) {
			// Node theNode = (Node) myTree.getClosestPathForLocation(
			// myLastMouseClickPosition.x, myLastMouseClickPosition.y)
			// .getLastPathComponent();

		}
	}

	/**
	 * <code>Action</code> handling the editing of nodes.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.15 $
	 */
	protected class EditNodeAction extends AbstractAction {
		/**
		 * Comment for <code>serialVersionUID</code>.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor for an <code>EditNodeAction</code>.
		 */
		public EditNodeAction() {
			super("Edit properies");
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.awt.event.ActionListener
		 *      #actionPerformed(java.awt.event.ActionEvent)
		 */
		public final void actionPerformed(final ActionEvent e) {

			VisibleNode theNode = (VisibleNode) myTree
					.getClosestPathForLocation(myLastMouseClickPosition.x,
							myLastMouseClickPosition.y).getLastPathComponent();
			fireNodeEditEvent(new VisibleNodeEvent(this, theNode));
		}
	}

	/**
	 * @return Returns the myTreeSynthesizer
	 */
	public final AbstractTreeSynthesizer getTreeSynthesizer() {
		return myTreeSynthesizer;
	}

	/**
	 * expands saved Paths.
	 * 
	 * @param aId
	 *            the HashSet with the nodes' id that have to be expanded
	 */
	public final void resetExpandedPaths(final HashSet<ID> aId) {
		if (aId == null || aId.isEmpty()) {
			// skip it
			return;
		}
		Enumeration<?> enumeration = myTreeSynthesizer
				.getVisibleTree().breadthFirstEnumeration();

		// set new TreeModel
		// this needs to be done otherwise resetting
		// collapseed paths would not work

		if (myTreeModel == null) {
			myTreeModel = new DefaultTreeModel(myTreeSynthesizer
					.getVisibleTree());
			myTree.setModel(myTreeModel);
		} else {
			myTreeModel.setRoot(myTreeSynthesizer.getVisibleTree());
		}

		// loop through all tree elements
		// and reset saved expanded paths
		while (enumeration.hasMoreElements()) {
		   Object nextElement = enumeration.nextElement();
		   if(nextElement instanceof VisibleNode){
   	       VisibleNode node = (VisibleNode) nextElement;
   
             TreePath path = new TreePath(node.getPath());
             if (aId.contains(node.getNestedNode().getID())) {
                myTree.expandPath(path);
            }
		   }
		}
	}

	/**
	 * saves information about expanded paths in a HashSet.
	 * 
	 * @return the HashSet with the saved information
	 */
	public final HashSet<ID> saveExpandedPaths() {
		HashSet<ID> result = null;
		 if (getTreeModel() == null || getTreeModel().getRoot() == null) {
		 result = new HashSet<ID>();
		// don't try to save paths that don't exist...
		 } else {
		TreePath rootPath = new TreePath(getTreeModel().getRoot());
		Enumeration<TreePath> expandedPathsEnumeration = myTree
				.getExpandedDescendants(rootPath);
		if (expandedPathsEnumeration == null) {
			return null;
		}
		// saves Enumeration to Vector because the Enumeration might get changed
		result = new HashSet<ID>();
		while (expandedPathsEnumeration.hasMoreElements()) {
			TreePath path = expandedPathsEnumeration.nextElement();
			result.add(((EncapsulatingVisibleNode) path.getLastPathComponent())
					.getNestedNode().getID());
		}
		 }
		return result;
	}

	/**
	 * Sets the <code>TreeSynthesizer</code> of this
	 * <code>AbstractTreeView</code>.
	 * 
	 * @param aSynthesizer
	 *            The <code>TreeSynthesizer</code> to set.
	 */
	public final void setTreeSynthesizer(
			final AbstractTreeSynthesizer aSynthesizer) {

		myTreeSynthesizer = aSynthesizer;
		// TreePath tempPath[] = getExpandedPaths();
		if (getTreeSynthesizer() instanceof TreeProxyManager) {
			// make sure there is only one TreeProxyManager.MENU_NAME Menu
			// see eh1 there is a context menu
			if (getContextMenu() != null) {
				// loop through found context menu in reverse order
				// (TreeProxyManager.MENU_NAME Menu
				// is usually added to the end)
				// and search for TreeProxyManager.MENU_NAME then remove it
				for (int i = getContextMenu().getComponentCount() - 1; i >= 0; i--) {
					JMenuItem c = (JMenuItem) getContextMenu().getComponent(i);
					if (c.getText() != null
							&& c.getText().equals(TreeProxyManager.MENU_NAME)) {
						getContextMenu().remove(i);
						break;
					}

				}
				// add a new TreeProxyManager.MENU_NAME Menu
				getContextMenu().add(
						((TreeProxyManager) getTreeSynthesizer())
								.createManagerContextMenu());

			}
		}
		if (myTreeModel == null) {
			// && myTreeSynthesizer.getVisibleTree() != null) {
			myTreeModel = new DefaultTreeModel(myTreeSynthesizer
					.getVisibleTree());
			myTree.setModel(myTreeModel);
		} else { // if (myTreeSynthesizer.getVisibleTree() != null){
			myTreeModel.setRoot(myTreeSynthesizer.getVisibleTree());
		}
		myTreeSynthesizer
				.addTreeModelChangedListener(myTreeModelChangedHandler);
		myTree.expandRow(0);

	}

	/**
	 * Implementation of the onLoad method of the
	 * UIResourceRegistryEventListener.
	 * 
	 * @param aEvent
	 *            the Event calling this Listener
	 */
	public final void onUIResourceRegistryChanged(
			final UIResourceRegistryEvent aEvent) {
		getContextMenu().setName(
				UIResourceRegistry.getInstance().TREEVIEW_CONTEXTMENU_TITLE);
	}

	/**
	 * sets new state of AbstractTreeView using a memento.
	 * 
	 * @param memento
	 *            the memento to set
	 */
	public final void setMemento(final AbstractTreeViewMemento memento) {
		myEditableState = memento.isEditable();
		// myTree.setSelectionPaths(memento.getSelectedPaths());
		resetExpandedPaths(memento.getExpandedPaths());
		setSavedState(memento.getSavedState());
		myTreeScrollBar.setValue(memento.getTreeScrollbarPosition());
	}

	/**
	 * Gets a <code>ScrollBar</code> for the Tree.
	 * 
	 * @return this <code>TreeView</code>'s <code>ScrollBar</code>.
	 */
	public final JScrollBar getScrollBar() {
		return myTreeScrollBar;
	}

	/**
	 * sets saved states of filter menu.
	 * 
	 * @param aSavedState
	 *            the saved states to set
	 */
	public final void setSavedState(final boolean[] aSavedState) {
		if (aSavedState == null) {
			return;
		}
		JMenu treeProxyManagerMenu = null;
		// find the TreeProxyManager.MENU_NAME menu and set
		// treeProxyManagerMenu to it
		if (getContextMenu() != null) {
			for (int i = getContextMenu().getComponentCount() - 1; i >= 0; i--) {
				JMenuItem c = (JMenuItem) getContextMenu().getComponent(i);
				if (c.getText() != null
						&& c.getText().equals(TreeProxyManager.MENU_NAME)) {
					treeProxyManagerMenu = (JMenu) c;
					break;
				}
			}

			// loop through all entries of treeProxyManagerMenu
			// and set the components' state

			for (int j = 0; j < treeProxyManagerMenu.getMenuComponentCount(); j++) {
				if (treeProxyManagerMenu.getMenuComponent(j) instanceof JCheckBoxMenuItem) {
					// set state of JCheckBoxMenuItem
					((JCheckBoxMenuItem) treeProxyManagerMenu
							.getMenuComponent(j)).setSelected(aSavedState[j]);
				} else if (treeProxyManagerMenu.getMenuComponent(j) instanceof JRadioButtonMenuItem) {
					// set state of JRadioButtonMenuItem
					((JRadioButtonMenuItem) treeProxyManagerMenu
							.getMenuComponent(j)).setSelected(aSavedState[j]);
				}
			}
		}
	}

	/**
	 * returns the state of the filter popup menu.
	 * 
	 * @param filterMenu
	 *            the filter menu item
	 * @return boolean array with saved menu states
	 */
	private boolean[] getFilterMenuState(final JMenu filterMenu) {
		boolean[] savedState = null;
		if (filterMenu == null) {
			return null;
		}
		savedState = new boolean[filterMenu.getMenuComponentCount()];

		// loop through all filter menu components
		for (int j = 0; j < filterMenu.getMenuComponentCount(); j++) {
			// need to make distinction here since
			// both MenuItems have a different method for telling
			// whether they are selected or not
			if (filterMenu.getMenuComponent(j) instanceof JCheckBoxMenuItem) {
				// save state for JCheckBoxMenuItem
				savedState[j] = ((JCheckBoxMenuItem) filterMenu
						.getMenuComponent(j)).getState();
			} else if (filterMenu.getMenuComponent(j) instanceof JRadioButtonMenuItem) {
				// save state for JRadioButtonMenuItem
				savedState[j] = ((JRadioButtonMenuItem) filterMenu
						.getMenuComponent(j)).isSelected();
			}
		}

		return savedState;
	}

	/**
	 * gets the internal state of AbstractTreeView that is needed to reset it to
	 * a defined state.
	 * 
	 * @return the memento that has saved the AbstractTreeView's state
	 */
	@SuppressWarnings("unchecked")
	public final AbstractTreeViewMemento getMemento() {
		JMenu treeProxyManagerMenu = null;
		// boolean savedState[] = null;
		// find the TreeProxyManager.MENU_NAME menu and set
		// treeProxyManagerMenu to it
		if (getContextMenu() != null) {
			for (int i = getContextMenu().getComponentCount() - 1; i >= 0; i--) {
				JMenuItem c = (JMenuItem) getContextMenu().getComponent(i);
				if (c.getText() != null
						&& c.getText().equals(TreeProxyManager.MENU_NAME)) {
					treeProxyManagerMenu = (JMenu) c;
					break;
				}
			}
		}

		return new AbstractTreeViewMemento(
				getFilterMenuState(treeProxyManagerMenu),
				(HashSet<ID>) saveExpandedPaths().clone(), myEditableState,
				myTreeScrollBar.getValue());
	}

	/**
	 * Memento class for AbstractTreeView that is used to save
	 * AbstractTreeView's internal state.
	 * 
	 * @author schaal
	 */
	public class AbstractTreeViewMemento {

		/**
		 * The TreeView's saved state.
		 */
		private boolean[] mySavedState = null;

		/**
		 * boolean to tell whether the Tree is editable.
		 */
		private boolean myEditableState = false;

		/**
		 * Holds the position of the TreeScrollBar for this Memento.
		 */
		private int myTreeScrollbarPosition = 0;

		/**
		 * Holds the Expanded paths for this Memento.
		 */
		private HashSet<ID> myExpandedPaths = null;

		/**
		 * Constructor of an AbstractTreeViewMemento.
		 * 
		 * @param aSavedState
		 *            Filter state
		 * @param anExpandedPaths
		 *            expandede paths state
		 * @param anEditableState
		 *            editable state
		 * @param aTreeScrollBarPosition
		 *            treescrollbarposition state
		 */
		protected AbstractTreeViewMemento(final boolean[] aSavedState,
				final HashSet<ID> anExpandedPaths,
				final boolean anEditableState, final int aTreeScrollBarPosition) {
			if (aSavedState != null) {
				mySavedState = new boolean[aSavedState.length];
				System.arraycopy(aSavedState, 0, mySavedState, 0,
						aSavedState.length);
			}

			myEditableState = anEditableState;
			myTreeScrollbarPosition = aTreeScrollBarPosition;
			myExpandedPaths = anExpandedPaths;
		}

		/**
		 * @return Returns the myEditableState.
		 */
		private boolean isEditable() {
			return myEditableState;
		}

		/**
		 * @return Returns the myExpandedPaths.
		 */

		private HashSet<ID> getExpandedPaths() {
			return myExpandedPaths;
		}

		/**
		 * @return Returns the myTreeScrollbarPosition.
		 */
		private int getTreeScrollbarPosition() {
			return myTreeScrollbarPosition;
		}

		/**
		 * @return Returns the mySavedState
		 */
		private boolean[] getSavedState() {
			return mySavedState;
		}
	}

	/**
	 * A Runnable serving to display an Error message in case of an exception
	 * occuring in the above statements.
	 * 
	 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
	 * @version $Revision: 1.15 $
	 */
	public class DisplayException implements Runnable {
		/** The exception that leads to the message display. */
		private Exception ex;
		/**
		 * The JComponent on which the exception occured when something was
		 * dropped on it.
		 */
		private JComponent dropComponent;

		/**
		 * Constructor.
		 * 
		 * @param ex
		 *            the exception for which the message display is
		 *            constructed.
		 * @param dropComponent
		 *            the Component on wich something was dropped when the
		 *            exception occured.
		 */
		DisplayException(final Exception ex, final JComponent dropComponent) {
			this.ex = ex;
			this.dropComponent = dropComponent;
		}

		/**
		 * A Notice to display the type of exception thrown.
		 * 
		 * @return a String representation of the Exception class.
		 */
		private String exceptionNotice() {
			if (ex instanceof IOException) {
				return "IOException when dropping on ";
			} else if (ex instanceof UnsupportedFlavorException) {
				return "UnsupportedFlavor when dropping on ";
			} else {
				return ex.getMessage() + " when dropping on ";
			}
		}

		/**
		 * Displays the message box. {@inheritDoc}
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public final void run() {
			ByteArrayOutputStream stackTrace = new ByteArrayOutputStream();
			ex.printStackTrace(new PrintStream(stackTrace, true));
			JOptionPane.showMessageDialog(null, ex.getMessage() + "\n"
					+ stackTrace.toString().substring(0, MAX_ERROR_DSP_LENGTH),
					exceptionNotice() + dropComponent + ".",
					JOptionPane.ERROR_MESSAGE);
		}
	};
}
