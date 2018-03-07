package org.moflon.tgg.mosl.codeadapter.csp.constraints;

import org.moflon.tgg.language.csp.Variable;

public class IsNotEnum extends IsReallyEnum {
	public void solve(Variable var_0) {
		String bindingStates = getBindingStates(var_0);

		switch (bindingStates) {
		case "B":
			setSatisfied(!isEnum(var_0.getValue()));
			return;
		case "F":
			// Nothing to check
			setSatisfied(true);
			return;
		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}
}