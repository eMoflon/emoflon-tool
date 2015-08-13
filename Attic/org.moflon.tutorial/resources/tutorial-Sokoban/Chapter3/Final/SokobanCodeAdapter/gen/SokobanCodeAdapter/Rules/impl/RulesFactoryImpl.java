/**
 */
package SokobanCodeAdapter.Rules.impl;

import SokobanCodeAdapter.Rules.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RulesFactoryImpl extends EFactoryImpl implements RulesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RulesFactory init() {
		try {
			RulesFactory theRulesFactory = (RulesFactory) EPackage.Registry.INSTANCE
					.getEFactory(RulesPackage.eNS_URI);
			if (theRulesFactory != null) {
				return theRulesFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RulesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case RulesPackage.ADMIN_NODE_TO_FIGURE_RULE:
			return createAdminNodeToFigureRule();
		case RulesPackage.ALL_OTHER_NODE_TO_FLOOR_RULE:
			return createAllOtherNodeToFloorRule();
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE:
			return createBoardNodeToBoardRule();
		case RulesPackage.GOAL_NODE_TO_FIGURE_RULE:
			return createGoalNodeToFigureRule();
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE:
			return createLeftcolumnNodeToFloorRule();
		case RulesPackage.SERVER_NODE_TO_FIGURE_RULE:
			return createServerNodeToFigureRule();
		case RulesPackage.TOP_ROW_NODE_TO_FLOOR_RULE:
			return createTopRowNodeToFloorRule();
		case RulesPackage.WALL_NODE_TO_FIGURE_RULE:
			return createWallNodeToFigureRule();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName()
					+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdminNodeToFigureRule createAdminNodeToFigureRule() {
		AdminNodeToFigureRuleImpl adminNodeToFigureRule = new AdminNodeToFigureRuleImpl();
		return adminNodeToFigureRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AllOtherNodeToFloorRule createAllOtherNodeToFloorRule() {
		AllOtherNodeToFloorRuleImpl allOtherNodeToFloorRule = new AllOtherNodeToFloorRuleImpl();
		return allOtherNodeToFloorRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoardNodeToBoardRule createBoardNodeToBoardRule() {
		BoardNodeToBoardRuleImpl boardNodeToBoardRule = new BoardNodeToBoardRuleImpl();
		return boardNodeToBoardRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GoalNodeToFigureRule createGoalNodeToFigureRule() {
		GoalNodeToFigureRuleImpl goalNodeToFigureRule = new GoalNodeToFigureRuleImpl();
		return goalNodeToFigureRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LeftcolumnNodeToFloorRule createLeftcolumnNodeToFloorRule() {
		LeftcolumnNodeToFloorRuleImpl leftcolumnNodeToFloorRule = new LeftcolumnNodeToFloorRuleImpl();
		return leftcolumnNodeToFloorRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServerNodeToFigureRule createServerNodeToFigureRule() {
		ServerNodeToFigureRuleImpl serverNodeToFigureRule = new ServerNodeToFigureRuleImpl();
		return serverNodeToFigureRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TopRowNodeToFloorRule createTopRowNodeToFloorRule() {
		TopRowNodeToFloorRuleImpl topRowNodeToFloorRule = new TopRowNodeToFloorRuleImpl();
		return topRowNodeToFloorRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WallNodeToFigureRule createWallNodeToFigureRule() {
		WallNodeToFigureRuleImpl wallNodeToFigureRule = new WallNodeToFigureRuleImpl();
		return wallNodeToFigureRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesPackage getRulesPackage() {
		return (RulesPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RulesPackage getPackage() {
		return RulesPackage.eINSTANCE;
	}

} //RulesFactoryImpl
