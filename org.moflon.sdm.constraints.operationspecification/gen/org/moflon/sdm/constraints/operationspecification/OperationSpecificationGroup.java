/**
 */
package org.moflon.sdm.constraints.operationspecification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.moflon.sdm.compiler.democles.validation.result.ErrorMessage;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation Specification Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getAttributeConstraintLibrary <em>Attribute Constraint Library</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getConstraintSpecifications <em>Constraint Specifications</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getOperationSpecifications <em>Operation Specifications</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getParameterIDs <em>Parameter IDs</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getOperationIdentifier <em>Operation Identifier</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#isTemplateGroupGenerated <em>Template Group Generated</em>}</li>
 *   <li>{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getTemplateGroupString <em>Template Group String</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getOperationSpecificationGroup()
 * @model
 * @generated
 */
public interface OperationSpecificationGroup extends EObject {
	/**
	 * Returns the value of the '<em><b>Attribute Constraint Library</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getOperationSpecifications <em>Operation Specifications</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Constraint Library</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Constraint Library</em>' container reference.
	 * @see #setAttributeConstraintLibrary(AttributeConstraintLibrary)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getOperationSpecificationGroup_AttributeConstraintLibrary()
	 * @see org.moflon.sdm.constraints.operationspecification.AttributeConstraintLibrary#getOperationSpecifications
	 * @model opposite="operationSpecifications" required="true" transient="false"
	 * @generated
	 */
	AttributeConstraintLibrary getAttributeConstraintLibrary();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getAttributeConstraintLibrary <em>Attribute Constraint Library</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Constraint Library</em>' container reference.
	 * @see #getAttributeConstraintLibrary()
	 * @generated
	 */
	void setAttributeConstraintLibrary(AttributeConstraintLibrary value);

	/**
	 * Returns the value of the '<em><b>Constraint Specifications</b></em>' reference list.
	 * The list contents are of type {@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification}.
	 * It is bidirectional and its opposite is '{@link org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getOperationSpecificationGroup <em>Operation Specification Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint Specifications</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint Specifications</em>' reference list.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getOperationSpecificationGroup_ConstraintSpecifications()
	 * @see org.moflon.sdm.constraints.operationspecification.ConstraintSpecification#getOperationSpecificationGroup
	 * @model opposite="operationSpecificationGroup"
	 * @generated
	 */
	EList<ConstraintSpecification> getConstraintSpecifications();

	/**
	 * Returns the value of the '<em><b>Operation Specifications</b></em>' containment reference list.
	 * The list contents are of type {@link org.moflon.sdm.constraints.operationspecification.OperationSpecification}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation Specifications</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation Specifications</em>' containment reference list.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getOperationSpecificationGroup_OperationSpecifications()
	 * @model containment="true"
	 * @generated
	 */
	EList<OperationSpecification> getOperationSpecifications();

	/**
	 * Returns the value of the '<em><b>Parameter IDs</b></em>' containment reference list.
	 * The list contents are of type {@link org.moflon.sdm.constraints.operationspecification.ParamIdentifier}.
	 * It is bidirectional and its opposite is '{@link org.moflon.sdm.constraints.operationspecification.ParamIdentifier#getOperationSpecificationGroup <em>Operation Specification Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter IDs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter IDs</em>' containment reference list.
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getOperationSpecificationGroup_ParameterIDs()
	 * @see org.moflon.sdm.constraints.operationspecification.ParamIdentifier#getOperationSpecificationGroup
	 * @model opposite="operationSpecificationGroup" containment="true"
	 * @generated
	 */
	EList<ParamIdentifier> getParameterIDs();

	/**
	 * Returns the value of the '<em><b>Operation Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation Identifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation Identifier</em>' attribute.
	 * @see #setOperationIdentifier(String)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getOperationSpecificationGroup_OperationIdentifier()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getOperationIdentifier();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getOperationIdentifier <em>Operation Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation Identifier</em>' attribute.
	 * @see #getOperationIdentifier()
	 * @generated
	 */
	void setOperationIdentifier(String value);

	/**
	 * Returns the value of the '<em><b>Template Group Generated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Template Group Generated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Template Group Generated</em>' attribute.
	 * @see #setTemplateGroupGenerated(boolean)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getOperationSpecificationGroup_TemplateGroupGenerated()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	boolean isTemplateGroupGenerated();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#isTemplateGroupGenerated <em>Template Group Generated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template Group Generated</em>' attribute.
	 * @see #isTemplateGroupGenerated()
	 * @generated
	 */
	void setTemplateGroupGenerated(boolean value);

	/**
	 * Returns the value of the '<em><b>Template Group String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Template Group String</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Template Group String</em>' attribute.
	 * @see #setTemplateGroupString(String)
	 * @see org.moflon.sdm.constraints.operationspecification.OperationspecificationPackage#getOperationSpecificationGroup_TemplateGroupString()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getTemplateGroupString();

	/**
	 * Sets the value of the '{@link org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup#getTemplateGroupString <em>Template Group String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template Group String</em>' attribute.
	 * @see #getTemplateGroupString()
	 * @generated
	 */
	void setTemplateGroupString(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	ErrorMessage gernerateTemplate();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // OperationSpecificationGroup
