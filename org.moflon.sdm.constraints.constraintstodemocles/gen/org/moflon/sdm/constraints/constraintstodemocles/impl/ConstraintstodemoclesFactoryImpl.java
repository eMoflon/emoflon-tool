/**
 */
package org.moflon.sdm.constraints.constraintstodemocles.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.moflon.sdm.constraints.constraintstodemocles.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConstraintstodemoclesFactoryImpl extends EFactoryImpl implements ConstraintstodemoclesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConstraintstodemoclesFactory init() {
		try {
			ConstraintstodemoclesFactory theConstraintstodemoclesFactory = (ConstraintstodemoclesFactory) EPackage.Registry.INSTANCE
					.getEFactory(ConstraintstodemoclesPackage.eNS_URI);
			if (theConstraintstodemoclesFactory != null) {
				return theConstraintstodemoclesFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ConstraintstodemoclesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstraintstodemoclesFactoryImpl() {
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
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_BLACK_AND_NAC_PATTERN_TRANSFORMER:
			return createAttributeConstraintBlackAndNacPatternTransformer();
		case ConstraintstodemoclesPackage.ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_TRANSFORMER:
			return createAttributeConstraintGreenPatternTransformer();
		case ConstraintstodemoclesPackage.IDENTIFYER_HELPER:
			return createIdentifyerHelper();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case ConstraintstodemoclesPackage.ERRORS:
			return createErrorsFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case ConstraintstodemoclesPackage.ERRORS:
			return convertErrorsToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintBlackAndNacPatternTransformer createAttributeConstraintBlackAndNacPatternTransformer() {
		AttributeConstraintBlackAndNacPatternTransformerImpl attributeConstraintBlackAndNacPatternTransformer = new AttributeConstraintBlackAndNacPatternTransformerImpl();
		return attributeConstraintBlackAndNacPatternTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintGreenPatternTransformer createAttributeConstraintGreenPatternTransformer() {
		AttributeConstraintGreenPatternTransformerImpl attributeConstraintGreenPatternTransformer = new AttributeConstraintGreenPatternTransformerImpl();
		return attributeConstraintGreenPatternTransformer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdentifyerHelper createIdentifyerHelper() {
		IdentifyerHelperImpl identifyerHelper = new IdentifyerHelperImpl();
		return identifyerHelper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Errors createErrorsFromString(EDataType eDataType, String initialValue) {
		Errors result = Errors.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertErrorsToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstraintstodemoclesPackage getConstraintstodemoclesPackage() {
		return (ConstraintstodemoclesPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ConstraintstodemoclesPackage getPackage() {
		return ConstraintstodemoclesPackage.eINSTANCE;
	}

} //ConstraintstodemoclesFactoryImpl
