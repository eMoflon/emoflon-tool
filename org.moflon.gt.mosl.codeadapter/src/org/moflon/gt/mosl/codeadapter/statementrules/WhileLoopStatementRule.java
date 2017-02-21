package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.moslgt.WhileLoopStatement;
import org.moflon.sdm.runtime.democles.Scope;

public class WhileLoopStatementRule extends AbstractStatementRule<WhileLoopStatement> {

	@Override
	protected Class<WhileLoopStatement> getStatementClass() {
		return WhileLoopStatement.class;
	}

	@Override
	protected void transformStatement(WhileLoopStatement stmnt, Scope scope) {
		// TODO Auto-generated method stub
		
	}

}
