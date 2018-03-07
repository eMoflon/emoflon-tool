package org.moflon.ide.visualization.dot.tgg.csp.constraints;

import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

import SDMLanguage.expressions.ComparingOperator;

public class RecordEntryFromConstraint extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1, Variable var_2, Variable var_3) {
		String bindingStates = getBindingStates(var_0, var_1, var_2, var_3);

		switch (bindingStates) {
		case "FBBB": {
			ComparingOperator op = (ComparingOperator) var_1.getValue();
			String literal = (String) var_2.getValue();
			String attrName = (String) var_3.getValue();

			var_0.bindToValue(expectedConstraint(op, literal, attrName));
			setSatisfied(true);
			break;
		}
		case "BBBB": {
			ComparingOperator op = (ComparingOperator) var_1.getValue();
			String literal = (String) var_2.getValue();
			String attrName = (String) var_3.getValue();
			String constr = (String) var_0.getValue();

			setSatisfied(constr.equals(expectedConstraint(op, literal, attrName)));
			break;
		}
		case "FFFB":
			String attrName = (String) var_3.getValue();
			var_1.bindToValue(ComparingOperator.EQUAL);
			var_2.bindToValue("foo");
			var_0.bindToValue(
					expectedConstraint((ComparingOperator) var_1.getValue(), (String) var_2.getValue(), attrName));

			setSatisfied(true);
			break;

		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}

	private String expectedConstraint(ComparingOperator op, String literal, String attrName) {
		return attrName + " " + getLiteral(op) + " " + literal.replace("\"", "\\\"");
	}

	private String getLiteral(ComparingOperator op) {
		switch (op) {
		case EQUAL:
			return "==";
		case UNEQUAL:
			return "!=";
		case GREATER_OR_EQUAL:
			return "\\>=";
		case GREATER:
			return "\\>";
		case LESS:
			return "\\<";
		case LESS_OR_EQUAL:
			return "\\<=";
		default:
			return "unknown operator!";
		}
	}
}