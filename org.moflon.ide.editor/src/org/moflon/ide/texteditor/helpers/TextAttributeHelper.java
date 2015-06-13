package org.moflon.ide.texteditor.helpers;

import java.util.HashMap;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.moflon.ide.texteditor.editors.colors.COLORS;
import org.moflon.ide.texteditor.editors.colors.ColorManager;


public class TextAttributeHelper {

   private static HashMap<Integer, Font> fonts = new HashMap<>();
   
	public static TextAttribute createTextAttribute(COLORS color, COLORS bgColor, String fontName, int fontHeight, int style, int lineStyle){
		RGB rgb 		 = ColorManager.getInstance().getRGB(color);
		RGB bgRGBColor	 = ColorManager.getInstance().getRGB(bgColor);
		Color swtColor   = ColorManager.getInstance().getColor(rgb);
		Color swtBgColor = null;
		if(bgRGBColor != null)
		{
			swtBgColor = ColorManager.getInstance().getColor(bgRGBColor);
		}
		
		//Get current font settings
		FontData[] fontData =JFaceResources.getTextFont().getFontData();	

		//Set default settings if not set
		if(fontName == null && fontData.length > 0){
			fontName = fontData[0].getName();
		}
		
		if(fontHeight == 0 && fontData.length > 0){
			fontHeight = fontData[0].getHeight();
		}
		
		//Set new font
		Font font = getFont(style, fontName, fontHeight);

		return new TextAttribute(
				swtColor,
				swtBgColor,
				lineStyle,
				font);
	}

   private static Font getFont(int style, String fontName, int fontHeight)
   {
      if(fonts.get(style) == null){
         fonts.put(style, new Font(null, fontName, fontHeight, style));
      }
      
      return fonts.get(style);
   }
	
	
		
}
