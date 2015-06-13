/**
 * This Code has been developed by Fraunhofer Institute for Industrial
 * Engineering / IAO. The original developer of the MatrixBrowser and also the
 * FhG IAO will have no liability for use of this software or modifications or
 * derivatives thereof. It's Open Source for noncommercial applications. Please
 * read carefully the IAO_License.txt and/or contact the authors. File : $Id:
 * ResizeBorder.java,v 1.4 2004/04/13 12:36:35 kookaburra Exp $ Created on
 * 04.04.02
 */
package de.fhg.iao.matrixbrowser.widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

/**
 * A specialized <code>Border</code> for a <code>NodeCloseUpView</code> which
 * displays a grey area in the lower right corner indicating that the
 * sourrounded component can be resized.
 * 
 * @author <a href=mailto:christoph.kunz@iao.fraunhofer.de>Christoph Kunz </a>
 * @version $Revision: 1.3 $
 * @see javax.swing.border.Border
 */
public class ResizeBorder extends AbstractBorder {

	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 1L;

	/** Holds the borders color */
	private Color myColor = null;

	/** Holds the thickness of the border */
	private int myThickness = 0;

	/**
	 * Creates a line border with the specified color.
	 * 
	 * @param aColor
	 *            the color of the border
	 * @see javax.swing.border#AbstractBorder
	 */
	public ResizeBorder(Color aColor) {
		this(aColor, 2);
	}

	/**
	 * Creates a line border with the specified color and thickness.
	 * 
	 * @param color
	 *            the color of the border myThickness the thickness of the
	 *            border
	 * @see javax.swing.border#AbstractBorder
	 */
	public ResizeBorder(Color aColor, int aThickness) {
		myColor = aColor;
		myThickness = aThickness;
	}

	/**
	 * Paints the border for the specified component with the specified position
	 * and size.
	 * 
	 * @param c
	 *            the component for which this border is being painted
	 * @param g
	 *            the paint graphics
	 * @param x
	 *            the x position of the painted border
	 * @param y
	 *            the y position of the painted border
	 * @param width
	 *            - the width of the painted border
	 * @param height
	 *            - the height of the painted border
	 * @see javax.swing.border#AbstractBorder
	 */
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		Color oldColor = g.getColor();

		g.setColor(myColor);
		g.drawRect(x, y, width - 2, height - 2);

		Color brigthColor = myColor.brighter();
		g.setColor(brigthColor);
		g.drawLine(width - 15, height - 1, width - 1, height - 1);
		g.drawLine(width - 1, height - 15, width - 1, height - 1);
		g.setColor(oldColor);
	}

	/**
	 * Returns the insets of the border.
	 * 
	 * @param c
	 *            the component for which this border insets value applies
	 */
	public Insets getBorderInsets(Component c) {
		return new Insets(myThickness, myThickness, myThickness, myThickness);
	}

	/**
	 * Reinitialize the insets parameter with this Border's current Insets.
	 * 
	 * @param c
	 *            the component for which this border insets value applies
	 * @param insets
	 *            the object to be reinitialized
	 */
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.left = insets.top = insets.right = insets.bottom = myThickness;

		return insets;
	}

	/**
	 * Returns the color of the border.
	 * 
	 * @return the line color
	 */
	public Color getLineColor() {
		return myColor;
	}
}