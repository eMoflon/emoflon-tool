package org.moflon.tgg.mosl.ui.highlighting;

import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.util.Arrays;

import com.google.inject.Inject;

public class MOSLTokenMapper extends DefaultAntlrTokenToAttributeIdMapper {

	public MOSLTokenMapper(){
		super();
	}
	
	@Inject
	public static MOSLTokenMapper mapper = new MOSLTokenMapper();
	
	private static final String[] delemiters = {"':'","'{'","'}'","'('","')'"};
	private static final String[] enumerationDelemiters ={"'::'","'enum::'"};
	
	@Override
	protected String calculateId(String tokenName, int tokenType) {
		String oldId = super.calculateId(tokenName, tokenType);		
		if ("RULE_BOOL".equals(tokenName)) {
			return MOSLHighlightingConfiguration.BOOL_ID;
		}
		if(Arrays.contains(delemiters, tokenName))
			return MOSLHighlightingConfiguration.DEFAULT_ID;
		if(Arrays.contains(enumerationDelemiters, tokenName))
			return MOSLHighlightingConfiguration.STRING_ID;
		return oldId;
	}
	

}
