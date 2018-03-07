package org.moflon.tgg.algorithm.delta.attribute;

import java.util.ArrayList;
import java.util.Collection;

import org.moflon.tgg.language.csp.Variable;

public class AttributConstraintContainer {
	String instanceName;
	String ruleName;
	String constraint;
	boolean eq;

	public boolean isEq() {
		return eq;
	}

	public void setEq(boolean eq) {
		this.eq = eq;
	}

	Collection<Variable> destinationVars;
	Collection<String> solveVars;

	public AttributConstraintContainer(String ruleName) {
		destinationVars = new ArrayList<Variable>();
		solveVars = new ArrayList<String>();
		this.ruleName = ruleName;
		this.eq = false;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public Collection<Variable> getDestinationVars() {
		return destinationVars;
	}

	public void setDestinationVars(Collection<Variable> collection) {
		this.destinationVars = collection;
	}

	public Collection<String> getSolveVars() {
		return solveVars;
	}

	public void setSolveVars(Collection<String> solveVars) {
		this.solveVars = solveVars;
	}

	public String getRuleName() {
		return ruleName;
	}

	public String getConstraint() {
		return constraint;
	}

	public void setConstraint(String constraint) {
		this.constraint = constraint;
	}
}
