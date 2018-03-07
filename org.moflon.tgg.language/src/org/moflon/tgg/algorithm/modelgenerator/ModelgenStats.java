package org.moflon.tgg.algorithm.modelgenerator;

import java.util.HashMap;
import java.util.Map;

public class ModelgenStats {

	Map<String, RulePerformStats> ruleNameToPerformStats = new HashMap<String, RulePerformStats>();

	private int srcNodeCount;

	private int trgNodeCount;

	private int corrNodeCount;

	private int srcEdgeCount;

	private int trgEdgeCount;

	private int corrEdgeCount;

	public int getTotalRulePerformCount() {
		int i = 0;
		for (RulePerformStats ruleStats : ruleNameToPerformStats.values()) {
			i += ruleStats.getPerformCount();
		}
		return i;
	}

	public int getSrcElementCount() {
		return srcNodeCount;
	}

	public int getTrgElementCount() {
		return trgNodeCount;
	}

	public int getCorrElementCount() {
		return corrNodeCount;
	}

	public int getSrcConnectorCount() {
		return srcEdgeCount;
	}

	public int getTrgConnectorCount() {
		return trgEdgeCount;
	}

	public int getCorrConnectorCount() {
		return corrEdgeCount;
	}

	public int getTotalDuration() {
		int i = 0;
		for (RulePerformStats ruleStats : ruleNameToPerformStats.values()) {
			i += ruleStats.getTotalDuration();
		}
		return i;
	}

	public int getTotalFailureDuration() {
		int i = 0;
		for (RulePerformStats ruleStats : ruleNameToPerformStats.values()) {
			i += ruleStats.getTotalFailureDuration();
		}
		return i;
	}

	public int getTotalFailureCount() {
		int i = 0;
		for (RulePerformStats ruleStats : ruleNameToPerformStats.values()) {
			i += ruleStats.getFailures();
		}
		return i;
	}

	public Map<String, RulePerformStats> getRulePerformStats() {
		return ruleNameToPerformStats;
	}

	public void updateRulePerformCount(String ruleName, int countToAdd) {
		if (!ruleNameToPerformStats.containsKey(ruleName)) {
			ruleNameToPerformStats.put(ruleName, new RulePerformStats(ruleName));
		}
		ruleNameToPerformStats.get(ruleName).addPerforms(countToAdd);
	}

	public void updateRuleDuration(String ruleName, long duration) {
		if (!ruleNameToPerformStats.containsKey(ruleName)) {
			ruleNameToPerformStats.put(ruleName, new RulePerformStats(ruleName));
		}
		ruleNameToPerformStats.get(ruleName).addToDuration(duration);
	}

	public void addFailure(String ruleName, long duration) {
		if (ruleNameToPerformStats.containsKey(ruleName)) {
			ruleNameToPerformStats.get(ruleName).incrementFailures();
			ruleNameToPerformStats.get(ruleName).addToFailureDuration(duration);

		} else {
			RulePerformStats newStats = new RulePerformStats(ruleName);
			newStats.incrementFailures();
			newStats.addToFailureDuration(duration);
			ruleNameToPerformStats.put(ruleName, newStats);
		}
	}

	public void updateNodeEdgeCount(RuleAnalysis ruleAnalysis, int performCount) {
		for (int i = 0; i < performCount; i++) {
			this.srcNodeCount += ruleAnalysis.getGreenSrcNodes();
			this.corrNodeCount += ruleAnalysis.getGreenCorrNodes();
			this.trgNodeCount += ruleAnalysis.getGreenTrgNodes();
			this.srcEdgeCount += ruleAnalysis.getGreenSrcEdges();
			this.trgEdgeCount += ruleAnalysis.getGreenTrgEdges();
			this.corrEdgeCount += ruleAnalysis.getGreenCorrEdges();
		}

	}

}
