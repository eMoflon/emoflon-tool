/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * DefaultVerticalTreeView.java,v 1.3 2004/04/07 13:52:28 roehrijn Exp $ Created
 * on 26.02.02
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.tree.TreePath;

import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.VerticalTreeView;
import de.fhg.iao.swt.xswing.JFixedWidthTree;

/**
 * Default implementation for a vertical tree viewer.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.5 $
 */
public class DefaultVerticalTreeView extends AbstractTreeView implements
		VerticalTreeView {

	/**
	 * Constructs a <code>DefaultVerticalTreeView</code> given the main
	 * <code>MatrixBrowserPanel</code>.
	 * 
	 * @param aMatrixBrowserPanel
	 *            refernce to the <code>MatrixBrowserPanel</code>, which is
	 *            responsible for this <code>DefaultVerticalTreeView</code>
	 */
	public DefaultVerticalTreeView(MatrixBrowserPanel aMatrixBrowserPanel) {
		super(aMatrixBrowserPanel, null);

		setContextMenu(createDefaultContextMenu());
	}

	/**
	 * Initializes this component. If the given tree is <code>null</code> it
	 * will be initialized with a <code>JFixedWidthTree</code>.
	 * 
	 * @param aTree
	 *            The underlying tree, which can be <code>null</code>
	 * @see de.fhg.iao.swt.xswing.JFixedWidthTree
	 */
	protected void initialize(JTree aTree) {
		if (aTree == null) {
			aTree = new JFixedWidthTree(null);
			aTree.setCellRenderer(new DefaultVerticalTreeCellRenderer());
			aTree.setRowHeight(18);

			// BUGFIX: long entries did not display correctly
			// this actually is not a real bug, the tree is just set to
			// clip long entries.
			// If this is wanted, comment the next line out
			((JFixedWidthTree) aTree).setFixTo(JFixedWidthTree.MAXCELLWIDTH);
			// aTree.setShowsRootHandles(true);
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
		myTreeScrollPane.setVerticalScrollBar(myTreeScrollBar);
		myTreeScrollPane.setBorder(null);
		myTreeScrollPane.setOpaque(false);

		// configure viewport
		myTreeScrollPane.getViewport().setOpaque(false);
		add(myTreeScrollBar, BorderLayout.WEST);
		add(myTreeScrollPane, BorderLayout.CENTER);
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
		return getTree().getClosestPathForLocation(aX, aY - getScrollOffsetY());
	}

	public boolean canScroll() {
		return (getHeight() < myTreeScrollPane.getVerticalScrollBar()
				.getMaximum());
	}

}