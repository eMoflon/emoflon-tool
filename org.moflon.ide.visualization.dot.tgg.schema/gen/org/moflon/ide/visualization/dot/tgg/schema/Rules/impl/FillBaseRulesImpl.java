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

import org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar;
import org.moflon.ide.visualization.dot.tgg.schema.NodeToRule;

import org.moflon.ide.visualization.dot.tgg.schema.Rules.FillBaseRules;
import org.moflon.ide.visualization.dot.tgg.schema.Rules.RulesPackage;

import org.moflon.ide.visualization.dot.tgg.schema.SchemaFactory;

import org.moflon.tgg.language.LanguageFactory;
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
 * An implementation of the model object '<em><b>Fill Base Rules</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class FillBaseRulesImpl extends AbstractRuleImpl implements FillBaseRules {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FillBaseRulesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulesPackage.Literals.FILL_BASE_RULES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_FWD(Match match, DirectedGraph directedGraph, Box ruleBox) {
		// initial bindings
		Object[] result1_black = FillBaseRulesImpl.pattern_FillBaseRules_0_1_initialbindings_blackBBBB(this, match,
				directedGraph, ruleBox);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[directedGraph] = " + directedGraph
					+ ", " + "[ruleBox] = " + ruleBox + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = FillBaseRulesImpl
				.pattern_FillBaseRules_0_2_SolveCSP_bindingAndBlackFBBBB(this, match, directedGraph, ruleBox);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[directedGraph] = " + directedGraph + ", "
					+ "[ruleBox] = " + ruleBox + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (FillBaseRulesImpl.pattern_FillBaseRules_0_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = FillBaseRulesImpl
					.pattern_FillBaseRules_0_4_collectelementstobetranslated_blackBBB(match, directedGraph, ruleBox);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[directedGraph] = " + directedGraph + ", "
						+ "[ruleBox] = " + ruleBox + ".");
			}
			FillBaseRulesImpl.pattern_FillBaseRules_0_4_collectelementstobetranslated_greenBBBFF(match, directedGraph,
					ruleBox);
			// EMoflonEdge directedGraph__ruleBox____nodes = (EMoflonEdge) result4_green[3];
			// EMoflonEdge ruleBox__directedGraph____graph = (EMoflonEdge) result4_green[4];

			// collect context elements
			Object[] result5_black = FillBaseRulesImpl.pattern_FillBaseRules_0_5_collectcontextelements_blackBBB(match,
					directedGraph, ruleBox);
			if (result5_black == null) {
				throw new RuntimeException("Pattern matching in node [collect context elements] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[directedGraph] = " + directedGraph + ", "
						+ "[ruleBox] = " + ruleBox + ".");
			}
			FillBaseRulesImpl.pattern_FillBaseRules_0_5_collectcontextelements_greenBB(match, directedGraph);

			// register objects to match
			FillBaseRulesImpl.pattern_FillBaseRules_0_6_registerobjectstomatch_expressionBBBB(this, match,
					directedGraph, ruleBox);
			return FillBaseRulesImpl.pattern_FillBaseRules_0_7_expressionF();
		} else {
			return FillBaseRulesImpl.pattern_FillBaseRules_0_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_FWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = FillBaseRulesImpl
				.pattern_FillBaseRules_1_1_performtransformation_bindingAndBlackFFFFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result1_bindingAndBlack[0];
		DirectedGraph directedGraph = (DirectedGraph) result1_bindingAndBlack[1];
		Box ruleBox = (Box) result1_bindingAndBlack[2];
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result1_bindingAndBlack[3];
		CSP csp = (CSP) result1_bindingAndBlack[4];
		Object[] result1_green = FillBaseRulesImpl.pattern_FillBaseRules_1_1_performtransformation_greenFFBBB(ruleBox,
				tripleGraphGrammar, csp);
		NodeToRule nodeToRule = (NodeToRule) result1_green[0];
		TGGRule rule = (TGGRule) result1_green[1];

		// collect translated elements
		Object[] result2_black = FillBaseRulesImpl
				.pattern_FillBaseRules_1_2_collecttranslatedelements_blackBBB(nodeToRule, rule, ruleBox);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[nodeToRule] = " + nodeToRule + ", " + "[rule] = " + rule + ", " + "[ruleBox] = " + ruleBox
					+ ".");
		}
		Object[] result2_green = FillBaseRulesImpl
				.pattern_FillBaseRules_1_2_collecttranslatedelements_greenFBBB(nodeToRule, rule, ruleBox);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = FillBaseRulesImpl.pattern_FillBaseRules_1_3_bookkeepingforedges_blackBBBBBBB(
				ruleresult, nodeToRule, directedGraphToTripleGraphGrammar, rule, directedGraph, ruleBox,
				tripleGraphGrammar);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[nodeToRule] = " + nodeToRule + ", "
					+ "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ", " + "[rule] = "
					+ rule + ", " + "[directedGraph] = " + directedGraph + ", " + "[ruleBox] = " + ruleBox + ", "
					+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
		}
		FillBaseRulesImpl.pattern_FillBaseRules_1_3_bookkeepingforedges_greenBBBBBBFFFFFF(ruleresult, nodeToRule, rule,
				directedGraph, ruleBox, tripleGraphGrammar);
		// EMoflonEdge nodeToRule__rule____target = (EMoflonEdge) result3_green[6];
		// EMoflonEdge nodeToRule__ruleBox____source = (EMoflonEdge) result3_green[7];
		// EMoflonEdge directedGraph__ruleBox____nodes = (EMoflonEdge) result3_green[8];
		// EMoflonEdge ruleBox__directedGraph____graph = (EMoflonEdge) result3_green[9];
		// EMoflonEdge tripleGraphGrammar__rule____tggRule = (EMoflonEdge) result3_green[10];
		// EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		FillBaseRulesImpl.pattern_FillBaseRules_1_5_registerobjects_expressionBBBBBBBB(this, ruleresult, nodeToRule,
				directedGraphToTripleGraphGrammar, rule, directedGraph, ruleBox, tripleGraphGrammar);
		return FillBaseRulesImpl.pattern_FillBaseRules_1_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_FWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = FillBaseRulesImpl
				.pattern_FillBaseRules_2_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = FillBaseRulesImpl
				.pattern_FillBaseRules_2_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = FillBaseRulesImpl.pattern_FillBaseRules_2_2_corematch_bindingFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		DirectedGraph directedGraph = (DirectedGraph) result2_binding[0];
		Box ruleBox = (Box) result2_binding[1];
		for (Object[] result2_black : FillBaseRulesImpl.pattern_FillBaseRules_2_2_corematch_blackFBBFB(directedGraph,
				ruleBox, match)) {
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result2_black[0];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_black[3];
			// ForEach find context
			for (Object[] result3_black : FillBaseRulesImpl.pattern_FillBaseRules_2_3_findcontext_blackBBBB(
					directedGraphToTripleGraphGrammar, directedGraph, ruleBox, tripleGraphGrammar)) {
				Object[] result3_green = FillBaseRulesImpl.pattern_FillBaseRules_2_3_findcontext_greenBBBBFFFFF(
						directedGraphToTripleGraphGrammar, directedGraph, ruleBox, tripleGraphGrammar);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[4];
				// EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = (EMoflonEdge) result3_green[5];
				// EMoflonEdge directedGraph__ruleBox____nodes = (EMoflonEdge) result3_green[6];
				// EMoflonEdge ruleBox__directedGraph____graph = (EMoflonEdge) result3_green[7];
				// EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = (EMoflonEdge) result3_green[8];

				// solve CSP
				Object[] result4_bindingAndBlack = FillBaseRulesImpl
						.pattern_FillBaseRules_2_4_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch,
								directedGraphToTripleGraphGrammar, directedGraph, ruleBox, tripleGraphGrammar);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ", "
							+ "[directedGraph] = " + directedGraph + ", " + "[ruleBox] = " + ruleBox + ", "
							+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (FillBaseRulesImpl.pattern_FillBaseRules_2_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = FillBaseRulesImpl
							.pattern_FillBaseRules_2_6_addmatchtoruleresult_blackBB(ruleresult, isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					FillBaseRulesImpl.pattern_FillBaseRules_2_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return FillBaseRulesImpl.pattern_FillBaseRules_2_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_FWD(Match match, DirectedGraph directedGraph, Box ruleBox) {
		match.registerObject("directedGraph", directedGraph);
		match.registerObject("ruleBox", ruleBox);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_FWD(Match match, DirectedGraph directedGraph, Box ruleBox) {// Create CSP
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
	public CSP isApplicable_solveCsp_FWD(IsApplicableMatch isApplicableMatch,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, DirectedGraph directedGraph,
			Box ruleBox, TripleGraphGrammar tripleGraphGrammar) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox.label", true, csp);
		var_ruleBox_label.setValue(ruleBox.getLabel());
		var_ruleBox_label.setType("String");

		// Create unbound variables
		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule.name", csp);
		var_rule_name.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_ruleBox_label, var_rule_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);
		isApplicableMatch.registerObject("directedGraph", directedGraph);
		isApplicableMatch.registerObject("ruleBox", ruleBox);
		isApplicableMatch.registerObject("tripleGraphGrammar", tripleGraphGrammar);
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
	public void registerObjects_FWD(PerformRuleResult ruleresult, EObject nodeToRule,
			EObject directedGraphToTripleGraphGrammar, EObject rule, EObject directedGraph, EObject ruleBox,
			EObject tripleGraphGrammar) {
		ruleresult.registerObject("nodeToRule", nodeToRule);
		ruleresult.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);
		ruleresult.registerObject("rule", rule);
		ruleresult.registerObject("directedGraph", directedGraph);
		ruleresult.registerObject("ruleBox", ruleBox);
		ruleresult.registerObject("tripleGraphGrammar", tripleGraphGrammar);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_FWD(Match match) {
		return true
				&& org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("ruleBox").eClass()).equals("language.Box.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_BWD(Match match, TGGRule rule, TripleGraphGrammar tripleGraphGrammar) {
		// initial bindings
		Object[] result1_black = FillBaseRulesImpl.pattern_FillBaseRules_10_1_initialbindings_blackBBBB(this, match,
				rule, tripleGraphGrammar);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[rule] = " + rule + ", "
					+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = FillBaseRulesImpl
				.pattern_FillBaseRules_10_2_SolveCSP_bindingAndBlackFBBBB(this, match, rule, tripleGraphGrammar);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[rule] = " + rule + ", " + "[tripleGraphGrammar] = "
					+ tripleGraphGrammar + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (FillBaseRulesImpl.pattern_FillBaseRules_10_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = FillBaseRulesImpl
					.pattern_FillBaseRules_10_4_collectelementstobetranslated_blackBBB(match, rule, tripleGraphGrammar);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[rule] = " + rule + ", "
						+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
			}
			FillBaseRulesImpl.pattern_FillBaseRules_10_4_collectelementstobetranslated_greenBBBFF(match, rule,
					tripleGraphGrammar);
			// EMoflonEdge tripleGraphGrammar__rule____tggRule = (EMoflonEdge) result4_green[3];
			// EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result4_green[4];

			// collect context elements
			Object[] result5_black = FillBaseRulesImpl.pattern_FillBaseRules_10_5_collectcontextelements_blackBBB(match,
					rule, tripleGraphGrammar);
			if (result5_black == null) {
				throw new RuntimeException("Pattern matching in node [collect context elements] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[rule] = " + rule + ", "
						+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
			}
			FillBaseRulesImpl.pattern_FillBaseRules_10_5_collectcontextelements_greenBB(match, tripleGraphGrammar);

			// register objects to match
			FillBaseRulesImpl.pattern_FillBaseRules_10_6_registerobjectstomatch_expressionBBBB(this, match, rule,
					tripleGraphGrammar);
			return FillBaseRulesImpl.pattern_FillBaseRules_10_7_expressionF();
		} else {
			return FillBaseRulesImpl.pattern_FillBaseRules_10_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_BWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = FillBaseRulesImpl
				.pattern_FillBaseRules_11_1_performtransformation_bindingAndBlackFFFFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result1_bindingAndBlack[0];
		TGGRule rule = (TGGRule) result1_bindingAndBlack[1];
		DirectedGraph directedGraph = (DirectedGraph) result1_bindingAndBlack[2];
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result1_bindingAndBlack[3];
		CSP csp = (CSP) result1_bindingAndBlack[4];
		Object[] result1_green = FillBaseRulesImpl.pattern_FillBaseRules_11_1_performtransformation_greenFBBFB(rule,
				directedGraph, csp);
		NodeToRule nodeToRule = (NodeToRule) result1_green[0];
		Box ruleBox = (Box) result1_green[3];

		// collect translated elements
		Object[] result2_black = FillBaseRulesImpl
				.pattern_FillBaseRules_11_2_collecttranslatedelements_blackBBB(nodeToRule, rule, ruleBox);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[nodeToRule] = " + nodeToRule + ", " + "[rule] = " + rule + ", " + "[ruleBox] = " + ruleBox
					+ ".");
		}
		Object[] result2_green = FillBaseRulesImpl
				.pattern_FillBaseRules_11_2_collecttranslatedelements_greenFBBB(nodeToRule, rule, ruleBox);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = FillBaseRulesImpl.pattern_FillBaseRules_11_3_bookkeepingforedges_blackBBBBBBB(
				ruleresult, nodeToRule, directedGraphToTripleGraphGrammar, rule, directedGraph, ruleBox,
				tripleGraphGrammar);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[nodeToRule] = " + nodeToRule + ", "
					+ "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ", " + "[rule] = "
					+ rule + ", " + "[directedGraph] = " + directedGraph + ", " + "[ruleBox] = " + ruleBox + ", "
					+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
		}
		FillBaseRulesImpl.pattern_FillBaseRules_11_3_bookkeepingforedges_greenBBBBBBFFFFFF(ruleresult, nodeToRule, rule,
				directedGraph, ruleBox, tripleGraphGrammar);
		// EMoflonEdge nodeToRule__rule____target = (EMoflonEdge) result3_green[6];
		// EMoflonEdge nodeToRule__ruleBox____source = (EMoflonEdge) result3_green[7];
		// EMoflonEdge directedGraph__ruleBox____nodes = (EMoflonEdge) result3_green[8];
		// EMoflonEdge ruleBox__directedGraph____graph = (EMoflonEdge) result3_green[9];
		// EMoflonEdge tripleGraphGrammar__rule____tggRule = (EMoflonEdge) result3_green[10];
		// EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		FillBaseRulesImpl.pattern_FillBaseRules_11_5_registerobjects_expressionBBBBBBBB(this, ruleresult, nodeToRule,
				directedGraphToTripleGraphGrammar, rule, directedGraph, ruleBox, tripleGraphGrammar);
		return FillBaseRulesImpl.pattern_FillBaseRules_11_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_BWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = FillBaseRulesImpl
				.pattern_FillBaseRules_12_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = FillBaseRulesImpl
				.pattern_FillBaseRules_12_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = FillBaseRulesImpl.pattern_FillBaseRules_12_2_corematch_bindingFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		TGGRule rule = (TGGRule) result2_binding[0];
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_binding[1];
		for (Object[] result2_black : FillBaseRulesImpl.pattern_FillBaseRules_12_2_corematch_blackFBFBB(rule,
				tripleGraphGrammar, match)) {
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result2_black[0];
			DirectedGraph directedGraph = (DirectedGraph) result2_black[2];
			// ForEach find context
			for (Object[] result3_black : FillBaseRulesImpl.pattern_FillBaseRules_12_3_findcontext_blackBBBB(
					directedGraphToTripleGraphGrammar, rule, directedGraph, tripleGraphGrammar)) {
				Object[] result3_green = FillBaseRulesImpl.pattern_FillBaseRules_12_3_findcontext_greenBBBBFFFFF(
						directedGraphToTripleGraphGrammar, rule, directedGraph, tripleGraphGrammar);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[4];
				// EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = (EMoflonEdge) result3_green[5];
				// EMoflonEdge tripleGraphGrammar__rule____tggRule = (EMoflonEdge) result3_green[6];
				// EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = (EMoflonEdge) result3_green[7];
				// EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = (EMoflonEdge) result3_green[8];

				// solve CSP
				Object[] result4_bindingAndBlack = FillBaseRulesImpl
						.pattern_FillBaseRules_12_4_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch,
								directedGraphToTripleGraphGrammar, rule, directedGraph, tripleGraphGrammar);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ", "
							+ "[rule] = " + rule + ", " + "[directedGraph] = " + directedGraph + ", "
							+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (FillBaseRulesImpl.pattern_FillBaseRules_12_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = FillBaseRulesImpl
							.pattern_FillBaseRules_12_6_addmatchtoruleresult_blackBB(ruleresult, isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					FillBaseRulesImpl.pattern_FillBaseRules_12_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return FillBaseRulesImpl.pattern_FillBaseRules_12_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_BWD(Match match, TGGRule rule, TripleGraphGrammar tripleGraphGrammar) {
		match.registerObject("rule", rule);
		match.registerObject("tripleGraphGrammar", tripleGraphGrammar);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_BWD(Match match, TGGRule rule, TripleGraphGrammar tripleGraphGrammar) {// Create CSP
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
	public CSP isApplicable_solveCsp_BWD(IsApplicableMatch isApplicableMatch,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, TGGRule rule,
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule.name", true, csp);
		var_rule_name.setValue(rule.getName());
		var_rule_name.setType("String");

		// Create unbound variables
		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox.label", csp);
		var_ruleBox_label.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_ruleBox_label, var_rule_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);
		isApplicableMatch.registerObject("rule", rule);
		isApplicableMatch.registerObject("directedGraph", directedGraph);
		isApplicableMatch.registerObject("tripleGraphGrammar", tripleGraphGrammar);
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
	public void registerObjects_BWD(PerformRuleResult ruleresult, EObject nodeToRule,
			EObject directedGraphToTripleGraphGrammar, EObject rule, EObject directedGraph, EObject ruleBox,
			EObject tripleGraphGrammar) {
		ruleresult.registerObject("nodeToRule", nodeToRule);
		ruleresult.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);
		ruleresult.registerObject("rule", rule);
		ruleresult.registerObject("directedGraph", directedGraph);
		ruleresult.registerObject("ruleBox", ruleBox);
		ruleresult.registerObject("tripleGraphGrammar", tripleGraphGrammar);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_BWD(Match match) {
		return true
				&& org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("rule").eClass()).equals("language.TGGRule.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_FWD_EMoflonEdge_58(EMoflonEdge _edge_nodes) {
		// prepare return value
		Object[] result1_bindingAndBlack = FillBaseRulesImpl
				.pattern_FillBaseRules_20_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = FillBaseRulesImpl.pattern_FillBaseRules_20_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : FillBaseRulesImpl
				.pattern_FillBaseRules_20_2_testcorematchandDECs_blackFFB(_edge_nodes)) {
			DirectedGraph directedGraph = (DirectedGraph) result2_black[0];
			Box ruleBox = (Box) result2_black[1];
			Object[] result2_green = FillBaseRulesImpl
					.pattern_FillBaseRules_20_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (FillBaseRulesImpl.pattern_FillBaseRules_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
					this, match, directedGraph, ruleBox)) {
				// Ensure that the correct types of elements are matched
				if (FillBaseRulesImpl
						.pattern_FillBaseRules_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(this,
								match)) {

					// Add match to rule result
					Object[] result5_black = FillBaseRulesImpl
							.pattern_FillBaseRules_20_5_Addmatchtoruleresult_blackBBBB(match, __performOperation,
									__result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					FillBaseRulesImpl.pattern_FillBaseRules_20_5_Addmatchtoruleresult_greenBBBB(match,
							__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return FillBaseRulesImpl.pattern_FillBaseRules_20_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_BWD_EMoflonEdge_83(EMoflonEdge _edge_tggRule) {
		// prepare return value
		Object[] result1_bindingAndBlack = FillBaseRulesImpl
				.pattern_FillBaseRules_21_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = FillBaseRulesImpl.pattern_FillBaseRules_21_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : FillBaseRulesImpl
				.pattern_FillBaseRules_21_2_testcorematchandDECs_blackFFB(_edge_tggRule)) {
			TGGRule rule = (TGGRule) result2_black[0];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_black[1];
			Object[] result2_green = FillBaseRulesImpl
					.pattern_FillBaseRules_21_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (FillBaseRulesImpl.pattern_FillBaseRules_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
					this, match, rule, tripleGraphGrammar)) {
				// Ensure that the correct types of elements are matched
				if (FillBaseRulesImpl
						.pattern_FillBaseRules_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(this,
								match)) {

					// Add match to rule result
					Object[] result5_black = FillBaseRulesImpl
							.pattern_FillBaseRules_21_5_Addmatchtoruleresult_blackBBBB(match, __performOperation,
									__result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					FillBaseRulesImpl.pattern_FillBaseRules_21_5_Addmatchtoruleresult_greenBBBB(match,
							__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return FillBaseRulesImpl.pattern_FillBaseRules_21_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintsRuleResult checkAttributes_FWD(TripleMatch __tripleMatch) {
		AttributeConstraintsRuleResult ruleResult = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE
				.createAttributeConstraintsRuleResult();
		ruleResult.setRule("FillBaseRules");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox", true, csp);
		var_ruleBox_label.setValue(__helper.getValue("ruleBox", "label"));
		var_ruleBox_label.setType("String");

		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule", true, csp);
		var_rule_name.setValue(__helper.getValue("rule", "name"));
		var_rule_name.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		eq0.setRuleName("FillBaseRules");
		eq0.solve(var_ruleBox_label, var_rule_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			var_rule_name.setBound(false);
			eq0.solve(var_ruleBox_label, var_rule_name);
			if (csp.check()) {
				ruleResult.setSuccess(true);
				ruleResult.setRequiredChange(true);
				__helper.setValue("rule", "name", var_rule_name.getValue());
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
		ruleResult.setRule("FillBaseRules");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox", true, csp);
		var_ruleBox_label.setValue(__helper.getValue("ruleBox", "label"));
		var_ruleBox_label.setType("String");

		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule", true, csp);
		var_rule_name.setValue(__helper.getValue("rule", "name"));
		var_rule_name.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		eq0.setRuleName("FillBaseRules");
		eq0.solve(var_ruleBox_label, var_rule_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			var_ruleBox_label.setBound(false);
			eq0.solve(var_ruleBox_label, var_rule_name);
			if (csp.check()) {
				ruleResult.setSuccess(true);
				ruleResult.setRequiredChange(true);
				__helper.setValue("ruleBox", "label", var_ruleBox_label.getValue());
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
		Object[] result1_black = FillBaseRulesImpl.pattern_FillBaseRules_24_1_prepare_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [prepare] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = FillBaseRulesImpl.pattern_FillBaseRules_24_1_prepare_greenF();
		IsApplicableRuleResult result = (IsApplicableRuleResult) result1_green[0];

		// match src trg context
		Object[] result2_bindingAndBlack = FillBaseRulesImpl
				.pattern_FillBaseRules_24_2_matchsrctrgcontext_bindingAndBlackFFFFBB(sourceMatch, targetMatch);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [match src trg context] failed." + " Variables: "
					+ "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = " + targetMatch + ".");
		}
		TGGRule rule = (TGGRule) result2_bindingAndBlack[0];
		DirectedGraph directedGraph = (DirectedGraph) result2_bindingAndBlack[1];
		Box ruleBox = (Box) result2_bindingAndBlack[2];
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_bindingAndBlack[3];

		// solve csp
		Object[] result3_bindingAndBlack = FillBaseRulesImpl
				.pattern_FillBaseRules_24_3_solvecsp_bindingAndBlackFBBBBBBB(this, rule, directedGraph, ruleBox,
						tripleGraphGrammar, sourceMatch, targetMatch);
		if (result3_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [solve csp] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[rule] = " + rule + ", " + "[directedGraph] = " + directedGraph + ", "
					+ "[ruleBox] = " + ruleBox + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ", "
					+ "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = " + targetMatch + ".");
		}
		CSP csp = (CSP) result3_bindingAndBlack[0];
		// check CSP
		if (FillBaseRulesImpl.pattern_FillBaseRules_24_4_checkCSP_expressionFB(csp)) {
			// ForEach match corr context
			for (Object[] result5_black : FillBaseRulesImpl.pattern_FillBaseRules_24_5_matchcorrcontext_blackFBBBB(
					directedGraph, tripleGraphGrammar, sourceMatch, targetMatch)) {
				DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result5_black[0];
				Object[] result5_green = FillBaseRulesImpl.pattern_FillBaseRules_24_5_matchcorrcontext_greenBBBF(
						directedGraphToTripleGraphGrammar, sourceMatch, targetMatch);
				CCMatch ccMatch = (CCMatch) result5_green[3];

				// create correspondence
				Object[] result6_black = FillBaseRulesImpl.pattern_FillBaseRules_24_6_createcorrespondence_blackBBBBB(
						rule, directedGraph, ruleBox, tripleGraphGrammar, ccMatch);
				if (result6_black == null) {
					throw new RuntimeException("Pattern matching in node [create correspondence] failed."
							+ " Variables: " + "[rule] = " + rule + ", " + "[directedGraph] = " + directedGraph + ", "
							+ "[ruleBox] = " + ruleBox + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ", "
							+ "[ccMatch] = " + ccMatch + ".");
				}
				FillBaseRulesImpl.pattern_FillBaseRules_24_6_createcorrespondence_greenFBBB(rule, ruleBox, ccMatch);
				// NodeToRule nodeToRule = (NodeToRule) result6_green[0];

				// add to returned result
				Object[] result7_black = FillBaseRulesImpl
						.pattern_FillBaseRules_24_7_addtoreturnedresult_blackBB(result, ccMatch);
				if (result7_black == null) {
					throw new RuntimeException("Pattern matching in node [add to returned result] failed."
							+ " Variables: " + "[result] = " + result + ", " + "[ccMatch] = " + ccMatch + ".");
				}
				FillBaseRulesImpl.pattern_FillBaseRules_24_7_addtoreturnedresult_greenBB(result, ccMatch);

			}

		} else {
		}
		return FillBaseRulesImpl.pattern_FillBaseRules_24_8_expressionFB(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isApplicable_solveCsp_CC(TGGRule rule, DirectedGraph directedGraph, Box ruleBox,
			TripleGraphGrammar tripleGraphGrammar, Match sourceMatch, Match targetMatch) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();

		// Create literals

		// Create attribute variables
		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox.label", true, csp);
		var_ruleBox_label.setValue(ruleBox.getLabel());
		var_ruleBox_label.setType("String");
		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule.name", true, csp);
		var_rule_name.setValue(rule.getName());
		var_rule_name.setType("String");

		// Create unbound variables

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_ruleBox_label, var_rule_name);
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
	public boolean checkDEC_FWD(DirectedGraph directedGraph, Box ruleBox) {// match tgg pattern
		Object[] result1_black = FillBaseRulesImpl.pattern_FillBaseRules_27_1_matchtggpattern_blackBB(directedGraph,
				ruleBox);
		if (result1_black != null) {
			return FillBaseRulesImpl.pattern_FillBaseRules_27_2_expressionF();
		} else {
			return FillBaseRulesImpl.pattern_FillBaseRules_27_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDEC_BWD(TGGRule rule, TripleGraphGrammar tripleGraphGrammar) {// match tgg pattern
		Object[] result1_black = FillBaseRulesImpl.pattern_FillBaseRules_28_1_matchtggpattern_blackBB(rule,
				tripleGraphGrammar);
		if (result1_black != null) {
			return FillBaseRulesImpl.pattern_FillBaseRules_28_2_expressionF();
		} else {
			return FillBaseRulesImpl.pattern_FillBaseRules_28_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelgeneratorRuleResult generateModel(RuleEntryContainer ruleEntryContainer,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammarParameter) {
		// create result
		Object[] result1_black = FillBaseRulesImpl.pattern_FillBaseRules_29_1_createresult_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [create result] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = FillBaseRulesImpl.pattern_FillBaseRules_29_1_createresult_greenFF();
		IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result1_green[0];
		ModelgeneratorRuleResult ruleResult = (ModelgeneratorRuleResult) result1_green[1];

		// ForEach is applicable core
		for (Object[] result2_black : FillBaseRulesImpl
				.pattern_FillBaseRules_29_2_isapplicablecore_blackFFFFBB(ruleEntryContainer, ruleResult)) {
			// RuleEntryList directedGraphToTripleGraphGrammarList = (RuleEntryList) result2_black[0];
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result2_black[1];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_black[2];
			DirectedGraph directedGraph = (DirectedGraph) result2_black[3];

			// solve CSP
			Object[] result3_bindingAndBlack = FillBaseRulesImpl
					.pattern_FillBaseRules_29_3_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch,
							directedGraphToTripleGraphGrammar, directedGraph, tripleGraphGrammar, ruleResult);
			if (result3_bindingAndBlack == null) {
				throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: " + "[this] = "
						+ this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
						+ "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ", "
						+ "[directedGraph] = " + directedGraph + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar
						+ ", " + "[ruleResult] = " + ruleResult + ".");
			}
			CSP csp = (CSP) result3_bindingAndBlack[0];
			// check CSP
			if (FillBaseRulesImpl.pattern_FillBaseRules_29_4_checkCSP_expressionFBB(this, csp)) {
				// check nacs
				Object[] result5_black = FillBaseRulesImpl.pattern_FillBaseRules_29_5_checknacs_blackBBB(
						directedGraphToTripleGraphGrammar, directedGraph, tripleGraphGrammar);
				if (result5_black != null) {

					// perform
					Object[] result6_black = FillBaseRulesImpl.pattern_FillBaseRules_29_6_perform_blackBBBB(
							directedGraphToTripleGraphGrammar, directedGraph, tripleGraphGrammar, ruleResult);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [perform] failed." + " Variables: "
								+ "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ", "
								+ "[directedGraph] = " + directedGraph + ", " + "[tripleGraphGrammar] = "
								+ tripleGraphGrammar + ", " + "[ruleResult] = " + ruleResult + ".");
					}
					FillBaseRulesImpl.pattern_FillBaseRules_29_6_perform_greenFFBFBBB(directedGraph, tripleGraphGrammar,
							ruleResult, csp);
					// NodeToRule nodeToRule = (NodeToRule) result6_green[0];
					// TGGRule rule = (TGGRule) result6_green[1];
					// Box ruleBox = (Box) result6_green[3];

				} else {
				}

			} else {
			}

		}
		return FillBaseRulesImpl.pattern_FillBaseRules_29_7_expressionFB(ruleResult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP generateModel_solveCsp_BWD(IsApplicableMatch isApplicableMatch,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, DirectedGraph directedGraph,
			TripleGraphGrammar tripleGraphGrammar, ModelgeneratorRuleResult ruleResult) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables

		// Create unbound variables
		Variable var_ruleBox_label = CSPFactoryHelper.eINSTANCE.createVariable("ruleBox.label", csp);
		var_ruleBox_label.setType("String");
		Variable var_rule_name = CSPFactoryHelper.eINSTANCE.createVariable("rule.name", csp);
		var_rule_name.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_ruleBox_label, var_rule_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);
		isApplicableMatch.registerObject("directedGraph", directedGraph);
		isApplicableMatch.registerObject("tripleGraphGrammar", tripleGraphGrammar);
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
		case RulesPackage.FILL_BASE_RULES___IS_APPROPRIATE_FWD__MATCH_DIRECTEDGRAPH_BOX:
			return isAppropriate_FWD((Match) arguments.get(0), (DirectedGraph) arguments.get(1),
					(Box) arguments.get(2));
		case RulesPackage.FILL_BASE_RULES___PERFORM_FWD__ISAPPLICABLEMATCH:
			return perform_FWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___IS_APPLICABLE_FWD__MATCH:
			return isApplicable_FWD((Match) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_DIRECTEDGRAPH_BOX:
			registerObjectsToMatch_FWD((Match) arguments.get(0), (DirectedGraph) arguments.get(1),
					(Box) arguments.get(2));
			return null;
		case RulesPackage.FILL_BASE_RULES___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_DIRECTEDGRAPH_BOX:
			return isAppropriate_solveCsp_FWD((Match) arguments.get(0), (DirectedGraph) arguments.get(1),
					(Box) arguments.get(2));
		case RulesPackage.FILL_BASE_RULES___IS_APPROPRIATE_CHECK_CSP_FWD__CSP:
			return isAppropriate_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_BOX_TRIPLEGRAPHGRAMMAR:
			return isApplicable_solveCsp_FWD((IsApplicableMatch) arguments.get(0),
					(DirectedGraphToTripleGraphGrammar) arguments.get(1), (DirectedGraph) arguments.get(2),
					(Box) arguments.get(3), (TripleGraphGrammar) arguments.get(4));
		case RulesPackage.FILL_BASE_RULES___IS_APPLICABLE_CHECK_CSP_FWD__CSP:
			return isApplicable_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_FWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6));
			return null;
		case RulesPackage.FILL_BASE_RULES___CHECK_TYPES_FWD__MATCH:
			return checkTypes_FWD((Match) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___IS_APPROPRIATE_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR:
			return isAppropriate_BWD((Match) arguments.get(0), (TGGRule) arguments.get(1),
					(TripleGraphGrammar) arguments.get(2));
		case RulesPackage.FILL_BASE_RULES___PERFORM_BWD__ISAPPLICABLEMATCH:
			return perform_BWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___IS_APPLICABLE_BWD__MATCH:
			return isApplicable_BWD((Match) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR:
			registerObjectsToMatch_BWD((Match) arguments.get(0), (TGGRule) arguments.get(1),
					(TripleGraphGrammar) arguments.get(2));
			return null;
		case RulesPackage.FILL_BASE_RULES___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TGGRULE_TRIPLEGRAPHGRAMMAR:
			return isAppropriate_solveCsp_BWD((Match) arguments.get(0), (TGGRule) arguments.get(1),
					(TripleGraphGrammar) arguments.get(2));
		case RulesPackage.FILL_BASE_RULES___IS_APPROPRIATE_CHECK_CSP_BWD__CSP:
			return isAppropriate_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_TGGRULE_DIRECTEDGRAPH_TRIPLEGRAPHGRAMMAR:
			return isApplicable_solveCsp_BWD((IsApplicableMatch) arguments.get(0),
					(DirectedGraphToTripleGraphGrammar) arguments.get(1), (TGGRule) arguments.get(2),
					(DirectedGraph) arguments.get(3), (TripleGraphGrammar) arguments.get(4));
		case RulesPackage.FILL_BASE_RULES___IS_APPLICABLE_CHECK_CSP_BWD__CSP:
			return isApplicable_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_BWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6));
			return null;
		case RulesPackage.FILL_BASE_RULES___CHECK_TYPES_BWD__MATCH:
			return checkTypes_BWD((Match) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___IS_APPROPRIATE_FWD_EMOFLON_EDGE_58__EMOFLONEDGE:
			return isAppropriate_FWD_EMoflonEdge_58((EMoflonEdge) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___IS_APPROPRIATE_BWD_EMOFLON_EDGE_83__EMOFLONEDGE:
			return isAppropriate_BWD_EMoflonEdge_83((EMoflonEdge) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH:
			return checkAttributes_FWD((TripleMatch) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH:
			return checkAttributes_BWD((TripleMatch) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___IS_APPLICABLE_CC__MATCH_MATCH:
			return isApplicable_CC((Match) arguments.get(0), (Match) arguments.get(1));
		case RulesPackage.FILL_BASE_RULES___IS_APPLICABLE_SOLVE_CSP_CC__TGGRULE_DIRECTEDGRAPH_BOX_TRIPLEGRAPHGRAMMAR_MATCH_MATCH:
			return isApplicable_solveCsp_CC((TGGRule) arguments.get(0), (DirectedGraph) arguments.get(1),
					(Box) arguments.get(2), (TripleGraphGrammar) arguments.get(3), (Match) arguments.get(4),
					(Match) arguments.get(5));
		case RulesPackage.FILL_BASE_RULES___IS_APPLICABLE_CHECK_CSP_CC__CSP:
			return isApplicable_checkCsp_CC((CSP) arguments.get(0));
		case RulesPackage.FILL_BASE_RULES___CHECK_DEC_FWD__DIRECTEDGRAPH_BOX:
			return checkDEC_FWD((DirectedGraph) arguments.get(0), (Box) arguments.get(1));
		case RulesPackage.FILL_BASE_RULES___CHECK_DEC_BWD__TGGRULE_TRIPLEGRAPHGRAMMAR:
			return checkDEC_BWD((TGGRule) arguments.get(0), (TripleGraphGrammar) arguments.get(1));
		case RulesPackage.FILL_BASE_RULES___GENERATE_MODEL__RULEENTRYCONTAINER_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR:
			return generateModel((RuleEntryContainer) arguments.get(0),
					(DirectedGraphToTripleGraphGrammar) arguments.get(1));
		case RulesPackage.FILL_BASE_RULES___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_DIRECTEDGRAPHTOTRIPLEGRAPHGRAMMAR_DIRECTEDGRAPH_TRIPLEGRAPHGRAMMAR_MODELGENERATORRULERESULT:
			return generateModel_solveCsp_BWD((IsApplicableMatch) arguments.get(0),
					(DirectedGraphToTripleGraphGrammar) arguments.get(1), (DirectedGraph) arguments.get(2),
					(TripleGraphGrammar) arguments.get(3), (ModelgeneratorRuleResult) arguments.get(4));
		case RulesPackage.FILL_BASE_RULES___GENERATE_MODEL_CHECK_CSP_BWD__CSP:
			return generateModel_checkCsp_BWD((CSP) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	public static final Object[] pattern_FillBaseRules_0_1_initialbindings_blackBBBB(FillBaseRules _this, Match match,
			DirectedGraph directedGraph, Box ruleBox) {
		return new Object[] { _this, match, directedGraph, ruleBox };
	}

	public static final Object[] pattern_FillBaseRules_0_2_SolveCSP_bindingFBBBB(FillBaseRules _this, Match match,
			DirectedGraph directedGraph, Box ruleBox) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_FWD(match, directedGraph, ruleBox);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, directedGraph, ruleBox };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_0_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_FillBaseRules_0_2_SolveCSP_bindingAndBlackFBBBB(FillBaseRules _this,
			Match match, DirectedGraph directedGraph, Box ruleBox) {
		Object[] result_pattern_FillBaseRules_0_2_SolveCSP_binding = pattern_FillBaseRules_0_2_SolveCSP_bindingFBBBB(
				_this, match, directedGraph, ruleBox);
		if (result_pattern_FillBaseRules_0_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_FillBaseRules_0_2_SolveCSP_binding[0];

			Object[] result_pattern_FillBaseRules_0_2_SolveCSP_black = pattern_FillBaseRules_0_2_SolveCSP_blackB(csp);
			if (result_pattern_FillBaseRules_0_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, directedGraph, ruleBox };
			}
		}
		return null;
	}

	public static final boolean pattern_FillBaseRules_0_3_CheckCSP_expressionFBB(FillBaseRules _this, CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_0_4_collectelementstobetranslated_blackBBB(Match match,
			DirectedGraph directedGraph, Box ruleBox) {
		return new Object[] { match, directedGraph, ruleBox };
	}

	public static final Object[] pattern_FillBaseRules_0_4_collectelementstobetranslated_greenBBBFF(Match match,
			DirectedGraph directedGraph, Box ruleBox) {
		EMoflonEdge directedGraph__ruleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ruleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getToBeTranslatedNodes().add(ruleBox);
		String directedGraph__ruleBox____nodes_name_prime = "nodes";
		String ruleBox__directedGraph____graph_name_prime = "graph";
		directedGraph__ruleBox____nodes.setSrc(directedGraph);
		directedGraph__ruleBox____nodes.setTrg(ruleBox);
		match.getToBeTranslatedEdges().add(directedGraph__ruleBox____nodes);
		ruleBox__directedGraph____graph.setSrc(ruleBox);
		ruleBox__directedGraph____graph.setTrg(directedGraph);
		match.getToBeTranslatedEdges().add(ruleBox__directedGraph____graph);
		directedGraph__ruleBox____nodes.setName(directedGraph__ruleBox____nodes_name_prime);
		ruleBox__directedGraph____graph.setName(ruleBox__directedGraph____graph_name_prime);
		return new Object[] { match, directedGraph, ruleBox, directedGraph__ruleBox____nodes,
				ruleBox__directedGraph____graph };
	}

	public static final Object[] pattern_FillBaseRules_0_5_collectcontextelements_blackBBB(Match match,
			DirectedGraph directedGraph, Box ruleBox) {
		return new Object[] { match, directedGraph, ruleBox };
	}

	public static final Object[] pattern_FillBaseRules_0_5_collectcontextelements_greenBB(Match match,
			DirectedGraph directedGraph) {
		match.getContextNodes().add(directedGraph);
		return new Object[] { match, directedGraph };
	}

	public static final void pattern_FillBaseRules_0_6_registerobjectstomatch_expressionBBBB(FillBaseRules _this,
			Match match, DirectedGraph directedGraph, Box ruleBox) {
		_this.registerObjectsToMatch_FWD(match, directedGraph, ruleBox);

	}

	public static final boolean pattern_FillBaseRules_0_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_FillBaseRules_0_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_1_1_performtransformation_bindingFFFFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("directedGraphToTripleGraphGrammar");
		EObject _localVariable_1 = isApplicableMatch.getObject("directedGraph");
		EObject _localVariable_2 = isApplicableMatch.getObject("ruleBox");
		EObject _localVariable_3 = isApplicableMatch.getObject("tripleGraphGrammar");
		EObject tmpDirectedGraphToTripleGraphGrammar = _localVariable_0;
		EObject tmpDirectedGraph = _localVariable_1;
		EObject tmpRuleBox = _localVariable_2;
		EObject tmpTripleGraphGrammar = _localVariable_3;
		if (tmpDirectedGraphToTripleGraphGrammar instanceof DirectedGraphToTripleGraphGrammar) {
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) tmpDirectedGraphToTripleGraphGrammar;
			if (tmpDirectedGraph instanceof DirectedGraph) {
				DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
				if (tmpRuleBox instanceof Box) {
					Box ruleBox = (Box) tmpRuleBox;
					if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
						TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
						return new Object[] { directedGraphToTripleGraphGrammar, directedGraph, ruleBox,
								tripleGraphGrammar, isApplicableMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_1_1_performtransformation_blackBBBBFBB(
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, DirectedGraph directedGraph,
			Box ruleBox, TripleGraphGrammar tripleGraphGrammar, FillBaseRules _this,
			IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { directedGraphToTripleGraphGrammar, directedGraph, ruleBox, tripleGraphGrammar,
						csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_1_1_performtransformation_bindingAndBlackFFFFFBB(
			FillBaseRules _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_FillBaseRules_1_1_performtransformation_binding = pattern_FillBaseRules_1_1_performtransformation_bindingFFFFB(
				isApplicableMatch);
		if (result_pattern_FillBaseRules_1_1_performtransformation_binding != null) {
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result_pattern_FillBaseRules_1_1_performtransformation_binding[0];
			DirectedGraph directedGraph = (DirectedGraph) result_pattern_FillBaseRules_1_1_performtransformation_binding[1];
			Box ruleBox = (Box) result_pattern_FillBaseRules_1_1_performtransformation_binding[2];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result_pattern_FillBaseRules_1_1_performtransformation_binding[3];

			Object[] result_pattern_FillBaseRules_1_1_performtransformation_black = pattern_FillBaseRules_1_1_performtransformation_blackBBBBFBB(
					directedGraphToTripleGraphGrammar, directedGraph, ruleBox, tripleGraphGrammar, _this,
					isApplicableMatch);
			if (result_pattern_FillBaseRules_1_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_FillBaseRules_1_1_performtransformation_black[4];

				return new Object[] { directedGraphToTripleGraphGrammar, directedGraph, ruleBox, tripleGraphGrammar,
						csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_1_1_performtransformation_greenFFBBB(Box ruleBox,
			TripleGraphGrammar tripleGraphGrammar, CSP csp) {
		NodeToRule nodeToRule = SchemaFactory.eINSTANCE.createNodeToRule();
		TGGRule rule = LanguageFactory.eINSTANCE.createTGGRule();
		Object _localVariable_0 = csp.getValue("rule", "name");
		nodeToRule.setSource(ruleBox);
		nodeToRule.setTarget(rule);
		tripleGraphGrammar.getTggRule().add(rule);
		String rule_name_prime = (String) _localVariable_0;
		rule.setName(rule_name_prime);
		return new Object[] { nodeToRule, rule, ruleBox, tripleGraphGrammar, csp };
	}

	public static final Object[] pattern_FillBaseRules_1_2_collecttranslatedelements_blackBBB(NodeToRule nodeToRule,
			TGGRule rule, Box ruleBox) {
		return new Object[] { nodeToRule, rule, ruleBox };
	}

	public static final Object[] pattern_FillBaseRules_1_2_collecttranslatedelements_greenFBBB(NodeToRule nodeToRule,
			TGGRule rule, Box ruleBox) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getCreatedLinkElements().add(nodeToRule);
		ruleresult.getCreatedElements().add(rule);
		ruleresult.getTranslatedElements().add(ruleBox);
		return new Object[] { ruleresult, nodeToRule, rule, ruleBox };
	}

	public static final Object[] pattern_FillBaseRules_1_3_bookkeepingforedges_blackBBBBBBB(
			PerformRuleResult ruleresult, EObject nodeToRule, EObject directedGraphToTripleGraphGrammar, EObject rule,
			EObject directedGraph, EObject ruleBox, EObject tripleGraphGrammar) {
		if (!nodeToRule.equals(rule)) {
			if (!nodeToRule.equals(ruleBox)) {
				if (!nodeToRule.equals(tripleGraphGrammar)) {
					if (!directedGraphToTripleGraphGrammar.equals(nodeToRule)) {
						if (!directedGraphToTripleGraphGrammar.equals(rule)) {
							if (!directedGraphToTripleGraphGrammar.equals(ruleBox)) {
								if (!directedGraphToTripleGraphGrammar.equals(tripleGraphGrammar)) {
									if (!rule.equals(ruleBox)) {
										if (!rule.equals(tripleGraphGrammar)) {
											if (!directedGraph.equals(nodeToRule)) {
												if (!directedGraph.equals(directedGraphToTripleGraphGrammar)) {
													if (!directedGraph.equals(rule)) {
														if (!directedGraph.equals(ruleBox)) {
															if (!directedGraph.equals(tripleGraphGrammar)) {
																if (!ruleBox.equals(tripleGraphGrammar)) {
																	return new Object[] { ruleresult, nodeToRule,
																			directedGraphToTripleGraphGrammar, rule,
																			directedGraph, ruleBox,
																			tripleGraphGrammar };
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

	public static final Object[] pattern_FillBaseRules_1_3_bookkeepingforedges_greenBBBBBBFFFFFF(
			PerformRuleResult ruleresult, EObject nodeToRule, EObject rule, EObject directedGraph, EObject ruleBox,
			EObject tripleGraphGrammar) {
		EMoflonEdge nodeToRule__rule____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge nodeToRule__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__ruleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ruleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge tripleGraphGrammar__rule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "FillBaseRules";
		String nodeToRule__rule____target_name_prime = "target";
		String nodeToRule__ruleBox____source_name_prime = "source";
		String directedGraph__ruleBox____nodes_name_prime = "nodes";
		String ruleBox__directedGraph____graph_name_prime = "graph";
		String tripleGraphGrammar__rule____tggRule_name_prime = "tggRule";
		String rule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		nodeToRule__rule____target.setSrc(nodeToRule);
		nodeToRule__rule____target.setTrg(rule);
		ruleresult.getCreatedEdges().add(nodeToRule__rule____target);
		nodeToRule__ruleBox____source.setSrc(nodeToRule);
		nodeToRule__ruleBox____source.setTrg(ruleBox);
		ruleresult.getCreatedEdges().add(nodeToRule__ruleBox____source);
		directedGraph__ruleBox____nodes.setSrc(directedGraph);
		directedGraph__ruleBox____nodes.setTrg(ruleBox);
		ruleresult.getTranslatedEdges().add(directedGraph__ruleBox____nodes);
		ruleBox__directedGraph____graph.setSrc(ruleBox);
		ruleBox__directedGraph____graph.setTrg(directedGraph);
		ruleresult.getTranslatedEdges().add(ruleBox__directedGraph____graph);
		tripleGraphGrammar__rule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setTrg(rule);
		ruleresult.getCreatedEdges().add(tripleGraphGrammar__rule____tggRule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setSrc(rule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		ruleresult.getCreatedEdges().add(rule__tripleGraphGrammar____tripleGraphGrammar);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		nodeToRule__rule____target.setName(nodeToRule__rule____target_name_prime);
		nodeToRule__ruleBox____source.setName(nodeToRule__ruleBox____source_name_prime);
		directedGraph__ruleBox____nodes.setName(directedGraph__ruleBox____nodes_name_prime);
		ruleBox__directedGraph____graph.setName(ruleBox__directedGraph____graph_name_prime);
		tripleGraphGrammar__rule____tggRule.setName(tripleGraphGrammar__rule____tggRule_name_prime);
		rule__tripleGraphGrammar____tripleGraphGrammar
				.setName(rule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		return new Object[] { ruleresult, nodeToRule, rule, directedGraph, ruleBox, tripleGraphGrammar,
				nodeToRule__rule____target, nodeToRule__ruleBox____source, directedGraph__ruleBox____nodes,
				ruleBox__directedGraph____graph, tripleGraphGrammar__rule____tggRule,
				rule__tripleGraphGrammar____tripleGraphGrammar };
	}

	public static final void pattern_FillBaseRules_1_5_registerobjects_expressionBBBBBBBB(FillBaseRules _this,
			PerformRuleResult ruleresult, EObject nodeToRule, EObject directedGraphToTripleGraphGrammar, EObject rule,
			EObject directedGraph, EObject ruleBox, EObject tripleGraphGrammar) {
		_this.registerObjects_FWD(ruleresult, nodeToRule, directedGraphToTripleGraphGrammar, rule, directedGraph,
				ruleBox, tripleGraphGrammar);

	}

	public static final PerformRuleResult pattern_FillBaseRules_1_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_2_1_preparereturnvalue_bindingFB(FillBaseRules _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_2_1_preparereturnvalue_blackFBB(EClass eClass,
			FillBaseRules _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_FWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_2_1_preparereturnvalue_bindingAndBlackFFB(FillBaseRules _this) {
		Object[] result_pattern_FillBaseRules_2_1_preparereturnvalue_binding = pattern_FillBaseRules_2_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_FillBaseRules_2_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_FillBaseRules_2_1_preparereturnvalue_binding[0];

			Object[] result_pattern_FillBaseRules_2_1_preparereturnvalue_black = pattern_FillBaseRules_2_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_FillBaseRules_2_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_FillBaseRules_2_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_2_1_preparereturnvalue_greenBF(EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "FillBaseRules";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_FillBaseRules_2_2_corematch_bindingFFB(Match match) {
		EObject _localVariable_0 = match.getObject("directedGraph");
		EObject _localVariable_1 = match.getObject("ruleBox");
		EObject tmpDirectedGraph = _localVariable_0;
		EObject tmpRuleBox = _localVariable_1;
		if (tmpDirectedGraph instanceof DirectedGraph) {
			DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
			if (tmpRuleBox instanceof Box) {
				Box ruleBox = (Box) tmpRuleBox;
				return new Object[] { directedGraph, ruleBox, match };
			}
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_FillBaseRules_2_2_corematch_blackFBBFB(DirectedGraph directedGraph,
			Box ruleBox, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar : org.moflon.core.utilities.eMoflonEMFUtil
				.getOppositeReferenceTyped(directedGraph, DirectedGraphToTripleGraphGrammar.class, "source")) {
			TripleGraphGrammar tripleGraphGrammar = directedGraphToTripleGraphGrammar.getTarget();
			if (tripleGraphGrammar != null) {
				_result.add(new Object[] { directedGraphToTripleGraphGrammar, directedGraph, ruleBox,
						tripleGraphGrammar, match });
			}

		}
		return _result;
	}

	public static final Iterable<Object[]> pattern_FillBaseRules_2_3_findcontext_blackBBBB(
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, DirectedGraph directedGraph,
			Box ruleBox, TripleGraphGrammar tripleGraphGrammar) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (tripleGraphGrammar.equals(directedGraphToTripleGraphGrammar.getTarget())) {
			if (directedGraph.getNodes().contains(ruleBox)) {
				if (directedGraph.equals(directedGraphToTripleGraphGrammar.getSource())) {
					_result.add(new Object[] { directedGraphToTripleGraphGrammar, directedGraph, ruleBox,
							tripleGraphGrammar });
				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_2_3_findcontext_greenBBBBFFFFF(
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, DirectedGraph directedGraph,
			Box ruleBox, TripleGraphGrammar tripleGraphGrammar) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		EMoflonEdge directedGraph__ruleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ruleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		String directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime = "target";
		String directedGraph__ruleBox____nodes_name_prime = "nodes";
		String ruleBox__directedGraph____graph_name_prime = "graph";
		String directedGraphToTripleGraphGrammar__directedGraph____source_name_prime = "source";
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(directedGraph);
		isApplicableMatch.getAllContextElements().add(ruleBox);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target);
		directedGraph__ruleBox____nodes.setSrc(directedGraph);
		directedGraph__ruleBox____nodes.setTrg(ruleBox);
		isApplicableMatch.getAllContextElements().add(directedGraph__ruleBox____nodes);
		ruleBox__directedGraph____graph.setSrc(ruleBox);
		ruleBox__directedGraph____graph.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(ruleBox__directedGraph____graph);
		directedGraphToTripleGraphGrammar__directedGraph____source.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__directedGraph____source.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar__directedGraph____source);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target
				.setName(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime);
		directedGraph__ruleBox____nodes.setName(directedGraph__ruleBox____nodes_name_prime);
		ruleBox__directedGraph____graph.setName(ruleBox__directedGraph____graph_name_prime);
		directedGraphToTripleGraphGrammar__directedGraph____source
				.setName(directedGraphToTripleGraphGrammar__directedGraph____source_name_prime);
		return new Object[] { directedGraphToTripleGraphGrammar, directedGraph, ruleBox, tripleGraphGrammar,
				isApplicableMatch, directedGraphToTripleGraphGrammar__tripleGraphGrammar____target,
				directedGraph__ruleBox____nodes, ruleBox__directedGraph____graph,
				directedGraphToTripleGraphGrammar__directedGraph____source };
	}

	public static final Object[] pattern_FillBaseRules_2_4_solveCSP_bindingFBBBBBB(FillBaseRules _this,
			IsApplicableMatch isApplicableMatch, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar,
			DirectedGraph directedGraph, Box ruleBox, TripleGraphGrammar tripleGraphGrammar) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_FWD(isApplicableMatch, directedGraphToTripleGraphGrammar,
				directedGraph, ruleBox, tripleGraphGrammar);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, directedGraphToTripleGraphGrammar, directedGraph,
					ruleBox, tripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_2_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_FillBaseRules_2_4_solveCSP_bindingAndBlackFBBBBBB(FillBaseRules _this,
			IsApplicableMatch isApplicableMatch, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar,
			DirectedGraph directedGraph, Box ruleBox, TripleGraphGrammar tripleGraphGrammar) {
		Object[] result_pattern_FillBaseRules_2_4_solveCSP_binding = pattern_FillBaseRules_2_4_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, directedGraphToTripleGraphGrammar, directedGraph, ruleBox,
				tripleGraphGrammar);
		if (result_pattern_FillBaseRules_2_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_FillBaseRules_2_4_solveCSP_binding[0];

			Object[] result_pattern_FillBaseRules_2_4_solveCSP_black = pattern_FillBaseRules_2_4_solveCSP_blackB(csp);
			if (result_pattern_FillBaseRules_2_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, directedGraphToTripleGraphGrammar, directedGraph,
						ruleBox, tripleGraphGrammar };
			}
		}
		return null;
	}

	public static final boolean pattern_FillBaseRules_2_5_checkCSP_expressionFBB(FillBaseRules _this, CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_2_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_FillBaseRules_2_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "FillBaseRules";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_FillBaseRules_2_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_10_1_initialbindings_blackBBBB(FillBaseRules _this, Match match,
			TGGRule rule, TripleGraphGrammar tripleGraphGrammar) {
		return new Object[] { _this, match, rule, tripleGraphGrammar };
	}

	public static final Object[] pattern_FillBaseRules_10_2_SolveCSP_bindingFBBBB(FillBaseRules _this, Match match,
			TGGRule rule, TripleGraphGrammar tripleGraphGrammar) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_BWD(match, rule, tripleGraphGrammar);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, rule, tripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_10_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_FillBaseRules_10_2_SolveCSP_bindingAndBlackFBBBB(FillBaseRules _this,
			Match match, TGGRule rule, TripleGraphGrammar tripleGraphGrammar) {
		Object[] result_pattern_FillBaseRules_10_2_SolveCSP_binding = pattern_FillBaseRules_10_2_SolveCSP_bindingFBBBB(
				_this, match, rule, tripleGraphGrammar);
		if (result_pattern_FillBaseRules_10_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_FillBaseRules_10_2_SolveCSP_binding[0];

			Object[] result_pattern_FillBaseRules_10_2_SolveCSP_black = pattern_FillBaseRules_10_2_SolveCSP_blackB(csp);
			if (result_pattern_FillBaseRules_10_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, rule, tripleGraphGrammar };
			}
		}
		return null;
	}

	public static final boolean pattern_FillBaseRules_10_3_CheckCSP_expressionFBB(FillBaseRules _this, CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_10_4_collectelementstobetranslated_blackBBB(Match match,
			TGGRule rule, TripleGraphGrammar tripleGraphGrammar) {
		return new Object[] { match, rule, tripleGraphGrammar };
	}

	public static final Object[] pattern_FillBaseRules_10_4_collectelementstobetranslated_greenBBBFF(Match match,
			TGGRule rule, TripleGraphGrammar tripleGraphGrammar) {
		EMoflonEdge tripleGraphGrammar__rule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getToBeTranslatedNodes().add(rule);
		String tripleGraphGrammar__rule____tggRule_name_prime = "tggRule";
		String rule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		tripleGraphGrammar__rule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setTrg(rule);
		match.getToBeTranslatedEdges().add(tripleGraphGrammar__rule____tggRule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setSrc(rule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		match.getToBeTranslatedEdges().add(rule__tripleGraphGrammar____tripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setName(tripleGraphGrammar__rule____tggRule_name_prime);
		rule__tripleGraphGrammar____tripleGraphGrammar
				.setName(rule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		return new Object[] { match, rule, tripleGraphGrammar, tripleGraphGrammar__rule____tggRule,
				rule__tripleGraphGrammar____tripleGraphGrammar };
	}

	public static final Object[] pattern_FillBaseRules_10_5_collectcontextelements_blackBBB(Match match, TGGRule rule,
			TripleGraphGrammar tripleGraphGrammar) {
		return new Object[] { match, rule, tripleGraphGrammar };
	}

	public static final Object[] pattern_FillBaseRules_10_5_collectcontextelements_greenBB(Match match,
			TripleGraphGrammar tripleGraphGrammar) {
		match.getContextNodes().add(tripleGraphGrammar);
		return new Object[] { match, tripleGraphGrammar };
	}

	public static final void pattern_FillBaseRules_10_6_registerobjectstomatch_expressionBBBB(FillBaseRules _this,
			Match match, TGGRule rule, TripleGraphGrammar tripleGraphGrammar) {
		_this.registerObjectsToMatch_BWD(match, rule, tripleGraphGrammar);

	}

	public static final boolean pattern_FillBaseRules_10_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_FillBaseRules_10_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_11_1_performtransformation_bindingFFFFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("directedGraphToTripleGraphGrammar");
		EObject _localVariable_1 = isApplicableMatch.getObject("rule");
		EObject _localVariable_2 = isApplicableMatch.getObject("directedGraph");
		EObject _localVariable_3 = isApplicableMatch.getObject("tripleGraphGrammar");
		EObject tmpDirectedGraphToTripleGraphGrammar = _localVariable_0;
		EObject tmpRule = _localVariable_1;
		EObject tmpDirectedGraph = _localVariable_2;
		EObject tmpTripleGraphGrammar = _localVariable_3;
		if (tmpDirectedGraphToTripleGraphGrammar instanceof DirectedGraphToTripleGraphGrammar) {
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) tmpDirectedGraphToTripleGraphGrammar;
			if (tmpRule instanceof TGGRule) {
				TGGRule rule = (TGGRule) tmpRule;
				if (tmpDirectedGraph instanceof DirectedGraph) {
					DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
					if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
						TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
						return new Object[] { directedGraphToTripleGraphGrammar, rule, directedGraph,
								tripleGraphGrammar, isApplicableMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_11_1_performtransformation_blackBBBBFBB(
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, TGGRule rule,
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar, FillBaseRules _this,
			IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { directedGraphToTripleGraphGrammar, rule, directedGraph, tripleGraphGrammar, csp,
						_this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_11_1_performtransformation_bindingAndBlackFFFFFBB(
			FillBaseRules _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_FillBaseRules_11_1_performtransformation_binding = pattern_FillBaseRules_11_1_performtransformation_bindingFFFFB(
				isApplicableMatch);
		if (result_pattern_FillBaseRules_11_1_performtransformation_binding != null) {
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result_pattern_FillBaseRules_11_1_performtransformation_binding[0];
			TGGRule rule = (TGGRule) result_pattern_FillBaseRules_11_1_performtransformation_binding[1];
			DirectedGraph directedGraph = (DirectedGraph) result_pattern_FillBaseRules_11_1_performtransformation_binding[2];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result_pattern_FillBaseRules_11_1_performtransformation_binding[3];

			Object[] result_pattern_FillBaseRules_11_1_performtransformation_black = pattern_FillBaseRules_11_1_performtransformation_blackBBBBFBB(
					directedGraphToTripleGraphGrammar, rule, directedGraph, tripleGraphGrammar, _this,
					isApplicableMatch);
			if (result_pattern_FillBaseRules_11_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_FillBaseRules_11_1_performtransformation_black[4];

				return new Object[] { directedGraphToTripleGraphGrammar, rule, directedGraph, tripleGraphGrammar, csp,
						_this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_11_1_performtransformation_greenFBBFB(TGGRule rule,
			DirectedGraph directedGraph, CSP csp) {
		NodeToRule nodeToRule = SchemaFactory.eINSTANCE.createNodeToRule();
		Box ruleBox = org.moflon.ide.visualization.dot.language.LanguageFactory.eINSTANCE.createBox();
		Object _localVariable_0 = csp.getValue("ruleBox", "label");
		nodeToRule.setTarget(rule);
		nodeToRule.setSource(ruleBox);
		directedGraph.getNodes().add(ruleBox);
		String ruleBox_label_prime = (String) _localVariable_0;
		ruleBox.setLabel(ruleBox_label_prime);
		return new Object[] { nodeToRule, rule, directedGraph, ruleBox, csp };
	}

	public static final Object[] pattern_FillBaseRules_11_2_collecttranslatedelements_blackBBB(NodeToRule nodeToRule,
			TGGRule rule, Box ruleBox) {
		return new Object[] { nodeToRule, rule, ruleBox };
	}

	public static final Object[] pattern_FillBaseRules_11_2_collecttranslatedelements_greenFBBB(NodeToRule nodeToRule,
			TGGRule rule, Box ruleBox) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getCreatedLinkElements().add(nodeToRule);
		ruleresult.getTranslatedElements().add(rule);
		ruleresult.getCreatedElements().add(ruleBox);
		return new Object[] { ruleresult, nodeToRule, rule, ruleBox };
	}

	public static final Object[] pattern_FillBaseRules_11_3_bookkeepingforedges_blackBBBBBBB(
			PerformRuleResult ruleresult, EObject nodeToRule, EObject directedGraphToTripleGraphGrammar, EObject rule,
			EObject directedGraph, EObject ruleBox, EObject tripleGraphGrammar) {
		if (!nodeToRule.equals(rule)) {
			if (!nodeToRule.equals(ruleBox)) {
				if (!nodeToRule.equals(tripleGraphGrammar)) {
					if (!directedGraphToTripleGraphGrammar.equals(nodeToRule)) {
						if (!directedGraphToTripleGraphGrammar.equals(rule)) {
							if (!directedGraphToTripleGraphGrammar.equals(ruleBox)) {
								if (!directedGraphToTripleGraphGrammar.equals(tripleGraphGrammar)) {
									if (!rule.equals(ruleBox)) {
										if (!rule.equals(tripleGraphGrammar)) {
											if (!directedGraph.equals(nodeToRule)) {
												if (!directedGraph.equals(directedGraphToTripleGraphGrammar)) {
													if (!directedGraph.equals(rule)) {
														if (!directedGraph.equals(ruleBox)) {
															if (!directedGraph.equals(tripleGraphGrammar)) {
																if (!ruleBox.equals(tripleGraphGrammar)) {
																	return new Object[] { ruleresult, nodeToRule,
																			directedGraphToTripleGraphGrammar, rule,
																			directedGraph, ruleBox,
																			tripleGraphGrammar };
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

	public static final Object[] pattern_FillBaseRules_11_3_bookkeepingforedges_greenBBBBBBFFFFFF(
			PerformRuleResult ruleresult, EObject nodeToRule, EObject rule, EObject directedGraph, EObject ruleBox,
			EObject tripleGraphGrammar) {
		EMoflonEdge nodeToRule__rule____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge nodeToRule__ruleBox____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraph__ruleBox____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ruleBox__directedGraph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge tripleGraphGrammar__rule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "FillBaseRules";
		String nodeToRule__rule____target_name_prime = "target";
		String nodeToRule__ruleBox____source_name_prime = "source";
		String directedGraph__ruleBox____nodes_name_prime = "nodes";
		String ruleBox__directedGraph____graph_name_prime = "graph";
		String tripleGraphGrammar__rule____tggRule_name_prime = "tggRule";
		String rule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		nodeToRule__rule____target.setSrc(nodeToRule);
		nodeToRule__rule____target.setTrg(rule);
		ruleresult.getCreatedEdges().add(nodeToRule__rule____target);
		nodeToRule__ruleBox____source.setSrc(nodeToRule);
		nodeToRule__ruleBox____source.setTrg(ruleBox);
		ruleresult.getCreatedEdges().add(nodeToRule__ruleBox____source);
		directedGraph__ruleBox____nodes.setSrc(directedGraph);
		directedGraph__ruleBox____nodes.setTrg(ruleBox);
		ruleresult.getCreatedEdges().add(directedGraph__ruleBox____nodes);
		ruleBox__directedGraph____graph.setSrc(ruleBox);
		ruleBox__directedGraph____graph.setTrg(directedGraph);
		ruleresult.getCreatedEdges().add(ruleBox__directedGraph____graph);
		tripleGraphGrammar__rule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setTrg(rule);
		ruleresult.getTranslatedEdges().add(tripleGraphGrammar__rule____tggRule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setSrc(rule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		ruleresult.getTranslatedEdges().add(rule__tripleGraphGrammar____tripleGraphGrammar);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		nodeToRule__rule____target.setName(nodeToRule__rule____target_name_prime);
		nodeToRule__ruleBox____source.setName(nodeToRule__ruleBox____source_name_prime);
		directedGraph__ruleBox____nodes.setName(directedGraph__ruleBox____nodes_name_prime);
		ruleBox__directedGraph____graph.setName(ruleBox__directedGraph____graph_name_prime);
		tripleGraphGrammar__rule____tggRule.setName(tripleGraphGrammar__rule____tggRule_name_prime);
		rule__tripleGraphGrammar____tripleGraphGrammar
				.setName(rule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		return new Object[] { ruleresult, nodeToRule, rule, directedGraph, ruleBox, tripleGraphGrammar,
				nodeToRule__rule____target, nodeToRule__ruleBox____source, directedGraph__ruleBox____nodes,
				ruleBox__directedGraph____graph, tripleGraphGrammar__rule____tggRule,
				rule__tripleGraphGrammar____tripleGraphGrammar };
	}

	public static final void pattern_FillBaseRules_11_5_registerobjects_expressionBBBBBBBB(FillBaseRules _this,
			PerformRuleResult ruleresult, EObject nodeToRule, EObject directedGraphToTripleGraphGrammar, EObject rule,
			EObject directedGraph, EObject ruleBox, EObject tripleGraphGrammar) {
		_this.registerObjects_BWD(ruleresult, nodeToRule, directedGraphToTripleGraphGrammar, rule, directedGraph,
				ruleBox, tripleGraphGrammar);

	}

	public static final PerformRuleResult pattern_FillBaseRules_11_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_12_1_preparereturnvalue_bindingFB(FillBaseRules _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_12_1_preparereturnvalue_blackFBB(EClass eClass,
			FillBaseRules _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_BWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_12_1_preparereturnvalue_bindingAndBlackFFB(FillBaseRules _this) {
		Object[] result_pattern_FillBaseRules_12_1_preparereturnvalue_binding = pattern_FillBaseRules_12_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_FillBaseRules_12_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_FillBaseRules_12_1_preparereturnvalue_binding[0];

			Object[] result_pattern_FillBaseRules_12_1_preparereturnvalue_black = pattern_FillBaseRules_12_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_FillBaseRules_12_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_FillBaseRules_12_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_12_1_preparereturnvalue_greenBF(EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "FillBaseRules";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_FillBaseRules_12_2_corematch_bindingFFB(Match match) {
		EObject _localVariable_0 = match.getObject("rule");
		EObject _localVariable_1 = match.getObject("tripleGraphGrammar");
		EObject tmpRule = _localVariable_0;
		EObject tmpTripleGraphGrammar = _localVariable_1;
		if (tmpRule instanceof TGGRule) {
			TGGRule rule = (TGGRule) tmpRule;
			if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
				TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
				return new Object[] { rule, tripleGraphGrammar, match };
			}
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_FillBaseRules_12_2_corematch_blackFBFBB(TGGRule rule,
			TripleGraphGrammar tripleGraphGrammar, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar : org.moflon.core.utilities.eMoflonEMFUtil
				.getOppositeReferenceTyped(tripleGraphGrammar, DirectedGraphToTripleGraphGrammar.class, "target")) {
			DirectedGraph directedGraph = directedGraphToTripleGraphGrammar.getSource();
			if (directedGraph != null) {
				_result.add(new Object[] { directedGraphToTripleGraphGrammar, rule, directedGraph, tripleGraphGrammar,
						match });
			}

		}
		return _result;
	}

	public static final Iterable<Object[]> pattern_FillBaseRules_12_3_findcontext_blackBBBB(
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, TGGRule rule,
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (tripleGraphGrammar.equals(directedGraphToTripleGraphGrammar.getTarget())) {
			if (tripleGraphGrammar.getTggRule().contains(rule)) {
				if (directedGraph.equals(directedGraphToTripleGraphGrammar.getSource())) {
					_result.add(new Object[] { directedGraphToTripleGraphGrammar, rule, directedGraph,
							tripleGraphGrammar });
				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_12_3_findcontext_greenBBBBFFFFF(
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, TGGRule rule,
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		EMoflonEdge tripleGraphGrammar__rule____tggRule = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rule__tripleGraphGrammar____tripleGraphGrammar = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		String directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime = "target";
		String tripleGraphGrammar__rule____tggRule_name_prime = "tggRule";
		String rule__tripleGraphGrammar____tripleGraphGrammar_name_prime = "tripleGraphGrammar";
		String directedGraphToTripleGraphGrammar__directedGraph____source_name_prime = "source";
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(rule);
		isApplicableMatch.getAllContextElements().add(directedGraph);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target);
		tripleGraphGrammar__rule____tggRule.setSrc(tripleGraphGrammar);
		tripleGraphGrammar__rule____tggRule.setTrg(rule);
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar__rule____tggRule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setSrc(rule);
		rule__tripleGraphGrammar____tripleGraphGrammar.setTrg(tripleGraphGrammar);
		isApplicableMatch.getAllContextElements().add(rule__tripleGraphGrammar____tripleGraphGrammar);
		directedGraphToTripleGraphGrammar__directedGraph____source.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__directedGraph____source.setTrg(directedGraph);
		isApplicableMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar__directedGraph____source);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target
				.setName(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime);
		tripleGraphGrammar__rule____tggRule.setName(tripleGraphGrammar__rule____tggRule_name_prime);
		rule__tripleGraphGrammar____tripleGraphGrammar
				.setName(rule__tripleGraphGrammar____tripleGraphGrammar_name_prime);
		directedGraphToTripleGraphGrammar__directedGraph____source
				.setName(directedGraphToTripleGraphGrammar__directedGraph____source_name_prime);
		return new Object[] { directedGraphToTripleGraphGrammar, rule, directedGraph, tripleGraphGrammar,
				isApplicableMatch, directedGraphToTripleGraphGrammar__tripleGraphGrammar____target,
				tripleGraphGrammar__rule____tggRule, rule__tripleGraphGrammar____tripleGraphGrammar,
				directedGraphToTripleGraphGrammar__directedGraph____source };
	}

	public static final Object[] pattern_FillBaseRules_12_4_solveCSP_bindingFBBBBBB(FillBaseRules _this,
			IsApplicableMatch isApplicableMatch, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar,
			TGGRule rule, DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_BWD(isApplicableMatch, directedGraphToTripleGraphGrammar,
				rule, directedGraph, tripleGraphGrammar);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, directedGraphToTripleGraphGrammar, rule, directedGraph,
					tripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_12_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_FillBaseRules_12_4_solveCSP_bindingAndBlackFBBBBBB(FillBaseRules _this,
			IsApplicableMatch isApplicableMatch, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar,
			TGGRule rule, DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar) {
		Object[] result_pattern_FillBaseRules_12_4_solveCSP_binding = pattern_FillBaseRules_12_4_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, directedGraphToTripleGraphGrammar, rule, directedGraph, tripleGraphGrammar);
		if (result_pattern_FillBaseRules_12_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_FillBaseRules_12_4_solveCSP_binding[0];

			Object[] result_pattern_FillBaseRules_12_4_solveCSP_black = pattern_FillBaseRules_12_4_solveCSP_blackB(csp);
			if (result_pattern_FillBaseRules_12_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, directedGraphToTripleGraphGrammar, rule,
						directedGraph, tripleGraphGrammar };
			}
		}
		return null;
	}

	public static final boolean pattern_FillBaseRules_12_5_checkCSP_expressionFBB(FillBaseRules _this, CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_12_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_FillBaseRules_12_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "FillBaseRules";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_FillBaseRules_12_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_20_1_preparereturnvalue_bindingFB(FillBaseRules _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_20_1_preparereturnvalue_blackFBBF(EClass __eClass,
			FillBaseRules _this) {
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

	public static final Object[] pattern_FillBaseRules_20_1_preparereturnvalue_bindingAndBlackFFBF(
			FillBaseRules _this) {
		Object[] result_pattern_FillBaseRules_20_1_preparereturnvalue_binding = pattern_FillBaseRules_20_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_FillBaseRules_20_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_FillBaseRules_20_1_preparereturnvalue_binding[0];

			Object[] result_pattern_FillBaseRules_20_1_preparereturnvalue_black = pattern_FillBaseRules_20_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_FillBaseRules_20_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_FillBaseRules_20_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_FillBaseRules_20_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_20_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_FillBaseRules_20_2_testcorematchandDECs_blackFFB(
			EMoflonEdge _edge_nodes) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		EObject tmpDirectedGraph = _edge_nodes.getSrc();
		if (tmpDirectedGraph instanceof DirectedGraph) {
			DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
			EObject tmpRuleBox = _edge_nodes.getTrg();
			if (tmpRuleBox instanceof Box) {
				Box ruleBox = (Box) tmpRuleBox;
				if (directedGraph.getNodes().contains(ruleBox)) {
					_result.add(new Object[] { directedGraph, ruleBox, _edge_nodes });
				}
			}

		}

		return _result;
	}

	public static final Object[] pattern_FillBaseRules_20_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_FillBaseRules_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
			FillBaseRules _this, Match match, DirectedGraph directedGraph, Box ruleBox) {
		boolean _localVariable_0 = _this.isAppropriate_FWD(match, directedGraph, ruleBox);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_FillBaseRules_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			FillBaseRules _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_FWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_20_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_20_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_FillBaseRules_20_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_21_1_preparereturnvalue_bindingFB(FillBaseRules _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_21_1_preparereturnvalue_blackFBBF(EClass __eClass,
			FillBaseRules _this) {
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

	public static final Object[] pattern_FillBaseRules_21_1_preparereturnvalue_bindingAndBlackFFBF(
			FillBaseRules _this) {
		Object[] result_pattern_FillBaseRules_21_1_preparereturnvalue_binding = pattern_FillBaseRules_21_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_FillBaseRules_21_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_FillBaseRules_21_1_preparereturnvalue_binding[0];

			Object[] result_pattern_FillBaseRules_21_1_preparereturnvalue_black = pattern_FillBaseRules_21_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_FillBaseRules_21_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_FillBaseRules_21_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_FillBaseRules_21_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_21_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_FillBaseRules_21_2_testcorematchandDECs_blackFFB(
			EMoflonEdge _edge_tggRule) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		EObject tmpTripleGraphGrammar = _edge_tggRule.getSrc();
		if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
			EObject tmpRule = _edge_tggRule.getTrg();
			if (tmpRule instanceof TGGRule) {
				TGGRule rule = (TGGRule) tmpRule;
				if (tripleGraphGrammar.getTggRule().contains(rule)) {
					_result.add(new Object[] { rule, tripleGraphGrammar, _edge_tggRule });
				}
			}

		}

		return _result;
	}

	public static final Object[] pattern_FillBaseRules_21_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_FillBaseRules_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
			FillBaseRules _this, Match match, TGGRule rule, TripleGraphGrammar tripleGraphGrammar) {
		boolean _localVariable_0 = _this.isAppropriate_BWD(match, rule, tripleGraphGrammar);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_FillBaseRules_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			FillBaseRules _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_BWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_21_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_21_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_FillBaseRules_21_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_24_1_prepare_blackB(FillBaseRules _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_FillBaseRules_24_1_prepare_greenF() {
		IsApplicableRuleResult result = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		return new Object[] { result };
	}

	public static final Object[] pattern_FillBaseRules_24_2_matchsrctrgcontext_bindingFFFFBB(Match targetMatch,
			Match sourceMatch) {
		EObject _localVariable_0 = targetMatch.getObject("rule");
		EObject _localVariable_1 = sourceMatch.getObject("directedGraph");
		EObject _localVariable_2 = sourceMatch.getObject("ruleBox");
		EObject _localVariable_3 = targetMatch.getObject("tripleGraphGrammar");
		EObject tmpRule = _localVariable_0;
		EObject tmpDirectedGraph = _localVariable_1;
		EObject tmpRuleBox = _localVariable_2;
		EObject tmpTripleGraphGrammar = _localVariable_3;
		if (tmpRule instanceof TGGRule) {
			TGGRule rule = (TGGRule) tmpRule;
			if (tmpDirectedGraph instanceof DirectedGraph) {
				DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
				if (tmpRuleBox instanceof Box) {
					Box ruleBox = (Box) tmpRuleBox;
					if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
						TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
						return new Object[] { rule, directedGraph, ruleBox, tripleGraphGrammar, targetMatch,
								sourceMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_24_2_matchsrctrgcontext_blackBBBBBB(TGGRule rule,
			DirectedGraph directedGraph, Box ruleBox, TripleGraphGrammar tripleGraphGrammar, Match sourceMatch,
			Match targetMatch) {
		if (!sourceMatch.equals(targetMatch)) {
			return new Object[] { rule, directedGraph, ruleBox, tripleGraphGrammar, sourceMatch, targetMatch };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_24_2_matchsrctrgcontext_bindingAndBlackFFFFBB(Match sourceMatch,
			Match targetMatch) {
		Object[] result_pattern_FillBaseRules_24_2_matchsrctrgcontext_binding = pattern_FillBaseRules_24_2_matchsrctrgcontext_bindingFFFFBB(
				targetMatch, sourceMatch);
		if (result_pattern_FillBaseRules_24_2_matchsrctrgcontext_binding != null) {
			TGGRule rule = (TGGRule) result_pattern_FillBaseRules_24_2_matchsrctrgcontext_binding[0];
			DirectedGraph directedGraph = (DirectedGraph) result_pattern_FillBaseRules_24_2_matchsrctrgcontext_binding[1];
			Box ruleBox = (Box) result_pattern_FillBaseRules_24_2_matchsrctrgcontext_binding[2];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result_pattern_FillBaseRules_24_2_matchsrctrgcontext_binding[3];

			Object[] result_pattern_FillBaseRules_24_2_matchsrctrgcontext_black = pattern_FillBaseRules_24_2_matchsrctrgcontext_blackBBBBBB(
					rule, directedGraph, ruleBox, tripleGraphGrammar, sourceMatch, targetMatch);
			if (result_pattern_FillBaseRules_24_2_matchsrctrgcontext_black != null) {

				return new Object[] { rule, directedGraph, ruleBox, tripleGraphGrammar, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_24_3_solvecsp_bindingFBBBBBBB(FillBaseRules _this, TGGRule rule,
			DirectedGraph directedGraph, Box ruleBox, TripleGraphGrammar tripleGraphGrammar, Match sourceMatch,
			Match targetMatch) {
		CSP _localVariable_4 = _this.isApplicable_solveCsp_CC(rule, directedGraph, ruleBox, tripleGraphGrammar,
				sourceMatch, targetMatch);
		CSP csp = _localVariable_4;
		if (csp != null) {
			return new Object[] { csp, _this, rule, directedGraph, ruleBox, tripleGraphGrammar, sourceMatch,
					targetMatch };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_24_3_solvecsp_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_FillBaseRules_24_3_solvecsp_bindingAndBlackFBBBBBBB(FillBaseRules _this,
			TGGRule rule, DirectedGraph directedGraph, Box ruleBox, TripleGraphGrammar tripleGraphGrammar,
			Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_FillBaseRules_24_3_solvecsp_binding = pattern_FillBaseRules_24_3_solvecsp_bindingFBBBBBBB(
				_this, rule, directedGraph, ruleBox, tripleGraphGrammar, sourceMatch, targetMatch);
		if (result_pattern_FillBaseRules_24_3_solvecsp_binding != null) {
			CSP csp = (CSP) result_pattern_FillBaseRules_24_3_solvecsp_binding[0];

			Object[] result_pattern_FillBaseRules_24_3_solvecsp_black = pattern_FillBaseRules_24_3_solvecsp_blackB(csp);
			if (result_pattern_FillBaseRules_24_3_solvecsp_black != null) {

				return new Object[] { csp, _this, rule, directedGraph, ruleBox, tripleGraphGrammar, sourceMatch,
						targetMatch };
			}
		}
		return null;
	}

	public static final boolean pattern_FillBaseRules_24_4_checkCSP_expressionFB(CSP csp) {
		boolean _localVariable_0 = csp.check();
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Iterable<Object[]> pattern_FillBaseRules_24_5_matchcorrcontext_blackFBBBB(
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar, Match sourceMatch, Match targetMatch) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!sourceMatch.equals(targetMatch)) {
			for (DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar : org.moflon.core.utilities.eMoflonEMFUtil
					.getOppositeReferenceTyped(tripleGraphGrammar, DirectedGraphToTripleGraphGrammar.class, "target")) {
				if (directedGraph.equals(directedGraphToTripleGraphGrammar.getSource())) {
					_result.add(new Object[] { directedGraphToTripleGraphGrammar, directedGraph, tripleGraphGrammar,
							sourceMatch, targetMatch });
				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_24_5_matchcorrcontext_greenBBBF(
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, Match sourceMatch, Match targetMatch) {
		CCMatch ccMatch = RuntimeFactory.eINSTANCE.createCCMatch();
		String ccMatch_ruleName_prime = "FillBaseRules";
		ccMatch.setSourceMatch(sourceMatch);
		ccMatch.setTargetMatch(targetMatch);
		ccMatch.getAllContextElements().add(directedGraphToTripleGraphGrammar);
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { directedGraphToTripleGraphGrammar, sourceMatch, targetMatch, ccMatch };
	}

	public static final Object[] pattern_FillBaseRules_24_6_createcorrespondence_blackBBBBB(TGGRule rule,
			DirectedGraph directedGraph, Box ruleBox, TripleGraphGrammar tripleGraphGrammar, CCMatch ccMatch) {
		return new Object[] { rule, directedGraph, ruleBox, tripleGraphGrammar, ccMatch };
	}

	public static final Object[] pattern_FillBaseRules_24_6_createcorrespondence_greenFBBB(TGGRule rule, Box ruleBox,
			CCMatch ccMatch) {
		NodeToRule nodeToRule = SchemaFactory.eINSTANCE.createNodeToRule();
		nodeToRule.setTarget(rule);
		nodeToRule.setSource(ruleBox);
		ccMatch.getCreateCorr().add(nodeToRule);
		return new Object[] { nodeToRule, rule, ruleBox, ccMatch };
	}

	public static final Object[] pattern_FillBaseRules_24_7_addtoreturnedresult_blackBB(IsApplicableRuleResult result,
			CCMatch ccMatch) {
		return new Object[] { result, ccMatch };
	}

	public static final Object[] pattern_FillBaseRules_24_7_addtoreturnedresult_greenBB(IsApplicableRuleResult result,
			CCMatch ccMatch) {
		result.getIsApplicableMatch().add(ccMatch);
		boolean result_success_prime = Boolean.valueOf(true);
		String ccMatch_ruleName_prime = "FillBaseRules";
		result.setSuccess(Boolean.valueOf(result_success_prime));
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { result, ccMatch };
	}

	public static final IsApplicableRuleResult pattern_FillBaseRules_24_8_expressionFB(IsApplicableRuleResult result) {
		IsApplicableRuleResult _result = result;
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_27_1_matchtggpattern_blackBB(DirectedGraph directedGraph,
			Box ruleBox) {
		if (directedGraph.getNodes().contains(ruleBox)) {
			return new Object[] { directedGraph, ruleBox };
		}
		return null;
	}

	public static final boolean pattern_FillBaseRules_27_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_FillBaseRules_27_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_28_1_matchtggpattern_blackBB(TGGRule rule,
			TripleGraphGrammar tripleGraphGrammar) {
		if (tripleGraphGrammar.getTggRule().contains(rule)) {
			return new Object[] { rule, tripleGraphGrammar };
		}
		return null;
	}

	public static final boolean pattern_FillBaseRules_28_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_FillBaseRules_28_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_29_1_createresult_blackB(FillBaseRules _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_FillBaseRules_29_1_createresult_greenFF() {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		ModelgeneratorRuleResult ruleResult = RuntimeFactory.eINSTANCE.createModelgeneratorRuleResult();
		boolean ruleResult_success_prime = false;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		return new Object[] { isApplicableMatch, ruleResult };
	}

	public static final Object[] pattern_FillBaseRules_29_2_isapplicablecore_black_nac_0BB(
			ModelgeneratorRuleResult ruleResult, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		if (ruleResult.getCorrObjects().contains(directedGraphToTripleGraphGrammar)) {
			return new Object[] { ruleResult, directedGraphToTripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_29_2_isapplicablecore_black_nac_1BB(
			ModelgeneratorRuleResult ruleResult, TripleGraphGrammar tripleGraphGrammar) {
		if (ruleResult.getTargetObjects().contains(tripleGraphGrammar)) {
			return new Object[] { ruleResult, tripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_29_2_isapplicablecore_black_nac_2BB(
			ModelgeneratorRuleResult ruleResult, DirectedGraph directedGraph) {
		if (ruleResult.getSourceObjects().contains(directedGraph)) {
			return new Object[] { ruleResult, directedGraph };
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_FillBaseRules_29_2_isapplicablecore_blackFFFFBB(
			RuleEntryContainer ruleEntryContainer, ModelgeneratorRuleResult ruleResult) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (RuleEntryList directedGraphToTripleGraphGrammarList : ruleEntryContainer.getRuleEntryList()) {
			for (EObject tmpDirectedGraphToTripleGraphGrammar : directedGraphToTripleGraphGrammarList
					.getEntryObjects()) {
				if (tmpDirectedGraphToTripleGraphGrammar instanceof DirectedGraphToTripleGraphGrammar) {
					DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) tmpDirectedGraphToTripleGraphGrammar;
					TripleGraphGrammar tripleGraphGrammar = directedGraphToTripleGraphGrammar.getTarget();
					if (tripleGraphGrammar != null) {
						DirectedGraph directedGraph = directedGraphToTripleGraphGrammar.getSource();
						if (directedGraph != null) {
							if (pattern_FillBaseRules_29_2_isapplicablecore_black_nac_0BB(ruleResult,
									directedGraphToTripleGraphGrammar) == null) {
								if (pattern_FillBaseRules_29_2_isapplicablecore_black_nac_1BB(ruleResult,
										tripleGraphGrammar) == null) {
									if (pattern_FillBaseRules_29_2_isapplicablecore_black_nac_2BB(ruleResult,
											directedGraph) == null) {
										_result.add(new Object[] { directedGraphToTripleGraphGrammarList,
												directedGraphToTripleGraphGrammar, tripleGraphGrammar, directedGraph,
												ruleEntryContainer, ruleResult });
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

	public static final Object[] pattern_FillBaseRules_29_3_solveCSP_bindingFBBBBBB(FillBaseRules _this,
			IsApplicableMatch isApplicableMatch, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar,
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar, ModelgeneratorRuleResult ruleResult) {
		CSP _localVariable_0 = _this.generateModel_solveCsp_BWD(isApplicableMatch, directedGraphToTripleGraphGrammar,
				directedGraph, tripleGraphGrammar, ruleResult);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, directedGraphToTripleGraphGrammar, directedGraph,
					tripleGraphGrammar, ruleResult };
		}
		return null;
	}

	public static final Object[] pattern_FillBaseRules_29_3_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_FillBaseRules_29_3_solveCSP_bindingAndBlackFBBBBBB(FillBaseRules _this,
			IsApplicableMatch isApplicableMatch, DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar,
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar, ModelgeneratorRuleResult ruleResult) {
		Object[] result_pattern_FillBaseRules_29_3_solveCSP_binding = pattern_FillBaseRules_29_3_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, directedGraphToTripleGraphGrammar, directedGraph, tripleGraphGrammar,
				ruleResult);
		if (result_pattern_FillBaseRules_29_3_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_FillBaseRules_29_3_solveCSP_binding[0];

			Object[] result_pattern_FillBaseRules_29_3_solveCSP_black = pattern_FillBaseRules_29_3_solveCSP_blackB(csp);
			if (result_pattern_FillBaseRules_29_3_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, directedGraphToTripleGraphGrammar, directedGraph,
						tripleGraphGrammar, ruleResult };
			}
		}
		return null;
	}

	public static final boolean pattern_FillBaseRules_29_4_checkCSP_expressionFBB(FillBaseRules _this, CSP csp) {
		boolean _localVariable_0 = _this.generateModel_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_FillBaseRules_29_5_checknacs_blackBBB(
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, DirectedGraph directedGraph,
			TripleGraphGrammar tripleGraphGrammar) {
		return new Object[] { directedGraphToTripleGraphGrammar, directedGraph, tripleGraphGrammar };
	}

	public static final Object[] pattern_FillBaseRules_29_6_perform_blackBBBB(
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar, DirectedGraph directedGraph,
			TripleGraphGrammar tripleGraphGrammar, ModelgeneratorRuleResult ruleResult) {
		return new Object[] { directedGraphToTripleGraphGrammar, directedGraph, tripleGraphGrammar, ruleResult };
	}

	public static final Object[] pattern_FillBaseRules_29_6_perform_greenFFBFBBB(DirectedGraph directedGraph,
			TripleGraphGrammar tripleGraphGrammar, ModelgeneratorRuleResult ruleResult, CSP csp) {
		NodeToRule nodeToRule = SchemaFactory.eINSTANCE.createNodeToRule();
		TGGRule rule = LanguageFactory.eINSTANCE.createTGGRule();
		Box ruleBox = org.moflon.ide.visualization.dot.language.LanguageFactory.eINSTANCE.createBox();
		Object _localVariable_0 = csp.getValue("rule", "name");
		Object _localVariable_1 = csp.getValue("ruleBox", "label");
		boolean ruleResult_success_prime = Boolean.valueOf(true);
		int _localVariable_2 = ruleResult.getIncrementedPerformCount();
		ruleResult.getCorrObjects().add(nodeToRule);
		nodeToRule.setTarget(rule);
		tripleGraphGrammar.getTggRule().add(rule);
		ruleResult.getTargetObjects().add(rule);
		nodeToRule.setSource(ruleBox);
		directedGraph.getNodes().add(ruleBox);
		ruleResult.getSourceObjects().add(ruleBox);
		String rule_name_prime = (String) _localVariable_0;
		String ruleBox_label_prime = (String) _localVariable_1;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		int ruleResult_performCount_prime = Integer.valueOf(_localVariable_2);
		rule.setName(rule_name_prime);
		ruleBox.setLabel(ruleBox_label_prime);
		ruleResult.setPerformCount(Integer.valueOf(ruleResult_performCount_prime));
		return new Object[] { nodeToRule, rule, directedGraph, ruleBox, tripleGraphGrammar, ruleResult, csp };
	}

	public static final ModelgeneratorRuleResult pattern_FillBaseRules_29_7_expressionFB(
			ModelgeneratorRuleResult ruleResult) {
		ModelgeneratorRuleResult _result = ruleResult;
		return _result;
	}

	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //FillBaseRulesImpl
