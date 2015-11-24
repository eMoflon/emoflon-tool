/**
 */
package org.moflon.sdm.constraints.operationspecification;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationFactory
 * @model kind="package"
 * @generated
 */
public interface OperationspecificationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "org.moflon.sdm.constraints.operationspecification";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/plugin/org.moflon.sdm.constraints.operationspecification/model/Operationspecification.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.moflon.sdm.constraints.operationspecification";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	OperationspecificationPackage eINSTANCE = org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationImpl <em>Operation Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationImpl
	 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getOperationSpecification()
	 * @generated
	 */
	int OPERATION_SPECIFICATION = 0;

	/**
	 * The feature id for the '<em><b>Specification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION__SPECIFICATION = 0;

	/**
	 * The feature id for the '<em><b>Adornment String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION__ADORNMENT_STRING = 1;

	/**
	 * The feature id for the '<em><b>Always Successful</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION__ALWAYS_SUCCESSFUL = 2;

	/**
	 * The number of structural features of the '<em>Operation Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Operation Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.gervarro.democles.specification.ConstraintType <em>Constraint Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.gervarro.democles.specification.ConstraintType
	 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getConstraintType()
	 * @generated
	 */
	int CONSTRAINT_TYPE = 1;

	/**
	 * The number of structural features of the '<em>Constraint Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_TYPE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Constraint Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.operationspecification.impl.AttributeConstraintLibraryImpl <em>Attribute Constraint Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.operationspecification.impl.AttributeConstraintLibraryImpl
	 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getAttributeConstraintLibrary()
	 * @generated
	 */
	int ATTRIBUTE_CONSTRAINT_LIBRARY = 2;

	/**
	 * The feature id for the '<em><b>Operation Specifications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_LIBRARY__OPERATION_SPECIFICATIONS = 0;

	/**
	 * The feature id for the '<em><b>Constraint Specifications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_LIBRARY__CONSTRAINT_SPECIFICATIONS = 1;

	/**
	 * The feature id for the '<em><b>Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_LIBRARY__PREFIX = 2;

	/**
	 * The number of structural features of the '<em>Attribute Constraint Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_LIBRARY_FEATURE_COUNT = 3;

	/**
	 * The operation id for the '<em>Lookup Constraint Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_LIBRARY___LOOKUP_CONSTRAINT_TYPE__ATTRIBUTEVARIABLECONSTRAINT = 0;

	/**
	 * The number of operations of the '<em>Attribute Constraint Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_LIBRARY_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationGroupImpl <em>Operation Specification Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationGroupImpl
	 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getOperationSpecificationGroup()
	 * @generated
	 */
	int OPERATION_SPECIFICATION_GROUP = 3;

	/**
	 * The feature id for the '<em><b>Attribute Constraint Library</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY = 0;

	/**
	 * The feature id for the '<em><b>Constraint Specifications</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION_GROUP__CONSTRAINT_SPECIFICATIONS = 1;

	/**
	 * The feature id for the '<em><b>Operation Specifications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION_GROUP__OPERATION_SPECIFICATIONS = 2;

	/**
	 * The feature id for the '<em><b>Parameter IDs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION_GROUP__PARAMETER_IDS = 3;

	/**
	 * The feature id for the '<em><b>Operation Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION_GROUP__OPERATION_IDENTIFIER = 4;

	/**
	 * The feature id for the '<em><b>Template Group Generated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_GENERATED = 5;

	/**
	 * The feature id for the '<em><b>Template Group String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_STRING = 6;

	/**
	 * The number of structural features of the '<em>Operation Specification Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION_GROUP_FEATURE_COUNT = 7;

	/**
	 * The operation id for the '<em>Gernerate Template</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION_GROUP___GERNERATE_TEMPLATE = 0;

	/**
	 * The number of operations of the '<em>Operation Specification Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SPECIFICATION_GROUP_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.operationspecification.impl.ParamIdentifierImpl <em>Param Identifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.operationspecification.impl.ParamIdentifierImpl
	 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getParamIdentifier()
	 * @generated
	 */
	int PARAM_IDENTIFIER = 4;

	/**
	 * The feature id for the '<em><b>Operation Specification Group</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP = 0;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM_IDENTIFIER__IDENTIFIER = 1;

	/**
	 * The number of structural features of the '<em>Param Identifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM_IDENTIFIER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Param Identifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM_IDENTIFIER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.operationspecification.impl.ConstraintSpecificationImpl <em>Constraint Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.operationspecification.impl.ConstraintSpecificationImpl
	 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getConstraintSpecification()
	 * @generated
	 */
	int CONSTRAINT_SPECIFICATION = 5;

	/**
	 * The feature id for the '<em><b>Attribute Constraint Library</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY = CONSTRAINT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameter Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_SPECIFICATION__PARAMETER_TYPES = CONSTRAINT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operation Specification Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_SPECIFICATION__OPERATION_SPECIFICATION_GROUP = CONSTRAINT_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_SPECIFICATION__SYMBOL = CONSTRAINT_TYPE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Constraint Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_SPECIFICATION_FEATURE_COUNT = CONSTRAINT_TYPE_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Constraint Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_SPECIFICATION_OPERATION_COUNT = CONSTRAINT_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.operationspecification.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.operationspecification.impl.ParameterTypeImpl
	 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getParameterType()
	 * @generated
	 */
	int PARAMETER_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE__TYPE = 0;

	/**
	 * The number of structural features of the '<em>Parameter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Parameter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecification <em>Operation Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation Specification</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecification
	 * @generated
	 */
	EClass getOperationSpecification();

	/**
	 * Returns the meta object for the attribute '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecification#getSpecification <em>Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specification</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecification#getSpecification()
	 * @see #getOperationSpecification()
	 * @generated
	 */
	EAttribute getOperationSpecification_Specification();

	/**
	 * Returns the meta object for the attribute '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecification#getAdornmentString <em>Adornment String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Adornment String</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecification#getAdornmentString()
	 * @see #getOperationSpecification()
	 * @generated
	 */
	EAttribute getOperationSpecification_AdornmentString();

	/**
	 * Returns the meta object for the attribute '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecification#isAlwaysSuccessful <em>Always Successful</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Always Successful</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecification#isAlwaysSuccessful()
	 * @see #getOperationSpecification()
	 * @generated
	 */
	EAttribute getOperationSpecification_AlwaysSuccessful();

	/**
	 * Returns the meta object for class '{@link org.gervarro.democles.specification.ConstraintType <em>Constraint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint Type</em>'.
	 * @see org.gervarro.democles.specification.ConstraintType
	 * @model instanceClass="org.gervarro.democles.specification.ConstraintType"
	 * @generated
	 */
	EClass getConstraintType();

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary <em>Attribute Constraint Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Constraint Library</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary
	 * @generated
	 */
	EClass getAttributeConstraintLibrary();

	/**
	 * Returns the meta object for the containment reference list '{@link org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getOperationSpecifications <em>Operation Specifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operation Specifications</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getOperationSpecifications()
	 * @see #getAttributeConstraintLibrary()
	 * @generated
	 */
	EReference getAttributeConstraintLibrary_OperationSpecifications();

	/**
	 * Returns the meta object for the containment reference list '{@link org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getConstraintSpecifications <em>Constraint Specifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraint Specifications</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getConstraintSpecifications()
	 * @see #getAttributeConstraintLibrary()
	 * @generated
	 */
	EReference getAttributeConstraintLibrary_ConstraintSpecifications();

	/**
	 * Returns the meta object for the attribute '{@link org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getPrefix <em>Prefix</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Prefix</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getPrefix()
	 * @see #getAttributeConstraintLibrary()
	 * @generated
	 */
	EAttribute getAttributeConstraintLibrary_Prefix();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#lookupConstraintType(org.moflon.sdm.constraints.democles.AttributeVariableConstraint) <em>Lookup Constraint Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Lookup Constraint Type</em>' operation.
	 * @see org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#lookupConstraintType(org.moflon.sdm.constraints.democles.AttributeVariableConstraint)
	 * @generated
	 */
	EOperation getAttributeConstraintLibrary__LookupConstraintType__AttributeVariableConstraint();

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup <em>Operation Specification Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation Specification Group</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup
	 * @generated
	 */
	EClass getOperationSpecificationGroup();

	/**
	 * Returns the meta object for the container reference '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getAttributeConstraintLibrary <em>Attribute Constraint Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Attribute Constraint Library</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getAttributeConstraintLibrary()
	 * @see #getOperationSpecificationGroup()
	 * @generated
	 */
	EReference getOperationSpecificationGroup_AttributeConstraintLibrary();

	/**
	 * Returns the meta object for the reference list '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getConstraintSpecifications <em>Constraint Specifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Constraint Specifications</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getConstraintSpecifications()
	 * @see #getOperationSpecificationGroup()
	 * @generated
	 */
	EReference getOperationSpecificationGroup_ConstraintSpecifications();

	/**
	 * Returns the meta object for the containment reference list '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getOperationSpecifications <em>Operation Specifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operation Specifications</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getOperationSpecifications()
	 * @see #getOperationSpecificationGroup()
	 * @generated
	 */
	EReference getOperationSpecificationGroup_OperationSpecifications();

	/**
	 * Returns the meta object for the containment reference list '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getParameterIDs <em>Parameter IDs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter IDs</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getParameterIDs()
	 * @see #getOperationSpecificationGroup()
	 * @generated
	 */
	EReference getOperationSpecificationGroup_ParameterIDs();

	/**
	 * Returns the meta object for the attribute '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getOperationIdentifier <em>Operation Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operation Identifier</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getOperationIdentifier()
	 * @see #getOperationSpecificationGroup()
	 * @generated
	 */
	EAttribute getOperationSpecificationGroup_OperationIdentifier();

	/**
	 * Returns the meta object for the attribute '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#isTemplateGroupGenerated <em>Template Group Generated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Template Group Generated</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#isTemplateGroupGenerated()
	 * @see #getOperationSpecificationGroup()
	 * @generated
	 */
	EAttribute getOperationSpecificationGroup_TemplateGroupGenerated();

	/**
	 * Returns the meta object for the attribute '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getTemplateGroupString <em>Template Group String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Template Group String</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getTemplateGroupString()
	 * @see #getOperationSpecificationGroup()
	 * @generated
	 */
	EAttribute getOperationSpecificationGroup_TemplateGroupString();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#gernerateTemplate() <em>Gernerate Template</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Gernerate Template</em>' operation.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#gernerateTemplate()
	 * @generated
	 */
	EOperation getOperationSpecificationGroup__GernerateTemplate();

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.operationspecification.ParamIdentifier <em>Param Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Param Identifier</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.ParamIdentifier
	 * @generated
	 */
	EClass getParamIdentifier();

	/**
	 * Returns the meta object for the container reference '{@link org.moflon.sdm.constraints.operationspecification.ParamIdentifier#getOperationSpecificationGroup <em>Operation Specification Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Operation Specification Group</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.ParamIdentifier#getOperationSpecificationGroup()
	 * @see #getParamIdentifier()
	 * @generated
	 */
	EReference getParamIdentifier_OperationSpecificationGroup();

	/**
	 * Returns the meta object for the attribute '{@link org.moflon.sdm.constraints.operationspecification.ParamIdentifier#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.ParamIdentifier#getIdentifier()
	 * @see #getParamIdentifier()
	 * @generated
	 */
	EAttribute getParamIdentifier_Identifier();

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification <em>Constraint Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint Specification</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.ConstraintSpecification
	 * @generated
	 */
	EClass getConstraintSpecification();

	/**
	 * Returns the meta object for the container reference '{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getAttributeConstraintLibrary <em>Attribute Constraint Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Attribute Constraint Library</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getAttributeConstraintLibrary()
	 * @see #getConstraintSpecification()
	 * @generated
	 */
	EReference getConstraintSpecification_AttributeConstraintLibrary();

	/**
	 * Returns the meta object for the containment reference list '{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getParameterTypes <em>Parameter Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Types</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getParameterTypes()
	 * @see #getConstraintSpecification()
	 * @generated
	 */
	EReference getConstraintSpecification_ParameterTypes();

	/**
	 * Returns the meta object for the reference '{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getOperationSpecificationGroup <em>Operation Specification Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Operation Specification Group</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getOperationSpecificationGroup()
	 * @see #getConstraintSpecification()
	 * @generated
	 */
	EReference getConstraintSpecification_OperationSpecificationGroup();

	/**
	 * Returns the meta object for the attribute '{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getSymbol <em>Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Symbol</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getSymbol()
	 * @see #getConstraintSpecification()
	 * @generated
	 */
	EAttribute getConstraintSpecification_Symbol();

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.operationspecification.ParameterType <em>Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter Type</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.ParameterType
	 * @generated
	 */
	EClass getParameterType();

	/**
	 * Returns the meta object for the reference '{@link org.moflon.sdm.constraints.operationspecification.ParameterType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.moflon.sdm.constraints.operationspecification.ParameterType#getType()
	 * @see #getParameterType()
	 * @generated
	 */
	EReference getParameterType_Type();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	OperationspecificationFactory getOperationspecificationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationImpl <em>Operation Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationImpl
		 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getOperationSpecification()
		 * @generated
		 */
		EClass OPERATION_SPECIFICATION = eINSTANCE.getOperationSpecification();

		/**
		 * The meta object literal for the '<em><b>Specification</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_SPECIFICATION__SPECIFICATION = eINSTANCE.getOperationSpecification_Specification();

		/**
		 * The meta object literal for the '<em><b>Adornment String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_SPECIFICATION__ADORNMENT_STRING = eINSTANCE.getOperationSpecification_AdornmentString();

		/**
		 * The meta object literal for the '<em><b>Always Successful</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_SPECIFICATION__ALWAYS_SUCCESSFUL = eINSTANCE.getOperationSpecification_AlwaysSuccessful();

		/**
		 * The meta object literal for the '{@link org.gervarro.democles.specification.ConstraintType <em>Constraint Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.gervarro.democles.specification.ConstraintType
		 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getConstraintType()
		 * @generated
		 */
		EClass CONSTRAINT_TYPE = eINSTANCE.getConstraintType();

		/**
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.operationspecification.impl.AttributeConstraintLibraryImpl <em>Attribute Constraint Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.operationspecification.impl.AttributeConstraintLibraryImpl
		 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getAttributeConstraintLibrary()
		 * @generated
		 */
		EClass ATTRIBUTE_CONSTRAINT_LIBRARY = eINSTANCE.getAttributeConstraintLibrary();

		/**
		 * The meta object literal for the '<em><b>Operation Specifications</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_CONSTRAINT_LIBRARY__OPERATION_SPECIFICATIONS = eINSTANCE
				.getAttributeConstraintLibrary_OperationSpecifications();

		/**
		 * The meta object literal for the '<em><b>Constraint Specifications</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_CONSTRAINT_LIBRARY__CONSTRAINT_SPECIFICATIONS = eINSTANCE
				.getAttributeConstraintLibrary_ConstraintSpecifications();

		/**
		 * The meta object literal for the '<em><b>Prefix</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_CONSTRAINT_LIBRARY__PREFIX = eINSTANCE.getAttributeConstraintLibrary_Prefix();

		/**
		 * The meta object literal for the '<em><b>Lookup Constraint Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_LIBRARY___LOOKUP_CONSTRAINT_TYPE__ATTRIBUTEVARIABLECONSTRAINT = eINSTANCE
				.getAttributeConstraintLibrary__LookupConstraintType__AttributeVariableConstraint();

		/**
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationGroupImpl <em>Operation Specification Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationSpecificationGroupImpl
		 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getOperationSpecificationGroup()
		 * @generated
		 */
		EClass OPERATION_SPECIFICATION_GROUP = eINSTANCE.getOperationSpecificationGroup();

		/**
		 * The meta object literal for the '<em><b>Attribute Constraint Library</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_SPECIFICATION_GROUP__ATTRIBUTE_CONSTRAINT_LIBRARY = eINSTANCE
				.getOperationSpecificationGroup_AttributeConstraintLibrary();

		/**
		 * The meta object literal for the '<em><b>Constraint Specifications</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_SPECIFICATION_GROUP__CONSTRAINT_SPECIFICATIONS = eINSTANCE
				.getOperationSpecificationGroup_ConstraintSpecifications();

		/**
		 * The meta object literal for the '<em><b>Operation Specifications</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_SPECIFICATION_GROUP__OPERATION_SPECIFICATIONS = eINSTANCE
				.getOperationSpecificationGroup_OperationSpecifications();

		/**
		 * The meta object literal for the '<em><b>Parameter IDs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_SPECIFICATION_GROUP__PARAMETER_IDS = eINSTANCE
				.getOperationSpecificationGroup_ParameterIDs();

		/**
		 * The meta object literal for the '<em><b>Operation Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_SPECIFICATION_GROUP__OPERATION_IDENTIFIER = eINSTANCE
				.getOperationSpecificationGroup_OperationIdentifier();

		/**
		 * The meta object literal for the '<em><b>Template Group Generated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_GENERATED = eINSTANCE
				.getOperationSpecificationGroup_TemplateGroupGenerated();

		/**
		 * The meta object literal for the '<em><b>Template Group String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_SPECIFICATION_GROUP__TEMPLATE_GROUP_STRING = eINSTANCE
				.getOperationSpecificationGroup_TemplateGroupString();

		/**
		 * The meta object literal for the '<em><b>Gernerate Template</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation OPERATION_SPECIFICATION_GROUP___GERNERATE_TEMPLATE = eINSTANCE
				.getOperationSpecificationGroup__GernerateTemplate();

		/**
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.operationspecification.impl.ParamIdentifierImpl <em>Param Identifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.operationspecification.impl.ParamIdentifierImpl
		 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getParamIdentifier()
		 * @generated
		 */
		EClass PARAM_IDENTIFIER = eINSTANCE.getParamIdentifier();

		/**
		 * The meta object literal for the '<em><b>Operation Specification Group</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAM_IDENTIFIER__OPERATION_SPECIFICATION_GROUP = eINSTANCE
				.getParamIdentifier_OperationSpecificationGroup();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAM_IDENTIFIER__IDENTIFIER = eINSTANCE.getParamIdentifier_Identifier();

		/**
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.operationspecification.impl.ConstraintSpecificationImpl <em>Constraint Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.operationspecification.impl.ConstraintSpecificationImpl
		 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getConstraintSpecification()
		 * @generated
		 */
		EClass CONSTRAINT_SPECIFICATION = eINSTANCE.getConstraintSpecification();

		/**
		 * The meta object literal for the '<em><b>Attribute Constraint Library</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_SPECIFICATION__ATTRIBUTE_CONSTRAINT_LIBRARY = eINSTANCE
				.getConstraintSpecification_AttributeConstraintLibrary();

		/**
		 * The meta object literal for the '<em><b>Parameter Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_SPECIFICATION__PARAMETER_TYPES = eINSTANCE.getConstraintSpecification_ParameterTypes();

		/**
		 * The meta object literal for the '<em><b>Operation Specification Group</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_SPECIFICATION__OPERATION_SPECIFICATION_GROUP = eINSTANCE
				.getConstraintSpecification_OperationSpecificationGroup();

		/**
		 * The meta object literal for the '<em><b>Symbol</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT_SPECIFICATION__SYMBOL = eINSTANCE.getConstraintSpecification_Symbol();

		/**
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.operationspecification.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.operationspecification.impl.ParameterTypeImpl
		 * @see org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationPackageImpl#getParameterType()
		 * @generated
		 */
		EClass PARAMETER_TYPE = eINSTANCE.getParameterType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER_TYPE__TYPE = eINSTANCE.getParameterType_Type();

	}

} //OperationspecificationPackage
