package org.moflon.tgg.algorithm.exceptions;

import java.util.Collection;
import java.util.stream.Collectors;

import org.moflon.tgg.runtime.Match;

/**
 * Thrown when the synchronization algorithm is unable to parse the input graph.
 * See {@link InputLocalCompletenessException#getMessage(String)} for more
 * details.
 * 
 * @author anjorin
 *
 */
@SuppressWarnings("serial")
public class InputLocalCompletenessException extends LocalCompletenessException {
	public InputLocalCompletenessException(Collection<Match> inputMatches) {
		ruleNames = inputMatches.stream().map(m -> m.getRuleName()).collect(Collectors.toSet());
		matches = inputMatches.stream().map(m -> m.getNodeMappings()).collect(Collectors.toSet());
	}

	public String getMessage(String projectName) {
		return "Your TGG " + projectName
				+ " is not input locally complete!\nThis means I was unable parse the input graph without backtracking.\n"
				+ "This is usually due to conflicts between TGG rules that try to create the same elements.\n"
				+ "Please consider your TGG carefully and try to avoid creating the same elements with different rules if possible.";
	}
}
