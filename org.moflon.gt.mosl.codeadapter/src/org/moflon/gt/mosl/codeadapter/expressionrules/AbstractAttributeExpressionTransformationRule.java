package org.moflon.gt.mosl.codeadapter.expressionrules;

import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypeFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable;
import org.moflon.gt.mosl.moslgt.AbstractAttribute;
import org.moflon.gt.mosl.moslgt.AttributeExpression;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;

public abstract class AbstractAttributeExpressionTransformationRule <A extends AbstractAttribute> extends AbstractExpressionTransformerRule<A, AttributeExpression>{

	@Override
	protected Class<AttributeExpression> getExpressionClass() {
		return AttributeExpression.class;
	}

	@Override
	protected void transform(ObjectVariableDefinition ov, AttributeExpression expr, PatternBody patternBody) {
		ObjectVariableDefinition ovRef = expr.getObjectVar();
		EMFVariable newVariable = EMFTypeFactory.eINSTANCE.createEMFVariable();
		patternBody.getHeader().getSymbolicParameters().add(newVariable);
		newVariable.setName(ovRef.getName());
		newVariable.setEClassifier(ovRef.getType());
	}

}
