/**
 */
package org.moflon.sdm.constraints.operationspecification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.moflon.sdm.constraints.democles.AttributeVariableConstraint;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Constraint Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getOperationSpecifications <em>Operation Specifications</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getConstraintSpecifications <em>Constraint Specifications</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getPrefix <em>Prefix</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getAttributeConstraintLibrary()
 * @model
 * @generated
 */
public interface AttributeConstraintLibrary extends EObject {
	/**
	 * Returns the value of the '<em><b>Operation Specifications</b></em>' containment reference list.
	 * The list contents are of type {@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup}.
	 * It is bidirectional and its opposite is '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getAttributeConstraintLibrary <em>Attribute Constraint Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation Specifications</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation Specifications</em>' containment reference list.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getAttributeConstraintLibrary_OperationSpecifications()
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getAttributeConstraintLibrary
	 * @model opposite="attributeConstraintLibrary" containment="true"
	 * @generated
	 */
	EList<OperationSpecificationGroup> getOperationSpecifications();

	/**
	 * Returns the value of the '<em><b>Constraint Specifications</b></em>' containment reference list.
	 * The list contents are of type {@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification}.
	 * It is bidirectional and its opposite is '{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getAttributeConstraintLibrary <em>Attribute Constraint Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint Specifications</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint Specifications</em>' containment reference list.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getAttributeConstraintLibrary_ConstraintSpecifications()
	 * @see org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getAttributeConstraintLibrary
	 * @model opposite="attributeConstraintLibrary" containment="true"
	 * @generated
	 */
	EList<ConstraintSpecification> getConstraintSpecifications();

	/**
	 * Returns the value of the '<em><b>Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prefix</em>' attribute.
	 * @see #setPrefix(String)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getAttributeConstraintLibrary_Prefix()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getPrefix();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getPrefix <em>Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prefix</em>' attribute.
	 * @see #getPrefix()
	 * @generated
	 */
	void setPrefix(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	ConstraintSpecification lookupConstraintType(AttributeVariableConstraint constraint);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // AttributeConstraintLibrary
