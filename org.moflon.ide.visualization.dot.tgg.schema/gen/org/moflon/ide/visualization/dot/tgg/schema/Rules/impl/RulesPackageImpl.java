/**
 */
package org.moflon.ide.visualization.dot.tgg.schema.Rules.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.moflon.ide.visualization.dot.language.LanguagePackage;

import org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule;
import org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules;
import org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules;
import org.moflon.ide.visualization.dot.tgg.schema.Rules.RulesFactory;
import org.moflon.ide.visualization.dot.tgg.schema.Rules.RulesPackage;
import org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom;

import org.moflon.ide.visualization.dot.tgg.schema.SchemaPackage;

import org.moflon.ide.visualization.dot.tgg.schema.impl.SchemaPackageImpl;

import org.moflon.tgg.language.csp.CspPackage;

import org.moflon.tgg.language.modelgenerator.ModelgeneratorPackage;

import org.moflon.tgg.runtime.RuntimePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RulesPackageImpl extends EPackageImpl implements RulesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass refinmentRulesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fillBaseRulesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggGrammarDirectedGraphAxiomEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass complementRuleEClass = null;

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
	 * @see org.moflon.ide.visualization.dot.tgg.schema.Rules.RulesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RulesPackageImpl() {
		super(eNS_URI, RulesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link RulesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RulesPackage init() {
		if (isInited)
			return (RulesPackage) EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI);

		// Obtain or create and register package
		RulesPackageImpl theRulesPackage = (RulesPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof RulesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new RulesPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		LanguagePackage.eINSTANCE.eClass();
		org.moflon.tgg.language.LanguagePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		SchemaPackageImpl theSchemaPackage = (SchemaPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(SchemaPackage.eNS_URI) instanceof SchemaPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(SchemaPackage.eNS_URI) : SchemaPackage.eINSTANCE);

		// Create package meta-data objects
		theRulesPackage.createPackageContents();
		theSchemaPackage.createPackageContents();

		// Initialize created meta-data
		theRulesPackage.initializePackageContents();
		theSchemaPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRulesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RulesPackage.eNS_URI, theRulesPackage);
		return theRulesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRefinmentRules() {
		return refinmentRulesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsAppropriate_FWD__Match_Box_Box_Inheritance_DirectedGraph() {
		return refinmentRulesEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__Perform_FWD__IsApplicableMatch() {
		return refinmentRulesEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsApplicable_FWD__Match() {
		return refinmentRulesEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__RegisterObjectsToMatch_FWD__Match_Box_Box_Inheritance_DirectedGraph() {
		return refinmentRulesEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsAppropriate_solveCsp_FWD__Match_Box_Box_Inheritance_DirectedGraph() {
		return refinmentRulesEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsAppropriate_checkCsp_FWD__CSP() {
		return refinmentRulesEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsApplicable_solveCsp_FWD__IsApplicableMatch_Box_NodeToRule_Box_Inheritance_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_DirectedGraphToTripleGraphGrammar() {
		return refinmentRulesEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsApplicable_checkCsp_FWD__CSP() {
		return refinmentRulesEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject() {
		return refinmentRulesEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__CheckTypes_FWD__Match() {
		return refinmentRulesEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsAppropriate_BWD__Match_TGGRule_TGGRule_TripleGraphGrammar() {
		return refinmentRulesEClass.getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__Perform_BWD__IsApplicableMatch() {
		return refinmentRulesEClass.getEOperations().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsApplicable_BWD__Match() {
		return refinmentRulesEClass.getEOperations().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__RegisterObjectsToMatch_BWD__Match_TGGRule_TGGRule_TripleGraphGrammar() {
		return refinmentRulesEClass.getEOperations().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsAppropriate_solveCsp_BWD__Match_TGGRule_TGGRule_TripleGraphGrammar() {
		return refinmentRulesEClass.getEOperations().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsAppropriate_checkCsp_BWD__CSP() {
		return refinmentRulesEClass.getEOperations().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsApplicable_solveCsp_BWD__IsApplicableMatch_Box_NodeToRule_Box_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_DirectedGraphToTripleGraphGrammar() {
		return refinmentRulesEClass.getEOperations().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsApplicable_checkCsp_BWD__CSP() {
		return refinmentRulesEClass.getEOperations().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject() {
		return refinmentRulesEClass.getEOperations().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__CheckTypes_BWD__Match() {
		return refinmentRulesEClass.getEOperations().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsAppropriate_FWD_EMoflonEdge_57__EMoflonEdge() {
		return refinmentRulesEClass.getEOperations().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsAppropriate_BWD_EMoflonEdge_82__EMoflonEdge() {
		return refinmentRulesEClass.getEOperations().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__CheckAttributes_FWD__TripleMatch() {
		return refinmentRulesEClass.getEOperations().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__CheckAttributes_BWD__TripleMatch() {
		return refinmentRulesEClass.getEOperations().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsApplicable_CC__Match_Match() {
		return refinmentRulesEClass.getEOperations().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsApplicable_solveCsp_CC__Box_Box_Inheritance_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_Match_Match() {
		return refinmentRulesEClass.getEOperations().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__IsApplicable_checkCsp_CC__CSP() {
		return refinmentRulesEClass.getEOperations().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__CheckDEC_FWD__Box_Box_Inheritance_DirectedGraph() {
		return refinmentRulesEClass.getEOperations().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__CheckDEC_BWD__TGGRule_TGGRule_TripleGraphGrammar() {
		return refinmentRulesEClass.getEOperations().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__GenerateModel__RuleEntryContainer_NodeToRule() {
		return refinmentRulesEClass.getEOperations().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__GenerateModel_solveCsp_BWD__IsApplicableMatch_Box_NodeToRule_Box_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_DirectedGraphToTripleGraphGrammar_ModelgeneratorRuleResult() {
		return refinmentRulesEClass.getEOperations().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRefinmentRules__GenerateModel_checkCsp_BWD__CSP() {
		return refinmentRulesEClass.getEOperations().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFillBaseRules() {
		return fillBaseRulesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsAppropriate_FWD__Match_DirectedGraph_Box() {
		return fillBaseRulesEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__Perform_FWD__IsApplicableMatch() {
		return fillBaseRulesEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsApplicable_FWD__Match() {
		return fillBaseRulesEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__RegisterObjectsToMatch_FWD__Match_DirectedGraph_Box() {
		return fillBaseRulesEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsAppropriate_solveCsp_FWD__Match_DirectedGraph_Box() {
		return fillBaseRulesEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsAppropriate_checkCsp_FWD__CSP() {
		return fillBaseRulesEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsApplicable_solveCsp_FWD__IsApplicableMatch_DirectedGraphToTripleGraphGrammar_DirectedGraph_Box_TripleGraphGrammar() {
		return fillBaseRulesEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsApplicable_checkCsp_FWD__CSP() {
		return fillBaseRulesEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject() {
		return fillBaseRulesEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__CheckTypes_FWD__Match() {
		return fillBaseRulesEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsAppropriate_BWD__Match_TGGRule_TripleGraphGrammar() {
		return fillBaseRulesEClass.getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__Perform_BWD__IsApplicableMatch() {
		return fillBaseRulesEClass.getEOperations().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsApplicable_BWD__Match() {
		return fillBaseRulesEClass.getEOperations().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__RegisterObjectsToMatch_BWD__Match_TGGRule_TripleGraphGrammar() {
		return fillBaseRulesEClass.getEOperations().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsAppropriate_solveCsp_BWD__Match_TGGRule_TripleGraphGrammar() {
		return fillBaseRulesEClass.getEOperations().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsAppropriate_checkCsp_BWD__CSP() {
		return fillBaseRulesEClass.getEOperations().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsApplicable_solveCsp_BWD__IsApplicableMatch_DirectedGraphToTripleGraphGrammar_TGGRule_DirectedGraph_TripleGraphGrammar() {
		return fillBaseRulesEClass.getEOperations().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsApplicable_checkCsp_BWD__CSP() {
		return fillBaseRulesEClass.getEOperations().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject() {
		return fillBaseRulesEClass.getEOperations().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__CheckTypes_BWD__Match() {
		return fillBaseRulesEClass.getEOperations().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsAppropriate_FWD_EMoflonEdge_58__EMoflonEdge() {
		return fillBaseRulesEClass.getEOperations().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsAppropriate_BWD_EMoflonEdge_83__EMoflonEdge() {
		return fillBaseRulesEClass.getEOperations().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__CheckAttributes_FWD__TripleMatch() {
		return fillBaseRulesEClass.getEOperations().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__CheckAttributes_BWD__TripleMatch() {
		return fillBaseRulesEClass.getEOperations().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsApplicable_CC__Match_Match() {
		return fillBaseRulesEClass.getEOperations().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsApplicable_solveCsp_CC__TGGRule_DirectedGraph_Box_TripleGraphGrammar_Match_Match() {
		return fillBaseRulesEClass.getEOperations().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__IsApplicable_checkCsp_CC__CSP() {
		return fillBaseRulesEClass.getEOperations().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__CheckDEC_FWD__DirectedGraph_Box() {
		return fillBaseRulesEClass.getEOperations().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__CheckDEC_BWD__TGGRule_TripleGraphGrammar() {
		return fillBaseRulesEClass.getEOperations().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__GenerateModel__RuleEntryContainer_DirectedGraphToTripleGraphGrammar() {
		return fillBaseRulesEClass.getEOperations().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__GenerateModel_solveCsp_BWD__IsApplicableMatch_DirectedGraphToTripleGraphGrammar_DirectedGraph_TripleGraphGrammar_ModelgeneratorRuleResult() {
		return fillBaseRulesEClass.getEOperations().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFillBaseRules__GenerateModel_checkCsp_BWD__CSP() {
		return fillBaseRulesEClass.getEOperations().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGGrammarDirectedGraphAxiom() {
		return tggGrammarDirectedGraphAxiomEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_FWD__Match_DirectedGraph() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__Perform_FWD__IsApplicableMatch() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_FWD__Match() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__RegisterObjectsToMatch_FWD__Match_DirectedGraph() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_solveCsp_FWD__Match_DirectedGraph() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_checkCsp_FWD__CSP() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_solveCsp_FWD__IsApplicableMatch_DirectedGraph() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_checkCsp_FWD__CSP() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__CheckTypes_FWD__Match() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_BWD__Match_TripleGraphGrammar() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__Perform_BWD__IsApplicableMatch() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_BWD__Match() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__RegisterObjectsToMatch_BWD__Match_TripleGraphGrammar() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_solveCsp_BWD__Match_TripleGraphGrammar() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_checkCsp_BWD__CSP() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_solveCsp_BWD__IsApplicableMatch_TripleGraphGrammar() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_checkCsp_BWD__CSP() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__CheckTypes_BWD__Match() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_FWD_DirectedGraph_0__DirectedGraph() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsAppropriate_BWD_TripleGraphGrammar_0__TripleGraphGrammar() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__CheckAttributes_FWD__TripleMatch() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__CheckAttributes_BWD__TripleMatch() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_CC__Match_Match() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_solveCsp_CC__DirectedGraph_TripleGraphGrammar_Match_Match() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__IsApplicable_checkCsp_CC__CSP() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__CheckDEC_FWD__DirectedGraph() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__CheckDEC_BWD__TripleGraphGrammar() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__GenerateModel__RuleEntryContainer() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__GenerateModel_solveCsp_BWD__IsApplicableMatch_ModelgeneratorRuleResult() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTGGGrammarDirectedGraphAxiom__GenerateModel_checkCsp_BWD__CSP() {
		return tggGrammarDirectedGraphAxiomEClass.getEOperations().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComplementRule() {
		return complementRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsAppropriate_FWD__Match_DirectedGraph_Box_Composite_Box() {
		return complementRuleEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__Perform_FWD__IsApplicableMatch() {
		return complementRuleEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsApplicable_FWD__Match() {
		return complementRuleEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__RegisterObjectsToMatch_FWD__Match_DirectedGraph_Box_Composite_Box() {
		return complementRuleEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsAppropriate_solveCsp_FWD__Match_DirectedGraph_Box_Composite_Box() {
		return complementRuleEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsAppropriate_checkCsp_FWD__CSP() {
		return complementRuleEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsApplicable_solveCsp_FWD__IsApplicableMatch_TGGRule_TripleGraphGrammar_DirectedGraph_Box_NodeToRule_Composite_TGGRule_DirectedGraphToTripleGraphGrammar_Box() {
		return complementRuleEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsApplicable_checkCsp_FWD__CSP() {
		return complementRuleEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject() {
		return complementRuleEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__CheckTypes_FWD__Match() {
		return complementRuleEClass.getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsAppropriate_BWD__Match_TGGRule_TripleGraphGrammar_TGGRule() {
		return complementRuleEClass.getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__Perform_BWD__IsApplicableMatch() {
		return complementRuleEClass.getEOperations().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsApplicable_BWD__Match() {
		return complementRuleEClass.getEOperations().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__RegisterObjectsToMatch_BWD__Match_TGGRule_TripleGraphGrammar_TGGRule() {
		return complementRuleEClass.getEOperations().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsAppropriate_solveCsp_BWD__Match_TGGRule_TripleGraphGrammar_TGGRule() {
		return complementRuleEClass.getEOperations().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsAppropriate_checkCsp_BWD__CSP() {
		return complementRuleEClass.getEOperations().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsApplicable_solveCsp_BWD__IsApplicableMatch_TGGRule_TripleGraphGrammar_DirectedGraph_Box_NodeToRule_TGGRule_DirectedGraphToTripleGraphGrammar_Box() {
		return complementRuleEClass.getEOperations().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsApplicable_checkCsp_BWD__CSP() {
		return complementRuleEClass.getEOperations().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject() {
		return complementRuleEClass.getEOperations().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__CheckTypes_BWD__Match() {
		return complementRuleEClass.getEOperations().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsAppropriate_FWD_EMoflonEdge_59__EMoflonEdge() {
		return complementRuleEClass.getEOperations().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsAppropriate_BWD_EMoflonEdge_84__EMoflonEdge() {
		return complementRuleEClass.getEOperations().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__CheckAttributes_FWD__TripleMatch() {
		return complementRuleEClass.getEOperations().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__CheckAttributes_BWD__TripleMatch() {
		return complementRuleEClass.getEOperations().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsApplicable_CC__Match_Match() {
		return complementRuleEClass.getEOperations().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsApplicable_solveCsp_CC__TGGRule_TripleGraphGrammar_DirectedGraph_Box_Composite_TGGRule_Box_Match_Match() {
		return complementRuleEClass.getEOperations().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__IsApplicable_checkCsp_CC__CSP() {
		return complementRuleEClass.getEOperations().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__CheckDEC_FWD__DirectedGraph_Box_Composite_Box() {
		return complementRuleEClass.getEOperations().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__CheckDEC_BWD__TGGRule_TripleGraphGrammar_TGGRule() {
		return complementRuleEClass.getEOperations().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__GenerateModel__RuleEntryContainer_NodeToRule() {
		return complementRuleEClass.getEOperations().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__GenerateModel_solveCsp_BWD__IsApplicableMatch_TGGRule_TripleGraphGrammar_DirectedGraph_Box_NodeToRule_TGGRule_DirectedGraphToTripleGraphGrammar_Box_ModelgeneratorRuleResult() {
		return complementRuleEClass.getEOperations().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getComplementRule__GenerateModel_checkCsp_BWD__CSP() {
		return complementRuleEClass.getEOperations().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesFactory getRulesFactory() {
		return (RulesFactory) getEFactoryInstance();
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
		refinmentRulesEClass = createEClass(REFINMENT_RULES);
		createEOperation(refinmentRulesEClass,
				REFINMENT_RULES___IS_APPROPRIATE_FWD__MATCH_BOX_BOX_INHERITANCE_DIRECTEDGRAPH);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___PERFORM_FWD__ISAPPLICABLEMATCH);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___IS_APPLICABLE_FWD__MATCH);
		createEOperation(refinmentRulesEClass,
				REFINMENT_RULES___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_BOX_BOX_INHERITANCE_DIRECTEDGRAPH);
		createEOperation(refinmentRulesEClass,
				REFINMENT_RULES___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_BOX_BOX_INHERITANCE_DIRECTEDGRAPH);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___IS_APPROPRIATE_CHECK_CSP_FWD__CSP);
		createEOperation(refinmentRulesEClass,
				REFINMENT_RULES___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_BOX_NODETORULE_BOX_INHERITANCE_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___IS_APPLICABLE_CHECK_CSP_FWD__CSP);
		createEOperation(refinmentRulesEClass,
				REFINMENT_RULES___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___CHECK_TYPES_FWD__MATCH);
		createEOperation(refinmentRulesEClass,
				REFINMENT_RULES___IS_APPROPRIATE_BWD__MATCH_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___PERFORM_BWD__ISAPPLICABLEMATCH);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___IS_APPLICABLE_BWD__MATCH);
		createEOperation(refinmentRulesEClass,
				REFINMENT_RULES___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR);
		createEOperation(refinmentRulesEClass,
				REFINMENT_RULES___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___IS_APPROPRIATE_CHECK_CSP_BWD__CSP);
		createEOperation(refinmentRulesEClass,
				REFINMENT_RULES___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_BOX_NODETORULE_BOX_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___IS_APPLICABLE_CHECK_CSP_BWD__CSP);
		createEOperation(refinmentRulesEClass,
				REFINMENT_RULES___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___CHECK_TYPES_BWD__MATCH);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___IS_APPROPRIATE_FWD_EMOFLON_EDGE_57__EMOFLONEDGE);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___IS_APPROPRIATE_BWD_EMOFLON_EDGE_82__EMOFLONEDGE);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___IS_APPLICABLE_CC__MATCH_MATCH);
		createEOperation(refinmentRulesEClass,
				REFINMENT_RULES___IS_APPLICABLE_SOLVE_CSP_CC__BOX_BOX_INHERITANCE_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_MATCH_MATCH);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___IS_APPLICABLE_CHECK_CSP_CC__CSP);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___CHECK_DEC_FWD__BOX_BOX_INHERITANCE_DIRECTEDGRAPH);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___CHECK_DEC_BWD__TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___GENERATE_MODEL__RULEENTRYCONTAINER_NODETORULE);
		createEOperation(refinmentRulesEClass,
				REFINMENT_RULES___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_BOX_NODETORULE_BOX_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_MODELGENERATORRULERESULT);
		createEOperation(refinmentRulesEClass, REFINMENT_RULES___GENERATE_MODEL_CHECK_CSP_BWD__CSP);

		fillBaseRulesEClass = createEClass(FILL_BASE_RULES);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___IS_APPROPRIATE_FWD__MATCH_DIRECTEDGRAPH_BOX);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___PERFORM_FWD__ISAPPLICABLEMATCH);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___IS_APPLICABLE_FWD__MATCH);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_DIRECTEDGRAPH_BOX);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_DIRECTEDGRAPH_BOX);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___IS_APPROPRIATE_CHECK_CSP_FWD__CSP);
		createEOperation(fillBaseRulesEClass,
				FILL_BASE_RULES___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_TRIPLEGRAPHGRAMMAR);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___IS_APPLICABLE_CHECK_CSP_FWD__CSP);
		createEOperation(fillBaseRulesEClass,
				FILL_BASE_RULES___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___CHECK_TYPES_FWD__MATCH);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___IS_APPROPRIATE_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___PERFORM_BWD__ISAPPLICABLEMATCH);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___IS_APPLICABLE_BWD__MATCH);
		createEOperation(fillBaseRulesEClass,
				FILL_BASE_RULES___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR);
		createEOperation(fillBaseRulesEClass,
				FILL_BASE_RULES___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___IS_APPROPRIATE_CHECK_CSP_BWD__CSP);
		createEOperation(fillBaseRulesEClass,
				FILL_BASE_RULES___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_TGGRULE_DIRECTEDGRAPH_TRIPLEGRAPHGRAMMAR);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___IS_APPLICABLE_CHECK_CSP_BWD__CSP);
		createEOperation(fillBaseRulesEClass,
				FILL_BASE_RULES___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___CHECK_TYPES_BWD__MATCH);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___IS_APPROPRIATE_FWD_EMOFLON_EDGE_58__EMOFLONEDGE);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___IS_APPROPRIATE_BWD_EMOFLON_EDGE_83__EMOFLONEDGE);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___IS_APPLICABLE_CC__MATCH_MATCH);
		createEOperation(fillBaseRulesEClass,
				FILL_BASE_RULES___IS_APPLICABLE_SOLVE_CSP_CC__TGGRULE_DIRECTEDGRAPH_BOX_TRIPLEGRAPHGRAMMAR_MATCH_MATCH);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___IS_APPLICABLE_CHECK_CSP_CC__CSP);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___CHECK_DEC_FWD__DIRECTEDGRAPH_BOX);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___CHECK_DEC_BWD__TGGRULE_TRIPLEGRAPHGRAMMAR);
		createEOperation(fillBaseRulesEClass,
				FILL_BASE_RULES___GENERATE_MODEL__RULEENTRYCONTAINER_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR);
		createEOperation(fillBaseRulesEClass,
				FILL_BASE_RULES___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_TRIPLEGRAPHGRAMMAR_MODELGENERATORRULERESULT);
		createEOperation(fillBaseRulesEClass, FILL_BASE_RULES___GENERATE_MODEL_CHECK_CSP_BWD__CSP);

		tggGrammarDirectedGraphAxiomEClass = createEClass(TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_FWD__MATCH_DIRECTEDGRAPH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___PERFORM_FWD__ISAPPLICABLEMATCH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_FWD__MATCH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_DIRECTEDGRAPH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_DIRECTEDGRAPH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_CHECK_CSP_FWD__CSP);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_DIRECTEDGRAPH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CHECK_CSP_FWD__CSP);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT);
		createEOperation(tggGrammarDirectedGraphAxiomEClass, TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_TYPES_FWD__MATCH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_BWD__MATCH_TRIPLEGRAPHGRAMMAR);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___PERFORM_BWD__ISAPPLICABLEMATCH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_BWD__MATCH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TRIPLEGRAPHGRAMMAR);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TRIPLEGRAPHGRAMMAR);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_CHECK_CSP_BWD__CSP);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_TRIPLEGRAPHGRAMMAR);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CHECK_CSP_BWD__CSP);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT);
		createEOperation(tggGrammarDirectedGraphAxiomEClass, TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_TYPES_BWD__MATCH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_FWD_DIRECTED_GRAPH_0__DIRECTEDGRAPH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_BWD_TRIPLE_GRAPH_GRAMMAR_0__TRIPLEGRAPHGRAMMAR);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CC__MATCH_MATCH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_SOLVE_CSP_CC__DIRECTEDGRAPH_TRIPLEGRAPHGRAMMAR_MATCH_MATCH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CHECK_CSP_CC__CSP);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_DEC_FWD__DIRECTEDGRAPH);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_DEC_BWD__TRIPLEGRAPHGRAMMAR);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___GENERATE_MODEL__RULEENTRYCONTAINER);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_MODELGENERATORRULERESULT);
		createEOperation(tggGrammarDirectedGraphAxiomEClass,
				TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___GENERATE_MODEL_CHECK_CSP_BWD__CSP);

		complementRuleEClass = createEClass(COMPLEMENT_RULE);
		createEOperation(complementRuleEClass,
				COMPLEMENT_RULE___IS_APPROPRIATE_FWD__MATCH_DIRECTEDGRAPH_BOX_COMPOSITE_BOX);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___PERFORM_FWD__ISAPPLICABLEMATCH);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___IS_APPLICABLE_FWD__MATCH);
		createEOperation(complementRuleEClass,
				COMPLEMENT_RULE___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_DIRECTEDGRAPH_BOX_COMPOSITE_BOX);
		createEOperation(complementRuleEClass,
				COMPLEMENT_RULE___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_DIRECTEDGRAPH_BOX_COMPOSITE_BOX);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___IS_APPROPRIATE_CHECK_CSP_FWD__CSP);
		createEOperation(complementRuleEClass,
				COMPLEMENT_RULE___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_NODETORULE_COMPOSITE_TGGRULE_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_BOX);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___IS_APPLICABLE_CHECK_CSP_FWD__CSP);
		createEOperation(complementRuleEClass,
				COMPLEMENT_RULE___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___CHECK_TYPES_FWD__MATCH);
		createEOperation(complementRuleEClass,
				COMPLEMENT_RULE___IS_APPROPRIATE_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___PERFORM_BWD__ISAPPLICABLEMATCH);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___IS_APPLICABLE_BWD__MATCH);
		createEOperation(complementRuleEClass,
				COMPLEMENT_RULE___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE);
		createEOperation(complementRuleEClass,
				COMPLEMENT_RULE___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___IS_APPROPRIATE_CHECK_CSP_BWD__CSP);
		createEOperation(complementRuleEClass,
				COMPLEMENT_RULE___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_NODETORULE_TGGRULE_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_BOX);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___IS_APPLICABLE_CHECK_CSP_BWD__CSP);
		createEOperation(complementRuleEClass,
				COMPLEMENT_RULE___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___CHECK_TYPES_BWD__MATCH);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___IS_APPROPRIATE_FWD_EMOFLON_EDGE_59__EMOFLONEDGE);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___IS_APPROPRIATE_BWD_EMOFLON_EDGE_84__EMOFLONEDGE);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___IS_APPLICABLE_CC__MATCH_MATCH);
		createEOperation(complementRuleEClass,
				COMPLEMENT_RULE___IS_APPLICABLE_SOLVE_CSP_CC__TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_COMPOSITE_TGGRULE_BOX_MATCH_MATCH);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___IS_APPLICABLE_CHECK_CSP_CC__CSP);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___CHECK_DEC_FWD__DIRECTEDGRAPH_BOX_COMPOSITE_BOX);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___CHECK_DEC_BWD__TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___GENERATE_MODEL__RULEENTRYCONTAINER_NODETORULE);
		createEOperation(complementRuleEClass,
				COMPLEMENT_RULE___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_NODETORULE_TGGRULE_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_BOX_MODELGENERATORRULERESULT);
		createEOperation(complementRuleEClass, COMPLEMENT_RULE___GENERATE_MODEL_CHECK_CSP_BWD__CSP);
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
		RuntimePackage theRuntimePackage = (RuntimePackage) EPackage.Registry.INSTANCE
				.getEPackage(RuntimePackage.eNS_URI);
		LanguagePackage theLanguagePackage = (LanguagePackage) EPackage.Registry.INSTANCE
				.getEPackage(LanguagePackage.eNS_URI);
		CspPackage theCspPackage = (CspPackage) EPackage.Registry.INSTANCE.getEPackage(CspPackage.eNS_URI);
		SchemaPackage theSchemaPackage = (SchemaPackage) EPackage.Registry.INSTANCE.getEPackage(SchemaPackage.eNS_URI);
		org.moflon.tgg.language.LanguagePackage theLanguagePackage_1 = (org.moflon.tgg.language.LanguagePackage) EPackage.Registry.INSTANCE
				.getEPackage(org.moflon.tgg.language.LanguagePackage.eNS_URI);
		ModelgeneratorPackage theModelgeneratorPackage = (ModelgeneratorPackage) EPackage.Registry.INSTANCE
				.getEPackage(ModelgeneratorPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		refinmentRulesEClass.getESuperTypes().add(theRuntimePackage.getAbstractRule());
		fillBaseRulesEClass.getESuperTypes().add(theRuntimePackage.getAbstractRule());
		tggGrammarDirectedGraphAxiomEClass.getESuperTypes().add(theRuntimePackage.getAbstractRule());
		complementRuleEClass.getESuperTypes().add(theRuntimePackage.getAbstractRule());

		// Initialize classes, features, and operations; add parameters
		initEClass(refinmentRulesEClass, RefinmentRules.class, "RefinmentRules", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		EOperation op = initEOperation(getRefinmentRules__IsAppropriate_FWD__Match_Box_Box_Inheritance_DirectedGraph(),
				ecorePackage.getEBoolean(), "isAppropriate_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getInheritance(), "refined", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__Perform_FWD__IsApplicableMatch(),
				theRuntimePackage.getPerformRuleResult(), "perform_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__IsApplicable_FWD__Match(), theRuntimePackage.getIsApplicableRuleResult(),
				"isApplicable_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__RegisterObjectsToMatch_FWD__Match_Box_Box_Inheritance_DirectedGraph(),
				null, "registerObjectsToMatch_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getInheritance(), "refined", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__IsAppropriate_solveCsp_FWD__Match_Box_Box_Inheritance_DirectedGraph(),
				theCspPackage.getCSP(), "isAppropriate_solveCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getInheritance(), "refined", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__IsAppropriate_checkCsp_FWD__CSP(), ecorePackage.getEBoolean(),
				"isAppropriate_checkCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getRefinmentRules__IsApplicable_solveCsp_FWD__IsApplicableMatch_Box_NodeToRule_Box_Inheritance_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_DirectedGraphToTripleGraphGrammar(),
				theCspPackage.getCSP(), "isApplicable_solveCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getNodeToRule(), "nodeToRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getInheritance(), "refined", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getDirectedGraphToTripleGraphGrammar(), "directedGraphToTripleGraphGrammar",
				0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__IsApplicable_checkCsp_FWD__CSP(), ecorePackage.getEBoolean(),
				"isApplicable_checkCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getRefinmentRules__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject(),
				null, "registerObjects_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getPerformRuleResult(), "ruleresult", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "nodeToRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "refined", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "tripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraphToTripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__CheckTypes_FWD__Match(), ecorePackage.getEBoolean(), "checkTypes_FWD", 0,
				1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__IsAppropriate_BWD__Match_TGGRule_TGGRule_TripleGraphGrammar(),
				ecorePackage.getEBoolean(), "isAppropriate_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getRefinmentRules__Perform_BWD__IsApplicableMatch(),
				theRuntimePackage.getPerformRuleResult(), "perform_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__IsApplicable_BWD__Match(), theRuntimePackage.getIsApplicableRuleResult(),
				"isApplicable_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__RegisterObjectsToMatch_BWD__Match_TGGRule_TGGRule_TripleGraphGrammar(),
				null, "registerObjectsToMatch_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getRefinmentRules__IsAppropriate_solveCsp_BWD__Match_TGGRule_TGGRule_TripleGraphGrammar(),
				theCspPackage.getCSP(), "isAppropriate_solveCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getRefinmentRules__IsAppropriate_checkCsp_BWD__CSP(), ecorePackage.getEBoolean(),
				"isAppropriate_checkCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getRefinmentRules__IsApplicable_solveCsp_BWD__IsApplicableMatch_Box_NodeToRule_Box_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_DirectedGraphToTripleGraphGrammar(),
				theCspPackage.getCSP(), "isApplicable_solveCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getNodeToRule(), "nodeToRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getDirectedGraphToTripleGraphGrammar(), "directedGraphToTripleGraphGrammar",
				0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__IsApplicable_checkCsp_BWD__CSP(), ecorePackage.getEBoolean(),
				"isApplicable_checkCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getRefinmentRules__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject(),
				null, "registerObjects_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getPerformRuleResult(), "ruleresult", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "nodeToRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "refined", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "tripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraphToTripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__CheckTypes_BWD__Match(), ecorePackage.getEBoolean(), "checkTypes_BWD", 0,
				1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__IsAppropriate_FWD_EMoflonEdge_57__EMoflonEdge(),
				theRuntimePackage.getEObjectContainer(), "isAppropriate_FWD_EMoflonEdge_57", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getEMoflonEdge(), "_edge_source", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__IsAppropriate_BWD_EMoflonEdge_82__EMoflonEdge(),
				theRuntimePackage.getEObjectContainer(), "isAppropriate_BWD_EMoflonEdge_82", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getEMoflonEdge(), "_edge_refines", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__CheckAttributes_FWD__TripleMatch(),
				theRuntimePackage.getAttributeConstraintsRuleResult(), "checkAttributes_FWD", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getTripleMatch(), "__tripleMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__CheckAttributes_BWD__TripleMatch(),
				theRuntimePackage.getAttributeConstraintsRuleResult(), "checkAttributes_BWD", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getTripleMatch(), "__tripleMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__IsApplicable_CC__Match_Match(),
				theRuntimePackage.getIsApplicableRuleResult(), "isApplicable_CC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "sourceMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "targetMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getRefinmentRules__IsApplicable_solveCsp_CC__Box_Box_Inheritance_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_Match_Match(),
				theCspPackage.getCSP(), "isApplicable_solveCsp_CC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getInheritance(), "refined", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "sourceMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "targetMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__IsApplicable_checkCsp_CC__CSP(), ecorePackage.getEBoolean(),
				"isApplicable_checkCsp_CC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__CheckDEC_FWD__Box_Box_Inheritance_DirectedGraph(),
				ecorePackage.getEBoolean(), "checkDEC_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getInheritance(), "refined", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__CheckDEC_BWD__TGGRule_TGGRule_TripleGraphGrammar(),
				ecorePackage.getEBoolean(), "checkDEC_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getRefinmentRules__GenerateModel__RuleEntryContainer_NodeToRule(),
				theRuntimePackage.getModelgeneratorRuleResult(), "generateModel", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theModelgeneratorPackage.getRuleEntryContainer(), "ruleEntryContainer", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theSchemaPackage.getNodeToRule(), "nodeToRuleParameter", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getRefinmentRules__GenerateModel_solveCsp_BWD__IsApplicableMatch_Box_NodeToRule_Box_TGGRule_TGGRule_TripleGraphGrammar_DirectedGraph_DirectedGraphToTripleGraphGrammar_ModelgeneratorRuleResult(),
				theCspPackage.getCSP(), "generateModel_solveCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getNodeToRule(), "nodeToRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getDirectedGraphToTripleGraphGrammar(), "directedGraphToTripleGraphGrammar",
				0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getModelgeneratorRuleResult(), "ruleResult", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRefinmentRules__GenerateModel_checkCsp_BWD__CSP(), ecorePackage.getEBoolean(),
				"generateModel_checkCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(fillBaseRulesEClass, FillBaseRules.class, "FillBaseRules", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		op = initEOperation(getFillBaseRules__IsAppropriate_FWD__Match_DirectedGraph_Box(), ecorePackage.getEBoolean(),
				"isAppropriate_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__Perform_FWD__IsApplicableMatch(),
				theRuntimePackage.getPerformRuleResult(), "perform_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__IsApplicable_FWD__Match(), theRuntimePackage.getIsApplicableRuleResult(),
				"isApplicable_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__RegisterObjectsToMatch_FWD__Match_DirectedGraph_Box(), null,
				"registerObjectsToMatch_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__IsAppropriate_solveCsp_FWD__Match_DirectedGraph_Box(),
				theCspPackage.getCSP(), "isAppropriate_solveCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__IsAppropriate_checkCsp_FWD__CSP(), ecorePackage.getEBoolean(),
				"isAppropriate_checkCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getFillBaseRules__IsApplicable_solveCsp_FWD__IsApplicableMatch_DirectedGraphToTripleGraphGrammar_DirectedGraph_Box_TripleGraphGrammar(),
				theCspPackage.getCSP(), "isApplicable_solveCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getDirectedGraphToTripleGraphGrammar(), "directedGraphToTripleGraphGrammar",
				0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getFillBaseRules__IsApplicable_checkCsp_FWD__CSP(), ecorePackage.getEBoolean(),
				"isApplicable_checkCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getFillBaseRules__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject(),
				null, "registerObjects_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getPerformRuleResult(), "ruleresult", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "nodeToRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraphToTripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "tripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__CheckTypes_FWD__Match(), ecorePackage.getEBoolean(), "checkTypes_FWD", 0,
				1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__IsAppropriate_BWD__Match_TGGRule_TripleGraphGrammar(),
				ecorePackage.getEBoolean(), "isAppropriate_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getFillBaseRules__Perform_BWD__IsApplicableMatch(),
				theRuntimePackage.getPerformRuleResult(), "perform_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__IsApplicable_BWD__Match(), theRuntimePackage.getIsApplicableRuleResult(),
				"isApplicable_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__RegisterObjectsToMatch_BWD__Match_TGGRule_TripleGraphGrammar(), null,
				"registerObjectsToMatch_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getFillBaseRules__IsAppropriate_solveCsp_BWD__Match_TGGRule_TripleGraphGrammar(),
				theCspPackage.getCSP(), "isAppropriate_solveCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getFillBaseRules__IsAppropriate_checkCsp_BWD__CSP(), ecorePackage.getEBoolean(),
				"isAppropriate_checkCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getFillBaseRules__IsApplicable_solveCsp_BWD__IsApplicableMatch_DirectedGraphToTripleGraphGrammar_TGGRule_DirectedGraph_TripleGraphGrammar(),
				theCspPackage.getCSP(), "isApplicable_solveCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getDirectedGraphToTripleGraphGrammar(), "directedGraphToTripleGraphGrammar",
				0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getFillBaseRules__IsApplicable_checkCsp_BWD__CSP(), ecorePackage.getEBoolean(),
				"isApplicable_checkCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getFillBaseRules__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject(),
				null, "registerObjects_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getPerformRuleResult(), "ruleresult", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "nodeToRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraphToTripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "tripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__CheckTypes_BWD__Match(), ecorePackage.getEBoolean(), "checkTypes_BWD", 0,
				1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__IsAppropriate_FWD_EMoflonEdge_58__EMoflonEdge(),
				theRuntimePackage.getEObjectContainer(), "isAppropriate_FWD_EMoflonEdge_58", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getEMoflonEdge(), "_edge_nodes", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__IsAppropriate_BWD_EMoflonEdge_83__EMoflonEdge(),
				theRuntimePackage.getEObjectContainer(), "isAppropriate_BWD_EMoflonEdge_83", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getEMoflonEdge(), "_edge_tggRule", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__CheckAttributes_FWD__TripleMatch(),
				theRuntimePackage.getAttributeConstraintsRuleResult(), "checkAttributes_FWD", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getTripleMatch(), "__tripleMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__CheckAttributes_BWD__TripleMatch(),
				theRuntimePackage.getAttributeConstraintsRuleResult(), "checkAttributes_BWD", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getTripleMatch(), "__tripleMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__IsApplicable_CC__Match_Match(),
				theRuntimePackage.getIsApplicableRuleResult(), "isApplicable_CC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "sourceMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "targetMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getFillBaseRules__IsApplicable_solveCsp_CC__TGGRule_DirectedGraph_Box_TripleGraphGrammar_Match_Match(),
				theCspPackage.getCSP(), "isApplicable_solveCsp_CC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "sourceMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "targetMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__IsApplicable_checkCsp_CC__CSP(), ecorePackage.getEBoolean(),
				"isApplicable_checkCsp_CC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__CheckDEC_FWD__DirectedGraph_Box(), ecorePackage.getEBoolean(),
				"checkDEC_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__CheckDEC_BWD__TGGRule_TripleGraphGrammar(), ecorePackage.getEBoolean(),
				"checkDEC_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getFillBaseRules__GenerateModel__RuleEntryContainer_DirectedGraphToTripleGraphGrammar(),
				theRuntimePackage.getModelgeneratorRuleResult(), "generateModel", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theModelgeneratorPackage.getRuleEntryContainer(), "ruleEntryContainer", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theSchemaPackage.getDirectedGraphToTripleGraphGrammar(),
				"directedGraphToTripleGraphGrammarParameter", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getFillBaseRules__GenerateModel_solveCsp_BWD__IsApplicableMatch_DirectedGraphToTripleGraphGrammar_DirectedGraph_TripleGraphGrammar_ModelgeneratorRuleResult(),
				theCspPackage.getCSP(), "generateModel_solveCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getDirectedGraphToTripleGraphGrammar(), "directedGraphToTripleGraphGrammar",
				0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getModelgeneratorRuleResult(), "ruleResult", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFillBaseRules__GenerateModel_checkCsp_BWD__CSP(), ecorePackage.getEBoolean(),
				"generateModel_checkCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(tggGrammarDirectedGraphAxiomEClass, TGGGrammarDirectedGraphAxiom.class,
				"TGGGrammarDirectedGraphAxiom", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__IsAppropriate_FWD__Match_DirectedGraph(),
				ecorePackage.getEBoolean(), "isAppropriate_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__Perform_FWD__IsApplicableMatch(),
				theRuntimePackage.getPerformRuleResult(), "perform_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__IsApplicable_FWD__Match(),
				theRuntimePackage.getIsApplicableRuleResult(), "isApplicable_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__RegisterObjectsToMatch_FWD__Match_DirectedGraph(), null,
				"registerObjectsToMatch_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__IsAppropriate_solveCsp_FWD__Match_DirectedGraph(),
				theCspPackage.getCSP(), "isAppropriate_solveCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__IsAppropriate_checkCsp_FWD__CSP(),
				ecorePackage.getEBoolean(), "isAppropriate_checkCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getTGGGrammarDirectedGraphAxiom__IsApplicable_solveCsp_FWD__IsApplicableMatch_DirectedGraph(),
				theCspPackage.getCSP(), "isApplicable_solveCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__IsApplicable_checkCsp_FWD__CSP(),
				ecorePackage.getEBoolean(), "isApplicable_checkCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getTGGGrammarDirectedGraphAxiom__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject(), null,
				"registerObjects_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getPerformRuleResult(), "ruleresult", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "tripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraphToTripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__CheckTypes_FWD__Match(), ecorePackage.getEBoolean(),
				"checkTypes_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__IsAppropriate_BWD__Match_TripleGraphGrammar(),
				ecorePackage.getEBoolean(), "isAppropriate_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__Perform_BWD__IsApplicableMatch(),
				theRuntimePackage.getPerformRuleResult(), "perform_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__IsApplicable_BWD__Match(),
				theRuntimePackage.getIsApplicableRuleResult(), "isApplicable_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__RegisterObjectsToMatch_BWD__Match_TripleGraphGrammar(),
				null, "registerObjectsToMatch_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__IsAppropriate_solveCsp_BWD__Match_TripleGraphGrammar(),
				theCspPackage.getCSP(), "isAppropriate_solveCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__IsAppropriate_checkCsp_BWD__CSP(),
				ecorePackage.getEBoolean(), "isAppropriate_checkCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getTGGGrammarDirectedGraphAxiom__IsApplicable_solveCsp_BWD__IsApplicableMatch_TripleGraphGrammar(),
				theCspPackage.getCSP(), "isApplicable_solveCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__IsApplicable_checkCsp_BWD__CSP(),
				ecorePackage.getEBoolean(), "isApplicable_checkCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getTGGGrammarDirectedGraphAxiom__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject(), null,
				"registerObjects_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getPerformRuleResult(), "ruleresult", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "tripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraphToTripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__CheckTypes_BWD__Match(), ecorePackage.getEBoolean(),
				"checkTypes_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__IsAppropriate_FWD_DirectedGraph_0__DirectedGraph(),
				theRuntimePackage.getEObjectContainer(), "isAppropriate_FWD_DirectedGraph_0", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getTGGGrammarDirectedGraphAxiom__IsAppropriate_BWD_TripleGraphGrammar_0__TripleGraphGrammar(),
				theRuntimePackage.getEObjectContainer(), "isAppropriate_BWD_TripleGraphGrammar_0", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__CheckAttributes_FWD__TripleMatch(),
				theRuntimePackage.getAttributeConstraintsRuleResult(), "checkAttributes_FWD", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getTripleMatch(), "__tripleMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__CheckAttributes_BWD__TripleMatch(),
				theRuntimePackage.getAttributeConstraintsRuleResult(), "checkAttributes_BWD", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getTripleMatch(), "__tripleMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__IsApplicable_CC__Match_Match(),
				theRuntimePackage.getIsApplicableRuleResult(), "isApplicable_CC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "sourceMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "targetMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getTGGGrammarDirectedGraphAxiom__IsApplicable_solveCsp_CC__DirectedGraph_TripleGraphGrammar_Match_Match(),
				theCspPackage.getCSP(), "isApplicable_solveCsp_CC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "sourceMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "targetMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__IsApplicable_checkCsp_CC__CSP(),
				ecorePackage.getEBoolean(), "isApplicable_checkCsp_CC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__CheckDEC_FWD__DirectedGraph(), ecorePackage.getEBoolean(),
				"checkDEC_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__CheckDEC_BWD__TripleGraphGrammar(),
				ecorePackage.getEBoolean(), "checkDEC_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__GenerateModel__RuleEntryContainer(),
				theRuntimePackage.getModelgeneratorRuleResult(), "generateModel", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theModelgeneratorPackage.getRuleEntryContainer(), "ruleEntryContainer", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		op = initEOperation(
				getTGGGrammarDirectedGraphAxiom__GenerateModel_solveCsp_BWD__IsApplicableMatch_ModelgeneratorRuleResult(),
				theCspPackage.getCSP(), "generateModel_solveCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getModelgeneratorRuleResult(), "ruleResult", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getTGGGrammarDirectedGraphAxiom__GenerateModel_checkCsp_BWD__CSP(),
				ecorePackage.getEBoolean(), "generateModel_checkCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(complementRuleEClass, ComplementRule.class, "ComplementRule", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		op = initEOperation(getComplementRule__IsAppropriate_FWD__Match_DirectedGraph_Box_Composite_Box(),
				ecorePackage.getEBoolean(), "isAppropriate_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getComposite(), "complements", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__Perform_FWD__IsApplicableMatch(),
				theRuntimePackage.getPerformRuleResult(), "perform_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__IsApplicable_FWD__Match(), theRuntimePackage.getIsApplicableRuleResult(),
				"isApplicable_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__RegisterObjectsToMatch_FWD__Match_DirectedGraph_Box_Composite_Box(),
				null, "registerObjectsToMatch_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getComposite(), "complements", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__IsAppropriate_solveCsp_FWD__Match_DirectedGraph_Box_Composite_Box(),
				theCspPackage.getCSP(), "isAppropriate_solveCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getComposite(), "complements", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__IsAppropriate_checkCsp_FWD__CSP(), ecorePackage.getEBoolean(),
				"isAppropriate_checkCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getComplementRule__IsApplicable_solveCsp_FWD__IsApplicableMatch_TGGRule_TripleGraphGrammar_DirectedGraph_Box_NodeToRule_Composite_TGGRule_DirectedGraphToTripleGraphGrammar_Box(),
				theCspPackage.getCSP(), "isApplicable_solveCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getNodeToRule(), "nodeToRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getComposite(), "complements", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getDirectedGraphToTripleGraphGrammar(), "directedGraphToTripleGraphGrammar",
				0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__IsApplicable_checkCsp_FWD__CSP(), ecorePackage.getEBoolean(),
				"isApplicable_checkCsp_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getComplementRule__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject(),
				null, "registerObjects_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getPerformRuleResult(), "ruleresult", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "tripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "nodeToRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "complements", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraphToTripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__CheckTypes_FWD__Match(), ecorePackage.getEBoolean(), "checkTypes_FWD", 0,
				1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__IsAppropriate_BWD__Match_TGGRule_TripleGraphGrammar_TGGRule(),
				ecorePackage.getEBoolean(), "isAppropriate_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__Perform_BWD__IsApplicableMatch(),
				theRuntimePackage.getPerformRuleResult(), "perform_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__IsApplicable_BWD__Match(), theRuntimePackage.getIsApplicableRuleResult(),
				"isApplicable_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__RegisterObjectsToMatch_BWD__Match_TGGRule_TripleGraphGrammar_TGGRule(),
				null, "registerObjectsToMatch_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__IsAppropriate_solveCsp_BWD__Match_TGGRule_TripleGraphGrammar_TGGRule(),
				theCspPackage.getCSP(), "isAppropriate_solveCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__IsAppropriate_checkCsp_BWD__CSP(), ecorePackage.getEBoolean(),
				"isAppropriate_checkCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getComplementRule__IsApplicable_solveCsp_BWD__IsApplicableMatch_TGGRule_TripleGraphGrammar_DirectedGraph_Box_NodeToRule_TGGRule_DirectedGraphToTripleGraphGrammar_Box(),
				theCspPackage.getCSP(), "isApplicable_solveCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getNodeToRule(), "nodeToRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getDirectedGraphToTripleGraphGrammar(), "directedGraphToTripleGraphGrammar",
				0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__IsApplicable_checkCsp_BWD__CSP(), ecorePackage.getEBoolean(),
				"isApplicable_checkCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getComplementRule__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject_EObject(),
				null, "registerObjects_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getPerformRuleResult(), "ruleresult", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "tripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "nodeToRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "complements", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "directedGraphToTripleGraphGrammar", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__CheckTypes_BWD__Match(), ecorePackage.getEBoolean(), "checkTypes_BWD", 0,
				1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "match", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__IsAppropriate_FWD_EMoflonEdge_59__EMoflonEdge(),
				theRuntimePackage.getEObjectContainer(), "isAppropriate_FWD_EMoflonEdge_59", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getEMoflonEdge(), "_edge_edges", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__IsAppropriate_BWD_EMoflonEdge_84__EMoflonEdge(),
				theRuntimePackage.getEObjectContainer(), "isAppropriate_BWD_EMoflonEdge_84", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getEMoflonEdge(), "_edge_kernel", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__CheckAttributes_FWD__TripleMatch(),
				theRuntimePackage.getAttributeConstraintsRuleResult(), "checkAttributes_FWD", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getTripleMatch(), "__tripleMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__CheckAttributes_BWD__TripleMatch(),
				theRuntimePackage.getAttributeConstraintsRuleResult(), "checkAttributes_BWD", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theRuntimePackage.getTripleMatch(), "__tripleMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__IsApplicable_CC__Match_Match(),
				theRuntimePackage.getIsApplicableRuleResult(), "isApplicable_CC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "sourceMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "targetMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getComplementRule__IsApplicable_solveCsp_CC__TGGRule_TripleGraphGrammar_DirectedGraph_Box_Composite_TGGRule_Box_Match_Match(),
				theCspPackage.getCSP(), "isApplicable_solveCsp_CC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getComposite(), "complements", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "sourceMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getMatch(), "targetMatch", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__IsApplicable_checkCsp_CC__CSP(), ecorePackage.getEBoolean(),
				"isApplicable_checkCsp_CC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__CheckDEC_FWD__DirectedGraph_Box_Composite_Box(),
				ecorePackage.getEBoolean(), "checkDEC_FWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getComposite(), "complements", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__CheckDEC_BWD__TGGRule_TripleGraphGrammar_TGGRule(),
				ecorePackage.getEBoolean(), "checkDEC_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__GenerateModel__RuleEntryContainer_NodeToRule(),
				theRuntimePackage.getModelgeneratorRuleResult(), "generateModel", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theModelgeneratorPackage.getRuleEntryContainer(), "ruleEntryContainer", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theSchemaPackage.getNodeToRule(), "nodeToRuleParameter", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(
				getComplementRule__GenerateModel_solveCsp_BWD__IsApplicableMatch_TGGRule_TripleGraphGrammar_DirectedGraph_Box_NodeToRule_TGGRule_DirectedGraphToTripleGraphGrammar_Box_ModelgeneratorRuleResult(),
				theCspPackage.getCSP(), "generateModel_solveCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getIsApplicableMatch(), "isApplicableMatch", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "superRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTripleGraphGrammar(), "tripleGraphGrammar", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, theLanguagePackage.getDirectedGraph(), "directedGraph", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "ruleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getNodeToRule(), "nodeToRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage_1.getTGGRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theSchemaPackage.getDirectedGraphToTripleGraphGrammar(), "directedGraphToTripleGraphGrammar",
				0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theLanguagePackage.getBox(), "superRuleBox", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theRuntimePackage.getModelgeneratorRuleResult(), "ruleResult", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getComplementRule__GenerateModel_checkCsp_BWD__CSP(), ecorePackage.getEBoolean(),
				"generateModel_checkCsp_BWD", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCspPackage.getCSP(), "csp", 0, 1, IS_UNIQUE, IS_ORDERED);
	}

} //RulesPackageImpl
