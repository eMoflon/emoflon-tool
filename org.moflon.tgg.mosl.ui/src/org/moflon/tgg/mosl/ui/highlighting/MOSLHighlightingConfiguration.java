package org.moflon.tgg.mosl.ui.highlighting;

import org.eclipse.swt.SWT;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;

import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class MOSLHighlightingConfiguration extends DefaultHighlightingConfiguration
{

	public static final String CREATION_ID = "Create";
	public static final String DESTROY_ID = "Destroy";
	public static final String NEGATE_ID = "Negate";
	
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		acceptor.acceptDefaultHighlighting(CREATION_ID, "Create-Operator", creationTextStyle());
		acceptor.acceptDefaultHighlighting(DESTROY_ID, "Destroy-Operator", destroyTextStyle());
		acceptor.acceptDefaultHighlighting(NEGATE_ID, "Negate-Operator", negateTextStyle());
	}
	
   @Override
   public TextStyle keywordTextStyle()
   {
      TextStyle ts = super.keywordTextStyle();
      ts.setStyle(SWT.ITALIC);
      return ts;
   }

   public TextStyle creationTextStyle(){
	      TextStyle ts = defaultTextStyle().copy();
	      ts.setColor(MOSLColor.DARK_GREEN.getColor());
	      return ts;
   }
   
   public TextStyle destroyTextStyle(){
	      TextStyle ts = defaultTextStyle().copy();
	      ts.setColor(MOSLColor.RED.getColor());
	      return ts;
   }
   
   public TextStyle negateTextStyle(){
	      TextStyle ts = defaultTextStyle().copy();
	      ts.setColor(MOSLColor.BLACK.getColor());
	      ts.setStyle(SWT.BOLD);
	      return ts;
}

}
