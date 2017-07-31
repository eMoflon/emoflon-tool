package org.moflon.tgg.algorithm.ccutils;

import java.util.HashMap;

public class UserDefinedILPConstraint {



	private HashMap<Integer, Double> idsToCoefficients;
	
	private String mathematicalSign;
	
	private double referenceValue;
	
	public UserDefinedILPConstraint(HashMap<Integer, Double> idsToCoefficients, String mathematicalSign,
			double referenceValue) {
		this.idsToCoefficients = idsToCoefficients;
		this.mathematicalSign = mathematicalSign;
		this.referenceValue = referenceValue;
	}
	
	protected HashMap<Integer, Double> getIdsToCoefficients() {
		return idsToCoefficients;
	}

	protected String getMathematicalSign() {
		return mathematicalSign;
	}

	protected double getReferenceValue() {
		return referenceValue;
	}
}
