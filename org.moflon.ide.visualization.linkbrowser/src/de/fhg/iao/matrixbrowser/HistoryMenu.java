/*
 * Created on 16.08.2004
 */
package de.fhg.iao.matrixbrowser;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.fhg.iao.matrixbrowser.widgets.AbstractTreeView;
import de.fhg.iao.matrixbrowser.widgets.DefaultRelationPane;

/**
 * @author schaal A History Menu for the Menu bar
 */
public class HistoryMenu extends JMenu implements ActionListener {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/** maximum number of entries in the different submenus */
	private final int MAXIMUM_MENU_ENTRIES = 5;

	/** maximum number of submenus */
	private final int MAXIMUM_MENUS = 3;

	/** text of the submenus (completed with a number) */
	private final String MENU_TEXT = "Tree Transfer";

	/** text of the entries in the submenus (completed with a number) */
	private final String ITEM_TEXT = "Entry";

	/** reference to the MatrixBrowserPanel */
	private MatrixBrowserPanel myMatrixBrowserPanel;

	/** this history menu */
	// never read
	// private HistoryMenu myHistoryMenu;
	/** the current menu to which entries are added */
	private JMenu currentMenu;

	/** counter to check if maximum of menus is reached */
	private int menuCounter = 0;

	/** counter to check if maximum of entries in a menu is reached */
	private int entryCounter = 0;

	/** flag to indicate if maximum of menus is reached */
	private boolean menuLimitReached = false;

	/**
	 * array which size is equal to the number of submenus and holds the
	 * information if or if not the maximum entry number in a submenu is reached
	 */
	private boolean entryLimitReached[];

	/**
	 * constructor
	 * 
	 * @param name
	 *            the name of this Menu
	 * @param aPanel
	 *            the parent MatrixBrowserPanel
	 */
	public HistoryMenu(String name, MatrixBrowserPanel aPanel) {
		super(name);
		addActionListener(this);

		entryCounter = 0;
		menuCounter = 0;
		myMatrixBrowserPanel = aPanel;
		// schotti: never read
		// myHistoryMenu = this
		entryLimitReached = new boolean[MAXIMUM_MENU_ENTRIES];
	}

	/**
	 * resets the whole menu
	 */
	public void resetAll() {
		entryCounter = 0;
		menuCounter = 0;
		removeAll();
		validate();
	}

	/**
	 * resets the History Menu after a new tree was drag 'n dropped Adds a new
	 * transfer folder and saves the initial entry of this folder
	 */
	public void reset() {
		entryCounter = 0;
		// check if maximum for menus is reached
		// no
		if (!menuLimitReached) {
			// if MAXIMUM_ENTRIES is not reached, increment counter
			if (menuCounter < MAXIMUM_MENUS) {
				menuCounter++;
			} else {
				// otherwise set flag that maximum was reached
				menuLimitReached = true;
				// set the menuCounter to 1
				menuCounter = 1;
				// remove all entries of the first submenu
				((JMenu) getMenuComponent(0)).removeAll();
				// remove the first submenu
				remove(0);
				// get current menu
				currentMenu = (JMenu) getMenuComponent(0);
				validate();
			}
		}
		// maximum of menus reached
		else {
			// if MAXIMUM_ENTRIES is not reached, increment counter
			if (menuCounter < MAXIMUM_MENUS) {
				menuCounter++;
			} else
				// otherwise start from the beginning
				menuCounter = 1;
			// remove all entries of the first submenu
			((JMenu) getMenuComponent(0)).removeAll();
			// remove the first submenu
			remove(0);
			// get current menu
			currentMenu = (JMenu) getMenuComponent(0);
			validate();
		}
		// build new current menu with MENU_TEXT and the number of menuCounter
		currentMenu = new JMenu(MENU_TEXT + " " + menuCounter);
		add(currentMenu);
		entryLimitReached[menuCounter] = false;
	}

	/**
	 * adds a new HistoryEntry to History
	 * 
	 * @param horizontalMemento
	 *            horizontal TreeView Memento
	 * @param verticalMemento
	 *            vertical TreeView Memento
	 * @param relationPaneMemento
	 *            RelationPane Memento
	 */
	public void addEntry(
			MatrixBrowserPanel.MatrixBrowserPanelMemento mbpMemento,
			AbstractTreeView.AbstractTreeViewMemento horizontalMemento,
			AbstractTreeView.AbstractTreeViewMemento verticalMemento,
			DefaultRelationPane.DefaultRelationPaneMemento relationPaneMemento) {
		if (relationPaneMemento.getScreenShot() == null)
			return;

		// check if the maximum of the menu entries is reached
		// for the current JMenu folder
		if (!entryLimitReached[menuCounter]) {

			// no, add more entries
			if (entryCounter < MAXIMUM_MENU_ENTRIES) {
				entryCounter++;
			} else {
				// yes, overwrite first entry
				entryLimitReached[menuCounter] = true;
				entryCounter = 1;
				((HistoryMenuEntry) currentMenu.getMenuComponent(0))
						.removeActionListener(this);
				currentMenu.remove(0);
				validate();
			}
		} else {
			// if maximum was already reached, overwrite the entries
			// as if they were in a ring buffer
			if (entryCounter < MAXIMUM_MENU_ENTRIES) {
				entryCounter++;
			} else
				entryCounter = 1;

			((HistoryMenuEntry) currentMenu.getMenuComponent(0))
					.removeActionListener(this);
			currentMenu.remove(0);
			validate();
		}

		HistoryMenuEntry entry = new HistoryMenuEntry(ITEM_TEXT + " "
				+ entryCounter, mbpMemento, horizontalMemento, verticalMemento,
				relationPaneMemento);
		entry.setActionCommand(currentMenu.getText() + ITEM_TEXT + " "
				+ entryCounter);
		entry.addActionListener(this);
		currentMenu.add(entry);
	}

	/**
	 * sets information of history entry in the widgets they belong to
	 * 
	 * @param entry
	 *            the HistoryMenuEntry that will be set
	 */
	public void setHistoryEntry(HistoryMenuEntry entry) {
		myMatrixBrowserPanel.setMemento(entry.getMatrixBrowserPanelMemento());
		((DefaultRelationPane) myMatrixBrowserPanel.getRelationPane())
				.setMemento(entry.getRelationPaneMemento());
		((AbstractTreeView) myMatrixBrowserPanel.getVerticalTreeView())
				.setMemento(entry.getVerticalTreeViewMemento());
		((AbstractTreeView) myMatrixBrowserPanel.getHorizontalTreeView())
				.setMemento(entry.getHorizontalTreeViewMemento());
	}

	/**
	 * reacts on an ActionEvent of a HistoryMenuEntry which means that this
	 * method finds the appropriate# HistoryMenuEntry and sets its information
	 */
	public void actionPerformed(ActionEvent anActionEvent) {
		String actionCommand = anActionEvent.getActionCommand();
		HistoryMenuEntry entry = null;
		// schotti: never read
		// HistoryMenuEntry source = (HistoryMenuEntry)
		// anActionEvent.getSource();
		boolean found = false;

		// loop through all submenus
		for (int j = getMenuComponentCount() - 1; j >= 0; j--) {

			JMenu menuEntry = (JMenu) getMenuComponent(j);

			// loop through all entries of the menuEntry submenu
			for (int i = menuEntry.getMenuComponentCount() - 1; i >= 0; i--) {
				entry = (HistoryMenuEntry) (menuEntry.getMenuComponent(i));
				// did we find the actual command
				if (entry.getActionCommand().equals(actionCommand)) {
					// yes, so break (found is needed for the second for-loop)
					found = true;
					break;
				}
				entry = null;
			}
			// if entry was found, then break
			if (found)
				break;
		}
		// if entry was found, set the history
		if (found) {
			setHistoryEntry(entry);
		}

	}

	/**
	 * @author schaal HistoryMenuEntry is an entry for a JMenu that holds
	 *         additional information of different Mementos
	 */
	protected class HistoryMenuEntry extends JMenuItem {

		/**
		 * Comment for <code>serialVersionUID</code>
		 */
		private static final long serialVersionUID = 1L;

		AbstractTreeView.AbstractTreeViewMemento atvVerticalMemento,
				atvHorizontalMemento;

		DefaultRelationPane.DefaultRelationPaneMemento drpMemento;

		MatrixBrowserPanel.MatrixBrowserPanelMemento mbpMemento;

		private HistoryPopup mJPopupMenu;

		/**
		 * constructor.
		 * 
		 * @param text
		 *            name of this JMenuItem
		 * @param panelMemento
		 *            MatrixBrowserPanelMemento
		 * @param horizontalMemento
		 *            AbstractTreeViewMemento of horizontal TreeView
		 * @param verticalMemento
		 *            AbstractTreeViewMemento of vertical TreeView
		 * @param relationPaneMemento
		 *            DefaultRelationPaneMemento
		 */
		public HistoryMenuEntry(
				String text,
				MatrixBrowserPanel.MatrixBrowserPanelMemento panelMemento,
				AbstractTreeView.AbstractTreeViewMemento horizontalMemento,
				AbstractTreeView.AbstractTreeViewMemento verticalMemento,
				DefaultRelationPane.DefaultRelationPaneMemento relationPaneMemento) {
			super();
			setText(text);
			mbpMemento = panelMemento;
			atvHorizontalMemento = horizontalMemento;
			atvVerticalMemento = verticalMemento;
			drpMemento = relationPaneMemento;
			// create HistoryPopup
			mJPopupMenu = new HistoryPopup(this);
			mJPopupMenu.add(text);
			// set PopupListener of this entry
			MouseListener popupListener = new PopupListener();
			addMouseListener(popupListener);
		}

		/**
		 * Returns the atvHorizontalMemento.
		 * 
		 * @TODO schotti: whatever that means----
		 * @return the atvHorizontalMemento.
		 */
		protected AbstractTreeView.AbstractTreeViewMemento getHorizontalTreeViewMemento() {
			return atvHorizontalMemento;
		}

		/**
		 * Returns the atvVerticalMemento.
		 * 
		 * @TODO schotti:whatever that means...
		 * @return the atvVerticalMemento.
		 */
		protected AbstractTreeView.AbstractTreeViewMemento getVerticalTreeViewMemento() {
			return atvVerticalMemento;
		}

		/**
		 * Returns the drpMemento.
		 * 
		 * @TODO schotti: whatever that means...
		 * @return the drpMemento.
		 */
		protected DefaultRelationPane.DefaultRelationPaneMemento getRelationPaneMemento() {
			return drpMemento;
		}

		/**
		 * Returns the mbpMemento.
		 * 
		 * @TODO schotti: whatever that means...
		 * 
		 * @return the mbpMemento
		 */
		protected MatrixBrowserPanel.MatrixBrowserPanelMemento getMatrixBrowserPanelMemento() {
			return mbpMemento;
		}

		/**
		 * Returns the saved screenshot of DefaultRelationPaneMemento.
		 * 
		 * @return the saved screenshot
		 */
		public Image getScreenShot() {
			return drpMemento.getScreenShot();
		}

		/**
		 * PopupListener is an extended MouseAdapter to react if the mouse
		 * enters a HistoryMenuEntry. If entered, it calculates the position of
		 * the popup menu and displays it
		 * 
		 * @author schaal
		 */
		private class PopupListener extends MouseAdapter {

			private final int INDENT = 10;

			/**
			 * calculate position of HistoryPopup and display it
			 */
			public void mouseEntered(MouseEvent e) {
				Dimension screenSize = getToolkit().getScreenSize();
				Dimension popupSize = mJPopupMenu.getPreferredSize();

				Point ownerLocation = e.getComponent().getLocationOnScreen();
				Dimension ownerSize = e.getComponent().getPreferredSize();

				Point calculatedLocation = new Point();

				if (ownerLocation.x + ownerSize.width + popupSize.width > screenSize.width) {
					calculatedLocation.x = ownerLocation.x - popupSize.width;
					calculatedLocation.x -= INDENT;
				} else {
					calculatedLocation.x = ownerLocation.x + ownerSize.width;
					calculatedLocation.x += INDENT;
				}

				if (ownerLocation.y + ownerSize.height + popupSize.height > screenSize.height) {
					calculatedLocation.y = ownerLocation.y;
					// calculatedLocation.y -= INDENT;
				} else {
					calculatedLocation.y = ownerLocation.y;
					// calculatedLocation.y += INDENT;
				}

				mJPopupMenu.setLocation(calculatedLocation);
				mJPopupMenu.setVisible(true);

			}

			/**
			 * hide HistoryPopup if mouse press occurs
			 */
			public void mousePressed(MouseEvent e) {
				mJPopupMenu.setVisible(false);
			}

			/**
			 * hide HistoryPopup if mouse release occurs
			 */
			public void mouseReleased(MouseEvent e) {
				mJPopupMenu.setVisible(false);
			}

			/**
			 * hide HistoryPopup if mouse exits the HistoryMenuEntry
			 */
			public void mouseExited(MouseEvent e) {
				mJPopupMenu.setVisible(false);
			}
		}
	}
}