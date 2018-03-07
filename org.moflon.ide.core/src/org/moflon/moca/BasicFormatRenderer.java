package org.moflon.moca;

import org.antlr.stringtemplate.AttributeRenderer;
import org.moflon.core.utilities.MoflonConventions;

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
		switch (formatName) {
		case "firstToUpper":
			return firstToUpper(o.toString());
		case "firstToLower":
			return firstToLower(o.toString());
		case "toLower":
			return o.toString().toLowerCase();
		case "extractLastSegment":
			return extractLastSegment(o.toString());
		default:
			throw new IllegalArgumentException("Unsupported format name: " + formatName);
		}
	}

	public static String firstToUpper(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static String firstToLower(String s) {
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	public static String extractLastSegment(String s) {
		return MoflonConventions.getDefaultNameOfFileInProjectWithoutExtension(s);
	}
}