/**
 */
package org.moflon.ide.visualization.dot.ecore.epackageviz.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.moflon.ide.visualization.dot.ecore.epackageviz.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EpackagevizFactoryImpl extends EFactoryImpl implements EpackagevizFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EpackagevizFactory init() {
		try {
			EpackagevizFactory theEpackagevizFactory = (EpackagevizFactory) EPackage.Registry.INSTANCE
					.getEFactory(EpackagevizPackage.eNS_URI);
			if (theEpackagevizFactory != null) {
				return theEpackagevizFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EpackagevizFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EpackagevizFactoryImpl() {
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
		case EpackagevizPackage.PNODE_TO_ECLASSIFIER:
			return createPNodeToEClassifier();
		case EpackagevizPackage.CLASS_GRAPH_TO_EPACKAGE:
			return createClassGraphToEPackage();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PNodeToEClassifier createPNodeToEClassifier() {
		PNodeToEClassifierImpl pNodeToEClassifier = new PNodeToEClassifierImpl();
		return pNodeToEClassifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassGraphToEPackage createClassGraphToEPackage() {
		ClassGraphToEPackageImpl classGraphToEPackage = new ClassGraphToEPackageImpl();
		return classGraphToEPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EpackagevizPackage getEpackagevizPackage() {
		return (EpackagevizPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EpackagevizPackage getPackage() {
		return EpackagevizPackage.eINSTANCE;
	}

} //EpackagevizFactoryImpl
