package org.moflon.ide.visualization.dot.ecore.epackageviz;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.moflon.tgg.algorithm.configuration.Configurator;
import org.moflon.tgg.algorithm.configuration.RuleResult;

public class EPackageVisualizationConfigurator implements Configurator {
	private static Map<String, Integer> priorities = new HashMap<>();

	public EPackageVisualizationConfigurator() {
		super();
		priorities.clear();
	}

	@Override
	public RuleResult chooseOne(Collection<RuleResult> alternatives) {
		return alternatives.stream().filter(rr -> rr.getRule().contains("Add")).findAny()
				.orElse(chooseExistingAndBi(alternatives));
	}

	private RuleResult chooseExistingAndBi(Collection<RuleResult> alternatives) {
		return alternatives.stream().filter(rr -> rr.getRule().contains("Existing") && rr.getRule().contains("Bi"))
				.findAny().orElse(chooseBiDirectional(alternatives));
	}

	private RuleResult chooseBiDirectional(Collection<RuleResult> alternatives) {
		return alternatives.stream().filter(rr -> rr.getRule().contains("Bi")).findAny()
				.orElse(Configurator.super.chooseOne(alternatives));
	}

}
