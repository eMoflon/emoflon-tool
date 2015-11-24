/**
 */
package org.moflon.sdm.constraints.constraintstodemocles;

import SDMLanguageToDemocles.SDMLanguageToDemoclesPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

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
 * @see org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesFactory
 * @model kind="package"
 * @generated
 */
public interface ConstraintstodemoclesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "org.moflon.sdm.constraints.constraintstodemocles";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/plugin/org.moflon.sdm.constraints.constraintstodemocles/model/Constraintstodemocles.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.moflon.sdm.constraints.constraintstodemocles";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConstraintstodemoclesPackage eINSTANCE = org.moflon.sdm.constraints.constraintstodemocles.impl.ConstraintstodemoclesPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.constraintstodemocles.impl.AttributeConstraintBlackAndNacPatternTransformerImpl <em>Attribute Constraint Black And Nac Pattern Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.AttributeConstraintBlackAndNacPatternTransformerImpl
	 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.ConstraintstodemoclesPackageImpl#getAttributeConstraintBlackAndNacPatternTransformer()
	 * @generated
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER = 0;

	/**
	 * The feature id for the '<em><b>Validation Report</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER__VALIDATION_REPORT = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER__VALIDATION_REPORT;

	/**
	 * The feature id for the '<em><b>Expression Transformer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER__EXPRESSION_TRANSFORMER = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER__EXPRESSION_TRANSFORMER;

	/**
	 * The number of structural features of the '<em>Attribute Constraint Black And Nac Pattern Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER_FEATURE_COUNT = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER_FEATURE_COUNT
			+ 0;

	/**
	 * The operation id for the '<em>Concat</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___CONCAT__STRING_STRING = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___CONCAT__STRING_STRING;

	/**
	 * The operation id for the '<em>Get Attribute Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___GET_ATTRIBUTE_VARIABLE__OBJECTVARIABLE_EATTRIBUTE_PATTERN = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___GET_ATTRIBUTE_VARIABLE__OBJECTVARIABLE_EATTRIBUTE_PATTERN;

	/**
	 * The operation id for the '<em>Lookup Variable In Pattern</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___LOOKUP_VARIABLE_IN_PATTERN__PATTERN_STRING_ECLASSIFIER = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___LOOKUP_VARIABLE_IN_PATTERN__PATTERN_STRING_ECLASSIFIER;

	/**
	 * The operation id for the '<em>Add Link To Pattern</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___ADD_LINK_TO_PATTERN__LINKVARIABLE_PATTERN = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___ADD_LINK_TO_PATTERN__LINKVARIABLE_PATTERN;

	/**
	 * The operation id for the '<em>Get EReference</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___GET_EREFERENCE__LINKVARIABLE = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___GET_EREFERENCE__LINKVARIABLE;

	/**
	 * The operation id for the '<em>Has Symbolic Parameter</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___HAS_SYMBOLIC_PARAMETER__PATTERN_STRING = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___HAS_SYMBOLIC_PARAMETER__PATTERN_STRING;

	/**
	 * The operation id for the '<em>Process Link Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_LINK_VARIABLES__STORYPATTERN_PATTERN = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_LINK_VARIABLES__STORYPATTERN_PATTERN;

	/**
	 * The operation id for the '<em>Transform</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___TRANSFORM__STORYPATTERN_PATTERN = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___TRANSFORM__STORYPATTERN_PATTERN;

	/**
	 * The operation id for the '<em>Analyze Injectivity</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___ANALYZE_INJECTIVITY__PATTERN = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___ANALYZE_INJECTIVITY__PATTERN;

	/**
	 * The operation id for the '<em>Are Compatible Types</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___ARE_COMPATIBLE_TYPES__ECLASSIFIER_ECLASSIFIER = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___ARE_COMPATIBLE_TYPES__ECLASSIFIER_ECLASSIFIER;

	/**
	 * The operation id for the '<em>Create Injectivity Constraint</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___CREATE_INJECTIVITY_CONSTRAINT__PATTERN_VARIABLE_VARIABLE = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___CREATE_INJECTIVITY_CONSTRAINT__PATTERN_VARIABLE_VARIABLE;

	/**
	 * The operation id for the '<em>Create Relational Constraint</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___CREATE_RELATIONAL_CONSTRAINT__COMPARISONEXPRESSION = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___CREATE_RELATIONAL_CONSTRAINT__COMPARISONEXPRESSION;

	/**
	 * The operation id for the '<em>Injective Mapping Required</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___INJECTIVE_MAPPING_REQUIRED__VARIABLE_VARIABLE = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___INJECTIVE_MAPPING_REQUIRED__VARIABLE_VARIABLE;

	/**
	 * The operation id for the '<em>Is Link To Add</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___IS_LINK_TO_ADD__LINKVARIABLE_PATTERN = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___IS_LINK_TO_ADD__LINKVARIABLE_PATTERN;

	/**
	 * The operation id for the '<em>Is Variable To Add</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___IS_VARIABLE_TO_ADD__OBJECTVARIABLE = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___IS_VARIABLE_TO_ADD__OBJECTVARIABLE;

	/**
	 * The operation id for the '<em>Transform Attribute Constraint</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___TRANSFORM_ATTRIBUTE_CONSTRAINT__CONSTRAINT_PATTERN = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER___TRANSFORM_ATTRIBUTE_CONSTRAINT__CONSTRAINT_PATTERN;

	/**
	 * The operation id for the '<em>Postprocess</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___POSTPROCESS__STORYPATTERN_PATTERN = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 0;

	/**
	 * The operation id for the '<em>Process Primitive Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_PRIMITIVE_VARIABLE__PRIMITIVEVARIABLE_PATTERN = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 1;

	/**
	 * The operation id for the '<em>Process Lookup Constraint</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_LOOKUP_CONSTRAINT__ATTRIBUTELOOKUPCONSTRAINT_PATTERN = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 2;

	/**
	 * The operation id for the '<em>Process Csp Constraint</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_CSP_CONSTRAINT__CSPCONSTRAINT_PATTERN = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 3;

	/**
	 * The operation id for the '<em>Lookup Variable By Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___LOOKUP_VARIABLE_BY_NAME__STRING_PATTERN = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 4;

	/**
	 * The operation id for the '<em>Call Super Postprocess</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___CALL_SUPER_POSTPROCESS__STORYPATTERN_PATTERN = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 5;

	/**
	 * The operation id for the '<em>Validate Csp Constraints</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___VALIDATE_CSP_CONSTRAINTS__ATTRIBUTEVARIABLECONSTRAINT_CSPCONSTRAINT = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 6;

	/**
	 * The operation id for the '<em>Update User Defined Constraint Lib</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___UPDATE_USER_DEFINED_CONSTRAINT_LIB__ATTRIBUTEVARIABLECONSTRAINT_ATTRIBUTECONSTRAINTLIBRARY = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 7;

	/**
	 * The operation id for the '<em>Validate Constraint Library</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___VALIDATE_CONSTRAINT_LIBRARY__ATTRIBUTECONSTRAINTLIBRARY_STRING = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 8;

	/**
	 * The operation id for the '<em>Create Error Message</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___CREATE_ERROR_MESSAGE__EOBJECT = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 9;

	/**
	 * The number of operations of the '<em>Attribute Constraint Black And Nac Pattern Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER_OPERATION_COUNT = SDMLanguageToDemoclesPackage.BLACK_AND_NAC_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 10;

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.constraintstodemocles.impl.AttributeConstraintGreenPatternTransformerImpl <em>Attribute Constraint Green Pattern Transformer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.AttributeConstraintGreenPatternTransformerImpl
	 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.ConstraintstodemoclesPackageImpl#getAttributeConstraintGreenPatternTransformer()
	 * @generated
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER = 1;

	/**
	 * The feature id for the '<em><b>Validation Report</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER__VALIDATION_REPORT = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER__VALIDATION_REPORT;

	/**
	 * The feature id for the '<em><b>Expression Transformer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER__EXPRESSION_TRANSFORMER = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER__EXPRESSION_TRANSFORMER;

	/**
	 * The number of structural features of the '<em>Attribute Constraint Green Pattern Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER_FEATURE_COUNT = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER_FEATURE_COUNT
			+ 0;

	/**
	 * The operation id for the '<em>Concat</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___CONCAT__STRING_STRING = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER___CONCAT__STRING_STRING;

	/**
	 * The operation id for the '<em>Get Attribute Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___GET_ATTRIBUTE_VARIABLE__OBJECTVARIABLE_EATTRIBUTE_PATTERN = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER___GET_ATTRIBUTE_VARIABLE__OBJECTVARIABLE_EATTRIBUTE_PATTERN;

	/**
	 * The operation id for the '<em>Lookup Variable In Pattern</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___LOOKUP_VARIABLE_IN_PATTERN__PATTERN_STRING_ECLASSIFIER = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER___LOOKUP_VARIABLE_IN_PATTERN__PATTERN_STRING_ECLASSIFIER;

	/**
	 * The operation id for the '<em>Add Link To Pattern</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___ADD_LINK_TO_PATTERN__LINKVARIABLE_PATTERN = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER___ADD_LINK_TO_PATTERN__LINKVARIABLE_PATTERN;

	/**
	 * The operation id for the '<em>Get EReference</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___GET_EREFERENCE__LINKVARIABLE = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER___GET_EREFERENCE__LINKVARIABLE;

	/**
	 * The operation id for the '<em>Has Symbolic Parameter</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___HAS_SYMBOLIC_PARAMETER__PATTERN_STRING = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER___HAS_SYMBOLIC_PARAMETER__PATTERN_STRING;

	/**
	 * The operation id for the '<em>Process Link Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___PROCESS_LINK_VARIABLES__STORYPATTERN_PATTERN = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER___PROCESS_LINK_VARIABLES__STORYPATTERN_PATTERN;

	/**
	 * The operation id for the '<em>Transform</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___TRANSFORM__STORYPATTERN_PATTERN = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER___TRANSFORM__STORYPATTERN_PATTERN;

	/**
	 * The operation id for the '<em>Is Link To Add</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___IS_LINK_TO_ADD__LINKVARIABLE_PATTERN = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER___IS_LINK_TO_ADD__LINKVARIABLE_PATTERN;

	/**
	 * The operation id for the '<em>Transform Assignment</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___TRANSFORM_ASSIGNMENT__ATTRIBUTEASSIGNMENT_PATTERN = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER___TRANSFORM_ASSIGNMENT__ATTRIBUTEASSIGNMENT_PATTERN;

	/**
	 * The operation id for the '<em>Postprocess</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___POSTPROCESS__STORYPATTERN_PATTERN = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 0;

	/**
	 * The operation id for the '<em>Call Super Postprocess</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___CALL_SUPER_POSTPROCESS__STORYPATTERN_PATTERN = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 1;

	/**
	 * The operation id for the '<em>Process Assignment Constraint</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___PROCESS_ASSIGNMENT_CONSTRAINT__ASSIGNMENTCONSTRAINT_PATTERN = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 2;

	/**
	 * The operation id for the '<em>Lookup Variable By Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___LOOKUP_VARIABLE_BY_NAME__STRING_PATTERN = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 3;

	/**
	 * The operation id for the '<em>Is Variable To Add</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___IS_VARIABLE_TO_ADD__OBJECTVARIABLE = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 4;

	/**
	 * The operation id for the '<em>Call Super Is Variable To Add</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___CALL_SUPER_IS_VARIABLE_TO_ADD__OBJECTVARIABLE = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 5;

	/**
	 * The number of operations of the '<em>Attribute Constraint Green Pattern Transformer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER_OPERATION_COUNT = SDMLanguageToDemoclesPackage.GREEN_PATTERN_TRANSFORMER_OPERATION_COUNT
			+ 6;

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.constraintstodemocles.impl.IdentifyerHelperImpl <em>Identifyer Helper</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.IdentifyerHelperImpl
	 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.ConstraintstodemoclesPackageImpl#getIdentifyerHelper()
	 * @generated
	 */
	int IDENTIFYER_HELPER = 2;

	/**
	 * The number of structural features of the '<em>Identifyer Helper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFYER_HELPER_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Get Next Identifyer</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFYER_HELPER___GET_NEXT_IDENTIFYER = 0;

	/**
	 * The number of operations of the '<em>Identifyer Helper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFYER_HELPER_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.constraintstodemocles.Errors <em>Errors</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.constraintstodemocles.Errors
	 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.ConstraintstodemoclesPackageImpl#getErrors()
	 * @generated
	 */
	int ERRORS = 3;

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer <em>Attribute Constraint Black And Nac Pattern Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Constraint Black And Nac Pattern Transformer</em>'.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer
	 * @generated
	 */
	EClass getAttributeConstraintBlackAndNacPatternTransformer();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#postprocess(SDMLanguage.patterns.StoryPattern, org.gervarro.democles.specification.emf.Pattern) <em>Postprocess</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Postprocess</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#postprocess(SDMLanguage.patterns.StoryPattern, org.gervarro.democles.specification.emf.Pattern)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackAndNacPatternTransformer__Postprocess__StoryPattern_Pattern();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#processPrimitiveVariable(SDMLanguage.patterns.AttributeConstraints.PrimitiveVariable, org.gervarro.democles.specification.emf.Pattern) <em>Process Primitive Variable</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Process Primitive Variable</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#processPrimitiveVariable(SDMLanguage.patterns.AttributeConstraints.PrimitiveVariable, org.gervarro.democles.specification.emf.Pattern)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackAndNacPatternTransformer__ProcessPrimitiveVariable__PrimitiveVariable_Pattern();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#processLookupConstraint(SDMLanguage.patterns.AttributeConstraints.AttributeLookupConstraint, org.gervarro.democles.specification.emf.Pattern) <em>Process Lookup Constraint</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Process Lookup Constraint</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#processLookupConstraint(SDMLanguage.patterns.AttributeConstraints.AttributeLookupConstraint, org.gervarro.democles.specification.emf.Pattern)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackAndNacPatternTransformer__ProcessLookupConstraint__AttributeLookupConstraint_Pattern();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#processCspConstraint(SDMLanguage.patterns.AttributeConstraints.CspConstraint, org.gervarro.democles.specification.emf.Pattern) <em>Process Csp Constraint</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Process Csp Constraint</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#processCspConstraint(SDMLanguage.patterns.AttributeConstraints.CspConstraint, org.gervarro.democles.specification.emf.Pattern)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackAndNacPatternTransformer__ProcessCspConstraint__CspConstraint_Pattern();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#lookupVariableByName(java.lang.String, org.gervarro.democles.specification.emf.Pattern) <em>Lookup Variable By Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Lookup Variable By Name</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#lookupVariableByName(java.lang.String, org.gervarro.democles.specification.emf.Pattern)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackAndNacPatternTransformer__LookupVariableByName__String_Pattern();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#callSuperPostprocess(SDMLanguage.patterns.StoryPattern, org.gervarro.democles.specification.emf.Pattern) <em>Call Super Postprocess</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Call Super Postprocess</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#callSuperPostprocess(SDMLanguage.patterns.StoryPattern, org.gervarro.democles.specification.emf.Pattern)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackAndNacPatternTransformer__CallSuperPostprocess__StoryPattern_Pattern();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#validateCspConstraints(org.moflon.sdm.constraints.democles.AttributeVariableConstraint, SDMLanguage.patterns.AttributeConstraints.CspConstraint) <em>Validate Csp Constraints</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Validate Csp Constraints</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#validateCspConstraints(org.moflon.sdm.constraints.democles.AttributeVariableConstraint, SDMLanguage.patterns.AttributeConstraints.CspConstraint)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackAndNacPatternTransformer__ValidateCspConstraints__AttributeVariableConstraint_CspConstraint();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#updateUserDefinedConstraintLib(org.moflon.sdm.constraints.democles.AttributeVariableConstraint, org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary) <em>Update User Defined Constraint Lib</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Update User Defined Constraint Lib</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#updateUserDefinedConstraintLib(org.moflon.sdm.constraints.democles.AttributeVariableConstraint, org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackAndNacPatternTransformer__UpdateUserDefinedConstraintLib__AttributeVariableConstraint_AttributeConstraintLibrary();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#validateConstraintLibrary(org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary, java.lang.String) <em>Validate Constraint Library</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Validate Constraint Library</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#validateConstraintLibrary(org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary, java.lang.String)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackAndNacPatternTransformer__ValidateConstraintLibrary__AttributeConstraintLibrary_String();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#createErrorMessage(org.eclipse.emf.ecore.EObject) <em>Create Error Message</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Error Message</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintBlackAndNacPatternTransformer#createErrorMessage(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackAndNacPatternTransformer__CreateErrorMessage__EObject();

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer <em>Attribute Constraint Green Pattern Transformer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Constraint Green Pattern Transformer</em>'.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer
	 * @generated
	 */
	EClass getAttributeConstraintGreenPatternTransformer();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer#postprocess(SDMLanguage.patterns.StoryPattern, org.gervarro.democles.specification.emf.Pattern) <em>Postprocess</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Postprocess</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer#postprocess(SDMLanguage.patterns.StoryPattern, org.gervarro.democles.specification.emf.Pattern)
	 * @generated
	 */
	EOperation getAttributeConstraintGreenPatternTransformer__Postprocess__StoryPattern_Pattern();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer#callSuperPostprocess(SDMLanguage.patterns.StoryPattern, org.gervarro.democles.specification.emf.Pattern) <em>Call Super Postprocess</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Call Super Postprocess</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer#callSuperPostprocess(SDMLanguage.patterns.StoryPattern, org.gervarro.democles.specification.emf.Pattern)
	 * @generated
	 */
	EOperation getAttributeConstraintGreenPatternTransformer__CallSuperPostprocess__StoryPattern_Pattern();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer#processAssignmentConstraint(SDMLanguage.patterns.AttributeConstraints.AssignmentConstraint, org.gervarro.democles.specification.emf.Pattern) <em>Process Assignment Constraint</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Process Assignment Constraint</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer#processAssignmentConstraint(SDMLanguage.patterns.AttributeConstraints.AssignmentConstraint, org.gervarro.democles.specification.emf.Pattern)
	 * @generated
	 */
	EOperation getAttributeConstraintGreenPatternTransformer__ProcessAssignmentConstraint__AssignmentConstraint_Pattern();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer#lookupVariableByName(java.lang.String, org.gervarro.democles.specification.emf.Pattern) <em>Lookup Variable By Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Lookup Variable By Name</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer#lookupVariableByName(java.lang.String, org.gervarro.democles.specification.emf.Pattern)
	 * @generated
	 */
	EOperation getAttributeConstraintGreenPatternTransformer__LookupVariableByName__String_Pattern();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer#isVariableToAdd(SDMLanguage.patterns.ObjectVariable) <em>Is Variable To Add</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Variable To Add</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer#isVariableToAdd(SDMLanguage.patterns.ObjectVariable)
	 * @generated
	 */
	EOperation getAttributeConstraintGreenPatternTransformer__IsVariableToAdd__ObjectVariable();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer#callSuperIsVariableToAdd(SDMLanguage.patterns.ObjectVariable) <em>Call Super Is Variable To Add</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Call Super Is Variable To Add</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.AttributeConstraintGreenPatternTransformer#callSuperIsVariableToAdd(SDMLanguage.patterns.ObjectVariable)
	 * @generated
	 */
	EOperation getAttributeConstraintGreenPatternTransformer__CallSuperIsVariableToAdd__ObjectVariable();

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.constraintstodemocles.IdentifyerHelper <em>Identifyer Helper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identifyer Helper</em>'.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.IdentifyerHelper
	 * @generated
	 */
	EClass getIdentifyerHelper();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.constraintstodemocles.IdentifyerHelper#getNextIdentifyer() <em>Get Next Identifyer</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Next Identifyer</em>' operation.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.IdentifyerHelper#getNextIdentifyer()
	 * @generated
	 */
	EOperation getIdentifyerHelper__GetNextIdentifyer();

	/**
	 * Returns the meta object for enum '{@link org.moflon.sdm.constraints.constraintstodemocles.Errors <em>Errors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Errors</em>'.
	 * @see org.moflon.sdm.constraints.constraintstodemocles.Errors
	 * @generated
	 */
	EEnum getErrors();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ConstraintstodemoclesFactory getConstraintstodemoclesFactory();

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
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.constraintstodemocles.impl.AttributeConstraintBlackAndNacPatternTransformerImpl <em>Attribute Constraint Black And Nac Pattern Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.AttributeConstraintBlackAndNacPatternTransformerImpl
		 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.ConstraintstodemoclesPackageImpl#getAttributeConstraintBlackAndNacPatternTransformer()
		 * @generated
		 */
		EClass ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER = eINSTANCE
				.getAttributeConstraintBlackAndNacPatternTransformer();

		/**
		 * The meta object literal for the '<em><b>Postprocess</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___POSTPROCESS__STORYPATTERN_PATTERN = eINSTANCE
				.getAttributeConstraintBlackAndNacPatternTransformer__Postprocess__StoryPattern_Pattern();

		/**
		 * The meta object literal for the '<em><b>Process Primitive Variable</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_PRIMITIVE_VARIABLE__PRIMITIVEVARIABLE_PATTERN = eINSTANCE
				.getAttributeConstraintBlackAndNacPatternTransformer__ProcessPrimitiveVariable__PrimitiveVariable_Pattern();

		/**
		 * The meta object literal for the '<em><b>Process Lookup Constraint</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_LOOKUP_CONSTRAINT__ATTRIBUTELOOKUPCONSTRAINT_PATTERN = eINSTANCE
				.getAttributeConstraintBlackAndNacPatternTransformer__ProcessLookupConstraint__AttributeLookupConstraint_Pattern();

		/**
		 * The meta object literal for the '<em><b>Process Csp Constraint</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___PROCESS_CSP_CONSTRAINT__CSPCONSTRAINT_PATTERN = eINSTANCE
				.getAttributeConstraintBlackAndNacPatternTransformer__ProcessCspConstraint__CspConstraint_Pattern();

		/**
		 * The meta object literal for the '<em><b>Lookup Variable By Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___LOOKUP_VARIABLE_BY_NAME__STRING_PATTERN = eINSTANCE
				.getAttributeConstraintBlackAndNacPatternTransformer__LookupVariableByName__String_Pattern();

		/**
		 * The meta object literal for the '<em><b>Call Super Postprocess</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___CALL_SUPER_POSTPROCESS__STORYPATTERN_PATTERN = eINSTANCE
				.getAttributeConstraintBlackAndNacPatternTransformer__CallSuperPostprocess__StoryPattern_Pattern();

		/**
		 * The meta object literal for the '<em><b>Validate Csp Constraints</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___VALIDATE_CSP_CONSTRAINTS__ATTRIBUTEVARIABLECONSTRAINT_CSPCONSTRAINT = eINSTANCE
				.getAttributeConstraintBlackAndNacPatternTransformer__ValidateCspConstraints__AttributeVariableConstraint_CspConstraint();

		/**
		 * The meta object literal for the '<em><b>Update User Defined Constraint Lib</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___UPDATE_USER_DEFINED_CONSTRAINT_LIB__ATTRIBUTEVARIABLECONSTRAINT_ATTRIBUTECONSTRAINTLIBRARY = eINSTANCE
				.getAttributeConstraintBlackAndNacPatternTransformer__UpdateUserDefinedConstraintLib__AttributeVariableConstraint_AttributeConstraintLibrary();

		/**
		 * The meta object literal for the '<em><b>Validate Constraint Library</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___VALIDATE_CONSTRAINT_LIBRARY__ATTRIBUTECONSTRAINTLIBRARY_STRING = eINSTANCE
				.getAttributeConstraintBlackAndNacPatternTransformer__ValidateConstraintLibrary__AttributeConstraintLibrary_String();

		/**
		 * The meta object literal for the '<em><b>Create Error Message</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER___CREATE_ERROR_MESSAGE__EOBJECT = eINSTANCE
				.getAttributeConstraintBlackAndNacPatternTransformer__CreateErrorMessage__EObject();

		/**
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.constraintstodemocles.impl.AttributeConstraintGreenPatternTransformerImpl <em>Attribute Constraint Green Pattern Transformer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.AttributeConstraintGreenPatternTransformerImpl
		 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.ConstraintstodemoclesPackageImpl#getAttributeConstraintGreenPatternTransformer()
		 * @generated
		 */
		EClass ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER = eINSTANCE
				.getAttributeConstraintGreenPatternTransformer();

		/**
		 * The meta object literal for the '<em><b>Postprocess</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___POSTPROCESS__STORYPATTERN_PATTERN = eINSTANCE
				.getAttributeConstraintGreenPatternTransformer__Postprocess__StoryPattern_Pattern();

		/**
		 * The meta object literal for the '<em><b>Call Super Postprocess</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___CALL_SUPER_POSTPROCESS__STORYPATTERN_PATTERN = eINSTANCE
				.getAttributeConstraintGreenPatternTransformer__CallSuperPostprocess__StoryPattern_Pattern();

		/**
		 * The meta object literal for the '<em><b>Process Assignment Constraint</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___PROCESS_ASSIGNMENT_CONSTRAINT__ASSIGNMENTCONSTRAINT_PATTERN = eINSTANCE
				.getAttributeConstraintGreenPatternTransformer__ProcessAssignmentConstraint__AssignmentConstraint_Pattern();

		/**
		 * The meta object literal for the '<em><b>Lookup Variable By Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___LOOKUP_VARIABLE_BY_NAME__STRING_PATTERN = eINSTANCE
				.getAttributeConstraintGreenPatternTransformer__LookupVariableByName__String_Pattern();

		/**
		 * The meta object literal for the '<em><b>Is Variable To Add</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___IS_VARIABLE_TO_ADD__OBJECTVARIABLE = eINSTANCE
				.getAttributeConstraintGreenPatternTransformer__IsVariableToAdd__ObjectVariable();

		/**
		 * The meta object literal for the '<em><b>Call Super Is Variable To Add</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER___CALL_SUPER_IS_VARIABLE_TO_ADD__OBJECTVARIABLE = eINSTANCE
				.getAttributeConstraintGreenPatternTransformer__CallSuperIsVariableToAdd__ObjectVariable();

		/**
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.constraintstodemocles.impl.IdentifyerHelperImpl <em>Identifyer Helper</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.IdentifyerHelperImpl
		 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.ConstraintstodemoclesPackageImpl#getIdentifyerHelper()
		 * @generated
		 */
		EClass IDENTIFYER_HELPER = eINSTANCE.getIdentifyerHelper();

		/**
		 * The meta object literal for the '<em><b>Get Next Identifyer</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IDENTIFYER_HELPER___GET_NEXT_IDENTIFYER = eINSTANCE.getIdentifyerHelper__GetNextIdentifyer();

		/**
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.constraintstodemocles.Errors <em>Errors</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.constraintstodemocles.Errors
		 * @see org.moflon.sdm.constraints.constraintstodemocles.impl.ConstraintstodemoclesPackageImpl#getErrors()
		 * @generated
		 */
		EEnum ERRORS = eINSTANCE.getErrors();

	}

} //ConstraintstodemoclesPackage
