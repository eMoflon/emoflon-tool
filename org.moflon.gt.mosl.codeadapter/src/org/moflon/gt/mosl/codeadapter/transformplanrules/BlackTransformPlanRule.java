package org.moflon.gt.mosl.codeadapter.transformplanrules;

import java.util.Map;

import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.moslgt.NACGroup;
import org.moflon.gt.mosl.moslgt.Operator;
import org.moflon.sdm.runtime.democles.CFVariable;

public class BlackTransformPlanRule extends SimpleTypeVariableOrientedTransformPlanRule
{

   public BlackTransformPlanRule()
   {
      super(PatternKind.BLACK);
   }

   @Override
   protected boolean getConditionFromOperator(Operator op)
   {
      return op == null || op.getValue() == null || "".compareTo(op.getValue())==0 || op.getValue().equals("--");
   }

   @Override
   protected boolean filterConditionNac(NACGroup nac, Map<String, CFVariable> env)
   {
      return true;
   }

}
