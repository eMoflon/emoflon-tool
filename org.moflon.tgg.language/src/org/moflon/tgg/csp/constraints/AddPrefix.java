package org.moflon.tgg.csp.constraints;

import org.moflon.tgg.csp.constraints.generator.Generator;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class AddPrefix extends TGGConstraintImpl {

	/**
	 * addPrefix(prefix,word,result) prefix+word = result (prefix concatenated with
	 * word equals result)
	 * 
	 * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
	 */
	public void solve(Variable prefix, Variable word, Variable result) {
		String bindingStates = getBindingStates(prefix, word, result);

		// BBB - check prefix
		if (bindingStates.equals("BBB")) {
			setSatisfied(((String) prefix.getValue() + word.getValue()).equals(result.getValue()));
		}
		// BBF - add prefix
		else if (bindingStates.equals("BBF")) {
			result.setValue((String) prefix.getValue() + word.getValue());
			result.setBound(true);
			setSatisfied(true);
		}
		// BFB - remove prefix
		else if (bindingStates.equals("BFB")) {
			if (((String) result.getValue()).startsWith((String) prefix.getValue())) {
				word.setValue(((String) result.getValue()).substring(((String) prefix.getValue()).length()));
				word.setBound(true);
				setSatisfied(true);
			}
		}
		// FBB - determine prefix
		else if (bindingStates.equals("FBB")) {
			if (((String) result.getValue()).endsWith((String) word.getValue())) {
				prefix.setValue(((String) result.getValue()).substring(0,
						((String) result.getValue()).length() - ((String) word.getValue()).length()));
				prefix.setBound(true);
				setSatisfied(true);
			}
		}
		// modelgen implementations
		else if (bindingStates.equals("BFF")) {
			String randomWord = Generator.getNewRandomString(word.getType());
			word.bindToValue(randomWord);
			result.bindToValue(prefix.getValue() + randomWord);
			setSatisfied(true);
		} else if (bindingStates.equals("FFF")) {
			String randomWord = Generator.getNewRandomString(word.getType());
			prefix.bindToValue("prefix");
			word.bindToValue(randomWord);
			result.bindToValue(prefix.getValue() + randomWord);
			setSatisfied(true);
		} else if (bindingStates.equals("FBF")) {
			String randomWord = Generator.getNewRandomString(prefix.getType());
			prefix.bindToValue(randomWord);
			result.bindToValue(prefix.getValue().toString() + word.getValue());
			setSatisfied(true);
		} else {
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}

}
