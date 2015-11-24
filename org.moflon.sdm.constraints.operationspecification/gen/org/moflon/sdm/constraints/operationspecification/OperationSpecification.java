/**
 */
package org.moflon.sdm.constraints.operationspecification;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.OperationSpecification#getSpecification <em>Specification</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.OperationSpecification#getAdornmentString <em>Adornment String</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.OperationSpecification#isAlwaysSuccessful <em>Always Successful</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getOperationSpecification()
 * @model
 * @generated
 */
public interface OperationSpecification extends EObject {
	/**
	 * Returns the value of the '<em><b>Specification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specification</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specification</em>' attribute.
	 * @see #setSpecification(String)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getOperationSpecification_Specification()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getSpecification();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecification#getSpecification <em>Specification</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specification</em>' attribute.
	 * @see #getSpecification()
	 * @generated
	 */
	void setSpecification(String value);

	/**
	 * Returns the value of the '<em><b>Adornment String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adornment String</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Adornment String</em>' attribute.
	 * @see #setAdornmentString(String)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getOperationSpecification_AdornmentString()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getAdornmentString();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecification#getAdornmentString <em>Adornment String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Adornment String</em>' attribute.
	 * @see #getAdornmentString()
	 * @generated
	 */
	void setAdornmentString(String value);

	/**
	 * Returns the value of the '<em><b>Always Successful</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Always Successful</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Always Successful</em>' attribute.
	 * @see #setAlwaysSuccessful(boolean)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getOperationSpecification_AlwaysSuccessful()
	 * @model default="true" required="true" ordered="false"
	 * @generated
	 */
	boolean isAlwaysSuccessful();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecification#isAlwaysSuccessful <em>Always Successful</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Always Successful</em>' attribute.
	 * @see #isAlwaysSuccessful()
	 * @generated
	 */
	void setAlwaysSuccessful(boolean value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // OperationSpecification
