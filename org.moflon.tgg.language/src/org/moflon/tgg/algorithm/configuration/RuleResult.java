package org.moflon.tgg.algorithm.configuration;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.moflon.tgg.runtime.IsApplicableMatch;
import org.moflon.tgg.runtime.IsApplicableRuleResult;

/**
 * Wrapper for internal rule result data structure used when providing user
 * access.
 * 
 * @author anjorin
 *
 */
public class RuleResult {
	private Collection<MatchInfo> matches;

	private String ruleName;

	public RuleResult(IsApplicableRuleResult ruleResult) {
		matches = ruleResult.getIsApplicableMatch().stream().map(MatchInfo::new).collect(Collectors.toSet());
		ruleName = ruleResult.getRule();
	}

	public IsApplicableMatch anyMatch() {
		return matches.stream().findAny().get().getInternalMatch();
	}

	public boolean isEmpty() {
		return matches.isEmpty();
	}

	public boolean isRule(String name) {
		return ruleName.equals(name);
	}

	public String getRule() {
		return ruleName;
	}

	public void restrictMatchesTo(Predicate<MatchInfo> condition) {
		matches = matches.stream().filter(condition).collect(Collectors.toSet());
	}

	public boolean isUnique() {
		return matches.size() == 1;
	}
}
