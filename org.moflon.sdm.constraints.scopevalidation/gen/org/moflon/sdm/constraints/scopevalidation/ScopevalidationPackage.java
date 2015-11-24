/**
 */
package org.moflon.sdm.constraints.scopevalidation;

import ScopeValidation.ScopeValidationPackage;

import org.eclipse.emf.ecore.EClass;
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
 * @see org.moflon.sdm.constraints.scopevalidation.ScopevalidationFactory
 * @model kind="package"
 * @generated
 */
public interface ScopevalidationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "org.moflon.sdm.constraints.scopevalidation";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/plugin/org.moflon.sdm.constraints.scopevalidation/model/Scopevalidation.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.moflon.sdm.constraints.scopevalidation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ScopevalidationPackage eINSTANCE = org.moflon.sdm.constraints.scopevalidation.impl.ScopevalidationPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.scopevalidation.impl.AttributeConstraintBlackPatternInvocationBuilderImpl <em>Attribute Constraint Black Pattern Invocation Builder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.scopevalidation.impl.AttributeConstraintBlackPatternInvocationBuilderImpl
	 * @see org.moflon.sdm.constraints.scopevalidation.impl.ScopevalidationPackageImpl#getAttributeConstraintBlackPatternInvocationBuilder()
	 * @generated
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER = 0;

	/**
	 * The feature id for the '<em><b>Validator</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__VALIDATOR = ScopeValidationPackage.BLACK_PATTERN_BUILDER__VALIDATOR;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__PARENT = ScopeValidationPackage.BLACK_PATTERN_BUILDER__PARENT;

	/**
	 * The feature id for the '<em><b>Result</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__RESULT = ScopeValidationPackage.BLACK_PATTERN_BUILDER__RESULT;

	/**
	 * The feature id for the '<em><b>Expression Explorer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__EXPRESSION_EXPLORER = ScopeValidationPackage.BLACK_PATTERN_BUILDER__EXPRESSION_EXPLORER;

	/**
	 * The feature id for the '<em><b>Pattern Matcher</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__PATTERN_MATCHER = ScopeValidationPackage.BLACK_PATTERN_BUILDER__PATTERN_MATCHER;

	/**
	 * The feature id for the '<em><b>Suffix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__SUFFIX = ScopeValidationPackage.BLACK_PATTERN_BUILDER__SUFFIX;

	/**
	 * The feature id for the '<em><b>Parent Builder</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__PARENT_BUILDER = ScopeValidationPackage.BLACK_PATTERN_BUILDER__PARENT_BUILDER;

	/**
	 * The feature id for the '<em><b>Pattern Transformer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__PATTERN_TRANSFORMER = ScopeValidationPackage.BLACK_PATTERN_BUILDER__PATTERN_TRANSFORMER;

	/**
	 * The feature id for the '<em><b>Main Action Builder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__MAIN_ACTION_BUILDER = ScopeValidationPackage.BLACK_PATTERN_BUILDER__MAIN_ACTION_BUILDER;

	/**
	 * The feature id for the '<em><b>Child Builders</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__CHILD_BUILDERS = ScopeValidationPackage.BLACK_PATTERN_BUILDER__CHILD_BUILDERS;

	/**
	 * The number of structural features of the '<em>Attribute Constraint Black Pattern Invocation Builder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER_FEATURE_COUNT = ScopeValidationPackage.BLACK_PATTERN_BUILDER_FEATURE_COUNT
			+ 0;

	/**
	 * The operation id for the '<em>Add Action</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___ADD_ACTION__CFNODE_ACTION = ScopeValidationPackage.BLACK_PATTERN_BUILDER___ADD_ACTION__CFNODE_ACTION;

	/**
	 * The operation id for the '<em>Lookup Control Flow Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___LOOKUP_CONTROL_FLOW_VARIABLE__ACTION_STRING_ECLASSIFIER = ScopeValidationPackage.BLACK_PATTERN_BUILDER___LOOKUP_CONTROL_FLOW_VARIABLE__ACTION_STRING_ECLASSIFIER;

	/**
	 * The operation id for the '<em>Validate Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___VALIDATE_VARIABLE__ACTION_CFVARIABLE_OBJECTVARIABLE = ScopeValidationPackage.BLACK_PATTERN_BUILDER___VALIDATE_VARIABLE__ACTION_CFVARIABLE_OBJECTVARIABLE;

	/**
	 * The operation id for the '<em>Attach To Resource</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___ATTACH_TO_RESOURCE__CFNODE_PATTERN = ScopeValidationPackage.BLACK_PATTERN_BUILDER___ATTACH_TO_RESOURCE__CFNODE_PATTERN;

	/**
	 * The operation id for the '<em>Calculate Adornment</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CALCULATE_ADORNMENT__PATTERNINVOCATION = ScopeValidationPackage.BLACK_PATTERN_BUILDER___CALCULATE_ADORNMENT__PATTERNINVOCATION;

	/**
	 * The operation id for the '<em>Calculate Pattern Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CALCULATE_PATTERN_NAME__CFNODE = ScopeValidationPackage.BLACK_PATTERN_BUILDER___CALCULATE_PATTERN_NAME__CFNODE;

	/**
	 * The operation id for the '<em>Handle Pattern Matcher Report</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HANDLE_PATTERN_MATCHER_REPORT__PATTERNINVOCATION_VALIDATIONREPORT = ScopeValidationPackage.BLACK_PATTERN_BUILDER___HANDLE_PATTERN_MATCHER_REPORT__PATTERNINVOCATION_VALIDATIONREPORT;

	/**
	 * The operation id for the '<em>Has Errors</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HAS_ERRORS = ScopeValidationPackage.BLACK_PATTERN_BUILDER___HAS_ERRORS;

	/**
	 * The operation id for the '<em>Lookup Action</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___LOOKUP_ACTION__CFNODE = ScopeValidationPackage.BLACK_PATTERN_BUILDER___LOOKUP_ACTION__CFNODE;

	/**
	 * The operation id for the '<em>Build Action</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___BUILD_ACTION__CFNODE = ScopeValidationPackage.BLACK_PATTERN_BUILDER___BUILD_ACTION__CFNODE;

	/**
	 * The operation id for the '<em>Build Pattern Interface</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___BUILD_PATTERN_INTERFACE__CFNODE_STORYPATTERN = ScopeValidationPackage.BLACK_PATTERN_BUILDER___BUILD_PATTERN_INTERFACE__CFNODE_STORYPATTERN;

	/**
	 * The operation id for the '<em>Create Action</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CREATE_ACTION = ScopeValidationPackage.BLACK_PATTERN_BUILDER___CREATE_ACTION;

	/**
	 * The operation id for the '<em>Explore Attribute Assignments</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___EXPLORE_ATTRIBUTE_ASSIGNMENTS__PATTERNINVOCATIONBUILDER_EXPRESSIONEXPLORER_OBJECTVARIABLE_PATTERN = ScopeValidationPackage.BLACK_PATTERN_BUILDER___EXPLORE_ATTRIBUTE_ASSIGNMENTS__PATTERNINVOCATIONBUILDER_EXPRESSIONEXPLORER_OBJECTVARIABLE_PATTERN;

	/**
	 * The operation id for the '<em>Explore Attribute Constraints</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___EXPLORE_ATTRIBUTE_CONSTRAINTS__PATTERNINVOCATIONBUILDER_EXPRESSIONEXPLORER_OBJECTVARIABLE_PATTERN = ScopeValidationPackage.BLACK_PATTERN_BUILDER___EXPLORE_ATTRIBUTE_CONSTRAINTS__PATTERNINVOCATIONBUILDER_EXPRESSIONEXPLORER_OBJECTVARIABLE_PATTERN;

	/**
	 * The operation id for the '<em>Get Pattern Invocation Builder</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___GET_PATTERN_INVOCATION_BUILDER = ScopeValidationPackage.BLACK_PATTERN_BUILDER___GET_PATTERN_INVOCATION_BUILDER;

	/**
	 * The operation id for the '<em>Handle Relevant Object Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HANDLE_RELEVANT_OBJECT_VARIABLE__PATTERNINVOCATION_OBJECTVARIABLE = ScopeValidationPackage.BLACK_PATTERN_BUILDER___HANDLE_RELEVANT_OBJECT_VARIABLE__PATTERNINVOCATION_OBJECTVARIABLE;

	/**
	 * The operation id for the '<em>Handle Variable Reference</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HANDLE_VARIABLE_REFERENCE__VARIABLE_ECLASSIFIER = ScopeValidationPackage.BLACK_PATTERN_BUILDER___HANDLE_VARIABLE_REFERENCE__VARIABLE_ECLASSIFIER;

	/**
	 * The operation id for the '<em>Handle Variable Reference In Superclass</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HANDLE_VARIABLE_REFERENCE_IN_SUPERCLASS__VARIABLE_ECLASSIFIER = ScopeValidationPackage.BLACK_PATTERN_BUILDER___HANDLE_VARIABLE_REFERENCE_IN_SUPERCLASS__VARIABLE_ECLASSIFIER;

	/**
	 * The operation id for the '<em>Is Variable To Add</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___IS_VARIABLE_TO_ADD__OBJECTVARIABLE = ScopeValidationPackage.BLACK_PATTERN_BUILDER___IS_VARIABLE_TO_ADD__OBJECTVARIABLE;

	/**
	 * The operation id for the '<em>Lookup Scope Validator</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___LOOKUP_SCOPE_VALIDATOR = ScopeValidationPackage.BLACK_PATTERN_BUILDER___LOOKUP_SCOPE_VALIDATOR;

	/**
	 * The operation id for the '<em>Postprocess Pattern Interface</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___POSTPROCESS_PATTERN_INTERFACE__STORYPATTERN = ScopeValidationPackage.BLACK_PATTERN_BUILDER___POSTPROCESS_PATTERN_INTERFACE__STORYPATTERN;

	/**
	 * The operation id for the '<em>Transform Pattern</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___TRANSFORM_PATTERN__STORYPATTERN_PATTERN = ScopeValidationPackage.BLACK_PATTERN_BUILDER___TRANSFORM_PATTERN__STORYPATTERN_PATTERN;

	/**
	 * The operation id for the '<em>Analyze Dependencies In Superclass</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___ANALYZE_DEPENDENCIES_IN_SUPERCLASS__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN = ScopeValidationPackage.BLACK_PATTERN_BUILDER___ANALYZE_DEPENDENCIES_IN_SUPERCLASS__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN;

	/**
	 * The operation id for the '<em>Build Pattern Content</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___BUILD_PATTERN_CONTENT__REGULARPATTERNINVOCATION_STORYPATTERN = ScopeValidationPackage.BLACK_PATTERN_BUILDER___BUILD_PATTERN_CONTENT__REGULARPATTERNINVOCATION_STORYPATTERN;

	/**
	 * The operation id for the '<em>Build Pattern Content In Superclass</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___BUILD_PATTERN_CONTENT_IN_SUPERCLASS__REGULARPATTERNINVOCATION_STORYPATTERN = ScopeValidationPackage.BLACK_PATTERN_BUILDER___BUILD_PATTERN_CONTENT_IN_SUPERCLASS__REGULARPATTERNINVOCATION_STORYPATTERN;

	/**
	 * The operation id for the '<em>Analyze Dependencies</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___ANALYZE_DEPENDENCIES__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN = ScopeValidationPackage.BLACK_PATTERN_BUILDER_OPERATION_COUNT
			+ 0;

	/**
	 * The operation id for the '<em>Call Super Analyze Dependencies</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CALL_SUPER_ANALYZE_DEPENDENCIES__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN = ScopeValidationPackage.BLACK_PATTERN_BUILDER_OPERATION_COUNT
			+ 1;

	/**
	 * The operation id for the '<em>Concat</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CONCAT__STRING_INT = ScopeValidationPackage.BLACK_PATTERN_BUILDER_OPERATION_COUNT
			+ 2;

	/**
	 * The operation id for the '<em>Calc Local Variable Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CALC_LOCAL_VARIABLE_NAME__VARIABLE = ScopeValidationPackage.BLACK_PATTERN_BUILDER_OPERATION_COUNT
			+ 3;

	/**
	 * The operation id for the '<em>Handle Attr Variable Reference</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HANDLE_ATTR_VARIABLE_REFERENCE__VARIABLE_ECLASSIFIER = ScopeValidationPackage.BLACK_PATTERN_BUILDER_OPERATION_COUNT
			+ 4;

	/**
	 * The number of operations of the '<em>Attribute Constraint Black Pattern Invocation Builder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER_OPERATION_COUNT = ScopeValidationPackage.BLACK_PATTERN_BUILDER_OPERATION_COUNT
			+ 5;

	/**
	 * The meta object id for the '{@link org.moflon.sdm.constraints.scopevalidation.impl.AttributeConstraintGreenPatternInvocationBuilderImpl <em>Attribute Constraint Green Pattern Invocation Builder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.sdm.constraints.scopevalidation.impl.AttributeConstraintGreenPatternInvocationBuilderImpl
	 * @see org.moflon.sdm.constraints.scopevalidation.impl.ScopevalidationPackageImpl#getAttributeConstraintGreenPatternInvocationBuilder()
	 * @generated
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER = 1;

	/**
	 * The feature id for the '<em><b>Validator</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER__VALIDATOR = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__VALIDATOR;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER__PARENT = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__PARENT;

	/**
	 * The feature id for the '<em><b>Result</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER__RESULT = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__RESULT;

	/**
	 * The feature id for the '<em><b>Expression Explorer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER__EXPRESSION_EXPLORER = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__EXPRESSION_EXPLORER;

	/**
	 * The feature id for the '<em><b>Pattern Matcher</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER__PATTERN_MATCHER = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__PATTERN_MATCHER;

	/**
	 * The feature id for the '<em><b>Suffix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER__SUFFIX = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__SUFFIX;

	/**
	 * The feature id for the '<em><b>Parent Builder</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER__PARENT_BUILDER = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__PARENT_BUILDER;

	/**
	 * The feature id for the '<em><b>Pattern Transformer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER__PATTERN_TRANSFORMER = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__PATTERN_TRANSFORMER;

	/**
	 * The feature id for the '<em><b>Main Action Builder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER__MAIN_ACTION_BUILDER = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__MAIN_ACTION_BUILDER;

	/**
	 * The feature id for the '<em><b>Child Builders</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER__CHILD_BUILDERS = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER__CHILD_BUILDERS;

	/**
	 * The number of structural features of the '<em>Attribute Constraint Green Pattern Invocation Builder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER_FEATURE_COUNT = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER_FEATURE_COUNT
			+ 0;

	/**
	 * The operation id for the '<em>Add Action</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___ADD_ACTION__CFNODE_ACTION = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___ADD_ACTION__CFNODE_ACTION;

	/**
	 * The operation id for the '<em>Lookup Control Flow Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___LOOKUP_CONTROL_FLOW_VARIABLE__ACTION_STRING_ECLASSIFIER = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___LOOKUP_CONTROL_FLOW_VARIABLE__ACTION_STRING_ECLASSIFIER;

	/**
	 * The operation id for the '<em>Attach To Resource</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___ATTACH_TO_RESOURCE__CFNODE_PATTERN = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___ATTACH_TO_RESOURCE__CFNODE_PATTERN;

	/**
	 * The operation id for the '<em>Calculate Adornment</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___CALCULATE_ADORNMENT__PATTERNINVOCATION = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CALCULATE_ADORNMENT__PATTERNINVOCATION;

	/**
	 * The operation id for the '<em>Calculate Pattern Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___CALCULATE_PATTERN_NAME__CFNODE = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CALCULATE_PATTERN_NAME__CFNODE;

	/**
	 * The operation id for the '<em>Handle Pattern Matcher Report</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___HANDLE_PATTERN_MATCHER_REPORT__PATTERNINVOCATION_VALIDATIONREPORT = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HANDLE_PATTERN_MATCHER_REPORT__PATTERNINVOCATION_VALIDATIONREPORT;

	/**
	 * The operation id for the '<em>Has Errors</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___HAS_ERRORS = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HAS_ERRORS;

	/**
	 * The operation id for the '<em>Lookup Action</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___LOOKUP_ACTION__CFNODE = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___LOOKUP_ACTION__CFNODE;

	/**
	 * The operation id for the '<em>Build Action</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___BUILD_ACTION__CFNODE = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___BUILD_ACTION__CFNODE;

	/**
	 * The operation id for the '<em>Build Pattern Interface</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___BUILD_PATTERN_INTERFACE__CFNODE_STORYPATTERN = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___BUILD_PATTERN_INTERFACE__CFNODE_STORYPATTERN;

	/**
	 * The operation id for the '<em>Create Action</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___CREATE_ACTION = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CREATE_ACTION;

	/**
	 * The operation id for the '<em>Explore Attribute Assignments</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___EXPLORE_ATTRIBUTE_ASSIGNMENTS__PATTERNINVOCATIONBUILDER_EXPRESSIONEXPLORER_OBJECTVARIABLE_PATTERN = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___EXPLORE_ATTRIBUTE_ASSIGNMENTS__PATTERNINVOCATIONBUILDER_EXPRESSIONEXPLORER_OBJECTVARIABLE_PATTERN;

	/**
	 * The operation id for the '<em>Explore Attribute Constraints</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___EXPLORE_ATTRIBUTE_CONSTRAINTS__PATTERNINVOCATIONBUILDER_EXPRESSIONEXPLORER_OBJECTVARIABLE_PATTERN = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___EXPLORE_ATTRIBUTE_CONSTRAINTS__PATTERNINVOCATIONBUILDER_EXPRESSIONEXPLORER_OBJECTVARIABLE_PATTERN;

	/**
	 * The operation id for the '<em>Get Pattern Invocation Builder</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___GET_PATTERN_INVOCATION_BUILDER = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___GET_PATTERN_INVOCATION_BUILDER;

	/**
	 * The operation id for the '<em>Handle Relevant Object Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___HANDLE_RELEVANT_OBJECT_VARIABLE__PATTERNINVOCATION_OBJECTVARIABLE = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HANDLE_RELEVANT_OBJECT_VARIABLE__PATTERNINVOCATION_OBJECTVARIABLE;

	/**
	 * The operation id for the '<em>Handle Variable Reference</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___HANDLE_VARIABLE_REFERENCE__VARIABLE_ECLASSIFIER = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HANDLE_VARIABLE_REFERENCE__VARIABLE_ECLASSIFIER;

	/**
	 * The operation id for the '<em>Handle Variable Reference In Superclass</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___HANDLE_VARIABLE_REFERENCE_IN_SUPERCLASS__VARIABLE_ECLASSIFIER = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HANDLE_VARIABLE_REFERENCE_IN_SUPERCLASS__VARIABLE_ECLASSIFIER;

	/**
	 * The operation id for the '<em>Is Variable To Add</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___IS_VARIABLE_TO_ADD__OBJECTVARIABLE = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___IS_VARIABLE_TO_ADD__OBJECTVARIABLE;

	/**
	 * The operation id for the '<em>Lookup Scope Validator</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___LOOKUP_SCOPE_VALIDATOR = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___LOOKUP_SCOPE_VALIDATOR;

	/**
	 * The operation id for the '<em>Postprocess Pattern Interface</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___POSTPROCESS_PATTERN_INTERFACE__STORYPATTERN = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___POSTPROCESS_PATTERN_INTERFACE__STORYPATTERN;

	/**
	 * The operation id for the '<em>Transform Pattern</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___TRANSFORM_PATTERN__STORYPATTERN_PATTERN = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___TRANSFORM_PATTERN__STORYPATTERN_PATTERN;

	/**
	 * The operation id for the '<em>Analyze Dependencies In Superclass</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___ANALYZE_DEPENDENCIES_IN_SUPERCLASS__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___ANALYZE_DEPENDENCIES_IN_SUPERCLASS__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN;

	/**
	 * The operation id for the '<em>Build Pattern Content</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___BUILD_PATTERN_CONTENT__REGULARPATTERNINVOCATION_STORYPATTERN = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___BUILD_PATTERN_CONTENT__REGULARPATTERNINVOCATION_STORYPATTERN;

	/**
	 * The operation id for the '<em>Build Pattern Content In Superclass</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___BUILD_PATTERN_CONTENT_IN_SUPERCLASS__REGULARPATTERNINVOCATION_STORYPATTERN = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___BUILD_PATTERN_CONTENT_IN_SUPERCLASS__REGULARPATTERNINVOCATION_STORYPATTERN;

	/**
	 * The operation id for the '<em>Analyze Dependencies</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___ANALYZE_DEPENDENCIES__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___ANALYZE_DEPENDENCIES__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN;

	/**
	 * The operation id for the '<em>Call Super Analyze Dependencies</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___CALL_SUPER_ANALYZE_DEPENDENCIES__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CALL_SUPER_ANALYZE_DEPENDENCIES__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN;

	/**
	 * The operation id for the '<em>Concat</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___CONCAT__STRING_INT = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CONCAT__STRING_INT;

	/**
	 * The operation id for the '<em>Calc Local Variable Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___CALC_LOCAL_VARIABLE_NAME__VARIABLE = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CALC_LOCAL_VARIABLE_NAME__VARIABLE;

	/**
	 * The operation id for the '<em>Handle Attr Variable Reference</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___HANDLE_ATTR_VARIABLE_REFERENCE__VARIABLE_ECLASSIFIER = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HANDLE_ATTR_VARIABLE_REFERENCE__VARIABLE_ECLASSIFIER;

	/**
	 * The operation id for the '<em>Validate Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___VALIDATE_VARIABLE__ACTION_CFVARIABLE_OBJECTVARIABLE = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER_OPERATION_COUNT
			+ 0;

	/**
	 * The number of operations of the '<em>Attribute Constraint Green Pattern Invocation Builder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER_OPERATION_COUNT = ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER_OPERATION_COUNT
			+ 1;

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder <em>Attribute Constraint Black Pattern Invocation Builder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Constraint Black Pattern Invocation Builder</em>'.
	 * @see org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder
	 * @generated
	 */
	EClass getAttributeConstraintBlackPatternInvocationBuilder();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder#analyzeDependencies(ScopeValidation.PatternInvocationBuilder, SDMLanguage.patterns.StoryPattern, org.gervarro.democles.specification.emf.Pattern) <em>Analyze Dependencies</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Analyze Dependencies</em>' operation.
	 * @see org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder#analyzeDependencies(ScopeValidation.PatternInvocationBuilder, SDMLanguage.patterns.StoryPattern, org.gervarro.democles.specification.emf.Pattern)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackPatternInvocationBuilder__AnalyzeDependencies__PatternInvocationBuilder_StoryPattern_Pattern();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder#callSuperAnalyzeDependencies(ScopeValidation.PatternInvocationBuilder, SDMLanguage.patterns.StoryPattern, org.gervarro.democles.specification.emf.Pattern) <em>Call Super Analyze Dependencies</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Call Super Analyze Dependencies</em>' operation.
	 * @see org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder#callSuperAnalyzeDependencies(ScopeValidation.PatternInvocationBuilder, SDMLanguage.patterns.StoryPattern, org.gervarro.democles.specification.emf.Pattern)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackPatternInvocationBuilder__CallSuperAnalyzeDependencies__PatternInvocationBuilder_StoryPattern_Pattern();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder#concat(java.lang.String, int) <em>Concat</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Concat</em>' operation.
	 * @see org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder#concat(java.lang.String, int)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackPatternInvocationBuilder__Concat__String_int();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder#calcLocalVariableName(org.gervarro.democles.specification.emf.Variable) <em>Calc Local Variable Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Calc Local Variable Name</em>' operation.
	 * @see org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder#calcLocalVariableName(org.gervarro.democles.specification.emf.Variable)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackPatternInvocationBuilder__CalcLocalVariableName__Variable();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder#handleAttrVariableReference(org.gervarro.democles.specification.emf.Variable, org.eclipse.emf.ecore.EClassifier) <em>Handle Attr Variable Reference</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Handle Attr Variable Reference</em>' operation.
	 * @see org.moflon.sdm.constraints.scopevalidation.AttributeConstraintBlackPatternInvocationBuilder#handleAttrVariableReference(org.gervarro.democles.specification.emf.Variable, org.eclipse.emf.ecore.EClassifier)
	 * @generated
	 */
	EOperation getAttributeConstraintBlackPatternInvocationBuilder__HandleAttrVariableReference__Variable_EClassifier();

	/**
	 * Returns the meta object for class '{@link org.moflon.sdm.constraints.scopevalidation.AttributeConstraintGreenPatternInvocationBuilder <em>Attribute Constraint Green Pattern Invocation Builder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Constraint Green Pattern Invocation Builder</em>'.
	 * @see org.moflon.sdm.constraints.scopevalidation.AttributeConstraintGreenPatternInvocationBuilder
	 * @generated
	 */
	EClass getAttributeConstraintGreenPatternInvocationBuilder();

	/**
	 * Returns the meta object for the '{@link org.moflon.sdm.constraints.scopevalidation.AttributeConstraintGreenPatternInvocationBuilder#validateVariable(ControlFlow.Action, ControlFlow.CFVariable, SDMLanguage.patterns.ObjectVariable) <em>Validate Variable</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Validate Variable</em>' operation.
	 * @see org.moflon.sdm.constraints.scopevalidation.AttributeConstraintGreenPatternInvocationBuilder#validateVariable(ControlFlow.Action, ControlFlow.CFVariable, SDMLanguage.patterns.ObjectVariable)
	 * @generated
	 */
	EOperation getAttributeConstraintGreenPatternInvocationBuilder__ValidateVariable__Action_CFVariable_ObjectVariable();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ScopevalidationFactory getScopevalidationFactory();

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
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.scopevalidation.impl.AttributeConstraintBlackPatternInvocationBuilderImpl <em>Attribute Constraint Black Pattern Invocation Builder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.scopevalidation.impl.AttributeConstraintBlackPatternInvocationBuilderImpl
		 * @see org.moflon.sdm.constraints.scopevalidation.impl.ScopevalidationPackageImpl#getAttributeConstraintBlackPatternInvocationBuilder()
		 * @generated
		 */
		EClass ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER = eINSTANCE
				.getAttributeConstraintBlackPatternInvocationBuilder();

		/**
		 * The meta object literal for the '<em><b>Analyze Dependencies</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___ANALYZE_DEPENDENCIES__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN = eINSTANCE
				.getAttributeConstraintBlackPatternInvocationBuilder__AnalyzeDependencies__PatternInvocationBuilder_StoryPattern_Pattern();

		/**
		 * The meta object literal for the '<em><b>Call Super Analyze Dependencies</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CALL_SUPER_ANALYZE_DEPENDENCIES__PATTERNINVOCATIONBUILDER_STORYPATTERN_PATTERN = eINSTANCE
				.getAttributeConstraintBlackPatternInvocationBuilder__CallSuperAnalyzeDependencies__PatternInvocationBuilder_StoryPattern_Pattern();

		/**
		 * The meta object literal for the '<em><b>Concat</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CONCAT__STRING_INT = eINSTANCE
				.getAttributeConstraintBlackPatternInvocationBuilder__Concat__String_int();

		/**
		 * The meta object literal for the '<em><b>Calc Local Variable Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___CALC_LOCAL_VARIABLE_NAME__VARIABLE = eINSTANCE
				.getAttributeConstraintBlackPatternInvocationBuilder__CalcLocalVariableName__Variable();

		/**
		 * The meta object literal for the '<em><b>Handle Attr Variable Reference</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER___HANDLE_ATTR_VARIABLE_REFERENCE__VARIABLE_ECLASSIFIER = eINSTANCE
				.getAttributeConstraintBlackPatternInvocationBuilder__HandleAttrVariableReference__Variable_EClassifier();

		/**
		 * The meta object literal for the '{@link org.moflon.sdm.constraints.scopevalidation.impl.AttributeConstraintGreenPatternInvocationBuilderImpl <em>Attribute Constraint Green Pattern Invocation Builder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.sdm.constraints.scopevalidation.impl.AttributeConstraintGreenPatternInvocationBuilderImpl
		 * @see org.moflon.sdm.constraints.scopevalidation.impl.ScopevalidationPackageImpl#getAttributeConstraintGreenPatternInvocationBuilder()
		 * @generated
		 */
		EClass ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER = eINSTANCE
				.getAttributeConstraintGreenPatternInvocationBuilder();

		/**
		 * The meta object literal for the '<em><b>Validate Variable</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER___VALIDATE_VARIABLE__ACTION_CFVARIABLE_OBJECTVARIABLE = eINSTANCE
				.getAttributeConstraintGreenPatternInvocationBuilder__ValidateVariable__Action_CFVariable_ObjectVariable();

	}

} //ScopevalidationPackage
