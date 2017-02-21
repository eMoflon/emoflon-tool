package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.moslgt.ConditionStatement;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.IfStatement;
import org.moflon.sdm.runtime.democles.Scope;

public class ConditionStatementRule extends AbstractStatementRule<ConditionStatement> {

	@Override
	protected Class<ConditionStatement> getStatementClass() {
		return ConditionStatement.class;
	}

	@Override
	protected void transformStatement(ConditionStatement stmnt, Scope scope) {
		IfStatement ifStatement = DemoclesFactory.eINSTANCE.createIfStatement();
		scope.getContents().add(ifStatement);
		
		
		
	}

}
