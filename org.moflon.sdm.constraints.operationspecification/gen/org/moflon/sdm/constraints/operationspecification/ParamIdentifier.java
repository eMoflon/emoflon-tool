/**
 */
package org.moflon.sdm.constraints.operationspecification;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Param Identifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.ParamIdentifier#getOperationSpecificationGroup <em>Operation Specification Group</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.ParamIdentifier#getIdentifier <em>Identifier</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getParamIdentifier()
 * @model
 * @generated
 */
public interface ParamIdentifier extends EObject {
	/**
	 * Returns the value of the '<em><b>Operation Specification Group</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getParameterIDs <em>Parameter IDs</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation Specification Group</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation Specification Group</em>' container reference.
	 * @see #setOperationSpecificationGroup(OperationSpecificationGroup)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getParamIdentifier_OperationSpecificationGroup()
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getParameterIDs
	 * @model opposite="parameterIDs" required="true" transient="false"
	 * @generated
	 */
	OperationSpecificationGroup getOperationSpecificationGroup();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.ParamIdentifier#getOperationSpecificationGroup <em>Operation Specification Group</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation Specification Group</em>' container reference.
	 * @see #getOperationSpecificationGroup()
	 * @generated
	 */
	void setOperationSpecificationGroup(OperationSpecificationGroup value);

	/**
	 * Returns the value of the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Identifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identifier</em>' attribute.
	 * @see #setIdentifier(String)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getParamIdentifier_Identifier()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getIdentifier();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.ParamIdentifier#getIdentifier <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identifier</em>' attribute.
	 * @see #getIdentifier()
	 * @generated
	 */
	void setIdentifier(String value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // ParamIdentifier
