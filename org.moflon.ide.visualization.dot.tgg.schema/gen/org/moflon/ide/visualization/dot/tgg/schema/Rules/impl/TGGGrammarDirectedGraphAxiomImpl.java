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

import org.moflon.ide.visualization.dot.language.DirectedGraph;

import org.moflon.ide.visualization.dot.tgg.schema.DirectedGraphToTripleGraphGrammar;

import org.moflon.ide.visualization.dot.tgg.schema.Rules.RulesPackage;
import org.moflon.ide.visualization.dot.tgg.schema.Rules.TGGGrammarDirectedGraphAxiom;

import org.moflon.ide.visualization.dot.tgg.schema.SchemaFactory;

import org.moflon.tgg.language.LanguageFactory;
import org.moflon.tgg.language.TripleGraphGrammar;

import org.moflon.tgg.language.csp.CSP;

import org.moflon.tgg.language.modelgenerator.RuleEntryContainer;

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
 * An implementation of the model object '<em><b>TGG Grammar Directed Graph Axiom</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class TGGGrammarDirectedGraphAxiomImpl extends AbstractRuleImpl implements TGGGrammarDirectedGraphAxiom {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGGrammarDirectedGraphAxiomImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulesPackage.Literals.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_FWD(Match match, DirectedGraph directedGraph) {
		// initial bindings
		Object[] result1_black = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_0_1_initialbindings_blackBBB(this, match, directedGraph);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [initial bindings] failed." + " Variables: " + "[this] = " + this + ", "
							+ "[match] = " + match + ", " + "[directedGraph] = " + directedGraph + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_0_2_SolveCSP_bindingAndBlackFBBB(this, match, directedGraph);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[directedGraph] = " + directedGraph + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_0_3_CheckCSP_expressionFBB(this,
				csp)) {

			// collect elements to be translated
			Object[] result4_black = TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_0_4_collectelementstobetranslated_blackBB(match,
							directedGraph);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[directedGraph] = " + directedGraph + ".");
			}
			TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_0_4_collectelementstobetranslated_greenBB(match,
							directedGraph);

			// collect context elements
			Object[] result5_black = TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_0_5_collectcontextelements_blackBB(match, directedGraph);
			if (result5_black == null) {
				throw new RuntimeException("Pattern matching in node [collect context elements] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[directedGraph] = " + directedGraph + ".");
			}
			// register objects to match
			TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_0_6_registerobjectstomatch_expressionBBB(this, match,
							directedGraph);
			return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_0_7_expressionF();
		} else {
			return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_0_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_FWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_bindingAndBlackFFBB(this,
						isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		DirectedGraph directedGraph = (DirectedGraph) result1_bindingAndBlack[0];
		// CSP csp = (CSP) result1_bindingAndBlack[1];
		Object[] result1_green = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_greenBFF(directedGraph);
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result1_green[1];
		DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result1_green[2];

		// collect translated elements
		Object[] result2_black = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_1_2_collecttranslatedelements_blackBBB(directedGraph,
						tripleGraphGrammar, directedGraphToTripleGraphGrammar);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[directedGraph] = " + directedGraph + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar
					+ ", " + "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ".");
		}
		Object[] result2_green = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_1_2_collecttranslatedelements_greenFBBB(directedGraph,
						tripleGraphGrammar, directedGraphToTripleGraphGrammar);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_1_3_bookkeepingforedges_blackBBBB(ruleresult, directedGraph,
						tripleGraphGrammar, directedGraphToTripleGraphGrammar);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[directedGraph] = " + directedGraph + ", "
					+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ", " + "[directedGraphToTripleGraphGrammar] = "
					+ directedGraphToTripleGraphGrammar + ".");
		}
		TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_1_3_bookkeepingforedges_greenBBBBFF(
				ruleresult, directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar);
		// EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = (EMoflonEdge) result3_green[4];
		// EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = (EMoflonEdge) result3_green[5];

		// perform postprocessing story node is empty
		// register objects
		TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_1_5_registerobjects_expressionBBBBB(this,
				ruleresult, directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar);
		return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_1_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_FWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_2_2_corematch_bindingFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		DirectedGraph directedGraph = (DirectedGraph) result2_binding[0];
		for (Object[] result2_black : TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_2_2_corematch_blackBB(directedGraph, match)) {
			// ForEach find context
			for (Object[] result3_black : TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_2_3_findcontext_blackB(directedGraph)) {
				Object[] result3_green = TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_2_3_findcontext_greenBF(directedGraph);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[1];

				// solve CSP
				Object[] result4_bindingAndBlack = TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_2_4_solveCSP_bindingAndBlackFBBB(this, isApplicableMatch,
								directedGraph);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[directedGraph] = " + directedGraph + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_2_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = TGGGrammarDirectedGraphAxiomImpl
							.pattern_TGGGrammarDirectedGraphAxiom_2_6_addmatchtoruleresult_blackBB(ruleresult,
									isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					TGGGrammarDirectedGraphAxiomImpl
							.pattern_TGGGrammarDirectedGraphAxiom_2_6_addmatchtoruleresult_greenBB(ruleresult,
									isApplicableMatch);

				} else {
				}

			}

		}
		return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_2_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_FWD(Match match, DirectedGraph directedGraph) {
		match.registerObject("directedGraph", directedGraph);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_FWD(Match match, DirectedGraph directedGraph) {// Create CSP
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
	public CSP isApplicable_solveCsp_FWD(IsApplicableMatch isApplicableMatch, DirectedGraph directedGraph) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables

		// Create unbound variables

		// Create constraints

		// Solve CSP

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("directedGraph", directedGraph);
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
	public void registerObjects_FWD(PerformRuleResult ruleresult, EObject directedGraph, EObject tripleGraphGrammar,
			EObject directedGraphToTripleGraphGrammar) {
		ruleresult.registerObject("directedGraph", directedGraph);
		ruleresult.registerObject("tripleGraphGrammar", tripleGraphGrammar);
		ruleresult.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_FWD(Match match) {
		return true && org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("directedGraph").eClass())
				.equals("language.DirectedGraph.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_BWD(Match match, TripleGraphGrammar tripleGraphGrammar) {
		// initial bindings
		Object[] result1_black = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_10_1_initialbindings_blackBBB(this, match, tripleGraphGrammar);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [initial bindings] failed." + " Variables: " + "[this] = " + this + ", "
							+ "[match] = " + match + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_10_2_SolveCSP_bindingAndBlackFBBB(this, match,
						tripleGraphGrammar);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_10_3_CheckCSP_expressionFBB(this,
				csp)) {

			// collect elements to be translated
			Object[] result4_black = TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_10_4_collectelementstobetranslated_blackBB(match,
							tripleGraphGrammar);
			if (result4_black == null) {
				throw new RuntimeException(
						"Pattern matching in node [collect elements to be translated] failed." + " Variables: "
								+ "[match] = " + match + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
			}
			TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_10_4_collectelementstobetranslated_greenBB(match,
							tripleGraphGrammar);

			// collect context elements
			Object[] result5_black = TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_10_5_collectcontextelements_blackBB(match,
							tripleGraphGrammar);
			if (result5_black == null) {
				throw new RuntimeException(
						"Pattern matching in node [collect context elements] failed." + " Variables: " + "[match] = "
								+ match + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
			}
			// register objects to match
			TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_10_6_registerobjectstomatch_expressionBBB(this, match,
							tripleGraphGrammar);
			return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_10_7_expressionF();
		} else {
			return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_10_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_BWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_bindingAndBlackFFBB(this,
						isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result1_bindingAndBlack[0];
		// CSP csp = (CSP) result1_bindingAndBlack[1];
		Object[] result1_green = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_greenFBF(tripleGraphGrammar);
		DirectedGraph directedGraph = (DirectedGraph) result1_green[0];
		DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result1_green[2];

		// collect translated elements
		Object[] result2_black = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_11_2_collecttranslatedelements_blackBBB(directedGraph,
						tripleGraphGrammar, directedGraphToTripleGraphGrammar);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[directedGraph] = " + directedGraph + ", " + "[tripleGraphGrammar] = " + tripleGraphGrammar
					+ ", " + "[directedGraphToTripleGraphGrammar] = " + directedGraphToTripleGraphGrammar + ".");
		}
		Object[] result2_green = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_11_2_collecttranslatedelements_greenFBBB(directedGraph,
						tripleGraphGrammar, directedGraphToTripleGraphGrammar);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_11_3_bookkeepingforedges_blackBBBB(ruleresult, directedGraph,
						tripleGraphGrammar, directedGraphToTripleGraphGrammar);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[directedGraph] = " + directedGraph + ", "
					+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ", " + "[directedGraphToTripleGraphGrammar] = "
					+ directedGraphToTripleGraphGrammar + ".");
		}
		TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_11_3_bookkeepingforedges_greenBBBBFF(
				ruleresult, directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar);
		// EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = (EMoflonEdge) result3_green[4];
		// EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = (EMoflonEdge) result3_green[5];

		// perform postprocessing story node is empty
		// register objects
		TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_11_5_registerobjects_expressionBBBBB(this,
				ruleresult, directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar);
		return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_11_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_BWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_12_2_corematch_bindingFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_binding[0];
		for (Object[] result2_black : TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_12_2_corematch_blackBB(tripleGraphGrammar, match)) {
			// ForEach find context
			for (Object[] result3_black : TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_12_3_findcontext_blackB(tripleGraphGrammar)) {
				Object[] result3_green = TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_12_3_findcontext_greenBF(tripleGraphGrammar);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[1];

				// solve CSP
				Object[] result4_bindingAndBlack = TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_12_4_solveCSP_bindingAndBlackFBBB(this, isApplicableMatch,
								tripleGraphGrammar);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[tripleGraphGrammar] = " + tripleGraphGrammar + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_12_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = TGGGrammarDirectedGraphAxiomImpl
							.pattern_TGGGrammarDirectedGraphAxiom_12_6_addmatchtoruleresult_blackBB(ruleresult,
									isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					TGGGrammarDirectedGraphAxiomImpl
							.pattern_TGGGrammarDirectedGraphAxiom_12_6_addmatchtoruleresult_greenBB(ruleresult,
									isApplicableMatch);

				} else {
				}

			}

		}
		return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_12_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_BWD(Match match, TripleGraphGrammar tripleGraphGrammar) {
		match.registerObject("tripleGraphGrammar", tripleGraphGrammar);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_BWD(Match match, TripleGraphGrammar tripleGraphGrammar) {// Create CSP
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
	public CSP isApplicable_solveCsp_BWD(IsApplicableMatch isApplicableMatch, TripleGraphGrammar tripleGraphGrammar) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables

		// Create unbound variables

		// Create constraints

		// Solve CSP

		// Snapshot pattern match on which CSP is solved
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
	public void registerObjects_BWD(PerformRuleResult ruleresult, EObject directedGraph, EObject tripleGraphGrammar,
			EObject directedGraphToTripleGraphGrammar) {
		ruleresult.registerObject("directedGraph", directedGraph);
		ruleresult.registerObject("tripleGraphGrammar", tripleGraphGrammar);
		ruleresult.registerObject("directedGraphToTripleGraphGrammar", directedGraphToTripleGraphGrammar);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_BWD(Match match) {
		return true && org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("tripleGraphGrammar").eClass())
				.equals("language.TripleGraphGrammar.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_FWD_DirectedGraph_0(DirectedGraph directedGraph) {
		// prepare return value
		Object[] result1_bindingAndBlack = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_20_2_testcorematchandDECs_blackB(directedGraph)) {
			Object[] result2_green = TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_20_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBB(
							this, match, directedGraph)) {
				// Ensure that the correct types of elements are matched
				if (TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
								this, match)) {

					// Add match to rule result
					Object[] result5_black = TGGGrammarDirectedGraphAxiomImpl
							.pattern_TGGGrammarDirectedGraphAxiom_20_5_Addmatchtoruleresult_blackBBBB(match,
									__performOperation, __result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					TGGGrammarDirectedGraphAxiomImpl
							.pattern_TGGGrammarDirectedGraphAxiom_20_5_Addmatchtoruleresult_greenBBBB(match,
									__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_20_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_BWD_TripleGraphGrammar_0(TripleGraphGrammar tripleGraphGrammar) {
		// prepare return value
		Object[] result1_bindingAndBlack = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_21_2_testcorematchandDECs_blackB(tripleGraphGrammar)) {
			Object[] result2_green = TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_21_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBB(
							this, match, tripleGraphGrammar)) {
				// Ensure that the correct types of elements are matched
				if (TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
								this, match)) {

					// Add match to rule result
					Object[] result5_black = TGGGrammarDirectedGraphAxiomImpl
							.pattern_TGGGrammarDirectedGraphAxiom_21_5_Addmatchtoruleresult_blackBBBB(match,
									__performOperation, __result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					TGGGrammarDirectedGraphAxiomImpl
							.pattern_TGGGrammarDirectedGraphAxiom_21_5_Addmatchtoruleresult_greenBBBB(match,
									__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_21_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintsRuleResult checkAttributes_FWD(TripleMatch __tripleMatch) {
		AttributeConstraintsRuleResult ruleResult = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE
				.createAttributeConstraintsRuleResult();
		ruleResult.setRule("TGGGrammarDirectedGraphAxiom");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		if (!__helper.hasExpectedValue("directedGraph", "name", "Schema", ComparingOperator.EQUAL)) {
			ruleResult.setSuccess(false);
			return ruleResult;
		}

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
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
		ruleResult.setRule("TGGGrammarDirectedGraphAxiom");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		if (!__helper.hasExpectedValue("directedGraph", "name", "Schema", ComparingOperator.EQUAL)) {
			ruleResult.setSuccess(false);
			return ruleResult;
		}

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
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
		Object[] result1_black = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_24_1_prepare_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [prepare] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_24_1_prepare_greenF();
		IsApplicableRuleResult result = (IsApplicableRuleResult) result1_green[0];

		// match src trg context
		Object[] result2_bindingAndBlack = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_24_2_matchsrctrgcontext_bindingAndBlackFFBB(sourceMatch,
						targetMatch);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [match src trg context] failed." + " Variables: "
					+ "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = " + targetMatch + ".");
		}
		DirectedGraph directedGraph = (DirectedGraph) result2_bindingAndBlack[0];
		TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result2_bindingAndBlack[1];

		// solve csp
		Object[] result3_bindingAndBlack = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_24_3_solvecsp_bindingAndBlackFBBBBB(this, directedGraph,
						tripleGraphGrammar, sourceMatch, targetMatch);
		if (result3_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [solve csp] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[directedGraph] = " + directedGraph + ", " + "[tripleGraphGrammar] = "
					+ tripleGraphGrammar + ", " + "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = "
					+ targetMatch + ".");
		}
		CSP csp = (CSP) result3_bindingAndBlack[0];
		// check CSP
		if (TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_24_4_checkCSP_expressionFB(csp)) {
			// ForEach match corr context
			for (Object[] result5_black : TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_24_5_matchcorrcontext_blackBB(sourceMatch, targetMatch)) {
				Object[] result5_green = TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_24_5_matchcorrcontext_greenBBF(sourceMatch, targetMatch);
				CCMatch ccMatch = (CCMatch) result5_green[2];

				// create correspondence
				Object[] result6_black = TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_24_6_createcorrespondence_blackBBB(directedGraph,
								tripleGraphGrammar, ccMatch);
				if (result6_black == null) {
					throw new RuntimeException("Pattern matching in node [create correspondence] failed."
							+ " Variables: " + "[directedGraph] = " + directedGraph + ", " + "[tripleGraphGrammar] = "
							+ tripleGraphGrammar + ", " + "[ccMatch] = " + ccMatch + ".");
				}
				TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_24_6_createcorrespondence_greenBBFB(directedGraph,
								tripleGraphGrammar, ccMatch);
				// DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result6_green[2];

				// add to returned result
				Object[] result7_black = TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_24_7_addtoreturnedresult_blackBB(result, ccMatch);
				if (result7_black == null) {
					throw new RuntimeException("Pattern matching in node [add to returned result] failed."
							+ " Variables: " + "[result] = " + result + ", " + "[ccMatch] = " + ccMatch + ".");
				}
				TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_24_7_addtoreturnedresult_greenBB(result, ccMatch);

			}

		} else {
		}
		return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_24_8_expressionFB(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isApplicable_solveCsp_CC(DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar,
			Match sourceMatch, Match targetMatch) {// Create CSP
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
	public boolean isApplicable_checkCsp_CC(CSP csp) {
		return csp.check();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDEC_FWD(DirectedGraph directedGraph) {// match tgg pattern
		Object[] result1_black = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_27_1_matchtggpattern_blackB(directedGraph);
		if (result1_black != null) {
			TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_27_1_matchtggpattern_greenB(directedGraph);

			return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_27_2_expressionF();
		} else {
			return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_27_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDEC_BWD(TripleGraphGrammar tripleGraphGrammar) {// match tgg pattern
		Object[] result1_black = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_28_1_matchtggpattern_blackB(tripleGraphGrammar);
		if (result1_black != null) {
			return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_28_2_expressionF();
		} else {
			return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_28_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelgeneratorRuleResult generateModel(RuleEntryContainer ruleEntryContainer) {
		// create result
		Object[] result1_black = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_29_1_createresult_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [create result] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_29_1_createresult_greenFF();
		IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result1_green[0];
		ModelgeneratorRuleResult ruleResult = (ModelgeneratorRuleResult) result1_green[1];

		// is applicable core
		Object[] result2_black = TGGGrammarDirectedGraphAxiomImpl
				.pattern_TGGGrammarDirectedGraphAxiom_29_2_isapplicablecore_blackB(this);
		if (result2_black != null) {

			// solve CSP
			Object[] result3_bindingAndBlack = TGGGrammarDirectedGraphAxiomImpl
					.pattern_TGGGrammarDirectedGraphAxiom_29_3_solveCSP_bindingAndBlackFBBB(this, isApplicableMatch,
							ruleResult);
			if (result3_bindingAndBlack == null) {
				throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: " + "[this] = "
						+ this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", " + "[ruleResult] = "
						+ ruleResult + ".");
			}
			CSP csp = (CSP) result3_bindingAndBlack[0];
			// check CSP
			if (TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_29_4_checkCSP_expressionFBB(this,
					csp)) {
				// check nacs story node is empty

				// perform
				Object[] result6_black = TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_29_6_perform_blackB(ruleResult);
				if (result6_black == null) {
					throw new RuntimeException("Pattern matching in node [perform] failed." + " Variables: "
							+ "[ruleResult] = " + ruleResult + ".");
				}
				TGGGrammarDirectedGraphAxiomImpl
						.pattern_TGGGrammarDirectedGraphAxiom_29_6_perform_greenFFFB(ruleResult);
				// DirectedGraph directedGraph = (DirectedGraph) result6_green[0];
				// TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result6_green[1];
				// DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = (DirectedGraphToTripleGraphGrammar) result6_green[2];

			} else {
			}

		} else {
		}
		return TGGGrammarDirectedGraphAxiomImpl.pattern_TGGGrammarDirectedGraphAxiom_29_7_expressionFB(ruleResult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP generateModel_solveCsp_BWD(IsApplicableMatch isApplicableMatch, ModelgeneratorRuleResult ruleResult) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables

		// Create unbound variables

		// Create constraints

		// Solve CSP

		// Snapshot pattern match on which CSP is solved
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
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_FWD__MATCH_DIRECTEDGRAPH:
			return isAppropriate_FWD((Match) arguments.get(0), (DirectedGraph) arguments.get(1));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___PERFORM_FWD__ISAPPLICABLEMATCH:
			return perform_FWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_FWD__MATCH:
			return isApplicable_FWD((Match) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_DIRECTEDGRAPH:
			registerObjectsToMatch_FWD((Match) arguments.get(0), (DirectedGraph) arguments.get(1));
			return null;
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_DIRECTEDGRAPH:
			return isAppropriate_solveCsp_FWD((Match) arguments.get(0), (DirectedGraph) arguments.get(1));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_CHECK_CSP_FWD__CSP:
			return isAppropriate_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_DIRECTEDGRAPH:
			return isApplicable_solveCsp_FWD((IsApplicableMatch) arguments.get(0), (DirectedGraph) arguments.get(1));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CHECK_CSP_FWD__CSP:
			return isApplicable_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_FWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3));
			return null;
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_TYPES_FWD__MATCH:
			return checkTypes_FWD((Match) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_BWD__MATCH_TRIPLEGRAPHGRAMMAR:
			return isAppropriate_BWD((Match) arguments.get(0), (TripleGraphGrammar) arguments.get(1));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___PERFORM_BWD__ISAPPLICABLEMATCH:
			return perform_BWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_BWD__MATCH:
			return isApplicable_BWD((Match) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_TRIPLEGRAPHGRAMMAR:
			registerObjectsToMatch_BWD((Match) arguments.get(0), (TripleGraphGrammar) arguments.get(1));
			return null;
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_TRIPLEGRAPHGRAMMAR:
			return isAppropriate_solveCsp_BWD((Match) arguments.get(0), (TripleGraphGrammar) arguments.get(1));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_CHECK_CSP_BWD__CSP:
			return isAppropriate_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_TRIPLEGRAPHGRAMMAR:
			return isApplicable_solveCsp_BWD((IsApplicableMatch) arguments.get(0),
					(TripleGraphGrammar) arguments.get(1));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CHECK_CSP_BWD__CSP:
			return isApplicable_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_BWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3));
			return null;
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_TYPES_BWD__MATCH:
			return checkTypes_BWD((Match) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_FWD_DIRECTED_GRAPH_0__DIRECTEDGRAPH:
			return isAppropriate_FWD_DirectedGraph_0((DirectedGraph) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPROPRIATE_BWD_TRIPLE_GRAPH_GRAMMAR_0__TRIPLEGRAPHGRAMMAR:
			return isAppropriate_BWD_TripleGraphGrammar_0((TripleGraphGrammar) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH:
			return checkAttributes_FWD((TripleMatch) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH:
			return checkAttributes_BWD((TripleMatch) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CC__MATCH_MATCH:
			return isApplicable_CC((Match) arguments.get(0), (Match) arguments.get(1));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_SOLVE_CSP_CC__DIRECTEDGRAPH_TRIPLEGRAPHGRAMMAR_MATCH_MATCH:
			return isApplicable_solveCsp_CC((DirectedGraph) arguments.get(0), (TripleGraphGrammar) arguments.get(1),
					(Match) arguments.get(2), (Match) arguments.get(3));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___IS_APPLICABLE_CHECK_CSP_CC__CSP:
			return isApplicable_checkCsp_CC((CSP) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_DEC_FWD__DIRECTEDGRAPH:
			return checkDEC_FWD((DirectedGraph) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___CHECK_DEC_BWD__TRIPLEGRAPHGRAMMAR:
			return checkDEC_BWD((TripleGraphGrammar) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___GENERATE_MODEL__RULEENTRYCONTAINER:
			return generateModel((RuleEntryContainer) arguments.get(0));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_MODELGENERATORRULERESULT:
			return generateModel_solveCsp_BWD((IsApplicableMatch) arguments.get(0),
					(ModelgeneratorRuleResult) arguments.get(1));
		case RulesPackage.TGG_GRAMMAR_DIRECTED_GRAPH_AXIOM___GENERATE_MODEL_CHECK_CSP_BWD__CSP:
			return generateModel_checkCsp_BWD((CSP) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_0_1_initialbindings_blackBBB(
			TGGGrammarDirectedGraphAxiom _this, Match match, DirectedGraph directedGraph) {
		return new Object[] { _this, match, directedGraph };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_0_2_SolveCSP_bindingFBBB(
			TGGGrammarDirectedGraphAxiom _this, Match match, DirectedGraph directedGraph) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_FWD(match, directedGraph);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, directedGraph };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_0_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_0_2_SolveCSP_bindingAndBlackFBBB(
			TGGGrammarDirectedGraphAxiom _this, Match match, DirectedGraph directedGraph) {
		Object[] result_pattern_TGGGrammarDirectedGraphAxiom_0_2_SolveCSP_binding = pattern_TGGGrammarDirectedGraphAxiom_0_2_SolveCSP_bindingFBBB(
				_this, match, directedGraph);
		if (result_pattern_TGGGrammarDirectedGraphAxiom_0_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_TGGGrammarDirectedGraphAxiom_0_2_SolveCSP_binding[0];

			Object[] result_pattern_TGGGrammarDirectedGraphAxiom_0_2_SolveCSP_black = pattern_TGGGrammarDirectedGraphAxiom_0_2_SolveCSP_blackB(
					csp);
			if (result_pattern_TGGGrammarDirectedGraphAxiom_0_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, directedGraph };
			}
		}
		return null;
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_0_3_CheckCSP_expressionFBB(
			TGGGrammarDirectedGraphAxiom _this, CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_0_4_collectelementstobetranslated_blackBB(
			Match match, DirectedGraph directedGraph) {
		return new Object[] { match, directedGraph };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_0_4_collectelementstobetranslated_greenBB(
			Match match, DirectedGraph directedGraph) {
		match.getToBeTranslatedNodes().add(directedGraph);
		return new Object[] { match, directedGraph };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_0_5_collectcontextelements_blackBB(Match match,
			DirectedGraph directedGraph) {
		return new Object[] { match, directedGraph };
	}

	public static final void pattern_TGGGrammarDirectedGraphAxiom_0_6_registerobjectstomatch_expressionBBB(
			TGGGrammarDirectedGraphAxiom _this, Match match, DirectedGraph directedGraph) {
		_this.registerObjectsToMatch_FWD(match, directedGraph);

	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_0_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_0_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_bindingFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("directedGraph");
		EObject tmpDirectedGraph = _localVariable_0;
		if (tmpDirectedGraph instanceof DirectedGraph) {
			DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
			return new Object[] { directedGraph, isApplicableMatch };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_blackBFBB(
			DirectedGraph directedGraph, TGGGrammarDirectedGraphAxiom _this, IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { directedGraph, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_bindingAndBlackFFBB(
			TGGGrammarDirectedGraphAxiom _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_binding = pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_bindingFB(
				isApplicableMatch);
		if (result_pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_binding != null) {
			DirectedGraph directedGraph = (DirectedGraph) result_pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_binding[0];

			Object[] result_pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_black = pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_blackBFBB(
					directedGraph, _this, isApplicableMatch);
			if (result_pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_black[1];

				return new Object[] { directedGraph, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_1_1_performtransformation_greenBFF(
			DirectedGraph directedGraph) {
		TripleGraphGrammar tripleGraphGrammar = LanguageFactory.eINSTANCE.createTripleGraphGrammar();
		DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = SchemaFactory.eINSTANCE
				.createDirectedGraphToTripleGraphGrammar();
		directedGraphToTripleGraphGrammar.setSource(directedGraph);
		directedGraphToTripleGraphGrammar.setTarget(tripleGraphGrammar);
		return new Object[] { directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_1_2_collecttranslatedelements_blackBBB(
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		return new Object[] { directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_1_2_collecttranslatedelements_greenFBBB(
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getTranslatedElements().add(directedGraph);
		ruleresult.getCreatedElements().add(tripleGraphGrammar);
		ruleresult.getCreatedLinkElements().add(directedGraphToTripleGraphGrammar);
		return new Object[] { ruleresult, directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_1_3_bookkeepingforedges_blackBBBB(
			PerformRuleResult ruleresult, EObject directedGraph, EObject tripleGraphGrammar,
			EObject directedGraphToTripleGraphGrammar) {
		if (!directedGraph.equals(tripleGraphGrammar)) {
			if (!directedGraph.equals(directedGraphToTripleGraphGrammar)) {
				if (!directedGraphToTripleGraphGrammar.equals(tripleGraphGrammar)) {
					return new Object[] { ruleresult, directedGraph, tripleGraphGrammar,
							directedGraphToTripleGraphGrammar };
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_1_3_bookkeepingforedges_greenBBBBFF(
			PerformRuleResult ruleresult, EObject directedGraph, EObject tripleGraphGrammar,
			EObject directedGraphToTripleGraphGrammar) {
		EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		String ruleresult_ruleName_prime = "TGGGrammarDirectedGraphAxiom";
		String directedGraphToTripleGraphGrammar__directedGraph____source_name_prime = "source";
		String directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime = "target";
		directedGraphToTripleGraphGrammar__directedGraph____source.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__directedGraph____source.setTrg(directedGraph);
		ruleresult.getCreatedEdges().add(directedGraphToTripleGraphGrammar__directedGraph____source);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setTrg(tripleGraphGrammar);
		ruleresult.getCreatedEdges().add(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		directedGraphToTripleGraphGrammar__directedGraph____source
				.setName(directedGraphToTripleGraphGrammar__directedGraph____source_name_prime);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target
				.setName(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime);
		return new Object[] { ruleresult, directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar,
				directedGraphToTripleGraphGrammar__directedGraph____source,
				directedGraphToTripleGraphGrammar__tripleGraphGrammar____target };
	}

	public static final void pattern_TGGGrammarDirectedGraphAxiom_1_5_registerobjects_expressionBBBBB(
			TGGGrammarDirectedGraphAxiom _this, PerformRuleResult ruleresult, EObject directedGraph,
			EObject tripleGraphGrammar, EObject directedGraphToTripleGraphGrammar) {
		_this.registerObjects_FWD(ruleresult, directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar);

	}

	public static final PerformRuleResult pattern_TGGGrammarDirectedGraphAxiom_1_6_expressionFB(
			PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_bindingFB(
			TGGGrammarDirectedGraphAxiom _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_blackFBB(EClass eClass,
			TGGGrammarDirectedGraphAxiom _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_FWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_bindingAndBlackFFB(
			TGGGrammarDirectedGraphAxiom _this) {
		Object[] result_pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_binding = pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_binding[0];

			Object[] result_pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_black = pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_2_1_preparereturnvalue_greenBF(
			EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "TGGGrammarDirectedGraphAxiom";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_2_2_corematch_bindingFB(Match match) {
		EObject _localVariable_0 = match.getObject("directedGraph");
		EObject tmpDirectedGraph = _localVariable_0;
		if (tmpDirectedGraph instanceof DirectedGraph) {
			DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
			return new Object[] { directedGraph, match };
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_TGGGrammarDirectedGraphAxiom_2_2_corematch_blackBB(
			DirectedGraph directedGraph, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		String directedGraph_name = directedGraph.getName();
		if (directedGraph_name.equals("Schema")) {
			_result.add(new Object[] { directedGraph, match });
		}

		return _result;
	}

	public static final Iterable<Object[]> pattern_TGGGrammarDirectedGraphAxiom_2_3_findcontext_blackB(
			DirectedGraph directedGraph) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		String directedGraph_name = directedGraph.getName();
		if (directedGraph_name.equals("Schema")) {
			_result.add(new Object[] { directedGraph });
		}

		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_2_3_findcontext_greenBF(
			DirectedGraph directedGraph) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		isApplicableMatch.getAllContextElements().add(directedGraph);
		return new Object[] { directedGraph, isApplicableMatch };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_2_4_solveCSP_bindingFBBB(
			TGGGrammarDirectedGraphAxiom _this, IsApplicableMatch isApplicableMatch, DirectedGraph directedGraph) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_FWD(isApplicableMatch, directedGraph);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, directedGraph };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_2_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_2_4_solveCSP_bindingAndBlackFBBB(
			TGGGrammarDirectedGraphAxiom _this, IsApplicableMatch isApplicableMatch, DirectedGraph directedGraph) {
		Object[] result_pattern_TGGGrammarDirectedGraphAxiom_2_4_solveCSP_binding = pattern_TGGGrammarDirectedGraphAxiom_2_4_solveCSP_bindingFBBB(
				_this, isApplicableMatch, directedGraph);
		if (result_pattern_TGGGrammarDirectedGraphAxiom_2_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_TGGGrammarDirectedGraphAxiom_2_4_solveCSP_binding[0];

			Object[] result_pattern_TGGGrammarDirectedGraphAxiom_2_4_solveCSP_black = pattern_TGGGrammarDirectedGraphAxiom_2_4_solveCSP_blackB(
					csp);
			if (result_pattern_TGGGrammarDirectedGraphAxiom_2_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, directedGraph };
			}
		}
		return null;
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_2_5_checkCSP_expressionFBB(
			TGGGrammarDirectedGraphAxiom _this, CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_2_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_2_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "TGGGrammarDirectedGraphAxiom";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_TGGGrammarDirectedGraphAxiom_2_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_10_1_initialbindings_blackBBB(
			TGGGrammarDirectedGraphAxiom _this, Match match, TripleGraphGrammar tripleGraphGrammar) {
		return new Object[] { _this, match, tripleGraphGrammar };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_10_2_SolveCSP_bindingFBBB(
			TGGGrammarDirectedGraphAxiom _this, Match match, TripleGraphGrammar tripleGraphGrammar) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_BWD(match, tripleGraphGrammar);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, tripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_10_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_10_2_SolveCSP_bindingAndBlackFBBB(
			TGGGrammarDirectedGraphAxiom _this, Match match, TripleGraphGrammar tripleGraphGrammar) {
		Object[] result_pattern_TGGGrammarDirectedGraphAxiom_10_2_SolveCSP_binding = pattern_TGGGrammarDirectedGraphAxiom_10_2_SolveCSP_bindingFBBB(
				_this, match, tripleGraphGrammar);
		if (result_pattern_TGGGrammarDirectedGraphAxiom_10_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_TGGGrammarDirectedGraphAxiom_10_2_SolveCSP_binding[0];

			Object[] result_pattern_TGGGrammarDirectedGraphAxiom_10_2_SolveCSP_black = pattern_TGGGrammarDirectedGraphAxiom_10_2_SolveCSP_blackB(
					csp);
			if (result_pattern_TGGGrammarDirectedGraphAxiom_10_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, tripleGraphGrammar };
			}
		}
		return null;
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_10_3_CheckCSP_expressionFBB(
			TGGGrammarDirectedGraphAxiom _this, CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_10_4_collectelementstobetranslated_blackBB(
			Match match, TripleGraphGrammar tripleGraphGrammar) {
		return new Object[] { match, tripleGraphGrammar };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_10_4_collectelementstobetranslated_greenBB(
			Match match, TripleGraphGrammar tripleGraphGrammar) {
		match.getToBeTranslatedNodes().add(tripleGraphGrammar);
		return new Object[] { match, tripleGraphGrammar };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_10_5_collectcontextelements_blackBB(Match match,
			TripleGraphGrammar tripleGraphGrammar) {
		return new Object[] { match, tripleGraphGrammar };
	}

	public static final void pattern_TGGGrammarDirectedGraphAxiom_10_6_registerobjectstomatch_expressionBBB(
			TGGGrammarDirectedGraphAxiom _this, Match match, TripleGraphGrammar tripleGraphGrammar) {
		_this.registerObjectsToMatch_BWD(match, tripleGraphGrammar);

	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_10_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_10_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_bindingFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("tripleGraphGrammar");
		EObject tmpTripleGraphGrammar = _localVariable_0;
		if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
			return new Object[] { tripleGraphGrammar, isApplicableMatch };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_blackBFBB(
			TripleGraphGrammar tripleGraphGrammar, TGGGrammarDirectedGraphAxiom _this,
			IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { tripleGraphGrammar, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_bindingAndBlackFFBB(
			TGGGrammarDirectedGraphAxiom _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_binding = pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_bindingFB(
				isApplicableMatch);
		if (result_pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_binding != null) {
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result_pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_binding[0];

			Object[] result_pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_black = pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_blackBFBB(
					tripleGraphGrammar, _this, isApplicableMatch);
			if (result_pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_black[1];

				return new Object[] { tripleGraphGrammar, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_11_1_performtransformation_greenFBF(
			TripleGraphGrammar tripleGraphGrammar) {
		DirectedGraph directedGraph = org.moflon.ide.visualization.dot.language.LanguageFactory.eINSTANCE
				.createDirectedGraph();
		DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = SchemaFactory.eINSTANCE
				.createDirectedGraphToTripleGraphGrammar();
		String directedGraph_name_prime = "Schema";
		directedGraphToTripleGraphGrammar.setSource(directedGraph);
		directedGraphToTripleGraphGrammar.setTarget(tripleGraphGrammar);
		directedGraph.setName(directedGraph_name_prime);
		return new Object[] { directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_11_2_collecttranslatedelements_blackBBB(
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		return new Object[] { directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_11_2_collecttranslatedelements_greenFBBB(
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar,
			DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getCreatedElements().add(directedGraph);
		ruleresult.getTranslatedElements().add(tripleGraphGrammar);
		ruleresult.getCreatedLinkElements().add(directedGraphToTripleGraphGrammar);
		return new Object[] { ruleresult, directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_11_3_bookkeepingforedges_blackBBBB(
			PerformRuleResult ruleresult, EObject directedGraph, EObject tripleGraphGrammar,
			EObject directedGraphToTripleGraphGrammar) {
		if (!directedGraph.equals(tripleGraphGrammar)) {
			if (!directedGraph.equals(directedGraphToTripleGraphGrammar)) {
				if (!directedGraphToTripleGraphGrammar.equals(tripleGraphGrammar)) {
					return new Object[] { ruleresult, directedGraph, tripleGraphGrammar,
							directedGraphToTripleGraphGrammar };
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_11_3_bookkeepingforedges_greenBBBBFF(
			PerformRuleResult ruleresult, EObject directedGraph, EObject tripleGraphGrammar,
			EObject directedGraphToTripleGraphGrammar) {
		EMoflonEdge directedGraphToTripleGraphGrammar__directedGraph____source = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		EMoflonEdge directedGraphToTripleGraphGrammar__tripleGraphGrammar____target = RuntimeFactory.eINSTANCE
				.createEMoflonEdge();
		String ruleresult_ruleName_prime = "TGGGrammarDirectedGraphAxiom";
		String directedGraphToTripleGraphGrammar__directedGraph____source_name_prime = "source";
		String directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime = "target";
		directedGraphToTripleGraphGrammar__directedGraph____source.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__directedGraph____source.setTrg(directedGraph);
		ruleresult.getCreatedEdges().add(directedGraphToTripleGraphGrammar__directedGraph____source);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setSrc(directedGraphToTripleGraphGrammar);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target.setTrg(tripleGraphGrammar);
		ruleresult.getCreatedEdges().add(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		directedGraphToTripleGraphGrammar__directedGraph____source
				.setName(directedGraphToTripleGraphGrammar__directedGraph____source_name_prime);
		directedGraphToTripleGraphGrammar__tripleGraphGrammar____target
				.setName(directedGraphToTripleGraphGrammar__tripleGraphGrammar____target_name_prime);
		return new Object[] { ruleresult, directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar,
				directedGraphToTripleGraphGrammar__directedGraph____source,
				directedGraphToTripleGraphGrammar__tripleGraphGrammar____target };
	}

	public static final void pattern_TGGGrammarDirectedGraphAxiom_11_5_registerobjects_expressionBBBBB(
			TGGGrammarDirectedGraphAxiom _this, PerformRuleResult ruleresult, EObject directedGraph,
			EObject tripleGraphGrammar, EObject directedGraphToTripleGraphGrammar) {
		_this.registerObjects_BWD(ruleresult, directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar);

	}

	public static final PerformRuleResult pattern_TGGGrammarDirectedGraphAxiom_11_6_expressionFB(
			PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_bindingFB(
			TGGGrammarDirectedGraphAxiom _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_blackFBB(EClass eClass,
			TGGGrammarDirectedGraphAxiom _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_BWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_bindingAndBlackFFB(
			TGGGrammarDirectedGraphAxiom _this) {
		Object[] result_pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_binding = pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_binding[0];

			Object[] result_pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_black = pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_12_1_preparereturnvalue_greenBF(
			EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "TGGGrammarDirectedGraphAxiom";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_12_2_corematch_bindingFB(Match match) {
		EObject _localVariable_0 = match.getObject("tripleGraphGrammar");
		EObject tmpTripleGraphGrammar = _localVariable_0;
		if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
			return new Object[] { tripleGraphGrammar, match };
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_TGGGrammarDirectedGraphAxiom_12_2_corematch_blackBB(
			TripleGraphGrammar tripleGraphGrammar, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		_result.add(new Object[] { tripleGraphGrammar, match });
		return _result;
	}

	public static final Iterable<Object[]> pattern_TGGGrammarDirectedGraphAxiom_12_3_findcontext_blackB(
			TripleGraphGrammar tripleGraphGrammar) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		_result.add(new Object[] { tripleGraphGrammar });
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_12_3_findcontext_greenBF(
			TripleGraphGrammar tripleGraphGrammar) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		isApplicableMatch.getAllContextElements().add(tripleGraphGrammar);
		return new Object[] { tripleGraphGrammar, isApplicableMatch };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_12_4_solveCSP_bindingFBBB(
			TGGGrammarDirectedGraphAxiom _this, IsApplicableMatch isApplicableMatch,
			TripleGraphGrammar tripleGraphGrammar) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_BWD(isApplicableMatch, tripleGraphGrammar);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, tripleGraphGrammar };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_12_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_12_4_solveCSP_bindingAndBlackFBBB(
			TGGGrammarDirectedGraphAxiom _this, IsApplicableMatch isApplicableMatch,
			TripleGraphGrammar tripleGraphGrammar) {
		Object[] result_pattern_TGGGrammarDirectedGraphAxiom_12_4_solveCSP_binding = pattern_TGGGrammarDirectedGraphAxiom_12_4_solveCSP_bindingFBBB(
				_this, isApplicableMatch, tripleGraphGrammar);
		if (result_pattern_TGGGrammarDirectedGraphAxiom_12_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_TGGGrammarDirectedGraphAxiom_12_4_solveCSP_binding[0];

			Object[] result_pattern_TGGGrammarDirectedGraphAxiom_12_4_solveCSP_black = pattern_TGGGrammarDirectedGraphAxiom_12_4_solveCSP_blackB(
					csp);
			if (result_pattern_TGGGrammarDirectedGraphAxiom_12_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, tripleGraphGrammar };
			}
		}
		return null;
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_12_5_checkCSP_expressionFBB(
			TGGGrammarDirectedGraphAxiom _this, CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_12_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_12_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "TGGGrammarDirectedGraphAxiom";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_TGGGrammarDirectedGraphAxiom_12_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_bindingFB(
			TGGGrammarDirectedGraphAxiom _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_blackFBBF(EClass __eClass,
			TGGGrammarDirectedGraphAxiom _this) {
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

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_bindingAndBlackFFBF(
			TGGGrammarDirectedGraphAxiom _this) {
		Object[] result_pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_binding = pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_binding[0];

			Object[] result_pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_black = pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_20_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_TGGGrammarDirectedGraphAxiom_20_2_testcorematchandDECs_blackB(
			DirectedGraph directedGraph) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		String directedGraph_name = directedGraph.getName();
		if (directedGraph_name.equals("Schema")) {
			_result.add(new Object[] { directedGraph });
		}

		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_20_2_testcorematchandDECs_greenFB(
			EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBB(
			TGGGrammarDirectedGraphAxiom _this, Match match, DirectedGraph directedGraph) {
		boolean _localVariable_0 = _this.isAppropriate_FWD(match, directedGraph);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			TGGGrammarDirectedGraphAxiom _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_FWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_20_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_20_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_TGGGrammarDirectedGraphAxiom_20_6_expressionFB(
			EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_bindingFB(
			TGGGrammarDirectedGraphAxiom _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_blackFBBF(EClass __eClass,
			TGGGrammarDirectedGraphAxiom _this) {
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

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_bindingAndBlackFFBF(
			TGGGrammarDirectedGraphAxiom _this) {
		Object[] result_pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_binding = pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_binding[0];

			Object[] result_pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_black = pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_21_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_TGGGrammarDirectedGraphAxiom_21_2_testcorematchandDECs_blackB(
			TripleGraphGrammar tripleGraphGrammar) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		_result.add(new Object[] { tripleGraphGrammar });
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_21_2_testcorematchandDECs_greenFB(
			EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBB(
			TGGGrammarDirectedGraphAxiom _this, Match match, TripleGraphGrammar tripleGraphGrammar) {
		boolean _localVariable_0 = _this.isAppropriate_BWD(match, tripleGraphGrammar);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			TGGGrammarDirectedGraphAxiom _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_BWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_21_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_21_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_TGGGrammarDirectedGraphAxiom_21_6_expressionFB(
			EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_24_1_prepare_blackB(
			TGGGrammarDirectedGraphAxiom _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_24_1_prepare_greenF() {
		IsApplicableRuleResult result = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		return new Object[] { result };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_24_2_matchsrctrgcontext_bindingFFBB(
			Match sourceMatch, Match targetMatch) {
		EObject _localVariable_0 = sourceMatch.getObject("directedGraph");
		EObject _localVariable_1 = targetMatch.getObject("tripleGraphGrammar");
		EObject tmpDirectedGraph = _localVariable_0;
		EObject tmpTripleGraphGrammar = _localVariable_1;
		if (tmpDirectedGraph instanceof DirectedGraph) {
			DirectedGraph directedGraph = (DirectedGraph) tmpDirectedGraph;
			if (tmpTripleGraphGrammar instanceof TripleGraphGrammar) {
				TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) tmpTripleGraphGrammar;
				return new Object[] { directedGraph, tripleGraphGrammar, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_24_2_matchsrctrgcontext_blackBBBB(
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar, Match sourceMatch, Match targetMatch) {
		if (!sourceMatch.equals(targetMatch)) {
			String directedGraph_name = directedGraph.getName();
			if (directedGraph_name.equals("Schema")) {
				return new Object[] { directedGraph, tripleGraphGrammar, sourceMatch, targetMatch };
			}

		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_24_2_matchsrctrgcontext_bindingAndBlackFFBB(
			Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_TGGGrammarDirectedGraphAxiom_24_2_matchsrctrgcontext_binding = pattern_TGGGrammarDirectedGraphAxiom_24_2_matchsrctrgcontext_bindingFFBB(
				sourceMatch, targetMatch);
		if (result_pattern_TGGGrammarDirectedGraphAxiom_24_2_matchsrctrgcontext_binding != null) {
			DirectedGraph directedGraph = (DirectedGraph) result_pattern_TGGGrammarDirectedGraphAxiom_24_2_matchsrctrgcontext_binding[0];
			TripleGraphGrammar tripleGraphGrammar = (TripleGraphGrammar) result_pattern_TGGGrammarDirectedGraphAxiom_24_2_matchsrctrgcontext_binding[1];

			Object[] result_pattern_TGGGrammarDirectedGraphAxiom_24_2_matchsrctrgcontext_black = pattern_TGGGrammarDirectedGraphAxiom_24_2_matchsrctrgcontext_blackBBBB(
					directedGraph, tripleGraphGrammar, sourceMatch, targetMatch);
			if (result_pattern_TGGGrammarDirectedGraphAxiom_24_2_matchsrctrgcontext_black != null) {

				return new Object[] { directedGraph, tripleGraphGrammar, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_24_3_solvecsp_bindingFBBBBB(
			TGGGrammarDirectedGraphAxiom _this, DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar,
			Match sourceMatch, Match targetMatch) {
		CSP _localVariable_2 = _this.isApplicable_solveCsp_CC(directedGraph, tripleGraphGrammar, sourceMatch,
				targetMatch);
		CSP csp = _localVariable_2;
		if (csp != null) {
			return new Object[] { csp, _this, directedGraph, tripleGraphGrammar, sourceMatch, targetMatch };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_24_3_solvecsp_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_24_3_solvecsp_bindingAndBlackFBBBBB(
			TGGGrammarDirectedGraphAxiom _this, DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar,
			Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_TGGGrammarDirectedGraphAxiom_24_3_solvecsp_binding = pattern_TGGGrammarDirectedGraphAxiom_24_3_solvecsp_bindingFBBBBB(
				_this, directedGraph, tripleGraphGrammar, sourceMatch, targetMatch);
		if (result_pattern_TGGGrammarDirectedGraphAxiom_24_3_solvecsp_binding != null) {
			CSP csp = (CSP) result_pattern_TGGGrammarDirectedGraphAxiom_24_3_solvecsp_binding[0];

			Object[] result_pattern_TGGGrammarDirectedGraphAxiom_24_3_solvecsp_black = pattern_TGGGrammarDirectedGraphAxiom_24_3_solvecsp_blackB(
					csp);
			if (result_pattern_TGGGrammarDirectedGraphAxiom_24_3_solvecsp_black != null) {

				return new Object[] { csp, _this, directedGraph, tripleGraphGrammar, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_24_4_checkCSP_expressionFB(CSP csp) {
		boolean _localVariable_0 = csp.check();
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Iterable<Object[]> pattern_TGGGrammarDirectedGraphAxiom_24_5_matchcorrcontext_blackBB(
			Match sourceMatch, Match targetMatch) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!sourceMatch.equals(targetMatch)) {
			_result.add(new Object[] { sourceMatch, targetMatch });
		}
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_24_5_matchcorrcontext_greenBBF(Match sourceMatch,
			Match targetMatch) {
		CCMatch ccMatch = RuntimeFactory.eINSTANCE.createCCMatch();
		String ccMatch_ruleName_prime = "TGGGrammarDirectedGraphAxiom";
		ccMatch.setSourceMatch(sourceMatch);
		ccMatch.setTargetMatch(targetMatch);
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { sourceMatch, targetMatch, ccMatch };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_24_6_createcorrespondence_blackBBB(
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar, CCMatch ccMatch) {
		return new Object[] { directedGraph, tripleGraphGrammar, ccMatch };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_24_6_createcorrespondence_greenBBFB(
			DirectedGraph directedGraph, TripleGraphGrammar tripleGraphGrammar, CCMatch ccMatch) {
		DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = SchemaFactory.eINSTANCE
				.createDirectedGraphToTripleGraphGrammar();
		directedGraphToTripleGraphGrammar.setSource(directedGraph);
		directedGraphToTripleGraphGrammar.setTarget(tripleGraphGrammar);
		ccMatch.getCreateCorr().add(directedGraphToTripleGraphGrammar);
		return new Object[] { directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar, ccMatch };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_24_7_addtoreturnedresult_blackBB(
			IsApplicableRuleResult result, CCMatch ccMatch) {
		return new Object[] { result, ccMatch };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_24_7_addtoreturnedresult_greenBB(
			IsApplicableRuleResult result, CCMatch ccMatch) {
		result.getIsApplicableMatch().add(ccMatch);
		boolean result_success_prime = Boolean.valueOf(true);
		String ccMatch_ruleName_prime = "TGGGrammarDirectedGraphAxiom";
		result.setSuccess(Boolean.valueOf(result_success_prime));
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { result, ccMatch };
	}

	public static final IsApplicableRuleResult pattern_TGGGrammarDirectedGraphAxiom_24_8_expressionFB(
			IsApplicableRuleResult result) {
		IsApplicableRuleResult _result = result;
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_27_1_matchtggpattern_blackB(
			DirectedGraph directedGraph) {
		return new Object[] { directedGraph };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_27_1_matchtggpattern_greenB(
			DirectedGraph directedGraph) {
		String directedGraph_name_prime = "Schema";
		directedGraph.setName(directedGraph_name_prime);
		return new Object[] { directedGraph };
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_27_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_27_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_28_1_matchtggpattern_blackB(
			TripleGraphGrammar tripleGraphGrammar) {
		return new Object[] { tripleGraphGrammar };
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_28_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_28_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_29_1_createresult_blackB(
			TGGGrammarDirectedGraphAxiom _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_29_1_createresult_greenFF() {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		ModelgeneratorRuleResult ruleResult = RuntimeFactory.eINSTANCE.createModelgeneratorRuleResult();
		boolean ruleResult_success_prime = false;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		return new Object[] { isApplicableMatch, ruleResult };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_29_2_isapplicablecore_blackB(
			TGGGrammarDirectedGraphAxiom _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_29_3_solveCSP_bindingFBBB(
			TGGGrammarDirectedGraphAxiom _this, IsApplicableMatch isApplicableMatch,
			ModelgeneratorRuleResult ruleResult) {
		CSP _localVariable_0 = _this.generateModel_solveCsp_BWD(isApplicableMatch, ruleResult);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, ruleResult };
		}
		return null;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_29_3_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_29_3_solveCSP_bindingAndBlackFBBB(
			TGGGrammarDirectedGraphAxiom _this, IsApplicableMatch isApplicableMatch,
			ModelgeneratorRuleResult ruleResult) {
		Object[] result_pattern_TGGGrammarDirectedGraphAxiom_29_3_solveCSP_binding = pattern_TGGGrammarDirectedGraphAxiom_29_3_solveCSP_bindingFBBB(
				_this, isApplicableMatch, ruleResult);
		if (result_pattern_TGGGrammarDirectedGraphAxiom_29_3_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_TGGGrammarDirectedGraphAxiom_29_3_solveCSP_binding[0];

			Object[] result_pattern_TGGGrammarDirectedGraphAxiom_29_3_solveCSP_black = pattern_TGGGrammarDirectedGraphAxiom_29_3_solveCSP_blackB(
					csp);
			if (result_pattern_TGGGrammarDirectedGraphAxiom_29_3_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, ruleResult };
			}
		}
		return null;
	}

	public static final boolean pattern_TGGGrammarDirectedGraphAxiom_29_4_checkCSP_expressionFBB(
			TGGGrammarDirectedGraphAxiom _this, CSP csp) {
		boolean _localVariable_0 = _this.generateModel_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_29_6_perform_blackB(
			ModelgeneratorRuleResult ruleResult) {
		return new Object[] { ruleResult };
	}

	public static final Object[] pattern_TGGGrammarDirectedGraphAxiom_29_6_perform_greenFFFB(
			ModelgeneratorRuleResult ruleResult) {
		DirectedGraph directedGraph = org.moflon.ide.visualization.dot.language.LanguageFactory.eINSTANCE
				.createDirectedGraph();
		TripleGraphGrammar tripleGraphGrammar = LanguageFactory.eINSTANCE.createTripleGraphGrammar();
		DirectedGraphToTripleGraphGrammar directedGraphToTripleGraphGrammar = SchemaFactory.eINSTANCE
				.createDirectedGraphToTripleGraphGrammar();
		String directedGraph_name_prime = "Schema";
		boolean ruleResult_success_prime = Boolean.valueOf(true);
		int _localVariable_0 = ruleResult.getIncrementedPerformCount();
		ruleResult.getSourceObjects().add(directedGraph);
		ruleResult.getTargetObjects().add(tripleGraphGrammar);
		directedGraphToTripleGraphGrammar.setSource(directedGraph);
		directedGraphToTripleGraphGrammar.setTarget(tripleGraphGrammar);
		ruleResult.getCorrObjects().add(directedGraphToTripleGraphGrammar);
		directedGraph.setName(directedGraph_name_prime);
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		int ruleResult_performCount_prime = Integer.valueOf(_localVariable_0);
		ruleResult.setPerformCount(Integer.valueOf(ruleResult_performCount_prime));
		return new Object[] { directedGraph, tripleGraphGrammar, directedGraphToTripleGraphGrammar, ruleResult };
	}

	public static final ModelgeneratorRuleResult pattern_TGGGrammarDirectedGraphAxiom_29_7_expressionFB(
			ModelgeneratorRuleResult ruleResult) {
		ModelgeneratorRuleResult _result = ruleResult;
		return _result;
	}

	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //TGGGrammarDirectedGraphAxiomImpl
