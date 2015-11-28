/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * TreeView.java,v 1.4 2004/04/07 13:52:32 roehrijn Exp $ Created on 26.02.02
 */
package de.fhg.iao.matrixbrowser;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentListener;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import de.fhg.iao.matrixbrowser.context.AbstractTreeSynthesizer;
import de.fhg.iao.swt.xswing.JContextMenu;

/**
 * Interface for a tree view showing the hierachies in the
 * <code>MatrixBrowserPanel</code>.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.3 $
 * @see MatrixBrowserPanel
 */
public interface TreeView {

	/**
	 * Adds a <code>AdjustmentListener</code>, that will be notified about
	 * adjustments in the <code>JScrollBar</code> which scrolls also the
	 * <code>VisibleRelation</code> s in the <code>RelationPane</code>.
	 * 
	 * @param aAdjustmentListener
	 *            the <code>AdjustmentListener</code> that gets notified about
	 *            <code>AdjustmentEvent</code>s.
	 * @see javax.swing.JScrollBar#addAdjustmentListener(java.awt.event.AdjustmentListener)
	 * @see java.awt.event.AdjustmentListener
	 * @see java.awt.event.AdjustmentEvent
	 */
	public void addAdjustmentListener(AdjustmentListener aAdjustmentListener);

	/**
	 * Adds a <code>VisibleNodeEventListener</code>, that will be notified if
	 * someone clicks on a node in this <code>TreeView<\code>.
	 * 
	 * @param aVisibleNodeEventListener
	 *            the <code>VisibleNodeEventListener</code> to be notified of
	 *            <code>NodeEvent</code>s
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleNodeEvent
	 */
	public void addVisibleNodeEventListener(
			VisibleNodeEventListener aVisibleNodeEventListener);

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
			TreeExpansionListener aTreeExpansionListener);

	/**
	 * Checks if the <code>TreeView</code> currently shows a specific
	 * <code>EncapsulatingVisibleNode</code>.
	 * 
	 * @return true if the given <code>VisibleNode</code> is actually shown
	 * @see de.fhg.iao.matrixbrowser.EncapsulatingVisibleNode
	 */
	boolean containsVisibleNode(VisibleNode aNode);

	/**
	 * Expands the given <code>TreePath</code>.
	 * 
	 * @param aPath
	 *            the <code>TreePath</code> to be expanded
	 * @see javax.swing.JTree#expandPath(javax.swing.tree.TreePath)
	 */
	public void expandPath(TreePath aPath);

	/**
	 * Gets the closest <code>TreePath</code> to the given location.
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @return the closest <code>TreePath</code> to the given location
	 * @see javax.swing.JTree#getClosestPathForLocation(int, int)
	 */
	public TreePath getClosestPathForLocation(int x, int y);

	/**
	 * Returns the <code>JContextMenu</code> of this <code>TreeView</code>.
	 * Returns a default menu, if the actual one is <code>null</code>.
	 * 
	 * @return the <code>JContextMenu</code> of this <code>TreeView</code>
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 */
	public JContextMenu getContextMenu();

	/**
	 * @return the <code>TreeView</code> s editable state
	 */
	public boolean getEditable();

	/**
	 * Returns all the expanded <code>TreePath</code> �s.
	 * 
	 * @return <code>TreePath</code>[] array with expanded paths
	 */
	public TreePath[] getExpandedPaths();

	/**
	 * Returns a Point with the last position of the mouse, when it was clicked.
	 * 
	 * @return the last position of the mouse, where it was last clicked
	 * @see java.awt.Point
	 */
	public Point getLastMouseClickPosition();

	/**
	 * Returns the <code>Bounds</code> of the <code>TreePath</code> position.
	 * 
	 * @param aPath
	 *            TreePath of the position
	 * @return the bounds
	 * @see javax.swing.JTree#getPathBounds(javax.swing.tree.TreePath)
	 */
	public Rectangle getPathBounds(TreePath aPath);

	/**
	 * Returns the <code>TreePath</code> for the given row.
	 * 
	 * @param aRow
	 *            the Row
	 * @return the <code>TreePath</code>
	 * @see javax.swing.JTree#getPathForRow(int)
	 */
	public TreePath getPathForRow(int aRow);

	/**
	 * Returns the row height of the tree cells.
	 * 
	 * @return the row height of the tree cells
	 * @see javax.swing.JTree#getRowHeight()
	 */
	public int getRowHeight();

	/**
	 * Returns the x offset from possible x scrolling.
	 * 
	 * @return the x offset from x scrolling operations
	 */
	public int getScrollOffsetX();

	/**
	 * Returns the y offset from possible y scrolling.
	 * 
	 * @return the y offset from x scrolling operations
	 */
	public int getScrollOffsetY();

	/**
	 * Sets the x offset of possible x scrolling.
	 */
	public void setScrollOffsetX(int x);

	/**
	 * Sets the y offset of possible y scrolling.
	 */
	public void setScrollOffsetY(int y);

	/**
	 * @return the underlying <code>JTree</code>
	 */
	public JTree getTree();

	/**
	 * @return the underlying <code>TreeModel</code>.
	 * @see javax.swing.tree.TreeModel
	 * @see javax.swing.JTree#getModel()
	 */
	public TreeModel getTreeModel();

	/**
	 * Gets the collapsed state of a <code>TreePath</code>.
	 * 
	 * @return true if the given <code>TreePath</code> is collapsed
	 * @see javax.swing.tree.TreePath
	 * @see javax.swing.JTree#isCollapsed(javax.swing.tree.TreePath)
	 */
	public boolean isCollapsed(TreePath aPath);

	/**
	 * Gets the expanded state of a <code>TreePath</code>.
	 * 
	 * @return true if the given <code>TreePath</code> is expanded
	 * @see javax.swing.tree.TreePath
	 * @see javax.swing.JTree#isExpanded(javax.swing.tree.TreePath)
	 */
	public boolean isExpanded(TreePath aPath);

	/**
	 * Removes a <code>AdjustmentListener</code> from the event listener list.
	 * 
	 * @param aListener
	 *            the listener
	 * @see javax.swing.JScrollBar#addAdjustmentListener(java.awt.event.AdjustmentListener)
	 */
	public void removeAdjustmentListener(AdjustmentListener aListener);

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
			VisibleNodeEventListener aVisibleNodeEventListener);

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
			TreeExpansionListener aTreeExpansionListener);

	/**
	 * Sets the trees <code>TreeCellEditor</code>.
	 * 
	 * @param aCellEditor
	 *            the new <code>TreeCellEditor</code>.
	 * @see javax.swing.JTree#setCellEditor(javax.swing.tree.TreeCellEditor)
	 */
	public void setCellEditor(TreeCellEditor aCellEditor);

	/**
	 * Sets the trees <code>TreeCellRenderer</code>.
	 * 
	 * @param aCellRenderer
	 *            the new <code>TreeCellRenderer</code>
	 * @see javax.swing.JTree#setCellRenderer(javax.swing.tree.TreeCellRenderer)
	 */
	public void setCellRenderer(TreeCellRenderer aCellRenderer);

	/**
	 * Sets the context menu.
	 * 
	 * @param aContextMenu
	 *            the new context menu
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 */
	public void setContextMenu(JContextMenu aContextMenu);

	/**
	 * Sets this <code>TreeView</code> s editable state.
	 * 
	 * @param aEditableState
	 *            the new editable state. <code>true</code> to make it editable
	 */
	public void setEditable(boolean aEditableState);

	/**
	 * Sets all <code>TreePath</code> s to be expanded.
	 * 
	 * @param aExpandedPaths
	 *            array with expanded <code>TreePath</code>
	 */
	public void setExpandedPaths(TreePath[] aExpandedPaths);

	/**
	 * Sets the row height of the tree cells.
	 * 
	 * @param newHeight
	 *            the new row height
	 * @see javax.swing.JTree#setRowHeight(int)
	 */
	public void setRowHeight(int newHeight);

	/**
	 * Set�s the selection to the given <code>TreePath</code>.
	 * 
	 * @param aPath
	 *            the <code>TreePath</code> to be selected
	 * @see javax.swing.JTree#setSelectionPath(javax.swing.tree.TreePath)
	 */
	public void setSelectionPath(TreePath aPath);

	/**
	 * Sets the <code>JTree</code> for this hierachy view.
	 * 
	 * @param aTree
	 *            the new <code>JTree</code>
	 */
	public void setTree(JTree aTree);

	/**
	 * Toggles the expand state of the given <code>TreePath</code>.
	 * 
	 * @param aPath
	 *            the <code>TreePath</code> to be toggled
	 */
	public void toggleExpandStateOfPath(TreePath aPath);

	public AbstractTreeSynthesizer getTreeSynthesizer();

	public void setTreeSynthesizer(AbstractTreeSynthesizer aSynthesizer);

	public boolean canScroll();
}