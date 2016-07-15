/**
 */
package org.moflon.ide.visualization.dot.tgg.schema.Rules;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RulesPackage
 * @generated
 */
public interface RulesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RulesFactory eINSTANCE = org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RulesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Refinment Rules</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Refinment Rules</em>'.
	 * @generated
	 */
	RefinmentRules createRefinmentRules();

	/**
	 * Returns a new object of class '<em>Fill Base Rules</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Fill Base Rules</em>'.
	 * @generated
	 */
	FillBaseRules createFillBaseRules();

	/**
	 * Returns a new object of class '<em>TGG Grammar Directed Graph Axiom</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Grammar Directed Graph Axiom</em>'.
	 * @generated
	 */
	TGGGrammarDirectedGraphAxiom createTGGGrammarDirectedGraphAxiom();

	/**
	 * Returns a new object of class '<em>Complement Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Complement Rule</em>'.
	 * @generated
	 */
	ComplementRule createComplementRule();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RulesPackage getRulesPackage();

} //RulesFactory
