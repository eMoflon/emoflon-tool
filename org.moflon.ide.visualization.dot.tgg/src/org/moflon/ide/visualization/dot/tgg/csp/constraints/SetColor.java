package org.moflon.ide.visualization.dot.tgg.csp.constraints;

import org.moflon.ide.visualization.dot.language.DotColor;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

import SDMLanguage.patterns.BindingOperator;
import SDMLanguage.patterns.BindingSemantics;

public class SetColor extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1, Variable var_2) {
		String bindingStates = getBindingStates(var_0, var_1, var_2);

		BindingOperator op = (BindingOperator) var_1.getValue();
		BindingSemantics negative = (BindingSemantics) var_2.getValue();

		switch (bindingStates) {
		case "FBB":
			if (negative.equals(BindingSemantics.NEGATIVE)) {
				var_0.bindToValue(DotColor.BLUE);
				setSatisfied(true);
				return;
			}

			switch (op) {
			case CHECK_ONLY:
				var_0.bindToValue(DotColor.BLACK);
				setSatisfied(true);
				return;

			case CREATE:
				var_0.bindToValue(DotColor.GREEN);
				setSatisfied(true);
				return;

			case DESTROY:
				var_0.bindToValue(DotColor.RED);
				setSatisfied(true);
				return;

			default:
				break;
			}
		case "BBB":
			if (negative.equals(BindingSemantics.NEGATIVE)) {
				setSatisfied(var_0.getValue().equals(DotColor.BLUE));
				return;
			}

			switch (op) {
			case CHECK_ONLY:
				setSatisfied(var_0.getValue().equals(DotColor.BLACK));
				return;

			case CREATE:
				setSatisfied(var_0.getValue().equals(DotColor.GREEN));
				return;

			case DESTROY:
				setSatisfied(var_0.getValue().equals(DotColor.RED));
				return;

			default:
				break;
			}
		case "FFF":
			var_0.bindToValue(DotColor.GREEN);
			var_1.bindToValue(BindingOperator.CREATE);
			var_2.bindToValue(BindingSemantics.MANDATORY);
			setSatisfied(true);
			return;

		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}
}