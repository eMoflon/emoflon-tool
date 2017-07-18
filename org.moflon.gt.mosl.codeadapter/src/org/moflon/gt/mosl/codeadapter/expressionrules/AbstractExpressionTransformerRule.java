package org.moflon.gt.mosl.codeadapter.expressionrules;

import org.gervarro.democles.specification.emf.PatternBody;
import org.moflon.gt.mosl.moslgt.AbstractAttribute;
import org.moflon.gt.mosl.moslgt.Expression;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;

public abstract class AbstractExpressionTransformerRule<A extends AbstractAttribute, E extends Expression> implements IAbstractAttributeClassGetter<A>, IExpressionTransformationRule
{
   protected abstract Class<E> getExpressionClass();

   protected abstract ValidationReport transform(ObjectVariableDefinition ov, E expr, PatternBody patternBody);
   
   @Override
   public boolean canHandle(final AbstractAttribute attribute)
   {
      final Expression expression = attribute.getValueExp();
      return expression.getClass().isInstance(getExpressionClass());
   }

   @Override
   public ValidationReport invoke(final ObjectVariableDefinition ov, final AbstractAttribute abstractAttribute, final PatternBody patternBody)
   {
      return castAndInvokeExpression(ov, abstractAttribute.getValueExp(), patternBody);
   }

   private ValidationReport castAndInvokeExpression(final ObjectVariableDefinition objectVariable, final Expression expression, final PatternBody patternBody)
   {
      final E castedExpr = getExpressionClass().cast(expression);
      return transform(objectVariable, castedExpr, patternBody);
   }
}
