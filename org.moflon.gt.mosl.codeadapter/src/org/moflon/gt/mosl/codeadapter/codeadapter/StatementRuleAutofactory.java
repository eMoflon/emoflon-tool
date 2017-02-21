package org.moflon.gt.mosl.codeadapter.codeadapter;

import org.moflon.gt.mosl.codeadapter.statementrules.*;

public class StatementRuleAutofactory {

	public StatementRuleAutofactory(){
		createAllInstances();
	}

	private void createAllInstances() {
		new ReturnStatementRule();
		new PatternStatementRule();
		new ConditionStatementRule();
		new WhileLoopStatementRule();
		new ForLoopStatementRule();
		new DoLoopStatementRule();
		new ObjectVariableDefinitionRule();
	}
	
}
