package org.moflon.compiler.sdm.democles.stringtemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.GeneratorVariable;
import org.gervarro.democles.codegen.stringtemplate.ParameterHandler;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.specification.impl.Variable;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;

public class PatternMatcherModelAdaptor extends ParameterHandler {

	public synchronized Object getProperty(Interpreter interpreter, ST template, Object object, Object property,
			String propertyName) {
		if (object instanceof GeneratorOperation) {
			if ("freeParameters".equals(propertyName)) {
				GeneratorOperation operation = (GeneratorOperation) object;
				List<GeneratorVariable> freeParameters = new LinkedList<>();
				Adornment precondition = operation.getPrecondition();
				for (int i = 0; i < precondition.size(); i++) {
					if (precondition.get(i) == Adornment.FREE) {
						freeParameters.add(operation.getParameters().get(i));
					}
				}
				return freeParameters;
			} else if ("boundParameters".equals(propertyName)) {
				GeneratorOperation operation = (GeneratorOperation) object;
				List<GeneratorVariable> boundParameters = new LinkedList<>();
				Adornment precondition = operation.getPrecondition();
				for (int i = 0; i < precondition.size(); i++) {
					if (precondition.get(i) == Adornment.BOUND) {
						boundParameters.add(operation.getParameters().get(i));
					}
				}
				return boundParameters;
			}
		}
		Object result = super.getProperty(interpreter, template, object, property, propertyName);
		if (object instanceof Variable && "name".equals(propertyName)) {
			if (result instanceof String) {
				return getJavaSafeVariable((String) result);
			}
		}
		return result;
	}

	public String toString(Object o, String formatString, Locale locale) {
		if (o instanceof Variable) {
			return getJavaSafeVariable(super.toString((Variable) o, formatString, locale));
		}
		return super.toString(o, formatString, locale);
	}

	private final String getJavaSafeVariable(String name) {
		if ("this".equals(name)) {
			return "_this";
		} else {
			return name;
		}
	}
}
