/**
 */
package org.moflon.ide.visualization.dot.tgg.schema.Rules.impl;

import java.lang.Iterable;

import java.lang.reflect.InvocationTargetException;

import java.util.LinkedList;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;

import org.moflon.ide.visualization.dot.language.Box;
import org.moflon.ide.visualization.dot.language.DirectedGraph;
import org.moflon.ide.visualization.dot.language.Inheritance;
import org.moflon.ide.visualization.dot.language.LanguageFactory;
import org.moflon.ide.visualization.dot.language.NodeCommand;

import org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar;
import org.moflon.ide.visualization.dot.tgg.schema.NodeToRule;

import org.moflon.ide.visualization.dot.tgg.schema.Rules.RefinmentRules;
import org.moflon.ide.visualization.dot.tgg.schema.Rules.RulesPackage;

import org.moflon.tgg.language.TGGRule;
import org.moflon.tgg.language.TripleGraphGrammar;

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
 * An implementation of the model object '<em><b>Refinment Rules</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class RefinmentRulesImpl extends AbstractRuleImpl implements RefinmentRules {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RefinmentRulesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulesPackage.Literals.REFINMENT_RULES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_FWD(Match match, Box superRuleBox, Box ruleBox, Inheritance refined,
			DirectedGraph directedGraph) {
		// initial bindings
		Object[] result1_black = RefinmentRulesImpl.pattern_RefinmentRules_0_1_initialbindings_blackBBBBBB(this, match,
				superRuleBox, ruleBox, refined, directedGraph);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[superRuleBox] = " + superRuleBox
					+ ", " + "[ruleBox] = " + ruleBox + ", " + "[refined] = " + refined + ", " + "[directedGraph] = "
					+ directedGraph + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = RefinmentRulesImpl
				.pattern_RefinmentRules_0_2_SolveCSP_bindingAndBlackFBBBBBB(this, match, superRuleBox, ruleBox, refined,
						directedGraph);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[superRuleBox] = " + superRuleBox + ", "
					+ "[ruleBox] = " + ruleBox + ", " + "[refined] = " + refined + ", " + "[directedGraph] = "
					+ directedGraph + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (RefinmentRulesImpl.pattern_RefinmentRules_0_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = RefinmentRulesImpl
					.pattern_RefinmentRules_0_4_collectelementstobetranslated_blackBBBBB(match, superRuleBox, ruleBox,
							refined, directedGraph);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[superRuleBox] = " + superRuleBox + ", "
						+ "[ruleBox] = " + ruleBox + ", " + "[refined] = " + refined + ", " + "[directedGraph] = "
						+ directedGraph + ".");
			}
			RefinmentRulesImpl.pattern_RefinmentRules_0_4_collectelementstobetranslated_greenBBBBBFFFF(match,
					superRuleBox, ruleBox, refined, directedGraph);
			// EMoflonEdge refined__ruleBox____source = (EMoflonEdge) result4_green[5];
			// EMoflonEdge directedGraph__refined____edges = (EMoflonEdge) result4_green[6];
			// EMoflonEdge refined__directedGraph____graph = (EMoflonEdge) result4_green[7];
			// EMoflonEdge refined__superRuleBox____target = (EMoflonEdge) result4_green[8];

			// collect context elements
			Object[] result5_black = RefinmentRulesImpl.pattern_RefinmentRules_0_5_collectcontextelements_blackBBBBB(
					match, superRuleBox, ruleBox, refined, directedGraph);
			if (result5_black == null) {
				throw new RuntimeException(
						"Pattern matching in node [collect context elements] failed." + " Variables: " + "[match] = "
								+ match + ", " + "[superRuleBox] = " + superRuleBox + ", " + "[ruleBox] = " + ruleBox
								+ ", " + "[refined] = " + refined + ", " + "[directedGraph] = " + directedGraph + ".");
			}
			RefinmentRulesImpl.pattern_RefinmentRules_0_5_collectcontextelements_greenBBBBFFFF(match, superRuleBox,
					ruleBox, directedGraph);
			// EMoflonEdge directedGraph__ruleBox____nodes = (EMoflonEdge) result5_green[4];
			// EMoflonEdge ruleBox__directedGraph____graph = (EMoflonEdge) result5_green[5];
			// EMoflonEdge directedGraph__superRuleBox____nodes = (EMoflonEdge) result5_green[6];
			// EMoflonEdge superRuleBox__directedGraph____graph = (EMoflonEdge) result5_green[7];

			// register objects to match
			RefinmentRulesImpl.pattern_RefinmentRules_0_6_registerobjectstomatch_expressionBBBBBB(this, match,
					superRuleBox, ruleBox, refined, directedGraph);
			return RefinmentRulesImpl.pattern_RefinmentRules_0_7_expressionF();
		} else {
			return RefinmentRulesImpl.pattern_RefinmentRules_0_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_FWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = RefinmentRulesImpl
				.pattern_RefinmentRules_1_1_performtransformation_bindingAndBlackFFFFFFFFFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		Box superRuleBox = (Box) result1_bindingAndBlack[0];
		NodeToRule nodeToRule = (NodeToRule) result1_bindingAndBlack[1];
		Box ruleBox = (Box) result1_bindingAndBlack[2];
		Inheritance refined = (Inheritance) result1_bindingAndBlack[3];
		TGGRule rule = (TGGRule) result1_bindingAndBlack[4];
		TGGRule superRule = (TGGRule) result1_bindingAndBlack[5];
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result1_bindingAndBlack[6];
		DirectedGraph directedGraph = (DirectedGraph) result1_bindingAndBlack[7];
		DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result1_bindingAndBlack[8];
		// CSP csp = (CSP) result1_bindingAndBlack[9];
		RefinmentRulesImpl.pattern_RefinmentRules_1_1_performtransformation_greenBB(rule, superRule);

		// collect translated elements
		Object[] result2_black = RefinmentRulesImpl
				.pattern_RefinmentRules_1_2_collecttranslatedelements_blackB(refined);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[refined] = " + refined + ".");
		}
		Object[] result2_green = RefinmentRulesImpl
				.pattern_RefinmentRules_1_2_collecttranslatedelements_greenFB(refined);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = RefinmentRulesImpl.pattern_RefinmentRules_1_3_bookkeepingforedges_blackBBBBBBBBBB(
				ruleresult, superRuleBox, nodeToRule, ruleBox, refined, rule, superRule, tripleGraphGrammar,
				directedGraph, directedGraphToTripleGraphGrammar);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[superRuleBox] = " + superRuleBox + ", "
					+ "[nodeToRule] = " + nodeToRule + ", " + "[ruleBox] = " + ruleBox + ", " + "[refined] = " + refined
					+ ", " + "[rule] = " + rule + ", " + "[superRule] = " + superRule + ", " + "[tripleGraphGrammar] = "
					+ tripleGraphGrammar + ", " + "[directedGraph] = " + directedGraph + ", "
					+ "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ".");
		}
		RefinmentRulesImpl.pattern_RefinmentRules_1_3_bookkeepingforedges_greenBBBBBBBFFFFF(ruleresult, superRuleBox,
				ruleBox, refined, rule, superRule, directedGraph);
		// EMoflonEdge refined__ruleBox____source = (EMoflonEdge) result3_green[7];
		// EMoflonEdge rule__superRule____refines = (EMoflonEdge) result3_green[8];
		// EMoflonEdge directedGraph__refined____edges = (EMoflonEdge) result3_green[9];
		// EMoflonEdge refined__directedGraph____graph = (EMoflonEdge) result3_green[10];
		// EMoflonEdge refined__superRuleBox____target = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		RefinmentRulesImpl.pattern_RefinmentRules_1_5_registerobjects_expressionBBBBBBBBBBB(this, ruleresult,
				superRuleBox, nodeToRule, ruleBox, refined, rule, superRule, tripleGraphGrammar, directedGraph,
				directedGraphToTripleGraphGrammar);
		return RefinmentRulesImpl.pattern_RefinmentRules_1_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_FWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = RefinmentRulesImpl
				.pattern_RefinmentRules_2_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = RefinmentRulesImpl
				.pattern_RefinmentRules_2_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = RefinmentRulesImpl.pattern_RefinmentRules_2_2_corematch_bindingFFFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		Box superRuleBox = (Box) result2_binding[0];
		Box ruleBox = (Box) result2_binding[1];
		Inheritance refined = (Inheritance) result2_binding[2];
		DirectedGraph directedGraph = (DirectedGraph) result2_binding[3];
		for (Object[] result2_black : RefinmentRulesImpl.pattern_RefinmentRules_2_2_corematch_blackBFBBFFBFB(
				superRuleBox, ruleBox, refined, directedGraph, match)) {
			NodeToRule nodeToRule = (NodeToRule) result2_black[1];
			TGGRule rule = (TGGRule) result2_black[4];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_black[5];
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result2_black[7];
			// ForEach find context
			for (Object[] result3_black : RefinmentRulesImpl.pattern_RefinmentRules_2_3_findcontext_blackBBBBBFBBB(
					superRuleBox, nodeToRule, ruleBox, refined, rule, tripleGraphGrammar, directedGraph,
					directedGraphToTripleGraphGrammar)) {
				TGGRule superRule = (TGGRule) result3_black[5];
				Object[] result3_green = RefinmentRulesImpl
						.pattern_RefinmentRules_2_3_findcontext_greenBBBBBBBBBFFFFFFFFFFFFFFFFF(superRuleBox,
								nodeToRule, ruleBox, refined, rule, superRule, tripleGraphGrammar, directedGraph,
								directedGraphToTripleGraphGrammar);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[9];
				// EMoflonEdge tripleGraphGrammar__rule____tggRule = (EMoflonEdge) result3_green[10];
				// EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result3_green[11];
				// EMoflonEdge refined__ruleBox____source = (EMoflonEdge) result3_green[12];
				// EMoflonEdge nodeToRule__rule____target = (EMoflonEdge) result3_green[13];
				// EMoflonEdge directedGraph__ruleBox____nodes = (EMoflonEdge) result3_green[14];
				// EMoflonEdge ruleBox__directedGraph____graph = (EMoflonEdge) result3_green[15];
				// EMoflonEdge directedGraph__refined____edges = (EMoflonEdge) result3_green[16];
				// EMoflonEdge refined__directedGraph____graph = (EMoflonEdge) result3_green[17];
				// EMoflonEdge refined__superRuleBox____target = (EMoflonEdge) result3_green[18];
				// EMoflonEdge tripleGraphGrammar__superRule____tggRule = (EMoflonEdge) result3_green[19];
				// EMoflonEdge superRule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result3_green[20];
				// EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = (EMoflonEdge) result3_green[21];
				// EMoflonEdge directedGraph__superRuleBox____nodes = (EMoflonEdge) result3_green[22];
				// EMoflonEdge superRuleBox__directedGraph____graph = (EMoflonEdge) result3_green[23];
				// EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = (EMoflonEdge) result3_green[24];
				// EMoflonEdge nodeToRule__ruleBox____source = (EMoflonEdge) result3_green[25];

				// solve CSP
				Object[] result4_bindingAndBlack = RefinmentRulesImpl
						.pattern_RefinmentRules_2_4_solveCSP_bindingAndBlackFBBBBBBBBBBB(this, isApplicableMatch,
								superRuleBox, nodeToRule, ruleBox, refined, rule, superRule, tripleGraphGrammar,
								directedGraph, directedGraphToTripleGraphGrammar);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[superRuleBox] = " + superRuleBox + ", " + "[nodeToRule] = " + nodeToRule + ", "
							+ "[ruleBox] = " + ruleBox + ", " + "[refined] = " + refined + ", " + "[rule] = " + rule
							+ ", " + "[superRule] = " + superRule + ", " + "[tripleGraphGrammar] = "
							+ tripleGraphGrammar + ", " + "[directedGraph] = " + directedGraph + ", "
							+ "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (RefinmentRulesImpl.pattern_RefinmentRules_2_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = RefinmentRulesImpl
							.pattern_RefinmentRules_2_6_addmatchtoruleresult_blackBB(ruleresult, isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					RefinmentRulesImpl.pattern_RefinmentRules_2_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return RefinmentRulesImpl.pattern_RefinmentRules_2_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_FWD(Match match, Box superRuleBox, Box ruleBox, Inheritance refined,
			DirectedGraph directedGraph) {
		match.registerObject("superRuleBox", superRuleBox);
		match.registerObject("ruleBox", ruleBox);
		match.registerObject("refined", refined);
		match.registerObject("directedGraph", directedGraph);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_FWD(Match match, Box superRuleBox, Box ruleBox, Inheritance refined,
			DirectedGraph directedGraph) {// Create CSP
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
	public CSP isApplicable_solveCsp_FWD(IsApplicableMatch isApplicableMatch, Box superRuleBox, NodeToRule nodeToRule,
			Box ruleBox, Inheritance refined, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_superRuleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("superRuleBox.label", true, csp);
		var_superRuleBox_label.setValue(superRuleBox.getLabel());
		var_superRuleBox_label.setType("String");
		Variable var_superRule_name = CSPFactoryHelper.eINSTANCE.createVariable("superRule.name", true, csp);
		var_superRule_name.setValue(superRule.getName());
		var_superRule_name.setType("String");
		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox.label", true, csp);
		var_ruleBox_label.setValue(ruleBox.getLabel());
		var_ruleBox_label.setType("String");
		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule.name", true, csp);
		var_rule_name.setValue(rule.getName());
		var_rule_name.setType("String");

		// Create unbound variables

		// Create constraints
		Eq eq = new Eq();
		Eq eq_0 = new Eq();

		csp.getConstraints().add(eq);
		csp.getConstraints().add(eq_0);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_superRuleBox_label, var_superRule_name);
		eq_0.setRuleName("");
		eq_0.solve(var_ruleBox_label, var_rule_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("superRuleBox", superRuleBox);
		isApplicableMatch.registerObject("nodeToRule", nodeToRule);
		isApplicableMatch.registerObject("ruleBox", ruleBox);
		isApplicableMatch.registerObject("refined", refined);
		isApplicableMatch.registerObject("rule", rule);
		isApplicableMatch.registerObject("superRule", superRule);
		isApplicableMatch.registerObject("tripleGraphGrammar", tripleGraphGrammar);
		isApplicableMatch.registerObject("directedGraph", directedGraph);
		isApplicableMatch.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);
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
	public void registerObjects_FWD(PerformRuleResult ruleresult, EObject superRuleBox, EObject nodeToRule,
			EObject ruleBox, EObject refined, EObject rule, EObject superRule, EObject tripleGraphGrammar,
			EObject directedGraph, EObject directedGraphToTripleGraphGrammar) {
		ruleresult.registerObject("superRuleBox", superRuleBox);
		ruleresult.registerObject("nodeToRule", nodeToRule);
		ruleresult.registerObject("ruleBox", ruleBox);
		ruleresult.registerObject("refined", refined);
		ruleresult.registerObject("rule", rule);
		ruleresult.registerObject("superRule", superRule);
		ruleresult.registerObject("tripleGraphGrammar", tripleGraphGrammar);
		ruleresult.registerObject("directedGraph", directedGraph);
		ruleresult.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_FWD(Match match) {
		return true && org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("refined").eClass())
				.equals("language.Inheritance.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_BWD(Match match, TGGRule rule, TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar) {
		// initial bindings
		Object[] result1_black = RefinmentRulesImpl.pattern_RefinmentRules_10_1_initialbindings_blackBBBBB(this, match,
				rule, superRule, tripleGraphGrammar);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[rule] = " + rule + ", "
					+ "[superRule] = " + superRule + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = RefinmentRulesImpl
				.pattern_RefinmentRules_10_2_SolveCSP_bindingAndBlackFBBBBB(this, match, rule, superRule,
						tripleGraphGrammar);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[rule] = " + rule + ", " + "[superRule] = "
					+ superRule + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (RefinmentRulesImpl.pattern_RefinmentRules_10_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = RefinmentRulesImpl
					.pattern_RefinmentRules_10_4_collectelementstobetranslated_blackBBBB(match, rule, superRule,
							tripleGraphGrammar);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[rule] = " + rule + ", " + "[superRule] = "
						+ superRule + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
			}
			RefinmentRulesImpl.pattern_RefinmentRules_10_4_collectelementstobetranslated_greenBBBF(match, rule,
					superRule);
			// EMoflonEdge rule__superRule____refines = (EMoflonEdge) result4_green[3];

			// collect context elements
			Object[] result5_black = RefinmentRulesImpl.pattern_RefinmentRules_10_5_collectcontextelements_blackBBBB(
					match, rule, superRule, tripleGraphGrammar);
			if (result5_black == null) {
				throw new RuntimeException("Pattern matching in node [collect context elements] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[rule] = " + rule + ", " + "[superRule] = "
						+ superRule + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
			}
			RefinmentRulesImpl.pattern_RefinmentRules_10_5_collectcontextelements_greenBBBBFFFF(match, rule, superRule,
					tripleGraphGrammar);
			// EMoflonEdge tripleGraphGrammar__rule____tggRule = (EMoflonEdge) result5_green[4];
			// EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result5_green[5];
			// EMoflonEdge tripleGraphGrammar__superRule____tggRule = (EMoflonEdge) result5_green[6];
			// EMoflonEdge superRule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result5_green[7];

			// register objects to match
			RefinmentRulesImpl.pattern_RefinmentRules_10_6_registerobjectstomatch_expressionBBBBB(this, match, rule,
					superRule, tripleGraphGrammar);
			return RefinmentRulesImpl.pattern_RefinmentRules_10_7_expressionF();
		} else {
			return RefinmentRulesImpl.pattern_RefinmentRules_10_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_BWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = RefinmentRulesImpl
				.pattern_RefinmentRules_11_1_performtransformation_bindingAndBlackFFFFFFFFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		Box superRuleBox = (Box) result1_bindingAndBlack[0];
		NodeToRule nodeToRule = (NodeToRule) result1_bindingAndBlack[1];
		Box ruleBox = (Box) result1_bindingAndBlack[2];
		TGGRule rule = (TGGRule) result1_bindingAndBlack[3];
		TGGRule superRule = (TGGRule) result1_bindingAndBlack[4];
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result1_bindingAndBlack[5];
		DirectedGraph directedGraph = (DirectedGraph) result1_bindingAndBlack[6];
		DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result1_bindingAndBlack[7];
		// CSP csp = (CSP) result1_bindingAndBlack[8];
		Object[] result1_green = RefinmentRulesImpl
				.pattern_RefinmentRules_11_1_performtransformation_greenBBFB(superRuleBox, ruleBox, directedGraph);
		Inheritance refined = (Inheritance) result1_green[2];

		// collect translated elements
		Object[] result2_black = RefinmentRulesImpl
				.pattern_RefinmentRules_11_2_collecttranslatedelements_blackB(refined);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[refined] = " + refined + ".");
		}
		Object[] result2_green = RefinmentRulesImpl
				.pattern_RefinmentRules_11_2_collecttranslatedelements_greenFB(refined);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = RefinmentRulesImpl.pattern_RefinmentRules_11_3_bookkeepingforedges_blackBBBBBBBBBB(
				ruleresult, superRuleBox, nodeToRule, ruleBox, refined, rule, superRule, tripleGraphGrammar,
				directedGraph, directedGraphToTripleGraphGrammar);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[superRuleBox] = " + superRuleBox + ", "
					+ "[nodeToRule] = " + nodeToRule + ", " + "[ruleBox] = " + ruleBox + ", " + "[refined] = " + refined
					+ ", " + "[rule] = " + rule + ", " + "[superRule] = " + superRule + ", " + "[tripleGraphGrammar] = "
					+ tripleGraphGrammar + ", " + "[directedGraph] = " + directedGraph + ", "
					+ "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ".");
		}
		RefinmentRulesImpl.pattern_RefinmentRules_11_3_bookkeepingforedges_greenBBBBBBBFFFFF(ruleresult, superRuleBox,
				ruleBox, refined, rule, superRule, directedGraph);
		// EMoflonEdge refined__ruleBox____source = (EMoflonEdge) result3_green[7];
		// EMoflonEdge rule__superRule____refines = (EMoflonEdge) result3_green[8];
		// EMoflonEdge directedGraph__refined____edges = (EMoflonEdge) result3_green[9];
		// EMoflonEdge refined__directedGraph____graph = (EMoflonEdge) result3_green[10];
		// EMoflonEdge refined__superRuleBox____target = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		RefinmentRulesImpl.pattern_RefinmentRules_11_5_registerobjects_expressionBBBBBBBBBBB(this, ruleresult,
				superRuleBox, nodeToRule, ruleBox, refined, rule, superRule, tripleGraphGrammar, directedGraph,
				directedGraphToTripleGraphGrammar);
		return RefinmentRulesImpl.pattern_RefinmentRules_11_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_BWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = RefinmentRulesImpl
				.pattern_RefinmentRules_12_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = RefinmentRulesImpl
				.pattern_RefinmentRules_12_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = RefinmentRulesImpl.pattern_RefinmentRules_12_2_corematch_bindingFFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		TGGRule rule = (TGGRule) result2_binding[0];
		TGGRule superRule = (TGGRule) result2_binding[1];
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_binding[2];
		for (Object[] result2_black : RefinmentRulesImpl.pattern_RefinmentRules_12_2_corematch_blackFFBBBFFB(rule,
				superRule, tripleGraphGrammar, match)) {
			NodeToRule nodeToRule = (NodeToRule) result2_black[0];
			Box ruleBox = (Box) result2_black[1];
			DirectedGraph directedGraph = (DirectedGraph) result2_black[5];
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result2_black[6];
			// ForEach find context
			for (Object[] result3_black : RefinmentRulesImpl.pattern_RefinmentRules_12_3_findcontext_blackFBBBBBBB(
					nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar, directedGraph,
					directedGraphToTripleGraphGrammar)) {
				Box superRuleBox = (Box) result3_black[0];
				Object[] result3_green = RefinmentRulesImpl
						.pattern_RefinmentRules_12_3_findcontext_greenBBBBBBBBFFFFFFFFFFFFFF(superRuleBox, nodeToRule,
								ruleBox, rule, superRule, tripleGraphGrammar, directedGraph,
								directedGraphToTripleGraphGrammar);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[8];
				// EMoflonEdge tripleGraphGrammar__rule____tggRule = (EMoflonEdge) result3_green[9];
				// EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result3_green[10];
				// EMoflonEdge nodeToRule__rule____target = (EMoflonEdge) result3_green[11];
				// EMoflonEdge rule__superRule____refines = (EMoflonEdge) result3_green[12];
				// EMoflonEdge directedGraph__ruleBox____nodes = (EMoflonEdge) result3_green[13];
				// EMoflonEdge ruleBox__directedGraph____graph = (EMoflonEdge) result3_green[14];
				// EMoflonEdge tripleGraphGrammar__superRule____tggRule = (EMoflonEdge) result3_green[15];
				// EMoflonEdge superRule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result3_green[16];
				// EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = (EMoflonEdge) result3_green[17];
				// EMoflonEdge directedGraph__superRuleBox____nodes = (EMoflonEdge) result3_green[18];
				// EMoflonEdge superRuleBox__directedGraph____graph = (EMoflonEdge) result3_green[19];
				// EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = (EMoflonEdge) result3_green[20];
				// EMoflonEdge nodeToRule__ruleBox____source = (EMoflonEdge) result3_green[21];

				// solve CSP
				Object[] result4_bindingAndBlack = RefinmentRulesImpl
						.pattern_RefinmentRules_12_4_solveCSP_bindingAndBlackFBBBBBBBBBB(this, isApplicableMatch,
								superRuleBox, nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar, directedGraph,
								directedGraphToTripleGraphGrammar);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[superRuleBox] = " + superRuleBox + ", " + "[nodeToRule] = " + nodeToRule + ", "
							+ "[ruleBox] = " + ruleBox + ", " + "[rule] = " + rule + ", " + "[superRule] = " + superRule
							+ ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ", " + "[directedGraph] = "
							+ directedGraph + ", " + "[directedGraphToTripleGraphGrammar] = "
							+ directedGraphToTripleGraphGrammar + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (RefinmentRulesImpl.pattern_RefinmentRules_12_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = RefinmentRulesImpl
							.pattern_RefinmentRules_12_6_addmatchtoruleresult_blackBB(ruleresult, isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					RefinmentRulesImpl.pattern_RefinmentRules_12_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return RefinmentRulesImpl.pattern_RefinmentRules_12_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_BWD(Match match, TGGRule rule, TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar) {
		match.registerObject("rule", rule);
		match.registerObject("superRule", superRule);
		match.registerObject("tripleGraphGrammar", tripleGraphGrammar);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_BWD(Match match, TGGRule rule, TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar) {// Create CSP
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
	public CSP isApplicable_solveCsp_BWD(IsApplicableMatch isApplicableMatch, Box superRuleBox, NodeToRule nodeToRule,
			Box ruleBox, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_superRuleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("superRuleBox.label", true, csp);
		var_superRuleBox_label.setValue(superRuleBox.getLabel());
		var_superRuleBox_label.setType("String");
		Variable var_superRule_name = CSPFactoryHelper.eINSTANCE.createVariable("superRule.name", true, csp);
		var_superRule_name.setValue(superRule.getName());
		var_superRule_name.setType("String");
		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox.label", true, csp);
		var_ruleBox_label.setValue(ruleBox.getLabel());
		var_ruleBox_label.setType("String");
		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule.name", true, csp);
		var_rule_name.setValue(rule.getName());
		var_rule_name.setType("String");

		// Create unbound variables

		// Create constraints
		Eq eq = new Eq();
		Eq eq_0 = new Eq();

		csp.getConstraints().add(eq);
		csp.getConstraints().add(eq_0);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_superRuleBox_label, var_superRule_name);
		eq_0.setRuleName("");
		eq_0.solve(var_ruleBox_label, var_rule_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("superRuleBox", superRuleBox);
		isApplicableMatch.registerObject("nodeToRule", nodeToRule);
		isApplicableMatch.registerObject("ruleBox", ruleBox);
		isApplicableMatch.registerObject("rule", rule);
		isApplicableMatch.registerObject("superRule", superRule);
		isApplicableMatch.registerObject("tripleGraphGrammar", tripleGraphGrammar);
		isApplicableMatch.registerObject("directedGraph", directedGraph);
		isApplicableMatch.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);
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
	public void registerObjects_BWD(PerformRuleResult ruleresult, EObject superRuleBox, EObject nodeToRule,
			EObject ruleBox, EObject refined, EObject rule, EObject superRule, EObject tripleGraphGrammar,
			EObject directedGraph, EObject directedGraphToTripleGraphGrammar) {
		ruleresult.registerObject("superRuleBox", superRuleBox);
		ruleresult.registerObject("nodeToRule", nodeToRule);
		ruleresult.registerObject("ruleBox", ruleBox);
		ruleresult.registerObject("refined", refined);
		ruleresult.registerObject("rule", rule);
		ruleresult.registerObject("superRule", superRule);
		ruleresult.registerObject("tripleGraphGrammar", tripleGraphGrammar);
		ruleresult.registerObject("directedGraph", directedGraph);
		ruleresult.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_BWD(Match match) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_FWD_EMoflonEdge_57(EMoflonEdge _edge_source) {
		// prepare return value
		Object[] result1_bindingAndBlack = RefinmentRulesImpl
				.pattern_RefinmentRules_20_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = RefinmentRulesImpl.pattern_RefinmentRules_20_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : RefinmentRulesImpl
				.pattern_RefinmentRules_20_2_testcorematchandDECs_blackFFFFB(_edge_source)) {
			Box superRuleBox = (Box) result2_black[0];
			Box ruleBox = (Box) result2_black[1];
			Inheritance refined = (Inheritance) result2_black[2];
			DirectedGraph directedGraph = (DirectedGraph) result2_black[3];
			Object[] result2_green = RefinmentRulesImpl
					.pattern_RefinmentRules_20_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (RefinmentRulesImpl
					.pattern_RefinmentRules_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBBBB(this,
							match, superRuleBox, ruleBox, refined, directedGraph)) {
				// Ensure that the correct types of elements are matched
				if (RefinmentRulesImpl
						.pattern_RefinmentRules_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(this,
								match)) {

					// Add match to rule result
					Object[] result5_black = RefinmentRulesImpl
							.pattern_RefinmentRules_20_5_Addmatchtoruleresult_blackBBBB(match, __performOperation,
									__result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					RefinmentRulesImpl.pattern_RefinmentRules_20_5_Addmatchtoruleresult_greenBBBB(match,
							__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return RefinmentRulesImpl.pattern_RefinmentRules_20_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_BWD_EMoflonEdge_82(EMoflonEdge _edge_refines) {
		// prepare return value
		Object[] result1_bindingAndBlack = RefinmentRulesImpl
				.pattern_RefinmentRules_21_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = RefinmentRulesImpl.pattern_RefinmentRules_21_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : RefinmentRulesImpl
				.pattern_RefinmentRules_21_2_testcorematchandDECs_blackFFFB(_edge_refines)) {
			TGGRule rule = (TGGRule) result2_black[0];
			TGGRule superRule = (TGGRule) result2_black[1];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_black[2];
			Object[] result2_green = RefinmentRulesImpl
					.pattern_RefinmentRules_21_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (RefinmentRulesImpl
					.pattern_RefinmentRules_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBBB(this, match,
							rule, superRule, tripleGraphGrammar)) {
				// Ensure that the correct types of elements are matched
				if (RefinmentRulesImpl
						.pattern_RefinmentRules_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(this,
								match)) {

					// Add match to rule result
					Object[] result5_black = RefinmentRulesImpl
							.pattern_RefinmentRules_21_5_Addmatchtoruleresult_blackBBBB(match, __performOperation,
									__result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					RefinmentRulesImpl.pattern_RefinmentRules_21_5_Addmatchtoruleresult_greenBBBB(match,
							__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return RefinmentRulesImpl.pattern_RefinmentRules_21_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintsRuleResult checkAttributes_FWD(TripleMatch __tripleMatch) {
		AttributeConstraintsRuleResult ruleResult = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE
				.createAttributeConstraintsRuleResult();
		ruleResult.setRule("RefinmentRules");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		Variable var_superRule_name = CSPFactoryHelper.eINSTANCE.createVariable("superRule", true, csp);
		var_superRule_name.setValue(__helper.getValue("superRule", "name"));
		var_superRule_name.setType("String");

		Variable var_superRuleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("superRuleBox", true, csp);
		var_superRuleBox_label.setValue(__helper.getValue("superRuleBox", "label"));
		var_superRuleBox_label.setType("String");

		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox", true, csp);
		var_ruleBox_label.setValue(__helper.getValue("ruleBox", "label"));
		var_ruleBox_label.setType("String");

		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule", true, csp);
		var_rule_name.setValue(__helper.getValue("rule", "name"));
		var_rule_name.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		Eq eq1 = new Eq();
		csp.getConstraints().add(eq1);

		eq0.setRuleName("RefinmentRules");
		eq0.solve(var_superRuleBox_label, var_superRule_name);

		eq1.setRuleName("RefinmentRules");
		eq1.solve(var_ruleBox_label, var_rule_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			eq0.solve(var_superRuleBox_label, var_superRule_name);
			eq1.solve(var_ruleBox_label, var_rule_name);
			if (csp.check()) {
				ruleResult.setSuccess(true);
				ruleResult.setRequiredChange(true);
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
		ruleResult.setRule("RefinmentRules");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		Variable var_superRule_name = CSPFactoryHelper.eINSTANCE.createVariable("superRule", true, csp);
		var_superRule_name.setValue(__helper.getValue("superRule", "name"));
		var_superRule_name.setType("String");

		Variable var_superRuleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("superRuleBox", true, csp);
		var_superRuleBox_label.setValue(__helper.getValue("superRuleBox", "label"));
		var_superRuleBox_label.setType("String");

		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox", true, csp);
		var_ruleBox_label.setValue(__helper.getValue("ruleBox", "label"));
		var_ruleBox_label.setType("String");

		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule", true, csp);
		var_rule_name.setValue(__helper.getValue("rule", "name"));
		var_rule_name.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		Eq eq1 = new Eq();
		csp.getConstraints().add(eq1);

		eq0.setRuleName("RefinmentRules");
		eq0.solve(var_superRuleBox_label, var_superRule_name);

		eq1.setRuleName("RefinmentRules");
		eq1.solve(var_ruleBox_label, var_rule_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			eq0.solve(var_superRuleBox_label, var_superRule_name);
			eq1.solve(var_ruleBox_label, var_rule_name);
			if (csp.check()) {
				ruleResult.setSuccess(true);
				ruleResult.setRequiredChange(true);
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
		Object[] result1_black = RefinmentRulesImpl.pattern_RefinmentRules_24_1_prepare_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [prepare] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = RefinmentRulesImpl.pattern_RefinmentRules_24_1_prepare_greenF();
		IsApplicableRuleResult result = (IsApplicableRuleResult) result1_green[0];

		// match src trg context
		Object[] result2_bindingAndBlack = RefinmentRulesImpl
				.pattern_RefinmentRules_24_2_matchsrctrgcontext_bindingAndBlackFFFFFFFBB(sourceMatch, targetMatch);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [match src trg context] failed." + " Variables: "
					+ "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = " + targetMatch + ".");
		}
		Box superRuleBox = (Box) result2_bindingAndBlack[0];
		Box ruleBox = (Box) result2_bindingAndBlack[1];
		Inheritance refined = (Inheritance) result2_bindingAndBlack[2];
		TGGRule rule = (TGGRule) result2_bindingAndBlack[3];
		TGGRule superRule = (TGGRule) result2_bindingAndBlack[4];
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_bindingAndBlack[5];
		DirectedGraph directedGraph = (DirectedGraph) result2_bindingAndBlack[6];

		// solve csp
		Object[] result3_bindingAndBlack = RefinmentRulesImpl
				.pattern_RefinmentRules_24_3_solvecsp_bindingAndBlackFBBBBBBBBBB(this, superRuleBox, ruleBox, refined,
						rule, superRule, tripleGraphGrammar, directedGraph, sourceMatch, targetMatch);
		if (result3_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [solve csp] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[superRuleBox] = " + superRuleBox + ", " + "[ruleBox] = " + ruleBox + ", "
					+ "[refined] = " + refined + ", " + "[rule] = " + rule + ", " + "[superRule] = " + superRule + ", "
					+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ", " + "[directedGraph] = " + directedGraph
					+ ", " + "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = " + targetMatch + ".");
		}
		CSP csp = (CSP) result3_bindingAndBlack[0];
		// check CSP
		if (RefinmentRulesImpl.pattern_RefinmentRules_24_4_checkCSP_expressionFB(csp)) {
			// ForEach match corr context
			for (Object[] result5_black : RefinmentRulesImpl.pattern_RefinmentRules_24_5_matchcorrcontext_blackFBBBBFBB(
					ruleBox, rule, tripleGraphGrammar, directedGraph, sourceMatch, targetMatch)) {
				NodeToRule nodeToRule = (NodeToRule) result5_black[0];
				DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result5_black[5];
				Object[] result5_green = RefinmentRulesImpl.pattern_RefinmentRules_24_5_matchcorrcontext_greenBBBBF(
						nodeToRule, directedGraphToTripleGraphGrammar, sourceMatch, targetMatch);
				CCMatch ccMatch = (CCMatch) result5_green[4];

				// create correspondence
				Object[] result6_black = RefinmentRulesImpl
						.pattern_RefinmentRules_24_6_createcorrespondence_blackBBBBBBBB(superRuleBox, ruleBox, refined,
								rule, superRule, tripleGraphGrammar, directedGraph, ccMatch);
				if (result6_black == null) {
					throw new RuntimeException("Pattern matching in node [create correspondence] failed."
							+ " Variables: " + "[superRuleBox] = " + superRuleBox + ", " + "[ruleBox] = " + ruleBox
							+ ", " + "[refined] = " + refined + ", " + "[rule] = " + rule + ", " + "[superRule] = "
							+ superRule + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ", "
							+ "[directedGraph] = " + directedGraph + ", " + "[ccMatch] = " + ccMatch + ".");
				}

				// add to returned result
				Object[] result7_black = RefinmentRulesImpl
						.pattern_RefinmentRules_24_7_addtoreturnedresult_blackBB(result, ccMatch);
				if (result7_black == null) {
					throw new RuntimeException("Pattern matching in node [add to returned result] failed."
							+ " Variables: " + "[result] = " + result + ", " + "[ccMatch] = " + ccMatch + ".");
				}
				RefinmentRulesImpl.pattern_RefinmentRules_24_7_addtoreturnedresult_greenBB(result, ccMatch);

			}

		} else {
		}
		return RefinmentRulesImpl.pattern_RefinmentRules_24_8_expressionFB(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isApplicable_solveCsp_CC(Box superRuleBox, Box ruleBox, Inheritance refined, TGGRule rule,
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Match sourceMatch,
			Match targetMatch) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();

		// Create literals

		// Create attribute variables
		Variable var_superRuleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("superRuleBox.label", true, csp);
		var_superRuleBox_label.setValue(superRuleBox.getLabel());
		var_superRuleBox_label.setType("String");
		Variable var_superRule_name = CSPFactoryHelper.eINSTANCE.createVariable("superRule.name", true, csp);
		var_superRule_name.setValue(superRule.getName());
		var_superRule_name.setType("String");
		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox.label", true, csp);
		var_ruleBox_label.setValue(ruleBox.getLabel());
		var_ruleBox_label.setType("String");
		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule.name", true, csp);
		var_rule_name.setValue(rule.getName());
		var_rule_name.setType("String");

		// Create unbound variables

		// Create constraints
		Eq eq = new Eq();
		Eq eq_0 = new Eq();

		csp.getConstraints().add(eq);
		csp.getConstraints().add(eq_0);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_superRuleBox_label, var_superRule_name);
		eq_0.setRuleName("");
		eq_0.solve(var_ruleBox_label, var_rule_name);
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
	public boolean checkDEC_FWD(Box superRuleBox, Box ruleBox, Inheritance refined, DirectedGraph directedGraph) {// match tgg pattern
		Object[] result1_black = RefinmentRulesImpl.pattern_RefinmentRules_27_1_matchtggpattern_blackBBBB(superRuleBox,
				ruleBox, refined, directedGraph);
		if (result1_black != null) {
			return RefinmentRulesImpl.pattern_RefinmentRules_27_2_expressionF();
		} else {
			return RefinmentRulesImpl.pattern_RefinmentRules_27_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDEC_BWD(TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar) {// match tgg pattern
		Object[] result1_black = RefinmentRulesImpl.pattern_RefinmentRules_28_1_matchtggpattern_blackBBB(rule,
				superRule, tripleGraphGrammar);
		if (result1_black != null) {
			return RefinmentRulesImpl.pattern_RefinmentRules_28_2_expressionF();
		} else {
			return RefinmentRulesImpl.pattern_RefinmentRules_28_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelgeneratorRuleResult generateModel(RuleEntryContainer ruleEntryContainer,
			NodeToRule nodeToRuleParameter) {
		// create result
		Object[] result1_black = RefinmentRulesImpl.pattern_RefinmentRules_29_1_createresult_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [create result] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = RefinmentRulesImpl.pattern_RefinmentRules_29_1_createresult_greenFF();
		IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result1_green[0];
		ModelgeneratorRuleResult ruleResult = (ModelgeneratorRuleResult) result1_green[1];

		// ForEach is applicable core
		for (Object[] result2_black : RefinmentRulesImpl
				.pattern_RefinmentRules_29_2_isapplicablecore_blackFFFFFFFFFBB(ruleEntryContainer, ruleResult)) {
			// RuleEntryList nodeToRuleList = (RuleEntryList) result2_black[0];
			Box superRuleBox = (Box) result2_black[1];
			DirectedGraph directedGraph = (DirectedGraph) result2_black[2];
			Box ruleBox = (Box) result2_black[3];
			NodeToRule nodeToRule = (NodeToRule) result2_black[4];
			TGGRule rule = (TGGRule) result2_black[5];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_black[6];
			TGGRule superRule = (TGGRule) result2_black[7];
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result2_black[8];

			// solve CSP
			Object[] result3_bindingAndBlack = RefinmentRulesImpl
					.pattern_RefinmentRules_29_3_solveCSP_bindingAndBlackFBBBBBBBBBBB(this, isApplicableMatch,
							superRuleBox, nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar, directedGraph,
							directedGraphToTripleGraphGrammar, ruleResult);
			if (result3_bindingAndBlack == null) {
				throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: " + "[this] = "
						+ this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", " + "[superRuleBox] = "
						+ superRuleBox + ", " + "[nodeToRule] = " + nodeToRule + ", " + "[ruleBox] = " + ruleBox + ", "
						+ "[rule] = " + rule + ", " + "[superRule] = " + superRule + ", " + "[tripleGraphGrammar] = "
						+ tripleGraphGrammar + ", " + "[directedGraph] = " + directedGraph + ", "
						+ "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ", "
						+ "[ruleResult] = " + ruleResult + ".");
			}
			CSP csp = (CSP) result3_bindingAndBlack[0];
			// check CSP
			if (RefinmentRulesImpl.pattern_RefinmentRules_29_4_checkCSP_expressionFBB(this, csp)) {
				// check nacs
				Object[] result5_black = RefinmentRulesImpl.pattern_RefinmentRules_29_5_checknacs_blackBBBBBBBB(
						superRuleBox, nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar, directedGraph,
						directedGraphToTripleGraphGrammar);
				if (result5_black != null) {

					// perform
					Object[] result6_black = RefinmentRulesImpl.pattern_RefinmentRules_29_6_perform_blackBBBBBBBBB(
							superRuleBox, nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar, directedGraph,
							directedGraphToTripleGraphGrammar, ruleResult);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [perform] failed." + " Variables: "
								+ "[superRuleBox] = " + superRuleBox + ", " + "[nodeToRule] = " + nodeToRule + ", "
								+ "[ruleBox] = " + ruleBox + ", " + "[rule] = " + rule + ", " + "[superRule] = "
								+ superRule + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ", "
								+ "[directedGraph] = " + directedGraph + ", " + "[directedGraphToTripleGraphGrammar] = "
								+ directedGraphToTripleGraphGrammar + ", " + "[ruleResult] = " + ruleResult + ".");
					}
					RefinmentRulesImpl.pattern_RefinmentRules_29_6_perform_greenBBFBBBB(superRuleBox, ruleBox, rule,
							superRule, directedGraph, ruleResult);
					// Inheritance refined = (Inheritance) result6_green[2];

				} else {
				}

			} else {
			}

		}
		return RefinmentRulesImpl.pattern_RefinmentRules_29_7_expressionFB(ruleResult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP generateModel_solveCsp_BWD(IsApplicableMatch isApplicableMatch, Box superRuleBox, NodeToRule nodeToRule,
			Box ruleBox, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar,
			ModelgeneratorRuleResult ruleResult) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_superRuleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("superRuleBox.label", true, csp);
		var_superRuleBox_label.setValue(superRuleBox.getLabel());
		var_superRuleBox_label.setType("String");
		Variable var_superRule_name = CSPFactoryHelper.eINSTANCE.createVariable("superRule.name", true, csp);
		var_superRule_name.setValue(superRule.getName());
		var_superRule_name.setType("String");
		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox.label", true, csp);
		var_ruleBox_label.setValue(ruleBox.getLabel());
		var_ruleBox_label.setType("String");
		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule.name", true, csp);
		var_rule_name.setValue(rule.getName());
		var_rule_name.setType("String");

		// Create unbound variables

		// Create constraints
		Eq eq = new Eq();
		Eq eq_0 = new Eq();

		csp.getConstraints().add(eq);
		csp.getConstraints().add(eq_0);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_superRuleBox_label, var_superRule_name);
		eq_0.setRuleName("");
		eq_0.solve(var_ruleBox_label, var_rule_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("superRuleBox", superRuleBox);
		isApplicableMatch.registerObject("nodeToRule", nodeToRule);
		isApplicableMatch.registerObject("ruleBox", ruleBox);
		isApplicableMatch.registerObject("rule", rule);
		isApplicableMatch.registerObject("superRule", superRule);
		isApplicableMatch.registerObject("tripleGraphGrammar", tripleGraphGrammar);
		isApplicableMatch.registerObject("directedGraph", directedGraph);
		isApplicableMatch.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);
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
		case RulesPackage.REFINMENT_RULES___IS_APPROPRIATE_FWD__MATCH_BOX_BOX_INHERITANCE_DIRECTEDGRAPH:
			return isAppropriate_FWD((Match) arguments.get(0), (Box) arguments.get(1), (Box) arguments.get(2),
					(Inheritance) arguments.get(3), (DirectedGraph) arguments.get(4));
		case RulesPackage.REFINMENT_RULES___PERFORM_FWD__ISAPPLICABLEMATCH:
			return perform_FWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___IS_APPLICABLE_FWD__MATCH:
			return isApplicable_FWD((Match) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_BOX_BOX_INHERITANCE_DIRECTEDGRAPH:
			registerObjectsToMatch_FWD((Match) arguments.get(0), (Box) arguments.get(1), (Box) arguments.get(2),
					(Inheritance) arguments.get(3), (DirectedGraph) arguments.get(4));
			return null;
		case RulesPackage.REFINMENT_RULES___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_BOX_BOX_INHERITANCE_DIRECTEDGRAPH:
			return isAppropriate_solveCsp_FWD((Match) arguments.get(0), (Box) arguments.get(1), (Box) arguments.get(2),
					(Inheritance) arguments.get(3), (DirectedGraph) arguments.get(4));
		case RulesPackage.REFINMENT_RULES___IS_APPROPRIATE_CHECK_CSP_FWD__CSP:
			return isAppropriate_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_BOX_NODETORULE_BOX_INHERITANCE_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR:
			return isApplicable_solveCsp_FWD((IsApplicableMatch) arguments.get(0), (Box) arguments.get(1),
					(NodeToRule) arguments.get(2), (Box) arguments.get(3), (Inheritance) arguments.get(4),
					(TGGRule) arguments.get(5), (TGGRule) arguments.get(6), (TripleGraphGrammar) arguments.get(7),
					(DirectedGraph) arguments.get(8), (DirectedGraphToTripleGraphGrammar) arguments.get(9));
		case RulesPackage.REFINMENT_RULES___IS_APPLICABLE_CHECK_CSP_FWD__CSP:
			return isApplicable_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_FWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6), (EObject) arguments.get(7),
					(EObject) arguments.get(8), (EObject) arguments.get(9));
			return null;
		case RulesPackage.REFINMENT_RULES___CHECK_TYPES_FWD__MATCH:
			return checkTypes_FWD((Match) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___IS_APPROPRIATE_BWD__MATCH_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR:
			return isAppropriate_BWD((Match) arguments.get(0), (TGGRule) arguments.get(1), (TGGRule) arguments.get(2),
					(TripleGraphGrammar) arguments.get(3));
		case RulesPackage.REFINMENT_RULES___PERFORM_BWD__ISAPPLICABLEMATCH:
			return perform_BWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___IS_APPLICABLE_BWD__MATCH:
			return isApplicable_BWD((Match) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR:
			registerObjectsToMatch_BWD((Match) arguments.get(0), (TGGRule) arguments.get(1), (TGGRule) arguments.get(2),
					(TripleGraphGrammar) arguments.get(3));
			return null;
		case RulesPackage.REFINMENT_RULES___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR:
			return isAppropriate_solveCsp_BWD((Match) arguments.get(0), (TGGRule) arguments.get(1),
					(TGGRule) arguments.get(2), (TripleGraphGrammar) arguments.get(3));
		case RulesPackage.REFINMENT_RULES___IS_APPROPRIATE_CHECK_CSP_BWD__CSP:
			return isAppropriate_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_BOX_NODETORULE_BOX_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR:
			return isApplicable_solveCsp_BWD((IsApplicableMatch) arguments.get(0), (Box) arguments.get(1),
					(NodeToRule) arguments.get(2), (Box) arguments.get(3), (TGGRule) arguments.get(4),
					(TGGRule) arguments.get(5), (TripleGraphGrammar) arguments.get(6), (DirectedGraph) arguments.get(7),
					(DirectedGraphToTripleGraphGrammar) arguments.get(8));
		case RulesPackage.REFINMENT_RULES___IS_APPLICABLE_CHECK_CSP_BWD__CSP:
			return isApplicable_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_BWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6), (EObject) arguments.get(7),
					(EObject) arguments.get(8), (EObject) arguments.get(9));
			return null;
		case RulesPackage.REFINMENT_RULES___CHECK_TYPES_BWD__MATCH:
			return checkTypes_BWD((Match) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___IS_APPROPRIATE_FWD_EMOFLON_EDGE_57__EMOFLONEDGE:
			return isAppropriate_FWD_EMoflonEdge_57((EMoflonEdge) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___IS_APPROPRIATE_BWD_EMOFLON_EDGE_82__EMOFLONEDGE:
			return isAppropriate_BWD_EMoflonEdge_82((EMoflonEdge) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH:
			return checkAttributes_FWD((TripleMatch) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH:
			return checkAttributes_BWD((TripleMatch) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___IS_APPLICABLE_CC__MATCH_MATCH:
			return isApplicable_CC((Match) arguments.get(0), (Match) arguments.get(1));
		case RulesPackage.REFINMENT_RULES___IS_APPLICABLE_SOLVE_CSP_CC__BOX_BOX_INHERITANCE_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_MATCH_MATCH:
			return isApplicable_solveCsp_CC((Box) arguments.get(0), (Box) arguments.get(1),
					(Inheritance) arguments.get(2), (TGGRule) arguments.get(3), (TGGRule) arguments.get(4),
					(TripleGraphGrammar) arguments.get(5), (DirectedGraph) arguments.get(6), (Match) arguments.get(7),
					(Match) arguments.get(8));
		case RulesPackage.REFINMENT_RULES___IS_APPLICABLE_CHECK_CSP_CC__CSP:
			return isApplicable_checkCsp_CC((CSP) arguments.get(0));
		case RulesPackage.REFINMENT_RULES___CHECK_DEC_FWD__BOX_BOX_INHERITANCE_DIRECTEDGRAPH:
			return checkDEC_FWD((Box) arguments.get(0), (Box) arguments.get(1), (Inheritance) arguments.get(2),
					(DirectedGraph) arguments.get(3));
		case RulesPackage.REFINMENT_RULES___CHECK_DEC_BWD__TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR:
			return checkDEC_BWD((TGGRule) arguments.get(0), (TGGRule) arguments.get(1),
					(TripleGraphGrammar) arguments.get(2));
		case RulesPackage.REFINMENT_RULES___GENERATE_MODEL__RULEENTRYCONTAINER_NODETORULE:
			return generateModel((RuleEntryContainer) arguments.get(0), (NodeToRule) arguments.get(1));
		case RulesPackage.REFINMENT_RULES___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_BOX_NODETORULE_BOX_TGGRULE_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_MODELGENERATORRULERESULT:
			return generateModel_solveCsp_BWD((IsApplicableMatch) arguments.get(0), (Box) arguments.get(1),
					(NodeToRule) arguments.get(2), (Box) arguments.get(3), (TGGRule) arguments.get(4),
					(TGGRule) arguments.get(5), (TripleGraphGrammar) arguments.get(6), (DirectedGraph) arguments.get(7),
					(DirectedGraphToTripleGraphGrammar) arguments.get(8), (ModelgeneratorRuleResult) arguments.get(9));
		case RulesPackage.REFINMENT_RULES___GENERATE_MODEL_CHECK_CSP_BWD__CSP:
			return generateModel_checkCsp_BWD((CSP) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	public static final Object[] pattern_RefinmentRules_0_1_initialbindings_blackBBBBBB(RefinmentRules _this,
			Match match, Box superRuleBox, Box ruleBox, Inheritance refined, DirectedGraph directedGraph) {
		if (!ruleBox.equals(superRuleBox)) {
			return new Object[] { _this, match, superRuleBox, ruleBox, refined, directedGraph };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_0_2_SolveCSP_bindingFBBBBBB(RefinmentRules _this, Match match,
			Box superRuleBox, Box ruleBox, Inheritance refined, DirectedGraph directedGraph) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_FWD(match, superRuleBox, ruleBox, refined, directedGraph);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, superRuleBox, ruleBox, refined, directedGraph };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_0_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_RefinmentRules_0_2_SolveCSP_bindingAndBlackFBBBBBB(RefinmentRules _this,
			Match match, Box superRuleBox, Box ruleBox, Inheritance refined, DirectedGraph directedGraph) {
		Object[] result_pattern_RefinmentRules_0_2_SolveCSP_binding = pattern_RefinmentRules_0_2_SolveCSP_bindingFBBBBBB(
				_this, match, superRuleBox, ruleBox, refined, directedGraph);
		if (result_pattern_RefinmentRules_0_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_RefinmentRules_0_2_SolveCSP_binding[0];

			Object[] result_pattern_RefinmentRules_0_2_SolveCSP_black = pattern_RefinmentRules_0_2_SolveCSP_blackB(csp);
			if (result_pattern_RefinmentRules_0_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, superRuleBox, ruleBox, refined, directedGraph };
			}
		}
		return null;
	}

	public static final boolean pattern_RefinmentRules_0_3_CheckCSP_expressionFBB(RefinmentRules _this, CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_0_4_collectelementstobetranslated_blackBBBBB(Match match,
			Box superRuleBox, Box ruleBox, Inheritance refined, DirectedGraph directedGraph) {
		if (!ruleBox.equals(superRuleBox)) {
			return new Object[] { match, superRuleBox, ruleBox, refined, directedGraph };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_0_4_collectelementstobetranslated_greenBBBBBFFFF(Match match,
			Box superRuleBox, Box ruleBox, Inheritance refined, DirectedGraph directedGraph) {
		EMoflonEdge refined__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__refined____edges = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge refined__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge refined__superRuleBox____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getToBeTranslatedNodes().add(refined);
		String refined__ruleBox____source_name_prime = "source";
		String directedGraph__refined____edges_name_prime = "edges";
		String refined__directedGraph____graph_name_prime = "graph";
		String refined__superRuleBox____target_name_prime = "target";
		refined__ruleBox____source.setSrc(refined);
		refined__ruleBox____source.setTrg(ruleBox);
		match.getToBeTranslatedEdges().add(refined__ruleBox____source);
		directedGraph__refined____edges.setSrc(directedGraph);
		directedGraph__refined____edges.setTrg(refined);
		match.getToBeTranslatedEdges().add(directedGraph__refined____edges);
		refined__directedGraph____graph.setSrc(refined);
		refined__directedGraph____graph.setTrg(directedGraph);
		match.getToBeTranslatedEdges().add(refined__directedGraph____graph);
		refined__superRuleBox____target.setSrc(refined);
		refined__superRuleBox____target.setTrg(superRuleBox);
		match.getToBeTranslatedEdges().add(refined__superRuleBox____target);
		refined__ruleBox____source.setName(refined__ruleBox____source_name_prime);
		directedGraph__refined____edges.setName(directedGraph__refined____edges_name_prime);
		refined__directedGraph____graph.setName(refined__directedGraph____graph_name_prime);
		refined__superRuleBox____target.setName(refined__superRuleBox____target_name_prime);
		return new Object[] { match, superRuleBox, ruleBox, refined, directedGraph, refined__ruleBox____source,
				directedGraph__refined____edges, refined__directedGraph____graph, refined__superRuleBox____target };
	}

	public static final Object[] pattern_RefinmentRules_0_5_collectcontextelements_blackBBBBB(Match match,
			Box superRuleBox, Box ruleBox, Inheritance refined, DirectedGraph directedGraph) {
		if (!ruleBox.equals(superRuleBox)) {
			return new Object[] { match, superRuleBox, ruleBox, refined, directedGraph };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_0_5_collectcontextelements_greenBBBBFFFF(Match match,
			Box superRuleBox, Box ruleBox, DirectedGraph directedGraph) {
		EMoflonEdge directedGraph__ruleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ruleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__superRuleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge superRuleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getContextNodes().add(superRuleBox);
		match.getContextNodes().add(ruleBox);
		match.getContextNodes().add(directedGraph);
		String directedGraph__ruleBox____nodes_name_prime = "nodes";
		String ruleBox__directedGraph____graph_name_prime = "graph";
		String directedGraph__superRuleBox____nodes_name_prime = "nodes";
		String superRuleBox__directedGraph____graph_name_prime = "graph";
		directedGraph__ruleBox____nodes.setSrc(directedGraph);
		directedGraph__ruleBox____nodes.setTrg(ruleBox);
		match.getContextEdges().add(directedGraph__ruleBox____nodes);
		ruleBox__directedGraph____graph.setSrc(ruleBox);
		ruleBox__directedGraph____graph.setTrg(directedGraph);
		match.getContextEdges().add(ruleBox__directedGraph____graph);
		directedGraph__superRuleBox____nodes.setSrc(directedGraph);
		directedGraph__superRuleBox____nodes.setTrg(superRuleBox);
		match.getContextEdges().add(directedGraph__superRuleBox____nodes);
		superRuleBox__directedGraph____graph.setSrc(superRuleBox);
		superRuleBox__directedGraph____graph.setTrg(directedGraph);
		match.getContextEdges().add(superRuleBox__directedGraph____graph);
		directedGraph__ruleBox____nodes.setName(directedGraph__ruleBox____nodes_name_prime);
		ruleBox__directedGraph____graph.setName(ruleBox__directedGraph____graph_name_prime);
		directedGraph__superRuleBox____nodes.setName(directedGraph__superRuleBox____nodes_name_prime);
		superRuleBox__directedGraph____graph.setName(superRuleBox__directedGraph____graph_name_prime);
		return new Object[] { match, superRuleBox, ruleBox, directedGraph, directedGraph__ruleBox____nodes,
				ruleBox__directedGraph____graph, directedGraph__superRuleBox____nodes,
				superRuleBox__directedGraph____graph };
	}

	public static final void pattern_RefinmentRules_0_6_registerobjectstomatch_expressionBBBBBB(RefinmentRules _this,
			Match match, Box superRuleBox, Box ruleBox, Inheritance refined, DirectedGraph directedGraph) {
		_this.registerObjectsToMatch_FWD(match, superRuleBox, ruleBox, refined, directedGraph);

	}

	public static final boolean pattern_RefinmentRules_0_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_RefinmentRules_0_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_1_1_performtransformation_bindingFFFFFFFFFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("superRuleBox");
		EObject _localVariable_1 = isApplicableMatch.getObject("nodeToRule");
		EObject _localVariable_2 = isApplicableMatch.getObject("ruleBox");
		EObject _localVariable_3 = isApplicableMatch.getObject("refined");
		EObject _localVariable_4 = isApplicableMatch.getObject("rule");
		EObject _localVariable_5 = isApplicableMatch.getObject("superRule");
		EObject _localVariable_6 = isApplicableMatch.getObject("tripleGraphGrammar");
		EObject _localVariable_7 = isApplicableMatch.getObject("directedGraph");
		EObject _localVariable_8 = isApplicableMatch.getObject("directedGraphToTripleGraphGrammar");
		EObject tmpSuperRuleBox = _localVariable_0;
		EObject tmpNodeToRule = _localVariable_1;
		EObject tmpRuleBox = _localVariable_2;
		EObject tmpRefined = _localVariable_3;
		EObject tmpRule = _localVariable_4;
		EObject tmpSuperRule = _localVariable_5;
		EObject tmpTripleGraphGrammar = _localVariable_6;
		EObject tmpDirectedGraph = _localVariable_7;
		EObject tmpDirectedGraphToTripleGraphGrammar = _localVariable_8;
		if (tmpSuperRuleBox instanceof Box) {
			Box superRuleBox = (Box) tmpSuperRuleBox;
			if (tmpNodeToRule instanceof NodeToRule) {
				NodeToRule nodeToRule = (NodeToRule) tmpNodeToRule;
				if (tmpRuleBox instanceof Box) {
					Box ruleBox = (Box) tmpRuleBox;
					if (tmpRefined instanceof Inheritance) {
						Inheritance refined = (Inheritance) tmpRefined;
						if (tmpRule instanceof TGGRule) {
							TGGRule rule = (TGGRule) tmpRule;
							if (tmpSuperRule instanceof TGGRule) {
								TGGRule superRule = (TGGRule) tmpSuperRule;
								if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
									TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
									if (tmpDirectedGraph instanceof DirectedGraph) {
										DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
										if (tmpDirectedGraphToTripleGraphGrammar instanceof DirectedGraphToTripleGraphGrammar) {
											DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) tmpDirectedGraphToTripleGraphGrammar;
											return new Object[] { superRuleBox, nodeToRule, ruleBox, refined, rule,
													superRule, tripleGraphGrammar, directedGraph,
													directedGraphToTripleGraphGrammar, isApplicableMatch };
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

	public static final Object[] pattern_RefinmentRules_1_1_performtransformation_blackBBBBBBBBBFBB(Box superRuleBox,
			NodeToRule nodeToRule, Box ruleBox, Inheritance refined, TGGRule rule, TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, RefinmentRules _this,
			IsApplicableMatch isApplicableMatch) {
		if (!ruleBox.equals(superRuleBox)) {
			if (!rule.equals(superRule)) {
				for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
					if (tmpCsp instanceof CSP) {
						CSP csp = (CSP) tmpCsp;
						return new Object[] { superRuleBox, nodeToRule, ruleBox, refined, rule, superRule,
								tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar, csp, _this,
								isApplicableMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_1_1_performtransformation_bindingAndBlackFFFFFFFFFFBB(
			RefinmentRules _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_RefinmentRules_1_1_performtransformation_binding = pattern_RefinmentRules_1_1_performtransformation_bindingFFFFFFFFFB(
				isApplicableMatch);
		if (result_pattern_RefinmentRules_1_1_performtransformation_binding != null) {
			Box superRuleBox = (Box) result_pattern_RefinmentRules_1_1_performtransformation_binding[0];
			NodeToRule nodeToRule = (NodeToRule) result_pattern_RefinmentRules_1_1_performtransformation_binding[1];
			Box ruleBox = (Box) result_pattern_RefinmentRules_1_1_performtransformation_binding[2];
			Inheritance refined = (Inheritance) result_pattern_RefinmentRules_1_1_performtransformation_binding[3];
			TGGRule rule = (TGGRule) result_pattern_RefinmentRules_1_1_performtransformation_binding[4];
			TGGRule superRule = (TGGRule) result_pattern_RefinmentRules_1_1_performtransformation_binding[5];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result_pattern_RefinmentRules_1_1_performtransformation_binding[6];
			DirectedGraph directedGraph = (DirectedGraph) result_pattern_RefinmentRules_1_1_performtransformation_binding[7];
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result_pattern_RefinmentRules_1_1_performtransformation_binding[8];

			Object[] result_pattern_RefinmentRules_1_1_performtransformation_black = pattern_RefinmentRules_1_1_performtransformation_blackBBBBBBBBBFBB(
					superRuleBox, nodeToRule, ruleBox, refined, rule, superRule, tripleGraphGrammar, directedGraph,
					directedGraphToTripleGraphGrammar, _this, isApplicableMatch);
			if (result_pattern_RefinmentRules_1_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_RefinmentRules_1_1_performtransformation_black[9];

				return new Object[] { superRuleBox, nodeToRule, ruleBox, refined, rule, superRule, tripleGraphGrammar,
						directedGraph, directedGraphToTripleGraphGrammar, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_1_1_performtransformation_greenBB(TGGRule rule,
			TGGRule superRule) {
		rule.getRefines().add(superRule);
		return new Object[] { rule, superRule };
	}

	public static final Object[] pattern_RefinmentRules_1_2_collecttranslatedelements_blackB(Inheritance refined) {
		return new Object[] { refined };
	}

	public static final Object[] pattern_RefinmentRules_1_2_collecttranslatedelements_greenFB(Inheritance refined) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getTranslatedElements().add(refined);
		return new Object[] { ruleresult, refined };
	}

	public static final Object[] pattern_RefinmentRules_1_3_bookkeepingforedges_blackBBBBBBBBBB(
			PerformRuleResult ruleresult, EObject superRuleBox, EObject nodeToRule, EObject ruleBox, EObject refined,
			EObject rule, EObject superRule, EObject tripleGraphGrammar, EObject directedGraph,
			EObject directedGraphToTripleGraphGrammar) {
		if (!superRuleBox.equals(tripleGraphGrammar)) {
			if (!nodeToRule.equals(superRuleBox)) {
				if (!nodeToRule.equals(ruleBox)) {
					if (!nodeToRule.equals(refined)) {
						if (!nodeToRule.equals(rule)) {
							if (!nodeToRule.equals(superRule)) {
								if (!nodeToRule.equals(tripleGraphGrammar)) {
									if (!ruleBox.equals(superRuleBox)) {
										if (!ruleBox.equals(superRule)) {
											if (!ruleBox.equals(tripleGraphGrammar)) {
												if (!refined.equals(superRuleBox)) {
													if (!refined.equals(ruleBox)) {
														if (!refined.equals(rule)) {
															if (!refined.equals(superRule)) {
																if (!refined.equals(tripleGraphGrammar)) {
																	if (!rule.equals(superRuleBox)) {
																		if (!rule.equals(ruleBox)) {
																			if (!rule.equals(superRule)) {
																				if (!rule.equals(tripleGraphGrammar)) {
																					if (!superRule
																							.equals(superRuleBox)) {
																						if (!superRule.equals(
																								tripleGraphGrammar)) {
																							if (!directedGraph.equals(
																									superRuleBox)) {
																								if (!directedGraph
																										.equals(nodeToRule)) {
																									if (!directedGraph
																											.equals(ruleBox)) {
																										if (!directedGraph
																												.equals(refined)) {
																											if (!directedGraph
																													.equals(rule)) {
																												if (!directedGraph
																														.equals(superRule)) {
																													if (!directedGraph
																															.equals(tripleGraphGrammar)) {
																														if (!directedGraph
																																.equals(directedGraphToTripleGraphGrammar)) {
																															if (!directedGraphToTripleGraphGrammar
																																	.equals(superRuleBox)) {
																																if (!directedGraphToTripleGraphGrammar
																																		.equals(nodeToRule)) {
																																	if (!directedGraphToTripleGraphGrammar
																																			.equals(ruleBox)) {
																																		if (!directedGraphToTripleGraphGrammar
																																				.equals(refined)) {
																																			if (!directedGraphToTripleGraphGrammar
																																					.equals(rule)) {
																																				if (!directedGraphToTripleGraphGrammar
																																						.equals(superRule)) {
																																					if (!directedGraphToTripleGraphGrammar
																																							.equals(tripleGraphGrammar)) {
																																						return new Object[] {
																																								ruleresult,
																																								superRuleBox,
																																								nodeToRule,
																																								ruleBox,
																																								refined,
																																								rule,
																																								superRule,
																																								tripleGraphGrammar,
																																								directedGraph,
																																								directedGraphToTripleGraphGrammar };
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
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_1_3_bookkeepingforedges_greenBBBBBBBFFFFF(
			PerformRuleResult ruleresult, EObject superRuleBox, EObject ruleBox, EObject refined, EObject rule,
			EObject superRule, EObject directedGraph) {
		EMoflonEdge refined__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__superRule____refines = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__refined____edges = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge refined__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge refined__superRuleBox____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "RefinmentRules";
		String refined__ruleBox____source_name_prime = "source";
		String rule__superRule____refines_name_prime = "refines";
		String directedGraph__refined____edges_name_prime = "edges";
		String refined__directedGraph____graph_name_prime = "graph";
		String refined__superRuleBox____target_name_prime = "target";
		refined__ruleBox____source.setSrc(refined);
		refined__ruleBox____source.setTrg(ruleBox);
		ruleresult.getTranslatedEdges().add(refined__ruleBox____source);
		rule__superRule____refines.setSrc(rule);
		rule__superRule____refines.setTrg(superRule);
		ruleresult.getCreatedEdges().add(rule__superRule____refines);
		directedGraph__refined____edges.setSrc(directedGraph);
		directedGraph__refined____edges.setTrg(refined);
		ruleresult.getTranslatedEdges().add(directedGraph__refined____edges);
		refined__directedGraph____graph.setSrc(refined);
		refined__directedGraph____graph.setTrg(directedGraph);
		ruleresult.getTranslatedEdges().add(refined__directedGraph____graph);
		refined__superRuleBox____target.setSrc(refined);
		refined__superRuleBox____target.setTrg(superRuleBox);
		ruleresult.getTranslatedEdges().add(refined__superRuleBox____target);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		refined__ruleBox____source.setName(refined__ruleBox____source_name_prime);
		rule__superRule____refines.setName(rule__superRule____refines_name_prime);
		directedGraph__refined____edges.setName(directedGraph__refined____edges_name_prime);
		refined__directedGraph____graph.setName(refined__directedGraph____graph_name_prime);
		refined__superRuleBox____target.setName(refined__superRuleBox____target_name_prime);
		return new Object[] { ruleresult, superRuleBox, ruleBox, refined, rule, superRule, directedGraph,
				refined__ruleBox____source, rule__superRule____refines, directedGraph__refined____edges,
				refined__directedGraph____graph, refined__superRuleBox____target };
	}

	public static final void pattern_RefinmentRules_1_5_registerobjects_expressionBBBBBBBBBBB(RefinmentRules _this,
			PerformRuleResult ruleresult, EObject superRuleBox, EObject nodeToRule, EObject ruleBox, EObject refined,
			EObject rule, EObject superRule, EObject tripleGraphGrammar, EObject directedGraph,
			EObject directedGraphToTripleGraphGrammar) {
		_this.registerObjects_FWD(ruleresult, superRuleBox, nodeToRule, ruleBox, refined, rule, superRule,
				tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar);

	}

	public static final PerformRuleResult pattern_RefinmentRules_1_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_2_1_preparereturnvalue_bindingFB(RefinmentRules _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_2_1_preparereturnvalue_blackFBB(EClass eClass,
			RefinmentRules _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_FWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_2_1_preparereturnvalue_bindingAndBlackFFB(
			RefinmentRules _this) {
		Object[] result_pattern_RefinmentRules_2_1_preparereturnvalue_binding = pattern_RefinmentRules_2_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_RefinmentRules_2_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_RefinmentRules_2_1_preparereturnvalue_binding[0];

			Object[] result_pattern_RefinmentRules_2_1_preparereturnvalue_black = pattern_RefinmentRules_2_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_RefinmentRules_2_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_RefinmentRules_2_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_2_1_preparereturnvalue_greenBF(EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "RefinmentRules";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_RefinmentRules_2_2_corematch_bindingFFFFB(Match match) {
		EObject _localVariable_0 = match.getObject("superRuleBox");
		EObject _localVariable_1 = match.getObject("ruleBox");
		EObject _localVariable_2 = match.getObject("refined");
		EObject _localVariable_3 = match.getObject("directedGraph");
		EObject tmpSuperRuleBox = _localVariable_0;
		EObject tmpRuleBox = _localVariable_1;
		EObject tmpRefined = _localVariable_2;
		EObject tmpDirectedGraph = _localVariable_3;
		if (tmpSuperRuleBox instanceof Box) {
			Box superRuleBox = (Box) tmpSuperRuleBox;
			if (tmpRuleBox instanceof Box) {
				Box ruleBox = (Box) tmpRuleBox;
				if (tmpRefined instanceof Inheritance) {
					Inheritance refined = (Inheritance) tmpRefined;
					if (tmpDirectedGraph instanceof DirectedGraph) {
						DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
						return new Object[] { superRuleBox, ruleBox, refined, directedGraph, match };
					}
				}
			}
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_RefinmentRules_2_2_corematch_blackBFBBFFBFB(Box superRuleBox,
			Box ruleBox, Inheritance refined, DirectedGraph directedGraph, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!ruleBox.equals(superRuleBox)) {
			for (DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar : org.moflon.core.utilities.eMoflonEMFUtil
					.getOppositeReferenceTyped(directedGraph, DirectedGraphToTripleGraphGrammar.class, "source")) {
				TripleGraphGrammar tripleGraphGrammar = directedGraphToTripleGraphGrammar.getTarget();
				if (tripleGraphGrammar != null) {
					for (NodeToRule nodeToRule : org.moflon.core.utilities.eMoflonEMFUtil
							.getOppositeReferenceTyped(ruleBox, NodeToRule.class, "source")) {
						TGGRule rule = nodeToRule.getTarget();
						if (rule != null) {
							_result.add(new Object[] { superRuleBox, nodeToRule, ruleBox, refined, rule,
									tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar, match });
						}

					}
				}

			}
		}
		return _result;
	}

	public static final Iterable<Object[]> pattern_RefinmentRules_2_3_findcontext_blackBBBBBFBBB(Box superRuleBox,
			NodeToRule nodeToRule, Box ruleBox, Inheritance refined, TGGRule rule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!ruleBox.equals(superRuleBox)) {
			if (tripleGraphGrammar.getTggRule().contains(rule)) {
				if (ruleBox.equals(refined.getSource())) {
					if (rule.equals(nodeToRule.getTarget())) {
						if (directedGraph.getNodes().contains(ruleBox)) {
							if (directedGraph.getEdges().contains(refined)) {
								if (superRuleBox.equals(refined.getTarget())) {
									if (directedGraph.equals(directedGraphToTripleGraphGrammar.getSource())) {
										if (directedGraph.getNodes().contains(superRuleBox)) {
											if (tripleGraphGrammar
													.equals(directedGraphToTripleGraphGrammar.getTarget())) {
												if (ruleBox.equals(nodeToRule.getSource())) {
													for (TGGRule superRule : tripleGraphGrammar.getTggRule()) {
														if (!rule.equals(superRule)) {
															_result.add(new Object[] { superRuleBox, nodeToRule,
																	ruleBox, refined, rule, superRule,
																	tripleGraphGrammar, directedGraph,
																	directedGraphToTripleGraphGrammar });
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
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_2_3_findcontext_greenBBBBBBBBBFFFFFFFFFFFFFFFFF(
			Box superRuleBox, NodeToRule nodeToRule, Box ruleBox, Inheritance refined, TGGRule rule, TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		EMoflonEdge tripleGraphGrammar__rule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge refined__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge nodeToRule__rule____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__ruleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ruleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__refined____edges = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge refined__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge refined__superRuleBox____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge tripleGraphGrammar__superRule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge superRule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		EMoflonEdge directedGraph__superRuleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge superRuleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		EMoflonEdge nodeToRule__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String tripleGraphGrammar__rule____tggRule_name_prime = "tggRule";
		String rule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		String refined__ruleBox____source_name_prime = "source";
		String nodeToRule__rule____target_name_prime = "target";
		String directedGraph__ruleBox____nodes_name_prime = "nodes";
		String ruleBox__directedGraph____graph_name_prime = "graph";
		String directedGraph__refined____edges_name_prime = "edges";
		String refined__directedGraph____graph_name_prime = "graph";
		String refined__superRuleBox____target_name_prime = "target";
		String tripleGraphGrammar__superRule____tggRule_name_prime = "tggRule";
		String superRule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		String directedGraphToTripleGraphGrammar__directedGraph____source_name_prime = "source";
		String directedGraph__superRuleBox____nodes_name_prime = "nodes";
		String superRuleBox__directedGraph____graph_name_prime = "graph";
		String directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime = "target";
		String nodeToRule__ruleBox____source_name_prime = "source";
		isApplicableMatch.getAllContextElements().add(superRuleBox);
		isApplicableMatch.getAllContextElements().add(nodeToRule);
		isApplicableMatch.getAllContextElements().add(ruleBox);
		isApplicableMatch.getAllContextElements().add(refined);
		isApplicableMatch.getAllContextElements().add(rule);
		isApplicableMatch.getAllContextElements().add(superRule);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(directedGraph);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setTrg(rule);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar__rule____tggRule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setSrc(rule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(rule__tripleGraphGrammar____tripleGraphGrammar);
		refined__ruleBox____source.setSrc(refined);
		refined__ruleBox____source.setTrg(ruleBox);
		isApplicableMatch.getAllContextElements().add(refined__ruleBox____source);
		nodeToRule__rule____target.setSrc(nodeToRule);
		nodeToRule__rule____target.setTrg(rule);
		isApplicableMatch.getAllContextElements().add(nodeToRule__rule____target);
		directedGraph__ruleBox____nodes.setSrc(directedGraph);
		directedGraph__ruleBox____nodes.setTrg(ruleBox);
		isApplicableMatch.getAllContextElements().add(directedGraph__ruleBox____nodes);
		ruleBox__directedGraph____graph.setSrc(ruleBox);
		ruleBox__directedGraph____graph.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(ruleBox__directedGraph____graph);
		directedGraph__refined____edges.setSrc(directedGraph);
		directedGraph__refined____edges.setTrg(refined);
		isApplicableMatch.getAllContextElements().add(directedGraph__refined____edges);
		refined__directedGraph____graph.setSrc(refined);
		refined__directedGraph____graph.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(refined__directedGraph____graph);
		refined__superRuleBox____target.setSrc(refined);
		refined__superRuleBox____target.setTrg(superRuleBox);
		isApplicableMatch.getAllContextElements().add(refined__superRuleBox____target);
		tripleGraphGrammar__superRule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__superRule____tggRule.setTrg(superRule);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar__superRule____tggRule);
		superRule__tripleGraphGrammar____tripleGraphGrammar.setSrc(superRule);
		superRule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(superRule__tripleGraphGrammar____tripleGraphGrammar);
		directedGraphToTripleGraphGrammar__directedGraph____source.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__directedGraph____source.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar__directedGraph____source);
		directedGraph__superRuleBox____nodes.setSrc(directedGraph);
		directedGraph__superRuleBox____nodes.setTrg(superRuleBox);
		isApplicableMatch.getAllContextElements().add(directedGraph__superRuleBox____nodes);
		superRuleBox__directedGraph____graph.setSrc(superRuleBox);
		superRuleBox__directedGraph____graph.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(superRuleBox__directedGraph____graph);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target);
		nodeToRule__ruleBox____source.setSrc(nodeToRule);
		nodeToRule__ruleBox____source.setTrg(ruleBox);
		isApplicableMatch.getAllContextElements().add(nodeToRule__ruleBox____source);
		tripleGraphGrammar__rule____tggRule.setName(tripleGraphGrammar__rule____tggRule_name_prime);
		rule__tripleGraphGrammar____tripleGraphGrammar
				.setName(rule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		refined__ruleBox____source.setName(refined__ruleBox____source_name_prime);
		nodeToRule__rule____target.setName(nodeToRule__rule____target_name_prime);
		directedGraph__ruleBox____nodes.setName(directedGraph__ruleBox____nodes_name_prime);
		ruleBox__directedGraph____graph.setName(ruleBox__directedGraph____graph_name_prime);
		directedGraph__refined____edges.setName(directedGraph__refined____edges_name_prime);
		refined__directedGraph____graph.setName(refined__directedGraph____graph_name_prime);
		refined__superRuleBox____target.setName(refined__superRuleBox____target_name_prime);
		tripleGraphGrammar__superRule____tggRule.setName(tripleGraphGrammar__superRule____tggRule_name_prime);
		superRule__tripleGraphGrammar____tripleGraphGrammar
				.setName(superRule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		directedGraphToTripleGraphGrammar__directedGraph____source
				.setName(directedGraphToTripleGraphGrammar__directedGraph____source_name_prime);
		directedGraph__superRuleBox____nodes.setName(directedGraph__superRuleBox____nodes_name_prime);
		superRuleBox__directedGraph____graph.setName(superRuleBox__directedGraph____graph_name_prime);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target
				.setName(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime);
		nodeToRule__ruleBox____source.setName(nodeToRule__ruleBox____source_name_prime);
		return new Object[] { superRuleBox, nodeToRule, ruleBox, refined, rule, superRule, tripleGraphGrammar,
				directedGraph, directedGraphToTripleGraphGrammar, isApplicableMatch,
				tripleGraphGrammar__rule____tggRule, rule__tripleGraphGrammar____tripleGraphGrammar,
				refined__ruleBox____source, nodeToRule__rule____target, directedGraph__ruleBox____nodes,
				ruleBox__directedGraph____graph, directedGraph__refined____edges, refined__directedGraph____graph,
				refined__superRuleBox____target, tripleGraphGrammar__superRule____tggRule,
				superRule__tripleGraphGrammar____tripleGraphGrammar,
				directedGraphToTripleGraphGrammar__directedGraph____source, directedGraph__superRuleBox____nodes,
				superRuleBox__directedGraph____graph, directedGraphToTripleGraphGrammar__tripleGraphGrammar____target,
				nodeToRule__ruleBox____source };
	}

	public static final Object[] pattern_RefinmentRules_2_4_solveCSP_bindingFBBBBBBBBBBB(RefinmentRules _this,
			IsApplicableMatch isApplicableMatch, Box superRuleBox, NodeToRule nodeToRule, Box ruleBox,
			Inheritance refined, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_FWD(isApplicableMatch, superRuleBox, nodeToRule, ruleBox,
				refined, rule, superRule, tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, superRuleBox, nodeToRule, ruleBox, refined, rule,
					superRule, tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_2_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_RefinmentRules_2_4_solveCSP_bindingAndBlackFBBBBBBBBBBB(RefinmentRules _this,
			IsApplicableMatch isApplicableMatch, Box superRuleBox, NodeToRule nodeToRule, Box ruleBox,
			Inheritance refined, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		Object[] result_pattern_RefinmentRules_2_4_solveCSP_binding = pattern_RefinmentRules_2_4_solveCSP_bindingFBBBBBBBBBBB(
				_this, isApplicableMatch, superRuleBox, nodeToRule, ruleBox, refined, rule, superRule,
				tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar);
		if (result_pattern_RefinmentRules_2_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_RefinmentRules_2_4_solveCSP_binding[0];

			Object[] result_pattern_RefinmentRules_2_4_solveCSP_black = pattern_RefinmentRules_2_4_solveCSP_blackB(csp);
			if (result_pattern_RefinmentRules_2_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, superRuleBox, nodeToRule, ruleBox, refined, rule,
						superRule, tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar };
			}
		}
		return null;
	}

	public static final boolean pattern_RefinmentRules_2_5_checkCSP_expressionFBB(RefinmentRules _this, CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_2_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_RefinmentRules_2_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "RefinmentRules";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_RefinmentRules_2_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_10_1_initialbindings_blackBBBBB(RefinmentRules _this,
			Match match, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar) {
		if (!rule.equals(superRule)) {
			return new Object[] { _this, match, rule, superRule, tripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_10_2_SolveCSP_bindingFBBBBB(RefinmentRules _this, Match match,
			TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_BWD(match, rule, superRule, tripleGraphGrammar);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, rule, superRule, tripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_10_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_RefinmentRules_10_2_SolveCSP_bindingAndBlackFBBBBB(RefinmentRules _this,
			Match match, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar) {
		Object[] result_pattern_RefinmentRules_10_2_SolveCSP_binding = pattern_RefinmentRules_10_2_SolveCSP_bindingFBBBBB(
				_this, match, rule, superRule, tripleGraphGrammar);
		if (result_pattern_RefinmentRules_10_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_RefinmentRules_10_2_SolveCSP_binding[0];

			Object[] result_pattern_RefinmentRules_10_2_SolveCSP_black = pattern_RefinmentRules_10_2_SolveCSP_blackB(
					csp);
			if (result_pattern_RefinmentRules_10_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, rule, superRule, tripleGraphGrammar };
			}
		}
		return null;
	}

	public static final boolean pattern_RefinmentRules_10_3_CheckCSP_expressionFBB(RefinmentRules _this, CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_10_4_collectelementstobetranslated_blackBBBB(Match match,
			TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar) {
		if (!rule.equals(superRule)) {
			return new Object[] { match, rule, superRule, tripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_10_4_collectelementstobetranslated_greenBBBF(Match match,
			TGGRule rule, TGGRule superRule) {
		EMoflonEdge rule__superRule____refines = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String rule__superRule____refines_name_prime = "refines";
		rule__superRule____refines.setSrc(rule);
		rule__superRule____refines.setTrg(superRule);
		match.getToBeTranslatedEdges().add(rule__superRule____refines);
		rule__superRule____refines.setName(rule__superRule____refines_name_prime);
		return new Object[] { match, rule, superRule, rule__superRule____refines };
	}

	public static final Object[] pattern_RefinmentRules_10_5_collectcontextelements_blackBBBB(Match match, TGGRule rule,
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar) {
		if (!rule.equals(superRule)) {
			return new Object[] { match, rule, superRule, tripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_10_5_collectcontextelements_greenBBBBFFFF(Match match,
			TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar) {
		EMoflonEdge tripleGraphGrammar__rule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge tripleGraphGrammar__superRule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge superRule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getContextNodes().add(rule);
		match.getContextNodes().add(superRule);
		match.getContextNodes().add(tripleGraphGrammar);
		String tripleGraphGrammar__rule____tggRule_name_prime = "tggRule";
		String rule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		String tripleGraphGrammar__superRule____tggRule_name_prime = "tggRule";
		String superRule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		tripleGraphGrammar__rule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setTrg(rule);
		match.getContextEdges().add(tripleGraphGrammar__rule____tggRule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setSrc(rule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		match.getContextEdges().add(rule__tripleGraphGrammar____tripleGraphGrammar);
		tripleGraphGrammar__superRule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__superRule____tggRule.setTrg(superRule);
		match.getContextEdges().add(tripleGraphGrammar__superRule____tggRule);
		superRule__tripleGraphGrammar____tripleGraphGrammar.setSrc(superRule);
		superRule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		match.getContextEdges().add(superRule__tripleGraphGrammar____tripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setName(tripleGraphGrammar__rule____tggRule_name_prime);
		rule__tripleGraphGrammar____tripleGraphGrammar
				.setName(rule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		tripleGraphGrammar__superRule____tggRule.setName(tripleGraphGrammar__superRule____tggRule_name_prime);
		superRule__tripleGraphGrammar____tripleGraphGrammar
				.setName(superRule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		return new Object[] { match, rule, superRule, tripleGraphGrammar, tripleGraphGrammar__rule____tggRule,
				rule__tripleGraphGrammar____tripleGraphGrammar, tripleGraphGrammar__superRule____tggRule,
				superRule__tripleGraphGrammar____tripleGraphGrammar };
	}

	public static final void pattern_RefinmentRules_10_6_registerobjectstomatch_expressionBBBBB(RefinmentRules _this,
			Match match, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar) {
		_this.registerObjectsToMatch_BWD(match, rule, superRule, tripleGraphGrammar);

	}

	public static final boolean pattern_RefinmentRules_10_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_RefinmentRules_10_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_11_1_performtransformation_bindingFFFFFFFFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("superRuleBox");
		EObject _localVariable_1 = isApplicableMatch.getObject("nodeToRule");
		EObject _localVariable_2 = isApplicableMatch.getObject("ruleBox");
		EObject _localVariable_3 = isApplicableMatch.getObject("rule");
		EObject _localVariable_4 = isApplicableMatch.getObject("superRule");
		EObject _localVariable_5 = isApplicableMatch.getObject("tripleGraphGrammar");
		EObject _localVariable_6 = isApplicableMatch.getObject("directedGraph");
		EObject _localVariable_7 = isApplicableMatch.getObject("directedGraphToTripleGraphGrammar");
		EObject tmpSuperRuleBox = _localVariable_0;
		EObject tmpNodeToRule = _localVariable_1;
		EObject tmpRuleBox = _localVariable_2;
		EObject tmpRule = _localVariable_3;
		EObject tmpSuperRule = _localVariable_4;
		EObject tmpTripleGraphGrammar = _localVariable_5;
		EObject tmpDirectedGraph = _localVariable_6;
		EObject tmpDirectedGraphToTripleGraphGrammar = _localVariable_7;
		if (tmpSuperRuleBox instanceof Box) {
			Box superRuleBox = (Box) tmpSuperRuleBox;
			if (tmpNodeToRule instanceof NodeToRule) {
				NodeToRule nodeToRule = (NodeToRule) tmpNodeToRule;
				if (tmpRuleBox instanceof Box) {
					Box ruleBox = (Box) tmpRuleBox;
					if (tmpRule instanceof TGGRule) {
						TGGRule rule = (TGGRule) tmpRule;
						if (tmpSuperRule instanceof TGGRule) {
							TGGRule superRule = (TGGRule) tmpSuperRule;
							if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
								TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
								if (tmpDirectedGraph instanceof DirectedGraph) {
									DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
									if (tmpDirectedGraphToTripleGraphGrammar instanceof DirectedGraphToTripleGraphGrammar) {
										DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) tmpDirectedGraphToTripleGraphGrammar;
										return new Object[] { superRuleBox, nodeToRule, ruleBox, rule, superRule,
												tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar,
												isApplicableMatch };
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

	public static final Object[] pattern_RefinmentRules_11_1_performtransformation_blackBBBBBBBBFBB(Box superRuleBox,
			NodeToRule nodeToRule, Box ruleBox, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar,
			RefinmentRules _this, IsApplicableMatch isApplicableMatch) {
		if (!ruleBox.equals(superRuleBox)) {
			if (!rule.equals(superRule)) {
				for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
					if (tmpCsp instanceof CSP) {
						CSP csp = (CSP) tmpCsp;
						return new Object[] { superRuleBox, nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar,
								directedGraph, directedGraphToTripleGraphGrammar, csp, _this, isApplicableMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_11_1_performtransformation_bindingAndBlackFFFFFFFFFBB(
			RefinmentRules _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_RefinmentRules_11_1_performtransformation_binding = pattern_RefinmentRules_11_1_performtransformation_bindingFFFFFFFFB(
				isApplicableMatch);
		if (result_pattern_RefinmentRules_11_1_performtransformation_binding != null) {
			Box superRuleBox = (Box) result_pattern_RefinmentRules_11_1_performtransformation_binding[0];
			NodeToRule nodeToRule = (NodeToRule) result_pattern_RefinmentRules_11_1_performtransformation_binding[1];
			Box ruleBox = (Box) result_pattern_RefinmentRules_11_1_performtransformation_binding[2];
			TGGRule rule = (TGGRule) result_pattern_RefinmentRules_11_1_performtransformation_binding[3];
			TGGRule superRule = (TGGRule) result_pattern_RefinmentRules_11_1_performtransformation_binding[4];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result_pattern_RefinmentRules_11_1_performtransformation_binding[5];
			DirectedGraph directedGraph = (DirectedGraph) result_pattern_RefinmentRules_11_1_performtransformation_binding[6];
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result_pattern_RefinmentRules_11_1_performtransformation_binding[7];

			Object[] result_pattern_RefinmentRules_11_1_performtransformation_black = pattern_RefinmentRules_11_1_performtransformation_blackBBBBBBBBFBB(
					superRuleBox, nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar, directedGraph,
					directedGraphToTripleGraphGrammar, _this, isApplicableMatch);
			if (result_pattern_RefinmentRules_11_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_RefinmentRules_11_1_performtransformation_black[8];

				return new Object[] { superRuleBox, nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar,
						directedGraph, directedGraphToTripleGraphGrammar, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_11_1_performtransformation_greenBBFB(Box superRuleBox,
			Box ruleBox, DirectedGraph directedGraph) {
		Inheritance refined = LanguageFactory.eINSTANCE.createInheritance();
		refined.setSource(ruleBox);
		directedGraph.getEdges().add(refined);
		refined.setTarget(superRuleBox);
		return new Object[] { superRuleBox, ruleBox, refined, directedGraph };
	}

	public static final Object[] pattern_RefinmentRules_11_2_collecttranslatedelements_blackB(Inheritance refined) {
		return new Object[] { refined };
	}

	public static final Object[] pattern_RefinmentRules_11_2_collecttranslatedelements_greenFB(Inheritance refined) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getCreatedElements().add(refined);
		return new Object[] { ruleresult, refined };
	}

	public static final Object[] pattern_RefinmentRules_11_3_bookkeepingforedges_blackBBBBBBBBBB(
			PerformRuleResult ruleresult, EObject superRuleBox, EObject nodeToRule, EObject ruleBox, EObject refined,
			EObject rule, EObject superRule, EObject tripleGraphGrammar, EObject directedGraph,
			EObject directedGraphToTripleGraphGrammar) {
		if (!superRuleBox.equals(tripleGraphGrammar)) {
			if (!nodeToRule.equals(superRuleBox)) {
				if (!nodeToRule.equals(ruleBox)) {
					if (!nodeToRule.equals(refined)) {
						if (!nodeToRule.equals(rule)) {
							if (!nodeToRule.equals(superRule)) {
								if (!nodeToRule.equals(tripleGraphGrammar)) {
									if (!ruleBox.equals(superRuleBox)) {
										if (!ruleBox.equals(superRule)) {
											if (!ruleBox.equals(tripleGraphGrammar)) {
												if (!refined.equals(superRuleBox)) {
													if (!refined.equals(ruleBox)) {
														if (!refined.equals(rule)) {
															if (!refined.equals(superRule)) {
																if (!refined.equals(tripleGraphGrammar)) {
																	if (!rule.equals(superRuleBox)) {
																		if (!rule.equals(ruleBox)) {
																			if (!rule.equals(superRule)) {
																				if (!rule.equals(tripleGraphGrammar)) {
																					if (!superRule
																							.equals(superRuleBox)) {
																						if (!superRule.equals(
																								tripleGraphGrammar)) {
																							if (!directedGraph.equals(
																									superRuleBox)) {
																								if (!directedGraph
																										.equals(nodeToRule)) {
																									if (!directedGraph
																											.equals(ruleBox)) {
																										if (!directedGraph
																												.equals(refined)) {
																											if (!directedGraph
																													.equals(rule)) {
																												if (!directedGraph
																														.equals(superRule)) {
																													if (!directedGraph
																															.equals(tripleGraphGrammar)) {
																														if (!directedGraph
																																.equals(directedGraphToTripleGraphGrammar)) {
																															if (!directedGraphToTripleGraphGrammar
																																	.equals(superRuleBox)) {
																																if (!directedGraphToTripleGraphGrammar
																																		.equals(nodeToRule)) {
																																	if (!directedGraphToTripleGraphGrammar
																																			.equals(ruleBox)) {
																																		if (!directedGraphToTripleGraphGrammar
																																				.equals(refined)) {
																																			if (!directedGraphToTripleGraphGrammar
																																					.equals(rule)) {
																																				if (!directedGraphToTripleGraphGrammar
																																						.equals(superRule)) {
																																					if (!directedGraphToTripleGraphGrammar
																																							.equals(tripleGraphGrammar)) {
																																						return new Object[] {
																																								ruleresult,
																																								superRuleBox,
																																								nodeToRule,
																																								ruleBox,
																																								refined,
																																								rule,
																																								superRule,
																																								tripleGraphGrammar,
																																								directedGraph,
																																								directedGraphToTripleGraphGrammar };
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
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_11_3_bookkeepingforedges_greenBBBBBBBFFFFF(
			PerformRuleResult ruleresult, EObject superRuleBox, EObject ruleBox, EObject refined, EObject rule,
			EObject superRule, EObject directedGraph) {
		EMoflonEdge refined__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__superRule____refines = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__refined____edges = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge refined__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge refined__superRuleBox____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "RefinmentRules";
		String refined__ruleBox____source_name_prime = "source";
		String rule__superRule____refines_name_prime = "refines";
		String directedGraph__refined____edges_name_prime = "edges";
		String refined__directedGraph____graph_name_prime = "graph";
		String refined__superRuleBox____target_name_prime = "target";
		refined__ruleBox____source.setSrc(refined);
		refined__ruleBox____source.setTrg(ruleBox);
		ruleresult.getCreatedEdges().add(refined__ruleBox____source);
		rule__superRule____refines.setSrc(rule);
		rule__superRule____refines.setTrg(superRule);
		ruleresult.getTranslatedEdges().add(rule__superRule____refines);
		directedGraph__refined____edges.setSrc(directedGraph);
		directedGraph__refined____edges.setTrg(refined);
		ruleresult.getCreatedEdges().add(directedGraph__refined____edges);
		refined__directedGraph____graph.setSrc(refined);
		refined__directedGraph____graph.setTrg(directedGraph);
		ruleresult.getCreatedEdges().add(refined__directedGraph____graph);
		refined__superRuleBox____target.setSrc(refined);
		refined__superRuleBox____target.setTrg(superRuleBox);
		ruleresult.getCreatedEdges().add(refined__superRuleBox____target);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		refined__ruleBox____source.setName(refined__ruleBox____source_name_prime);
		rule__superRule____refines.setName(rule__superRule____refines_name_prime);
		directedGraph__refined____edges.setName(directedGraph__refined____edges_name_prime);
		refined__directedGraph____graph.setName(refined__directedGraph____graph_name_prime);
		refined__superRuleBox____target.setName(refined__superRuleBox____target_name_prime);
		return new Object[] { ruleresult, superRuleBox, ruleBox, refined, rule, superRule, directedGraph,
				refined__ruleBox____source, rule__superRule____refines, directedGraph__refined____edges,
				refined__directedGraph____graph, refined__superRuleBox____target };
	}

	public static final void pattern_RefinmentRules_11_5_registerobjects_expressionBBBBBBBBBBB(RefinmentRules _this,
			PerformRuleResult ruleresult, EObject superRuleBox, EObject nodeToRule, EObject ruleBox, EObject refined,
			EObject rule, EObject superRule, EObject tripleGraphGrammar, EObject directedGraph,
			EObject directedGraphToTripleGraphGrammar) {
		_this.registerObjects_BWD(ruleresult, superRuleBox, nodeToRule, ruleBox, refined, rule, superRule,
				tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar);

	}

	public static final PerformRuleResult pattern_RefinmentRules_11_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_12_1_preparereturnvalue_bindingFB(RefinmentRules _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_12_1_preparereturnvalue_blackFBB(EClass eClass,
			RefinmentRules _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_BWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_12_1_preparereturnvalue_bindingAndBlackFFB(
			RefinmentRules _this) {
		Object[] result_pattern_RefinmentRules_12_1_preparereturnvalue_binding = pattern_RefinmentRules_12_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_RefinmentRules_12_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_RefinmentRules_12_1_preparereturnvalue_binding[0];

			Object[] result_pattern_RefinmentRules_12_1_preparereturnvalue_black = pattern_RefinmentRules_12_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_RefinmentRules_12_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_RefinmentRules_12_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_12_1_preparereturnvalue_greenBF(EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "RefinmentRules";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_RefinmentRules_12_2_corematch_bindingFFFB(Match match) {
		EObject _localVariable_0 = match.getObject("rule");
		EObject _localVariable_1 = match.getObject("superRule");
		EObject _localVariable_2 = match.getObject("tripleGraphGrammar");
		EObject tmpRule = _localVariable_0;
		EObject tmpSuperRule = _localVariable_1;
		EObject tmpTripleGraphGrammar = _localVariable_2;
		if (tmpRule instanceof TGGRule) {
			TGGRule rule = (TGGRule) tmpRule;
			if (tmpSuperRule instanceof TGGRule) {
				TGGRule superRule = (TGGRule) tmpSuperRule;
				if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
					TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
					return new Object[] { rule, superRule, tripleGraphGrammar, match };
				}
			}
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_RefinmentRules_12_2_corematch_blackFFBBBFFB(TGGRule rule,
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!rule.equals(superRule)) {
			for (NodeToRule nodeToRule : org.moflon.core.utilities.eMoflonEMFUtil.getOppositeReferenceTyped(rule,
					NodeToRule.class, "target")) {
				NodeCommand tmpRuleBox = nodeToRule.getSource();
				if (tmpRuleBox instanceof Box) {
					Box ruleBox = (Box) tmpRuleBox;
					for (DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar : org.moflon.core.utilities.eMoflonEMFUtil
							.getOppositeReferenceTyped(tripleGraphGrammar, DirectedGraphToTripleGraphGrammar.class,
									"target")) {
						DirectedGraph directedGraph = directedGraphToTripleGraphGrammar.getSource();
						if (directedGraph != null) {
							_result.add(new Object[] { nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar,
									directedGraph, directedGraphToTripleGraphGrammar, match });
						}

					}
				}

			}
		}
		return _result;
	}

	public static final Iterable<Object[]> pattern_RefinmentRules_12_3_findcontext_blackFBBBBBBB(NodeToRule nodeToRule,
			Box ruleBox, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!rule.equals(superRule)) {
			if (tripleGraphGrammar.getTggRule().contains(rule)) {
				if (rule.equals(nodeToRule.getTarget())) {
					if (rule.getRefines().contains(superRule)) {
						if (directedGraph.getNodes().contains(ruleBox)) {
							if (tripleGraphGrammar.getTggRule().contains(superRule)) {
								if (directedGraph.equals(directedGraphToTripleGraphGrammar.getSource())) {
									if (tripleGraphGrammar.equals(directedGraphToTripleGraphGrammar.getTarget())) {
										if (ruleBox.equals(nodeToRule.getSource())) {
											for (NodeCommand tmpSuperRuleBox : directedGraph.getNodes()) {
												if (tmpSuperRuleBox instanceof Box) {
													Box superRuleBox = (Box) tmpSuperRuleBox;
													if (!ruleBox.equals(superRuleBox)) {
														_result.add(new Object[] { superRuleBox, nodeToRule, ruleBox,
																rule, superRule, tripleGraphGrammar, directedGraph,
																directedGraphToTripleGraphGrammar });
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
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_12_3_findcontext_greenBBBBBBBBFFFFFFFFFFFFFF(Box superRuleBox,
			NodeToRule nodeToRule, Box ruleBox, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		EMoflonEdge tripleGraphGrammar__rule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge nodeToRule__rule____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__superRule____refines = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__ruleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ruleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge tripleGraphGrammar__superRule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge superRule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		EMoflonEdge directedGraph__superRuleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge superRuleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		EMoflonEdge nodeToRule__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String tripleGraphGrammar__rule____tggRule_name_prime = "tggRule";
		String rule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		String nodeToRule__rule____target_name_prime = "target";
		String rule__superRule____refines_name_prime = "refines";
		String directedGraph__ruleBox____nodes_name_prime = "nodes";
		String ruleBox__directedGraph____graph_name_prime = "graph";
		String tripleGraphGrammar__superRule____tggRule_name_prime = "tggRule";
		String superRule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		String directedGraphToTripleGraphGrammar__directedGraph____source_name_prime = "source";
		String directedGraph__superRuleBox____nodes_name_prime = "nodes";
		String superRuleBox__directedGraph____graph_name_prime = "graph";
		String directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime = "target";
		String nodeToRule__ruleBox____source_name_prime = "source";
		isApplicableMatch.getAllContextElements().add(superRuleBox);
		isApplicableMatch.getAllContextElements().add(nodeToRule);
		isApplicableMatch.getAllContextElements().add(ruleBox);
		isApplicableMatch.getAllContextElements().add(rule);
		isApplicableMatch.getAllContextElements().add(superRule);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(directedGraph);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setTrg(rule);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar__rule____tggRule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setSrc(rule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(rule__tripleGraphGrammar____tripleGraphGrammar);
		nodeToRule__rule____target.setSrc(nodeToRule);
		nodeToRule__rule____target.setTrg(rule);
		isApplicableMatch.getAllContextElements().add(nodeToRule__rule____target);
		rule__superRule____refines.setSrc(rule);
		rule__superRule____refines.setTrg(superRule);
		isApplicableMatch.getAllContextElements().add(rule__superRule____refines);
		directedGraph__ruleBox____nodes.setSrc(directedGraph);
		directedGraph__ruleBox____nodes.setTrg(ruleBox);
		isApplicableMatch.getAllContextElements().add(directedGraph__ruleBox____nodes);
		ruleBox__directedGraph____graph.setSrc(ruleBox);
		ruleBox__directedGraph____graph.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(ruleBox__directedGraph____graph);
		tripleGraphGrammar__superRule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__superRule____tggRule.setTrg(superRule);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar__superRule____tggRule);
		superRule__tripleGraphGrammar____tripleGraphGrammar.setSrc(superRule);
		superRule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(superRule__tripleGraphGrammar____tripleGraphGrammar);
		directedGraphToTripleGraphGrammar__directedGraph____source.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__directedGraph____source.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar__directedGraph____source);
		directedGraph__superRuleBox____nodes.setSrc(directedGraph);
		directedGraph__superRuleBox____nodes.setTrg(superRuleBox);
		isApplicableMatch.getAllContextElements().add(directedGraph__superRuleBox____nodes);
		superRuleBox__directedGraph____graph.setSrc(superRuleBox);
		superRuleBox__directedGraph____graph.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(superRuleBox__directedGraph____graph);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target);
		nodeToRule__ruleBox____source.setSrc(nodeToRule);
		nodeToRule__ruleBox____source.setTrg(ruleBox);
		isApplicableMatch.getAllContextElements().add(nodeToRule__ruleBox____source);
		tripleGraphGrammar__rule____tggRule.setName(tripleGraphGrammar__rule____tggRule_name_prime);
		rule__tripleGraphGrammar____tripleGraphGrammar
				.setName(rule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		nodeToRule__rule____target.setName(nodeToRule__rule____target_name_prime);
		rule__superRule____refines.setName(rule__superRule____refines_name_prime);
		directedGraph__ruleBox____nodes.setName(directedGraph__ruleBox____nodes_name_prime);
		ruleBox__directedGraph____graph.setName(ruleBox__directedGraph____graph_name_prime);
		tripleGraphGrammar__superRule____tggRule.setName(tripleGraphGrammar__superRule____tggRule_name_prime);
		superRule__tripleGraphGrammar____tripleGraphGrammar
				.setName(superRule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		directedGraphToTripleGraphGrammar__directedGraph____source
				.setName(directedGraphToTripleGraphGrammar__directedGraph____source_name_prime);
		directedGraph__superRuleBox____nodes.setName(directedGraph__superRuleBox____nodes_name_prime);
		superRuleBox__directedGraph____graph.setName(superRuleBox__directedGraph____graph_name_prime);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target
				.setName(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime);
		nodeToRule__ruleBox____source.setName(nodeToRule__ruleBox____source_name_prime);
		return new Object[] { superRuleBox, nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar, directedGraph,
				directedGraphToTripleGraphGrammar, isApplicableMatch, tripleGraphGrammar__rule____tggRule,
				rule__tripleGraphGrammar____tripleGraphGrammar, nodeToRule__rule____target, rule__superRule____refines,
				directedGraph__ruleBox____nodes, ruleBox__directedGraph____graph,
				tripleGraphGrammar__superRule____tggRule, superRule__tripleGraphGrammar____tripleGraphGrammar,
				directedGraphToTripleGraphGrammar__directedGraph____source, directedGraph__superRuleBox____nodes,
				superRuleBox__directedGraph____graph, directedGraphToTripleGraphGrammar__tripleGraphGrammar____target,
				nodeToRule__ruleBox____source };
	}

	public static final Object[] pattern_RefinmentRules_12_4_solveCSP_bindingFBBBBBBBBBB(RefinmentRules _this,
			IsApplicableMatch isApplicableMatch, Box superRuleBox, NodeToRule nodeToRule, Box ruleBox, TGGRule rule,
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_BWD(isApplicableMatch, superRuleBox, nodeToRule, ruleBox,
				rule, superRule, tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, superRuleBox, nodeToRule, ruleBox, rule, superRule,
					tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_12_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_RefinmentRules_12_4_solveCSP_bindingAndBlackFBBBBBBBBBB(RefinmentRules _this,
			IsApplicableMatch isApplicableMatch, Box superRuleBox, NodeToRule nodeToRule, Box ruleBox, TGGRule rule,
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		Object[] result_pattern_RefinmentRules_12_4_solveCSP_binding = pattern_RefinmentRules_12_4_solveCSP_bindingFBBBBBBBBBB(
				_this, isApplicableMatch, superRuleBox, nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar,
				directedGraph, directedGraphToTripleGraphGrammar);
		if (result_pattern_RefinmentRules_12_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_RefinmentRules_12_4_solveCSP_binding[0];

			Object[] result_pattern_RefinmentRules_12_4_solveCSP_black = pattern_RefinmentRules_12_4_solveCSP_blackB(
					csp);
			if (result_pattern_RefinmentRules_12_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, superRuleBox, nodeToRule, ruleBox, rule, superRule,
						tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar };
			}
		}
		return null;
	}

	public static final boolean pattern_RefinmentRules_12_5_checkCSP_expressionFBB(RefinmentRules _this, CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_12_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_RefinmentRules_12_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "RefinmentRules";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_RefinmentRules_12_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_20_1_preparereturnvalue_bindingFB(RefinmentRules _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_20_1_preparereturnvalue_blackFBBF(EClass __eClass,
			RefinmentRules _this) {
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

	public static final Object[] pattern_RefinmentRules_20_1_preparereturnvalue_bindingAndBlackFFBF(
			RefinmentRules _this) {
		Object[] result_pattern_RefinmentRules_20_1_preparereturnvalue_binding = pattern_RefinmentRules_20_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_RefinmentRules_20_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_RefinmentRules_20_1_preparereturnvalue_binding[0];

			Object[] result_pattern_RefinmentRules_20_1_preparereturnvalue_black = pattern_RefinmentRules_20_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_RefinmentRules_20_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_RefinmentRules_20_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_RefinmentRules_20_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_20_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_RefinmentRules_20_2_testcorematchandDECs_blackFFFFB(
			EMoflonEdge _edge_source) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		EObject tmpRefined = _edge_source.getSrc();
		if (tmpRefined instanceof Inheritance) {
			Inheritance refined = (Inheritance) tmpRefined;
			EObject tmpRuleBox = _edge_source.getTrg();
			if (tmpRuleBox instanceof Box) {
				Box ruleBox = (Box) tmpRuleBox;
				if (ruleBox.equals(refined.getSource())) {
					DirectedGraph directedGraph = refined.getGraph();
					if (directedGraph != null) {
						if (directedGraph.getNodes().contains(ruleBox)) {
							NodeCommand tmpSuperRuleBox = refined.getTarget();
							if (tmpSuperRuleBox instanceof Box) {
								Box superRuleBox = (Box) tmpSuperRuleBox;
								if (!ruleBox.equals(superRuleBox)) {
									if (directedGraph.getNodes().contains(superRuleBox)) {
										_result.add(new Object[] { superRuleBox, ruleBox, refined, directedGraph,
												_edge_source });
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

	public static final Object[] pattern_RefinmentRules_20_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_RefinmentRules_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBBBB(
			RefinmentRules _this, Match match, Box superRuleBox, Box ruleBox, Inheritance refined,
			DirectedGraph directedGraph) {
		boolean _localVariable_0 = _this.isAppropriate_FWD(match, superRuleBox, ruleBox, refined, directedGraph);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_RefinmentRules_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			RefinmentRules _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_FWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_20_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_20_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_RefinmentRules_20_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_21_1_preparereturnvalue_bindingFB(RefinmentRules _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_21_1_preparereturnvalue_blackFBBF(EClass __eClass,
			RefinmentRules _this) {
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

	public static final Object[] pattern_RefinmentRules_21_1_preparereturnvalue_bindingAndBlackFFBF(
			RefinmentRules _this) {
		Object[] result_pattern_RefinmentRules_21_1_preparereturnvalue_binding = pattern_RefinmentRules_21_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_RefinmentRules_21_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_RefinmentRules_21_1_preparereturnvalue_binding[0];

			Object[] result_pattern_RefinmentRules_21_1_preparereturnvalue_black = pattern_RefinmentRules_21_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_RefinmentRules_21_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_RefinmentRules_21_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_RefinmentRules_21_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_21_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_RefinmentRules_21_2_testcorematchandDECs_blackFFFB(
			EMoflonEdge _edge_refines) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		EObject tmpRule = _edge_refines.getSrc();
		if (tmpRule instanceof TGGRule) {
			TGGRule rule = (TGGRule) tmpRule;
			EObject tmpSuperRule = _edge_refines.getTrg();
			if (tmpSuperRule instanceof TGGRule) {
				TGGRule superRule = (TGGRule) tmpSuperRule;
				if (!rule.equals(superRule)) {
					if (rule.getRefines().contains(superRule)) {
						TripleGraphGrammar tripleGraphGrammar = rule.getTripleGraphGrammar();
						if (tripleGraphGrammar != null) {
							if (tripleGraphGrammar.getTggRule().contains(superRule)) {
								_result.add(new Object[] { rule, superRule, tripleGraphGrammar, _edge_refines });
							}
						}

					}
				}
			}

		}

		return _result;
	}

	public static final Object[] pattern_RefinmentRules_21_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_RefinmentRules_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBBB(
			RefinmentRules _this, Match match, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar) {
		boolean _localVariable_0 = _this.isAppropriate_BWD(match, rule, superRule, tripleGraphGrammar);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_RefinmentRules_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			RefinmentRules _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_BWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_21_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_21_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_RefinmentRules_21_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_24_1_prepare_blackB(RefinmentRules _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_RefinmentRules_24_1_prepare_greenF() {
		IsApplicableRuleResult result = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		return new Object[] { result };
	}

	public static final Object[] pattern_RefinmentRules_24_2_matchsrctrgcontext_bindingFFFFFFFBB(Match sourceMatch,
			Match targetMatch) {
		EObject _localVariable_0 = sourceMatch.getObject("superRuleBox");
		EObject _localVariable_1 = sourceMatch.getObject("ruleBox");
		EObject _localVariable_2 = sourceMatch.getObject("refined");
		EObject _localVariable_3 = targetMatch.getObject("rule");
		EObject _localVariable_4 = targetMatch.getObject("superRule");
		EObject _localVariable_5 = targetMatch.getObject("tripleGraphGrammar");
		EObject _localVariable_6 = sourceMatch.getObject("directedGraph");
		EObject tmpSuperRuleBox = _localVariable_0;
		EObject tmpRuleBox = _localVariable_1;
		EObject tmpRefined = _localVariable_2;
		EObject tmpRule = _localVariable_3;
		EObject tmpSuperRule = _localVariable_4;
		EObject tmpTripleGraphGrammar = _localVariable_5;
		EObject tmpDirectedGraph = _localVariable_6;
		if (tmpSuperRuleBox instanceof Box) {
			Box superRuleBox = (Box) tmpSuperRuleBox;
			if (tmpRuleBox instanceof Box) {
				Box ruleBox = (Box) tmpRuleBox;
				if (tmpRefined instanceof Inheritance) {
					Inheritance refined = (Inheritance) tmpRefined;
					if (tmpRule instanceof TGGRule) {
						TGGRule rule = (TGGRule) tmpRule;
						if (tmpSuperRule instanceof TGGRule) {
							TGGRule superRule = (TGGRule) tmpSuperRule;
							if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
								TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
								if (tmpDirectedGraph instanceof DirectedGraph) {
									DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
									return new Object[] { superRuleBox, ruleBox, refined, rule, superRule,
											tripleGraphGrammar, directedGraph, sourceMatch, targetMatch };
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_24_2_matchsrctrgcontext_blackBBBBBBBBB(Box superRuleBox,
			Box ruleBox, Inheritance refined, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, Match sourceMatch, Match targetMatch) {
		if (!ruleBox.equals(superRuleBox)) {
			if (!rule.equals(superRule)) {
				if (!sourceMatch.equals(targetMatch)) {
					return new Object[] { superRuleBox, ruleBox, refined, rule, superRule, tripleGraphGrammar,
							directedGraph, sourceMatch, targetMatch };
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_24_2_matchsrctrgcontext_bindingAndBlackFFFFFFFBB(
			Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_RefinmentRules_24_2_matchsrctrgcontext_binding = pattern_RefinmentRules_24_2_matchsrctrgcontext_bindingFFFFFFFBB(
				sourceMatch, targetMatch);
		if (result_pattern_RefinmentRules_24_2_matchsrctrgcontext_binding != null) {
			Box superRuleBox = (Box) result_pattern_RefinmentRules_24_2_matchsrctrgcontext_binding[0];
			Box ruleBox = (Box) result_pattern_RefinmentRules_24_2_matchsrctrgcontext_binding[1];
			Inheritance refined = (Inheritance) result_pattern_RefinmentRules_24_2_matchsrctrgcontext_binding[2];
			TGGRule rule = (TGGRule) result_pattern_RefinmentRules_24_2_matchsrctrgcontext_binding[3];
			TGGRule superRule = (TGGRule) result_pattern_RefinmentRules_24_2_matchsrctrgcontext_binding[4];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result_pattern_RefinmentRules_24_2_matchsrctrgcontext_binding[5];
			DirectedGraph directedGraph = (DirectedGraph) result_pattern_RefinmentRules_24_2_matchsrctrgcontext_binding[6];

			Object[] result_pattern_RefinmentRules_24_2_matchsrctrgcontext_black = pattern_RefinmentRules_24_2_matchsrctrgcontext_blackBBBBBBBBB(
					superRuleBox, ruleBox, refined, rule, superRule, tripleGraphGrammar, directedGraph, sourceMatch,
					targetMatch);
			if (result_pattern_RefinmentRules_24_2_matchsrctrgcontext_black != null) {

				return new Object[] { superRuleBox, ruleBox, refined, rule, superRule, tripleGraphGrammar,
						directedGraph, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_24_3_solvecsp_bindingFBBBBBBBBBB(RefinmentRules _this,
			Box superRuleBox, Box ruleBox, Inheritance refined, TGGRule rule, TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Match sourceMatch, Match targetMatch) {
		CSP _localVariable_7 = _this.isApplicable_solveCsp_CC(superRuleBox, ruleBox, refined, rule, superRule,
				tripleGraphGrammar, directedGraph, sourceMatch, targetMatch);
		CSP csp = _localVariable_7;
		if (csp != null) {
			return new Object[] { csp, _this, superRuleBox, ruleBox, refined, rule, superRule, tripleGraphGrammar,
					directedGraph, sourceMatch, targetMatch };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_24_3_solvecsp_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_RefinmentRules_24_3_solvecsp_bindingAndBlackFBBBBBBBBBB(RefinmentRules _this,
			Box superRuleBox, Box ruleBox, Inheritance refined, TGGRule rule, TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_RefinmentRules_24_3_solvecsp_binding = pattern_RefinmentRules_24_3_solvecsp_bindingFBBBBBBBBBB(
				_this, superRuleBox, ruleBox, refined, rule, superRule, tripleGraphGrammar, directedGraph, sourceMatch,
				targetMatch);
		if (result_pattern_RefinmentRules_24_3_solvecsp_binding != null) {
			CSP csp = (CSP) result_pattern_RefinmentRules_24_3_solvecsp_binding[0];

			Object[] result_pattern_RefinmentRules_24_3_solvecsp_black = pattern_RefinmentRules_24_3_solvecsp_blackB(
					csp);
			if (result_pattern_RefinmentRules_24_3_solvecsp_black != null) {

				return new Object[] { csp, _this, superRuleBox, ruleBox, refined, rule, superRule, tripleGraphGrammar,
						directedGraph, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final boolean pattern_RefinmentRules_24_4_checkCSP_expressionFB(CSP csp) {
		boolean _localVariable_0 = csp.check();
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Iterable<Object[]> pattern_RefinmentRules_24_5_matchcorrcontext_blackFBBBBFBB(Box ruleBox,
			TGGRule rule, TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Match sourceMatch,
			Match targetMatch) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!sourceMatch.equals(targetMatch)) {
			for (NodeToRule nodeToRule : org.moflon.core.utilities.eMoflonEMFUtil.getOppositeReferenceTyped(rule,
					NodeToRule.class, "target")) {
				if (ruleBox.equals(nodeToRule.getSource())) {
					for (DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar : org.moflon.core.utilities.eMoflonEMFUtil
							.getOppositeReferenceTyped(directedGraph, DirectedGraphToTripleGraphGrammar.class,
									"source")) {
						if (tripleGraphGrammar.equals(directedGraphToTripleGraphGrammar.getTarget())) {
							_result.add(new Object[] { nodeToRule, ruleBox, rule, tripleGraphGrammar, directedGraph,
									directedGraphToTripleGraphGrammar, sourceMatch, targetMatch });
						}
					}
				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_24_5_matchcorrcontext_greenBBBBF(NodeToRule nodeToRule,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Match sourceMatch, Match targetMatch) {
		CCMatch ccMatch = RuntimeFactory.eINSTANCE.createCCMatch();
		String ccMatch_ruleName_prime = "RefinmentRules";
		ccMatch.setSourceMatch(sourceMatch);
		ccMatch.setTargetMatch(targetMatch);
		ccMatch.getAllContextElements().add(nodeToRule);
		ccMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar);
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { nodeToRule, directedGraphToTripleGraphGrammar, sourceMatch, targetMatch, ccMatch };
	}

	public static final Object[] pattern_RefinmentRules_24_6_createcorrespondence_blackBBBBBBBB(Box superRuleBox,
			Box ruleBox, Inheritance refined, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, CCMatch ccMatch) {
		if (!ruleBox.equals(superRuleBox)) {
			if (!rule.equals(superRule)) {
				return new Object[] { superRuleBox, ruleBox, refined, rule, superRule, tripleGraphGrammar,
						directedGraph, ccMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_24_7_addtoreturnedresult_blackBB(IsApplicableRuleResult result,
			CCMatch ccMatch) {
		return new Object[] { result, ccMatch };
	}

	public static final Object[] pattern_RefinmentRules_24_7_addtoreturnedresult_greenBB(IsApplicableRuleResult result,
			CCMatch ccMatch) {
		result.getIsApplicableMatch().add(ccMatch);
		boolean result_success_prime = Boolean.valueOf(true);
		String ccMatch_ruleName_prime = "RefinmentRules";
		result.setSuccess(Boolean.valueOf(result_success_prime));
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { result, ccMatch };
	}

	public static final IsApplicableRuleResult pattern_RefinmentRules_24_8_expressionFB(IsApplicableRuleResult result) {
		IsApplicableRuleResult _result = result;
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_27_1_matchtggpattern_blackBBBB(Box superRuleBox, Box ruleBox,
			Inheritance refined, DirectedGraph directedGraph) {
		if (!ruleBox.equals(superRuleBox)) {
			if (ruleBox.equals(refined.getSource())) {
				if (directedGraph.getNodes().contains(ruleBox)) {
					if (directedGraph.getEdges().contains(refined)) {
						if (superRuleBox.equals(refined.getTarget())) {
							if (directedGraph.getNodes().contains(superRuleBox)) {
								return new Object[] { superRuleBox, ruleBox, refined, directedGraph };
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static final boolean pattern_RefinmentRules_27_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_RefinmentRules_27_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_28_1_matchtggpattern_blackBBB(TGGRule rule, TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar) {
		if (!rule.equals(superRule)) {
			if (tripleGraphGrammar.getTggRule().contains(rule)) {
				if (rule.getRefines().contains(superRule)) {
					if (tripleGraphGrammar.getTggRule().contains(superRule)) {
						return new Object[] { rule, superRule, tripleGraphGrammar };
					}
				}
			}
		}
		return null;
	}

	public static final boolean pattern_RefinmentRules_28_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_RefinmentRules_28_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_29_1_createresult_blackB(RefinmentRules _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_RefinmentRules_29_1_createresult_greenFF() {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		ModelgeneratorRuleResult ruleResult = RuntimeFactory.eINSTANCE.createModelgeneratorRuleResult();
		boolean ruleResult_success_prime = false;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		return new Object[] { isApplicableMatch, ruleResult };
	}

	public static final Object[] pattern_RefinmentRules_29_2_isapplicablecore_black_nac_0BB(
			ModelgeneratorRuleResult ruleResult, Box superRuleBox) {
		if (ruleResult.getSourceObjects().contains(superRuleBox)) {
			return new Object[] { ruleResult, superRuleBox };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_29_2_isapplicablecore_black_nac_1BB(
			ModelgeneratorRuleResult ruleResult, DirectedGraph directedGraph) {
		if (ruleResult.getSourceObjects().contains(directedGraph)) {
			return new Object[] { ruleResult, directedGraph };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_29_2_isapplicablecore_black_nac_2BB(
			ModelgeneratorRuleResult ruleResult, Box ruleBox) {
		if (ruleResult.getSourceObjects().contains(ruleBox)) {
			return new Object[] { ruleResult, ruleBox };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_29_2_isapplicablecore_black_nac_3BB(
			ModelgeneratorRuleResult ruleResult, NodeToRule nodeToRule) {
		if (ruleResult.getCorrObjects().contains(nodeToRule)) {
			return new Object[] { ruleResult, nodeToRule };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_29_2_isapplicablecore_black_nac_4BB(
			ModelgeneratorRuleResult ruleResult, TGGRule rule) {
		if (ruleResult.getTargetObjects().contains(rule)) {
			return new Object[] { ruleResult, rule };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_29_2_isapplicablecore_black_nac_5BB(
			ModelgeneratorRuleResult ruleResult, TripleGraphGrammar tripleGraphGrammar) {
		if (ruleResult.getTargetObjects().contains(tripleGraphGrammar)) {
			return new Object[] { ruleResult, tripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_29_2_isapplicablecore_black_nac_6BB(
			ModelgeneratorRuleResult ruleResult, TGGRule superRule) {
		if (ruleResult.getTargetObjects().contains(superRule)) {
			return new Object[] { ruleResult, superRule };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_29_2_isapplicablecore_black_nac_7BB(
			ModelgeneratorRuleResult ruleResult, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		if (ruleResult.getCorrObjects().contains(directedGraphToTripleGraphGrammar)) {
			return new Object[] { ruleResult, directedGraphToTripleGraphGrammar };
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_RefinmentRules_29_2_isapplicablecore_blackFFFFFFFFFBB(
			RuleEntryContainer ruleEntryContainer, ModelgeneratorRuleResult ruleResult) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (RuleEntryList nodeToRuleList : ruleEntryContainer.getRuleEntryList()) {
			for (EObject tmpNodeToRule : nodeToRuleList.getEntryObjects()) {
				if (tmpNodeToRule instanceof NodeToRule) {
					NodeToRule nodeToRule = (NodeToRule) tmpNodeToRule;
					NodeCommand tmpRuleBox = nodeToRule.getSource();
					if (tmpRuleBox instanceof Box) {
						Box ruleBox = (Box) tmpRuleBox;
						TGGRule rule = nodeToRule.getTarget();
						if (rule != null) {
							DirectedGraph directedGraph = ruleBox.getGraph();
							if (directedGraph != null) {
								TripleGraphGrammar tripleGraphGrammar = rule.getTripleGraphGrammar();
								if (tripleGraphGrammar != null) {
									if (pattern_RefinmentRules_29_2_isapplicablecore_black_nac_3BB(ruleResult,
											nodeToRule) == null) {
										if (pattern_RefinmentRules_29_2_isapplicablecore_black_nac_2BB(ruleResult,
												ruleBox) == null) {
											if (pattern_RefinmentRules_29_2_isapplicablecore_black_nac_4BB(ruleResult,
													rule) == null) {
												if (pattern_RefinmentRules_29_2_isapplicablecore_black_nac_1BB(
														ruleResult, directedGraph) == null) {
													if (pattern_RefinmentRules_29_2_isapplicablecore_black_nac_5BB(
															ruleResult, tripleGraphGrammar) == null) {
														for (NodeCommand tmpSuperRuleBox : directedGraph.getNodes()) {
															if (tmpSuperRuleBox instanceof Box) {
																Box superRuleBox = (Box) tmpSuperRuleBox;
																if (!ruleBox.equals(superRuleBox)) {
																	if (pattern_RefinmentRules_29_2_isapplicablecore_black_nac_0BB(
																			ruleResult, superRuleBox) == null) {
																		for (TGGRule superRule : tripleGraphGrammar
																				.getTggRule()) {
																			if (!rule.equals(superRule)) {
																				if (pattern_RefinmentRules_29_2_isapplicablecore_black_nac_6BB(
																						ruleResult,
																						superRule) == null) {
																					for (DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar : org.moflon.core.utilities.eMoflonEMFUtil
																							.getOppositeReferenceTyped(
																									directedGraph,
																									DirectedGraphToTripleGraphGrammar.class,
																									"source")) {
																						if (tripleGraphGrammar
																								.equals(directedGraphToTripleGraphGrammar
																										.getTarget())) {
																							if (pattern_RefinmentRules_29_2_isapplicablecore_black_nac_7BB(
																									ruleResult,
																									directedGraphToTripleGraphGrammar) == null) {
																								_result.add(
																										new Object[] {
																												nodeToRuleList,
																												superRuleBox,
																												directedGraph,
																												ruleBox,
																												nodeToRule,
																												rule,
																												tripleGraphGrammar,
																												superRule,
																												directedGraphToTripleGraphGrammar,
																												ruleEntryContainer,
																												ruleResult });
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
								}

							}

						}

					}

				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_29_3_solveCSP_bindingFBBBBBBBBBBB(RefinmentRules _this,
			IsApplicableMatch isApplicableMatch, Box superRuleBox, NodeToRule nodeToRule, Box ruleBox, TGGRule rule,
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, ModelgeneratorRuleResult ruleResult) {
		CSP _localVariable_0 = _this.generateModel_solveCsp_BWD(isApplicableMatch, superRuleBox, nodeToRule, ruleBox,
				rule, superRule, tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar, ruleResult);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, superRuleBox, nodeToRule, ruleBox, rule, superRule,
					tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar, ruleResult };
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_29_3_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_RefinmentRules_29_3_solveCSP_bindingAndBlackFBBBBBBBBBBB(RefinmentRules _this,
			IsApplicableMatch isApplicableMatch, Box superRuleBox, NodeToRule nodeToRule, Box ruleBox, TGGRule rule,
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, ModelgeneratorRuleResult ruleResult) {
		Object[] result_pattern_RefinmentRules_29_3_solveCSP_binding = pattern_RefinmentRules_29_3_solveCSP_bindingFBBBBBBBBBBB(
				_this, isApplicableMatch, superRuleBox, nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar,
				directedGraph, directedGraphToTripleGraphGrammar, ruleResult);
		if (result_pattern_RefinmentRules_29_3_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_RefinmentRules_29_3_solveCSP_binding[0];

			Object[] result_pattern_RefinmentRules_29_3_solveCSP_black = pattern_RefinmentRules_29_3_solveCSP_blackB(
					csp);
			if (result_pattern_RefinmentRules_29_3_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, superRuleBox, nodeToRule, ruleBox, rule, superRule,
						tripleGraphGrammar, directedGraph, directedGraphToTripleGraphGrammar, ruleResult };
			}
		}
		return null;
	}

	public static final boolean pattern_RefinmentRules_29_4_checkCSP_expressionFBB(RefinmentRules _this, CSP csp) {
		boolean _localVariable_0 = _this.generateModel_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_RefinmentRules_29_5_checknacs_blackBBBBBBBB(Box superRuleBox,
			NodeToRule nodeToRule, Box ruleBox, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		if (!ruleBox.equals(superRuleBox)) {
			if (!rule.equals(superRule)) {
				return new Object[] { superRuleBox, nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar,
						directedGraph, directedGraphToTripleGraphGrammar };
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_29_6_perform_blackBBBBBBBBB(Box superRuleBox,
			NodeToRule nodeToRule, Box ruleBox, TGGRule rule, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar,
			ModelgeneratorRuleResult ruleResult) {
		if (!ruleBox.equals(superRuleBox)) {
			if (!rule.equals(superRule)) {
				return new Object[] { superRuleBox, nodeToRule, ruleBox, rule, superRule, tripleGraphGrammar,
						directedGraph, directedGraphToTripleGraphGrammar, ruleResult };
			}
		}
		return null;
	}

	public static final Object[] pattern_RefinmentRules_29_6_perform_greenBBFBBBB(Box superRuleBox, Box ruleBox,
			TGGRule rule, TGGRule superRule, DirectedGraph directedGraph, ModelgeneratorRuleResult ruleResult) {
		Inheritance refined = LanguageFactory.eINSTANCE.createInheritance();
		rule.getRefines().add(superRule);
		boolean ruleResult_success_prime = Boolean.valueOf(true);
		int _localVariable_0 = ruleResult.getIncrementedPerformCount();
		refined.setSource(ruleBox);
		directedGraph.getEdges().add(refined);
		refined.setTarget(superRuleBox);
		ruleResult.getSourceObjects().add(refined);
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		int ruleResult_performCount_prime = Integer.valueOf(_localVariable_0);
		ruleResult.setPerformCount(Integer.valueOf(ruleResult_performCount_prime));
		return new Object[] { superRuleBox, ruleBox, refined, rule, superRule, directedGraph, ruleResult };
	}

	public static final ModelgeneratorRuleResult pattern_RefinmentRules_29_7_expressionFB(
			ModelgeneratorRuleResult ruleResult) {
		ModelgeneratorRuleResult _result = ruleResult;
		return _result;
	}

	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //RefinmentRulesImpl
