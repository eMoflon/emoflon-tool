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
import org.moflon.ide.visualization.dot.language.Composite;
import org.moflon.ide.visualization.dot.language.DirectedGraph;
import org.moflon.ide.visualization.dot.language.LanguageFactory;
import org.moflon.ide.visualization.dot.language.NodeCommand;

import org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar;
import org.moflon.ide.visualization.dot.tgg.schema.NodeToRule;

import org.moflon.ide.visualization.dot.tgg.schema.Rules.ComplementRule;
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
 * An implementation of the model object '<em><b>Complement Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ComplementRuleImpl extends AbstractRuleImpl implements ComplementRule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComplementRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulesPackage.Literals.COMPLEMENT_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_FWD(Match match, DirectedGraph directedGraph, Box ruleBox, Composite complements,
			Box superRuleBox) {
		// initial bindings
		Object[] result1_black = ComplementRuleImpl.pattern_ComplementRule_0_1_initialbindings_blackBBBBBB(this, match,
				directedGraph, ruleBox, complements, superRuleBox);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[directedGraph] = " + directedGraph
					+ ", " + "[ruleBox] = " + ruleBox + ", " + "[complements] = " + complements + ", "
					+ "[superRuleBox] = " + superRuleBox + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = ComplementRuleImpl
				.pattern_ComplementRule_0_2_SolveCSP_bindingAndBlackFBBBBBB(this, match, directedGraph, ruleBox,
						complements, superRuleBox);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[directedGraph] = " + directedGraph + ", "
					+ "[ruleBox] = " + ruleBox + ", " + "[complements] = " + complements + ", " + "[superRuleBox] = "
					+ superRuleBox + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (ComplementRuleImpl.pattern_ComplementRule_0_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = ComplementRuleImpl
					.pattern_ComplementRule_0_4_collectelementstobetranslated_blackBBBBB(match, directedGraph, ruleBox,
							complements, superRuleBox);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[directedGraph] = " + directedGraph + ", "
						+ "[ruleBox] = " + ruleBox + ", " + "[complements] = " + complements + ", "
						+ "[superRuleBox] = " + superRuleBox + ".");
			}
			ComplementRuleImpl.pattern_ComplementRule_0_4_collectelementstobetranslated_greenBBBBBFFFF(match,
					directedGraph, ruleBox, complements, superRuleBox);
			// EMoflonEdge directedGraph__complements____edges = (EMoflonEdge) result4_green[5];
			// EMoflonEdge complements__directedGraph____graph = (EMoflonEdge) result4_green[6];
			// EMoflonEdge complements__ruleBox____source = (EMoflonEdge) result4_green[7];
			// EMoflonEdge complements__superRuleBox____target = (EMoflonEdge) result4_green[8];

			// collect context elements
			Object[] result5_black = ComplementRuleImpl.pattern_ComplementRule_0_5_collectcontextelements_blackBBBBB(
					match, directedGraph, ruleBox, complements, superRuleBox);
			if (result5_black == null) {
				throw new RuntimeException("Pattern matching in node [collect context elements] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[directedGraph] = " + directedGraph + ", "
						+ "[ruleBox] = " + ruleBox + ", " + "[complements] = " + complements + ", "
						+ "[superRuleBox] = " + superRuleBox + ".");
			}
			ComplementRuleImpl.pattern_ComplementRule_0_5_collectcontextelements_greenBBBBFFFF(match, directedGraph,
					ruleBox, superRuleBox);
			// EMoflonEdge directedGraph__ruleBox____nodes = (EMoflonEdge) result5_green[4];
			// EMoflonEdge ruleBox__directedGraph____graph = (EMoflonEdge) result5_green[5];
			// EMoflonEdge directedGraph__superRuleBox____nodes = (EMoflonEdge) result5_green[6];
			// EMoflonEdge superRuleBox__directedGraph____graph = (EMoflonEdge) result5_green[7];

			// register objects to match
			ComplementRuleImpl.pattern_ComplementRule_0_6_registerobjectstomatch_expressionBBBBBB(this, match,
					directedGraph, ruleBox, complements, superRuleBox);
			return ComplementRuleImpl.pattern_ComplementRule_0_7_expressionF();
		} else {
			return ComplementRuleImpl.pattern_ComplementRule_0_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_FWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = ComplementRuleImpl
				.pattern_ComplementRule_1_1_performtransformation_bindingAndBlackFFFFFFFFFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		TGGRule superRule = (TGGRule) result1_bindingAndBlack[0];
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result1_bindingAndBlack[1];
		DirectedGraph directedGraph = (DirectedGraph) result1_bindingAndBlack[2];
		Box ruleBox = (Box) result1_bindingAndBlack[3];
		NodeToRule nodeToRule = (NodeToRule) result1_bindingAndBlack[4];
		Composite complements = (Composite) result1_bindingAndBlack[5];
		TGGRule rule = (TGGRule) result1_bindingAndBlack[6];
		DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result1_bindingAndBlack[7];
		Box superRuleBox = (Box) result1_bindingAndBlack[8];
		// CSP csp = (CSP) result1_bindingAndBlack[9];
		ComplementRuleImpl.pattern_ComplementRule_1_1_performtransformation_greenBB(superRule, rule);

		// collect translated elements
		Object[] result2_black = ComplementRuleImpl
				.pattern_ComplementRule_1_2_collecttranslatedelements_blackB(complements);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[complements] = " + complements + ".");
		}
		Object[] result2_green = ComplementRuleImpl
				.pattern_ComplementRule_1_2_collecttranslatedelements_greenFB(complements);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = ComplementRuleImpl.pattern_ComplementRule_1_3_bookkeepingforedges_blackBBBBBBBBBB(
				ruleresult, superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, complements, rule,
				directedGraphToTripleGraphGrammar, superRuleBox);
		if (result3_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [bookkeeping for edges] failed." + " Variables: " + "[ruleresult] = "
							+ ruleresult + ", " + "[superRule] = " + superRule + ", " + "[tripleGraphGrammar] = "
							+ tripleGraphGrammar + ", " + "[directedGraph] = " + directedGraph + ", " + "[ruleBox] = "
							+ ruleBox + ", " + "[nodeToRule] = " + nodeToRule + ", " + "[complements] = " + complements
							+ ", " + "[rule] = " + rule + ", " + "[directedGraphToTripleGraphGrammar] = "
							+ directedGraphToTripleGraphGrammar + ", " + "[superRuleBox] = " + superRuleBox + ".");
		}
		ComplementRuleImpl.pattern_ComplementRule_1_3_bookkeepingforedges_greenBBBBBBBFFFFF(ruleresult, superRule,
				directedGraph, ruleBox, complements, rule, superRuleBox);
		// EMoflonEdge directedGraph__complements____edges = (EMoflonEdge) result3_green[7];
		// EMoflonEdge complements__directedGraph____graph = (EMoflonEdge) result3_green[8];
		// EMoflonEdge complements__ruleBox____source = (EMoflonEdge) result3_green[9];
		// EMoflonEdge rule__superRule____kernel = (EMoflonEdge) result3_green[10];
		// EMoflonEdge complements__superRuleBox____target = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		ComplementRuleImpl.pattern_ComplementRule_1_5_registerobjects_expressionBBBBBBBBBBB(this, ruleresult, superRule,
				tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, complements, rule,
				directedGraphToTripleGraphGrammar, superRuleBox);
		return ComplementRuleImpl.pattern_ComplementRule_1_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_FWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = ComplementRuleImpl
				.pattern_ComplementRule_2_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = ComplementRuleImpl
				.pattern_ComplementRule_2_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = ComplementRuleImpl.pattern_ComplementRule_2_2_corematch_bindingFFFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		DirectedGraph directedGraph = (DirectedGraph) result2_binding[0];
		Box ruleBox = (Box) result2_binding[1];
		Composite complements = (Composite) result2_binding[2];
		Box superRuleBox = (Box) result2_binding[3];
		for (Object[] result2_black : ComplementRuleImpl.pattern_ComplementRule_2_2_corematch_blackFBBFBFFBB(
				directedGraph, ruleBox, complements, superRuleBox, match)) {
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_black[0];
			NodeToRule nodeToRule = (NodeToRule) result2_black[3];
			TGGRule rule = (TGGRule) result2_black[5];
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result2_black[6];
			// ForEach find context
			for (Object[] result3_black : ComplementRuleImpl.pattern_ComplementRule_2_3_findcontext_blackFBBBBBBBB(
					tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, complements, rule,
					directedGraphToTripleGraphGrammar, superRuleBox)) {
				TGGRule superRule = (TGGRule) result3_black[0];
				Object[] result3_green = ComplementRuleImpl
						.pattern_ComplementRule_2_3_findcontext_greenBBBBBBBBBFFFFFFFFFFFFFFFFF(superRule,
								tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, complements, rule,
								directedGraphToTripleGraphGrammar, superRuleBox);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[9];
				// EMoflonEdge directedGraph__complements____edges = (EMoflonEdge) result3_green[10];
				// EMoflonEdge complements__directedGraph____graph = (EMoflonEdge) result3_green[11];
				// EMoflonEdge tripleGraphGrammar__superRule____tggRule = (EMoflonEdge) result3_green[12];
				// EMoflonEdge superRule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result3_green[13];
				// EMoflonEdge directedGraph__ruleBox____nodes = (EMoflonEdge) result3_green[14];
				// EMoflonEdge ruleBox__directedGraph____graph = (EMoflonEdge) result3_green[15];
				// EMoflonEdge nodeToRule__ruleBox____source = (EMoflonEdge) result3_green[16];
				// EMoflonEdge directedGraph__superRuleBox____nodes = (EMoflonEdge) result3_green[17];
				// EMoflonEdge superRuleBox__directedGraph____graph = (EMoflonEdge) result3_green[18];
				// EMoflonEdge complements__ruleBox____source = (EMoflonEdge) result3_green[19];
				// EMoflonEdge tripleGraphGrammar__rule____tggRule = (EMoflonEdge) result3_green[20];
				// EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result3_green[21];
				// EMoflonEdge nodeToRule__rule____target = (EMoflonEdge) result3_green[22];
				// EMoflonEdge complements__superRuleBox____target = (EMoflonEdge) result3_green[23];
				// EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = (EMoflonEdge) result3_green[24];
				// EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = (EMoflonEdge) result3_green[25];

				// solve CSP
				Object[] result4_bindingAndBlack = ComplementRuleImpl
						.pattern_ComplementRule_2_4_solveCSP_bindingAndBlackFBBBBBBBBBBB(this, isApplicableMatch,
								superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, complements, rule,
								directedGraphToTripleGraphGrammar, superRuleBox);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[superRule] = " + superRule + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar
							+ ", " + "[directedGraph] = " + directedGraph + ", " + "[ruleBox] = " + ruleBox + ", "
							+ "[nodeToRule] = " + nodeToRule + ", " + "[complements] = " + complements + ", "
							+ "[rule] = " + rule + ", " + "[directedGraphToTripleGraphGrammar] = "
							+ directedGraphToTripleGraphGrammar + ", " + "[superRuleBox] = " + superRuleBox + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (ComplementRuleImpl.pattern_ComplementRule_2_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = ComplementRuleImpl
							.pattern_ComplementRule_2_6_addmatchtoruleresult_blackBB(ruleresult, isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					ComplementRuleImpl.pattern_ComplementRule_2_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return ComplementRuleImpl.pattern_ComplementRule_2_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_FWD(Match match, DirectedGraph directedGraph, Box ruleBox, Composite complements,
			Box superRuleBox) {
		match.registerObject("directedGraph", directedGraph);
		match.registerObject("ruleBox", ruleBox);
		match.registerObject("complements", complements);
		match.registerObject("superRuleBox", superRuleBox);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_FWD(Match match, DirectedGraph directedGraph, Box ruleBox, Composite complements,
			Box superRuleBox) {// Create CSP
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
	public CSP isApplicable_solveCsp_FWD(IsApplicableMatch isApplicableMatch, TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule,
			Composite complements, TGGRule rule, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar,
			Box superRuleBox) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox.label", true, csp);
		var_ruleBox_label.setValue(ruleBox.getLabel());
		var_ruleBox_label.setType("String");
		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule.name", true, csp);
		var_rule_name.setValue(rule.getName());
		var_rule_name.setType("String");
		Variable var_superRuleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("superRuleBox.label", true, csp);
		var_superRuleBox_label.setValue(superRuleBox.getLabel());
		var_superRuleBox_label.setType("String");
		Variable var_superRule_name = CSPFactoryHelper.eINSTANCE.createVariable("superRule.name", true, csp);
		var_superRule_name.setValue(superRule.getName());
		var_superRule_name.setType("String");

		// Create unbound variables

		// Create constraints
		Eq eq = new Eq();
		Eq eq_0 = new Eq();

		csp.getConstraints().add(eq);
		csp.getConstraints().add(eq_0);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_ruleBox_label, var_rule_name);
		eq_0.setRuleName("");
		eq_0.solve(var_superRuleBox_label, var_superRule_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("superRule", superRule);
		isApplicableMatch.registerObject("tripleGraphGrammar", tripleGraphGrammar);
		isApplicableMatch.registerObject("directedGraph", directedGraph);
		isApplicableMatch.registerObject("ruleBox", ruleBox);
		isApplicableMatch.registerObject("nodeToRule", nodeToRule);
		isApplicableMatch.registerObject("complements", complements);
		isApplicableMatch.registerObject("rule", rule);
		isApplicableMatch.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);
		isApplicableMatch.registerObject("superRuleBox", superRuleBox);
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
	public void registerObjects_FWD(PerformRuleResult ruleresult, EObject superRule, EObject tripleGraphGrammar,
			EObject directedGraph, EObject ruleBox, EObject nodeToRule, EObject complements, EObject rule,
			EObject directedGraphToTripleGraphGrammar, EObject superRuleBox) {
		ruleresult.registerObject("superRule", superRule);
		ruleresult.registerObject("tripleGraphGrammar", tripleGraphGrammar);
		ruleresult.registerObject("directedGraph", directedGraph);
		ruleresult.registerObject("ruleBox", ruleBox);
		ruleresult.registerObject("nodeToRule", nodeToRule);
		ruleresult.registerObject("complements", complements);
		ruleresult.registerObject("rule", rule);
		ruleresult.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);
		ruleresult.registerObject("superRuleBox", superRuleBox);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_FWD(Match match) {
		return true && org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("complements").eClass())
				.equals("language.Composite.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_BWD(Match match, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			TGGRule rule) {
		// initial bindings
		Object[] result1_black = ComplementRuleImpl.pattern_ComplementRule_10_1_initialbindings_blackBBBBB(this, match,
				superRule, tripleGraphGrammar, rule);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[superRule] = " + superRule + ", "
					+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ", " + "[rule] = " + rule + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = ComplementRuleImpl
				.pattern_ComplementRule_10_2_SolveCSP_bindingAndBlackFBBBBB(this, match, superRule, tripleGraphGrammar,
						rule);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[superRule] = " + superRule + ", "
					+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ", " + "[rule] = " + rule + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (ComplementRuleImpl.pattern_ComplementRule_10_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = ComplementRuleImpl
					.pattern_ComplementRule_10_4_collectelementstobetranslated_blackBBBB(match, superRule,
							tripleGraphGrammar, rule);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[superRule] = " + superRule + ", "
						+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ", " + "[rule] = " + rule + ".");
			}
			ComplementRuleImpl.pattern_ComplementRule_10_4_collectelementstobetranslated_greenBBBF(match, superRule,
					rule);
			// EMoflonEdge rule__superRule____kernel = (EMoflonEdge) result4_green[3];

			// collect context elements
			Object[] result5_black = ComplementRuleImpl.pattern_ComplementRule_10_5_collectcontextelements_blackBBBB(
					match, superRule, tripleGraphGrammar, rule);
			if (result5_black == null) {
				throw new RuntimeException("Pattern matching in node [collect context elements] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[superRule] = " + superRule + ", "
						+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ", " + "[rule] = " + rule + ".");
			}
			ComplementRuleImpl.pattern_ComplementRule_10_5_collectcontextelements_greenBBBBFFFF(match, superRule,
					tripleGraphGrammar, rule);
			// EMoflonEdge tripleGraphGrammar__superRule____tggRule = (EMoflonEdge) result5_green[4];
			// EMoflonEdge superRule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result5_green[5];
			// EMoflonEdge tripleGraphGrammar__rule____tggRule = (EMoflonEdge) result5_green[6];
			// EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result5_green[7];

			// register objects to match
			ComplementRuleImpl.pattern_ComplementRule_10_6_registerobjectstomatch_expressionBBBBB(this, match,
					superRule, tripleGraphGrammar, rule);
			return ComplementRuleImpl.pattern_ComplementRule_10_7_expressionF();
		} else {
			return ComplementRuleImpl.pattern_ComplementRule_10_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_BWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = ComplementRuleImpl
				.pattern_ComplementRule_11_1_performtransformation_bindingAndBlackFFFFFFFFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		TGGRule superRule = (TGGRule) result1_bindingAndBlack[0];
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result1_bindingAndBlack[1];
		DirectedGraph directedGraph = (DirectedGraph) result1_bindingAndBlack[2];
		Box ruleBox = (Box) result1_bindingAndBlack[3];
		NodeToRule nodeToRule = (NodeToRule) result1_bindingAndBlack[4];
		TGGRule rule = (TGGRule) result1_bindingAndBlack[5];
		DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result1_bindingAndBlack[6];
		Box superRuleBox = (Box) result1_bindingAndBlack[7];
		// CSP csp = (CSP) result1_bindingAndBlack[8];
		Object[] result1_green = ComplementRuleImpl
				.pattern_ComplementRule_11_1_performtransformation_greenBBFB(directedGraph, ruleBox, superRuleBox);
		Composite complements = (Composite) result1_green[2];

		// collect translated elements
		Object[] result2_black = ComplementRuleImpl
				.pattern_ComplementRule_11_2_collecttranslatedelements_blackB(complements);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[complements] = " + complements + ".");
		}
		Object[] result2_green = ComplementRuleImpl
				.pattern_ComplementRule_11_2_collecttranslatedelements_greenFB(complements);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = ComplementRuleImpl.pattern_ComplementRule_11_3_bookkeepingforedges_blackBBBBBBBBBB(
				ruleresult, superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, complements, rule,
				directedGraphToTripleGraphGrammar, superRuleBox);
		if (result3_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [bookkeeping for edges] failed." + " Variables: " + "[ruleresult] = "
							+ ruleresult + ", " + "[superRule] = " + superRule + ", " + "[tripleGraphGrammar] = "
							+ tripleGraphGrammar + ", " + "[directedGraph] = " + directedGraph + ", " + "[ruleBox] = "
							+ ruleBox + ", " + "[nodeToRule] = " + nodeToRule + ", " + "[complements] = " + complements
							+ ", " + "[rule] = " + rule + ", " + "[directedGraphToTripleGraphGrammar] = "
							+ directedGraphToTripleGraphGrammar + ", " + "[superRuleBox] = " + superRuleBox + ".");
		}
		ComplementRuleImpl.pattern_ComplementRule_11_3_bookkeepingforedges_greenBBBBBBBFFFFF(ruleresult, superRule,
				directedGraph, ruleBox, complements, rule, superRuleBox);
		// EMoflonEdge directedGraph__complements____edges = (EMoflonEdge) result3_green[7];
		// EMoflonEdge complements__directedGraph____graph = (EMoflonEdge) result3_green[8];
		// EMoflonEdge complements__ruleBox____source = (EMoflonEdge) result3_green[9];
		// EMoflonEdge rule__superRule____kernel = (EMoflonEdge) result3_green[10];
		// EMoflonEdge complements__superRuleBox____target = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		ComplementRuleImpl.pattern_ComplementRule_11_5_registerobjects_expressionBBBBBBBBBBB(this, ruleresult,
				superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, complements, rule,
				directedGraphToTripleGraphGrammar, superRuleBox);
		return ComplementRuleImpl.pattern_ComplementRule_11_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_BWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = ComplementRuleImpl
				.pattern_ComplementRule_12_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = ComplementRuleImpl
				.pattern_ComplementRule_12_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = ComplementRuleImpl.pattern_ComplementRule_12_2_corematch_bindingFFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		TGGRule superRule = (TGGRule) result2_binding[0];
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_binding[1];
		TGGRule rule = (TGGRule) result2_binding[2];
		for (Object[] result2_black : ComplementRuleImpl.pattern_ComplementRule_12_2_corematch_blackBBFFFBFB(superRule,
				tripleGraphGrammar, rule, match)) {
			DirectedGraph directedGraph = (DirectedGraph) result2_black[2];
			Box ruleBox = (Box) result2_black[3];
			NodeToRule nodeToRule = (NodeToRule) result2_black[4];
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result2_black[6];
			// ForEach find context
			for (Object[] result3_black : ComplementRuleImpl.pattern_ComplementRule_12_3_findcontext_blackBBBBBBBF(
					superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
					directedGraphToTripleGraphGrammar)) {
				Box superRuleBox = (Box) result3_black[7];
				Object[] result3_green = ComplementRuleImpl
						.pattern_ComplementRule_12_3_findcontext_greenBBBBBBBBFFFFFFFFFFFFFF(superRule,
								tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
								directedGraphToTripleGraphGrammar, superRuleBox);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[8];
				// EMoflonEdge tripleGraphGrammar__superRule____tggRule = (EMoflonEdge) result3_green[9];
				// EMoflonEdge superRule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result3_green[10];
				// EMoflonEdge directedGraph__ruleBox____nodes = (EMoflonEdge) result3_green[11];
				// EMoflonEdge ruleBox__directedGraph____graph = (EMoflonEdge) result3_green[12];
				// EMoflonEdge nodeToRule__ruleBox____source = (EMoflonEdge) result3_green[13];
				// EMoflonEdge directedGraph__superRuleBox____nodes = (EMoflonEdge) result3_green[14];
				// EMoflonEdge superRuleBox__directedGraph____graph = (EMoflonEdge) result3_green[15];
				// EMoflonEdge tripleGraphGrammar__rule____tggRule = (EMoflonEdge) result3_green[16];
				// EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result3_green[17];
				// EMoflonEdge rule__superRule____kernel = (EMoflonEdge) result3_green[18];
				// EMoflonEdge nodeToRule__rule____target = (EMoflonEdge) result3_green[19];
				// EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = (EMoflonEdge) result3_green[20];
				// EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = (EMoflonEdge) result3_green[21];

				// solve CSP
				Object[] result4_bindingAndBlack = ComplementRuleImpl
						.pattern_ComplementRule_12_4_solveCSP_bindingAndBlackFBBBBBBBBBB(this, isApplicableMatch,
								superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
								directedGraphToTripleGraphGrammar, superRuleBox);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[superRule] = " + superRule + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar
							+ ", " + "[directedGraph] = " + directedGraph + ", " + "[ruleBox] = " + ruleBox + ", "
							+ "[nodeToRule] = " + nodeToRule + ", " + "[rule] = " + rule + ", "
							+ "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ", "
							+ "[superRuleBox] = " + superRuleBox + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (ComplementRuleImpl.pattern_ComplementRule_12_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = ComplementRuleImpl
							.pattern_ComplementRule_12_6_addmatchtoruleresult_blackBB(ruleresult, isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					ComplementRuleImpl.pattern_ComplementRule_12_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return ComplementRuleImpl.pattern_ComplementRule_12_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_BWD(Match match, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			TGGRule rule) {
		match.registerObject("superRule", superRule);
		match.registerObject("tripleGraphGrammar", tripleGraphGrammar);
		match.registerObject("rule", rule);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_BWD(Match match, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			TGGRule rule) {// Create CSP
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
	public CSP isApplicable_solveCsp_BWD(IsApplicableMatch isApplicableMatch, TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule,
			TGGRule rule, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Box superRuleBox) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox.label", true, csp);
		var_ruleBox_label.setValue(ruleBox.getLabel());
		var_ruleBox_label.setType("String");
		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule.name", true, csp);
		var_rule_name.setValue(rule.getName());
		var_rule_name.setType("String");
		Variable var_superRuleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("superRuleBox.label", true, csp);
		var_superRuleBox_label.setValue(superRuleBox.getLabel());
		var_superRuleBox_label.setType("String");
		Variable var_superRule_name = CSPFactoryHelper.eINSTANCE.createVariable("superRule.name", true, csp);
		var_superRule_name.setValue(superRule.getName());
		var_superRule_name.setType("String");

		// Create unbound variables

		// Create constraints
		Eq eq = new Eq();
		Eq eq_0 = new Eq();

		csp.getConstraints().add(eq);
		csp.getConstraints().add(eq_0);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_ruleBox_label, var_rule_name);
		eq_0.setRuleName("");
		eq_0.solve(var_superRuleBox_label, var_superRule_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("superRule", superRule);
		isApplicableMatch.registerObject("tripleGraphGrammar", tripleGraphGrammar);
		isApplicableMatch.registerObject("directedGraph", directedGraph);
		isApplicableMatch.registerObject("ruleBox", ruleBox);
		isApplicableMatch.registerObject("nodeToRule", nodeToRule);
		isApplicableMatch.registerObject("rule", rule);
		isApplicableMatch.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);
		isApplicableMatch.registerObject("superRuleBox", superRuleBox);
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
	public void registerObjects_BWD(PerformRuleResult ruleresult, EObject superRule, EObject tripleGraphGrammar,
			EObject directedGraph, EObject ruleBox, EObject nodeToRule, EObject complements, EObject rule,
			EObject directedGraphToTripleGraphGrammar, EObject superRuleBox) {
		ruleresult.registerObject("superRule", superRule);
		ruleresult.registerObject("tripleGraphGrammar", tripleGraphGrammar);
		ruleresult.registerObject("directedGraph", directedGraph);
		ruleresult.registerObject("ruleBox", ruleBox);
		ruleresult.registerObject("nodeToRule", nodeToRule);
		ruleresult.registerObject("complements", complements);
		ruleresult.registerObject("rule", rule);
		ruleresult.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);
		ruleresult.registerObject("superRuleBox", superRuleBox);

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
	public EObjectContainer isAppropriate_FWD_EMoflonEdge_59(EMoflonEdge _edge_edges) {
		// prepare return value
		Object[] result1_bindingAndBlack = ComplementRuleImpl
				.pattern_ComplementRule_20_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = ComplementRuleImpl.pattern_ComplementRule_20_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : ComplementRuleImpl
				.pattern_ComplementRule_20_2_testcorematchandDECs_blackFFFFB(_edge_edges)) {
			DirectedGraph directedGraph = (DirectedGraph) result2_black[0];
			Box ruleBox = (Box) result2_black[1];
			Composite complements = (Composite) result2_black[2];
			Box superRuleBox = (Box) result2_black[3];
			Object[] result2_green = ComplementRuleImpl
					.pattern_ComplementRule_20_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (ComplementRuleImpl
					.pattern_ComplementRule_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBBBB(this,
							match, directedGraph, ruleBox, complements, superRuleBox)) {
				// Ensure that the correct types of elements are matched
				if (ComplementRuleImpl
						.pattern_ComplementRule_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(this,
								match)) {

					// Add match to rule result
					Object[] result5_black = ComplementRuleImpl
							.pattern_ComplementRule_20_5_Addmatchtoruleresult_blackBBBB(match, __performOperation,
									__result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					ComplementRuleImpl.pattern_ComplementRule_20_5_Addmatchtoruleresult_greenBBBB(match,
							__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return ComplementRuleImpl.pattern_ComplementRule_20_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_BWD_EMoflonEdge_84(EMoflonEdge _edge_kernel) {
		// prepare return value
		Object[] result1_bindingAndBlack = ComplementRuleImpl
				.pattern_ComplementRule_21_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = ComplementRuleImpl.pattern_ComplementRule_21_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : ComplementRuleImpl
				.pattern_ComplementRule_21_2_testcorematchandDECs_blackFFFB(_edge_kernel)) {
			TGGRule superRule = (TGGRule) result2_black[0];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_black[1];
			TGGRule rule = (TGGRule) result2_black[2];
			Object[] result2_green = ComplementRuleImpl
					.pattern_ComplementRule_21_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (ComplementRuleImpl
					.pattern_ComplementRule_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBBB(this, match,
							superRule, tripleGraphGrammar, rule)) {
				// Ensure that the correct types of elements are matched
				if (ComplementRuleImpl
						.pattern_ComplementRule_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(this,
								match)) {

					// Add match to rule result
					Object[] result5_black = ComplementRuleImpl
							.pattern_ComplementRule_21_5_Addmatchtoruleresult_blackBBBB(match, __performOperation,
									__result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					ComplementRuleImpl.pattern_ComplementRule_21_5_Addmatchtoruleresult_greenBBBB(match,
							__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return ComplementRuleImpl.pattern_ComplementRule_21_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintsRuleResult checkAttributes_FWD(TripleMatch __tripleMatch) {
		AttributeConstraintsRuleResult ruleResult = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE
				.createAttributeConstraintsRuleResult();
		ruleResult.setRule("ComplementRule");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		if (!__helper.hasExpectedValue("complements", "label", "0..*", ComparingOperator.EQUAL)) {
			ruleResult.setSuccess(false);
			return ruleResult;
		}

		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule", true, csp);
		var_rule_name.setValue(__helper.getValue("rule", "name"));
		var_rule_name.setType("String");

		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox", true, csp);
		var_ruleBox_label.setValue(__helper.getValue("ruleBox", "label"));
		var_ruleBox_label.setType("String");

		Variable var_superRuleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("superRuleBox", true, csp);
		var_superRuleBox_label.setValue(__helper.getValue("superRuleBox", "label"));
		var_superRuleBox_label.setType("String");

		Variable var_superRule_name = CSPFactoryHelper.eINSTANCE.createVariable("superRule", true, csp);
		var_superRule_name.setValue(__helper.getValue("superRule", "name"));
		var_superRule_name.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		Eq eq1 = new Eq();
		csp.getConstraints().add(eq1);

		eq0.setRuleName("ComplementRule");
		eq0.solve(var_ruleBox_label, var_rule_name);

		eq1.setRuleName("ComplementRule");
		eq1.solve(var_superRuleBox_label, var_superRule_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			eq0.solve(var_ruleBox_label, var_rule_name);
			eq1.solve(var_superRuleBox_label, var_superRule_name);
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
		ruleResult.setRule("ComplementRule");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		if (!__helper.hasExpectedValue("complements", "label", "0..*", ComparingOperator.EQUAL)) {
			ruleResult.setSuccess(false);
			return ruleResult;
		}

		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule", true, csp);
		var_rule_name.setValue(__helper.getValue("rule", "name"));
		var_rule_name.setType("String");

		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox", true, csp);
		var_ruleBox_label.setValue(__helper.getValue("ruleBox", "label"));
		var_ruleBox_label.setType("String");

		Variable var_superRuleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("superRuleBox", true, csp);
		var_superRuleBox_label.setValue(__helper.getValue("superRuleBox", "label"));
		var_superRuleBox_label.setType("String");

		Variable var_superRule_name = CSPFactoryHelper.eINSTANCE.createVariable("superRule", true, csp);
		var_superRule_name.setValue(__helper.getValue("superRule", "name"));
		var_superRule_name.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		Eq eq1 = new Eq();
		csp.getConstraints().add(eq1);

		eq0.setRuleName("ComplementRule");
		eq0.solve(var_ruleBox_label, var_rule_name);

		eq1.setRuleName("ComplementRule");
		eq1.solve(var_superRuleBox_label, var_superRule_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			eq0.solve(var_ruleBox_label, var_rule_name);
			eq1.solve(var_superRuleBox_label, var_superRule_name);
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
		Object[] result1_black = ComplementRuleImpl.pattern_ComplementRule_24_1_prepare_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [prepare] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = ComplementRuleImpl.pattern_ComplementRule_24_1_prepare_greenF();
		IsApplicableRuleResult result = (IsApplicableRuleResult) result1_green[0];

		// match src trg context
		Object[] result2_bindingAndBlack = ComplementRuleImpl
				.pattern_ComplementRule_24_2_matchsrctrgcontext_bindingAndBlackFFFFFFFBB(sourceMatch, targetMatch);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [match src trg context] failed." + " Variables: "
					+ "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = " + targetMatch + ".");
		}
		TGGRule superRule = (TGGRule) result2_bindingAndBlack[0];
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_bindingAndBlack[1];
		DirectedGraph directedGraph = (DirectedGraph) result2_bindingAndBlack[2];
		Box ruleBox = (Box) result2_bindingAndBlack[3];
		Composite complements = (Composite) result2_bindingAndBlack[4];
		TGGRule rule = (TGGRule) result2_bindingAndBlack[5];
		Box superRuleBox = (Box) result2_bindingAndBlack[6];

		// solve csp
		Object[] result3_bindingAndBlack = ComplementRuleImpl
				.pattern_ComplementRule_24_3_solvecsp_bindingAndBlackFBBBBBBBBBB(this, superRule, tripleGraphGrammar,
						directedGraph, ruleBox, complements, rule, superRuleBox, sourceMatch, targetMatch);
		if (result3_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [solve csp] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[superRule] = " + superRule + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar
					+ ", " + "[directedGraph] = " + directedGraph + ", " + "[ruleBox] = " + ruleBox + ", "
					+ "[complements] = " + complements + ", " + "[rule] = " + rule + ", " + "[superRuleBox] = "
					+ superRuleBox + ", " + "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = " + targetMatch
					+ ".");
		}
		CSP csp = (CSP) result3_bindingAndBlack[0];
		// check CSP
		if (ComplementRuleImpl.pattern_ComplementRule_24_4_checkCSP_expressionFB(csp)) {
			// ForEach match corr context
			for (Object[] result5_black : ComplementRuleImpl.pattern_ComplementRule_24_5_matchcorrcontext_blackBBBFBFBB(
					tripleGraphGrammar, directedGraph, ruleBox, rule, sourceMatch, targetMatch)) {
				NodeToRule nodeToRule = (NodeToRule) result5_black[3];
				DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result5_black[5];
				Object[] result5_green = ComplementRuleImpl.pattern_ComplementRule_24_5_matchcorrcontext_greenBBBBF(
						nodeToRule, directedGraphToTripleGraphGrammar, sourceMatch, targetMatch);
				CCMatch ccMatch = (CCMatch) result5_green[4];

				// create correspondence
				Object[] result6_black = ComplementRuleImpl
						.pattern_ComplementRule_24_6_createcorrespondence_blackBBBBBBBB(superRule, tripleGraphGrammar,
								directedGraph, ruleBox, complements, rule, superRuleBox, ccMatch);
				if (result6_black == null) {
					throw new RuntimeException("Pattern matching in node [create correspondence] failed."
							+ " Variables: " + "[superRule] = " + superRule + ", " + "[tripleGraphGrammar] = "
							+ tripleGraphGrammar + ", " + "[directedGraph] = " + directedGraph + ", " + "[ruleBox] = "
							+ ruleBox + ", " + "[complements] = " + complements + ", " + "[rule] = " + rule + ", "
							+ "[superRuleBox] = " + superRuleBox + ", " + "[ccMatch] = " + ccMatch + ".");
				}

				// add to returned result
				Object[] result7_black = ComplementRuleImpl
						.pattern_ComplementRule_24_7_addtoreturnedresult_blackBB(result, ccMatch);
				if (result7_black == null) {
					throw new RuntimeException("Pattern matching in node [add to returned result] failed."
							+ " Variables: " + "[result] = " + result + ", " + "[ccMatch] = " + ccMatch + ".");
				}
				ComplementRuleImpl.pattern_ComplementRule_24_7_addtoreturnedresult_greenBB(result, ccMatch);

			}

		} else {
		}
		return ComplementRuleImpl.pattern_ComplementRule_24_8_expressionFB(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isApplicable_solveCsp_CC(TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, Box ruleBox, Composite complements, TGGRule rule, Box superRuleBox,
			Match sourceMatch, Match targetMatch) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();

		// Create literals

		// Create attribute variables
		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox.label", true, csp);
		var_ruleBox_label.setValue(ruleBox.getLabel());
		var_ruleBox_label.setType("String");
		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule.name", true, csp);
		var_rule_name.setValue(rule.getName());
		var_rule_name.setType("String");
		Variable var_superRuleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("superRuleBox.label", true, csp);
		var_superRuleBox_label.setValue(superRuleBox.getLabel());
		var_superRuleBox_label.setType("String");
		Variable var_superRule_name = CSPFactoryHelper.eINSTANCE.createVariable("superRule.name", true, csp);
		var_superRule_name.setValue(superRule.getName());
		var_superRule_name.setType("String");

		// Create unbound variables

		// Create constraints
		Eq eq = new Eq();
		Eq eq_0 = new Eq();

		csp.getConstraints().add(eq);
		csp.getConstraints().add(eq_0);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_ruleBox_label, var_rule_name);
		eq_0.setRuleName("");
		eq_0.solve(var_superRuleBox_label, var_superRule_name);
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
	public boolean checkDEC_FWD(DirectedGraph directedGraph, Box ruleBox, Composite complements, Box superRuleBox) {// match tgg pattern
		Object[] result1_black = ComplementRuleImpl.pattern_ComplementRule_27_1_matchtggpattern_blackBBBB(directedGraph,
				ruleBox, complements, superRuleBox);
		if (result1_black != null) {
			ComplementRuleImpl.pattern_ComplementRule_27_1_matchtggpattern_greenB(complements);

			return ComplementRuleImpl.pattern_ComplementRule_27_2_expressionF();
		} else {
			return ComplementRuleImpl.pattern_ComplementRule_27_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDEC_BWD(TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, TGGRule rule) {// match tgg pattern
		Object[] result1_black = ComplementRuleImpl.pattern_ComplementRule_28_1_matchtggpattern_blackBBB(superRule,
				tripleGraphGrammar, rule);
		if (result1_black != null) {
			return ComplementRuleImpl.pattern_ComplementRule_28_2_expressionF();
		} else {
			return ComplementRuleImpl.pattern_ComplementRule_28_3_expressionF();
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
		Object[] result1_black = ComplementRuleImpl.pattern_ComplementRule_29_1_createresult_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [create result] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = ComplementRuleImpl.pattern_ComplementRule_29_1_createresult_greenFF();
		IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result1_green[0];
		ModelgeneratorRuleResult ruleResult = (ModelgeneratorRuleResult) result1_green[1];

		// ForEach is applicable core
		for (Object[] result2_black : ComplementRuleImpl
				.pattern_ComplementRule_29_2_isapplicablecore_blackFFFFFFFFFBB(ruleEntryContainer, ruleResult)) {
			// RuleEntryList nodeToRuleList = (RuleEntryList) result2_black[0];
			TGGRule superRule = (TGGRule) result2_black[1];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_black[2];
			TGGRule rule = (TGGRule) result2_black[3];
			NodeToRule nodeToRule = (NodeToRule) result2_black[4];
			Box ruleBox = (Box) result2_black[5];
			DirectedGraph directedGraph = (DirectedGraph) result2_black[6];
			Box superRuleBox = (Box) result2_black[7];
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result2_black[8];

			// solve CSP
			Object[] result3_bindingAndBlack = ComplementRuleImpl
					.pattern_ComplementRule_29_3_solveCSP_bindingAndBlackFBBBBBBBBBBB(this, isApplicableMatch,
							superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
							directedGraphToTripleGraphGrammar, superRuleBox, ruleResult);
			if (result3_bindingAndBlack == null) {
				throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: " + "[this] = "
						+ this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", " + "[superRule] = "
						+ superRule + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ", "
						+ "[directedGraph] = " + directedGraph + ", " + "[ruleBox] = " + ruleBox + ", "
						+ "[nodeToRule] = " + nodeToRule + ", " + "[rule] = " + rule + ", "
						+ "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ", "
						+ "[superRuleBox] = " + superRuleBox + ", " + "[ruleResult] = " + ruleResult + ".");
			}
			CSP csp = (CSP) result3_bindingAndBlack[0];
			// check CSP
			if (ComplementRuleImpl.pattern_ComplementRule_29_4_checkCSP_expressionFBB(this, csp)) {
				// check nacs
				Object[] result5_black = ComplementRuleImpl.pattern_ComplementRule_29_5_checknacs_blackBBBBBBBB(
						superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
						directedGraphToTripleGraphGrammar, superRuleBox);
				if (result5_black != null) {

					// perform
					Object[] result6_black = ComplementRuleImpl.pattern_ComplementRule_29_6_perform_blackBBBBBBBBB(
							superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
							directedGraphToTripleGraphGrammar, superRuleBox, ruleResult);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [perform] failed." + " Variables: "
								+ "[superRule] = " + superRule + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar
								+ ", " + "[directedGraph] = " + directedGraph + ", " + "[ruleBox] = " + ruleBox + ", "
								+ "[nodeToRule] = " + nodeToRule + ", " + "[rule] = " + rule + ", "
								+ "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ", "
								+ "[superRuleBox] = " + superRuleBox + ", " + "[ruleResult] = " + ruleResult + ".");
					}
					ComplementRuleImpl.pattern_ComplementRule_29_6_perform_greenBBBFBBB(superRule, directedGraph,
							ruleBox, rule, superRuleBox, ruleResult);
					// Composite complements = (Composite) result6_green[3];

				} else {
				}

			} else {
			}

		}
		return ComplementRuleImpl.pattern_ComplementRule_29_7_expressionFB(ruleResult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP generateModel_solveCsp_BWD(IsApplicableMatch isApplicableMatch, TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule,
			TGGRule rule, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Box superRuleBox,
			ModelgeneratorRuleResult ruleResult) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox.label", true, csp);
		var_ruleBox_label.setValue(ruleBox.getLabel());
		var_ruleBox_label.setType("String");
		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule.name", true, csp);
		var_rule_name.setValue(rule.getName());
		var_rule_name.setType("String");
		Variable var_superRuleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("superRuleBox.label", true, csp);
		var_superRuleBox_label.setValue(superRuleBox.getLabel());
		var_superRuleBox_label.setType("String");
		Variable var_superRule_name = CSPFactoryHelper.eINSTANCE.createVariable("superRule.name", true, csp);
		var_superRule_name.setValue(superRule.getName());
		var_superRule_name.setType("String");

		// Create unbound variables

		// Create constraints
		Eq eq = new Eq();
		Eq eq_0 = new Eq();

		csp.getConstraints().add(eq);
		csp.getConstraints().add(eq_0);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_ruleBox_label, var_rule_name);
		eq_0.setRuleName("");
		eq_0.solve(var_superRuleBox_label, var_superRule_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("superRule", superRule);
		isApplicableMatch.registerObject("tripleGraphGrammar", tripleGraphGrammar);
		isApplicableMatch.registerObject("directedGraph", directedGraph);
		isApplicableMatch.registerObject("ruleBox", ruleBox);
		isApplicableMatch.registerObject("nodeToRule", nodeToRule);
		isApplicableMatch.registerObject("rule", rule);
		isApplicableMatch.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);
		isApplicableMatch.registerObject("superRuleBox", superRuleBox);
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
		case RulesPackage.COMPLEMENT_RULE___IS_APPROPRIATE_FWD__MATCH_DIRECTEDGRAPH_BOX_COMPOSITE_BOX:
			return isAppropriate_FWD((Match) arguments.get(0), (DirectedGraph) arguments.get(1), (Box) arguments.get(2),
					(Composite) arguments.get(3), (Box) arguments.get(4));
		case RulesPackage.COMPLEMENT_RULE___PERFORM_FWD__ISAPPLICABLEMATCH:
			return perform_FWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___IS_APPLICABLE_FWD__MATCH:
			return isApplicable_FWD((Match) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_DIRECTEDGRAPH_BOX_COMPOSITE_BOX:
			registerObjectsToMatch_FWD((Match) arguments.get(0), (DirectedGraph) arguments.get(1),
					(Box) arguments.get(2), (Composite) arguments.get(3), (Box) arguments.get(4));
			return null;
		case RulesPackage.COMPLEMENT_RULE___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_DIRECTEDGRAPH_BOX_COMPOSITE_BOX:
			return isAppropriate_solveCsp_FWD((Match) arguments.get(0), (DirectedGraph) arguments.get(1),
					(Box) arguments.get(2), (Composite) arguments.get(3), (Box) arguments.get(4));
		case RulesPackage.COMPLEMENT_RULE___IS_APPROPRIATE_CHECK_CSP_FWD__CSP:
			return isAppropriate_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_NODETORULE_COMPOSITE_TGGRULE_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_BOX:
			return isApplicable_solveCsp_FWD((IsApplicableMatch) arguments.get(0), (TGGRule) arguments.get(1),
					(TripleGraphGrammar) arguments.get(2), (DirectedGraph) arguments.get(3), (Box) arguments.get(4),
					(NodeToRule) arguments.get(5), (Composite) arguments.get(6), (TGGRule) arguments.get(7),
					(DirectedGraphToTripleGraphGrammar) arguments.get(8), (Box) arguments.get(9));
		case RulesPackage.COMPLEMENT_RULE___IS_APPLICABLE_CHECK_CSP_FWD__CSP:
			return isApplicable_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_FWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6), (EObject) arguments.get(7),
					(EObject) arguments.get(8), (EObject) arguments.get(9));
			return null;
		case RulesPackage.COMPLEMENT_RULE___CHECK_TYPES_FWD__MATCH:
			return checkTypes_FWD((Match) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___IS_APPROPRIATE_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE:
			return isAppropriate_BWD((Match) arguments.get(0), (TGGRule) arguments.get(1),
					(TripleGraphGrammar) arguments.get(2), (TGGRule) arguments.get(3));
		case RulesPackage.COMPLEMENT_RULE___PERFORM_BWD__ISAPPLICABLEMATCH:
			return perform_BWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___IS_APPLICABLE_BWD__MATCH:
			return isApplicable_BWD((Match) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE:
			registerObjectsToMatch_BWD((Match) arguments.get(0), (TGGRule) arguments.get(1),
					(TripleGraphGrammar) arguments.get(2), (TGGRule) arguments.get(3));
			return null;
		case RulesPackage.COMPLEMENT_RULE___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE:
			return isAppropriate_solveCsp_BWD((Match) arguments.get(0), (TGGRule) arguments.get(1),
					(TripleGraphGrammar) arguments.get(2), (TGGRule) arguments.get(3));
		case RulesPackage.COMPLEMENT_RULE___IS_APPROPRIATE_CHECK_CSP_BWD__CSP:
			return isAppropriate_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_NODETORULE_TGGRULE_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_BOX:
			return isApplicable_solveCsp_BWD((IsApplicableMatch) arguments.get(0), (TGGRule) arguments.get(1),
					(TripleGraphGrammar) arguments.get(2), (DirectedGraph) arguments.get(3), (Box) arguments.get(4),
					(NodeToRule) arguments.get(5), (TGGRule) arguments.get(6),
					(DirectedGraphToTripleGraphGrammar) arguments.get(7), (Box) arguments.get(8));
		case RulesPackage.COMPLEMENT_RULE___IS_APPLICABLE_CHECK_CSP_BWD__CSP:
			return isApplicable_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_BWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6), (EObject) arguments.get(7),
					(EObject) arguments.get(8), (EObject) arguments.get(9));
			return null;
		case RulesPackage.COMPLEMENT_RULE___CHECK_TYPES_BWD__MATCH:
			return checkTypes_BWD((Match) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___IS_APPROPRIATE_FWD_EMOFLON_EDGE_59__EMOFLONEDGE:
			return isAppropriate_FWD_EMoflonEdge_59((EMoflonEdge) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___IS_APPROPRIATE_BWD_EMOFLON_EDGE_84__EMOFLONEDGE:
			return isAppropriate_BWD_EMoflonEdge_84((EMoflonEdge) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH:
			return checkAttributes_FWD((TripleMatch) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH:
			return checkAttributes_BWD((TripleMatch) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___IS_APPLICABLE_CC__MATCH_MATCH:
			return isApplicable_CC((Match) arguments.get(0), (Match) arguments.get(1));
		case RulesPackage.COMPLEMENT_RULE___IS_APPLICABLE_SOLVE_CSP_CC__TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_COMPOSITE_TGGRULE_BOX_MATCH_MATCH:
			return isApplicable_solveCsp_CC((TGGRule) arguments.get(0), (TripleGraphGrammar) arguments.get(1),
					(DirectedGraph) arguments.get(2), (Box) arguments.get(3), (Composite) arguments.get(4),
					(TGGRule) arguments.get(5), (Box) arguments.get(6), (Match) arguments.get(7),
					(Match) arguments.get(8));
		case RulesPackage.COMPLEMENT_RULE___IS_APPLICABLE_CHECK_CSP_CC__CSP:
			return isApplicable_checkCsp_CC((CSP) arguments.get(0));
		case RulesPackage.COMPLEMENT_RULE___CHECK_DEC_FWD__DIRECTEDGRAPH_BOX_COMPOSITE_BOX:
			return checkDEC_FWD((DirectedGraph) arguments.get(0), (Box) arguments.get(1), (Composite) arguments.get(2),
					(Box) arguments.get(3));
		case RulesPackage.COMPLEMENT_RULE___CHECK_DEC_BWD__TGGRULE_TRIPLEGRAPHGRAMMAR_TGGRULE:
			return checkDEC_BWD((TGGRule) arguments.get(0), (TripleGraphGrammar) arguments.get(1),
					(TGGRule) arguments.get(2));
		case RulesPackage.COMPLEMENT_RULE___GENERATE_MODEL__RULEENTRYCONTAINER_NODETORULE:
			return generateModel((RuleEntryContainer) arguments.get(0), (NodeToRule) arguments.get(1));
		case RulesPackage.COMPLEMENT_RULE___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_TGGRULE_TRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_NODETORULE_TGGRULE_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_BOX_MODELGENERATORRULERESULT:
			return generateModel_solveCsp_BWD((IsApplicableMatch) arguments.get(0), (TGGRule) arguments.get(1),
					(TripleGraphGrammar) arguments.get(2), (DirectedGraph) arguments.get(3), (Box) arguments.get(4),
					(NodeToRule) arguments.get(5), (TGGRule) arguments.get(6),
					(DirectedGraphToTripleGraphGrammar) arguments.get(7), (Box) arguments.get(8),
					(ModelgeneratorRuleResult) arguments.get(9));
		case RulesPackage.COMPLEMENT_RULE___GENERATE_MODEL_CHECK_CSP_BWD__CSP:
			return generateModel_checkCsp_BWD((CSP) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	public static final Object[] pattern_ComplementRule_0_1_initialbindings_blackBBBBBB(ComplementRule _this,
			Match match, DirectedGraph directedGraph, Box ruleBox, Composite complements, Box superRuleBox) {
		if (!ruleBox.equals(superRuleBox)) {
			return new Object[] { _this, match, directedGraph, ruleBox, complements, superRuleBox };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_0_2_SolveCSP_bindingFBBBBBB(ComplementRule _this, Match match,
			DirectedGraph directedGraph, Box ruleBox, Composite complements, Box superRuleBox) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_FWD(match, directedGraph, ruleBox, complements,
				superRuleBox);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, directedGraph, ruleBox, complements, superRuleBox };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_0_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_ComplementRule_0_2_SolveCSP_bindingAndBlackFBBBBBB(ComplementRule _this,
			Match match, DirectedGraph directedGraph, Box ruleBox, Composite complements, Box superRuleBox) {
		Object[] result_pattern_ComplementRule_0_2_SolveCSP_binding = pattern_ComplementRule_0_2_SolveCSP_bindingFBBBBBB(
				_this, match, directedGraph, ruleBox, complements, superRuleBox);
		if (result_pattern_ComplementRule_0_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_ComplementRule_0_2_SolveCSP_binding[0];

			Object[] result_pattern_ComplementRule_0_2_SolveCSP_black = pattern_ComplementRule_0_2_SolveCSP_blackB(csp);
			if (result_pattern_ComplementRule_0_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, directedGraph, ruleBox, complements, superRuleBox };
			}
		}
		return null;
	}

	public static final boolean pattern_ComplementRule_0_3_CheckCSP_expressionFBB(ComplementRule _this, CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_ComplementRule_0_4_collectelementstobetranslated_blackBBBBB(Match match,
			DirectedGraph directedGraph, Box ruleBox, Composite complements, Box superRuleBox) {
		if (!ruleBox.equals(superRuleBox)) {
			return new Object[] { match, directedGraph, ruleBox, complements, superRuleBox };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_0_4_collectelementstobetranslated_greenBBBBBFFFF(Match match,
			DirectedGraph directedGraph, Box ruleBox, Composite complements, Box superRuleBox) {
		EMoflonEdge directedGraph__complements____edges = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge complements__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge complements__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge complements__superRuleBox____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getToBeTranslatedNodes().add(complements);
		String directedGraph__complements____edges_name_prime = "edges";
		String complements__directedGraph____graph_name_prime = "graph";
		String complements__ruleBox____source_name_prime = "source";
		String complements__superRuleBox____target_name_prime = "target";
		directedGraph__complements____edges.setSrc(directedGraph);
		directedGraph__complements____edges.setTrg(complements);
		match.getToBeTranslatedEdges().add(directedGraph__complements____edges);
		complements__directedGraph____graph.setSrc(complements);
		complements__directedGraph____graph.setTrg(directedGraph);
		match.getToBeTranslatedEdges().add(complements__directedGraph____graph);
		complements__ruleBox____source.setSrc(complements);
		complements__ruleBox____source.setTrg(ruleBox);
		match.getToBeTranslatedEdges().add(complements__ruleBox____source);
		complements__superRuleBox____target.setSrc(complements);
		complements__superRuleBox____target.setTrg(superRuleBox);
		match.getToBeTranslatedEdges().add(complements__superRuleBox____target);
		directedGraph__complements____edges.setName(directedGraph__complements____edges_name_prime);
		complements__directedGraph____graph.setName(complements__directedGraph____graph_name_prime);
		complements__ruleBox____source.setName(complements__ruleBox____source_name_prime);
		complements__superRuleBox____target.setName(complements__superRuleBox____target_name_prime);
		return new Object[] { match, directedGraph, ruleBox, complements, superRuleBox,
				directedGraph__complements____edges, complements__directedGraph____graph,
				complements__ruleBox____source, complements__superRuleBox____target };
	}

	public static final Object[] pattern_ComplementRule_0_5_collectcontextelements_blackBBBBB(Match match,
			DirectedGraph directedGraph, Box ruleBox, Composite complements, Box superRuleBox) {
		if (!ruleBox.equals(superRuleBox)) {
			return new Object[] { match, directedGraph, ruleBox, complements, superRuleBox };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_0_5_collectcontextelements_greenBBBBFFFF(Match match,
			DirectedGraph directedGraph, Box ruleBox, Box superRuleBox) {
		EMoflonEdge directedGraph__ruleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ruleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__superRuleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge superRuleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getContextNodes().add(directedGraph);
		match.getContextNodes().add(ruleBox);
		match.getContextNodes().add(superRuleBox);
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
		return new Object[] { match, directedGraph, ruleBox, superRuleBox, directedGraph__ruleBox____nodes,
				ruleBox__directedGraph____graph, directedGraph__superRuleBox____nodes,
				superRuleBox__directedGraph____graph };
	}

	public static final void pattern_ComplementRule_0_6_registerobjectstomatch_expressionBBBBBB(ComplementRule _this,
			Match match, DirectedGraph directedGraph, Box ruleBox, Composite complements, Box superRuleBox) {
		_this.registerObjectsToMatch_FWD(match, directedGraph, ruleBox, complements, superRuleBox);

	}

	public static final boolean pattern_ComplementRule_0_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_ComplementRule_0_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_ComplementRule_1_1_performtransformation_bindingFFFFFFFFFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("superRule");
		EObject _localVariable_1 = isApplicableMatch.getObject("tripleGraphGrammar");
		EObject _localVariable_2 = isApplicableMatch.getObject("directedGraph");
		EObject _localVariable_3 = isApplicableMatch.getObject("ruleBox");
		EObject _localVariable_4 = isApplicableMatch.getObject("nodeToRule");
		EObject _localVariable_5 = isApplicableMatch.getObject("complements");
		EObject _localVariable_6 = isApplicableMatch.getObject("rule");
		EObject _localVariable_7 = isApplicableMatch.getObject("directedGraphToTripleGraphGrammar");
		EObject _localVariable_8 = isApplicableMatch.getObject("superRuleBox");
		EObject tmpSuperRule = _localVariable_0;
		EObject tmpTripleGraphGrammar = _localVariable_1;
		EObject tmpDirectedGraph = _localVariable_2;
		EObject tmpRuleBox = _localVariable_3;
		EObject tmpNodeToRule = _localVariable_4;
		EObject tmpComplements = _localVariable_5;
		EObject tmpRule = _localVariable_6;
		EObject tmpDirectedGraphToTripleGraphGrammar = _localVariable_7;
		EObject tmpSuperRuleBox = _localVariable_8;
		if (tmpSuperRule instanceof TGGRule) {
			TGGRule superRule = (TGGRule) tmpSuperRule;
			if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
				TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
				if (tmpDirectedGraph instanceof DirectedGraph) {
					DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
					if (tmpRuleBox instanceof Box) {
						Box ruleBox = (Box) tmpRuleBox;
						if (tmpNodeToRule instanceof NodeToRule) {
							NodeToRule nodeToRule = (NodeToRule) tmpNodeToRule;
							if (tmpComplements instanceof Composite) {
								Composite complements = (Composite) tmpComplements;
								if (tmpRule instanceof TGGRule) {
									TGGRule rule = (TGGRule) tmpRule;
									if (tmpDirectedGraphToTripleGraphGrammar instanceof DirectedGraphToTripleGraphGrammar) {
										DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) tmpDirectedGraphToTripleGraphGrammar;
										if (tmpSuperRuleBox instanceof Box) {
											Box superRuleBox = (Box) tmpSuperRuleBox;
											return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox,
													nodeToRule, complements, rule, directedGraphToTripleGraphGrammar,
													superRuleBox, isApplicableMatch };
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

	public static final Object[] pattern_ComplementRule_1_1_performtransformation_blackBBBBBBBBBFBB(TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule,
			Composite complements, TGGRule rule, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar,
			Box superRuleBox, ComplementRule _this, IsApplicableMatch isApplicableMatch) {
		if (!ruleBox.equals(superRuleBox)) {
			if (!rule.equals(superRule)) {
				for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
					if (tmpCsp instanceof CSP) {
						CSP csp = (CSP) tmpCsp;
						return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule,
								complements, rule, directedGraphToTripleGraphGrammar, superRuleBox, csp, _this,
								isApplicableMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_1_1_performtransformation_bindingAndBlackFFFFFFFFFFBB(
			ComplementRule _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_ComplementRule_1_1_performtransformation_binding = pattern_ComplementRule_1_1_performtransformation_bindingFFFFFFFFFB(
				isApplicableMatch);
		if (result_pattern_ComplementRule_1_1_performtransformation_binding != null) {
			TGGRule superRule = (TGGRule) result_pattern_ComplementRule_1_1_performtransformation_binding[0];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result_pattern_ComplementRule_1_1_performtransformation_binding[1];
			DirectedGraph directedGraph = (DirectedGraph) result_pattern_ComplementRule_1_1_performtransformation_binding[2];
			Box ruleBox = (Box) result_pattern_ComplementRule_1_1_performtransformation_binding[3];
			NodeToRule nodeToRule = (NodeToRule) result_pattern_ComplementRule_1_1_performtransformation_binding[4];
			Composite complements = (Composite) result_pattern_ComplementRule_1_1_performtransformation_binding[5];
			TGGRule rule = (TGGRule) result_pattern_ComplementRule_1_1_performtransformation_binding[6];
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result_pattern_ComplementRule_1_1_performtransformation_binding[7];
			Box superRuleBox = (Box) result_pattern_ComplementRule_1_1_performtransformation_binding[8];

			Object[] result_pattern_ComplementRule_1_1_performtransformation_black = pattern_ComplementRule_1_1_performtransformation_blackBBBBBBBBBFBB(
					superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, complements, rule,
					directedGraphToTripleGraphGrammar, superRuleBox, _this, isApplicableMatch);
			if (result_pattern_ComplementRule_1_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_ComplementRule_1_1_performtransformation_black[9];

				return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, complements,
						rule, directedGraphToTripleGraphGrammar, superRuleBox, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_1_1_performtransformation_greenBB(TGGRule superRule,
			TGGRule rule) {
		rule.setKernel(superRule);
		return new Object[] { superRule, rule };
	}

	public static final Object[] pattern_ComplementRule_1_2_collecttranslatedelements_blackB(Composite complements) {
		return new Object[] { complements };
	}

	public static final Object[] pattern_ComplementRule_1_2_collecttranslatedelements_greenFB(Composite complements) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getTranslatedElements().add(complements);
		return new Object[] { ruleresult, complements };
	}

	public static final Object[] pattern_ComplementRule_1_3_bookkeepingforedges_blackBBBBBBBBBB(
			PerformRuleResult ruleresult, EObject superRule, EObject tripleGraphGrammar, EObject directedGraph,
			EObject ruleBox, EObject nodeToRule, EObject complements, EObject rule,
			EObject directedGraphToTripleGraphGrammar, EObject superRuleBox) {
		if (!superRule.equals(tripleGraphGrammar)) {
			if (!superRule.equals(superRuleBox)) {
				if (!directedGraph.equals(superRule)) {
					if (!directedGraph.equals(tripleGraphGrammar)) {
						if (!directedGraph.equals(ruleBox)) {
							if (!directedGraph.equals(nodeToRule)) {
								if (!directedGraph.equals(rule)) {
									if (!directedGraph.equals(directedGraphToTripleGraphGrammar)) {
										if (!directedGraph.equals(superRuleBox)) {
											if (!ruleBox.equals(superRule)) {
												if (!ruleBox.equals(tripleGraphGrammar)) {
													if (!ruleBox.equals(superRuleBox)) {
														if (!nodeToRule.equals(superRule)) {
															if (!nodeToRule.equals(tripleGraphGrammar)) {
																if (!nodeToRule.equals(ruleBox)) {
																	if (!nodeToRule.equals(rule)) {
																		if (!nodeToRule.equals(superRuleBox)) {
																			if (!complements.equals(superRule)) {
																				if (!complements
																						.equals(tripleGraphGrammar)) {
																					if (!complements
																							.equals(directedGraph)) {
																						if (!complements
																								.equals(ruleBox)) {
																							if (!complements.equals(
																									nodeToRule)) {
																								if (!complements
																										.equals(rule)) {
																									if (!complements
																											.equals(directedGraphToTripleGraphGrammar)) {
																										if (!complements
																												.equals(superRuleBox)) {
																											if (!rule
																													.equals(superRule)) {
																												if (!rule
																														.equals(tripleGraphGrammar)) {
																													if (!rule
																															.equals(ruleBox)) {
																														if (!rule
																																.equals(superRuleBox)) {
																															if (!directedGraphToTripleGraphGrammar
																																	.equals(superRule)) {
																																if (!directedGraphToTripleGraphGrammar
																																		.equals(tripleGraphGrammar)) {
																																	if (!directedGraphToTripleGraphGrammar
																																			.equals(ruleBox)) {
																																		if (!directedGraphToTripleGraphGrammar
																																				.equals(nodeToRule)) {
																																			if (!directedGraphToTripleGraphGrammar
																																					.equals(rule)) {
																																				if (!directedGraphToTripleGraphGrammar
																																						.equals(superRuleBox)) {
																																					if (!superRuleBox
																																							.equals(tripleGraphGrammar)) {
																																						return new Object[] {
																																								ruleresult,
																																								superRule,
																																								tripleGraphGrammar,
																																								directedGraph,
																																								ruleBox,
																																								nodeToRule,
																																								complements,
																																								rule,
																																								directedGraphToTripleGraphGrammar,
																																								superRuleBox };
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

	public static final Object[] pattern_ComplementRule_1_3_bookkeepingforedges_greenBBBBBBBFFFFF(
			PerformRuleResult ruleresult, EObject superRule, EObject directedGraph, EObject ruleBox,
			EObject complements, EObject rule, EObject superRuleBox) {
		EMoflonEdge directedGraph__complements____edges = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge complements__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge complements__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__superRule____kernel = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge complements__superRuleBox____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "ComplementRule";
		String directedGraph__complements____edges_name_prime = "edges";
		String complements__directedGraph____graph_name_prime = "graph";
		String complements__ruleBox____source_name_prime = "source";
		String rule__superRule____kernel_name_prime = "kernel";
		String complements__superRuleBox____target_name_prime = "target";
		directedGraph__complements____edges.setSrc(directedGraph);
		directedGraph__complements____edges.setTrg(complements);
		ruleresult.getTranslatedEdges().add(directedGraph__complements____edges);
		complements__directedGraph____graph.setSrc(complements);
		complements__directedGraph____graph.setTrg(directedGraph);
		ruleresult.getTranslatedEdges().add(complements__directedGraph____graph);
		complements__ruleBox____source.setSrc(complements);
		complements__ruleBox____source.setTrg(ruleBox);
		ruleresult.getTranslatedEdges().add(complements__ruleBox____source);
		rule__superRule____kernel.setSrc(rule);
		rule__superRule____kernel.setTrg(superRule);
		ruleresult.getCreatedEdges().add(rule__superRule____kernel);
		complements__superRuleBox____target.setSrc(complements);
		complements__superRuleBox____target.setTrg(superRuleBox);
		ruleresult.getTranslatedEdges().add(complements__superRuleBox____target);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		directedGraph__complements____edges.setName(directedGraph__complements____edges_name_prime);
		complements__directedGraph____graph.setName(complements__directedGraph____graph_name_prime);
		complements__ruleBox____source.setName(complements__ruleBox____source_name_prime);
		rule__superRule____kernel.setName(rule__superRule____kernel_name_prime);
		complements__superRuleBox____target.setName(complements__superRuleBox____target_name_prime);
		return new Object[] { ruleresult, superRule, directedGraph, ruleBox, complements, rule, superRuleBox,
				directedGraph__complements____edges, complements__directedGraph____graph,
				complements__ruleBox____source, rule__superRule____kernel, complements__superRuleBox____target };
	}

	public static final void pattern_ComplementRule_1_5_registerobjects_expressionBBBBBBBBBBB(ComplementRule _this,
			PerformRuleResult ruleresult, EObject superRule, EObject tripleGraphGrammar, EObject directedGraph,
			EObject ruleBox, EObject nodeToRule, EObject complements, EObject rule,
			EObject directedGraphToTripleGraphGrammar, EObject superRuleBox) {
		_this.registerObjects_FWD(ruleresult, superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule,
				complements, rule, directedGraphToTripleGraphGrammar, superRuleBox);

	}

	public static final PerformRuleResult pattern_ComplementRule_1_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_ComplementRule_2_1_preparereturnvalue_bindingFB(ComplementRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_2_1_preparereturnvalue_blackFBB(EClass eClass,
			ComplementRule _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_FWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_2_1_preparereturnvalue_bindingAndBlackFFB(
			ComplementRule _this) {
		Object[] result_pattern_ComplementRule_2_1_preparereturnvalue_binding = pattern_ComplementRule_2_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_ComplementRule_2_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_ComplementRule_2_1_preparereturnvalue_binding[0];

			Object[] result_pattern_ComplementRule_2_1_preparereturnvalue_black = pattern_ComplementRule_2_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_ComplementRule_2_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_ComplementRule_2_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_2_1_preparereturnvalue_greenBF(EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "ComplementRule";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_ComplementRule_2_2_corematch_bindingFFFFB(Match match) {
		EObject _localVariable_0 = match.getObject("directedGraph");
		EObject _localVariable_1 = match.getObject("ruleBox");
		EObject _localVariable_2 = match.getObject("complements");
		EObject _localVariable_3 = match.getObject("superRuleBox");
		EObject tmpDirectedGraph = _localVariable_0;
		EObject tmpRuleBox = _localVariable_1;
		EObject tmpComplements = _localVariable_2;
		EObject tmpSuperRuleBox = _localVariable_3;
		if (tmpDirectedGraph instanceof DirectedGraph) {
			DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
			if (tmpRuleBox instanceof Box) {
				Box ruleBox = (Box) tmpRuleBox;
				if (tmpComplements instanceof Composite) {
					Composite complements = (Composite) tmpComplements;
					if (tmpSuperRuleBox instanceof Box) {
						Box superRuleBox = (Box) tmpSuperRuleBox;
						return new Object[] { directedGraph, ruleBox, complements, superRuleBox, match };
					}
				}
			}
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_ComplementRule_2_2_corematch_blackFBBFBFFBB(
			DirectedGraph directedGraph, Box ruleBox, Composite complements, Box superRuleBox, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!ruleBox.equals(superRuleBox)) {
			String complements_label = complements.getLabel();
			if (complements_label.equals("0..*")) {
				for (NodeToRule nodeToRule : org.moflon.core.utilities.eMoflonEMFUtil.getOppositeReferenceTyped(ruleBox,
						NodeToRule.class, "source")) {
					TGGRule rule = nodeToRule.getTarget();
					if (rule != null) {
						for (DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar : org.moflon.core.utilities.eMoflonEMFUtil
								.getOppositeReferenceTyped(directedGraph, DirectedGraphToTripleGraphGrammar.class,
										"source")) {
							TripleGraphGrammar tripleGraphGrammar = directedGraphToTripleGraphGrammar.getTarget();
							if (tripleGraphGrammar != null) {
								_result.add(new Object[] { tripleGraphGrammar, directedGraph, ruleBox, nodeToRule,
										complements, rule, directedGraphToTripleGraphGrammar, superRuleBox, match });
							}

						}
					}

				}
			}

		}
		return _result;
	}

	public static final Iterable<Object[]> pattern_ComplementRule_2_3_findcontext_blackFBBBBBBBB(
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule,
			Composite complements, TGGRule rule, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar,
			Box superRuleBox) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!ruleBox.equals(superRuleBox)) {
			if (directedGraph.getEdges().contains(complements)) {
				if (directedGraph.getNodes().contains(ruleBox)) {
					if (ruleBox.equals(nodeToRule.getSource())) {
						if (directedGraph.getNodes().contains(superRuleBox)) {
							if (ruleBox.equals(complements.getSource())) {
								if (tripleGraphGrammar.getTggRule().contains(rule)) {
									if (rule.equals(nodeToRule.getTarget())) {
										if (superRuleBox.equals(complements.getTarget())) {
											if (tripleGraphGrammar
													.equals(directedGraphToTripleGraphGrammar.getTarget())) {
												if (directedGraph
														.equals(directedGraphToTripleGraphGrammar.getSource())) {
													String complements_label = complements.getLabel();
													if (complements_label.equals("0..*")) {
														for (TGGRule superRule : tripleGraphGrammar.getTggRule()) {
															if (!rule.equals(superRule)) {
																_result.add(new Object[] { superRule,
																		tripleGraphGrammar, directedGraph, ruleBox,
																		nodeToRule, complements, rule,
																		directedGraphToTripleGraphGrammar,
																		superRuleBox });
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

	public static final Object[] pattern_ComplementRule_2_3_findcontext_greenBBBBBBBBBFFFFFFFFFFFFFFFFF(
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox,
			NodeToRule nodeToRule, Composite complements, TGGRule rule,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Box superRuleBox) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		EMoflonEdge directedGraph__complements____edges = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge complements__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge tripleGraphGrammar__superRule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge superRule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__ruleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ruleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge nodeToRule__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__superRuleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge superRuleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge complements__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge tripleGraphGrammar__rule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge nodeToRule__rule____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge complements__superRuleBox____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		String directedGraph__complements____edges_name_prime = "edges";
		String complements__directedGraph____graph_name_prime = "graph";
		String tripleGraphGrammar__superRule____tggRule_name_prime = "tggRule";
		String superRule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		String directedGraph__ruleBox____nodes_name_prime = "nodes";
		String ruleBox__directedGraph____graph_name_prime = "graph";
		String nodeToRule__ruleBox____source_name_prime = "source";
		String directedGraph__superRuleBox____nodes_name_prime = "nodes";
		String superRuleBox__directedGraph____graph_name_prime = "graph";
		String complements__ruleBox____source_name_prime = "source";
		String tripleGraphGrammar__rule____tggRule_name_prime = "tggRule";
		String rule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		String nodeToRule__rule____target_name_prime = "target";
		String complements__superRuleBox____target_name_prime = "target";
		String directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime = "target";
		String directedGraphToTripleGraphGrammar__directedGraph____source_name_prime = "source";
		isApplicableMatch.getAllContextElements().add(superRule);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(directedGraph);
		isApplicableMatch.getAllContextElements().add(ruleBox);
		isApplicableMatch.getAllContextElements().add(nodeToRule);
		isApplicableMatch.getAllContextElements().add(complements);
		isApplicableMatch.getAllContextElements().add(rule);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(superRuleBox);
		directedGraph__complements____edges.setSrc(directedGraph);
		directedGraph__complements____edges.setTrg(complements);
		isApplicableMatch.getAllContextElements().add(directedGraph__complements____edges);
		complements__directedGraph____graph.setSrc(complements);
		complements__directedGraph____graph.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(complements__directedGraph____graph);
		tripleGraphGrammar__superRule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__superRule____tggRule.setTrg(superRule);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar__superRule____tggRule);
		superRule__tripleGraphGrammar____tripleGraphGrammar.setSrc(superRule);
		superRule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(superRule__tripleGraphGrammar____tripleGraphGrammar);
		directedGraph__ruleBox____nodes.setSrc(directedGraph);
		directedGraph__ruleBox____nodes.setTrg(ruleBox);
		isApplicableMatch.getAllContextElements().add(directedGraph__ruleBox____nodes);
		ruleBox__directedGraph____graph.setSrc(ruleBox);
		ruleBox__directedGraph____graph.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(ruleBox__directedGraph____graph);
		nodeToRule__ruleBox____source.setSrc(nodeToRule);
		nodeToRule__ruleBox____source.setTrg(ruleBox);
		isApplicableMatch.getAllContextElements().add(nodeToRule__ruleBox____source);
		directedGraph__superRuleBox____nodes.setSrc(directedGraph);
		directedGraph__superRuleBox____nodes.setTrg(superRuleBox);
		isApplicableMatch.getAllContextElements().add(directedGraph__superRuleBox____nodes);
		superRuleBox__directedGraph____graph.setSrc(superRuleBox);
		superRuleBox__directedGraph____graph.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(superRuleBox__directedGraph____graph);
		complements__ruleBox____source.setSrc(complements);
		complements__ruleBox____source.setTrg(ruleBox);
		isApplicableMatch.getAllContextElements().add(complements__ruleBox____source);
		tripleGraphGrammar__rule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setTrg(rule);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar__rule____tggRule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setSrc(rule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(rule__tripleGraphGrammar____tripleGraphGrammar);
		nodeToRule__rule____target.setSrc(nodeToRule);
		nodeToRule__rule____target.setTrg(rule);
		isApplicableMatch.getAllContextElements().add(nodeToRule__rule____target);
		complements__superRuleBox____target.setSrc(complements);
		complements__superRuleBox____target.setTrg(superRuleBox);
		isApplicableMatch.getAllContextElements().add(complements__superRuleBox____target);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target);
		directedGraphToTripleGraphGrammar__directedGraph____source.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__directedGraph____source.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar__directedGraph____source);
		directedGraph__complements____edges.setName(directedGraph__complements____edges_name_prime);
		complements__directedGraph____graph.setName(complements__directedGraph____graph_name_prime);
		tripleGraphGrammar__superRule____tggRule.setName(tripleGraphGrammar__superRule____tggRule_name_prime);
		superRule__tripleGraphGrammar____tripleGraphGrammar
				.setName(superRule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		directedGraph__ruleBox____nodes.setName(directedGraph__ruleBox____nodes_name_prime);
		ruleBox__directedGraph____graph.setName(ruleBox__directedGraph____graph_name_prime);
		nodeToRule__ruleBox____source.setName(nodeToRule__ruleBox____source_name_prime);
		directedGraph__superRuleBox____nodes.setName(directedGraph__superRuleBox____nodes_name_prime);
		superRuleBox__directedGraph____graph.setName(superRuleBox__directedGraph____graph_name_prime);
		complements__ruleBox____source.setName(complements__ruleBox____source_name_prime);
		tripleGraphGrammar__rule____tggRule.setName(tripleGraphGrammar__rule____tggRule_name_prime);
		rule__tripleGraphGrammar____tripleGraphGrammar
				.setName(rule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		nodeToRule__rule____target.setName(nodeToRule__rule____target_name_prime);
		complements__superRuleBox____target.setName(complements__superRuleBox____target_name_prime);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target
				.setName(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime);
		directedGraphToTripleGraphGrammar__directedGraph____source
				.setName(directedGraphToTripleGraphGrammar__directedGraph____source_name_prime);
		return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, complements, rule,
				directedGraphToTripleGraphGrammar, superRuleBox, isApplicableMatch, directedGraph__complements____edges,
				complements__directedGraph____graph, tripleGraphGrammar__superRule____tggRule,
				superRule__tripleGraphGrammar____tripleGraphGrammar, directedGraph__ruleBox____nodes,
				ruleBox__directedGraph____graph, nodeToRule__ruleBox____source, directedGraph__superRuleBox____nodes,
				superRuleBox__directedGraph____graph, complements__ruleBox____source,
				tripleGraphGrammar__rule____tggRule, rule__tripleGraphGrammar____tripleGraphGrammar,
				nodeToRule__rule____target, complements__superRuleBox____target,
				directedGraphToTripleGraphGrammar__tripleGraphGrammar____target,
				directedGraphToTripleGraphGrammar__directedGraph____source };
	}

	public static final Object[] pattern_ComplementRule_2_4_solveCSP_bindingFBBBBBBBBBBB(ComplementRule _this,
			IsApplicableMatch isApplicableMatch, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule, Composite complements, TGGRule rule,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Box superRuleBox) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_FWD(isApplicableMatch, superRule, tripleGraphGrammar,
				directedGraph, ruleBox, nodeToRule, complements, rule, directedGraphToTripleGraphGrammar, superRuleBox);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, superRule, tripleGraphGrammar, directedGraph, ruleBox,
					nodeToRule, complements, rule, directedGraphToTripleGraphGrammar, superRuleBox };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_2_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_ComplementRule_2_4_solveCSP_bindingAndBlackFBBBBBBBBBBB(ComplementRule _this,
			IsApplicableMatch isApplicableMatch, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule, Composite complements, TGGRule rule,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Box superRuleBox) {
		Object[] result_pattern_ComplementRule_2_4_solveCSP_binding = pattern_ComplementRule_2_4_solveCSP_bindingFBBBBBBBBBBB(
				_this, isApplicableMatch, superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule,
				complements, rule, directedGraphToTripleGraphGrammar, superRuleBox);
		if (result_pattern_ComplementRule_2_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_ComplementRule_2_4_solveCSP_binding[0];

			Object[] result_pattern_ComplementRule_2_4_solveCSP_black = pattern_ComplementRule_2_4_solveCSP_blackB(csp);
			if (result_pattern_ComplementRule_2_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, superRule, tripleGraphGrammar, directedGraph,
						ruleBox, nodeToRule, complements, rule, directedGraphToTripleGraphGrammar, superRuleBox };
			}
		}
		return null;
	}

	public static final boolean pattern_ComplementRule_2_5_checkCSP_expressionFBB(ComplementRule _this, CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_ComplementRule_2_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_ComplementRule_2_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "ComplementRule";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_ComplementRule_2_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_ComplementRule_10_1_initialbindings_blackBBBBB(ComplementRule _this,
			Match match, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, TGGRule rule) {
		if (!rule.equals(superRule)) {
			return new Object[] { _this, match, superRule, tripleGraphGrammar, rule };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_10_2_SolveCSP_bindingFBBBBB(ComplementRule _this, Match match,
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, TGGRule rule) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_BWD(match, superRule, tripleGraphGrammar, rule);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, superRule, tripleGraphGrammar, rule };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_10_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_ComplementRule_10_2_SolveCSP_bindingAndBlackFBBBBB(ComplementRule _this,
			Match match, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, TGGRule rule) {
		Object[] result_pattern_ComplementRule_10_2_SolveCSP_binding = pattern_ComplementRule_10_2_SolveCSP_bindingFBBBBB(
				_this, match, superRule, tripleGraphGrammar, rule);
		if (result_pattern_ComplementRule_10_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_ComplementRule_10_2_SolveCSP_binding[0];

			Object[] result_pattern_ComplementRule_10_2_SolveCSP_black = pattern_ComplementRule_10_2_SolveCSP_blackB(
					csp);
			if (result_pattern_ComplementRule_10_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, superRule, tripleGraphGrammar, rule };
			}
		}
		return null;
	}

	public static final boolean pattern_ComplementRule_10_3_CheckCSP_expressionFBB(ComplementRule _this, CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_ComplementRule_10_4_collectelementstobetranslated_blackBBBB(Match match,
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, TGGRule rule) {
		if (!rule.equals(superRule)) {
			return new Object[] { match, superRule, tripleGraphGrammar, rule };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_10_4_collectelementstobetranslated_greenBBBF(Match match,
			TGGRule superRule, TGGRule rule) {
		EMoflonEdge rule__superRule____kernel = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String rule__superRule____kernel_name_prime = "kernel";
		rule__superRule____kernel.setSrc(rule);
		rule__superRule____kernel.setTrg(superRule);
		match.getToBeTranslatedEdges().add(rule__superRule____kernel);
		rule__superRule____kernel.setName(rule__superRule____kernel_name_prime);
		return new Object[] { match, superRule, rule, rule__superRule____kernel };
	}

	public static final Object[] pattern_ComplementRule_10_5_collectcontextelements_blackBBBB(Match match,
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, TGGRule rule) {
		if (!rule.equals(superRule)) {
			return new Object[] { match, superRule, tripleGraphGrammar, rule };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_10_5_collectcontextelements_greenBBBBFFFF(Match match,
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, TGGRule rule) {
		EMoflonEdge tripleGraphGrammar__superRule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge superRule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge tripleGraphGrammar__rule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getContextNodes().add(superRule);
		match.getContextNodes().add(tripleGraphGrammar);
		match.getContextNodes().add(rule);
		String tripleGraphGrammar__superRule____tggRule_name_prime = "tggRule";
		String superRule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		String tripleGraphGrammar__rule____tggRule_name_prime = "tggRule";
		String rule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		tripleGraphGrammar__superRule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__superRule____tggRule.setTrg(superRule);
		match.getContextEdges().add(tripleGraphGrammar__superRule____tggRule);
		superRule__tripleGraphGrammar____tripleGraphGrammar.setSrc(superRule);
		superRule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		match.getContextEdges().add(superRule__tripleGraphGrammar____tripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setTrg(rule);
		match.getContextEdges().add(tripleGraphGrammar__rule____tggRule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setSrc(rule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		match.getContextEdges().add(rule__tripleGraphGrammar____tripleGraphGrammar);
		tripleGraphGrammar__superRule____tggRule.setName(tripleGraphGrammar__superRule____tggRule_name_prime);
		superRule__tripleGraphGrammar____tripleGraphGrammar
				.setName(superRule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		tripleGraphGrammar__rule____tggRule.setName(tripleGraphGrammar__rule____tggRule_name_prime);
		rule__tripleGraphGrammar____tripleGraphGrammar
				.setName(rule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		return new Object[] { match, superRule, tripleGraphGrammar, rule, tripleGraphGrammar__superRule____tggRule,
				superRule__tripleGraphGrammar____tripleGraphGrammar, tripleGraphGrammar__rule____tggRule,
				rule__tripleGraphGrammar____tripleGraphGrammar };
	}

	public static final void pattern_ComplementRule_10_6_registerobjectstomatch_expressionBBBBB(ComplementRule _this,
			Match match, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, TGGRule rule) {
		_this.registerObjectsToMatch_BWD(match, superRule, tripleGraphGrammar, rule);

	}

	public static final boolean pattern_ComplementRule_10_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_ComplementRule_10_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_ComplementRule_11_1_performtransformation_bindingFFFFFFFFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("superRule");
		EObject _localVariable_1 = isApplicableMatch.getObject("tripleGraphGrammar");
		EObject _localVariable_2 = isApplicableMatch.getObject("directedGraph");
		EObject _localVariable_3 = isApplicableMatch.getObject("ruleBox");
		EObject _localVariable_4 = isApplicableMatch.getObject("nodeToRule");
		EObject _localVariable_5 = isApplicableMatch.getObject("rule");
		EObject _localVariable_6 = isApplicableMatch.getObject("directedGraphToTripleGraphGrammar");
		EObject _localVariable_7 = isApplicableMatch.getObject("superRuleBox");
		EObject tmpSuperRule = _localVariable_0;
		EObject tmpTripleGraphGrammar = _localVariable_1;
		EObject tmpDirectedGraph = _localVariable_2;
		EObject tmpRuleBox = _localVariable_3;
		EObject tmpNodeToRule = _localVariable_4;
		EObject tmpRule = _localVariable_5;
		EObject tmpDirectedGraphToTripleGraphGrammar = _localVariable_6;
		EObject tmpSuperRuleBox = _localVariable_7;
		if (tmpSuperRule instanceof TGGRule) {
			TGGRule superRule = (TGGRule) tmpSuperRule;
			if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
				TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
				if (tmpDirectedGraph instanceof DirectedGraph) {
					DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
					if (tmpRuleBox instanceof Box) {
						Box ruleBox = (Box) tmpRuleBox;
						if (tmpNodeToRule instanceof NodeToRule) {
							NodeToRule nodeToRule = (NodeToRule) tmpNodeToRule;
							if (tmpRule instanceof TGGRule) {
								TGGRule rule = (TGGRule) tmpRule;
								if (tmpDirectedGraphToTripleGraphGrammar instanceof DirectedGraphToTripleGraphGrammar) {
									DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) tmpDirectedGraphToTripleGraphGrammar;
									if (tmpSuperRuleBox instanceof Box) {
										Box superRuleBox = (Box) tmpSuperRuleBox;
										return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox,
												nodeToRule, rule, directedGraphToTripleGraphGrammar, superRuleBox,
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

	public static final Object[] pattern_ComplementRule_11_1_performtransformation_blackBBBBBBBBFBB(TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule,
			TGGRule rule, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Box superRuleBox,
			ComplementRule _this, IsApplicableMatch isApplicableMatch) {
		if (!ruleBox.equals(superRuleBox)) {
			if (!rule.equals(superRule)) {
				for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
					if (tmpCsp instanceof CSP) {
						CSP csp = (CSP) tmpCsp;
						return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
								directedGraphToTripleGraphGrammar, superRuleBox, csp, _this, isApplicableMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_11_1_performtransformation_bindingAndBlackFFFFFFFFFBB(
			ComplementRule _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_ComplementRule_11_1_performtransformation_binding = pattern_ComplementRule_11_1_performtransformation_bindingFFFFFFFFB(
				isApplicableMatch);
		if (result_pattern_ComplementRule_11_1_performtransformation_binding != null) {
			TGGRule superRule = (TGGRule) result_pattern_ComplementRule_11_1_performtransformation_binding[0];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result_pattern_ComplementRule_11_1_performtransformation_binding[1];
			DirectedGraph directedGraph = (DirectedGraph) result_pattern_ComplementRule_11_1_performtransformation_binding[2];
			Box ruleBox = (Box) result_pattern_ComplementRule_11_1_performtransformation_binding[3];
			NodeToRule nodeToRule = (NodeToRule) result_pattern_ComplementRule_11_1_performtransformation_binding[4];
			TGGRule rule = (TGGRule) result_pattern_ComplementRule_11_1_performtransformation_binding[5];
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result_pattern_ComplementRule_11_1_performtransformation_binding[6];
			Box superRuleBox = (Box) result_pattern_ComplementRule_11_1_performtransformation_binding[7];

			Object[] result_pattern_ComplementRule_11_1_performtransformation_black = pattern_ComplementRule_11_1_performtransformation_blackBBBBBBBBFBB(
					superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
					directedGraphToTripleGraphGrammar, superRuleBox, _this, isApplicableMatch);
			if (result_pattern_ComplementRule_11_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_ComplementRule_11_1_performtransformation_black[8];

				return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
						directedGraphToTripleGraphGrammar, superRuleBox, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_11_1_performtransformation_greenBBFB(
			DirectedGraph directedGraph, Box ruleBox, Box superRuleBox) {
		Composite complements = LanguageFactory.eINSTANCE.createComposite();
		String complements_label_prime = "0..*";
		directedGraph.getEdges().add(complements);
		complements.setSource(ruleBox);
		complements.setTarget(superRuleBox);
		complements.setLabel(complements_label_prime);
		return new Object[] { directedGraph, ruleBox, complements, superRuleBox };
	}

	public static final Object[] pattern_ComplementRule_11_2_collecttranslatedelements_blackB(Composite complements) {
		return new Object[] { complements };
	}

	public static final Object[] pattern_ComplementRule_11_2_collecttranslatedelements_greenFB(Composite complements) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getCreatedElements().add(complements);
		return new Object[] { ruleresult, complements };
	}

	public static final Object[] pattern_ComplementRule_11_3_bookkeepingforedges_blackBBBBBBBBBB(
			PerformRuleResult ruleresult, EObject superRule, EObject tripleGraphGrammar, EObject directedGraph,
			EObject ruleBox, EObject nodeToRule, EObject complements, EObject rule,
			EObject directedGraphToTripleGraphGrammar, EObject superRuleBox) {
		if (!superRule.equals(tripleGraphGrammar)) {
			if (!superRule.equals(superRuleBox)) {
				if (!directedGraph.equals(superRule)) {
					if (!directedGraph.equals(tripleGraphGrammar)) {
						if (!directedGraph.equals(ruleBox)) {
							if (!directedGraph.equals(nodeToRule)) {
								if (!directedGraph.equals(rule)) {
									if (!directedGraph.equals(directedGraphToTripleGraphGrammar)) {
										if (!directedGraph.equals(superRuleBox)) {
											if (!ruleBox.equals(superRule)) {
												if (!ruleBox.equals(tripleGraphGrammar)) {
													if (!ruleBox.equals(superRuleBox)) {
														if (!nodeToRule.equals(superRule)) {
															if (!nodeToRule.equals(tripleGraphGrammar)) {
																if (!nodeToRule.equals(ruleBox)) {
																	if (!nodeToRule.equals(rule)) {
																		if (!nodeToRule.equals(superRuleBox)) {
																			if (!complements.equals(superRule)) {
																				if (!complements
																						.equals(tripleGraphGrammar)) {
																					if (!complements
																							.equals(directedGraph)) {
																						if (!complements
																								.equals(ruleBox)) {
																							if (!complements.equals(
																									nodeToRule)) {
																								if (!complements
																										.equals(rule)) {
																									if (!complements
																											.equals(directedGraphToTripleGraphGrammar)) {
																										if (!complements
																												.equals(superRuleBox)) {
																											if (!rule
																													.equals(superRule)) {
																												if (!rule
																														.equals(tripleGraphGrammar)) {
																													if (!rule
																															.equals(ruleBox)) {
																														if (!rule
																																.equals(superRuleBox)) {
																															if (!directedGraphToTripleGraphGrammar
																																	.equals(superRule)) {
																																if (!directedGraphToTripleGraphGrammar
																																		.equals(tripleGraphGrammar)) {
																																	if (!directedGraphToTripleGraphGrammar
																																			.equals(ruleBox)) {
																																		if (!directedGraphToTripleGraphGrammar
																																				.equals(nodeToRule)) {
																																			if (!directedGraphToTripleGraphGrammar
																																					.equals(rule)) {
																																				if (!directedGraphToTripleGraphGrammar
																																						.equals(superRuleBox)) {
																																					if (!superRuleBox
																																							.equals(tripleGraphGrammar)) {
																																						return new Object[] {
																																								ruleresult,
																																								superRule,
																																								tripleGraphGrammar,
																																								directedGraph,
																																								ruleBox,
																																								nodeToRule,
																																								complements,
																																								rule,
																																								directedGraphToTripleGraphGrammar,
																																								superRuleBox };
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

	public static final Object[] pattern_ComplementRule_11_3_bookkeepingforedges_greenBBBBBBBFFFFF(
			PerformRuleResult ruleresult, EObject superRule, EObject directedGraph, EObject ruleBox,
			EObject complements, EObject rule, EObject superRuleBox) {
		EMoflonEdge directedGraph__complements____edges = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge complements__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge complements__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__superRule____kernel = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge complements__superRuleBox____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "ComplementRule";
		String directedGraph__complements____edges_name_prime = "edges";
		String complements__directedGraph____graph_name_prime = "graph";
		String complements__ruleBox____source_name_prime = "source";
		String rule__superRule____kernel_name_prime = "kernel";
		String complements__superRuleBox____target_name_prime = "target";
		directedGraph__complements____edges.setSrc(directedGraph);
		directedGraph__complements____edges.setTrg(complements);
		ruleresult.getCreatedEdges().add(directedGraph__complements____edges);
		complements__directedGraph____graph.setSrc(complements);
		complements__directedGraph____graph.setTrg(directedGraph);
		ruleresult.getCreatedEdges().add(complements__directedGraph____graph);
		complements__ruleBox____source.setSrc(complements);
		complements__ruleBox____source.setTrg(ruleBox);
		ruleresult.getCreatedEdges().add(complements__ruleBox____source);
		rule__superRule____kernel.setSrc(rule);
		rule__superRule____kernel.setTrg(superRule);
		ruleresult.getTranslatedEdges().add(rule__superRule____kernel);
		complements__superRuleBox____target.setSrc(complements);
		complements__superRuleBox____target.setTrg(superRuleBox);
		ruleresult.getCreatedEdges().add(complements__superRuleBox____target);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		directedGraph__complements____edges.setName(directedGraph__complements____edges_name_prime);
		complements__directedGraph____graph.setName(complements__directedGraph____graph_name_prime);
		complements__ruleBox____source.setName(complements__ruleBox____source_name_prime);
		rule__superRule____kernel.setName(rule__superRule____kernel_name_prime);
		complements__superRuleBox____target.setName(complements__superRuleBox____target_name_prime);
		return new Object[] { ruleresult, superRule, directedGraph, ruleBox, complements, rule, superRuleBox,
				directedGraph__complements____edges, complements__directedGraph____graph,
				complements__ruleBox____source, rule__superRule____kernel, complements__superRuleBox____target };
	}

	public static final void pattern_ComplementRule_11_5_registerobjects_expressionBBBBBBBBBBB(ComplementRule _this,
			PerformRuleResult ruleresult, EObject superRule, EObject tripleGraphGrammar, EObject directedGraph,
			EObject ruleBox, EObject nodeToRule, EObject complements, EObject rule,
			EObject directedGraphToTripleGraphGrammar, EObject superRuleBox) {
		_this.registerObjects_BWD(ruleresult, superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule,
				complements, rule, directedGraphToTripleGraphGrammar, superRuleBox);

	}

	public static final PerformRuleResult pattern_ComplementRule_11_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_ComplementRule_12_1_preparereturnvalue_bindingFB(ComplementRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_12_1_preparereturnvalue_blackFBB(EClass eClass,
			ComplementRule _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_BWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_12_1_preparereturnvalue_bindingAndBlackFFB(
			ComplementRule _this) {
		Object[] result_pattern_ComplementRule_12_1_preparereturnvalue_binding = pattern_ComplementRule_12_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_ComplementRule_12_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_ComplementRule_12_1_preparereturnvalue_binding[0];

			Object[] result_pattern_ComplementRule_12_1_preparereturnvalue_black = pattern_ComplementRule_12_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_ComplementRule_12_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_ComplementRule_12_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_12_1_preparereturnvalue_greenBF(EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "ComplementRule";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_ComplementRule_12_2_corematch_bindingFFFB(Match match) {
		EObject _localVariable_0 = match.getObject("superRule");
		EObject _localVariable_1 = match.getObject("tripleGraphGrammar");
		EObject _localVariable_2 = match.getObject("rule");
		EObject tmpSuperRule = _localVariable_0;
		EObject tmpTripleGraphGrammar = _localVariable_1;
		EObject tmpRule = _localVariable_2;
		if (tmpSuperRule instanceof TGGRule) {
			TGGRule superRule = (TGGRule) tmpSuperRule;
			if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
				TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
				if (tmpRule instanceof TGGRule) {
					TGGRule rule = (TGGRule) tmpRule;
					return new Object[] { superRule, tripleGraphGrammar, rule, match };
				}
			}
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_ComplementRule_12_2_corematch_blackBBFFFBFB(TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, TGGRule rule, Match match) {
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
							_result.add(new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox,
									nodeToRule, rule, directedGraphToTripleGraphGrammar, match });
						}

					}
				}

			}
		}
		return _result;
	}

	public static final Iterable<Object[]> pattern_ComplementRule_12_3_findcontext_blackBBBBBBBF(TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule,
			TGGRule rule, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!rule.equals(superRule)) {
			if (tripleGraphGrammar.getTggRule().contains(superRule)) {
				if (directedGraph.getNodes().contains(ruleBox)) {
					if (ruleBox.equals(nodeToRule.getSource())) {
						if (tripleGraphGrammar.getTggRule().contains(rule)) {
							if (superRule.equals(rule.getKernel())) {
								if (rule.equals(nodeToRule.getTarget())) {
									if (tripleGraphGrammar.equals(directedGraphToTripleGraphGrammar.getTarget())) {
										if (directedGraph.equals(directedGraphToTripleGraphGrammar.getSource())) {
											for (NodeCommand tmpSuperRuleBox : directedGraph.getNodes()) {
												if (tmpSuperRuleBox instanceof Box) {
													Box superRuleBox = (Box) tmpSuperRuleBox;
													if (!ruleBox.equals(superRuleBox)) {
														_result.add(new Object[] { superRule, tripleGraphGrammar,
																directedGraph, ruleBox, nodeToRule, rule,
																directedGraphToTripleGraphGrammar, superRuleBox });
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

	public static final Object[] pattern_ComplementRule_12_3_findcontext_greenBBBBBBBBFFFFFFFFFFFFFF(TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule,
			TGGRule rule, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Box superRuleBox) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		EMoflonEdge tripleGraphGrammar__superRule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge superRule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__ruleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ruleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge nodeToRule__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__superRuleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge superRuleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge tripleGraphGrammar__rule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__superRule____kernel = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge nodeToRule__rule____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		String tripleGraphGrammar__superRule____tggRule_name_prime = "tggRule";
		String superRule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		String directedGraph__ruleBox____nodes_name_prime = "nodes";
		String ruleBox__directedGraph____graph_name_prime = "graph";
		String nodeToRule__ruleBox____source_name_prime = "source";
		String directedGraph__superRuleBox____nodes_name_prime = "nodes";
		String superRuleBox__directedGraph____graph_name_prime = "graph";
		String tripleGraphGrammar__rule____tggRule_name_prime = "tggRule";
		String rule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		String rule__superRule____kernel_name_prime = "kernel";
		String nodeToRule__rule____target_name_prime = "target";
		String directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime = "target";
		String directedGraphToTripleGraphGrammar__directedGraph____source_name_prime = "source";
		isApplicableMatch.getAllContextElements().add(superRule);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(directedGraph);
		isApplicableMatch.getAllContextElements().add(ruleBox);
		isApplicableMatch.getAllContextElements().add(nodeToRule);
		isApplicableMatch.getAllContextElements().add(rule);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(superRuleBox);
		tripleGraphGrammar__superRule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__superRule____tggRule.setTrg(superRule);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar__superRule____tggRule);
		superRule__tripleGraphGrammar____tripleGraphGrammar.setSrc(superRule);
		superRule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(superRule__tripleGraphGrammar____tripleGraphGrammar);
		directedGraph__ruleBox____nodes.setSrc(directedGraph);
		directedGraph__ruleBox____nodes.setTrg(ruleBox);
		isApplicableMatch.getAllContextElements().add(directedGraph__ruleBox____nodes);
		ruleBox__directedGraph____graph.setSrc(ruleBox);
		ruleBox__directedGraph____graph.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(ruleBox__directedGraph____graph);
		nodeToRule__ruleBox____source.setSrc(nodeToRule);
		nodeToRule__ruleBox____source.setTrg(ruleBox);
		isApplicableMatch.getAllContextElements().add(nodeToRule__ruleBox____source);
		directedGraph__superRuleBox____nodes.setSrc(directedGraph);
		directedGraph__superRuleBox____nodes.setTrg(superRuleBox);
		isApplicableMatch.getAllContextElements().add(directedGraph__superRuleBox____nodes);
		superRuleBox__directedGraph____graph.setSrc(superRuleBox);
		superRuleBox__directedGraph____graph.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(superRuleBox__directedGraph____graph);
		tripleGraphGrammar__rule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setTrg(rule);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar__rule____tggRule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setSrc(rule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(rule__tripleGraphGrammar____tripleGraphGrammar);
		rule__superRule____kernel.setSrc(rule);
		rule__superRule____kernel.setTrg(superRule);
		isApplicableMatch.getAllContextElements().add(rule__superRule____kernel);
		nodeToRule__rule____target.setSrc(nodeToRule);
		nodeToRule__rule____target.setTrg(rule);
		isApplicableMatch.getAllContextElements().add(nodeToRule__rule____target);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target);
		directedGraphToTripleGraphGrammar__directedGraph____source.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__directedGraph____source.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar__directedGraph____source);
		tripleGraphGrammar__superRule____tggRule.setName(tripleGraphGrammar__superRule____tggRule_name_prime);
		superRule__tripleGraphGrammar____tripleGraphGrammar
				.setName(superRule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		directedGraph__ruleBox____nodes.setName(directedGraph__ruleBox____nodes_name_prime);
		ruleBox__directedGraph____graph.setName(ruleBox__directedGraph____graph_name_prime);
		nodeToRule__ruleBox____source.setName(nodeToRule__ruleBox____source_name_prime);
		directedGraph__superRuleBox____nodes.setName(directedGraph__superRuleBox____nodes_name_prime);
		superRuleBox__directedGraph____graph.setName(superRuleBox__directedGraph____graph_name_prime);
		tripleGraphGrammar__rule____tggRule.setName(tripleGraphGrammar__rule____tggRule_name_prime);
		rule__tripleGraphGrammar____tripleGraphGrammar
				.setName(rule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		rule__superRule____kernel.setName(rule__superRule____kernel_name_prime);
		nodeToRule__rule____target.setName(nodeToRule__rule____target_name_prime);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target
				.setName(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime);
		directedGraphToTripleGraphGrammar__directedGraph____source
				.setName(directedGraphToTripleGraphGrammar__directedGraph____source_name_prime);
		return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
				directedGraphToTripleGraphGrammar, superRuleBox, isApplicableMatch,
				tripleGraphGrammar__superRule____tggRule, superRule__tripleGraphGrammar____tripleGraphGrammar,
				directedGraph__ruleBox____nodes, ruleBox__directedGraph____graph, nodeToRule__ruleBox____source,
				directedGraph__superRuleBox____nodes, superRuleBox__directedGraph____graph,
				tripleGraphGrammar__rule____tggRule, rule__tripleGraphGrammar____tripleGraphGrammar,
				rule__superRule____kernel, nodeToRule__rule____target,
				directedGraphToTripleGraphGrammar__tripleGraphGrammar____target,
				directedGraphToTripleGraphGrammar__directedGraph____source };
	}

	public static final Object[] pattern_ComplementRule_12_4_solveCSP_bindingFBBBBBBBBBB(ComplementRule _this,
			IsApplicableMatch isApplicableMatch, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule, TGGRule rule,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Box superRuleBox) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_BWD(isApplicableMatch, superRule, tripleGraphGrammar,
				directedGraph, ruleBox, nodeToRule, rule, directedGraphToTripleGraphGrammar, superRuleBox);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, superRule, tripleGraphGrammar, directedGraph, ruleBox,
					nodeToRule, rule, directedGraphToTripleGraphGrammar, superRuleBox };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_12_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_ComplementRule_12_4_solveCSP_bindingAndBlackFBBBBBBBBBB(ComplementRule _this,
			IsApplicableMatch isApplicableMatch, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule, TGGRule rule,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Box superRuleBox) {
		Object[] result_pattern_ComplementRule_12_4_solveCSP_binding = pattern_ComplementRule_12_4_solveCSP_bindingFBBBBBBBBBB(
				_this, isApplicableMatch, superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
				directedGraphToTripleGraphGrammar, superRuleBox);
		if (result_pattern_ComplementRule_12_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_ComplementRule_12_4_solveCSP_binding[0];

			Object[] result_pattern_ComplementRule_12_4_solveCSP_black = pattern_ComplementRule_12_4_solveCSP_blackB(
					csp);
			if (result_pattern_ComplementRule_12_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, superRule, tripleGraphGrammar, directedGraph,
						ruleBox, nodeToRule, rule, directedGraphToTripleGraphGrammar, superRuleBox };
			}
		}
		return null;
	}

	public static final boolean pattern_ComplementRule_12_5_checkCSP_expressionFBB(ComplementRule _this, CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_ComplementRule_12_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_ComplementRule_12_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "ComplementRule";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_ComplementRule_12_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_ComplementRule_20_1_preparereturnvalue_bindingFB(ComplementRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_20_1_preparereturnvalue_blackFBBF(EClass __eClass,
			ComplementRule _this) {
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

	public static final Object[] pattern_ComplementRule_20_1_preparereturnvalue_bindingAndBlackFFBF(
			ComplementRule _this) {
		Object[] result_pattern_ComplementRule_20_1_preparereturnvalue_binding = pattern_ComplementRule_20_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_ComplementRule_20_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_ComplementRule_20_1_preparereturnvalue_binding[0];

			Object[] result_pattern_ComplementRule_20_1_preparereturnvalue_black = pattern_ComplementRule_20_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_ComplementRule_20_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_ComplementRule_20_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_ComplementRule_20_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_20_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_ComplementRule_20_2_testcorematchandDECs_blackFFFFB(
			EMoflonEdge _edge_edges) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		EObject tmpDirectedGraph = _edge_edges.getSrc();
		if (tmpDirectedGraph instanceof DirectedGraph) {
			DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
			EObject tmpComplements = _edge_edges.getTrg();
			if (tmpComplements instanceof Composite) {
				Composite complements = (Composite) tmpComplements;
				if (directedGraph.getEdges().contains(complements)) {
					NodeCommand tmpRuleBox = complements.getSource();
					if (tmpRuleBox instanceof Box) {
						Box ruleBox = (Box) tmpRuleBox;
						if (directedGraph.getNodes().contains(ruleBox)) {
							NodeCommand tmpSuperRuleBox = complements.getTarget();
							if (tmpSuperRuleBox instanceof Box) {
								Box superRuleBox = (Box) tmpSuperRuleBox;
								if (!ruleBox.equals(superRuleBox)) {
									if (directedGraph.getNodes().contains(superRuleBox)) {
										String complements_label = complements.getLabel();
										if (complements_label.equals("0..*")) {
											_result.add(new Object[] { directedGraph, ruleBox, complements,
													superRuleBox, _edge_edges });
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

	public static final Object[] pattern_ComplementRule_20_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_ComplementRule_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBBBB(
			ComplementRule _this, Match match, DirectedGraph directedGraph, Box ruleBox, Composite complements,
			Box superRuleBox) {
		boolean _localVariable_0 = _this.isAppropriate_FWD(match, directedGraph, ruleBox, complements, superRuleBox);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_ComplementRule_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			ComplementRule _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_FWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_ComplementRule_20_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_20_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_ComplementRule_20_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_ComplementRule_21_1_preparereturnvalue_bindingFB(ComplementRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_21_1_preparereturnvalue_blackFBBF(EClass __eClass,
			ComplementRule _this) {
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

	public static final Object[] pattern_ComplementRule_21_1_preparereturnvalue_bindingAndBlackFFBF(
			ComplementRule _this) {
		Object[] result_pattern_ComplementRule_21_1_preparereturnvalue_binding = pattern_ComplementRule_21_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_ComplementRule_21_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_ComplementRule_21_1_preparereturnvalue_binding[0];

			Object[] result_pattern_ComplementRule_21_1_preparereturnvalue_black = pattern_ComplementRule_21_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_ComplementRule_21_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_ComplementRule_21_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_ComplementRule_21_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_21_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_ComplementRule_21_2_testcorematchandDECs_blackFFFB(
			EMoflonEdge _edge_kernel) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		EObject tmpRule = _edge_kernel.getSrc();
		if (tmpRule instanceof TGGRule) {
			TGGRule rule = (TGGRule) tmpRule;
			EObject tmpSuperRule = _edge_kernel.getTrg();
			if (tmpSuperRule instanceof TGGRule) {
				TGGRule superRule = (TGGRule) tmpSuperRule;
				if (!rule.equals(superRule)) {
					if (superRule.equals(rule.getKernel())) {
						TripleGraphGrammar tripleGraphGrammar = rule.getTripleGraphGrammar();
						if (tripleGraphGrammar != null) {
							if (tripleGraphGrammar.getTggRule().contains(superRule)) {
								_result.add(new Object[] { superRule, tripleGraphGrammar, rule, _edge_kernel });
							}
						}

					}
				}
			}

		}

		return _result;
	}

	public static final Object[] pattern_ComplementRule_21_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_ComplementRule_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBBB(
			ComplementRule _this, Match match, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, TGGRule rule) {
		boolean _localVariable_0 = _this.isAppropriate_BWD(match, superRule, tripleGraphGrammar, rule);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_ComplementRule_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			ComplementRule _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_BWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_ComplementRule_21_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_21_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_ComplementRule_21_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_ComplementRule_24_1_prepare_blackB(ComplementRule _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_ComplementRule_24_1_prepare_greenF() {
		IsApplicableRuleResult result = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		return new Object[] { result };
	}

	public static final Object[] pattern_ComplementRule_24_2_matchsrctrgcontext_bindingFFFFFFFBB(Match targetMatch,
			Match sourceMatch) {
		EObject _localVariable_0 = targetMatch.getObject("superRule");
		EObject _localVariable_1 = targetMatch.getObject("tripleGraphGrammar");
		EObject _localVariable_2 = sourceMatch.getObject("directedGraph");
		EObject _localVariable_3 = sourceMatch.getObject("ruleBox");
		EObject _localVariable_4 = sourceMatch.getObject("complements");
		EObject _localVariable_5 = targetMatch.getObject("rule");
		EObject _localVariable_6 = sourceMatch.getObject("superRuleBox");
		EObject tmpSuperRule = _localVariable_0;
		EObject tmpTripleGraphGrammar = _localVariable_1;
		EObject tmpDirectedGraph = _localVariable_2;
		EObject tmpRuleBox = _localVariable_3;
		EObject tmpComplements = _localVariable_4;
		EObject tmpRule = _localVariable_5;
		EObject tmpSuperRuleBox = _localVariable_6;
		if (tmpSuperRule instanceof TGGRule) {
			TGGRule superRule = (TGGRule) tmpSuperRule;
			if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
				TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
				if (tmpDirectedGraph instanceof DirectedGraph) {
					DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
					if (tmpRuleBox instanceof Box) {
						Box ruleBox = (Box) tmpRuleBox;
						if (tmpComplements instanceof Composite) {
							Composite complements = (Composite) tmpComplements;
							if (tmpRule instanceof TGGRule) {
								TGGRule rule = (TGGRule) tmpRule;
								if (tmpSuperRuleBox instanceof Box) {
									Box superRuleBox = (Box) tmpSuperRuleBox;
									return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox,
											complements, rule, superRuleBox, targetMatch, sourceMatch };
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_24_2_matchsrctrgcontext_blackBBBBBBBBB(TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox, Composite complements,
			TGGRule rule, Box superRuleBox, Match sourceMatch, Match targetMatch) {
		if (!ruleBox.equals(superRuleBox)) {
			if (!rule.equals(superRule)) {
				if (!sourceMatch.equals(targetMatch)) {
					String complements_label = complements.getLabel();
					if (complements_label.equals("0..*")) {
						return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox, complements, rule,
								superRuleBox, sourceMatch, targetMatch };
					}

				}
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_24_2_matchsrctrgcontext_bindingAndBlackFFFFFFFBB(
			Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_ComplementRule_24_2_matchsrctrgcontext_binding = pattern_ComplementRule_24_2_matchsrctrgcontext_bindingFFFFFFFBB(
				targetMatch, sourceMatch);
		if (result_pattern_ComplementRule_24_2_matchsrctrgcontext_binding != null) {
			TGGRule superRule = (TGGRule) result_pattern_ComplementRule_24_2_matchsrctrgcontext_binding[0];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result_pattern_ComplementRule_24_2_matchsrctrgcontext_binding[1];
			DirectedGraph directedGraph = (DirectedGraph) result_pattern_ComplementRule_24_2_matchsrctrgcontext_binding[2];
			Box ruleBox = (Box) result_pattern_ComplementRule_24_2_matchsrctrgcontext_binding[3];
			Composite complements = (Composite) result_pattern_ComplementRule_24_2_matchsrctrgcontext_binding[4];
			TGGRule rule = (TGGRule) result_pattern_ComplementRule_24_2_matchsrctrgcontext_binding[5];
			Box superRuleBox = (Box) result_pattern_ComplementRule_24_2_matchsrctrgcontext_binding[6];

			Object[] result_pattern_ComplementRule_24_2_matchsrctrgcontext_black = pattern_ComplementRule_24_2_matchsrctrgcontext_blackBBBBBBBBB(
					superRule, tripleGraphGrammar, directedGraph, ruleBox, complements, rule, superRuleBox, sourceMatch,
					targetMatch);
			if (result_pattern_ComplementRule_24_2_matchsrctrgcontext_black != null) {

				return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox, complements, rule,
						superRuleBox, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_24_3_solvecsp_bindingFBBBBBBBBBB(ComplementRule _this,
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox,
			Composite complements, TGGRule rule, Box superRuleBox, Match sourceMatch, Match targetMatch) {
		CSP _localVariable_7 = _this.isApplicable_solveCsp_CC(superRule, tripleGraphGrammar, directedGraph, ruleBox,
				complements, rule, superRuleBox, sourceMatch, targetMatch);
		CSP csp = _localVariable_7;
		if (csp != null) {
			return new Object[] { csp, _this, superRule, tripleGraphGrammar, directedGraph, ruleBox, complements, rule,
					superRuleBox, sourceMatch, targetMatch };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_24_3_solvecsp_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_ComplementRule_24_3_solvecsp_bindingAndBlackFBBBBBBBBBB(ComplementRule _this,
			TGGRule superRule, TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox,
			Composite complements, TGGRule rule, Box superRuleBox, Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_ComplementRule_24_3_solvecsp_binding = pattern_ComplementRule_24_3_solvecsp_bindingFBBBBBBBBBB(
				_this, superRule, tripleGraphGrammar, directedGraph, ruleBox, complements, rule, superRuleBox,
				sourceMatch, targetMatch);
		if (result_pattern_ComplementRule_24_3_solvecsp_binding != null) {
			CSP csp = (CSP) result_pattern_ComplementRule_24_3_solvecsp_binding[0];

			Object[] result_pattern_ComplementRule_24_3_solvecsp_black = pattern_ComplementRule_24_3_solvecsp_blackB(
					csp);
			if (result_pattern_ComplementRule_24_3_solvecsp_black != null) {

				return new Object[] { csp, _this, superRule, tripleGraphGrammar, directedGraph, ruleBox, complements,
						rule, superRuleBox, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final boolean pattern_ComplementRule_24_4_checkCSP_expressionFB(CSP csp) {
		boolean _localVariable_0 = csp.check();
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Iterable<Object[]> pattern_ComplementRule_24_5_matchcorrcontext_blackBBBFBFBB(
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox, TGGRule rule,
			Match sourceMatch, Match targetMatch) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!sourceMatch.equals(targetMatch)) {
			for (NodeToRule nodeToRule : org.moflon.core.utilities.eMoflonEMFUtil.getOppositeReferenceTyped(ruleBox,
					NodeToRule.class, "source")) {
				if (rule.equals(nodeToRule.getTarget())) {
					for (DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar : org.moflon.core.utilities.eMoflonEMFUtil
							.getOppositeReferenceTyped(tripleGraphGrammar, DirectedGraphToTripleGraphGrammar.class,
									"target")) {
						if (directedGraph.equals(directedGraphToTripleGraphGrammar.getSource())) {
							_result.add(new Object[] { tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
									directedGraphToTripleGraphGrammar, sourceMatch, targetMatch });
						}
					}
				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_ComplementRule_24_5_matchcorrcontext_greenBBBBF(NodeToRule nodeToRule,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Match sourceMatch, Match targetMatch) {
		CCMatch ccMatch = RuntimeFactory.eINSTANCE.createCCMatch();
		String ccMatch_ruleName_prime = "ComplementRule";
		ccMatch.setSourceMatch(sourceMatch);
		ccMatch.setTargetMatch(targetMatch);
		ccMatch.getAllContextElements().add(nodeToRule);
		ccMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar);
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { nodeToRule, directedGraphToTripleGraphGrammar, sourceMatch, targetMatch, ccMatch };
	}

	public static final Object[] pattern_ComplementRule_24_6_createcorrespondence_blackBBBBBBBB(TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox, Composite complements,
			TGGRule rule, Box superRuleBox, CCMatch ccMatch) {
		if (!ruleBox.equals(superRuleBox)) {
			if (!rule.equals(superRule)) {
				return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox, complements, rule,
						superRuleBox, ccMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_24_7_addtoreturnedresult_blackBB(IsApplicableRuleResult result,
			CCMatch ccMatch) {
		return new Object[] { result, ccMatch };
	}

	public static final Object[] pattern_ComplementRule_24_7_addtoreturnedresult_greenBB(IsApplicableRuleResult result,
			CCMatch ccMatch) {
		result.getIsApplicableMatch().add(ccMatch);
		boolean result_success_prime = Boolean.valueOf(true);
		String ccMatch_ruleName_prime = "ComplementRule";
		result.setSuccess(Boolean.valueOf(result_success_prime));
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { result, ccMatch };
	}

	public static final IsApplicableRuleResult pattern_ComplementRule_24_8_expressionFB(IsApplicableRuleResult result) {
		IsApplicableRuleResult _result = result;
		return _result;
	}

	public static final Object[] pattern_ComplementRule_27_1_matchtggpattern_blackBBBB(DirectedGraph directedGraph,
			Box ruleBox, Composite complements, Box superRuleBox) {
		if (!ruleBox.equals(superRuleBox)) {
			if (directedGraph.getEdges().contains(complements)) {
				if (directedGraph.getNodes().contains(ruleBox)) {
					if (directedGraph.getNodes().contains(superRuleBox)) {
						if (ruleBox.equals(complements.getSource())) {
							if (superRuleBox.equals(complements.getTarget())) {
								return new Object[] { directedGraph, ruleBox, complements, superRuleBox };
							}
						}
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_27_1_matchtggpattern_greenB(Composite complements) {
		String complements_label_prime = "0..*";
		complements.setLabel(complements_label_prime);
		return new Object[] { complements };
	}

	public static final boolean pattern_ComplementRule_27_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_ComplementRule_27_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_ComplementRule_28_1_matchtggpattern_blackBBB(TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, TGGRule rule) {
		if (!rule.equals(superRule)) {
			if (tripleGraphGrammar.getTggRule().contains(superRule)) {
				if (tripleGraphGrammar.getTggRule().contains(rule)) {
					if (superRule.equals(rule.getKernel())) {
						return new Object[] { superRule, tripleGraphGrammar, rule };
					}
				}
			}
		}
		return null;
	}

	public static final boolean pattern_ComplementRule_28_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_ComplementRule_28_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_ComplementRule_29_1_createresult_blackB(ComplementRule _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_ComplementRule_29_1_createresult_greenFF() {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		ModelgeneratorRuleResult ruleResult = RuntimeFactory.eINSTANCE.createModelgeneratorRuleResult();
		boolean ruleResult_success_prime = false;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		return new Object[] { isApplicableMatch, ruleResult };
	}

	public static final Object[] pattern_ComplementRule_29_2_isapplicablecore_black_nac_0BB(
			ModelgeneratorRuleResult ruleResult, TGGRule superRule) {
		if (ruleResult.getTargetObjects().contains(superRule)) {
			return new Object[] { ruleResult, superRule };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_29_2_isapplicablecore_black_nac_1BB(
			ModelgeneratorRuleResult ruleResult, TripleGraphGrammar tripleGraphGrammar) {
		if (ruleResult.getTargetObjects().contains(tripleGraphGrammar)) {
			return new Object[] { ruleResult, tripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_29_2_isapplicablecore_black_nac_2BB(
			ModelgeneratorRuleResult ruleResult, TGGRule rule) {
		if (ruleResult.getTargetObjects().contains(rule)) {
			return new Object[] { ruleResult, rule };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_29_2_isapplicablecore_black_nac_3BB(
			ModelgeneratorRuleResult ruleResult, NodeToRule nodeToRule) {
		if (ruleResult.getCorrObjects().contains(nodeToRule)) {
			return new Object[] { ruleResult, nodeToRule };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_29_2_isapplicablecore_black_nac_4BB(
			ModelgeneratorRuleResult ruleResult, Box ruleBox) {
		if (ruleResult.getSourceObjects().contains(ruleBox)) {
			return new Object[] { ruleResult, ruleBox };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_29_2_isapplicablecore_black_nac_5BB(
			ModelgeneratorRuleResult ruleResult, DirectedGraph directedGraph) {
		if (ruleResult.getSourceObjects().contains(directedGraph)) {
			return new Object[] { ruleResult, directedGraph };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_29_2_isapplicablecore_black_nac_6BB(
			ModelgeneratorRuleResult ruleResult, Box superRuleBox) {
		if (ruleResult.getSourceObjects().contains(superRuleBox)) {
			return new Object[] { ruleResult, superRuleBox };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_29_2_isapplicablecore_black_nac_7BB(
			ModelgeneratorRuleResult ruleResult, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		if (ruleResult.getCorrObjects().contains(directedGraphToTripleGraphGrammar)) {
			return new Object[] { ruleResult, directedGraphToTripleGraphGrammar };
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_ComplementRule_29_2_isapplicablecore_blackFFFFFFFFFBB(
			RuleEntryContainer ruleEntryContainer, ModelgeneratorRuleResult ruleResult) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (RuleEntryList nodeToRuleList : ruleEntryContainer.getRuleEntryList()) {
			for (EObject tmpNodeToRule : nodeToRuleList.getEntryObjects()) {
				if (tmpNodeToRule instanceof NodeToRule) {
					NodeToRule nodeToRule = (NodeToRule) tmpNodeToRule;
					TGGRule rule = nodeToRule.getTarget();
					if (rule != null) {
						NodeCommand tmpRuleBox = nodeToRule.getSource();
						if (tmpRuleBox instanceof Box) {
							Box ruleBox = (Box) tmpRuleBox;
							TripleGraphGrammar tripleGraphGrammar = rule.getTripleGraphGrammar();
							if (tripleGraphGrammar != null) {
								DirectedGraph directedGraph = ruleBox.getGraph();
								if (directedGraph != null) {
									if (pattern_ComplementRule_29_2_isapplicablecore_black_nac_3BB(ruleResult,
											nodeToRule) == null) {
										if (pattern_ComplementRule_29_2_isapplicablecore_black_nac_2BB(ruleResult,
												rule) == null) {
											if (pattern_ComplementRule_29_2_isapplicablecore_black_nac_4BB(ruleResult,
													ruleBox) == null) {
												if (pattern_ComplementRule_29_2_isapplicablecore_black_nac_1BB(
														ruleResult, tripleGraphGrammar) == null) {
													if (pattern_ComplementRule_29_2_isapplicablecore_black_nac_5BB(
															ruleResult, directedGraph) == null) {
														for (TGGRule superRule : tripleGraphGrammar.getTggRule()) {
															if (!rule.equals(superRule)) {
																if (pattern_ComplementRule_29_2_isapplicablecore_black_nac_0BB(
																		ruleResult, superRule) == null) {
																	for (NodeCommand tmpSuperRuleBox : directedGraph
																			.getNodes()) {
																		if (tmpSuperRuleBox instanceof Box) {
																			Box superRuleBox = (Box) tmpSuperRuleBox;
																			if (!ruleBox.equals(superRuleBox)) {
																				if (pattern_ComplementRule_29_2_isapplicablecore_black_nac_6BB(
																						ruleResult,
																						superRuleBox) == null) {
																					for (DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar : org.moflon.core.utilities.eMoflonEMFUtil
																							.getOppositeReferenceTyped(
																									tripleGraphGrammar,
																									DirectedGraphToTripleGraphGrammar.class,
																									"target")) {
																						if (directedGraph
																								.equals(directedGraphToTripleGraphGrammar
																										.getSource())) {
																							if (pattern_ComplementRule_29_2_isapplicablecore_black_nac_7BB(
																									ruleResult,
																									directedGraphToTripleGraphGrammar) == null) {
																								_result.add(
																										new Object[] {
																												nodeToRuleList,
																												superRule,
																												tripleGraphGrammar,
																												rule,
																												nodeToRule,
																												ruleBox,
																												directedGraph,
																												superRuleBox,
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

	public static final Object[] pattern_ComplementRule_29_3_solveCSP_bindingFBBBBBBBBBBB(ComplementRule _this,
			IsApplicableMatch isApplicableMatch, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule, TGGRule rule,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Box superRuleBox,
			ModelgeneratorRuleResult ruleResult) {
		CSP _localVariable_0 = _this.generateModel_solveCsp_BWD(isApplicableMatch, superRule, tripleGraphGrammar,
				directedGraph, ruleBox, nodeToRule, rule, directedGraphToTripleGraphGrammar, superRuleBox, ruleResult);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, superRule, tripleGraphGrammar, directedGraph, ruleBox,
					nodeToRule, rule, directedGraphToTripleGraphGrammar, superRuleBox, ruleResult };
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_29_3_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_ComplementRule_29_3_solveCSP_bindingAndBlackFBBBBBBBBBBB(ComplementRule _this,
			IsApplicableMatch isApplicableMatch, TGGRule superRule, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule, TGGRule rule,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Box superRuleBox,
			ModelgeneratorRuleResult ruleResult) {
		Object[] result_pattern_ComplementRule_29_3_solveCSP_binding = pattern_ComplementRule_29_3_solveCSP_bindingFBBBBBBBBBBB(
				_this, isApplicableMatch, superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
				directedGraphToTripleGraphGrammar, superRuleBox, ruleResult);
		if (result_pattern_ComplementRule_29_3_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_ComplementRule_29_3_solveCSP_binding[0];

			Object[] result_pattern_ComplementRule_29_3_solveCSP_black = pattern_ComplementRule_29_3_solveCSP_blackB(
					csp);
			if (result_pattern_ComplementRule_29_3_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, superRule, tripleGraphGrammar, directedGraph,
						ruleBox, nodeToRule, rule, directedGraphToTripleGraphGrammar, superRuleBox, ruleResult };
			}
		}
		return null;
	}

	public static final boolean pattern_ComplementRule_29_4_checkCSP_expressionFBB(ComplementRule _this, CSP csp) {
		boolean _localVariable_0 = _this.generateModel_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_ComplementRule_29_5_checknacs_blackBBBBBBBB(TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule,
			TGGRule rule, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Box superRuleBox) {
		if (!ruleBox.equals(superRuleBox)) {
			if (!rule.equals(superRule)) {
				return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
						directedGraphToTripleGraphGrammar, superRuleBox };
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_29_6_perform_blackBBBBBBBBB(TGGRule superRule,
			TripleGraphGrammar tripleGraphGrammar, DirectedGraph directedGraph, Box ruleBox, NodeToRule nodeToRule,
			TGGRule rule, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Box superRuleBox,
			ModelgeneratorRuleResult ruleResult) {
		if (!ruleBox.equals(superRuleBox)) {
			if (!rule.equals(superRule)) {
				return new Object[] { superRule, tripleGraphGrammar, directedGraph, ruleBox, nodeToRule, rule,
						directedGraphToTripleGraphGrammar, superRuleBox, ruleResult };
			}
		}
		return null;
	}

	public static final Object[] pattern_ComplementRule_29_6_perform_greenBBBFBBB(TGGRule superRule,
			DirectedGraph directedGraph, Box ruleBox, TGGRule rule, Box superRuleBox,
			ModelgeneratorRuleResult ruleResult) {
		Composite complements = LanguageFactory.eINSTANCE.createComposite();
		rule.setKernel(superRule);
		String complements_label_prime = "0..*";
		boolean ruleResult_success_prime = Boolean.valueOf(true);
		int _localVariable_0 = ruleResult.getIncrementedPerformCount();
		directedGraph.getEdges().add(complements);
		complements.setSource(ruleBox);
		complements.setTarget(superRuleBox);
		ruleResult.getSourceObjects().add(complements);
		complements.setLabel(complements_label_prime);
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		int ruleResult_performCount_prime = Integer.valueOf(_localVariable_0);
		ruleResult.setPerformCount(Integer.valueOf(ruleResult_performCount_prime));
		return new Object[] { superRule, directedGraph, ruleBox, complements, rule, superRuleBox, ruleResult };
	}

	public static final ModelgeneratorRuleResult pattern_ComplementRule_29_7_expressionFB(
			ModelgeneratorRuleResult ruleResult) {
		ModelgeneratorRuleResult _result = ruleResult;
		return _result;
	}

	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //ComplementRuleImpl
