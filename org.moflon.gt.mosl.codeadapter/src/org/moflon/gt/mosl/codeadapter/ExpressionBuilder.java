package org.moflon.gt.mosl.codeadapter;

import java.util.ArrayList;
import java.util.List;

import org.gervarro.democles.specification.emf.PatternBody;
import org.moflon.gt.mosl.codeadapter.expressionrules.IExpressionTransformationRule;
import org.moflon.gt.mosl.moslgt.AbstractAttribute;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;

public class ExpressionBuilder
{

   private final List<IExpressionTransformationRule> transformationRules; 

   public ExpressionBuilder()
   {
      this.transformationRules = new ArrayList<>();
   }

   public void transformExpression(ObjectVariableDefinition ov, AbstractAttribute attribute, PatternBody patternBody)
   {
      for (final IExpressionTransformationRule transformationRule : this.transformationRules)
      {
         if (transformationRule.canHandle(attribute))
         {
            transformationRule.invoke(ov, attribute, patternBody);
         }
      }
   }
}
