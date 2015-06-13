/*
 * Created on 20.08.2004
 */
package de.fhg.iao.matrixbrowser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPopupMenu;

import de.fhg.iao.matrixbrowser.HistoryMenu.HistoryMenuEntry;

/**
 * @author schaal Popup for the History Menu that displays the screenshot saved
 *         in a HistoryMenuEntry
 */
public class HistoryPopup extends JPopupMenu {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/** the HistoryMenuEntry of this HistoryPopup, needed for the screenshot */
	HistoryMenuEntry mEntry;

	/** the size of the border displayed around the screenshot */
	public final int BORDER_SIZE = 10;

	/** the color of the border */
	public final Color BORDER_COLOR = Color.red;

	/** dimension of the screenshot */
	Dimension mDimension;

	/**
	 * constructor
	 * 
	 * @param entry
	 *            the HistoryMenuEntry this Popup belongs to
	 */
	public HistoryPopup(HistoryMenuEntry entry) {
		super();
		mEntry = entry;
		calculateSize();
		setPreferredSize(mDimension);

	}

	/**
	 * calculates the preferred Size of this component the maximum size of the
	 * screenshot is half of the current screen size this is done to avoid
	 * problems with placing the screenshot.
	 */
	private void calculateSize() {
		Dimension screenSize = getToolkit().getScreenSize();
		if (mEntry.getScreenShot() != null) {
			mDimension = new Dimension(mEntry.getScreenShot().getWidth(null)
					/ 2 + 2 * BORDER_SIZE, mEntry.getScreenShot().getHeight(
					null)
					/ 2 + 2 * BORDER_SIZE);
			if (mDimension.width > screenSize.width / 2) {
				mDimension.width = screenSize.width / 2;
			}
			if (mDimension.height > screenSize.height / 2) {
				mDimension.height = screenSize.height / 2;
			}
		} else
			mDimension = new Dimension(100, 100);
	}

	/**
	 * returns the previously calculated dimension of this popup
	 */
	public Dimension getPreferredSize() {
		return mDimension;
	}

	/**
	 * returns the previously calculated dimension of this popup
	 */
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	/**
	 * returns the previously calculated dimension of this popup
	 */
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	/**
	 * paints this component, i.e. displays the screenshot with a border
	 */
	public void paint(Graphics g) {

		g.setColor(BORDER_COLOR);
		g.fillRect(0, 0, mDimension.width, mDimension.height);
		if (mEntry.getScreenShot() != null)
			g.drawImage(mEntry.getScreenShot(), BORDER_SIZE, BORDER_SIZE,
					mDimension.width - 2 * BORDER_SIZE, mDimension.height - 2
							* BORDER_SIZE, null);
	}
}