package de.fhg.iao.matrixbrowser.gui;

import javax.swing.JMenuBar;

/**
 * A Normal JMenuBar, with one added property, its flavour, represented by an
 * int.
 * 
 * @see LinkBrowserFlavours
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.1 $
 */
public class FlavouredMenuBar extends JMenuBar {
	/** Default <code>serialVersionUID</code> for copleteness. */
	private static final long serialVersionUID = 1L;

	/** This MenuBar's Flavour{@see LinkBrowserFlavours}. */
	private LinkBrowserFlavour flavour;

	/** Constructs a flavouredMenuBar. */
	FlavouredMenuBar() {
		super();
	}

	/**
	 * Gets the flavour of this MenuBar.
	 * 
	 * @return the MenuBar's flavour
	 * @see LinkBrowserFlavour
	 */
	public final LinkBrowserFlavour getFlavour() {
		return this.flavour;
	}

	/**
	 * Sets the flavour of this MenuBar to the given flavour.
	 * 
	 * @param flavour
	 *            The flavor to set this MenuBar to.
	 * @see LinkBrowserFlavour
	 */
	public final void setFlavour(final LinkBrowserFlavour flavour) {
		this.flavour = flavour;
	}
}
