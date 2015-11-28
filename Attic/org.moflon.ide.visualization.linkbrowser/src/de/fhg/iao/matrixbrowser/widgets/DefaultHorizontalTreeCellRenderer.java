/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * DefaultHorizontalTreeCellRenderer.java,v 1.5 2004/04/14 11:47:56 maxmonroe
 * Exp $ Created on 25.09.01
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.DefaultMutableTreeNode;

import de.fhg.iao.matrixbrowser.UIResourceRegistry;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEvent;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEventListener;
import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.swt.xswing.DefaultHorizontalRotateTreeCellRenderer;

/**
 * Cell renderer with tooltip for the <code>DefaultHorizontalTreeView</code>.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.7 $
 * @see DefaultHorizontalTreeView
 */
public class DefaultHorizontalTreeCellRenderer extends
		DefaultHorizontalRotateTreeCellRenderer implements
		UIResourceRegistryEventListener {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a <code>DefaultHorizontalTreeCellRenderer</code>.
	 */
	public DefaultHorizontalTreeCellRenderer() {
		super();
		initialize();
		UIResourceRegistry.getInstance().addUIResourceRegistryEventListener(
				this);
	}

	/**
	 * Initialise icons and border
	 */
	private void initialize() {

		Object value = UIManager
				.get(UIResourceRegistry.getInstance().TREE_DRAWFOCUSBORDERAROUNDICON);
		drawsFocusBorderAroundIcon = ((value != null) && ((Boolean) value)
				.booleanValue());

		// set icons
		setLeafIcon(UIResourceRegistry.getInstance().getIcon(
				UIResourceRegistry.getInstance().TREEVIEW_LEAFICON));
		setClosedIcon(UIResourceRegistry.getInstance().getIcon(
				UIResourceRegistry.getInstance().TREEVIEW_COLLAPSEDICON));
		setOpenIcon(UIResourceRegistry.getInstance().getIcon(
				UIResourceRegistry.getInstance().TREEVIEW_OPENICON));

	}

	/**
	 * @return the ready configured <code>Component</code> rendering the actual
	 *         <code>VisibleNode</code>.
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		if (value instanceof VisibleNode) {

			VisibleNode node = ((VisibleNode) value);
			String str = ((VisibleNode) value).getNestedNode().getName();

			Color col = node.getNodeBGColor();

			if (col != null) {
				setBackgroundNonSelectionColor(col);
			} else {
				setBackgroundNonSelectionColor(new ColorUIResource(222, 222,
						222));
			}

			String description = node.getNestedNode().getDescription();
			if (description == null) {
				setToolTipText(str);

			} else {
				setToolTipText(str + " : " + description);
			}

			return this;
		} else {
			super.getTreeCellRendererComponent(tree, value, sel, expanded,
					leaf, row, hasFocus);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			setBackgroundNonSelectionColor(new ColorUIResource(222, 222, 222));
			setToolTipText(node.toString());
			return this;
		}

		// Color col = node.getNodeBGColor();
	}

	/**
	 * Implementation of the onLoad method of the
	 * UIResourceRegistryEventListener
	 */
	public void onUIResourceRegistryChanged(UIResourceRegistryEvent aEvent) {
		initialize();
	}
}