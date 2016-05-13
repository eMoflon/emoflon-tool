package org.moflon.tgg.mosl.ui.highlighting;

import org.moflon.tgg.mosl.ui.highlighting.rules.CreationOperatorPatternHighlightRule;
import org.moflon.tgg.mosl.ui.highlighting.rules.DestructionOperatorPatternHighlightRule;
import org.moflon.tgg.mosl.ui.highlighting.rules.NegationOperationPatternHighlightRule;
import org.moflon.tgg.mosl.ui.highlighting.rules.RefinedVariablePatternHighlightingRule;

public class MOSLHighlightFactory {
	
	/**
	 * In this method must all new HighlightingRules be created. If a Rule is not created it will not be used.
	 */
	public static void createAllInstances(){
		new CreationOperatorPatternHighlightRule();
		new DestructionOperatorPatternHighlightRule();
		new NegationOperationPatternHighlightRule();
		new RefinedVariablePatternHighlightingRule();
	}
}
