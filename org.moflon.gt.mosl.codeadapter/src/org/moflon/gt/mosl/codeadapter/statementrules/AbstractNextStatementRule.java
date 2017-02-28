package org.moflon.gt.mosl.codeadapter.statementrules;

import org.eclipse.emf.ecore.EClassifier;
import org.moflon.gt.mosl.codeadapter.codeadapter.StatementAdapter;
import org.moflon.gt.mosl.exceptions.MissingReturnException;
import org.moflon.gt.mosl.moslgt.MoslgtFactory;
import org.moflon.gt.mosl.moslgt.NextStatement;
import org.moflon.gt.mosl.moslgt.Statement;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public abstract class AbstractNextStatementRule<S extends NextStatement> extends AbstractStatementRule<S> {
	@Override
	protected void postTransformStatement(S stmnt, Scope scope) {
		Statement nextStmnt = stmnt.getNext();
		scope.setNextScope(DemoclesFactory.eINSTANCE.createScope());
		if(nextStmnt == null){
			EClassifier methodType =StatementAdapter.getInstance().getCurrentMethod().getType();
			if(methodType==null)
				nextStmnt = MoslgtFactory.eINSTANCE.createReturnStatement();
			else
				throw new MissingReturnException(StatementAdapter.getInstance().getCurrentMethod());
		}		
		StatementAdapter.getInstance().transformStatement(nextStmnt, scope.getNextScope());
	}
}
