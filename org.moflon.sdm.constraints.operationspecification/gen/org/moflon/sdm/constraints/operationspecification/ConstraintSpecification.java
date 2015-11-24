/**
 */
package org.moflon.sdm.constraints.operationspecification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.gervarro.democles.specification.ConstraintType;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraint Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getAttributeConstraintLibrary <em>Attribute Constraint Library</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getParameterTypes <em>Parameter Types</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getOperationSpecificationGroup <em>Operation Specification Group</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getSymbol <em>Symbol</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getConstraintSpecification()
 * @model superTypes="org.moflon.sdm.constraints.operationspecification.ConstraintType"
 * @generated
 */
public interface ConstraintSpecification extends EObject, ConstraintType {
	/**
	 * Returns the value of the '<em><b>Attribute Constraint Library</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getConstraintSpecifications <em>Constraint Specifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Constraint Library</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Constraint Library</em>' container reference.
	 * @see #setAttributeConstraintLibrary(AttributeConstraintLibrary)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getConstraintSpecification_AttributeConstraintLibrary()
	 * @see org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getConstraintSpecifications
	 * @model opposite="constraintSpecifications" required="true" transient="false"
	 * @generated
	 */
	AttributeConstraintLibrary getAttributeConstraintLibrary();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getAttributeConstraintLibrary <em>Attribute Constraint Library</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Constraint Library</em>' container reference.
	 * @see #getAttributeConstraintLibrary()
	 * @generated
	 */
	void setAttributeConstraintLibrary(AttributeConstraintLibrary value);

	/**
	 * Returns the value of the '<em><b>Parameter Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.moflon.sdm.constraints.operationspecification.ParameterType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Types</em>' containment reference list.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getConstraintSpecification_ParameterTypes()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterType> getParameterTypes();

	/**
	 * Returns the value of the '<em><b>Operation Specification Group</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getConstraintSpecifications <em>Constraint Specifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation Specification Group</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation Specification Group</em>' reference.
	 * @see #setOperationSpecificationGroup(OperationSpecificationGroup)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getConstraintSpecification_OperationSpecificationGroup()
	 * @see org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getConstraintSpecifications
	 * @model opposite="constraintSpecifications" required="true"
	 * @generated
	 */
	OperationSpecificationGroup getOperationSpecificationGroup();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getOperationSpecificationGroup <em>Operation Specification Group</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation Specification Group</em>' reference.
	 * @see #getOperationSpecificationGroup()
	 * @generated
	 */
	void setOperationSpecificationGroup(OperationSpecificationGroup value);

	/**
	 * Returns the value of the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Symbol</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symbol</em>' attribute.
	 * @see #setSymbol(String)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getConstraintSpecification_Symbol()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getSymbol();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getSymbol <em>Symbol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Symbol</em>' attribute.
	 * @see #getSymbol()
	 * @generated
	 */
	void setSymbol(String value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // ConstraintSpecification
