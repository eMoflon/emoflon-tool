package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.codeadapter.codeadapter.StatementAdapter;
import org.moflon.gt.mosl.moslgt.NextStatement;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public abstract class AbstractNextStatementRule<S extends NextStatement> extends AbstractStatementRule<S> {
	@Override
	protected void postTransformStatement(S stmnt, Scope scope) {
		scope.setNextScope(DemoclesFactory.eINSTANCE.createScope());		
		StatementAdapter.getInstance().transformStatement(stmnt.getNext(), scope.getNextScope());
	}
}
