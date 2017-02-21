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
	
	protected void preTransformStatement(S stmnt, Scope scope){
		transformStatement(stmnt, scope);
		postTransformStatement(stmnt, scope);
	}
	
	protected void postTransformStatement(S stmnt, Scope scope){
		//default do nothing
	}
	
	private void castStatement(final Statement stmnt, Scope scope){
		preTransformStatement(getStatementClass().cast(stmnt), scope);
	}
	
	
}
