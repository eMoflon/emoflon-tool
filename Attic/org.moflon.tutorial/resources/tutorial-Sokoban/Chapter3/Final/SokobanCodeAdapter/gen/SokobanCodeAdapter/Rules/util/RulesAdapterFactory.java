/**
 */
package SokobanCodeAdapter.Rules.util;

import SokobanCodeAdapter.Rules.*;

import TGGRuntime.AbstractRule;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see SokobanCodeAdapter.Rules.RulesPackage
 * @generated
 */
public class RulesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RulesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = RulesPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RulesSwitch<Adapter> modelSwitch = new RulesSwitch<Adapter>() {
		@Override
		public Adapter caseAdminNodeToFigureRule(AdminNodeToFigureRule object) {
			return createAdminNodeToFigureRuleAdapter();
		}

		@Override
		public Adapter caseAllOtherNodeToFloorRule(
				AllOtherNodeToFloorRule object) {
			return createAllOtherNodeToFloorRuleAdapter();
		}

		@Override
		public Adapter caseBoardNodeToBoardRule(BoardNodeToBoardRule object) {
			return createBoardNodeToBoardRuleAdapter();
		}

		@Override
		public Adapter caseGoalNodeToFigureRule(GoalNodeToFigureRule object) {
			return createGoalNodeToFigureRuleAdapter();
		}

		@Override
		public Adapter caseLeftcolumnNodeToFloorRule(
				LeftcolumnNodeToFloorRule object) {
			return createLeftcolumnNodeToFloorRuleAdapter();
		}

		@Override
		public Adapter caseServerNodeToFigureRule(ServerNodeToFigureRule object) {
			return createServerNodeToFigureRuleAdapter();
		}

		@Override
		public Adapter caseTopRowNodeToFloorRule(TopRowNodeToFloorRule object) {
			return createTopRowNodeToFloorRuleAdapter();
		}

		@Override
		public Adapter caseWallNodeToFigureRule(WallNodeToFigureRule object) {
			return createWallNodeToFigureRuleAdapter();
		}

		@Override
		public Adapter caseAbstractRule(AbstractRule object) {
			return createAbstractRuleAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link SokobanCodeAdapter.Rules.AdminNodeToFigureRule <em>Admin Node To Figure Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SokobanCodeAdapter.Rules.AdminNodeToFigureRule
	 * @generated
	 */
	public Adapter createAdminNodeToFigureRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule <em>All Other Node To Floor Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SokobanCodeAdapter.Rules.AllOtherNodeToFloorRule
	 * @generated
	 */
	public Adapter createAllOtherNodeToFloorRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SokobanCodeAdapter.Rules.BoardNodeToBoardRule <em>Board Node To Board Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SokobanCodeAdapter.Rules.BoardNodeToBoardRule
	 * @generated
	 */
	public Adapter createBoardNodeToBoardRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SokobanCodeAdapter.Rules.GoalNodeToFigureRule <em>Goal Node To Figure Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SokobanCodeAdapter.Rules.GoalNodeToFigureRule
	 * @generated
	 */
	public Adapter createGoalNodeToFigureRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule <em>Leftcolumn Node To Floor Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SokobanCodeAdapter.Rules.LeftcolumnNodeToFloorRule
	 * @generated
	 */
	public Adapter createLeftcolumnNodeToFloorRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SokobanCodeAdapter.Rules.ServerNodeToFigureRule <em>Server Node To Figure Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SokobanCodeAdapter.Rules.ServerNodeToFigureRule
	 * @generated
	 */
	public Adapter createServerNodeToFigureRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SokobanCodeAdapter.Rules.TopRowNodeToFloorRule <em>Top Row Node To Floor Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SokobanCodeAdapter.Rules.TopRowNodeToFloorRule
	 * @generated
	 */
	public Adapter createTopRowNodeToFloorRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link SokobanCodeAdapter.Rules.WallNodeToFigureRule <em>Wall Node To Figure Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see SokobanCodeAdapter.Rules.WallNodeToFigureRule
	 * @generated
	 */
	public Adapter createWallNodeToFigureRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link TGGRuntime.AbstractRule <em>Abstract Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see TGGRuntime.AbstractRule
	 * @generated
	 */
	public Adapter createAbstractRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //RulesAdapterFactory
