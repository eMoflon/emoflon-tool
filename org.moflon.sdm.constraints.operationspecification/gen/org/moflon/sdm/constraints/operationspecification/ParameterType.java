/**
 */
package org.moflon.sdm.constraints.operationspecification;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.ParameterType#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getParameterType()
 * @model
 * @generated
 */
public interface ParameterType extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EClassifier)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getParameterType_Type()
	 * @model
	 * @generated
	 */
	EClassifier getType();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.ParameterType#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EClassifier value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // ParameterType
