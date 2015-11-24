/**
 */
package org.moflon.sdm.constraints.scopevalidation.impl;

import ControlFlow.ControlFlowPackage;

import SDMLanguage.patterns.PatternsPackage;

import ScopeValidation.ScopeValidationPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.gervarro.democles.specification.emf.SpecificationPackage;

import org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder;
import org.moflon.sdm.constraints.scopevalidation.AttributeConstraintGreenPatternInvocationBuilder;
import org.moflon.sdm.constraints.scopevalidation.ScopevalidationFactory;
import org.moflon.sdm.constraints.scopevalidation.ScopevalidationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ScopevalidationPackageImpl extends EPackageImpl implements ScopevalidationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeConstraintBlackPatternInvocationBuilderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeConstraintGreenPatternInvocationBuilderEClass = null;

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
	 * @see org.moflon.sdm.constraints.scopevalidation.ScopevalidationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ScopevalidationPackageImpl() {
		super(eNS_URI, ScopevalidationFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ScopevalidationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ScopevalidationPackage init() {
		if (isInited)
			return (ScopevalidationPackage) EPackage.Registry.INSTANCE.getEPackage(ScopevalidationPackage.eNS_URI);

		// Obtain or create and register package
		ScopevalidationPackageImpl theScopevalidationPackage = (ScopevalidationPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof ScopevalidationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new ScopevalidationPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ScopeValidationPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theScopevalidationPackage.createPackageContents();

		// Initialize created meta-data
		theScopevalidationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theScopevalidationPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ScopevalidationPackage.eNS_URI, theScopevalidationPackage);
		return theScopevalidationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeConstraintBlackPatternInvocationBuilder() {
		return attributeConstraintBlackPatternInvocationBuilderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackPatternInvocationBuilder__AnalyzeDependencies__PatternInvocationBuilder_StoryPattern_Pattern() {
		return attributeConstraintBlackPatternInvocationBuilderEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackPatternInvocationBuilder__CallSuperAnalyzeDependencies__PatternInvocationBuilder_StoryPattern_Pattern() {
		return attributeConstraintBlackPatternInvocationBuilderEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackPatternInvocationBuilder__Concat__String_int() {
		return attributeConstraintBlackPatternInvocationBuilderEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackPatternInvocationBuilder__CalcLocalVariableName__Variable() {
		return attributeConstraintBlackPatternInvocationBuilderEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintBlackPatternInvocationBuilder__HandleAttrVariableReference__Variable_EClassifier() {
		return attributeConstraintBlackPatternInvocationBuilderEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeConstraintGreenPatternInvocationBuilder() {
		return attributeConstraintGreenPatternInvocationBuilderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintGreenPatternInvocationBuilder__ValidateVariable__Action_CFVariable_ObjectVariable() {
		return attributeConstraintGreenPatternInvocationBuilderEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopevalidationFactory getScopevalidationFactory() {
		return (ScopevalidationFactory) getEFactoryInstance();
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
		attributeConstraintBlackPatternInvocationBuilderEClass = createEClass(
				ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER);
		createEOperation(attributeConstraintBlackPatternInvocationBuilderEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___ANALYZE_DEPENDENCIES__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN);
		createEOperation(attributeConstraintBlackPatternInvocationBuilderEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CALL_SUPER_ANALYZE_DEPENDENCIES__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN);
		createEOperation(attributeConstraintBlackPatternInvocationBuilderEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CONCAT__STRING_INT);
		createEOperation(attributeConstraintBlackPatternInvocationBuilderEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CALC_LOCAL_VARIABLE_NAME__VARIABLE);
		createEOperation(attributeConstraintBlackPatternInvocationBuilderEClass,
				ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HANDLE_ATTR_VARIABLE_REFERENCE__VARIABLE_ECLASSIFIER);

		attributeConstraintGreenPatternInvocationBuilderEClass = createEClass(
				ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER);
		createEOperation(attributeConstraintGreenPatternInvocationBuilderEClass,
				ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___VALIDATE_VARIABLE__ACTION_CFVARIABLE_OBJECTVARIABLE);
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
		ScopeValidationPackage theScopeValidationPackage = (ScopeValidationPackage) EPackage.Registry.INSTANCE
				.getEPackage(ScopeValidationPackage.eNS_URI);
		PatternsPackage thePatternsPackage = (PatternsPackage) EPackage.Registry.INSTANCE
				.getEPackage(PatternsPackage.eNS_URI);
		SpecificationPackage theSpecificationPackage = (SpecificationPackage) EPackage.Registry.INSTANCE
				.getEPackage(SpecificationPackage.eNS_URI);
		ControlFlowPackage theControlFlowPackage = (ControlFlowPackage) EPackage.Registry.INSTANCE
				.getEPackage(ControlFlowPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		attributeConstraintBlackPatternInvocationBuilderEClass.getESuperTypes()
				.add(theScopeValidationPackage.getBlackPatternBuilder());
		attributeConstraintGreenPatternInvocationBuilderEClass.getESuperTypes()
				.add(this.getAttributeConstraintBlackPatternInvocationBuilder());

		// Initialize classes, features, and operations; add parameters
		initEClass(attributeConstraintBlackPatternInvocationBuilderEClass,
				AttributeConstraintBlackPatternInvocationBuilder.class,
				"AttributeConstraintBlackPatternInvocationBuilder", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		EOperation op = initEOperation(
				getAttributeConstraintBlackPatternInvocationBuilder__AnalyzeDependencies__PatternInvocationBuilder_StoryPattern_Pattern(),
				null, "analyzeDependencies", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theScopeValidationPackage.getPatternInvocationBuilder(), "patternInvocationBuilder", 0, 1,
				IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePatternsPackage.getStoryPattern(), "storyPattern", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getPattern(), "pattern", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getAttributeConstraintBlackPatternInvocationBuilder__CallSuperAnalyzeDependencies__PatternInvocationBuilder_StoryPattern_Pattern(),
				null, "callSuperAnalyzeDependencies", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theScopeValidationPackage.getPatternInvocationBuilder(), "patternInvocationBuilder", 0, 1,
				IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePatternsPackage.getStoryPattern(), "storyPattern", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getPattern(), "pattern", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAttributeConstraintBlackPatternInvocationBuilder__Concat__String_int(),
				ecorePackage.getEString(), "concat", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "param1", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "param2", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getAttributeConstraintBlackPatternInvocationBuilder__CalcLocalVariableName__Variable(),
				ecorePackage.getEString(), "calcLocalVariableName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getVariable(), "variable", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getAttributeConstraintBlackPatternInvocationBuilder__HandleAttrVariableReference__Variable_EClassifier(),
				theControlFlowPackage.getVariableReference(), "handleAttrVariableReference", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theSpecificationPackage.getVariable(), "variable", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEClassifier(), "variableType", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(attributeConstraintGreenPatternInvocationBuilderEClass,
				AttributeConstraintGreenPatternInvocationBuilder.class,
				"AttributeConstraintGreenPatternInvocationBuilder", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		op = initEOperation(
				getAttributeConstraintGreenPatternInvocationBuilder__ValidateVariable__Action_CFVariable_ObjectVariable(),
				null, "validateVariable", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theControlFlowPackage.getAction(), "action", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theControlFlowPackage.getCFVariable(), "variable", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, thePatternsPackage.getObjectVariable(), "objectVariable", 0, 1, IS_UNIQUE, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //ScopevalidationPackageImpl
