package org.moflon.ide.visualization.dot.sdmpatterns.csp.constraints;

import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

import SDMLanguage.patterns.BindingState;

public class BoldToState extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1) {
		String bindingStates = getBindingStates(var_0, var_1);

		switch (bindingStates) {
		case "FB":
			BindingState state = (BindingState) var_1.getValue();
			switch (state) {
			case BOUND:
				var_0.bindToValue(true);
				break;
			case UNBOUND:
				var_0.bindToValue(false);
				break;

			default:
				break;
			}
			break;
		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

		setSatisfied(true);
	}
}