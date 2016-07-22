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

import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.RulesPackage;
import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.SimpleClassNodeRule;

import org.moflon.ide.visualization.dot.language.ClassGraph;
import org.moflon.ide.visualization.dot.language.LanguageFactory;
import org.moflon.ide.visualization.dot.language.PClass;

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
 * An implementation of the model object '<em><b>Simple Class Node Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class SimpleClassNodeRuleImpl extends AbstractRuleImpl implements SimpleClassNodeRule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimpleClassNodeRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulesPackage.eINSTANCE.getSimpleClassNodeRule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_FWD(Match match, PClass pClass, ClassGraph graph) {
		// initial bindings
		Object[] result1_black = SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_0_1_initialbindings_blackBBBB(this,
				match, pClass, graph);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [initial bindings] failed." + " Variables: " + "[this] = " + this + ", "
							+ "[match] = " + match + ", " + "[pClass] = " + pClass + ", " + "[graph] = " + graph + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_0_2_SolveCSP_bindingAndBlackFBBBB(this, match, pClass, graph);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException(
					"Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = " + this + ", "
							+ "[match] = " + match + ", " + "[pClass] = " + pClass + ", " + "[graph] = " + graph + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_0_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = SimpleClassNodeRuleImpl
					.pattern_SimpleClassNodeRule_0_4_collectelementstobetranslated_blackBBB(match, pClass, graph);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[pClass] = " + pClass + ", " + "[graph] = "
						+ graph + ".");
			}
			SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_0_4_collectelementstobetranslated_greenBBBFF(match,
					pClass, graph);
			// EMoflonEdge pClass__graph____graph = (EMoflonEdge) result4_green[3];
			// EMoflonEdge graph__pClass____nodes = (EMoflonEdge) result4_green[4];

			// collect context elements
			Object[] result5_black = SimpleClassNodeRuleImpl
					.pattern_SimpleClassNodeRule_0_5_collectcontextelements_blackBBB(match, pClass, graph);
			if (result5_black == null) {
				throw new RuntimeException(
						"Pattern matching in node [collect context elements] failed." + " Variables: " + "[match] = "
								+ match + ", " + "[pClass] = " + pClass + ", " + "[graph] = " + graph + ".");
			}
			SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_0_5_collectcontextelements_greenBB(match, graph);

			// register objects to match
			SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_0_6_registerobjectstomatch_expressionBBBB(this, match,
					pClass, graph);
			return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_0_7_expressionF();
		} else {
			return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_0_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_FWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_1_1_performtransformation_bindingAndBlackFFFFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result1_bindingAndBlack[0];
		EPackage rootPackage = (EPackage) result1_bindingAndBlack[1];
		PClass pClass = (PClass) result1_bindingAndBlack[2];
		ClassGraph graph = (ClassGraph) result1_bindingAndBlack[3];
		CSP csp = (CSP) result1_bindingAndBlack[4];
		Object[] result1_green = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_1_1_performtransformation_greenBBFFB(rootPackage, pClass, csp);
		PNodeToEClassifier pClassToEClass = (PNodeToEClassifier) result1_green[2];
		EClass eClass = (EClass) result1_green[3];

		// collect translated elements
		Object[] result2_black = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_1_2_collecttranslatedelements_blackBBB(pClass, pClassToEClass, eClass);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[pClass] = " + pClass + ", " + "[pClassToEClass] = " + pClassToEClass + ", " + "[eClass] = "
					+ eClass + ".");
		}
		Object[] result2_green = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_1_2_collecttranslatedelements_greenFBBB(pClass, pClassToEClass, eClass);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_1_3_bookkeepingforedges_blackBBBBBBB(ruleresult, graphToPackage,
						rootPackage, pClass, pClassToEClass, graph, eClass);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[graphToPackage] = " + graphToPackage + ", "
					+ "[rootPackage] = " + rootPackage + ", " + "[pClass] = " + pClass + ", " + "[pClassToEClass] = "
					+ pClassToEClass + ", " + "[graph] = " + graph + ", " + "[eClass] = " + eClass + ".");
		}
		SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_1_3_bookkeepingforedges_greenBBBBBBFFFFFF(ruleresult,
				rootPackage, pClass, pClassToEClass, graph, eClass);
		// EMoflonEdge pClass__graph____graph = (EMoflonEdge) result3_green[6];
		// EMoflonEdge graph__pClass____nodes = (EMoflonEdge) result3_green[7];
		// EMoflonEdge pClassToEClass__eClass____target = (EMoflonEdge) result3_green[8];
		// EMoflonEdge pClassToEClass__pClass____source = (EMoflonEdge) result3_green[9];
		// EMoflonEdge eClass__rootPackage____ePackage = (EMoflonEdge) result3_green[10];
		// EMoflonEdge rootPackage__eClass____eClassifiers = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_1_5_registerobjects_expressionBBBBBBBB(this, ruleresult,
				graphToPackage, rootPackage, pClass, pClassToEClass, graph, eClass);
		return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_1_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_FWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_2_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_2_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_2_2_corematch_bindingFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		PClass pClass = (PClass) result2_binding[0];
		ClassGraph graph = (ClassGraph) result2_binding[1];
		for (Object[] result2_black : SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_2_2_corematch_blackFFBBB(pClass, graph, match)) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result2_black[0];
			EPackage rootPackage = (EPackage) result2_black[1];
			// ForEach find context
			for (Object[] result3_black : SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_2_3_findcontext_blackBBBB(
					graphToPackage, rootPackage, pClass, graph)) {
				Object[] result3_green = SimpleClassNodeRuleImpl
						.pattern_SimpleClassNodeRule_2_3_findcontext_greenBBBBFFFFF(graphToPackage, rootPackage, pClass,
								graph);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[4];
				// EMoflonEdge pClass__graph____graph = (EMoflonEdge) result3_green[5];
				// EMoflonEdge graph__pClass____nodes = (EMoflonEdge) result3_green[6];
				// EMoflonEdge graphToPackage__rootPackage____target = (EMoflonEdge) result3_green[7];
				// EMoflonEdge graphToPackage__graph____source = (EMoflonEdge) result3_green[8];

				// solve CSP
				Object[] result4_bindingAndBlack = SimpleClassNodeRuleImpl
						.pattern_SimpleClassNodeRule_2_4_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch,
								graphToPackage, rootPackage, pClass, graph);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[graphToPackage] = " + graphToPackage + ", " + "[rootPackage] = " + rootPackage + ", "
							+ "[pClass] = " + pClass + ", " + "[graph] = " + graph + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_2_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = SimpleClassNodeRuleImpl
							.pattern_SimpleClassNodeRule_2_6_addmatchtoruleresult_blackBB(ruleresult,
									isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_2_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_2_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_FWD(Match match, PClass pClass, ClassGraph graph) {
		match.registerObject("pClass", pClass);
		match.registerObject("graph", graph);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_FWD(Match match, PClass pClass, ClassGraph graph) {// Create CSP
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
	public CSP isApplicable_solveCsp_FWD(IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, PClass pClass, ClassGraph graph) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_pClass_label = CSPFactoryHelper.eINSTANCE.createVariable("pClass.label", true, csp);
		var_pClass_label.setValue(pClass.getLabel());
		var_pClass_label.setType("String");

		// Create unbound variables
		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass.name", csp);
		var_eClass_name.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pClass_label, var_eClass_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("graphToPackage", graphToPackage);
		isApplicableMatch.registerObject("rootPackage", rootPackage);
		isApplicableMatch.registerObject("pClass", pClass);
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
	public void registerObjects_FWD(PerformRuleResult ruleresult, EObject graphToPackage, EObject rootPackage,
			EObject pClass, EObject pClassToEClass, EObject graph, EObject eClass) {
		ruleresult.registerObject("graphToPackage", graphToPackage);
		ruleresult.registerObject("rootPackage", rootPackage);
		ruleresult.registerObject("pClass", pClass);
		ruleresult.registerObject("pClassToEClass", pClassToEClass);
		ruleresult.registerObject("graph", graph);
		ruleresult.registerObject("eClass", eClass);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_FWD(Match match) {
		return true
				&& org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("pClass").eClass()).equals("language.PClass.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_BWD(Match match, EPackage rootPackage, EClass eClass) {
		// initial bindings
		Object[] result1_black = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_10_1_initialbindings_blackBBBB(this, match, rootPackage, eClass);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ", "
					+ "[eClass] = " + eClass + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_10_2_SolveCSP_bindingAndBlackFBBBB(this, match, rootPackage, eClass);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ", "
					+ "[eClass] = " + eClass + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_10_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = SimpleClassNodeRuleImpl
					.pattern_SimpleClassNodeRule_10_4_collectelementstobetranslated_blackBBB(match, rootPackage,
							eClass);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ", "
						+ "[eClass] = " + eClass + ".");
			}
			SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_10_4_collectelementstobetranslated_greenBBBFF(match,
					rootPackage, eClass);
			// EMoflonEdge eClass__rootPackage____ePackage = (EMoflonEdge) result4_green[3];
			// EMoflonEdge rootPackage__eClass____eClassifiers = (EMoflonEdge) result4_green[4];

			// collect context elements
			Object[] result5_black = SimpleClassNodeRuleImpl
					.pattern_SimpleClassNodeRule_10_5_collectcontextelements_blackBBB(match, rootPackage, eClass);
			if (result5_black == null) {
				throw new RuntimeException("Pattern matching in node [collect context elements] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ", "
						+ "[eClass] = " + eClass + ".");
			}
			SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_10_5_collectcontextelements_greenBB(match, rootPackage);

			// register objects to match
			SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_10_6_registerobjectstomatch_expressionBBBB(this, match,
					rootPackage, eClass);
			return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_10_7_expressionF();
		} else {
			return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_10_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_BWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_11_1_performtransformation_bindingAndBlackFFFFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result1_bindingAndBlack[0];
		EPackage rootPackage = (EPackage) result1_bindingAndBlack[1];
		ClassGraph graph = (ClassGraph) result1_bindingAndBlack[2];
		EClass eClass = (EClass) result1_bindingAndBlack[3];
		CSP csp = (CSP) result1_bindingAndBlack[4];
		Object[] result1_green = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_11_1_performtransformation_greenFFBBB(graph, eClass, csp);
		PClass pClass = (PClass) result1_green[0];
		PNodeToEClassifier pClassToEClass = (PNodeToEClassifier) result1_green[1];

		// collect translated elements
		Object[] result2_black = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_11_2_collecttranslatedelements_blackBBB(pClass, pClassToEClass, eClass);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[pClass] = " + pClass + ", " + "[pClassToEClass] = " + pClassToEClass + ", " + "[eClass] = "
					+ eClass + ".");
		}
		Object[] result2_green = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_11_2_collecttranslatedelements_greenFBBB(pClass, pClassToEClass, eClass);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_11_3_bookkeepingforedges_blackBBBBBBB(ruleresult, graphToPackage,
						rootPackage, pClass, pClassToEClass, graph, eClass);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[graphToPackage] = " + graphToPackage + ", "
					+ "[rootPackage] = " + rootPackage + ", " + "[pClass] = " + pClass + ", " + "[pClassToEClass] = "
					+ pClassToEClass + ", " + "[graph] = " + graph + ", " + "[eClass] = " + eClass + ".");
		}
		SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_11_3_bookkeepingforedges_greenBBBBBBFFFFFF(ruleresult,
				rootPackage, pClass, pClassToEClass, graph, eClass);
		// EMoflonEdge pClass__graph____graph = (EMoflonEdge) result3_green[6];
		// EMoflonEdge graph__pClass____nodes = (EMoflonEdge) result3_green[7];
		// EMoflonEdge pClassToEClass__eClass____target = (EMoflonEdge) result3_green[8];
		// EMoflonEdge pClassToEClass__pClass____source = (EMoflonEdge) result3_green[9];
		// EMoflonEdge eClass__rootPackage____ePackage = (EMoflonEdge) result3_green[10];
		// EMoflonEdge rootPackage__eClass____eClassifiers = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_11_5_registerobjects_expressionBBBBBBBB(this, ruleresult,
				graphToPackage, rootPackage, pClass, pClassToEClass, graph, eClass);
		return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_11_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_BWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_12_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_12_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_12_2_corematch_bindingFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		EPackage rootPackage = (EPackage) result2_binding[0];
		eClass = (EClass) result2_binding[1];
		for (Object[] result2_black : SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_12_2_corematch_blackFBFBB(rootPackage, eClass, match)) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result2_black[0];
			ClassGraph graph = (ClassGraph) result2_black[2];
			// ForEach find context
			for (Object[] result3_black : SimpleClassNodeRuleImpl
					.pattern_SimpleClassNodeRule_12_3_findcontext_blackBBBB(graphToPackage, rootPackage, graph,
							eClass)) {
				Object[] result3_green = SimpleClassNodeRuleImpl
						.pattern_SimpleClassNodeRule_12_3_findcontext_greenBBBBFFFFF(graphToPackage, rootPackage, graph,
								eClass);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[4];
				// EMoflonEdge graphToPackage__rootPackage____target = (EMoflonEdge) result3_green[5];
				// EMoflonEdge eClass__rootPackage____ePackage = (EMoflonEdge) result3_green[6];
				// EMoflonEdge rootPackage__eClass____eClassifiers = (EMoflonEdge) result3_green[7];
				// EMoflonEdge graphToPackage__graph____source = (EMoflonEdge) result3_green[8];

				// solve CSP
				Object[] result4_bindingAndBlack = SimpleClassNodeRuleImpl
						.pattern_SimpleClassNodeRule_12_4_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch,
								graphToPackage, rootPackage, graph, eClass);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[graphToPackage] = " + graphToPackage + ", " + "[rootPackage] = " + rootPackage + ", "
							+ "[graph] = " + graph + ", " + "[eClass] = " + eClass + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_12_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = SimpleClassNodeRuleImpl
							.pattern_SimpleClassNodeRule_12_6_addmatchtoruleresult_blackBB(ruleresult,
									isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_12_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_12_7_expressionFB(ruleresult);
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
		Variable var_pClass_label = CSPFactoryHelper.eINSTANCE.createVariable("pClass.label", csp);
		var_pClass_label.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pClass_label, var_eClass_name);

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
	public void registerObjects_BWD(PerformRuleResult ruleresult, EObject graphToPackage, EObject rootPackage,
			EObject pClass, EObject pClassToEClass, EObject graph, EObject eClass) {
		ruleresult.registerObject("graphToPackage", graphToPackage);
		ruleresult.registerObject("rootPackage", rootPackage);
		ruleresult.registerObject("pClass", pClass);
		ruleresult.registerObject("pClassToEClass", pClassToEClass);
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
	public EObjectContainer isAppropriate_BWD_EMoflonEdge_6(EMoflonEdge _edge_ePackage) {
		// prepare return value
		Object[] result1_bindingAndBlack = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_20_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_20_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_20_2_testcorematchandDECs_blackFFB(_edge_ePackage)) {
			EPackage rootPackage = (EPackage) result2_black[0];
			EClass eClass = (EClass) result2_black[1];
			Object[] result2_green = SimpleClassNodeRuleImpl
					.pattern_SimpleClassNodeRule_20_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (SimpleClassNodeRuleImpl
					.pattern_SimpleClassNodeRule_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(this,
							match, rootPackage, eClass)) {
				// Ensure that the correct types of elements are matched
				if (SimpleClassNodeRuleImpl
						.pattern_SimpleClassNodeRule_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
								this, match)) {

					// Add match to rule result
					Object[] result5_black = SimpleClassNodeRuleImpl
							.pattern_SimpleClassNodeRule_20_5_Addmatchtoruleresult_blackBBBB(match, __performOperation,
									__result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_20_5_Addmatchtoruleresult_greenBBBB(match,
							__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_20_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_FWD_EMoflonEdge_6(EMoflonEdge _edge_graph) {
		// prepare return value
		Object[] result1_bindingAndBlack = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_21_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_21_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_21_2_testcorematchandDECs_blackFFB(_edge_graph)) {
			PClass pClass = (PClass) result2_black[0];
			ClassGraph graph = (ClassGraph) result2_black[1];
			Object[] result2_green = SimpleClassNodeRuleImpl
					.pattern_SimpleClassNodeRule_21_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (SimpleClassNodeRuleImpl
					.pattern_SimpleClassNodeRule_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(this,
							match, pClass, graph)) {
				// Ensure that the correct types of elements are matched
				if (SimpleClassNodeRuleImpl
						.pattern_SimpleClassNodeRule_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
								this, match)) {

					// Add match to rule result
					Object[] result5_black = SimpleClassNodeRuleImpl
							.pattern_SimpleClassNodeRule_21_5_Addmatchtoruleresult_blackBBBB(match, __performOperation,
									__result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_21_5_Addmatchtoruleresult_greenBBBB(match,
							__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_21_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintsRuleResult checkAttributes_FWD(TripleMatch __tripleMatch) {
		AttributeConstraintsRuleResult ruleResult = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE
				.createAttributeConstraintsRuleResult();
		ruleResult.setRule("SimpleClassNodeRule");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		if (!__helper.hasExpectedValue("eClass", "interface", false, ComparingOperator.EQUAL)) {
			ruleResult.setSuccess(false);
			return ruleResult;
		}

		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass", true, csp);
		var_eClass_name.setValue(__helper.getValue("eClass", "name"));
		var_eClass_name.setType("String");

		Variable var_pClass_label = CSPFactoryHelper.eINSTANCE.createVariable("pClass", true, csp);
		var_pClass_label.setValue(__helper.getValue("pClass", "label"));
		var_pClass_label.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		eq0.setRuleName("SimpleClassNodeRule");
		eq0.solve(var_pClass_label, var_eClass_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			var_eClass_name.setBound(false);
			eq0.solve(var_pClass_label, var_eClass_name);
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
		ruleResult.setRule("SimpleClassNodeRule");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		if (!__helper.hasExpectedValue("eClass", "interface", false, ComparingOperator.EQUAL)) {
			ruleResult.setSuccess(false);
			return ruleResult;
		}

		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass", true, csp);
		var_eClass_name.setValue(__helper.getValue("eClass", "name"));
		var_eClass_name.setType("String");

		Variable var_pClass_label = CSPFactoryHelper.eINSTANCE.createVariable("pClass", true, csp);
		var_pClass_label.setValue(__helper.getValue("pClass", "label"));
		var_pClass_label.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		eq0.setRuleName("SimpleClassNodeRule");
		eq0.solve(var_pClass_label, var_eClass_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			var_pClass_label.setBound(false);
			eq0.solve(var_pClass_label, var_eClass_name);
			if (csp.check()) {
				ruleResult.setSuccess(true);
				ruleResult.setRequiredChange(true);
				__helper.setValue("pClass", "label", var_pClass_label.getValue());
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
		Object[] result1_black = SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_24_1_prepare_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [prepare] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_24_1_prepare_greenF();
		IsApplicableRuleResult result = (IsApplicableRuleResult) result1_green[0];

		// match src trg context
		Object[] result2_bindingAndBlack = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_bindingAndBlackFFFFBB(sourceMatch, targetMatch);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [match src trg context] failed." + " Variables: "
					+ "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = " + targetMatch + ".");
		}
		EPackage rootPackage = (EPackage) result2_bindingAndBlack[0];
		PClass pClass = (PClass) result2_bindingAndBlack[1];
		ClassGraph graph = (ClassGraph) result2_bindingAndBlack[2];
		EClass eClass = (EClass) result2_bindingAndBlack[3];

		// solve csp
		Object[] result3_bindingAndBlack = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_24_3_solvecsp_bindingAndBlackFBBBBBBB(this, rootPackage, pClass, graph,
						eClass, sourceMatch, targetMatch);
		if (result3_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [solve csp] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[rootPackage] = " + rootPackage + ", " + "[pClass] = " + pClass + ", "
					+ "[graph] = " + graph + ", " + "[eClass] = " + eClass + ", " + "[sourceMatch] = " + sourceMatch
					+ ", " + "[targetMatch] = " + targetMatch + ".");
		}
		CSP csp = (CSP) result3_bindingAndBlack[0];
		// check CSP
		if (SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_24_4_checkCSP_expressionFB(csp)) {
			// ForEach match corr context
			for (Object[] result5_black : SimpleClassNodeRuleImpl
					.pattern_SimpleClassNodeRule_24_5_matchcorrcontext_blackFBBBB(rootPackage, graph, sourceMatch,
							targetMatch)) {
				ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result5_black[0];
				Object[] result5_green = SimpleClassNodeRuleImpl
						.pattern_SimpleClassNodeRule_24_5_matchcorrcontext_greenBBBF(graphToPackage, sourceMatch,
								targetMatch);
				CCMatch ccMatch = (CCMatch) result5_green[3];

				// create correspondence
				Object[] result6_black = SimpleClassNodeRuleImpl
						.pattern_SimpleClassNodeRule_24_6_createcorrespondence_blackBBBBB(rootPackage, pClass, graph,
								eClass, ccMatch);
				if (result6_black == null) {
					throw new RuntimeException("Pattern matching in node [create correspondence] failed."
							+ " Variables: " + "[rootPackage] = " + rootPackage + ", " + "[pClass] = " + pClass + ", "
							+ "[graph] = " + graph + ", " + "[eClass] = " + eClass + ", " + "[ccMatch] = " + ccMatch
							+ ".");
				}
				SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_24_6_createcorrespondence_greenBFBB(pClass, eClass,
						ccMatch);
				// PNodeToEClassifier pClassToEClass = (PNodeToEClassifier) result6_green[1];

				// add to returned result
				Object[] result7_black = SimpleClassNodeRuleImpl
						.pattern_SimpleClassNodeRule_24_7_addtoreturnedresult_blackBB(result, ccMatch);
				if (result7_black == null) {
					throw new RuntimeException("Pattern matching in node [add to returned result] failed."
							+ " Variables: " + "[result] = " + result + ", " + "[ccMatch] = " + ccMatch + ".");
				}
				SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_24_7_addtoreturnedresult_greenBB(result, ccMatch);

			}

		} else {
		}
		return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_24_8_expressionFB(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isApplicable_solveCsp_CC(EPackage rootPackage, PClass pClass, ClassGraph graph, EClass eClass,
			Match sourceMatch, Match targetMatch) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();

		// Create literals

		// Create attribute variables
		Variable var_pClass_label = CSPFactoryHelper.eINSTANCE.createVariable("pClass.label", true, csp);
		var_pClass_label.setValue(pClass.getLabel());
		var_pClass_label.setType("String");
		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass.name", true, csp);
		var_eClass_name.setValue(eClass.getName());
		var_eClass_name.setType("String");

		// Create unbound variables

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pClass_label, var_eClass_name);
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
	public boolean checkDEC_FWD(PClass pClass, ClassGraph graph) {// match tgg pattern
		Object[] result1_black = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_27_1_matchtggpattern_blackBB(pClass, graph);
		if (result1_black != null) {
			return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_27_2_expressionF();
		} else {
			return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_27_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDEC_BWD(EPackage rootPackage, EClass eClass) {// match tgg pattern
		Object[] result1_black = SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_28_1_matchtggpattern_blackBB(rootPackage, eClass);
		if (result1_black != null) {
			SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_28_1_matchtggpattern_greenB(eClass);

			return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_28_2_expressionF();
		} else {
			return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_28_3_expressionF();
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
		Object[] result1_black = SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_29_1_createresult_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [create result] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_29_1_createresult_greenFF();
		IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result1_green[0];
		ModelgeneratorRuleResult ruleResult = (ModelgeneratorRuleResult) result1_green[1];

		// ForEach is applicable core
		for (Object[] result2_black : SimpleClassNodeRuleImpl
				.pattern_SimpleClassNodeRule_29_2_isapplicablecore_blackFFFFBB(ruleEntryContainer, ruleResult)) {
			// RuleEntryList graphToPackageList = (RuleEntryList) result2_black[0];
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result2_black[1];
			EPackage rootPackage = (EPackage) result2_black[2];
			ClassGraph graph = (ClassGraph) result2_black[3];

			// solve CSP
			Object[] result3_bindingAndBlack = SimpleClassNodeRuleImpl
					.pattern_SimpleClassNodeRule_29_3_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch,
							graphToPackage, rootPackage, graph, ruleResult);
			if (result3_bindingAndBlack == null) {
				throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: " + "[this] = "
						+ this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", " + "[graphToPackage] = "
						+ graphToPackage + ", " + "[rootPackage] = " + rootPackage + ", " + "[graph] = " + graph + ", "
						+ "[ruleResult] = " + ruleResult + ".");
			}
			CSP csp = (CSP) result3_bindingAndBlack[0];
			// check CSP
			if (SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_29_4_checkCSP_expressionFBB(this, csp)) {
				// check nacs
				Object[] result5_black = SimpleClassNodeRuleImpl
						.pattern_SimpleClassNodeRule_29_5_checknacs_blackBBB(graphToPackage, rootPackage, graph);
				if (result5_black != null) {

					// perform
					Object[] result6_black = SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_29_6_perform_blackBBBB(
							graphToPackage, rootPackage, graph, ruleResult);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [perform] failed." + " Variables: "
								+ "[graphToPackage] = " + graphToPackage + ", " + "[rootPackage] = " + rootPackage
								+ ", " + "[graph] = " + graph + ", " + "[ruleResult] = " + ruleResult + ".");
					}
					SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_29_6_perform_greenBFFBFBB(rootPackage, graph,
							ruleResult, csp);
					// PClass pClass = (PClass) result6_green[1];
					// PNodeToEClassifier pClassToEClass = (PNodeToEClassifier) result6_green[2];
					// EClass eClass = (EClass) result6_green[4];

				} else {
				}

			} else {
			}

		}
		return SimpleClassNodeRuleImpl.pattern_SimpleClassNodeRule_29_7_expressionFB(ruleResult);
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
		Variable var_pClass_label = CSPFactoryHelper.eINSTANCE.createVariable("pClass.label", csp);
		var_pClass_label.setType("String");
		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass.name", csp);
		var_eClass_name.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pClass_label, var_eClass_name);

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
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPROPRIATE_FWD__MATCH_PCLASS_CLASSGRAPH:
			return isAppropriate_FWD((Match) arguments.get(0), (PClass) arguments.get(1),
					(ClassGraph) arguments.get(2));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___PERFORM_FWD__ISAPPLICABLEMATCH:
			return perform_FWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPLICABLE_FWD__MATCH:
			return isApplicable_FWD((Match) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_PCLASS_CLASSGRAPH:
			registerObjectsToMatch_FWD((Match) arguments.get(0), (PClass) arguments.get(1),
					(ClassGraph) arguments.get(2));
			return null;
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_PCLASS_CLASSGRAPH:
			return isAppropriate_solveCsp_FWD((Match) arguments.get(0), (PClass) arguments.get(1),
					(ClassGraph) arguments.get(2));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPROPRIATE_CHECK_CSP_FWD__CSP:
			return isAppropriate_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_CLASSGRAPHTOEPACKAGE_EPACKAGE_PCLASS_CLASSGRAPH:
			return isApplicable_solveCsp_FWD((IsApplicableMatch) arguments.get(0),
					(ClassGraphToEPackage) arguments.get(1), (EPackage) arguments.get(2), (PClass) arguments.get(3),
					(ClassGraph) arguments.get(4));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPLICABLE_CHECK_CSP_FWD__CSP:
			return isApplicable_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_FWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6));
			return null;
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___CHECK_TYPES_FWD__MATCH:
			return checkTypes_FWD((Match) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPROPRIATE_BWD__MATCH_EPACKAGE_ECLASS:
			return isAppropriate_BWD((Match) arguments.get(0), (EPackage) arguments.get(1), (EClass) arguments.get(2));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___PERFORM_BWD__ISAPPLICABLEMATCH:
			return perform_BWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPLICABLE_BWD__MATCH:
			return isApplicable_BWD((Match) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_EPACKAGE_ECLASS:
			registerObjectsToMatch_BWD((Match) arguments.get(0), (EPackage) arguments.get(1),
					(EClass) arguments.get(2));
			return null;
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_EPACKAGE_ECLASS:
			return isAppropriate_solveCsp_BWD((Match) arguments.get(0), (EPackage) arguments.get(1),
					(EClass) arguments.get(2));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPROPRIATE_CHECK_CSP_BWD__CSP:
			return isAppropriate_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_CLASSGRAPHTOEPACKAGE_EPACKAGE_CLASSGRAPH_ECLASS:
			return isApplicable_solveCsp_BWD((IsApplicableMatch) arguments.get(0),
					(ClassGraphToEPackage) arguments.get(1), (EPackage) arguments.get(2), (ClassGraph) arguments.get(3),
					(EClass) arguments.get(4));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPLICABLE_CHECK_CSP_BWD__CSP:
			return isApplicable_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_BWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6));
			return null;
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___CHECK_TYPES_BWD__MATCH:
			return checkTypes_BWD((Match) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPROPRIATE_BWD_EMOFLON_EDGE_6__EMOFLONEDGE:
			return isAppropriate_BWD_EMoflonEdge_6((EMoflonEdge) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPROPRIATE_FWD_EMOFLON_EDGE_6__EMOFLONEDGE:
			return isAppropriate_FWD_EMoflonEdge_6((EMoflonEdge) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH:
			return checkAttributes_FWD((TripleMatch) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH:
			return checkAttributes_BWD((TripleMatch) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPLICABLE_CC__MATCH_MATCH:
			return isApplicable_CC((Match) arguments.get(0), (Match) arguments.get(1));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPLICABLE_SOLVE_CSP_CC__EPACKAGE_PCLASS_CLASSGRAPH_ECLASS_MATCH_MATCH:
			return isApplicable_solveCsp_CC((EPackage) arguments.get(0), (PClass) arguments.get(1),
					(ClassGraph) arguments.get(2), (EClass) arguments.get(3), (Match) arguments.get(4),
					(Match) arguments.get(5));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___IS_APPLICABLE_CHECK_CSP_CC__CSP:
			return isApplicable_checkCsp_CC((CSP) arguments.get(0));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___CHECK_DEC_FWD__PCLASS_CLASSGRAPH:
			return checkDEC_FWD((PClass) arguments.get(0), (ClassGraph) arguments.get(1));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___CHECK_DEC_BWD__EPACKAGE_ECLASS:
			return checkDEC_BWD((EPackage) arguments.get(0), (EClass) arguments.get(1));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___GENERATE_MODEL__RULEENTRYCONTAINER_CLASSGRAPHTOEPACKAGE:
			return generateModel((RuleEntryContainer) arguments.get(0), (ClassGraphToEPackage) arguments.get(1));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_CLASSGRAPHTOEPACKAGE_EPACKAGE_CLASSGRAPH_MODELGENERATORRULERESULT:
			return generateModel_solveCsp_BWD((IsApplicableMatch) arguments.get(0),
					(ClassGraphToEPackage) arguments.get(1), (EPackage) arguments.get(2), (ClassGraph) arguments.get(3),
					(ModelgeneratorRuleResult) arguments.get(4));
		case RulesPackage.SIMPLE_CLASS_NODE_RULE___GENERATE_MODEL_CHECK_CSP_BWD__CSP:
			return generateModel_checkCsp_BWD((CSP) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	public static final Object[] pattern_SimpleClassNodeRule_0_1_initialbindings_blackBBBB(SimpleClassNodeRule _this,
			Match match, PClass pClass, ClassGraph graph) {
		return new Object[] { _this, match, pClass, graph };
	}

	public static final Object[] pattern_SimpleClassNodeRule_0_2_SolveCSP_bindingFBBBB(SimpleClassNodeRule _this,
			Match match, PClass pClass, ClassGraph graph) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_FWD(match, pClass, graph);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, pClass, graph };
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_0_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_SimpleClassNodeRule_0_2_SolveCSP_bindingAndBlackFBBBB(
			SimpleClassNodeRule _this, Match match, PClass pClass, ClassGraph graph) {
		Object[] result_pattern_SimpleClassNodeRule_0_2_SolveCSP_binding = pattern_SimpleClassNodeRule_0_2_SolveCSP_bindingFBBBB(
				_this, match, pClass, graph);
		if (result_pattern_SimpleClassNodeRule_0_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_SimpleClassNodeRule_0_2_SolveCSP_binding[0];

			Object[] result_pattern_SimpleClassNodeRule_0_2_SolveCSP_black = pattern_SimpleClassNodeRule_0_2_SolveCSP_blackB(
					csp);
			if (result_pattern_SimpleClassNodeRule_0_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, pClass, graph };
			}
		}
		return null;
	}

	public static final boolean pattern_SimpleClassNodeRule_0_3_CheckCSP_expressionFBB(SimpleClassNodeRule _this,
			CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_0_4_collectelementstobetranslated_blackBBB(Match match,
			PClass pClass, ClassGraph graph) {
		return new Object[] { match, pClass, graph };
	}

	public static final Object[] pattern_SimpleClassNodeRule_0_4_collectelementstobetranslated_greenBBBFF(Match match,
			PClass pClass, ClassGraph graph) {
		EMoflonEdge pClass__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pClass____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getToBeTranslatedNodes().add(pClass);
		String pClass__graph____graph_name_prime = "graph";
		String graph__pClass____nodes_name_prime = "nodes";
		pClass__graph____graph.setSrc(pClass);
		pClass__graph____graph.setTrg(graph);
		match.getToBeTranslatedEdges().add(pClass__graph____graph);
		graph__pClass____nodes.setSrc(graph);
		graph__pClass____nodes.setTrg(pClass);
		match.getToBeTranslatedEdges().add(graph__pClass____nodes);
		pClass__graph____graph.setName(pClass__graph____graph_name_prime);
		graph__pClass____nodes.setName(graph__pClass____nodes_name_prime);
		return new Object[] { match, pClass, graph, pClass__graph____graph, graph__pClass____nodes };
	}

	public static final Object[] pattern_SimpleClassNodeRule_0_5_collectcontextelements_blackBBB(Match match,
			PClass pClass, ClassGraph graph) {
		return new Object[] { match, pClass, graph };
	}

	public static final Object[] pattern_SimpleClassNodeRule_0_5_collectcontextelements_greenBB(Match match,
			ClassGraph graph) {
		match.getContextNodes().add(graph);
		return new Object[] { match, graph };
	}

	public static final void pattern_SimpleClassNodeRule_0_6_registerobjectstomatch_expressionBBBB(
			SimpleClassNodeRule _this, Match match, PClass pClass, ClassGraph graph) {
		_this.registerObjectsToMatch_FWD(match, pClass, graph);

	}

	public static final boolean pattern_SimpleClassNodeRule_0_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_SimpleClassNodeRule_0_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_1_1_performtransformation_bindingFFFFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("graphToPackage");
		EObject _localVariable_1 = isApplicableMatch.getObject("rootPackage");
		EObject _localVariable_2 = isApplicableMatch.getObject("pClass");
		EObject _localVariable_3 = isApplicableMatch.getObject("graph");
		EObject tmpGraphToPackage = _localVariable_0;
		EObject tmpRootPackage = _localVariable_1;
		EObject tmpPClass = _localVariable_2;
		EObject tmpGraph = _localVariable_3;
		if (tmpGraphToPackage instanceof ClassGraphToEPackage) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) tmpGraphToPackage;
			if (tmpRootPackage instanceof EPackage) {
				EPackage rootPackage = (EPackage) tmpRootPackage;
				if (tmpPClass instanceof PClass) {
					PClass pClass = (PClass) tmpPClass;
					if (tmpGraph instanceof ClassGraph) {
						ClassGraph graph = (ClassGraph) tmpGraph;
						return new Object[] { graphToPackage, rootPackage, pClass, graph, isApplicableMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_1_1_performtransformation_blackBBBBFBB(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, PClass pClass, ClassGraph graph,
			SimpleClassNodeRule _this, IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { graphToPackage, rootPackage, pClass, graph, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_1_1_performtransformation_bindingAndBlackFFFFFBB(
			SimpleClassNodeRule _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_SimpleClassNodeRule_1_1_performtransformation_binding = pattern_SimpleClassNodeRule_1_1_performtransformation_bindingFFFFB(
				isApplicableMatch);
		if (result_pattern_SimpleClassNodeRule_1_1_performtransformation_binding != null) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result_pattern_SimpleClassNodeRule_1_1_performtransformation_binding[0];
			EPackage rootPackage = (EPackage) result_pattern_SimpleClassNodeRule_1_1_performtransformation_binding[1];
			PClass pClass = (PClass) result_pattern_SimpleClassNodeRule_1_1_performtransformation_binding[2];
			ClassGraph graph = (ClassGraph) result_pattern_SimpleClassNodeRule_1_1_performtransformation_binding[3];

			Object[] result_pattern_SimpleClassNodeRule_1_1_performtransformation_black = pattern_SimpleClassNodeRule_1_1_performtransformation_blackBBBBFBB(
					graphToPackage, rootPackage, pClass, graph, _this, isApplicableMatch);
			if (result_pattern_SimpleClassNodeRule_1_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_SimpleClassNodeRule_1_1_performtransformation_black[4];

				return new Object[] { graphToPackage, rootPackage, pClass, graph, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_1_1_performtransformation_greenBBFFB(EPackage rootPackage,
			PClass pClass, CSP csp) {
		PNodeToEClassifier pClassToEClass = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		boolean eClass_interface_prime = false;
		Object _localVariable_0 = csp.getValue("eClass", "name");
		pClassToEClass.setSource(pClass);
		pClassToEClass.setTarget(eClass);
		rootPackage.getEClassifiers().add(eClass);
		eClass.setInterface(Boolean.valueOf(eClass_interface_prime));
		String eClass_name_prime = (String) _localVariable_0;
		eClass.setName(eClass_name_prime);
		return new Object[] { rootPackage, pClass, pClassToEClass, eClass, csp };
	}

	public static final Object[] pattern_SimpleClassNodeRule_1_2_collecttranslatedelements_blackBBB(PClass pClass,
			PNodeToEClassifier pClassToEClass, EClass eClass) {
		return new Object[] { pClass, pClassToEClass, eClass };
	}

	public static final Object[] pattern_SimpleClassNodeRule_1_2_collecttranslatedelements_greenFBBB(PClass pClass,
			PNodeToEClassifier pClassToEClass, EClass eClass) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getTranslatedElements().add(pClass);
		ruleresult.getCreatedLinkElements().add(pClassToEClass);
		ruleresult.getCreatedElements().add(eClass);
		return new Object[] { ruleresult, pClass, pClassToEClass, eClass };
	}

	public static final Object[] pattern_SimpleClassNodeRule_1_3_bookkeepingforedges_blackBBBBBBB(
			PerformRuleResult ruleresult, EObject graphToPackage, EObject rootPackage, EObject pClass,
			EObject pClassToEClass, EObject graph, EObject eClass) {
		if (!graphToPackage.equals(rootPackage)) {
			if (!graphToPackage.equals(pClass)) {
				if (!graphToPackage.equals(pClassToEClass)) {
					if (!pClass.equals(rootPackage)) {
						if (!pClass.equals(pClassToEClass)) {
							if (!pClassToEClass.equals(rootPackage)) {
								if (!graph.equals(graphToPackage)) {
									if (!graph.equals(rootPackage)) {
										if (!graph.equals(pClass)) {
											if (!graph.equals(pClassToEClass)) {
												if (!eClass.equals(graphToPackage)) {
													if (!eClass.equals(rootPackage)) {
														if (!eClass.equals(pClass)) {
															if (!eClass.equals(pClassToEClass)) {
																if (!eClass.equals(graph)) {
																	return new Object[] { ruleresult, graphToPackage,
																			rootPackage, pClass, pClassToEClass, graph,
																			eClass };
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

	public static final Object[] pattern_SimpleClassNodeRule_1_3_bookkeepingforedges_greenBBBBBBFFFFFF(
			PerformRuleResult ruleresult, EObject rootPackage, EObject pClass, EObject pClassToEClass, EObject graph,
			EObject eClass) {
		EMoflonEdge pClass__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pClass____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pClassToEClass__eClass____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pClassToEClass__pClass____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge eClass__rootPackage____ePackage = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rootPackage__eClass____eClassifiers = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "SimpleClassNodeRule";
		String pClass__graph____graph_name_prime = "graph";
		String graph__pClass____nodes_name_prime = "nodes";
		String pClassToEClass__eClass____target_name_prime = "target";
		String pClassToEClass__pClass____source_name_prime = "source";
		String eClass__rootPackage____ePackage_name_prime = "ePackage";
		String rootPackage__eClass____eClassifiers_name_prime = "eClassifiers";
		pClass__graph____graph.setSrc(pClass);
		pClass__graph____graph.setTrg(graph);
		ruleresult.getTranslatedEdges().add(pClass__graph____graph);
		graph__pClass____nodes.setSrc(graph);
		graph__pClass____nodes.setTrg(pClass);
		ruleresult.getTranslatedEdges().add(graph__pClass____nodes);
		pClassToEClass__eClass____target.setSrc(pClassToEClass);
		pClassToEClass__eClass____target.setTrg(eClass);
		ruleresult.getCreatedEdges().add(pClassToEClass__eClass____target);
		pClassToEClass__pClass____source.setSrc(pClassToEClass);
		pClassToEClass__pClass____source.setTrg(pClass);
		ruleresult.getCreatedEdges().add(pClassToEClass__pClass____source);
		eClass__rootPackage____ePackage.setSrc(eClass);
		eClass__rootPackage____ePackage.setTrg(rootPackage);
		ruleresult.getCreatedEdges().add(eClass__rootPackage____ePackage);
		rootPackage__eClass____eClassifiers.setSrc(rootPackage);
		rootPackage__eClass____eClassifiers.setTrg(eClass);
		ruleresult.getCreatedEdges().add(rootPackage__eClass____eClassifiers);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		pClass__graph____graph.setName(pClass__graph____graph_name_prime);
		graph__pClass____nodes.setName(graph__pClass____nodes_name_prime);
		pClassToEClass__eClass____target.setName(pClassToEClass__eClass____target_name_prime);
		pClassToEClass__pClass____source.setName(pClassToEClass__pClass____source_name_prime);
		eClass__rootPackage____ePackage.setName(eClass__rootPackage____ePackage_name_prime);
		rootPackage__eClass____eClassifiers.setName(rootPackage__eClass____eClassifiers_name_prime);
		return new Object[] { ruleresult, rootPackage, pClass, pClassToEClass, graph, eClass, pClass__graph____graph,
				graph__pClass____nodes, pClassToEClass__eClass____target, pClassToEClass__pClass____source,
				eClass__rootPackage____ePackage, rootPackage__eClass____eClassifiers };
	}

	public static final void pattern_SimpleClassNodeRule_1_5_registerobjects_expressionBBBBBBBB(
			SimpleClassNodeRule _this, PerformRuleResult ruleresult, EObject graphToPackage, EObject rootPackage,
			EObject pClass, EObject pClassToEClass, EObject graph, EObject eClass) {
		_this.registerObjects_FWD(ruleresult, graphToPackage, rootPackage, pClass, pClassToEClass, graph, eClass);

	}

	public static final PerformRuleResult pattern_SimpleClassNodeRule_1_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_2_1_preparereturnvalue_bindingFB(
			SimpleClassNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_2_1_preparereturnvalue_blackFBB(EClass eClass,
			SimpleClassNodeRule _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_FWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_2_1_preparereturnvalue_bindingAndBlackFFB(
			SimpleClassNodeRule _this) {
		Object[] result_pattern_SimpleClassNodeRule_2_1_preparereturnvalue_binding = pattern_SimpleClassNodeRule_2_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_SimpleClassNodeRule_2_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_SimpleClassNodeRule_2_1_preparereturnvalue_binding[0];

			Object[] result_pattern_SimpleClassNodeRule_2_1_preparereturnvalue_black = pattern_SimpleClassNodeRule_2_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_SimpleClassNodeRule_2_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_SimpleClassNodeRule_2_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_2_1_preparereturnvalue_greenBF(
			EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "SimpleClassNodeRule";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_SimpleClassNodeRule_2_2_corematch_bindingFFB(Match match) {
		EObject _localVariable_0 = match.getObject("pClass");
		EObject _localVariable_1 = match.getObject("graph");
		EObject tmpPClass = _localVariable_0;
		EObject tmpGraph = _localVariable_1;
		if (tmpPClass instanceof PClass) {
			PClass pClass = (PClass) tmpPClass;
			if (tmpGraph instanceof ClassGraph) {
				ClassGraph graph = (ClassGraph) tmpGraph;
				return new Object[] { pClass, graph, match };
			}
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_SimpleClassNodeRule_2_2_corematch_blackFFBBB(PClass pClass,
			ClassGraph graph, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (ClassGraphToEPackage graphToPackage : org.moflon.core.utilities.eMoflonEMFUtil
				.getOppositeReferenceTyped(graph, ClassGraphToEPackage.class, "source")) {
			EPackage rootPackage = graphToPackage.getTarget();
			if (rootPackage != null) {
				_result.add(new Object[] { graphToPackage, rootPackage, pClass, graph, match });
			}

		}
		return _result;
	}

	public static final Iterable<Object[]> pattern_SimpleClassNodeRule_2_3_findcontext_blackBBBB(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, PClass pClass, ClassGraph graph) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (graph.equals(pClass.getGraph())) {
			if (rootPackage.equals(graphToPackage.getTarget())) {
				if (graph.equals(graphToPackage.getSource())) {
					_result.add(new Object[] { graphToPackage, rootPackage, pClass, graph });
				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_2_3_findcontext_greenBBBBFFFFF(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, PClass pClass, ClassGraph graph) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		EMoflonEdge pClass__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pClass____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graphToPackage__rootPackage____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graphToPackage__graph____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String pClass__graph____graph_name_prime = "graph";
		String graph__pClass____nodes_name_prime = "nodes";
		String graphToPackage__rootPackage____target_name_prime = "target";
		String graphToPackage__graph____source_name_prime = "source";
		isApplicableMatch.getAllContextElements().add(graphToPackage);
		isApplicableMatch.getAllContextElements().add(rootPackage);
		isApplicableMatch.getAllContextElements().add(pClass);
		isApplicableMatch.getAllContextElements().add(graph);
		pClass__graph____graph.setSrc(pClass);
		pClass__graph____graph.setTrg(graph);
		isApplicableMatch.getAllContextElements().add(pClass__graph____graph);
		graph__pClass____nodes.setSrc(graph);
		graph__pClass____nodes.setTrg(pClass);
		isApplicableMatch.getAllContextElements().add(graph__pClass____nodes);
		graphToPackage__rootPackage____target.setSrc(graphToPackage);
		graphToPackage__rootPackage____target.setTrg(rootPackage);
		isApplicableMatch.getAllContextElements().add(graphToPackage__rootPackage____target);
		graphToPackage__graph____source.setSrc(graphToPackage);
		graphToPackage__graph____source.setTrg(graph);
		isApplicableMatch.getAllContextElements().add(graphToPackage__graph____source);
		pClass__graph____graph.setName(pClass__graph____graph_name_prime);
		graph__pClass____nodes.setName(graph__pClass____nodes_name_prime);
		graphToPackage__rootPackage____target.setName(graphToPackage__rootPackage____target_name_prime);
		graphToPackage__graph____source.setName(graphToPackage__graph____source_name_prime);
		return new Object[] { graphToPackage, rootPackage, pClass, graph, isApplicableMatch, pClass__graph____graph,
				graph__pClass____nodes, graphToPackage__rootPackage____target, graphToPackage__graph____source };
	}

	public static final Object[] pattern_SimpleClassNodeRule_2_4_solveCSP_bindingFBBBBBB(SimpleClassNodeRule _this,
			IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage, EPackage rootPackage,
			PClass pClass, ClassGraph graph) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_FWD(isApplicableMatch, graphToPackage, rootPackage, pClass,
				graph);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, graphToPackage, rootPackage, pClass, graph };
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_2_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_SimpleClassNodeRule_2_4_solveCSP_bindingAndBlackFBBBBBB(
			SimpleClassNodeRule _this, IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, PClass pClass, ClassGraph graph) {
		Object[] result_pattern_SimpleClassNodeRule_2_4_solveCSP_binding = pattern_SimpleClassNodeRule_2_4_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, graphToPackage, rootPackage, pClass, graph);
		if (result_pattern_SimpleClassNodeRule_2_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_SimpleClassNodeRule_2_4_solveCSP_binding[0];

			Object[] result_pattern_SimpleClassNodeRule_2_4_solveCSP_black = pattern_SimpleClassNodeRule_2_4_solveCSP_blackB(
					csp);
			if (result_pattern_SimpleClassNodeRule_2_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, graphToPackage, rootPackage, pClass, graph };
			}
		}
		return null;
	}

	public static final boolean pattern_SimpleClassNodeRule_2_5_checkCSP_expressionFBB(SimpleClassNodeRule _this,
			CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_2_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_SimpleClassNodeRule_2_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "SimpleClassNodeRule";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_SimpleClassNodeRule_2_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_10_1_initialbindings_blackBBBB(SimpleClassNodeRule _this,
			Match match, EPackage rootPackage, EClass eClass) {
		return new Object[] { _this, match, rootPackage, eClass };
	}

	public static final Object[] pattern_SimpleClassNodeRule_10_2_SolveCSP_bindingFBBBB(SimpleClassNodeRule _this,
			Match match, EPackage rootPackage, EClass eClass) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_BWD(match, rootPackage, eClass);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, rootPackage, eClass };
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_10_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_SimpleClassNodeRule_10_2_SolveCSP_bindingAndBlackFBBBB(
			SimpleClassNodeRule _this, Match match, EPackage rootPackage, EClass eClass) {
		Object[] result_pattern_SimpleClassNodeRule_10_2_SolveCSP_binding = pattern_SimpleClassNodeRule_10_2_SolveCSP_bindingFBBBB(
				_this, match, rootPackage, eClass);
		if (result_pattern_SimpleClassNodeRule_10_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_SimpleClassNodeRule_10_2_SolveCSP_binding[0];

			Object[] result_pattern_SimpleClassNodeRule_10_2_SolveCSP_black = pattern_SimpleClassNodeRule_10_2_SolveCSP_blackB(
					csp);
			if (result_pattern_SimpleClassNodeRule_10_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, rootPackage, eClass };
			}
		}
		return null;
	}

	public static final boolean pattern_SimpleClassNodeRule_10_3_CheckCSP_expressionFBB(SimpleClassNodeRule _this,
			CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_10_4_collectelementstobetranslated_blackBBB(Match match,
			EPackage rootPackage, EClass eClass) {
		return new Object[] { match, rootPackage, eClass };
	}

	public static final Object[] pattern_SimpleClassNodeRule_10_4_collectelementstobetranslated_greenBBBFF(Match match,
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

	public static final Object[] pattern_SimpleClassNodeRule_10_5_collectcontextelements_blackBBB(Match match,
			EPackage rootPackage, EClass eClass) {
		return new Object[] { match, rootPackage, eClass };
	}

	public static final Object[] pattern_SimpleClassNodeRule_10_5_collectcontextelements_greenBB(Match match,
			EPackage rootPackage) {
		match.getContextNodes().add(rootPackage);
		return new Object[] { match, rootPackage };
	}

	public static final void pattern_SimpleClassNodeRule_10_6_registerobjectstomatch_expressionBBBB(
			SimpleClassNodeRule _this, Match match, EPackage rootPackage, EClass eClass) {
		_this.registerObjectsToMatch_BWD(match, rootPackage, eClass);

	}

	public static final boolean pattern_SimpleClassNodeRule_10_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_SimpleClassNodeRule_10_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_11_1_performtransformation_bindingFFFFB(
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

	public static final Object[] pattern_SimpleClassNodeRule_11_1_performtransformation_blackBBBBFBB(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph, EClass eClass,
			SimpleClassNodeRule _this, IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { graphToPackage, rootPackage, graph, eClass, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_11_1_performtransformation_bindingAndBlackFFFFFBB(
			SimpleClassNodeRule _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_SimpleClassNodeRule_11_1_performtransformation_binding = pattern_SimpleClassNodeRule_11_1_performtransformation_bindingFFFFB(
				isApplicableMatch);
		if (result_pattern_SimpleClassNodeRule_11_1_performtransformation_binding != null) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result_pattern_SimpleClassNodeRule_11_1_performtransformation_binding[0];
			EPackage rootPackage = (EPackage) result_pattern_SimpleClassNodeRule_11_1_performtransformation_binding[1];
			ClassGraph graph = (ClassGraph) result_pattern_SimpleClassNodeRule_11_1_performtransformation_binding[2];
			EClass eClass = (EClass) result_pattern_SimpleClassNodeRule_11_1_performtransformation_binding[3];

			Object[] result_pattern_SimpleClassNodeRule_11_1_performtransformation_black = pattern_SimpleClassNodeRule_11_1_performtransformation_blackBBBBFBB(
					graphToPackage, rootPackage, graph, eClass, _this, isApplicableMatch);
			if (result_pattern_SimpleClassNodeRule_11_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_SimpleClassNodeRule_11_1_performtransformation_black[4];

				return new Object[] { graphToPackage, rootPackage, graph, eClass, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_11_1_performtransformation_greenFFBBB(ClassGraph graph,
			EClass eClass, CSP csp) {
		PClass pClass = LanguageFactory.eINSTANCE.createPClass();
		PNodeToEClassifier pClassToEClass = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		Object _localVariable_0 = csp.getValue("pClass", "label");
		pClass.setGraph(graph);
		pClassToEClass.setTarget(eClass);
		pClassToEClass.setSource(pClass);
		String pClass_label_prime = (String) _localVariable_0;
		pClass.setLabel(pClass_label_prime);
		return new Object[] { pClass, pClassToEClass, graph, eClass, csp };
	}

	public static final Object[] pattern_SimpleClassNodeRule_11_2_collecttranslatedelements_blackBBB(PClass pClass,
			PNodeToEClassifier pClassToEClass, EClass eClass) {
		return new Object[] { pClass, pClassToEClass, eClass };
	}

	public static final Object[] pattern_SimpleClassNodeRule_11_2_collecttranslatedelements_greenFBBB(PClass pClass,
			PNodeToEClassifier pClassToEClass, EClass eClass) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getCreatedElements().add(pClass);
		ruleresult.getCreatedLinkElements().add(pClassToEClass);
		ruleresult.getTranslatedElements().add(eClass);
		return new Object[] { ruleresult, pClass, pClassToEClass, eClass };
	}

	public static final Object[] pattern_SimpleClassNodeRule_11_3_bookkeepingforedges_blackBBBBBBB(
			PerformRuleResult ruleresult, EObject graphToPackage, EObject rootPackage, EObject pClass,
			EObject pClassToEClass, EObject graph, EObject eClass) {
		if (!graphToPackage.equals(rootPackage)) {
			if (!graphToPackage.equals(pClass)) {
				if (!graphToPackage.equals(pClassToEClass)) {
					if (!pClass.equals(rootPackage)) {
						if (!pClass.equals(pClassToEClass)) {
							if (!pClassToEClass.equals(rootPackage)) {
								if (!graph.equals(graphToPackage)) {
									if (!graph.equals(rootPackage)) {
										if (!graph.equals(pClass)) {
											if (!graph.equals(pClassToEClass)) {
												if (!eClass.equals(graphToPackage)) {
													if (!eClass.equals(rootPackage)) {
														if (!eClass.equals(pClass)) {
															if (!eClass.equals(pClassToEClass)) {
																if (!eClass.equals(graph)) {
																	return new Object[] { ruleresult, graphToPackage,
																			rootPackage, pClass, pClassToEClass, graph,
																			eClass };
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

	public static final Object[] pattern_SimpleClassNodeRule_11_3_bookkeepingforedges_greenBBBBBBFFFFFF(
			PerformRuleResult ruleresult, EObject rootPackage, EObject pClass, EObject pClassToEClass, EObject graph,
			EObject eClass) {
		EMoflonEdge pClass__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pClass____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pClassToEClass__eClass____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pClassToEClass__pClass____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge eClass__rootPackage____ePackage = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rootPackage__eClass____eClassifiers = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "SimpleClassNodeRule";
		String pClass__graph____graph_name_prime = "graph";
		String graph__pClass____nodes_name_prime = "nodes";
		String pClassToEClass__eClass____target_name_prime = "target";
		String pClassToEClass__pClass____source_name_prime = "source";
		String eClass__rootPackage____ePackage_name_prime = "ePackage";
		String rootPackage__eClass____eClassifiers_name_prime = "eClassifiers";
		pClass__graph____graph.setSrc(pClass);
		pClass__graph____graph.setTrg(graph);
		ruleresult.getCreatedEdges().add(pClass__graph____graph);
		graph__pClass____nodes.setSrc(graph);
		graph__pClass____nodes.setTrg(pClass);
		ruleresult.getCreatedEdges().add(graph__pClass____nodes);
		pClassToEClass__eClass____target.setSrc(pClassToEClass);
		pClassToEClass__eClass____target.setTrg(eClass);
		ruleresult.getCreatedEdges().add(pClassToEClass__eClass____target);
		pClassToEClass__pClass____source.setSrc(pClassToEClass);
		pClassToEClass__pClass____source.setTrg(pClass);
		ruleresult.getCreatedEdges().add(pClassToEClass__pClass____source);
		eClass__rootPackage____ePackage.setSrc(eClass);
		eClass__rootPackage____ePackage.setTrg(rootPackage);
		ruleresult.getTranslatedEdges().add(eClass__rootPackage____ePackage);
		rootPackage__eClass____eClassifiers.setSrc(rootPackage);
		rootPackage__eClass____eClassifiers.setTrg(eClass);
		ruleresult.getTranslatedEdges().add(rootPackage__eClass____eClassifiers);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		pClass__graph____graph.setName(pClass__graph____graph_name_prime);
		graph__pClass____nodes.setName(graph__pClass____nodes_name_prime);
		pClassToEClass__eClass____target.setName(pClassToEClass__eClass____target_name_prime);
		pClassToEClass__pClass____source.setName(pClassToEClass__pClass____source_name_prime);
		eClass__rootPackage____ePackage.setName(eClass__rootPackage____ePackage_name_prime);
		rootPackage__eClass____eClassifiers.setName(rootPackage__eClass____eClassifiers_name_prime);
		return new Object[] { ruleresult, rootPackage, pClass, pClassToEClass, graph, eClass, pClass__graph____graph,
				graph__pClass____nodes, pClassToEClass__eClass____target, pClassToEClass__pClass____source,
				eClass__rootPackage____ePackage, rootPackage__eClass____eClassifiers };
	}

	public static final void pattern_SimpleClassNodeRule_11_5_registerobjects_expressionBBBBBBBB(
			SimpleClassNodeRule _this, PerformRuleResult ruleresult, EObject graphToPackage, EObject rootPackage,
			EObject pClass, EObject pClassToEClass, EObject graph, EObject eClass) {
		_this.registerObjects_BWD(ruleresult, graphToPackage, rootPackage, pClass, pClassToEClass, graph, eClass);

	}

	public static final PerformRuleResult pattern_SimpleClassNodeRule_11_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_12_1_preparereturnvalue_bindingFB(
			SimpleClassNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_12_1_preparereturnvalue_blackFBB(EClass eClass,
			SimpleClassNodeRule _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_BWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_12_1_preparereturnvalue_bindingAndBlackFFB(
			SimpleClassNodeRule _this) {
		Object[] result_pattern_SimpleClassNodeRule_12_1_preparereturnvalue_binding = pattern_SimpleClassNodeRule_12_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_SimpleClassNodeRule_12_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_SimpleClassNodeRule_12_1_preparereturnvalue_binding[0];

			Object[] result_pattern_SimpleClassNodeRule_12_1_preparereturnvalue_black = pattern_SimpleClassNodeRule_12_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_SimpleClassNodeRule_12_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_SimpleClassNodeRule_12_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_12_1_preparereturnvalue_greenBF(
			EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "SimpleClassNodeRule";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_SimpleClassNodeRule_12_2_corematch_bindingFFB(Match match) {
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

	public static final Iterable<Object[]> pattern_SimpleClassNodeRule_12_2_corematch_blackFBFBB(EPackage rootPackage,
			EClass eClass, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		boolean eClass_interface = eClass.isInterface();
		if (Boolean.valueOf(eClass_interface).equals(false)) {
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

	public static final Iterable<Object[]> pattern_SimpleClassNodeRule_12_3_findcontext_blackBBBB(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph, EClass eClass) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (rootPackage.equals(graphToPackage.getTarget())) {
			if (rootPackage.equals(eClass.getEPackage())) {
				if (graph.equals(graphToPackage.getSource())) {
					boolean eClass_interface = eClass.isInterface();
					if (Boolean.valueOf(eClass_interface).equals(false)) {
						_result.add(new Object[] { graphToPackage, rootPackage, graph, eClass });
					}

				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_12_3_findcontext_greenBBBBFFFFF(
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

	public static final Object[] pattern_SimpleClassNodeRule_12_4_solveCSP_bindingFBBBBBB(SimpleClassNodeRule _this,
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

	public static final Object[] pattern_SimpleClassNodeRule_12_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_SimpleClassNodeRule_12_4_solveCSP_bindingAndBlackFBBBBBB(
			SimpleClassNodeRule _this, IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, ClassGraph graph, EClass eClass) {
		Object[] result_pattern_SimpleClassNodeRule_12_4_solveCSP_binding = pattern_SimpleClassNodeRule_12_4_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, graphToPackage, rootPackage, graph, eClass);
		if (result_pattern_SimpleClassNodeRule_12_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_SimpleClassNodeRule_12_4_solveCSP_binding[0];

			Object[] result_pattern_SimpleClassNodeRule_12_4_solveCSP_black = pattern_SimpleClassNodeRule_12_4_solveCSP_blackB(
					csp);
			if (result_pattern_SimpleClassNodeRule_12_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, graphToPackage, rootPackage, graph, eClass };
			}
		}
		return null;
	}

	public static final boolean pattern_SimpleClassNodeRule_12_5_checkCSP_expressionFBB(SimpleClassNodeRule _this,
			CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_12_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_SimpleClassNodeRule_12_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "SimpleClassNodeRule";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_SimpleClassNodeRule_12_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_20_1_preparereturnvalue_bindingFB(
			SimpleClassNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_20_1_preparereturnvalue_blackFBBF(EClass __eClass,
			SimpleClassNodeRule _this) {
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

	public static final Object[] pattern_SimpleClassNodeRule_20_1_preparereturnvalue_bindingAndBlackFFBF(
			SimpleClassNodeRule _this) {
		Object[] result_pattern_SimpleClassNodeRule_20_1_preparereturnvalue_binding = pattern_SimpleClassNodeRule_20_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_SimpleClassNodeRule_20_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_SimpleClassNodeRule_20_1_preparereturnvalue_binding[0];

			Object[] result_pattern_SimpleClassNodeRule_20_1_preparereturnvalue_black = pattern_SimpleClassNodeRule_20_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_SimpleClassNodeRule_20_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_SimpleClassNodeRule_20_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_SimpleClassNodeRule_20_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_20_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_SimpleClassNodeRule_20_2_testcorematchandDECs_blackFFB(
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
					if (Boolean.valueOf(eClass_interface).equals(false)) {
						_result.add(new Object[] { rootPackage, eClass, _edge_ePackage });
					}

				}
			}

		}

		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_20_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_SimpleClassNodeRule_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
			SimpleClassNodeRule _this, Match match, EPackage rootPackage, EClass eClass) {
		boolean _localVariable_0 = _this.isAppropriate_BWD(match, rootPackage, eClass);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_SimpleClassNodeRule_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			SimpleClassNodeRule _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_BWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_20_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_20_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_SimpleClassNodeRule_20_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_21_1_preparereturnvalue_bindingFB(
			SimpleClassNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_21_1_preparereturnvalue_blackFBBF(EClass __eClass,
			SimpleClassNodeRule _this) {
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

	public static final Object[] pattern_SimpleClassNodeRule_21_1_preparereturnvalue_bindingAndBlackFFBF(
			SimpleClassNodeRule _this) {
		Object[] result_pattern_SimpleClassNodeRule_21_1_preparereturnvalue_binding = pattern_SimpleClassNodeRule_21_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_SimpleClassNodeRule_21_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_SimpleClassNodeRule_21_1_preparereturnvalue_binding[0];

			Object[] result_pattern_SimpleClassNodeRule_21_1_preparereturnvalue_black = pattern_SimpleClassNodeRule_21_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_SimpleClassNodeRule_21_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_SimpleClassNodeRule_21_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_SimpleClassNodeRule_21_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_21_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_SimpleClassNodeRule_21_2_testcorematchandDECs_blackFFB(
			EMoflonEdge _edge_graph) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		EObject tmpPClass = _edge_graph.getSrc();
		if (tmpPClass instanceof PClass) {
			PClass pClass = (PClass) tmpPClass;
			EObject tmpGraph = _edge_graph.getTrg();
			if (tmpGraph instanceof ClassGraph) {
				ClassGraph graph = (ClassGraph) tmpGraph;
				if (graph.equals(pClass.getGraph())) {
					_result.add(new Object[] { pClass, graph, _edge_graph });
				}
			}

		}

		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_21_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_SimpleClassNodeRule_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
			SimpleClassNodeRule _this, Match match, PClass pClass, ClassGraph graph) {
		boolean _localVariable_0 = _this.isAppropriate_FWD(match, pClass, graph);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_SimpleClassNodeRule_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			SimpleClassNodeRule _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_FWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_21_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_21_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_SimpleClassNodeRule_21_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_24_1_prepare_blackB(SimpleClassNodeRule _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_SimpleClassNodeRule_24_1_prepare_greenF() {
		IsApplicableRuleResult result = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		return new Object[] { result };
	}

	public static final Object[] pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_bindingFFFFBB(Match targetMatch,
			Match sourceMatch) {
		EObject _localVariable_0 = targetMatch.getObject("rootPackage");
		EObject _localVariable_1 = sourceMatch.getObject("pClass");
		EObject _localVariable_2 = sourceMatch.getObject("graph");
		EObject _localVariable_3 = targetMatch.getObject("eClass");
		EObject tmpRootPackage = _localVariable_0;
		EObject tmpPClass = _localVariable_1;
		EObject tmpGraph = _localVariable_2;
		EObject tmpEClass = _localVariable_3;
		if (tmpRootPackage instanceof EPackage) {
			EPackage rootPackage = (EPackage) tmpRootPackage;
			if (tmpPClass instanceof PClass) {
				PClass pClass = (PClass) tmpPClass;
				if (tmpGraph instanceof ClassGraph) {
					ClassGraph graph = (ClassGraph) tmpGraph;
					if (tmpEClass instanceof EClass) {
						EClass eClass = (EClass) tmpEClass;
						return new Object[] { rootPackage, pClass, graph, eClass, targetMatch, sourceMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_blackBBBBBB(EPackage rootPackage,
			PClass pClass, ClassGraph graph, EClass eClass, Match sourceMatch, Match targetMatch) {
		if (!sourceMatch.equals(targetMatch)) {
			boolean eClass_interface = eClass.isInterface();
			if (Boolean.valueOf(eClass_interface).equals(false)) {
				return new Object[] { rootPackage, pClass, graph, eClass, sourceMatch, targetMatch };
			}

		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_bindingAndBlackFFFFBB(
			Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_binding = pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_bindingFFFFBB(
				targetMatch, sourceMatch);
		if (result_pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_binding != null) {
			EPackage rootPackage = (EPackage) result_pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_binding[0];
			PClass pClass = (PClass) result_pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_binding[1];
			ClassGraph graph = (ClassGraph) result_pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_binding[2];
			EClass eClass = (EClass) result_pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_binding[3];

			Object[] result_pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_black = pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_blackBBBBBB(
					rootPackage, pClass, graph, eClass, sourceMatch, targetMatch);
			if (result_pattern_SimpleClassNodeRule_24_2_matchsrctrgcontext_black != null) {

				return new Object[] { rootPackage, pClass, graph, eClass, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_24_3_solvecsp_bindingFBBBBBBB(SimpleClassNodeRule _this,
			EPackage rootPackage, PClass pClass, ClassGraph graph, EClass eClass, Match sourceMatch,
			Match targetMatch) {
		CSP _localVariable_4 = _this.isApplicable_solveCsp_CC(rootPackage, pClass, graph, eClass, sourceMatch,
				targetMatch);
		CSP csp = _localVariable_4;
		if (csp != null) {
			return new Object[] { csp, _this, rootPackage, pClass, graph, eClass, sourceMatch, targetMatch };
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_24_3_solvecsp_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_SimpleClassNodeRule_24_3_solvecsp_bindingAndBlackFBBBBBBB(
			SimpleClassNodeRule _this, EPackage rootPackage, PClass pClass, ClassGraph graph, EClass eClass,
			Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_SimpleClassNodeRule_24_3_solvecsp_binding = pattern_SimpleClassNodeRule_24_3_solvecsp_bindingFBBBBBBB(
				_this, rootPackage, pClass, graph, eClass, sourceMatch, targetMatch);
		if (result_pattern_SimpleClassNodeRule_24_3_solvecsp_binding != null) {
			CSP csp = (CSP) result_pattern_SimpleClassNodeRule_24_3_solvecsp_binding[0];

			Object[] result_pattern_SimpleClassNodeRule_24_3_solvecsp_black = pattern_SimpleClassNodeRule_24_3_solvecsp_blackB(
					csp);
			if (result_pattern_SimpleClassNodeRule_24_3_solvecsp_black != null) {

				return new Object[] { csp, _this, rootPackage, pClass, graph, eClass, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final boolean pattern_SimpleClassNodeRule_24_4_checkCSP_expressionFB(CSP csp) {
		boolean _localVariable_0 = csp.check();
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Iterable<Object[]> pattern_SimpleClassNodeRule_24_5_matchcorrcontext_blackFBBBB(
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

	public static final Object[] pattern_SimpleClassNodeRule_24_5_matchcorrcontext_greenBBBF(
			ClassGraphToEPackage graphToPackage, Match sourceMatch, Match targetMatch) {
		CCMatch ccMatch = RuntimeFactory.eINSTANCE.createCCMatch();
		String ccMatch_ruleName_prime = "SimpleClassNodeRule";
		ccMatch.setSourceMatch(sourceMatch);
		ccMatch.setTargetMatch(targetMatch);
		ccMatch.getAllContextElements().add(graphToPackage);
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { graphToPackage, sourceMatch, targetMatch, ccMatch };
	}

	public static final Object[] pattern_SimpleClassNodeRule_24_6_createcorrespondence_blackBBBBB(EPackage rootPackage,
			PClass pClass, ClassGraph graph, EClass eClass, CCMatch ccMatch) {
		return new Object[] { rootPackage, pClass, graph, eClass, ccMatch };
	}

	public static final Object[] pattern_SimpleClassNodeRule_24_6_createcorrespondence_greenBFBB(PClass pClass,
			EClass eClass, CCMatch ccMatch) {
		PNodeToEClassifier pClassToEClass = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		pClassToEClass.setTarget(eClass);
		pClassToEClass.setSource(pClass);
		ccMatch.getCreateCorr().add(pClassToEClass);
		return new Object[] { pClass, pClassToEClass, eClass, ccMatch };
	}

	public static final Object[] pattern_SimpleClassNodeRule_24_7_addtoreturnedresult_blackBB(
			IsApplicableRuleResult result, CCMatch ccMatch) {
		return new Object[] { result, ccMatch };
	}

	public static final Object[] pattern_SimpleClassNodeRule_24_7_addtoreturnedresult_greenBB(
			IsApplicableRuleResult result, CCMatch ccMatch) {
		result.getIsApplicableMatch().add(ccMatch);
		boolean result_success_prime = Boolean.valueOf(true);
		String ccMatch_ruleName_prime = "SimpleClassNodeRule";
		result.setSuccess(Boolean.valueOf(result_success_prime));
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { result, ccMatch };
	}

	public static final IsApplicableRuleResult pattern_SimpleClassNodeRule_24_8_expressionFB(
			IsApplicableRuleResult result) {
		IsApplicableRuleResult _result = result;
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_27_1_matchtggpattern_blackBB(PClass pClass,
			ClassGraph graph) {
		if (graph.equals(pClass.getGraph())) {
			return new Object[] { pClass, graph };
		}
		return null;
	}

	public static final boolean pattern_SimpleClassNodeRule_27_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_SimpleClassNodeRule_27_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_28_1_matchtggpattern_blackBB(EPackage rootPackage,
			EClass eClass) {
		if (rootPackage.equals(eClass.getEPackage())) {
			return new Object[] { rootPackage, eClass };
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_28_1_matchtggpattern_greenB(EClass eClass) {
		boolean eClass_interface_prime = false;
		eClass.setInterface(Boolean.valueOf(eClass_interface_prime));
		return new Object[] { eClass };
	}

	public static final boolean pattern_SimpleClassNodeRule_28_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_SimpleClassNodeRule_28_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_29_1_createresult_blackB(SimpleClassNodeRule _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_SimpleClassNodeRule_29_1_createresult_greenFF() {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		ModelgeneratorRuleResult ruleResult = RuntimeFactory.eINSTANCE.createModelgeneratorRuleResult();
		boolean ruleResult_success_prime = false;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		return new Object[] { isApplicableMatch, ruleResult };
	}

	public static final Object[] pattern_SimpleClassNodeRule_29_2_isapplicablecore_black_nac_0BB(
			ModelgeneratorRuleResult ruleResult, ClassGraphToEPackage graphToPackage) {
		if (ruleResult.getCorrObjects().contains(graphToPackage)) {
			return new Object[] { ruleResult, graphToPackage };
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_29_2_isapplicablecore_black_nac_1BB(
			ModelgeneratorRuleResult ruleResult, EPackage rootPackage) {
		if (ruleResult.getTargetObjects().contains(rootPackage)) {
			return new Object[] { ruleResult, rootPackage };
		}
		return null;
	}

	public static final Object[] pattern_SimpleClassNodeRule_29_2_isapplicablecore_black_nac_2BB(
			ModelgeneratorRuleResult ruleResult, ClassGraph graph) {
		if (ruleResult.getSourceObjects().contains(graph)) {
			return new Object[] { ruleResult, graph };
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_SimpleClassNodeRule_29_2_isapplicablecore_blackFFFFBB(
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
							if (pattern_SimpleClassNodeRule_29_2_isapplicablecore_black_nac_0BB(ruleResult,
									graphToPackage) == null) {
								if (pattern_SimpleClassNodeRule_29_2_isapplicablecore_black_nac_1BB(ruleResult,
										rootPackage) == null) {
									if (pattern_SimpleClassNodeRule_29_2_isapplicablecore_black_nac_2BB(ruleResult,
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

	public static final Object[] pattern_SimpleClassNodeRule_29_3_solveCSP_bindingFBBBBBB(SimpleClassNodeRule _this,
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

	public static final Object[] pattern_SimpleClassNodeRule_29_3_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_SimpleClassNodeRule_29_3_solveCSP_bindingAndBlackFBBBBBB(
			SimpleClassNodeRule _this, IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, ClassGraph graph, ModelgeneratorRuleResult ruleResult) {
		Object[] result_pattern_SimpleClassNodeRule_29_3_solveCSP_binding = pattern_SimpleClassNodeRule_29_3_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, graphToPackage, rootPackage, graph, ruleResult);
		if (result_pattern_SimpleClassNodeRule_29_3_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_SimpleClassNodeRule_29_3_solveCSP_binding[0];

			Object[] result_pattern_SimpleClassNodeRule_29_3_solveCSP_black = pattern_SimpleClassNodeRule_29_3_solveCSP_blackB(
					csp);
			if (result_pattern_SimpleClassNodeRule_29_3_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, graphToPackage, rootPackage, graph, ruleResult };
			}
		}
		return null;
	}

	public static final boolean pattern_SimpleClassNodeRule_29_4_checkCSP_expressionFBB(SimpleClassNodeRule _this,
			CSP csp) {
		boolean _localVariable_0 = _this.generateModel_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_SimpleClassNodeRule_29_5_checknacs_blackBBB(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph) {
		return new Object[] { graphToPackage, rootPackage, graph };
	}

	public static final Object[] pattern_SimpleClassNodeRule_29_6_perform_blackBBBB(ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, ClassGraph graph, ModelgeneratorRuleResult ruleResult) {
		return new Object[] { graphToPackage, rootPackage, graph, ruleResult };
	}

	public static final Object[] pattern_SimpleClassNodeRule_29_6_perform_greenBFFBFBB(EPackage rootPackage,
			ClassGraph graph, ModelgeneratorRuleResult ruleResult, CSP csp) {
		PClass pClass = LanguageFactory.eINSTANCE.createPClass();
		PNodeToEClassifier pClassToEClass = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		Object _localVariable_0 = csp.getValue("pClass", "label");
		boolean eClass_interface_prime = false;
		Object _localVariable_1 = csp.getValue("eClass", "name");
		boolean ruleResult_success_prime = Boolean.valueOf(true);
		int _localVariable_2 = ruleResult.getIncrementedPerformCount();
		pClass.setGraph(graph);
		ruleResult.getSourceObjects().add(pClass);
		pClassToEClass.setSource(pClass);
		ruleResult.getCorrObjects().add(pClassToEClass);
		pClassToEClass.setTarget(eClass);
		rootPackage.getEClassifiers().add(eClass);
		ruleResult.getTargetObjects().add(eClass);
		String pClass_label_prime = (String) _localVariable_0;
		eClass.setInterface(Boolean.valueOf(eClass_interface_prime));
		String eClass_name_prime = (String) _localVariable_1;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		int ruleResult_performCount_prime = Integer.valueOf(_localVariable_2);
		pClass.setLabel(pClass_label_prime);
		eClass.setName(eClass_name_prime);
		ruleResult.setPerformCount(Integer.valueOf(ruleResult_performCount_prime));
		return new Object[] { rootPackage, pClass, pClassToEClass, graph, eClass, ruleResult, csp };
	}

	public static final ModelgeneratorRuleResult pattern_SimpleClassNodeRule_29_7_expressionFB(
			ModelgeneratorRuleResult ruleResult) {
		ModelgeneratorRuleResult _result = ruleResult;
		return _result;
	}

	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //SimpleClassNodeRuleImpl
