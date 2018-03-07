package org.moflon.tgg.algorithm.configuration;

import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.runtime.IsApplicableMatch;

/**
 * Wrapper used to hide details of our internal match structure when allowing
 * user access.
 * 
 * @author anjorin
 */
public class MatchInfo {
	private String ruleName;

	private IsApplicableMatch internalMatch;

	public MatchInfo(IsApplicableMatch match) {
		ruleName = match.getIsApplicableRuleResult().getRule();
		internalMatch = match;
	}

	public EObject getMatchedObject(String name) {
		return internalMatch.getObject(name);
	}

	public boolean isRule(String name) {
		return ruleName.equals(name);
	}

	IsApplicableMatch getInternalMatch() {
		return internalMatch;
	}

}
