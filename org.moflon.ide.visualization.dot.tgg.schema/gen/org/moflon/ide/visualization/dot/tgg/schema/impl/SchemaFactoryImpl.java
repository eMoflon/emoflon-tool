/**
 */
package org.moflon.ide.visualization.dot.tgg.schema.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.moflon.ide.visualization.dot.tgg.schema.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SchemaFactoryImpl extends EFactoryImpl implements SchemaFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SchemaFactory init() {
		try {
			SchemaFactory theSchemaFactory = (SchemaFactory) EPackage.Registry.INSTANCE
					.getEFactory(SchemaPackage.eNS_URI);
			if (theSchemaFactory != null) {
				return theSchemaFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SchemaFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SchemaFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case SchemaPackage.NODE_TO_DOMAIN:
			return createNodeToDomain();
		case SchemaPackage.DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR:
			return createDirectedGraphToTripleGraphGrammar();
		case SchemaPackage.NODE_TO_RULE:
			return createNodeToRule();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeToDomain createNodeToDomain() {
		NodeToDomainImpl nodeToDomain = new NodeToDomainImpl();
		return nodeToDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DirectedGraphToTripleGraphGrammar createDirectedGraphToTripleGraphGrammar() {
		DirectedGraphToTripleGraphGrammarImpl directedGraphToTripleGraphGrammar = new DirectedGraphToTripleGraphGrammarImpl();
		return directedGraphToTripleGraphGrammar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeToRule createNodeToRule() {
		NodeToRuleImpl nodeToRule = new NodeToRuleImpl();
		return nodeToRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SchemaPackage getSchemaPackage() {
		return (SchemaPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SchemaPackage getPackage() {
		return SchemaPackage.eINSTANCE;
	}

} //SchemaFactoryImpl
