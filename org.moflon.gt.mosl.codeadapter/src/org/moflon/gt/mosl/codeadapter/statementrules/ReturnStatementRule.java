package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.codeadapter.config.TransformationConfiguration;
import org.moflon.gt.mosl.moslgt.ReturnStatement;
import org.moflon.sdm.compiler.democles.validation.result.ResultFactory;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.sdm.runtime.democles.Action;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.Scope;

public class ReturnStatementRule extends AbstractStatementRule<ReturnStatement>
{

   @Override
   protected Class<ReturnStatement> getStatementClass()
   {
      return ReturnStatement.class;
   }

   @Override
   protected ValidationReport transformStatement(ReturnStatement stmnt, Scope scope, CFNode previosCFNode, final TransformationConfiguration transformationConfiguration)
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
      return ResultFactory.eINSTANCE.createValidationReport();
   }

   @Override
   protected void invokeNextRule(ReturnStatement stmnt, Scope scope, CFNode previosCFNode, final TransformationConfiguration transformationConfiguration)
   {
      // Nothing to do here since returns statement terminate the control flow.
   }
}
