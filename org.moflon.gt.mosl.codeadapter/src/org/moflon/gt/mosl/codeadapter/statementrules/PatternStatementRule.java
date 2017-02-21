package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.moslgt.PatternStatement;
import org.moflon.sdm.runtime.democles.Scope;

public class PatternStatementRule extends AbstractNextStatementRule<PatternStatement> {

	@Override
	protected Class<PatternStatement> getStatementClass() {
		return PatternStatement.class;
	}

	@Override
	protected void transformStatement(PatternStatement stmnt, Scope scope) {
		// TODO Auto-generated method stub
		
	}

}
