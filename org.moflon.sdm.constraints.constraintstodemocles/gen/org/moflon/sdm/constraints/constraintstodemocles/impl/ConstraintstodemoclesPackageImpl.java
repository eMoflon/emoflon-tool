/**
 */
package org.moflon.sdm.constraints.constraintstodemocles.impl;

import SDMLanguage.patterns.AttributeConstraints.AttributeConstraintsPackage;

import SDMLanguage.patterns.PatternsPackage;

import SDMLanguageToDemocles.SDMLanguageToDemoclesPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.gervarro.democles.specification.emf.SpecificationPackage;

import org.moflon.sdm.compiler.democles.validation.result.ResultPackage;

import org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer;
import org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer;
import org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesFactory;
import org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesPackage;
import org.moflon.sdm.constraints.constraintstodemocles.Errors;
import org.moflon.sdm.constraints.constraintstodemocles.IdentifyerHelper;

import org.moflon.sdm.constraints.democles.DemoclesPackage;

import org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConstraintstodemoclesPackageImpl extends EPackageImpl implements ConstraintstodemoclesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeConstraintBlackAndNacPatternTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeConstraintGreenPatternTransformerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass identifyerHelperEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum errorsEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ConstraintstodemoclesPackageImpl() {
		super(eNS_URI, ConstraintstodemoclesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ConstraintstodemoclesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ConstraintstodemoclesPackage init() {
		if (isInited)
			return (ConstraintstodemoclesPackage) EPackage.Registry.INSTANCE
					.getEPackage(ConstraintstodemoclesPackage.eNS_URI);

		// Obtain or create and register package
		ConstraintstodemoclesPackageImpl theConstraintstodemoclesPackage = (ConstraintstodemoclesPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof ConstraintstodemoclesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new ConstraintstodemoclesPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		OperationspecificationPackage.eINSTANCE.eClass();
		SDMLanguageToDemoclesPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theConstraintstodemoclesPackage.createPackageContents();

		// Initialize created meta-data
		theConstraintstodemoclesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theConstraintstodemoclesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ConstraintstodemoclesPackage.eNS_URI, theConstraintstodemoclesPackage);
		return theConstraintstodemoclesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeConstraintBlackAndNacPatternTransformer() {
		return attributeConstraintBlackAndNacPatternTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackAndNacPatternTransformer__Postprocess__StoryPattern_Pattern() {
		return attributeConstraintBlackAndNacPatternTransformerEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackAndNacPatternTransformer__ProcessPrimitiveVariable__PrimitiveVariable_Pattern() {
		return attributeConstraintBlackAndNacPatternTransformerEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackAndNacPatternTransformer__ProcessLookupConstraint__AttributeLookupConstraint_Pattern() {
		return attributeConstraintBlackAndNacPatternTransformerEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackAndNacPatternTransformer__ProcessCspConstraint__CspConstraint_Pattern() {
		return attributeConstraintBlackAndNacPatternTransformerEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackAndNacPatternTransformer__LookupVariableByName__String_Pattern() {
		return attributeConstraintBlackAndNacPatternTransformerEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackAndNacPatternTransformer__CallSuperPostprocess__StoryPattern_Pattern() {
		return attributeConstraintBlackAndNacPatternTransformerEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackAndNacPatternTransformer__ValidateCspConstraints__AttributeVariableConstraint_CspConstraint() {
		return attributeConstraintBlackAndNacPatternTransformerEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackAndNacPatternTransformer__UpdateUserDefinedConstraintLib__AttributeVariableConstraint_AttributeConstraintLibrary() {
		return attributeConstraintBlackAndNacPatternTransformerEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackAndNacPatternTransformer__ValidateConstraintLibrary__AttributeConstraintLibrary_String() {
		return attributeConstraintBlackAndNacPatternTransformerEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackAndNacPatternTransformer__CreateErrorMessage__EObject() {
		return attributeConstraintBlackAndNacPatternTransformerEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeConstraintGreenPatternTransformer() {
		return attributeConstraintGreenPatternTransformerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintGreenPatternTransformer__Postprocess__StoryPattern_Pattern() {
		return attributeConstraintGreenPatternTransformerEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintGreenPatternTransformer__CallSuperPostprocess__StoryPattern_Pattern() {
		return attributeConstraintGreenPatternTransformerEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintGreenPatternTransformer__ProcessAssignmentConstraint__AssignmentConstraint_Pattern() {
		return attributeConstraintGreenPatternTransformerEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintGreenPatternTransformer__LookupVariableByName__String_Pattern() {
		return attributeConstraintGreenPatternTransformerEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintGreenPatternTransformer__IsVariableToAdd__ObjectVariable() {
		return attributeConstraintGreenPatternTransformerEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintGreenPatternTransformer__CallSuperIsVariableToAdd__ObjectVariable() {
		return attributeConstraintGreenPatternTransformerEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIdentifyerHelper() {
		return identifyerHelperEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getIdentifyerHelper__GetNextIdentifyer() {
		return identifyerHelperEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getErrors() {
		return errorsEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstraintstodemoclesFactory getConstraintstodemoclesFactory() {
		return (ConstraintstodemoclesFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		attributeConstraintBlackAndNacPatternTransformerEClass = createEClass(
				ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER);
		createEOperation(attributeConstraintBlackAndNacPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___POSTPROCESS__STORYPATTERN_PATTERN);
		createEOperation(attributeConstraintBlackAndNacPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_PRIMITIVE_VARIABLE__PRIMITIVEVARIABLE_PATTERN);
		createEOperation(attributeConstraintBlackAndNacPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_LOOKUP_CONSTRAINT__ATTRIBUTELOOKUPCONSTRAINT_PATTERN);
		createEOperation(attributeConstraintBlackAndNacPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_CSP_CONSTRAINT__CSPCONSTRAINT_PATTERN);
		createEOperation(attributeConstraintBlackAndNacPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___LOOKUP_VARIABLE_BY_NAME__STRING_PATTERN);
		createEOperation(attributeConstraintBlackAndNacPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___CALL_SUPER_POSTPROCESS__STORYPATTERN_PATTERN);
		createEOperation(attributeConstraintBlackAndNacPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___VALIDATE_CSP_CONSTRAINTS__ATTRIBUTEVARIABLECONSTRAINT_CSPCONSTRAINT);
		createEOperation(attributeConstraintBlackAndNacPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___UPDATE_USER_DEFINED_CONSTRAINT_LIB__ATTRIBUTEVARIABLECONSTRAINT_ATTRIBUTECONSTRAINTLIBRARY);
		createEOperation(attributeConstraintBlackAndNacPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___VALIDATE_CONSTRAINT_LIBRARY__ATTRIBUTECONSTRAINTLIBRARY_STRING);
		createEOperation(attributeConstraintBlackAndNacPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___CREATE_ERROR_MESSAGE__EOBJECT);

		attributeConstraintGreenPatternTransformerEClass = createEClass(ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER);
		createEOperation(attributeConstraintGreenPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___POSTPROCESS__STORYPATTERN_PATTERN);
		createEOperation(attributeConstraintGreenPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___CALL_SUPER_POSTPROCESS__STORYPATTERN_PATTERN);
		createEOperation(attributeConstraintGreenPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___PROCESS_ASSIGNMENT_CONSTRAINT__ASSIGNMENTCONSTRAINT_PATTERN);
		createEOperation(attributeConstraintGreenPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___LOOKUP_VARIABLE_BY_NAME__STRING_PATTERN);
		createEOperation(attributeConstraintGreenPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___IS_VARIABLE_TO_ADD__OBJECTVARIABLE);
		createEOperation(attributeConstraintGreenPatternTransformerEClass,
				ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___CALL_SUPER_IS_VARIABLE_TO_ADD__OBJECTVARIABLE);

		identifyerHelperEClass = createEClass(IDENTIFYER_HELPER);
		createEOperation(identifyerHelperEClass, IDENTIFYER_HELPER___GET_NEXT_IDENTIFYER);

		// Create enums
		errorsEEnum = createEEnum(ERRORS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		SDMLanguageToDemoclesPackage theSDMLanguageToDemoclesPackage = (SDMLanguageToDemoclesPackage) EPackage.Registry.INSTANCE
				.getEPackage(SDMLanguageToDemoclesPackage.eNS_URI);
		PatternsPackage thePatternsPackage = (PatternsPackage) EPackage.Registry.INSTANCE
				.getEPackage(PatternsPackage.eNS_URI);
		SpecificationPackage theSpecificationPackage = (SpecificationPackage) EPackage.Registry.INSTANCE
				.getEPackage(SpecificationPackage.eNS_URI);
		AttributeConstraintsPackage theAttributeConstraintsPackage = (AttributeConstraintsPackage) EPackage.Registry.INSTANCE
				.getEPackage(AttributeConstraintsPackage.eNS_URI);
		DemoclesPackage theDemoclesPackage = (DemoclesPackage) EPackage.Registry.INSTANCE
				.getEPackage(DemoclesPackage.eNS_URI);
		OperationspecificationPackage theOperationspecificationPackage = (OperationspecificationPackage) EPackage.Registry.INSTANCE
				.getEPackage(OperationspecificationPackage.eNS_URI);
		ResultPackage theResultPackage = (ResultPackage) EPackage.Registry.INSTANCE.getEPackage(ResultPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		attributeConstraintBlackAndNacPatternTransformerEClass.getESuperTypes()
				.add(theSDMLanguageToDemoclesPackage.getBlackAndNacPatternTransformer());
		attributeConstraintGreenPatternTransformerEClass.getESuperTypes()
				.add(theSDMLanguageToDemoclesPackage.getGreenPatternTransformer());

		// Initialize classes, features, and operations; add parameters
		initEClass(attributeConstraintBlackAndNacPatternTransformerEClass,
				AttributeConstraintBlackAndNacPatternTransformer.class,
				"AttributeConstraintBlackAndNacPatternTransformer", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		EOperation op = initEOperation(
				getAttributeConstraintBlackAndNacPatternTransformer__Postprocess__StoryPattern_Pattern(), null,
				"postprocess", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePatternsPackage.getStoryPattern(), "storyPattern", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getPattern(), "pattern", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getAttributeConstraintBlackAndNacPatternTransformer__ProcessPrimitiveVariable__PrimitiveVariable_Pattern(),
				null, "processPrimitiveVariable", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theAttributeConstraintsPackage.getPrimitiveVariable(), "primitiveVariable", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getPattern(), "pattern", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getAttributeConstraintBlackAndNacPatternTransformer__ProcessLookupConstraint__AttributeLookupConstraint_Pattern(),
				null, "processLookupConstraint", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theAttributeConstraintsPackage.getAttributeLookupConstraint(), "lookupConstraint", 0, 1,
				IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getPattern(), "pattern", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getAttributeConstraintBlackAndNacPatternTransformer__ProcessCspConstraint__CspConstraint_Pattern(),
				null, "processCspConstraint", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theAttributeConstraintsPackage.getCspConstraint(), "cspConstraint", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getPattern(), "pattern", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAttributeConstraintBlackAndNacPatternTransformer__LookupVariableByName__String_Pattern(),
				theSpecificationPackage.getVariable(), "lookupVariableByName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getPattern(), "pattern", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getAttributeConstraintBlackAndNacPatternTransformer__CallSuperPostprocess__StoryPattern_Pattern(), null,
				"callSuperPostprocess", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePatternsPackage.getStoryPattern(), "storyPattern", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getPattern(), "pattern", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getAttributeConstraintBlackAndNacPatternTransformer__ValidateCspConstraints__AttributeVariableConstraint_CspConstraint(),
				null, "validateCspConstraints", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theDemoclesPackage.getAttributeVariableConstraint(), "attributeConstraint", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theAttributeConstraintsPackage.getCspConstraint(), "cspConstraint", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(
				getAttributeConstraintBlackAndNacPatternTransformer__UpdateUserDefinedConstraintLib__AttributeVariableConstraint_AttributeConstraintLibrary(),
				null, "updateUserDefinedConstraintLib", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theDemoclesPackage.getAttributeVariableConstraint(), "attributeConstraint", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theOperationspecificationPackage.getAttributeConstraintLibrary(), "userDefinedLib", 0, 1,
				IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getAttributeConstraintBlackAndNacPatternTransformer__ValidateConstraintLibrary__AttributeConstraintLibrary_String(),
				null, "validateConstraintLibrary", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theOperationspecificationPackage.getAttributeConstraintLibrary(),
				"attributeConstraintLibrary", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "projectName", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAttributeConstraintBlackAndNacPatternTransformer__CreateErrorMessage__EObject(),
				theResultPackage.getErrorMessage(), "createErrorMessage", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "location", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(attributeConstraintGreenPatternTransformerEClass, AttributeConstraintGreenPatternTransformer.class,
				"AttributeConstraintGreenPatternTransformer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = initEOperation(getAttributeConstraintGreenPatternTransformer__Postprocess__StoryPattern_Pattern(), null,
				"postprocess", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePatternsPackage.getStoryPattern(), "storyPattern", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getPattern(), "pattern", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAttributeConstraintGreenPatternTransformer__CallSuperPostprocess__StoryPattern_Pattern(),
				null, "callSuperPostprocess", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePatternsPackage.getStoryPattern(), "storyPattern", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getPattern(), "pattern", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getAttributeConstraintGreenPatternTransformer__ProcessAssignmentConstraint__AssignmentConstraint_Pattern(),
				null, "processAssignmentConstraint", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theAttributeConstraintsPackage.getAssignmentConstraint(), "assignmentConstraint", 0, 1,
				IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getPattern(), "pattern", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAttributeConstraintGreenPatternTransformer__LookupVariableByName__String_Pattern(),
				theSpecificationPackage.getVariable(), "lookupVariableByName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "variableName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getPattern(), "pattern", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAttributeConstraintGreenPatternTransformer__IsVariableToAdd__ObjectVariable(),
				ecorePackage.getEBoolean(), "isVariableToAdd", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePatternsPackage.getObjectVariable(), "objectVariable", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAttributeConstraintGreenPatternTransformer__CallSuperIsVariableToAdd__ObjectVariable(),
				ecorePackage.getEBoolean(), "callSuperIsVariableToAdd", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePatternsPackage.getObjectVariable(), "objectVariable", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(identifyerHelperEClass, IdentifyerHelper.class, "IdentifyerHelper", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEOperation(getIdentifyerHelper__GetNextIdentifyer(), ecorePackage.getEString(), "getNextIdentifyer", 0, 1,
				IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(errorsEEnum, Errors.class, "Errors");
		addEEnumLiteral(errorsEEnum, Errors.UNKNOWN_ATTRIBUTE_CONSTRAINT);
		addEEnumLiteral(errorsEEnum, Errors.MALFORMED_ATTRIBUTE_CONSTRAINT);

		// Create resource
		createResource(eNS_URI);
	}

} //ConstraintstodemoclesPackageImpl
