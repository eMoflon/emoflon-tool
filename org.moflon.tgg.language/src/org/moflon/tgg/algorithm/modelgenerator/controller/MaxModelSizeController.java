package org.moflon.tgg.algorithm.modelgenerator.controller;

import org.moflon.tgg.algorithm.modelgenerator.ModelgenStats;
import org.moflon.tgg.language.DomainType;
import org.moflon.tgg.runtime.RuleResult;

public class MaxModelSizeController implements IContinuationController {

	private int maxModelElementCount;

	private DomainType domainType;

	public MaxModelSizeController(int maxModelElementCount, DomainType domainType) {
		this.domainType = domainType;
		this.maxModelElementCount = maxModelElementCount;
	}

	@Override
	public boolean continueWork(RuleResult lastResult, ModelgenStats modelgenStats) {

		int countToCheck = 0;

		if (domainType == DomainType.SOURCE) {
			countToCheck = modelgenStats.getSrcElementCount() + modelgenStats.getSrcConnectorCount();

		} else if (domainType == DomainType.TARGET) {
			countToCheck = modelgenStats.getTrgElementCount() + modelgenStats.getTrgConnectorCount();
		} else if (domainType == DomainType.CORRESPONDENCE) {
			countToCheck = modelgenStats.getCorrElementCount() + modelgenStats.getCorrConnectorCount();
		}

		if (countToCheck >= maxModelElementCount) {
			return false;
		}

		return true;
	}

}
