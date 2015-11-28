/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 *     $Id: UniqueMBModelElementInterface.java,v 1.2 2009-03-11 12:31:38 srose Exp $
 * 
 * Created on Aug 8, 2008
 */
package de.fhg.iao.matrixbrowser.model.elements;

import java.awt.Color;
import java.util.Collection;

import javax.swing.JComponent;

import de.fhg.iao.matrixbrowser.model.ICommand;

public interface UniqueMBModelElementInterface extends
		UniqueModelElementInterface {

	/**
	 * Gets the name of this element. This should be a human readable textual
	 * representation of this element, as it will be shown in the views.
	 * 
	 * @return this element's name
	 */
	public abstract String getName();

	/**
	 * Sets the name of this element. This should be a human readable textual
	 * representation of this element, as it will be shown in the views.
	 * 
	 * @param name
	 *            this element's name
	 */
	public abstract void setName(final String name);

	/**
	 * Gets the description of this <code>UniqueMBModelElement</code> All
	 * UniqueMBModelElements have a Description, which should be a textual
	 * representation of the ModelElement, eg. a Node's or a Relation's
	 * description, to be shown in its tooltip.
	 * 
	 * @return the element's description
	 */
	public abstract String getDescription();

	/**
	 * Sets the element's description to the given String. All
	 * UniqueMBModelElements have a Description, which should be a textual
	 * representation of the ModelElement, eg. a Node's or a Relation's
	 * description, to be shown in its tooltip.
	 * 
	 * @param description
	 *            the element's description to be set.
	 */
	public abstract void setDescription(final String description);

	/**
	 * Gets the contextMenu of this element. Relations and nodes (for example)
	 * can have a context menu offering functions to edit the specified element.
	 * 
	 * @return the element's contextMenu, if defined.
	 */
	public abstract Collection<ICommand> getContextMenu();

	/**
	 * Sets the contextMenu for this Element.
	 * 
	 * @param contextMenu
	 *            the element's new contextMenu to set
	 */
	public abstract void setContextMenu(final Collection<ICommand> contextMenu);

	/**
	 * Gets the Color of this element. Elements get displayed in the specified
	 * color.
	 * 
	 * @return the element's color
	 */
	public abstract Color getColor();

	/**
	 * Sets the color of this element. This element will be displayed in the
	 * specified color.
	 * 
	 * @param color
	 *            the element's color to set.
	 */
	public abstract void setColor(final Color color);

	/**
	 * Gets the JComponent of this element. Elements can be displayed as
	 * JComponents. This function returns the JComponent specified for this
	 * Element.
	 * 
	 * @return the JComponent of this element
	 */
	public abstract JComponent getJComponent();

	/**
	 * Sets the JComponent of this element. Elements can be displayed as
	 * JComponents. This function returns the JComponent specified for this
	 * Element.
	 * 
	 * @param component
	 *            the JComponent to set for this element.
	 */
	public abstract void setJComponent(final JComponent component);

	/**
	 * Gets a textual representation of this Element. This will usually be the
	 * name of the element, if it is not set, the description. It falls back to
	 * the Object.toString() method if neither is defined.
	 * 
	 * @return the textual description of this Element.
	 * @see java.lang.Object#toString()
	 */
	public abstract String toString();

}