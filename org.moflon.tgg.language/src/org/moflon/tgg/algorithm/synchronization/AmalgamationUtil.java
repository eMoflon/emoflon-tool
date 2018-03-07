package org.moflon.tgg.algorithm.synchronization;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.moflon.tgg.language.analysis.Rule;
import org.moflon.tgg.language.analysis.RulesTable;
import org.moflon.tgg.runtime.IsApplicableMatch;
import org.moflon.tgg.runtime.Match;
import org.moflon.tgg.runtime.PerformRuleResult;
import org.moflon.tgg.runtime.TGGRuleMorphism;

public class AmalgamationUtil {

	private boolean isAmalgamatedTGG;

	private RulesTable lookupMethods = null;

	private HashMap<Match, Collection<Match>> matchToComplements = new HashMap<>();

	private HashSet<String> complementRuleNames = null;

	public AmalgamationUtil(RulesTable lookupMethods) {
		this.lookupMethods = lookupMethods;
		isAmalgamatedTGG = lookupMethods.getRules().stream().anyMatch(r -> r.getKernel() != null);
		complementRuleNames = new HashSet<>();
		lookupMethods.getRules().stream().filter(r -> r.getKernel() != null)
				.forEach(r -> complementRuleNames.add(r.getRuleName()));
	}

	public Collection<Match> determineComplements(Match kernel, Collection<Match> candidates) {
		Rule kernelRule = getRule(kernel.getRuleName());
		if (!kernelRule.getComplements().isEmpty()) {
			if (!matchToComplements.containsKey(kernel))

				matchToComplements.put(kernel, candidates.stream().filter(comp -> isKernelOf(kernel, comp, kernelRule))
						.collect(Collectors.toSet()));

			return matchToComplements.get(kernel);
		}
		return new HashSet<Match>();
	}

	public Collection<IsApplicableMatch> determineComplements(PerformRuleResult kernel,
			Stream<IsApplicableMatch> candidates) {
		return candidates.filter(comp -> isKernelOf(kernel, comp, getRule(kernel.getRuleName())))
				.collect(Collectors.toSet());
	}

	private boolean isKernelOf(TGGRuleMorphism kernel, TGGRuleMorphism complement, Rule kernelRule) {
		if (kernelRule.getComplements().stream()
				.anyMatch(comp -> comp.getRuleName().equals(complement.getRuleName()))) {
			return kernel.getNodeMappings().keySet().stream()
					.noneMatch(key -> complement.getObject(key) != kernel.getObject(key));
		}

		return false;
	}

	private Rule getRule(String name) {
		return lookupMethods.getRules().stream().filter(r -> r.getRuleName().equals(name)).findAny().get();
	}

	public boolean isAmalgamatedTGG() {
		return isAmalgamatedTGG;
	}

	public boolean isComplementMatch(Match m) {
		return isAmalgamatedTGG() && complementRuleNames.contains(m.getRuleName());
	}

	public boolean isKernelMatch(Match m) {
		return !isComplementMatch(m);
	}

}
