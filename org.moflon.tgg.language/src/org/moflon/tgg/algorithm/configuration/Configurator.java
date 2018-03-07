package org.moflon.tgg.algorithm.configuration;

import java.util.Collection;

import org.moflon.tgg.algorithm.datastructures.PrecedenceInputGraph;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;

public interface Configurator {
	/**
	 * Used to choose between multiple applicable rules during translation.
	 */
	public default RuleResult chooseOne(Collection<RuleResult> alternatives) {
		return alternatives.stream().findAny().get();
	}

	/**
	 * Return true to activate an additional heuristic used to resolve possible
	 * conflicts (matches that can translate the same element). As this can be quite
	 * costly and is not always required the default is false.
	 */
	public default boolean handleConflicts() {
		return false;
	}

	/**
	 * Allows for user-defined optimizations on the precedence graph before
	 * translation starts. This can be used to ignore certain elements (by removing
	 * their matches from the precedence graph) or by resolving conflicts by
	 * filtering matches for the same element.
	 * 
	 * This method can also be used to save the precedence graph via
	 * {@link SynchronizationHelper#savePrecedenceGraph(PrecedenceInputGraph, String)}
	 * 
	 * @param pg
	 *            The precedence graph built for all collected input matches.
	 */
	public default void inspectPG(PrecedenceInputGraph pg) {

	}
}
