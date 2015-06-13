package org.moflon.ide.texteditor.modules.rules;

import org.eclipse.jface.text.rules.IWordDetector;

/**
 * used to determine a specified character is start/part of a word or not 
 * @author mdavid
 * @author (last editor) $Author$
 * @version $Revision$ $Date$
 *
 */
public class WordDetector implements IWordDetector {

	private char[] delimiters = new char[]{};
	
	public WordDetector(char[] delimiters){
		this.delimiters = delimiters;
	}
	/**
	 * every char but whitespace can be start of a word
	 * @see org.eclipse.jface.text.rules.IWordDetector#isWordStart(char)
	 */
	public boolean isWordStart(char c) {
		if(isDelimiter(c))
		   return false;
		if (new WhitespaceDetector().isWhitespace(c))
		   return false;
		return true;
	}

	/**
	 * every char but whitespace can be part of a word
	 * @see org.eclipse.jface.text.rules.IWordDetector#isWordPart(char)
	 */
	@Override
	public boolean isWordPart(char c) {
      if(isDelimiter(c))
         return false;
      if (new WhitespaceDetector().isWhitespace(c))
         return false;
      return true;
	}
	
	public void setDelimiters(char[] delimiters){
		this.delimiters = delimiters;
	}
	
	private boolean isDelimiter(char c){
		for(int i = 0; i < delimiters.length; i++){
			if(c == delimiters[i])
				return true;
		}
		return false;
	}

}
