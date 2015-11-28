/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications
 * derivatives thereof. 
 * 
 * It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 *    $Id: DefaultRelationPane.java,v 1.21 2010-04-29 10:11:49 smueller Exp $
 * 
 * Created on 20.01.2002
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import javax.swing.TransferHandler;
import javax.swing.event.EventListenerList;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import de.fhg.iao.matrixbrowser.HorizontalTreeView;
import de.fhg.iao.matrixbrowser.MatrixBrowserPanel;
import de.fhg.iao.matrixbrowser.RelationPane;
import de.fhg.iao.matrixbrowser.RelationRenderer;
import de.fhg.iao.matrixbrowser.TreeView;
import de.fhg.iao.matrixbrowser.UIResourceRegistry;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEvent;
import de.fhg.iao.matrixbrowser.UIResourceRegistryEventListener;
import de.fhg.iao.matrixbrowser.VerticalTreeView;
import de.fhg.iao.matrixbrowser.VisibleNode;
import de.fhg.iao.matrixbrowser.VisibleRelation;
import de.fhg.iao.matrixbrowser.VisibleRelationEvent;
import de.fhg.iao.matrixbrowser.VisibleRelationEventListener;
import de.fhg.iao.matrixbrowser.context.TreeCombinator;
import de.fhg.iao.matrixbrowser.context.TreeModelChangedEvent;
import de.fhg.iao.matrixbrowser.context.TreeModelChangedListener;
import de.fhg.iao.matrixbrowser.context.TreeProxyManager;
import de.fhg.iao.matrixbrowser.model.ICommand;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.matrixbrowser.model.elements.RelationType;
import de.fhg.iao.swt.util.uniqueidentifier.ID;
import de.fhg.iao.swt.xswing.JContextMenu;
import de.fhg.iao.swt.xswing.JHorizontalRotateTree;

/**
 * Default implementation of a (@link <code>RelationPane</code>).
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.21 $
 */
public class DefaultRelationPane extends JPanel implements RelationPane,
		UIResourceRegistryEventListener {

	private static final Logger log = Logger
			.getLogger(DefaultRelationPane.class);

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/** Holds a list of event listeners */
	protected EventListenerList myEventListenerList = null;

	/** Tool tip string for collapsed relations */
	protected String myCollapsedRelationDescription = null;

	/** Tool tip string for identity relations */
	protected String myIdentityRelationDescription = null;

	/** The dnd enabled state */
	protected boolean myDnDEnabledState = false;

	/** The actual gesture recognizer of this relation pane */
	protected RelationPaneDragGestureRecognizer myGestureRecognizer = null;

	/** Saves the last mouse position, when clicked */
	protected Point myLastMouseClickPosition = null;

	/** Reference to the matrixbrowser panel */
	private MatrixBrowserPanel myMatrixBrowserPanel = null;

	/** The JContextMenu property */
	protected JContextMenu myContextMenu = null;

	/** holds an instance of a RelationRenderer component */
	protected RelationRenderer myRelationRenderer = null;

	/** Holds the currently roll overed VisibleRelation */
	protected VisibleRelation myRollOverRelation = null;

	/** Holds the currently selected VisibleRelation */
	protected VisibleRelation mySelectedRelation = null;

	/** Holds the currently selected VisibleRelation */
	protected Vector<VisibleRelation> mySelectedRelationVector = new Vector<VisibleRelation>();

	/** All the relation types, which should be rendered */
	private Collection<RelationType> myVisibleRelationTypes = null;

	/** the cache of currently visible relations */
	protected List<VisibleRelation> myVisibleRelationsCache = null;

	/** Constatnt identifiying the virtual border size */
	protected int VIRTUAL_BORDER = 16;

	// unused
	// private static final Color SELECTED_RELATION_COLOR = Color.red;
	// unused
	// private static final Color ROLLOVER_RELATION_COLOR = Color.BLUE;

	/***/
	private Image offScreenRelationBuffer = null, offScreenDragBuffer = null;

	/***/
	private boolean mustRepaint = true;

	/***/
	private HashSet<ID> myVerticalNodeIdHashSet = new HashSet<ID>();

	/***/
	private HashSet<ID> myHorizontalNodeIdHashSet = new HashSet<ID>();

	private boolean wasContextMenuDisplayed = false;

	int coordinateX, coordinateY = 0;
	boolean repainter = true;
	VisibleRelation visibleRelation;
	/**
	 * Stores the additional x offset of visible tree nodes of the horizontal
	 * tree view in case its a <code>JHorizontalRotateTree</code> It important
	 * because the entries of the tree are rotated and so the vertical lines of
	 * the relation pane move additionally when the vertical height of the
	 * horizontal tree changes.
	 */
	protected int myXOffsRotate = 0;

	// private Vector jComp=null; //never read

	/**
	 * Constructs a <code>DefaultRelationPane</code> given its parent
	 * <code>MatrixBrowserPanel</code>
	 * 
	 * @param aMatrixBrowserPanel
	 *            teh <code>MatrixBrowserPanel</code> controlling this
	 *            <code>DefaultRelationPane</code>
	 * @see de.fhg.iao.matrixbrowser.MatrixBrowserPanel
	 */

	public DefaultRelationPane(final MatrixBrowserPanel aMatrixBrowserPanel) {
		this.setLayout(null);
		this.setBorder(null);
		this.setOpaque(false);
		setRelationRenderer(new DefaultRelationRenderer());
		myMatrixBrowserPanel = aMatrixBrowserPanel;

		initialize();

		// set up context menu
		setContextMenu(getContextMenu());

		// setup member variables and caches
		myEventListenerList = new EventListenerList();
		myVisibleRelationsCache = Collections.emptyList();

		myMatrixBrowserPanel
				.addTreeModelChangedListener(new TreeModelChangedHandler());

		// DnD
		myGestureRecognizer = new RelationPaneDragGestureRecognizer();
		addMouseListener(myGestureRecognizer);
		addMouseMotionListener(myGestureRecognizer);
		VisibleRelationEventHandler myVisibleRelationEventHandler = new VisibleRelationEventHandler();
		addVisibleRelationEventListener(myVisibleRelationEventHandler);

		MouseEventHandler mouseHandler = new MouseEventHandler();
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);

		ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		toolTipManager.registerComponent(this);
		myVisibleRelationTypes = new HashSet<RelationType>();

		// set transfer handler
		this.setTransferHandler(new RelationPaneTransferHandler());
		UIResourceRegistry.getInstance().addUIResourceRegistryEventListener(
				this);
	}

	/**
	 * initialize components that can be updated via the
	 * UIResourceRegistryEventListener
	 */

	private void initialize() {
		this.setBackground(Color.white);

		myCollapsedRelationDescription = UIResourceRegistry
				.getInstance()
				.getString(
						UIResourceRegistry.getInstance().RELATIONPANE_COLLAPSEDRELATION_TOOLTIP); //$NON-NLS-1$
		myIdentityRelationDescription = UIResourceRegistry
				.getInstance()
				.getString(
						UIResourceRegistry.getInstance().RELATIONPANE_IDENTITYRELATION_TOOLTIP); //$NON-NLS-1$
	}

	/**
	 * Implementation of the onLoad method of the
	 * UIResourceRegistryEventListener
	 */

	@Override
   public void onUIResourceRegistryChanged(final UIResourceRegistryEvent aEvent) {
		initialize();
		getContextMenu().setName(
				UIResourceRegistry.getInstance().TREEVIEW_CONTEXTMENU_TITLE);
	}

	// Event procedures
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
	@Override
   public void addVisibleRelationEventListener(
			final VisibleRelationEventListener aVisibleRelationEventListener) {
		myEventListenerList.add(VisibleRelationEventListener.class,
				aVisibleRelationEventListener);
	}

	/**
	 * @return the default context menu the default <code>JContextMenu</code> of
	 *         this <code>IAODefaultRelationpane</code> when switched to editing
	 *         mode
	 * @see de.fhg.iao.swt.xswing.JContextMenu
	 */

	protected void createDefaultContextMenu() {
		myContextMenu = new JContextMenu(
				UIResourceRegistry.getInstance().RELATIONPANE_CONTEXTMENU_TITLE,
				this); //$NON-NLS-1$

		myContextMenu.add(new NodesDragSelectedAction());
		myContextMenu.add(new DeleteRelationAction());
		myContextMenu.add(new EditRelationAction());

	}

	/**
	 * Baut ein neues ContextMenu fuer Relations. Ueblicherweise wird dieses
	 * direkt vom Model uebergeben, es ist jedoch sinnvoll, ein Default-Menu
	 * fuer Relations mit der Methode setDefaultRelationConextMenu zu zu
	 * uebergeben. So haben Relations je nach Typ bereits vorgefertigte
	 * EintrÃ¤ge.
	 * 
	 * @param clickedRelation
	 *            Die Relation, die geclicked wurde, um ihr Kontextmeue zu
	 *            oeffnen.
	 * @return
	 */
	protected JContextMenu createRelationContextMenu(
			final VisibleRelation clickedRelation) {

		JContextMenu menu = new JContextMenu(
				UIResourceRegistry.getInstance().RELATIONPANE_CONTEXTMENU_TITLE,
				this);

		Vector<ICommand> allFunctions = (Vector<ICommand>) clickedRelation
				.getRelContextMenu();
		if (allFunctions != null) // was (allFunctions == null)
			try {
				for (int i = 0; i < allFunctions.size(); i++) {
					JMenuItem g = new JMenuItem(allFunctions
							.elementAt(i).getName());
					RelHandler handler = new RelHandler(clickedRelation, i);
					g.addActionListener(handler);
					menu.add(g);
				}
			} catch (Exception h) {
				log.debug("EXCEPTION!!! DEFAULT RELATIONPANE.JCONTEXTMENU");
			}
		return menu;

	}

	protected class RelHandler implements ActionListener {

		private int itemNumber;

		Vector<ICommand> allItems;

		public RelHandler(final VisibleRelation rel, final int i) {
			this.itemNumber = i;
			allItems = (Vector<ICommand>) rel.getRelContextMenu();
		}

		@Override
      public void actionPerformed(final ActionEvent e) {

			try {
				for (int f = 0; f < allItems.size(); f++) {
					if (itemNumber == f) {
						allItems.elementAt(itemNumber).execute();
					}
				}
			} catch (Exception er) {
			}
		}
	}

	// /////////////////////////////////////////////////////////////////////////
	/**
	 * Sets the rollover state of all <code>VisibleRelation</code> objects to
	 * false.
	 */
	protected void deRollOverVisibleRelations() {
		for (int i = 0; i < myVisibleRelationsCache.size(); i++) {
			VisibleRelation aRelation = myVisibleRelationsCache
					.get(i);
			aRelation.setRollOver(false);
		}
	}

	/**
	 * Deselects all <code>VisibleRelation</code> objects.
	 */
	protected void deSelectVisibleRelations() {
		for (int i = 0; i < myVisibleRelationsCache.size(); i++) {
			VisibleRelation aRelation = myVisibleRelationsCache
					.get(i);
			aRelation.setSelected(false);
		}
	}

	/**
	 * Fires an <code>VisibleRelationEvent<\code>by calling 'onRelationSelected'
	 * at the listener. This should happen when a relation was clicked in the 
	 * <code>RelationPane</code> and therefore selected. *
	 * 
	 * @param aVisibleRelationEvent
	 *            the <code>VisibleRelationEvent</code> to fire
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEventListener
	 */
	protected void fireRelationClickedEvent(
			final VisibleRelationEvent aVisibleRelationEvent) {

		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleRelationEventListener.class) {

				((VisibleRelationEventListener) listeners[i + 1])
						.onRelationClicked(aVisibleRelationEvent);
				// .onRelationSelected(aVisibleRelationEvent);
			}
		}
	}

	/**
	 * Fires an <code>VisibleRelationEvent<\code>by calling 'onRelationEdit'
	 * at the listener. This should happen when a relation was clicked in the 
	 * <code>RelationPane</code> and the
	 * <code>MatrixBrowserPanel</code> is in editing mode. *
	 * 
	 * @param aVisibleRelationEvent
	 *            the <code>VisibleRelationEvent</code> to fire
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEventListener
	 */
	protected void fireRelationEditEvent(

	final VisibleRelationEvent aVisibleRelationEvent) {

		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleRelationEventListener.class) {
				((VisibleRelationEventListener) listeners[i + 1])
						.onRelationEdit(aVisibleRelationEvent);
			}
		}
	}

	/**
	 * Fires an <code>VisibleRelationEvent<\code>by calling 'onRelationOver'
	 * at the listener. This should happen when a relation was mouse overed in the 
	 * <code>RelationPane</code>. *
	 * 
	 * @param aVisibleRelationEvent
	 *            the <code>VisibleRelationEvent</code> to fire
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEventListener
	 */
	protected void fireRelationOverEvent(
			final VisibleRelationEvent aVisibleRelationEvent) {
		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleRelationEventListener.class) {
				((VisibleRelationEventListener) listeners[i + 1])
						.onRelationOver(aVisibleRelationEvent);
			}
		}
	}

	/**
	 * Fires an <code>VisibleRelationEvent</code>by calling 'onRelationSelected'
	 * at the listener. This should happen when a
	 * relation was mouse overed in the <code>RelationPane</code>. *
	 * 
	 * @param aVisibleRelationEvent
	 *            the <code>VisibleRelationEvent</code> to fire
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEventListener
	 */
	protected void fireRelationSelectedEvent(
			final VisibleRelationEvent aVisibleRelationEvent) {
		Object[] listeners = myEventListenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == VisibleRelationEventListener.class) {
				((VisibleRelationEventListener) listeners[i + 1])
						.onRelationSelected(aVisibleRelationEvent);
			}
		}
	}

	/**
	 * @return Returns the <code>JContextMenu</code> of this
	 *         <code>RelationPane</code>. If the actual one is null, a default
	 *         context menu is returned.
	 */
	@Override
   public JContextMenu getContextMenu() {
		if (myContextMenu == null) {
			createDefaultContextMenu();
		}
		return myContextMenu;

	}

	/**
	 * @return the drag and drop mechanisms enabled state.
	 */
	@Override
   public boolean getDragEnabled() {
		return myDnDEnabledState;
	}

	/**
	 * Gets the the row in the <code>HorizontalTreeView<\code>for an 
   * x coordinate in the <code>MatrixBrowserPanel<\code>.
	 * 
	 * @param aX
	 *            x coordinate in the <code>MatrixBrowserPanel<\code>
	 * @return the row number in the <code>HorizontalTreeView<\code>
	 * @see de.fhg.iao.matrixbrowser.HorizontalTreeView
	 */
	@Override
   public int getHorizontalRowForX(final int aX) {
		int retVal = (aX - myXOffsRotate + getMatrixBrowserPanel()
				.getHorizontalTreeView().getScrollOffsetX()) / getMatrixBrowserPanel()
				.getHorizontalTreeView().getRowHeight();

		return retVal;
	}

	/**
	 * Gets the the row in the <code>HorizontalTreeView<\code>for an 
   * x coordinate in the <code>MatrixBrowserPanel<\code>.
	 * 
	 * @param aX
	 *            x coordinate in the <code>MatrixBrowserPanel<\code>
	 * @param offset
	 *            scroll offset of the <code>HorizontalTreeView<\code>
	 * @return the row number in the <code>HorizontalTreeView<\code>
	 * @see de.fhg.iao.matrixbrowser.HorizontalTreeView
	 */
	public int getHorizontalRowForX(final int aX, final int offset) {
		int retVal = (aX - myXOffsRotate + offset) / getMatrixBrowserPanel()
				.getHorizontalTreeView().getRowHeight();

		return retVal;
	}

	/**
	 * Gets the the row in the <code>VerticalTreeView<\code>for an 
   * y coordinate in the <code>MatrixBrowserPanel<\code>.
	 * 
	 * @param aY
	 *            y coordinate in the <code>MatrixBrowserPanel<\code>
	 * @return the row number in the <code>VerticalTreeView<\code>
	 * @see de.fhg.iao.matrixbrowser.VerticalTreeView
	 */
	@Override
   public int getVerticalRowForY(final int aY) {
		return (aY
				- ((JComponent) getMatrixBrowserPanel().getVerticalTreeView())
						.getLocation().y + getMatrixBrowserPanel()
				.getVerticalTreeView().getScrollOffsetY()) / getMatrixBrowserPanel()
				.getVerticalTreeView().getRowHeight();
	}

	/**
	 * Gets the the row in the <code>VerticalTreeView<\code>for an 
   * y coordinate in the <code>MatrixBrowserPanel<\code>.
	 * 
	 * @param aY
	 *            y coordinate in the <code>MatrixBrowserPanel<\code>
	 * @param offset
	 *            scroll offset of the <code>HorizontalTreeView<\code>
	 * @return the row number in the <code>VerticalTreeView<\code>
	 * @see de.fhg.iao.matrixbrowser.VerticalTreeView
	 */
	public int getVerticalRowForY(final int aY, final int offset) {
		return (aY
				- ((JComponent) getMatrixBrowserPanel().getVerticalTreeView())
						.getLocation().y + offset) / getMatrixBrowserPanel()
				.getVerticalTreeView().getRowHeight();
	}

	/**
	 * @return the <code>MatrixBrowserPanel</code> this
	 *         <code>RelationPane</code> handels the relations in the matrix
	 *         cells
	 */
	@Override
   public MatrixBrowserPanel getMatrixBrowserPanel() {
		return myMatrixBrowserPanel;
	}

	/**
	 * Gets the actual <code>RelationRenderer</code> component.
	 * 
	 * @return aRenderer the <code>RelationRenderer</code>
	 * @see de.fhg.iao.matrixbrowser.RelationRenderer
	 */
	@Override
   public RelationRenderer getRelationRenderer() {
		return myRelationRenderer;
	}

	/**
	 * @return the last mouse overed <code>VisibleRelation</code> object.
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	@Override
   public VisibleRelation getRollOverRelation() {
		// return null; // Why?
		return myRollOverRelation; // looks logical, doesn't seem to change
									// anything
	}

	/**
	 * @return the last selected <code>VisibleRelation</code> object.
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	@Override
   public VisibleRelation getSelectedRelation() {
		return mySelectedRelation;
	}

	/**
	 * @return the last selected <code>VisibleRelation</code> object.
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	public Vector<VisibleRelation> getSelectedRelationVector() {
		return mySelectedRelationVector;
	}

	/**
	 * Returns the string to be used as the tooltip for <i>event </i>. By
	 * default this returns any string set using setToolTipText(). If a
	 * component provides more extensive API to support differing tooltips at
	 * different locations, this method should be overridden.
	 * 
	 * @param e
	 *            the MouseEvent, which triggers a tooltip
	 * @return a String containing the description a IAORelation provides.
	 * @see java.awt.event#MouseEvent
	 * @see javax.swing#JComponent
	 */
	// ////////////////////////////////////////Marwan diese Methode wird nich
	// mehr
	// benutzt
	/*
	 * public String getToolTipText(MouseEvent e) { VisibleRelation aRelation =
	 * getVisibleRelationForRow(getHorizontalRowForX(e .getX()),
	 * getVerticalRowForY(e.getY()));
	 * 
	 * 
	 * 
	 * if (aRelation != null) {
	 * 
	 * if (aRelation.getType() != RelationClass.COLLAPSED ) {
	 * 
	 * 
	 * 
	 * String description =aRelation.getRelDescription();/// String
	 * name=aRelation.getRelTypName();/// // JComponent
	 * comp=aRelation.getJComponent(); ///////////////////////// if(name!=null){
	 * 
	 * if (description != null) { return name+" : "+description; } else { if
	 * (aRelation.getType() == RelationClass.IDENTITY) { return
	 * name+" : "+description; } else return name; } } ///////////////////// if
	 * (description != null) { return description; } else { if
	 * (aRelation.getType() == RelationClass.IDENTITY) { return description; }
	 * else return null; } } else { return myCollapsedRelationDescription; } }
	 * return null; }
	 */
	// Dafï¿½r ist diese neue Methode implementiert.
	@Override
   public String getToolTipText(final MouseEvent e) {
		VisibleRelation aRelation = getVisibleRelationForRow(
				getHorizontalRowForX(e.getX()), getVerticalRowForY(e.getY()));

		if (aRelation != null) {
			if (aRelation.getType() != RelationClass.COLLAPSED) {
				// JComponent comp=aRelation.getJComponent();
				// if(comp!=null){return null;}
				String description = aRelation.getRelDescription();
				String name = aRelation.getRelTypName();

				if (name != null) {
					if (description != null) {

						return name + " : " + description;
					} else {

						return name;
					}
				} else {
					if (description != null) {

						return description;
					} else {

						return null;
					}
				}
			}

			else {
				return myCollapsedRelationDescription;
			}

		}

		else {
			return null;
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @return the virtual border of the <code>RelationPane</code> to draw e.g.
	 *         arrows showing the direction/type of relations. |---------
	 *         NodeCloseUpView ----------| |- VerticalTreeView -|- virt.
	 *         border-|-RelationPane-|
	 */
	@Override
   public int getVirtualBorderWidth() {
		return VIRTUAL_BORDER;
	}

	/**
	 * Returns the virtual bounds of the relation pane. Since the relation panes
	 * size is equal to the matrix browser panel size, the virtual size is the
	 * rectangle starting at the node closeup view panels buttom right corner.
	 * 
	 * @return a <code>Rectangle</code> containing the virtual bounds
	 */
	@Override
   public Rectangle getVirtualBounds() {
		return getVirtualBounds(null);
	}

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
	@Override
   public Rectangle getVirtualBounds(Rectangle aRectangle) {
		if (aRectangle == null) {
			aRectangle = new Rectangle();
		}
		aRectangle.x = ((JComponent) getMatrixBrowserPanel()
				.getNodeCloseUpView()).getWidth();
		aRectangle.y = ((JComponent) getMatrixBrowserPanel()
				.getNodeCloseUpView()).getHeight();
		aRectangle.width = getWidth() - aRectangle.x;
		aRectangle.height = getHeight() - aRectangle.y;
		return aRectangle;
	}

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
	@Override
   public VisibleRelation getVisibleRelationForPath(final TreePath horizontalPath,
			final TreePath verticalPath) {
		for (int i = 0; i < myVisibleRelationsCache.size(); i++) {
			VisibleRelation aRelation = myVisibleRelationsCache
					.get(i);
			if (((VisibleNode) aRelation.getHorizontalPath()
					.getLastPathComponent()).equals(horizontalPath
					.getLastPathComponent())
					&& ((VisibleNode) aRelation.getVerticalPath()
							.getLastPathComponent()).equals(verticalPath
							.getLastPathComponent())) {
				return aRelation;
			}
		}
		return null;
	}

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
	@Override
   public VisibleRelation getVisibleRelationForRow(final int horizontalRow,
			final int verticalRow) {
		TreePath horizPath = getMatrixBrowserPanel().getHorizontalTreeView()
				.getPathForRow(horizontalRow);
		TreePath vertPath = getMatrixBrowserPanel().getVerticalTreeView()
				.getPathForRow(verticalRow);
		for (int i = 0; i < myVisibleRelationsCache.size(); i++) {
			VisibleRelation aRelation = myVisibleRelationsCache
					.get(i);
			if ((aRelation.getHorizontalPath().equals(horizPath) && (aRelation
					.getVerticalPath().equals(vertPath)))) {
				return aRelation;
			}
		}
		return null;
	}

	/**
	 * @return the actual <code>Set</code> of visible <code>RelationType</code>
	 *         s.
	 * @see de.fhg.iao.matrixbrowser.model.elements.RelationType
	 */
	@Override
   public Collection<RelationType> getVisibleRelationTypes() {
		return myVisibleRelationTypes;
	}

	/**
	 * updates the graphics context using an offscreen image as buffer
	 */
	@Override
   public void update(final Graphics g) {

		Graphics gr;
		// Will hold the graphics context from the offScreenBuffer.
		// We need to make sure we keep our offscreen buffer the same size
		// as the graphics context we're working with.
		if (offScreenRelationBuffer == null
				|| (!(offScreenRelationBuffer.getWidth(this) == getBounds()
						.getWidth() && offScreenRelationBuffer.getHeight(this) == getBounds()
						.getHeight()))) {
			// We need to use our buffer Image as a Graphics object:

			offScreenRelationBuffer = createImage(getBounds().width,
					getBounds().height);
		}
		gr = offScreenRelationBuffer.getGraphics();

		if (mustRepaint) {
			// repaint();

			paintAll(gr);
		}
		mustRepaint = false;
		// log.debug("Graphic is complete:"+
		g.drawImage(offScreenRelationBuffer, 0, 0, null);
		// );

		gr.dispose();
	}

	/**
	 * paints the dragging rectangle r using an offscreen image as buffer
	 * 
	 * @param r
	 */
	public void paintDraggingRectangle(final Rectangle r) {
		Graphics gr;
		if (r == null) {
			repaint();
			return;
		}

		if (offScreenDragBuffer == null
				|| (!(offScreenDragBuffer.getWidth(this) == getBounds()
						.getWidth() && offScreenDragBuffer.getHeight(this) == getBounds()
						.getHeight()))) {
			offScreenDragBuffer = this.createImage(getWidth(), getHeight());
		}
		// offScreenDragBuffer = this.createImage(r.width+2, r.height+2);

		// We need to use our buffer Image as a Graphics object:
		gr = offScreenDragBuffer.getGraphics();

		mustRepaint = true;
		update(gr);

		gr.setColor(Color.red);
		gr.drawRect(r.x, r.y, r.width, r.height);
		getGraphics().drawImage(offScreenDragBuffer, 0, 0, null);
		gr.dispose();
	}

	/**
	 * Get all relation ids that are in a given rectangle and return a hashset
	 * with these ids
	 */
	public HashSet<ID> getVisibleRelationIDsForRectangle(final Rectangle r,
			final int offsetX, final int offsetY) {

		HashSet<ID> myHashSet = new HashSet<ID>();
		myVerticalNodeIdHashSet.clear();
		myHorizontalNodeIdHashSet.clear();
		setSelectedRelation(null);
		// deRollOverVisibleRelations();
		// deSelectVisibleRelations();
		// get all horizontal rows from beginning of r to end of r
		// increment is rowHeight of horizontal tree to avoid retrieving same
		// row more than once
		// Vector visibleRelationVector = new Vector();
		for (int x = r.x; x < r.x + r.width; x += getMatrixBrowserPanel()
				.getHorizontalTreeView().getRowHeight()) {

			int horizontalRow = getHorizontalRowForX(x, offsetX);

			// get all vertical rows from beginning of r to end of r
			// increment is rowHeight of vertical tree to avoid retrieving same
			// row more than once

			for (int y = r.y; y < r.y + r.height; y += getMatrixBrowserPanel()
					.getVerticalTreeView().getRowHeight()) {
				int verticalRow = getVerticalRowForY(y, offsetY);
				// get the relation of the row (x,y)
				VisibleRelation myRelation = getVisibleRelationForRow(
						horizontalRow, verticalRow);
				// if there is a relation, continue here
				if (myRelation != null) {

					setSelectedRelation(myRelation);

					// if selected relation is a collapsed relation,
					// add all its children to the hashset, too!
					// this is applied recursively for all children!
					if (myRelation.getType() == RelationClass.COLLAPSED
							|| myRelation.getType() == RelationClass.INFERRED) {

						// add vertical visible nodes to hashset
						Vector<VisibleNode> v = myRelation
								.getVerticalVisibleNode().getChildren();
						if (v != null) {
							for (int i = 0; i < v.size(); i++) {
								myHashSet.add(v.elementAt(i).getNestedNode()
										.getID());
								myVerticalNodeIdHashSet.add(v.elementAt(i)
										.getNestedNode().getID());
							}
						}
						// add horizontal visible nodes to hashset
						v = myRelation.getHorizontalVisibleNode().getChildren();
						if (v != null) {
							for (int i = 0; i < v.size(); i++) {
								myHashSet.add(v.elementAt(i).getNestedNode()
										.getID());
								myHorizontalNodeIdHashSet.add(v.elementAt(i)
										.getNestedNode().getID());
							}
						}

						// add the path node ids to the hashset
						// for vertical and horizontal nodes
						TreeNode[] tempNode = myRelation
								.getVerticalVisibleNode().getPath();
						for (int t = 0; t < tempNode.length; t++) {
							myHashSet.add(((VisibleNode) tempNode[t])
									.getNestedNode().getID());
							myVerticalNodeIdHashSet
									.add(((VisibleNode) tempNode[t])
											.getNestedNode().getID());
						}

						tempNode = myRelation.getHorizontalVisibleNode()
								.getPath();
						for (int t = 0; t < tempNode.length; t++) {
							myHashSet.add(((VisibleNode) tempNode[t])
									.getNestedNode().getID());
							myHorizontalNodeIdHashSet
									.add(((VisibleNode) tempNode[t])
											.getNestedNode().getID());
						}

					}

					else {
						// add the path node ids to the hashset
						// for vertical and horizontal nodes
						TreeNode tempNode[] = myRelation
								.getVerticalVisibleNode().getPath();
						for (int t = 0; t < tempNode.length; t++) {
							myHashSet.add(((VisibleNode) tempNode[t])
									.getNestedNode().getID());
							myVerticalNodeIdHashSet
									.add(((VisibleNode) tempNode[t])
											.getNestedNode().getID());
						}

						tempNode = myRelation.getHorizontalVisibleNode()
								.getPath();
						for (int t = 0; t < tempNode.length; t++) {
							myHashSet.add(((VisibleNode) tempNode[t])
									.getNestedNode().getID());
							myHorizontalNodeIdHashSet
									.add(((VisibleNode) tempNode[t])
											.getNestedNode().getID());
						}
					}
				} // end if
			} // end for
		} // end for

		if (myHashSet.size() == 0)
			return null;

		return myHashSet;
	}

	/**
	 * Paints this <code>IAORelationPanel</code>.
	 * 
	 * @param g
	 *            - a Graphics context
	 * @see java.awt.graphics#Graphics
	 */
	@Override
   protected void paintComponent(final Graphics g) {

		super.paintComponent(g);
		
		// variables which save the relevant data of the relation where
		// the mouse cursor is situated
		List<VisibleRelationCoord> visibleRelationsCoordCache = new ArrayList<VisibleRelationCoord>();

		List<VisibleRelation> selectedVisibleRelationsCache = new ArrayList<VisibleRelation>();
		List<VisibleRelationCoord> selectedVisibleRelationsCoordCache = new ArrayList<VisibleRelationCoord>();

		VisibleRelationCoord rolloverRelationCoord = null;
		HorizontalTreeView horizTreeView = myMatrixBrowserPanel.getHorizontalTreeView();
      JTree horizTree = horizTreeView.getTree();
      VerticalTreeView vertTreeView = myMatrixBrowserPanel.getVerticalTreeView();

      // offset of the relation pane
		int xOffs = ((JComponent) horizTreeView).getLocation().x;
		int yOffs = ((JComponent) vertTreeView).getLocation().y;

		// the normal x Offset without declanation
		myXOffsRotate = xOffs;

		
		g.setColor(Color.white);
		g.fillRect(getVirtualBounds().x, getVirtualBounds().y, getVirtualBounds().width, getVirtualBounds().height);
		// g.fillRect(getBounds().x, getBounds().y,getBounds().width,getBounds().height);
		g.setColor(Color.black);
		
		if (horizTree instanceof JHorizontalRotateTree) {
			// for the declanation, we need to know wether the Viewport height
			// of the
			// horizontal tree is too small to display its full height or not

			int nodeHeight = 0;
			TreePath firstPath = horizTree.getPathForRow(0);
			if (firstPath != null) {
			   Rectangle boundsOfFirstPath = horizTree.getPathBounds(firstPath);
			   if (boundsOfFirstPath != null)
			   {
			      nodeHeight = boundsOfFirstPath.height - horizTreeView.getScrollOffsetY();
			   }
			   else
			   {
			      // in rare cases (threading problems??) the bounds of the first path are undefined
			      log.error("unable to determine bounds of first path in horizontal tree view");
			   }
			}

			// difference between the height of the component and its actual
			// (maximum) node height
			int minimumHorizontalHeight = ((JComponent) horizTreeView).getHeight()
					- nodeHeight - getVirtualBorderWidth();

			// if the difference is below zero, we need to change the x Offset
			// according to a linear
			// function
			if (minimumHorizontalHeight < 0) {
				// -1 is the gradient of a 45 degree angle
				myXOffsRotate += -1 * minimumHorizontalHeight;
			}
			// the godgiven trial and error additional offset
			myXOffsRotate -= 2;
		}

		// iterate over all currently visible relations
		// draw all lines which are not selected, and rebuild coord cache if
		// necessary
		for (int i = 0; i < myVisibleRelationsCache.size(); i++) {
			// get the ith visible relation
			visibleRelation = myVisibleRelationsCache.get(i);

			// the corresponding coordinates
			VisibleRelationCoord coord = new VisibleRelationCoord();
			// neither rolled over nor selected
			// path in horizontal tree view
			TreePath horizPath = visibleRelation.getHorizontalPath();
			// path in vertical tree view
			TreePath verticalPath = visibleRelation.getVerticalPath();

			// get the bounds for each path
			Rectangle horizBounds = horizTreeView.getPathBounds(horizPath);
			Rectangle verticalBounds = vertTreeView.getPathBounds(verticalPath);

			if(horizBounds == null){
			   horizBounds = new Rectangle();
			}
			if(verticalBounds == null){
			   verticalBounds = new Rectangle();
			}
			
			if ((horizBounds == null) || (verticalBounds == null)) {
			   log.error("horizontal or vertical bounds are null: should never happen");
				return;
			}

			coord = new VisibleRelationCoord();

			// the x center is the xOffs plus the horizontal bounds x value plus
			// half of the row height (attention: row height in the horizontal
			// tree
			// view actually means its width)
			//
			// xOffs horizBounds.x rowHeight/2
			// |---------|---------------|---*---|
			//
			//
			//

			coord.myCenterX = myXOffsRotate
					+ horizBounds.x
					+ horizTreeView.getRowHeight() / 3;// Marwan hier durch 3 statt durch 2 einfach so

			// the same for centerY
			coord.myCenterY = yOffs
					+ verticalBounds.y
					+ vertTreeView.getRowHeight() / 2;

			coord.myMinX = verticalBounds.x + verticalBounds.width
					+ getVirtualBorderWidth();
			// the +2 is godgiven (trial and error)
			coord.myMinX = Math
					.min(coord.myMinX, ((JComponent) vertTreeView).getBounds().width);
			// the minimum y value the line must be painted to
			// is horizBounds.y plus horizBounds.height (no explanatin needed)
			// plus a godgiven constant (trial and error)
			// minus the offset the tree was translated by
			coord.myMinY = horizBounds.y + horizBounds.height
					+ getVirtualBorderWidth();

			// check if minY is inside the bounds of the horizontal view
			// (otherwise
			// clip it)
			coord.myMinY = Math.min(coord.myMinY,
					((JComponent) horizTreeView).getBounds().height);
			visibleRelationsCoordCache.add(coord);

			if ((!visibleRelation.isRollOver())
					&& (!visibleRelation.isSelected())) {
				// draw horizontal and vertical line

				if ((coord.myCenterY > yOffs) && (coord.myCenterX > xOffs)) {
					// /////////////////////////////////////////Marwan color
					// fï¿½r Relations
					Color relationColor = visibleRelation.getRelationColor();
					if (relationColor == null) {
						g.setColor(Color.black);
					} else {
						g.setColor(relationColor);
					}
					// //////////////////////////////////////////////////////////////////////

					g.drawLine(coord.myMinX, coord.myCenterY, coord.myCenterX,
							coord.myCenterY);

					g.drawLine(coord.myCenterX, coord.myMinY + 5,
							coord.myCenterX, coord.myCenterY);

				}

				// if visibleRelation is rolled over or selected, i.e.
				// the mouse cursor is situated at the relation
				// save the relation in order to paint it after the for loop
			} else {

				if (visibleRelation.isRollOver()) {
					rolloverRelationCoord = coord;

				} else {
					selectedVisibleRelationsCache.add(visibleRelation);
					selectedVisibleRelationsCoordCache.add(coord);
				}
			}
		} // end for

		// loop over all saved relations, i.e. relations that are rolled over or
		// selected
		for (int i = 0; i < selectedVisibleRelationsCache.size(); i++) {
			// VisibleRelation myRelation = (VisibleRelation)
			// selectedVisibleRelationsCache
			// .get(i); //never read
			VisibleRelationCoord myRelationCoord = selectedVisibleRelationsCoordCache
					.get(i);

			if ((myRelationCoord.myCenterY > yOffs)
					&& (myRelationCoord.myCenterX > xOffs)) { // relation is in
				// bounds

				g.drawLine(myRelationCoord.myMinX, myRelationCoord.myCenterY,
						myRelationCoord.myCenterX, myRelationCoord.myCenterY);

				g.drawLine(myRelationCoord.myCenterX, myRelationCoord.myMinY,
						myRelationCoord.myCenterX, myRelationCoord.myCenterY);
			}
		}

		// now, draw the saved rollOverRelation if there is one
		if (rolloverRelationCoord != null && myRollOverRelation != null) {

		   // Marwan: für den neuen Look von RollOver

			if (myRollOverRelation.getJComponent() == null) {
				coordinateX = rolloverRelationCoord.myCenterX;
				coordinateY = rolloverRelationCoord.myCenterY;
			} else {
				coordinateX = rolloverRelationCoord.myCenterX
						- ((myRollOverRelation.getJComponent().getWidth()) / 2)
						- 2;
				coordinateY = rolloverRelationCoord.myCenterY
						- ((myRollOverRelation.getJComponent().getHeight()) / 2)
						- 2;
			}

			g.setColor(Color.WHITE);// Damit wird der normale link gelï¿½scht.
			g.drawLine(rolloverRelationCoord.myMinX,
					rolloverRelationCoord.myCenterY, coordinateX + 2,
					rolloverRelationCoord.myCenterY);

			if (myRollOverRelation.getRelationColor() == null) {
				g.setColor(Color.black);
			} else {
				g.setColor(myRollOverRelation.getRelationColor());
			}

			g.drawLine(rolloverRelationCoord.myMinX,
					rolloverRelationCoord.myCenterY - 1, coordinateX,
					rolloverRelationCoord.myCenterY - 1);
			g.drawLine(rolloverRelationCoord.myCenterX - 1,
					rolloverRelationCoord.myMinY + 3,
					rolloverRelationCoord.myCenterX - 1, coordinateY);

			g.drawLine(rolloverRelationCoord.myMinX,
					rolloverRelationCoord.myCenterY + 1, coordinateX,
					rolloverRelationCoord.myCenterY + 1);
			g.drawLine(rolloverRelationCoord.myCenterX + 1,
					rolloverRelationCoord.myMinY + 3,
					rolloverRelationCoord.myCenterX + 1, coordinateY);

			g.drawLine(rolloverRelationCoord.myMinX,
					rolloverRelationCoord.myCenterY, coordinateX,
					rolloverRelationCoord.myCenterY);
			g.drawLine(rolloverRelationCoord.myCenterX,
					rolloverRelationCoord.myMinY + 3,
					rolloverRelationCoord.myCenterX, coordinateY);

		}
		// then draw renderers, i.e. paint the "please click to expand" things
		// little ugly that we have to iterate over all visible relations again


		// is it a collapsed relation?
		boolean everyVisibleRelationIsCollapsed = true;
		List<VisibleRelation> visibleRelations = new ArrayList<VisibleRelation>(myVisibleRelationsCache);
		for (VisibleRelation visibleRelation : visibleRelations)
		{
		   // is there at least one non-collapsed visible relation?
			if (visibleRelation.getType() != RelationClass.COLLAPSED) {
				everyVisibleRelationIsCollapsed = false;
			}
		}
		if (myVisibleRelationsCache.size() == 0) {
			// delete all JComponents and set gap between nodes
			this.removeAll();
			horizTreeView.setRowHeight(25);
			vertTreeView.setRowHeight(18);
		}
		else {
		   // there is at least one visible relation
			if (everyVisibleRelationIsCollapsed) {
				this.removeAll();
				horizTreeView.setRowHeight(25);
				vertTreeView.setRowHeight(18);
			}
			// if there is at least one non-collapsed relation
			// determine which JComponent is the largest
			for (int i = 0; i < myVisibleRelationsCache.size(); i++) {
            if (repainter) {
               this.repaint();
               repainter = false;
            }
				if(i < visibleRelationsCoordCache.size()&& i < myVisibleRelationsCache.size())
				{
		         VisibleRelation visibleRelation = myVisibleRelationsCache.get(i);
				   VisibleRelationCoord coord = visibleRelationsCoordCache.get(i);
				   JComponent aRenderer = myRelationRenderer.getRelationRendererComponent(this, coord.myCenterX, coord.myCenterY, visibleRelation);
				   aRenderer.paint(g);
				}
				else {
				   Logger.getLogger(this.getClass()).error("visibleRelationsCoordCache and myVisibleRelation are inconsistent");
				}
			}
		}

	}

	public void setRepainter() {
		repainter = true;
	}

	/**
	 * Removes a <code>VisibleRelationEventListener</code> from the event
	 * listener list.
	 * 
	 * @param aVisibleRelationEventListener
	 *            the <code>VisibleRelationEventListener</code> to be removed
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEventListener
	 * @see de.fhg.iao.matrixbrowser.VisibleRelationEvent
	 */
	@Override
   public void removeVisibleRelationEventListener(
			final VisibleRelationEventListener aVisibleRelationEventListener) {
		myEventListenerList.remove(VisibleRelationEventListener.class,
				aVisibleRelationEventListener);
	}

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
	@Override
   public void setContextMenu(final JContextMenu myPopUpMenu) {
		this.myContextMenu = myPopUpMenu;
		myPopUpMenu.setParentComponent(this);
	}

	/**
	 * Sets the drag and drop mechanisms enabled state.
	 * 
	 * @param aEnabledState
	 *            true to enable d&d
	 */
	@Override
   public void setDragEnabled(final boolean aEnabledState) {
		myDnDEnabledState = aEnabledState;
	}

	/**
	 * Sets a new <code>RelationRenderer</code> component.
	 * 
	 * @param aRenderer
	 *            the new <code>RelationRenderer</code>
	 * @see de.fhg.iao.matrixbrowser.RelationRenderer
	 */
	@Override
   public void setRelationRenderer(final RelationRenderer aRenderer) {
		myRelationRenderer = aRenderer;
	}

	/**
	 * Sets a mouse overed <code>VisibleRelation</code>.
	 * 
	 * @param aRelation
	 *            the roll-over <code>VisibleRelation</code> object.
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	@Override
   public void setRollOverRelation(final VisibleRelation aRelation) {
		setRollOverRelation(aRelation, true);
	}

	/**
	 * Sets a mouse overed <code>VisibleRelation</code>.
	 * 
	 * @param aRelation
	 *            the roll-over <code>VisibleRelation</code> object.
	 * @param removeOthers
	 *            should all other rolled over relations be kept or set to not
	 *            rolled over
	 */
	public void setRollOverRelation(final VisibleRelation aRelation,
			final boolean removeOthers) {
		// if (removeOthers == true) deRollOverVisibleRelations();
		myRollOverRelation = aRelation;
		myRollOverRelation.setRollOver(true);
		if (removeOthers == false)
			aRelation.setRollOver(true);

		// if (removeOthers == true) repaint();
	}

	/**
	 * Sets the current selected <code>VisibleRelation</code>.
	 * 
	 * @param aRelation
	 *            the roll-over <code>VisibleRelation</code> object.
	 * @see de.fhg.iao.matrixbrowser.VisibleRelation
	 */
	@Override
   public void setSelectedRelation(final VisibleRelation aRelation) {
		if (aRelation == null) {
			deSelectVisibleRelations();
			mySelectedRelationVector.clear();
		} else {
			mySelectedRelation = aRelation;
			mySelectedRelation.setSelected(true);
			mySelectedRelationVector.add(aRelation);
		}
		// repaint();
	}

	/**
	 * Sets the actual <code>Set</code> of visible <code>RelationType</code> s.
	 * 
	 * @param aVisibleRelationsSet
	 *            a <code>Set</code> of visible <code>RelationType</code> s
	 * @see de.fhg.iao.matrixbrowser.model.elements.RelationType
	 */
	@Override
   public void setVisibleRelationTypes(
			final Collection<RelationType> visibleRelations) {
		myVisibleRelationTypes = visibleRelations;
	}

	/**
	 * @return the image representing the current screenshot
	 */
	public Image capture() {
		Graphics gr;
		Image result = null;
		result = this.createImage(getBounds().width, getBounds().height);
		gr = result.getGraphics();
		paint(gr);
		gr.dispose();

		return result;
	}

	/**
	 * Recalculates the currently visible relations depending on the current
	 * state.
	 */
	@Override
   public void updateVisibleRelations() {
		if (this.getMatrixBrowserPanel().getTreeManager() == null)
			return;

		// offScreenRelationBuffer = null;
		mustRepaint = true;
		// reinit Visiblerelation cache
		myVisibleRelationsCache = new ArrayList<VisibleRelation>();

		TreeView verticalTreeView = this.getMatrixBrowserPanel()
				.getVerticalTreeView();
		TreeView horizontalTreeView = this.getMatrixBrowserPanel()
				.getHorizontalTreeView();

		/*
		 * now we go through all visible nodes in both tree views and request
		 * relations for the pairs. If there is one we create a new
		 * VisibleRelation for this Relation. If one of the pair's node is
		 * collapsed we have to request possible collapsed relations.
		 */

		for (int i = 0;; i++) {
			TreePath verticalPath = verticalTreeView.getPathForRow(i);
			if (verticalPath == null)
				break;
			if (!(verticalPath.getLastPathComponent() instanceof VisibleNode))
				break;
			VisibleNode verticalVisibleNode = (VisibleNode) verticalPath
					.getLastPathComponent();
			for (int j = 0;; j++) {
				TreePath horizontalPath = horizontalTreeView.getPathForRow(j);
				if (horizontalPath == null)
					break;
				if (!(horizontalPath.getLastPathComponent() instanceof VisibleNode))
					break;
				VisibleNode horizontalVisibleNode = (VisibleNode) horizontalPath
						.getLastPathComponent();
				/*
				 * Relation relation = tc.getRelation(verticalVisibleNode,
				 * horizontalVisibleNode);
				 */
				if (getMatrixBrowserPanel().getTreeCombinator() == null)
					return;
				Relation relation = getMatrixBrowserPanel()
						.getTreeCombinator()
						.getRelation(verticalVisibleNode, horizontalVisibleNode);
				VisibleRelation visibleRelation = null;

				if (relation != null) {
				   
				   RelationDirection direction = relation.getDirection();
					/*
					 * ok, we have a direct relation for this pair. No we have
					 * to determine if it is infered or real
					 */
					if (relation.getRelationClass() == RelationClass.REAL) {
					   /*
						 * we have a REAL relation -> add this relation to the
						 * cache
						 */
						visibleRelation = new VisibleRelation(
								RelationClass.REAL, direction,
								true, verticalPath, horizontalPath, relation
										.getRelationType().getName(), relation
										.getDescription(), relation);
					} else if (relation.getRelationClass() == RelationClass.INFERRED) { // marwan
																						// visiblerelation
																						// modifiziert
						/*
						 * we have an infered relation -> now we must determine
						 * if the relation is collapsed or expanded and later
						 * add is to the cache
						 */
						boolean expanded = (verticalTreeView
								.isExpanded(verticalPath) || verticalVisibleNode
								.isLeaf());
						expanded = expanded
								&& (horizontalTreeView
										.isExpanded(horizontalPath) || horizontalVisibleNode
										.isLeaf());
						visibleRelation = new VisibleRelation(
								RelationClass.INFERRED,
								direction, expanded,
								verticalPath, horizontalPath, relation
										.getRelationType().getName(), relation
										.getDescription(), relation);
					} else {// marwan visiblerelation modifiziert
						log.debug("But I still reached it (DefaultRelationPane, updateVisibleRelations");
					}
	         }
             // This is a relation between two elements from different
             // trees (tree spanning relation), i.e. a link.
             // we must determine if there is a collapsed relation
				int collapsedType = 0;
				// first check, if the verticalNode is collapsed
				if(verticalTreeView.isCollapsed(verticalPath)&& !verticalVisibleNode.isLeaf()){
				   collapsedType += TreeCombinator.VERTICAL_COLLAPSED;
				}
				// then check, if the horizontalNode is collapsed
				if(horizontalTreeView.isCollapsed(horizontalPath)&& !horizontalVisibleNode.isLeaf()){
				   collapsedType += TreeCombinator.HORIZONTAL_COLLAPSED;
				}
				// at least check, if a collapsed relation exists
				if (getMatrixBrowserPanel().getTreeCombinator()
				      .hasCollapsedRelation(verticalVisibleNode, horizontalVisibleNode, collapsedType)) {
				   //check if a visible relation still exists and chose the new relation corresponding to.
				   if(visibleRelation == null){
                  visibleRelation = new VisibleRelation(
                        RelationClass.INFERRED,
                        RelationDirection.UNDIRECTED, false,
                        verticalPath, horizontalPath, null, null);
				   }
				   else{
                  visibleRelation = new VisibleRelation(
                        RelationClass.COLLAPSED,
                        RelationDirection.UNDIRECTED, false,
                        verticalPath, horizontalPath, null, null);
				   }
				}
				if (visibleRelation != null) {
					// Testen, ob der Typ der Relation auch zur Menge der
					// anzuzeigenden Typen gehï¿½rt. [ak]
					// if(getVisibleRelationTypes().contains(visibleRelation.getNestedRelation().getRelationType()))
					// {
					myVisibleRelationsCache.add(visibleRelation);
					// }
				}
			}
		}
		this.repaint();
	}

	/**
	 * Holds the reqiured coordinations of a <code>VisibleRelation</code> needed
	 * for rendering.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.21 $
	 */
	protected class VisibleRelationCoord {

		/** Minimum X coord of a <code>VisibleRelation</code> */
		protected int myMinX = 0;

		/** Minimum Y coord of a <code>VisibleRelation</code> */
		protected int myMinY = 0;

		/** Center X coord of a <code>VisibleRelation</code> */
		protected int myCenterX = 0;

		/** Center Y coord of a <code>VisibleRelation</code> */
		protected int myCenterY = 0;
	}

	/**
	 * Transferhandler for relations.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.21 $
	 */
	protected class RelationPaneTransferHandler extends TransferHandler {

		/**
		 * Comment for <code>serialVersionUID</code>
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Null constructor.
		 */
		public RelationPaneTransferHandler() {
			super();
		}

		/**
		 * Checks if the given <code>DataFlavor</code> s can be imported into
		 * the given component.
		 * 
		 * @param aComponent
		 *            the componet, which should import
		 * @param aTransferFlavors
		 *            the <code>DataFlavor</code> s of the import
		 * @return if the aComponent can import one of the aDataFlavors
		 * @see javax.swing.TransferHandler#canImport(javax.swing.JComponent,
		 *      java.awt.datatransfer.DataFlavor[])
		 */
		@Override
      public boolean canImport(final JComponent aComponent,
				final DataFlavor[] aTransferFlavors) {
			return false;
		}

		/**
		 * Creates a <code>TreeTransferable</code> to be exported. It consist
		 * out of an array of NodeTransferable.
		 * 
		 * @param aComponent
		 *            the component for which to create the Transferable
		 * @return a <code>TreeTrasnferable</code> encoding th actual selection.
		 * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
		 * @see de.fhg.iao.swt.util.dnd.datatransfer.TreeTransferable
		 */
		@Override
      protected Transferable createTransferable(final JComponent aComponent) {
			DefaultRelationPane pane = (DefaultRelationPane) aComponent;
			Point lastClickPosition = pane.myGestureRecognizer.lastClick;
			VisibleRelation thatVisibleRelation = pane
					.getVisibleRelationForRow(pane
							.getHorizontalRowForX((int) lastClickPosition
									.getX()), pane
							.getVerticalRowForY((int) lastClickPosition.getY()));
			if (thatVisibleRelation != null) {
				Relation relation = thatVisibleRelation.getNestedRelation();
				if (relation != null) {
					return relation.createTransferableRelation();
				}
			}
			return null;
		}

		/**
		 * Gets the source actions of this object. In case the source object is
		 * an <code>TreeView</code> this returns a <code>COPY</code> action.
		 * Outherwise <code>NONE</code>.
		 * 
		 * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
		 */
		@Override
      public int getSourceActions(final JComponent aComponent) {
			if ((aComponent instanceof DefaultRelationPane)
					&& (myDnDEnabledState)) {
				return COPY;
			}
			return NONE;
		}

		/**
		 * Imports the data of the given <code>Transferable</code> into the
		 * given component.
		 * 
		 * @param aComponent
		 *            the component which imports the data
		 * @param aTransferable
		 *            the data to be imported
		 * @return if the operation suceeded
		 * @see javax.swing.TransferHandler#importData(javax.swing.JComponent,
		 *      java.awt.datatransfer.Transferable)
		 */
		@Override
      public boolean importData(final JComponent aComponent,
				final Transferable aTransferable) {
			return false;
		}
	}

	/**
	 * Handles <code>ModelChangedEvent</code> s
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.21 $
	 */
	protected class TreeModelChangedHandler implements TreeModelChangedListener {

		/**
		 * Recieves a <code>ModelChangedEvent</code>.
		 * 
		 * @see de.fhg.iao.matrixbrowser.IAOModelChangedListener#onModelChangedEvent(de.fhg.iao.matrixbrowser.model.ModelChangedEvent)
		 */
		@Override
      public void onTreeModelChanged(final TreeModelChangedEvent e) {

			DefaultRelationPane.this.updateVisibleRelations();
		}
	}

	/**
	 * The <code>MouseEvent</code> handler of the
	 * <code>DefaultRelationPane</code>
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.21 $
	 */
	protected class MouseEventHandler implements MouseListener,
			MouseMotionListener {

		/** Is the user currently dragging */
		private boolean myIsDragging = false;

		// private boolean wasTimer = false; //never accessed

		/** mouse coordinates where mouse was pressed for dragging */
		private int oldMouseX, oldMouseY;

		/**
		 * start offset of the horizontal and vertical scrollbar when mouse was
		 * pressed for dragging
		 */
		private int startOffsetX, startOffsetY;

		/** current mouse coordinates while dragging */
		private int newMouseX, newMouseY;

		/**
		 * current offset of the horizontal and vertical scrollbar
		 */
		private int endOffsetX, endOffsetY;

		/** the left and right offset calculated from start and end offset */
		private int leftOffsetX; // rightOffsetX; //never read

		/** the up and down offset calculated from start and end offset */
		private int upOffsetY; // , downOffsetY; //never read

		// used for rectangle
		/**
		 * start position, width and height of rectangle used for finding
		 * selected relations
		 */
		private int startX, startY, width, height;

		/** end position of rectangle used for finding selected relations */
		private int endX, endY;

		/** horizontal and vertical scrollbar used */
		private JScrollBar horizontalScrollBar = ((AbstractTreeView) getMatrixBrowserPanel()
				.getHorizontalTreeView()).getScrollBar(),
				verticalScrollBar = ((AbstractTreeView) getMatrixBrowserPanel()
						.getVerticalTreeView()).getScrollBar();

		/** unit increment of horizontal and vertical scrollbar */
		private int unitIncrementX = horizontalScrollBar.getUnitIncrement(),
				unitIncrementY = verticalScrollBar.getUnitIncrement();

		private final int BORDER_X = 10, BORDER_Y = 10;

		private final int ONE_SECOND = 1000;

		/** increment used for shifting dragging rectangle */
		private int incrementX, incrementY;

		/**
		 * Timers for automatically scrolling relations if mouse is dragged and
		 * close to the border of the relation pane component
		 */

		private final Timer mHorizontalTimer = new Timer(ONE_SECOND / 10,
				new ActionListener() {

					@Override
               public void actionPerformed(final ActionEvent e) {
						if (!(getCursor().getType() == Cursor.DEFAULT_CURSOR))
							return;

						// mouse close to right border
						if (newMouseX > getBounds().getWidth() - BORDER_X) {
							if (horizontalScrollBar.getValue() + unitIncrementX >= (horizontalScrollBar
									.getMaximum() - horizontalScrollBar
									.getVisibleAmount())) {
								horizontalScrollBar
										.setValue((horizontalScrollBar
												.getMaximum() - horizontalScrollBar
												.getVisibleAmount()));
								mHorizontalTimer.stop();
							} else {
								horizontalScrollBar
										.setValue(horizontalScrollBar
												.getValue()
												+ unitIncrementX);
								incrementX -= unitIncrementX;
							}
							// mouse close to left border
						} else if (newMouseX < getVirtualBounds().getX()
								+ BORDER_X) {
							if (horizontalScrollBar.getValue() - unitIncrementX <= 0) {
								horizontalScrollBar.setValue(0);
								mHorizontalTimer.stop();
							} else {
								horizontalScrollBar
										.setValue(horizontalScrollBar
												.getValue()
												- unitIncrementX);
								incrementX += unitIncrementX;
							}
						} else
							mHorizontalTimer.stop();
						doDragging();
					}
				}),
				mVerticalTimer = new Timer(ONE_SECOND / 10,
						new ActionListener() {

							@Override
                     public void actionPerformed(final ActionEvent e) {
								if (!(getCursor().getType() == Cursor.DEFAULT_CURSOR))
									return;

								if (newMouseY > getBounds().getHeight()
										- BORDER_Y) {
									if (verticalScrollBar.getValue()
											+ unitIncrementY >= (verticalScrollBar
											.getMaximum() - verticalScrollBar
											.getVisibleAmount())) {
										verticalScrollBar
												.setValue((verticalScrollBar
														.getMaximum() - verticalScrollBar
														.getVisibleAmount()));
										mVerticalTimer.stop();
									} else {
										incrementY += unitIncrementY;
										verticalScrollBar
												.setValue(verticalScrollBar
														.getValue()
														+ unitIncrementY);
									}
								} else if (newMouseY < getVirtualBounds()
										.getY()
										+ BORDER_Y) {
									if (verticalScrollBar.getValue()
											- unitIncrementY <= 0) {
										verticalScrollBar.setValue(0);
										mVerticalTimer.stop();
									} else {
										incrementY -= unitIncrementY;
										verticalScrollBar
												.setValue(verticalScrollBar
														.getValue()
														- unitIncrementY);
									}
								} else
									mVerticalTimer.stop();
								doDragging();
							}
						});

		/**
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */

		@Override
      public void mouseClicked(final MouseEvent e) {
			myLastMouseClickPosition = e.getPoint();
			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {

				VisibleRelation aRelation = getVisibleRelationForRow(
						getHorizontalRowForX(e.getX()), getVerticalRowForY(e
								.getY()));
				if (aRelation != null) {

					VisibleRelationEvent re = new VisibleRelationEvent(this,
							aRelation);
					fireRelationClickedEvent(re);
				}
				DefaultRelationPane.this.setRepainter();
			}

		}

		/**
		 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
		 */
		@Override
      public void mouseDragged(final MouseEvent e) {
			if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {

				myIsDragging = false;

				return;
			}

			// check if the current cursor is no resize cursor
			// otherwise the nodecloseup view panel gets resized and we don't
			// use the drag here
			if (!(getCursor().getType() == Cursor.DEFAULT_CURSOR))
				return;

			// BUGFIX: if RMB was pressed to bring up the popup menu
			// (wasContextMenuDisplayed)
			// and then a drag immediately followed in the relation pane,
			// the matrix browser panel was displayed false
			// to see the bug, comment the following if(...) out.
			if (wasContextMenuDisplayed) {
				wasContextMenuDisplayed = false;
				repaint();
				return;
			}

			myIsDragging = true;

			newMouseX = e.getX();
			newMouseY = e.getY();

			if (newMouseX > getBounds().getWidth() - BORDER_X) {
				mHorizontalTimer.start();
			} else if (newMouseX < getVirtualBounds().getX() + BORDER_X) {
				mHorizontalTimer.start();
			}

			if (newMouseY > getBounds().getHeight() - BORDER_Y)
				mVerticalTimer.start();
			else if (newMouseY < getVirtualBounds().getY() + BORDER_Y) {
				mVerticalTimer.start();
			} else {
				mVerticalTimer.stop();
			}

			doDragging();
		}

		private void doDragging() {

			// construct the corresponding rectangle
			// Rectangle r = new Rectangle(startX, startY, width, height);

			// get ids of all nodes with relations inside the rectangle
			getVisibleRelationIDsForRectangle(computeBoundary(oldMouseX,
					oldMouseY, newMouseX, newMouseY), leftOffsetX, upOffsetY);

			paintDraggingRectangle(computeDisplayBoundary());

		}

		/**
		 * computes the dragging rectangle that will be displayed
		 * 
		 * @return the dragging rectangle
		 */
		private Rectangle computeDisplayBoundary() {
			// first, construct a new Rectangle object
			Rectangle r = new Rectangle();

			// the x value is the mouse position where the mouse was pressed
			// first for dragging plus the change of the horizontal scrollbars
			r.x = oldMouseX + incrementX;
			// the y value is the mouse position where the mouse was pressed
			// first for dragging plus the change of the vertical scrollbars
			r.y = oldMouseY - incrementY;

			// int tempX = Math.min(Math.max(newMouseX,getVirtualBounds().x),
			// getWidth());
			// the width of the rectangle is the absolut value of r.x minus the
			// current x mouse position
			r.width = Math.abs(r.x - newMouseX);
			// the width of the rectangle is the absolut value of r.y minus the
			// current y mouse position
			r.height = Math.abs(r.y - newMouseY);

			// if the current mouse position is left from r.x,
			// r.x minus the new mouse position has to be subtracted from r.x
			// otherwise, the rectangle's width would be painted too much on
			// the right side of the actual position
			if (newMouseX < r.x) {
				r.x -= Math.abs(r.x - newMouseX);
			}
			// the same applies for the y values
			if (newMouseY < r.y) {
				r.y -= Math.abs(r.y - newMouseY);
			}

			// if r.x is smaller than the virtual bounds' x value, i.e
			// r.x is more on the left than the vertical treeview,
			// set r.x to this border
			if (r.x < getVirtualBounds().x) {
				r.width -= Math.abs(r.x - getVirtualBounds().x - 1);
				r.x = getVirtualBounds().x - 1;
			}

			// log.debug("before "+r.x+" "+r.width+" "+getWidth());
			// if the rectangle's starting position plus its width is greater
			// than the bounds' width of this component, clip it
			if (r.x + r.width > getBounds().width) {
				r.width = getWidth() - r.x + 1;
			}
			// log.debug("after "+r.x+" "+r.width+" "+getWidth());

			// same again for y
			if (r.y < getVirtualBounds().y) {
				r.height -= Math.abs(r.y - getVirtualBounds().y - 1);
				r.y = getVirtualBounds().y - 1;
			}
			// and one more time
			if (r.y + r.height > getBounds().height) {
				r.height = getHeight() - r.y + 1;
			}

			// finally, return the result
			return r;
		}

		private Rectangle computeBoundary(final int oldX, final int oldY, final int newX, final int newY) {

			endOffsetX = getMatrixBrowserPanel().getHorizontalTreeView()
					.getScrollOffsetX();
			endOffsetY = getMatrixBrowserPanel().getVerticalTreeView()
					.getScrollOffsetY();

			// left offset is the minimum of start and end offset (X direction)
			leftOffsetX = Math.min(startOffsetX, endOffsetX);
			// right offset is the maximum of start and end offset (X direction)
			// rightOffsetX = Math.max(startOffsetX, endOffsetX); //never read

			// up offset is the minimum of start and end offset (Y direction)
			upOffsetY = Math.min(startOffsetY, endOffsetY);
			// down offset is the maximum of start and end offset (Y direction)
			// downOffsetY = Math.max(startOffsetY, endOffsetY); //never read

			// then set the starting position to minimum of oldMouseX and
			// newMouseX
			startX = Math.min(oldX, newX);
			// do the same for the minimum of oldMouseY and newMouseY
			startY = Math.min(oldY, newY);
			// then set the starting position to minimum of oldMouseX and
			// newMouseX
			endX = Math.max(oldX, newX);
			// do the same for the minimum of oldMouseY and newMouseY
			endY = Math.max(oldY, newY);
			// compute the absolut width
			width = Math.abs(endX - startX);
			// compute the absolut height
			height = Math.abs(endY - startY);

			// add delta between starting and ending offset to width (X
			// direction)
			width += Math.abs(startOffsetX - endOffsetX);
			// add delta between starting and ending offset to height (Y
			// direction)
			height += Math.abs(startOffsetY - endOffsetY);

			return new Rectangle(startX, startY, width, height);
		}

		/**
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		@Override
      public void mouseEntered(final MouseEvent e) {
			if (myIsDragging) {
				newMouseX = e.getX();
				newMouseY = e.getY();
			}
		}

		/**
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
      public void mouseExited(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
      public void mousePressed(final MouseEvent e) {
			// the starting offset of the scrollbars
			startOffsetX = getMatrixBrowserPanel().getHorizontalTreeView()
					.getScrollOffsetX();
			startOffsetY = getMatrixBrowserPanel().getVerticalTreeView()
					.getScrollOffsetY();

			oldMouseX = e.getX();
			oldMouseY = e.getY();
			incrementX = 0;
			incrementY = 0;
		}

		/**
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
      public void mouseReleased(final MouseEvent e) {

			mVerticalTimer.stop();
			mHorizontalTimer.stop();
			if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {

				VisibleRelation aRelation = getVisibleRelationForRow(

				getHorizontalRowForX(e.getX()), getVerticalRowForY(e.getY()));
				wasContextMenuDisplayed = true;

				if (aRelation != null) {
					if (aRelation.getJComponent() == null) {
						createRelationContextMenu(aRelation).show(
								e.getComponent(), e.getX(), e.getY());
					}

				} else {
					if (getContextMenu() != null) {
						getContextMenu().show(e.getComponent(), e.getX(),
								e.getY());
					}
				}
			}

			else
				wasContextMenuDisplayed = false;

			if (myIsDragging) {
				paintDraggingRectangle(null);
				myIsDragging = false;
			} else { // myIsDragging == false
				if (getContextMenu() != null && !getContextMenu().isVisible())
					// do not use RMB to drag
					if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK)
						deSelectVisibleRelations();
			}
		}

		/**
		 * java.awt.event.MouseListener#mouseMoved(java.awt.event.MouseEvent)
		 */
		@Override
      public void mouseMoved(final MouseEvent e) {
			// Marwan Diese For schleife ist fï¿½r JComp rollover.
			for (int i = 0; i < myVisibleRelationsCache.size(); i++) {
				VisibleRelation visibleRelation = myVisibleRelationsCache
						.get(i);
				if (visibleRelation.getJComponent() != null
						&& visibleRelation.getBelegt() == false) {
					// ///belegt damit genau nur ein RollOverJComp fï¿½r jeden
					// JComp erstellt wird
					visibleRelation.getJComponent().addMouseListener(
							new RollOverJComp(visibleRelation, this));

				}

			}
			/*
			 * if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) !=
			 * InputEvent.CTRL_DOWN_MASK) { deSelectVisibleRelations(); }
			 */
			if (myRollOverRelation != null)
				myRollOverRelation.setRollOver(false);
			VisibleRelation aRelation = getVisibleRelationForRow(
					getHorizontalRowForX(e.getX()),
					getVerticalRowForY(e.getY()));
			// wheter no JComp exists or the relation is collapsed
			if (aRelation != null && aRelation.getJComponent() == null) { 
				if ((e.getX() >= getMatrixBrowserPanel()
						.getHorizontalTreeView().getPathBounds(
								aRelation.getHorizontalPath()).x
						+ myXOffsRotate
						+ getMatrixBrowserPanel().getHorizontalTreeView()
								.getRowHeight() / 2 - 8)
						&& (e.getX() <= getMatrixBrowserPanel()
								.getHorizontalTreeView().getPathBounds(
										aRelation.getHorizontalPath()).x
								+ myXOffsRotate
								+ getMatrixBrowserPanel()
										.getHorizontalTreeView().getRowHeight()
								/ 2 + 8)) {

					/*
					 * if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) !=
					 * InputEvent.CTRL_DOWN_MASK) { aRelation.setSelected(true);
					 * }
					 */
					aRelation.setRollOver(true);
					myRollOverRelation = aRelation;

					VisibleRelationEvent re = new VisibleRelationEvent(this,
							aRelation);
					fireRelationOverEvent(re);

				}
			} else if (getMatrixBrowserPanel().getEditable()) {

				// set the selected paths in the views
				getMatrixBrowserPanel()
						.getHorizontalTreeView()
						.setSelectionPath(
								getMatrixBrowserPanel().getHorizontalTreeView()
										.getPathForRow(
												getHorizontalRowForX(e.getX())));
				getMatrixBrowserPanel().getVerticalTreeView().setSelectionPath(
						getMatrixBrowserPanel().getVerticalTreeView()
								.getPathForRow(getVerticalRowForY(e.getY())));
			}

			DefaultRelationPane.this.repaint();

		}

	}

	class RollOverJComp extends MouseAdapter {
		VisibleRelation visibleRelation;
		MouseEventHandler meh;

		public RollOverJComp(final VisibleRelation visibleRelation,
				final MouseEventHandler meh) {
			this.visibleRelation = visibleRelation;
			this.meh = meh;
			visibleRelation.setBelegt();
		}

		@Override
      public void mouseEntered(final MouseEvent event) {
			visibleRelation.setRollOver(true);
			myRollOverRelation = visibleRelation;
			VisibleRelationEvent re = new VisibleRelationEvent(meh,
					visibleRelation);
			fireRelationOverEvent(re);

			DefaultRelationPane.this.repaint();
		}
	}

	/**
	 * A gesture recognizer looking for drag&drop gestures
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.21 $
	 */
	protected class RelationPaneDragGestureRecognizer implements MouseListener,
			MouseMotionListener {

		protected Point lastClick = null;

		private MouseEvent sourceAction = null;

		private int threshold = 6;

		/** Is the user currently dragging */
		// private boolean myIsDragging = false; //never read
		/**
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
      public void mouseClicked(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
		 */
		@Override
      public void mouseDragged(final MouseEvent e) {
			// check whether it is a drag operation or not
			if (this.sourceAction != null) {
				Point p1 = this.sourceAction.getPoint();
				Point p2 = e.getPoint();
				// int distance = (int) p1.distance(p2); //never read
				if (p1.distance(p2) >= this.threshold) {
					this.sourceAction = null;
					// it is a drag operation
					JComponent c = (JComponent) e.getSource();
					TransferHandler handler = c.getTransferHandler();
					handler.exportAsDrag(c, e, TransferHandler.COPY);
				}
			}
		}

		/**
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		@Override
      public void mouseEntered(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
      public void mouseExited(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
		 */
		@Override
      public void mouseMoved(final MouseEvent e) {
		}

		/**
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
      public void mousePressed(final MouseEvent e) {
			this.sourceAction = e;
			this.lastClick = e.getPoint();
		}

		/**
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
      public void mouseReleased(final MouseEvent e) {
		}
	}

	/**
	 * <code>Action</code> handling the creation of new relations.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.21 $
	 */
	protected class AddRelationHandler implements ActionListener {

		@Override
      public void actionPerformed(final ActionEvent e) {
		}
	}

	protected class NodesDragSelectedAction extends AbstractAction {

		/**
		 * Comment for <code>serialVersionUID</code>
		 */
		private static final long serialVersionUID = 1L;
		private static final String TEXT = "Show selected relations";

		public NodesDragSelectedAction() {

			super(TEXT);

		}

		@Override
      public void actionPerformed(final ActionEvent e) {

			Vector<VisibleRelation> selectedVector = getSelectedRelationVector();
			// check if there are any selected relations
			if (selectedVector.size() > 0) {

				VisibleRelation myArray[] = new VisibleRelation[selectedVector
						.size()];

				getSelectedRelationVector().toArray(myArray);

				VisibleRelationEvent event = new VisibleRelationEvent(this,
						myArray);
				fireRelationSelectedEvent(event);

				// set selected relations
				((TreeProxyManager) getMatrixBrowserPanel()
						.getVerticalTreeView().getTreeSynthesizer())
						.setHashSet(myVerticalNodeIdHashSet);
				((TreeProxyManager) getMatrixBrowserPanel()
						.getHorizontalTreeView().getTreeSynthesizer())
						.setHashSet(myHorizontalNodeIdHashSet);
			}

		}

	}

	/**
	 * <code>Action</code> handling the deletion of new relations.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.21 $
	 */
	protected class DeleteRelationAction extends AbstractAction {

		/**
		 * Comment for <code>serialVersionUID</code>
		 */
		private static final long serialVersionUID = 1L;

		public DeleteRelationAction() {
			super("Delete Relation");
		}

		@Override
      public void actionPerformed(final ActionEvent e) {
		}
	}

	/**
	 * <code>Action</code> handling the editing of relations.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.21 $
	 */
	protected class EditRelationAction extends AbstractAction {

		/**
		 * Comment for <code>serialVersionUID</code>
		 */
		private static final long serialVersionUID = 1L;

		public EditRelationAction() {
			super("Edit Relation");
		}

		@Override
      public void actionPerformed(final ActionEvent e) { 
		}
	}

	/**
	 * <code>Action</code> handling the creation of new relation types.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.21 $
	 */
	protected class NewRelationTypeAction extends AbstractAction {

		/**
		 * Comment for <code>serialVersionUID</code>
		 */
		private static final long serialVersionUID = 1L;

		public NewRelationTypeAction() {
			super("New Relation");
		}

		@Override
      public void actionPerformed(final ActionEvent e) { 
		}
	}

	/**
	 * A dialog for relation params.
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.21 $
	 */
	protected class NewRelationDialog extends JDialog {

		/**
		 * Comment for <code>serialVersionUID</code>
		 */
		private static final long serialVersionUID = 1L;

		JPanel buttonPanel = new JPanel();

		JButton cancelButton = new JButton();

		GridLayout gridLayout1 = new GridLayout();

		JButton okButton = new JButton();

		JLabel relationName = new JLabel();

		JTextField relationNameField = new JTextField();

		JPanel textPanel = new JPanel();

		public NewRelationDialog() {
			try {
				jbInit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/** Dialog schlieï¿½en */
		void cancel() {
			dispose();
		}

		void cancelButton_actionPerformed(final ActionEvent e) {
			cancel();
		}

		protected String getText() {
			return relationNameField.getText();
		}

		protected void jbInit() throws Exception {
			relationName.setText("Please input new relation type :");
			textPanel.setLayout(gridLayout1);
			gridLayout1.setRows(2);
			okButton.setMnemonic('O');
			okButton.setText("OK");
			okButton.addActionListener(new java.awt.event.ActionListener() {

				@Override
            public void actionPerformed(final ActionEvent e) {
					okButton_actionPerformed(e);
				}
			});
			cancelButton.setMnemonic('C');
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {

				@Override
            public void actionPerformed(final ActionEvent e) {
					cancelButton_actionPerformed(e);
				}
			});
			this.getContentPane().add(buttonPanel, BorderLayout.CENTER);
			buttonPanel.add(okButton, null);
			buttonPanel.add(cancelButton, null);
			this.getContentPane().add(textPanel, BorderLayout.NORTH);
			textPanel.add(relationName, null);
			textPanel.add(relationNameField, null);
			setResizable(false);
			setSize(200, 110);
			setTitle("Edit Relation");
		}

		void okButton_actionPerformed(final ActionEvent e) {
			setVisible(false);
		}

		/* (non-Javadoc)
		 * @see javax.swing.JDialog#processWindowEvent(java.awt.event.WindowEvent)
		 */
		@Override
      protected void processWindowEvent(final WindowEvent e) {
			if (e.getID() == WindowEvent.WINDOW_CLOSING) {
				cancel();
			}
			super.processWindowEvent(e);
		}
	}

	/**
	 * A dialog for editing relation properties
	 * 
	 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz
	 *         </a>
	 * @version $Revision: 1.21 $
	 */
	protected class RelationEditDialog extends JDialog {

		/**
		 * Comment for <code>serialVersionUID</code>
		 */
		private static final long serialVersionUID = 1L;

		JPanel buttonPanel = new JPanel();

		JButton cancelButton = new JButton();

		JComboBox<String> directionBox = new JComboBox<>();

		String[] directionStrings = { "Left", "Right", "Bi directional",
				"Not directed" };

		GridLayout gridLayout1 = new GridLayout();

		JButton okButton = new JButton();

		JLabel relationDirectionLabel = new JLabel();

		JLabel relationTypeLabel = new JLabel();

		JPanel textPanel = new JPanel();

		JComboBox<String> typeBox = new JComboBox<>();

		String[] typeStrings = { "Real", "Infered" };

		public RelationEditDialog() {
			super();
			try {
				jbInit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		void cancel() {
			dispose();
		}

		void cancelButton_actionPerformed(final ActionEvent e) {
			cancel();
		}

		public int getDirection() {
			return -directionBox.getSelectedIndex() - 1;
		}

		@Override
      public Type getType() {
			//return -typeBox.getSelectedIndex() - 1;
		   return Type.NORMAL;
		}

		protected void jbInit() throws Exception {
			directionBox.setModel(new DefaultComboBoxModel<String>(directionStrings));
			typeBox.setModel(new DefaultComboBoxModel<String>(typeStrings));
			relationDirectionLabel.setText("Select relation direction :");
			textPanel.setLayout(gridLayout1);
			okButton.setMnemonic('O');
			okButton.setText("OK");
			okButton.addActionListener(new java.awt.event.ActionListener() {

				@Override
            public void actionPerformed(final ActionEvent e) {
					okButton_actionPerformed(e);
				}
			});
			cancelButton.setMnemonic('C');
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {

				@Override
            public void actionPerformed(final ActionEvent e) {
					cancelButton_actionPerformed(e);
				}
			});
			gridLayout1.setRows(2);
			gridLayout1.setColumns(2);
			relationTypeLabel.setToolTipText("");
			relationTypeLabel.setText("Select relation type :");
			this.getContentPane().add(buttonPanel, BorderLayout.CENTER);
			buttonPanel.add(okButton, null);
			buttonPanel.add(cancelButton, null);
			this.getContentPane().add(textPanel, BorderLayout.NORTH);
			textPanel.add(relationDirectionLabel, null);
			textPanel.add(directionBox, null);
			textPanel.add(relationTypeLabel, null);
			textPanel.add(typeBox, null);
			setResizable(false);
			setSize(300, 110);
			setTitle("Edit relation");
		}

		void okButton_actionPerformed(final ActionEvent e) {
			setVisible(false);
		}

		@Override
      protected void processWindowEvent(final WindowEvent e) {
			if (e.getID() == WindowEvent.WINDOW_CLOSING) {
				cancel();
			}
			super.processWindowEvent(e);
		}
	}

	/**
	 * Does not do anything
	 * 
	 * @author unknown
	 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
	 * @version $Revision: 1.21 $
	 */
	protected class VisibleRelationEventHandler implements
			VisibleRelationEventListener {

		@Override
      public void onRelationOver(final VisibleRelationEvent aEvent) {
		}

		@Override
      public void onRelationClicked(final VisibleRelationEvent aEvent) {
			log
					.debug("defaultRelationPane.VisibleRelationEventHandler says a Relation was clicked.");
		}

		@Override
      public void onRelationEdit(final VisibleRelationEvent aEvent) {
			log
					.debug("DefaultRelationPane.VisibleRelationEventHandler says a Relation is edited");
			RelationEditDialog relationEditDialog = new RelationEditDialog();
			relationEditDialog.setVisible(true);
		}

		@Override
      public void onRelationSelected(final VisibleRelationEvent aEvent) {
			log
					.debug("DefaultRelationPane.VisibleRelationEventHandler says a Relation is selected.");
		}

	}

	/**
	 * get the memento for this component and returns it
	 * 
	 * @return the DefaultRelationPaneMemento to be returned
	 */
	@SuppressWarnings("unchecked")
	public DefaultRelationPaneMemento getMemento() {
		return new DefaultRelationPaneMemento(capture(),
				(Vector<VisibleRelation>) getSelectedRelationVector().clone(),
				(HashSet<ID>) myHorizontalNodeIdHashSet.clone(),
				(HashSet<ID>) myVerticalNodeIdHashSet.clone());
	}

	/**
	 * sets a new state of this component via the DefaultRelationPaneMemento
	 * 
	 * @param memento
	 *            the DefaultRelationPaneMemento used for the new state
	 */
	@SuppressWarnings("unchecked")
	public void setMemento(final DefaultRelationPaneMemento memento) {
		this.mySelectedRelationVector = (Vector<VisibleRelation>) memento.visibleRelationsVector
				.clone();
		this.myHorizontalNodeIdHashSet = (HashSet<ID>) memento.horizontalHashSet
				.clone();
		this.myVerticalNodeIdHashSet = (HashSet<ID>) memento.verticalHashSet
				.clone();

		((TreeProxyManager) getMatrixBrowserPanel().getVerticalTreeView()
				.getTreeSynthesizer()).setHashSet(myVerticalNodeIdHashSet);
		((TreeProxyManager) getMatrixBrowserPanel().getHorizontalTreeView()
				.getTreeSynthesizer()).setHashSet(myHorizontalNodeIdHashSet);
	}

	/**
	 * @author schaal The memento class used for this component it saves the
	 *         current state of this component, i.e. the relations of this
	 *         component and a screenshot
	 */
	public class DefaultRelationPaneMemento {

		protected Image screenShot;

		protected Vector<VisibleRelation> visibleRelationsVector;

		protected HashSet<ID> verticalHashSet, horizontalHashSet;

		public DefaultRelationPaneMemento(final Image aScreenShot,
				final Vector<VisibleRelation> visibleRelationsVector,
				final HashSet<ID> horizontalHashSet, final HashSet<ID> verticalHashSet) {
			this.screenShot = createImage(aScreenShot.getWidth(null),
					aScreenShot.getHeight(null));
			screenShot.getGraphics().drawImage(aScreenShot, 0, 0,
					aScreenShot.getWidth(null), aScreenShot.getHeight(null),
					null);

			this.visibleRelationsVector = visibleRelationsVector;
			this.verticalHashSet = verticalHashSet;
			this.horizontalHashSet = horizontalHashSet;
		}

		public Image getScreenShot() {
			return screenShot;
		}
	}
}
