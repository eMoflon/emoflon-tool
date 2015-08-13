/**
 */
package SokobanCodeAdapter;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see SokobanCodeAdapter.SokobanCodeAdapterPackage
 * @generated
 */
public interface SokobanCodeAdapterFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SokobanCodeAdapterFactory eINSTANCE = SokobanCodeAdapter.impl.SokobanCodeAdapterFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Node To Board</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node To Board</em>'.
	 * @generated
	 */
	NodeToBoard createNodeToBoard();

	/**
	 * Returns a new object of class '<em>Node To Floor</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node To Floor</em>'.
	 * @generated
	 */
	NodeToFloor createNodeToFloor();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SokobanCodeAdapterPackage getSokobanCodeAdapterPackage();

} //SokobanCodeAdapterFactory
