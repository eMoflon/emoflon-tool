package org.moflon.tgg.algorithm.modelgenerator.controller;

import org.apache.log4j.Logger;
import org.moflon.tgg.algorithm.modelgenerator.ModelGenerator;
import org.moflon.tgg.algorithm.modelgenerator.ModelgenStats;
import org.moflon.tgg.runtime.RuleResult;

public class MaxRulePerformCounterController implements IContinuationController {

	private int rulePerformCountMax;

	private final Logger logger = Logger.getLogger(MaxRulePerformCounterController.class);

	public MaxRulePerformCounterController(int rulePerformCountMax) {
		this.rulePerformCountMax = rulePerformCountMax;
	}

	@Override
	public boolean continueWork(RuleResult lastResult, ModelgenStats modelgenStats) {
		if (modelgenStats.getTotalRulePerformCount() >= rulePerformCountMax) {
			if (ModelGenerator.writeLog) {
				logger.info("Maximum number of rule performances has been reached - model generation will be stopped");
			}
			return false;
		}
		return true;
	}
}
