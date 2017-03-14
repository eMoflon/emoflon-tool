package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.codeadapter.codeadapter.StatementBuilder;
import org.moflon.gt.mosl.moslgt.Statement;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.Scope;

public abstract class AbstractStatementRule <S extends Statement>{

	public AbstractStatementRule() {
	   //TODO@rkluge: This usage of static method invocations may obfuscate the control flow from novice developers. I suggest to use dependency injection: Pass a StatementBuider instance to the constructor!
		StatementBuilder.setStatementRule(getStatementClass(), 
				stmnt -> scope -> previosCFNode ->{castStatement(stmnt, scope, previosCFNode);}); // using currying here
	}
	
	protected abstract Class<S> getStatementClass();
	protected abstract void transformStatement(S stmnt, Scope scope, CFNode previosCFNode);
	
   //TODO@rkluge: I hav no clue why preTransformtStatement invokes transformStatement and then postTransformStatement...
	protected void preTransformStatement(S stmnt, Scope scope, CFNode previosCFNode){
		transformStatement(stmnt, scope, previosCFNode);
		postTransformStatement(stmnt, scope, previosCFNode);
	}
	
	protected void postTransformStatement(S stmnt, Scope scope, CFNode previosCFNode){
		//default do nothing
	}
	
	//TODO@rkluge: I hav no clue why preTransformtStatement is called here...
	private void castStatement(final Statement stmnt, Scope scope, CFNode previosCFNode){
		preTransformStatement(getStatementClass().cast(stmnt), scope, previosCFNode);
	}
	
	
}
