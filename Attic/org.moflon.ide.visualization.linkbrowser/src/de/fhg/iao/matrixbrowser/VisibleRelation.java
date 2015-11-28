/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * VisibleRelation.java,v 1.3 2004/04/07 13:52:32 roehrijn Exp $ Created on
 * 15.03.02
 */
package de.fhg.iao.matrixbrowser;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.tree.TreePath;

import de.fhg.iao.matrixbrowser.model.ICommand;
import de.fhg.iao.matrixbrowser.model.elements.Relation;
import de.fhg.iao.matrixbrowser.model.elements.RelationClass;
import de.fhg.iao.matrixbrowser.model.elements.RelationDirection;
import de.fhg.iao.swt.xswing.JContextMenu;

/**
 * Represents a relation that is currently visible in the
 * <code>RelationPane</code>.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.14 $
 * @see de.fhg.iao.matrixbrowser.RelationPane
 */
public class VisibleRelation implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/** Holds the expanded state */
	private boolean isExpanded = false;

	/** Holds the rollover state */
	private boolean isRollOver = false;

	/** Holds the selected state */
	private boolean isSelected = false;

	/** Holds the description String */
	private String myDescription = null;
	/** Holds the verical <code>TreePath</code> object */
	private TreePath myVerticalPath = null;

	/** Holds the horizontal <code>TreePath</code> object */
	private TreePath myHorizontalPath = null;

	/** Holds the <code>RelationDirection</code> */
	private RelationDirection myDirection = null;

	/** The underlying <code>Relation</code> object, which is visualized */
	private Relation myNestedRelation = null;

	/** Holds the <code>Relationtype</code> of the nested <code>Relation</code> */
	private RelationClass myType = RelationClass.REAL;

	private String myRelDescription = null;

	private String myRelTypName = null;

	private boolean belegt = false;

	/**
	 * Constructs a <code>VisibleRelation</code> object.
	 * 
	 * @param aActualType
	 *            the actual type of the relation
	 * @param aActualDirection
	 *            the actual direction of the relation
	 * @param aExpandedState
	 *            the actual expanded state
	 * @param aVerticalPath
	 *            the <code>TreePath</code> of the vertical
	 *            <code>TreeView</code>
	 * @param aHorizontalPath
	 *            the <code>TreePath</code> of the horizontal
	 *            <code>TreeView</code>
	 * @param aDescription
	 *            a textual description for this relation
	 * @param aRelation
	 *            the underlying <code>Relation</code>, which should be
	 *            visualized
	 * @see javax.swing.tree.TreePath
	 * @see de.fhg.iao.matrixbrowser.model.elements.RelationDirection
	 * @see de.fhg.iao.matrixbrowser.model.elements.RelationType
	 * @see de.fhg.iao.matrixbrowser.model.elements.Relation
	 * @see de.fhg.iao.matrixbrowser.widgets.TreeView
	 */
	public VisibleRelation(RelationClass aActualType,
			RelationDirection aActualDirection, boolean aExpandedState,
			TreePath aVerticalPath, TreePath aHorizontalPath,
			String aDescription, Relation aRelation) {
		myType = aActualType;
		myDirection = aActualDirection;
		isExpanded = aExpandedState;
		myVerticalPath = aVerticalPath;
		myHorizontalPath = aHorizontalPath;
		myDescription = aDescription;
		this.myNestedRelation = aRelation;

	}

	public VisibleRelation(RelationClass aActualType,
			RelationDirection aActualDirection, boolean aExpandedState,
			TreePath aVerticalPath, TreePath aHorizontalPath,
			String relTypName, String relDescription, Relation aRelation) {
		myType = aActualType;
		myDirection = aActualDirection;
		isExpanded = aExpandedState;
		myVerticalPath = aVerticalPath;
		myHorizontalPath = aHorizontalPath;
		myRelTypName = relTypName;
		myRelDescription = relDescription;
		this.myNestedRelation = aRelation;

	}

	public String getRelTypName() {
		return myRelTypName;
	}

	/**
	 * Gets the ContextMenu of this relation. If this VisibleRelation does not
	 * have a Nested Relation, it returns null.
	 * 
	 * @return the relation's context menu, null if no relation is defined
	 */
	public Collection<ICommand> getRelContextMenu() {
		if (myNestedRelation == null) {
			return null;
		}
		return myNestedRelation.getContextMenu();
	}

	public Color getRelationColor() {
		if (myNestedRelation == null) {
			return null;
		}
		return myNestedRelation.getColor();
	}

	public JComponent getJComponent() {
		if (myNestedRelation == null) {
			return null;
		}
		JComponent component = null;
		boolean showComponent = false;
		showComponent |= myNestedRelation.getRelationClass() != RelationClass.INFERRED;
		showComponent |= getHorizontalVisibleNode().isLeaf()
				&& getVerticalVisibleNode().isLeaf();
		showComponent |= !isExpanded;
		if (showComponent) {
			component = myNestedRelation.getJComponent();
			if (component != null) {
				component.setToolTipText(JcompToolTip());
				if (myNestedRelation.getContextMenu() != null) {
					component.addMouseListener(new CMListener(myNestedRelation
							.getContextMenu(), this));
				}

			}
		}
		return component;
	}

	public String JcompToolTip() {

		if (this.getType() != RelationClass.COLLAPSED) {
			if (myRelTypName != null) {
				if (myRelDescription != null) {
					return myRelTypName + " : " + myRelDescription;
				} else {
					return myRelTypName;
				}
			}

			else {
				if (myRelDescription != null) {
					return myRelDescription;
				} else {
					return null;
				}

			}
		}
		return null;

	}

	public void setBelegt() {
		belegt = true;
	}

	public boolean getBelegt() {
		return belegt;
	}

	/**
	 * @return a textual description of the <code>VisibleRelation</code>, which
	 *         can be shown as a tooltip
	 */
	public String getRelDescription() {
		return myRelDescription;
	}

	/**
	 * @return the direction of the <code>VisibleRelation</code>
	 * @see de.fhg.iao.matrixbrowser.model.elements.RelationDirection
	 */
	public RelationDirection getDirection() {
		return myDirection;
	}

	/**
	 * @return the horizontal <code>TreePath</code>
	 */
	public TreePath getHorizontalPath() {
		return myHorizontalPath;
	}

	/**
	 * @return the underlying <code>Relation</code>, which is visualized by this
	 *         <code>VisibleRelation</code>
	 */
	public Relation getNestedRelation() {
		return myNestedRelation;
	}

	/**
	 * @return the type of the <code>VisibleRelation</code>
	 * @see de.fhg.iao.matrixbrowser.model.elements.RelationType
	 */
	public RelationClass getType() {
		return myType;
	}

	/**
	 * @return the vertical <code>TreePath</code>
	 */
	public TreePath getVerticalPath() {
		return myVerticalPath;
	}

	/**
	 * @return the expanded state of the <code>VisibleRelation</code>
	 */
	public boolean isExpanded() {
		return isExpanded;
	}

	/**
	 * @return the rollover state of the <code>VisibleRelation</code>
	 */
	public boolean isRollOver() {
		return isRollOver;
	}

	/**
	 * @return the selection state of the <code>VisibleRelation</code>
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * Sets the underlying relation <code>Relation</code>, which should be
	 * visualized by this <code>VisibleRelation</code>
	 * 
	 * @param aRelation
	 *            the <code>Relation</code>, which should be visualized
	 */
	public void setNestedRelation(Relation aRelation) {
		myNestedRelation = aRelation;
	}

	/**
	 * Sets the rollover state, if the mouse is over this
	 * <code>VisibleRelation</code>.
	 * 
	 * @param aRolloverState
	 *            the new rollover state
	 */
	public void setRollOver(boolean aRolloverState) {

		isRollOver = aRolloverState;
	}

	/**
	 * Sets the selected state, if the mouse was clicked over this
	 * <code>VisibleRelation</code>.
	 * 
	 * @param aRolloverState
	 *            the new rollover state
	 */
	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	public VisibleNode getVerticalVisibleNode() {
		return (VisibleNode) myVerticalPath.getLastPathComponent();
	}

	public VisibleNode getHorizontalVisibleNode() {
		return (VisibleNode) myHorizontalPath.getLastPathComponent();
	}

}

class CMListener extends MouseAdapter {
	Collection<ICommand> contextMenu = null;

	VisibleRelation aRel;

	public CMListener(Collection<ICommand> contextMenu, VisibleRelation aRel) {
		this.contextMenu = contextMenu;
		this.aRel = aRel;
	}

	public void mousePressed(MouseEvent event) {
		if ((event.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {

			JContextMenu con = new JContextMenu();

			Vector<ICommand> allFunctions = (Vector<ICommand>) contextMenu;
			try {
				for (int i = 0; i < allFunctions.size(); i++) {
					JMenuItem g = new JMenuItem(((ICommand) allFunctions
							.elementAt(i)).getName());
					RelHandler handler = new RelHandler(contextMenu, i);
					g.addActionListener(handler);
					con.add(g);
				}
			} catch (Exception h) {

			}
			con.show(event.getComponent(), event.getX(), event.getY());

		}

	}

}

class RelHandler implements ActionListener {

	private int itemNumber;

	Vector<ICommand> allItems;

	public RelHandler(Collection<ICommand> context, int i) {
		this.itemNumber = i;
		allItems = (Vector<ICommand>) context;
	}

	public void actionPerformed(ActionEvent e) {

		try {
			for (int f = 0; f < allItems.size(); f++) {
				if (itemNumber == f) {
					((ICommand) allItems.elementAt(itemNumber)).execute();
				}
			}
		} catch (Exception er) {
		}
	}
}
