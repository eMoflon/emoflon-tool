/**
 */
package org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.moflon.ide.visualization.dot.ecore.epackageviz.EpackagevizPackage;

import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.RulesFactory;
import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.RulesPackage;

import org.moflon.ide.visualization.dot.ecore.epackageviz.impl.EpackagevizPackageImpl;

import org.moflon.ide.visualization.dot.language.LanguagePackage;

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
	private EClass interfaceNodeRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass packageAxiomEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeAbstractRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumNodeRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractClassNodeRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleClassNodeRuleEClass = null;

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
	 * @see org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.RulesPackage#eNS_URI
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
		EpackagevizPackageImpl theEpackagevizPackage = (EpackagevizPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(EpackagevizPackage.eNS_URI) instanceof EpackagevizPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(EpackagevizPackage.eNS_URI)
						: EpackagevizPackage.eINSTANCE);

		// Load packages
		theEpackagevizPackage.loadPackage();

		// Fix loaded packages
		theRulesPackage.fixPackageContents();
		theEpackagevizPackage.fixPackageContents();

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
	public EClass getInterfaceNodeRule() {
		if (interfaceNodeRuleEClass == null) {
			interfaceNodeRuleEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI)
					.getEClassifiers().get(0);
		}
		return interfaceNodeRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsAppropriate_FWD__Match_PInterface_ClassGraph() {
		return getInterfaceNodeRule().getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__Perform_FWD__IsApplicableMatch() {
		return getInterfaceNodeRule().getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsApplicable_FWD__Match() {
		return getInterfaceNodeRule().getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__RegisterObjectsToMatch_FWD__Match_PInterface_ClassGraph() {
		return getInterfaceNodeRule().getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsAppropriate_solveCsp_FWD__Match_PInterface_ClassGraph() {
		return getInterfaceNodeRule().getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsAppropriate_checkCsp_FWD__CSP() {
		return getInterfaceNodeRule().getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsApplicable_solveCsp_FWD__IsApplicableMatch_PInterface_ClassGraphToEPackage_EPackage_ClassGraph() {
		return getInterfaceNodeRule().getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsApplicable_checkCsp_FWD__CSP() {
		return getInterfaceNodeRule().getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject() {
		return getInterfaceNodeRule().getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__CheckTypes_FWD__Match() {
		return getInterfaceNodeRule().getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsAppropriate_BWD__Match_EPackage_EClass() {
		return getInterfaceNodeRule().getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__Perform_BWD__IsApplicableMatch() {
		return getInterfaceNodeRule().getEOperations().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsApplicable_BWD__Match() {
		return getInterfaceNodeRule().getEOperations().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__RegisterObjectsToMatch_BWD__Match_EPackage_EClass() {
		return getInterfaceNodeRule().getEOperations().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsAppropriate_solveCsp_BWD__Match_EPackage_EClass() {
		return getInterfaceNodeRule().getEOperations().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsAppropriate_checkCsp_BWD__CSP() {
		return getInterfaceNodeRule().getEOperations().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsApplicable_solveCsp_BWD__IsApplicableMatch_ClassGraphToEPackage_EPackage_ClassGraph_EClass() {
		return getInterfaceNodeRule().getEOperations().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsApplicable_checkCsp_BWD__CSP() {
		return getInterfaceNodeRule().getEOperations().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject() {
		return getInterfaceNodeRule().getEOperations().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__CheckTypes_BWD__Match() {
		return getInterfaceNodeRule().getEOperations().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsAppropriate_BWD_EMoflonEdge_3__EMoflonEdge() {
		return getInterfaceNodeRule().getEOperations().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsAppropriate_FWD_EMoflonEdge_3__EMoflonEdge() {
		return getInterfaceNodeRule().getEOperations().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__CheckAttributes_FWD__TripleMatch() {
		return getInterfaceNodeRule().getEOperations().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__CheckAttributes_BWD__TripleMatch() {
		return getInterfaceNodeRule().getEOperations().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsApplicable_CC__Match_Match() {
		return getInterfaceNodeRule().getEOperations().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsApplicable_solveCsp_CC__PInterface_EPackage_ClassGraph_EClass_Match_Match() {
		return getInterfaceNodeRule().getEOperations().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__IsApplicable_checkCsp_CC__CSP() {
		return getInterfaceNodeRule().getEOperations().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__CheckDEC_FWD__PInterface_ClassGraph() {
		return getInterfaceNodeRule().getEOperations().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__CheckDEC_BWD__EPackage_EClass() {
		return getInterfaceNodeRule().getEOperations().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__GenerateModel__RuleEntryContainer_ClassGraphToEPackage() {
		return getInterfaceNodeRule().getEOperations().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__GenerateModel_solveCsp_BWD__IsApplicableMatch_ClassGraphToEPackage_EPackage_ClassGraph_ModelgeneratorRuleResult() {
		return getInterfaceNodeRule().getEOperations().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getInterfaceNodeRule__GenerateModel_checkCsp_BWD__CSP() {
		return getInterfaceNodeRule().getEOperations().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPackageAxiom() {
		if (packageAxiomEClass == null) {
			packageAxiomEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI).getEClassifiers()
					.get(1);
		}
		return packageAxiomEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsAppropriate_FWD__Match_ClassGraph() {
		return getPackageAxiom().getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__Perform_FWD__IsApplicableMatch() {
		return getPackageAxiom().getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsApplicable_FWD__Match() {
		return getPackageAxiom().getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__RegisterObjectsToMatch_FWD__Match_ClassGraph() {
		return getPackageAxiom().getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsAppropriate_solveCsp_FWD__Match_ClassGraph() {
		return getPackageAxiom().getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsAppropriate_checkCsp_FWD__CSP() {
		return getPackageAxiom().getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsApplicable_solveCsp_FWD__IsApplicableMatch_ClassGraph() {
		return getPackageAxiom().getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsApplicable_checkCsp_FWD__CSP() {
		return getPackageAxiom().getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject() {
		return getPackageAxiom().getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__CheckTypes_FWD__Match() {
		return getPackageAxiom().getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsAppropriate_BWD__Match_EPackage() {
		return getPackageAxiom().getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__Perform_BWD__IsApplicableMatch() {
		return getPackageAxiom().getEOperations().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsApplicable_BWD__Match() {
		return getPackageAxiom().getEOperations().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__RegisterObjectsToMatch_BWD__Match_EPackage() {
		return getPackageAxiom().getEOperations().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsAppropriate_solveCsp_BWD__Match_EPackage() {
		return getPackageAxiom().getEOperations().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsAppropriate_checkCsp_BWD__CSP() {
		return getPackageAxiom().getEOperations().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsApplicable_solveCsp_BWD__IsApplicableMatch_EPackage() {
		return getPackageAxiom().getEOperations().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsApplicable_checkCsp_BWD__CSP() {
		return getPackageAxiom().getEOperations().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject() {
		return getPackageAxiom().getEOperations().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__CheckTypes_BWD__Match() {
		return getPackageAxiom().getEOperations().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsAppropriate_BWD_EPackage_1__EPackage() {
		return getPackageAxiom().getEOperations().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsAppropriate_FWD_ClassGraph_1__ClassGraph() {
		return getPackageAxiom().getEOperations().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__CheckAttributes_FWD__TripleMatch() {
		return getPackageAxiom().getEOperations().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__CheckAttributes_BWD__TripleMatch() {
		return getPackageAxiom().getEOperations().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsApplicable_CC__Match_Match() {
		return getPackageAxiom().getEOperations().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsApplicable_solveCsp_CC__ClassGraph_EPackage_Match_Match() {
		return getPackageAxiom().getEOperations().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__IsApplicable_checkCsp_CC__CSP() {
		return getPackageAxiom().getEOperations().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__CheckDEC_FWD__ClassGraph() {
		return getPackageAxiom().getEOperations().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__CheckDEC_BWD__EPackage() {
		return getPackageAxiom().getEOperations().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__GenerateModel__RuleEntryContainer() {
		return getPackageAxiom().getEOperations().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__GenerateModel_solveCsp_BWD__IsApplicableMatch_ModelgeneratorRuleResult() {
		return getPackageAxiom().getEOperations().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPackageAxiom__GenerateModel_checkCsp_BWD__CSP() {
		return getPackageAxiom().getEOperations().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNodeAbstractRule() {
		if (nodeAbstractRuleEClass == null) {
			nodeAbstractRuleEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI)
					.getEClassifiers().get(2);
		}
		return nodeAbstractRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumNodeRule() {
		if (enumNodeRuleEClass == null) {
			enumNodeRuleEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI).getEClassifiers()
					.get(3);
		}
		return enumNodeRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsAppropriate_FWD__Match_PEnum_ClassGraph() {
		return getEnumNodeRule().getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__Perform_FWD__IsApplicableMatch() {
		return getEnumNodeRule().getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsApplicable_FWD__Match() {
		return getEnumNodeRule().getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__RegisterObjectsToMatch_FWD__Match_PEnum_ClassGraph() {
		return getEnumNodeRule().getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsAppropriate_solveCsp_FWD__Match_PEnum_ClassGraph() {
		return getEnumNodeRule().getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsAppropriate_checkCsp_FWD__CSP() {
		return getEnumNodeRule().getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsApplicable_solveCsp_FWD__IsApplicableMatch_PEnum_ClassGraphToEPackage_ClassGraph_EPackage() {
		return getEnumNodeRule().getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsApplicable_checkCsp_FWD__CSP() {
		return getEnumNodeRule().getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject() {
		return getEnumNodeRule().getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__CheckTypes_FWD__Match() {
		return getEnumNodeRule().getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsAppropriate_BWD__Match_EEnum_EPackage() {
		return getEnumNodeRule().getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__Perform_BWD__IsApplicableMatch() {
		return getEnumNodeRule().getEOperations().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsApplicable_BWD__Match() {
		return getEnumNodeRule().getEOperations().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__RegisterObjectsToMatch_BWD__Match_EEnum_EPackage() {
		return getEnumNodeRule().getEOperations().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsAppropriate_solveCsp_BWD__Match_EEnum_EPackage() {
		return getEnumNodeRule().getEOperations().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsAppropriate_checkCsp_BWD__CSP() {
		return getEnumNodeRule().getEOperations().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsApplicable_solveCsp_BWD__IsApplicableMatch_EEnum_ClassGraphToEPackage_ClassGraph_EPackage() {
		return getEnumNodeRule().getEOperations().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsApplicable_checkCsp_BWD__CSP() {
		return getEnumNodeRule().getEOperations().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject() {
		return getEnumNodeRule().getEOperations().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__CheckTypes_BWD__Match() {
		return getEnumNodeRule().getEOperations().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsAppropriate_BWD_EMoflonEdge_4__EMoflonEdge() {
		return getEnumNodeRule().getEOperations().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsAppropriate_FWD_EMoflonEdge_4__EMoflonEdge() {
		return getEnumNodeRule().getEOperations().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__CheckAttributes_FWD__TripleMatch() {
		return getEnumNodeRule().getEOperations().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__CheckAttributes_BWD__TripleMatch() {
		return getEnumNodeRule().getEOperations().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsApplicable_CC__Match_Match() {
		return getEnumNodeRule().getEOperations().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsApplicable_solveCsp_CC__PEnum_EEnum_ClassGraph_EPackage_Match_Match() {
		return getEnumNodeRule().getEOperations().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__IsApplicable_checkCsp_CC__CSP() {
		return getEnumNodeRule().getEOperations().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__CheckDEC_FWD__PEnum_ClassGraph() {
		return getEnumNodeRule().getEOperations().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__CheckDEC_BWD__EEnum_EPackage() {
		return getEnumNodeRule().getEOperations().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__GenerateModel__RuleEntryContainer_ClassGraphToEPackage() {
		return getEnumNodeRule().getEOperations().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__GenerateModel_solveCsp_BWD__IsApplicableMatch_ClassGraphToEPackage_ClassGraph_EPackage_ModelgeneratorRuleResult() {
		return getEnumNodeRule().getEOperations().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumNodeRule__GenerateModel_checkCsp_BWD__CSP() {
		return getEnumNodeRule().getEOperations().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractClassNodeRule() {
		if (abstractClassNodeRuleEClass == null) {
			abstractClassNodeRuleEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI)
					.getEClassifiers().get(4);
		}
		return abstractClassNodeRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsAppropriate_FWD__Match_PAbstract_ClassGraph() {
		return getAbstractClassNodeRule().getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__Perform_FWD__IsApplicableMatch() {
		return getAbstractClassNodeRule().getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsApplicable_FWD__Match() {
		return getAbstractClassNodeRule().getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__RegisterObjectsToMatch_FWD__Match_PAbstract_ClassGraph() {
		return getAbstractClassNodeRule().getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsAppropriate_solveCsp_FWD__Match_PAbstract_ClassGraph() {
		return getAbstractClassNodeRule().getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsAppropriate_checkCsp_FWD__CSP() {
		return getAbstractClassNodeRule().getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsApplicable_solveCsp_FWD__IsApplicableMatch_ClassGraphToEPackage_EPackage_PAbstract_ClassGraph() {
		return getAbstractClassNodeRule().getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsApplicable_checkCsp_FWD__CSP() {
		return getAbstractClassNodeRule().getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject() {
		return getAbstractClassNodeRule().getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__CheckTypes_FWD__Match() {
		return getAbstractClassNodeRule().getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsAppropriate_BWD__Match_EPackage_EClass() {
		return getAbstractClassNodeRule().getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__Perform_BWD__IsApplicableMatch() {
		return getAbstractClassNodeRule().getEOperations().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsApplicable_BWD__Match() {
		return getAbstractClassNodeRule().getEOperations().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__RegisterObjectsToMatch_BWD__Match_EPackage_EClass() {
		return getAbstractClassNodeRule().getEOperations().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsAppropriate_solveCsp_BWD__Match_EPackage_EClass() {
		return getAbstractClassNodeRule().getEOperations().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsAppropriate_checkCsp_BWD__CSP() {
		return getAbstractClassNodeRule().getEOperations().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsApplicable_solveCsp_BWD__IsApplicableMatch_ClassGraphToEPackage_EPackage_ClassGraph_EClass() {
		return getAbstractClassNodeRule().getEOperations().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsApplicable_checkCsp_BWD__CSP() {
		return getAbstractClassNodeRule().getEOperations().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject() {
		return getAbstractClassNodeRule().getEOperations().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__CheckTypes_BWD__Match() {
		return getAbstractClassNodeRule().getEOperations().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsAppropriate_BWD_EMoflonEdge_5__EMoflonEdge() {
		return getAbstractClassNodeRule().getEOperations().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsAppropriate_FWD_EMoflonEdge_5__EMoflonEdge() {
		return getAbstractClassNodeRule().getEOperations().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__CheckAttributes_FWD__TripleMatch() {
		return getAbstractClassNodeRule().getEOperations().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__CheckAttributes_BWD__TripleMatch() {
		return getAbstractClassNodeRule().getEOperations().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsApplicable_CC__Match_Match() {
		return getAbstractClassNodeRule().getEOperations().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsApplicable_solveCsp_CC__EPackage_PAbstract_ClassGraph_EClass_Match_Match() {
		return getAbstractClassNodeRule().getEOperations().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__IsApplicable_checkCsp_CC__CSP() {
		return getAbstractClassNodeRule().getEOperations().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__CheckDEC_FWD__PAbstract_ClassGraph() {
		return getAbstractClassNodeRule().getEOperations().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__CheckDEC_BWD__EPackage_EClass() {
		return getAbstractClassNodeRule().getEOperations().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__GenerateModel__RuleEntryContainer_ClassGraphToEPackage() {
		return getAbstractClassNodeRule().getEOperations().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__GenerateModel_solveCsp_BWD__IsApplicableMatch_ClassGraphToEPackage_EPackage_ClassGraph_ModelgeneratorRuleResult() {
		return getAbstractClassNodeRule().getEOperations().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getAbstractClassNodeRule__GenerateModel_checkCsp_BWD__CSP() {
		return getAbstractClassNodeRule().getEOperations().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimpleClassNodeRule() {
		if (simpleClassNodeRuleEClass == null) {
			simpleClassNodeRuleEClass = (EClass) EPackage.Registry.INSTANCE.getEPackage(RulesPackage.eNS_URI)
					.getEClassifiers().get(5);
		}
		return simpleClassNodeRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsAppropriate_FWD__Match_PClass_ClassGraph() {
		return getSimpleClassNodeRule().getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__Perform_FWD__IsApplicableMatch() {
		return getSimpleClassNodeRule().getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsApplicable_FWD__Match() {
		return getSimpleClassNodeRule().getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__RegisterObjectsToMatch_FWD__Match_PClass_ClassGraph() {
		return getSimpleClassNodeRule().getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsAppropriate_solveCsp_FWD__Match_PClass_ClassGraph() {
		return getSimpleClassNodeRule().getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsAppropriate_checkCsp_FWD__CSP() {
		return getSimpleClassNodeRule().getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsApplicable_solveCsp_FWD__IsApplicableMatch_ClassGraphToEPackage_EPackage_PClass_ClassGraph() {
		return getSimpleClassNodeRule().getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsApplicable_checkCsp_FWD__CSP() {
		return getSimpleClassNodeRule().getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__RegisterObjects_FWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject() {
		return getSimpleClassNodeRule().getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__CheckTypes_FWD__Match() {
		return getSimpleClassNodeRule().getEOperations().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsAppropriate_BWD__Match_EPackage_EClass() {
		return getSimpleClassNodeRule().getEOperations().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__Perform_BWD__IsApplicableMatch() {
		return getSimpleClassNodeRule().getEOperations().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsApplicable_BWD__Match() {
		return getSimpleClassNodeRule().getEOperations().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__RegisterObjectsToMatch_BWD__Match_EPackage_EClass() {
		return getSimpleClassNodeRule().getEOperations().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsAppropriate_solveCsp_BWD__Match_EPackage_EClass() {
		return getSimpleClassNodeRule().getEOperations().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsAppropriate_checkCsp_BWD__CSP() {
		return getSimpleClassNodeRule().getEOperations().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsApplicable_solveCsp_BWD__IsApplicableMatch_ClassGraphToEPackage_EPackage_ClassGraph_EClass() {
		return getSimpleClassNodeRule().getEOperations().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsApplicable_checkCsp_BWD__CSP() {
		return getSimpleClassNodeRule().getEOperations().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__RegisterObjects_BWD__PerformRuleResult_EObject_EObject_EObject_EObject_EObject_EObject() {
		return getSimpleClassNodeRule().getEOperations().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__CheckTypes_BWD__Match() {
		return getSimpleClassNodeRule().getEOperations().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsAppropriate_BWD_EMoflonEdge_6__EMoflonEdge() {
		return getSimpleClassNodeRule().getEOperations().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsAppropriate_FWD_EMoflonEdge_6__EMoflonEdge() {
		return getSimpleClassNodeRule().getEOperations().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__CheckAttributes_FWD__TripleMatch() {
		return getSimpleClassNodeRule().getEOperations().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__CheckAttributes_BWD__TripleMatch() {
		return getSimpleClassNodeRule().getEOperations().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsApplicable_CC__Match_Match() {
		return getSimpleClassNodeRule().getEOperations().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsApplicable_solveCsp_CC__EPackage_PClass_ClassGraph_EClass_Match_Match() {
		return getSimpleClassNodeRule().getEOperations().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__IsApplicable_checkCsp_CC__CSP() {
		return getSimpleClassNodeRule().getEOperations().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__CheckDEC_FWD__PClass_ClassGraph() {
		return getSimpleClassNodeRule().getEOperations().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__CheckDEC_BWD__EPackage_EClass() {
		return getSimpleClassNodeRule().getEOperations().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__GenerateModel__RuleEntryContainer_ClassGraphToEPackage() {
		return getSimpleClassNodeRule().getEOperations().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__GenerateModel_solveCsp_BWD__IsApplicableMatch_ClassGraphToEPackage_EPackage_ClassGraph_ModelgeneratorRuleResult() {
		return getSimpleClassNodeRule().getEOperations().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleClassNodeRule__GenerateModel_checkCsp_BWD__CSP() {
		return getSimpleClassNodeRule().getEOperations().get(31);
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
	private boolean isFixed = false;

	/**
	 * Fixes up the loaded package, to make it appear as if it had been programmatically built.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fixPackageContents() {
		if (isFixed)
			return;
		isFixed = true;
		fixEClassifiers();
	}

	/**
	 * Sets the instance class on the given classifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void fixInstanceClass(EClassifier eClassifier) {
		if (eClassifier.getInstanceClassName() == null) {
			eClassifier.setInstanceClassName(
					"org.moflon.ide.visualization.dot.ecore.epackageviz.Rules." + eClassifier.getName());
			setGeneratedClassName(eClassifier);
		}
	}

} //RulesPackageImpl
