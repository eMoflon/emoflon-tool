package org.moflon.tgg.mosl.codeadapter.csp.constraints;

import org.apache.commons.lang3.StringUtils;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class IsReallyEnum extends TGGConstraintImpl {
	public void solve(Variable var_0) {
		String bindingStates = getBindingStates(var_0);

		switch (bindingStates) {
		case "F":
			// Nothing to check
			setSatisfied(true);
			return;
		case "B":
			setSatisfied(isEnum(var_0.getValue()));
			return;
		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}

	protected boolean isEnum(Object value) {
		if (!(value instanceof String))
			return false;

		String valueAsString = String.class.cast(value).trim();

		if (!valueAsString.contains("."))
			return false;

		if (valueAsString.startsWith("\""))
			return false;

		if (StringUtils.isNumeric(valueAsString))
			return false;

		return true;
	}
}