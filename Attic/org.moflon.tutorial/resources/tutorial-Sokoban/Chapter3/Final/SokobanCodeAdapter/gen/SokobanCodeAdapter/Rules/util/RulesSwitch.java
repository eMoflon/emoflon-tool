/**
 */
package SokobanCodeAdapter.Rules.util;

import SokobanCodeAdapter.Rules.*;

import TGGRuntime.AbstractRule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see SokobanCodeAdapter.Rules.RulesPackage
 * @generated
 */
public class RulesSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RulesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesSwitch() {
		if (modelPackage == null) {
			modelPackage = RulesPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case RulesPackage.ADMIN_NODE_TO_FIGURE_RULE: {
			AdminNodeToFigureRule adminNodeToFigureRule = (AdminNodeToFigureRule) theEObject;
			T result = caseAdminNodeToFigureRule(adminNodeToFigureRule);
			if (result == null)
				result = caseAbstractRule(adminNodeToFigureRule);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case RulesPackage.ALL_OTHER_NODE_TO_FLOOR_RULE: {
			AllOtherNodeToFloorRule allOtherNodeToFloorRule = (AllOtherNodeToFloorRule) theEObject;
			T result = caseAllOtherNodeToFloorRule(allOtherNodeToFloorRule);
			if (result == null)
				result = caseAbstractRule(allOtherNodeToFloorRule);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case RulesPackage.BOARD_NODE_TO_BOARD_RULE: {
			BoardNodeToBoardRule boardNodeToBoardRule = (BoardNodeToBoardRule) theEObject;
			T result = caseBoardNodeToBoardRule(boardNodeToBoardRule);
			if (result == null)
				result = caseAbstractRule(boardNodeToBoardRule);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case RulesPackage.GOAL_NODE_TO_FIGURE_RULE: {
			GoalNodeToFigureRule goalNodeToFigureRule = (GoalNodeToFigureRule) theEObject;
			T result = caseGoalNodeToFigureRule(goalNodeToFigureRule);
			if (result == null)
				result = caseAbstractRule(goalNodeToFigureRule);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case RulesPackage.LEFTCOLUMN_NODE_TO_FLOOR_RULE: {
			LeftcolumnNodeToFloorRule leftcolumnNodeToFloorRule = (LeftcolumnNodeToFloorRule) theEObject;
			T result = caseLeftcolumnNodeToFloorRule(leftcolumnNodeToFloorRule);
			if (result == null)
				result = caseAbstractRule(leftcolumnNodeToFloorRule);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case RulesPackage.SERVER_NODE_TO_FIGURE_RULE: {
			ServerNodeToFigureRule serverNodeToFigureRule = (ServerNodeToFigureRule) theEObject;
			T result = caseServerNodeToFigureRule(serverNodeToFigureRule);
			if (result == null)
				result = caseAbstractRule(serverNodeToFigureRule);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case RulesPackage.TOP_ROW_NODE_TO_FLOOR_RULE: {
			TopRowNodeToFloorRule topRowNodeToFloorRule = (TopRowNodeToFloorRule) theEObject;
			T result = caseTopRowNodeToFloorRule(topRowNodeToFloorRule);
			if (result == null)
				result = caseAbstractRule(topRowNodeToFloorRule);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case RulesPackage.WALL_NODE_TO_FIGURE_RULE: {
			WallNodeToFigureRule wallNodeToFigureRule = (WallNodeToFigureRule) theEObject;
			T result = caseWallNodeToFigureRule(wallNodeToFigureRule);
			if (result == null)
				result = caseAbstractRule(wallNodeToFigureRule);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Admin Node To Figure Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Admin Node To Figure Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdminNodeToFigureRule(AdminNodeToFigureRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>All Other Node To Floor Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>All Other Node To Floor Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAllOtherNodeToFloorRule(AllOtherNodeToFloorRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Board Node To Board Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Board Node To Board Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoardNodeToBoardRule(BoardNodeToBoardRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Goal Node To Figure Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Goal Node To Figure Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGoalNodeToFigureRule(GoalNodeToFigureRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Leftcolumn Node To Floor Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Leftcolumn Node To Floor Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLeftcolumnNodeToFloorRule(LeftcolumnNodeToFloorRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Server Node To Figure Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Server Node To Figure Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseServerNodeToFigureRule(ServerNodeToFigureRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Top Row Node To Floor Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Top Row Node To Floor Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTopRowNodeToFloorRule(TopRowNodeToFloorRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Wall Node To Figure Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Wall Node To Figure Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWallNodeToFigureRule(WallNodeToFigureRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractRule(AbstractRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //RulesSwitch
