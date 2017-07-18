package org.moflon.gt.mosl.codeadapter.expressionrules;

import org.gervarro.democles.specification.emf.PatternBody;
import org.moflon.gt.mosl.moslgt.AbstractAttribute;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;

public interface IExpressionTransformationRule
{

   ValidationReport invoke(final ObjectVariableDefinition ov, final AbstractAttribute attribute, final PatternBody patternBody);

   boolean canHandle(final AbstractAttribute attribute);

}
