/**
 */
package org.moflon.sdm.constraints.operationspecification;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage
 * @generated
 */
public interface OperationspecificationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	OperationspecificationFactory eINSTANCE = org.moflon.sdm.constraints.operationspecification.impl.OperationspecificationFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Operation Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation Specification</em>'.
	 * @generated
	 */
	OperationSpecification createOperationSpecification();

	/**
	 * Returns a new object of class '<em>Attribute Constraint Library</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Constraint Library</em>'.
	 * @generated
	 */
	AttributeConstraintLibrary createAttributeConstraintLibrary();

	/**
	 * Returns a new object of class '<em>Operation Specification Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation Specification Group</em>'.
	 * @generated
	 */
	OperationSpecificationGroup createOperationSpecificationGroup();

	/**
	 * Returns a new object of class '<em>Param Identifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Param Identifier</em>'.
	 * @generated
	 */
	ParamIdentifier createParamIdentifier();

	/**
	 * Returns a new object of class '<em>Constraint Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constraint Specification</em>'.
	 * @generated
	 */
	ConstraintSpecification createConstraintSpecification();

	/**
	 * Returns a new object of class '<em>Parameter Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter Type</em>'.
	 * @generated
	 */
	ParameterType createParameterType();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	OperationspecificationPackage getOperationspecificationPackage();

} //OperationspecificationFactory
