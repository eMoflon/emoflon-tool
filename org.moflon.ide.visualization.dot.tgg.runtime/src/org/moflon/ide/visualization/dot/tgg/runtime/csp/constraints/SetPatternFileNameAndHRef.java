package org.moflon.ide.visualization.dot.tgg.runtime.csp.constraints;

import java.io.File;

import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class SetPatternFileNameAndHRef extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1) {
		String bindingStates = getBindingStates(var_0, var_1);

		switch (bindingStates) {
		case "BB": {
			setSatisfied(true);
			return;
		}

		case "FF": {
			var_0.bindToValue("Pattern_" + var_0.hashCode() + ".dot");
			var_1.bindToValue("Patterns" + File.separator + ((String) var_0.getValue()).replace(".dot", ".svg"));
			setSatisfied(true);
			return;
		}
		}

	}
}