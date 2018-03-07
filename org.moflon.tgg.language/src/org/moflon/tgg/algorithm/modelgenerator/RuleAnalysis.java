package org.moflon.tgg.algorithm.modelgenerator;

public class RuleAnalysis {

	private String ruleName;

	private int greenSrcNodes;

	private int greenSrcEdges;

	private int greenTrgNodes;

	private int greenTrgEdges;

	private int greenCorrNodes;

	private int greenCorrEdges;

	public RuleAnalysis(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public int getGreenSrcNodes() {
		return greenSrcNodes;
	}

	public void setGreenSrcNodes(int greenSrcNodes) {
		this.greenSrcNodes = greenSrcNodes;
	}

	public int getGreenSrcEdges() {
		return greenSrcEdges;
	}

	public void setGreenSrcEdges(int greenSrcEdges) {
		this.greenSrcEdges = greenSrcEdges;
	}

	public int getGreenTrgNodes() {
		return greenTrgNodes;
	}

	public void setGreenTrgNodes(int greenTrgNodes) {
		this.greenTrgNodes = greenTrgNodes;
	}

	public int getGreenTrgEdges() {
		return greenTrgEdges;
	}

	public void setGreenTrgEdges(int greenTrgEdges) {
		this.greenTrgEdges = greenTrgEdges;
	}

	public int getGreenCorrNodes() {
		return greenCorrNodes;
	}

	public void setGreenCorrNodes(int greenCorrNodes) {
		this.greenCorrNodes = greenCorrNodes;
	}

	public int getGreenCorrEdges() {
		return greenCorrEdges;
	}

	public void setGreenCorrEdges(int greenCorrEdges) {
		this.greenCorrEdges = greenCorrEdges;
	}

}
