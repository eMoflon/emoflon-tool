package org.moflon.tgg.algorithm.modelgenerator.controller;

import org.apache.log4j.Logger;
import org.moflon.tgg.algorithm.modelgenerator.ModelGenerator;
import org.moflon.tgg.algorithm.modelgenerator.ModelgenStats;
import org.moflon.tgg.runtime.RuleResult;

public class TimeoutController implements IContinuationController {

	private int timeoutInMs;

	Logger logger = Logger.getLogger(TimeoutController.class);

	public TimeoutController(int timeoutInMs) {
		this.timeoutInMs = timeoutInMs;
	}

	@Override
	public boolean continueWork(RuleResult lastResult, ModelgenStats modelgenStats) {

		if ((modelgenStats.getTotalDuration() + modelgenStats.getTotalFailureDuration()) >= this.timeoutInMs) {
			if (ModelGenerator.writeLog) {
				logger.warn("Modelgenerator algorithm timed out with the last rule: " + lastResult.getRule());
			}
			return false;
		}

		return true;
	}

}
