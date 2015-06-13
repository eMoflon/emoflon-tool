/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * DefaultHorizontalTreeView.java,v 1.5 2004/04/13 12:36:35 kookaburra Exp $
 * Created on 26.02.02
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.BorderLayout;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.tree.TreePath;

import de.fhg.iao.matrixbrowser.HorizontalTreeView;
import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.swt.xswing.JHorizontalRotateTree;

/**
 * Default implementation for a rotated horizontal
 * <code>HorizontalTreeView</code> derived from
 * <code>DefaultHorizontalTreeView</code>.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.7 $
 */
public class DefaultHorizontalTreeView extends AbstractTreeView implements
		HorizontalTreeView {// , TreeModelChangedListener {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a <code>DefaultHorizontalTreeView</code> given the main
	 * <code>MatrixBrowserPanel</code>.
	 * 
	 * @param aMatrixBrowserPanel
	 *            reference to the <code>MatrixBrowserPanel</code>, which is
	 *            responsible for this <code>TreeView</code>
	 */
	public DefaultHorizontalTreeView(MatrixBrowserPanel aMatrixBrowserPanel) {
		super(aMatrixBrowserPanel);
		setContextMenu(createDefaultContextMenu());
	}

	/**
	 * Initializes this component
	 * 
	 * @param aTree
	 *            the underlying tree, which can be null
	 */
	protected void initialize(JTree aTree) {

		if (aTree == null) {
			aTree = new JHorizontalRotateTree(null);
			aTree.setCellRenderer(new DefaultHorizontalTreeCellRenderer());
		}

		// configure this
		setLayout(new BorderLayout());
		setDoubleBuffered(true);
		setOpaque(false);

		// configure scroll pane
		myTreeScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		myTreeScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		myTreeScrollPane.setHorizontalScrollBar(myTreeScrollBar);
		myTreeScrollPane.setBorder(null);
		myTreeScrollPane.setOpaque(false);

		// configure viewport
		myTreeScrollPane.getViewport().setOpaque(false);
		myTreeScrollBar.setOrientation(JScrollBar.HORIZONTAL);
		add(myTreeScrollBar, BorderLayout.NORTH);
		add(myTreeScrollPane, BorderLayout.CENTER);
		myTreeScrollPane.getViewport().setOpaque(false);
		myTreeScrollPane.getViewport().setScrollMode(
				JViewport.SIMPLE_SCROLL_MODE);

		// set the tree
		setTree(aTree);

	}

	/**
	 * Gets the closest TreePath to a screen position.
	 * 
	 * @param aX
	 *            x position
	 * @param aY
	 *            y position
	 * @return the closest TreePath
	 */
	public TreePath getClosestPathForLocation(int aX, int aY) {
		return getTree().getClosestPathForLocation(aX - getScrollOffsetX(), aY);
	}

	public boolean canScroll() {
		return (getWidth() < myTreeScrollPane.getHorizontalScrollBar()
				.getMaximum());
	}

	// public void onTreeModelChanged(TreeModelChangedEvent e) {
	// this.myTreeModelChangedHandler.onTreeModelChanged(e);

	// }

}
