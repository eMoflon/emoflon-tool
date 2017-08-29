package org.moflon.gt.mosl.codeadapter.transformplanrules;

import java.util.Map;

import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.moslgt.Expression;
import org.moflon.sdm.runtime.democles.CFVariable;

public abstract class VariableOrientedTransformPlanRule extends TransformPlanRule
{
   public VariableOrientedTransformPlanRule(PatternKind patternKind)
   {
      super(patternKind);
   }

   @Override
   protected boolean filterConditionExpression(Expression expr, Map<String, CFVariable> env)
   {
      // Variable Oriented Patterns doesn't need to look at Expressions
      return false;
   }

}
