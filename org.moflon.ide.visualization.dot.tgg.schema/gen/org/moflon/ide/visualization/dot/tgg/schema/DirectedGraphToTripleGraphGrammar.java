/**
 */
package org.moflon.ide.visualization.dot.tgg.schema;

import org.moflon.ide.visualization.dot.language.DirectedGraph;

import org.moflon.tgg.language.TripleGraphGrammar;

import org.moflon.tgg.runtime.AbstractCorrespondence;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Directed Graph To Triple Graph Grammar</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar#getSource <em>Source</em>}</li>
 *   <li>{@link org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.moflon.ide.visualization.dot.tgg.schema.SchemaPackage#getDirectedGraphToTripleGraphGrammar()
 * @model
 * @generated
 */
public interface DirectedGraphToTripleGraphGrammar extends AbstractCorrespondence {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(DirectedGraph)
	 * @see org.moflon.ide.visualization.dot.tgg.schema.SchemaPackage#getDirectedGraphToTripleGraphGrammar_Source()
	 * @model required="true"
	 * @generated
	 */
	DirectedGraph getSource();

	/**
	 * Sets the value of the '{@link org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(DirectedGraph value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(TripleGraphGrammar)
	 * @see org.moflon.ide.visualization.dot.tgg.schema.SchemaPackage#getDirectedGraphToTripleGraphGrammar_Target()
	 * @model required="true"
	 * @generated
	 */
	TripleGraphGrammar getTarget();

	/**
	 * Sets the value of the '{@link org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(TripleGraphGrammar value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // DirectedGraphToTripleGraphGrammar
