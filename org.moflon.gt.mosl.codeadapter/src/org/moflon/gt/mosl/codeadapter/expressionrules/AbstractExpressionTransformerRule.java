package org.moflon.gt.mosl.codeadapter.expressionrules;

import org.gervarro.democles.specification.emf.PatternBody;
import org.moflon.gt.mosl.codeadapter.ExpressionBuilder;
import org.moflon.gt.mosl.moslgt.AbstractAttribute;
import org.moflon.gt.mosl.moslgt.Expression;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;

public abstract class AbstractExpressionTransformerRule<A extends AbstractAttribute, E extends Expression> implements IAbstractAttributeClassGetter<A>
{

   public AbstractExpressionTransformerRule()
   {
      ExpressionBuilder.getInstance().addTranformerFun(getAbstractAttributeClass(), getExpressionClass(), ov -> expr -> patternBody -> {
         castExpression(ov, expr, patternBody);
      });
   }

   protected abstract Class<E> getExpressionClass();

   protected void preTransform(ObjectVariableDefinition ov, E expr, PatternBody patternBody)
   {
      // Do nothing
   }

   protected abstract void transform(ObjectVariableDefinition ov, E expr, PatternBody patternBody);

   protected void postTransform(ObjectVariableDefinition ov, E expr, PatternBody patternBody)
   {
      // Do nothing
   }

   private void castExpression(ObjectVariableDefinition ov, Expression expr, PatternBody patternBody)
   {
      E castedExpr = getExpressionClass().cast(expr);
      preTransform(ov, castedExpr, patternBody);
      transform(ov, castedExpr, patternBody);
      postTransform(ov, castedExpr, patternBody);
   }
}
