package org.moflon.tgg.csp.constraints;

import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class Divide extends TGGConstraintImpl {

	/**
	 * Constraint divide(a,b,c) a / b = c
	 * 
	 * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
	 */
	public void solve(Variable a, Variable b, Variable c) {
		String bindingStates = getBindingStates(a, b, c);

		if (bindingStates.equals("BBB")) {
			setSatisfied((((Number) a.getValue()).doubleValue()
					/ ((Number) b.getValue()).doubleValue()) == ((Number) c.getValue()).doubleValue());
		} else if (bindingStates.equals("FBB")) {
			a.setValue(((Number) c.getValue()).doubleValue() * ((Number) b.getValue()).doubleValue());
			a.setBound(true);
			setSatisfied(true);
		} else if (bindingStates.equals("BFB")) {
			b.setValue(((Number) a.getValue()).doubleValue() / ((Number) c.getValue()).doubleValue());
			b.setBound(true);
			setSatisfied(true);
		} else if (bindingStates.equals("BBF")) {
			c.setValue(((Number) a.getValue()).doubleValue() / ((Number) b.getValue()).doubleValue());
			c.setBound(true);
			setSatisfied(true);
		}

	}

}
