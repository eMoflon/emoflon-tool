package org.moflon.gt.mosl.codeadapter.statementrules;

import java.util.function.Consumer;

import org.moflon.gt.mosl.moslgt.Condition;
import org.moflon.gt.mosl.moslgt.ConditionContainingStatement;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.Scope;

public abstract class AbstractConditionStatementRule<S extends ConditionContainingStatement> extends AbstractNextStatementRule<S>
{

   @Override
   protected void preTransformStatement(S stmnt, Scope scope, CFNode previosCFNode)
   {
      Condition cond = stmnt.getCond();
      handlePattern = c -> this.handlePattern(cond.getParameters(), cond.getPattern(), c, scope);
      super.preTransformStatement(stmnt, scope, previosCFNode);
   }

   private Consumer<CFNode> handlePattern;

   protected void handlePattern(CFNode node)
   {
      handlePattern.accept(node);
   }
}
