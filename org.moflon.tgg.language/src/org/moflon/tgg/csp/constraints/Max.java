package org.moflon.tgg.csp.constraints;

import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class Max extends TGGConstraintImpl {

	/**
	 * Constraint max(a,b,c) c = max(a,b)
	 * 
	 * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
	 */
	public void solve(Variable a, Variable b, Variable maximum) {
		String bindingStates = getBindingStates(a, b, maximum);

		// check
		if (bindingStates.equals("BBB")) {
			setSatisfied(maximum.getValue().equals(max(a, b)));
		}
		// maximum := max(a,b)
		else if (bindingStates.equals("BBF")) {
			maximum.setValue(max(a, b));
			maximum.setBound(true);
			setSatisfied(true);
		}
		// maximum = max(a,b) - check if a=maximum and set b to 0...
		else if (bindingStates.equals("BFB")) {
			// is a the maximum?
			if (a.getValue().equals(maximum.getValue())) {
				b.setValue(0);
				b.setBound(true);
				setSatisfied(true);
			}

		}
	}

	private Number max(Variable a, Variable b) {
		return Math.max(((Number) a.getValue()).doubleValue(), ((Number) b.getValue()).doubleValue());
	}

}
