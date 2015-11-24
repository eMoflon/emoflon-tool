/**
 */
package org.moflon.sdm.constraints.constraintstodemocles.impl;

import SDMLanguage.patterns.AttributeConstraints.AssignmentConstraint;
import SDMLanguage.patterns.AttributeConstraints.AttributeConstraint;
import SDMLanguage.patterns.AttributeConstraints.AttributeConstraintVariable;
import SDMLanguage.patterns.AttributeConstraints.PrimitiveVariable;

import SDMLanguage.patterns.ObjectVariable;
import SDMLanguage.patterns.StoryPattern;

import SDMLanguageToDemocles.impl.GreenPatternTransformerImpl;

import java.lang.Iterable;

import java.lang.reflect.InvocationTargetException;

import java.util.LinkedList;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;

import org.gervarro.democles.specification.emf.ConstraintParameter;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.Variable;

import org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer;
import org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesPackage;

import org.moflon.sdm.constraints.democles.AttributeValueConstraint;
import org.moflon.sdm.constraints.democles.DemoclesFactory;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Constraint Green Pattern Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class AttributeConstraintGreenPatternTransformerImpl extends GreenPatternTransformerImpl
		implements AttributeConstraintGreenPatternTransformer {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeConstraintGreenPatternTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConstraintstodemoclesPackage.Literals.ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void postprocess(StoryPattern storyPattern, Pattern pattern) {
		// ActivityNode18
		Object[] result1_black = AttributeConstraintGreenPatternTransformerImpl
				.pattern_AttributeConstraintGreenPatternTransformer_0_1_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [ActivityNode18] failed." + " Variables: " + "[this] = " + this + ".");
		}
		// ActivityNode19
		AttributeConstraintGreenPatternTransformerImpl
				.pattern_AttributeConstraintGreenPatternTransformer_0_2_expressionBBB(this, storyPattern, pattern);
		// ForEach ActivityNode21
		for (Object[] result3_black : AttributeConstraintGreenPatternTransformerImpl
				.pattern_AttributeConstraintGreenPatternTransformer_0_3_blackFB(storyPattern)) {
			AssignmentConstraint assignmentConstraint = (AssignmentConstraint) result3_black[0];
			// ActivityNode20
			AttributeConstraintGreenPatternTransformerImpl
					.pattern_AttributeConstraintGreenPatternTransformer_0_4_expressionBBB(this, assignmentConstraint,
							pattern);

		}
		return;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void callSuperPostprocess(StoryPattern storyPattern, Pattern pattern) {
		// [user code injected with eMoflon]
		super.postprocess(storyPattern, pattern);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void processAssignmentConstraint(AssignmentConstraint assignmentConstraint, Pattern pattern) {
		// ActivityNode11
		Object[] result1_black = AttributeConstraintGreenPatternTransformerImpl
				.pattern_AttributeConstraintGreenPatternTransformer_2_1_blackBBFFF(assignmentConstraint, this);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [ActivityNode11] failed." + " Variables: "
					+ "[assignmentConstraint] = " + assignmentConstraint + ", " + "[this] = " + this + ".");
		}
		EClassifier variableType = (EClassifier) result1_black[2];
		PrimitiveVariable primitiveVariable = (PrimitiveVariable) result1_black[3];
		ObjectVariable objectVariable = (ObjectVariable) result1_black[4];

		// lookupLeftInSymbolicParameters
		Object[] result2_bindingAndBlack = AttributeConstraintGreenPatternTransformerImpl
				.pattern_AttributeConstraintGreenPatternTransformer_2_2_bindingAndBlackFFFBBFBBBB(assignmentConstraint,
						pattern, this, objectVariable, variableType, primitiveVariable);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [lookupLeftInSymbolicParameters] failed."
					+ " Variables: " + "[assignmentConstraint] = " + assignmentConstraint + ", " + "[pattern] = "
					+ pattern + ", " + "[this] = " + this + ", " + "[objectVariable] = " + objectVariable + ", "
					+ "[variableType] = " + variableType + ", " + "[primitiveVariable] = " + primitiveVariable + ".");
		}
		Variable leftVariable = (Variable) result2_bindingAndBlack[0];
		PatternBody patternBody = (PatternBody) result2_bindingAndBlack[1];
		EAttribute eAttribute = (EAttribute) result2_bindingAndBlack[2];
		Variable right = (Variable) result2_bindingAndBlack[5];
		Object[] result2_green = AttributeConstraintGreenPatternTransformerImpl
				.pattern_AttributeConstraintGreenPatternTransformer_2_2_greenFBB(patternBody, eAttribute);
		AttributeValueConstraint attributePredicate = (AttributeValueConstraint) result2_green[0];

		// handleLeftVariable
		Object[] result3_black = AttributeConstraintGreenPatternTransformerImpl
				.pattern_AttributeConstraintGreenPatternTransformer_2_3_blackBB(leftVariable, attributePredicate);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [handleLeftVariable] failed." + " Variables: "
					+ "[leftVariable] = " + leftVariable + ", " + "[attributePredicate] = " + attributePredicate + ".");
		}
		AttributeConstraintGreenPatternTransformerImpl
				.pattern_AttributeConstraintGreenPatternTransformer_2_3_greenBBF(leftVariable, attributePredicate);
				// ConstraintParameter leftConstraintParameter = (ConstraintParameter) result3_green[2];

		// handleRightVariable
		Object[] result4_black = AttributeConstraintGreenPatternTransformerImpl
				.pattern_AttributeConstraintGreenPatternTransformer_2_4_blackBB(attributePredicate, right);
		if (result4_black == null) {
			throw new RuntimeException("Pattern matching in node [handleRightVariable] failed." + " Variables: "
					+ "[attributePredicate] = " + attributePredicate + ", " + "[right] = " + right + ".");
		}
		AttributeConstraintGreenPatternTransformerImpl
				.pattern_AttributeConstraintGreenPatternTransformer_2_4_greenFBB(attributePredicate, right);
		// ConstraintParameter rightConstraintParameter = (ConstraintParameter) result4_green[0];

		return;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable lookupVariableByName(String variableName, Pattern pattern) {// ActivityNode13
		Object[] result1_black = AttributeConstraintGreenPatternTransformerImpl
				.pattern_AttributeConstraintGreenPatternTransformer_3_1_blackBFB(pattern, variableName);
		if (result1_black != null) {
			Variable variable = (Variable) result1_black[1];
			return AttributeConstraintGreenPatternTransformerImpl
					.pattern_AttributeConstraintGreenPatternTransformer_3_2_expressionFB(variable);
		} else {
			// ActivityNode14
			Object[] result3_black = AttributeConstraintGreenPatternTransformerImpl
					.pattern_AttributeConstraintGreenPatternTransformer_3_3_blackFFBB(pattern, variableName);
			if (result3_black != null) {
				// PatternBody patternBody = (PatternBody) result3_black[0];
				Variable variable2 = (Variable) result3_black[1];
				return AttributeConstraintGreenPatternTransformerImpl
						.pattern_AttributeConstraintGreenPatternTransformer_3_4_expressionFB(variable2);
			} else {
				return AttributeConstraintGreenPatternTransformerImpl
						.pattern_AttributeConstraintGreenPatternTransformer_3_5_expressionF();
			}

		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVariableToAdd(ObjectVariable objectVariable) {
		// ActivityNode23
		Object[] result1_black = AttributeConstraintGreenPatternTransformerImpl
				.pattern_AttributeConstraintGreenPatternTransformer_4_1_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [ActivityNode23] failed." + " Variables: " + "[this] = " + this + ".");
		}
		// ActivityNode22
		if (AttributeConstraintGreenPatternTransformerImpl
				.pattern_AttributeConstraintGreenPatternTransformer_4_2_expressionFBB(this, objectVariable)) {
			return AttributeConstraintGreenPatternTransformerImpl
					.pattern_AttributeConstraintGreenPatternTransformer_4_3_expressionF();
		} else {
			// ActivityNode24
			Object[] result4_black = AttributeConstraintGreenPatternTransformerImpl
					.pattern_AttributeConstraintGreenPatternTransformer_4_4_blackFBF(objectVariable);
			if (result4_black != null) {
				// StoryPattern storyPattern = (StoryPattern) result4_black[0];
				// AssignmentConstraint assignmentConstraint = (AssignmentConstraint) result4_black[2];
				return AttributeConstraintGreenPatternTransformerImpl
						.pattern_AttributeConstraintGreenPatternTransformer_4_5_expressionF();
			} else {
				return AttributeConstraintGreenPatternTransformerImpl
						.pattern_AttributeConstraintGreenPatternTransformer_4_6_expressionF();
			}

		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean callSuperIsVariableToAdd(ObjectVariable objectVariable) {
		// [user code injected with eMoflon]
		return super.isVariableToAdd(objectVariable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___POSTPROCESS__STORYPATTERN_PATTERN:
			postprocess((StoryPattern) arguments.get(0), (Pattern) arguments.get(1));
			return null;
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___CALL_SUPER_POSTPROCESS__STORYPATTERN_PATTERN:
			callSuperPostprocess((StoryPattern) arguments.get(0), (Pattern) arguments.get(1));
			return null;
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___PROCESS_ASSIGNMENT_CONSTRAINT__ASSIGNMENTCONSTRAINT_PATTERN:
			processAssignmentConstraint((AssignmentConstraint) arguments.get(0), (Pattern) arguments.get(1));
			return null;
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___LOOKUP_VARIABLE_BY_NAME__STRING_PATTERN:
			return lookupVariableByName((String) arguments.get(0), (Pattern) arguments.get(1));
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___IS_VARIABLE_TO_ADD__OBJECTVARIABLE:
			return isVariableToAdd((ObjectVariable) arguments.get(0));
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___CALL_SUPER_IS_VARIABLE_TO_ADD__OBJECTVARIABLE:
			return callSuperIsVariableToAdd((ObjectVariable) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_0_1_blackB(
			AttributeConstraintGreenPatternTransformer _this) {
		return new Object[] { _this };
	}

	public static final void pattern_AttributeConstraintGreenPatternTransformer_0_2_expressionBBB(
			AttributeConstraintGreenPatternTransformer _this, StoryPattern storyPattern, Pattern pattern) {
		_this.callSuperPostprocess(storyPattern, pattern);

	}

	public static final Iterable<Object[]> pattern_AttributeConstraintGreenPatternTransformer_0_3_blackFB(
			StoryPattern storyPattern) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (AttributeConstraint tmpAssignmentConstraint : storyPattern.getConstraints()) {
			if (tmpAssignmentConstraint instanceof AssignmentConstraint) {
				AssignmentConstraint assignmentConstraint = (AssignmentConstraint) tmpAssignmentConstraint;
				_result.add(new Object[] { assignmentConstraint, storyPattern });
			}
		}
		return _result;
	}

	public static final void pattern_AttributeConstraintGreenPatternTransformer_0_4_expressionBBB(
			AttributeConstraintGreenPatternTransformer _this, AssignmentConstraint assignmentConstraint,
			Pattern pattern) {
		_this.processAssignmentConstraint(assignmentConstraint, pattern);

	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_2_1_blackBBFFF(
			AssignmentConstraint assignmentConstraint, AttributeConstraintGreenPatternTransformer _this) {
		AttributeConstraintVariable tmpPrimitiveVariable = assignmentConstraint.getParameter();
		if (tmpPrimitiveVariable instanceof PrimitiveVariable) {
			PrimitiveVariable primitiveVariable = (PrimitiveVariable) tmpPrimitiveVariable;
			ObjectVariable objectVariable = assignmentConstraint.getObjectVariable();
			if (objectVariable != null) {
				EClassifier variableType = objectVariable.getType();
				if (variableType != null) {
					return new Object[] { assignmentConstraint, _this, variableType, primitiveVariable,
							objectVariable };
				}

			}

		}

		return null;
	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_2_2_bindingFFBBBBB(
			AttributeConstraintGreenPatternTransformer _this, Pattern pattern, ObjectVariable objectVariable,
			EClassifier variableType, PrimitiveVariable primitiveVariable) {
		String objectVariable_name = objectVariable.getName();
		Variable _localVariable_0 = _this.lookupVariableInPattern(pattern, objectVariable_name, variableType);
		Variable leftVariable = _localVariable_0;
		if (leftVariable != null) {
			String primitiveVariable_name = primitiveVariable.getName();
			Variable _localVariable_1 = _this.lookupVariableByName(primitiveVariable_name, pattern);
			Variable right = _localVariable_1;
			if (right != null) {
				return new Object[] { leftVariable, right, _this, pattern, objectVariable, variableType,
						primitiveVariable };
			}

		}

		return null;
	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_2_2_blackBFFBBB(
			Variable leftVariable, AssignmentConstraint assignmentConstraint, Pattern pattern, Variable right) {
		if (!leftVariable.equals(right)) {
			EAttribute eAttribute = assignmentConstraint.getType();
			if (eAttribute != null) {
				for (PatternBody patternBody : pattern.getBodies()) {
					return new Object[] { leftVariable, patternBody, eAttribute, assignmentConstraint, pattern, right };
				}
			}

		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_2_2_bindingAndBlackFFFBBFBBBB(
			AssignmentConstraint assignmentConstraint, Pattern pattern,
			AttributeConstraintGreenPatternTransformer _this, ObjectVariable objectVariable, EClassifier variableType,
			PrimitiveVariable primitiveVariable) {
		Object[] result_pattern_AttributeConstraintGreenPatternTransformer_2_2_binding = pattern_AttributeConstraintGreenPatternTransformer_2_2_bindingFFBBBBB(
				_this, pattern, objectVariable, variableType, primitiveVariable);
		if (result_pattern_AttributeConstraintGreenPatternTransformer_2_2_binding != null) {
			Variable leftVariable = (Variable) result_pattern_AttributeConstraintGreenPatternTransformer_2_2_binding[0];
			Variable right = (Variable) result_pattern_AttributeConstraintGreenPatternTransformer_2_2_binding[1];

			Object[] result_pattern_AttributeConstraintGreenPatternTransformer_2_2_black = pattern_AttributeConstraintGreenPatternTransformer_2_2_blackBFFBBB(
					leftVariable, assignmentConstraint, pattern, right);
			if (result_pattern_AttributeConstraintGreenPatternTransformer_2_2_black != null) {
				PatternBody patternBody = (PatternBody) result_pattern_AttributeConstraintGreenPatternTransformer_2_2_black[1];
				EAttribute eAttribute = (EAttribute) result_pattern_AttributeConstraintGreenPatternTransformer_2_2_black[2];

				return new Object[] { leftVariable, patternBody, eAttribute, assignmentConstraint, pattern, right,
						_this, objectVariable, variableType, primitiveVariable };
			}
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_2_2_greenFBB(
			PatternBody patternBody, EAttribute eAttribute) {
		AttributeValueConstraint attributePredicate = DemoclesFactory.eINSTANCE.createAttributeValueConstraint();
		attributePredicate.setEModelElement(eAttribute);
		patternBody.getConstraints().add(attributePredicate);
		return new Object[] { attributePredicate, patternBody, eAttribute };
	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_2_3_blackBB(Variable leftVariable,
			AttributeValueConstraint attributePredicate) {
		return new Object[] { leftVariable, attributePredicate };
	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_2_3_greenBBF(Variable leftVariable,
			AttributeValueConstraint attributePredicate) {
		ConstraintParameter leftConstraintParameter = SpecificationFactory.eINSTANCE.createConstraintParameter();
		attributePredicate.getParameters().add(leftConstraintParameter);
		leftConstraintParameter.setReference(leftVariable);
		return new Object[] { leftVariable, attributePredicate, leftConstraintParameter };
	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_2_4_blackBB(
			AttributeValueConstraint attributePredicate, Variable right) {
		return new Object[] { attributePredicate, right };
	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_2_4_greenFBB(
			AttributeValueConstraint attributePredicate, Variable right) {
		ConstraintParameter rightConstraintParameter = SpecificationFactory.eINSTANCE.createConstraintParameter();
		rightConstraintParameter.setReference(right);
		attributePredicate.getParameters().add(rightConstraintParameter);
		return new Object[] { rightConstraintParameter, attributePredicate, right };
	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_3_1_blackBFB(Pattern pattern,
			String variableName) {
		for (Variable variable : pattern.getSymbolicParameters()) {
			String variable_name = variable.getName();
			if (variable_name.equals(variableName)) {
				return new Object[] { pattern, variable, variableName };
			}

		}
		return null;
	}

	public static final Variable pattern_AttributeConstraintGreenPatternTransformer_3_2_expressionFB(
			Variable variable) {
		Variable _result = variable;
		return _result;
	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_3_3_blackFFBB(Pattern pattern,
			String variableName) {
		for (PatternBody patternBody : pattern.getBodies()) {
			for (Variable variable2 : patternBody.getLocalVariables()) {
				String variable2_name = variable2.getName();
				if (variable2_name.equals(variableName)) {
					return new Object[] { patternBody, variable2, pattern, variableName };
				}

			}
		}
		return null;
	}

	public static final Variable pattern_AttributeConstraintGreenPatternTransformer_3_4_expressionFB(
			Variable variable2) {
		Variable _result = variable2;
		return _result;
	}

	public static final Variable pattern_AttributeConstraintGreenPatternTransformer_3_5_expressionF() {
		Variable _result = null;
		return _result;
	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_4_1_blackB(
			AttributeConstraintGreenPatternTransformer _this) {
		return new Object[] { _this };
	}

	public static final boolean pattern_AttributeConstraintGreenPatternTransformer_4_2_expressionFBB(
			AttributeConstraintGreenPatternTransformer _this, ObjectVariable objectVariable) {
		boolean _localVariable_0 = _this.callSuperIsVariableToAdd(objectVariable);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_AttributeConstraintGreenPatternTransformer_4_3_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final Object[] pattern_AttributeConstraintGreenPatternTransformer_4_4_blackFBF(
			ObjectVariable objectVariable) {
		StoryPattern storyPattern = objectVariable.getPattern();
		if (storyPattern != null) {
			for (AttributeConstraint tmpAssignmentConstraint : storyPattern.getConstraints()) {
				if (tmpAssignmentConstraint instanceof AssignmentConstraint) {
					AssignmentConstraint assignmentConstraint = (AssignmentConstraint) tmpAssignmentConstraint;
					if (objectVariable.equals(assignmentConstraint.getObjectVariable())) {
						return new Object[] { storyPattern, objectVariable, assignmentConstraint };
					}
				}
			}
		}

		return null;
	}

	public static final boolean pattern_AttributeConstraintGreenPatternTransformer_4_5_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_AttributeConstraintGreenPatternTransformer_4_6_expressionF() {
		boolean _result = false;
		return _result;
	}

	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //AttributeConstraintGreenPatternTransformerImpl
