/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * TransportPanel.java,v 1.9 2004/05/14 18:43:29 roehrijn Exp $ Created on
 * 28.08.2002
 */

/**
 * Copied from TransportPanel, functionality taken away...
 */
package de.fhg.iao.matrixbrowser;

import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import de.fhg.iao.matrixbrowser.context.TreeModelChangedEvent;
import de.fhg.iao.matrixbrowser.context.TreeModelChangedListener;
import de.fhg.iao.matrixbrowser.widgets.DefaultVerticalTreeCellRenderer;
import de.fhg.iao.matrixbrowser.widgets.TreeTransferHandler;

/**
 * The transport panel shows only the main tree, which can be build out of the
 * underlying graph model. This is for gaining overview and insight into the
 * main topic areas of the graph.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.2 $
 */
public class SimpleTransportPanel extends JPanel implements
		TreeModelChangedListener {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/** The transport Tree */
	private JTree myTransportTree = null;

	/** A <code>JScrollPane</code> that the tree can be scrolled */
	private JScrollPane myTreeScrollPane = null;

	// private TreeManager myTreeManager = null;

	// schotti: never read locally, private -> not needed.
	// private DefaultTreeModel myTreeModel = null;

	// private AbstractTreeSynthesizer myTreeSynthesizer;

	/**
	 * Constructs a <code>TransportPanel</code> for the given
	 * <code>MatrixBrowserPanel</code>.
	 * 
	 * @param aMatrixBrowserPanel
	 *            reference to the <code>MatrixBrowserPanel</code>, which is
	 *            responsible for this <code>TreeView</code>
	 */
	public SimpleTransportPanel() {
		super();
		TreeModel aTreeModel = null;
		myTransportTree = new JTree(aTreeModel);
	}

	/**
	 * Constructs a <code>TransportPanel</code> for the given
	 * <code>MatrixBrowserPanel</code>.
	 * 
	 * @param aMatrixBrowserPanel
	 *            reference to the <code>MatrixBrowserPanel</code>, which is
	 *            responsible for this <code>TreeView</code>
	 */
	public SimpleTransportPanel(final JTree transportTree) {
		super();
		myTransportTree = transportTree;
		myTransportTree.setRootVisible(true);

		// configure this
		setDoubleBuffered(true);
		setOpaque(false);
		setLayout(new BorderLayout());

		// configure the tree
		myTransportTree.setRowHeight(18);

		// for the icons:
		DefaultVerticalTreeCellRenderer renderer = new DefaultVerticalTreeCellRenderer();
		String iconsPathName = "de/fhg/iao/matrixbrowser/widgets/";
		ImageIcon iconLeaf = null; // for the TreeLeaf-Icon
		URL leafIconURL = ClassLoader.getSystemResource(iconsPathName
				+ "TreeLeaf.gif");

		if (leafIconURL != null) {
			iconLeaf = new ImageIcon(leafIconURL, "a tree leaf icon");
		}

		if (iconLeaf != null) {
			renderer.setLeafIcon(iconLeaf);
		}

		ImageIcon iconTreeClosed = null; // for the TreeClosed-Icon
		URL treeClosedIconURL = ClassLoader.getSystemResource(iconsPathName
				+ "TreeClosed.gif");

		if (treeClosedIconURL != null) {
			iconTreeClosed = new ImageIcon(treeClosedIconURL,
					"tree is closed icon");
		}

		if (iconTreeClosed != null) {
			renderer.setClosedIcon(iconTreeClosed);
		}

		ImageIcon iconTreeOpen = null; // for the TreeOpen-Icon
		URL iconOpenURL = ClassLoader.getSystemResource(iconsPathName
				+ "TreeOpen.gif");

		if (iconOpenURL != null) {
			iconTreeOpen = new ImageIcon(iconOpenURL, "tree is open icon");
		}

		if (iconTreeOpen != null) {
			renderer.setOpenIcon(iconTreeOpen);
		}

		/*
		 * renderer.setOpenIcon(null); renderer.setClosedIcon(null);
		 * renderer.setLeafIcon(null);
		 */
		myTransportTree.setCellRenderer(renderer);
		myTransportTree.setShowsRootHandles(true);

		// configure scroll pane
		myTreeScrollPane = new JScrollPane();
		myTreeScrollPane.getViewport().add(myTransportTree);
		add(myTreeScrollPane, BorderLayout.CENTER);
		// this.repaint();
		// Setting the TransferHandler for DnD
		// The TransportTree can be used as Source but not as Target for DnD
		this.myTransportTree.setTransferHandler(new TreeTransferHandler());

		this.myTransportTree.setDragEnabled(true);

	}

	public final void setTransportTree(final JTree transportTree) {
		this.myTransportTree = transportTree;
		myTransportTree.setRootVisible(true);

		// configure this
		setDoubleBuffered(true);
		setOpaque(false);
		setLayout(new BorderLayout());

		// configure the tree
		myTransportTree.setRowHeight(18);

		// for the icons:
		DefaultVerticalTreeCellRenderer renderer = new DefaultVerticalTreeCellRenderer();
		String iconsPathName = "de/fhg/iao/matrixbrowser/widgets/";
		ImageIcon iconLeaf = null; // for the TreeLeaf-Icon
		URL leafIconURL = ClassLoader.getSystemResource(iconsPathName
				+ "TreeLeaf.gif");

		if (leafIconURL != null) {
			iconLeaf = new ImageIcon(leafIconURL, "a tree leaf icon");
		}

		if (iconLeaf != null) {
			renderer.setLeafIcon(iconLeaf);
		}

		ImageIcon iconTreeClosed = null; // for the TreeClosed-Icon
		URL treeClosedIconURL = ClassLoader.getSystemResource(iconsPathName
				+ "TreeClosed.gif");

		if (treeClosedIconURL != null) {
			iconTreeClosed = new ImageIcon(treeClosedIconURL,
					"tree is closed icon");
		}

		if (iconTreeClosed != null) {
			renderer.setClosedIcon(iconTreeClosed);
		}

		ImageIcon iconTreeOpen = null; // for the TreeOpen-Icon
		URL iconOpenURL = ClassLoader.getSystemResource(iconsPathName
				+ "TreeOpen.gif");

		if (iconOpenURL != null) {
			iconTreeOpen = new ImageIcon(iconOpenURL, "tree is open icon");
		}

		if (iconTreeOpen != null) {
			renderer.setOpenIcon(iconTreeOpen);
		}

		/*
		 * renderer.setOpenIcon(null); renderer.setClosedIcon(null);
		 * renderer.setLeafIcon(null);
		 */
		myTransportTree.setCellRenderer(renderer);
		myTransportTree.setShowsRootHandles(true);

		// configure scroll pane
		myTreeScrollPane = new JScrollPane();
		myTreeScrollPane.getViewport().add(myTransportTree);
		add(myTreeScrollPane, BorderLayout.CENTER);
		// this.repaint();
		// Setting the TransferHandler for DnD
		// The TransportTree can be used as Source but not as Target for DnD
		this.myTransportTree.setTransferHandler(new TreeTransferHandler());

		this.myTransportTree.setDragEnabled(true);

	}

	/**
	 * Gets the Tree displayed in the TransportPanel.
	 * 
	 * @return the underlying tree
	 */
	public JTree getTransportTree() {
		return myTransportTree;
	}

	public void onTreeModelChanged(TreeModelChangedEvent aE) {
		getTransportTree().setModel(aE.getNewModel());
		// new DefaultTreeModel(myTreeSynthesizer.getVisibleTree()));
	}
}