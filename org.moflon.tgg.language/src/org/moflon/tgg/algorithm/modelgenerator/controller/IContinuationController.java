package org.moflon.tgg.algorithm.modelgenerator.controller;

import org.moflon.tgg.algorithm.modelgenerator.ModelgenStats;
import org.moflon.tgg.runtime.RuleResult;

public interface IContinuationController {

	boolean continueWork(RuleResult lastResult, ModelgenStats modelgenStats);

}