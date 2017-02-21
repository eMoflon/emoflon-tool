package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.codeadapter.codeadapter.StatementAdapter;
import org.moflon.gt.mosl.moslgt.ConditionStatement;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.IfStatement;
import org.moflon.sdm.runtime.democles.Scope;

public class ConditionStatementRule extends AbstractConditionStatementRule<ConditionStatement> {

	@Override
	protected Class<ConditionStatement> getStatementClass() {
		return ConditionStatement.class;
	}

	@Override
	protected void transformStatement(ConditionStatement stmnt, Scope scope) {
		IfStatement ifStatement = DemoclesFactory.eINSTANCE.createIfStatement();
		scope.getContents().add(ifStatement);
		
		Scope thenScope = DemoclesFactory.eINSTANCE.createScope();
		thenScope.setParent(ifStatement);		
		StatementAdapter.getInstance().transformStatement(stmnt.getThenStartStatement(), thenScope);
		
		if(stmnt.getElseStartStatement() != null){
			Scope elseScope = DemoclesFactory.eINSTANCE.createScope();
			elseScope.setParent(ifStatement);
		}		
	}

}
