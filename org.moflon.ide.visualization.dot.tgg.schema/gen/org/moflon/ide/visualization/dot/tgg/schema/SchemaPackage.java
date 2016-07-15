/**
 */
package org.moflon.ide.visualization.dot.tgg.schema;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.moflon.tgg.runtime.RuntimePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.moflon.ide.visualization.dot.tgg.schema.SchemaFactory
 * @model kind="package"
 * @generated
 */
public interface SchemaPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "schema";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/plugin/org.moflon.ide.visualization.dot.tgg.schema/model/Schema.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.moflon.ide.visualization.dot.tgg.schema";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SchemaPackage eINSTANCE = org.moflon.ide.visualization.dot.tgg.schema.impl.SchemaPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.moflon.ide.visualization.dot.tgg.schema.impl.NodeToDomainImpl <em>Node To Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.ide.visualization.dot.tgg.schema.impl.NodeToDomainImpl
	 * @see org.moflon.ide.visualization.dot.tgg.schema.impl.SchemaPackageImpl#getNodeToDomain()
	 * @generated
	 */
	int NODE_TO_DOMAIN = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_DOMAIN__SOURCE = RuntimePackage.ABSTRACT_CORRESPONDENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_DOMAIN__TARGET = RuntimePackage.ABSTRACT_CORRESPONDENCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Node To Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_DOMAIN_FEATURE_COUNT = RuntimePackage.ABSTRACT_CORRESPONDENCE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Node To Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_DOMAIN_OPERATION_COUNT = RuntimePackage.ABSTRACT_CORRESPONDENCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.moflon.ide.visualization.dot.tgg.schema.impl.DirectedGraphToTripleGraphGrammarImpl <em>Directed Graph To Triple Graph Grammar</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.ide.visualization.dot.tgg.schema.impl.DirectedGraphToTripleGraphGrammarImpl
	 * @see org.moflon.ide.visualization.dot.tgg.schema.impl.SchemaPackageImpl#getDirectedGraphToTripleGraphGrammar()
	 * @generated
	 */
	int DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__SOURCE = RuntimePackage.ABSTRACT_CORRESPONDENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__TARGET = RuntimePackage.ABSTRACT_CORRESPONDENCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Directed Graph To Triple Graph Grammar</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR_FEATURE_COUNT = RuntimePackage.ABSTRACT_CORRESPONDENCE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Directed Graph To Triple Graph Grammar</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR_OPERATION_COUNT = RuntimePackage.ABSTRACT_CORRESPONDENCE_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the '{@link org.moflon.ide.visualization.dot.tgg.schema.impl.NodeToRuleImpl <em>Node To Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.moflon.ide.visualization.dot.tgg.schema.impl.NodeToRuleImpl
	 * @see org.moflon.ide.visualization.dot.tgg.schema.impl.SchemaPackageImpl#getNodeToRule()
	 * @generated
	 */
	int NODE_TO_RULE = 2;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_RULE__SOURCE = RuntimePackage.ABSTRACT_CORRESPONDENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_RULE__TARGET = RuntimePackage.ABSTRACT_CORRESPONDENCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Node To Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_RULE_FEATURE_COUNT = RuntimePackage.ABSTRACT_CORRESPONDENCE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Node To Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_TO_RULE_OPERATION_COUNT = RuntimePackage.ABSTRACT_CORRESPONDENCE_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.moflon.ide.visualization.dot.tgg.schema.NodeToDomain <em>Node To Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node To Domain</em>'.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.NodeToDomain
	 * @generated
	 */
	EClass getNodeToDomain();

	/**
	 * Returns the meta object for the reference '{@link org.moflon.ide.visualization.dot.tgg.schema.NodeToDomain#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.NodeToDomain#getSource()
	 * @see #getNodeToDomain()
	 * @generated
	 */
	EReference getNodeToDomain_Source();

	/**
	 * Returns the meta object for the reference '{@link org.moflon.ide.visualization.dot.tgg.schema.NodeToDomain#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.NodeToDomain#getTarget()
	 * @see #getNodeToDomain()
	 * @generated
	 */
	EReference getNodeToDomain_Target();

	/**
	 * Returns the meta object for class '{@link org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar <em>Directed Graph To Triple Graph Grammar</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Directed Graph To Triple Graph Grammar</em>'.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar
	 * @generated
	 */
	EClass getDirectedGraphToTripleGraphGrammar();

	/**
	 * Returns the meta object for the reference '{@link org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar#getSource()
	 * @see #getDirectedGraphToTripleGraphGrammar()
	 * @generated
	 */
	EReference getDirectedGraphToTripleGraphGrammar_Source();

	/**
	 * Returns the meta object for the reference '{@link org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar#getTarget()
	 * @see #getDirectedGraphToTripleGraphGrammar()
	 * @generated
	 */
	EReference getDirectedGraphToTripleGraphGrammar_Target();

	/**
	 * Returns the meta object for class '{@link org.moflon.ide.visualization.dot.tgg.schema.NodeToRule <em>Node To Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node To Rule</em>'.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.NodeToRule
	 * @generated
	 */
	EClass getNodeToRule();

	/**
	 * Returns the meta object for the reference '{@link org.moflon.ide.visualization.dot.tgg.schema.NodeToRule#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.NodeToRule#getSource()
	 * @see #getNodeToRule()
	 * @generated
	 */
	EReference getNodeToRule_Source();

	/**
	 * Returns the meta object for the reference '{@link org.moflon.ide.visualization.dot.tgg.schema.NodeToRule#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.moflon.ide.visualization.dot.tgg.schema.NodeToRule#getTarget()
	 * @see #getNodeToRule()
	 * @generated
	 */
	EReference getNodeToRule_Target();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SchemaFactory getSchemaFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.moflon.ide.visualization.dot.tgg.schema.impl.NodeToDomainImpl <em>Node To Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.ide.visualization.dot.tgg.schema.impl.NodeToDomainImpl
		 * @see org.moflon.ide.visualization.dot.tgg.schema.impl.SchemaPackageImpl#getNodeToDomain()
		 * @generated
		 */
		EClass NODE_TO_DOMAIN = eINSTANCE.getNodeToDomain();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_TO_DOMAIN__SOURCE = eINSTANCE.getNodeToDomain_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_TO_DOMAIN__TARGET = eINSTANCE.getNodeToDomain_Target();

		/**
		 * The meta object literal for the '{@link org.moflon.ide.visualization.dot.tgg.schema.impl.DirectedGraphToTripleGraphGrammarImpl <em>Directed Graph To Triple Graph Grammar</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.ide.visualization.dot.tgg.schema.impl.DirectedGraphToTripleGraphGrammarImpl
		 * @see org.moflon.ide.visualization.dot.tgg.schema.impl.SchemaPackageImpl#getDirectedGraphToTripleGraphGrammar()
		 * @generated
		 */
		EClass DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR = eINSTANCE.getDirectedGraphToTripleGraphGrammar();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__SOURCE = eINSTANCE
				.getDirectedGraphToTripleGraphGrammar_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__TARGET = eINSTANCE
				.getDirectedGraphToTripleGraphGrammar_Target();

		/**
		 * The meta object literal for the '{@link org.moflon.ide.visualization.dot.tgg.schema.impl.NodeToRuleImpl <em>Node To Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.moflon.ide.visualization.dot.tgg.schema.impl.NodeToRuleImpl
		 * @see org.moflon.ide.visualization.dot.tgg.schema.impl.SchemaPackageImpl#getNodeToRule()
		 * @generated
		 */
		EClass NODE_TO_RULE = eINSTANCE.getNodeToRule();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_TO_RULE__SOURCE = eINSTANCE.getNodeToRule_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_TO_RULE__TARGET = eINSTANCE.getNodeToRule_Target();

	}

} //SchemaPackage
