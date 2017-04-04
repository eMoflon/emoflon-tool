package org.moflon.gt.mosl.codeadapter.transformplanrules;

import java.util.Map;

import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.Operator;
import org.moflon.sdm.runtime.democles.CFVariable;

public abstract class SimpleTypeVariableOrientedTransformPlanRule extends VariableOrientedTransformPlanRule
{

   public SimpleTypeVariableOrientedTransformPlanRule(PatternKind patternKind)
   {
      super(patternKind);
   }

   @Override
   protected boolean filterConditionObjectVariable(ObjectVariableDefinition ov, Map<String, Boolean> bindings, Map<String, CFVariable> env)
   {
      return getConditionFromOperator(ov.getOp());
   }

   @Override
   protected boolean filterConditionLinkVariable(LinkVariablePattern lv, ObjectVariableDefinition ov, Map<String, Boolean> bindings, Map<String, CFVariable> env)
   {
      boolean condition = getConditionFromOperator(lv.getOp());
      if(condition){
         this.patternObjectIndex.add(ov);
         this.patternObjectIndex.add(lv.getTarget());
      }
      return condition;
   }

   protected abstract boolean getConditionFromOperator(Operator op);
}
