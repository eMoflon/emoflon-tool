/**
 */
package org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.impl;

import java.lang.Iterable;

import java.lang.reflect.InvocationTargetException;

import java.util.LinkedList;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;

import org.moflon.ide.visualization.dot.ecore.epackageviz.ClassGraphToEPackage;
import org.moflon.ide.visualization.dot.ecore.epackageviz.EpackagevizFactory;
import org.moflon.ide.visualization.dot.ecore.epackageviz.PNodeToEClassifier;

import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.InterfaceNodeRule;
import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.RulesPackage;

import org.moflon.ide.visualization.dot.language.ClassGraph;
import org.moflon.ide.visualization.dot.language.LanguageFactory;
import org.moflon.ide.visualization.dot.language.PInterface;

import org.moflon.tgg.language.csp.CSP;

import org.moflon.tgg.language.modelgenerator.RuleEntryContainer;
import org.moflon.tgg.language.modelgenerator.RuleEntryList;

import org.moflon.tgg.runtime.AttributeConstraintsRuleResult;
import org.moflon.tgg.runtime.CCMatch;
import org.moflon.tgg.runtime.EMoflonEdge;
import org.moflon.tgg.runtime.EObjectContainer;
import org.moflon.tgg.runtime.IsApplicableMatch;
import org.moflon.tgg.runtime.IsApplicableRuleResult;
import org.moflon.tgg.runtime.Match;
import org.moflon.tgg.runtime.ModelgeneratorRuleResult;
import org.moflon.tgg.runtime.PerformRuleResult;
import org.moflon.tgg.runtime.RuntimeFactory;
import org.moflon.tgg.runtime.TripleMatch;

import org.moflon.tgg.runtime.impl.AbstractRuleImpl;
// <-- [user defined imports]
import org.moflon.csp.*;
import csp.constraints.*;
import org.moflon.tgg.language.csp.*;
import org.moflon.tgg.runtime.TripleMatchNodeMapping;
import java.util.Optional;
import org.moflon.tgg.algorithm.delta.attribute.CheckAttributeHelper;
import SDMLanguage.expressions.ComparingOperator;
import org.moflon.tgg.runtime.TripleMatchNodeMapping;
import java.util.Optional;
import org.moflon.tgg.algorithm.delta.attribute.CheckAttributeHelper;
import SDMLanguage.expressions.ComparingOperator;
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interface Node Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class InterfaceNodeRuleImpl extends AbstractRuleImpl implements InterfaceNodeRule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InterfaceNodeRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulesPackage.eINSTANCE.getInterfaceNodeRule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_FWD(Match match, PInterface pInterface, ClassGraph graph) {
		// initial bindings
		Object[] result1_black = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_0_1_initialbindings_blackBBBB(this,
				match, pInterface, graph);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[pInterface] = " + pInterface + ", "
					+ "[graph] = " + graph + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_0_2_SolveCSP_bindingAndBlackFBBBB(this, match, pInterface, graph);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[pInterface] = " + pInterface + ", " + "[graph] = "
					+ graph + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_0_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = InterfaceNodeRuleImpl
					.pattern_InterfaceNodeRule_0_4_collectelementstobetranslated_blackBBB(match, pInterface, graph);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[pInterface] = " + pInterface + ", "
						+ "[graph] = " + graph + ".");
			}
			InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_0_4_collectelementstobetranslated_greenBBBFF(match,
					pInterface, graph);
			// EMoflonEdge pInterface__graph____graph = (EMoflonEdge) result4_green[3];
			// EMoflonEdge graph__pInterface____nodes = (EMoflonEdge) result4_green[4];

			// collect context elements
			Object[] result5_black = InterfaceNodeRuleImpl
					.pattern_InterfaceNodeRule_0_5_collectcontextelements_blackBBB(match, pInterface, graph);
			if (result5_black == null) {
				throw new RuntimeException(
						"Pattern matching in node [collect context elements] failed." + " Variables: " + "[match] = "
								+ match + ", " + "[pInterface] = " + pInterface + ", " + "[graph] = " + graph + ".");
			}
			InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_0_5_collectcontextelements_greenBB(match, graph);

			// register objects to match
			InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_0_6_registerobjectstomatch_expressionBBBB(this, match,
					pInterface, graph);
			return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_0_7_expressionF();
		} else {
			return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_0_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_FWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_1_1_performtransformation_bindingAndBlackFFFFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		PInterface pInterface = (PInterface) result1_bindingAndBlack[0];
		ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result1_bindingAndBlack[1];
		EPackage rootPackage = (EPackage) result1_bindingAndBlack[2];
		ClassGraph graph = (ClassGraph) result1_bindingAndBlack[3];
		CSP csp = (CSP) result1_bindingAndBlack[4];
		Object[] result1_green = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_1_1_performtransformation_greenBFBFB(pInterface, rootPackage, csp);
		PNodeToEClassifier pInterfaceToEClass = (PNodeToEClassifier) result1_green[1];
		EClass eClass = (EClass) result1_green[3];

		// collect translated elements
		Object[] result2_black = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_1_2_collecttranslatedelements_blackBBB(
				pInterface, pInterfaceToEClass, eClass);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[pInterface] = " + pInterface + ", " + "[pInterfaceToEClass] = " + pInterfaceToEClass + ", "
					+ "[eClass] = " + eClass + ".");
		}
		Object[] result2_green = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_1_2_collecttranslatedelements_greenFBBB(pInterface, pInterfaceToEClass,
						eClass);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_1_3_bookkeepingforedges_blackBBBBBBB(
				ruleresult, pInterface, graphToPackage, pInterfaceToEClass, rootPackage, graph, eClass);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[pInterface] = " + pInterface + ", "
					+ "[graphToPackage] = " + graphToPackage + ", " + "[pInterfaceToEClass] = " + pInterfaceToEClass
					+ ", " + "[rootPackage] = " + rootPackage + ", " + "[graph] = " + graph + ", " + "[eClass] = "
					+ eClass + ".");
		}
		InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_1_3_bookkeepingforedges_greenBBBBBBFFFFFF(ruleresult,
				pInterface, pInterfaceToEClass, rootPackage, graph, eClass);
		// EMoflonEdge pInterface__graph____graph = (EMoflonEdge) result3_green[6];
		// EMoflonEdge graph__pInterface____nodes = (EMoflonEdge) result3_green[7];
		// EMoflonEdge pInterfaceToEClass__pInterface____source = (EMoflonEdge) result3_green[8];
		// EMoflonEdge pInterfaceToEClass__eClass____target = (EMoflonEdge) result3_green[9];
		// EMoflonEdge eClass__rootPackage____ePackage = (EMoflonEdge) result3_green[10];
		// EMoflonEdge rootPackage__eClass____eClassifiers = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_1_5_registerobjects_expressionBBBBBBBB(this, ruleresult,
				pInterface, graphToPackage, pInterfaceToEClass, rootPackage, graph, eClass);
		return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_1_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_FWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_2_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_2_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_2_2_corematch_bindingFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		PInterface pInterface = (PInterface) result2_binding[0];
		ClassGraph graph = (ClassGraph) result2_binding[1];
		for (Object[] result2_black : InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_2_2_corematch_blackBFFBB(pInterface, graph, match)) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result2_black[1];
			EPackage rootPackage = (EPackage) result2_black[2];
			// ForEach find context
			for (Object[] result3_black : InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_2_3_findcontext_blackBBBB(
					pInterface, graphToPackage, rootPackage, graph)) {
				Object[] result3_green = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_2_3_findcontext_greenBBBBFFFFF(
						pInterface, graphToPackage, rootPackage, graph);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[4];
				// EMoflonEdge pInterface__graph____graph = (EMoflonEdge) result3_green[5];
				// EMoflonEdge graph__pInterface____nodes = (EMoflonEdge) result3_green[6];
				// EMoflonEdge graphToPackage__rootPackage____target = (EMoflonEdge) result3_green[7];
				// EMoflonEdge graphToPackage__graph____source = (EMoflonEdge) result3_green[8];

				// solve CSP
				Object[] result4_bindingAndBlack = InterfaceNodeRuleImpl
						.pattern_InterfaceNodeRule_2_4_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch,
								pInterface, graphToPackage, rootPackage, graph);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[pInterface] = " + pInterface + ", " + "[graphToPackage] = " + graphToPackage + ", "
							+ "[rootPackage] = " + rootPackage + ", " + "[graph] = " + graph + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_2_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = InterfaceNodeRuleImpl
							.pattern_InterfaceNodeRule_2_6_addmatchtoruleresult_blackBB(ruleresult, isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_2_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_2_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_FWD(Match match, PInterface pInterface, ClassGraph graph) {
		match.registerObject("pInterface", pInterface);
		match.registerObject("graph", graph);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_FWD(Match match, PInterface pInterface, ClassGraph graph) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();

		// Create literals

		// Create attribute variables

		// Create unbound variables

		// Create constraints

		// Solve CSP
		return csp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_checkCsp_FWD(CSP csp) {
		return csp.check();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isApplicable_solveCsp_FWD(IsApplicableMatch isApplicableMatch, PInterface pInterface,
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_pInterface_label = CSPFactoryHelper.eINSTANCE.createVariable("pInterface.label", true, csp);
		var_pInterface_label.setValue(pInterface.getLabel());
		var_pInterface_label.setType("String");

		// Create unbound variables
		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass.name", csp);
		var_eClass_name.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pInterface_label, var_eClass_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("pInterface", pInterface);
		isApplicableMatch.registerObject("graphToPackage", graphToPackage);
		isApplicableMatch.registerObject("rootPackage", rootPackage);
		isApplicableMatch.registerObject("graph", graph);
		return csp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isApplicable_checkCsp_FWD(CSP csp) {
		return csp.check();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjects_FWD(PerformRuleResult ruleresult, EObject pInterface, EObject graphToPackage,
			EObject pInterfaceToEClass, EObject rootPackage, EObject graph, EObject eClass) {
		ruleresult.registerObject("pInterface", pInterface);
		ruleresult.registerObject("graphToPackage", graphToPackage);
		ruleresult.registerObject("pInterfaceToEClass", pInterfaceToEClass);
		ruleresult.registerObject("rootPackage", rootPackage);
		ruleresult.registerObject("graph", graph);
		ruleresult.registerObject("eClass", eClass);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_FWD(Match match) {
		return true && org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("pInterface").eClass())
				.equals("language.PInterface.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_BWD(Match match, EPackage rootPackage, EClass eClass) {
		// initial bindings
		Object[] result1_black = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_10_1_initialbindings_blackBBBB(this,
				match, rootPackage, eClass);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ", "
					+ "[eClass] = " + eClass + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_10_2_SolveCSP_bindingAndBlackFBBBB(this, match, rootPackage, eClass);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ", "
					+ "[eClass] = " + eClass + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_10_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = InterfaceNodeRuleImpl
					.pattern_InterfaceNodeRule_10_4_collectelementstobetranslated_blackBBB(match, rootPackage, eClass);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ", "
						+ "[eClass] = " + eClass + ".");
			}
			InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_10_4_collectelementstobetranslated_greenBBBFF(match,
					rootPackage, eClass);
			// EMoflonEdge eClass__rootPackage____ePackage = (EMoflonEdge) result4_green[3];
			// EMoflonEdge rootPackage__eClass____eClassifiers = (EMoflonEdge) result4_green[4];

			// collect context elements
			Object[] result5_black = InterfaceNodeRuleImpl
					.pattern_InterfaceNodeRule_10_5_collectcontextelements_blackBBB(match, rootPackage, eClass);
			if (result5_black == null) {
				throw new RuntimeException("Pattern matching in node [collect context elements] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ", "
						+ "[eClass] = " + eClass + ".");
			}
			InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_10_5_collectcontextelements_greenBB(match, rootPackage);

			// register objects to match
			InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_10_6_registerobjectstomatch_expressionBBBB(this, match,
					rootPackage, eClass);
			return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_10_7_expressionF();
		} else {
			return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_10_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_BWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_11_1_performtransformation_bindingAndBlackFFFFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result1_bindingAndBlack[0];
		EPackage rootPackage = (EPackage) result1_bindingAndBlack[1];
		ClassGraph graph = (ClassGraph) result1_bindingAndBlack[2];
		EClass eClass = (EClass) result1_bindingAndBlack[3];
		CSP csp = (CSP) result1_bindingAndBlack[4];
		Object[] result1_green = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_11_1_performtransformation_greenFFBBB(graph, eClass, csp);
		PInterface pInterface = (PInterface) result1_green[0];
		PNodeToEClassifier pInterfaceToEClass = (PNodeToEClassifier) result1_green[1];

		// collect translated elements
		Object[] result2_black = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_11_2_collecttranslatedelements_blackBBB(pInterface, pInterfaceToEClass,
						eClass);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[pInterface] = " + pInterface + ", " + "[pInterfaceToEClass] = " + pInterfaceToEClass + ", "
					+ "[eClass] = " + eClass + ".");
		}
		Object[] result2_green = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_11_2_collecttranslatedelements_greenFBBB(pInterface, pInterfaceToEClass,
						eClass);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_11_3_bookkeepingforedges_blackBBBBBBB(
				ruleresult, pInterface, graphToPackage, pInterfaceToEClass, rootPackage, graph, eClass);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[pInterface] = " + pInterface + ", "
					+ "[graphToPackage] = " + graphToPackage + ", " + "[pInterfaceToEClass] = " + pInterfaceToEClass
					+ ", " + "[rootPackage] = " + rootPackage + ", " + "[graph] = " + graph + ", " + "[eClass] = "
					+ eClass + ".");
		}
		InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_11_3_bookkeepingforedges_greenBBBBBBFFFFFF(ruleresult,
				pInterface, pInterfaceToEClass, rootPackage, graph, eClass);
		// EMoflonEdge pInterface__graph____graph = (EMoflonEdge) result3_green[6];
		// EMoflonEdge graph__pInterface____nodes = (EMoflonEdge) result3_green[7];
		// EMoflonEdge pInterfaceToEClass__pInterface____source = (EMoflonEdge) result3_green[8];
		// EMoflonEdge pInterfaceToEClass__eClass____target = (EMoflonEdge) result3_green[9];
		// EMoflonEdge eClass__rootPackage____ePackage = (EMoflonEdge) result3_green[10];
		// EMoflonEdge rootPackage__eClass____eClassifiers = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_11_5_registerobjects_expressionBBBBBBBB(this, ruleresult,
				pInterface, graphToPackage, pInterfaceToEClass, rootPackage, graph, eClass);
		return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_11_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_BWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_12_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_12_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_12_2_corematch_bindingFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		EPackage rootPackage = (EPackage) result2_binding[0];
		eClass = (EClass) result2_binding[1];
		for (Object[] result2_black : InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_12_2_corematch_blackFBFBB(rootPackage, eClass, match)) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result2_black[0];
			ClassGraph graph = (ClassGraph) result2_black[2];
			// ForEach find context
			for (Object[] result3_black : InterfaceNodeRuleImpl
					.pattern_InterfaceNodeRule_12_3_findcontext_blackBBBB(graphToPackage, rootPackage, graph, eClass)) {
				Object[] result3_green = InterfaceNodeRuleImpl
						.pattern_InterfaceNodeRule_12_3_findcontext_greenBBBBFFFFF(graphToPackage, rootPackage, graph,
								eClass);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[4];
				// EMoflonEdge graphToPackage__rootPackage____target = (EMoflonEdge) result3_green[5];
				// EMoflonEdge eClass__rootPackage____ePackage = (EMoflonEdge) result3_green[6];
				// EMoflonEdge rootPackage__eClass____eClassifiers = (EMoflonEdge) result3_green[7];
				// EMoflonEdge graphToPackage__graph____source = (EMoflonEdge) result3_green[8];

				// solve CSP
				Object[] result4_bindingAndBlack = InterfaceNodeRuleImpl
						.pattern_InterfaceNodeRule_12_4_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch,
								graphToPackage, rootPackage, graph, eClass);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[graphToPackage] = " + graphToPackage + ", " + "[rootPackage] = " + rootPackage + ", "
							+ "[graph] = " + graph + ", " + "[eClass] = " + eClass + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_12_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = InterfaceNodeRuleImpl
							.pattern_InterfaceNodeRule_12_6_addmatchtoruleresult_blackBB(ruleresult, isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_12_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_12_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_BWD(Match match, EPackage rootPackage, EClass eClass) {
		match.registerObject("rootPackage", rootPackage);
		match.registerObject("eClass", eClass);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_BWD(Match match, EPackage rootPackage, EClass eClass) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();

		// Create literals

		// Create attribute variables

		// Create unbound variables

		// Create constraints

		// Solve CSP
		return csp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_checkCsp_BWD(CSP csp) {
		return csp.check();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isApplicable_solveCsp_BWD(IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, ClassGraph graph, EClass eClass) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass.name", true, csp);
		var_eClass_name.setValue(eClass.getName());
		var_eClass_name.setType("String");

		// Create unbound variables
		Variable var_pInterface_label = CSPFactoryHelper.eINSTANCE.createVariable("pInterface.label", csp);
		var_pInterface_label.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pInterface_label, var_eClass_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("graphToPackage", graphToPackage);
		isApplicableMatch.registerObject("rootPackage", rootPackage);
		isApplicableMatch.registerObject("graph", graph);
		isApplicableMatch.registerObject("eClass", eClass);
		return csp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isApplicable_checkCsp_BWD(CSP csp) {
		return csp.check();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjects_BWD(PerformRuleResult ruleresult, EObject pInterface, EObject graphToPackage,
			EObject pInterfaceToEClass, EObject rootPackage, EObject graph, EObject eClass) {
		ruleresult.registerObject("pInterface", pInterface);
		ruleresult.registerObject("graphToPackage", graphToPackage);
		ruleresult.registerObject("pInterfaceToEClass", pInterfaceToEClass);
		ruleresult.registerObject("rootPackage", rootPackage);
		ruleresult.registerObject("graph", graph);
		ruleresult.registerObject("eClass", eClass);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_BWD(Match match) {
		return true
				&& org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("eClass").eClass()).equals("ecore.EClass.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_BWD_EMoflonEdge_3(EMoflonEdge _edge_ePackage) {
		// prepare return value
		Object[] result1_bindingAndBlack = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_20_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_20_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_20_2_testcorematchandDECs_blackFFB(_edge_ePackage)) {
			EPackage rootPackage = (EPackage) result2_black[0];
			EClass eClass = (EClass) result2_black[1];
			Object[] result2_green = InterfaceNodeRuleImpl
					.pattern_InterfaceNodeRule_20_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (InterfaceNodeRuleImpl
					.pattern_InterfaceNodeRule_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(this,
							match, rootPackage, eClass)) {
				// Ensure that the correct types of elements are matched
				if (InterfaceNodeRuleImpl
						.pattern_InterfaceNodeRule_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
								this, match)) {

					// Add match to rule result
					Object[] result5_black = InterfaceNodeRuleImpl
							.pattern_InterfaceNodeRule_20_5_Addmatchtoruleresult_blackBBBB(match, __performOperation,
									__result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_20_5_Addmatchtoruleresult_greenBBBB(match,
							__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_20_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_FWD_EMoflonEdge_3(EMoflonEdge _edge_graph) {
		// prepare return value
		Object[] result1_bindingAndBlack = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_21_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_21_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_21_2_testcorematchandDECs_blackFFB(_edge_graph)) {
			PInterface pInterface = (PInterface) result2_black[0];
			ClassGraph graph = (ClassGraph) result2_black[1];
			Object[] result2_green = InterfaceNodeRuleImpl
					.pattern_InterfaceNodeRule_21_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (InterfaceNodeRuleImpl
					.pattern_InterfaceNodeRule_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(this,
							match, pInterface, graph)) {
				// Ensure that the correct types of elements are matched
				if (InterfaceNodeRuleImpl
						.pattern_InterfaceNodeRule_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
								this, match)) {

					// Add match to rule result
					Object[] result5_black = InterfaceNodeRuleImpl
							.pattern_InterfaceNodeRule_21_5_Addmatchtoruleresult_blackBBBB(match, __performOperation,
									__result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_21_5_Addmatchtoruleresult_greenBBBB(match,
							__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_21_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintsRuleResult checkAttributes_FWD(TripleMatch __tripleMatch) {
		AttributeConstraintsRuleResult ruleResult = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE
				.createAttributeConstraintsRuleResult();
		ruleResult.setRule("InterfaceNodeRule");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		if (!__helper.hasExpectedValue("eClass", "interface", true, ComparingOperator.EQUAL)) {
			ruleResult.setSuccess(false);
			return ruleResult;
		}

		Variable var_pInterface_label = CSPFactoryHelper.eINSTANCE.createVariable("pInterface", true, csp);
		var_pInterface_label.setValue(__helper.getValue("pInterface", "label"));
		var_pInterface_label.setType("String");

		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass", true, csp);
		var_eClass_name.setValue(__helper.getValue("eClass", "name"));
		var_eClass_name.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		eq0.setRuleName("InterfaceNodeRule");
		eq0.solve(var_pInterface_label, var_eClass_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			var_eClass_name.setBound(false);
			eq0.solve(var_pInterface_label, var_eClass_name);
			if (csp.check()) {
				ruleResult.setSuccess(true);
				ruleResult.setRequiredChange(true);
				__helper.setValue("eClass", "name", var_eClass_name.getValue());
			} else {
				ruleResult.setSuccess(false);
				return ruleResult;
			}
		}

		return ruleResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintsRuleResult checkAttributes_BWD(TripleMatch __tripleMatch) {
		AttributeConstraintsRuleResult ruleResult = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE
				.createAttributeConstraintsRuleResult();
		ruleResult.setRule("InterfaceNodeRule");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		if (!__helper.hasExpectedValue("eClass", "interface", true, ComparingOperator.EQUAL)) {
			ruleResult.setSuccess(false);
			return ruleResult;
		}

		Variable var_pInterface_label = CSPFactoryHelper.eINSTANCE.createVariable("pInterface", true, csp);
		var_pInterface_label.setValue(__helper.getValue("pInterface", "label"));
		var_pInterface_label.setType("String");

		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass", true, csp);
		var_eClass_name.setValue(__helper.getValue("eClass", "name"));
		var_eClass_name.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		eq0.setRuleName("InterfaceNodeRule");
		eq0.solve(var_pInterface_label, var_eClass_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			var_pInterface_label.setBound(false);
			eq0.solve(var_pInterface_label, var_eClass_name);
			if (csp.check()) {
				ruleResult.setSuccess(true);
				ruleResult.setRequiredChange(true);
				__helper.setValue("pInterface", "label", var_pInterface_label.getValue());
			} else {
				ruleResult.setSuccess(false);
				return ruleResult;
			}
		}

		return ruleResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_CC(Match sourceMatch, Match targetMatch) {
		// prepare
		Object[] result1_black = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_24_1_prepare_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [prepare] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_24_1_prepare_greenF();
		IsApplicableRuleResult result = (IsApplicableRuleResult) result1_green[0];

		// match src trg context
		Object[] result2_bindingAndBlack = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_bindingAndBlackFFFFBB(sourceMatch, targetMatch);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [match src trg context] failed." + " Variables: "
					+ "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = " + targetMatch + ".");
		}
		PInterface pInterface = (PInterface) result2_bindingAndBlack[0];
		EPackage rootPackage = (EPackage) result2_bindingAndBlack[1];
		ClassGraph graph = (ClassGraph) result2_bindingAndBlack[2];
		EClass eClass = (EClass) result2_bindingAndBlack[3];

		// solve csp
		Object[] result3_bindingAndBlack = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_24_3_solvecsp_bindingAndBlackFBBBBBBB(this, pInterface, rootPackage, graph,
						eClass, sourceMatch, targetMatch);
		if (result3_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [solve csp] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[pInterface] = " + pInterface + ", " + "[rootPackage] = " + rootPackage + ", "
					+ "[graph] = " + graph + ", " + "[eClass] = " + eClass + ", " + "[sourceMatch] = " + sourceMatch
					+ ", " + "[targetMatch] = " + targetMatch + ".");
		}
		CSP csp = (CSP) result3_bindingAndBlack[0];
		// check CSP
		if (InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_24_4_checkCSP_expressionFB(csp)) {
			// ForEach match corr context
			for (Object[] result5_black : InterfaceNodeRuleImpl
					.pattern_InterfaceNodeRule_24_5_matchcorrcontext_blackFBBBB(rootPackage, graph, sourceMatch,
							targetMatch)) {
				ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result5_black[0];
				Object[] result5_green = InterfaceNodeRuleImpl
						.pattern_InterfaceNodeRule_24_5_matchcorrcontext_greenBBBF(graphToPackage, sourceMatch,
								targetMatch);
				CCMatch ccMatch = (CCMatch) result5_green[3];

				// create correspondence
				Object[] result6_black = InterfaceNodeRuleImpl
						.pattern_InterfaceNodeRule_24_6_createcorrespondence_blackBBBBB(pInterface, rootPackage, graph,
								eClass, ccMatch);
				if (result6_black == null) {
					throw new RuntimeException("Pattern matching in node [create correspondence] failed."
							+ " Variables: " + "[pInterface] = " + pInterface + ", " + "[rootPackage] = " + rootPackage
							+ ", " + "[graph] = " + graph + ", " + "[eClass] = " + eClass + ", " + "[ccMatch] = "
							+ ccMatch + ".");
				}
				InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_24_6_createcorrespondence_greenBFBB(pInterface, eClass,
						ccMatch);
				// PNodeToEClassifier pInterfaceToEClass = (PNodeToEClassifier) result6_green[1];

				// add to returned result
				Object[] result7_black = InterfaceNodeRuleImpl
						.pattern_InterfaceNodeRule_24_7_addtoreturnedresult_blackBB(result, ccMatch);
				if (result7_black == null) {
					throw new RuntimeException("Pattern matching in node [add to returned result] failed."
							+ " Variables: " + "[result] = " + result + ", " + "[ccMatch] = " + ccMatch + ".");
				}
				InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_24_7_addtoreturnedresult_greenBB(result, ccMatch);

			}

		} else {
		}
		return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_24_8_expressionFB(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isApplicable_solveCsp_CC(PInterface pInterface, EPackage rootPackage, ClassGraph graph, EClass eClass,
			Match sourceMatch, Match targetMatch) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();

		// Create literals

		// Create attribute variables
		Variable var_pInterface_label = CSPFactoryHelper.eINSTANCE.createVariable("pInterface.label", true, csp);
		var_pInterface_label.setValue(pInterface.getLabel());
		var_pInterface_label.setType("String");
		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass.name", true, csp);
		var_eClass_name.setValue(eClass.getName());
		var_eClass_name.setType("String");

		// Create unbound variables

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pInterface_label, var_eClass_name);
		return csp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isApplicable_checkCsp_CC(CSP csp) {
		return csp.check();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDEC_FWD(PInterface pInterface, ClassGraph graph) {// match tgg pattern
		Object[] result1_black = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_27_1_matchtggpattern_blackBB(pInterface, graph);
		if (result1_black != null) {
			return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_27_2_expressionF();
		} else {
			return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_27_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDEC_BWD(EPackage rootPackage, EClass eClass) {// match tgg pattern
		Object[] result1_black = InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_28_1_matchtggpattern_blackBB(rootPackage, eClass);
		if (result1_black != null) {
			InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_28_1_matchtggpattern_greenB(eClass);

			return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_28_2_expressionF();
		} else {
			return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_28_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelgeneratorRuleResult generateModel(RuleEntryContainer ruleEntryContainer,
			ClassGraphToEPackage graphToPackageParameter) {
		// create result
		Object[] result1_black = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_29_1_createresult_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [create result] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_29_1_createresult_greenFF();
		IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result1_green[0];
		ModelgeneratorRuleResult ruleResult = (ModelgeneratorRuleResult) result1_green[1];

		// ForEach is applicable core
		for (Object[] result2_black : InterfaceNodeRuleImpl
				.pattern_InterfaceNodeRule_29_2_isapplicablecore_blackFFFFBB(ruleEntryContainer, ruleResult)) {
			// RuleEntryList graphToPackageList = (RuleEntryList) result2_black[0];
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result2_black[1];
			EPackage rootPackage = (EPackage) result2_black[2];
			ClassGraph graph = (ClassGraph) result2_black[3];

			// solve CSP
			Object[] result3_bindingAndBlack = InterfaceNodeRuleImpl
					.pattern_InterfaceNodeRule_29_3_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch,
							graphToPackage, rootPackage, graph, ruleResult);
			if (result3_bindingAndBlack == null) {
				throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: " + "[this] = "
						+ this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", " + "[graphToPackage] = "
						+ graphToPackage + ", " + "[rootPackage] = " + rootPackage + ", " + "[graph] = " + graph + ", "
						+ "[ruleResult] = " + ruleResult + ".");
			}
			CSP csp = (CSP) result3_bindingAndBlack[0];
			// check CSP
			if (InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_29_4_checkCSP_expressionFBB(this, csp)) {
				// check nacs
				Object[] result5_black = InterfaceNodeRuleImpl
						.pattern_InterfaceNodeRule_29_5_checknacs_blackBBB(graphToPackage, rootPackage, graph);
				if (result5_black != null) {

					// perform
					Object[] result6_black = InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_29_6_perform_blackBBBB(
							graphToPackage, rootPackage, graph, ruleResult);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [perform] failed." + " Variables: "
								+ "[graphToPackage] = " + graphToPackage + ", " + "[rootPackage] = " + rootPackage
								+ ", " + "[graph] = " + graph + ", " + "[ruleResult] = " + ruleResult + ".");
					}
					InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_29_6_perform_greenFFBBFBB(rootPackage, graph,
							ruleResult, csp);
					// PInterface pInterface = (PInterface) result6_green[0];
					// PNodeToEClassifier pInterfaceToEClass = (PNodeToEClassifier) result6_green[1];
					// EClass eClass = (EClass) result6_green[4];

				} else {
				}

			} else {
			}

		}
		return InterfaceNodeRuleImpl.pattern_InterfaceNodeRule_29_7_expressionFB(ruleResult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP generateModel_solveCsp_BWD(IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, ClassGraph graph, ModelgeneratorRuleResult ruleResult) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables

		// Create unbound variables
		Variable var_pInterface_label = CSPFactoryHelper.eINSTANCE.createVariable("pInterface.label", csp);
		var_pInterface_label.setType("String");
		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass.name", csp);
		var_eClass_name.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pInterface_label, var_eClass_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("graphToPackage", graphToPackage);
		isApplicableMatch.registerObject("rootPackage", rootPackage);
		isApplicableMatch.registerObject("graph", graph);
		return csp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean generateModel_checkCsp_BWD(CSP csp) {
		return csp.check();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPROPRIATE_FWD__MATCH_PINTERFACE_CLASSGRAPH:
			return isAppropriate_FWD((Match) arguments.get(0), (PInterface) arguments.get(1),
					(ClassGraph) arguments.get(2));
		case RulesPackage.INTERFACE_NODE_RULE___PERFORM_FWD__ISAPPLICABLEMATCH:
			return perform_FWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPLICABLE_FWD__MATCH:
			return isApplicable_FWD((Match) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_PINTERFACE_CLASSGRAPH:
			registerObjectsToMatch_FWD((Match) arguments.get(0), (PInterface) arguments.get(1),
					(ClassGraph) arguments.get(2));
			return null;
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_PINTERFACE_CLASSGRAPH:
			return isAppropriate_solveCsp_FWD((Match) arguments.get(0), (PInterface) arguments.get(1),
					(ClassGraph) arguments.get(2));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPROPRIATE_CHECK_CSP_FWD__CSP:
			return isAppropriate_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_PINTERFACE_CLASSGRAPHTOEPACKAGE_EPACKAGE_CLASSGRAPH:
			return isApplicable_solveCsp_FWD((IsApplicableMatch) arguments.get(0), (PInterface) arguments.get(1),
					(ClassGraphToEPackage) arguments.get(2), (EPackage) arguments.get(3),
					(ClassGraph) arguments.get(4));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPLICABLE_CHECK_CSP_FWD__CSP:
			return isApplicable_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_FWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6));
			return null;
		case RulesPackage.INTERFACE_NODE_RULE___CHECK_TYPES_FWD__MATCH:
			return checkTypes_FWD((Match) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPROPRIATE_BWD__MATCH_EPACKAGE_ECLASS:
			return isAppropriate_BWD((Match) arguments.get(0), (EPackage) arguments.get(1), (EClass) arguments.get(2));
		case RulesPackage.INTERFACE_NODE_RULE___PERFORM_BWD__ISAPPLICABLEMATCH:
			return perform_BWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPLICABLE_BWD__MATCH:
			return isApplicable_BWD((Match) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_EPACKAGE_ECLASS:
			registerObjectsToMatch_BWD((Match) arguments.get(0), (EPackage) arguments.get(1),
					(EClass) arguments.get(2));
			return null;
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_EPACKAGE_ECLASS:
			return isAppropriate_solveCsp_BWD((Match) arguments.get(0), (EPackage) arguments.get(1),
					(EClass) arguments.get(2));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPROPRIATE_CHECK_CSP_BWD__CSP:
			return isAppropriate_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_CLASSGRAPHTOEPACKAGE_EPACKAGE_CLASSGRAPH_ECLASS:
			return isApplicable_solveCsp_BWD((IsApplicableMatch) arguments.get(0),
					(ClassGraphToEPackage) arguments.get(1), (EPackage) arguments.get(2), (ClassGraph) arguments.get(3),
					(EClass) arguments.get(4));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPLICABLE_CHECK_CSP_BWD__CSP:
			return isApplicable_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_BWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6));
			return null;
		case RulesPackage.INTERFACE_NODE_RULE___CHECK_TYPES_BWD__MATCH:
			return checkTypes_BWD((Match) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPROPRIATE_BWD_EMOFLON_EDGE_3__EMOFLONEDGE:
			return isAppropriate_BWD_EMoflonEdge_3((EMoflonEdge) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPROPRIATE_FWD_EMOFLON_EDGE_3__EMOFLONEDGE:
			return isAppropriate_FWD_EMoflonEdge_3((EMoflonEdge) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH:
			return checkAttributes_FWD((TripleMatch) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH:
			return checkAttributes_BWD((TripleMatch) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPLICABLE_CC__MATCH_MATCH:
			return isApplicable_CC((Match) arguments.get(0), (Match) arguments.get(1));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPLICABLE_SOLVE_CSP_CC__PINTERFACE_EPACKAGE_CLASSGRAPH_ECLASS_MATCH_MATCH:
			return isApplicable_solveCsp_CC((PInterface) arguments.get(0), (EPackage) arguments.get(1),
					(ClassGraph) arguments.get(2), (EClass) arguments.get(3), (Match) arguments.get(4),
					(Match) arguments.get(5));
		case RulesPackage.INTERFACE_NODE_RULE___IS_APPLICABLE_CHECK_CSP_CC__CSP:
			return isApplicable_checkCsp_CC((CSP) arguments.get(0));
		case RulesPackage.INTERFACE_NODE_RULE___CHECK_DEC_FWD__PINTERFACE_CLASSGRAPH:
			return checkDEC_FWD((PInterface) arguments.get(0), (ClassGraph) arguments.get(1));
		case RulesPackage.INTERFACE_NODE_RULE___CHECK_DEC_BWD__EPACKAGE_ECLASS:
			return checkDEC_BWD((EPackage) arguments.get(0), (EClass) arguments.get(1));
		case RulesPackage.INTERFACE_NODE_RULE___GENERATE_MODEL__RULEENTRYCONTAINER_CLASSGRAPHTOEPACKAGE:
			return generateModel((RuleEntryContainer) arguments.get(0), (ClassGraphToEPackage) arguments.get(1));
		case RulesPackage.INTERFACE_NODE_RULE___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_CLASSGRAPHTOEPACKAGE_EPACKAGE_CLASSGRAPH_MODELGENERATORRULERESULT:
			return generateModel_solveCsp_BWD((IsApplicableMatch) arguments.get(0),
					(ClassGraphToEPackage) arguments.get(1), (EPackage) arguments.get(2), (ClassGraph) arguments.get(3),
					(ModelgeneratorRuleResult) arguments.get(4));
		case RulesPackage.INTERFACE_NODE_RULE___GENERATE_MODEL_CHECK_CSP_BWD__CSP:
			return generateModel_checkCsp_BWD((CSP) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	public static final Object[] pattern_InterfaceNodeRule_0_1_initialbindings_blackBBBB(InterfaceNodeRule _this,
			Match match, PInterface pInterface, ClassGraph graph) {
		return new Object[] { _this, match, pInterface, graph };
	}

	public static final Object[] pattern_InterfaceNodeRule_0_2_SolveCSP_bindingFBBBB(InterfaceNodeRule _this,
			Match match, PInterface pInterface, ClassGraph graph) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_FWD(match, pInterface, graph);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, pInterface, graph };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_0_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_InterfaceNodeRule_0_2_SolveCSP_bindingAndBlackFBBBB(InterfaceNodeRule _this,
			Match match, PInterface pInterface, ClassGraph graph) {
		Object[] result_pattern_InterfaceNodeRule_0_2_SolveCSP_binding = pattern_InterfaceNodeRule_0_2_SolveCSP_bindingFBBBB(
				_this, match, pInterface, graph);
		if (result_pattern_InterfaceNodeRule_0_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_InterfaceNodeRule_0_2_SolveCSP_binding[0];

			Object[] result_pattern_InterfaceNodeRule_0_2_SolveCSP_black = pattern_InterfaceNodeRule_0_2_SolveCSP_blackB(
					csp);
			if (result_pattern_InterfaceNodeRule_0_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, pInterface, graph };
			}
		}
		return null;
	}

	public static final boolean pattern_InterfaceNodeRule_0_3_CheckCSP_expressionFBB(InterfaceNodeRule _this, CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_0_4_collectelementstobetranslated_blackBBB(Match match,
			PInterface pInterface, ClassGraph graph) {
		return new Object[] { match, pInterface, graph };
	}

	public static final Object[] pattern_InterfaceNodeRule_0_4_collectelementstobetranslated_greenBBBFF(Match match,
			PInterface pInterface, ClassGraph graph) {
		EMoflonEdge pInterface__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pInterface____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getToBeTranslatedNodes().add(pInterface);
		String pInterface__graph____graph_name_prime = "graph";
		String graph__pInterface____nodes_name_prime = "nodes";
		pInterface__graph____graph.setSrc(pInterface);
		pInterface__graph____graph.setTrg(graph);
		match.getToBeTranslatedEdges().add(pInterface__graph____graph);
		graph__pInterface____nodes.setSrc(graph);
		graph__pInterface____nodes.setTrg(pInterface);
		match.getToBeTranslatedEdges().add(graph__pInterface____nodes);
		pInterface__graph____graph.setName(pInterface__graph____graph_name_prime);
		graph__pInterface____nodes.setName(graph__pInterface____nodes_name_prime);
		return new Object[] { match, pInterface, graph, pInterface__graph____graph, graph__pInterface____nodes };
	}

	public static final Object[] pattern_InterfaceNodeRule_0_5_collectcontextelements_blackBBB(Match match,
			PInterface pInterface, ClassGraph graph) {
		return new Object[] { match, pInterface, graph };
	}

	public static final Object[] pattern_InterfaceNodeRule_0_5_collectcontextelements_greenBB(Match match,
			ClassGraph graph) {
		match.getContextNodes().add(graph);
		return new Object[] { match, graph };
	}

	public static final void pattern_InterfaceNodeRule_0_6_registerobjectstomatch_expressionBBBB(
			InterfaceNodeRule _this, Match match, PInterface pInterface, ClassGraph graph) {
		_this.registerObjectsToMatch_FWD(match, pInterface, graph);

	}

	public static final boolean pattern_InterfaceNodeRule_0_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_InterfaceNodeRule_0_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_1_1_performtransformation_bindingFFFFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("pInterface");
		EObject _localVariable_1 = isApplicableMatch.getObject("graphToPackage");
		EObject _localVariable_2 = isApplicableMatch.getObject("rootPackage");
		EObject _localVariable_3 = isApplicableMatch.getObject("graph");
		EObject tmpPInterface = _localVariable_0;
		EObject tmpGraphToPackage = _localVariable_1;
		EObject tmpRootPackage = _localVariable_2;
		EObject tmpGraph = _localVariable_3;
		if (tmpPInterface instanceof PInterface) {
			PInterface pInterface = (PInterface) tmpPInterface;
			if (tmpGraphToPackage instanceof ClassGraphToEPackage) {
				ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) tmpGraphToPackage;
				if (tmpRootPackage instanceof EPackage) {
					EPackage rootPackage = (EPackage) tmpRootPackage;
					if (tmpGraph instanceof ClassGraph) {
						ClassGraph graph = (ClassGraph) tmpGraph;
						return new Object[] { pInterface, graphToPackage, rootPackage, graph, isApplicableMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_1_1_performtransformation_blackBBBBFBB(PInterface pInterface,
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph, InterfaceNodeRule _this,
			IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { pInterface, graphToPackage, rootPackage, graph, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_1_1_performtransformation_bindingAndBlackFFFFFBB(
			InterfaceNodeRule _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_InterfaceNodeRule_1_1_performtransformation_binding = pattern_InterfaceNodeRule_1_1_performtransformation_bindingFFFFB(
				isApplicableMatch);
		if (result_pattern_InterfaceNodeRule_1_1_performtransformation_binding != null) {
			PInterface pInterface = (PInterface) result_pattern_InterfaceNodeRule_1_1_performtransformation_binding[0];
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result_pattern_InterfaceNodeRule_1_1_performtransformation_binding[1];
			EPackage rootPackage = (EPackage) result_pattern_InterfaceNodeRule_1_1_performtransformation_binding[2];
			ClassGraph graph = (ClassGraph) result_pattern_InterfaceNodeRule_1_1_performtransformation_binding[3];

			Object[] result_pattern_InterfaceNodeRule_1_1_performtransformation_black = pattern_InterfaceNodeRule_1_1_performtransformation_blackBBBBFBB(
					pInterface, graphToPackage, rootPackage, graph, _this, isApplicableMatch);
			if (result_pattern_InterfaceNodeRule_1_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_InterfaceNodeRule_1_1_performtransformation_black[4];

				return new Object[] { pInterface, graphToPackage, rootPackage, graph, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_1_1_performtransformation_greenBFBFB(PInterface pInterface,
			EPackage rootPackage, CSP csp) {
		PNodeToEClassifier pInterfaceToEClass = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		boolean eClass_interface_prime = Boolean.valueOf(true);
		Object _localVariable_0 = csp.getValue("eClass", "name");
		pInterfaceToEClass.setSource(pInterface);
		pInterfaceToEClass.setTarget(eClass);
		rootPackage.getEClassifiers().add(eClass);
		eClass.setInterface(Boolean.valueOf(eClass_interface_prime));
		String eClass_name_prime = (String) _localVariable_0;
		eClass.setName(eClass_name_prime);
		return new Object[] { pInterface, pInterfaceToEClass, rootPackage, eClass, csp };
	}

	public static final Object[] pattern_InterfaceNodeRule_1_2_collecttranslatedelements_blackBBB(PInterface pInterface,
			PNodeToEClassifier pInterfaceToEClass, EClass eClass) {
		return new Object[] { pInterface, pInterfaceToEClass, eClass };
	}

	public static final Object[] pattern_InterfaceNodeRule_1_2_collecttranslatedelements_greenFBBB(
			PInterface pInterface, PNodeToEClassifier pInterfaceToEClass, EClass eClass) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getTranslatedElements().add(pInterface);
		ruleresult.getCreatedLinkElements().add(pInterfaceToEClass);
		ruleresult.getCreatedElements().add(eClass);
		return new Object[] { ruleresult, pInterface, pInterfaceToEClass, eClass };
	}

	public static final Object[] pattern_InterfaceNodeRule_1_3_bookkeepingforedges_blackBBBBBBB(
			PerformRuleResult ruleresult, EObject pInterface, EObject graphToPackage, EObject pInterfaceToEClass,
			EObject rootPackage, EObject graph, EObject eClass) {
		if (!pInterface.equals(pInterfaceToEClass)) {
			if (!pInterface.equals(rootPackage)) {
				if (!graphToPackage.equals(pInterface)) {
					if (!graphToPackage.equals(pInterfaceToEClass)) {
						if (!graphToPackage.equals(rootPackage)) {
							if (!pInterfaceToEClass.equals(rootPackage)) {
								if (!graph.equals(pInterface)) {
									if (!graph.equals(graphToPackage)) {
										if (!graph.equals(pInterfaceToEClass)) {
											if (!graph.equals(rootPackage)) {
												if (!eClass.equals(pInterface)) {
													if (!eClass.equals(graphToPackage)) {
														if (!eClass.equals(pInterfaceToEClass)) {
															if (!eClass.equals(rootPackage)) {
																if (!eClass.equals(graph)) {
																	return new Object[] { ruleresult, pInterface,
																			graphToPackage, pInterfaceToEClass,
																			rootPackage, graph, eClass };
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_1_3_bookkeepingforedges_greenBBBBBBFFFFFF(
			PerformRuleResult ruleresult, EObject pInterface, EObject pInterfaceToEClass, EObject rootPackage,
			EObject graph, EObject eClass) {
		EMoflonEdge pInterface__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pInterface____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pInterfaceToEClass__pInterface____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pInterfaceToEClass__eClass____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge eClass__rootPackage____ePackage = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rootPackage__eClass____eClassifiers = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "InterfaceNodeRule";
		String pInterface__graph____graph_name_prime = "graph";
		String graph__pInterface____nodes_name_prime = "nodes";
		String pInterfaceToEClass__pInterface____source_name_prime = "source";
		String pInterfaceToEClass__eClass____target_name_prime = "target";
		String eClass__rootPackage____ePackage_name_prime = "ePackage";
		String rootPackage__eClass____eClassifiers_name_prime = "eClassifiers";
		pInterface__graph____graph.setSrc(pInterface);
		pInterface__graph____graph.setTrg(graph);
		ruleresult.getTranslatedEdges().add(pInterface__graph____graph);
		graph__pInterface____nodes.setSrc(graph);
		graph__pInterface____nodes.setTrg(pInterface);
		ruleresult.getTranslatedEdges().add(graph__pInterface____nodes);
		pInterfaceToEClass__pInterface____source.setSrc(pInterfaceToEClass);
		pInterfaceToEClass__pInterface____source.setTrg(pInterface);
		ruleresult.getCreatedEdges().add(pInterfaceToEClass__pInterface____source);
		pInterfaceToEClass__eClass____target.setSrc(pInterfaceToEClass);
		pInterfaceToEClass__eClass____target.setTrg(eClass);
		ruleresult.getCreatedEdges().add(pInterfaceToEClass__eClass____target);
		eClass__rootPackage____ePackage.setSrc(eClass);
		eClass__rootPackage____ePackage.setTrg(rootPackage);
		ruleresult.getCreatedEdges().add(eClass__rootPackage____ePackage);
		rootPackage__eClass____eClassifiers.setSrc(rootPackage);
		rootPackage__eClass____eClassifiers.setTrg(eClass);
		ruleresult.getCreatedEdges().add(rootPackage__eClass____eClassifiers);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		pInterface__graph____graph.setName(pInterface__graph____graph_name_prime);
		graph__pInterface____nodes.setName(graph__pInterface____nodes_name_prime);
		pInterfaceToEClass__pInterface____source.setName(pInterfaceToEClass__pInterface____source_name_prime);
		pInterfaceToEClass__eClass____target.setName(pInterfaceToEClass__eClass____target_name_prime);
		eClass__rootPackage____ePackage.setName(eClass__rootPackage____ePackage_name_prime);
		rootPackage__eClass____eClassifiers.setName(rootPackage__eClass____eClassifiers_name_prime);
		return new Object[] { ruleresult, pInterface, pInterfaceToEClass, rootPackage, graph, eClass,
				pInterface__graph____graph, graph__pInterface____nodes, pInterfaceToEClass__pInterface____source,
				pInterfaceToEClass__eClass____target, eClass__rootPackage____ePackage,
				rootPackage__eClass____eClassifiers };
	}

	public static final void pattern_InterfaceNodeRule_1_5_registerobjects_expressionBBBBBBBB(InterfaceNodeRule _this,
			PerformRuleResult ruleresult, EObject pInterface, EObject graphToPackage, EObject pInterfaceToEClass,
			EObject rootPackage, EObject graph, EObject eClass) {
		_this.registerObjects_FWD(ruleresult, pInterface, graphToPackage, pInterfaceToEClass, rootPackage, graph,
				eClass);

	}

	public static final PerformRuleResult pattern_InterfaceNodeRule_1_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_2_1_preparereturnvalue_bindingFB(InterfaceNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_2_1_preparereturnvalue_blackFBB(EClass eClass,
			InterfaceNodeRule _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_FWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_2_1_preparereturnvalue_bindingAndBlackFFB(
			InterfaceNodeRule _this) {
		Object[] result_pattern_InterfaceNodeRule_2_1_preparereturnvalue_binding = pattern_InterfaceNodeRule_2_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_InterfaceNodeRule_2_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_InterfaceNodeRule_2_1_preparereturnvalue_binding[0];

			Object[] result_pattern_InterfaceNodeRule_2_1_preparereturnvalue_black = pattern_InterfaceNodeRule_2_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_InterfaceNodeRule_2_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_InterfaceNodeRule_2_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_2_1_preparereturnvalue_greenBF(EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "InterfaceNodeRule";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_InterfaceNodeRule_2_2_corematch_bindingFFB(Match match) {
		EObject _localVariable_0 = match.getObject("pInterface");
		EObject _localVariable_1 = match.getObject("graph");
		EObject tmpPInterface = _localVariable_0;
		EObject tmpGraph = _localVariable_1;
		if (tmpPInterface instanceof PInterface) {
			PInterface pInterface = (PInterface) tmpPInterface;
			if (tmpGraph instanceof ClassGraph) {
				ClassGraph graph = (ClassGraph) tmpGraph;
				return new Object[] { pInterface, graph, match };
			}
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_InterfaceNodeRule_2_2_corematch_blackBFFBB(PInterface pInterface,
			ClassGraph graph, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (ClassGraphToEPackage graphToPackage : org.moflon.core.utilities.eMoflonEMFUtil
				.getOppositeReferenceTyped(graph, ClassGraphToEPackage.class, "source")) {
			EPackage rootPackage = graphToPackage.getTarget();
			if (rootPackage != null) {
				_result.add(new Object[] { pInterface, graphToPackage, rootPackage, graph, match });
			}

		}
		return _result;
	}

	public static final Iterable<Object[]> pattern_InterfaceNodeRule_2_3_findcontext_blackBBBB(PInterface pInterface,
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (graph.equals(pInterface.getGraph())) {
			if (rootPackage.equals(graphToPackage.getTarget())) {
				if (graph.equals(graphToPackage.getSource())) {
					_result.add(new Object[] { pInterface, graphToPackage, rootPackage, graph });
				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_2_3_findcontext_greenBBBBFFFFF(PInterface pInterface,
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		EMoflonEdge pInterface__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pInterface____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graphToPackage__rootPackage____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graphToPackage__graph____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String pInterface__graph____graph_name_prime = "graph";
		String graph__pInterface____nodes_name_prime = "nodes";
		String graphToPackage__rootPackage____target_name_prime = "target";
		String graphToPackage__graph____source_name_prime = "source";
		isApplicableMatch.getAllContextElements().add(pInterface);
		isApplicableMatch.getAllContextElements().add(graphToPackage);
		isApplicableMatch.getAllContextElements().add(rootPackage);
		isApplicableMatch.getAllContextElements().add(graph);
		pInterface__graph____graph.setSrc(pInterface);
		pInterface__graph____graph.setTrg(graph);
		isApplicableMatch.getAllContextElements().add(pInterface__graph____graph);
		graph__pInterface____nodes.setSrc(graph);
		graph__pInterface____nodes.setTrg(pInterface);
		isApplicableMatch.getAllContextElements().add(graph__pInterface____nodes);
		graphToPackage__rootPackage____target.setSrc(graphToPackage);
		graphToPackage__rootPackage____target.setTrg(rootPackage);
		isApplicableMatch.getAllContextElements().add(graphToPackage__rootPackage____target);
		graphToPackage__graph____source.setSrc(graphToPackage);
		graphToPackage__graph____source.setTrg(graph);
		isApplicableMatch.getAllContextElements().add(graphToPackage__graph____source);
		pInterface__graph____graph.setName(pInterface__graph____graph_name_prime);
		graph__pInterface____nodes.setName(graph__pInterface____nodes_name_prime);
		graphToPackage__rootPackage____target.setName(graphToPackage__rootPackage____target_name_prime);
		graphToPackage__graph____source.setName(graphToPackage__graph____source_name_prime);
		return new Object[] { pInterface, graphToPackage, rootPackage, graph, isApplicableMatch,
				pInterface__graph____graph, graph__pInterface____nodes, graphToPackage__rootPackage____target,
				graphToPackage__graph____source };
	}

	public static final Object[] pattern_InterfaceNodeRule_2_4_solveCSP_bindingFBBBBBB(InterfaceNodeRule _this,
			IsApplicableMatch isApplicableMatch, PInterface pInterface, ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, ClassGraph graph) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_FWD(isApplicableMatch, pInterface, graphToPackage,
				rootPackage, graph);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, pInterface, graphToPackage, rootPackage, graph };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_2_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_InterfaceNodeRule_2_4_solveCSP_bindingAndBlackFBBBBBB(InterfaceNodeRule _this,
			IsApplicableMatch isApplicableMatch, PInterface pInterface, ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, ClassGraph graph) {
		Object[] result_pattern_InterfaceNodeRule_2_4_solveCSP_binding = pattern_InterfaceNodeRule_2_4_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, pInterface, graphToPackage, rootPackage, graph);
		if (result_pattern_InterfaceNodeRule_2_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_InterfaceNodeRule_2_4_solveCSP_binding[0];

			Object[] result_pattern_InterfaceNodeRule_2_4_solveCSP_black = pattern_InterfaceNodeRule_2_4_solveCSP_blackB(
					csp);
			if (result_pattern_InterfaceNodeRule_2_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, pInterface, graphToPackage, rootPackage, graph };
			}
		}
		return null;
	}

	public static final boolean pattern_InterfaceNodeRule_2_5_checkCSP_expressionFBB(InterfaceNodeRule _this, CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_2_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_InterfaceNodeRule_2_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "InterfaceNodeRule";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_InterfaceNodeRule_2_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_10_1_initialbindings_blackBBBB(InterfaceNodeRule _this,
			Match match, EPackage rootPackage, EClass eClass) {
		return new Object[] { _this, match, rootPackage, eClass };
	}

	public static final Object[] pattern_InterfaceNodeRule_10_2_SolveCSP_bindingFBBBB(InterfaceNodeRule _this,
			Match match, EPackage rootPackage, EClass eClass) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_BWD(match, rootPackage, eClass);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, rootPackage, eClass };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_10_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_InterfaceNodeRule_10_2_SolveCSP_bindingAndBlackFBBBB(InterfaceNodeRule _this,
			Match match, EPackage rootPackage, EClass eClass) {
		Object[] result_pattern_InterfaceNodeRule_10_2_SolveCSP_binding = pattern_InterfaceNodeRule_10_2_SolveCSP_bindingFBBBB(
				_this, match, rootPackage, eClass);
		if (result_pattern_InterfaceNodeRule_10_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_InterfaceNodeRule_10_2_SolveCSP_binding[0];

			Object[] result_pattern_InterfaceNodeRule_10_2_SolveCSP_black = pattern_InterfaceNodeRule_10_2_SolveCSP_blackB(
					csp);
			if (result_pattern_InterfaceNodeRule_10_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, rootPackage, eClass };
			}
		}
		return null;
	}

	public static final boolean pattern_InterfaceNodeRule_10_3_CheckCSP_expressionFBB(InterfaceNodeRule _this,
			CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_10_4_collectelementstobetranslated_blackBBB(Match match,
			EPackage rootPackage, EClass eClass) {
		return new Object[] { match, rootPackage, eClass };
	}

	public static final Object[] pattern_InterfaceNodeRule_10_4_collectelementstobetranslated_greenBBBFF(Match match,
			EPackage rootPackage, EClass eClass) {
		EMoflonEdge eClass__rootPackage____ePackage = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rootPackage__eClass____eClassifiers = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getToBeTranslatedNodes().add(eClass);
		String eClass__rootPackage____ePackage_name_prime = "ePackage";
		String rootPackage__eClass____eClassifiers_name_prime = "eClassifiers";
		eClass__rootPackage____ePackage.setSrc(eClass);
		eClass__rootPackage____ePackage.setTrg(rootPackage);
		match.getToBeTranslatedEdges().add(eClass__rootPackage____ePackage);
		rootPackage__eClass____eClassifiers.setSrc(rootPackage);
		rootPackage__eClass____eClassifiers.setTrg(eClass);
		match.getToBeTranslatedEdges().add(rootPackage__eClass____eClassifiers);
		eClass__rootPackage____ePackage.setName(eClass__rootPackage____ePackage_name_prime);
		rootPackage__eClass____eClassifiers.setName(rootPackage__eClass____eClassifiers_name_prime);
		return new Object[] { match, rootPackage, eClass, eClass__rootPackage____ePackage,
				rootPackage__eClass____eClassifiers };
	}

	public static final Object[] pattern_InterfaceNodeRule_10_5_collectcontextelements_blackBBB(Match match,
			EPackage rootPackage, EClass eClass) {
		return new Object[] { match, rootPackage, eClass };
	}

	public static final Object[] pattern_InterfaceNodeRule_10_5_collectcontextelements_greenBB(Match match,
			EPackage rootPackage) {
		match.getContextNodes().add(rootPackage);
		return new Object[] { match, rootPackage };
	}

	public static final void pattern_InterfaceNodeRule_10_6_registerobjectstomatch_expressionBBBB(
			InterfaceNodeRule _this, Match match, EPackage rootPackage, EClass eClass) {
		_this.registerObjectsToMatch_BWD(match, rootPackage, eClass);

	}

	public static final boolean pattern_InterfaceNodeRule_10_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_InterfaceNodeRule_10_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_11_1_performtransformation_bindingFFFFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("graphToPackage");
		EObject _localVariable_1 = isApplicableMatch.getObject("rootPackage");
		EObject _localVariable_2 = isApplicableMatch.getObject("graph");
		EObject _localVariable_3 = isApplicableMatch.getObject("eClass");
		EObject tmpGraphToPackage = _localVariable_0;
		EObject tmpRootPackage = _localVariable_1;
		EObject tmpGraph = _localVariable_2;
		EObject tmpEClass = _localVariable_3;
		if (tmpGraphToPackage instanceof ClassGraphToEPackage) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) tmpGraphToPackage;
			if (tmpRootPackage instanceof EPackage) {
				EPackage rootPackage = (EPackage) tmpRootPackage;
				if (tmpGraph instanceof ClassGraph) {
					ClassGraph graph = (ClassGraph) tmpGraph;
					if (tmpEClass instanceof EClass) {
						EClass eClass = (EClass) tmpEClass;
						return new Object[] { graphToPackage, rootPackage, graph, eClass, isApplicableMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_11_1_performtransformation_blackBBBBFBB(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph, EClass eClass,
			InterfaceNodeRule _this, IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { graphToPackage, rootPackage, graph, eClass, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_11_1_performtransformation_bindingAndBlackFFFFFBB(
			InterfaceNodeRule _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_InterfaceNodeRule_11_1_performtransformation_binding = pattern_InterfaceNodeRule_11_1_performtransformation_bindingFFFFB(
				isApplicableMatch);
		if (result_pattern_InterfaceNodeRule_11_1_performtransformation_binding != null) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result_pattern_InterfaceNodeRule_11_1_performtransformation_binding[0];
			EPackage rootPackage = (EPackage) result_pattern_InterfaceNodeRule_11_1_performtransformation_binding[1];
			ClassGraph graph = (ClassGraph) result_pattern_InterfaceNodeRule_11_1_performtransformation_binding[2];
			EClass eClass = (EClass) result_pattern_InterfaceNodeRule_11_1_performtransformation_binding[3];

			Object[] result_pattern_InterfaceNodeRule_11_1_performtransformation_black = pattern_InterfaceNodeRule_11_1_performtransformation_blackBBBBFBB(
					graphToPackage, rootPackage, graph, eClass, _this, isApplicableMatch);
			if (result_pattern_InterfaceNodeRule_11_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_InterfaceNodeRule_11_1_performtransformation_black[4];

				return new Object[] { graphToPackage, rootPackage, graph, eClass, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_11_1_performtransformation_greenFFBBB(ClassGraph graph,
			EClass eClass, CSP csp) {
		PInterface pInterface = LanguageFactory.eINSTANCE.createPInterface();
		PNodeToEClassifier pInterfaceToEClass = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		Object _localVariable_0 = csp.getValue("pInterface", "label");
		pInterface.setGraph(graph);
		pInterfaceToEClass.setSource(pInterface);
		pInterfaceToEClass.setTarget(eClass);
		String pInterface_label_prime = (String) _localVariable_0;
		pInterface.setLabel(pInterface_label_prime);
		return new Object[] { pInterface, pInterfaceToEClass, graph, eClass, csp };
	}

	public static final Object[] pattern_InterfaceNodeRule_11_2_collecttranslatedelements_blackBBB(
			PInterface pInterface, PNodeToEClassifier pInterfaceToEClass, EClass eClass) {
		return new Object[] { pInterface, pInterfaceToEClass, eClass };
	}

	public static final Object[] pattern_InterfaceNodeRule_11_2_collecttranslatedelements_greenFBBB(
			PInterface pInterface, PNodeToEClassifier pInterfaceToEClass, EClass eClass) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getCreatedElements().add(pInterface);
		ruleresult.getCreatedLinkElements().add(pInterfaceToEClass);
		ruleresult.getTranslatedElements().add(eClass);
		return new Object[] { ruleresult, pInterface, pInterfaceToEClass, eClass };
	}

	public static final Object[] pattern_InterfaceNodeRule_11_3_bookkeepingforedges_blackBBBBBBB(
			PerformRuleResult ruleresult, EObject pInterface, EObject graphToPackage, EObject pInterfaceToEClass,
			EObject rootPackage, EObject graph, EObject eClass) {
		if (!pInterface.equals(pInterfaceToEClass)) {
			if (!pInterface.equals(rootPackage)) {
				if (!graphToPackage.equals(pInterface)) {
					if (!graphToPackage.equals(pInterfaceToEClass)) {
						if (!graphToPackage.equals(rootPackage)) {
							if (!pInterfaceToEClass.equals(rootPackage)) {
								if (!graph.equals(pInterface)) {
									if (!graph.equals(graphToPackage)) {
										if (!graph.equals(pInterfaceToEClass)) {
											if (!graph.equals(rootPackage)) {
												if (!eClass.equals(pInterface)) {
													if (!eClass.equals(graphToPackage)) {
														if (!eClass.equals(pInterfaceToEClass)) {
															if (!eClass.equals(rootPackage)) {
																if (!eClass.equals(graph)) {
																	return new Object[] { ruleresult, pInterface,
																			graphToPackage, pInterfaceToEClass,
																			rootPackage, graph, eClass };
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_11_3_bookkeepingforedges_greenBBBBBBFFFFFF(
			PerformRuleResult ruleresult, EObject pInterface, EObject pInterfaceToEClass, EObject rootPackage,
			EObject graph, EObject eClass) {
		EMoflonEdge pInterface__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pInterface____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pInterfaceToEClass__pInterface____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pInterfaceToEClass__eClass____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge eClass__rootPackage____ePackage = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rootPackage__eClass____eClassifiers = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "InterfaceNodeRule";
		String pInterface__graph____graph_name_prime = "graph";
		String graph__pInterface____nodes_name_prime = "nodes";
		String pInterfaceToEClass__pInterface____source_name_prime = "source";
		String pInterfaceToEClass__eClass____target_name_prime = "target";
		String eClass__rootPackage____ePackage_name_prime = "ePackage";
		String rootPackage__eClass____eClassifiers_name_prime = "eClassifiers";
		pInterface__graph____graph.setSrc(pInterface);
		pInterface__graph____graph.setTrg(graph);
		ruleresult.getCreatedEdges().add(pInterface__graph____graph);
		graph__pInterface____nodes.setSrc(graph);
		graph__pInterface____nodes.setTrg(pInterface);
		ruleresult.getCreatedEdges().add(graph__pInterface____nodes);
		pInterfaceToEClass__pInterface____source.setSrc(pInterfaceToEClass);
		pInterfaceToEClass__pInterface____source.setTrg(pInterface);
		ruleresult.getCreatedEdges().add(pInterfaceToEClass__pInterface____source);
		pInterfaceToEClass__eClass____target.setSrc(pInterfaceToEClass);
		pInterfaceToEClass__eClass____target.setTrg(eClass);
		ruleresult.getCreatedEdges().add(pInterfaceToEClass__eClass____target);
		eClass__rootPackage____ePackage.setSrc(eClass);
		eClass__rootPackage____ePackage.setTrg(rootPackage);
		ruleresult.getTranslatedEdges().add(eClass__rootPackage____ePackage);
		rootPackage__eClass____eClassifiers.setSrc(rootPackage);
		rootPackage__eClass____eClassifiers.setTrg(eClass);
		ruleresult.getTranslatedEdges().add(rootPackage__eClass____eClassifiers);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		pInterface__graph____graph.setName(pInterface__graph____graph_name_prime);
		graph__pInterface____nodes.setName(graph__pInterface____nodes_name_prime);
		pInterfaceToEClass__pInterface____source.setName(pInterfaceToEClass__pInterface____source_name_prime);
		pInterfaceToEClass__eClass____target.setName(pInterfaceToEClass__eClass____target_name_prime);
		eClass__rootPackage____ePackage.setName(eClass__rootPackage____ePackage_name_prime);
		rootPackage__eClass____eClassifiers.setName(rootPackage__eClass____eClassifiers_name_prime);
		return new Object[] { ruleresult, pInterface, pInterfaceToEClass, rootPackage, graph, eClass,
				pInterface__graph____graph, graph__pInterface____nodes, pInterfaceToEClass__pInterface____source,
				pInterfaceToEClass__eClass____target, eClass__rootPackage____ePackage,
				rootPackage__eClass____eClassifiers };
	}

	public static final void pattern_InterfaceNodeRule_11_5_registerobjects_expressionBBBBBBBB(InterfaceNodeRule _this,
			PerformRuleResult ruleresult, EObject pInterface, EObject graphToPackage, EObject pInterfaceToEClass,
			EObject rootPackage, EObject graph, EObject eClass) {
		_this.registerObjects_BWD(ruleresult, pInterface, graphToPackage, pInterfaceToEClass, rootPackage, graph,
				eClass);

	}

	public static final PerformRuleResult pattern_InterfaceNodeRule_11_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_12_1_preparereturnvalue_bindingFB(InterfaceNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_12_1_preparereturnvalue_blackFBB(EClass eClass,
			InterfaceNodeRule _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_BWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_12_1_preparereturnvalue_bindingAndBlackFFB(
			InterfaceNodeRule _this) {
		Object[] result_pattern_InterfaceNodeRule_12_1_preparereturnvalue_binding = pattern_InterfaceNodeRule_12_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_InterfaceNodeRule_12_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_InterfaceNodeRule_12_1_preparereturnvalue_binding[0];

			Object[] result_pattern_InterfaceNodeRule_12_1_preparereturnvalue_black = pattern_InterfaceNodeRule_12_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_InterfaceNodeRule_12_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_InterfaceNodeRule_12_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_12_1_preparereturnvalue_greenBF(
			EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "InterfaceNodeRule";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_InterfaceNodeRule_12_2_corematch_bindingFFB(Match match) {
		EObject _localVariable_0 = match.getObject("rootPackage");
		EObject _localVariable_1 = match.getObject("eClass");
		EObject tmpRootPackage = _localVariable_0;
		EObject tmpEClass = _localVariable_1;
		if (tmpRootPackage instanceof EPackage) {
			EPackage rootPackage = (EPackage) tmpRootPackage;
			if (tmpEClass instanceof EClass) {
				EClass eClass = (EClass) tmpEClass;
				return new Object[] { rootPackage, eClass, match };
			}
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_InterfaceNodeRule_12_2_corematch_blackFBFBB(EPackage rootPackage,
			EClass eClass, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		boolean eClass_interface = eClass.isInterface();
		if (Boolean.valueOf(eClass_interface).equals(Boolean.valueOf(true))) {
			for (ClassGraphToEPackage graphToPackage : org.moflon.core.utilities.eMoflonEMFUtil
					.getOppositeReferenceTyped(rootPackage, ClassGraphToEPackage.class, "target")) {
				ClassGraph graph = graphToPackage.getSource();
				if (graph != null) {
					_result.add(new Object[] { graphToPackage, rootPackage, graph, eClass, match });
				}

			}
		}

		return _result;
	}

	public static final Iterable<Object[]> pattern_InterfaceNodeRule_12_3_findcontext_blackBBBB(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph, EClass eClass) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (rootPackage.equals(graphToPackage.getTarget())) {
			if (rootPackage.equals(eClass.getEPackage())) {
				if (graph.equals(graphToPackage.getSource())) {
					boolean eClass_interface = eClass.isInterface();
					if (Boolean.valueOf(eClass_interface).equals(Boolean.valueOf(true))) {
						_result.add(new Object[] { graphToPackage, rootPackage, graph, eClass });
					}

				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_12_3_findcontext_greenBBBBFFFFF(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph, EClass eClass) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		EMoflonEdge graphToPackage__rootPackage____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge eClass__rootPackage____ePackage = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rootPackage__eClass____eClassifiers = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graphToPackage__graph____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String graphToPackage__rootPackage____target_name_prime = "target";
		String eClass__rootPackage____ePackage_name_prime = "ePackage";
		String rootPackage__eClass____eClassifiers_name_prime = "eClassifiers";
		String graphToPackage__graph____source_name_prime = "source";
		isApplicableMatch.getAllContextElements().add(graphToPackage);
		isApplicableMatch.getAllContextElements().add(rootPackage);
		isApplicableMatch.getAllContextElements().add(graph);
		isApplicableMatch.getAllContextElements().add(eClass);
		graphToPackage__rootPackage____target.setSrc(graphToPackage);
		graphToPackage__rootPackage____target.setTrg(rootPackage);
		isApplicableMatch.getAllContextElements().add(graphToPackage__rootPackage____target);
		eClass__rootPackage____ePackage.setSrc(eClass);
		eClass__rootPackage____ePackage.setTrg(rootPackage);
		isApplicableMatch.getAllContextElements().add(eClass__rootPackage____ePackage);
		rootPackage__eClass____eClassifiers.setSrc(rootPackage);
		rootPackage__eClass____eClassifiers.setTrg(eClass);
		isApplicableMatch.getAllContextElements().add(rootPackage__eClass____eClassifiers);
		graphToPackage__graph____source.setSrc(graphToPackage);
		graphToPackage__graph____source.setTrg(graph);
		isApplicableMatch.getAllContextElements().add(graphToPackage__graph____source);
		graphToPackage__rootPackage____target.setName(graphToPackage__rootPackage____target_name_prime);
		eClass__rootPackage____ePackage.setName(eClass__rootPackage____ePackage_name_prime);
		rootPackage__eClass____eClassifiers.setName(rootPackage__eClass____eClassifiers_name_prime);
		graphToPackage__graph____source.setName(graphToPackage__graph____source_name_prime);
		return new Object[] { graphToPackage, rootPackage, graph, eClass, isApplicableMatch,
				graphToPackage__rootPackage____target, eClass__rootPackage____ePackage,
				rootPackage__eClass____eClassifiers, graphToPackage__graph____source };
	}

	public static final Object[] pattern_InterfaceNodeRule_12_4_solveCSP_bindingFBBBBBB(InterfaceNodeRule _this,
			IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage, EPackage rootPackage,
			ClassGraph graph, EClass eClass) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_BWD(isApplicableMatch, graphToPackage, rootPackage, graph,
				eClass);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, graphToPackage, rootPackage, graph, eClass };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_12_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_InterfaceNodeRule_12_4_solveCSP_bindingAndBlackFBBBBBB(InterfaceNodeRule _this,
			IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage, EPackage rootPackage,
			ClassGraph graph, EClass eClass) {
		Object[] result_pattern_InterfaceNodeRule_12_4_solveCSP_binding = pattern_InterfaceNodeRule_12_4_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, graphToPackage, rootPackage, graph, eClass);
		if (result_pattern_InterfaceNodeRule_12_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_InterfaceNodeRule_12_4_solveCSP_binding[0];

			Object[] result_pattern_InterfaceNodeRule_12_4_solveCSP_black = pattern_InterfaceNodeRule_12_4_solveCSP_blackB(
					csp);
			if (result_pattern_InterfaceNodeRule_12_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, graphToPackage, rootPackage, graph, eClass };
			}
		}
		return null;
	}

	public static final boolean pattern_InterfaceNodeRule_12_5_checkCSP_expressionFBB(InterfaceNodeRule _this,
			CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_12_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_InterfaceNodeRule_12_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "InterfaceNodeRule";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_InterfaceNodeRule_12_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_20_1_preparereturnvalue_bindingFB(InterfaceNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_20_1_preparereturnvalue_blackFBBF(EClass __eClass,
			InterfaceNodeRule _this) {
		for (EOperation __performOperation : __eClass.getEOperations()) {
			String __performOperation_name = __performOperation.getName();
			if (__performOperation_name.equals("isApplicable_BWD")) {
				for (EOperation isApplicableCC : __eClass.getEOperations()) {
					if (!__performOperation.equals(isApplicableCC)) {
						String isApplicableCC_name = isApplicableCC.getName();
						if (isApplicableCC_name.equals("isApplicable_CC")) {
							return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
						}

					}
				}
			}

		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_20_1_preparereturnvalue_bindingAndBlackFFBF(
			InterfaceNodeRule _this) {
		Object[] result_pattern_InterfaceNodeRule_20_1_preparereturnvalue_binding = pattern_InterfaceNodeRule_20_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_InterfaceNodeRule_20_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_InterfaceNodeRule_20_1_preparereturnvalue_binding[0];

			Object[] result_pattern_InterfaceNodeRule_20_1_preparereturnvalue_black = pattern_InterfaceNodeRule_20_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_InterfaceNodeRule_20_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_InterfaceNodeRule_20_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_InterfaceNodeRule_20_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_20_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_InterfaceNodeRule_20_2_testcorematchandDECs_blackFFB(
			EMoflonEdge _edge_ePackage) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		EObject tmpEClass = _edge_ePackage.getSrc();
		if (tmpEClass instanceof EClass) {
			EClass eClass = (EClass) tmpEClass;
			EObject tmpRootPackage = _edge_ePackage.getTrg();
			if (tmpRootPackage instanceof EPackage) {
				EPackage rootPackage = (EPackage) tmpRootPackage;
				if (rootPackage.equals(eClass.getEPackage())) {
					boolean eClass_interface = eClass.isInterface();
					if (Boolean.valueOf(eClass_interface).equals(Boolean.valueOf(true))) {
						_result.add(new Object[] { rootPackage, eClass, _edge_ePackage });
					}

				}
			}

		}

		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_20_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_InterfaceNodeRule_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
			InterfaceNodeRule _this, Match match, EPackage rootPackage, EClass eClass) {
		boolean _localVariable_0 = _this.isAppropriate_BWD(match, rootPackage, eClass);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_InterfaceNodeRule_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			InterfaceNodeRule _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_BWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_20_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_20_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_InterfaceNodeRule_20_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_21_1_preparereturnvalue_bindingFB(InterfaceNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_21_1_preparereturnvalue_blackFBBF(EClass __eClass,
			InterfaceNodeRule _this) {
		for (EOperation __performOperation : __eClass.getEOperations()) {
			String __performOperation_name = __performOperation.getName();
			if (__performOperation_name.equals("isApplicable_FWD")) {
				for (EOperation isApplicableCC : __eClass.getEOperations()) {
					if (!__performOperation.equals(isApplicableCC)) {
						String isApplicableCC_name = isApplicableCC.getName();
						if (isApplicableCC_name.equals("isApplicable_CC")) {
							return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
						}

					}
				}
			}

		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_21_1_preparereturnvalue_bindingAndBlackFFBF(
			InterfaceNodeRule _this) {
		Object[] result_pattern_InterfaceNodeRule_21_1_preparereturnvalue_binding = pattern_InterfaceNodeRule_21_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_InterfaceNodeRule_21_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_InterfaceNodeRule_21_1_preparereturnvalue_binding[0];

			Object[] result_pattern_InterfaceNodeRule_21_1_preparereturnvalue_black = pattern_InterfaceNodeRule_21_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_InterfaceNodeRule_21_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_InterfaceNodeRule_21_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_InterfaceNodeRule_21_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_21_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_InterfaceNodeRule_21_2_testcorematchandDECs_blackFFB(
			EMoflonEdge _edge_graph) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		EObject tmpPInterface = _edge_graph.getSrc();
		if (tmpPInterface instanceof PInterface) {
			PInterface pInterface = (PInterface) tmpPInterface;
			EObject tmpGraph = _edge_graph.getTrg();
			if (tmpGraph instanceof ClassGraph) {
				ClassGraph graph = (ClassGraph) tmpGraph;
				if (graph.equals(pInterface.getGraph())) {
					_result.add(new Object[] { pInterface, graph, _edge_graph });
				}
			}

		}

		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_21_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_InterfaceNodeRule_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
			InterfaceNodeRule _this, Match match, PInterface pInterface, ClassGraph graph) {
		boolean _localVariable_0 = _this.isAppropriate_FWD(match, pInterface, graph);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_InterfaceNodeRule_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			InterfaceNodeRule _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_FWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_21_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_21_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_InterfaceNodeRule_21_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_24_1_prepare_blackB(InterfaceNodeRule _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_InterfaceNodeRule_24_1_prepare_greenF() {
		IsApplicableRuleResult result = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		return new Object[] { result };
	}

	public static final Object[] pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_bindingFFFFBB(Match sourceMatch,
			Match targetMatch) {
		EObject _localVariable_0 = sourceMatch.getObject("pInterface");
		EObject _localVariable_1 = targetMatch.getObject("rootPackage");
		EObject _localVariable_2 = sourceMatch.getObject("graph");
		EObject _localVariable_3 = targetMatch.getObject("eClass");
		EObject tmpPInterface = _localVariable_0;
		EObject tmpRootPackage = _localVariable_1;
		EObject tmpGraph = _localVariable_2;
		EObject tmpEClass = _localVariable_3;
		if (tmpPInterface instanceof PInterface) {
			PInterface pInterface = (PInterface) tmpPInterface;
			if (tmpRootPackage instanceof EPackage) {
				EPackage rootPackage = (EPackage) tmpRootPackage;
				if (tmpGraph instanceof ClassGraph) {
					ClassGraph graph = (ClassGraph) tmpGraph;
					if (tmpEClass instanceof EClass) {
						EClass eClass = (EClass) tmpEClass;
						return new Object[] { pInterface, rootPackage, graph, eClass, sourceMatch, targetMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_blackBBBBBB(PInterface pInterface,
			EPackage rootPackage, ClassGraph graph, EClass eClass, Match sourceMatch, Match targetMatch) {
		if (!sourceMatch.equals(targetMatch)) {
			boolean eClass_interface = eClass.isInterface();
			if (Boolean.valueOf(eClass_interface).equals(Boolean.valueOf(true))) {
				return new Object[] { pInterface, rootPackage, graph, eClass, sourceMatch, targetMatch };
			}

		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_bindingAndBlackFFFFBB(
			Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_binding = pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_bindingFFFFBB(
				sourceMatch, targetMatch);
		if (result_pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_binding != null) {
			PInterface pInterface = (PInterface) result_pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_binding[0];
			EPackage rootPackage = (EPackage) result_pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_binding[1];
			ClassGraph graph = (ClassGraph) result_pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_binding[2];
			EClass eClass = (EClass) result_pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_binding[3];

			Object[] result_pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_black = pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_blackBBBBBB(
					pInterface, rootPackage, graph, eClass, sourceMatch, targetMatch);
			if (result_pattern_InterfaceNodeRule_24_2_matchsrctrgcontext_black != null) {

				return new Object[] { pInterface, rootPackage, graph, eClass, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_24_3_solvecsp_bindingFBBBBBBB(InterfaceNodeRule _this,
			PInterface pInterface, EPackage rootPackage, ClassGraph graph, EClass eClass, Match sourceMatch,
			Match targetMatch) {
		CSP _localVariable_4 = _this.isApplicable_solveCsp_CC(pInterface, rootPackage, graph, eClass, sourceMatch,
				targetMatch);
		CSP csp = _localVariable_4;
		if (csp != null) {
			return new Object[] { csp, _this, pInterface, rootPackage, graph, eClass, sourceMatch, targetMatch };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_24_3_solvecsp_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_InterfaceNodeRule_24_3_solvecsp_bindingAndBlackFBBBBBBB(
			InterfaceNodeRule _this, PInterface pInterface, EPackage rootPackage, ClassGraph graph, EClass eClass,
			Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_InterfaceNodeRule_24_3_solvecsp_binding = pattern_InterfaceNodeRule_24_3_solvecsp_bindingFBBBBBBB(
				_this, pInterface, rootPackage, graph, eClass, sourceMatch, targetMatch);
		if (result_pattern_InterfaceNodeRule_24_3_solvecsp_binding != null) {
			CSP csp = (CSP) result_pattern_InterfaceNodeRule_24_3_solvecsp_binding[0];

			Object[] result_pattern_InterfaceNodeRule_24_3_solvecsp_black = pattern_InterfaceNodeRule_24_3_solvecsp_blackB(
					csp);
			if (result_pattern_InterfaceNodeRule_24_3_solvecsp_black != null) {

				return new Object[] { csp, _this, pInterface, rootPackage, graph, eClass, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final boolean pattern_InterfaceNodeRule_24_4_checkCSP_expressionFB(CSP csp) {
		boolean _localVariable_0 = csp.check();
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Iterable<Object[]> pattern_InterfaceNodeRule_24_5_matchcorrcontext_blackFBBBB(
			EPackage rootPackage, ClassGraph graph, Match sourceMatch, Match targetMatch) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!sourceMatch.equals(targetMatch)) {
			for (ClassGraphToEPackage graphToPackage : org.moflon.core.utilities.eMoflonEMFUtil
					.getOppositeReferenceTyped(rootPackage, ClassGraphToEPackage.class, "target")) {
				if (graph.equals(graphToPackage.getSource())) {
					_result.add(new Object[] { graphToPackage, rootPackage, graph, sourceMatch, targetMatch });
				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_24_5_matchcorrcontext_greenBBBF(
			ClassGraphToEPackage graphToPackage, Match sourceMatch, Match targetMatch) {
		CCMatch ccMatch = RuntimeFactory.eINSTANCE.createCCMatch();
		String ccMatch_ruleName_prime = "InterfaceNodeRule";
		ccMatch.setSourceMatch(sourceMatch);
		ccMatch.setTargetMatch(targetMatch);
		ccMatch.getAllContextElements().add(graphToPackage);
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { graphToPackage, sourceMatch, targetMatch, ccMatch };
	}

	public static final Object[] pattern_InterfaceNodeRule_24_6_createcorrespondence_blackBBBBB(PInterface pInterface,
			EPackage rootPackage, ClassGraph graph, EClass eClass, CCMatch ccMatch) {
		return new Object[] { pInterface, rootPackage, graph, eClass, ccMatch };
	}

	public static final Object[] pattern_InterfaceNodeRule_24_6_createcorrespondence_greenBFBB(PInterface pInterface,
			EClass eClass, CCMatch ccMatch) {
		PNodeToEClassifier pInterfaceToEClass = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		pInterfaceToEClass.setSource(pInterface);
		pInterfaceToEClass.setTarget(eClass);
		ccMatch.getCreateCorr().add(pInterfaceToEClass);
		return new Object[] { pInterface, pInterfaceToEClass, eClass, ccMatch };
	}

	public static final Object[] pattern_InterfaceNodeRule_24_7_addtoreturnedresult_blackBB(
			IsApplicableRuleResult result, CCMatch ccMatch) {
		return new Object[] { result, ccMatch };
	}

	public static final Object[] pattern_InterfaceNodeRule_24_7_addtoreturnedresult_greenBB(
			IsApplicableRuleResult result, CCMatch ccMatch) {
		result.getIsApplicableMatch().add(ccMatch);
		boolean result_success_prime = Boolean.valueOf(true);
		String ccMatch_ruleName_prime = "InterfaceNodeRule";
		result.setSuccess(Boolean.valueOf(result_success_prime));
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { result, ccMatch };
	}

	public static final IsApplicableRuleResult pattern_InterfaceNodeRule_24_8_expressionFB(
			IsApplicableRuleResult result) {
		IsApplicableRuleResult _result = result;
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_27_1_matchtggpattern_blackBB(PInterface pInterface,
			ClassGraph graph) {
		if (graph.equals(pInterface.getGraph())) {
			return new Object[] { pInterface, graph };
		}
		return null;
	}

	public static final boolean pattern_InterfaceNodeRule_27_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_InterfaceNodeRule_27_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_28_1_matchtggpattern_blackBB(EPackage rootPackage,
			EClass eClass) {
		if (rootPackage.equals(eClass.getEPackage())) {
			return new Object[] { rootPackage, eClass };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_28_1_matchtggpattern_greenB(EClass eClass) {
		boolean eClass_interface_prime = Boolean.valueOf(true);
		eClass.setInterface(Boolean.valueOf(eClass_interface_prime));
		return new Object[] { eClass };
	}

	public static final boolean pattern_InterfaceNodeRule_28_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_InterfaceNodeRule_28_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_29_1_createresult_blackB(InterfaceNodeRule _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_InterfaceNodeRule_29_1_createresult_greenFF() {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		ModelgeneratorRuleResult ruleResult = RuntimeFactory.eINSTANCE.createModelgeneratorRuleResult();
		boolean ruleResult_success_prime = false;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		return new Object[] { isApplicableMatch, ruleResult };
	}

	public static final Object[] pattern_InterfaceNodeRule_29_2_isapplicablecore_black_nac_0BB(
			ModelgeneratorRuleResult ruleResult, ClassGraphToEPackage graphToPackage) {
		if (ruleResult.getCorrObjects().contains(graphToPackage)) {
			return new Object[] { ruleResult, graphToPackage };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_29_2_isapplicablecore_black_nac_1BB(
			ModelgeneratorRuleResult ruleResult, EPackage rootPackage) {
		if (ruleResult.getTargetObjects().contains(rootPackage)) {
			return new Object[] { ruleResult, rootPackage };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_29_2_isapplicablecore_black_nac_2BB(
			ModelgeneratorRuleResult ruleResult, ClassGraph graph) {
		if (ruleResult.getSourceObjects().contains(graph)) {
			return new Object[] { ruleResult, graph };
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_InterfaceNodeRule_29_2_isapplicablecore_blackFFFFBB(
			RuleEntryContainer ruleEntryContainer, ModelgeneratorRuleResult ruleResult) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (RuleEntryList graphToPackageList : ruleEntryContainer.getRuleEntryList()) {
			for (EObject tmpGraphToPackage : graphToPackageList.getEntryObjects()) {
				if (tmpGraphToPackage instanceof ClassGraphToEPackage) {
					ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) tmpGraphToPackage;
					EPackage rootPackage = graphToPackage.getTarget();
					if (rootPackage != null) {
						ClassGraph graph = graphToPackage.getSource();
						if (graph != null) {
							if (pattern_InterfaceNodeRule_29_2_isapplicablecore_black_nac_0BB(ruleResult,
									graphToPackage) == null) {
								if (pattern_InterfaceNodeRule_29_2_isapplicablecore_black_nac_1BB(ruleResult,
										rootPackage) == null) {
									if (pattern_InterfaceNodeRule_29_2_isapplicablecore_black_nac_2BB(ruleResult,
											graph) == null) {
										_result.add(new Object[] { graphToPackageList, graphToPackage, rootPackage,
												graph, ruleEntryContainer, ruleResult });
									}
								}
							}
						}

					}

				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_29_3_solveCSP_bindingFBBBBBB(InterfaceNodeRule _this,
			IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage, EPackage rootPackage,
			ClassGraph graph, ModelgeneratorRuleResult ruleResult) {
		CSP _localVariable_0 = _this.generateModel_solveCsp_BWD(isApplicableMatch, graphToPackage, rootPackage, graph,
				ruleResult);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, graphToPackage, rootPackage, graph, ruleResult };
		}
		return null;
	}

	public static final Object[] pattern_InterfaceNodeRule_29_3_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_InterfaceNodeRule_29_3_solveCSP_bindingAndBlackFBBBBBB(InterfaceNodeRule _this,
			IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage, EPackage rootPackage,
			ClassGraph graph, ModelgeneratorRuleResult ruleResult) {
		Object[] result_pattern_InterfaceNodeRule_29_3_solveCSP_binding = pattern_InterfaceNodeRule_29_3_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, graphToPackage, rootPackage, graph, ruleResult);
		if (result_pattern_InterfaceNodeRule_29_3_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_InterfaceNodeRule_29_3_solveCSP_binding[0];

			Object[] result_pattern_InterfaceNodeRule_29_3_solveCSP_black = pattern_InterfaceNodeRule_29_3_solveCSP_blackB(
					csp);
			if (result_pattern_InterfaceNodeRule_29_3_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, graphToPackage, rootPackage, graph, ruleResult };
			}
		}
		return null;
	}

	public static final boolean pattern_InterfaceNodeRule_29_4_checkCSP_expressionFBB(InterfaceNodeRule _this,
			CSP csp) {
		boolean _localVariable_0 = _this.generateModel_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_InterfaceNodeRule_29_5_checknacs_blackBBB(ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, ClassGraph graph) {
		return new Object[] { graphToPackage, rootPackage, graph };
	}

	public static final Object[] pattern_InterfaceNodeRule_29_6_perform_blackBBBB(ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, ClassGraph graph, ModelgeneratorRuleResult ruleResult) {
		return new Object[] { graphToPackage, rootPackage, graph, ruleResult };
	}

	public static final Object[] pattern_InterfaceNodeRule_29_6_perform_greenFFBBFBB(EPackage rootPackage,
			ClassGraph graph, ModelgeneratorRuleResult ruleResult, CSP csp) {
		PInterface pInterface = LanguageFactory.eINSTANCE.createPInterface();
		PNodeToEClassifier pInterfaceToEClass = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		Object _localVariable_0 = csp.getValue("pInterface", "label");
		boolean eClass_interface_prime = Boolean.valueOf(true);
		Object _localVariable_1 = csp.getValue("eClass", "name");
		boolean ruleResult_success_prime = Boolean.valueOf(true);
		int _localVariable_2 = ruleResult.getIncrementedPerformCount();
		pInterface.setGraph(graph);
		ruleResult.getSourceObjects().add(pInterface);
		pInterfaceToEClass.setSource(pInterface);
		ruleResult.getCorrObjects().add(pInterfaceToEClass);
		pInterfaceToEClass.setTarget(eClass);
		rootPackage.getEClassifiers().add(eClass);
		ruleResult.getTargetObjects().add(eClass);
		String pInterface_label_prime = (String) _localVariable_0;
		eClass.setInterface(Boolean.valueOf(eClass_interface_prime));
		String eClass_name_prime = (String) _localVariable_1;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		int ruleResult_performCount_prime = Integer.valueOf(_localVariable_2);
		pInterface.setLabel(pInterface_label_prime);
		eClass.setName(eClass_name_prime);
		ruleResult.setPerformCount(Integer.valueOf(ruleResult_performCount_prime));
		return new Object[] { pInterface, pInterfaceToEClass, rootPackage, graph, eClass, ruleResult, csp };
	}

	public static final ModelgeneratorRuleResult pattern_InterfaceNodeRule_29_7_expressionFB(
			ModelgeneratorRuleResult ruleResult) {
		ModelgeneratorRuleResult _result = ruleResult;
		return _result;
	}

	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //InterfaceNodeRuleImpl
