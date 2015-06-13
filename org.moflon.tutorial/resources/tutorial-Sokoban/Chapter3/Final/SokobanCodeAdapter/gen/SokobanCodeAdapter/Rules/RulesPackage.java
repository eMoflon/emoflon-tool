/**
 */
package SokobanCodeAdapter.Rules;

import TGGRuntime.TGGRuntimePackage;

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
 * @see SokobanCodeAdapter.Rules.RulesFactory
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
	String eNS_URI = "http://www.moflon.org.SokobanCodeAdapter.Rules";

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
	RulesPackage eINSTANCE = SokobanCodeAdapter.Rules.impl.RulesPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link SokobanCodeAdapter.Rules.impl.AdminNodeToFigureRuleImpl <em>Admin Node To Figure Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SokobanCodeAdapter.Rules.impl.AdminNodeToFigureRuleImpl
	 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getAdminNodeToFigureRule()
	 * @generated
	 */
	int ADMIN_NODE_TO_FIGURE_RULE = 0;

	/**
	 * The number of structural features of the '<em>Admin Node To Figure Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADMIN_NODE_TO_FIGURE_RULE_FEATURE_COUNT = TGGRuntimePackage.ABSTRACT_RULE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Appropriate FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADMIN_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Perform FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADMIN_NODE_TO_FIGURE_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Applicable FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADMIN_NODE_TO_FIGURE_RULE___IS_APPLICABLE_FWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Appropriate BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADMIN_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD__MATCH_FLOOR_ADMIN = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Perform BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADMIN_NODE_TO_FIGURE_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Applicable BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADMIN_NODE_TO_FIGURE_RULE___IS_APPLICABLE_BWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 0</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADMIN_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_0__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 1</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADMIN_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_1__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Floor 0</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADMIN_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_FLOOR_0__FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Admin 0</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADMIN_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_ADMIN_0__ADMIN = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 9;

	/**
	 * The number of operations of the '<em>Admin Node To Figure Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADMIN_NODE_TO_FIGURE_RULE_OPERATION_COUNT = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 10;

	/**
	 * The meta object id for the '{@link SokobanCodeAdapter.Rules.impl.AllOtherNodeToFloorRuleImpl <em>All Other Node To Floor Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SokobanCodeAdapter.Rules.impl.AllOtherNodeToFloorRuleImpl
	 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getAllOtherNodeToFloorRule()
	 * @generated
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE = 1;

	/**
	 * The number of structural features of the '<em>All Other Node To Floor Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE_FEATURE_COUNT = TGGRuntimePackage.ABSTRACT_RULE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Appropriate FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE_NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Perform FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Applicable FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPLICABLE_FWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Appropriate BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD__MATCH_BOARD_FLOOR_FLOOR_FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Perform BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Applicable BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPLICABLE_BWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 2</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_2__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 3</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_3__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 4</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_4__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Board 0</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_BOARD_0__BOARD = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Floor 1</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_1__FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Floor 2</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_2__FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Floor 3</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_3__FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 12;

	/**
	 * The number of operations of the '<em>All Other Node To Floor Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALL_OTHER_NODE_TO_FLOOR_RULE_OPERATION_COUNT = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 13;

	/**
	 * The meta object id for the '{@link SokobanCodeAdapter.Rules.impl.BoardNodeToBoardRuleImpl <em>Board Node To Board Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SokobanCodeAdapter.Rules.impl.BoardNodeToBoardRuleImpl
	 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getBoardNodeToBoardRule()
	 * @generated
	 */
	int BOARD_NODE_TO_BOARD_RULE = 2;

	/**
	 * The number of structural features of the '<em>Board Node To Board Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE_FEATURE_COUNT = TGGRuntimePackage.ABSTRACT_RULE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Appropriate FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD__MATCH_FILE_NODE_NODE_NODE_NODE_NODE_NODE_NODE_NODE_NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Perform FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Applicable FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPLICABLE_FWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Appropriate BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_BWD__MATCH_BOARD_FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Perform BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Applicable BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPLICABLE_BWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Appropriate FWD File 0</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_FILE_0__FILE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 5</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_5__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 6</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_6__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 7</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_7__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 8</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_8__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 9</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_9__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 10</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_10__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 11</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_11__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 13;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 12</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_12__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 14;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 13</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_13__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 15;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Board 1</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_BWD_BOARD_1__BOARD = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 16;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Floor 4</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_BWD_FLOOR_4__FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 17;

	/**
	 * The number of operations of the '<em>Board Node To Board Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_NODE_TO_BOARD_RULE_OPERATION_COUNT = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 18;

	/**
	 * The meta object id for the '{@link SokobanCodeAdapter.Rules.impl.GoalNodeToFigureRuleImpl <em>Goal Node To Figure Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SokobanCodeAdapter.Rules.impl.GoalNodeToFigureRuleImpl
	 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getGoalNodeToFigureRule()
	 * @generated
	 */
	int GOAL_NODE_TO_FIGURE_RULE = 3;

	/**
	 * The number of structural features of the '<em>Goal Node To Figure Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_NODE_TO_FIGURE_RULE_FEATURE_COUNT = TGGRuntimePackage.ABSTRACT_RULE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Appropriate FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Perform FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_NODE_TO_FIGURE_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Applicable FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_NODE_TO_FIGURE_RULE___IS_APPLICABLE_FWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Appropriate BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD__MATCH_FLOOR_GOAL = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Perform BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_NODE_TO_FIGURE_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Applicable BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_NODE_TO_FIGURE_RULE___IS_APPLICABLE_BWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 14</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_14__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 15</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_15__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Floor 5</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_FLOOR_5__FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Goal 0</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_GOAL_0__GOAL = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 9;

	/**
	 * The number of operations of the '<em>Goal Node To Figure Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_NODE_TO_FIGURE_RULE_OPERATION_COUNT = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 10;

	/**
	 * The meta object id for the '{@link SokobanCodeAdapter.Rules.impl.LeftcolumnNodeToFloorRuleImpl <em>Leftcolumn Node To Floor Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SokobanCodeAdapter.Rules.impl.LeftcolumnNodeToFloorRuleImpl
	 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getLeftcolumnNodeToFloorRule()
	 * @generated
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE = 4;

	/**
	 * The number of structural features of the '<em>Leftcolumn Node To Floor Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE_FEATURE_COUNT = TGGRuntimePackage.ABSTRACT_RULE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Appropriate FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE_NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Perform FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Applicable FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPLICABLE_FWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Appropriate BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD__MATCH_BOARD_FLOOR_FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Perform BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Applicable BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPLICABLE_BWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 16</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_16__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 17</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_17__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 18</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_18__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Board 2</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_BOARD_2__BOARD = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Floor 6</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_6__FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Floor 7</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_7__FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 11;

	/**
	 * The number of operations of the '<em>Leftcolumn Node To Floor Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEFTCOLUMN_NODE_TO_FLOOR_RULE_OPERATION_COUNT = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 12;

	/**
	 * The meta object id for the '{@link SokobanCodeAdapter.Rules.impl.ServerNodeToFigureRuleImpl <em>Server Node To Figure Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SokobanCodeAdapter.Rules.impl.ServerNodeToFigureRuleImpl
	 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getServerNodeToFigureRule()
	 * @generated
	 */
	int SERVER_NODE_TO_FIGURE_RULE = 5;

	/**
	 * The number of structural features of the '<em>Server Node To Figure Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_NODE_TO_FIGURE_RULE_FEATURE_COUNT = TGGRuntimePackage.ABSTRACT_RULE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Appropriate FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Perform FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_NODE_TO_FIGURE_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Applicable FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_NODE_TO_FIGURE_RULE___IS_APPLICABLE_FWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Appropriate BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD__MATCH_FLOOR_SERVER = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Perform BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_NODE_TO_FIGURE_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Applicable BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_NODE_TO_FIGURE_RULE___IS_APPLICABLE_BWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 19</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_19__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 20</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_20__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Floor 8</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_FLOOR_8__FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Server 0</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_SERVER_0__SERVER = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 9;

	/**
	 * The number of operations of the '<em>Server Node To Figure Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVER_NODE_TO_FIGURE_RULE_OPERATION_COUNT = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 10;

	/**
	 * The meta object id for the '{@link SokobanCodeAdapter.Rules.impl.TopRowNodeToFloorRuleImpl <em>Top Row Node To Floor Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SokobanCodeAdapter.Rules.impl.TopRowNodeToFloorRuleImpl
	 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getTopRowNodeToFloorRule()
	 * @generated
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE = 6;

	/**
	 * The number of structural features of the '<em>Top Row Node To Floor Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE_FEATURE_COUNT = TGGRuntimePackage.ABSTRACT_RULE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Appropriate FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE_NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Perform FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Applicable FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPLICABLE_FWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Appropriate BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD__MATCH_BOARD_FLOOR_FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Perform BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Applicable BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPLICABLE_BWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 21</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_21__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 22</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_22__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 23</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_23__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Board 3</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_BOARD_3__BOARD = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Floor 9</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_9__FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Floor 10</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_10__FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 11;

	/**
	 * The number of operations of the '<em>Top Row Node To Floor Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_ROW_NODE_TO_FLOOR_RULE_OPERATION_COUNT = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 12;

	/**
	 * The meta object id for the '{@link SokobanCodeAdapter.Rules.impl.WallNodeToFigureRuleImpl <em>Wall Node To Figure Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SokobanCodeAdapter.Rules.impl.WallNodeToFigureRuleImpl
	 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getWallNodeToFigureRule()
	 * @generated
	 */
	int WALL_NODE_TO_FIGURE_RULE = 7;

	/**
	 * The number of structural features of the '<em>Wall Node To Figure Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_NODE_TO_FIGURE_RULE_FEATURE_COUNT = TGGRuntimePackage.ABSTRACT_RULE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Appropriate FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Perform FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_NODE_TO_FIGURE_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Applicable FWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_NODE_TO_FIGURE_RULE___IS_APPLICABLE_FWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Appropriate BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD__MATCH_FLOOR_WALL = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Perform BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_NODE_TO_FIGURE_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Applicable BWD</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_NODE_TO_FIGURE_RULE___IS_APPLICABLE_BWD__MATCH = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 24</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_24__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Appropriate FWD Node 25</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_25__NODE = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Floor 11</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_FLOOR_11__FLOOR = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Appropriate BWD Wall 0</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_WALL_0__WALL = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 9;

	/**
	 * The number of operations of the '<em>Wall Node To Figure Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WALL_NODE_TO_FIGURE_RULE_OPERATION_COUNT = TGGRuntimePackage.ABSTRACT_RULE_OPERATION_COUNT + 10;

	/**
	 * Returns the meta object for class '{@link SokobanCodeAdapter.Rules.AdminNodeToFigureRule <em>Admin Node To Figure Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Admin Node To Figure Rule</em>'.
	 * @see SokobanCodeAdapter.Rules.AdminNodeToFigureRule
	 * @generated
	 */
	EClass getAdminNodeToFigureRule();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AdminNodeToFigureRule#perform_FWD(TGGRuntime.IsApplicableMatch) <em>Perform FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AdminNodeToFigureRule#perform_FWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getAdminNodeToFigureRule__Perform_FWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node) <em>Is Appropriate FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node)
	 * @generated
	 */
	EOperation getAdminNodeToFigureRule__IsAppropriate_FWD__Match_Node_Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isApplicable_FWD(TGGRuntime.Match) <em>Is Applicable FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isApplicable_FWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getAdminNodeToFigureRule__IsApplicable_FWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AdminNodeToFigureRule#perform_BWD(TGGRuntime.IsApplicableMatch) <em>Perform BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AdminNodeToFigureRule#perform_BWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getAdminNodeToFigureRule__Perform_BWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Floor, SokobanLanguage.Admin) <em>Is Appropriate BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Floor, SokobanLanguage.Admin)
	 * @generated
	 */
	EOperation getAdminNodeToFigureRule__IsAppropriate_BWD__Match_Floor_Admin();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isApplicable_BWD(TGGRuntime.Match) <em>Is Applicable BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isApplicable_BWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getAdminNodeToFigureRule__IsApplicable_BWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isAppropriate_FWD_Node_0(MocaTree.Node) <em>Is Appropriate FWD Node 0</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 0</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isAppropriate_FWD_Node_0(MocaTree.Node)
	 * @generated
	 */
	EOperation getAdminNodeToFigureRule__IsAppropriate_FWD_Node_0__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isAppropriate_FWD_Node_1(MocaTree.Node) <em>Is Appropriate FWD Node 1</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 1</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isAppropriate_FWD_Node_1(MocaTree.Node)
	 * @generated
	 */
	EOperation getAdminNodeToFigureRule__IsAppropriate_FWD_Node_1__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isAppropriate_BWD_Floor_0(SokobanLanguage.Floor) <em>Is Appropriate BWD Floor 0</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Floor 0</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isAppropriate_BWD_Floor_0(SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getAdminNodeToFigureRule__IsAppropriate_BWD_Floor_0__Floor();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isAppropriate_BWD_Admin_0(SokobanLanguage.Admin) <em>Is Appropriate BWD Admin 0</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Admin 0</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AdminNodeToFigureRule#isAppropriate_BWD_Admin_0(SokobanLanguage.Admin)
	 * @generated
	 */
	EOperation getAdminNodeToFigureRule__IsAppropriate_BWD_Admin_0__Admin();

	/**
	 * Returns the meta object for class '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule <em>All Other Node To Floor Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>All Other Node To Floor Rule</em>'.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule
	 * @generated
	 */
	EClass getAllOtherNodeToFloorRule();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#perform_FWD(TGGRuntime.IsApplicableMatch) <em>Perform FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#perform_FWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getAllOtherNodeToFloorRule__Perform_FWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node, MocaTree.Node) <em>Is Appropriate FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node, MocaTree.Node)
	 * @generated
	 */
	EOperation getAllOtherNodeToFloorRule__IsAppropriate_FWD__Match_Node_Node_Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isApplicable_FWD(TGGRuntime.Match) <em>Is Applicable FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isApplicable_FWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getAllOtherNodeToFloorRule__IsApplicable_FWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#perform_BWD(TGGRuntime.IsApplicableMatch) <em>Perform BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#perform_BWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getAllOtherNodeToFloorRule__Perform_BWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Board, SokobanLanguage.Floor, SokobanLanguage.Floor, SokobanLanguage.Floor) <em>Is Appropriate BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Board, SokobanLanguage.Floor, SokobanLanguage.Floor, SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getAllOtherNodeToFloorRule__IsAppropriate_BWD__Match_Board_Floor_Floor_Floor();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isApplicable_BWD(TGGRuntime.Match) <em>Is Applicable BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isApplicable_BWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getAllOtherNodeToFloorRule__IsApplicable_BWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_FWD_Node_2(MocaTree.Node) <em>Is Appropriate FWD Node 2</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 2</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_FWD_Node_2(MocaTree.Node)
	 * @generated
	 */
	EOperation getAllOtherNodeToFloorRule__IsAppropriate_FWD_Node_2__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_FWD_Node_3(MocaTree.Node) <em>Is Appropriate FWD Node 3</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 3</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_FWD_Node_3(MocaTree.Node)
	 * @generated
	 */
	EOperation getAllOtherNodeToFloorRule__IsAppropriate_FWD_Node_3__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_FWD_Node_4(MocaTree.Node) <em>Is Appropriate FWD Node 4</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 4</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_FWD_Node_4(MocaTree.Node)
	 * @generated
	 */
	EOperation getAllOtherNodeToFloorRule__IsAppropriate_FWD_Node_4__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_BWD_Board_0(SokobanLanguage.Board) <em>Is Appropriate BWD Board 0</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Board 0</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_BWD_Board_0(SokobanLanguage.Board)
	 * @generated
	 */
	EOperation getAllOtherNodeToFloorRule__IsAppropriate_BWD_Board_0__Board();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_BWD_Floor_1(SokobanLanguage.Floor) <em>Is Appropriate BWD Floor 1</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Floor 1</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_BWD_Floor_1(SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getAllOtherNodeToFloorRule__IsAppropriate_BWD_Floor_1__Floor();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_BWD_Floor_2(SokobanLanguage.Floor) <em>Is Appropriate BWD Floor 2</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Floor 2</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_BWD_Floor_2(SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getAllOtherNodeToFloorRule__IsAppropriate_BWD_Floor_2__Floor();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_BWD_Floor_3(SokobanLanguage.Floor) <em>Is Appropriate BWD Floor 3</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Floor 3</em>' operation.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule#isAppropriate_BWD_Floor_3(SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getAllOtherNodeToFloorRule__IsAppropriate_BWD_Floor_3__Floor();

	/**
	 * Returns the meta object for class '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule <em>Board Node To Board Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Board Node To Board Rule</em>'.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule
	 * @generated
	 */
	EClass getBoardNodeToBoardRule();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#perform_FWD(TGGRuntime.IsApplicableMatch) <em>Perform FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#perform_FWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__Perform_FWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.File, MocaTree.Node, MocaTree.Node, MocaTree.Node, MocaTree.Node, MocaTree.Node, MocaTree.Node, MocaTree.Node, MocaTree.Node, MocaTree.Node) <em>Is Appropriate FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.File, MocaTree.Node, MocaTree.Node, MocaTree.Node, MocaTree.Node, MocaTree.Node, MocaTree.Node, MocaTree.Node, MocaTree.Node, MocaTree.Node)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_FWD__Match_File_Node_Node_Node_Node_Node_Node_Node_Node_Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isApplicable_FWD(TGGRuntime.Match) <em>Is Applicable FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isApplicable_FWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsApplicable_FWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#perform_BWD(TGGRuntime.IsApplicableMatch) <em>Perform BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#perform_BWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__Perform_BWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Board, SokobanLanguage.Floor) <em>Is Appropriate BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Board, SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_BWD__Match_Board_Floor();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isApplicable_BWD(TGGRuntime.Match) <em>Is Applicable BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isApplicable_BWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsApplicable_BWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_File_0(MocaTree.File) <em>Is Appropriate FWD File 0</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD File 0</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_File_0(MocaTree.File)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_FWD_File_0__File();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_5(MocaTree.Node) <em>Is Appropriate FWD Node 5</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 5</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_5(MocaTree.Node)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_FWD_Node_5__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_6(MocaTree.Node) <em>Is Appropriate FWD Node 6</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 6</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_6(MocaTree.Node)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_FWD_Node_6__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_7(MocaTree.Node) <em>Is Appropriate FWD Node 7</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 7</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_7(MocaTree.Node)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_FWD_Node_7__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_8(MocaTree.Node) <em>Is Appropriate FWD Node 8</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 8</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_8(MocaTree.Node)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_FWD_Node_8__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_9(MocaTree.Node) <em>Is Appropriate FWD Node 9</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 9</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_9(MocaTree.Node)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_FWD_Node_9__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_10(MocaTree.Node) <em>Is Appropriate FWD Node 10</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 10</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_10(MocaTree.Node)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_FWD_Node_10__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_11(MocaTree.Node) <em>Is Appropriate FWD Node 11</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 11</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_11(MocaTree.Node)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_FWD_Node_11__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_12(MocaTree.Node) <em>Is Appropriate FWD Node 12</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 12</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_12(MocaTree.Node)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_FWD_Node_12__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_13(MocaTree.Node) <em>Is Appropriate FWD Node 13</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 13</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_FWD_Node_13(MocaTree.Node)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_FWD_Node_13__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_BWD_Board_1(SokobanLanguage.Board) <em>Is Appropriate BWD Board 1</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Board 1</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_BWD_Board_1(SokobanLanguage.Board)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_BWD_Board_1__Board();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_BWD_Floor_4(SokobanLanguage.Floor) <em>Is Appropriate BWD Floor 4</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Floor 4</em>' operation.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule#isAppropriate_BWD_Floor_4(SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getBoardNodeToBoardRule__IsAppropriate_BWD_Floor_4__Floor();

	/**
	 * Returns the meta object for class '{@link SokobanCodeAdapter.Rules.GoalNodeToFigureRule <em>Goal Node To Figure Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Goal Node To Figure Rule</em>'.
	 * @see SokobanCodeAdapter.Rules.GoalNodeToFigureRule
	 * @generated
	 */
	EClass getGoalNodeToFigureRule();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.GoalNodeToFigureRule#perform_FWD(TGGRuntime.IsApplicableMatch) <em>Perform FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.GoalNodeToFigureRule#perform_FWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getGoalNodeToFigureRule__Perform_FWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node) <em>Is Appropriate FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node)
	 * @generated
	 */
	EOperation getGoalNodeToFigureRule__IsAppropriate_FWD__Match_Node_Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isApplicable_FWD(TGGRuntime.Match) <em>Is Applicable FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isApplicable_FWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getGoalNodeToFigureRule__IsApplicable_FWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.GoalNodeToFigureRule#perform_BWD(TGGRuntime.IsApplicableMatch) <em>Perform BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.GoalNodeToFigureRule#perform_BWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getGoalNodeToFigureRule__Perform_BWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Floor, SokobanLanguage.Goal) <em>Is Appropriate BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Floor, SokobanLanguage.Goal)
	 * @generated
	 */
	EOperation getGoalNodeToFigureRule__IsAppropriate_BWD__Match_Floor_Goal();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isApplicable_BWD(TGGRuntime.Match) <em>Is Applicable BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isApplicable_BWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getGoalNodeToFigureRule__IsApplicable_BWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isAppropriate_FWD_Node_14(MocaTree.Node) <em>Is Appropriate FWD Node 14</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 14</em>' operation.
	 * @see SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isAppropriate_FWD_Node_14(MocaTree.Node)
	 * @generated
	 */
	EOperation getGoalNodeToFigureRule__IsAppropriate_FWD_Node_14__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isAppropriate_FWD_Node_15(MocaTree.Node) <em>Is Appropriate FWD Node 15</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 15</em>' operation.
	 * @see SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isAppropriate_FWD_Node_15(MocaTree.Node)
	 * @generated
	 */
	EOperation getGoalNodeToFigureRule__IsAppropriate_FWD_Node_15__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isAppropriate_BWD_Floor_5(SokobanLanguage.Floor) <em>Is Appropriate BWD Floor 5</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Floor 5</em>' operation.
	 * @see SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isAppropriate_BWD_Floor_5(SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getGoalNodeToFigureRule__IsAppropriate_BWD_Floor_5__Floor();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isAppropriate_BWD_Goal_0(SokobanLanguage.Goal) <em>Is Appropriate BWD Goal 0</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Goal 0</em>' operation.
	 * @see SokobanCodeAdapter.Rules.GoalNodeToFigureRule#isAppropriate_BWD_Goal_0(SokobanLanguage.Goal)
	 * @generated
	 */
	EOperation getGoalNodeToFigureRule__IsAppropriate_BWD_Goal_0__Goal();

	/**
	 * Returns the meta object for class '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule <em>Leftcolumn Node To Floor Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Leftcolumn Node To Floor Rule</em>'.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule
	 * @generated
	 */
	EClass getLeftcolumnNodeToFloorRule();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#perform_FWD(TGGRuntime.IsApplicableMatch) <em>Perform FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#perform_FWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getLeftcolumnNodeToFloorRule__Perform_FWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node, MocaTree.Node) <em>Is Appropriate FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node, MocaTree.Node)
	 * @generated
	 */
	EOperation getLeftcolumnNodeToFloorRule__IsAppropriate_FWD__Match_Node_Node_Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isApplicable_FWD(TGGRuntime.Match) <em>Is Applicable FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isApplicable_FWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getLeftcolumnNodeToFloorRule__IsApplicable_FWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#perform_BWD(TGGRuntime.IsApplicableMatch) <em>Perform BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#perform_BWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getLeftcolumnNodeToFloorRule__Perform_BWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Board, SokobanLanguage.Floor, SokobanLanguage.Floor) <em>Is Appropriate BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Board, SokobanLanguage.Floor, SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getLeftcolumnNodeToFloorRule__IsAppropriate_BWD__Match_Board_Floor_Floor();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isApplicable_BWD(TGGRuntime.Match) <em>Is Applicable BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isApplicable_BWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getLeftcolumnNodeToFloorRule__IsApplicable_BWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_FWD_Node_16(MocaTree.Node) <em>Is Appropriate FWD Node 16</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 16</em>' operation.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_FWD_Node_16(MocaTree.Node)
	 * @generated
	 */
	EOperation getLeftcolumnNodeToFloorRule__IsAppropriate_FWD_Node_16__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_FWD_Node_17(MocaTree.Node) <em>Is Appropriate FWD Node 17</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 17</em>' operation.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_FWD_Node_17(MocaTree.Node)
	 * @generated
	 */
	EOperation getLeftcolumnNodeToFloorRule__IsAppropriate_FWD_Node_17__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_FWD_Node_18(MocaTree.Node) <em>Is Appropriate FWD Node 18</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 18</em>' operation.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_FWD_Node_18(MocaTree.Node)
	 * @generated
	 */
	EOperation getLeftcolumnNodeToFloorRule__IsAppropriate_FWD_Node_18__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_BWD_Board_2(SokobanLanguage.Board) <em>Is Appropriate BWD Board 2</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Board 2</em>' operation.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_BWD_Board_2(SokobanLanguage.Board)
	 * @generated
	 */
	EOperation getLeftcolumnNodeToFloorRule__IsAppropriate_BWD_Board_2__Board();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_BWD_Floor_6(SokobanLanguage.Floor) <em>Is Appropriate BWD Floor 6</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Floor 6</em>' operation.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_BWD_Floor_6(SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getLeftcolumnNodeToFloorRule__IsAppropriate_BWD_Floor_6__Floor();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_BWD_Floor_7(SokobanLanguage.Floor) <em>Is Appropriate BWD Floor 7</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Floor 7</em>' operation.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule#isAppropriate_BWD_Floor_7(SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getLeftcolumnNodeToFloorRule__IsAppropriate_BWD_Floor_7__Floor();

	/**
	 * Returns the meta object for class '{@link SokobanCodeAdapter.Rules.ServerNodeToFigureRule <em>Server Node To Figure Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Server Node To Figure Rule</em>'.
	 * @see SokobanCodeAdapter.Rules.ServerNodeToFigureRule
	 * @generated
	 */
	EClass getServerNodeToFigureRule();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.ServerNodeToFigureRule#perform_FWD(TGGRuntime.IsApplicableMatch) <em>Perform FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.ServerNodeToFigureRule#perform_FWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getServerNodeToFigureRule__Perform_FWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node) <em>Is Appropriate FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node)
	 * @generated
	 */
	EOperation getServerNodeToFigureRule__IsAppropriate_FWD__Match_Node_Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isApplicable_FWD(TGGRuntime.Match) <em>Is Applicable FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isApplicable_FWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getServerNodeToFigureRule__IsApplicable_FWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.ServerNodeToFigureRule#perform_BWD(TGGRuntime.IsApplicableMatch) <em>Perform BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.ServerNodeToFigureRule#perform_BWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getServerNodeToFigureRule__Perform_BWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Floor, SokobanLanguage.Server) <em>Is Appropriate BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Floor, SokobanLanguage.Server)
	 * @generated
	 */
	EOperation getServerNodeToFigureRule__IsAppropriate_BWD__Match_Floor_Server();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isApplicable_BWD(TGGRuntime.Match) <em>Is Applicable BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isApplicable_BWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getServerNodeToFigureRule__IsApplicable_BWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isAppropriate_FWD_Node_19(MocaTree.Node) <em>Is Appropriate FWD Node 19</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 19</em>' operation.
	 * @see SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isAppropriate_FWD_Node_19(MocaTree.Node)
	 * @generated
	 */
	EOperation getServerNodeToFigureRule__IsAppropriate_FWD_Node_19__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isAppropriate_FWD_Node_20(MocaTree.Node) <em>Is Appropriate FWD Node 20</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 20</em>' operation.
	 * @see SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isAppropriate_FWD_Node_20(MocaTree.Node)
	 * @generated
	 */
	EOperation getServerNodeToFigureRule__IsAppropriate_FWD_Node_20__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isAppropriate_BWD_Floor_8(SokobanLanguage.Floor) <em>Is Appropriate BWD Floor 8</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Floor 8</em>' operation.
	 * @see SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isAppropriate_BWD_Floor_8(SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getServerNodeToFigureRule__IsAppropriate_BWD_Floor_8__Floor();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isAppropriate_BWD_Server_0(SokobanLanguage.Server) <em>Is Appropriate BWD Server 0</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Server 0</em>' operation.
	 * @see SokobanCodeAdapter.Rules.ServerNodeToFigureRule#isAppropriate_BWD_Server_0(SokobanLanguage.Server)
	 * @generated
	 */
	EOperation getServerNodeToFigureRule__IsAppropriate_BWD_Server_0__Server();

	/**
	 * Returns the meta object for class '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule <em>Top Row Node To Floor Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Top Row Node To Floor Rule</em>'.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule
	 * @generated
	 */
	EClass getTopRowNodeToFloorRule();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#perform_FWD(TGGRuntime.IsApplicableMatch) <em>Perform FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#perform_FWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getTopRowNodeToFloorRule__Perform_FWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node, MocaTree.Node) <em>Is Appropriate FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node, MocaTree.Node)
	 * @generated
	 */
	EOperation getTopRowNodeToFloorRule__IsAppropriate_FWD__Match_Node_Node_Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isApplicable_FWD(TGGRuntime.Match) <em>Is Applicable FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isApplicable_FWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getTopRowNodeToFloorRule__IsApplicable_FWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#perform_BWD(TGGRuntime.IsApplicableMatch) <em>Perform BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#perform_BWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getTopRowNodeToFloorRule__Perform_BWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Board, SokobanLanguage.Floor, SokobanLanguage.Floor) <em>Is Appropriate BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Board, SokobanLanguage.Floor, SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getTopRowNodeToFloorRule__IsAppropriate_BWD__Match_Board_Floor_Floor();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isApplicable_BWD(TGGRuntime.Match) <em>Is Applicable BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isApplicable_BWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getTopRowNodeToFloorRule__IsApplicable_BWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_FWD_Node_21(MocaTree.Node) <em>Is Appropriate FWD Node 21</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 21</em>' operation.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_FWD_Node_21(MocaTree.Node)
	 * @generated
	 */
	EOperation getTopRowNodeToFloorRule__IsAppropriate_FWD_Node_21__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_FWD_Node_22(MocaTree.Node) <em>Is Appropriate FWD Node 22</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 22</em>' operation.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_FWD_Node_22(MocaTree.Node)
	 * @generated
	 */
	EOperation getTopRowNodeToFloorRule__IsAppropriate_FWD_Node_22__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_FWD_Node_23(MocaTree.Node) <em>Is Appropriate FWD Node 23</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 23</em>' operation.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_FWD_Node_23(MocaTree.Node)
	 * @generated
	 */
	EOperation getTopRowNodeToFloorRule__IsAppropriate_FWD_Node_23__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_BWD_Board_3(SokobanLanguage.Board) <em>Is Appropriate BWD Board 3</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Board 3</em>' operation.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_BWD_Board_3(SokobanLanguage.Board)
	 * @generated
	 */
	EOperation getTopRowNodeToFloorRule__IsAppropriate_BWD_Board_3__Board();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_BWD_Floor_9(SokobanLanguage.Floor) <em>Is Appropriate BWD Floor 9</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Floor 9</em>' operation.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_BWD_Floor_9(SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getTopRowNodeToFloorRule__IsAppropriate_BWD_Floor_9__Floor();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_BWD_Floor_10(SokobanLanguage.Floor) <em>Is Appropriate BWD Floor 10</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Floor 10</em>' operation.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule#isAppropriate_BWD_Floor_10(SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getTopRowNodeToFloorRule__IsAppropriate_BWD_Floor_10__Floor();

	/**
	 * Returns the meta object for class '{@link SokobanCodeAdapter.Rules.WallNodeToFigureRule <em>Wall Node To Figure Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Wall Node To Figure Rule</em>'.
	 * @see SokobanCodeAdapter.Rules.WallNodeToFigureRule
	 * @generated
	 */
	EClass getWallNodeToFigureRule();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.WallNodeToFigureRule#perform_FWD(TGGRuntime.IsApplicableMatch) <em>Perform FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.WallNodeToFigureRule#perform_FWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getWallNodeToFigureRule__Perform_FWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.WallNodeToFigureRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node) <em>Is Appropriate FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.WallNodeToFigureRule#isAppropriate_FWD(TGGRuntime.Match, MocaTree.Node, MocaTree.Node)
	 * @generated
	 */
	EOperation getWallNodeToFigureRule__IsAppropriate_FWD__Match_Node_Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.WallNodeToFigureRule#isApplicable_FWD(TGGRuntime.Match) <em>Is Applicable FWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable FWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.WallNodeToFigureRule#isApplicable_FWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getWallNodeToFigureRule__IsApplicable_FWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.WallNodeToFigureRule#perform_BWD(TGGRuntime.IsApplicableMatch) <em>Perform BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Perform BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.WallNodeToFigureRule#perform_BWD(TGGRuntime.IsApplicableMatch)
	 * @generated
	 */
	EOperation getWallNodeToFigureRule__Perform_BWD__IsApplicableMatch();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.WallNodeToFigureRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Floor, SokobanLanguage.Wall) <em>Is Appropriate BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.WallNodeToFigureRule#isAppropriate_BWD(TGGRuntime.Match, SokobanLanguage.Floor, SokobanLanguage.Wall)
	 * @generated
	 */
	EOperation getWallNodeToFigureRule__IsAppropriate_BWD__Match_Floor_Wall();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.WallNodeToFigureRule#isApplicable_BWD(TGGRuntime.Match) <em>Is Applicable BWD</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Applicable BWD</em>' operation.
	 * @see SokobanCodeAdapter.Rules.WallNodeToFigureRule#isApplicable_BWD(TGGRuntime.Match)
	 * @generated
	 */
	EOperation getWallNodeToFigureRule__IsApplicable_BWD__Match();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.WallNodeToFigureRule#isAppropriate_FWD_Node_24(MocaTree.Node) <em>Is Appropriate FWD Node 24</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 24</em>' operation.
	 * @see SokobanCodeAdapter.Rules.WallNodeToFigureRule#isAppropriate_FWD_Node_24(MocaTree.Node)
	 * @generated
	 */
	EOperation getWallNodeToFigureRule__IsAppropriate_FWD_Node_24__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.WallNodeToFigureRule#isAppropriate_FWD_Node_25(MocaTree.Node) <em>Is Appropriate FWD Node 25</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate FWD Node 25</em>' operation.
	 * @see SokobanCodeAdapter.Rules.WallNodeToFigureRule#isAppropriate_FWD_Node_25(MocaTree.Node)
	 * @generated
	 */
	EOperation getWallNodeToFigureRule__IsAppropriate_FWD_Node_25__Node();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.WallNodeToFigureRule#isAppropriate_BWD_Floor_11(SokobanLanguage.Floor) <em>Is Appropriate BWD Floor 11</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Floor 11</em>' operation.
	 * @see SokobanCodeAdapter.Rules.WallNodeToFigureRule#isAppropriate_BWD_Floor_11(SokobanLanguage.Floor)
	 * @generated
	 */
	EOperation getWallNodeToFigureRule__IsAppropriate_BWD_Floor_11__Floor();

	/**
	 * Returns the meta object for the '{@link SokobanCodeAdapter.Rules.WallNodeToFigureRule#isAppropriate_BWD_Wall_0(SokobanLanguage.Wall) <em>Is Appropriate BWD Wall 0</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Appropriate BWD Wall 0</em>' operation.
	 * @see SokobanCodeAdapter.Rules.WallNodeToFigureRule#isAppropriate_BWD_Wall_0(SokobanLanguage.Wall)
	 * @generated
	 */
	EOperation getWallNodeToFigureRule__IsAppropriate_BWD_Wall_0__Wall();

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
		 * The meta object literal for the '{@link SokobanCodeAdapter.Rules.impl.AdminNodeToFigureRuleImpl <em>Admin Node To Figure Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SokobanCodeAdapter.Rules.impl.AdminNodeToFigureRuleImpl
		 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getAdminNodeToFigureRule()
		 * @generated
		 */
		EClass ADMIN_NODE_TO_FIGURE_RULE = eINSTANCE.getAdminNodeToFigureRule();

		/**
		 * The meta object literal for the '<em><b>Perform FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ADMIN_NODE_TO_FIGURE_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = eINSTANCE
				.getAdminNodeToFigureRule__Perform_FWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ADMIN_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE = eINSTANCE
				.getAdminNodeToFigureRule__IsAppropriate_FWD__Match_Node_Node();

		/**
		 * The meta object literal for the '<em><b>Is Applicable FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ADMIN_NODE_TO_FIGURE_RULE___IS_APPLICABLE_FWD__MATCH = eINSTANCE
				.getAdminNodeToFigureRule__IsApplicable_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Perform BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ADMIN_NODE_TO_FIGURE_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = eINSTANCE
				.getAdminNodeToFigureRule__Perform_BWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ADMIN_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD__MATCH_FLOOR_ADMIN = eINSTANCE
				.getAdminNodeToFigureRule__IsAppropriate_BWD__Match_Floor_Admin();

		/**
		 * The meta object literal for the '<em><b>Is Applicable BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ADMIN_NODE_TO_FIGURE_RULE___IS_APPLICABLE_BWD__MATCH = eINSTANCE
				.getAdminNodeToFigureRule__IsApplicable_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 0</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ADMIN_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_0__NODE = eINSTANCE
				.getAdminNodeToFigureRule__IsAppropriate_FWD_Node_0__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 1</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ADMIN_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_1__NODE = eINSTANCE
				.getAdminNodeToFigureRule__IsAppropriate_FWD_Node_1__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Floor 0</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ADMIN_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_FLOOR_0__FLOOR = eINSTANCE
				.getAdminNodeToFigureRule__IsAppropriate_BWD_Floor_0__Floor();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Admin 0</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ADMIN_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_ADMIN_0__ADMIN = eINSTANCE
				.getAdminNodeToFigureRule__IsAppropriate_BWD_Admin_0__Admin();

		/**
		 * The meta object literal for the '{@link SokobanCodeAdapter.Rules.impl.AllOtherNodeToFloorRuleImpl <em>All Other Node To Floor Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SokobanCodeAdapter.Rules.impl.AllOtherNodeToFloorRuleImpl
		 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getAllOtherNodeToFloorRule()
		 * @generated
		 */
		EClass ALL_OTHER_NODE_TO_FLOOR_RULE = eINSTANCE
				.getAllOtherNodeToFloorRule();

		/**
		 * The meta object literal for the '<em><b>Perform FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALL_OTHER_NODE_TO_FLOOR_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = eINSTANCE
				.getAllOtherNodeToFloorRule__Perform_FWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE_NODE = eINSTANCE
				.getAllOtherNodeToFloorRule__IsAppropriate_FWD__Match_Node_Node_Node();

		/**
		 * The meta object literal for the '<em><b>Is Applicable FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPLICABLE_FWD__MATCH = eINSTANCE
				.getAllOtherNodeToFloorRule__IsApplicable_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Perform BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALL_OTHER_NODE_TO_FLOOR_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = eINSTANCE
				.getAllOtherNodeToFloorRule__Perform_BWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD__MATCH_BOARD_FLOOR_FLOOR_FLOOR = eINSTANCE
				.getAllOtherNodeToFloorRule__IsAppropriate_BWD__Match_Board_Floor_Floor_Floor();

		/**
		 * The meta object literal for the '<em><b>Is Applicable BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPLICABLE_BWD__MATCH = eINSTANCE
				.getAllOtherNodeToFloorRule__IsApplicable_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 2</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_2__NODE = eINSTANCE
				.getAllOtherNodeToFloorRule__IsAppropriate_FWD_Node_2__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 3</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_3__NODE = eINSTANCE
				.getAllOtherNodeToFloorRule__IsAppropriate_FWD_Node_3__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 4</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_4__NODE = eINSTANCE
				.getAllOtherNodeToFloorRule__IsAppropriate_FWD_Node_4__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Board 0</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_BOARD_0__BOARD = eINSTANCE
				.getAllOtherNodeToFloorRule__IsAppropriate_BWD_Board_0__Board();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Floor 1</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_1__FLOOR = eINSTANCE
				.getAllOtherNodeToFloorRule__IsAppropriate_BWD_Floor_1__Floor();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Floor 2</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_2__FLOOR = eINSTANCE
				.getAllOtherNodeToFloorRule__IsAppropriate_BWD_Floor_2__Floor();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Floor 3</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ALL_OTHER_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_3__FLOOR = eINSTANCE
				.getAllOtherNodeToFloorRule__IsAppropriate_BWD_Floor_3__Floor();

		/**
		 * The meta object literal for the '{@link SokobanCodeAdapter.Rules.impl.BoardNodeToBoardRuleImpl <em>Board Node To Board Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SokobanCodeAdapter.Rules.impl.BoardNodeToBoardRuleImpl
		 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getBoardNodeToBoardRule()
		 * @generated
		 */
		EClass BOARD_NODE_TO_BOARD_RULE = eINSTANCE.getBoardNodeToBoardRule();

		/**
		 * The meta object literal for the '<em><b>Perform FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = eINSTANCE
				.getBoardNodeToBoardRule__Perform_FWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD__MATCH_FILE_NODE_NODE_NODE_NODE_NODE_NODE_NODE_NODE_NODE = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_FWD__Match_File_Node_Node_Node_Node_Node_Node_Node_Node_Node();

		/**
		 * The meta object literal for the '<em><b>Is Applicable FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPLICABLE_FWD__MATCH = eINSTANCE
				.getBoardNodeToBoardRule__IsApplicable_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Perform BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = eINSTANCE
				.getBoardNodeToBoardRule__Perform_BWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_BWD__MATCH_BOARD_FLOOR = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_BWD__Match_Board_Floor();

		/**
		 * The meta object literal for the '<em><b>Is Applicable BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPLICABLE_BWD__MATCH = eINSTANCE
				.getBoardNodeToBoardRule__IsApplicable_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD File 0</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_FILE_0__FILE = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_FWD_File_0__File();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 5</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_5__NODE = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_FWD_Node_5__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 6</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_6__NODE = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_FWD_Node_6__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 7</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_7__NODE = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_FWD_Node_7__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 8</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_8__NODE = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_FWD_Node_8__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 9</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_9__NODE = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_FWD_Node_9__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 10</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_10__NODE = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_FWD_Node_10__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 11</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_11__NODE = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_FWD_Node_11__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 12</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_12__NODE = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_FWD_Node_12__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 13</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_FWD_NODE_13__NODE = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_FWD_Node_13__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Board 1</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_BWD_BOARD_1__BOARD = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_BWD_Board_1__Board();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Floor 4</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOARD_NODE_TO_BOARD_RULE___IS_APPROPRIATE_BWD_FLOOR_4__FLOOR = eINSTANCE
				.getBoardNodeToBoardRule__IsAppropriate_BWD_Floor_4__Floor();

		/**
		 * The meta object literal for the '{@link SokobanCodeAdapter.Rules.impl.GoalNodeToFigureRuleImpl <em>Goal Node To Figure Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SokobanCodeAdapter.Rules.impl.GoalNodeToFigureRuleImpl
		 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getGoalNodeToFigureRule()
		 * @generated
		 */
		EClass GOAL_NODE_TO_FIGURE_RULE = eINSTANCE.getGoalNodeToFigureRule();

		/**
		 * The meta object literal for the '<em><b>Perform FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GOAL_NODE_TO_FIGURE_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = eINSTANCE
				.getGoalNodeToFigureRule__Perform_FWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GOAL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE = eINSTANCE
				.getGoalNodeToFigureRule__IsAppropriate_FWD__Match_Node_Node();

		/**
		 * The meta object literal for the '<em><b>Is Applicable FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GOAL_NODE_TO_FIGURE_RULE___IS_APPLICABLE_FWD__MATCH = eINSTANCE
				.getGoalNodeToFigureRule__IsApplicable_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Perform BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GOAL_NODE_TO_FIGURE_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = eINSTANCE
				.getGoalNodeToFigureRule__Perform_BWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GOAL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD__MATCH_FLOOR_GOAL = eINSTANCE
				.getGoalNodeToFigureRule__IsAppropriate_BWD__Match_Floor_Goal();

		/**
		 * The meta object literal for the '<em><b>Is Applicable BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GOAL_NODE_TO_FIGURE_RULE___IS_APPLICABLE_BWD__MATCH = eINSTANCE
				.getGoalNodeToFigureRule__IsApplicable_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 14</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GOAL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_14__NODE = eINSTANCE
				.getGoalNodeToFigureRule__IsAppropriate_FWD_Node_14__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 15</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GOAL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_15__NODE = eINSTANCE
				.getGoalNodeToFigureRule__IsAppropriate_FWD_Node_15__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Floor 5</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GOAL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_FLOOR_5__FLOOR = eINSTANCE
				.getGoalNodeToFigureRule__IsAppropriate_BWD_Floor_5__Floor();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Goal 0</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GOAL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_GOAL_0__GOAL = eINSTANCE
				.getGoalNodeToFigureRule__IsAppropriate_BWD_Goal_0__Goal();

		/**
		 * The meta object literal for the '{@link SokobanCodeAdapter.Rules.impl.LeftcolumnNodeToFloorRuleImpl <em>Leftcolumn Node To Floor Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SokobanCodeAdapter.Rules.impl.LeftcolumnNodeToFloorRuleImpl
		 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getLeftcolumnNodeToFloorRule()
		 * @generated
		 */
		EClass LEFTCOLUMN_NODE_TO_FLOOR_RULE = eINSTANCE
				.getLeftcolumnNodeToFloorRule();

		/**
		 * The meta object literal for the '<em><b>Perform FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LEFTCOLUMN_NODE_TO_FLOOR_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = eINSTANCE
				.getLeftcolumnNodeToFloorRule__Perform_FWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE_NODE = eINSTANCE
				.getLeftcolumnNodeToFloorRule__IsAppropriate_FWD__Match_Node_Node_Node();

		/**
		 * The meta object literal for the '<em><b>Is Applicable FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPLICABLE_FWD__MATCH = eINSTANCE
				.getLeftcolumnNodeToFloorRule__IsApplicable_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Perform BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LEFTCOLUMN_NODE_TO_FLOOR_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = eINSTANCE
				.getLeftcolumnNodeToFloorRule__Perform_BWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD__MATCH_BOARD_FLOOR_FLOOR = eINSTANCE
				.getLeftcolumnNodeToFloorRule__IsAppropriate_BWD__Match_Board_Floor_Floor();

		/**
		 * The meta object literal for the '<em><b>Is Applicable BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPLICABLE_BWD__MATCH = eINSTANCE
				.getLeftcolumnNodeToFloorRule__IsApplicable_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 16</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_16__NODE = eINSTANCE
				.getLeftcolumnNodeToFloorRule__IsAppropriate_FWD_Node_16__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 17</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_17__NODE = eINSTANCE
				.getLeftcolumnNodeToFloorRule__IsAppropriate_FWD_Node_17__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 18</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_18__NODE = eINSTANCE
				.getLeftcolumnNodeToFloorRule__IsAppropriate_FWD_Node_18__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Board 2</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_BOARD_2__BOARD = eINSTANCE
				.getLeftcolumnNodeToFloorRule__IsAppropriate_BWD_Board_2__Board();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Floor 6</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_6__FLOOR = eINSTANCE
				.getLeftcolumnNodeToFloorRule__IsAppropriate_BWD_Floor_6__Floor();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Floor 7</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LEFTCOLUMN_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_7__FLOOR = eINSTANCE
				.getLeftcolumnNodeToFloorRule__IsAppropriate_BWD_Floor_7__Floor();

		/**
		 * The meta object literal for the '{@link SokobanCodeAdapter.Rules.impl.ServerNodeToFigureRuleImpl <em>Server Node To Figure Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SokobanCodeAdapter.Rules.impl.ServerNodeToFigureRuleImpl
		 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getServerNodeToFigureRule()
		 * @generated
		 */
		EClass SERVER_NODE_TO_FIGURE_RULE = eINSTANCE
				.getServerNodeToFigureRule();

		/**
		 * The meta object literal for the '<em><b>Perform FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SERVER_NODE_TO_FIGURE_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = eINSTANCE
				.getServerNodeToFigureRule__Perform_FWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SERVER_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE = eINSTANCE
				.getServerNodeToFigureRule__IsAppropriate_FWD__Match_Node_Node();

		/**
		 * The meta object literal for the '<em><b>Is Applicable FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SERVER_NODE_TO_FIGURE_RULE___IS_APPLICABLE_FWD__MATCH = eINSTANCE
				.getServerNodeToFigureRule__IsApplicable_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Perform BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SERVER_NODE_TO_FIGURE_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = eINSTANCE
				.getServerNodeToFigureRule__Perform_BWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SERVER_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD__MATCH_FLOOR_SERVER = eINSTANCE
				.getServerNodeToFigureRule__IsAppropriate_BWD__Match_Floor_Server();

		/**
		 * The meta object literal for the '<em><b>Is Applicable BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SERVER_NODE_TO_FIGURE_RULE___IS_APPLICABLE_BWD__MATCH = eINSTANCE
				.getServerNodeToFigureRule__IsApplicable_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 19</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SERVER_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_19__NODE = eINSTANCE
				.getServerNodeToFigureRule__IsAppropriate_FWD_Node_19__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 20</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SERVER_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_20__NODE = eINSTANCE
				.getServerNodeToFigureRule__IsAppropriate_FWD_Node_20__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Floor 8</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SERVER_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_FLOOR_8__FLOOR = eINSTANCE
				.getServerNodeToFigureRule__IsAppropriate_BWD_Floor_8__Floor();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Server 0</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SERVER_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_SERVER_0__SERVER = eINSTANCE
				.getServerNodeToFigureRule__IsAppropriate_BWD_Server_0__Server();

		/**
		 * The meta object literal for the '{@link SokobanCodeAdapter.Rules.impl.TopRowNodeToFloorRuleImpl <em>Top Row Node To Floor Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SokobanCodeAdapter.Rules.impl.TopRowNodeToFloorRuleImpl
		 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getTopRowNodeToFloorRule()
		 * @generated
		 */
		EClass TOP_ROW_NODE_TO_FLOOR_RULE = eINSTANCE
				.getTopRowNodeToFloorRule();

		/**
		 * The meta object literal for the '<em><b>Perform FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TOP_ROW_NODE_TO_FLOOR_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = eINSTANCE
				.getTopRowNodeToFloorRule__Perform_FWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE_NODE = eINSTANCE
				.getTopRowNodeToFloorRule__IsAppropriate_FWD__Match_Node_Node_Node();

		/**
		 * The meta object literal for the '<em><b>Is Applicable FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPLICABLE_FWD__MATCH = eINSTANCE
				.getTopRowNodeToFloorRule__IsApplicable_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Perform BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TOP_ROW_NODE_TO_FLOOR_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = eINSTANCE
				.getTopRowNodeToFloorRule__Perform_BWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD__MATCH_BOARD_FLOOR_FLOOR = eINSTANCE
				.getTopRowNodeToFloorRule__IsAppropriate_BWD__Match_Board_Floor_Floor();

		/**
		 * The meta object literal for the '<em><b>Is Applicable BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPLICABLE_BWD__MATCH = eINSTANCE
				.getTopRowNodeToFloorRule__IsApplicable_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 21</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_21__NODE = eINSTANCE
				.getTopRowNodeToFloorRule__IsAppropriate_FWD_Node_21__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 22</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_22__NODE = eINSTANCE
				.getTopRowNodeToFloorRule__IsAppropriate_FWD_Node_22__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 23</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_FWD_NODE_23__NODE = eINSTANCE
				.getTopRowNodeToFloorRule__IsAppropriate_FWD_Node_23__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Board 3</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_BOARD_3__BOARD = eINSTANCE
				.getTopRowNodeToFloorRule__IsAppropriate_BWD_Board_3__Board();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Floor 9</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_9__FLOOR = eINSTANCE
				.getTopRowNodeToFloorRule__IsAppropriate_BWD_Floor_9__Floor();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Floor 10</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TOP_ROW_NODE_TO_FLOOR_RULE___IS_APPROPRIATE_BWD_FLOOR_10__FLOOR = eINSTANCE
				.getTopRowNodeToFloorRule__IsAppropriate_BWD_Floor_10__Floor();

		/**
		 * The meta object literal for the '{@link SokobanCodeAdapter.Rules.impl.WallNodeToFigureRuleImpl <em>Wall Node To Figure Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SokobanCodeAdapter.Rules.impl.WallNodeToFigureRuleImpl
		 * @see SokobanCodeAdapter.Rules.impl.RulesPackageImpl#getWallNodeToFigureRule()
		 * @generated
		 */
		EClass WALL_NODE_TO_FIGURE_RULE = eINSTANCE.getWallNodeToFigureRule();

		/**
		 * The meta object literal for the '<em><b>Perform FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation WALL_NODE_TO_FIGURE_RULE___PERFORM_FWD__ISAPPLICABLEMATCH = eINSTANCE
				.getWallNodeToFigureRule__Perform_FWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD__MATCH_NODE_NODE = eINSTANCE
				.getWallNodeToFigureRule__IsAppropriate_FWD__Match_Node_Node();

		/**
		 * The meta object literal for the '<em><b>Is Applicable FWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation WALL_NODE_TO_FIGURE_RULE___IS_APPLICABLE_FWD__MATCH = eINSTANCE
				.getWallNodeToFigureRule__IsApplicable_FWD__Match();

		/**
		 * The meta object literal for the '<em><b>Perform BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation WALL_NODE_TO_FIGURE_RULE___PERFORM_BWD__ISAPPLICABLEMATCH = eINSTANCE
				.getWallNodeToFigureRule__Perform_BWD__IsApplicableMatch();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD__MATCH_FLOOR_WALL = eINSTANCE
				.getWallNodeToFigureRule__IsAppropriate_BWD__Match_Floor_Wall();

		/**
		 * The meta object literal for the '<em><b>Is Applicable BWD</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation WALL_NODE_TO_FIGURE_RULE___IS_APPLICABLE_BWD__MATCH = eINSTANCE
				.getWallNodeToFigureRule__IsApplicable_BWD__Match();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 24</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_24__NODE = eINSTANCE
				.getWallNodeToFigureRule__IsAppropriate_FWD_Node_24__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate FWD Node 25</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_FWD_NODE_25__NODE = eINSTANCE
				.getWallNodeToFigureRule__IsAppropriate_FWD_Node_25__Node();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Floor 11</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_FLOOR_11__FLOOR = eINSTANCE
				.getWallNodeToFigureRule__IsAppropriate_BWD_Floor_11__Floor();

		/**
		 * The meta object literal for the '<em><b>Is Appropriate BWD Wall 0</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation WALL_NODE_TO_FIGURE_RULE___IS_APPROPRIATE_BWD_WALL_0__WALL = eINSTANCE
				.getWallNodeToFigureRule__IsAppropriate_BWD_Wall_0__Wall();

	}

} //RulesPackage
