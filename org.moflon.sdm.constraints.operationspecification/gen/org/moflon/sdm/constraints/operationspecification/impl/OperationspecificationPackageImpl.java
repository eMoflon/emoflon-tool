/**
 */
package org.moflon.sdm.constraints.operationspecification.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.gervarro.democles.specification.ConstraintType;

import org.moflon.sdm.compiler.democles.validation.result.ResultPackage;

import org.moflon.sdm.constraints.democles.DemoclesPackage;

import org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary;
import org.moflon.sdm.constraints.operationspecification.ConstraintSpecification;
import org.moflon.sdm.constraints.operationspecification.OperationSpecification;
import org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup;
import org.moflon.sdm.constraints.operationspecification.OperationspecificationFactory;
import org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage;
import org.moflon.sdm.constraints.operationspecification.ParamIdentifier;
import org.moflon.sdm.constraints.operationspecification.ParameterType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class OperationspecificationPackageImpl extends EPackageImpl implements OperationspecificationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constraintTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeConstraintLibraryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationSpecificationGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass paramIdentifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constraintSpecificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterTypeEClass = null;

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
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private OperationspecificationPackageImpl() {
		super(eNS_URI, OperationspecificationFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link OperationspecificationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static OperationspecificationPackage init() {
		if (isInited)
			return (OperationspecificationPackage) EPackage.Registry.INSTANCE
					.getEPackage(OperationspecificationPackage.eNS_URI);

		// Obtain or create and register package
		OperationspecificationPackageImpl theOperationspecificationPackage = (OperationspecificationPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof OperationspecificationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new OperationspecificationPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		DemoclesPackage.eINSTANCE.eClass();
		ResultPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theOperationspecificationPackage.createPackageContents();

		// Initialize created meta-data
		theOperationspecificationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theOperationspecificationPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(OperationspecificationPackage.eNS_URI, theOperationspecificationPackage);
		return theOperationspecificationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationSpecification() {
		return operationSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationSpecification_Specification() {
		return (EAttribute) operationSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationSpecification_AdornmentString() {
		return (EAttribute) operationSpecificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationSpecification_AlwaysSuccessful() {
		return (EAttribute) operationSpecificationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConstraintType() {
		return constraintTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeConstraintLibrary() {
		return attributeConstraintLibraryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeConstraintLibrary_OperationSpecifications() {
		return (EReference) attributeConstraintLibraryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeConstraintLibrary_ConstraintSpecifications() {
		return (EReference) attributeConstraintLibraryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeConstraintLibrary_Prefix() {
		return (EAttribute) attributeConstraintLibraryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAttributeConstraintLibrary__LookupConstraintType__AttributeVariableConstraint() {
		return attributeConstraintLibraryEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationSpecificationGroup() {
		return operationSpecificationGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationSpecificationGroup_AttributeConstraintLibrary() {
		return (EReference) operationSpecificationGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationSpecificationGroup_ConstraintSpecifications() {
		return (EReference) operationSpecificationGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationSpecificationGroup_OperationSpecifications() {
		return (EReference) operationSpecificationGroupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationSpecificationGroup_ParameterIDs() {
		return (EReference) operationSpecificationGroupEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationSpecificationGroup_OperationIdentifier() {
		return (EAttribute) operationSpecificationGroupEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationSpecificationGroup_TemplateGroupGenerated() {
		return (EAttribute) operationSpecificationGroupEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationSpecificationGroup_TemplateGroupString() {
		return (EAttribute) operationSpecificationGroupEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getOperationSpecificationGroup__GernerateTemplate() {
		return operationSpecificationGroupEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParamIdentifier() {
		return paramIdentifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParamIdentifier_OperationSpecificationGroup() {
		return (EReference) paramIdentifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParamIdentifier_Identifier() {
		return (EAttribute) paramIdentifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConstraintSpecification() {
		return constraintSpecificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstraintSpecification_AttributeConstraintLibrary() {
		return (EReference) constraintSpecificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstraintSpecification_ParameterTypes() {
		return (EReference) constraintSpecificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConstraintSpecification_OperationSpecificationGroup() {
		return (EReference) constraintSpecificationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConstraintSpecification_Symbol() {
		return (EAttribute) constraintSpecificationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameterType() {
		return parameterTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameterType_Type() {
		return (EReference) parameterTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationspecificationFactory getOperationspecificationFactory() {
		return (OperationspecificationFactory) getEFactoryInstance();
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
		operationSpecificationEClass = createEClass(OPERATION_SPECIFICATION);
		createEAttribute(operationSpecificationEClass, OPERATION_SPECIFICATION__SPECIFICATION);
		createEAttribute(operationSpecificationEClass, OPERATION_SPECIFICATION__ADORNMENT_STRING);
		createEAttribute(operationSpecificationEClass, OPERATION_SPECIFICATION__ALWAYS_SUCCESSFUL);

		constraintTypeEClass = createEClass(CONSTRAINT_TYPE);

		attributeConstraintLibraryEClass = createEClass(ATTRIBUTE_CONSTRAINT_LIBRARY);
		createEReference(attributeConstraintLibraryEClass, ATTRIBUTE_CONSTRAINT_LIBRARY__OPERATION_SPECIFICATIONS);
		createEReference(attributeConstraintLibraryEClass, ATTRIBUTE_CONSTRAINT_LIBRARY__CONSTRAINT_SPECIFICATIONS);
		createEAttribute(attributeConstraintLibraryEClass, ATTRIBUTE_CONSTRAINT_LIBRARY__PREFIX);
		createEOperation(attributeConstraintLibraryEClass,
				ATTRIBUTE_CONSTRAINT_LIBRARY___LOOKUP_CONSTRAINT_TYPE__ATTRIBUTEVARIABLECONSTRAINT);

		operationSpecificationGroupEClass = createEClass(OPERATION_SPECIFICATION_GROUP);
		createEReference(operationSpecificationGroupEClass,
				OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY);
		createEReference(operationSpecificationGroupEClass, OPERATION_SPECIFICATION_GROUP__CONSTRAINT_SPECIFICATIONS);
		createEReference(operationSpecificationGroupEClass, OPERATION_SPECIFICATION_GROUP__OPERATION_SPECIFICATIONS);
		createEReference(operationSpecificationGroupEClass, OPERATION_SPECIFICATION_GROUP__PARAMETER_IDS);
		createEAttribute(operationSpecificationGroupEClass, OPERATION_SPECIFICATION_GROUP__OPERATION_IDENTIFIER);
		createEAttribute(operationSpecificationGroupEClass, OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_GENERATED);
		createEAttribute(operationSpecificationGroupEClass, OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_STRING);
		createEOperation(operationSpecificationGroupEClass, OPERATION_SPECIFICATION_GROUP___GERNERATE_TEMPLATE);

		paramIdentifierEClass = createEClass(PARAM_IDENTIFIER);
		createEReference(paramIdentifierEClass, PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP);
		createEAttribute(paramIdentifierEClass, PARAM_IDENTIFIER__IDENTIFIER);

		constraintSpecificationEClass = createEClass(CONSTRAINT_SPECIFICATION);
		createEReference(constraintSpecificationEClass, CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY);
		createEReference(constraintSpecificationEClass, CONSTRAINT_SPECIFICATION__PARAMETER_TYPES);
		createEReference(constraintSpecificationEClass, CONSTRAINT_SPECIFICATION__OPERATION_SPECIFICATION_GROUP);
		createEAttribute(constraintSpecificationEClass, CONSTRAINT_SPECIFICATION__SYMBOL);

		parameterTypeEClass = createEClass(PARAMETER_TYPE);
		createEReference(parameterTypeEClass, PARAMETER_TYPE__TYPE);
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
		DemoclesPackage theDemoclesPackage = (DemoclesPackage) EPackage.Registry.INSTANCE
				.getEPackage(DemoclesPackage.eNS_URI);
		ResultPackage theResultPackage = (ResultPackage) EPackage.Registry.INSTANCE.getEPackage(ResultPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		constraintSpecificationEClass.getESuperTypes().add(this.getConstraintType());

		// Initialize classes, features, and operations; add parameters
		initEClass(operationSpecificationEClass, OperationSpecification.class, "OperationSpecification", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOperationSpecification_Specification(), ecorePackage.getEString(), "specification", null, 1,
				1, OperationSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getOperationSpecification_AdornmentString(), ecorePackage.getEString(), "adornmentString", null,
				1, 1, OperationSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getOperationSpecification_AlwaysSuccessful(), ecorePackage.getEBoolean(), "alwaysSuccessful",
				"true", 1, 1, OperationSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(constraintTypeEClass, ConstraintType.class, "ConstraintType", IS_ABSTRACT, IS_INTERFACE,
				!IS_GENERATED_INSTANCE_CLASS);

		initEClass(attributeConstraintLibraryEClass, AttributeConstraintLibrary.class, "AttributeConstraintLibrary",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeConstraintLibrary_OperationSpecifications(), this.getOperationSpecificationGroup(),
				this.getOperationSpecificationGroup_AttributeConstraintLibrary(), "operationSpecifications", null, 0,
				-1, AttributeConstraintLibrary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeConstraintLibrary_ConstraintSpecifications(), this.getConstraintSpecification(),
				this.getConstraintSpecification_AttributeConstraintLibrary(), "constraintSpecifications", null, 0, -1,
				AttributeConstraintLibrary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeConstraintLibrary_Prefix(), ecorePackage.getEString(), "prefix", null, 1, 1,
				AttributeConstraintLibrary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		EOperation op = initEOperation(
				getAttributeConstraintLibrary__LookupConstraintType__AttributeVariableConstraint(),
				this.getConstraintSpecification(), "lookupConstraintType", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theDemoclesPackage.getAttributeVariableConstraint(), "constraint", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		initEClass(operationSpecificationGroupEClass, OperationSpecificationGroup.class, "OperationSpecificationGroup",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOperationSpecificationGroup_AttributeConstraintLibrary(),
				this.getAttributeConstraintLibrary(), this.getAttributeConstraintLibrary_OperationSpecifications(),
				"attributeConstraintLibrary", null, 1, 1, OperationSpecificationGroup.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getOperationSpecificationGroup_ConstraintSpecifications(), this.getConstraintSpecification(),
				this.getConstraintSpecification_OperationSpecificationGroup(), "constraintSpecifications", null, 0, -1,
				OperationSpecificationGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOperationSpecificationGroup_OperationSpecifications(), this.getOperationSpecification(), null,
				"operationSpecifications", null, 0, -1, OperationSpecificationGroup.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOperationSpecificationGroup_ParameterIDs(), this.getParamIdentifier(),
				this.getParamIdentifier_OperationSpecificationGroup(), "parameterIDs", null, 0, -1,
				OperationSpecificationGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOperationSpecificationGroup_OperationIdentifier(), ecorePackage.getEString(),
				"operationIdentifier", null, 1, 1, OperationSpecificationGroup.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getOperationSpecificationGroup_TemplateGroupGenerated(), ecorePackage.getEBoolean(),
				"templateGroupGenerated", null, 1, 1, OperationSpecificationGroup.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getOperationSpecificationGroup_TemplateGroupString(), ecorePackage.getEString(),
				"templateGroupString", null, 1, 1, OperationSpecificationGroup.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEOperation(getOperationSpecificationGroup__GernerateTemplate(), theResultPackage.getErrorMessage(),
				"gernerateTemplate", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(paramIdentifierEClass, ParamIdentifier.class, "ParamIdentifier", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParamIdentifier_OperationSpecificationGroup(), this.getOperationSpecificationGroup(),
				this.getOperationSpecificationGroup_ParameterIDs(), "operationSpecificationGroup", null, 1, 1,
				ParamIdentifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParamIdentifier_Identifier(), ecorePackage.getEString(), "identifier", null, 1, 1,
				ParamIdentifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, !IS_ORDERED);

		initEClass(constraintSpecificationEClass, ConstraintSpecification.class, "ConstraintSpecification",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConstraintSpecification_AttributeConstraintLibrary(), this.getAttributeConstraintLibrary(),
				this.getAttributeConstraintLibrary_ConstraintSpecifications(), "attributeConstraintLibrary", null, 1, 1,
				ConstraintSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstraintSpecification_ParameterTypes(), this.getParameterType(), null, "parameterTypes",
				null, 0, -1, ConstraintSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstraintSpecification_OperationSpecificationGroup(), this.getOperationSpecificationGroup(),
				this.getOperationSpecificationGroup_ConstraintSpecifications(), "operationSpecificationGroup", null, 1,
				1, ConstraintSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConstraintSpecification_Symbol(), ecorePackage.getEString(), "symbol", null, 1, 1,
				ConstraintSpecification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(parameterTypeEClass, ParameterType.class, "ParameterType", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParameterType_Type(), ecorePackage.getEClassifier(), null, "type", null, 0, 1,
				ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //OperationspecificationPackageImpl
