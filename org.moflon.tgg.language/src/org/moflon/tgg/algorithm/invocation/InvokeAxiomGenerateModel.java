package org.moflon.tgg.algorithm.invocation;

import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.moflon.tgg.algorithm.modelgenerator.RulePerformData;
import org.moflon.tgg.language.modelgenerator.ModelgeneratorFactory;
import org.moflon.tgg.language.modelgenerator.RuleEntryContainer;
import org.moflon.tgg.runtime.ModelgeneratorRuleResult;

public class InvokeAxiomGenerateModel implements Function<RulePerformData, ModelgeneratorRuleResult> {

	@Override
	public ModelgeneratorRuleResult apply(RulePerformData performData) {
		EOperation eOperation = performData.getCurrentGenModelOperation();
		EClass ruleClass = eOperation.getEContainingClass();

		RuleEntryContainer emptyContainer = ModelgeneratorFactory.eINSTANCE.createRuleEntryContainer();

		return (ModelgeneratorRuleResult) InvokeUtil.invokeOperationWithSingleArg(ruleClass, eOperation,
				emptyContainer);
	}

}
