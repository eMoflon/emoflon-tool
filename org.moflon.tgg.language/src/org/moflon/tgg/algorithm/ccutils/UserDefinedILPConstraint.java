package org.moflon.tgg.algorithm.ccutils;

import java.util.HashMap;
import java.util.Map;

public class UserDefinedILPConstraint {



	private Map<Integer, Double> idsToCoefficients;
	
	private String mathematicalSign;
	
	private double referenceValue;
	
	public UserDefinedILPConstraint(Map<Integer, Double> idsToCoefficients, String mathematicalSign,
			double referenceValue) {
		this.idsToCoefficients = idsToCoefficients;
		this.mathematicalSign = mathematicalSign;
		this.referenceValue = referenceValue;
	}
	
	protected Map<Integer, Double> getIdsToCoefficients() {
		return idsToCoefficients;
	}

	protected String getMathematicalSign() {
		return mathematicalSign;
	}

	protected double getReferenceValue() {
		return referenceValue;
	}
}
