package org.moflon.gt.mosl.codeadapter.codeadapter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.moflon.gt.mosl.moslgt.MethodDec;
import org.moflon.gt.mosl.moslgt.Statement;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.Scope;

public class StatementBuilder {
	
	/* 
	 * using currying like Haskell so the function look like Statement->Scope->Nothing
	 * which means void f(Statement arg0, Scope arg1)
	 */
	private static Map<Class<? extends Statement>, Function<Statement, Function<Scope, Consumer<CFNode>>>> statementRuleCache = new HashMap<>();
	
	private static StatementBuilder instance;
	
	private MethodDec currentMethod;
	
	private StatementBuilder(){
	   //TODO@rkluge: This mix of instance-level and type-level operations is really(!) confusing me.
		statementRuleCache.clear();
	}
	
	//TODO@rkluge: I would refrain from using singletons. Often, singletons preclude a proper initialization sequence of the whole build process. I claim that in most cases you can replace the singletons with fresh instances or reuse instances throughout the whole process. Do not(!) worry for performance here.
	public static StatementBuilder getInstance(){
		if(instance == null)
			instance = new StatementBuilder();
		return instance;
	}
	
	public static void setStatementRule(Class<? extends Statement> stmtClass, Function<Statement, Function<Scope, Consumer<CFNode>>> transformerRule){
		statementRuleCache.put(stmtClass, transformerRule);
	}
	
	/*
	 * A modular Version of many instanceof type matcher. 
	 * If there is a performance issue this modular program must transform to many ugly instanceof conditions.
	 * Reason instanceof uses bytecode and Class.isInstance is running at runtime.  
	 */
	public void transformStatement(final Statement stmnt, Scope scope, CFNode previosCFNode){
		for(Map.Entry<Class<? extends Statement>, Function<Statement, Function<Scope, Consumer<CFNode>>>> entry : statementRuleCache.entrySet()){
			Class<? extends Statement> stmntRuleClass = entry.getKey();
			if(stmntRuleClass.isInstance(stmnt)){
				entry.getValue().apply(stmnt).apply(scope).accept(previosCFNode);
			}
		}
	}
	
	/*
	 * load the currentMethod before using transformStatement !!!!!!
	 */
	//TODO@rkluge: Comment does not match behavior - setCurrentMethod would be more appropriate for it.
	public void loadCurrentMethod(MethodDec method){
		currentMethod=method;
	}
	
	public MethodDec getCurrentMethod(){
		return currentMethod;
	}
}
