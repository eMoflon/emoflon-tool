package org.moflon.tgg.algorithm.ccutils;

import java.util.Map;

public class UserDefinedILPObjective {

	private Map<Integer, Double> idsToCoefficients;
	
	private OptGoal optimizationGoal;
	
	public UserDefinedILPObjective(Map<Integer, Double> idsToCoefficients, OptGoal optimizationGoal) {
		this.idsToCoefficients = idsToCoefficients;
		this.optimizationGoal = optimizationGoal;
	}
	
	protected Map<Integer, Double> getIdsToCoefficients() {
		return idsToCoefficients;
	}

	protected OptGoal getOptimizationGoal() {
		return optimizationGoal;
	}

	public enum OptGoal {
		MIN,
		MAX
	}
}
