package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.moslgt.ForLoopStatement;
import org.moflon.sdm.runtime.democles.Scope;

public class ForLoopStatementRule extends AbstractStatementRule<ForLoopStatement> {

	@Override
	protected Class<ForLoopStatement> getStatementClass() {
		return ForLoopStatement.class;
	}

	@Override
	protected void transformStatement(ForLoopStatement stmnt, Scope scope) {
		// TODO Auto-generated method s
		
	}

}
