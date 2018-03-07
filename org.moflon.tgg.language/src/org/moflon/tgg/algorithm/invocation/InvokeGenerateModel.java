package org.moflon.tgg.algorithm.invocation;

import java.util.function.Function;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.moflon.tgg.algorithm.modelgenerator.RulePerformData;
import org.moflon.tgg.runtime.ModelgeneratorRuleResult;

public class InvokeGenerateModel implements Function<RulePerformData, ModelgeneratorRuleResult> {

	@Override
	public ModelgeneratorRuleResult apply(RulePerformData performData) {
		EOperation eOperation = performData.getCurrentGenModelOperation();
		EClass ruleClass = eOperation.getEContainingClass();
		EList<Object> parameterValues = new BasicEList<Object>();

		parameterValues.add(performData.getCurrentContainerParameter());
		for (int i = 0; i < eOperation.getEParameters().size() - 1; i++) {
			parameterValues.add(null);
		}

		return (ModelgeneratorRuleResult) InvokeUtil.invokeOperationWithNArguments(ruleClass, eOperation,
				parameterValues);
	}

}
