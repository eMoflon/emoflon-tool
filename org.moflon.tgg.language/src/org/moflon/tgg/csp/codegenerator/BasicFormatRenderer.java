package org.moflon.tgg.csp.codegenerator;

import org.antlr.stringtemplate.AttributeRenderer;

/**
 * A basic format renderer, which provides String operation for StringTemplates.
 * 
 * @author david
 * 
 */
public class BasicFormatRenderer implements AttributeRenderer {
	public String toString(Object o) {
		return o.toString();
	}

	public String toString(Object o, String formatName) {
		if (formatName.equals("firstToUpper")) {
			return firstToUpper(o.toString());
		} else if (formatName.equals("firstToLower")) {
			return firstToLower(o.toString());
		} else if (formatName.equals("toLower")) {
			return o.toString().toLowerCase();
		} else {
			throw new IllegalArgumentException("Unsupported format name");
		}
	}

	public static String firstToUpper(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static String firstToLower(String s) {
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

}