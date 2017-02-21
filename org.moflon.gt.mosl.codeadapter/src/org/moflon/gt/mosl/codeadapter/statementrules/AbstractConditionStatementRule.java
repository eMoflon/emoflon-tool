package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.moslgt.ConditionContainingStatement;
import org.moflon.sdm.runtime.democles.Scope;

public abstract class AbstractConditionStatementRule<S extends ConditionContainingStatement> extends AbstractNextStatementRule<S> {
	
	
	@Override
	protected void preTransformStatement(S stmnt, Scope scope) {
		//TODO Conditions
		super.preTransformStatement(stmnt, scope);
	}
}
