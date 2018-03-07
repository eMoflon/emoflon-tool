package org.moflon.tgg.algorithm.modelgenerator.controller;

import java.util.List;

import org.eclipse.emf.ecore.EOperation;
import org.moflon.tgg.algorithm.modelgenerator.ModelgenStats;

public interface IRuleSelector {

	EOperation getNextGenModelOperation(List<EOperation> rulesToBeApplied, ModelgenStats modelgenStats);

}
