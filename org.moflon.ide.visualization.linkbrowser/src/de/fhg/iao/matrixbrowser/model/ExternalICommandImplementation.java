/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 *     $Id: ExternalICommandImplementation.java,v 1.3 2009-03-11 12:31:38 srose Exp $
 * 
 * Created on Aug 23, 2008
 */
package de.fhg.iao.matrixbrowser.model;

import java.awt.event.ActionEvent;

import de.fhg.iao.matrixbrowser.VisibleNode;

/**
 * An Implementation of the ICommand Interface for externally constructed
 * MatrixBrowsers. This implementation reacts on Events happening on this
 * ICommand by issuing an Event including the name of the menu Entry, leaving
 * the final reaction to the Event up to external choices.
 * 
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.3 $
 */
public abstract class ExternalICommandImplementation implements ICommand {
	/**
	 * The name of this <code>ICommand</code>.
	 * */
	private String name;

	/**
	 * Constructor for an <code>ExternalICommandImplementation</code>.
	 * 
	 * @param menuEntry
	 *            the Name of the Menu Entry
	 */
	public ExternalICommandImplementation(final String menuEntry) {
		this.name = menuEntry;
	}

	/**
	 * {@inheritDoc} This implementation issues a MenuEntrySelectedEvent with
	 * the menu entry included in the Event for further treatment by the
	 * external caller.
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.ICommand#execute()
	 */
	public abstract void execute(ActionEvent e, VisibleNode visibleNode);

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.fhg.iao.matrixbrowser.model.ICommand#getName()
	 */
	public final String getName() {
		return this.name;
	}

	public void execute() {
	   // Can be implemented to react to changes without further information
	}

}
