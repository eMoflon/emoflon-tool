package org.moflon.tgg.csp.constraints;

import org.moflon.tgg.csp.constraints.generator.Generator;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class Add extends TGGConstraintImpl {

	/**
	 * Constraint add(a,b,c) a + b = c
	 * 
	 * @see TGGLanguage.csp.impl.TGGConstraintImpl#solve()
	 */
	public void solve(Variable a, Variable b, Variable c) {
		String bindingStates = getBindingStates(a, b, c);

		if (bindingStates.equals("BBB")) {
			// a + b == c
			setSatisfied((((Number) a.getValue()).doubleValue()
					+ ((Number) b.getValue()).doubleValue()) == ((Number) c.getValue()).doubleValue());
		} else if (bindingStates.equals("FBB")) {
			// a = c - b
			a.bindToValue(((Number) c.getValue()).doubleValue() - ((Number) b.getValue()).doubleValue());
			setSatisfied(true);
		} else if (bindingStates.equals("BFB")) {
			// b = c - a
			b.bindToValue(((Number) c.getValue()).doubleValue() - ((Number) a.getValue()).doubleValue());
			setSatisfied(true);
		} else if (bindingStates.equals("BBF")) {
			// c = a + b
			c.bindToValue(((Number) a.getValue()).doubleValue() + ((Number) b.getValue()).doubleValue());
			setSatisfied(true);
		}
		// modelgen implementations
		else if (bindingStates.equals("FFB")) {
			setSatisfied(true);
			int newNumber = Generator.getNewUniqueNumber();
			a.bindToValue(newNumber);
			b.bindToValue(((Number) a.getValue()).doubleValue() + ((Number) c.getValue()).doubleValue());
		} else if (bindingStates.equals("BFF")) {
			setSatisfied(true);
			long newNumber = Generator.getNewUniqueNumber();
			b.bindToValue(newNumber);
			c.bindToValue(((Number) a.getValue()).doubleValue() + ((Number) b.getValue()).doubleValue());
		} else if (bindingStates.equals("FBF")) {
			setSatisfied(true);
			long newNumber = Generator.getNewUniqueNumber();
			a.bindToValue(newNumber);
			c.bindToValue(((Number) a.getValue()).doubleValue() + ((Number) b.getValue()).doubleValue());
		} else {
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}

}
