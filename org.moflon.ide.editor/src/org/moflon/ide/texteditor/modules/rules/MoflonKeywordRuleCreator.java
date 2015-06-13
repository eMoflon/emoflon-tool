package org.moflon.ide.texteditor.modules.rules;

import java.util.Collection;
import java.util.Vector;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.moflon.ide.texteditor.editors.colors.COLORS;
import org.moflon.ide.texteditor.helpers.TextAttributeHelper;

public class MoflonKeywordRuleCreator extends MoflonRuleCreator
{

	private String lastAddedKeyword;
	private WordRule wordrule;

	public MoflonKeywordRuleCreator(IWordDetector wordDetector)
	{
		this.wordrule = new WordRule(wordDetector);
	}

	/**
	 * 
	 * @param color
	 *            defines the color of the text.
	 */
	public void as(COLORS color)
	{
		TextAttribute attribute = TextAttributeHelper.createTextAttribute(color, null, null, 0, 0, 0);
		IToken token = new Token(attribute);
		wordrule.addWord(lastAddedKeyword, token);
	}

	/**
	 * 
	 * @param color
	 *            defines the color of the text.
	 * @param style
	 *            defines text style attributes. Values: BOLD, ITALIC. If style
	 *            is 0, no style will be applied.
	 * @param lineStyle
	 *            defines line style of text. Values: UNDERLINED, STRIKETHROUGH.
	 *            If lineStyle is 0, no lines will be applied.
	 */
	public void as(COLORS color, int style, int lineStyle)
	{
		TextAttribute attribute = TextAttributeHelper.createTextAttribute(color, null, null, 0, style, lineStyle);
		IToken token = new Token(attribute);
		wordrule.addWord(lastAddedKeyword, token);
	}

	/**
	 * 
	 * @param color
	 *            defines the color of the text.
	 * @param bgColor
	 *            defines the color of the background. If bgColor is null, color
	 *            wont be changed.
	 * @param fontName
	 *            defines the font. If fontName is null, font wont be changed.
	 * @param fontHeight
	 *            defines the size of the text. If fontHeight is 0, size wont be
	 *            changed.
	 * @param style
	 *            defines text style attributes. Values: BOLD, ITALIC. If style
	 *            is 0, no style will be applied.
	 * @param lineStyle
	 *            defines line style of text. Values: UNDERLINED, STRIKETHROUGH.
	 *            If lineStyle is 0, no lines will be applied.
	 * 
	 */
	public void as(COLORS color, COLORS bgColor, String fontName, int fontHeight, int style, int lineStyle)
	{
		TextAttribute attribute = TextAttributeHelper.createTextAttribute(color, bgColor, fontName, fontHeight, style, lineStyle);
		IToken token = new Token(attribute);
		wordrule.addWord(lastAddedKeyword, token);
	}

	public void setKeyword(String keyword)
	{
		this.lastAddedKeyword = keyword;
	}

	@Override
	public Collection<IRule> getRules()
	{
		Vector<IRule> result = new Vector<IRule>();
		result.add(wordrule);
		return result;
	}

}
