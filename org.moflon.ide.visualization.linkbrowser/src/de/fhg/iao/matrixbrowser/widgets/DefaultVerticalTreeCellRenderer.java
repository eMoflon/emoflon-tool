/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * DefaultVerticalTreeCellRenderer.java,v 1.8 2004/04/21 08:42:07 kookaburra Exp $
 * Created on 25.09.01
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import de.fhg.iao.matrixbrowser.UIResourceRegistry;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEvent;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEventListener;
import de.fhg.iao.matrixbrowser.VisibleNode;

/**
 * Cell renderer showing a tooltip for the <code>DefaultVerticalTreeView</code>.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.5 $
 * @see javax.swing.tree.TreeCellRenderer
 * @see DefaultVerticalTreeView
 */
public class DefaultVerticalTreeCellRenderer extends DefaultTreeCellRenderer
		implements UIResourceRegistryEventListener {

	/** True if draws focus border around icon as well. */
	protected boolean drawsFocusBorderAroundIcon;

	/** Some storage variables for painting */
	protected Rectangle paintIconR = new Rectangle();

	/** Some storage variables for painting */
	protected Rectangle paintTextR = new Rectangle();

	/** Some storage variables for painting */
	protected Rectangle paintViewR = new Rectangle();

	/** Some storage variables for painting */
	protected Insets paintViewInsets = new Insets(0, 0, 0, 0);

	/**
	 * Constructs the <code>DefaultVerticalTreeCellRenderer</code>.
	 */
	public DefaultVerticalTreeCellRenderer() {
		super();
		initialize();
		UIResourceRegistry.getInstance().addUIResourceRegistryEventListener(
				this);
	}

	/**
	 * initialize icons and border
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
	 * Configures the actual component
	 * 
	 * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree,
	 *      java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	@Override
	public Component getTreeCellRendererComponent(final JTree tree, final Object value,
			final boolean sel, final boolean expanded, final boolean leaf, final int row,
			final boolean hasFocus) {
		VisibleNode node;
		String tool;
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		if (value instanceof VisibleNode) {
			node = (VisibleNode) value;
			tool = node.getNestedNode().getName();
		} else {
			node = (VisibleNode) ((DefaultMutableTreeNode) value)
					.getUserObject();
			tool = node.toString();
		}
		// /////////////////////////////////////Marwan Hier wurde die Farbe und
		// den Tooltipp f�r
		// / die Nodes implemntiert.
		Color col = node.getNodeBGColor();
		if (col != null) {
			setBackgroundNonSelectionColor(col);
		} else {
			setBackgroundNonSelectionColor(new ColorUIResource(222, 222, 222));
		}

		String description = node.getNestedNode().getDescription();
		if (description == null) {
			setToolTipText(tool);
		} else {
			setToolTipText(tool + " : " + description);
		}

		// ///////////////////////////////////////////////////////////////////////////////

		return this;
	}

	/**
	 * Paints the current row of the tree. Overwritten to size the background
	 * rectangle to the actual <code>FontMetrics</code> and not to the real row
	 * height.
	 * 
	 * @param g
	 *            the graphics context
	 */
	@Override
	public void paint(final Graphics g) {
		// this is ugly, since we have to recode JLabels paint method, or
		// more precisely the JLabels ui delegates paint method but
		// JLabel paints acordingly to the getBounds() method and not to
		// the JTree way g.getClipBounds

		Graphics2D g2 = (Graphics2D) g;
		// enable antialiasing
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		Color bColor;
		int imageOffset = getLabelStart();
		Icon currentI = getIcon();
		FontMetrics fm = g2.getFontMetrics();
		int height = fm.getHeight();
		String text = getText();

		if (((currentI == null) && (text == null)) || (text.equals(""))) {
			return;
		}

		g2.setFont(getFont());

		paintViewInsets = this.getInsets(paintViewInsets);

		paintViewR.x = paintViewInsets.left;
		paintViewR.y = paintViewInsets.top;
		paintViewR.height = getHeight();
		paintViewR.width = getWidth(); // g.getClipBounds().width;

		paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
		paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;

		String clippedText = SwingUtilities.layoutCompoundLabel(this, fm, text,
				currentI, SwingConstants.CENTER, SwingConstants.LEFT,
				SwingConstants.CENTER, SwingConstants.TRAILING, paintViewR,
				paintIconR, paintTextR, (currentI == null) ? 0
						: getIconTextGap());

		if (selected) {
			bColor = getBackgroundSelectionColor();
		} else {
			bColor = getBackgroundNonSelectionColor();

			if (bColor == null) {
				bColor = getBackground();
			}
		}

		// center the background rect
		int yOffs = (getHeight() / 2) - (height / 2);

		if (bColor != null) {
			g2.setColor(bColor);

			if (getComponentOrientation().isLeftToRight()) {
				g2.fillRect(imageOffset, yOffs, getWidth() - 1 - imageOffset,
						height);
			} else {
				g2.fillRect(yOffs, 0, getWidth() - 1 - imageOffset, height);
			}
		}

		if (hasFocus) {
			if (drawsFocusBorderAroundIcon) {
				imageOffset = 0;
			}

			Color bsColor = getBorderSelectionColor();

			if (bsColor != null) {
				g2.setColor(bsColor);

				if (getComponentOrientation().isLeftToRight()) {
					g2.drawRect(imageOffset, yOffs, getWidth() - 1
							- imageOffset, height);
				} else {
					g2.drawRect(0, yOffs, getWidth() - 1 - imageOffset, height);
				}
			}
		}

		if (currentI != null) {
			currentI.paintIcon(this, g2, paintIconR.x, paintIconR.y);
		}

		g2.setColor(getForeground());
		g2.drawString(clippedText, paintTextR.x, paintTextR.y + fm.getAscent());
	}

	/**
	 * Overrides <code>JComponent.getPreferredSize</code> to return slightly
	 * wider preferred size value.
	 */

	@Override
	public Dimension getPreferredSize() {
		Dimension retDimension = super.getPreferredSize();
		if (retDimension != null)
			retDimension = new Dimension(retDimension.width + 3,
					retDimension.height);
		return retDimension;
	}

	protected int getLabelStart() {
		Icon currentI = getIcon();

		if ((currentI != null) && (getText() != null)) {
			return currentI.getIconWidth() + Math.max(0, getIconTextGap() - 1);
		}

		return 0;
	}

	/**
	 * Implementation of the onLoad method of the
	 * UIResourceRegistryEventListener
	 */
	@Override
	public void onUIResourceRegistryChanged(final UIResourceRegistryEvent aEvent) {
		initialize();
	}
	private static final long serialVersionUID = -8887127120670561612L;
}
