/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * DefaultRelationRenderer.java,v 1.7 2004/04/14 11:47:56 maxmonroe Exp $
 * Created on 26.02.02
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JComponent;

import de.fhg.iao.matrixbrowser.RelationPane;
import de.fhg.iao.matrixbrowser.RelationRenderer;
import de.fhg.iao.matrixbrowser.UIResourceRegistry;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEvent;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEventListener;
import de.fhg.iao.matrixbrowser.VisibleRelation;
import de.fhg.iao.matrixbrowser.model.elements.Relation;

/**
 * Renders a <code>VisibleRelation</code> on the <code>RelationPane</code>.
 * Following keys must be defined in the <code>UIResourceRegistry</code> which
 * have to suppliy icons. <br>
 * RelationRenderer.RealRelation <br>
 * RelationRenderer.CollapsedRelation <br>
 * RelationRenderer.InferedRelationOpen <br>
 * RelationRenderer.InferedRelationCollapsed <br>
 * RelationRenderer.IdentityRelationRelationRenderer.UpArrow <br>
 * RelationRenderer.UpArrowSelected <br>
 * RelationRenderer.UpArrowRollOver <br>
 * RelationRenderer.RightArrow <br>
 * RelationRenderer.RightArrowSelected <br>
 * RelationRenderer.RightArrowRollOver <br>
 * RelationRenderer.DownArrow <br>
 * RelationRenderer.DownArrowSelected <br>
 * RelationRenderer.DownArrowRollOver <br>
 * RelationRenderer.LeftArrow <br>
 * RelationRenderer.LeftArrowSelected <br>
 * RelationRenderer.LeftArrowRollOver <br>
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.11 $
 * @see de.fhg.iao.matrixbrowser.UIResourceRegistry
 */
public class DefaultRelationRenderer extends JComponent implements
		RelationRenderer, UIResourceRegistryEventListener {

	/** The <code>RelationPane</code> s virtual border */
	protected int myRelationPaneVirtualBorder = 0;

	/** Variable for the centry x position of the renderer */
	protected int myCenterX = 0;

	/** Variable for the center y position of the renderer */
	protected int myCenterY = 0;

	/** The actual visible relation to render */
	protected VisibleRelation myVisibleRelation = null;

	/** The <code>RelationPane</code> s virtual bounds */
	protected Rectangle myRelationPaneVirtualBounds = null;

	/** The actual <code>RelationPane</code>. */
	protected RelationPane myRelationPane = null;

	/** The <code>Icon</code> for real relations */
	protected Icon myRealRelationIcon = null;

	/** The <code>Icon</code> for collapsed relations */
	protected Icon myCollapsedRelationIcon = null;

	/** The <code>Icon</code> for open infered relations */
	protected Icon myInferedRelationOpenIcon = null;

	/** The <code>Icon</code> for collapsed infered relations */
	protected Icon myInferedRelationCollapsedIcon = null;

	/** The <code>Icon</code> for identity relations */
	protected Icon myIdentityRelationIcon = null;

	/** The up arrow <code>Icon</code> for right relations */
	protected Icon myUpArrowIcon = null;

	/** The up arrow <code>Icon</code> for right relations */
	protected Icon myUpArrowRollOverIcon = null;

	/** The up arrow <code>Icon</code> for right relations */
	protected Icon myUpArrowSelectedIcon = null;

	/** The right arrow <code>Icon</code> for right relations */
	protected Icon myRightArrowIcon = null;

	/** The right arrow <code>Icon</code> for right relations */
	protected Icon myRightArrowRollOverIcon = null;

	/** The right arrow <code>Icon</code> for right relations */
	protected Icon myRightArrowSelectedIcon = null;

	/** The down arrow <code>Icon</code> for right relations */
	protected Icon myDownArrowIcon = null;

	/** The down arrow <code>Icon</code> for right relations */
	protected Icon myDownArrowRollOverIcon = null;

	/** The down arrow <code>Icon</code> for right relations */
	protected Icon myDownArrowSelectedIcon = null;

	/** The left arrow <code>Icon</code> for right relations */
	protected Icon myLeftArrowIcon = null;

	/** The left arrow <code>Icon</code> for right relations */
	protected Icon myLeftArrowRollOverIcon = null;

	/** The left arrow <code>Icon</code> for right relations */
	protected Icon myLeftArrowSelectedIcon = null;

	public static DefaultRelationPane relationPane = null;

	private static int maxRowForHorizontalTree = 0;

	private static int maxRowForVerticalTree = 0;

	boolean paintUpdater = true;

	/**
	 * Constructs a <code>DefaultRelationRenderer</code>
	 */
	public DefaultRelationRenderer() {

		myRelationPaneVirtualBounds = new Rectangle();

		initialize();
		UIResourceRegistry.getInstance().addUIResourceRegistryEventListener(
				this);

	}

	/**
	 * initialize components that can be updated via the
	 * UIResourceRegistryEventListener
	 */
	private void initialize() {
		// init icons

		myRealRelationIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().RELATIONRENDERER_REAL_RELATION);
		myCollapsedRelationIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().RELATIONRENDERER_COLLAPSED_RELATION);
		myInferedRelationOpenIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().RELATIONRENDERER_INFERED_RELATION_OPEN);
		myInferedRelationCollapsedIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().RELATIONRENDERER_INFERED_RELATION_COLLAPSED);
		myIdentityRelationIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().RELATIONRENDERER_IDENTITY_RELATION);
		myUpArrowIcon = UIResourceRegistry.getInstance().getIcon(
				UIResourceRegistry.getInstance().RELATIONRENDERER_UPARROW);
		myUpArrowSelectedIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().RELATIONRENDERER_UPARROW_SELECTED);
		myUpArrowRollOverIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().RELATIONRENDERER_UPARROW_ROLLOVER);

		myRightArrowIcon = UIResourceRegistry.getInstance().getIcon(
				UIResourceRegistry.getInstance().RELATIONRENDERER_RIGHTARROW);
		myRightArrowSelectedIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().RELATIONRENDERER_RIGHTARROW_SELECTED);
		myRightArrowRollOverIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().RELATIONRENDERER_RIGHTARROW_ROLLOVER);

		myDownArrowIcon = UIResourceRegistry.getInstance().getIcon(
				UIResourceRegistry.getInstance().RELATIONRENDERER_DOWNARROW);
		myDownArrowSelectedIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().RELATIONRENDERER_DOWNARROW_SELECTED);
		myDownArrowRollOverIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().RELATIONRENDERER_DOWNARROW_ROLLOVER);

		myLeftArrowIcon = UIResourceRegistry.getInstance().getIcon(
				UIResourceRegistry.getInstance().RELATIONRENDERER_LEFTARROW);
		myLeftArrowSelectedIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().RELATIONRENDERER_LEFTARROW_SELECTED);
		myLeftArrowRollOverIcon = UIResourceRegistry
				.getInstance()
				.getIcon(
						UIResourceRegistry.getInstance().RELATIONRENDERER_LEFTARROW_ROLLOVER);

	}

	/**
	 * Gets an instance of a <code>RelationRenderer</code>.
	 * 
	 * @param aRelationPane
	 *            the <code>RelationPane</code> which renderes the relations
	 * @param aCenterX
	 *            the centered x coordinate of the relation to render
	 * @param aCenterY
	 *            the centered y coordinate of the relation to render
	 * @param aRelation
	 *            the relation to render
	 * @return the actually configured component
	 */
	// /////////////////////////////////////////////Marwan Diese Methode letzte
	// zeile modi.
	@Override
	public JComponent getRelationRendererComponent(final RelationPane aRelationPane,
			final int aCenterX, final int aCenterY, final VisibleRelation aRelation) {
		myRelationPane = aRelationPane;
		myRelationPaneVirtualBorder = myRelationPane.getVirtualBorderWidth();
		myVisibleRelation = aRelation;

		myCenterX = aCenterX;
		myCenterY = aCenterY;
		myRelationPaneVirtualBounds = aRelationPane
				.getVirtualBounds(myRelationPaneVirtualBounds);

		relationPane = (DefaultRelationPane) aRelationPane;

		return this;

	}

	/**
	 * Paints the Renderer
	 * 
	 * @param g
	 *            a Graphics context
	 * @see java.awt.graphics#Graphics
	 */
	@Override
	public void paint(final Graphics g) {

		// paint matrix cells
		g.setClip(myRelationPaneVirtualBounds.x, myRelationPaneVirtualBounds.y,
				myRelationPaneVirtualBounds.width,
				myRelationPaneVirtualBounds.height);

		Icon paintedIcon = null;

		JComponent component = myVisibleRelation.getJComponent();
		int jComponentWidth = 0;
		int jComponentHeight = 0;

		switch (myVisibleRelation.getType()) {
		case INFERRED:
			if (component == null) {

				if (myVisibleRelation.isExpanded()) {
					// paint the open icon
					myInferedRelationOpenIcon.paintIcon(this, g, myCenterX
							- (myInferedRelationOpenIcon.getIconWidth() / 2),
							myCenterY
									- (myInferedRelationOpenIcon
											.getIconHeight() / 2));
					paintedIcon = myInferedRelationOpenIcon;

				} else {
					// paint the closed icon
					myInferedRelationCollapsedIcon.paintIcon(this, g,
							myCenterX
									- (myInferedRelationCollapsedIcon
											.getIconWidth() / 2), myCenterY
									- (myInferedRelationCollapsedIcon
											.getIconHeight() / 2));
					paintedIcon = myInferedRelationCollapsedIcon;
				}

			}

			else {

				jComponentWidth = component.getWidth();
				jComponentHeight = component.getHeight();

				maxRowForHorizontalTree = Math.max(jComponentWidth,
						maxRowForHorizontalTree);
				maxRowForVerticalTree = Math.max(jComponentHeight,
						maxRowForVerticalTree);

				// setze den Abstand zwischen die Knoten des horizontalen Baumes
				// gleich die gefundene Breite.
				myRelationPane.getMatrixBrowserPanel().getHorizontalTreeView()
						.setRowHeight(maxRowForHorizontalTree);
				// setze den Abstand zwischen die Knoten des vertikalen Baumes
				// gleich die gefundene Breite.
				myRelationPane.getMatrixBrowserPanel().getVerticalTreeView()
						.setRowHeight(maxRowForVerticalTree);
				// richte den JComponent ein, und f�ge ihn in dem Brwoser
				// hinzu.
				component.setBounds(myCenterX - jComponentWidth / 2, myCenterY
						- jComponentHeight / 2, jComponentWidth,
						jComponentHeight);
				relationPane.add(component);
				// den Browser neu zeichnen, nur wenn es n�tig ist, sonst ist
				// die
				// CPU Auslastung zu hoch.
				if (paintUpdater) {
					relationPane.repaint();
					paintUpdater = false;
				}
			}

			break;

		case COLLAPSED:

			// paint the collapsed relation icon
			myCollapsedRelationIcon.paintIcon(this, g, myCenterX
					- (myCollapsedRelationIcon.getIconWidth() / 2), myCenterY
					- (myCollapsedRelationIcon.getIconHeight() / 2));

			paintedIcon = myCollapsedRelationIcon;

			break;

		case REAL:

			// paint the real relation icon
			if (component == null) {

				myRealRelationIcon.paintIcon(this, g, myCenterX
						- (myRealRelationIcon.getIconWidth() / 2), myCenterY
						- (myRealRelationIcon.getIconHeight() / 2));

				paintedIcon = myRealRelationIcon;
			} else {// / Dasselbe wie bei RelationClass.INFERRED:
				jComponentWidth = component.getWidth();
				jComponentHeight = component.getHeight();

				maxRowForHorizontalTree = Math.max(jComponentWidth,
						maxRowForHorizontalTree);
				maxRowForVerticalTree = Math.max(jComponentHeight,
						maxRowForVerticalTree);

				myRelationPane.getMatrixBrowserPanel().getHorizontalTreeView()
						.setRowHeight(maxRowForHorizontalTree);
				myRelationPane.getMatrixBrowserPanel().getVerticalTreeView()
						.setRowHeight(maxRowForVerticalTree);
				component.setBounds(myCenterX - jComponentWidth / 2, myCenterY
						- jComponentHeight / 2, jComponentWidth,
						jComponentHeight);
				relationPane.add(component);

				if (paintUpdater) { // hier darf nur einmal repaint, ansonsten
									// h�he cpu-Auslastung
					relationPane.repaint();
					paintUpdater = false;
				}

			}

			break;
		case IDENTITY:

			// paint the real relation icon
			myIdentityRelationIcon.paintIcon(this, g, myCenterX
					- (myIdentityRelationIcon.getIconWidth() / 2), myCenterY
					- (myIdentityRelationIcon.getIconHeight() / 2));
			paintedIcon = myIdentityRelationIcon;

			break;
		}

		if (myVisibleRelation.isSelected()) {

			if (myVisibleRelation.getJComponent() == null) {
				g.drawRect(myCenterX - 1 - (paintedIcon.getIconWidth() / 2),
						myCenterY - 1 - (paintedIcon.getIconWidth() / 2),
						paintedIcon.getIconWidth() + 1, paintedIcon
								.getIconHeight() + 1);
			} else {
				// draw selected rectangle for jcomponent
				g.drawRect((myCenterX - jComponentWidth / 2) - 2,
						(myCenterY - jComponentHeight / 2) - 2,
						jComponentWidth + 2, jComponentHeight + 2);

			}

		}

		if (myVisibleRelation.isRollOver()) {
			g.setColor(myVisibleRelation.getRelationColor());

			if (myVisibleRelation.getJComponent() == null) {

				g.drawRect(myCenterX - 1 - (paintedIcon.getIconWidth() / 2),
						myCenterY - 1 - (paintedIcon.getIconWidth() / 2),
						paintedIcon.getIconWidth() + 1, paintedIcon
								.getIconHeight() + 1);
			}

			else {
				// draw rollOver rectangle for jcomponent
				g.drawRect((myCenterX - jComponentWidth / 2) - 2,
						(myCenterY - jComponentHeight / 2) - 2,
						jComponentWidth + 2, jComponentHeight + 2);

			}
		}

		g
				.setClip(myRelationPaneVirtualBounds.x
						- myRelationPaneVirtualBorder,
						myRelationPaneVirtualBounds.y
								- myRelationPaneVirtualBorder,
						myRelationPaneVirtualBounds.width
								+ myRelationPaneVirtualBorder,
						myRelationPaneVirtualBounds.height
								+ myRelationPaneVirtualBorder);

		switch (myVisibleRelation.getDirection()) {
		case DIRECTED:

			Relation relation = myVisibleRelation.getNestedRelation();

			if (relation != null) {
				if (myVisibleRelation.getVerticalVisibleNode().getNestedNode()
						.getID().equals(relation.getSourceNodeID())) {
					// relation is right directed -> paint right/up arrows
					paintRightUpArrow(g);
				} else {
					paintLeftDownArrow(g);
				}
			}

			break;

		case BIDIRECTIONAL:
			paintLeftArrow(g);
			paintUpArrow(g);

			break;
			case UNDIRECTED :
				break;
			default :
				break;
		}

	}

	public void resetJComponentSizeCalculating() {
		maxRowForHorizontalTree = 0;
		maxRowForVerticalTree = 0;
	}

	public DefaultRelationPane getRelationPane() {
		return relationPane;
	}

	/**
	 * Paints left directed relation.
	 * 
	 * @param g
	 *            - a Graphics context
	 * @see java.awt.graphics#Graphics
	 */
	protected void paintLeftDownArrow(final Graphics g) {
	   paintLeftArrow(g);
	   paintDownArrow(g);
	}
	
	protected void paintDownArrow(final Graphics g){
      Icon downIcon = myDownArrowIcon;
      
      if (myVisibleRelation.isSelected()) {
         downIcon = myDownArrowSelectedIcon;
      }else if (myVisibleRelation.isRollOver()) {

      downIcon = myDownArrowRollOverIcon;
      }
      // for the arrow that points to the bottom:
      downIcon.paintIcon(this, g, myCenterX - (downIcon.getIconWidth() / 2),
            myRelationPaneVirtualBounds.y - downIcon.getIconHeight());
   }
   
   protected void paintLeftArrow(final Graphics g){
      Icon leftIcon = myLeftArrowIcon;

      if (myVisibleRelation.isSelected()) {
         leftIcon = myLeftArrowSelectedIcon;
      } else if (myVisibleRelation.isRollOver()) {
         leftIcon = myLeftArrowRollOverIcon;
      }

      // for the arrow that points to the left
      leftIcon.paintIcon(this, g, myRelationPaneVirtualBounds.x
            - myRelationPaneVirtualBorder, myCenterY
            - (leftIcon.getIconHeight() / 2));
   }

	/**
	 * Paints right directed relation.
	 * 
	 * @param g
	 *            - a Graphics context
	 * @see java.awt.graphics#Graphics
	 */
	protected void paintRightUpArrow(final Graphics g) {
	   paintRightArrow(g);
	   paintUpArrow(g);
	}

	protected void paintUpArrow(final Graphics g){
	   Icon upIcon = myUpArrowIcon;
	   if (myVisibleRelation.isSelected()) {
	      upIcon = myUpArrowSelectedIcon;
	   } else if (myVisibleRelation.isRollOver()) {
	      upIcon = myUpArrowRollOverIcon;
	   }
	   
      // for the arrow, that points to top
      upIcon.paintIcon(this, g, myCenterX - (upIcon.getIconWidth() / 2),
            myRelationPaneVirtualBounds.y - myRelationPaneVirtualBorder);
	}
	
	protected void paintRightArrow(final Graphics g){
      Icon rightIcon = myRightArrowIcon;
      if (myVisibleRelation.isSelected()) {
         rightIcon = myRightArrowSelectedIcon;
      } else if (myVisibleRelation.isRollOver()) {
         rightIcon = myRightArrowRollOverIcon;
      }

      // for the arrow that points right
      rightIcon.paintIcon(this, g, myRelationPaneVirtualBounds.x
            - rightIcon.getIconWidth(), myCenterY
            - (rightIcon.getIconHeight() / 2));
   }
	
	/**
	 * Implementation of the onLoad method of the
	 * UIResourceRegistryEventListener
	 */
	@Override
	public void onUIResourceRegistryChanged(final UIResourceRegistryEvent aEvent) {
		initialize();
	}
	
	private static final long serialVersionUID = -8887127120670561668L;
}