package org.moflon.tgg.algorithm.exceptions;

import java.util.Collection;
import java.util.stream.Collectors;

import org.moflon.tgg.runtime.IsApplicableRuleResult;

/**
 * Thrown when the synchronization algorithm is unable to extend the current
 * ready set of source matches. See
 * {@link TranslationLocalCompletenessException#getMessage(String)} for more
 * details.
 * 
 * @author anjorin
 *
 */
@SuppressWarnings("serial")
public class TranslationLocalCompletenessException extends LocalCompletenessException {
	public TranslationLocalCompletenessException(Collection<IsApplicableRuleResult> extended) {
		ruleNames = extended.stream().map(m -> m.getCoreMatch().getRuleName()).collect(Collectors.toSet());
		matches = extended.stream().map(m -> m.getCoreMatch().getNodeMappings()).collect(Collectors.toSet());
	}

	public String getMessage(String projectName) {
		return "Your TGG " + projectName
				+ " is not translation locally complete!\nThis means I was unable to translate the input graph without backtracking.\n"
				+ "This is usually due to TGG rules that do not have enough context information in the input component to guarantee the "
				+ "required context in the correspondence and output components when translating.\n"
				+ "Please consider your TGG carefully and make sure that all context elements in the output component can be implied from the "
				+ "input context elements of each rule.";
	}
}
