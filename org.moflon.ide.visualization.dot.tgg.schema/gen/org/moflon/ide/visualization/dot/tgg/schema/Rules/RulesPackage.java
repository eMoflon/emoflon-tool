/**
 */
package org.moflon.ide.visualization.dot.tgg.schema.Rules;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

import org.moflon.tgg.runtime.RuntimePackage;

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
 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RulesFactory
 * @model kind="package"
 * @generated
 */
public interface RulesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Rules";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/plugin/org.moflon.ide.visualization.dot.tgg.schema/model/Schema.ecore#//Rules";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "Rules";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RulesPackage eINSTANCE = org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RulesPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RefinmentRulesImpl <em>Refinment Rules</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RefinmentRulesImpl
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RulesPackageImpl#getRefinmentRules()
	 * @generated
	 */
	int REFINMENT_RULES = 0;

	/**
	 * The number of structural features of the '<em>Refinment Rules</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES_FEATURE_COUNT = RuntimePackage.ABSTRACT_RULE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Appropriate FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPROPRIATE_FWD__MATCH_BOX_BOX_INHERITANCE_DIRECTEDGRAPH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 0;

	/**
	 * The operation id for the '<em>Perform FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___PERFORM_FWD__ISAPPLICABLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Applicable FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPLICABLE_FWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Register Objects To Match FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_BOX_BOX_INHERITANCE_DIRECTEDGRAPH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 3;

	/**
	 * The operation id for the '<em>Is Appropriate solve Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_BOX_BOX_INHERITANCE_DIRECTEDGRAPH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 4;

	/**
	 * The operation id for the '<em>Is Appropriate check Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPROPRIATE_CHECK_CSP_FWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Applicable solve Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_BOX_NODETORULE_BOX_INHERITANCE_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 6;

	/**
	 * The operation id for the '<em>Is Applicable check Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPLICABLE_CHECK_CSP_FWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Register Objects FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 8;

	/**
	 * The operation id for the '<em>Check Types FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___CHECK_TYPES_FWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Appropriate BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPROPRIATE_BWD__MATCH_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 10;

	/**
	 * The operation id for the '<em>Perform BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___PERFORM_BWD__ISAPPLICABLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Is Applicable BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPLICABLE_BWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>Register Objects To Match BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 13;

	/**
	 * The operation id for the '<em>Is Appropriate solve Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 14;

	/**
	 * The operation id for the '<em>Is Appropriate check Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPROPRIATE_CHECK_CSP_BWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 15;

	/**
	 * The operation id for the '<em>Is Applicable solve Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_BOX_NODETORULE_BOX_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 16;

	/**
	 * The operation id for the '<em>Is Applicable check Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPLICABLE_CHECK_CSP_BWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 17;

	/**
	 * The operation id for the '<em>Register Objects BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 18;

	/**
	 * The operation id for the '<em>Check Types BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___CHECK_TYPES_BWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 19;

	/**
	 * The operation id for the '<em>Is Appropriate FWD EMoflon Edge 57</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPROPRIATE_FWD_EMOFLON_EDGE_57__EMOFLONEDGE = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 20;

	/**
	 * The operation id for the '<em>Is Appropriate BWD EMoflon Edge 82</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPROPRIATE_BWD_EMOFLON_EDGE_82__EMOFLONEDGE = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 21;

	/**
	 * The operation id for the '<em>Check Attributes FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 22;

	/**
	 * The operation id for the '<em>Check Attributes BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 23;

	/**
	 * The operation id for the '<em>Is Applicable CC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPLICABLE_CC__MATCH_MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 24;

	/**
	 * The operation id for the '<em>Is Applicable solve Csp CC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPLICABLE_SOLVE_CSP_CC__BOX_BOX_INHERITANCE_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_MATCH_MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 25;

	/**
	 * The operation id for the '<em>Is Applicable check Csp CC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___IS_APPLICABLE_CHECK_CSP_CC__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 26;

	/**
	 * The operation id for the '<em>Check DEC FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___CHECK_DEC_FWD__BOX_BOX_INHERITANCE_DIRECTEDGRAPH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 27;

	/**
	 * The operation id for the '<em>Check DEC BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___CHECK_DEC_BWD__TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 28;

	/**
	 * The operation id for the '<em>Generate Model</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___GENERATE_MODEL__RULEENTRYCONTAINER_NODETORULE = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 29;

	/**
	 * The operation id for the '<em>Generate Model solve Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_BOX_NODETORULE_BOX_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_MODELGENERATORRULERESULT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 30;

	/**
	 * The operation id for the '<em>Generate Model check Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES___GENERATE_MODEL_CHECK_CSP_BWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 31;

	/**
	 * The number of operations of the '<em>Refinment Rules</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFINMENT_RULES_OPERATION_COUNT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 32;

	/**
	 * The meta object id for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.FillBaseRulesImpl <em>Fill Base Rules</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.FillBaseRulesImpl
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RulesPackageImpl#getFillBaseRules()
	 * @generated
	 */
	int FILL_BASE_RULES = 1;

	/**
	 * The number of structural features of the '<em>Fill Base Rules</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES_FEATURE_COUNT = RuntimePackage.ABSTRACT_RULE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Appropriate FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPROPRIATE_FWD__MATCH_DIRECTEDGRAPH_BOX = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 0;

	/**
	 * The operation id for the '<em>Perform FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___PERFORM_FWD__ISAPPLICABLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Applicable FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPLICABLE_FWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Register Objects To Match FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_DIRECTEDGRAPH_BOX = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 3;

	/**
	 * The operation id for the '<em>Is Appropriate solve Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_DIRECTEDGRAPH_BOX = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 4;

	/**
	 * The operation id for the '<em>Is Appropriate check Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPROPRIATE_CHECK_CSP_FWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Applicable solve Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 6;

	/**
	 * The operation id for the '<em>Is Applicable check Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPLICABLE_CHECK_CSP_FWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Register Objects FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 8;

	/**
	 * The operation id for the '<em>Check Types FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___CHECK_TYPES_FWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Appropriate BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPROPRIATE_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 10;

	/**
	 * The operation id for the '<em>Perform BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___PERFORM_BWD__ISAPPLICABLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Is Applicable BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPLICABLE_BWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>Register Objects To Match BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 13;

	/**
	 * The operation id for the '<em>Is Appropriate solve Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 14;

	/**
	 * The operation id for the '<em>Is Appropriate check Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPROPRIATE_CHECK_CSP_BWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 15;

	/**
	 * The operation id for the '<em>Is Applicable solve Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_TGGRULE_DIRECTEDGRAPH_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 16;

	/**
	 * The operation id for the '<em>Is Applicable check Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPLICABLE_CHECK_CSP_BWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 17;

	/**
	 * The operation id for the '<em>Register Objects BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 18;

	/**
	 * The operation id for the '<em>Check Types BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___CHECK_TYPES_BWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 19;

	/**
	 * The operation id for the '<em>Is Appropriate FWD EMoflon Edge 58</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPROPRIATE_FWD_EMOFLON_EDGE_58__EMOFLONEDGE = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 20;

	/**
	 * The operation id for the '<em>Is Appropriate BWD EMoflon Edge 83</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPROPRIATE_BWD_EMOFLON_EDGE_83__EMOFLONEDGE = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 21;

	/**
	 * The operation id for the '<em>Check Attributes FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 22;

	/**
	 * The operation id for the '<em>Check Attributes BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 23;

	/**
	 * The operation id for the '<em>Is Applicable CC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPLICABLE_CC__MATCH_MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 24;

	/**
	 * The operation id for the '<em>Is Applicable solve Csp CC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPLICABLE_SOLVE_CSP_CC__TGGRULE_DIRECTEDGRAPH_BOX_TRIPLEGRAPHGRAMMAR_MATCH_MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 25;

	/**
	 * The operation id for the '<em>Is Applicable check Csp CC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___IS_APPLICABLE_CHECK_CSP_CC__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 26;

	/**
	 * The operation id for the '<em>Check DEC FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___CHECK_DEC_FWD__DIRECTEDGRAPH_BOX = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 27;

	/**
	 * The operation id for the '<em>Check DEC BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___CHECK_DEC_BWD__TGGRULE_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 28;

	/**
	 * The operation id for the '<em>Generate Model</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___GENERATE_MODEL__RULEENTRYCONTAINER_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 29;

	/**
	 * The operation id for the '<em>Generate Model solve Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_TRIPLEGRAPHGRAMMAR_MODELGENERATORRULERESULT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 30;

	/**
	 * The operation id for the '<em>Generate Model check Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES___GENERATE_MODEL_CHECK_CSP_BWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 31;

	/**
	 * The number of operations of the '<em>Fill Base Rules</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILL_BASE_RULES_OPERATION_COUNT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 32;

	/**
	 * The meta object id for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.TGGGrammarDirectedGraphAxiomImpl <em>TGG Grammar Directed Graph Axiom</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.TGGGrammarDirectedGraphAxiomImpl
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RulesPackageImpl#getTGGGrammarDirectedGraphAxiom()
	 * @generated
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM = 2;

	/**
	 * The number of structural features of the '<em>TGG Grammar Directed Graph Axiom</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM_FEATURE_COUNT = RuntimePackage.ABSTRACT_RULE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Appropriate FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_FWD__MATCH_DIRECTEDGRAPH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 0;

	/**
	 * The operation id for the '<em>Perform FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___PERFORM_FWD__ISAPPLICABLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 1;

	/**
	 * The operation id for the '<em>Is Applicable FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_FWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Register Objects To Match FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_DIRECTEDGRAPH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 3;

	/**
	 * The operation id for the '<em>Is Appropriate solve Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_DIRECTEDGRAPH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 4;

	/**
	 * The operation id for the '<em>Is Appropriate check Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_CHECK_CSP_FWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 5;

	/**
	 * The operation id for the '<em>Is Applicable solve Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_DIRECTEDGRAPH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 6;

	/**
	 * The operation id for the '<em>Is Applicable check Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CHECK_CSP_FWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 7;

	/**
	 * The operation id for the '<em>Register Objects FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 8;

	/**
	 * The operation id for the '<em>Check Types FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_TYPES_FWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Appropriate BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_BWD__MATCH_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 10;

	/**
	 * The operation id for the '<em>Perform BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___PERFORM_BWD__ISAPPLICABLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 11;

	/**
	 * The operation id for the '<em>Is Applicable BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_BWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>Register Objects To Match BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 13;

	/**
	 * The operation id for the '<em>Is Appropriate solve Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 14;

	/**
	 * The operation id for the '<em>Is Appropriate check Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_CHECK_CSP_BWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 15;

	/**
	 * The operation id for the '<em>Is Applicable solve Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 16;

	/**
	 * The operation id for the '<em>Is Applicable check Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CHECK_CSP_BWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 17;

	/**
	 * The operation id for the '<em>Register Objects BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 18;

	/**
	 * The operation id for the '<em>Check Types BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_TYPES_BWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 19;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Directed Graph 0</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_FWD_DIRECTED_GRAPH_0__DIRECTEDGRAPH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 20;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Triple Graph Grammar 0</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_BWD_TRIPLE_GRAPH_GRAMMAR_0__TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 21;

	/**
	 * The operation id for the '<em>Check Attributes FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 22;

	/**
	 * The operation id for the '<em>Check Attributes BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 23;

	/**
	 * The operation id for the '<em>Is Applicable CC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CC__MATCH_MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 24;

	/**
	 * The operation id for the '<em>Is Applicable solve Csp CC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_SOLVE_CSP_CC__DIRECTEDGRAPH_TRIPLEGRAPHGRAMMAR_MATCH_MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 25;

	/**
	 * The operation id for the '<em>Is Applicable check Csp CC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CHECK_CSP_CC__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 26;

	/**
	 * The operation id for the '<em>Check DEC FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_DEC_FWD__DIRECTEDGRAPH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 27;

	/**
	 * The operation id for the '<em>Check DEC BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_DEC_BWD__TRIPLEGRAPHGRAMMAR = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 28;

	/**
	 * The operation id for the '<em>Generate Model</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___GENERATE_MODEL__RULEENTRYCONTAINER = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 29;

	/**
	 * The operation id for the '<em>Generate Model solve Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_MODELGENERATORRULERESULT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 30;

	/**
	 * The operation id for the '<em>Generate Model check Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___GENERATE_MODEL_CHECK_CSP_BWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 31;

	/**
	 * The number of operations of the '<em>TGG Grammar Directed Graph Axiom</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM_OPERATION_COUNT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 32;

	/**
	 * The meta object id for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.ComplementRuleImpl <em>Complement Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.ComplementRuleImpl
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RulesPackageImpl#getComplementRule()
	 * @generated
	 */
	int COMPLEMENT_RULE = 3;

	/**
	 * The number of structural features of the '<em>Complement Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE_FEATURE_COUNT = RuntimePackage.ABSTRACT_RULE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Appropriate FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPROPRIATE_FWD__MATCH_DIRECTEDGRAPH_BOX_COMPOSITE_BOX = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 0;

	/**
	 * The operation id for the '<em>Perform FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Applicable FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPLICABLE_FWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Register Objects To Match FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_DIRECTEDGRAPH_BOX_COMPOSITE_BOX = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 3;

	/**
	 * The operation id for the '<em>Is Appropriate solve Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_DIRECTEDGRAPH_BOX_COMPOSITE_BOX = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 4;

	/**
	 * The operation id for the '<em>Is Appropriate check Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPROPRIATE_CHECK_CSP_FWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Applicable solve Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_NODETORULE_COMPOSITE_TGGRULE_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_BOX = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 6;

	/**
	 * The operation id for the '<em>Is Applicable check Csp FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPLICABLE_CHECK_CSP_FWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Register Objects FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 8;

	/**
	 * The operation id for the '<em>Check Types FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___CHECK_TYPES_FWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Appropriate BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPROPRIATE_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 10;

	/**
	 * The operation id for the '<em>Perform BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Is Applicable BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPLICABLE_BWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>Register Objects To Match BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 13;

	/**
	 * The operation id for the '<em>Is Appropriate solve Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 14;

	/**
	 * The operation id for the '<em>Is Appropriate check Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPROPRIATE_CHECK_CSP_BWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 15;

	/**
	 * The operation id for the '<em>Is Applicable solve Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_NODETORULE_TGGRULE_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_BOX = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 16;

	/**
	 * The operation id for the '<em>Is Applicable check Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPLICABLE_CHECK_CSP_BWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 17;

	/**
	 * The operation id for the '<em>Register Objects BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 18;

	/**
	 * The operation id for the '<em>Check Types BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___CHECK_TYPES_BWD__MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 19;

	/**
	 * The operation id for the '<em>Is Appropriate FWD EMoflon Edge 59</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPROPRIATE_FWD_EMOFLON_EDGE_59__EMOFLONEDGE = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 20;

	/**
	 * The operation id for the '<em>Is Appropriate BWD EMoflon Edge 84</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPROPRIATE_BWD_EMOFLON_EDGE_84__EMOFLONEDGE = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 21;

	/**
	 * The operation id for the '<em>Check Attributes FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 22;

	/**
	 * The operation id for the '<em>Check Attributes BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 23;

	/**
	 * The operation id for the '<em>Is Applicable CC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPLICABLE_CC__MATCH_MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 24;

	/**
	 * The operation id for the '<em>Is Applicable solve Csp CC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPLICABLE_SOLVE_CSP_CC__TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_COMPOSITE_TGGRULE_BOX_MATCH_MATCH = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 25;

	/**
	 * The operation id for the '<em>Is Applicable check Csp CC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___IS_APPLICABLE_CHECK_CSP_CC__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 26;

	/**
	 * The operation id for the '<em>Check DEC FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___CHECK_DEC_FWD__DIRECTEDGRAPH_BOX_COMPOSITE_BOX = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 27;

	/**
	 * The operation id for the '<em>Check DEC BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___CHECK_DEC_BWD__TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 28;

	/**
	 * The operation id for the '<em>Generate Model</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___GENERATE_MODEL__RULEENTRYCONTAINER_NODETORULE = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 29;

	/**
	 * The operation id for the '<em>Generate Model solve Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_NODETORULE_TGGRULE_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_BOX_MODELGENERATORRULERESULT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT
			+ 30;

	/**
	 * The operation id for the '<em>Generate Model check Csp BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE___GENERATE_MODEL_CHECK_CSP_BWD__CSP = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 31;

	/**
	 * The number of operations of the '<em>Complement Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEMENT_RULE_OPERATION_COUNT = RuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 32;

	/**
	 * Returns the meta object for class '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules <em>Refinment Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Refinment Rules</em>'.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules
	 * @generated
	 */
	EClass getRefinmentRules();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Inheritance, org.moflon.ide.visualization.dot.language.DirectedGraph) <em>Is Appropriate FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Inheritance, org.moflon.ide.visualization.dot.language.DirectedGraph)
	 * @generated
	 */
	EOperation getRefinmentRules__IsAppropriate_FWD__Match_Box_Box_Inheritance_DirectedGraph();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#perform_FWD(org.moflon.tgg.runtime.IsApplicableMatch) <em>Perform FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#perform_FWD(org.moflon.tgg.runtime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getRefinmentRules__Perform_FWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_FWD(org.moflon.tgg.runtime.Match) <em>Is Applicable FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_FWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getRefinmentRules__IsApplicable_FWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#registerObjectsToMatch_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Inheritance, org.moflon.ide.visualization.dot.language.DirectedGraph) <em>Register Objects To Match FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects To Match FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#registerObjectsToMatch_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Inheritance, org.moflon.ide.visualization.dot.language.DirectedGraph)
	 * @generated
	 */
	EOperation getRefinmentRules__RegisterObjectsToMatch_FWD__Match_Box_Box_Inheritance_DirectedGraph();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_solveCsp_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Inheritance, org.moflon.ide.visualization.dot.language.DirectedGraph) <em>Is Appropriate solve Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate solve Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_solveCsp_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Inheritance, org.moflon.ide.visualization.dot.language.DirectedGraph)
	 * @generated
	 */
	EOperation getRefinmentRules__IsAppropriate_solveCsp_FWD__Match_Box_Box_Inheritance_DirectedGraph();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_checkCsp_FWD(org.moflon.tgg.language.csp.CSP) <em>Is Appropriate check Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate check Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_checkCsp_FWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getRefinmentRules__IsAppropriate_checkCsp_FWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_solveCsp_FWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Inheritance, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar) <em>Is Applicable solve Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable solve Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_solveCsp_FWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Inheritance, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar)
	 * @generated
	 */
	EOperation getRefinmentRules__IsApplicable_solveCsp_FWD__IsApplicableMatch_Box_NodeToRule_Box_Inheritance_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_DirectedGraphToTripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_checkCsp_FWD(org.moflon.tgg.language.csp.CSP) <em>Is Applicable check Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable check Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_checkCsp_FWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getRefinmentRules__IsApplicable_checkCsp_FWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#registerObjects_FWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject) <em>Register Objects FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#registerObjects_FWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getRefinmentRules__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#checkTypes_FWD(org.moflon.tgg.runtime.Match) <em>Check Types FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Types FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#checkTypes_FWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getRefinmentRules__CheckTypes_FWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar) <em>Is Appropriate BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getRefinmentRules__IsAppropriate_BWD__Match_TGGRule_TGGRule_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#perform_BWD(org.moflon.tgg.runtime.IsApplicableMatch) <em>Perform BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#perform_BWD(org.moflon.tgg.runtime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getRefinmentRules__Perform_BWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_BWD(org.moflon.tgg.runtime.Match) <em>Is Applicable BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_BWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getRefinmentRules__IsApplicable_BWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#registerObjectsToMatch_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar) <em>Register Objects To Match BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects To Match BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#registerObjectsToMatch_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getRefinmentRules__RegisterObjectsToMatch_BWD__Match_TGGRule_TGGRule_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_solveCsp_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar) <em>Is Appropriate solve Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate solve Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_solveCsp_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getRefinmentRules__IsAppropriate_solveCsp_BWD__Match_TGGRule_TGGRule_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_checkCsp_BWD(org.moflon.tgg.language.csp.CSP) <em>Is Appropriate check Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate check Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_checkCsp_BWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getRefinmentRules__IsAppropriate_checkCsp_BWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule, org.moflon.ide.visualization.dot.language.Box, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar) <em>Is Applicable solve Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable solve Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule, org.moflon.ide.visualization.dot.language.Box, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar)
	 * @generated
	 */
	EOperation getRefinmentRules__IsApplicable_solveCsp_BWD__IsApplicableMatch_Box_NodeToRule_Box_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_DirectedGraphToTripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_checkCsp_BWD(org.moflon.tgg.language.csp.CSP) <em>Is Applicable check Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable check Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_checkCsp_BWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getRefinmentRules__IsApplicable_checkCsp_BWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#registerObjects_BWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject) <em>Register Objects BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#registerObjects_BWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getRefinmentRules__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#checkTypes_BWD(org.moflon.tgg.runtime.Match) <em>Check Types BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Types BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#checkTypes_BWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getRefinmentRules__CheckTypes_BWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_FWD_EMoflonEdge_57(org.moflon.tgg.runtime.EMoflonEdge) <em>Is Appropriate FWD EMoflon Edge 57</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD EMoflon Edge 57</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_FWD_EMoflonEdge_57(org.moflon.tgg.runtime.EMoflonEdge)
	 * @generated
	 */
	EOperation getRefinmentRules__IsAppropriate_FWD_EMoflonEdge_57__EMoflonEdge();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_BWD_EMoflonEdge_82(org.moflon.tgg.runtime.EMoflonEdge) <em>Is Appropriate BWD EMoflon Edge 82</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD EMoflon Edge 82</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isAppropriate_BWD_EMoflonEdge_82(org.moflon.tgg.runtime.EMoflonEdge)
	 * @generated
	 */
	EOperation getRefinmentRules__IsAppropriate_BWD_EMoflonEdge_82__EMoflonEdge();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#checkAttributes_FWD(org.moflon.tgg.runtime.TripleMatch) <em>Check Attributes FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Attributes FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#checkAttributes_FWD(org.moflon.tgg.runtime.TripleMatch)
	 * @generated
	 */
	EOperation getRefinmentRules__CheckAttributes_FWD__TripleMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#checkAttributes_BWD(org.moflon.tgg.runtime.TripleMatch) <em>Check Attributes BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Attributes BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#checkAttributes_BWD(org.moflon.tgg.runtime.TripleMatch)
	 * @generated
	 */
	EOperation getRefinmentRules__CheckAttributes_BWD__TripleMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_CC(org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match) <em>Is Applicable CC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable CC</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_CC(org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getRefinmentRules__IsApplicable_CC__Match_Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_solveCsp_CC(org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Inheritance, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match) <em>Is Applicable solve Csp CC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable solve Csp CC</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_solveCsp_CC(org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Inheritance, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getRefinmentRules__IsApplicable_solveCsp_CC__Box_Box_Inheritance_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_Match_Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_checkCsp_CC(org.moflon.tgg.language.csp.CSP) <em>Is Applicable check Csp CC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable check Csp CC</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#isApplicable_checkCsp_CC(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getRefinmentRules__IsApplicable_checkCsp_CC__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#checkDEC_FWD(org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Inheritance, org.moflon.ide.visualization.dot.language.DirectedGraph) <em>Check DEC FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check DEC FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#checkDEC_FWD(org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Inheritance, org.moflon.ide.visualization.dot.language.DirectedGraph)
	 * @generated
	 */
	EOperation getRefinmentRules__CheckDEC_FWD__Box_Box_Inheritance_DirectedGraph();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#checkDEC_BWD(org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar) <em>Check DEC BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check DEC BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#checkDEC_BWD(org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getRefinmentRules__CheckDEC_BWD__TGGRule_TGGRule_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#generateModel(org.moflon.tgg.language.modelgenerator.RuleEntryContainer, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule) <em>Generate Model</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Generate Model</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#generateModel(org.moflon.tgg.language.modelgenerator.RuleEntryContainer, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule)
	 * @generated
	 */
	EOperation getRefinmentRules__GenerateModel__RuleEntryContainer_NodeToRule();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#generateModel_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule, org.moflon.ide.visualization.dot.language.Box, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.tgg.runtime.ModelgeneratorRuleResult) <em>Generate Model solve Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Generate Model solve Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#generateModel_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule, org.moflon.ide.visualization.dot.language.Box, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.tgg.runtime.ModelgeneratorRuleResult)
	 * @generated
	 */
	EOperation getRefinmentRules__GenerateModel_solveCsp_BWD__IsApplicableMatch_Box_NodeToRule_Box_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_DirectedGraphToTripleGraphGrammar_ModelgeneratorRuleResult();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#generateModel_checkCsp_BWD(org.moflon.tgg.language.csp.CSP) <em>Generate Model check Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Generate Model check Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules#generateModel_checkCsp_BWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getRefinmentRules__GenerateModel_checkCsp_BWD__CSP();

	/**
	 * Returns the meta object for class '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules <em>Fill Base Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fill Base Rules</em>'.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules
	 * @generated
	 */
	EClass getFillBaseRules();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box) <em>Is Appropriate FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box)
	 * @generated
	 */
	EOperation getFillBaseRules__IsAppropriate_FWD__Match_DirectedGraph_Box();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#perform_FWD(org.moflon.tgg.runtime.IsApplicableMatch) <em>Perform FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#perform_FWD(org.moflon.tgg.runtime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getFillBaseRules__Perform_FWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_FWD(org.moflon.tgg.runtime.Match) <em>Is Applicable FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_FWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getFillBaseRules__IsApplicable_FWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#registerObjectsToMatch_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box) <em>Register Objects To Match FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects To Match FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#registerObjectsToMatch_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box)
	 * @generated
	 */
	EOperation getFillBaseRules__RegisterObjectsToMatch_FWD__Match_DirectedGraph_Box();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_solveCsp_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box) <em>Is Appropriate solve Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate solve Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_solveCsp_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box)
	 * @generated
	 */
	EOperation getFillBaseRules__IsAppropriate_solveCsp_FWD__Match_DirectedGraph_Box();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_checkCsp_FWD(org.moflon.tgg.language.csp.CSP) <em>Is Appropriate check Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate check Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_checkCsp_FWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getFillBaseRules__IsAppropriate_checkCsp_FWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_solveCsp_FWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.tgg.language.TripleGraphGrammar) <em>Is Applicable solve Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable solve Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_solveCsp_FWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getFillBaseRules__IsApplicable_solveCsp_FWD__IsApplicableMatch_DirectedGraphToTripleGraphGrammar_DirectedGraph_Box_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_checkCsp_FWD(org.moflon.tgg.language.csp.CSP) <em>Is Applicable check Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable check Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_checkCsp_FWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getFillBaseRules__IsApplicable_checkCsp_FWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#registerObjects_FWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject) <em>Register Objects FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#registerObjects_FWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getFillBaseRules__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#checkTypes_FWD(org.moflon.tgg.runtime.Match) <em>Check Types FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Types FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#checkTypes_FWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getFillBaseRules__CheckTypes_FWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar) <em>Is Appropriate BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getFillBaseRules__IsAppropriate_BWD__Match_TGGRule_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#perform_BWD(org.moflon.tgg.runtime.IsApplicableMatch) <em>Perform BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#perform_BWD(org.moflon.tgg.runtime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getFillBaseRules__Perform_BWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_BWD(org.moflon.tgg.runtime.Match) <em>Is Applicable BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_BWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getFillBaseRules__IsApplicable_BWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#registerObjectsToMatch_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar) <em>Register Objects To Match BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects To Match BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#registerObjectsToMatch_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getFillBaseRules__RegisterObjectsToMatch_BWD__Match_TGGRule_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_solveCsp_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar) <em>Is Appropriate solve Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate solve Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_solveCsp_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getFillBaseRules__IsAppropriate_solveCsp_BWD__Match_TGGRule_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_checkCsp_BWD(org.moflon.tgg.language.csp.CSP) <em>Is Appropriate check Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate check Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_checkCsp_BWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getFillBaseRules__IsAppropriate_checkCsp_BWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.tgg.language.TGGRule, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.tgg.language.TripleGraphGrammar) <em>Is Applicable solve Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable solve Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.tgg.language.TGGRule, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getFillBaseRules__IsApplicable_solveCsp_BWD__IsApplicableMatch_DirectedGraphToTripleGraphGrammar_TGGRule_DirectedGraph_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_checkCsp_BWD(org.moflon.tgg.language.csp.CSP) <em>Is Applicable check Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable check Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_checkCsp_BWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getFillBaseRules__IsApplicable_checkCsp_BWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#registerObjects_BWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject) <em>Register Objects BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#registerObjects_BWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getFillBaseRules__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#checkTypes_BWD(org.moflon.tgg.runtime.Match) <em>Check Types BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Types BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#checkTypes_BWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getFillBaseRules__CheckTypes_BWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_FWD_EMoflonEdge_58(org.moflon.tgg.runtime.EMoflonEdge) <em>Is Appropriate FWD EMoflon Edge 58</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD EMoflon Edge 58</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_FWD_EMoflonEdge_58(org.moflon.tgg.runtime.EMoflonEdge)
	 * @generated
	 */
	EOperation getFillBaseRules__IsAppropriate_FWD_EMoflonEdge_58__EMoflonEdge();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_BWD_EMoflonEdge_83(org.moflon.tgg.runtime.EMoflonEdge) <em>Is Appropriate BWD EMoflon Edge 83</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD EMoflon Edge 83</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isAppropriate_BWD_EMoflonEdge_83(org.moflon.tgg.runtime.EMoflonEdge)
	 * @generated
	 */
	EOperation getFillBaseRules__IsAppropriate_BWD_EMoflonEdge_83__EMoflonEdge();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#checkAttributes_FWD(org.moflon.tgg.runtime.TripleMatch) <em>Check Attributes FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Attributes FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#checkAttributes_FWD(org.moflon.tgg.runtime.TripleMatch)
	 * @generated
	 */
	EOperation getFillBaseRules__CheckAttributes_FWD__TripleMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#checkAttributes_BWD(org.moflon.tgg.runtime.TripleMatch) <em>Check Attributes BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Attributes BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#checkAttributes_BWD(org.moflon.tgg.runtime.TripleMatch)
	 * @generated
	 */
	EOperation getFillBaseRules__CheckAttributes_BWD__TripleMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_CC(org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match) <em>Is Applicable CC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable CC</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_CC(org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getFillBaseRules__IsApplicable_CC__Match_Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_solveCsp_CC(org.moflon.tgg.language.TGGRule, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match) <em>Is Applicable solve Csp CC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable solve Csp CC</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_solveCsp_CC(org.moflon.tgg.language.TGGRule, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getFillBaseRules__IsApplicable_solveCsp_CC__TGGRule_DirectedGraph_Box_TripleGraphGrammar_Match_Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_checkCsp_CC(org.moflon.tgg.language.csp.CSP) <em>Is Applicable check Csp CC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable check Csp CC</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#isApplicable_checkCsp_CC(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getFillBaseRules__IsApplicable_checkCsp_CC__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#checkDEC_FWD(org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box) <em>Check DEC FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check DEC FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#checkDEC_FWD(org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box)
	 * @generated
	 */
	EOperation getFillBaseRules__CheckDEC_FWD__DirectedGraph_Box();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#checkDEC_BWD(org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar) <em>Check DEC BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check DEC BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#checkDEC_BWD(org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getFillBaseRules__CheckDEC_BWD__TGGRule_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#generateModel(org.moflon.tgg.language.modelgenerator.RuleEntryContainer, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar) <em>Generate Model</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Generate Model</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#generateModel(org.moflon.tgg.language.modelgenerator.RuleEntryContainer, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar)
	 * @generated
	 */
	EOperation getFillBaseRules__GenerateModel__RuleEntryContainer_DirectedGraphToTripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#generateModel_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.runtime.ModelgeneratorRuleResult) <em>Generate Model solve Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Generate Model solve Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#generateModel_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.runtime.ModelgeneratorRuleResult)
	 * @generated
	 */
	EOperation getFillBaseRules__GenerateModel_solveCsp_BWD__IsApplicableMatch_DirectedGraphToTripleGraphGrammar_DirectedGraph_TripleGraphGrammar_ModelgeneratorRuleResult();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#generateModel_checkCsp_BWD(org.moflon.tgg.language.csp.CSP) <em>Generate Model check Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Generate Model check Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules#generateModel_checkCsp_BWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getFillBaseRules__GenerateModel_checkCsp_BWD__CSP();

	/**
	 * Returns the meta object for class '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom <em>TGG Grammar Directed Graph Axiom</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Grammar Directed Graph Axiom</em>'.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom
	 * @generated
	 */
	EClass getTGGGrammarDirectedGraphAxiom();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph) <em>Is Appropriate FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_FWD__Match_DirectedGraph();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#perform_FWD(org.moflon.tgg.runtime.IsApplicableMatch) <em>Perform FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#perform_FWD(org.moflon.tgg.runtime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__Perform_FWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_FWD(org.moflon.tgg.runtime.Match) <em>Is Applicable FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_FWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_FWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#registerObjectsToMatch_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph) <em>Register Objects To Match FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects To Match FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#registerObjectsToMatch_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__RegisterObjectsToMatch_FWD__Match_DirectedGraph();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_solveCsp_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph) <em>Is Appropriate solve Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate solve Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_solveCsp_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_solveCsp_FWD__Match_DirectedGraph();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_checkCsp_FWD(org.moflon.tgg.language.csp.CSP) <em>Is Appropriate check Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate check Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_checkCsp_FWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_checkCsp_FWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_solveCsp_FWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.language.DirectedGraph) <em>Is Applicable solve Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable solve Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_solveCsp_FWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.ide.visualization.dot.language.DirectedGraph)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_solveCsp_FWD__IsApplicableMatch_DirectedGraph();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_checkCsp_FWD(org.moflon.tgg.language.csp.CSP) <em>Is Applicable check Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable check Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_checkCsp_FWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_checkCsp_FWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#registerObjects_FWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject) <em>Register Objects FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#registerObjects_FWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#checkTypes_FWD(org.moflon.tgg.runtime.Match) <em>Check Types FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Types FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#checkTypes_FWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__CheckTypes_FWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TripleGraphGrammar) <em>Is Appropriate BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_BWD__Match_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#perform_BWD(org.moflon.tgg.runtime.IsApplicableMatch) <em>Perform BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#perform_BWD(org.moflon.tgg.runtime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__Perform_BWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_BWD(org.moflon.tgg.runtime.Match) <em>Is Applicable BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_BWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_BWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#registerObjectsToMatch_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TripleGraphGrammar) <em>Register Objects To Match BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects To Match BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#registerObjectsToMatch_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__RegisterObjectsToMatch_BWD__Match_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_solveCsp_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TripleGraphGrammar) <em>Is Appropriate solve Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate solve Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_solveCsp_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_solveCsp_BWD__Match_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_checkCsp_BWD(org.moflon.tgg.language.csp.CSP) <em>Is Appropriate check Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate check Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_checkCsp_BWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_checkCsp_BWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.tgg.language.TripleGraphGrammar) <em>Is Applicable solve Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable solve Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_solveCsp_BWD__IsApplicableMatch_TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_checkCsp_BWD(org.moflon.tgg.language.csp.CSP) <em>Is Applicable check Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable check Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_checkCsp_BWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_checkCsp_BWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#registerObjects_BWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject) <em>Register Objects BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#registerObjects_BWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#checkTypes_BWD(org.moflon.tgg.runtime.Match) <em>Check Types BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Types BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#checkTypes_BWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__CheckTypes_BWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_FWD_DirectedGraph_0(org.moflon.ide.visualization.dot.language.DirectedGraph) <em>Is Appropriate FWD Directed Graph 0</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Directed Graph 0</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_FWD_DirectedGraph_0(org.moflon.ide.visualization.dot.language.DirectedGraph)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_FWD_DirectedGraph_0__DirectedGraph();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_BWD_TripleGraphGrammar_0(org.moflon.tgg.language.TripleGraphGrammar) <em>Is Appropriate BWD Triple Graph Grammar 0</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Triple Graph Grammar 0</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isAppropriate_BWD_TripleGraphGrammar_0(org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_BWD_TripleGraphGrammar_0__TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#checkAttributes_FWD(org.moflon.tgg.runtime.TripleMatch) <em>Check Attributes FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Attributes FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#checkAttributes_FWD(org.moflon.tgg.runtime.TripleMatch)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__CheckAttributes_FWD__TripleMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#checkAttributes_BWD(org.moflon.tgg.runtime.TripleMatch) <em>Check Attributes BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Attributes BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#checkAttributes_BWD(org.moflon.tgg.runtime.TripleMatch)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__CheckAttributes_BWD__TripleMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_CC(org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match) <em>Is Applicable CC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable CC</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_CC(org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_CC__Match_Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_solveCsp_CC(org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match) <em>Is Applicable solve Csp CC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable solve Csp CC</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_solveCsp_CC(org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_solveCsp_CC__DirectedGraph_TripleGraphGrammar_Match_Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_checkCsp_CC(org.moflon.tgg.language.csp.CSP) <em>Is Applicable check Csp CC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable check Csp CC</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#isApplicable_checkCsp_CC(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_checkCsp_CC__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#checkDEC_FWD(org.moflon.ide.visualization.dot.language.DirectedGraph) <em>Check DEC FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check DEC FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#checkDEC_FWD(org.moflon.ide.visualization.dot.language.DirectedGraph)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__CheckDEC_FWD__DirectedGraph();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#checkDEC_BWD(org.moflon.tgg.language.TripleGraphGrammar) <em>Check DEC BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check DEC BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#checkDEC_BWD(org.moflon.tgg.language.TripleGraphGrammar)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__CheckDEC_BWD__TripleGraphGrammar();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#generateModel(org.moflon.tgg.language.modelgenerator.RuleEntryContainer) <em>Generate Model</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Generate Model</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#generateModel(org.moflon.tgg.language.modelgenerator.RuleEntryContainer)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__GenerateModel__RuleEntryContainer();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#generateModel_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.tgg.runtime.ModelgeneratorRuleResult) <em>Generate Model solve Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Generate Model solve Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#generateModel_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.tgg.runtime.ModelgeneratorRuleResult)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__GenerateModel_solveCsp_BWD__IsApplicableMatch_ModelgeneratorRuleResult();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#generateModel_checkCsp_BWD(org.moflon.tgg.language.csp.CSP) <em>Generate Model check Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Generate Model check Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom#generateModel_checkCsp_BWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getTGGGrammarDirectedGraphAxiom__GenerateModel_checkCsp_BWD__CSP();

	/**
	 * Returns the meta object for class '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule <em>Complement Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Complement Rule</em>'.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule
	 * @generated
	 */
	EClass getComplementRule();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Composite, org.moflon.ide.visualization.dot.language.Box) <em>Is Appropriate FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Composite, org.moflon.ide.visualization.dot.language.Box)
	 * @generated
	 */
	EOperation getComplementRule__IsAppropriate_FWD__Match_DirectedGraph_Box_Composite_Box();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#perform_FWD(org.moflon.tgg.runtime.IsApplicableMatch) <em>Perform FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#perform_FWD(org.moflon.tgg.runtime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getComplementRule__Perform_FWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_FWD(org.moflon.tgg.runtime.Match) <em>Is Applicable FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_FWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getComplementRule__IsApplicable_FWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#registerObjectsToMatch_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Composite, org.moflon.ide.visualization.dot.language.Box) <em>Register Objects To Match FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects To Match FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#registerObjectsToMatch_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Composite, org.moflon.ide.visualization.dot.language.Box)
	 * @generated
	 */
	EOperation getComplementRule__RegisterObjectsToMatch_FWD__Match_DirectedGraph_Box_Composite_Box();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_solveCsp_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Composite, org.moflon.ide.visualization.dot.language.Box) <em>Is Appropriate solve Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate solve Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_solveCsp_FWD(org.moflon.tgg.runtime.Match, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Composite, org.moflon.ide.visualization.dot.language.Box)
	 * @generated
	 */
	EOperation getComplementRule__IsAppropriate_solveCsp_FWD__Match_DirectedGraph_Box_Composite_Box();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_checkCsp_FWD(org.moflon.tgg.language.csp.CSP) <em>Is Appropriate check Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate check Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_checkCsp_FWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getComplementRule__IsAppropriate_checkCsp_FWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_solveCsp_FWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule, org.moflon.ide.visualization.dot.language.Composite, org.moflon.tgg.language.TGGRule, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.ide.visualization.dot.language.Box) <em>Is Applicable solve Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable solve Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_solveCsp_FWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule, org.moflon.ide.visualization.dot.language.Composite, org.moflon.tgg.language.TGGRule, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.ide.visualization.dot.language.Box)
	 * @generated
	 */
	EOperation getComplementRule__IsApplicable_solveCsp_FWD__IsApplicableMatch_TGGRule_TripleGraphGrammar_DirectedGraph_Box_NodeToRule_Composite_TGGRule_DirectedGraphToTripleGraphGrammar_Box();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_checkCsp_FWD(org.moflon.tgg.language.csp.CSP) <em>Is Applicable check Csp FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable check Csp FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_checkCsp_FWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getComplementRule__IsApplicable_checkCsp_FWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#registerObjects_FWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject) <em>Register Objects FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#registerObjects_FWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getComplementRule__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#checkTypes_FWD(org.moflon.tgg.runtime.Match) <em>Check Types FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Types FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#checkTypes_FWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getComplementRule__CheckTypes_FWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.language.TGGRule) <em>Is Appropriate BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.language.TGGRule)
	 * @generated
	 */
	EOperation getComplementRule__IsAppropriate_BWD__Match_TGGRule_TripleGraphGrammar_TGGRule();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#perform_BWD(org.moflon.tgg.runtime.IsApplicableMatch) <em>Perform BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#perform_BWD(org.moflon.tgg.runtime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getComplementRule__Perform_BWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_BWD(org.moflon.tgg.runtime.Match) <em>Is Applicable BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_BWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getComplementRule__IsApplicable_BWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#registerObjectsToMatch_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.language.TGGRule) <em>Register Objects To Match BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects To Match BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#registerObjectsToMatch_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.language.TGGRule)
	 * @generated
	 */
	EOperation getComplementRule__RegisterObjectsToMatch_BWD__Match_TGGRule_TripleGraphGrammar_TGGRule();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_solveCsp_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.language.TGGRule) <em>Is Appropriate solve Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate solve Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_solveCsp_BWD(org.moflon.tgg.runtime.Match, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.language.TGGRule)
	 * @generated
	 */
	EOperation getComplementRule__IsAppropriate_solveCsp_BWD__Match_TGGRule_TripleGraphGrammar_TGGRule();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_checkCsp_BWD(org.moflon.tgg.language.csp.CSP) <em>Is Appropriate check Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate check Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_checkCsp_BWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getComplementRule__IsAppropriate_checkCsp_BWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule, org.moflon.tgg.language.TGGRule, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.ide.visualization.dot.language.Box) <em>Is Applicable solve Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable solve Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule, org.moflon.tgg.language.TGGRule, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.ide.visualization.dot.language.Box)
	 * @generated
	 */
	EOperation getComplementRule__IsApplicable_solveCsp_BWD__IsApplicableMatch_TGGRule_TripleGraphGrammar_DirectedGraph_Box_NodeToRule_TGGRule_DirectedGraphToTripleGraphGrammar_Box();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_checkCsp_BWD(org.moflon.tgg.language.csp.CSP) <em>Is Applicable check Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable check Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_checkCsp_BWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getComplementRule__IsApplicable_checkCsp_BWD__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#registerObjects_BWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject) <em>Register Objects BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Register Objects BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#registerObjects_BWD(org.moflon.tgg.runtime.PerformRuleResult, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getComplementRule__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#checkTypes_BWD(org.moflon.tgg.runtime.Match) <em>Check Types BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Types BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#checkTypes_BWD(org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getComplementRule__CheckTypes_BWD__Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_FWD_EMoflonEdge_59(org.moflon.tgg.runtime.EMoflonEdge) <em>Is Appropriate FWD EMoflon Edge 59</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD EMoflon Edge 59</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_FWD_EMoflonEdge_59(org.moflon.tgg.runtime.EMoflonEdge)
	 * @generated
	 */
	EOperation getComplementRule__IsAppropriate_FWD_EMoflonEdge_59__EMoflonEdge();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_BWD_EMoflonEdge_84(org.moflon.tgg.runtime.EMoflonEdge) <em>Is Appropriate BWD EMoflon Edge 84</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD EMoflon Edge 84</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isAppropriate_BWD_EMoflonEdge_84(org.moflon.tgg.runtime.EMoflonEdge)
	 * @generated
	 */
	EOperation getComplementRule__IsAppropriate_BWD_EMoflonEdge_84__EMoflonEdge();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#checkAttributes_FWD(org.moflon.tgg.runtime.TripleMatch) <em>Check Attributes FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Attributes FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#checkAttributes_FWD(org.moflon.tgg.runtime.TripleMatch)
	 * @generated
	 */
	EOperation getComplementRule__CheckAttributes_FWD__TripleMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#checkAttributes_BWD(org.moflon.tgg.runtime.TripleMatch) <em>Check Attributes BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Attributes BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#checkAttributes_BWD(org.moflon.tgg.runtime.TripleMatch)
	 * @generated
	 */
	EOperation getComplementRule__CheckAttributes_BWD__TripleMatch();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_CC(org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match) <em>Is Applicable CC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable CC</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_CC(org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getComplementRule__IsApplicable_CC__Match_Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_solveCsp_CC(org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Composite, org.moflon.tgg.language.TGGRule, org.moflon.ide.visualization.dot.language.Box, org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match) <em>Is Applicable solve Csp CC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable solve Csp CC</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_solveCsp_CC(org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Composite, org.moflon.tgg.language.TGGRule, org.moflon.ide.visualization.dot.language.Box, org.moflon.tgg.runtime.Match, org.moflon.tgg.runtime.Match)
	 * @generated
	 */
	EOperation getComplementRule__IsApplicable_solveCsp_CC__TGGRule_TripleGraphGrammar_DirectedGraph_Box_Composite_TGGRule_Box_Match_Match();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_checkCsp_CC(org.moflon.tgg.language.csp.CSP) <em>Is Applicable check Csp CC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable check Csp CC</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#isApplicable_checkCsp_CC(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getComplementRule__IsApplicable_checkCsp_CC__CSP();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#checkDEC_FWD(org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Composite, org.moflon.ide.visualization.dot.language.Box) <em>Check DEC FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check DEC FWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#checkDEC_FWD(org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.language.Composite, org.moflon.ide.visualization.dot.language.Box)
	 * @generated
	 */
	EOperation getComplementRule__CheckDEC_FWD__DirectedGraph_Box_Composite_Box();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#checkDEC_BWD(org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.language.TGGRule) <em>Check DEC BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check DEC BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#checkDEC_BWD(org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.tgg.language.TGGRule)
	 * @generated
	 */
	EOperation getComplementRule__CheckDEC_BWD__TGGRule_TripleGraphGrammar_TGGRule();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#generateModel(org.moflon.tgg.language.modelgenerator.RuleEntryContainer, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule) <em>Generate Model</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Generate Model</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#generateModel(org.moflon.tgg.language.modelgenerator.RuleEntryContainer, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule)
	 * @generated
	 */
	EOperation getComplementRule__GenerateModel__RuleEntryContainer_NodeToRule();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#generateModel_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule, org.moflon.tgg.language.TGGRule, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.ide.visualization.dot.language.Box, org.moflon.tgg.runtime.ModelgeneratorRuleResult) <em>Generate Model solve Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Generate Model solve Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#generateModel_solveCsp_BWD(org.moflon.tgg.runtime.IsApplicableMatch, org.moflon.tgg.language.TGGRule, org.moflon.tgg.language.TripleGraphGrammar, org.moflon.ide.visualization.dot.language.DirectedGraph, org.moflon.ide.visualization.dot.language.Box, org.moflon.ide.visualization.dot.tgg.schema.NodeToRule, org.moflon.tgg.language.TGGRule, org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar, org.moflon.ide.visualization.dot.language.Box, org.moflon.tgg.runtime.ModelgeneratorRuleResult)
	 * @generated
	 */
	EOperation getComplementRule__GenerateModel_solveCsp_BWD__IsApplicableMatch_TGGRule_TripleGraphGrammar_DirectedGraph_Box_NodeToRule_TGGRule_DirectedGraphToTripleGraphGrammar_Box_ModelgeneratorRuleResult();

	/**
	 * Returns the meta object for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#generateModel_checkCsp_BWD(org.moflon.tgg.language.csp.CSP) <em>Generate Model check Csp BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Generate Model check Csp BWD</em>' operation.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule#generateModel_checkCsp_BWD(org.moflon.tgg.language.csp.CSP)
	 * @generated
	 */
	EOperation getComplementRule__GenerateModel_checkCsp_BWD__CSP();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RulesFactory getRulesFactory();

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
		 * The meta object literal for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RefinmentRulesImpl <em>Refinment Rules</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RefinmentRulesImpl
		 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RulesPackageImpl#getRefinmentRules()
		 * @generated
		 */
		EClass REFINMENT_RULES = eINSTANCE.getRefinmentRules();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPROPRIATE_FWD__MATCH_BOX_BOX_INHERITANCE_DIRECTEDGRAPH = eINSTANCE
				.getRefinmentRules__IsAppropriate_FWD__Match_Box_Box_Inheritance_DirectedGraph();

		/**
		 * The meta object literal for the '<em><b>Perform FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___PERFORM_FWD__ISAPPLICABLEMATCH = eINSTANCE
				.getRefinmentRules__Perform_FWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Applicable FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPLICABLE_FWD__MATCH = eINSTANCE.getRefinmentRules__IsApplicable_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Register Objects To Match FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_BOX_BOX_INHERITANCE_DIRECTEDGRAPH = eINSTANCE
				.getRefinmentRules__RegisterObjectsToMatch_FWD__Match_Box_Box_Inheritance_DirectedGraph();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate solve Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_BOX_BOX_INHERITANCE_DIRECTEDGRAPH = eINSTANCE
				.getRefinmentRules__IsAppropriate_solveCsp_FWD__Match_Box_Box_Inheritance_DirectedGraph();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate check Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPROPRIATE_CHECK_CSP_FWD__CSP = eINSTANCE
				.getRefinmentRules__IsAppropriate_checkCsp_FWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Is Applicable solve Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_BOX_NODETORULE_BOX_INHERITANCE_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR = eINSTANCE
				.getRefinmentRules__IsApplicable_solveCsp_FWD__IsApplicableMatch_Box_NodeToRule_Box_Inheritance_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_DirectedGraphToTripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Is Applicable check Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPLICABLE_CHECK_CSP_FWD__CSP = eINSTANCE
				.getRefinmentRules__IsApplicable_checkCsp_FWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Register Objects FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT = eINSTANCE
				.getRefinmentRules__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject();

		/**
		 * The meta object literal for the '<em><b>Check Types FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___CHECK_TYPES_FWD__MATCH = eINSTANCE.getRefinmentRules__CheckTypes_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPROPRIATE_BWD__MATCH_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getRefinmentRules__IsAppropriate_BWD__Match_TGGRule_TGGRule_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Perform BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___PERFORM_BWD__ISAPPLICABLEMATCH = eINSTANCE
				.getRefinmentRules__Perform_BWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Applicable BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPLICABLE_BWD__MATCH = eINSTANCE.getRefinmentRules__IsApplicable_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Register Objects To Match BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getRefinmentRules__RegisterObjectsToMatch_BWD__Match_TGGRule_TGGRule_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate solve Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getRefinmentRules__IsAppropriate_solveCsp_BWD__Match_TGGRule_TGGRule_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate check Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPROPRIATE_CHECK_CSP_BWD__CSP = eINSTANCE
				.getRefinmentRules__IsAppropriate_checkCsp_BWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Is Applicable solve Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_BOX_NODETORULE_BOX_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR = eINSTANCE
				.getRefinmentRules__IsApplicable_solveCsp_BWD__IsApplicableMatch_Box_NodeToRule_Box_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_DirectedGraphToTripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Is Applicable check Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPLICABLE_CHECK_CSP_BWD__CSP = eINSTANCE
				.getRefinmentRules__IsApplicable_checkCsp_BWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Register Objects BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT = eINSTANCE
				.getRefinmentRules__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject();

		/**
		 * The meta object literal for the '<em><b>Check Types BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___CHECK_TYPES_BWD__MATCH = eINSTANCE.getRefinmentRules__CheckTypes_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD EMoflon Edge 57</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPROPRIATE_FWD_EMOFLON_EDGE_57__EMOFLONEDGE = eINSTANCE
				.getRefinmentRules__IsAppropriate_FWD_EMoflonEdge_57__EMoflonEdge();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD EMoflon Edge 82</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPROPRIATE_BWD_EMOFLON_EDGE_82__EMOFLONEDGE = eINSTANCE
				.getRefinmentRules__IsAppropriate_BWD_EMoflonEdge_82__EMoflonEdge();

		/**
		 * The meta object literal for the '<em><b>Check Attributes FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH = eINSTANCE
				.getRefinmentRules__CheckAttributes_FWD__TripleMatch();

		/**
		 * The meta object literal for the '<em><b>Check Attributes BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH = eINSTANCE
				.getRefinmentRules__CheckAttributes_BWD__TripleMatch();

		/**
		 * The meta object literal for the '<em><b>Is Applicable CC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPLICABLE_CC__MATCH_MATCH = eINSTANCE
				.getRefinmentRules__IsApplicable_CC__Match_Match();

		/**
		 * The meta object literal for the '<em><b>Is Applicable solve Csp CC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPLICABLE_SOLVE_CSP_CC__BOX_BOX_INHERITANCE_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_MATCH_MATCH = eINSTANCE
				.getRefinmentRules__IsApplicable_solveCsp_CC__Box_Box_Inheritance_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_Match_Match();

		/**
		 * The meta object literal for the '<em><b>Is Applicable check Csp CC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___IS_APPLICABLE_CHECK_CSP_CC__CSP = eINSTANCE
				.getRefinmentRules__IsApplicable_checkCsp_CC__CSP();

		/**
		 * The meta object literal for the '<em><b>Check DEC FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___CHECK_DEC_FWD__BOX_BOX_INHERITANCE_DIRECTEDGRAPH = eINSTANCE
				.getRefinmentRules__CheckDEC_FWD__Box_Box_Inheritance_DirectedGraph();

		/**
		 * The meta object literal for the '<em><b>Check DEC BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___CHECK_DEC_BWD__TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getRefinmentRules__CheckDEC_BWD__TGGRule_TGGRule_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Generate Model</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___GENERATE_MODEL__RULEENTRYCONTAINER_NODETORULE = eINSTANCE
				.getRefinmentRules__GenerateModel__RuleEntryContainer_NodeToRule();

		/**
		 * The meta object literal for the '<em><b>Generate Model solve Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_BOX_NODETORULE_BOX_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_MODELGENERATORRULERESULT = eINSTANCE
				.getRefinmentRules__GenerateModel_solveCsp_BWD__IsApplicableMatch_Box_NodeToRule_Box_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_DirectedGraphToTripleGraphGrammar_ModelgeneratorRuleResult();

		/**
		 * The meta object literal for the '<em><b>Generate Model check Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFINMENT_RULES___GENERATE_MODEL_CHECK_CSP_BWD__CSP = eINSTANCE
				.getRefinmentRules__GenerateModel_checkCsp_BWD__CSP();

		/**
		 * The meta object literal for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.FillBaseRulesImpl <em>Fill Base Rules</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.FillBaseRulesImpl
		 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RulesPackageImpl#getFillBaseRules()
		 * @generated
		 */
		EClass FILL_BASE_RULES = eINSTANCE.getFillBaseRules();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPROPRIATE_FWD__MATCH_DIRECTEDGRAPH_BOX = eINSTANCE
				.getFillBaseRules__IsAppropriate_FWD__Match_DirectedGraph_Box();

		/**
		 * The meta object literal for the '<em><b>Perform FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___PERFORM_FWD__ISAPPLICABLEMATCH = eINSTANCE
				.getFillBaseRules__Perform_FWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Applicable FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPLICABLE_FWD__MATCH = eINSTANCE.getFillBaseRules__IsApplicable_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Register Objects To Match FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_DIRECTEDGRAPH_BOX = eINSTANCE
				.getFillBaseRules__RegisterObjectsToMatch_FWD__Match_DirectedGraph_Box();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate solve Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_DIRECTEDGRAPH_BOX = eINSTANCE
				.getFillBaseRules__IsAppropriate_solveCsp_FWD__Match_DirectedGraph_Box();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate check Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPROPRIATE_CHECK_CSP_FWD__CSP = eINSTANCE
				.getFillBaseRules__IsAppropriate_checkCsp_FWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Is Applicable solve Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getFillBaseRules__IsApplicable_solveCsp_FWD__IsApplicableMatch_DirectedGraphToTripleGraphGrammar_DirectedGraph_Box_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Is Applicable check Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPLICABLE_CHECK_CSP_FWD__CSP = eINSTANCE
				.getFillBaseRules__IsApplicable_checkCsp_FWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Register Objects FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT = eINSTANCE
				.getFillBaseRules__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject();

		/**
		 * The meta object literal for the '<em><b>Check Types FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___CHECK_TYPES_FWD__MATCH = eINSTANCE.getFillBaseRules__CheckTypes_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPROPRIATE_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getFillBaseRules__IsAppropriate_BWD__Match_TGGRule_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Perform BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___PERFORM_BWD__ISAPPLICABLEMATCH = eINSTANCE
				.getFillBaseRules__Perform_BWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Applicable BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPLICABLE_BWD__MATCH = eINSTANCE.getFillBaseRules__IsApplicable_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Register Objects To Match BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getFillBaseRules__RegisterObjectsToMatch_BWD__Match_TGGRule_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate solve Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getFillBaseRules__IsAppropriate_solveCsp_BWD__Match_TGGRule_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate check Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPROPRIATE_CHECK_CSP_BWD__CSP = eINSTANCE
				.getFillBaseRules__IsAppropriate_checkCsp_BWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Is Applicable solve Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_TGGRULE_DIRECTEDGRAPH_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getFillBaseRules__IsApplicable_solveCsp_BWD__IsApplicableMatch_DirectedGraphToTripleGraphGrammar_TGGRule_DirectedGraph_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Is Applicable check Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPLICABLE_CHECK_CSP_BWD__CSP = eINSTANCE
				.getFillBaseRules__IsApplicable_checkCsp_BWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Register Objects BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT = eINSTANCE
				.getFillBaseRules__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject();

		/**
		 * The meta object literal for the '<em><b>Check Types BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___CHECK_TYPES_BWD__MATCH = eINSTANCE.getFillBaseRules__CheckTypes_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD EMoflon Edge 58</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPROPRIATE_FWD_EMOFLON_EDGE_58__EMOFLONEDGE = eINSTANCE
				.getFillBaseRules__IsAppropriate_FWD_EMoflonEdge_58__EMoflonEdge();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD EMoflon Edge 83</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPROPRIATE_BWD_EMOFLON_EDGE_83__EMOFLONEDGE = eINSTANCE
				.getFillBaseRules__IsAppropriate_BWD_EMoflonEdge_83__EMoflonEdge();

		/**
		 * The meta object literal for the '<em><b>Check Attributes FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH = eINSTANCE
				.getFillBaseRules__CheckAttributes_FWD__TripleMatch();

		/**
		 * The meta object literal for the '<em><b>Check Attributes BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH = eINSTANCE
				.getFillBaseRules__CheckAttributes_BWD__TripleMatch();

		/**
		 * The meta object literal for the '<em><b>Is Applicable CC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPLICABLE_CC__MATCH_MATCH = eINSTANCE
				.getFillBaseRules__IsApplicable_CC__Match_Match();

		/**
		 * The meta object literal for the '<em><b>Is Applicable solve Csp CC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPLICABLE_SOLVE_CSP_CC__TGGRULE_DIRECTEDGRAPH_BOX_TRIPLEGRAPHGRAMMAR_MATCH_MATCH = eINSTANCE
				.getFillBaseRules__IsApplicable_solveCsp_CC__TGGRule_DirectedGraph_Box_TripleGraphGrammar_Match_Match();

		/**
		 * The meta object literal for the '<em><b>Is Applicable check Csp CC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___IS_APPLICABLE_CHECK_CSP_CC__CSP = eINSTANCE
				.getFillBaseRules__IsApplicable_checkCsp_CC__CSP();

		/**
		 * The meta object literal for the '<em><b>Check DEC FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___CHECK_DEC_FWD__DIRECTEDGRAPH_BOX = eINSTANCE
				.getFillBaseRules__CheckDEC_FWD__DirectedGraph_Box();

		/**
		 * The meta object literal for the '<em><b>Check DEC BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___CHECK_DEC_BWD__TGGRULE_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getFillBaseRules__CheckDEC_BWD__TGGRule_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Generate Model</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___GENERATE_MODEL__RULEENTRYCONTAINER_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR = eINSTANCE
				.getFillBaseRules__GenerateModel__RuleEntryContainer_DirectedGraphToTripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Generate Model solve Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_TRIPLEGRAPHGRAMMAR_MODELGENERATORRULERESULT = eINSTANCE
				.getFillBaseRules__GenerateModel_solveCsp_BWD__IsApplicableMatch_DirectedGraphToTripleGraphGrammar_DirectedGraph_TripleGraphGrammar_ModelgeneratorRuleResult();

		/**
		 * The meta object literal for the '<em><b>Generate Model check Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FILL_BASE_RULES___GENERATE_MODEL_CHECK_CSP_BWD__CSP = eINSTANCE
				.getFillBaseRules__GenerateModel_checkCsp_BWD__CSP();

		/**
		 * The meta object literal for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.TGGGrammarDirectedGraphAxiomImpl <em>TGG Grammar Directed Graph Axiom</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.TGGGrammarDirectedGraphAxiomImpl
		 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RulesPackageImpl#getTGGGrammarDirectedGraphAxiom()
		 * @generated
		 */
		EClass TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM = eINSTANCE.getTGGGrammarDirectedGraphAxiom();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_FWD__MATCH_DIRECTEDGRAPH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsAppropriate_FWD__Match_DirectedGraph();

		/**
		 * The meta object literal for the '<em><b>Perform FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___PERFORM_FWD__ISAPPLICABLEMATCH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__Perform_FWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Applicable FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_FWD__MATCH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsApplicable_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Register Objects To Match FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_DIRECTEDGRAPH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__RegisterObjectsToMatch_FWD__Match_DirectedGraph();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate solve Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_DIRECTEDGRAPH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsAppropriate_solveCsp_FWD__Match_DirectedGraph();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate check Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_CHECK_CSP_FWD__CSP = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsAppropriate_checkCsp_FWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Is Applicable solve Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_DIRECTEDGRAPH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsApplicable_solveCsp_FWD__IsApplicableMatch_DirectedGraph();

		/**
		 * The meta object literal for the '<em><b>Is Applicable check Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CHECK_CSP_FWD__CSP = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsApplicable_checkCsp_FWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Register Objects FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject();

		/**
		 * The meta object literal for the '<em><b>Check Types FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_TYPES_FWD__MATCH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__CheckTypes_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_BWD__MATCH_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsAppropriate_BWD__Match_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Perform BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___PERFORM_BWD__ISAPPLICABLEMATCH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__Perform_BWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Applicable BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_BWD__MATCH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsApplicable_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Register Objects To Match BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__RegisterObjectsToMatch_BWD__Match_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate solve Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsAppropriate_solveCsp_BWD__Match_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate check Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_CHECK_CSP_BWD__CSP = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsAppropriate_checkCsp_BWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Is Applicable solve Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsApplicable_solveCsp_BWD__IsApplicableMatch_TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Is Applicable check Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CHECK_CSP_BWD__CSP = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsApplicable_checkCsp_BWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Register Objects BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject();

		/**
		 * The meta object literal for the '<em><b>Check Types BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_TYPES_BWD__MATCH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__CheckTypes_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Directed Graph 0</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_FWD_DIRECTED_GRAPH_0__DIRECTEDGRAPH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsAppropriate_FWD_DirectedGraph_0__DirectedGraph();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Triple Graph Grammar 0</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_BWD_TRIPLE_GRAPH_GRAMMAR_0__TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsAppropriate_BWD_TripleGraphGrammar_0__TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Check Attributes FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__CheckAttributes_FWD__TripleMatch();

		/**
		 * The meta object literal for the '<em><b>Check Attributes BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__CheckAttributes_BWD__TripleMatch();

		/**
		 * The meta object literal for the '<em><b>Is Applicable CC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CC__MATCH_MATCH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsApplicable_CC__Match_Match();

		/**
		 * The meta object literal for the '<em><b>Is Applicable solve Csp CC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_SOLVE_CSP_CC__DIRECTEDGRAPH_TRIPLEGRAPHGRAMMAR_MATCH_MATCH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsApplicable_solveCsp_CC__DirectedGraph_TripleGraphGrammar_Match_Match();

		/**
		 * The meta object literal for the '<em><b>Is Applicable check Csp CC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CHECK_CSP_CC__CSP = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__IsApplicable_checkCsp_CC__CSP();

		/**
		 * The meta object literal for the '<em><b>Check DEC FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_DEC_FWD__DIRECTEDGRAPH = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__CheckDEC_FWD__DirectedGraph();

		/**
		 * The meta object literal for the '<em><b>Check DEC BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_DEC_BWD__TRIPLEGRAPHGRAMMAR = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__CheckDEC_BWD__TripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Generate Model</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___GENERATE_MODEL__RULEENTRYCONTAINER = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__GenerateModel__RuleEntryContainer();

		/**
		 * The meta object literal for the '<em><b>Generate Model solve Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_MODELGENERATORRULERESULT = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__GenerateModel_solveCsp_BWD__IsApplicableMatch_ModelgeneratorRuleResult();

		/**
		 * The meta object literal for the '<em><b>Generate Model check Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___GENERATE_MODEL_CHECK_CSP_BWD__CSP = eINSTANCE
				.getTGGGrammarDirectedGraphAxiom__GenerateModel_checkCsp_BWD__CSP();

		/**
		 * The meta object literal for the '{@link org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.ComplementRuleImpl <em>Complement Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.ComplementRuleImpl
		 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RulesPackageImpl#getComplementRule()
		 * @generated
		 */
		EClass COMPLEMENT_RULE = eINSTANCE.getComplementRule();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPROPRIATE_FWD__MATCH_DIRECTEDGRAPH_BOX_COMPOSITE_BOX = eINSTANCE
				.getComplementRule__IsAppropriate_FWD__Match_DirectedGraph_Box_Composite_Box();

		/**
		 * The meta object literal for the '<em><b>Perform FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = eINSTANCE
				.getComplementRule__Perform_FWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Applicable FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPLICABLE_FWD__MATCH = eINSTANCE.getComplementRule__IsApplicable_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Register Objects To Match FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_DIRECTEDGRAPH_BOX_COMPOSITE_BOX = eINSTANCE
				.getComplementRule__RegisterObjectsToMatch_FWD__Match_DirectedGraph_Box_Composite_Box();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate solve Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_DIRECTEDGRAPH_BOX_COMPOSITE_BOX = eINSTANCE
				.getComplementRule__IsAppropriate_solveCsp_FWD__Match_DirectedGraph_Box_Composite_Box();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate check Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPROPRIATE_CHECK_CSP_FWD__CSP = eINSTANCE
				.getComplementRule__IsAppropriate_checkCsp_FWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Is Applicable solve Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_NODETORULE_COMPOSITE_TGGRULE_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_BOX = eINSTANCE
				.getComplementRule__IsApplicable_solveCsp_FWD__IsApplicableMatch_TGGRule_TripleGraphGrammar_DirectedGraph_Box_NodeToRule_Composite_TGGRule_DirectedGraphToTripleGraphGrammar_Box();

		/**
		 * The meta object literal for the '<em><b>Is Applicable check Csp FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPLICABLE_CHECK_CSP_FWD__CSP = eINSTANCE
				.getComplementRule__IsApplicable_checkCsp_FWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Register Objects FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT = eINSTANCE
				.getComplementRule__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject();

		/**
		 * The meta object literal for the '<em><b>Check Types FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___CHECK_TYPES_FWD__MATCH = eINSTANCE.getComplementRule__CheckTypes_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPROPRIATE_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE = eINSTANCE
				.getComplementRule__IsAppropriate_BWD__Match_TGGRule_TripleGraphGrammar_TGGRule();

		/**
		 * The meta object literal for the '<em><b>Perform BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = eINSTANCE
				.getComplementRule__Perform_BWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Applicable BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPLICABLE_BWD__MATCH = eINSTANCE.getComplementRule__IsApplicable_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Register Objects To Match BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE = eINSTANCE
				.getComplementRule__RegisterObjectsToMatch_BWD__Match_TGGRule_TripleGraphGrammar_TGGRule();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate solve Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE = eINSTANCE
				.getComplementRule__IsAppropriate_solveCsp_BWD__Match_TGGRule_TripleGraphGrammar_TGGRule();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate check Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPROPRIATE_CHECK_CSP_BWD__CSP = eINSTANCE
				.getComplementRule__IsAppropriate_checkCsp_BWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Is Applicable solve Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_NODETORULE_TGGRULE_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_BOX = eINSTANCE
				.getComplementRule__IsApplicable_solveCsp_BWD__IsApplicableMatch_TGGRule_TripleGraphGrammar_DirectedGraph_Box_NodeToRule_TGGRule_DirectedGraphToTripleGraphGrammar_Box();

		/**
		 * The meta object literal for the '<em><b>Is Applicable check Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPLICABLE_CHECK_CSP_BWD__CSP = eINSTANCE
				.getComplementRule__IsApplicable_checkCsp_BWD__CSP();

		/**
		 * The meta object literal for the '<em><b>Register Objects BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT = eINSTANCE
				.getComplementRule__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject();

		/**
		 * The meta object literal for the '<em><b>Check Types BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___CHECK_TYPES_BWD__MATCH = eINSTANCE.getComplementRule__CheckTypes_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD EMoflon Edge 59</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPROPRIATE_FWD_EMOFLON_EDGE_59__EMOFLONEDGE = eINSTANCE
				.getComplementRule__IsAppropriate_FWD_EMoflonEdge_59__EMoflonEdge();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD EMoflon Edge 84</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPROPRIATE_BWD_EMOFLON_EDGE_84__EMOFLONEDGE = eINSTANCE
				.getComplementRule__IsAppropriate_BWD_EMoflonEdge_84__EMoflonEdge();

		/**
		 * The meta object literal for the '<em><b>Check Attributes FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH = eINSTANCE
				.getComplementRule__CheckAttributes_FWD__TripleMatch();

		/**
		 * The meta object literal for the '<em><b>Check Attributes BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH = eINSTANCE
				.getComplementRule__CheckAttributes_BWD__TripleMatch();

		/**
		 * The meta object literal for the '<em><b>Is Applicable CC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPLICABLE_CC__MATCH_MATCH = eINSTANCE
				.getComplementRule__IsApplicable_CC__Match_Match();

		/**
		 * The meta object literal for the '<em><b>Is Applicable solve Csp CC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPLICABLE_SOLVE_CSP_CC__TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_COMPOSITE_TGGRULE_BOX_MATCH_MATCH = eINSTANCE
				.getComplementRule__IsApplicable_solveCsp_CC__TGGRule_TripleGraphGrammar_DirectedGraph_Box_Composite_TGGRule_Box_Match_Match();

		/**
		 * The meta object literal for the '<em><b>Is Applicable check Csp CC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___IS_APPLICABLE_CHECK_CSP_CC__CSP = eINSTANCE
				.getComplementRule__IsApplicable_checkCsp_CC__CSP();

		/**
		 * The meta object literal for the '<em><b>Check DEC FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___CHECK_DEC_FWD__DIRECTEDGRAPH_BOX_COMPOSITE_BOX = eINSTANCE
				.getComplementRule__CheckDEC_FWD__DirectedGraph_Box_Composite_Box();

		/**
		 * The meta object literal for the '<em><b>Check DEC BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___CHECK_DEC_BWD__TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE = eINSTANCE
				.getComplementRule__CheckDEC_BWD__TGGRule_TripleGraphGrammar_TGGRule();

		/**
		 * The meta object literal for the '<em><b>Generate Model</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___GENERATE_MODEL__RULEENTRYCONTAINER_NODETORULE = eINSTANCE
				.getComplementRule__GenerateModel__RuleEntryContainer_NodeToRule();

		/**
		 * The meta object literal for the '<em><b>Generate Model solve Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_NODETORULE_TGGRULE_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_BOX_MODELGENERATORRULERESULT = eINSTANCE
				.getComplementRule__GenerateModel_solveCsp_BWD__IsApplicableMatch_TGGRule_TripleGraphGrammar_DirectedGraph_Box_NodeToRule_TGGRule_DirectedGraphToTripleGraphGrammar_Box_ModelgeneratorRuleResult();

		/**
		 * The meta object literal for the '<em><b>Generate Model check Csp BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPLEMENT_RULE___GENERATE_MODEL_CHECK_CSP_BWD__CSP = eINSTANCE
				.getComplementRule__GenerateModel_checkCsp_BWD__CSP();

	}

} //RulesPackage
