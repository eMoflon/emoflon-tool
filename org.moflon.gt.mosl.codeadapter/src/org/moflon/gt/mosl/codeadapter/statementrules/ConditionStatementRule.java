package org.moflon.gt.mosl.codeadapter.statementrules;

import org.moflon.gt.mosl.codeadapter.codeadapter.StatementBuilder;
import org.moflon.gt.mosl.moslgt.ConditionStatement;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.IfStatement;
import org.moflon.sdm.runtime.democles.Scope;

public class ConditionStatementRule extends AbstractConditionStatementRule<ConditionStatement>
{

   @Override
   protected Class<ConditionStatement> getStatementClass()
   {
      return ConditionStatement.class;
   }

   @Override
   protected void transformStatement(ConditionStatement stmnt, Scope scope, CFNode previosCFNode)
   {
      IfStatement ifStatement = this.updateCurrentNode(DemoclesFactory.eINSTANCE.createIfStatement());
      scope.getContents().add(ifStatement);

      handlePattern(ifStatement);

      Scope thenScope = DemoclesFactory.eINSTANCE.createScope();
      thenScope.setParent(ifStatement);
      StatementBuilder.getInstance().transformStatement(stmnt.getThenStartStatement(), thenScope, null);

      if (stmnt.getElseStartStatement() != null)
      {
         Scope elseScope = DemoclesFactory.eINSTANCE.createScope();
         elseScope.setParent(ifStatement);
         StatementBuilder.getInstance().transformStatement(stmnt.getElseStartStatement(), elseScope, null);
      }
   }

}
