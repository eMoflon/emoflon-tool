package org.moflon.tgg.algorithm.modelgenerator.controller;

import java.util.List;
import java.util.Random;

import org.eclipse.emf.ecore.EOperation;
import org.moflon.tgg.algorithm.modelgenerator.ModelgenStats;

public class RandomRuleSelector implements IRuleSelector {

	private Random random = new Random();

	@Override
	public EOperation getNextGenModelOperation(List<EOperation> rulesToBeApplied, ModelgenStats modelgenStats) {
		int nextInt = random.nextInt(rulesToBeApplied.size());
		EOperation nextRule = rulesToBeApplied.get(nextInt);

		return nextRule;
	}

}
