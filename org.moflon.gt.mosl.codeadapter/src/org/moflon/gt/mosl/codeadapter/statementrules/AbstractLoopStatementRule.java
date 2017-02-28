package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.codeadapter.codeadapter.StatementAdapter;
import org.moflon.gt.mosl.moslgt.LoopStatement;
import org.moflon.sdm.runtime.democles.CompoundNode;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public abstract class AbstractLoopStatementRule<S extends LoopStatement> extends AbstractConditionStatementRule<S> {
	@Override
	protected void transformStatement(S stmnt, Scope scope) {
		Scope innerScope = DemoclesFactory.eINSTANCE.createScope();
		CompoundNode parent = createCurrentCompoundNode();
		handlePattern(parent);
		innerScope.setParent(parent);
		parent.setScope(scope);
		StatementAdapter.getInstance().transformStatement(stmnt.getLoopStartStatement(), innerScope);
	}
	
	protected abstract CompoundNode createCurrentCompoundNode();
}