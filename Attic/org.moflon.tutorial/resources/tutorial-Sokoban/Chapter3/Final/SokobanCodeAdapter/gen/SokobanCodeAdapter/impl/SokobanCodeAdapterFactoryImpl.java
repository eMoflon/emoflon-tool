/**
 */
package SokobanCodeAdapter.impl;

import SokobanCodeAdapter.*;

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
public class SokobanCodeAdapterFactoryImpl extends EFactoryImpl implements
		SokobanCodeAdapterFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SokobanCodeAdapterFactory init() {
		try {
			SokobanCodeAdapterFactory theSokobanCodeAdapterFactory = (SokobanCodeAdapterFactory) EPackage.Registry.INSTANCE
					.getEFactory(SokobanCodeAdapterPackage.eNS_URI);
			if (theSokobanCodeAdapterFactory != null) {
				return theSokobanCodeAdapterFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SokobanCodeAdapterFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SokobanCodeAdapterFactoryImpl() {
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
		case SokobanCodeAdapterPackage.NODE_TO_BOARD:
			return createNodeToBoard();
		case SokobanCodeAdapterPackage.NODE_TO_FLOOR:
			return createNodeToFloor();
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
	public NodeToBoard createNodeToBoard() {
		NodeToBoardImpl nodeToBoard = new NodeToBoardImpl();
		return nodeToBoard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeToFloor createNodeToFloor() {
		NodeToFloorImpl nodeToFloor = new NodeToFloorImpl();
		return nodeToFloor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SokobanCodeAdapterPackage getSokobanCodeAdapterPackage() {
		return (SokobanCodeAdapterPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SokobanCodeAdapterPackage getPackage() {
		return SokobanCodeAdapterPackage.eINSTANCE;
	}

} //SokobanCodeAdapterFactoryImpl
