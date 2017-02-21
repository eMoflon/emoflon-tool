package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.codeadapter.codeadapter.StatementAdapter;
import org.moflon.gt.mosl.moslgt.Statement;
import org.moflon.sdm.runtime.democles.Scope;

public abstract class AbstractStatementRule <S extends Statement>{

	public AbstractStatementRule() {
		StatementAdapter.setStatementRule(getStatementClass(), 
				stmnt -> scope -> {castStatement(stmnt, scope);}); // using currying here
	}
	
	protected abstract Class<S> getStatementClass();
	protected abstract void transformStatement(S stmnt, Scope scope);
	private void castStatement(final Statement stmnt, Scope scope){
		transformStatement(getStatementClass().cast(stmnt), scope);
	}
	
}
