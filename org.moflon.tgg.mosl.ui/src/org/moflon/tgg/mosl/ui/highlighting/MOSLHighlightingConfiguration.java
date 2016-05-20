package org.moflon.tgg.mosl.ui.highlighting;

import org.eclipse.swt.SWT;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;

import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.tgg.mosl.ui.highlighting.rules.AbstractHighlightingRule;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLColor;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLHighlightProviderHelper;

public class MOSLHighlightingConfiguration extends DefaultHighlightingConfiguration
{
	
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		for(AbstractHighlightingRule<?> rule : MOSLHighlightProviderHelper.getHighlightRules())
			rule.setHighlightingConfiguration(acceptor);
	}
	
   @Override
   public TextStyle keywordTextStyle()
   {
      TextStyle ts = super.keywordTextStyle();
      ts.setStyle(SWT.ITALIC);
      return ts;
   }
   
   @Override
   public TextStyle commentTextStyle(){
	   TextStyle ts = super.commentTextStyle();
	   ts.setColor(MOSLColor.GRAY.getColor());
	   return ts;
   }
   
   @Override
	public TextStyle stringTextStyle() {
		TextStyle textStyle = super.stringTextStyle();
		textStyle.setColor(MOSLColor.LIGHT_BLUE.getColor());
		return textStyle;
	}

}
