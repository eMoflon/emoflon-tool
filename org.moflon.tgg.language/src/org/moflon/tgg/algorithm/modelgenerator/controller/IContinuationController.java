package org.moflon.tgg.algorithm.modelgenerator.controller;

import org.moflon.tgg.algorithm.modelgenerator.ModelgenStats;

import TGGRuntime.RuleResult;

public interface IContinuationController
{

   boolean continueWork(RuleResult lastResult, ModelgenStats modelgenStats);

}