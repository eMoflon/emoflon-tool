/**
 */
package org.moflon.ide.visualization.dot.tgg.schema;

import org.moflon.ide.visualization.dot.language.NodeCommand;

import org.moflon.tgg.language.Domain;

import org.moflon.tgg.runtime.AbstractCorrespondence;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node To Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.moflon.ide.visualization.dot.tgg.schema.NodeToDomain#getSource <em>Source</em>}</li>
 *   <li>{@link org.moflon.ide.visualization.dot.tgg.schema.NodeToDomain#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.moflon.ide.visualization.dot.tgg.schema.SchemaPackage#getNodeToDomain()
 * @model
 * @generated
 */
public interface NodeToDomain extends AbstractCorrespondence {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(NodeCommand)
	 * @see org.moflon.ide.visualization.dot.tgg.schema.SchemaPackage#getNodeToDomain_Source()
	 * @model required="true"
	 * @generated
	 */
	NodeCommand getSource();

	/**
	 * Sets the value of the '{@link org.moflon.ide.visualization.dot.tgg.schema.NodeToDomain#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(NodeCommand value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Domain)
	 * @see org.moflon.ide.visualization.dot.tgg.schema.SchemaPackage#getNodeToDomain_Target()
	 * @model required="true"
	 * @generated
	 */
	Domain getTarget();

	/**
	 * Sets the value of the '{@link org.moflon.ide.visualization.dot.tgg.schema.NodeToDomain#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Domain value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // NodeToDomain
