/**
 */
package org.moflon.sdm.constraints.constraintstodemocles.impl;

import SDMLanguage.patterns.AttributeConstraints.AttributeConstraint;
import SDMLanguage.patterns.AttributeConstraints.AttributeConstraintVariable;
import SDMLanguage.patterns.AttributeConstraints.AttributeLookupConstraint;
import SDMLanguage.patterns.AttributeConstraints.CspConstraint;
import SDMLanguage.patterns.AttributeConstraints.LiteralVariable;
import SDMLanguage.patterns.AttributeConstraints.PrimitiveVariable;

import SDMLanguage.patterns.ObjectVariable;
import SDMLanguage.patterns.StoryPattern;

import SDMLanguageToDemocles.impl.BlackAndNacPatternTransformerImpl;

import java.lang.Iterable;

import java.lang.reflect.InvocationTargetException;

import java.util.LinkedList;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.gervarro.democles.specification.emf.ConstraintParameter;
import org.gervarro.democles.specification.emf.ConstraintVariable;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.Variable;

import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypeFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable;

import org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer;
import org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesFactory;
import org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesPackage;
import org.moflon.sdm.constraints.constraintstodemocles.IdentifyerHelper;

import org.moflon.sdm.constraints.democles.AttributeValueConstraint;
import org.moflon.sdm.constraints.democles.AttributeVariableConstraint;
import org.moflon.sdm.constraints.democles.DemoclesFactory;
import org.moflon.sdm.constraints.democles.TypedConstant;

import org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary;
import org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup;
import org.moflon.sdm.constraints.operationspecification.OperationspecificationFactory;
import org.moflon.sdm.constraints.operationspecification.ParamIdentifier;
import org.moflon.sdm.constraints.operationspecification.ParameterType;
// <-- [user defined imports]
import org.moflon.sdm.constraints.operationspecification.ConstraintSpecification;
import DemoclesAttributeConstraintSpecification.constraint.util.AttributeConstraintLibUtil;
import ScopeValidation.ActionBuilder;
import org.moflon.sdm.compiler.democles.validation.result.ErrorMessage;
import org.moflon.sdm.compiler.democles.validation.result.Severity;
import org.moflon.sdm.compiler.democles.validation.result.ValidationReport;
import org.moflon.sdm.compiler.democles.validation.result.ResultFactory;
import org.moflon.sdm.constraints.constraintstodemocles.Errors;
import org.moflon.codegen.eclipse.CodeGeneratorPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.core.resources.IProject;
import org.moflon.sdm.constraints.operationspecification.OperationSpecification;
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Constraint Black And Nac Pattern Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class AttributeConstraintBlackAndNacPatternTransformerImpl extends BlackAndNacPatternTransformerImpl
		implements AttributeConstraintBlackAndNacPatternTransformer {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeConstraintBlackAndNacPatternTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConstraintstodemoclesPackage.Literals.ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void postprocess(StoryPattern storyPattern, Pattern pattern) {
		// lookupPatternTransformer
		Object[] result1_black = AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_0_1_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [lookupPatternTransformer] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		// performPostProcessingForBlackAndNacPatterns
		AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_0_2_expressionBBB(this, storyPattern,
						pattern);
		// ForEach forEachAttributeConstraintVariable
		for (Object[] result3_black : AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_0_3_blackBF(storyPattern)) {
			AttributeConstraintVariable attributeConstraintVariable = (AttributeConstraintVariable) result3_black[1];
			// isPrimitiveVariable
			Object[] result4_bindingAndBlack = AttributeConstraintBlackAndNacPatternTransformerImpl
					.pattern_AttributeConstraintBlackAndNacPatternTransformer_0_4_bindingAndBlackFB(
							attributeConstraintVariable);
			if (result4_bindingAndBlack != null) {
				PrimitiveVariable primitiveVariable = (PrimitiveVariable) result4_bindingAndBlack[0];
				// processPrimitiveVariable
				AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_0_5_expressionBBB(this,
								primitiveVariable, pattern);

			} else {
			}

		}
		// ForEach forEachAttributeConstraint
		for (Object[] result6_black : AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_0_6_blackFB(storyPattern)) {
			AttributeConstraint attributeConstraint = (AttributeConstraint) result6_black[0];
			// IsCspConstraint
			Object[] result7_bindingAndBlack = AttributeConstraintBlackAndNacPatternTransformerImpl
					.pattern_AttributeConstraintBlackAndNacPatternTransformer_0_7_bindingAndBlackFB(
							attributeConstraint);
			if (result7_bindingAndBlack != null) {
				CspConstraint cspConstraint = (CspConstraint) result7_bindingAndBlack[0];
				// processCspConstraint
				AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_0_8_expressionBBB(this, cspConstraint,
								pattern);

			} else {
				// IsLookupConstraint
				Object[] result9_bindingAndBlack = AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_0_9_bindingAndBlackFB(
								attributeConstraint);
				if (result9_bindingAndBlack != null) {
					AttributeLookupConstraint attributeLookupConstraint = (AttributeLookupConstraint) result9_bindingAndBlack[0];
					// processLookupConstraint
					AttributeConstraintBlackAndNacPatternTransformerImpl
							.pattern_AttributeConstraintBlackAndNacPatternTransformer_0_10_expressionBBB(this,
									attributeLookupConstraint, pattern);

				} else {
				}

			}

		}
		return;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void processPrimitiveVariable(PrimitiveVariable primitiveVariable, Pattern pattern) {// doesCorrespondingVariableStillExists
		Object[] result1_black = AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_1_1_blackBFB(pattern, primitiveVariable);
		if (result1_black != null) {
			// Variable variable = (Variable) result1_black[1];
			return;
		} else {

			// createLocalParameterVariable
			Object[] result3_black = AttributeConstraintBlackAndNacPatternTransformerImpl
					.pattern_AttributeConstraintBlackAndNacPatternTransformer_1_3_blackFBFB(pattern, primitiveVariable);
			if (result3_black == null) {
				throw new RuntimeException(
						"Pattern matching in node [createLocalParameterVariable] failed." + " Variables: "
								+ "[pattern] = " + pattern + ", " + "[primitiveVariable] = " + primitiveVariable + ".");
			}
			PatternBody patternBody = (PatternBody) result3_black[0];
			EDataType eDataType = (EDataType) result3_black[2];
			AttributeConstraintBlackAndNacPatternTransformerImpl
					.pattern_AttributeConstraintBlackAndNacPatternTransformer_1_3_greenBFBB(patternBody, eDataType,
							primitiveVariable);
			// EMFVariable newVariable = (EMFVariable) result3_green[1];

			return;
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void processLookupConstraint(AttributeLookupConstraint lookupConstraint, Pattern pattern) {
		// ActivityNode11
		Object[] result1_black = AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_2_1_blackBBFF(lookupConstraint, this);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [ActivityNode11] failed." + " Variables: "
					+ "[lookupConstraint] = " + lookupConstraint + ", " + "[this] = " + this + ".");
		}
		PrimitiveVariable primitiveVariable = (PrimitiveVariable) result1_black[2];
		ObjectVariable objectVariable = (ObjectVariable) result1_black[3];

		// lookupLeftInSymbolicParameters
		Object[] result2_bindingAndBlack = AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_bindingAndBlackFFBBFFBBB(pattern,
						lookupConstraint, this, primitiveVariable, objectVariable);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [lookupLeftInSymbolicParameters] failed."
					+ " Variables: " + "[pattern] = " + pattern + ", " + "[lookupConstraint] = " + lookupConstraint
					+ ", " + "[this] = " + this + ", " + "[primitiveVariable] = " + primitiveVariable + ", "
					+ "[objectVariable] = " + objectVariable + ".");
		}
		Variable right = (Variable) result2_bindingAndBlack[0];
		PatternBody patternBody = (PatternBody) result2_bindingAndBlack[1];
		EAttribute eAttribute = (EAttribute) result2_bindingAndBlack[4];
		Variable leftVariable = (Variable) result2_bindingAndBlack[5];
		Object[] result2_green = AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_greenBBF(patternBody, eAttribute);
		AttributeValueConstraint attributePredicate = (AttributeValueConstraint) result2_green[2];

		// handleLeftVariable
		Object[] result3_black = AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_2_3_blackBB(leftVariable, attributePredicate);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [handleLeftVariable] failed." + " Variables: "
					+ "[leftVariable] = " + leftVariable + ", " + "[attributePredicate] = " + attributePredicate + ".");
		}
		AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_2_3_greenBBF(leftVariable,
						attributePredicate);
						// ConstraintParameter leftConstraintParameter = (ConstraintParameter) result3_green[2];

		// handleRightVariable
		Object[] result4_black = AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_2_4_blackBB(attributePredicate, right);
		if (result4_black == null) {
			throw new RuntimeException("Pattern matching in node [handleRightVariable] failed." + " Variables: "
					+ "[attributePredicate] = " + attributePredicate + ", " + "[right] = " + right + ".");
		}
		AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_2_4_greenFBB(attributePredicate, right);
		// ConstraintParameter rightConstraintParameter = (ConstraintParameter) result4_green[0];

		return;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void processCspConstraint(CspConstraint cspConstraint, Pattern pattern) {
		// createAttributeValuePrediacateForCspConstraint
		Object[] result1_black = AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_3_1_blackBBBF(pattern, this, cspConstraint);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [createAttributeValuePrediacateForCspConstraint] failed." + " Variables: "
							+ "[pattern] = " + pattern + ", " + "[this] = " + this + ", " + "[cspConstraint] = "
							+ cspConstraint + ".");
		}
		PatternBody patternBody = (PatternBody) result1_black[3];
		Object[] result1_green = AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_3_1_greenFBB(patternBody, cspConstraint);
		AttributeVariableConstraint attributeVariablePredicate = (AttributeVariableConstraint) result1_green[0];

		// ForEach forEachParameter
		for (Object[] result2_black : AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_3_2_blackBF(cspConstraint)) {
			AttributeConstraintVariable parameter = (AttributeConstraintVariable) result2_black[1];
			// ifParameterIsLiteralLookupEFactory
			Object[] result3_bindingAndBlack = AttributeConstraintBlackAndNacPatternTransformerImpl
					.pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_bindingAndBlackFFFFB(parameter);
			if (result3_bindingAndBlack != null) {
				EDataType type = (EDataType) result3_bindingAndBlack[0];
				EFactory eFactory = (EFactory) result3_bindingAndBlack[1];
				LiteralVariable literalParameter = (LiteralVariable) result3_bindingAndBlack[2];
				// EPackage ePackage = (EPackage) result3_bindingAndBlack[3];

				// createConstant
				Object[] result4_black = AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_3_4_blackBBBB(
								attributeVariablePredicate, pattern, patternBody, type);
				if (result4_black == null) {
					throw new RuntimeException("Pattern matching in node [createConstant] failed." + " Variables: "
							+ "[attributeVariablePredicate] = " + attributeVariablePredicate + ", " + "[pattern] = "
							+ pattern + ", " + "[patternBody] = " + patternBody + ", " + "[type] = " + type + ".");
				}
				AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_3_4_greenBFFBBBB(
								attributeVariablePredicate, patternBody, type, eFactory, literalParameter);
				// TypedConstant constant = (TypedConstant) result4_green[1];
				// ConstraintParameter constraintParameter = (ConstraintParameter) result4_green[2];

			} else {

				// addConstraintParameter
				Object[] result5_bindingAndBlack = AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_bindingAndBlackBFFBBB(
								attributeVariablePredicate, parameter, this, pattern);
				if (result5_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [addConstraintParameter] failed."
							+ " Variables: " + "[attributeVariablePredicate] = " + attributeVariablePredicate + ", "
							+ "[parameter] = " + parameter + ", " + "[this] = " + this + ", " + "[pattern] = " + pattern
							+ ".");
				}
				// PrimitiveVariable primitiveVariable = (PrimitiveVariable) result5_bindingAndBlack[1];
				Variable variable = (Variable) result5_bindingAndBlack[2];
				AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_greenBBF(
								attributeVariablePredicate, variable);
				// ConstraintParameter constraintParameter = (ConstraintParameter) result5_green[2];

			}

		}
		// ActivityNode25
		AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_3_6_expressionBBB(this,
						attributeVariablePredicate, cspConstraint);
		return;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable lookupVariableByName(String name, Pattern pattern) {// ActivityNode13
		Object[] result1_black = AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_4_1_blackBFB(pattern, name);
		if (result1_black != null) {
			Variable variable = (Variable) result1_black[1];
			return AttributeConstraintBlackAndNacPatternTransformerImpl
					.pattern_AttributeConstraintBlackAndNacPatternTransformer_4_2_expressionFB(variable);
		} else {
			// ActivityNode14
			Object[] result3_black = AttributeConstraintBlackAndNacPatternTransformerImpl
					.pattern_AttributeConstraintBlackAndNacPatternTransformer_4_3_blackFFBB(pattern, name);
			if (result3_black != null) {
				Variable variable2 = (Variable) result3_black[0];
				// PatternBody patternBody = (PatternBody) result3_black[1];
				return AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_4_4_expressionFB(variable2);
			} else {
				return AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_4_5_expressionF();
			}

		}

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
	public void validateCspConstraints(AttributeVariableConstraint attributeConstraint, CspConstraint cspConstraint) {
		// [user code injected with eMoflon]
		//Lookup constraint in build in lib
		AttributeConstraintLibrary buildInLib = AttributeConstraintLibUtil.getBuildInAttributeConstraintLibrary();
		if (buildInLib.lookupConstraintType(attributeConstraint) != null) {
			return;
		}
		//Lookup constraint in user defined lib
		IProject project = CodeGeneratorPlugin.getWorkspaceProject(cspConstraint.eResource().getURI());
		AttributeConstraintLibrary userDefLib = AttributeConstraintLibUtil
				.getUserDefinedAttributeConstraintLibrary(project);
		validateConstraintLibrary(userDefLib, project.getName());
		if (userDefLib.lookupConstraintType(attributeConstraint) != null) {
			return;
		}
		//create Stub
		updateUserDefinedConstraintLib(attributeConstraint, userDefLib);
		AttributeConstraintLibUtil.saveAttributeConstraintLibrary(userDefLib);

		//create Error Message
		StringBuilder errorMsgString = new StringBuilder(Errors.UNKNOWN_ATTRIBUTE_CONSTRAINT.getLiteral());
		errorMsgString.append(": ");
		errorMsgString.append("\"" + cspConstraint.getName());
		errorMsgString.append("(");
		for (AttributeConstraintVariable var : cspConstraint.getParameters()) {
			errorMsgString.append(var.getType().getName() + ",");
		}
		errorMsgString.deleteCharAt(errorMsgString.length() - 1);
		errorMsgString.append(")\".");
		errorMsgString.append(
				"\n    Please complete stub in: \"" + project.getName() + " /lib/" + userDefLib.getPrefix() + ".xmi\"");

		ValidationReport report = this.getValidationReport();

		ErrorMessage errorMessage = ResultFactory.eINSTANCE.createErrorMessage();
		errorMessage.setId(errorMsgString.toString());
		errorMessage.setSeverity(Severity.ERROR);
		errorMessage.getLocation().add(attributeConstraint);
		report.getErrorMessages().add(errorMessage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void updateUserDefinedConstraintLib(AttributeVariableConstraint attributeConstraint,
			AttributeConstraintLibrary userDefinedLib) {
		// ActivityNode26
		Object[] result1_black = AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_7_1_blackBB(attributeConstraint,
						userDefinedLib);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [ActivityNode26] failed." + " Variables: " + "[attributeConstraint] = "
							+ attributeConstraint + ", " + "[userDefinedLib] = " + userDefinedLib + ".");
		}
		Object[] result1_green = AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_7_1_greenBFFFB(userDefinedLib,
						attributeConstraint);
		OperationSpecificationGroup operationSpecificationGroup = (OperationSpecificationGroup) result1_green[1];
		ConstraintSpecification constraintSpecification = (ConstraintSpecification) result1_green[2];
		IdentifyerHelper identifyerHelper = (IdentifyerHelper) result1_green[3];

		// ForEach ActivityNode27
		for (Object[] result2_black : AttributeConstraintBlackAndNacPatternTransformerImpl
				.pattern_AttributeConstraintBlackAndNacPatternTransformer_7_2_blackBFF(attributeConstraint)) {
			// ConstraintParameter constraintParameter = (ConstraintParameter) result2_black[1];
			ConstraintVariable variable = (ConstraintVariable) result2_black[2];
			// ActivityNode28
			Object[] result3_bindingAndBlack = AttributeConstraintBlackAndNacPatternTransformerImpl
					.pattern_AttributeConstraintBlackAndNacPatternTransformer_7_3_bindingAndBlackFFB(variable);
			if (result3_bindingAndBlack != null) {
				// EMFVariable emfVariable = (EMFVariable) result3_bindingAndBlack[0];
				EClassifier emfVarType = (EClassifier) result3_bindingAndBlack[1];

				// ActivityNode30
				Object[] result4_black = AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_7_4_blackBB(constraintSpecification,
								emfVarType);
				if (result4_black == null) {
					throw new RuntimeException("Pattern matching in node [ActivityNode30] failed." + " Variables: "
							+ "[constraintSpecification] = " + constraintSpecification + ", " + "[emfVarType] = "
							+ emfVarType + ".");
				}
				AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_7_4_greenBFB(constraintSpecification,
								emfVarType);
				// ParameterType parameterType = (ParameterType) result4_green[1];

			} else {

				// ActivityNode29
				Object[] result5_bindingAndBlack = AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_7_5_bindingAndBlackFFB(variable);
				if (result5_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [ActivityNode29] failed." + " Variables: "
							+ "[variable] = " + variable + ".");
				}
				// TypedConstant constant = (TypedConstant) result5_bindingAndBlack[0];
				EClassifier constantType = (EClassifier) result5_bindingAndBlack[1];

				// ActivityNode31
				Object[] result6_black = AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_7_6_blackBB(constraintSpecification,
								constantType);
				if (result6_black == null) {
					throw new RuntimeException("Pattern matching in node [ActivityNode31] failed." + " Variables: "
							+ "[constraintSpecification] = " + constraintSpecification + ", " + "[constantType] = "
							+ constantType + ".");
				}
				AttributeConstraintBlackAndNacPatternTransformerImpl
						.pattern_AttributeConstraintBlackAndNacPatternTransformer_7_6_greenBFB(constraintSpecification,
								constantType);
				// ParameterType parameterType = (ParameterType) result6_green[1];

			}

			// ActivityNode32
			Object[] result7_black = AttributeConstraintBlackAndNacPatternTransformerImpl
					.pattern_AttributeConstraintBlackAndNacPatternTransformer_7_7_blackB(operationSpecificationGroup);
			if (result7_black == null) {
				throw new RuntimeException("Pattern matching in node [ActivityNode32] failed." + " Variables: "
						+ "[operationSpecificationGroup] = " + operationSpecificationGroup + ".");
			}
			AttributeConstraintBlackAndNacPatternTransformerImpl
					.pattern_AttributeConstraintBlackAndNacPatternTransformer_7_7_greenBFB(operationSpecificationGroup,
							identifyerHelper);
			// ParamIdentifier paramIdentifier = (ParamIdentifier) result7_green[1];

		}
		return;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void validateConstraintLibrary(AttributeConstraintLibrary attributeConstraintLibrary, String projectName) {
		// [user code injected with eMoflon]
		ValidationReport validationReport = this.getValidationReport();
		String fileLoc = "\"" + projectName + "/lib/" + projectName + "AttributeConstraintLibrary.xmi\"";
		if (attributeConstraintLibrary.getPrefix() == null || attributeConstraintLibrary.getPrefix().length() == 0) {
			ErrorMessage errorMsg = createErrorMessage(attributeConstraintLibrary.eClass());
			errorMsg.setId(Errors.MALFORMED_ATTRIBUTE_CONSTRAINT.getLiteral()
					+ ": Missing \"prefix\" in \"AttributeConstraintLibrary\" " + fileLoc);

		}
		for (ConstraintSpecification constSpec : attributeConstraintLibrary.getConstraintSpecifications()) {
			if (constSpec.getSymbol() == null || constSpec.getSymbol().length() == 0) {
				ErrorMessage errorMsg = createErrorMessage(constSpec.eClass());
				errorMsg.setId(Errors.MALFORMED_ATTRIBUTE_CONSTRAINT.getLiteral()
						+ ": Missing \"symbol\" in \"ConstraintSpecification\" at index "
						+ attributeConstraintLibrary.getConstraintSpecifications().indexOf(constSpec)
						+ " in constraint library " + fileLoc);

			}
			if (constSpec.getOperationSpecificationGroup() == null) {
				ErrorMessage errorMsg = createErrorMessage(constSpec.eClass());
				errorMsg.setId(Errors.MALFORMED_ATTRIBUTE_CONSTRAINT.getLiteral()
						+ ": Missing link to \"operationSpecificationGroup\" in \"Constraintspecification\" with symbol \""
						+ constSpec.getSymbol() + "\" in constraint library " + fileLoc);
			}

			if (constSpec.getParameterTypes().size() <= 0) {
				ErrorMessage errorMsg = createErrorMessage(constSpec.eClass());
				errorMsg.setId(Errors.MALFORMED_ATTRIBUTE_CONSTRAINT.getLiteral()
						+ ": No \"ParameterTypes\" defined (at least one is required) for \"ConstraintSpecification\" with \"symbol=\""
						+ constSpec.getSymbol() + "\" in constraint library " + fileLoc);
			}
			if (constSpec.getOperationSpecificationGroup() != null) {
				if (constSpec.getOperationSpecificationGroup().getParameterIDs().size() != constSpec.getParameterTypes()
						.size()) {
					ErrorMessage errorMsg = createErrorMessage(constSpec.eClass());
					errorMsg.setId(Errors.MALFORMED_ATTRIBUTE_CONSTRAINT.getLiteral()
							+ ": Unequal number of \"ParameterTypes\" and \"ParameterIDs\" of \"ConstraintSpecification\" with \"symbol="
							+ constSpec.getSymbol() + "\" and corresponding \"OperationSpecificationGroup\""
							+ ". Both located in constraint library " + fileLoc);
				}
			}
			for (ParameterType parameterType : constSpec.getParameterTypes()) {
				if (parameterType.getType() == null) {
					ErrorMessage errorMsg = createErrorMessage(constSpec.eClass());
					errorMsg.setId(Errors.MALFORMED_ATTRIBUTE_CONSTRAINT.getLiteral()
							+ ": Missing \"type\" of \"ParameterType\" located in \"ConstraintSpecification\" with \"symbol="
							+ constSpec.getSymbol() + "\" in constraint library " + fileLoc);
				}
			}
		}
		for (OperationSpecificationGroup opSpecGroup : attributeConstraintLibrary.getOperationSpecifications()) {

			if (opSpecGroup.getOperationSpecifications().size() <= 0) {
				ErrorMessage errorMsg = createErrorMessage(opSpecGroup.eClass());
				errorMsg.setId(Errors.MALFORMED_ATTRIBUTE_CONSTRAINT.getLiteral()
						+ ": No \"OperationSpecifications\" in \"operationSpecificationGroup\" with \"operationIdentifier="
						+ opSpecGroup.getOperationIdentifier() + "\" in constraint library " + fileLoc
						+ "(at least one is required)");

			}
			if (opSpecGroup.getOperationIdentifier() == null || opSpecGroup.getOperationIdentifier().length() <= 0) {
				ErrorMessage errorMsg = createErrorMessage(opSpecGroup.eClass());
				errorMsg.setId(Errors.MALFORMED_ATTRIBUTE_CONSTRAINT.getLiteral()
						+ ": Missing \"operationIdentifier\" for \"OperationSpecificationGroup\" at index "
						+ attributeConstraintLibrary.getOperationSpecifications().indexOf(opSpecGroup)
						+ " in constraint library" + fileLoc);
			} else {
				if (opSpecGroup.getOperationIdentifier() != null && opSpecGroup.getOperationIdentifier().chars()
						.anyMatch(x -> !Character.isJavaIdentifierPart(x))) {
					ErrorMessage errorMsg = createErrorMessage(opSpecGroup.eClass());
					errorMsg.setId(Errors.MALFORMED_ATTRIBUTE_CONSTRAINT.getLiteral()
							+ ": Malformed \"operationIdentifier\" for \"OperationSpecificationGroup\" located at index "
							+ attributeConstraintLibrary.getOperationSpecifications().indexOf(opSpecGroup)
							+ " in constraint library" + fileLoc + " (Only valid java like identifier allowed).");
				}
			}
			for (ParamIdentifier pIdentifier : opSpecGroup.getParameterIDs()) {
				if (pIdentifier.getIdentifier() == null || pIdentifier.getIdentifier().length() == 0) {
					ErrorMessage errorMsg = createErrorMessage(opSpecGroup.eClass());
					errorMsg.setId(Errors.MALFORMED_ATTRIBUTE_CONSTRAINT.getLiteral()
							+ ": Missing the \"identifier\" for an \"ParamIdentifier\" in \"OperationSpecificationGroup\" located at index "
							+ attributeConstraintLibrary.getOperationSpecifications().indexOf(opSpecGroup)
							+ " in constraint library" + fileLoc + ".");
				}
			}
			for (OperationSpecification opSpec : opSpecGroup.getOperationSpecifications()) {
				if (opSpec.getAdornmentString() == null
						|| opSpec.getAdornmentString().length() != opSpecGroup.getParameterIDs().size()) {
					ErrorMessage errorMsg = createErrorMessage(opSpecGroup.eClass());
					errorMsg.setId(
							Errors.MALFORMED_ATTRIBUTE_CONSTRAINT.getLiteral()
									+ ": Malformed \"adornmentString\" in \"OperationSpecification\" located at index "
									+ opSpecGroup.getOperationSpecifications().indexOf(opSpec)
									+ " in \"OperationSpecificationGroup\" with \"operationIdentifier="
									+ (opSpecGroup.getOperationIdentifier() != null
											? opSpecGroup.getOperationIdentifier() : " ")
									+ "\"  in constraint library" + fileLoc + ".");
				}
				if (opSpec.getSpecification() == null || opSpec.getSpecification().length() == 0) {
					ErrorMessage errorMsg = createErrorMessage(opSpecGroup.eClass());
					errorMsg.setId(
							Errors.MALFORMED_ATTRIBUTE_CONSTRAINT.getLiteral()
									+ ": Missing \"specification\" in \"OperationSpecification\" located at index "
									+ opSpecGroup.getOperationSpecifications().indexOf(opSpec)
									+ " in \"OperationSpecificationGroup\" with \"operationIdentifier="
									+ (opSpecGroup.getOperationIdentifier() != null
											? opSpecGroup.getOperationIdentifier() : " ")
									+ "\"  in constraint library" + fileLoc + ".");
				}
			}

		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ErrorMessage createErrorMessage(EObject location) {
		// [user code injected with eMoflon]
		ErrorMessage errorMsg = ResultFactory.eINSTANCE.createErrorMessage();
		errorMsg.setSeverity(org.moflon.sdm.compiler.democles.validation.result.Severity.ERROR);
		errorMsg.getLocation().add(location);
		validationReport.getErrorMessages().add(errorMsg);
		return errorMsg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___POSTPROCESS__STORYPATTERN_PATTERN:
			postprocess((StoryPattern) arguments.get(0), (Pattern) arguments.get(1));
			return null;
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_PRIMITIVE_VARIABLE__PRIMITIVEVARIABLE_PATTERN:
			processPrimitiveVariable((PrimitiveVariable) arguments.get(0), (Pattern) arguments.get(1));
			return null;
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_LOOKUP_CONSTRAINT__ATTRIBUTELOOKUPCONSTRAINT_PATTERN:
			processLookupConstraint((AttributeLookupConstraint) arguments.get(0), (Pattern) arguments.get(1));
			return null;
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_CSP_CONSTRAINT__CSPCONSTRAINT_PATTERN:
			processCspConstraint((CspConstraint) arguments.get(0), (Pattern) arguments.get(1));
			return null;
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___LOOKUP_VARIABLE_BY_NAME__STRING_PATTERN:
			return lookupVariableByName((String) arguments.get(0), (Pattern) arguments.get(1));
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___CALL_SUPER_POSTPROCESS__STORYPATTERN_PATTERN:
			callSuperPostprocess((StoryPattern) arguments.get(0), (Pattern) arguments.get(1));
			return null;
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___VALIDATE_CSP_CONSTRAINTS__ATTRIBUTEVARIABLECONSTRAINT_CSPCONSTRAINT:
			validateCspConstraints((AttributeVariableConstraint) arguments.get(0), (CspConstraint) arguments.get(1));
			return null;
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___UPDATE_USER_DEFINED_CONSTRAINT_LIB__ATTRIBUTEVARIABLECONSTRAINT_ATTRIBUTECONSTRAINTLIBRARY:
			updateUserDefinedConstraintLib((AttributeVariableConstraint) arguments.get(0),
					(AttributeConstraintLibrary) arguments.get(1));
			return null;
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___VALIDATE_CONSTRAINT_LIBRARY__ATTRIBUTECONSTRAINTLIBRARY_STRING:
			validateConstraintLibrary((AttributeConstraintLibrary) arguments.get(0), (String) arguments.get(1));
			return null;
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___CREATE_ERROR_MESSAGE__EOBJECT:
			return createErrorMessage((EObject) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_0_1_blackB(
			AttributeConstraintBlackAndNacPatternTransformer _this) {
		return new Object[] { _this };
	}

	public static final void pattern_AttributeConstraintBlackAndNacPatternTransformer_0_2_expressionBBB(
			AttributeConstraintBlackAndNacPatternTransformer _this, StoryPattern storyPattern, Pattern pattern) {
		_this.callSuperPostprocess(storyPattern, pattern);

	}

	public static final Iterable<Object[]> pattern_AttributeConstraintBlackAndNacPatternTransformer_0_3_blackBF(
			StoryPattern storyPattern) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (AttributeConstraintVariable attributeConstraintVariable : storyPattern.getVariables()) {
			_result.add(new Object[] { storyPattern, attributeConstraintVariable });
		}
		return _result;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_0_4_bindingFB(
			AttributeConstraintVariable attributeConstraintVariable) {
		AttributeConstraintVariable tmpPrimitiveVariable = attributeConstraintVariable;
		if (tmpPrimitiveVariable instanceof PrimitiveVariable) {
			PrimitiveVariable primitiveVariable = (PrimitiveVariable) tmpPrimitiveVariable;
			return new Object[] { primitiveVariable, attributeConstraintVariable };
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_0_4_blackB(
			PrimitiveVariable primitiveVariable) {
		return new Object[] { primitiveVariable };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_0_4_bindingAndBlackFB(
			AttributeConstraintVariable attributeConstraintVariable) {
		Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_4_binding = pattern_AttributeConstraintBlackAndNacPatternTransformer_0_4_bindingFB(
				attributeConstraintVariable);
		if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_4_binding != null) {
			PrimitiveVariable primitiveVariable = (PrimitiveVariable) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_4_binding[0];

			Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_4_black = pattern_AttributeConstraintBlackAndNacPatternTransformer_0_4_blackB(
					primitiveVariable);
			if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_4_black != null) {

				return new Object[] { primitiveVariable, attributeConstraintVariable };
			}
		}
		return null;
	}

	public static final void pattern_AttributeConstraintBlackAndNacPatternTransformer_0_5_expressionBBB(
			AttributeConstraintBlackAndNacPatternTransformer _this, PrimitiveVariable primitiveVariable,
			Pattern pattern) {
		_this.processPrimitiveVariable(primitiveVariable, pattern);

	}

	public static final Iterable<Object[]> pattern_AttributeConstraintBlackAndNacPatternTransformer_0_6_blackFB(
			StoryPattern storyPattern) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (AttributeConstraint attributeConstraint : storyPattern.getConstraints()) {
			_result.add(new Object[] { attributeConstraint, storyPattern });
		}
		return _result;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_0_7_bindingFB(
			AttributeConstraint attributeConstraint) {
		AttributeConstraint tmpCspConstraint = attributeConstraint;
		if (tmpCspConstraint instanceof CspConstraint) {
			CspConstraint cspConstraint = (CspConstraint) tmpCspConstraint;
			return new Object[] { cspConstraint, attributeConstraint };
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_0_7_blackB(
			CspConstraint cspConstraint) {
		return new Object[] { cspConstraint };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_0_7_bindingAndBlackFB(
			AttributeConstraint attributeConstraint) {
		Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_7_binding = pattern_AttributeConstraintBlackAndNacPatternTransformer_0_7_bindingFB(
				attributeConstraint);
		if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_7_binding != null) {
			CspConstraint cspConstraint = (CspConstraint) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_7_binding[0];

			Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_7_black = pattern_AttributeConstraintBlackAndNacPatternTransformer_0_7_blackB(
					cspConstraint);
			if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_7_black != null) {

				return new Object[] { cspConstraint, attributeConstraint };
			}
		}
		return null;
	}

	public static final void pattern_AttributeConstraintBlackAndNacPatternTransformer_0_8_expressionBBB(
			AttributeConstraintBlackAndNacPatternTransformer _this, CspConstraint cspConstraint, Pattern pattern) {
		_this.processCspConstraint(cspConstraint, pattern);

	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_0_9_bindingFB(
			AttributeConstraint attributeConstraint) {
		AttributeConstraint tmpAttributeLookupConstraint = attributeConstraint;
		if (tmpAttributeLookupConstraint instanceof AttributeLookupConstraint) {
			AttributeLookupConstraint attributeLookupConstraint = (AttributeLookupConstraint) tmpAttributeLookupConstraint;
			return new Object[] { attributeLookupConstraint, attributeConstraint };
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_0_9_blackB(
			AttributeLookupConstraint attributeLookupConstraint) {
		return new Object[] { attributeLookupConstraint };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_0_9_bindingAndBlackFB(
			AttributeConstraint attributeConstraint) {
		Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_9_binding = pattern_AttributeConstraintBlackAndNacPatternTransformer_0_9_bindingFB(
				attributeConstraint);
		if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_9_binding != null) {
			AttributeLookupConstraint attributeLookupConstraint = (AttributeLookupConstraint) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_9_binding[0];

			Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_9_black = pattern_AttributeConstraintBlackAndNacPatternTransformer_0_9_blackB(
					attributeLookupConstraint);
			if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_0_9_black != null) {

				return new Object[] { attributeLookupConstraint, attributeConstraint };
			}
		}
		return null;
	}

	public static final void pattern_AttributeConstraintBlackAndNacPatternTransformer_0_10_expressionBBB(
			AttributeConstraintBlackAndNacPatternTransformer _this, AttributeLookupConstraint attributeLookupConstraint,
			Pattern pattern) {
		_this.processLookupConstraint(attributeLookupConstraint, pattern);

	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_1_1_blackBFB(Pattern pattern,
			PrimitiveVariable primitiveVariable) {
		String primitiveVariable_name = primitiveVariable.getName();
		for (Variable variable : pattern.getSymbolicParameters()) {
			String variable_name = variable.getName();
			if (variable_name.equals(primitiveVariable_name)) {
				return new Object[] { pattern, variable, primitiveVariable };
			}

		}

		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_1_3_blackFBFB(Pattern pattern,
			PrimitiveVariable primitiveVariable) {
		EDataType eDataType = primitiveVariable.getType();
		if (eDataType != null) {
			for (PatternBody patternBody : pattern.getBodies()) {
				return new Object[] { patternBody, pattern, eDataType, primitiveVariable };
			}
		}

		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_1_3_greenBFBB(
			PatternBody patternBody, EDataType eDataType, PrimitiveVariable primitiveVariable) {
		EMFVariable newVariable = EMFTypeFactory.eINSTANCE.createEMFVariable();
		patternBody.getLocalVariables().add(newVariable);
		newVariable.setEClassifier(eDataType);
		String primitiveVariable_name = primitiveVariable.getName();
		String newVariable_name_prime = primitiveVariable_name;
		newVariable.setName(newVariable_name_prime);
		return new Object[] { patternBody, newVariable, eDataType, primitiveVariable };

	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_2_1_blackBBFF(
			AttributeLookupConstraint lookupConstraint, AttributeConstraintBlackAndNacPatternTransformer _this) {
		AttributeConstraintVariable tmpPrimitiveVariable = lookupConstraint.getParameter();
		if (tmpPrimitiveVariable instanceof PrimitiveVariable) {
			PrimitiveVariable primitiveVariable = (PrimitiveVariable) tmpPrimitiveVariable;
			ObjectVariable objectVariable = lookupConstraint.getObjectVariable();
			if (objectVariable != null) {
				return new Object[] { lookupConstraint, _this, primitiveVariable, objectVariable };
			}

		}

		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_bindingFFBBBB(
			AttributeConstraintBlackAndNacPatternTransformer _this, PrimitiveVariable primitiveVariable,
			Pattern pattern, ObjectVariable objectVariable) {
		String primitiveVariable_name = primitiveVariable.getName();
		Variable _localVariable_0 = _this.lookupVariableByName(primitiveVariable_name, pattern);
		Variable right = _localVariable_0;
		if (right != null) {
			String objectVariable_name = objectVariable.getName();
			Variable _localVariable_1 = _this.lookupVariableByName(objectVariable_name, pattern);
			Variable leftVariable = _localVariable_1;
			if (leftVariable != null) {
				return new Object[] { right, leftVariable, _this, primitiveVariable, pattern, objectVariable };
			}

		}

		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_blackBFBBFB(
			Variable right, Pattern pattern, AttributeLookupConstraint lookupConstraint, Variable leftVariable) {
		if (!leftVariable.equals(right)) {
			EAttribute eAttribute = lookupConstraint.getType();
			if (eAttribute != null) {
				for (PatternBody patternBody : pattern.getBodies()) {
					return new Object[] { right, patternBody, pattern, lookupConstraint, eAttribute, leftVariable };
				}
			}

		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_bindingAndBlackFFBBFFBBB(
			Pattern pattern, AttributeLookupConstraint lookupConstraint,
			AttributeConstraintBlackAndNacPatternTransformer _this, PrimitiveVariable primitiveVariable,
			ObjectVariable objectVariable) {
		Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_binding = pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_bindingFFBBBB(
				_this, primitiveVariable, pattern, objectVariable);
		if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_binding != null) {
			Variable right = (Variable) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_binding[0];
			Variable leftVariable = (Variable) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_binding[1];

			Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_black = pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_blackBFBBFB(
					right, pattern, lookupConstraint, leftVariable);
			if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_black != null) {
				PatternBody patternBody = (PatternBody) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_black[1];
				EAttribute eAttribute = (EAttribute) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_black[4];

				return new Object[] { right, patternBody, pattern, lookupConstraint, eAttribute, leftVariable, _this,
						primitiveVariable, objectVariable };
			}
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_2_2_greenBBF(
			PatternBody patternBody, EAttribute eAttribute) {
		AttributeValueConstraint attributePredicate = DemoclesFactory.eINSTANCE.createAttributeValueConstraint();
		patternBody.getConstraints().add(attributePredicate);
		attributePredicate.setEModelElement(eAttribute);
		return new Object[] { patternBody, eAttribute, attributePredicate };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_2_3_blackBB(
			Variable leftVariable, AttributeValueConstraint attributePredicate) {
		return new Object[] { leftVariable, attributePredicate };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_2_3_greenBBF(
			Variable leftVariable, AttributeValueConstraint attributePredicate) {
		ConstraintParameter leftConstraintParameter = SpecificationFactory.eINSTANCE.createConstraintParameter();
		attributePredicate.getParameters().add(leftConstraintParameter);
		leftConstraintParameter.setReference(leftVariable);
		return new Object[] { leftVariable, attributePredicate, leftConstraintParameter };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_2_4_blackBB(
			AttributeValueConstraint attributePredicate, Variable right) {
		return new Object[] { attributePredicate, right };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_2_4_greenFBB(
			AttributeValueConstraint attributePredicate, Variable right) {
		ConstraintParameter rightConstraintParameter = SpecificationFactory.eINSTANCE.createConstraintParameter();
		rightConstraintParameter.setReference(right);
		attributePredicate.getParameters().add(rightConstraintParameter);
		return new Object[] { rightConstraintParameter, attributePredicate, right };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_3_1_blackBBBF(Pattern pattern,
			AttributeConstraintBlackAndNacPatternTransformer _this, CspConstraint cspConstraint) {
		for (PatternBody patternBody : pattern.getBodies()) {
			return new Object[] { pattern, _this, cspConstraint, patternBody };
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_3_1_greenFBB(
			PatternBody patternBody, CspConstraint cspConstraint) {
		AttributeVariableConstraint attributeVariablePredicate = DemoclesFactory.eINSTANCE
				.createAttributeVariableConstraint();
		patternBody.getConstraints().add(attributeVariablePredicate);
		String cspConstraint_name = cspConstraint.getName();
		String attributeVariablePredicate_predicateSymbol_prime = cspConstraint_name;
		attributeVariablePredicate.setPredicateSymbol(attributeVariablePredicate_predicateSymbol_prime);
		return new Object[] { attributeVariablePredicate, patternBody, cspConstraint };

	}

	public static final Iterable<Object[]> pattern_AttributeConstraintBlackAndNacPatternTransformer_3_2_blackBF(
			CspConstraint cspConstraint) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (AttributeConstraintVariable parameter : cspConstraint.getParameters()) {
			_result.add(new Object[] { cspConstraint, parameter });
		}
		return _result;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_bindingFB(
			AttributeConstraintVariable parameter) {
		AttributeConstraintVariable tmpLiteralParameter = parameter;
		if (tmpLiteralParameter instanceof LiteralVariable) {
			LiteralVariable literalParameter = (LiteralVariable) tmpLiteralParameter;
			return new Object[] { literalParameter, parameter };
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_blackFFBF(
			LiteralVariable literalParameter) {
		EDataType type = literalParameter.getType();
		if (type != null) {
			EPackage ePackage = type.getEPackage();
			if (ePackage != null) {
				EFactory eFactory = ePackage.getEFactoryInstance();
				if (eFactory != null) {
					return new Object[] { type, eFactory, literalParameter, ePackage };
				}

			}

		}

		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_bindingAndBlackFFFFB(
			AttributeConstraintVariable parameter) {
		Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_binding = pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_bindingFB(
				parameter);
		if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_binding != null) {
			LiteralVariable literalParameter = (LiteralVariable) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_binding[0];

			Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_black = pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_blackFFBF(
					literalParameter);
			if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_black != null) {
				EDataType type = (EDataType) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_black[0];
				EFactory eFactory = (EFactory) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_black[1];
				EPackage ePackage = (EPackage) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_3_black[3];

				return new Object[] { type, eFactory, literalParameter, ePackage, parameter };
			}
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_3_4_blackBBBB(
			AttributeVariableConstraint attributeVariablePredicate, Pattern pattern, PatternBody patternBody,
			EDataType type) {
		if (pattern.equals(patternBody.getHeader())) {
			return new Object[] { attributeVariablePredicate, pattern, patternBody, type };
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_3_4_greenBFFBBBB(
			AttributeVariableConstraint attributeVariablePredicate, PatternBody patternBody, EDataType type,
			EFactory eFactory, LiteralVariable literalParameter) {
		TypedConstant constant = DemoclesFactory.eINSTANCE.createTypedConstant();
		ConstraintParameter constraintParameter = SpecificationFactory.eINSTANCE.createConstraintParameter();
		constant.setEClassifier(type);
		patternBody.getConstants().add(constant);
		attributeVariablePredicate.getParameters().add(constraintParameter);
		constraintParameter.setReference(constant);
		String literalParameter_name = literalParameter.getName();
		Object _localVariable_0 = eFactory.createFromString(type, literalParameter_name);
		Object constant_value_prime = (Object) _localVariable_0;
		constant.setValue(constant_value_prime);
		return new Object[] { attributeVariablePredicate, constant, constraintParameter, patternBody, type, eFactory,
				literalParameter };

	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_bindingFFBBB(
			AttributeConstraintVariable parameter, AttributeConstraintBlackAndNacPatternTransformer _this,
			Pattern pattern) {
		AttributeConstraintVariable tmpPrimitiveVariable = parameter;
		if (tmpPrimitiveVariable instanceof PrimitiveVariable) {
			PrimitiveVariable primitiveVariable = (PrimitiveVariable) tmpPrimitiveVariable;
			String primitiveVariable_name = primitiveVariable.getName();
			Variable _localVariable_1 = _this.lookupVariableByName(primitiveVariable_name, pattern);
			Variable variable = _localVariable_1;
			if (variable != null) {
				return new Object[] { primitiveVariable, variable, parameter, _this, pattern };
			}

		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_blackBBB(
			AttributeVariableConstraint attributeVariablePredicate, PrimitiveVariable primitiveVariable,
			Variable variable) {
		return new Object[] { attributeVariablePredicate, primitiveVariable, variable };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_bindingAndBlackBFFBBB(
			AttributeVariableConstraint attributeVariablePredicate, AttributeConstraintVariable parameter,
			AttributeConstraintBlackAndNacPatternTransformer _this, Pattern pattern) {
		Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_binding = pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_bindingFFBBB(
				parameter, _this, pattern);
		if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_binding != null) {
			PrimitiveVariable primitiveVariable = (PrimitiveVariable) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_binding[0];
			Variable variable = (Variable) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_binding[1];

			Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_black = pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_blackBBB(
					attributeVariablePredicate, primitiveVariable, variable);
			if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_black != null) {

				return new Object[] { attributeVariablePredicate, primitiveVariable, variable, parameter, _this,
						pattern };
			}
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_3_5_greenBBF(
			AttributeVariableConstraint attributeVariablePredicate, Variable variable) {
		ConstraintParameter constraintParameter = SpecificationFactory.eINSTANCE.createConstraintParameter();
		attributeVariablePredicate.getParameters().add(constraintParameter);
		constraintParameter.setReference(variable);
		return new Object[] { attributeVariablePredicate, variable, constraintParameter };
	}

	public static final void pattern_AttributeConstraintBlackAndNacPatternTransformer_3_6_expressionBBB(
			AttributeConstraintBlackAndNacPatternTransformer _this,
			AttributeVariableConstraint attributeVariablePredicate, CspConstraint cspConstraint) {
		_this.validateCspConstraints(attributeVariablePredicate, cspConstraint);

	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_4_1_blackBFB(Pattern pattern,
			String name) {
		for (Variable variable : pattern.getSymbolicParameters()) {
			String variable_name = variable.getName();
			if (variable_name.equals(name)) {
				return new Object[] { pattern, variable, name };
			}

		}
		return null;
	}

	public static final Variable pattern_AttributeConstraintBlackAndNacPatternTransformer_4_2_expressionFB(
			Variable variable) {
		Variable _result = variable;
		return _result;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_4_3_blackFFBB(Pattern pattern,
			String name) {
		for (PatternBody patternBody : pattern.getBodies()) {
			for (Variable variable2 : patternBody.getLocalVariables()) {
				String variable2_name = variable2.getName();
				if (variable2_name.equals(name)) {
					return new Object[] { variable2, patternBody, pattern, name };
				}

			}
		}
		return null;
	}

	public static final Variable pattern_AttributeConstraintBlackAndNacPatternTransformer_4_4_expressionFB(
			Variable variable2) {
		Variable _result = variable2;
		return _result;
	}

	public static final Variable pattern_AttributeConstraintBlackAndNacPatternTransformer_4_5_expressionF() {
		Variable _result = null;
		return _result;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_1_blackBB(
			AttributeVariableConstraint attributeConstraint, AttributeConstraintLibrary userDefinedLib) {
		return new Object[] { attributeConstraint, userDefinedLib };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_1_greenBFFFB(
			AttributeConstraintLibrary userDefinedLib, AttributeVariableConstraint attributeConstraint) {
		OperationSpecificationGroup operationSpecificationGroup = OperationspecificationFactory.eINSTANCE
				.createOperationSpecificationGroup();
		ConstraintSpecification constraintSpecification = OperationspecificationFactory.eINSTANCE
				.createConstraintSpecification();
		IdentifyerHelper identifyerHelper = ConstraintstodemoclesFactory.eINSTANCE.createIdentifyerHelper();
		operationSpecificationGroup.setAttributeConstraintLibrary(userDefinedLib);
		constraintSpecification.setAttributeConstraintLibrary(userDefinedLib);
		operationSpecificationGroup.getConstraintSpecifications().add(constraintSpecification);
		String attributeConstraint_predicateSymbol = attributeConstraint.getPredicateSymbol();
		String operationSpecificationGroup_operationIdentifier_prime = attributeConstraint_predicateSymbol;
		String constraintSpecification_symbol_prime = attributeConstraint_predicateSymbol;
		operationSpecificationGroup.setOperationIdentifier(operationSpecificationGroup_operationIdentifier_prime);
		constraintSpecification.setSymbol(constraintSpecification_symbol_prime);
		return new Object[] { userDefinedLib, operationSpecificationGroup, constraintSpecification, identifyerHelper,
				attributeConstraint };

	}

	public static final Iterable<Object[]> pattern_AttributeConstraintBlackAndNacPatternTransformer_7_2_blackBFF(
			AttributeVariableConstraint attributeConstraint) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (ConstraintParameter constraintParameter : attributeConstraint.getParameters()) {
			ConstraintVariable variable = constraintParameter.getReference();
			if (variable != null) {
				_result.add(new Object[] { attributeConstraint, constraintParameter, variable });
			}

		}
		return _result;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_3_bindingFB(
			ConstraintVariable variable) {
		ConstraintVariable tmpEmfVariable = variable;
		if (tmpEmfVariable instanceof EMFVariable) {
			EMFVariable emfVariable = (EMFVariable) tmpEmfVariable;
			return new Object[] { emfVariable, variable };
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_3_blackBF(
			EMFVariable emfVariable) {
		EClassifier emfVarType = emfVariable.getEClassifier();
		if (emfVarType != null) {
			return new Object[] { emfVariable, emfVarType };
		}

		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_3_bindingAndBlackFFB(
			ConstraintVariable variable) {
		Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_7_3_binding = pattern_AttributeConstraintBlackAndNacPatternTransformer_7_3_bindingFB(
				variable);
		if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_7_3_binding != null) {
			EMFVariable emfVariable = (EMFVariable) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_7_3_binding[0];

			Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_7_3_black = pattern_AttributeConstraintBlackAndNacPatternTransformer_7_3_blackBF(
					emfVariable);
			if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_7_3_black != null) {
				EClassifier emfVarType = (EClassifier) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_7_3_black[1];

				return new Object[] { emfVariable, emfVarType, variable };
			}
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_4_blackBB(
			ConstraintSpecification constraintSpecification, EClassifier emfVarType) {
		return new Object[] { constraintSpecification, emfVarType };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_4_greenBFB(
			ConstraintSpecification constraintSpecification, EClassifier emfVarType) {
		ParameterType parameterType = OperationspecificationFactory.eINSTANCE.createParameterType();
		constraintSpecification.getParameterTypes().add(parameterType);
		parameterType.setType(emfVarType);
		return new Object[] { constraintSpecification, parameterType, emfVarType };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_5_bindingFB(
			ConstraintVariable variable) {
		ConstraintVariable tmpConstant = variable;
		if (tmpConstant instanceof TypedConstant) {
			TypedConstant constant = (TypedConstant) tmpConstant;
			return new Object[] { constant, variable };
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_5_blackBF(
			TypedConstant constant) {
		EClassifier constantType = constant.getEClassifier();
		if (constantType != null) {
			return new Object[] { constant, constantType };
		}

		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_5_bindingAndBlackFFB(
			ConstraintVariable variable) {
		Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_7_5_binding = pattern_AttributeConstraintBlackAndNacPatternTransformer_7_5_bindingFB(
				variable);
		if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_7_5_binding != null) {
			TypedConstant constant = (TypedConstant) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_7_5_binding[0];

			Object[] result_pattern_AttributeConstraintBlackAndNacPatternTransformer_7_5_black = pattern_AttributeConstraintBlackAndNacPatternTransformer_7_5_blackBF(
					constant);
			if (result_pattern_AttributeConstraintBlackAndNacPatternTransformer_7_5_black != null) {
				EClassifier constantType = (EClassifier) result_pattern_AttributeConstraintBlackAndNacPatternTransformer_7_5_black[1];

				return new Object[] { constant, constantType, variable };
			}
		}
		return null;
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_6_blackBB(
			ConstraintSpecification constraintSpecification, EClassifier constantType) {
		return new Object[] { constraintSpecification, constantType };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_6_greenBFB(
			ConstraintSpecification constraintSpecification, EClassifier constantType) {
		ParameterType parameterType = OperationspecificationFactory.eINSTANCE.createParameterType();
		constraintSpecification.getParameterTypes().add(parameterType);
		parameterType.setType(constantType);
		return new Object[] { constraintSpecification, parameterType, constantType };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_7_blackB(
			OperationSpecificationGroup operationSpecificationGroup) {
		return new Object[] { operationSpecificationGroup };
	}

	public static final Object[] pattern_AttributeConstraintBlackAndNacPatternTransformer_7_7_greenBFB(
			OperationSpecificationGroup operationSpecificationGroup, IdentifyerHelper identifyerHelper) {
		ParamIdentifier paramIdentifier = OperationspecificationFactory.eINSTANCE.createParamIdentifier();
		String _localVariable_0 = identifyerHelper.getNextIdentifyer();
		paramIdentifier.setOperationSpecificationGroup(operationSpecificationGroup);
		String paramIdentifier_identifier_prime = _localVariable_0;
		paramIdentifier.setIdentifier(paramIdentifier_identifier_prime);
		return new Object[] { operationSpecificationGroup, paramIdentifier, identifyerHelper };
	}

	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //AttributeConstraintBlackAndNacPatternTransformerImpl
