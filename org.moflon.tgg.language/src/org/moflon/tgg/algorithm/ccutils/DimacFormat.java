package org.moflon.tgg.algorithm.ccutils;

public class DimacFormat {

	private int[][] clauses;
	private int numberOfClauses;
	private int numberOfLiterals;

	public int getNumberOfClauses() {
		return numberOfClauses;
	}

	public int getNumberOfLiterals() {
		return numberOfLiterals;
	}

	public int[][] getClauses() {
		return clauses;
	}

	public String getClausesAsFilePath() {
		DimacFormatWriter writer = new DimacFormatWriter();
		return writer.writeDimacFile(clauses, numberOfClauses, numberOfLiterals);
	}

	public DimacFormat(int[][] clauses) {
		this.clauses = clauses;
		numberOfClauses = computeNumberOfClauses();
		numberOfLiterals = computeNumberOfLiterals();
	}

	private int computeNumberOfClauses() {
		return clauses.length;
	}

	private int computeNumberOfLiterals() {
		int highestNumber = 0;
		for (int i = 0; i < numberOfClauses; i++) {
			for (int j = 0; j < clauses[i].length; j++) {
				int current = Math.abs(clauses[i][j]);
				if (current > highestNumber) {
					highestNumber = current;
				}

			}
		}
		return highestNumber;
	}

}
