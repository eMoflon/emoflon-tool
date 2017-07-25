package org.moflon.gt.mosl.codeadapter.transformplanrules;

import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.moslgt.Operator;

public class GreenTransformPlanRule extends NonNACTransformationPlan
{

   public GreenTransformPlanRule()
   {
      super(PatternKind.GREEN);
   }

   @Override
   protected boolean getConditionFromOperator(Operator op)
   {
      return op != null && op.getValue() != null && "++".compareTo(op.getValue())==0;
   }

}
