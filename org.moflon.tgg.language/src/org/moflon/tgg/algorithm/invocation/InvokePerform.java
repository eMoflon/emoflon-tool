package org.moflon.tgg.algorithm.invocation;

import java.util.function.Function;

import org.eclipse.emf.ecore.EOperation;
import org.moflon.tgg.runtime.IsApplicableMatch;
import org.moflon.tgg.runtime.PerformRuleResult;

/**
 * Used to invoke "perform" methods. These methods are operationalized TGG rules
 * that actually apply a rule to a given isApplicable (complete) match over all
 * domains.
 * 
 * @author anjorin
 *
 */
public class InvokePerform implements Function<IsApplicableMatch, PerformRuleResult> {

	@Override
	public PerformRuleResult apply(IsApplicableMatch match) {
		EOperation perform = match.getIsApplicableRuleResult().getPerformOperation();

		return (PerformRuleResult) InvokeUtil.invokeOperationWithSingleArg(perform.getEContainingClass(), perform,
				match);
	}

}
