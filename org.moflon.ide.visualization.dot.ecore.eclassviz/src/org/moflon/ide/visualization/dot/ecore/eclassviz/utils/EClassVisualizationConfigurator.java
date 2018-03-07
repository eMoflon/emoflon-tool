package org.moflon.ide.visualization.dot.ecore.eclassviz.utils;

import java.util.Collection;

import org.moflon.tgg.algorithm.configuration.Configurator;
import org.moflon.tgg.algorithm.configuration.RuleResult;

public class EClassVisualizationConfigurator implements Configurator {
	@Override
	public RuleResult chooseOne(Collection<RuleResult> alternatives) {
		return alternatives.stream().filter(rr -> rr.getRule().contains("Interface")).findAny()
				.orElse(tryWithAbstract(alternatives));
	}

	private RuleResult tryWithAbstract(Collection<RuleResult> alternatives) {
		return alternatives.stream().filter(rr -> rr.getRule().contains("Abstract")).findAny()
				.orElse(Configurator.super.chooseOne(alternatives));
	}
}
