package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.sdm.runtime.democles.Scope;

public class ObjectVariableDefinitionRule extends AbstractStatementRule<ObjectVariableDefinition> {

	@Override
	protected Class<ObjectVariableDefinition> getStatementClass() {
		return ObjectVariableDefinition.class;
	}

	@Override
	protected void transformStatement(ObjectVariableDefinition stmnt, Scope scope) {
		// TODO Auto-generated method stub
		
	}

}
