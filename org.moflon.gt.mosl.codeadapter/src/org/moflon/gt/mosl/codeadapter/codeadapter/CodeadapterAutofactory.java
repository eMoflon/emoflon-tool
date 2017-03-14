package org.moflon.gt.mosl.codeadapter.codeadapter;

import org.moflon.gt.mosl.codeadapter.patterngeneration.MatchingUnboundOVTransformerRule;
import org.moflon.gt.mosl.codeadapter.statementrules.*;

public class CodeadapterAutofactory {

	public CodeadapterAutofactory(){
		createAllStatementInstances();
		createAllPatterTransformers();
	}

	private void createAllPatterTransformers() {
		new MatchingUnboundOVTransformerRule();
	}

	private void createAllStatementInstances() {
		new ReturnStatementRule();
		new PatternStatementRule();
		new ConditionStatementRule();
		new WhileLoopStatementRule();
		new ForLoopStatementRule();
		new DoLoopStatementRule();
		new ObjectVariableDefinitionRule();
	}
	
}
