package org.moflon.ide.visualization.dot.ecore.epackageviz.csp.constraints;

import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class Multiplicity extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1) {
		String bindingStates = getBindingStates(var_0, var_1);

		switch (bindingStates) {
		case "FB":
			translate(var_0, var_1);
			var_0.setBound(true);
			break;
		case "BB":
			translate(var_0, var_1);
			break;
		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}

	private void translate(Variable var_0, Variable var_1) {
		int boundInteger = (int) var_1.getValue();
		String boundString = null;
		if (boundInteger < 0) {
			boundString = "*";
		} else {
			boundString = String.valueOf(boundInteger);
		}
		var_0.bindToValue(boundString);
		setSatisfied(true);
	}
}