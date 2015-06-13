/**
 */
package SokobanCodeAdapter;

import org.eclipse.emf.ecore.EClass;
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
 * @see SokobanCodeAdapter.SokobanCodeAdapterFactory
 * @model kind="package"
 * @generated
 */
public interface SokobanCodeAdapterPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "SokobanCodeAdapter";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.moflon.org.SokobanCodeAdapter";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "SokobanCodeAdapter";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SokobanCodeAdapterPackage eINSTANCE = SokobanCodeAdapter.impl.SokobanCodeAdapterPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link SokobanCodeAdapter.impl.NodeToBoardImpl <em>Node To Board</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SokobanCodeAdapter.impl.NodeToBoardImpl
	 * @see SokobanCodeAdapter.impl.SokobanCodeAdapterPackageImpl#getNodeToBoard()
	 * @generated
	 */
	int NODE_TO_BOARD = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_BOARD__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_BOARD__TARGET = 1;

	/**
	 * The number of structural features of the '<em>Node To Board</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_BOARD_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Node To Board</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_BOARD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link SokobanCodeAdapter.impl.NodeToFloorImpl <em>Node To Floor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SokobanCodeAdapter.impl.NodeToFloorImpl
	 * @see SokobanCodeAdapter.impl.SokobanCodeAdapterPackageImpl#getNodeToFloor()
	 * @generated
	 */
	int NODE_TO_FLOOR = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_FLOOR__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_FLOOR__TARGET = 1;

	/**
	 * The number of structural features of the '<em>Node To Floor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_FLOOR_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Node To Floor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_FLOOR_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link SokobanCodeAdapter.NodeToBoard <em>Node To Board</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node To Board</em>'.
	 * @see SokobanCodeAdapter.NodeToBoard
	 * @generated
	 */
	EClass getNodeToBoard();

	/**
	 * Returns the meta object for the reference '{@link SokobanCodeAdapter.NodeToBoard#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see SokobanCodeAdapter.NodeToBoard#getSource()
	 * @see #getNodeToBoard()
	 * @generated
	 */
	EReference getNodeToBoard_Source();

	/**
	 * Returns the meta object for the reference '{@link SokobanCodeAdapter.NodeToBoard#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see SokobanCodeAdapter.NodeToBoard#getTarget()
	 * @see #getNodeToBoard()
	 * @generated
	 */
	EReference getNodeToBoard_Target();

	/**
	 * Returns the meta object for class '{@link SokobanCodeAdapter.NodeToFloor <em>Node To Floor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node To Floor</em>'.
	 * @see SokobanCodeAdapter.NodeToFloor
	 * @generated
	 */
	EClass getNodeToFloor();

	/**
	 * Returns the meta object for the reference '{@link SokobanCodeAdapter.NodeToFloor#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see SokobanCodeAdapter.NodeToFloor#getSource()
	 * @see #getNodeToFloor()
	 * @generated
	 */
	EReference getNodeToFloor_Source();

	/**
	 * Returns the meta object for the reference '{@link SokobanCodeAdapter.NodeToFloor#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see SokobanCodeAdapter.NodeToFloor#getTarget()
	 * @see #getNodeToFloor()
	 * @generated
	 */
	EReference getNodeToFloor_Target();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SokobanCodeAdapterFactory getSokobanCodeAdapterFactory();

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
		 * The meta object literal for the '{@link SokobanCodeAdapter.impl.NodeToBoardImpl <em>Node To Board</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SokobanCodeAdapter.impl.NodeToBoardImpl
		 * @see SokobanCodeAdapter.impl.SokobanCodeAdapterPackageImpl#getNodeToBoard()
		 * @generated
		 */
		EClass NODE_TO_BOARD = eINSTANCE.getNodeToBoard();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_TO_BOARD__SOURCE = eINSTANCE.getNodeToBoard_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_TO_BOARD__TARGET = eINSTANCE.getNodeToBoard_Target();

		/**
		 * The meta object literal for the '{@link SokobanCodeAdapter.impl.NodeToFloorImpl <em>Node To Floor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SokobanCodeAdapter.impl.NodeToFloorImpl
		 * @see SokobanCodeAdapter.impl.SokobanCodeAdapterPackageImpl#getNodeToFloor()
		 * @generated
		 */
		EClass NODE_TO_FLOOR = eINSTANCE.getNodeToFloor();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_TO_FLOOR__SOURCE = eINSTANCE.getNodeToFloor_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_TO_FLOOR__TARGET = eINSTANCE.getNodeToFloor_Target();

	}

} //SokobanCodeAdapterPackage
