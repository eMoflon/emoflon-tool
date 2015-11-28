/**
 * 
 * 
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 *     $Id: UniqueMBModelElement.java,v 1.3 2009-03-11 12:31:38 srose Exp $
 * 
 * Created on Jul 28, 2008
 * 
 * @author schotti
 */
package de.fhg.iao.matrixbrowser.model.elements;

import java.awt.Color;
import java.util.Collection;

import javax.swing.JComponent;

import de.fhg.iao.matrixbrowser.model.ICommand;
import de.fhg.iao.swt.util.uniqueidentifier.ID;

/**
 * The UniqueMBModelElement is the superclass of all Elements to be shown in the
 * LinkBrowser. It inherits from the UniqueModelElement, which only defines an
 * ID for each of the shown ModelElements. This class adds the common fields
 * like description, popupMenu,... All Elements that should be shown in the
 * Browser should inherit from this class.
 * 
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.3 $
 */
public class UniqueMBModelElement extends UniqueModelElement implements
		UniqueMBModelElementInterface {
	/**
	 * A textual description of this element that will usually be shown in the
	 * tooltip.
	 */
	private String description = null;

	/**
	 * holds the name for the element. For example are node names shown in the
	 * view as the text inside the graphical element for the node.
	 */
	private String name = null;
	/**
	 * Holds the context menu for the element, to be shown in the views. The
	 * <code>contextMenu</code> makes some functions available handling this
	 * node. They are shown for the corresponding
	 * {@link de.fhg.iao.matrixbrowser.EncapsulatingVisibleNode VisibleNode} as
	 * soon as this node is transformed to such a <code>VisibleNode</code> The
	 * reason why we define the contextMenu in here is that we want it to be
	 * saved during model export.
	 */
	private Collection<ICommand> contextMenu = null;
	/**
	 * The color, in which this element will be drawn.
	 */
	private Color color = null;
	/**
	 * A JComponent which will be drawn at the place of the element.
	 */
	private JComponent jComponent = null;

	/**
	 * null constructor calling the superclass's constructor.
	 */
	public UniqueMBModelElement() {
		super(); // assigns an ID to the element
	}

	/**
	 * constructor with an ID given for the new UniqueMBModelElement.
	 * 
	 * @param id
	 *            the ID to construct the Element with.
	 */
	public UniqueMBModelElement(final ID id) {
		super(id);
	}

	/**
	 * Constructs a <code>UniqueMBModelElement</code> with the given
	 * description.
	 * 
	 * @param description
	 *            the description of this Element
	 */
	public UniqueMBModelElement(final String description) {
		super(); // assigns an ID
		this.description = description;
	}

	/**
	 * Constructs a <code>UniqueMBModelElement</code> with the given name,
	 * description and ID.
	 * 
	 * @param name
	 *            the Element's name
	 * @param description
	 *            the Element's description
	 * @param id
	 *            the Element's ID
	 */
	public UniqueMBModelElement(final String name, final String description,
			final ID id) {
		super(id);
		this.description = description;
	}

	/**
	 * Constructs a <code>UniqueMBModelElement</code> given the Name and the
	 * description. for the element.
	 * 
	 * @param name
	 *            the Element's Name
	 * @param description
	 *            the Element's description
	 */
	public UniqueMBModelElement(final String name, final String description) {
		super(); // assigns an ID
		this.name = name;
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueMBModelElementInterface
	 * #getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueMBModelElementInterface
	 * #setName(java.lang.String)
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueMBModelElementInterface
	 * #getDescription()
	 */
	public final String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueMBModelElementInterface
	 * #setDescription(java.lang.String)
	 */
	public final void setDescription(final String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueMBModelElementInterface
	 * #getContextMenu()
	 */
	public final Collection<ICommand> getContextMenu() {
		return contextMenu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueMBModelElementInterface
	 * #setContextMenu(java.util.Collection)
	 */
	public final void setContextMenu(final Collection<ICommand> contextMenu) {
		this.contextMenu = contextMenu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueMBModelElementInterface
	 * #getColor()
	 */
	public final Color getColor() {
		return color;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueMBModelElementInterface
	 * #setColor(java.awt.Color)
	 */
	public final void setColor(Color color) {
		this.color = color;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueMBModelElementInterface
	 * #getJComponent()
	 */
	public final JComponent getJComponent() {
		return jComponent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueMBModelElementInterface
	 * #setJComponent(javax.swing.JComponent)
	 */
	public final void setJComponent(final JComponent component) {
		jComponent = component;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fhg.iao.matrixbrowser.model.elements.UniqueMBModelElementInterface
	 * #toString()
	 */
	public String toString() {
		String result;
		if (name != null) {
			result = name;
		} else if (description != null) {
			result = description;
		} else {
			result = super.toString();
		}
		return result;
	}
}
