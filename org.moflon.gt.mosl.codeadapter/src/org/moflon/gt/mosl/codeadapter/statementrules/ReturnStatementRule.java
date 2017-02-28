package org.moflon.gt.mosl.codeadapter.statementrules;


import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;

import org.moflon.gt.mosl.moslgt.ReturnStatement;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public class ReturnStatementRule extends AbstractStatementRule<ReturnStatement> implements IHandlePatternsInStatement{

	@Override
	protected Class<ReturnStatement> getStatementClass() {
		return ReturnStatement.class;
	}

	@Override
	protected void transformStatement(ReturnStatement stmnt, Scope scope, CFNode previosCFNode) {
		org.moflon.sdm.runtime.democles.ReturnStatement rs = DemoclesFactory.eINSTANCE.createReturnStatement();
		scope.getContents().add(rs);
		
		
		ObjectVariableDefinition returnValue = stmnt.getReturnObject();

	}



}
