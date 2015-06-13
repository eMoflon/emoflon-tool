/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * ConventionalHorizontalTreeCellRenderer.java,v 1.5 2004/04/14 11:47:56
 * maxmonroe Exp $ Created on 25.09.01
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.Component;

import javax.swing.JTree;

import de.fhg.iao.matrixbrowser.EncapsulatingVisibleNode;
import de.fhg.iao.matrixbrowser.UIResourceRegistry;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEvent;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEventListener;
import de.fhg.iao.swt.xswing.HorizontalTreeCellRenderer;

/**
 * Cell Renderer with tooltip for the
 * <code>ConventionalHorizontalTreeView</code>.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.4 $
 */
public class ConventionalHorizontalTreeCellRenderer extends
		HorizontalTreeCellRenderer implements UIResourceRegistryEventListener {

	/**
	 * Constructs a <code>ConventionalHorizontalTreeCellRenderer</code>.
	 */
	public ConventionalHorizontalTreeCellRenderer() {
		// set icons:
		initialize();
		UIResourceRegistry.getInstance().addUIResourceRegistryEventListener(
				this);
	}

	/**
	 * initialize icons
	 */
	private void initialize() {
		setLeafIcon(UIResourceRegistry.getInstance().getIcon(
				UIResourceRegistry.getInstance().TREEVIEW_LEAFICON));
		setClosedIcon(UIResourceRegistry.getInstance().getIcon(
				UIResourceRegistry.getInstance().TREEVIEW_COLLAPSEDICON));
		setOpenIcon(UIResourceRegistry.getInstance().getIcon(
				UIResourceRegistry.getInstance().TREEVIEW_OPENICON));
	}

	/**
	 * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree,
	 *      java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	@Override
	public Component getTreeCellRendererComponent(final JTree tree, final Object value,
			final boolean sel, final boolean expanded, final boolean leaf, final int row,
			final boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);

		EncapsulatingVisibleNode node = ((EncapsulatingVisibleNode) value);
		String str = ((EncapsulatingVisibleNode) value).getNestedNode()
				.getName();

		setToolTipText(str);

		return this;
	}

	/**
	 * Implementation of the onLoad method of the
	 * UIResourceRegistryEventListener
	 */
	@Override
	public void onUIResourceRegistryChanged(final UIResourceRegistryEvent aEvent) {
		initialize();
	}
	
	private static final long serialVersionUID = -8887127120670561667L;
}