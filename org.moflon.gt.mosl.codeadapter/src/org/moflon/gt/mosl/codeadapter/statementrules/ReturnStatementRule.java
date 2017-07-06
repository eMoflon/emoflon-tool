package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.moslgt.ReturnStatement;
import org.moflon.sdm.runtime.democles.Action;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public class ReturnStatementRule extends AbstractStatementRule<ReturnStatement> implements IHandlePatternsInStatement
{

   @Override
   protected Class<ReturnStatement> getStatementClass()
   {
      return ReturnStatement.class;
   }

   @Override
   protected void transformStatement(ReturnStatement stmnt, Scope scope, CFNode previosCFNode)
   {
      org.moflon.sdm.runtime.democles.ReturnStatement rs = DemoclesFactory.eINSTANCE.createReturnStatement();
      if (previosCFNode != null)
      {
         previosCFNode.setNext(rs);
         rs.setId(previosCFNode.getId() + 1);
      } else
         rs.setId(1);
      scope.getContents().add(rs);
      
      Action action = DemoclesFactory.eINSTANCE.createAction();
      
      rs.getActions().add(action);
      rs.setMainAction(action);
      
      

     // ObjectVariableDefinition returnValue = stmnt.getReturnObject();

   }

}
