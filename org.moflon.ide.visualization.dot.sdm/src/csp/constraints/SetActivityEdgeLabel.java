package csp.constraints;

import SDMLanguage.activities.EdgeGuard;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class SetActivityEdgeLabel extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1) {
		String bindingStates = getBindingStates(var_0, var_1);

		switch (bindingStates) {
		case "BB": {
			setSatisfied(true);
			return;
		}
		case "FB": {
			var_0.bindToValue(var_1.getValue().equals(EdgeGuard.NONE) ? ""
					: ((EdgeGuard) var_1.getValue()).getName());
			setSatisfied(true);
			return;
		}
		case "BF": {
			setSatisfied(true);
			return;
		}
		}

	}
}