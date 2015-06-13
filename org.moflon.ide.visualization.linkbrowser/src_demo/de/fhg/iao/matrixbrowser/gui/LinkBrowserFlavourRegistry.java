/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. 
 * The original developer of the MatrixBrowser and also the FhG IAO will 
 * have no liability for use of this software or modifications or derivatives 
 * thereof. It's Open Source for noncommercial applications. Please read 
 * carefully the file IAO_License.txt and/or contact the authors. 
 * 
 * File : 
 *     $Id: LinkBrowserFlavourRegistry.java,v 1.1 2010-03-17 12:54:51 smueller Exp $
 * 
 * Created on Jul 28, 2008
 */
package de.fhg.iao.matrixbrowser.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * A Singleton class holding default GUI-Options for the LinkBrowser. The
 * LinkBrowser can be opened with different properties for different
 * Applications.Properties that may change depending on the chosen flavour are
 * the Menu, shown Widgets and so on. Whenever a different flavour is chosen,
 * the Registry will issue a <code>FlavourChangedEvent</code>.
 * 
 * @author <a href=mailto:schotti@schotti.net>Christian Schott</a>
 * @version $Revision: 1.1 $
 */
public final class LinkBrowserFlavourRegistry {

	/**
	 * The Singleton <code>instance</code>.
	 */
	private static LinkBrowserFlavourRegistry instance = new LinkBrowserFlavourRegistry();

	/**
	 * Default Constructor, only to be called once by the RuntimeEnvironment.
	 */
	private LinkBrowserFlavourRegistry() {
	}

	/**
	 * Gets the instance of the LinkBrowserFlavourRegistry. The static method to
	 * access the Singleton class.
	 * 
	 * @return the single Instance of the
	 *         <code>LinkBrowserFlavourRegistry</code>
	 */
	public static LinkBrowserFlavourRegistry getInstance() {
		return instance;
	}

	/**
	 * The <code>flavour</code> of the LinkBrowser. Defaults to VANILLA
	 * 
	 * @see LinkBrowserFlavours
	 */
	private LinkBrowserFlavour flavour = LinkBrowserFlavour.VANILLA;
	/**
	 * Holds the current <code>mainMenu</code>.
	 */
	private FlavouredMenuBar mainMenu = null;

	/**
	 * Set the <code>LinkBrowserFlavourRegistry</code> to the specified flavour.
	 * This causes a <code>FlavourChangedEvent</code> to be fired. All
	 * components that can be flavoured will update to the new flavour.
	 * 
	 * @param flavour
	 *            the flavour to set the LinkBrowser to.
	 * @see LinkBrowserFlavours
	 */
	public void setFlavour(final LinkBrowserFlavour flavour) {
		this.flavour = flavour;
	}

	/**
	 * Gets a specific Main Menu Bar defined for the flavour defined in this
	 * Registry. The MainMenuBar may be configured using the different
	 * Linkbrowser flavours. Depending on the flavour chosen, it offers more or
	 * less menu entries paired with specific actions or not. The
	 * <code>LinkBrowserFlavourRegistry</code> keeps the currently selected menu
	 * bar in a Variable for faster access. Only if the flavour has changed, the
	 * menu bar is rebuilt from scratch using the definitions for the
	 * specifically flavoured JMenuBar.
	 * 
	 * @return a JMenuBar containing the JMenus for the currently selected
	 *         flavour
	 */
	public JMenuBar getMainMenu() {
		if (mainMenu == null || mainMenu.getFlavour() != this.flavour) {
			setMainMenuFlavour(this.flavour);
			return (JMenuBar) mainMenu;
		} else {
			return (JMenuBar) mainMenu;
		}
	}

	/**
	 * Set the MainMenu to a flavour.
	 * 
	 * @param flavour
	 *            the flavour to set the Main Menu to
	 */
	private void setMainMenuFlavour(final LinkBrowserFlavour flavour) {
		switch (flavour) {
		case VANILLA:
			mainMenu = vanillaMainMenu();
			break;
		case QUAP2P:
			mainMenu = quaP2PMainMenu();
			break;
		case TIE:
			mainMenu = tIEMainMenu();
			break;
		default:
			mainMenu = vanillaMainMenu();
			break;
		}
	}

	/**
	 * Creates a default (vanilla-flavoured) MainMenuBar.
	 * 
	 * @return a vanilla-flavoured MainMenuBar.
	 */
	public FlavouredMenuBar vanillaMainMenu() {
		FlavouredMenuBar result = new FlavouredMenuBar();
		result.setFlavour(LinkBrowserFlavour.VANILLA);
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu historyMenu = new JMenu("History");
		JMenu helpMenu = new JMenu("Help");

		JMenuItem fileOpenItem = new JMenuItem("Open...");
		JMenuItem fileSaveItem = new JMenuItem("Save");
		JMenuItem filePrintItem = new JMenuItem("Print");
		JMenuItem fileExitItem = new JMenuItem("Exit");

		JMenuItem editModeOnItem = new JMenuItem("Edit On");
		JMenuItem editModeOffItem = new JMenuItem("Edit Off");

		JMenuItem helpItem = new JMenuItem("Help");
		JMenuItem helpInfosItem = new JMenuItem("Infos");

		fileMenu.add(fileOpenItem);
		fileMenu.add(fileSaveItem);
		fileMenu.add(filePrintItem);
		fileMenu.add(fileExitItem);

		editMenu.add(editModeOnItem);
		editMenu.add(editModeOffItem);

		helpMenu.add(helpItem);
		helpMenu.add(helpInfosItem);

		result.add(fileMenu);
		result.add(editMenu);
		result.add(historyMenu);
		result.add(helpMenu);
		return result;
	}

	/**
	 * Creates a QuaP2P flavoured MenuBar.
	 * 
	 * @return a MenuBar in QuaP2P flavour.
	 */
	private FlavouredMenuBar quaP2PMainMenu() {
		FlavouredMenuBar result = vanillaMainMenu();
		result.setFlavour(LinkBrowserFlavour.QUAP2P);
		JMenuItem editAddNodeItem = new JMenuItem("AddNode");
		JMenuItem editDeleteNodeItem = new JMenuItem("Delete Node");
		JMenuItem editAddRelationItem = new JMenuItem("Add Relation");
		JMenuItem editDeleteRelationItem = new JMenuItem("Delete Relation");

		result.add(editAddNodeItem);
		result.add(editDeleteNodeItem);
		result.add(editAddRelationItem);
		result.add(editDeleteRelationItem);

		return result;
	}

	/**
	 * Creates a TIE flavoured MainMenu bar.
	 * 
	 * @return a TIE-flavoured MainMenuBar
	 */
	private FlavouredMenuBar tIEMainMenu() {
		FlavouredMenuBar result = vanillaMainMenu();
		result.setFlavour(LinkBrowserFlavour.TIE);
		return result;
	}
}
