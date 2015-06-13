package org.moflon.ide.texteditor.editors.colors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * class used for color handling
 * @author mdavid
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 *
 */
public class ColorManager {

	protected Map<RGB, Color> fColorTable = new HashMap<RGB, Color>(10);

	private static ColorManager instance;
	
	public static ColorManager getInstance(){
		if(instance == null)
			instance = new ColorManager();
		return instance;
	}
	
	private ColorManager(){
		
	}
	
	public RGB getRGB(COLORS colors){
		RGB rgb = new RGB(0, 0, 0);

		if (colors==null)
			return null;
		switch (colors) {
		case RED:
			rgb = new RGB(255, 0, 0);
			break;
		case GREEN:
			rgb = new RGB(0, 128, 0);
			break;
		case BLUE:
			rgb = new RGB(0, 0, 255);
			break;
		case DARKBLUE:
		   rgb = new RGB(0,0,128);
		   break;
		case GRAY:
			rgb = new RGB(144,144,144);
			break;
		case BROWN:
			rgb = new RGB(205, 133, 63);
			break;
		case ORANGE:
			rgb = new RGB(255, 165, 0);
			break;
		case PURPLE:
			rgb = new RGB(160, 32, 240);
			break;
		case VIOLET:
			rgb = new RGB(149, 0, 85);
			break;		
		case WHITE:
			rgb = new RGB(255, 255, 255);
			break;	
		case BLACK:
			rgb = new RGB(0, 0, 0);
			break;
			
		}
		
		return rgb;
	}
	
	/**
	 * Converts a org.eclipse.swt.graphics.RGB into a org.eclipse.swt.graphics.Color
	 * and stores the converted color if it's not stored jet.
	 * @param rgb the RGB to convert
	 * @return the Color
	 */
	public Color getColor(RGB rgb) {
		Color color = (Color) fColorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}
		return color;
	}
}
