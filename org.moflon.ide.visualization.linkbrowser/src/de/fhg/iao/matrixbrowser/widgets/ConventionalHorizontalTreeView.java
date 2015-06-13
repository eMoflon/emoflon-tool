/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * ConventionalHorizontalTreeView.java,v 1.3 2004/04/13 12:36:35 kookaburra Exp $
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
import de.fhg.iao.swt.xswing.JHorizontalTree;

/**
 * Implementation of a <code>HorizontalTreeView</code> with 90� rotated cells.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.5 $
 */
public class ConventionalHorizontalTreeView extends AbstractTreeView implements
		HorizontalTreeView {

	/**
	 * Comment for <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a <code>TreeView</code> given the main
	 * <code>MatrixBrowserPanel</code>.
	 * 
	 * @param aMatrixBrowserPanel
	 *            refernce to the <code>MatrixBrowserPanel</code>, which is
	 *            responsible for this <code>TreeView</code>
	 */
	public ConventionalHorizontalTreeView(MatrixBrowserPanel aMatrixBrowserPanel) {
		super(aMatrixBrowserPanel, null);
	}

	/**
	 * Initializes this Component
	 * 
	 * @param aTree
	 *            The underlying tree, which can be null
	 */
	protected void initialize(JTree aTree) {
		if (aTree == null) {
			aTree = new JHorizontalTree(null);
			aTree.setCellRenderer(new ConventionalHorizontalTreeCellRenderer());
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
		myTreeScrollPane.getViewport().setScrollMode(
				JViewport.SIMPLE_SCROLL_MODE);
		setTree(aTree);
	}

	/**
	 * Gets the closest <code>TreePath</code> to a screen position.
	 * 
	 * @param aX
	 *            x position
	 * @param aY
	 *            y position
	 * @return the closest <code>TreePath</code>
	 */
	public final TreePath getClosestPathForLocation(final int aX, final int aY) {
		return getTree().getClosestPathForLocation(aX - getScrollOffsetX(), aY);
	}

	public boolean canScroll() {
		return (getWidth() < myTreeScrollPane.getHorizontalScrollBar()
				.getMaximum());
	}
}