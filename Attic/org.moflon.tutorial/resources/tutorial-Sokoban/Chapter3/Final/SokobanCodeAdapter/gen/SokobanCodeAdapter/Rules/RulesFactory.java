/**
 */
package SokobanCodeAdapter.Rules;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see SokobanCodeAdapter.Rules.RulesPackage
 * @generated
 */
public interface RulesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RulesFactory eINSTANCE = SokobanCodeAdapter.Rules.impl.RulesFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Admin Node To Figure Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Admin Node To Figure Rule</em>'.
	 * @generated
	 */
	AdminNodeToFigureRule createAdminNodeToFigureRule();

	/**
	 * Returns a new object of class '<em>All Other Node To Floor Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>All Other Node To Floor Rule</em>'.
	 * @generated
	 */
	AllOtherNodeToFloorRule createAllOtherNodeToFloorRule();

	/**
	 * Returns a new object of class '<em>Board Node To Board Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Board Node To Board Rule</em>'.
	 * @generated
	 */
	BoardNodeToBoardRule createBoardNodeToBoardRule();

	/**
	 * Returns a new object of class '<em>Goal Node To Figure Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Goal Node To Figure Rule</em>'.
	 * @generated
	 */
	GoalNodeToFigureRule createGoalNodeToFigureRule();

	/**
	 * Returns a new object of class '<em>Leftcolumn Node To Floor Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Leftcolumn Node To Floor Rule</em>'.
	 * @generated
	 */
	LeftcolumnNodeToFloorRule createLeftcolumnNodeToFloorRule();

	/**
	 * Returns a new object of class '<em>Server Node To Figure Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Server Node To Figure Rule</em>'.
	 * @generated
	 */
	ServerNodeToFigureRule createServerNodeToFigureRule();

	/**
	 * Returns a new object of class '<em>Top Row Node To Floor Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Top Row Node To Floor Rule</em>'.
	 * @generated
	 */
	TopRowNodeToFloorRule createTopRowNodeToFloorRule();

	/**
	 * Returns a new object of class '<em>Wall Node To Figure Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Wall Node To Figure Rule</em>'.
	 * @generated
	 */
	WallNodeToFigureRule createWallNodeToFigureRule();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RulesPackage getRulesPackage();

} //RulesFactory
