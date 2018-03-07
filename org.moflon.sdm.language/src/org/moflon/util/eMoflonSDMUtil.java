package org.moflon.util;

import java.util.Optional;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.moflon.core.utilities.MoflonUtil;
import org.moflon.core.utilities.eMoflonEMFUtil;

import SDMLanguage.NamedElement;
import SDMLanguage.activities.Activity;
import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.calls.ParameterBinding;
import SDMLanguage.calls.callExpressions.MethodCallExpression;
import SDMLanguage.calls.callExpressions.ParameterExpression;
import SDMLanguage.expressions.ComparingOperator;
import SDMLanguage.expressions.ComparisonExpression;
import SDMLanguage.expressions.Expression;
import SDMLanguage.expressions.LiteralExpression;
import SDMLanguage.expressions.TextualExpression;
import SDMLanguage.patterns.AttributeAssignment;
import SDMLanguage.patterns.Constraint;
import SDMLanguage.patterns.patternExpressions.AttributeValueExpression;
import SDMLanguage.patterns.patternExpressions.ObjectVariableExpression;

public class eMoflonSDMUtil {
	/**
	 * Convenience method to retrieve the SDM diagram (as Activity from SDMLanguage)
	 * of an operation, whose implementation was specified by an SDM.
	 * 
	 * @param op
	 *            EOperation that is actually a MoflonOperation
	 * 
	 * @return EObject representation of the Activity (from SDMLanguage)
	 */
	public static Activity getActivityFromEOperation(final EOperation op) {
		if (op instanceof MoflonEOperation) {
			final MoflonEOperation moflonEOperation = (MoflonEOperation) op;
			return moflonEOperation.getActivity();
		}

		return null;
	}

	/**
	 * Used to delete an Activity (root of an SDM) if the operation is a
	 * MoflonOperation
	 *
	 * @param op
	 *            Operation whose Activity is deleted
	 */
	public static void deleteActivity(final EOperation op) {
		if (op instanceof MoflonEOperation) {
			final MoflonEOperation moflonEOperation = (MoflonEOperation) op;
			moflonEOperation.setActivity(null);
			return;
		}
	}

	// Methods for visualizing parts of SDMs

	public static String extractMethodCall(final MethodCallExpression methodCall, final String projectName) {
		String receiver = "";

		// Handle target object on which method is called
		Expression target = methodCall.getTarget();
		if (target instanceof TextualExpression)
			receiver = ((TextualExpression) target).getExpressionText();
		else if (target instanceof ObjectVariableExpression)
			receiver = ((ObjectVariableExpression) target).getObject().getName();
		else if (target instanceof ParameterExpression) {
			EParameter parameter = ((ParameterExpression) target).getParameter();
			receiver = parameter.getName();
		} else
			throw new UnsupportedOperationException("Target can only be textual or an object variable: " + target);

		// Handle method call
		String methodName = "";
		EOperation callee = methodCall.getCallee();
		if (callee != null)
			methodName = callee.getName();

		// Handle list of arguments
		String argumentList = "";
		for (EParameter param : methodCall.getCallee().getEParameters()) {
			Optional<ParameterBinding> bindingOptional = methodCall.getOwnedParameterBindings().stream()
					.filter(pb -> pb.getParameter().equals(param)).findAny();
			if (bindingOptional.isPresent()) {
				ParameterBinding binding = bindingOptional.get();
				argumentList = fillArgumentListWithBinding(projectName, argumentList, binding);
			} else {
				argumentList = handleDirtyMethodCall(projectName, methodCall);
				break;
			}
		}

		// Remove trailing ","
		argumentList = argumentList.compareTo("") == 0 ? "" : argumentList.substring(0, argumentList.length() - 1);

		return receiver + "." + methodName + "(" + argumentList + ")";
	}

	private static String handleDirtyMethodCall(String projectName, MethodCallExpression methodCall) {
		String argumentList = "";
		for (ParameterBinding binding : methodCall.getOwnedParameterBindings())
			argumentList = fillArgumentListWithBinding(projectName, argumentList, binding);

		return argumentList;
	}

	private static String fillArgumentListWithBinding(final String projectName, String argumentList,
			ParameterBinding binding) {
		Expression argumentExp = binding.getValueExpression();
		if (argumentExp instanceof TextualExpression) {
			argumentList += ((TextualExpression) argumentExp).getExpressionText() + ",";
		} else if (argumentExp instanceof ObjectVariableExpression) {
			argumentList += ((ObjectVariableExpression) argumentExp).getObject().getName() + ",";
		} else if (argumentExp instanceof LiteralExpression) {
			argumentList += extractValueFromLiteralExpression((LiteralExpression) argumentExp) + ",";
		} else if (argumentExp instanceof MethodCallExpression) {
			argumentList += extractMethodCall((MethodCallExpression) argumentExp, projectName) + ",";
		} else if (argumentExp instanceof ParameterExpression) {
			argumentList += ((ParameterExpression) (argumentExp)).getParameter().getName() + ",";
		} else if (argumentExp instanceof AttributeValueExpression) {
			argumentList += extractAttributeValueExpression((AttributeValueExpression) argumentExp, projectName) + ",";
		} else
			throw new UnsupportedOperationException(
					"Unsupported value of parameter binding (can only be textual or an object variable): "
							+ argumentExp);
		return argumentList;
	}

	public static String extractValueFromLiteralExpression(final LiteralExpression literalExpression) {
		StringBuilder resultBuilder = new StringBuilder();
		if (literalExpression.getType() != null && literalExpression.getType().getName().equals("EString")) {
			String value = StringEscapeUtils.escapeJava(literalExpression.getValue());
			resultBuilder.append("\"");
			resultBuilder.append(value);
			resultBuilder.append("\"");
		} else {
			// All other EDataTypes or null, are just copied directly into the generated
			// code
			resultBuilder.append(literalExpression.getValue());
		}

		return resultBuilder.toString();
	}

	public static String extractAttributeValueExpression(final AttributeValueExpression attributeValueExpression,
			final String projectName) {
		EAttribute attr = attributeValueExpression.getAttribute();
		String attribute = attr.getName();
		String value = StringUtils.capitalize(attribute);
		String prefix = ".get" + value;

		// Special handling of boolean attributes
		if (attributeValueExpression.getAttribute().getEAttributeType().getName().equals("EBoolean"))
			prefix = MoflonUtil.handlePrefixForBooleanAttributes(projectName, attribute);

		return attributeValueExpression.getObject().getName() + prefix + "()";
	}

	public static String getFQN(EObject eObject) {
		if (eObject == null)
			return "";
		else if (eObject instanceof NamedElement)
			return getFQN(eObject.eContainer()) + ((NamedElement) eObject).getName() + ".";
		else if (eObject instanceof ENamedElement)
			return getFQN(eObject.eContainer()) + ((ENamedElement) eObject).getName() + ".";
		else
			return getFQN(eObject.eContainer()) + eMoflonEMFUtil.getIdentifier(eObject) + ".";
	}

	public static String extractExpression(Expression exp, String projectName) {
		if (exp instanceof AttributeValueExpression) {
			return extractAttributeValueExpression((AttributeValueExpression) exp, projectName);
		} else if (exp instanceof MethodCallExpression) {
			return extractMethodCall((MethodCallExpression) exp, projectName);
		} else if (exp instanceof LiteralExpression) {
			return extractValueFromLiteralExpression((LiteralExpression) exp);
		} else if (exp instanceof ObjectVariableExpression) {
			return ((ObjectVariableExpression) exp).getObject().getName();
		} else if (exp instanceof ParameterExpression) {
			return ((ParameterExpression) exp).getParameter().getName();
		} else if (exp instanceof TextualExpression) {
			return ((TextualExpression) exp).getExpressionText();
		} else if (exp == null) {
			return "null";
		}

		throw new UnsupportedOperationException("Expression: " + exp + " is currently not supported");
	}

	public static String extractConstraint(Constraint constraint) {
		ComparisonExpression cmpExp = (ComparisonExpression) constraint.getConstraintExpression();
		AttributeValueExpression leftAttribute = (AttributeValueExpression) cmpExp.getLeftExpression();
		Expression rightExpression = cmpExp.getRightExpression();
		return leftAttribute.getAttribute().getName() + " " + extractComparingOperator(cmpExp.getOperator()) + " "
				+ eMoflonSDMUtil.extractExpression(rightExpression, "");
	}

	public static String extractAttributeAssignment(AttributeAssignment attributeAssignment) {
		return attributeAssignment.getAttribute().getName() + " := "
				+ eMoflonSDMUtil.extractExpression(attributeAssignment.getValueExpression(), "");
	}

	public static String extractComparingOperator(ComparingOperator op) {
		if (op == ComparingOperator.EQUAL) {
			return "==";
		} else if (op == ComparingOperator.GREATER) {
			return ">";
		} else if (op == ComparingOperator.LESS) {
			return "<";
		} else if (op == ComparingOperator.GREATER_OR_EQUAL) {
			return ">=";
		} else if (op == ComparingOperator.LESS_OR_EQUAL) {
			return "<=";
		} else if (op == ComparingOperator.UNEQUAL) {
			return "!=";
		}

		return "";
	}

}
