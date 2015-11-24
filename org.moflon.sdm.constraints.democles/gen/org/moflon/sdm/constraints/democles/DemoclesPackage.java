/**
 */
package org.moflon.sdm.constraints.democles;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.gervarro.democles.specification.emf.SpecificationPackage;

import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypePackage;

import org.gervarro.democles.specification.emf.constraint.relational.RelationalConstraintPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.moflon.sdm.constraints.democles.DemoclesFactory
 * @model kind="package"
 * @generated
 */
public class DemoclesPackage extends EPackageImpl {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "org.moflon.sdm.constraints.democles";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "platform:/plugin/org.moflon.sdm.constraints.democles/model/Democles.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "org.moflon.sdm.constraints.democles";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final DemoclesPackage eINSTANCE = org.moflon.sdm.constraints.democles.DemoclesPackage.init();

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.democles.AttributeVariableConstraint <em>Attribute Variable Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.democles.AttributeVariableConstraint
	 * @see org.moflon.sdm.constraints.democles.DemoclesPackage#getAttributeVariableConstraint()
	 * @generated
	 */
	public static final int ATTRIBUTE_VARIABLE_CONSTRAINT = 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_VARIABLE_CONSTRAINT__PARAMETERS = SpecificationPackage.CONSTRAINT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Predicate Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_VARIABLE_CONSTRAINT__PREDICATE_SYMBOL = SpecificationPackage.CONSTRAINT_FEATURE_COUNT
			+ 0;

	/**
	 * The number of structural features of the '<em>Attribute Variable Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_VARIABLE_CONSTRAINT_FEATURE_COUNT = SpecificationPackage.CONSTRAINT_FEATURE_COUNT
			+ 1;

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.democles.AttributeValueConstraint <em>Attribute Value Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.democles.AttributeValueConstraint
	 * @see org.moflon.sdm.constraints.democles.DemoclesPackage#getAttributeValueConstraint()
	 * @generated
	 */
	public static final int ATTRIBUTE_VALUE_CONSTRAINT = 1;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_VALUE_CONSTRAINT__PARAMETERS = EMFTypePackage.EMF_CONSTRAINT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>EModel Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_VALUE_CONSTRAINT__EMODEL_ELEMENT = EMFTypePackage.EMF_CONSTRAINT__EMODEL_ELEMENT;

	/**
	 * The number of structural features of the '<em>Attribute Value Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_VALUE_CONSTRAINT_FEATURE_COUNT = EMFTypePackage.EMF_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.democles.TypedConstant <em>Typed Constant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.democles.TypedConstant
	 * @see org.moflon.sdm.constraints.democles.DemoclesPackage#getTypedConstant()
	 * @generated
	 */
	public static final int TYPED_CONSTANT = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TYPED_CONSTANT__VALUE = SpecificationPackage.CONSTANT__VALUE;

	/**
	 * The feature id for the '<em><b>EClassifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TYPED_CONSTANT__ECLASSIFIER = SpecificationPackage.CONSTANT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Typed Constant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TYPED_CONSTANT_FEATURE_COUNT = SpecificationPackage.CONSTANT_FEATURE_COUNT + 1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeVariableConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeValueConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typedConstantEClass = null;

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
	 * @see org.moflon.sdm.constraints.democles.DemoclesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DemoclesPackage() {
		super(eNS_URI, DemoclesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link DemoclesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DemoclesPackage init() {
		if (isInited)
			return (DemoclesPackage) EPackage.Registry.INSTANCE.getEPackage(DemoclesPackage.eNS_URI);

		// Obtain or create and register package
		DemoclesPackage theDemoclesPackage = (DemoclesPackage) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof DemoclesPackage ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new DemoclesPackage());

		isInited = true;

		// Initialize simple dependencies
		EMFTypePackage.eINSTANCE.eClass();
		RelationalConstraintPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDemoclesPackage.createPackageContents();

		// Initialize created meta-data
		theDemoclesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDemoclesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DemoclesPackage.eNS_URI, theDemoclesPackage);
		return theDemoclesPackage;
	}

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.democles.AttributeVariableConstraint <em>Attribute Variable Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Variable Constraint</em>'.
	 * @see org.moflon.sdm.constraints.democles.AttributeVariableConstraint
	 * @generated
	 */
	public EClass getAttributeVariableConstraint() {
		return attributeVariableConstraintEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.moflon.sdm.constraints.democles.AttributeVariableConstraint#getPredicateSymbol <em>Predicate Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Predicate Symbol</em>'.
	 * @see org.moflon.sdm.constraints.democles.AttributeVariableConstraint#getPredicateSymbol()
	 * @see #getAttributeVariableConstraint()
	 * @generated
	 */
	public EAttribute getAttributeVariableConstraint_PredicateSymbol() {
		return (EAttribute) attributeVariableConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.democles.AttributeValueConstraint <em>Attribute Value Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Value Constraint</em>'.
	 * @see org.moflon.sdm.constraints.democles.AttributeValueConstraint
	 * @generated
	 */
	public EClass getAttributeValueConstraint() {
		return attributeValueConstraintEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.democles.TypedConstant <em>Typed Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Constant</em>'.
	 * @see org.moflon.sdm.constraints.democles.TypedConstant
	 * @generated
	 */
	public EClass getTypedConstant() {
		return typedConstantEClass;
	}

	/**
	 * Returns the meta object for the reference '{@link org.moflon.sdm.constraints.democles.TypedConstant#getEClassifier <em>EClassifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>EClassifier</em>'.
	 * @see org.moflon.sdm.constraints.democles.TypedConstant#getEClassifier()
	 * @see #getTypedConstant()
	 * @generated
	 */
	public EReference getTypedConstant_EClassifier() {
		return (EReference) typedConstantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public DemoclesFactory getDemoclesFactory() {
		return (DemoclesFactory) getEFactoryInstance();
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
		attributeVariableConstraintEClass = createEClass(ATTRIBUTE_VARIABLE_CONSTRAINT);
		createEAttribute(attributeVariableConstraintEClass, ATTRIBUTE_VARIABLE_CONSTRAINT__PREDICATE_SYMBOL);

		attributeValueConstraintEClass = createEClass(ATTRIBUTE_VALUE_CONSTRAINT);

		typedConstantEClass = createEClass(TYPED_CONSTANT);
		createEReference(typedConstantEClass, TYPED_CONSTANT__ECLASSIFIER);
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
		SpecificationPackage theSpecificationPackage = (SpecificationPackage) EPackage.Registry.INSTANCE
				.getEPackage(SpecificationPackage.eNS_URI);
		EMFTypePackage theEMFTypePackage = (EMFTypePackage) EPackage.Registry.INSTANCE
				.getEPackage(EMFTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		attributeVariableConstraintEClass.getESuperTypes().add(theSpecificationPackage.getConstraint());
		attributeValueConstraintEClass.getESuperTypes().add(theEMFTypePackage.getEMFConstraint());
		typedConstantEClass.getESuperTypes().add(theSpecificationPackage.getConstant());

		// Initialize classes and features; add operations and parameters
		initEClass(attributeVariableConstraintEClass, AttributeVariableConstraint.class, "AttributeVariableConstraint",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributeVariableConstraint_PredicateSymbol(), ecorePackage.getEString(), "predicateSymbol",
				null, 1, 1, AttributeVariableConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(attributeValueConstraintEClass, AttributeValueConstraint.class, "AttributeValueConstraint",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(typedConstantEClass, TypedConstant.class, "TypedConstant", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypedConstant_EClassifier(), ecorePackage.getEClassifier(), null, "eClassifier", null, 0, 1,
				TypedConstant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public interface Literals {
		/**
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.democles.AttributeVariableConstraint <em>Attribute Variable Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.democles.AttributeVariableConstraint
		 * @see org.moflon.sdm.constraints.democles.DemoclesPackage#getAttributeVariableConstraint()
		 * @generated
		 */
		public static final EClass ATTRIBUTE_VARIABLE_CONSTRAINT = eINSTANCE.getAttributeVariableConstraint();

		/**
		 * The meta object literal for the '<em><b>Predicate Symbol</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute ATTRIBUTE_VARIABLE_CONSTRAINT__PREDICATE_SYMBOL = eINSTANCE
				.getAttributeVariableConstraint_PredicateSymbol();

		/**
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.democles.AttributeValueConstraint <em>Attribute Value Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.democles.AttributeValueConstraint
		 * @see org.moflon.sdm.constraints.democles.DemoclesPackage#getAttributeValueConstraint()
		 * @generated
		 */
		public static final EClass ATTRIBUTE_VALUE_CONSTRAINT = eINSTANCE.getAttributeValueConstraint();

		/**
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.democles.TypedConstant <em>Typed Constant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.democles.TypedConstant
		 * @see org.moflon.sdm.constraints.democles.DemoclesPackage#getTypedConstant()
		 * @generated
		 */
		public static final EClass TYPED_CONSTANT = eINSTANCE.getTypedConstant();

		/**
		 * The meta object literal for the '<em><b>EClassifier</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference TYPED_CONSTANT__ECLASSIFIER = eINSTANCE.getTypedConstant_EClassifier();

	}

} //DemoclesPackage
