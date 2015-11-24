/**
 */
package org.moflon.sdm.constraints.scopevalidation;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.moflon.sdm.constraints.scopevalidation.ScopevalidationPackage
 * @generated
 */
public interface ScopevalidationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ScopevalidationFactory eINSTANCE = org.moflon.sdm.constraints.scopevalidation.impl.ScopevalidationFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Attribute Constraint Black Pattern Invocation Builder</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Constraint Black Pattern Invocation Builder</em>'.
	 * @generated
	 */
	AttributeConstraintBlackPatternInvocationBuilder createAttributeConstraintBlackPatternInvocationBuilder();

	/**
	 * Returns a new object of class '<em>Attribute Constraint Green Pattern Invocation Builder</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Constraint Green Pattern Invocation Builder</em>'.
	 * @generated
	 */
	AttributeConstraintGreenPatternInvocationBuilder createAttributeConstraintGreenPatternInvocationBuilder();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ScopevalidationPackage getScopevalidationPackage();

} //ScopevalidationFactory
