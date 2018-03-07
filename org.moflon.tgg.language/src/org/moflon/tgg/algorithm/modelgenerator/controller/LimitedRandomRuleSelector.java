package org.moflon.tgg.algorithm.modelgenerator.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EOperation;
import org.moflon.tgg.algorithm.modelgenerator.ModelgenStats;

/*
 * This rule selector selects a random rule and checks if this rule has already been used to the 
 * defined limit.
 */
public class LimitedRandomRuleSelector extends RandomRuleSelector {

	Map<String, Integer> ruleNameToPerformCount = new HashMap<String, Integer>();

	/**
	 * 
	 * @param ruleName
	 *            name of a rule which to be limited
	 * @param performLimit
	 *            maximum number of performs for the rule
	 * @return
	 */
	public LimitedRandomRuleSelector addRuleLimit(String ruleName, int performLimit) {
		ruleNameToPerformCount.put(ruleName, performLimit);
		return this;
	}

	@Override
	public EOperation getNextGenModelOperation(List<EOperation> rulesToBeApplied, ModelgenStats modelgenStats) {
		boolean atLeastASingleValidRule = isThereAnApplicableRule(rulesToBeApplied, modelgenStats);

		if (!atLeastASingleValidRule) {
			return null;
		}

		while (true) {
			EOperation randomOperation = super.getNextGenModelOperation(rulesToBeApplied, modelgenStats);
			String ruleName = randomOperation.getEContainingClass().getName();
			// if rule perform count is limited
			if (modelgenStats.getRulePerformStats().containsKey(ruleName)
					&& ruleNameToPerformCount.containsKey(ruleName)) {
				// current limit is lower than defined limit
				if (modelgenStats.getRulePerformStats().get(ruleName).getPerformCount() < ruleNameToPerformCount
						.get(ruleName)) {
					return randomOperation;
				}
			} else {
				return randomOperation;
			}
		}
	}

	private boolean isThereAnApplicableRule(List<EOperation> rulesToBeApplied, ModelgenStats modelgenStats) {
		for (EOperation rule : rulesToBeApplied) {
			String ruleName = rule.getEContainingClass().getName();

			if (modelgenStats.getRulePerformStats().containsKey(ruleName)
					&& ruleNameToPerformCount.containsKey(ruleName)) {
				if (modelgenStats.getRulePerformStats().get(ruleName).getPerformCount() < ruleNameToPerformCount
						.get(ruleName)) {
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}
}
