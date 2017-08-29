package org.moflon.gt.mosl.codeadapter.transformplanrules;

import java.util.Map;

import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.moslgt.NACGroup;
import org.moflon.sdm.runtime.democles.CFVariable;

public abstract class NonNACTransformationPlan extends SimpleTypeVariableOrientedTransformPlanRule
{

   public NonNACTransformationPlan(PatternKind patternKind)
   {
      super(patternKind);
   }

   @Override
   protected boolean filterConditionNac(NACGroup nac, Map<String, CFVariable> env)
   {
      return false;
   }

}
