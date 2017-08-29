package org.moflon.gt.mosl.codeadapter.transformplanrules;

import java.util.Map;

import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.NACAndObjectVariable;
import org.moflon.gt.mosl.moslgt.NACGroup;
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
   protected boolean filterConditionNACAndObjectVariable(NACAndObjectVariable nov, Map<String, CFVariable> env)
   {
      return nov instanceof NACGroup && filterConditionNac(NACGroup.class.cast(nov), env)
            || nov instanceof ObjectVariableDefinition && filterConditionObjectVariable(ObjectVariableDefinition.class.cast(nov), env);
   }

   @Override
   protected boolean filterConditionLinkVariable(LinkVariablePattern lv, ObjectVariableDefinition ov,
         Map<String, CFVariable> env)
   {
      boolean condition = getConditionFromOperator(lv.getOp());
      if (condition)
      {
         this.patternObjectIndex.add(PatternUtil.getCorrespondingOV(ov));
         this.patternObjectIndex.add(PatternUtil.getCorrespondingOV(lv.getTarget()));
      }
      return condition;
   }
   
   private boolean filterConditionObjectVariable(ObjectVariableDefinition ov, Map<String, CFVariable> env)
   {
      return getConditionFromOperator(ov.getOp());
   }

   protected abstract boolean filterConditionNac(NACGroup nac, Map<String, CFVariable> env);

   protected abstract boolean getConditionFromOperator(Operator op);
}
