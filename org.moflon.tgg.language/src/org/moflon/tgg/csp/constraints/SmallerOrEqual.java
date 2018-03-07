package org.moflon.tgg.csp.constraints;

import org.moflon.tgg.csp.constraints.generator.Generator;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class SmallerOrEqual extends TGGConstraintImpl {

	public void solve(Variable a, Variable b) {
		String bindingStates = getBindingStates(a, b);

		if (bindingStates.equals("BB")) {
			setSatisfied(((Number) a.getValue()).doubleValue() <= ((Number) b.getValue()).doubleValue());
		} else if (bindingStates.equals("BF")) {
			b.setValue(a.getValue());
			b.setBound(true);
			setSatisfied(true);
		} else if (bindingStates.equals("FB")) {
			a.setValue(b.getValue());
			a.setBound(true);
			setSatisfied(true);
		}
		// modelgen implementations
		else if (bindingStates.equals("FF")) {
			int number = Generator.getNewUniqueNumber();
			a.bindToValue(number);
			b.bindToValue(number);
			setSatisfied(true);
		} else {
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}

}
