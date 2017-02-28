package org.moflon.gt.mosl.codeadapter.codeadapter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.moflon.gt.mosl.moslgt.MethodDec;
import org.moflon.gt.mosl.moslgt.Statement;
import org.moflon.sdm.runtime.democles.Scope;

public class StatementAdapter {
	
	/* 
	 * using currying like Haskell so the function look like Statement->Scope->Nothing
	 * which means void f(Statement arg0, Scope arg1)
	 */
	private static Map<Class<? extends Statement>, Function<Statement, Consumer<Scope>>> statementRuleCache = new HashMap<>();
	
	private static StatementAdapter instance;
	
	private MethodDec currentMethod;
	
	private StatementAdapter(){
		statementRuleCache.clear();
		new StatementRuleAutofactory();
	}
	
	public static StatementAdapter getInstance(){
		if(instance == null)
			instance = new StatementAdapter();
		return instance;
	}
	
	public static void setStatementRule(Class<? extends Statement> stmtClass, Function<Statement, Consumer<Scope>> transformerRule){
		statementRuleCache.put(stmtClass, transformerRule);
	}
	
	/*
	 * A modular Version of many instanceof type matcher. 
	 * If there is a performance issue this modular program must transform to many ugly instanceof conditions.
	 * Reason instanceof uses bytecode and Class.isInstance is running at runtime.  
	 */
	public void transformStatement(final Statement stmnt, Scope scope){
		for(Map.Entry<Class<? extends Statement>, Function<Statement, Consumer<Scope>>> entry : statementRuleCache.entrySet()){
			Class<? extends Statement> stmntRuleClass = entry.getKey();
			if(stmntRuleClass.isInstance(stmnt)){
				entry.getValue().apply(stmnt).accept(scope);
			}
		}
	}
	
	/*
	 * load the currentMethod before using transformStatement !!!!!!
	 */
	public void loadCurrentMethod(MethodDec method){
		currentMethod=method;
	}
	
	public MethodDec getCurrentMethod(){
		return currentMethod;
	}
}
