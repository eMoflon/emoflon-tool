/**
 */
package org.moflon.ide.visualization.dot.tgg.schema.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.moflon.ide.visualization.dot.language.LanguagePackage;

import org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar;
import org.moflon.ide.visualization.dot.tgg.schema.NodeToDomain;
import org.moflon.ide.visualization.dot.tgg.schema.NodeToRule;

import org.moflon.ide.visualization.dot.tgg.schema.Rules.RulesPackage;

import org.moflon.ide.visualization.dot.tgg.schema.Rules.impl.RulesPackageImpl;

import org.moflon.ide.visualization.dot.tgg.schema.SchemaFactory;
import org.moflon.ide.visualization.dot.tgg.schema.SchemaPackage;

import org.moflon.tgg.runtime.RuntimePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SchemaPackageImpl extends EPackageImpl implements SchemaPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeToDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass directedGraphToTripleGraphGrammarEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeToRuleEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.moflon.ide.visualization.dot.tgg.schema.SchemaPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SchemaPackageImpl() {
		super(eNS_URI, SchemaFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link SchemaPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SchemaPackage init() {
		if (isInited)
			return (SchemaPackage) EPackage.Registry.INSTANCE.getEPackage(SchemaPackage.eNS_URI);

		// Obtain or create and register package
		SchemaPackageImpl theSchemaPackage = (SchemaPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof SchemaPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new SchemaPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		LanguagePackage.eINSTANCE.eClass();
		org.moflon.tgg.language.LanguagePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		RulesPackageImpl theRulesPackage = (RulesPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(RulesPackage.eNS_URI) instanceof RulesPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI) : RulesPackage.eINSTANCE);

		// Create package meta-data objects
		theSchemaPackage.createPackageContents();
		theRulesPackage.createPackageContents();

		// Initialize created meta-data
		theSchemaPackage.initializePackageContents();
		theRulesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSchemaPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SchemaPackage.eNS_URI, theSchemaPackage);
		return theSchemaPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNodeToDomain() {
		return nodeToDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeToDomain_Source() {
		return (EReference) nodeToDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeToDomain_Target() {
		return (EReference) nodeToDomainEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDirectedGraphToTripleGraphGrammar() {
		return directedGraphToTripleGraphGrammarEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDirectedGraphToTripleGraphGrammar_Source() {
		return (EReference) directedGraphToTripleGraphGrammarEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDirectedGraphToTripleGraphGrammar_Target() {
		return (EReference) directedGraphToTripleGraphGrammarEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNodeToRule() {
		return nodeToRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeToRule_Source() {
		return (EReference) nodeToRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeToRule_Target() {
		return (EReference) nodeToRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SchemaFactory getSchemaFactory() {
		return (SchemaFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		nodeToDomainEClass = createEClass(NODE_TO_DOMAIN);
		createEReference(nodeToDomainEClass, NODE_TO_DOMAIN__SOURCE);
		createEReference(nodeToDomainEClass, NODE_TO_DOMAIN__TARGET);

		directedGraphToTripleGraphGrammarEClass = createEClass(DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR);
		createEReference(directedGraphToTripleGraphGrammarEClass, DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__SOURCE);
		createEReference(directedGraphToTripleGraphGrammarEClass, DIRECTED_GRAPH_TO_TRIPLE_GRAPH_GRAMMAR__TARGET);

		nodeToRuleEClass = createEClass(NODE_TO_RULE);
		createEReference(nodeToRuleEClass, NODE_TO_RULE__SOURCE);
		createEReference(nodeToRuleEClass, NODE_TO_RULE__TARGET);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		RulesPackage theRulesPackage = (RulesPackage) EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI);
		RuntimePackage theRuntimePackage = (RuntimePackage) EPackage.Registry.INSTANCE
				.getEPackage(RuntimePackage.eNS_URI);
		LanguagePackage theLanguagePackage = (LanguagePackage) EPackage.Registry.INSTANCE
				.getEPackage(LanguagePackage.eNS_URI);
		org.moflon.tgg.language.LanguagePackage theLanguagePackage_1 = (org.moflon.tgg.language.LanguagePackage) EPackage.Registry.INSTANCE
				.getEPackage(org.moflon.tgg.language.LanguagePackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theRulesPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		nodeToDomainEClass.getESuperTypes().add(theRuntimePackage.getAbstractCorrespondence());
		directedGraphToTripleGraphGrammarEClass.getESuperTypes().add(theRuntimePackage.getAbstractCorrespondence());
		nodeToRuleEClass.getESuperTypes().add(theRuntimePackage.getAbstractCorrespondence());

		// Initialize classes, features, and operations; add parameters
		initEClass(nodeToDomainEClass, NodeToDomain.class, "NodeToDomain", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodeToDomain_Source(), theLanguagePackage.getNodeCommand(), null, "source", null, 1, 1,
				NodeToDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeToDomain_Target(), theLanguagePackage_1.getDomain(), null, "target", null, 1, 1,
				NodeToDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(directedGraphToTripleGraphGrammarEClass, DirectedGraphToTripleGraphGrammar.class,
				"DirectedGraphToTripleGraphGrammar", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDirectedGraphToTripleGraphGrammar_Source(), theLanguagePackage.getDirectedGraph(), null,
				"source", null, 1, 1, DirectedGraphToTripleGraphGrammar.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDirectedGraphToTripleGraphGrammar_Target(), theLanguagePackage_1.getTripleGraphGrammar(),
				null, "target", null, 1, 1, DirectedGraphToTripleGraphGrammar.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nodeToRuleEClass, NodeToRule.class, "NodeToRule", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodeToRule_Source(), theLanguagePackage.getNodeCommand(), null, "source", null, 1, 1,
				NodeToRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeToRule_Target(), theLanguagePackage_1.getTGGRule(), null, "target", null, 1, 1,
				NodeToRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //SchemaPackageImpl
