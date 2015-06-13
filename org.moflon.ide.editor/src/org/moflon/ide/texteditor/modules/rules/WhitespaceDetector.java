package org.moflon.ide.texteditor.modules.rules;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

/**
 * used to determine a specified character is whitespace or not
 * @author mdavid
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 *
 */
public class WhitespaceDetector implements IWhitespaceDetector {

	/**
	 * the chars ' ', '\t', '\n', '\r' are whitespace
	 * @see org.eclipse.jface.text.rules.IWhitespaceDetector
	 */
	@Override
	public boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}

}
