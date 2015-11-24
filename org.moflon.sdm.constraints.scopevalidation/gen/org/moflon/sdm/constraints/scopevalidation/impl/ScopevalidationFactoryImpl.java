/**
 */
package org.moflon.sdm.constraints.scopevalidation.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.moflon.sdm.constraints.scopevalidation.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ScopevalidationFactoryImpl extends EFactoryImpl implements ScopevalidationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScopevalidationFactory init() {
		try {
			ScopevalidationFactory theScopevalidationFactory = (ScopevalidationFactory) EPackage.Registry.INSTANCE
					.getEFactory(ScopevalidationPackage.eNS_URI);
			if (theScopevalidationFactory != null) {
				return theScopevalidationFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ScopevalidationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopevalidationFactoryImpl() {
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
		case ScopevalidationPackage.ATTRIBUTE_CONSTRAINT_BLACK_PATTERN_INVOCATION_BUILDER:
			return createAttributeConstraintBlackPatternInvocationBuilder();
		case ScopevalidationPackage.ATTRIBUTE_CONSTRAINT_GREEN_PATTERN_INVOCATION_BUILDER:
			return createAttributeConstraintGreenPatternInvocationBuilder();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintBlackPatternInvocationBuilder createAttributeConstraintBlackPatternInvocationBuilder() {
		AttributeConstraintBlackPatternInvocationBuilderImpl attributeConstraintBlackPatternInvocationBuilder = new AttributeConstraintBlackPatternInvocationBuilderImpl();
		return attributeConstraintBlackPatternInvocationBuilder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintGreenPatternInvocationBuilder createAttributeConstraintGreenPatternInvocationBuilder() {
		AttributeConstraintGreenPatternInvocationBuilderImpl attributeConstraintGreenPatternInvocationBuilder = new AttributeConstraintGreenPatternInvocationBuilderImpl();
		return attributeConstraintGreenPatternInvocationBuilder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopevalidationPackage getScopevalidationPackage() {
		return (ScopevalidationPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ScopevalidationPackage getPackage() {
		return ScopevalidationPackage.eINSTANCE;
	}

} //ScopevalidationFactoryImpl
