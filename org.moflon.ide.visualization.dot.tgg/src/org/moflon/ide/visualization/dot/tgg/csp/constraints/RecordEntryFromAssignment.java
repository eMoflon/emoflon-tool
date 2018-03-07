package org.moflon.ide.visualization.dot.tgg.csp.constraints;

import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class RecordEntryFromAssignment extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1, Variable var_2) {
		String bindingStates = getBindingStates(var_0, var_1, var_2);

		switch (bindingStates) {
		case "FBB": {
			String attrName = (String) var_1.getValue();
			String literal = (String) var_2.getValue();

			var_0.bindToValue(expectedAssignment(attrName, literal));
			setSatisfied(true);
			break;
		}
		case "BBB": {
			String attrName = (String) var_1.getValue();
			String literal = (String) var_2.getValue();
			String assgn = (String) var_0.getValue();

			setSatisfied(assgn.equals(expectedAssignment(attrName, literal)));
			break;
		}
		case "FBF":
			String attrName = (String) var_1.getValue();
			var_2.bindToValue("foo");
			var_0.bindToValue(expectedAssignment(attrName, (String) var_2.getValue()));
			setSatisfied(true);
			break;

		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}

	private String expectedAssignment(String attrName, String literal) {
		return attrName + " := " + literal.replace("\"", "\\\"");
	}
}