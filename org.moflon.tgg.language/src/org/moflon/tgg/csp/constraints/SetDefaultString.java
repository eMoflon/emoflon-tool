package org.moflon.tgg.csp.constraints;

import org.moflon.tgg.csp.constraints.generator.Generator;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class SetDefaultString extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1) {
		String bindingStates = getBindingStates(var_0, var_1);

		switch (bindingStates) {
		case "BB":
			setSatisfied(true);
			return;
		case "FB":
			var_0.bindToValue(var_1.getValue());
			setSatisfied(true);
			return;

		case "FF":

			var_0.bindToValue(Generator.getNewRandomString(var_0.getType()));
			var_1.bindToValue(Generator.getNewRandomString(var_1.getType()));
			setSatisfied(true);
			return;

		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}
}