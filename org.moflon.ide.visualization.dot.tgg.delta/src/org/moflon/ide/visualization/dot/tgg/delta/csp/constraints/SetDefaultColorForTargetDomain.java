package org.moflon.ide.visualization.dot.tgg.delta.csp.constraints;

import org.moflon.ide.visualization.dot.language.DotColor;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class SetDefaultColorForTargetDomain extends TGGConstraintImpl {
	public void solve(Variable var_0) {
		String bindingStates = getBindingStates(var_0);

		switch (bindingStates) {
		case "F":
			var_0.bindToValue(DotColor.MISTYROSE);
			setSatisfied(true);
			break;
		case "B":
			setSatisfied(var_0.getValue().equals(DotColor.MISTYROSE));
			break;
		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}
}