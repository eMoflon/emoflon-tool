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

import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.PackageAxiom;
import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.RulesPackage;

import org.moflon.ide.visualization.dot.language.ClassGraph;
import org.moflon.ide.visualization.dot.language.LanguageFactory;

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
 * An implementation of the model object '<em><b>Package Axiom</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class PackageAxiomImpl extends AbstractRuleImpl implements PackageAxiom {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PackageAxiomImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulesPackage.eINSTANCE.getPackageAxiom();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_FWD(Match match, ClassGraph graph) {
		// initial bindings
		Object[] result1_black = PackageAxiomImpl.pattern_PackageAxiom_0_1_initialbindings_blackBBB(this, match, graph);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[graph] = " + graph + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = PackageAxiomImpl.pattern_PackageAxiom_0_2_SolveCSP_bindingAndBlackFBBB(this,
				match, graph);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[graph] = " + graph + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (PackageAxiomImpl.pattern_PackageAxiom_0_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = PackageAxiomImpl
					.pattern_PackageAxiom_0_4_collectelementstobetranslated_blackBB(match, graph);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[graph] = " + graph + ".");
			}
			PackageAxiomImpl.pattern_PackageAxiom_0_4_collectelementstobetranslated_greenBB(match, graph);

			// collect context elements
			Object[] result5_black = PackageAxiomImpl.pattern_PackageAxiom_0_5_collectcontextelements_blackBB(match,
					graph);
			if (result5_black == null) {
				throw new RuntimeException("Pattern matching in node [collect context elements] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[graph] = " + graph + ".");
			}
			// register objects to match
			PackageAxiomImpl.pattern_PackageAxiom_0_6_registerobjectstomatch_expressionBBB(this, match, graph);
			return PackageAxiomImpl.pattern_PackageAxiom_0_7_expressionF();
		} else {
			return PackageAxiomImpl.pattern_PackageAxiom_0_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_FWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = PackageAxiomImpl
				.pattern_PackageAxiom_1_1_performtransformation_bindingAndBlackFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		ClassGraph graph = (ClassGraph) result1_bindingAndBlack[0];
		// CSP csp = (CSP) result1_bindingAndBlack[1];
		Object[] result1_green = PackageAxiomImpl.pattern_PackageAxiom_1_1_performtransformation_greenBFF(graph);
		ClassGraphToEPackage grapgToPackage = (ClassGraphToEPackage) result1_green[1];
		EPackage rootPackage = (EPackage) result1_green[2];

		// collect translated elements
		Object[] result2_black = PackageAxiomImpl.pattern_PackageAxiom_1_2_collecttranslatedelements_blackBBB(graph,
				grapgToPackage, rootPackage);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[graph] = " + graph + ", " + "[grapgToPackage] = " + grapgToPackage + ", " + "[rootPackage] = "
					+ rootPackage + ".");
		}
		Object[] result2_green = PackageAxiomImpl.pattern_PackageAxiom_1_2_collecttranslatedelements_greenFBBB(graph,
				grapgToPackage, rootPackage);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = PackageAxiomImpl.pattern_PackageAxiom_1_3_bookkeepingforedges_blackBBBB(ruleresult,
				graph, grapgToPackage, rootPackage);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[graph] = " + graph + ", " + "[grapgToPackage] = "
					+ grapgToPackage + ", " + "[rootPackage] = " + rootPackage + ".");
		}
		PackageAxiomImpl.pattern_PackageAxiom_1_3_bookkeepingforedges_greenBBBBFF(ruleresult, graph, grapgToPackage,
				rootPackage);
		// EMoflonEdge grapgToPackage__graph____source = (EMoflonEdge) result3_green[4];
		// EMoflonEdge grapgToPackage__rootPackage____target = (EMoflonEdge) result3_green[5];

		// perform postprocessing story node is empty
		// register objects
		PackageAxiomImpl.pattern_PackageAxiom_1_5_registerobjects_expressionBBBBB(this, ruleresult, graph,
				grapgToPackage, rootPackage);
		return PackageAxiomImpl.pattern_PackageAxiom_1_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_FWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = PackageAxiomImpl
				.pattern_PackageAxiom_2_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = PackageAxiomImpl.pattern_PackageAxiom_2_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = PackageAxiomImpl.pattern_PackageAxiom_2_2_corematch_bindingFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		ClassGraph graph = (ClassGraph) result2_binding[0];
		for (Object[] result2_black : PackageAxiomImpl.pattern_PackageAxiom_2_2_corematch_blackBB(graph, match)) {
			// ForEach find context
			for (Object[] result3_black : PackageAxiomImpl.pattern_PackageAxiom_2_3_findcontext_blackB(graph)) {
				Object[] result3_green = PackageAxiomImpl.pattern_PackageAxiom_2_3_findcontext_greenBF(graph);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[1];

				// solve CSP
				Object[] result4_bindingAndBlack = PackageAxiomImpl
						.pattern_PackageAxiom_2_4_solveCSP_bindingAndBlackFBBB(this, isApplicableMatch, graph);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException(
							"Pattern matching in node [solve CSP] failed." + " Variables: " + "[this] = " + this + ", "
									+ "[isApplicableMatch] = " + isApplicableMatch + ", " + "[graph] = " + graph + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (PackageAxiomImpl.pattern_PackageAxiom_2_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = PackageAxiomImpl
							.pattern_PackageAxiom_2_6_addmatchtoruleresult_blackBB(ruleresult, isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					PackageAxiomImpl.pattern_PackageAxiom_2_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return PackageAxiomImpl.pattern_PackageAxiom_2_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_FWD(Match match, ClassGraph graph) {
		match.registerObject("graph", graph);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_FWD(Match match, ClassGraph graph) {// Create CSP
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
	public CSP isApplicable_solveCsp_FWD(IsApplicableMatch isApplicableMatch, ClassGraph graph) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables

		// Create unbound variables

		// Create constraints

		// Solve CSP

		// Snapshot pattern match on which CSP is solved
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
	public void registerObjects_FWD(PerformRuleResult ruleresult, EObject graph, EObject grapgToPackage,
			EObject rootPackage) {
		ruleresult.registerObject("graph", graph);
		ruleresult.registerObject("grapgToPackage", grapgToPackage);
		ruleresult.registerObject("rootPackage", rootPackage);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_FWD(Match match) {
		return true && org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("graph").eClass())
				.equals("language.ClassGraph.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_BWD(Match match, EPackage rootPackage) {
		// initial bindings
		Object[] result1_black = PackageAxiomImpl.pattern_PackageAxiom_10_1_initialbindings_blackBBB(this, match,
				rootPackage);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = PackageAxiomImpl.pattern_PackageAxiom_10_2_SolveCSP_bindingAndBlackFBBB(this,
				match, rootPackage);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (PackageAxiomImpl.pattern_PackageAxiom_10_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = PackageAxiomImpl
					.pattern_PackageAxiom_10_4_collectelementstobetranslated_blackBB(match, rootPackage);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ".");
			}
			PackageAxiomImpl.pattern_PackageAxiom_10_4_collectelementstobetranslated_greenBB(match, rootPackage);

			// collect context elements
			Object[] result5_black = PackageAxiomImpl.pattern_PackageAxiom_10_5_collectcontextelements_blackBB(match,
					rootPackage);
			if (result5_black == null) {
				throw new RuntimeException("Pattern matching in node [collect context elements] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ".");
			}
			// register objects to match
			PackageAxiomImpl.pattern_PackageAxiom_10_6_registerobjectstomatch_expressionBBB(this, match, rootPackage);
			return PackageAxiomImpl.pattern_PackageAxiom_10_7_expressionF();
		} else {
			return PackageAxiomImpl.pattern_PackageAxiom_10_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_BWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = PackageAxiomImpl
				.pattern_PackageAxiom_11_1_performtransformation_bindingAndBlackFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		EPackage rootPackage = (EPackage) result1_bindingAndBlack[0];
		// CSP csp = (CSP) result1_bindingAndBlack[1];
		Object[] result1_green = PackageAxiomImpl.pattern_PackageAxiom_11_1_performtransformation_greenFFB(rootPackage);
		ClassGraph graph = (ClassGraph) result1_green[0];
		ClassGraphToEPackage grapgToPackage = (ClassGraphToEPackage) result1_green[1];

		// collect translated elements
		Object[] result2_black = PackageAxiomImpl.pattern_PackageAxiom_11_2_collecttranslatedelements_blackBBB(graph,
				grapgToPackage, rootPackage);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[graph] = " + graph + ", " + "[grapgToPackage] = " + grapgToPackage + ", " + "[rootPackage] = "
					+ rootPackage + ".");
		}
		Object[] result2_green = PackageAxiomImpl.pattern_PackageAxiom_11_2_collecttranslatedelements_greenFBBB(graph,
				grapgToPackage, rootPackage);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = PackageAxiomImpl.pattern_PackageAxiom_11_3_bookkeepingforedges_blackBBBB(ruleresult,
				graph, grapgToPackage, rootPackage);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[graph] = " + graph + ", " + "[grapgToPackage] = "
					+ grapgToPackage + ", " + "[rootPackage] = " + rootPackage + ".");
		}
		PackageAxiomImpl.pattern_PackageAxiom_11_3_bookkeepingforedges_greenBBBBFF(ruleresult, graph, grapgToPackage,
				rootPackage);
		// EMoflonEdge grapgToPackage__graph____source = (EMoflonEdge) result3_green[4];
		// EMoflonEdge grapgToPackage__rootPackage____target = (EMoflonEdge) result3_green[5];

		// perform postprocessing story node is empty
		// register objects
		PackageAxiomImpl.pattern_PackageAxiom_11_5_registerobjects_expressionBBBBB(this, ruleresult, graph,
				grapgToPackage, rootPackage);
		return PackageAxiomImpl.pattern_PackageAxiom_11_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_BWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = PackageAxiomImpl
				.pattern_PackageAxiom_12_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = PackageAxiomImpl
				.pattern_PackageAxiom_12_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = PackageAxiomImpl.pattern_PackageAxiom_12_2_corematch_bindingFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		EPackage rootPackage = (EPackage) result2_binding[0];
		for (Object[] result2_black : PackageAxiomImpl.pattern_PackageAxiom_12_2_corematch_blackBB(rootPackage,
				match)) {
			// ForEach find context
			for (Object[] result3_black : PackageAxiomImpl.pattern_PackageAxiom_12_3_findcontext_blackB(rootPackage)) {
				Object[] result3_green = PackageAxiomImpl.pattern_PackageAxiom_12_3_findcontext_greenBF(rootPackage);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[1];

				// solve CSP
				Object[] result4_bindingAndBlack = PackageAxiomImpl
						.pattern_PackageAxiom_12_4_solveCSP_bindingAndBlackFBBB(this, isApplicableMatch, rootPackage);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[rootPackage] = " + rootPackage + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (PackageAxiomImpl.pattern_PackageAxiom_12_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = PackageAxiomImpl
							.pattern_PackageAxiom_12_6_addmatchtoruleresult_blackBB(ruleresult, isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					PackageAxiomImpl.pattern_PackageAxiom_12_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return PackageAxiomImpl.pattern_PackageAxiom_12_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_BWD(Match match, EPackage rootPackage) {
		match.registerObject("rootPackage", rootPackage);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_BWD(Match match, EPackage rootPackage) {// Create CSP
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
	public CSP isApplicable_solveCsp_BWD(IsApplicableMatch isApplicableMatch, EPackage rootPackage) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables

		// Create unbound variables

		// Create constraints

		// Solve CSP

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("rootPackage", rootPackage);
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
	public void registerObjects_BWD(PerformRuleResult ruleresult, EObject graph, EObject grapgToPackage,
			EObject rootPackage) {
		ruleresult.registerObject("graph", graph);
		ruleresult.registerObject("grapgToPackage", grapgToPackage);
		ruleresult.registerObject("rootPackage", rootPackage);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_BWD(Match match) {
		return true && org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("rootPackage").eClass())
				.equals("ecore.EPackage.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_BWD_EPackage_1(EPackage rootPackage) {
		// prepare return value
		Object[] result1_bindingAndBlack = PackageAxiomImpl
				.pattern_PackageAxiom_20_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = PackageAxiomImpl.pattern_PackageAxiom_20_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : PackageAxiomImpl
				.pattern_PackageAxiom_20_2_testcorematchandDECs_blackB(rootPackage)) {
			Object[] result2_green = PackageAxiomImpl.pattern_PackageAxiom_20_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (PackageAxiomImpl.pattern_PackageAxiom_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBB(
					this, match, rootPackage)) {
				// Ensure that the correct types of elements are matched
				if (PackageAxiomImpl
						.pattern_PackageAxiom_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(this,
								match)) {

					// Add match to rule result
					Object[] result5_black = PackageAxiomImpl.pattern_PackageAxiom_20_5_Addmatchtoruleresult_blackBBBB(
							match, __performOperation, __result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					PackageAxiomImpl.pattern_PackageAxiom_20_5_Addmatchtoruleresult_greenBBBB(match, __performOperation,
							__result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return PackageAxiomImpl.pattern_PackageAxiom_20_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_FWD_ClassGraph_1(ClassGraph graph) {
		// prepare return value
		Object[] result1_bindingAndBlack = PackageAxiomImpl
				.pattern_PackageAxiom_21_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = PackageAxiomImpl.pattern_PackageAxiom_21_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : PackageAxiomImpl.pattern_PackageAxiom_21_2_testcorematchandDECs_blackB(graph)) {
			Object[] result2_green = PackageAxiomImpl.pattern_PackageAxiom_21_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (PackageAxiomImpl.pattern_PackageAxiom_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBB(
					this, match, graph)) {
				// Ensure that the correct types of elements are matched
				if (PackageAxiomImpl
						.pattern_PackageAxiom_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(this,
								match)) {

					// Add match to rule result
					Object[] result5_black = PackageAxiomImpl.pattern_PackageAxiom_21_5_Addmatchtoruleresult_blackBBBB(
							match, __performOperation, __result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					PackageAxiomImpl.pattern_PackageAxiom_21_5_Addmatchtoruleresult_greenBBBB(match, __performOperation,
							__result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return PackageAxiomImpl.pattern_PackageAxiom_21_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintsRuleResult checkAttributes_FWD(TripleMatch __tripleMatch) {
		AttributeConstraintsRuleResult ruleResult = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE
				.createAttributeConstraintsRuleResult();
		ruleResult.setRule("PackageAxiom");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

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
		ruleResult.setRule("PackageAxiom");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

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
		Object[] result1_black = PackageAxiomImpl.pattern_PackageAxiom_24_1_prepare_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [prepare] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = PackageAxiomImpl.pattern_PackageAxiom_24_1_prepare_greenF();
		IsApplicableRuleResult result = (IsApplicableRuleResult) result1_green[0];

		// match src trg context
		Object[] result2_bindingAndBlack = PackageAxiomImpl
				.pattern_PackageAxiom_24_2_matchsrctrgcontext_bindingAndBlackFFBB(sourceMatch, targetMatch);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [match src trg context] failed." + " Variables: "
					+ "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = " + targetMatch + ".");
		}
		ClassGraph graph = (ClassGraph) result2_bindingAndBlack[0];
		EPackage rootPackage = (EPackage) result2_bindingAndBlack[1];

		// solve csp
		Object[] result3_bindingAndBlack = PackageAxiomImpl.pattern_PackageAxiom_24_3_solvecsp_bindingAndBlackFBBBBB(
				this, graph, rootPackage, sourceMatch, targetMatch);
		if (result3_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [solve csp] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[graph] = " + graph + ", " + "[rootPackage] = " + rootPackage + ", "
					+ "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = " + targetMatch + ".");
		}
		CSP csp = (CSP) result3_bindingAndBlack[0];
		// check CSP
		if (PackageAxiomImpl.pattern_PackageAxiom_24_4_checkCSP_expressionFB(csp)) {
			// ForEach match corr context
			for (Object[] result5_black : PackageAxiomImpl
					.pattern_PackageAxiom_24_5_matchcorrcontext_blackBB(sourceMatch, targetMatch)) {
				Object[] result5_green = PackageAxiomImpl
						.pattern_PackageAxiom_24_5_matchcorrcontext_greenBBF(sourceMatch, targetMatch);
				CCMatch ccMatch = (CCMatch) result5_green[2];

				// create correspondence
				Object[] result6_black = PackageAxiomImpl.pattern_PackageAxiom_24_6_createcorrespondence_blackBBB(graph,
						rootPackage, ccMatch);
				if (result6_black == null) {
					throw new RuntimeException("Pattern matching in node [create correspondence] failed."
							+ " Variables: " + "[graph] = " + graph + ", " + "[rootPackage] = " + rootPackage + ", "
							+ "[ccMatch] = " + ccMatch + ".");
				}
				PackageAxiomImpl.pattern_PackageAxiom_24_6_createcorrespondence_greenBFBB(graph, rootPackage, ccMatch);
				// ClassGraphToEPackage grapgToPackage = (ClassGraphToEPackage) result6_green[1];

				// add to returned result
				Object[] result7_black = PackageAxiomImpl.pattern_PackageAxiom_24_7_addtoreturnedresult_blackBB(result,
						ccMatch);
				if (result7_black == null) {
					throw new RuntimeException("Pattern matching in node [add to returned result] failed."
							+ " Variables: " + "[result] = " + result + ", " + "[ccMatch] = " + ccMatch + ".");
				}
				PackageAxiomImpl.pattern_PackageAxiom_24_7_addtoreturnedresult_greenBB(result, ccMatch);

			}

		} else {
		}
		return PackageAxiomImpl.pattern_PackageAxiom_24_8_expressionFB(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isApplicable_solveCsp_CC(ClassGraph graph, EPackage rootPackage, Match sourceMatch, Match targetMatch) {// Create CSP
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
	public boolean checkDEC_FWD(ClassGraph graph) {// match tgg pattern
		Object[] result1_black = PackageAxiomImpl.pattern_PackageAxiom_27_1_matchtggpattern_blackB(graph);
		if (result1_black != null) {
			return PackageAxiomImpl.pattern_PackageAxiom_27_2_expressionF();
		} else {
			return PackageAxiomImpl.pattern_PackageAxiom_27_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDEC_BWD(EPackage rootPackage) {// match tgg pattern
		Object[] result1_black = PackageAxiomImpl.pattern_PackageAxiom_28_1_matchtggpattern_blackB(rootPackage);
		if (result1_black != null) {
			return PackageAxiomImpl.pattern_PackageAxiom_28_2_expressionF();
		} else {
			return PackageAxiomImpl.pattern_PackageAxiom_28_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelgeneratorRuleResult generateModel(RuleEntryContainer ruleEntryContainer) {
		// create result
		Object[] result1_black = PackageAxiomImpl.pattern_PackageAxiom_29_1_createresult_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [create result] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = PackageAxiomImpl.pattern_PackageAxiom_29_1_createresult_greenFF();
		IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result1_green[0];
		ModelgeneratorRuleResult ruleResult = (ModelgeneratorRuleResult) result1_green[1];

		// is applicable core
		Object[] result2_black = PackageAxiomImpl.pattern_PackageAxiom_29_2_isapplicablecore_blackB(this);
		if (result2_black != null) {

			// solve CSP
			Object[] result3_bindingAndBlack = PackageAxiomImpl
					.pattern_PackageAxiom_29_3_solveCSP_bindingAndBlackFBBB(this, isApplicableMatch, ruleResult);
			if (result3_bindingAndBlack == null) {
				throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: " + "[this] = "
						+ this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", " + "[ruleResult] = "
						+ ruleResult + ".");
			}
			CSP csp = (CSP) result3_bindingAndBlack[0];
			// check CSP
			if (PackageAxiomImpl.pattern_PackageAxiom_29_4_checkCSP_expressionFBB(this, csp)) {
				// check nacs story node is empty

				// perform
				Object[] result6_black = PackageAxiomImpl.pattern_PackageAxiom_29_6_perform_blackB(ruleResult);
				if (result6_black == null) {
					throw new RuntimeException("Pattern matching in node [perform] failed." + " Variables: "
							+ "[ruleResult] = " + ruleResult + ".");
				}
				PackageAxiomImpl.pattern_PackageAxiom_29_6_perform_greenFFFB(ruleResult);
				// ClassGraph graph = (ClassGraph) result6_green[0];
				// ClassGraphToEPackage grapgToPackage = (ClassGraphToEPackage) result6_green[1];
				// EPackage rootPackage = (EPackage) result6_green[2];

			} else {
			}

		} else {
		}
		return PackageAxiomImpl.pattern_PackageAxiom_29_7_expressionFB(ruleResult);
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
		case RulesPackage.PACKAGE_AXIOM___IS_APPROPRIATE_FWD__MATCH_CLASSGRAPH:
			return isAppropriate_FWD((Match) arguments.get(0), (ClassGraph) arguments.get(1));
		case RulesPackage.PACKAGE_AXIOM___PERFORM_FWD__ISAPPLICABLEMATCH:
			return perform_FWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___IS_APPLICABLE_FWD__MATCH:
			return isApplicable_FWD((Match) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_CLASSGRAPH:
			registerObjectsToMatch_FWD((Match) arguments.get(0), (ClassGraph) arguments.get(1));
			return null;
		case RulesPackage.PACKAGE_AXIOM___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_CLASSGRAPH:
			return isAppropriate_solveCsp_FWD((Match) arguments.get(0), (ClassGraph) arguments.get(1));
		case RulesPackage.PACKAGE_AXIOM___IS_APPROPRIATE_CHECK_CSP_FWD__CSP:
			return isAppropriate_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_CLASSGRAPH:
			return isApplicable_solveCsp_FWD((IsApplicableMatch) arguments.get(0), (ClassGraph) arguments.get(1));
		case RulesPackage.PACKAGE_AXIOM___IS_APPLICABLE_CHECK_CSP_FWD__CSP:
			return isApplicable_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_FWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3));
			return null;
		case RulesPackage.PACKAGE_AXIOM___CHECK_TYPES_FWD__MATCH:
			return checkTypes_FWD((Match) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___IS_APPROPRIATE_BWD__MATCH_EPACKAGE:
			return isAppropriate_BWD((Match) arguments.get(0), (EPackage) arguments.get(1));
		case RulesPackage.PACKAGE_AXIOM___PERFORM_BWD__ISAPPLICABLEMATCH:
			return perform_BWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___IS_APPLICABLE_BWD__MATCH:
			return isApplicable_BWD((Match) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_EPACKAGE:
			registerObjectsToMatch_BWD((Match) arguments.get(0), (EPackage) arguments.get(1));
			return null;
		case RulesPackage.PACKAGE_AXIOM___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_EPACKAGE:
			return isAppropriate_solveCsp_BWD((Match) arguments.get(0), (EPackage) arguments.get(1));
		case RulesPackage.PACKAGE_AXIOM___IS_APPROPRIATE_CHECK_CSP_BWD__CSP:
			return isAppropriate_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_EPACKAGE:
			return isApplicable_solveCsp_BWD((IsApplicableMatch) arguments.get(0), (EPackage) arguments.get(1));
		case RulesPackage.PACKAGE_AXIOM___IS_APPLICABLE_CHECK_CSP_BWD__CSP:
			return isApplicable_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_BWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3));
			return null;
		case RulesPackage.PACKAGE_AXIOM___CHECK_TYPES_BWD__MATCH:
			return checkTypes_BWD((Match) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___IS_APPROPRIATE_BWD_EPACKAGE_1__EPACKAGE:
			return isAppropriate_BWD_EPackage_1((EPackage) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___IS_APPROPRIATE_FWD_CLASS_GRAPH_1__CLASSGRAPH:
			return isAppropriate_FWD_ClassGraph_1((ClassGraph) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH:
			return checkAttributes_FWD((TripleMatch) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH:
			return checkAttributes_BWD((TripleMatch) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___IS_APPLICABLE_CC__MATCH_MATCH:
			return isApplicable_CC((Match) arguments.get(0), (Match) arguments.get(1));
		case RulesPackage.PACKAGE_AXIOM___IS_APPLICABLE_SOLVE_CSP_CC__CLASSGRAPH_EPACKAGE_MATCH_MATCH:
			return isApplicable_solveCsp_CC((ClassGraph) arguments.get(0), (EPackage) arguments.get(1),
					(Match) arguments.get(2), (Match) arguments.get(3));
		case RulesPackage.PACKAGE_AXIOM___IS_APPLICABLE_CHECK_CSP_CC__CSP:
			return isApplicable_checkCsp_CC((CSP) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___CHECK_DEC_FWD__CLASSGRAPH:
			return checkDEC_FWD((ClassGraph) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___CHECK_DEC_BWD__EPACKAGE:
			return checkDEC_BWD((EPackage) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___GENERATE_MODEL__RULEENTRYCONTAINER:
			return generateModel((RuleEntryContainer) arguments.get(0));
		case RulesPackage.PACKAGE_AXIOM___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_MODELGENERATORRULERESULT:
			return generateModel_solveCsp_BWD((IsApplicableMatch) arguments.get(0),
					(ModelgeneratorRuleResult) arguments.get(1));
		case RulesPackage.PACKAGE_AXIOM___GENERATE_MODEL_CHECK_CSP_BWD__CSP:
			return generateModel_checkCsp_BWD((CSP) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	public static final Object[] pattern_PackageAxiom_0_1_initialbindings_blackBBB(PackageAxiom _this, Match match,
			ClassGraph graph) {
		return new Object[] { _this, match, graph };
	}

	public static final Object[] pattern_PackageAxiom_0_2_SolveCSP_bindingFBBB(PackageAxiom _this, Match match,
			ClassGraph graph) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_FWD(match, graph);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, graph };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_0_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_PackageAxiom_0_2_SolveCSP_bindingAndBlackFBBB(PackageAxiom _this, Match match,
			ClassGraph graph) {
		Object[] result_pattern_PackageAxiom_0_2_SolveCSP_binding = pattern_PackageAxiom_0_2_SolveCSP_bindingFBBB(_this,
				match, graph);
		if (result_pattern_PackageAxiom_0_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_PackageAxiom_0_2_SolveCSP_binding[0];

			Object[] result_pattern_PackageAxiom_0_2_SolveCSP_black = pattern_PackageAxiom_0_2_SolveCSP_blackB(csp);
			if (result_pattern_PackageAxiom_0_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, graph };
			}
		}
		return null;
	}

	public static final boolean pattern_PackageAxiom_0_3_CheckCSP_expressionFBB(PackageAxiom _this, CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_0_4_collectelementstobetranslated_blackBB(Match match,
			ClassGraph graph) {
		return new Object[] { match, graph };
	}

	public static final Object[] pattern_PackageAxiom_0_4_collectelementstobetranslated_greenBB(Match match,
			ClassGraph graph) {
		match.getToBeTranslatedNodes().add(graph);
		return new Object[] { match, graph };
	}

	public static final Object[] pattern_PackageAxiom_0_5_collectcontextelements_blackBB(Match match,
			ClassGraph graph) {
		return new Object[] { match, graph };
	}

	public static final void pattern_PackageAxiom_0_6_registerobjectstomatch_expressionBBB(PackageAxiom _this,
			Match match, ClassGraph graph) {
		_this.registerObjectsToMatch_FWD(match, graph);

	}

	public static final boolean pattern_PackageAxiom_0_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_PackageAxiom_0_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_1_1_performtransformation_bindingFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("graph");
		EObject tmpGraph = _localVariable_0;
		if (tmpGraph instanceof ClassGraph) {
			ClassGraph graph = (ClassGraph) tmpGraph;
			return new Object[] { graph, isApplicableMatch };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_1_1_performtransformation_blackBFBB(ClassGraph graph,
			PackageAxiom _this, IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { graph, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_1_1_performtransformation_bindingAndBlackFFBB(PackageAxiom _this,
			IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_PackageAxiom_1_1_performtransformation_binding = pattern_PackageAxiom_1_1_performtransformation_bindingFB(
				isApplicableMatch);
		if (result_pattern_PackageAxiom_1_1_performtransformation_binding != null) {
			ClassGraph graph = (ClassGraph) result_pattern_PackageAxiom_1_1_performtransformation_binding[0];

			Object[] result_pattern_PackageAxiom_1_1_performtransformation_black = pattern_PackageAxiom_1_1_performtransformation_blackBFBB(
					graph, _this, isApplicableMatch);
			if (result_pattern_PackageAxiom_1_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_PackageAxiom_1_1_performtransformation_black[1];

				return new Object[] { graph, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_1_1_performtransformation_greenBFF(ClassGraph graph) {
		ClassGraphToEPackage grapgToPackage = EpackagevizFactory.eINSTANCE.createClassGraphToEPackage();
		EPackage rootPackage = EcoreFactory.eINSTANCE.createEPackage();
		grapgToPackage.setSource(graph);
		grapgToPackage.setTarget(rootPackage);
		return new Object[] { graph, grapgToPackage, rootPackage };
	}

	public static final Object[] pattern_PackageAxiom_1_2_collecttranslatedelements_blackBBB(ClassGraph graph,
			ClassGraphToEPackage grapgToPackage, EPackage rootPackage) {
		return new Object[] { graph, grapgToPackage, rootPackage };
	}

	public static final Object[] pattern_PackageAxiom_1_2_collecttranslatedelements_greenFBBB(ClassGraph graph,
			ClassGraphToEPackage grapgToPackage, EPackage rootPackage) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getTranslatedElements().add(graph);
		ruleresult.getCreatedLinkElements().add(grapgToPackage);
		ruleresult.getCreatedElements().add(rootPackage);
		return new Object[] { ruleresult, graph, grapgToPackage, rootPackage };
	}

	public static final Object[] pattern_PackageAxiom_1_3_bookkeepingforedges_blackBBBB(PerformRuleResult ruleresult,
			EObject graph, EObject grapgToPackage, EObject rootPackage) {
		if (!graph.equals(rootPackage)) {
			if (!grapgToPackage.equals(graph)) {
				if (!grapgToPackage.equals(rootPackage)) {
					return new Object[] { ruleresult, graph, grapgToPackage, rootPackage };
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_1_3_bookkeepingforedges_greenBBBBFF(PerformRuleResult ruleresult,
			EObject graph, EObject grapgToPackage, EObject rootPackage) {
		EMoflonEdge grapgToPackage__graph____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge grapgToPackage__rootPackage____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "PackageAxiom";
		String grapgToPackage__graph____source_name_prime = "source";
		String grapgToPackage__rootPackage____target_name_prime = "target";
		grapgToPackage__graph____source.setSrc(grapgToPackage);
		grapgToPackage__graph____source.setTrg(graph);
		ruleresult.getCreatedEdges().add(grapgToPackage__graph____source);
		grapgToPackage__rootPackage____target.setSrc(grapgToPackage);
		grapgToPackage__rootPackage____target.setTrg(rootPackage);
		ruleresult.getCreatedEdges().add(grapgToPackage__rootPackage____target);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		grapgToPackage__graph____source.setName(grapgToPackage__graph____source_name_prime);
		grapgToPackage__rootPackage____target.setName(grapgToPackage__rootPackage____target_name_prime);
		return new Object[] { ruleresult, graph, grapgToPackage, rootPackage, grapgToPackage__graph____source,
				grapgToPackage__rootPackage____target };
	}

	public static final void pattern_PackageAxiom_1_5_registerobjects_expressionBBBBB(PackageAxiom _this,
			PerformRuleResult ruleresult, EObject graph, EObject grapgToPackage, EObject rootPackage) {
		_this.registerObjects_FWD(ruleresult, graph, grapgToPackage, rootPackage);

	}

	public static final PerformRuleResult pattern_PackageAxiom_1_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_2_1_preparereturnvalue_bindingFB(PackageAxiom _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_2_1_preparereturnvalue_blackFBB(EClass eClass,
			PackageAxiom _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_FWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_2_1_preparereturnvalue_bindingAndBlackFFB(PackageAxiom _this) {
		Object[] result_pattern_PackageAxiom_2_1_preparereturnvalue_binding = pattern_PackageAxiom_2_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_PackageAxiom_2_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_PackageAxiom_2_1_preparereturnvalue_binding[0];

			Object[] result_pattern_PackageAxiom_2_1_preparereturnvalue_black = pattern_PackageAxiom_2_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_PackageAxiom_2_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_PackageAxiom_2_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_2_1_preparereturnvalue_greenBF(EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "PackageAxiom";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_PackageAxiom_2_2_corematch_bindingFB(Match match) {
		EObject _localVariable_0 = match.getObject("graph");
		EObject tmpGraph = _localVariable_0;
		if (tmpGraph instanceof ClassGraph) {
			ClassGraph graph = (ClassGraph) tmpGraph;
			return new Object[] { graph, match };
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_PackageAxiom_2_2_corematch_blackBB(ClassGraph graph, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		_result.add(new Object[] { graph, match });
		return _result;
	}

	public static final Iterable<Object[]> pattern_PackageAxiom_2_3_findcontext_blackB(ClassGraph graph) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		_result.add(new Object[] { graph });
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_2_3_findcontext_greenBF(ClassGraph graph) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		isApplicableMatch.getAllContextElements().add(graph);
		return new Object[] { graph, isApplicableMatch };
	}

	public static final Object[] pattern_PackageAxiom_2_4_solveCSP_bindingFBBB(PackageAxiom _this,
			IsApplicableMatch isApplicableMatch, ClassGraph graph) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_FWD(isApplicableMatch, graph);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, graph };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_2_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_PackageAxiom_2_4_solveCSP_bindingAndBlackFBBB(PackageAxiom _this,
			IsApplicableMatch isApplicableMatch, ClassGraph graph) {
		Object[] result_pattern_PackageAxiom_2_4_solveCSP_binding = pattern_PackageAxiom_2_4_solveCSP_bindingFBBB(_this,
				isApplicableMatch, graph);
		if (result_pattern_PackageAxiom_2_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_PackageAxiom_2_4_solveCSP_binding[0];

			Object[] result_pattern_PackageAxiom_2_4_solveCSP_black = pattern_PackageAxiom_2_4_solveCSP_blackB(csp);
			if (result_pattern_PackageAxiom_2_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, graph };
			}
		}
		return null;
	}

	public static final boolean pattern_PackageAxiom_2_5_checkCSP_expressionFBB(PackageAxiom _this, CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_2_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_PackageAxiom_2_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "PackageAxiom";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_PackageAxiom_2_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_10_1_initialbindings_blackBBB(PackageAxiom _this, Match match,
			EPackage rootPackage) {
		return new Object[] { _this, match, rootPackage };
	}

	public static final Object[] pattern_PackageAxiom_10_2_SolveCSP_bindingFBBB(PackageAxiom _this, Match match,
			EPackage rootPackage) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_BWD(match, rootPackage);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, rootPackage };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_10_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_PackageAxiom_10_2_SolveCSP_bindingAndBlackFBBB(PackageAxiom _this, Match match,
			EPackage rootPackage) {
		Object[] result_pattern_PackageAxiom_10_2_SolveCSP_binding = pattern_PackageAxiom_10_2_SolveCSP_bindingFBBB(
				_this, match, rootPackage);
		if (result_pattern_PackageAxiom_10_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_PackageAxiom_10_2_SolveCSP_binding[0];

			Object[] result_pattern_PackageAxiom_10_2_SolveCSP_black = pattern_PackageAxiom_10_2_SolveCSP_blackB(csp);
			if (result_pattern_PackageAxiom_10_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, rootPackage };
			}
		}
		return null;
	}

	public static final boolean pattern_PackageAxiom_10_3_CheckCSP_expressionFBB(PackageAxiom _this, CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_10_4_collectelementstobetranslated_blackBB(Match match,
			EPackage rootPackage) {
		return new Object[] { match, rootPackage };
	}

	public static final Object[] pattern_PackageAxiom_10_4_collectelementstobetranslated_greenBB(Match match,
			EPackage rootPackage) {
		match.getToBeTranslatedNodes().add(rootPackage);
		return new Object[] { match, rootPackage };
	}

	public static final Object[] pattern_PackageAxiom_10_5_collectcontextelements_blackBB(Match match,
			EPackage rootPackage) {
		return new Object[] { match, rootPackage };
	}

	public static final void pattern_PackageAxiom_10_6_registerobjectstomatch_expressionBBB(PackageAxiom _this,
			Match match, EPackage rootPackage) {
		_this.registerObjectsToMatch_BWD(match, rootPackage);

	}

	public static final boolean pattern_PackageAxiom_10_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_PackageAxiom_10_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_11_1_performtransformation_bindingFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("rootPackage");
		EObject tmpRootPackage = _localVariable_0;
		if (tmpRootPackage instanceof EPackage) {
			EPackage rootPackage = (EPackage) tmpRootPackage;
			return new Object[] { rootPackage, isApplicableMatch };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_11_1_performtransformation_blackBFBB(EPackage rootPackage,
			PackageAxiom _this, IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { rootPackage, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_11_1_performtransformation_bindingAndBlackFFBB(PackageAxiom _this,
			IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_PackageAxiom_11_1_performtransformation_binding = pattern_PackageAxiom_11_1_performtransformation_bindingFB(
				isApplicableMatch);
		if (result_pattern_PackageAxiom_11_1_performtransformation_binding != null) {
			EPackage rootPackage = (EPackage) result_pattern_PackageAxiom_11_1_performtransformation_binding[0];

			Object[] result_pattern_PackageAxiom_11_1_performtransformation_black = pattern_PackageAxiom_11_1_performtransformation_blackBFBB(
					rootPackage, _this, isApplicableMatch);
			if (result_pattern_PackageAxiom_11_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_PackageAxiom_11_1_performtransformation_black[1];

				return new Object[] { rootPackage, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_11_1_performtransformation_greenFFB(EPackage rootPackage) {
		ClassGraph graph = LanguageFactory.eINSTANCE.createClassGraph();
		ClassGraphToEPackage grapgToPackage = EpackagevizFactory.eINSTANCE.createClassGraphToEPackage();
		grapgToPackage.setSource(graph);
		grapgToPackage.setTarget(rootPackage);
		return new Object[] { graph, grapgToPackage, rootPackage };
	}

	public static final Object[] pattern_PackageAxiom_11_2_collecttranslatedelements_blackBBB(ClassGraph graph,
			ClassGraphToEPackage grapgToPackage, EPackage rootPackage) {
		return new Object[] { graph, grapgToPackage, rootPackage };
	}

	public static final Object[] pattern_PackageAxiom_11_2_collecttranslatedelements_greenFBBB(ClassGraph graph,
			ClassGraphToEPackage grapgToPackage, EPackage rootPackage) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getCreatedElements().add(graph);
		ruleresult.getCreatedLinkElements().add(grapgToPackage);
		ruleresult.getTranslatedElements().add(rootPackage);
		return new Object[] { ruleresult, graph, grapgToPackage, rootPackage };
	}

	public static final Object[] pattern_PackageAxiom_11_3_bookkeepingforedges_blackBBBB(PerformRuleResult ruleresult,
			EObject graph, EObject grapgToPackage, EObject rootPackage) {
		if (!graph.equals(rootPackage)) {
			if (!grapgToPackage.equals(graph)) {
				if (!grapgToPackage.equals(rootPackage)) {
					return new Object[] { ruleresult, graph, grapgToPackage, rootPackage };
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_11_3_bookkeepingforedges_greenBBBBFF(PerformRuleResult ruleresult,
			EObject graph, EObject grapgToPackage, EObject rootPackage) {
		EMoflonEdge grapgToPackage__graph____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge grapgToPackage__rootPackage____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "PackageAxiom";
		String grapgToPackage__graph____source_name_prime = "source";
		String grapgToPackage__rootPackage____target_name_prime = "target";
		grapgToPackage__graph____source.setSrc(grapgToPackage);
		grapgToPackage__graph____source.setTrg(graph);
		ruleresult.getCreatedEdges().add(grapgToPackage__graph____source);
		grapgToPackage__rootPackage____target.setSrc(grapgToPackage);
		grapgToPackage__rootPackage____target.setTrg(rootPackage);
		ruleresult.getCreatedEdges().add(grapgToPackage__rootPackage____target);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		grapgToPackage__graph____source.setName(grapgToPackage__graph____source_name_prime);
		grapgToPackage__rootPackage____target.setName(grapgToPackage__rootPackage____target_name_prime);
		return new Object[] { ruleresult, graph, grapgToPackage, rootPackage, grapgToPackage__graph____source,
				grapgToPackage__rootPackage____target };
	}

	public static final void pattern_PackageAxiom_11_5_registerobjects_expressionBBBBB(PackageAxiom _this,
			PerformRuleResult ruleresult, EObject graph, EObject grapgToPackage, EObject rootPackage) {
		_this.registerObjects_BWD(ruleresult, graph, grapgToPackage, rootPackage);

	}

	public static final PerformRuleResult pattern_PackageAxiom_11_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_12_1_preparereturnvalue_bindingFB(PackageAxiom _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_12_1_preparereturnvalue_blackFBB(EClass eClass,
			PackageAxiom _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_BWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_12_1_preparereturnvalue_bindingAndBlackFFB(PackageAxiom _this) {
		Object[] result_pattern_PackageAxiom_12_1_preparereturnvalue_binding = pattern_PackageAxiom_12_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_PackageAxiom_12_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_PackageAxiom_12_1_preparereturnvalue_binding[0];

			Object[] result_pattern_PackageAxiom_12_1_preparereturnvalue_black = pattern_PackageAxiom_12_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_PackageAxiom_12_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_PackageAxiom_12_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_12_1_preparereturnvalue_greenBF(EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "PackageAxiom";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_PackageAxiom_12_2_corematch_bindingFB(Match match) {
		EObject _localVariable_0 = match.getObject("rootPackage");
		EObject tmpRootPackage = _localVariable_0;
		if (tmpRootPackage instanceof EPackage) {
			EPackage rootPackage = (EPackage) tmpRootPackage;
			return new Object[] { rootPackage, match };
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_PackageAxiom_12_2_corematch_blackBB(EPackage rootPackage,
			Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		_result.add(new Object[] { rootPackage, match });
		return _result;
	}

	public static final Iterable<Object[]> pattern_PackageAxiom_12_3_findcontext_blackB(EPackage rootPackage) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		_result.add(new Object[] { rootPackage });
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_12_3_findcontext_greenBF(EPackage rootPackage) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		isApplicableMatch.getAllContextElements().add(rootPackage);
		return new Object[] { rootPackage, isApplicableMatch };
	}

	public static final Object[] pattern_PackageAxiom_12_4_solveCSP_bindingFBBB(PackageAxiom _this,
			IsApplicableMatch isApplicableMatch, EPackage rootPackage) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_BWD(isApplicableMatch, rootPackage);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, rootPackage };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_12_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_PackageAxiom_12_4_solveCSP_bindingAndBlackFBBB(PackageAxiom _this,
			IsApplicableMatch isApplicableMatch, EPackage rootPackage) {
		Object[] result_pattern_PackageAxiom_12_4_solveCSP_binding = pattern_PackageAxiom_12_4_solveCSP_bindingFBBB(
				_this, isApplicableMatch, rootPackage);
		if (result_pattern_PackageAxiom_12_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_PackageAxiom_12_4_solveCSP_binding[0];

			Object[] result_pattern_PackageAxiom_12_4_solveCSP_black = pattern_PackageAxiom_12_4_solveCSP_blackB(csp);
			if (result_pattern_PackageAxiom_12_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, rootPackage };
			}
		}
		return null;
	}

	public static final boolean pattern_PackageAxiom_12_5_checkCSP_expressionFBB(PackageAxiom _this, CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_12_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_PackageAxiom_12_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "PackageAxiom";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_PackageAxiom_12_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_20_1_preparereturnvalue_bindingFB(PackageAxiom _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_20_1_preparereturnvalue_blackFBBF(EClass __eClass,
			PackageAxiom _this) {
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

	public static final Object[] pattern_PackageAxiom_20_1_preparereturnvalue_bindingAndBlackFFBF(PackageAxiom _this) {
		Object[] result_pattern_PackageAxiom_20_1_preparereturnvalue_binding = pattern_PackageAxiom_20_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_PackageAxiom_20_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_PackageAxiom_20_1_preparereturnvalue_binding[0];

			Object[] result_pattern_PackageAxiom_20_1_preparereturnvalue_black = pattern_PackageAxiom_20_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_PackageAxiom_20_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_PackageAxiom_20_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_PackageAxiom_20_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_20_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_PackageAxiom_20_2_testcorematchandDECs_blackB(EPackage rootPackage) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		_result.add(new Object[] { rootPackage });
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_20_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_PackageAxiom_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBB(
			PackageAxiom _this, Match match, EPackage rootPackage) {
		boolean _localVariable_0 = _this.isAppropriate_BWD(match, rootPackage);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_PackageAxiom_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			PackageAxiom _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_BWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_20_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_20_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_PackageAxiom_20_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_21_1_preparereturnvalue_bindingFB(PackageAxiom _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_21_1_preparereturnvalue_blackFBBF(EClass __eClass,
			PackageAxiom _this) {
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

	public static final Object[] pattern_PackageAxiom_21_1_preparereturnvalue_bindingAndBlackFFBF(PackageAxiom _this) {
		Object[] result_pattern_PackageAxiom_21_1_preparereturnvalue_binding = pattern_PackageAxiom_21_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_PackageAxiom_21_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_PackageAxiom_21_1_preparereturnvalue_binding[0];

			Object[] result_pattern_PackageAxiom_21_1_preparereturnvalue_black = pattern_PackageAxiom_21_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_PackageAxiom_21_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_PackageAxiom_21_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_PackageAxiom_21_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_21_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_PackageAxiom_21_2_testcorematchandDECs_blackB(ClassGraph graph) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		_result.add(new Object[] { graph });
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_21_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_PackageAxiom_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBB(
			PackageAxiom _this, Match match, ClassGraph graph) {
		boolean _localVariable_0 = _this.isAppropriate_FWD(match, graph);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_PackageAxiom_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			PackageAxiom _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_FWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_21_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_21_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_PackageAxiom_21_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_24_1_prepare_blackB(PackageAxiom _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_PackageAxiom_24_1_prepare_greenF() {
		IsApplicableRuleResult result = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		return new Object[] { result };
	}

	public static final Object[] pattern_PackageAxiom_24_2_matchsrctrgcontext_bindingFFBB(Match sourceMatch,
			Match targetMatch) {
		EObject _localVariable_0 = sourceMatch.getObject("graph");
		EObject _localVariable_1 = targetMatch.getObject("rootPackage");
		EObject tmpGraph = _localVariable_0;
		EObject tmpRootPackage = _localVariable_1;
		if (tmpGraph instanceof ClassGraph) {
			ClassGraph graph = (ClassGraph) tmpGraph;
			if (tmpRootPackage instanceof EPackage) {
				EPackage rootPackage = (EPackage) tmpRootPackage;
				return new Object[] { graph, rootPackage, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_24_2_matchsrctrgcontext_blackBBBB(ClassGraph graph,
			EPackage rootPackage, Match sourceMatch, Match targetMatch) {
		if (!sourceMatch.equals(targetMatch)) {
			return new Object[] { graph, rootPackage, sourceMatch, targetMatch };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_24_2_matchsrctrgcontext_bindingAndBlackFFBB(Match sourceMatch,
			Match targetMatch) {
		Object[] result_pattern_PackageAxiom_24_2_matchsrctrgcontext_binding = pattern_PackageAxiom_24_2_matchsrctrgcontext_bindingFFBB(
				sourceMatch, targetMatch);
		if (result_pattern_PackageAxiom_24_2_matchsrctrgcontext_binding != null) {
			ClassGraph graph = (ClassGraph) result_pattern_PackageAxiom_24_2_matchsrctrgcontext_binding[0];
			EPackage rootPackage = (EPackage) result_pattern_PackageAxiom_24_2_matchsrctrgcontext_binding[1];

			Object[] result_pattern_PackageAxiom_24_2_matchsrctrgcontext_black = pattern_PackageAxiom_24_2_matchsrctrgcontext_blackBBBB(
					graph, rootPackage, sourceMatch, targetMatch);
			if (result_pattern_PackageAxiom_24_2_matchsrctrgcontext_black != null) {

				return new Object[] { graph, rootPackage, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_24_3_solvecsp_bindingFBBBBB(PackageAxiom _this, ClassGraph graph,
			EPackage rootPackage, Match sourceMatch, Match targetMatch) {
		CSP _localVariable_2 = _this.isApplicable_solveCsp_CC(graph, rootPackage, sourceMatch, targetMatch);
		CSP csp = _localVariable_2;
		if (csp != null) {
			return new Object[] { csp, _this, graph, rootPackage, sourceMatch, targetMatch };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_24_3_solvecsp_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_PackageAxiom_24_3_solvecsp_bindingAndBlackFBBBBB(PackageAxiom _this,
			ClassGraph graph, EPackage rootPackage, Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_PackageAxiom_24_3_solvecsp_binding = pattern_PackageAxiom_24_3_solvecsp_bindingFBBBBB(
				_this, graph, rootPackage, sourceMatch, targetMatch);
		if (result_pattern_PackageAxiom_24_3_solvecsp_binding != null) {
			CSP csp = (CSP) result_pattern_PackageAxiom_24_3_solvecsp_binding[0];

			Object[] result_pattern_PackageAxiom_24_3_solvecsp_black = pattern_PackageAxiom_24_3_solvecsp_blackB(csp);
			if (result_pattern_PackageAxiom_24_3_solvecsp_black != null) {

				return new Object[] { csp, _this, graph, rootPackage, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final boolean pattern_PackageAxiom_24_4_checkCSP_expressionFB(CSP csp) {
		boolean _localVariable_0 = csp.check();
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Iterable<Object[]> pattern_PackageAxiom_24_5_matchcorrcontext_blackBB(Match sourceMatch,
			Match targetMatch) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!sourceMatch.equals(targetMatch)) {
			_result.add(new Object[] { sourceMatch, targetMatch });
		}
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_24_5_matchcorrcontext_greenBBF(Match sourceMatch,
			Match targetMatch) {
		CCMatch ccMatch = RuntimeFactory.eINSTANCE.createCCMatch();
		String ccMatch_ruleName_prime = "PackageAxiom";
		ccMatch.setSourceMatch(sourceMatch);
		ccMatch.setTargetMatch(targetMatch);
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { sourceMatch, targetMatch, ccMatch };
	}

	public static final Object[] pattern_PackageAxiom_24_6_createcorrespondence_blackBBB(ClassGraph graph,
			EPackage rootPackage, CCMatch ccMatch) {
		return new Object[] { graph, rootPackage, ccMatch };
	}

	public static final Object[] pattern_PackageAxiom_24_6_createcorrespondence_greenBFBB(ClassGraph graph,
			EPackage rootPackage, CCMatch ccMatch) {
		ClassGraphToEPackage grapgToPackage = EpackagevizFactory.eINSTANCE.createClassGraphToEPackage();
		grapgToPackage.setSource(graph);
		grapgToPackage.setTarget(rootPackage);
		ccMatch.getCreateCorr().add(grapgToPackage);
		return new Object[] { graph, grapgToPackage, rootPackage, ccMatch };
	}

	public static final Object[] pattern_PackageAxiom_24_7_addtoreturnedresult_blackBB(IsApplicableRuleResult result,
			CCMatch ccMatch) {
		return new Object[] { result, ccMatch };
	}

	public static final Object[] pattern_PackageAxiom_24_7_addtoreturnedresult_greenBB(IsApplicableRuleResult result,
			CCMatch ccMatch) {
		result.getIsApplicableMatch().add(ccMatch);
		boolean result_success_prime = Boolean.valueOf(true);
		String ccMatch_ruleName_prime = "PackageAxiom";
		result.setSuccess(Boolean.valueOf(result_success_prime));
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { result, ccMatch };
	}

	public static final IsApplicableRuleResult pattern_PackageAxiom_24_8_expressionFB(IsApplicableRuleResult result) {
		IsApplicableRuleResult _result = result;
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_27_1_matchtggpattern_blackB(ClassGraph graph) {
		return new Object[] { graph };
	}

	public static final boolean pattern_PackageAxiom_27_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_PackageAxiom_27_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_28_1_matchtggpattern_blackB(EPackage rootPackage) {
		return new Object[] { rootPackage };
	}

	public static final boolean pattern_PackageAxiom_28_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_PackageAxiom_28_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_29_1_createresult_blackB(PackageAxiom _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_PackageAxiom_29_1_createresult_greenFF() {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		ModelgeneratorRuleResult ruleResult = RuntimeFactory.eINSTANCE.createModelgeneratorRuleResult();
		boolean ruleResult_success_prime = false;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		return new Object[] { isApplicableMatch, ruleResult };
	}

	public static final Object[] pattern_PackageAxiom_29_2_isapplicablecore_blackB(PackageAxiom _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_PackageAxiom_29_3_solveCSP_bindingFBBB(PackageAxiom _this,
			IsApplicableMatch isApplicableMatch, ModelgeneratorRuleResult ruleResult) {
		CSP _localVariable_0 = _this.generateModel_solveCsp_BWD(isApplicableMatch, ruleResult);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, ruleResult };
		}
		return null;
	}

	public static final Object[] pattern_PackageAxiom_29_3_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_PackageAxiom_29_3_solveCSP_bindingAndBlackFBBB(PackageAxiom _this,
			IsApplicableMatch isApplicableMatch, ModelgeneratorRuleResult ruleResult) {
		Object[] result_pattern_PackageAxiom_29_3_solveCSP_binding = pattern_PackageAxiom_29_3_solveCSP_bindingFBBB(
				_this, isApplicableMatch, ruleResult);
		if (result_pattern_PackageAxiom_29_3_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_PackageAxiom_29_3_solveCSP_binding[0];

			Object[] result_pattern_PackageAxiom_29_3_solveCSP_black = pattern_PackageAxiom_29_3_solveCSP_blackB(csp);
			if (result_pattern_PackageAxiom_29_3_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, ruleResult };
			}
		}
		return null;
	}

	public static final boolean pattern_PackageAxiom_29_4_checkCSP_expressionFBB(PackageAxiom _this, CSP csp) {
		boolean _localVariable_0 = _this.generateModel_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_PackageAxiom_29_6_perform_blackB(ModelgeneratorRuleResult ruleResult) {
		return new Object[] { ruleResult };
	}

	public static final Object[] pattern_PackageAxiom_29_6_perform_greenFFFB(ModelgeneratorRuleResult ruleResult) {
		ClassGraph graph = LanguageFactory.eINSTANCE.createClassGraph();
		ClassGraphToEPackage grapgToPackage = EpackagevizFactory.eINSTANCE.createClassGraphToEPackage();
		EPackage rootPackage = EcoreFactory.eINSTANCE.createEPackage();
		boolean ruleResult_success_prime = Boolean.valueOf(true);
		int _localVariable_0 = ruleResult.getIncrementedPerformCount();
		ruleResult.getSourceObjects().add(graph);
		grapgToPackage.setSource(graph);
		ruleResult.getCorrObjects().add(grapgToPackage);
		grapgToPackage.setTarget(rootPackage);
		ruleResult.getTargetObjects().add(rootPackage);
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		int ruleResult_performCount_prime = Integer.valueOf(_localVariable_0);
		ruleResult.setPerformCount(Integer.valueOf(ruleResult_performCount_prime));
		return new Object[] { graph, grapgToPackage, rootPackage, ruleResult };
	}

	public static final ModelgeneratorRuleResult pattern_PackageAxiom_29_7_expressionFB(
			ModelgeneratorRuleResult ruleResult) {
		ModelgeneratorRuleResult _result = ruleResult;
		return _result;
	}

	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //PackageAxiomImpl
