package org.moflon.tgg.csp.constraints;

import org.moflon.tgg.csp.constraints.generator.Generator;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class Eq extends TGGConstraintImpl {

	/**
	 * Constraint eq(a,b)
	 * 
	 * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
	 */
	public void solve(Variable a, Variable b) {
		String bindingStates = getBindingStates(a, b);

		if (bindingStates.equals("BB")) {
			if (a.getValue() instanceof Number)
				compareNumbers(a, b);
			else
				setSatisfied(a.getValue().equals(b.getValue()));
		} else if (bindingStates.equals("BF")) {
			b.setValue(a.getValue());
			b.setBound(true);
			setSatisfied(true);
		} else if (bindingStates.equals("FB")) {
			a.setValue(b.getValue());
			a.setBound(true);
			setSatisfied(true);

		}
		// modelgen implementation
		else if (bindingStates.equals("FF")) {
			String type = a.getType();
			Object value = generateValue(type);
			a.bindToValue(value);
			b.bindToValue(value);
			setSatisfied(true);
		} else {
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);

		}
	}

	private Object generateValue(String type) {
		Object value = null;

		if (type.equals("String")) {
			value = Generator.getNewRandomString(null);
		} else if (type.equals("Integer")) {
			value = Integer.valueOf((int) (Math.random() * 1000.0));
		} else if (type.equals("Double")) {
			value = Double.valueOf((Math.random() * 1000.0));
		} else if (type.equals("Boolean")) {
			value = Boolean.valueOf((Math.random() > Math.random()));
		} else
			throw new RuntimeException(
					"The type " + type + " is not supported for random value generation in Eq-constraints");
		return value;
	}

	private void compareNumbers(Variable a, Variable b) {
		Number anum = (Number) a.getValue();
		Number bnum = (Number) b.getValue();
		setSatisfied(anum.doubleValue() == bnum.doubleValue());
	}

}
