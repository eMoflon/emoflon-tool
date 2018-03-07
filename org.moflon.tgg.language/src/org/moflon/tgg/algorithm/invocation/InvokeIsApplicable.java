package org.moflon.tgg.algorithm.invocation;

import java.util.function.Function;

import org.eclipse.emf.ecore.EOperation;
import org.moflon.tgg.runtime.IsApplicableRuleResult;
import org.moflon.tgg.runtime.Match;

/**
 * Used to invoke "isApplicable" methods. These methods are operationalized TGG
 * rules that check if a current isAppropriate input match can be extended to a
 * full match over all domains.
 * 
 * @author anjorin
 *
 */
public class InvokeIsApplicable implements Function<Match, IsApplicableRuleResult> {

	@Override
	public IsApplicableRuleResult apply(Match match) {
		EOperation isAppl = match.getIsApplicableOperation();
		IsApplicableRuleResult isApplRR = (IsApplicableRuleResult) InvokeUtil
				.invokeOperationWithSingleArg(isAppl.getEContainingClass(), isAppl, match);
		// FIXME: do the following line in compiler
		isApplRR.setCoreMatch(match);
		return isApplRR;
	}

}
