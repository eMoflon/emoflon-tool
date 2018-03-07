package org.moflon.tgg.algorithm.modelgenerator;

public class RulePerformStats {

	private String ruleName;

	private int performCount;

	private int failures;

	private long totalPerformDuration;

	private long totalFailureDuration;

	protected RulePerformStats(String ruleName) {
		this.ruleName = ruleName;
	}

	public long getTotalDuration() {
		return totalPerformDuration;
	}

	public void addToDuration(long totalDuration) {
		this.totalPerformDuration += totalDuration;
	}

	public int getFailures() {
		return failures;
	}

	public void incrementFailures() {
		this.failures++;
	}

	public int getPerformCount() {
		return performCount;
	}

	public void addPerforms(int performCount) {
		this.performCount += performCount;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public long getTotalFailureDuration() {
		return totalFailureDuration;
	}

	public void addToFailureDuration(long totalFailureDuration) {
		this.totalFailureDuration += totalFailureDuration;
	}

}
