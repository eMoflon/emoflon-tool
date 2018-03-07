package org.moflon.tgg.algorithm.modelgenerator;

import org.eclipse.emf.ecore.EOperation;
import org.moflon.tgg.language.modelgenerator.RuleEntryContainer;

public class RulePerformData {

	private EOperation currentGenModelOperation;
	private RuleEntryContainer currentContainerParameter;

	public void setCurrentGenModelOperation(EOperation currentGenModelOperation) {
		this.currentGenModelOperation = currentGenModelOperation;
	}

	public EOperation getCurrentGenModelOperation() {
		return this.currentGenModelOperation;
	}

	public RuleEntryContainer getCurrentContainerParameter() {
		return this.currentContainerParameter;
	}

	public void setCurrentContainerParameter(RuleEntryContainer containerParameter) {
		this.currentContainerParameter = containerParameter;
	}

}
