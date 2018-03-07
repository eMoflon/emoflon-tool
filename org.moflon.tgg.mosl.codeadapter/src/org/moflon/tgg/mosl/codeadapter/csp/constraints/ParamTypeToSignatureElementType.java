package org.moflon.tgg.mosl.codeadapter.csp.constraints;

import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class ParamTypeToSignatureElementType extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1) {
		String bindingStates = getBindingStates(var_0, var_1);

		String paramType = (String) var_0.getValue();
		String sigEleType = (String) var_1.getValue();

		switch (bindingStates) {
		case "BB":
			if (paramType == null && sigEleType == null)
				setSatisfied(true);
			else if (paramType == null)
				setSatisfied(sigEleType.equals(""));
			else
				setSatisfied(paramType.equals(sigEleType));
			break;

		case "BF":
			if (paramType == null)
				var_1.setValue("");
			else
				var_1.setValue(paramType);

			setSatisfied(true);
			break;

		case "FB":
			var_0.setValue(sigEleType);
			setSatisfied(true);
			break;

		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}
}