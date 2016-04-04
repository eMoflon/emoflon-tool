package org.moflon.tgg.mosl.ui.highlighting;

import org.eclipse.swt.SWT;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class MOSLHighlightingConfiguration extends DefaultHighlightingConfiguration
{

   @Override
   public TextStyle keywordTextStyle()
   {
      TextStyle ts = super.keywordTextStyle();
      ts.setStyle(SWT.ITALIC);
      return ts;
   }
}
