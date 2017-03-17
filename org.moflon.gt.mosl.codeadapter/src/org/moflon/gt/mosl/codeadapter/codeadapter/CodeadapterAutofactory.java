package org.moflon.gt.mosl.codeadapter.codeadapter;

import org.moflon.gt.mosl.codeadapter.linkvariablerules.MatchingUnboundLVTransformingRule;
import org.moflon.gt.mosl.codeadapter.objectvariablerules.MatchingUnboundOVTransformerRule;
import org.moflon.gt.mosl.codeadapter.statementrules.*;

public class CodeadapterAutofactory
{

   public CodeadapterAutofactory()
   {
      createAllStatementInstances();
      createAllObjectVariableTransformers();
      createAllLinkVariableTransformers();
      createAllExpressionRules();
   }

   private void createAllExpressionRules()
   {
      // TODO Auto-generated method stub

   }

   private void createAllLinkVariableTransformers()
   {
      new MatchingUnboundLVTransformingRule();
   }

   private void createAllObjectVariableTransformers()
   {
      new MatchingUnboundOVTransformerRule();
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
