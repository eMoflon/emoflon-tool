package org.moflon.gt.mosl.codeadapter;

import org.moflon.gt.mosl.codeadapter.statementrules.*;
import org.moflon.gt.mosl.codeadapter.transformplanrules.BlackTransformPlanRule;
import org.moflon.gt.mosl.codeadapter.transformplanrules.GreenTransformPlanRule;
import org.moflon.gt.mosl.codeadapter.transformplanrules.RedTransformPlanRule;

public class CodeadapterAutofactory
{

   public CodeadapterAutofactory()
   {
      createAllStatementInstances();

      createAllExpressionRules();
      createAllTransformPlanRules();
   }

   private void createAllTransformPlanRules()
   {
      new BlackTransformPlanRule();
      new GreenTransformPlanRule();
      new RedTransformPlanRule();
   }

   private void createAllExpressionRules()
   {
      // TODO Auto-generated method stub

   }

   private void createAllStatementInstances()
   {
      new ReturnStatementRule();
      new PatternStatementRule();
      new ConditionStatementRule();
      new WhileLoopStatementRule();
      new ForLoopStatementRule();
      new DoLoopStatementRule();
      new ObjectVariableDefinitionRule();
   }

}
