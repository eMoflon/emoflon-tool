package org.moflon.tgg.mosl.ui.highlighting;

import org.moflon.tgg.mosl.ui.highlighting.rules.CreationVariablePatternHighlightRule;
import org.moflon.tgg.mosl.ui.highlighting.rules.DestructionVariablePatternHighlightRule;
import org.moflon.tgg.mosl.ui.highlighting.rules.NegationVariablePatternHighlightRule;
import org.moflon.tgg.mosl.ui.highlighting.rules.RefinedVariablePatternHighlightingRule;

public class MOSLHighlightFactory {
	
	/**
	 * In this method must all new HighlightingRules be created. If a Rule is not created it will not be used.
	 */
	public static void createAllInstances(){
		new CreationVariablePatternHighlightRule();
		new DestructionVariablePatternHighlightRule();
		new NegationVariablePatternHighlightRule();
		new RefinedVariablePatternHighlightingRule();
	}
}
