package org.moflon.tgg.csp;

import java.util.HashMap;

import org.moflon.tgg.language.csp.AttributeVariable;
import org.moflon.tgg.language.csp.CSP;
import org.moflon.tgg.language.csp.CspFactory;
import org.moflon.tgg.language.csp.Variable;

public class CSPFactoryHelper {
	public static CSPFactoryHelper eINSTANCE = new CSPFactoryHelper();

	private static HashMap<String, String> variableNamesToOVNames = new HashMap<String, String>();
	private static HashMap<String, String> variableNamesToAttrNames = new HashMap<String, String>();

	public Variable createVariable(String name, CSP csp) {
		Variable result;
		if (name.contains(".")) {
			AttributeVariable attrVariable = CspFactory.eINSTANCE.createAttributeVariable();
			int splitterIndex = -1;

			String ovName = variableNamesToOVNames.get(name);
			if (ovName == null) {
				splitterIndex = name.indexOf('.');
				ovName = name.substring(0, splitterIndex);
				variableNamesToOVNames.put(name, ovName);
			}

			String attrName = variableNamesToAttrNames.get(name);
			if (attrName == null) {
				attrName = name.substring(splitterIndex + 1, name.length());
				variableNamesToAttrNames.put(name, attrName);
			}

			attrVariable.setObjectVariable(ovName);
			attrVariable.setAttribute(attrName);
			result = attrVariable;
		} else {
			result = CspFactory.eINSTANCE.createVariable();
		}

		csp.getVariables().add(result);
		return result;
	}

	public Variable createVariable(String name, boolean bound, CSP csp) {
		Variable variable = createVariable(name, csp);
		variable.setBound(bound);
		return variable;
	}

}
