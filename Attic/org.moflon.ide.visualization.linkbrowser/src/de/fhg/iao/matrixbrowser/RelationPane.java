/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * RelationPane.java,v 1.3 2004/04/07 13:52:32 roehrijn Exp $ Created on
 * 11.11.02
 */
package de.fhg.iao.matrixbrowser;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;

import javax.swing.tree.TreePath;

import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.xswing.JContextMenu;

/**
 * Interface for the GUI widget , which shows the relations in the matrix cells.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.4 $
 */
public interface RelationPane {

	public static final String IDENTITY_RELATION = "Identity";

	/**
	 * Adds a <code>MouseListener<\code>.
	 * 
	 * @param aMouseListener
	 *            the <code>MouseListener<\code> to add
	 * @see java.awt.event.MouseListener
	 */
	public void addMouseListener(MouseListener aMouseListener);

	/**
	 * Adds a <code>MouseMotionListener<\code>.
	 * 
	 * @param aMouseMotionListener
	 *            the <code>MouseMotionListener<\code> to add
	 * @see java.awt.event.MouseMotionListener
	 */
	public void addMouseMotionListener(MouseMotionListener aMouseMotionListener);

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
			VisibleRelationEventListener aVisibleRelationEventListener);

	/**
	 * Returns the <code>JContextMenu</code> of this <code>RelationPane</code>.
	 * Returns a default context menu, if the actual one is null.
	 * 
	 * @return the <code>JContextMenu</code> of this <code>RelationPane</code>
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 */
	public JContextMenu getContextMenu();

	/**
	 * @return the drag and drop mechanisms enabled state.
	 */
	public boolean getDragEnabled();

	/**
	 * Gets the the row in the <code>HorizontalTreeView<\code>for an
   * x coordinate in the <code>MatrixBrowserPanel<\code>.
	 * 
	 * @param aX
	 *            x coordinate in the <code>MatrixBrowserPanel<\code>
	 * @return the row number in the <code>HorizontalTreeView<\code>
	 * @see de.fhg.iao.matrixbrowser.HorizontalTreeView
	 */
	public int getHorizontalRowForX(int aX);

	/**
	 * @return the <code>MatrixBrowserPanel</code> this
	 *         <code>RelationPane</code> handels the relations in the matrix
	 *         cells
	 */
	public MatrixBrowserPanel getMatrixBrowserPanel();

	/**
	 * Gets the actual <code>RelationRenderer</code> component.
	 * 
	 * @return aRenderer the <code>RelationRenderer</code>
	 * @see de.fhg.iao.matrixbrowser.RelationRenderer
	 */
	public RelationRenderer getRelationRenderer();

	/**
	 * @return the last mouse overed <code>VisibleRelation</code> object.
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	public VisibleRelation getRollOverRelation();

	/**
	 * @return the last selected <code>VisibleRelation</code> object.
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	public VisibleRelation getSelectedRelation();

	/**
	 * Returns the string to be used as the tooltip for <i>event </i>. By
	 * default this returns any string set using setToolTipText(). If a
	 * component provides more extensive API to support differing tooltips at
	 * different locations, this method should be overridden.
	 * 
	 * @param e
	 *            the MouseEvent, which triggers a tooltip
	 * @return a textual description a <code>VisibleRelation</code> provides.
	 * @see java.awt.event.MouseEvent
	 * @see javax.swing.JComponent.getToolTipText(java.awt.event.MouseEvent)
	 */
	public String getToolTipText(MouseEvent e);

	/**
	 * Gets the the row in the <code>VerticalTreeView<\code>for an
   * y coordinate in the <code>MatrixBrowserPanel<\code>.
	 * 
	 * @param aY
	 *            y coordinate in the <code>MatrixBrowserPanel<\code>
	 * @return the row number in the <code>VerticalTreeView<\code>
	 * @see de.fhg.iao.matrixbrowser.VerticalTreeView
	 */
	public int getVerticalRowForY(int aY);

	/**
	 * @return the virtual border of the <code>RelationPane</code> to draw e.g.
	 *         arrows showing the direction/type of relations. |---------
	 *         NodeCloseUpView ----------| |- VerticalTreeView -|- virt.
	 *         border-|-RelationPane-|
	 */
	public int getVirtualBorderWidth();

	/**
	 * Returns the virtual bounds of the relation pane. Since the relation panes
	 * size is equal to the matrix browser panel size, the virtual size is the
	 * rectangle starting at the node closup view panels buttom right corner.
	 * 
	 * @return a <code>Rectangle</code> containing the virtual bounds
	 */
	public Rectangle getVirtualBounds();

	/**
	 * Returns the virtual bounds of the relation pane. Since the relation panes
	 * size is equal to the matrix browser panel size, the virtual size is the
	 * rectangle starting at the node closup view panels buttom right corner.
	 * 
	 * @param aRectangle
	 *            the <code>Rectangle</code> store the virtual bounds. if it is
	 *            <code>null</code> a new one is created.
	 * @return a <code>Rectangle</code> containing the virtual bounds
	 */
	public Rectangle getVirtualBounds(Rectangle aRectangle);

	/**
	 * Gets the VisibleRelation object for the given <code>TreePath</code>s.
	 * 
	 * @param aHorizontalPath
	 *            the <code>TreePath</code> of the horizontal TreeView
	 * @param aVerticalPath
	 *            the <code>TreePath</code> of the vertical TreeView
	 * @return the <code>VisibleRelation</code> for the given
	 *         <code>TreePath</code> s
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	public VisibleRelation getVisibleRelationForPath(TreePath aHorizontalPath,
			TreePath aVerticalPath);

	/**
	 * Gets the <code>VisibleRelation</code> object for the given rows in the
	 * <code>TreeView</code>s.
	 * 
	 * @param aHorizontalRow
	 *            the row of the horizontal <code>HorizontalTreeView</code>
	 * @param aVerticalRow
	 *            the row of the vertical <code>VerticalTreeView</code>
	 * @return the <code>VisibleRelation</code> object
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 * @see de.fhg.iao.matrixbrowser.TreeView
	 */
	public VisibleRelation getVisibleRelationForRow(int aHorizontalRow,
			int aVerticalRow);

	/**
	 * @return the actual <code>Set</code> of visible <code>RelationType</code>
	 *         s.
	 * @see de.fhg.iao.matrixbrowser.model.elements.RelationType
	 */
	public Collection<RelationType> getVisibleRelationTypes();

	/**
	 * Removes a <code>MouseListener<\code>.
	 * 
	 * @param aMouseListener
	 *            the <code>MouseMotionListener<\code> to remove
	 * @see java.awt.event.MouseListener
	 */
	public void removeMouseListener(MouseListener aMouseListener);

	/**
	 * Removes a <code>MouseMotionListener<\code>.
	 * 
	 * @param aMouseMotionListener
	 *            the <code>MouseMotionListener<\code> to remove
	 * @see java.awt.event.MouseMotionListener
	 */
	public void removeMouseMotionListener(
			MouseMotionListener aMouseMotionListener);

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
			VisibleRelationEventListener aVisibleRelationEventListener);

	/**
	 * Sets a new <code>JContextMenu</code> for the current
	 * <code>RelationPane</code>. If the <code>JContextMenu</code> of the
	 * <code>RelationPane</code> is non <code>null</code> it is displayed if the
	 * user clicks on the right mous button.
	 * 
	 * @param aContextMenu
	 *            a new <code>JContextMenu</code> for the current
	 *            <code>RelationPane</code>
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 */
	public void setContextMenu(JContextMenu aContextMenu);

	/**
	 * Sets the drag and drop mechanisms enabled state.
	 * 
	 * @param aEnabledState
	 *            true to enable d&d
	 */
	public void setDragEnabled(boolean aEnabledState);

	/**
	 * Sets a new <code>RelationRenderer</code> component.
	 * 
	 * @param aRenderer
	 *            the new <code>RelationRenderer</code>
	 * @see de.fhg.iao.matrixbrowser.RelationRenderer
	 */
	public void setRelationRenderer(RelationRenderer aRenderer);

	/**
	 * Sets a mouse overed <code>VisibleRelation</code>.
	 * 
	 * @param aRelation
	 *            the roll-over <code>VisibleRelation</code> object.
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	public void setRollOverRelation(VisibleRelation aRelation);

	/**
	 * Sets the current selected <code>VisibleRelation</code>.
	 * 
	 * @param aRelation
	 *            the roll-over <code>VisibleRelation</code> object.
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	public void setSelectedRelation(VisibleRelation aRelation);

	/**
	 * Sets the actual <code>Set</code> of visible <code>RelationType</code> s.
	 * 
	 * @param aVisibleRelationsSet
	 *            a <code>Set</code> of visible <code>RelationType</code> s
	 */
	public void setVisibleRelationTypes(
			Collection<RelationType> aVisibleRelationsSet);

	/**
	 * Forces the <code>RelationPane</code> to recalculate all currently visible
	 * relations depending on the actual internal state.
	 */
	public void updateVisibleRelations();
}