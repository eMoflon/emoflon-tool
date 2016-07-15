/**
 */
package org.moflon.ide.visualization.dot.tgg.schema;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.moflon.ide.visualization.dot.tgg.schema.SchemaPackage
 * @generated
 */
public interface SchemaFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SchemaFactory eINSTANCE = org.moflon.ide.visualization.dot.tgg.schema.impl.SchemaFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Node To Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node To Domain</em>'.
	 * @generated
	 */
	NodeToDomain createNodeToDomain();

	/**
	 * Returns a new object of class '<em>Directed Graph To Triple Graph Grammar</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Directed Graph To Triple Graph Grammar</em>'.
	 * @generated
	 */
	DirectedGraphToTripleGraphGrammar createDirectedGraphToTripleGraphGrammar();

	/**
	 * Returns a new object of class '<em>Node To Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Node To Rule</em>'.
	 * @generated
	 */
	NodeToRule createNodeToRule();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SchemaPackage getSchemaPackage();

} //SchemaFactory
