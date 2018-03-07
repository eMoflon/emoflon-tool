package org.moflon.tgg.algorithm.modelgenerator.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EOperation;
import org.moflon.tgg.algorithm.modelgenerator.ModelgenStats;
import org.moflon.tgg.runtime.RuleResult;

public abstract class AbstractModelGenerationController {

	private List<IContinuationController> continuationControllers = new ArrayList<IContinuationController>();

	private IRuleSelector ruleSelector;

	public abstract void initializeSubControllers();

	public AbstractModelGenerationController() {
		initializeSubControllers();
	}

	public boolean continueGeneration(final RuleResult lastResult, final ModelgenStats modelgenStats) {
		for (IContinuationController ctrl : continuationControllers) {
			if (!ctrl.continueWork(lastResult, modelgenStats)) {
				return false;
			}
		}
		return true;
	}

	public EOperation getNextTggRule(final List<EOperation> rulesToBeApplied, final ModelgenStats modelgenStats) {
		return ruleSelector.getNextGenModelOperation(rulesToBeApplied, modelgenStats);
	}

	public void setDeadlineControllers(final List<IContinuationController> deadlineControllers) {
		this.continuationControllers = deadlineControllers;
	}

	public IRuleSelector getRuleSelector() {
		return ruleSelector;
	}

	public void setRuleSelector(final IRuleSelector ruleSelector) {
		this.ruleSelector = ruleSelector;
	}

	public void addContinuationController(final IContinuationController controller) {
		continuationControllers.add(controller);
	}

}
