/**
 */
package org.moflon.sdm.constraints.democles;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.moflon.sdm.constraints.democles.DemoclesPackage
 * @generated
 */
public class DemoclesFactory extends EFactoryImpl {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final DemoclesFactory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DemoclesFactory init() {
		try {
			DemoclesFactory theDemoclesFactory = (DemoclesFactory) EPackage.Registry.INSTANCE
					.getEFactory(DemoclesPackage.eNS_URI);
			if (theDemoclesFactory != null) {
				return theDemoclesFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DemoclesFactory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DemoclesFactory() {
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
		case DemoclesPackage.ATTRIBUTE_VARIABLE_CONSTRAINT:
			return createAttributeVariableConstraint();
		case DemoclesPackage.ATTRIBUTE_VALUE_CONSTRAINT:
			return createAttributeValueConstraint();
		case DemoclesPackage.TYPED_CONSTANT:
			return createTypedConstant();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeVariableConstraint createAttributeVariableConstraint() {
		AttributeVariableConstraint attributeVariableConstraint = new AttributeVariableConstraint();
		return attributeVariableConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeValueConstraint createAttributeValueConstraint() {
		AttributeValueConstraint attributeValueConstraint = new AttributeValueConstraint();
		return attributeValueConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypedConstant createTypedConstant() {
		TypedConstant typedConstant = new TypedConstant();
		return typedConstant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DemoclesPackage getDemoclesPackage() {
		return (DemoclesPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DemoclesPackage getPackage() {
		return DemoclesPackage.eINSTANCE;
	}

} //DemoclesFactory
