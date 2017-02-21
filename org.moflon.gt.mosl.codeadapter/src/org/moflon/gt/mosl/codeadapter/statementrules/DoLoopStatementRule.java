package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.moslgt.DoLoopStatement;
import org.moflon.sdm.runtime.democles.Scope;

public class DoLoopStatementRule extends AbstractStatementRule<DoLoopStatement> {

	@Override
	protected Class<DoLoopStatement> getStatementClass() {
		return DoLoopStatement.class;
	}

	@Override
	protected void transformStatement(DoLoopStatement stmnt, Scope scope) {
		// TODO Auto-generated method stub
		
	}

}
