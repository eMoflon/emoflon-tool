/**
 */
package org.moflon.sdm.constraints.constraintstodemocles;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.moflon.sdm.constraints.constraintstodemocles.ConstraintstodemoclesPackage
 * @generated
 */
public interface ConstraintstodemoclesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConstraintstodemoclesFactory eINSTANCE = org.moflon.sdm.constraints.constraintstodemocles.impl.ConstraintstodemoclesFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Attribute Constraint Black And Nac Pattern Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Constraint Black And Nac Pattern Transformer</em>'.
	 * @generated
	 */
	AttributeConstraintBlackAndNacPatternTransformer createAttributeConstraintBlackAndNacPatternTransformer();

	/**
	 * Returns a new object of class '<em>Attribute Constraint Green Pattern Transformer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Constraint Green Pattern Transformer</em>'.
	 * @generated
	 */
	AttributeConstraintGreenPatternTransformer createAttributeConstraintGreenPatternTransformer();

	/**
	 * Returns a new object of class '<em>Identifyer Helper</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Identifyer Helper</em>'.
	 * @generated
	 */
	IdentifyerHelper createIdentifyerHelper();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ConstraintstodemoclesPackage getConstraintstodemoclesPackage();

} //ConstraintstodemoclesFactory
