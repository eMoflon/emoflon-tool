/**
 */
package org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.impl;

import java.lang.Iterable;

import java.lang.reflect.InvocationTargetException;

import java.util.LinkedList;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;

import org.moflon.ide.visualization.dot.ecore.epackageviz.ClassGraphToEPackage;
import org.moflon.ide.visualization.dot.ecore.epackageviz.EpackagevizFactory;
import org.moflon.ide.visualization.dot.ecore.epackageviz.PNodeToEClassifier;

import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.EnumNodeRule;
import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.RulesPackage;

import org.moflon.ide.visualization.dot.language.ClassGraph;
import org.moflon.ide.visualization.dot.language.LanguageFactory;
import org.moflon.ide.visualization.dot.language.PEnum;

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
 * An implementation of the model object '<em><b>Enum Node Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class EnumNodeRuleImpl extends AbstractRuleImpl implements EnumNodeRule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnumNodeRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulesPackage.eINSTANCE.getEnumNodeRule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_FWD(Match match, PEnum pEnum, ClassGraph graph) {
		// initial bindings
		Object[] result1_black = EnumNodeRuleImpl.pattern_EnumNodeRule_0_1_initialbindings_blackBBBB(this, match, pEnum,
				graph);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [initial bindings] failed." + " Variables: " + "[this] = " + this + ", "
							+ "[match] = " + match + ", " + "[pEnum] = " + pEnum + ", " + "[graph] = " + graph + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = EnumNodeRuleImpl.pattern_EnumNodeRule_0_2_SolveCSP_bindingAndBlackFBBBB(this,
				match, pEnum, graph);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException(
					"Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = " + this + ", "
							+ "[match] = " + match + ", " + "[pEnum] = " + pEnum + ", " + "[graph] = " + graph + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (EnumNodeRuleImpl.pattern_EnumNodeRule_0_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = EnumNodeRuleImpl
					.pattern_EnumNodeRule_0_4_collectelementstobetranslated_blackBBB(match, pEnum, graph);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[pEnum] = " + pEnum + ", " + "[graph] = "
						+ graph + ".");
			}
			EnumNodeRuleImpl.pattern_EnumNodeRule_0_4_collectelementstobetranslated_greenBBBFF(match, pEnum, graph);
			// EMoflonEdge pEnum__graph____graph = (EMoflonEdge) result4_green[3];
			// EMoflonEdge graph__pEnum____nodes = (EMoflonEdge) result4_green[4];

			// collect context elements
			Object[] result5_black = EnumNodeRuleImpl.pattern_EnumNodeRule_0_5_collectcontextelements_blackBBB(match,
					pEnum, graph);
			if (result5_black == null) {
				throw new RuntimeException(
						"Pattern matching in node [collect context elements] failed." + " Variables: " + "[match] = "
								+ match + ", " + "[pEnum] = " + pEnum + ", " + "[graph] = " + graph + ".");
			}
			EnumNodeRuleImpl.pattern_EnumNodeRule_0_5_collectcontextelements_greenBB(match, graph);

			// register objects to match
			EnumNodeRuleImpl.pattern_EnumNodeRule_0_6_registerobjectstomatch_expressionBBBB(this, match, pEnum, graph);
			return EnumNodeRuleImpl.pattern_EnumNodeRule_0_7_expressionF();
		} else {
			return EnumNodeRuleImpl.pattern_EnumNodeRule_0_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_FWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = EnumNodeRuleImpl
				.pattern_EnumNodeRule_1_1_performtransformation_bindingAndBlackFFFFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		PEnum pEnum = (PEnum) result1_bindingAndBlack[0];
		ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result1_bindingAndBlack[1];
		ClassGraph graph = (ClassGraph) result1_bindingAndBlack[2];
		EPackage ePackage = (EPackage) result1_bindingAndBlack[3];
		CSP csp = (CSP) result1_bindingAndBlack[4];
		Object[] result1_green = EnumNodeRuleImpl.pattern_EnumNodeRule_1_1_performtransformation_greenBFFBB(pEnum,
				ePackage, csp);
		PNodeToEClassifier pEnumToEEnum = (PNodeToEClassifier) result1_green[1];
		EEnum eEnum = (EEnum) result1_green[2];

		// collect translated elements
		Object[] result2_black = EnumNodeRuleImpl.pattern_EnumNodeRule_1_2_collecttranslatedelements_blackBBB(pEnum,
				pEnumToEEnum, eEnum);
		if (result2_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [collect translated elements] failed." + " Variables: " + "[pEnum] = "
							+ pEnum + ", " + "[pEnumToEEnum] = " + pEnumToEEnum + ", " + "[eEnum] = " + eEnum + ".");
		}
		Object[] result2_green = EnumNodeRuleImpl.pattern_EnumNodeRule_1_2_collecttranslatedelements_greenFBBB(pEnum,
				pEnumToEEnum, eEnum);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = EnumNodeRuleImpl.pattern_EnumNodeRule_1_3_bookkeepingforedges_blackBBBBBBB(ruleresult,
				pEnum, pEnumToEEnum, eEnum, graphToPackage, graph, ePackage);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[pEnum] = " + pEnum + ", " + "[pEnumToEEnum] = "
					+ pEnumToEEnum + ", " + "[eEnum] = " + eEnum + ", " + "[graphToPackage] = " + graphToPackage + ", "
					+ "[graph] = " + graph + ", " + "[ePackage] = " + ePackage + ".");
		}
		EnumNodeRuleImpl.pattern_EnumNodeRule_1_3_bookkeepingforedges_greenBBBBBBFFFFFF(ruleresult, pEnum, pEnumToEEnum,
				eEnum, graph, ePackage);
		// EMoflonEdge pEnum__graph____graph = (EMoflonEdge) result3_green[6];
		// EMoflonEdge graph__pEnum____nodes = (EMoflonEdge) result3_green[7];
		// EMoflonEdge eEnum__ePackage____ePackage = (EMoflonEdge) result3_green[8];
		// EMoflonEdge ePackage__eEnum____eClassifiers = (EMoflonEdge) result3_green[9];
		// EMoflonEdge pEnumToEEnum__eEnum____target = (EMoflonEdge) result3_green[10];
		// EMoflonEdge pEnumToEEnum__pEnum____source = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		EnumNodeRuleImpl.pattern_EnumNodeRule_1_5_registerobjects_expressionBBBBBBBB(this, ruleresult, pEnum,
				pEnumToEEnum, eEnum, graphToPackage, graph, ePackage);
		return EnumNodeRuleImpl.pattern_EnumNodeRule_1_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_FWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = EnumNodeRuleImpl
				.pattern_EnumNodeRule_2_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = EnumNodeRuleImpl.pattern_EnumNodeRule_2_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = EnumNodeRuleImpl.pattern_EnumNodeRule_2_2_corematch_bindingFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		PEnum pEnum = (PEnum) result2_binding[0];
		ClassGraph graph = (ClassGraph) result2_binding[1];
		for (Object[] result2_black : EnumNodeRuleImpl.pattern_EnumNodeRule_2_2_corematch_blackBFBFB(pEnum, graph,
				match)) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result2_black[1];
			EPackage ePackage = (EPackage) result2_black[3];
			// ForEach find context
			for (Object[] result3_black : EnumNodeRuleImpl.pattern_EnumNodeRule_2_3_findcontext_blackBBBB(pEnum,
					graphToPackage, graph, ePackage)) {
				Object[] result3_green = EnumNodeRuleImpl.pattern_EnumNodeRule_2_3_findcontext_greenBBBBFFFFF(pEnum,
						graphToPackage, graph, ePackage);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[4];
				// EMoflonEdge pEnum__graph____graph = (EMoflonEdge) result3_green[5];
				// EMoflonEdge graph__pEnum____nodes = (EMoflonEdge) result3_green[6];
				// EMoflonEdge graphToPackage__ePackage____target = (EMoflonEdge) result3_green[7];
				// EMoflonEdge graphToPackage__graph____source = (EMoflonEdge) result3_green[8];

				// solve CSP
				Object[] result4_bindingAndBlack = EnumNodeRuleImpl
						.pattern_EnumNodeRule_2_4_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch, pEnum,
								graphToPackage, graph, ePackage);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[pEnum] = " + pEnum + ", " + "[graphToPackage] = " + graphToPackage + ", " + "[graph] = "
							+ graph + ", " + "[ePackage] = " + ePackage + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (EnumNodeRuleImpl.pattern_EnumNodeRule_2_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = EnumNodeRuleImpl
							.pattern_EnumNodeRule_2_6_addmatchtoruleresult_blackBB(ruleresult, isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					EnumNodeRuleImpl.pattern_EnumNodeRule_2_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return EnumNodeRuleImpl.pattern_EnumNodeRule_2_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_FWD(Match match, PEnum pEnum, ClassGraph graph) {
		match.registerObject("pEnum", pEnum);
		match.registerObject("graph", graph);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_FWD(Match match, PEnum pEnum, ClassGraph graph) {// Create CSP
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
	public CSP isApplicable_solveCsp_FWD(IsApplicableMatch isApplicableMatch, PEnum pEnum,
			ClassGraphToEPackage graphToPackage, ClassGraph graph, EPackage ePackage) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_pEnum_label = CSPFactoryHelper.eINSTANCE.createVariable("pEnum.label", true, csp);
		var_pEnum_label.setValue(pEnum.getLabel());
		var_pEnum_label.setType("String");

		// Create unbound variables
		Variable var_eEnum_name = CSPFactoryHelper.eINSTANCE.createVariable("eEnum.name", csp);
		var_eEnum_name.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pEnum_label, var_eEnum_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("pEnum", pEnum);
		isApplicableMatch.registerObject("graphToPackage", graphToPackage);
		isApplicableMatch.registerObject("graph", graph);
		isApplicableMatch.registerObject("ePackage", ePackage);
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
	public void registerObjects_FWD(PerformRuleResult ruleresult, EObject pEnum, EObject pEnumToEEnum, EObject eEnum,
			EObject graphToPackage, EObject graph, EObject ePackage) {
		ruleresult.registerObject("pEnum", pEnum);
		ruleresult.registerObject("pEnumToEEnum", pEnumToEEnum);
		ruleresult.registerObject("eEnum", eEnum);
		ruleresult.registerObject("graphToPackage", graphToPackage);
		ruleresult.registerObject("graph", graph);
		ruleresult.registerObject("ePackage", ePackage);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_FWD(Match match) {
		return true
				&& org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("pEnum").eClass()).equals("language.PEnum.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_BWD(Match match, EEnum eEnum, EPackage ePackage) {
		// initial bindings
		Object[] result1_black = EnumNodeRuleImpl.pattern_EnumNodeRule_10_1_initialbindings_blackBBBB(this, match,
				eEnum, ePackage);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[eEnum] = " + eEnum + ", "
					+ "[ePackage] = " + ePackage + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = EnumNodeRuleImpl
				.pattern_EnumNodeRule_10_2_SolveCSP_bindingAndBlackFBBBB(this, match, eEnum, ePackage);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[eEnum] = " + eEnum + ", " + "[ePackage] = "
					+ ePackage + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (EnumNodeRuleImpl.pattern_EnumNodeRule_10_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = EnumNodeRuleImpl
					.pattern_EnumNodeRule_10_4_collectelementstobetranslated_blackBBB(match, eEnum, ePackage);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[eEnum] = " + eEnum + ", " + "[ePackage] = "
						+ ePackage + ".");
			}
			EnumNodeRuleImpl.pattern_EnumNodeRule_10_4_collectelementstobetranslated_greenBBBFF(match, eEnum, ePackage);
			// EMoflonEdge eEnum__ePackage____ePackage = (EMoflonEdge) result4_green[3];
			// EMoflonEdge ePackage__eEnum____eClassifiers = (EMoflonEdge) result4_green[4];

			// collect context elements
			Object[] result5_black = EnumNodeRuleImpl.pattern_EnumNodeRule_10_5_collectcontextelements_blackBBB(match,
					eEnum, ePackage);
			if (result5_black == null) {
				throw new RuntimeException(
						"Pattern matching in node [collect context elements] failed." + " Variables: " + "[match] = "
								+ match + ", " + "[eEnum] = " + eEnum + ", " + "[ePackage] = " + ePackage + ".");
			}
			EnumNodeRuleImpl.pattern_EnumNodeRule_10_5_collectcontextelements_greenBB(match, ePackage);

			// register objects to match
			EnumNodeRuleImpl.pattern_EnumNodeRule_10_6_registerobjectstomatch_expressionBBBB(this, match, eEnum,
					ePackage);
			return EnumNodeRuleImpl.pattern_EnumNodeRule_10_7_expressionF();
		} else {
			return EnumNodeRuleImpl.pattern_EnumNodeRule_10_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_BWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = EnumNodeRuleImpl
				.pattern_EnumNodeRule_11_1_performtransformation_bindingAndBlackFFFFFBB(this, isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		EEnum eEnum = (EEnum) result1_bindingAndBlack[0];
		ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result1_bindingAndBlack[1];
		ClassGraph graph = (ClassGraph) result1_bindingAndBlack[2];
		EPackage ePackage = (EPackage) result1_bindingAndBlack[3];
		CSP csp = (CSP) result1_bindingAndBlack[4];
		Object[] result1_green = EnumNodeRuleImpl.pattern_EnumNodeRule_11_1_performtransformation_greenFFBBB(eEnum,
				graph, csp);
		PEnum pEnum = (PEnum) result1_green[0];
		PNodeToEClassifier pEnumToEEnum = (PNodeToEClassifier) result1_green[1];

		// collect translated elements
		Object[] result2_black = EnumNodeRuleImpl.pattern_EnumNodeRule_11_2_collecttranslatedelements_blackBBB(pEnum,
				pEnumToEEnum, eEnum);
		if (result2_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [collect translated elements] failed." + " Variables: " + "[pEnum] = "
							+ pEnum + ", " + "[pEnumToEEnum] = " + pEnumToEEnum + ", " + "[eEnum] = " + eEnum + ".");
		}
		Object[] result2_green = EnumNodeRuleImpl.pattern_EnumNodeRule_11_2_collecttranslatedelements_greenFBBB(pEnum,
				pEnumToEEnum, eEnum);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = EnumNodeRuleImpl.pattern_EnumNodeRule_11_3_bookkeepingforedges_blackBBBBBBB(ruleresult,
				pEnum, pEnumToEEnum, eEnum, graphToPackage, graph, ePackage);
		if (result3_black == null) {
			throw new RuntimeException("Pattern matching in node [bookkeeping for edges] failed." + " Variables: "
					+ "[ruleresult] = " + ruleresult + ", " + "[pEnum] = " + pEnum + ", " + "[pEnumToEEnum] = "
					+ pEnumToEEnum + ", " + "[eEnum] = " + eEnum + ", " + "[graphToPackage] = " + graphToPackage + ", "
					+ "[graph] = " + graph + ", " + "[ePackage] = " + ePackage + ".");
		}
		EnumNodeRuleImpl.pattern_EnumNodeRule_11_3_bookkeepingforedges_greenBBBBBBFFFFFF(ruleresult, pEnum,
				pEnumToEEnum, eEnum, graph, ePackage);
		// EMoflonEdge pEnum__graph____graph = (EMoflonEdge) result3_green[6];
		// EMoflonEdge graph__pEnum____nodes = (EMoflonEdge) result3_green[7];
		// EMoflonEdge eEnum__ePackage____ePackage = (EMoflonEdge) result3_green[8];
		// EMoflonEdge ePackage__eEnum____eClassifiers = (EMoflonEdge) result3_green[9];
		// EMoflonEdge pEnumToEEnum__eEnum____target = (EMoflonEdge) result3_green[10];
		// EMoflonEdge pEnumToEEnum__pEnum____source = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		EnumNodeRuleImpl.pattern_EnumNodeRule_11_5_registerobjects_expressionBBBBBBBB(this, ruleresult, pEnum,
				pEnumToEEnum, eEnum, graphToPackage, graph, ePackage);
		return EnumNodeRuleImpl.pattern_EnumNodeRule_11_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_BWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = EnumNodeRuleImpl
				.pattern_EnumNodeRule_12_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = EnumNodeRuleImpl
				.pattern_EnumNodeRule_12_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = EnumNodeRuleImpl.pattern_EnumNodeRule_12_2_corematch_bindingFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		EEnum eEnum = (EEnum) result2_binding[0];
		EPackage ePackage = (EPackage) result2_binding[1];
		for (Object[] result2_black : EnumNodeRuleImpl.pattern_EnumNodeRule_12_2_corematch_blackBFFBB(eEnum, ePackage,
				match)) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result2_black[1];
			ClassGraph graph = (ClassGraph) result2_black[2];
			// ForEach find context
			for (Object[] result3_black : EnumNodeRuleImpl.pattern_EnumNodeRule_12_3_findcontext_blackBBBB(eEnum,
					graphToPackage, graph, ePackage)) {
				Object[] result3_green = EnumNodeRuleImpl.pattern_EnumNodeRule_12_3_findcontext_greenBBBBFFFFF(eEnum,
						graphToPackage, graph, ePackage);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[4];
				// EMoflonEdge eEnum__ePackage____ePackage = (EMoflonEdge) result3_green[5];
				// EMoflonEdge ePackage__eEnum____eClassifiers = (EMoflonEdge) result3_green[6];
				// EMoflonEdge graphToPackage__ePackage____target = (EMoflonEdge) result3_green[7];
				// EMoflonEdge graphToPackage__graph____source = (EMoflonEdge) result3_green[8];

				// solve CSP
				Object[] result4_bindingAndBlack = EnumNodeRuleImpl
						.pattern_EnumNodeRule_12_4_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch, eEnum,
								graphToPackage, graph, ePackage);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[eEnum] = " + eEnum + ", " + "[graphToPackage] = " + graphToPackage + ", " + "[graph] = "
							+ graph + ", " + "[ePackage] = " + ePackage + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (EnumNodeRuleImpl.pattern_EnumNodeRule_12_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = EnumNodeRuleImpl
							.pattern_EnumNodeRule_12_6_addmatchtoruleresult_blackBB(ruleresult, isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					EnumNodeRuleImpl.pattern_EnumNodeRule_12_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return EnumNodeRuleImpl.pattern_EnumNodeRule_12_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_BWD(Match match, EEnum eEnum, EPackage ePackage) {
		match.registerObject("eEnum", eEnum);
		match.registerObject("ePackage", ePackage);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_BWD(Match match, EEnum eEnum, EPackage ePackage) {// Create CSP
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
	public CSP isApplicable_solveCsp_BWD(IsApplicableMatch isApplicableMatch, EEnum eEnum,
			ClassGraphToEPackage graphToPackage, ClassGraph graph, EPackage ePackage) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_eEnum_name = CSPFactoryHelper.eINSTANCE.createVariable("eEnum.name", true, csp);
		var_eEnum_name.setValue(eEnum.getName());
		var_eEnum_name.setType("String");

		// Create unbound variables
		Variable var_pEnum_label = CSPFactoryHelper.eINSTANCE.createVariable("pEnum.label", csp);
		var_pEnum_label.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pEnum_label, var_eEnum_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("eEnum", eEnum);
		isApplicableMatch.registerObject("graphToPackage", graphToPackage);
		isApplicableMatch.registerObject("graph", graph);
		isApplicableMatch.registerObject("ePackage", ePackage);
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
	public void registerObjects_BWD(PerformRuleResult ruleresult, EObject pEnum, EObject pEnumToEEnum, EObject eEnum,
			EObject graphToPackage, EObject graph, EObject ePackage) {
		ruleresult.registerObject("pEnum", pEnum);
		ruleresult.registerObject("pEnumToEEnum", pEnumToEEnum);
		ruleresult.registerObject("eEnum", eEnum);
		ruleresult.registerObject("graphToPackage", graphToPackage);
		ruleresult.registerObject("graph", graph);
		ruleresult.registerObject("ePackage", ePackage);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_BWD(Match match) {
		return true && org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("eEnum").eClass()).equals("ecore.EEnum.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_BWD_EMoflonEdge_4(EMoflonEdge _edge_ePackage) {
		// prepare return value
		Object[] result1_bindingAndBlack = EnumNodeRuleImpl
				.pattern_EnumNodeRule_20_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = EnumNodeRuleImpl.pattern_EnumNodeRule_20_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : EnumNodeRuleImpl
				.pattern_EnumNodeRule_20_2_testcorematchandDECs_blackFFB(_edge_ePackage)) {
			EEnum eEnum = (EEnum) result2_black[0];
			EPackage ePackage = (EPackage) result2_black[1];
			Object[] result2_green = EnumNodeRuleImpl.pattern_EnumNodeRule_20_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (EnumNodeRuleImpl.pattern_EnumNodeRule_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
					this, match, eEnum, ePackage)) {
				// Ensure that the correct types of elements are matched
				if (EnumNodeRuleImpl
						.pattern_EnumNodeRule_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(this,
								match)) {

					// Add match to rule result
					Object[] result5_black = EnumNodeRuleImpl.pattern_EnumNodeRule_20_5_Addmatchtoruleresult_blackBBBB(
							match, __performOperation, __result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					EnumNodeRuleImpl.pattern_EnumNodeRule_20_5_Addmatchtoruleresult_greenBBBB(match, __performOperation,
							__result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return EnumNodeRuleImpl.pattern_EnumNodeRule_20_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_FWD_EMoflonEdge_4(EMoflonEdge _edge_graph) {
		// prepare return value
		Object[] result1_bindingAndBlack = EnumNodeRuleImpl
				.pattern_EnumNodeRule_21_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = EnumNodeRuleImpl.pattern_EnumNodeRule_21_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : EnumNodeRuleImpl
				.pattern_EnumNodeRule_21_2_testcorematchandDECs_blackFFB(_edge_graph)) {
			PEnum pEnum = (PEnum) result2_black[0];
			ClassGraph graph = (ClassGraph) result2_black[1];
			Object[] result2_green = EnumNodeRuleImpl.pattern_EnumNodeRule_21_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (EnumNodeRuleImpl.pattern_EnumNodeRule_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
					this, match, pEnum, graph)) {
				// Ensure that the correct types of elements are matched
				if (EnumNodeRuleImpl
						.pattern_EnumNodeRule_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(this,
								match)) {

					// Add match to rule result
					Object[] result5_black = EnumNodeRuleImpl.pattern_EnumNodeRule_21_5_Addmatchtoruleresult_blackBBBB(
							match, __performOperation, __result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					EnumNodeRuleImpl.pattern_EnumNodeRule_21_5_Addmatchtoruleresult_greenBBBB(match, __performOperation,
							__result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return EnumNodeRuleImpl.pattern_EnumNodeRule_21_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintsRuleResult checkAttributes_FWD(TripleMatch __tripleMatch) {
		AttributeConstraintsRuleResult ruleResult = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE
				.createAttributeConstraintsRuleResult();
		ruleResult.setRule("EnumNodeRule");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		Variable var_eEnum_name = CSPFactoryHelper.eINSTANCE.createVariable("eEnum", true, csp);
		var_eEnum_name.setValue(__helper.getValue("eEnum", "name"));
		var_eEnum_name.setType("String");

		Variable var_pEnum_label = CSPFactoryHelper.eINSTANCE.createVariable("pEnum", true, csp);
		var_pEnum_label.setValue(__helper.getValue("pEnum", "label"));
		var_pEnum_label.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		eq0.setRuleName("EnumNodeRule");
		eq0.solve(var_pEnum_label, var_eEnum_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			var_eEnum_name.setBound(false);
			eq0.solve(var_pEnum_label, var_eEnum_name);
			if (csp.check()) {
				ruleResult.setSuccess(true);
				ruleResult.setRequiredChange(true);
				__helper.setValue("eEnum", "name", var_eEnum_name.getValue());
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
		ruleResult.setRule("EnumNodeRule");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		Variable var_eEnum_name = CSPFactoryHelper.eINSTANCE.createVariable("eEnum", true, csp);
		var_eEnum_name.setValue(__helper.getValue("eEnum", "name"));
		var_eEnum_name.setType("String");

		Variable var_pEnum_label = CSPFactoryHelper.eINSTANCE.createVariable("pEnum", true, csp);
		var_pEnum_label.setValue(__helper.getValue("pEnum", "label"));
		var_pEnum_label.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		eq0.setRuleName("EnumNodeRule");
		eq0.solve(var_pEnum_label, var_eEnum_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			var_pEnum_label.setBound(false);
			eq0.solve(var_pEnum_label, var_eEnum_name);
			if (csp.check()) {
				ruleResult.setSuccess(true);
				ruleResult.setRequiredChange(true);
				__helper.setValue("pEnum", "label", var_pEnum_label.getValue());
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
		Object[] result1_black = EnumNodeRuleImpl.pattern_EnumNodeRule_24_1_prepare_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [prepare] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = EnumNodeRuleImpl.pattern_EnumNodeRule_24_1_prepare_greenF();
		IsApplicableRuleResult result = (IsApplicableRuleResult) result1_green[0];

		// match src trg context
		Object[] result2_bindingAndBlack = EnumNodeRuleImpl
				.pattern_EnumNodeRule_24_2_matchsrctrgcontext_bindingAndBlackFFFFBB(sourceMatch, targetMatch);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [match src trg context] failed." + " Variables: "
					+ "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = " + targetMatch + ".");
		}
		PEnum pEnum = (PEnum) result2_bindingAndBlack[0];
		EEnum eEnum = (EEnum) result2_bindingAndBlack[1];
		ClassGraph graph = (ClassGraph) result2_bindingAndBlack[2];
		EPackage ePackage = (EPackage) result2_bindingAndBlack[3];

		// solve csp
		Object[] result3_bindingAndBlack = EnumNodeRuleImpl.pattern_EnumNodeRule_24_3_solvecsp_bindingAndBlackFBBBBBBB(
				this, pEnum, eEnum, graph, ePackage, sourceMatch, targetMatch);
		if (result3_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [solve csp] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[pEnum] = " + pEnum + ", " + "[eEnum] = " + eEnum + ", " + "[graph] = " + graph
					+ ", " + "[ePackage] = " + ePackage + ", " + "[sourceMatch] = " + sourceMatch + ", "
					+ "[targetMatch] = " + targetMatch + ".");
		}
		CSP csp = (CSP) result3_bindingAndBlack[0];
		// check CSP
		if (EnumNodeRuleImpl.pattern_EnumNodeRule_24_4_checkCSP_expressionFB(csp)) {
			// ForEach match corr context
			for (Object[] result5_black : EnumNodeRuleImpl.pattern_EnumNodeRule_24_5_matchcorrcontext_blackFBBBB(graph,
					ePackage, sourceMatch, targetMatch)) {
				ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result5_black[0];
				Object[] result5_green = EnumNodeRuleImpl
						.pattern_EnumNodeRule_24_5_matchcorrcontext_greenBBBF(graphToPackage, sourceMatch, targetMatch);
				CCMatch ccMatch = (CCMatch) result5_green[3];

				// create correspondence
				Object[] result6_black = EnumNodeRuleImpl.pattern_EnumNodeRule_24_6_createcorrespondence_blackBBBBB(
						pEnum, eEnum, graph, ePackage, ccMatch);
				if (result6_black == null) {
					throw new RuntimeException("Pattern matching in node [create correspondence] failed."
							+ " Variables: " + "[pEnum] = " + pEnum + ", " + "[eEnum] = " + eEnum + ", " + "[graph] = "
							+ graph + ", " + "[ePackage] = " + ePackage + ", " + "[ccMatch] = " + ccMatch + ".");
				}
				EnumNodeRuleImpl.pattern_EnumNodeRule_24_6_createcorrespondence_greenBFBB(pEnum, eEnum, ccMatch);
				// PNodeToEClassifier pEnumToEEnum = (PNodeToEClassifier) result6_green[1];

				// add to returned result
				Object[] result7_black = EnumNodeRuleImpl.pattern_EnumNodeRule_24_7_addtoreturnedresult_blackBB(result,
						ccMatch);
				if (result7_black == null) {
					throw new RuntimeException("Pattern matching in node [add to returned result] failed."
							+ " Variables: " + "[result] = " + result + ", " + "[ccMatch] = " + ccMatch + ".");
				}
				EnumNodeRuleImpl.pattern_EnumNodeRule_24_7_addtoreturnedresult_greenBB(result, ccMatch);

			}

		} else {
		}
		return EnumNodeRuleImpl.pattern_EnumNodeRule_24_8_expressionFB(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isApplicable_solveCsp_CC(PEnum pEnum, EEnum eEnum, ClassGraph graph, EPackage ePackage,
			Match sourceMatch, Match targetMatch) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();

		// Create literals

		// Create attribute variables
		Variable var_pEnum_label = CSPFactoryHelper.eINSTANCE.createVariable("pEnum.label", true, csp);
		var_pEnum_label.setValue(pEnum.getLabel());
		var_pEnum_label.setType("String");
		Variable var_eEnum_name = CSPFactoryHelper.eINSTANCE.createVariable("eEnum.name", true, csp);
		var_eEnum_name.setValue(eEnum.getName());
		var_eEnum_name.setType("String");

		// Create unbound variables

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pEnum_label, var_eEnum_name);
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
	public boolean checkDEC_FWD(PEnum pEnum, ClassGraph graph) {// match tgg pattern
		Object[] result1_black = EnumNodeRuleImpl.pattern_EnumNodeRule_27_1_matchtggpattern_blackBB(pEnum, graph);
		if (result1_black != null) {
			return EnumNodeRuleImpl.pattern_EnumNodeRule_27_2_expressionF();
		} else {
			return EnumNodeRuleImpl.pattern_EnumNodeRule_27_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDEC_BWD(EEnum eEnum, EPackage ePackage) {// match tgg pattern
		Object[] result1_black = EnumNodeRuleImpl.pattern_EnumNodeRule_28_1_matchtggpattern_blackBB(eEnum, ePackage);
		if (result1_black != null) {
			return EnumNodeRuleImpl.pattern_EnumNodeRule_28_2_expressionF();
		} else {
			return EnumNodeRuleImpl.pattern_EnumNodeRule_28_3_expressionF();
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
		Object[] result1_black = EnumNodeRuleImpl.pattern_EnumNodeRule_29_1_createresult_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [create result] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = EnumNodeRuleImpl.pattern_EnumNodeRule_29_1_createresult_greenFF();
		IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result1_green[0];
		ModelgeneratorRuleResult ruleResult = (ModelgeneratorRuleResult) result1_green[1];

		// ForEach is applicable core
		for (Object[] result2_black : EnumNodeRuleImpl
				.pattern_EnumNodeRule_29_2_isapplicablecore_blackFFFFBB(ruleEntryContainer, ruleResult)) {
			// RuleEntryList graphToPackageList = (RuleEntryList) result2_black[0];
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result2_black[1];
			EPackage ePackage = (EPackage) result2_black[2];
			ClassGraph graph = (ClassGraph) result2_black[3];

			// solve CSP
			Object[] result3_bindingAndBlack = EnumNodeRuleImpl
					.pattern_EnumNodeRule_29_3_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch, graphToPackage,
							graph, ePackage, ruleResult);
			if (result3_bindingAndBlack == null) {
				throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: " + "[this] = "
						+ this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", " + "[graphToPackage] = "
						+ graphToPackage + ", " + "[graph] = " + graph + ", " + "[ePackage] = " + ePackage + ", "
						+ "[ruleResult] = " + ruleResult + ".");
			}
			CSP csp = (CSP) result3_bindingAndBlack[0];
			// check CSP
			if (EnumNodeRuleImpl.pattern_EnumNodeRule_29_4_checkCSP_expressionFBB(this, csp)) {
				// check nacs
				Object[] result5_black = EnumNodeRuleImpl.pattern_EnumNodeRule_29_5_checknacs_blackBBB(graphToPackage,
						graph, ePackage);
				if (result5_black != null) {

					// perform
					Object[] result6_black = EnumNodeRuleImpl
							.pattern_EnumNodeRule_29_6_perform_blackBBBB(graphToPackage, graph, ePackage, ruleResult);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [perform] failed." + " Variables: "
								+ "[graphToPackage] = " + graphToPackage + ", " + "[graph] = " + graph + ", "
								+ "[ePackage] = " + ePackage + ", " + "[ruleResult] = " + ruleResult + ".");
					}
					EnumNodeRuleImpl.pattern_EnumNodeRule_29_6_perform_greenFFFBBBB(graph, ePackage, ruleResult, csp);
					// PEnum pEnum = (PEnum) result6_green[0];
					// PNodeToEClassifier pEnumToEEnum = (PNodeToEClassifier) result6_green[1];
					// EEnum eEnum = (EEnum) result6_green[2];

				} else {
				}

			} else {
			}

		}
		return EnumNodeRuleImpl.pattern_EnumNodeRule_29_7_expressionFB(ruleResult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP generateModel_solveCsp_BWD(IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage,
			ClassGraph graph, EPackage ePackage, ModelgeneratorRuleResult ruleResult) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables

		// Create unbound variables
		Variable var_pEnum_label = CSPFactoryHelper.eINSTANCE.createVariable("pEnum.label", csp);
		var_pEnum_label.setType("String");
		Variable var_eEnum_name = CSPFactoryHelper.eINSTANCE.createVariable("eEnum.name", csp);
		var_eEnum_name.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pEnum_label, var_eEnum_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("graphToPackage", graphToPackage);
		isApplicableMatch.registerObject("graph", graph);
		isApplicableMatch.registerObject("ePackage", ePackage);
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
		case RulesPackage.ENUM_NODE_RULE___IS_APPROPRIATE_FWD__MATCH_PENUM_CLASSGRAPH:
			return isAppropriate_FWD((Match) arguments.get(0), (PEnum) arguments.get(1), (ClassGraph) arguments.get(2));
		case RulesPackage.ENUM_NODE_RULE___PERFORM_FWD__ISAPPLICABLEMATCH:
			return perform_FWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___IS_APPLICABLE_FWD__MATCH:
			return isApplicable_FWD((Match) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_PENUM_CLASSGRAPH:
			registerObjectsToMatch_FWD((Match) arguments.get(0), (PEnum) arguments.get(1),
					(ClassGraph) arguments.get(2));
			return null;
		case RulesPackage.ENUM_NODE_RULE___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_PENUM_CLASSGRAPH:
			return isAppropriate_solveCsp_FWD((Match) arguments.get(0), (PEnum) arguments.get(1),
					(ClassGraph) arguments.get(2));
		case RulesPackage.ENUM_NODE_RULE___IS_APPROPRIATE_CHECK_CSP_FWD__CSP:
			return isAppropriate_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_PENUM_CLASSGRAPHTOEPACKAGE_CLASSGRAPH_EPACKAGE:
			return isApplicable_solveCsp_FWD((IsApplicableMatch) arguments.get(0), (PEnum) arguments.get(1),
					(ClassGraphToEPackage) arguments.get(2), (ClassGraph) arguments.get(3),
					(EPackage) arguments.get(4));
		case RulesPackage.ENUM_NODE_RULE___IS_APPLICABLE_CHECK_CSP_FWD__CSP:
			return isApplicable_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_FWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6));
			return null;
		case RulesPackage.ENUM_NODE_RULE___CHECK_TYPES_FWD__MATCH:
			return checkTypes_FWD((Match) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___IS_APPROPRIATE_BWD__MATCH_EENUM_EPACKAGE:
			return isAppropriate_BWD((Match) arguments.get(0), (EEnum) arguments.get(1), (EPackage) arguments.get(2));
		case RulesPackage.ENUM_NODE_RULE___PERFORM_BWD__ISAPPLICABLEMATCH:
			return perform_BWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___IS_APPLICABLE_BWD__MATCH:
			return isApplicable_BWD((Match) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_EENUM_EPACKAGE:
			registerObjectsToMatch_BWD((Match) arguments.get(0), (EEnum) arguments.get(1), (EPackage) arguments.get(2));
			return null;
		case RulesPackage.ENUM_NODE_RULE___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_EENUM_EPACKAGE:
			return isAppropriate_solveCsp_BWD((Match) arguments.get(0), (EEnum) arguments.get(1),
					(EPackage) arguments.get(2));
		case RulesPackage.ENUM_NODE_RULE___IS_APPROPRIATE_CHECK_CSP_BWD__CSP:
			return isAppropriate_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_EENUM_CLASSGRAPHTOEPACKAGE_CLASSGRAPH_EPACKAGE:
			return isApplicable_solveCsp_BWD((IsApplicableMatch) arguments.get(0), (EEnum) arguments.get(1),
					(ClassGraphToEPackage) arguments.get(2), (ClassGraph) arguments.get(3),
					(EPackage) arguments.get(4));
		case RulesPackage.ENUM_NODE_RULE___IS_APPLICABLE_CHECK_CSP_BWD__CSP:
			return isApplicable_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_BWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6));
			return null;
		case RulesPackage.ENUM_NODE_RULE___CHECK_TYPES_BWD__MATCH:
			return checkTypes_BWD((Match) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___IS_APPROPRIATE_BWD_EMOFLON_EDGE_4__EMOFLONEDGE:
			return isAppropriate_BWD_EMoflonEdge_4((EMoflonEdge) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___IS_APPROPRIATE_FWD_EMOFLON_EDGE_4__EMOFLONEDGE:
			return isAppropriate_FWD_EMoflonEdge_4((EMoflonEdge) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH:
			return checkAttributes_FWD((TripleMatch) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH:
			return checkAttributes_BWD((TripleMatch) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___IS_APPLICABLE_CC__MATCH_MATCH:
			return isApplicable_CC((Match) arguments.get(0), (Match) arguments.get(1));
		case RulesPackage.ENUM_NODE_RULE___IS_APPLICABLE_SOLVE_CSP_CC__PENUM_EENUM_CLASSGRAPH_EPACKAGE_MATCH_MATCH:
			return isApplicable_solveCsp_CC((PEnum) arguments.get(0), (EEnum) arguments.get(1),
					(ClassGraph) arguments.get(2), (EPackage) arguments.get(3), (Match) arguments.get(4),
					(Match) arguments.get(5));
		case RulesPackage.ENUM_NODE_RULE___IS_APPLICABLE_CHECK_CSP_CC__CSP:
			return isApplicable_checkCsp_CC((CSP) arguments.get(0));
		case RulesPackage.ENUM_NODE_RULE___CHECK_DEC_FWD__PENUM_CLASSGRAPH:
			return checkDEC_FWD((PEnum) arguments.get(0), (ClassGraph) arguments.get(1));
		case RulesPackage.ENUM_NODE_RULE___CHECK_DEC_BWD__EENUM_EPACKAGE:
			return checkDEC_BWD((EEnum) arguments.get(0), (EPackage) arguments.get(1));
		case RulesPackage.ENUM_NODE_RULE___GENERATE_MODEL__RULEENTRYCONTAINER_CLASSGRAPHTOEPACKAGE:
			return generateModel((RuleEntryContainer) arguments.get(0), (ClassGraphToEPackage) arguments.get(1));
		case RulesPackage.ENUM_NODE_RULE___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_CLASSGRAPHTOEPACKAGE_CLASSGRAPH_EPACKAGE_MODELGENERATORRULERESULT:
			return generateModel_solveCsp_BWD((IsApplicableMatch) arguments.get(0),
					(ClassGraphToEPackage) arguments.get(1), (ClassGraph) arguments.get(2), (EPackage) arguments.get(3),
					(ModelgeneratorRuleResult) arguments.get(4));
		case RulesPackage.ENUM_NODE_RULE___GENERATE_MODEL_CHECK_CSP_BWD__CSP:
			return generateModel_checkCsp_BWD((CSP) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	public static final Object[] pattern_EnumNodeRule_0_1_initialbindings_blackBBBB(EnumNodeRule _this, Match match,
			PEnum pEnum, ClassGraph graph) {
		return new Object[] { _this, match, pEnum, graph };
	}

	public static final Object[] pattern_EnumNodeRule_0_2_SolveCSP_bindingFBBBB(EnumNodeRule _this, Match match,
			PEnum pEnum, ClassGraph graph) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_FWD(match, pEnum, graph);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, pEnum, graph };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_0_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_EnumNodeRule_0_2_SolveCSP_bindingAndBlackFBBBB(EnumNodeRule _this, Match match,
			PEnum pEnum, ClassGraph graph) {
		Object[] result_pattern_EnumNodeRule_0_2_SolveCSP_binding = pattern_EnumNodeRule_0_2_SolveCSP_bindingFBBBB(
				_this, match, pEnum, graph);
		if (result_pattern_EnumNodeRule_0_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_EnumNodeRule_0_2_SolveCSP_binding[0];

			Object[] result_pattern_EnumNodeRule_0_2_SolveCSP_black = pattern_EnumNodeRule_0_2_SolveCSP_blackB(csp);
			if (result_pattern_EnumNodeRule_0_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, pEnum, graph };
			}
		}
		return null;
	}

	public static final boolean pattern_EnumNodeRule_0_3_CheckCSP_expressionFBB(EnumNodeRule _this, CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_0_4_collectelementstobetranslated_blackBBB(Match match,
			PEnum pEnum, ClassGraph graph) {
		return new Object[] { match, pEnum, graph };
	}

	public static final Object[] pattern_EnumNodeRule_0_4_collectelementstobetranslated_greenBBBFF(Match match,
			PEnum pEnum, ClassGraph graph) {
		EMoflonEdge pEnum__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pEnum____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getToBeTranslatedNodes().add(pEnum);
		String pEnum__graph____graph_name_prime = "graph";
		String graph__pEnum____nodes_name_prime = "nodes";
		pEnum__graph____graph.setSrc(pEnum);
		pEnum__graph____graph.setTrg(graph);
		match.getToBeTranslatedEdges().add(pEnum__graph____graph);
		graph__pEnum____nodes.setSrc(graph);
		graph__pEnum____nodes.setTrg(pEnum);
		match.getToBeTranslatedEdges().add(graph__pEnum____nodes);
		pEnum__graph____graph.setName(pEnum__graph____graph_name_prime);
		graph__pEnum____nodes.setName(graph__pEnum____nodes_name_prime);
		return new Object[] { match, pEnum, graph, pEnum__graph____graph, graph__pEnum____nodes };
	}

	public static final Object[] pattern_EnumNodeRule_0_5_collectcontextelements_blackBBB(Match match, PEnum pEnum,
			ClassGraph graph) {
		return new Object[] { match, pEnum, graph };
	}

	public static final Object[] pattern_EnumNodeRule_0_5_collectcontextelements_greenBB(Match match,
			ClassGraph graph) {
		match.getContextNodes().add(graph);
		return new Object[] { match, graph };
	}

	public static final void pattern_EnumNodeRule_0_6_registerobjectstomatch_expressionBBBB(EnumNodeRule _this,
			Match match, PEnum pEnum, ClassGraph graph) {
		_this.registerObjectsToMatch_FWD(match, pEnum, graph);

	}

	public static final boolean pattern_EnumNodeRule_0_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_EnumNodeRule_0_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_1_1_performtransformation_bindingFFFFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("pEnum");
		EObject _localVariable_1 = isApplicableMatch.getObject("graphToPackage");
		EObject _localVariable_2 = isApplicableMatch.getObject("graph");
		EObject _localVariable_3 = isApplicableMatch.getObject("ePackage");
		EObject tmpPEnum = _localVariable_0;
		EObject tmpGraphToPackage = _localVariable_1;
		EObject tmpGraph = _localVariable_2;
		EObject tmpEPackage = _localVariable_3;
		if (tmpPEnum instanceof PEnum) {
			PEnum pEnum = (PEnum) tmpPEnum;
			if (tmpGraphToPackage instanceof ClassGraphToEPackage) {
				ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) tmpGraphToPackage;
				if (tmpGraph instanceof ClassGraph) {
					ClassGraph graph = (ClassGraph) tmpGraph;
					if (tmpEPackage instanceof EPackage) {
						EPackage ePackage = (EPackage) tmpEPackage;
						return new Object[] { pEnum, graphToPackage, graph, ePackage, isApplicableMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_1_1_performtransformation_blackBBBBFBB(PEnum pEnum,
			ClassGraphToEPackage graphToPackage, ClassGraph graph, EPackage ePackage, EnumNodeRule _this,
			IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { pEnum, graphToPackage, graph, ePackage, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_1_1_performtransformation_bindingAndBlackFFFFFBB(
			EnumNodeRule _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_EnumNodeRule_1_1_performtransformation_binding = pattern_EnumNodeRule_1_1_performtransformation_bindingFFFFB(
				isApplicableMatch);
		if (result_pattern_EnumNodeRule_1_1_performtransformation_binding != null) {
			PEnum pEnum = (PEnum) result_pattern_EnumNodeRule_1_1_performtransformation_binding[0];
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result_pattern_EnumNodeRule_1_1_performtransformation_binding[1];
			ClassGraph graph = (ClassGraph) result_pattern_EnumNodeRule_1_1_performtransformation_binding[2];
			EPackage ePackage = (EPackage) result_pattern_EnumNodeRule_1_1_performtransformation_binding[3];

			Object[] result_pattern_EnumNodeRule_1_1_performtransformation_black = pattern_EnumNodeRule_1_1_performtransformation_blackBBBBFBB(
					pEnum, graphToPackage, graph, ePackage, _this, isApplicableMatch);
			if (result_pattern_EnumNodeRule_1_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_EnumNodeRule_1_1_performtransformation_black[4];

				return new Object[] { pEnum, graphToPackage, graph, ePackage, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_1_1_performtransformation_greenBFFBB(PEnum pEnum,
			EPackage ePackage, CSP csp) {
		PNodeToEClassifier pEnumToEEnum = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		EEnum eEnum = EcoreFactory.eINSTANCE.createEEnum();
		Object _localVariable_0 = csp.getValue("eEnum", "name");
		pEnumToEEnum.setSource(pEnum);
		ePackage.getEClassifiers().add(eEnum);
		pEnumToEEnum.setTarget(eEnum);
		String eEnum_name_prime = (String) _localVariable_0;
		eEnum.setName(eEnum_name_prime);
		return new Object[] { pEnum, pEnumToEEnum, eEnum, ePackage, csp };
	}

	public static final Object[] pattern_EnumNodeRule_1_2_collecttranslatedelements_blackBBB(PEnum pEnum,
			PNodeToEClassifier pEnumToEEnum, EEnum eEnum) {
		return new Object[] { pEnum, pEnumToEEnum, eEnum };
	}

	public static final Object[] pattern_EnumNodeRule_1_2_collecttranslatedelements_greenFBBB(PEnum pEnum,
			PNodeToEClassifier pEnumToEEnum, EEnum eEnum) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getTranslatedElements().add(pEnum);
		ruleresult.getCreatedLinkElements().add(pEnumToEEnum);
		ruleresult.getCreatedElements().add(eEnum);
		return new Object[] { ruleresult, pEnum, pEnumToEEnum, eEnum };
	}

	public static final Object[] pattern_EnumNodeRule_1_3_bookkeepingforedges_blackBBBBBBB(PerformRuleResult ruleresult,
			EObject pEnum, EObject pEnumToEEnum, EObject eEnum, EObject graphToPackage, EObject graph,
			EObject ePackage) {
		if (!pEnum.equals(pEnumToEEnum)) {
			if (!eEnum.equals(pEnum)) {
				if (!eEnum.equals(pEnumToEEnum)) {
					if (!eEnum.equals(graphToPackage)) {
						if (!eEnum.equals(graph)) {
							if (!eEnum.equals(ePackage)) {
								if (!graphToPackage.equals(pEnum)) {
									if (!graphToPackage.equals(pEnumToEEnum)) {
										if (!graph.equals(pEnum)) {
											if (!graph.equals(pEnumToEEnum)) {
												if (!graph.equals(graphToPackage)) {
													if (!ePackage.equals(pEnum)) {
														if (!ePackage.equals(pEnumToEEnum)) {
															if (!ePackage.equals(graphToPackage)) {
																if (!ePackage.equals(graph)) {
																	return new Object[] { ruleresult, pEnum,
																			pEnumToEEnum, eEnum, graphToPackage, graph,
																			ePackage };
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

	public static final Object[] pattern_EnumNodeRule_1_3_bookkeepingforedges_greenBBBBBBFFFFFF(
			PerformRuleResult ruleresult, EObject pEnum, EObject pEnumToEEnum, EObject eEnum, EObject graph,
			EObject ePackage) {
		EMoflonEdge pEnum__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pEnum____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge eEnum__ePackage____ePackage = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ePackage__eEnum____eClassifiers = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pEnumToEEnum__eEnum____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pEnumToEEnum__pEnum____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "EnumNodeRule";
		String pEnum__graph____graph_name_prime = "graph";
		String graph__pEnum____nodes_name_prime = "nodes";
		String eEnum__ePackage____ePackage_name_prime = "ePackage";
		String ePackage__eEnum____eClassifiers_name_prime = "eClassifiers";
		String pEnumToEEnum__eEnum____target_name_prime = "target";
		String pEnumToEEnum__pEnum____source_name_prime = "source";
		pEnum__graph____graph.setSrc(pEnum);
		pEnum__graph____graph.setTrg(graph);
		ruleresult.getTranslatedEdges().add(pEnum__graph____graph);
		graph__pEnum____nodes.setSrc(graph);
		graph__pEnum____nodes.setTrg(pEnum);
		ruleresult.getTranslatedEdges().add(graph__pEnum____nodes);
		eEnum__ePackage____ePackage.setSrc(eEnum);
		eEnum__ePackage____ePackage.setTrg(ePackage);
		ruleresult.getCreatedEdges().add(eEnum__ePackage____ePackage);
		ePackage__eEnum____eClassifiers.setSrc(ePackage);
		ePackage__eEnum____eClassifiers.setTrg(eEnum);
		ruleresult.getCreatedEdges().add(ePackage__eEnum____eClassifiers);
		pEnumToEEnum__eEnum____target.setSrc(pEnumToEEnum);
		pEnumToEEnum__eEnum____target.setTrg(eEnum);
		ruleresult.getCreatedEdges().add(pEnumToEEnum__eEnum____target);
		pEnumToEEnum__pEnum____source.setSrc(pEnumToEEnum);
		pEnumToEEnum__pEnum____source.setTrg(pEnum);
		ruleresult.getCreatedEdges().add(pEnumToEEnum__pEnum____source);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		pEnum__graph____graph.setName(pEnum__graph____graph_name_prime);
		graph__pEnum____nodes.setName(graph__pEnum____nodes_name_prime);
		eEnum__ePackage____ePackage.setName(eEnum__ePackage____ePackage_name_prime);
		ePackage__eEnum____eClassifiers.setName(ePackage__eEnum____eClassifiers_name_prime);
		pEnumToEEnum__eEnum____target.setName(pEnumToEEnum__eEnum____target_name_prime);
		pEnumToEEnum__pEnum____source.setName(pEnumToEEnum__pEnum____source_name_prime);
		return new Object[] { ruleresult, pEnum, pEnumToEEnum, eEnum, graph, ePackage, pEnum__graph____graph,
				graph__pEnum____nodes, eEnum__ePackage____ePackage, ePackage__eEnum____eClassifiers,
				pEnumToEEnum__eEnum____target, pEnumToEEnum__pEnum____source };
	}

	public static final void pattern_EnumNodeRule_1_5_registerobjects_expressionBBBBBBBB(EnumNodeRule _this,
			PerformRuleResult ruleresult, EObject pEnum, EObject pEnumToEEnum, EObject eEnum, EObject graphToPackage,
			EObject graph, EObject ePackage) {
		_this.registerObjects_FWD(ruleresult, pEnum, pEnumToEEnum, eEnum, graphToPackage, graph, ePackage);

	}

	public static final PerformRuleResult pattern_EnumNodeRule_1_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_2_1_preparereturnvalue_bindingFB(EnumNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_2_1_preparereturnvalue_blackFBB(EClass eClass,
			EnumNodeRule _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_FWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_2_1_preparereturnvalue_bindingAndBlackFFB(EnumNodeRule _this) {
		Object[] result_pattern_EnumNodeRule_2_1_preparereturnvalue_binding = pattern_EnumNodeRule_2_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_EnumNodeRule_2_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_EnumNodeRule_2_1_preparereturnvalue_binding[0];

			Object[] result_pattern_EnumNodeRule_2_1_preparereturnvalue_black = pattern_EnumNodeRule_2_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_EnumNodeRule_2_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_EnumNodeRule_2_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_2_1_preparereturnvalue_greenBF(EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "EnumNodeRule";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_EnumNodeRule_2_2_corematch_bindingFFB(Match match) {
		EObject _localVariable_0 = match.getObject("pEnum");
		EObject _localVariable_1 = match.getObject("graph");
		EObject tmpPEnum = _localVariable_0;
		EObject tmpGraph = _localVariable_1;
		if (tmpPEnum instanceof PEnum) {
			PEnum pEnum = (PEnum) tmpPEnum;
			if (tmpGraph instanceof ClassGraph) {
				ClassGraph graph = (ClassGraph) tmpGraph;
				return new Object[] { pEnum, graph, match };
			}
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_EnumNodeRule_2_2_corematch_blackBFBFB(PEnum pEnum, ClassGraph graph,
			Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (ClassGraphToEPackage graphToPackage : org.moflon.core.utilities.eMoflonEMFUtil
				.getOppositeReferenceTyped(graph, ClassGraphToEPackage.class, "source")) {
			EPackage ePackage = graphToPackage.getTarget();
			if (ePackage != null) {
				_result.add(new Object[] { pEnum, graphToPackage, graph, ePackage, match });
			}

		}
		return _result;
	}

	public static final Iterable<Object[]> pattern_EnumNodeRule_2_3_findcontext_blackBBBB(PEnum pEnum,
			ClassGraphToEPackage graphToPackage, ClassGraph graph, EPackage ePackage) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (graph.equals(pEnum.getGraph())) {
			if (ePackage.equals(graphToPackage.getTarget())) {
				if (graph.equals(graphToPackage.getSource())) {
					_result.add(new Object[] { pEnum, graphToPackage, graph, ePackage });
				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_2_3_findcontext_greenBBBBFFFFF(PEnum pEnum,
			ClassGraphToEPackage graphToPackage, ClassGraph graph, EPackage ePackage) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		EMoflonEdge pEnum__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pEnum____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graphToPackage__ePackage____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graphToPackage__graph____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String pEnum__graph____graph_name_prime = "graph";
		String graph__pEnum____nodes_name_prime = "nodes";
		String graphToPackage__ePackage____target_name_prime = "target";
		String graphToPackage__graph____source_name_prime = "source";
		isApplicableMatch.getAllContextElements().add(pEnum);
		isApplicableMatch.getAllContextElements().add(graphToPackage);
		isApplicableMatch.getAllContextElements().add(graph);
		isApplicableMatch.getAllContextElements().add(ePackage);
		pEnum__graph____graph.setSrc(pEnum);
		pEnum__graph____graph.setTrg(graph);
		isApplicableMatch.getAllContextElements().add(pEnum__graph____graph);
		graph__pEnum____nodes.setSrc(graph);
		graph__pEnum____nodes.setTrg(pEnum);
		isApplicableMatch.getAllContextElements().add(graph__pEnum____nodes);
		graphToPackage__ePackage____target.setSrc(graphToPackage);
		graphToPackage__ePackage____target.setTrg(ePackage);
		isApplicableMatch.getAllContextElements().add(graphToPackage__ePackage____target);
		graphToPackage__graph____source.setSrc(graphToPackage);
		graphToPackage__graph____source.setTrg(graph);
		isApplicableMatch.getAllContextElements().add(graphToPackage__graph____source);
		pEnum__graph____graph.setName(pEnum__graph____graph_name_prime);
		graph__pEnum____nodes.setName(graph__pEnum____nodes_name_prime);
		graphToPackage__ePackage____target.setName(graphToPackage__ePackage____target_name_prime);
		graphToPackage__graph____source.setName(graphToPackage__graph____source_name_prime);
		return new Object[] { pEnum, graphToPackage, graph, ePackage, isApplicableMatch, pEnum__graph____graph,
				graph__pEnum____nodes, graphToPackage__ePackage____target, graphToPackage__graph____source };
	}

	public static final Object[] pattern_EnumNodeRule_2_4_solveCSP_bindingFBBBBBB(EnumNodeRule _this,
			IsApplicableMatch isApplicableMatch, PEnum pEnum, ClassGraphToEPackage graphToPackage, ClassGraph graph,
			EPackage ePackage) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_FWD(isApplicableMatch, pEnum, graphToPackage, graph,
				ePackage);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, pEnum, graphToPackage, graph, ePackage };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_2_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_EnumNodeRule_2_4_solveCSP_bindingAndBlackFBBBBBB(EnumNodeRule _this,
			IsApplicableMatch isApplicableMatch, PEnum pEnum, ClassGraphToEPackage graphToPackage, ClassGraph graph,
			EPackage ePackage) {
		Object[] result_pattern_EnumNodeRule_2_4_solveCSP_binding = pattern_EnumNodeRule_2_4_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, pEnum, graphToPackage, graph, ePackage);
		if (result_pattern_EnumNodeRule_2_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_EnumNodeRule_2_4_solveCSP_binding[0];

			Object[] result_pattern_EnumNodeRule_2_4_solveCSP_black = pattern_EnumNodeRule_2_4_solveCSP_blackB(csp);
			if (result_pattern_EnumNodeRule_2_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, pEnum, graphToPackage, graph, ePackage };
			}
		}
		return null;
	}

	public static final boolean pattern_EnumNodeRule_2_5_checkCSP_expressionFBB(EnumNodeRule _this, CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_2_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_EnumNodeRule_2_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "EnumNodeRule";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_EnumNodeRule_2_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_10_1_initialbindings_blackBBBB(EnumNodeRule _this, Match match,
			EEnum eEnum, EPackage ePackage) {
		return new Object[] { _this, match, eEnum, ePackage };
	}

	public static final Object[] pattern_EnumNodeRule_10_2_SolveCSP_bindingFBBBB(EnumNodeRule _this, Match match,
			EEnum eEnum, EPackage ePackage) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_BWD(match, eEnum, ePackage);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, eEnum, ePackage };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_10_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_EnumNodeRule_10_2_SolveCSP_bindingAndBlackFBBBB(EnumNodeRule _this,
			Match match, EEnum eEnum, EPackage ePackage) {
		Object[] result_pattern_EnumNodeRule_10_2_SolveCSP_binding = pattern_EnumNodeRule_10_2_SolveCSP_bindingFBBBB(
				_this, match, eEnum, ePackage);
		if (result_pattern_EnumNodeRule_10_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_EnumNodeRule_10_2_SolveCSP_binding[0];

			Object[] result_pattern_EnumNodeRule_10_2_SolveCSP_black = pattern_EnumNodeRule_10_2_SolveCSP_blackB(csp);
			if (result_pattern_EnumNodeRule_10_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, eEnum, ePackage };
			}
		}
		return null;
	}

	public static final boolean pattern_EnumNodeRule_10_3_CheckCSP_expressionFBB(EnumNodeRule _this, CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_10_4_collectelementstobetranslated_blackBBB(Match match,
			EEnum eEnum, EPackage ePackage) {
		return new Object[] { match, eEnum, ePackage };
	}

	public static final Object[] pattern_EnumNodeRule_10_4_collectelementstobetranslated_greenBBBFF(Match match,
			EEnum eEnum, EPackage ePackage) {
		EMoflonEdge eEnum__ePackage____ePackage = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ePackage__eEnum____eClassifiers = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getToBeTranslatedNodes().add(eEnum);
		String eEnum__ePackage____ePackage_name_prime = "ePackage";
		String ePackage__eEnum____eClassifiers_name_prime = "eClassifiers";
		eEnum__ePackage____ePackage.setSrc(eEnum);
		eEnum__ePackage____ePackage.setTrg(ePackage);
		match.getToBeTranslatedEdges().add(eEnum__ePackage____ePackage);
		ePackage__eEnum____eClassifiers.setSrc(ePackage);
		ePackage__eEnum____eClassifiers.setTrg(eEnum);
		match.getToBeTranslatedEdges().add(ePackage__eEnum____eClassifiers);
		eEnum__ePackage____ePackage.setName(eEnum__ePackage____ePackage_name_prime);
		ePackage__eEnum____eClassifiers.setName(ePackage__eEnum____eClassifiers_name_prime);
		return new Object[] { match, eEnum, ePackage, eEnum__ePackage____ePackage, ePackage__eEnum____eClassifiers };
	}

	public static final Object[] pattern_EnumNodeRule_10_5_collectcontextelements_blackBBB(Match match, EEnum eEnum,
			EPackage ePackage) {
		return new Object[] { match, eEnum, ePackage };
	}

	public static final Object[] pattern_EnumNodeRule_10_5_collectcontextelements_greenBB(Match match,
			EPackage ePackage) {
		match.getContextNodes().add(ePackage);
		return new Object[] { match, ePackage };
	}

	public static final void pattern_EnumNodeRule_10_6_registerobjectstomatch_expressionBBBB(EnumNodeRule _this,
			Match match, EEnum eEnum, EPackage ePackage) {
		_this.registerObjectsToMatch_BWD(match, eEnum, ePackage);

	}

	public static final boolean pattern_EnumNodeRule_10_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_EnumNodeRule_10_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_11_1_performtransformation_bindingFFFFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("eEnum");
		EObject _localVariable_1 = isApplicableMatch.getObject("graphToPackage");
		EObject _localVariable_2 = isApplicableMatch.getObject("graph");
		EObject _localVariable_3 = isApplicableMatch.getObject("ePackage");
		EObject tmpEEnum = _localVariable_0;
		EObject tmpGraphToPackage = _localVariable_1;
		EObject tmpGraph = _localVariable_2;
		EObject tmpEPackage = _localVariable_3;
		if (tmpEEnum instanceof EEnum) {
			EEnum eEnum = (EEnum) tmpEEnum;
			if (tmpGraphToPackage instanceof ClassGraphToEPackage) {
				ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) tmpGraphToPackage;
				if (tmpGraph instanceof ClassGraph) {
					ClassGraph graph = (ClassGraph) tmpGraph;
					if (tmpEPackage instanceof EPackage) {
						EPackage ePackage = (EPackage) tmpEPackage;
						return new Object[] { eEnum, graphToPackage, graph, ePackage, isApplicableMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_11_1_performtransformation_blackBBBBFBB(EEnum eEnum,
			ClassGraphToEPackage graphToPackage, ClassGraph graph, EPackage ePackage, EnumNodeRule _this,
			IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { eEnum, graphToPackage, graph, ePackage, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_11_1_performtransformation_bindingAndBlackFFFFFBB(
			EnumNodeRule _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_EnumNodeRule_11_1_performtransformation_binding = pattern_EnumNodeRule_11_1_performtransformation_bindingFFFFB(
				isApplicableMatch);
		if (result_pattern_EnumNodeRule_11_1_performtransformation_binding != null) {
			EEnum eEnum = (EEnum) result_pattern_EnumNodeRule_11_1_performtransformation_binding[0];
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result_pattern_EnumNodeRule_11_1_performtransformation_binding[1];
			ClassGraph graph = (ClassGraph) result_pattern_EnumNodeRule_11_1_performtransformation_binding[2];
			EPackage ePackage = (EPackage) result_pattern_EnumNodeRule_11_1_performtransformation_binding[3];

			Object[] result_pattern_EnumNodeRule_11_1_performtransformation_black = pattern_EnumNodeRule_11_1_performtransformation_blackBBBBFBB(
					eEnum, graphToPackage, graph, ePackage, _this, isApplicableMatch);
			if (result_pattern_EnumNodeRule_11_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_EnumNodeRule_11_1_performtransformation_black[4];

				return new Object[] { eEnum, graphToPackage, graph, ePackage, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_11_1_performtransformation_greenFFBBB(EEnum eEnum,
			ClassGraph graph, CSP csp) {
		PEnum pEnum = LanguageFactory.eINSTANCE.createPEnum();
		PNodeToEClassifier pEnumToEEnum = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		Object _localVariable_0 = csp.getValue("pEnum", "label");
		pEnum.setGraph(graph);
		pEnumToEEnum.setTarget(eEnum);
		pEnumToEEnum.setSource(pEnum);
		String pEnum_label_prime = (String) _localVariable_0;
		pEnum.setLabel(pEnum_label_prime);
		return new Object[] { pEnum, pEnumToEEnum, eEnum, graph, csp };
	}

	public static final Object[] pattern_EnumNodeRule_11_2_collecttranslatedelements_blackBBB(PEnum pEnum,
			PNodeToEClassifier pEnumToEEnum, EEnum eEnum) {
		return new Object[] { pEnum, pEnumToEEnum, eEnum };
	}

	public static final Object[] pattern_EnumNodeRule_11_2_collecttranslatedelements_greenFBBB(PEnum pEnum,
			PNodeToEClassifier pEnumToEEnum, EEnum eEnum) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getCreatedElements().add(pEnum);
		ruleresult.getCreatedLinkElements().add(pEnumToEEnum);
		ruleresult.getTranslatedElements().add(eEnum);
		return new Object[] { ruleresult, pEnum, pEnumToEEnum, eEnum };
	}

	public static final Object[] pattern_EnumNodeRule_11_3_bookkeepingforedges_blackBBBBBBB(
			PerformRuleResult ruleresult, EObject pEnum, EObject pEnumToEEnum, EObject eEnum, EObject graphToPackage,
			EObject graph, EObject ePackage) {
		if (!pEnum.equals(pEnumToEEnum)) {
			if (!eEnum.equals(pEnum)) {
				if (!eEnum.equals(pEnumToEEnum)) {
					if (!eEnum.equals(graphToPackage)) {
						if (!eEnum.equals(graph)) {
							if (!eEnum.equals(ePackage)) {
								if (!graphToPackage.equals(pEnum)) {
									if (!graphToPackage.equals(pEnumToEEnum)) {
										if (!graph.equals(pEnum)) {
											if (!graph.equals(pEnumToEEnum)) {
												if (!graph.equals(graphToPackage)) {
													if (!ePackage.equals(pEnum)) {
														if (!ePackage.equals(pEnumToEEnum)) {
															if (!ePackage.equals(graphToPackage)) {
																if (!ePackage.equals(graph)) {
																	return new Object[] { ruleresult, pEnum,
																			pEnumToEEnum, eEnum, graphToPackage, graph,
																			ePackage };
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

	public static final Object[] pattern_EnumNodeRule_11_3_bookkeepingforedges_greenBBBBBBFFFFFF(
			PerformRuleResult ruleresult, EObject pEnum, EObject pEnumToEEnum, EObject eEnum, EObject graph,
			EObject ePackage) {
		EMoflonEdge pEnum__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pEnum____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge eEnum__ePackage____ePackage = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ePackage__eEnum____eClassifiers = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pEnumToEEnum__eEnum____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pEnumToEEnum__pEnum____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "EnumNodeRule";
		String pEnum__graph____graph_name_prime = "graph";
		String graph__pEnum____nodes_name_prime = "nodes";
		String eEnum__ePackage____ePackage_name_prime = "ePackage";
		String ePackage__eEnum____eClassifiers_name_prime = "eClassifiers";
		String pEnumToEEnum__eEnum____target_name_prime = "target";
		String pEnumToEEnum__pEnum____source_name_prime = "source";
		pEnum__graph____graph.setSrc(pEnum);
		pEnum__graph____graph.setTrg(graph);
		ruleresult.getCreatedEdges().add(pEnum__graph____graph);
		graph__pEnum____nodes.setSrc(graph);
		graph__pEnum____nodes.setTrg(pEnum);
		ruleresult.getCreatedEdges().add(graph__pEnum____nodes);
		eEnum__ePackage____ePackage.setSrc(eEnum);
		eEnum__ePackage____ePackage.setTrg(ePackage);
		ruleresult.getTranslatedEdges().add(eEnum__ePackage____ePackage);
		ePackage__eEnum____eClassifiers.setSrc(ePackage);
		ePackage__eEnum____eClassifiers.setTrg(eEnum);
		ruleresult.getTranslatedEdges().add(ePackage__eEnum____eClassifiers);
		pEnumToEEnum__eEnum____target.setSrc(pEnumToEEnum);
		pEnumToEEnum__eEnum____target.setTrg(eEnum);
		ruleresult.getCreatedEdges().add(pEnumToEEnum__eEnum____target);
		pEnumToEEnum__pEnum____source.setSrc(pEnumToEEnum);
		pEnumToEEnum__pEnum____source.setTrg(pEnum);
		ruleresult.getCreatedEdges().add(pEnumToEEnum__pEnum____source);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		pEnum__graph____graph.setName(pEnum__graph____graph_name_prime);
		graph__pEnum____nodes.setName(graph__pEnum____nodes_name_prime);
		eEnum__ePackage____ePackage.setName(eEnum__ePackage____ePackage_name_prime);
		ePackage__eEnum____eClassifiers.setName(ePackage__eEnum____eClassifiers_name_prime);
		pEnumToEEnum__eEnum____target.setName(pEnumToEEnum__eEnum____target_name_prime);
		pEnumToEEnum__pEnum____source.setName(pEnumToEEnum__pEnum____source_name_prime);
		return new Object[] { ruleresult, pEnum, pEnumToEEnum, eEnum, graph, ePackage, pEnum__graph____graph,
				graph__pEnum____nodes, eEnum__ePackage____ePackage, ePackage__eEnum____eClassifiers,
				pEnumToEEnum__eEnum____target, pEnumToEEnum__pEnum____source };
	}

	public static final void pattern_EnumNodeRule_11_5_registerobjects_expressionBBBBBBBB(EnumNodeRule _this,
			PerformRuleResult ruleresult, EObject pEnum, EObject pEnumToEEnum, EObject eEnum, EObject graphToPackage,
			EObject graph, EObject ePackage) {
		_this.registerObjects_BWD(ruleresult, pEnum, pEnumToEEnum, eEnum, graphToPackage, graph, ePackage);

	}

	public static final PerformRuleResult pattern_EnumNodeRule_11_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_12_1_preparereturnvalue_bindingFB(EnumNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_12_1_preparereturnvalue_blackFBB(EClass eClass,
			EnumNodeRule _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_BWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_12_1_preparereturnvalue_bindingAndBlackFFB(EnumNodeRule _this) {
		Object[] result_pattern_EnumNodeRule_12_1_preparereturnvalue_binding = pattern_EnumNodeRule_12_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_EnumNodeRule_12_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_EnumNodeRule_12_1_preparereturnvalue_binding[0];

			Object[] result_pattern_EnumNodeRule_12_1_preparereturnvalue_black = pattern_EnumNodeRule_12_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_EnumNodeRule_12_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_EnumNodeRule_12_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_12_1_preparereturnvalue_greenBF(EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "EnumNodeRule";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_EnumNodeRule_12_2_corematch_bindingFFB(Match match) {
		EObject _localVariable_0 = match.getObject("eEnum");
		EObject _localVariable_1 = match.getObject("ePackage");
		EObject tmpEEnum = _localVariable_0;
		EObject tmpEPackage = _localVariable_1;
		if (tmpEEnum instanceof EEnum) {
			EEnum eEnum = (EEnum) tmpEEnum;
			if (tmpEPackage instanceof EPackage) {
				EPackage ePackage = (EPackage) tmpEPackage;
				return new Object[] { eEnum, ePackage, match };
			}
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_EnumNodeRule_12_2_corematch_blackBFFBB(EEnum eEnum,
			EPackage ePackage, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (ClassGraphToEPackage graphToPackage : org.moflon.core.utilities.eMoflonEMFUtil
				.getOppositeReferenceTyped(ePackage, ClassGraphToEPackage.class, "target")) {
			ClassGraph graph = graphToPackage.getSource();
			if (graph != null) {
				_result.add(new Object[] { eEnum, graphToPackage, graph, ePackage, match });
			}

		}
		return _result;
	}

	public static final Iterable<Object[]> pattern_EnumNodeRule_12_3_findcontext_blackBBBB(EEnum eEnum,
			ClassGraphToEPackage graphToPackage, ClassGraph graph, EPackage ePackage) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (ePackage.equals(eEnum.getEPackage())) {
			if (ePackage.equals(graphToPackage.getTarget())) {
				if (graph.equals(graphToPackage.getSource())) {
					_result.add(new Object[] { eEnum, graphToPackage, graph, ePackage });
				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_12_3_findcontext_greenBBBBFFFFF(EEnum eEnum,
			ClassGraphToEPackage graphToPackage, ClassGraph graph, EPackage ePackage) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		EMoflonEdge eEnum__ePackage____ePackage = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge ePackage__eEnum____eClassifiers = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graphToPackage__ePackage____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graphToPackage__graph____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String eEnum__ePackage____ePackage_name_prime = "ePackage";
		String ePackage__eEnum____eClassifiers_name_prime = "eClassifiers";
		String graphToPackage__ePackage____target_name_prime = "target";
		String graphToPackage__graph____source_name_prime = "source";
		isApplicableMatch.getAllContextElements().add(eEnum);
		isApplicableMatch.getAllContextElements().add(graphToPackage);
		isApplicableMatch.getAllContextElements().add(graph);
		isApplicableMatch.getAllContextElements().add(ePackage);
		eEnum__ePackage____ePackage.setSrc(eEnum);
		eEnum__ePackage____ePackage.setTrg(ePackage);
		isApplicableMatch.getAllContextElements().add(eEnum__ePackage____ePackage);
		ePackage__eEnum____eClassifiers.setSrc(ePackage);
		ePackage__eEnum____eClassifiers.setTrg(eEnum);
		isApplicableMatch.getAllContextElements().add(ePackage__eEnum____eClassifiers);
		graphToPackage__ePackage____target.setSrc(graphToPackage);
		graphToPackage__ePackage____target.setTrg(ePackage);
		isApplicableMatch.getAllContextElements().add(graphToPackage__ePackage____target);
		graphToPackage__graph____source.setSrc(graphToPackage);
		graphToPackage__graph____source.setTrg(graph);
		isApplicableMatch.getAllContextElements().add(graphToPackage__graph____source);
		eEnum__ePackage____ePackage.setName(eEnum__ePackage____ePackage_name_prime);
		ePackage__eEnum____eClassifiers.setName(ePackage__eEnum____eClassifiers_name_prime);
		graphToPackage__ePackage____target.setName(graphToPackage__ePackage____target_name_prime);
		graphToPackage__graph____source.setName(graphToPackage__graph____source_name_prime);
		return new Object[] { eEnum, graphToPackage, graph, ePackage, isApplicableMatch, eEnum__ePackage____ePackage,
				ePackage__eEnum____eClassifiers, graphToPackage__ePackage____target, graphToPackage__graph____source };
	}

	public static final Object[] pattern_EnumNodeRule_12_4_solveCSP_bindingFBBBBBB(EnumNodeRule _this,
			IsApplicableMatch isApplicableMatch, EEnum eEnum, ClassGraphToEPackage graphToPackage, ClassGraph graph,
			EPackage ePackage) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_BWD(isApplicableMatch, eEnum, graphToPackage, graph,
				ePackage);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, eEnum, graphToPackage, graph, ePackage };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_12_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_EnumNodeRule_12_4_solveCSP_bindingAndBlackFBBBBBB(EnumNodeRule _this,
			IsApplicableMatch isApplicableMatch, EEnum eEnum, ClassGraphToEPackage graphToPackage, ClassGraph graph,
			EPackage ePackage) {
		Object[] result_pattern_EnumNodeRule_12_4_solveCSP_binding = pattern_EnumNodeRule_12_4_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, eEnum, graphToPackage, graph, ePackage);
		if (result_pattern_EnumNodeRule_12_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_EnumNodeRule_12_4_solveCSP_binding[0];

			Object[] result_pattern_EnumNodeRule_12_4_solveCSP_black = pattern_EnumNodeRule_12_4_solveCSP_blackB(csp);
			if (result_pattern_EnumNodeRule_12_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, eEnum, graphToPackage, graph, ePackage };
			}
		}
		return null;
	}

	public static final boolean pattern_EnumNodeRule_12_5_checkCSP_expressionFBB(EnumNodeRule _this, CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_12_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_EnumNodeRule_12_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "EnumNodeRule";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_EnumNodeRule_12_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_20_1_preparereturnvalue_bindingFB(EnumNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_20_1_preparereturnvalue_blackFBBF(EClass __eClass,
			EnumNodeRule _this) {
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

	public static final Object[] pattern_EnumNodeRule_20_1_preparereturnvalue_bindingAndBlackFFBF(EnumNodeRule _this) {
		Object[] result_pattern_EnumNodeRule_20_1_preparereturnvalue_binding = pattern_EnumNodeRule_20_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_EnumNodeRule_20_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_EnumNodeRule_20_1_preparereturnvalue_binding[0];

			Object[] result_pattern_EnumNodeRule_20_1_preparereturnvalue_black = pattern_EnumNodeRule_20_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_EnumNodeRule_20_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_EnumNodeRule_20_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_EnumNodeRule_20_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_20_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_EnumNodeRule_20_2_testcorematchandDECs_blackFFB(
			EMoflonEdge _edge_ePackage) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		EObject tmpEEnum = _edge_ePackage.getSrc();
		if (tmpEEnum instanceof EEnum) {
			EEnum eEnum = (EEnum) tmpEEnum;
			EObject tmpEPackage = _edge_ePackage.getTrg();
			if (tmpEPackage instanceof EPackage) {
				EPackage ePackage = (EPackage) tmpEPackage;
				if (ePackage.equals(eEnum.getEPackage())) {
					_result.add(new Object[] { eEnum, ePackage, _edge_ePackage });
				}
			}

		}

		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_20_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_EnumNodeRule_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
			EnumNodeRule _this, Match match, EEnum eEnum, EPackage ePackage) {
		boolean _localVariable_0 = _this.isAppropriate_BWD(match, eEnum, ePackage);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_EnumNodeRule_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			EnumNodeRule _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_BWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_20_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_20_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_EnumNodeRule_20_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_21_1_preparereturnvalue_bindingFB(EnumNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_21_1_preparereturnvalue_blackFBBF(EClass __eClass,
			EnumNodeRule _this) {
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

	public static final Object[] pattern_EnumNodeRule_21_1_preparereturnvalue_bindingAndBlackFFBF(EnumNodeRule _this) {
		Object[] result_pattern_EnumNodeRule_21_1_preparereturnvalue_binding = pattern_EnumNodeRule_21_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_EnumNodeRule_21_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_EnumNodeRule_21_1_preparereturnvalue_binding[0];

			Object[] result_pattern_EnumNodeRule_21_1_preparereturnvalue_black = pattern_EnumNodeRule_21_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_EnumNodeRule_21_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_EnumNodeRule_21_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_EnumNodeRule_21_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_21_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_EnumNodeRule_21_2_testcorematchandDECs_blackFFB(
			EMoflonEdge _edge_graph) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		EObject tmpPEnum = _edge_graph.getSrc();
		if (tmpPEnum instanceof PEnum) {
			PEnum pEnum = (PEnum) tmpPEnum;
			EObject tmpGraph = _edge_graph.getTrg();
			if (tmpGraph instanceof ClassGraph) {
				ClassGraph graph = (ClassGraph) tmpGraph;
				if (graph.equals(pEnum.getGraph())) {
					_result.add(new Object[] { pEnum, graph, _edge_graph });
				}
			}

		}

		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_21_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_EnumNodeRule_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
			EnumNodeRule _this, Match match, PEnum pEnum, ClassGraph graph) {
		boolean _localVariable_0 = _this.isAppropriate_FWD(match, pEnum, graph);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_EnumNodeRule_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			EnumNodeRule _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_FWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_21_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_21_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_EnumNodeRule_21_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_24_1_prepare_blackB(EnumNodeRule _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_EnumNodeRule_24_1_prepare_greenF() {
		IsApplicableRuleResult result = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		return new Object[] { result };
	}

	public static final Object[] pattern_EnumNodeRule_24_2_matchsrctrgcontext_bindingFFFFBB(Match sourceMatch,
			Match targetMatch) {
		EObject _localVariable_0 = sourceMatch.getObject("pEnum");
		EObject _localVariable_1 = targetMatch.getObject("eEnum");
		EObject _localVariable_2 = sourceMatch.getObject("graph");
		EObject _localVariable_3 = targetMatch.getObject("ePackage");
		EObject tmpPEnum = _localVariable_0;
		EObject tmpEEnum = _localVariable_1;
		EObject tmpGraph = _localVariable_2;
		EObject tmpEPackage = _localVariable_3;
		if (tmpPEnum instanceof PEnum) {
			PEnum pEnum = (PEnum) tmpPEnum;
			if (tmpEEnum instanceof EEnum) {
				EEnum eEnum = (EEnum) tmpEEnum;
				if (tmpGraph instanceof ClassGraph) {
					ClassGraph graph = (ClassGraph) tmpGraph;
					if (tmpEPackage instanceof EPackage) {
						EPackage ePackage = (EPackage) tmpEPackage;
						return new Object[] { pEnum, eEnum, graph, ePackage, sourceMatch, targetMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_24_2_matchsrctrgcontext_blackBBBBBB(PEnum pEnum, EEnum eEnum,
			ClassGraph graph, EPackage ePackage, Match sourceMatch, Match targetMatch) {
		if (!sourceMatch.equals(targetMatch)) {
			return new Object[] { pEnum, eEnum, graph, ePackage, sourceMatch, targetMatch };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_24_2_matchsrctrgcontext_bindingAndBlackFFFFBB(Match sourceMatch,
			Match targetMatch) {
		Object[] result_pattern_EnumNodeRule_24_2_matchsrctrgcontext_binding = pattern_EnumNodeRule_24_2_matchsrctrgcontext_bindingFFFFBB(
				sourceMatch, targetMatch);
		if (result_pattern_EnumNodeRule_24_2_matchsrctrgcontext_binding != null) {
			PEnum pEnum = (PEnum) result_pattern_EnumNodeRule_24_2_matchsrctrgcontext_binding[0];
			EEnum eEnum = (EEnum) result_pattern_EnumNodeRule_24_2_matchsrctrgcontext_binding[1];
			ClassGraph graph = (ClassGraph) result_pattern_EnumNodeRule_24_2_matchsrctrgcontext_binding[2];
			EPackage ePackage = (EPackage) result_pattern_EnumNodeRule_24_2_matchsrctrgcontext_binding[3];

			Object[] result_pattern_EnumNodeRule_24_2_matchsrctrgcontext_black = pattern_EnumNodeRule_24_2_matchsrctrgcontext_blackBBBBBB(
					pEnum, eEnum, graph, ePackage, sourceMatch, targetMatch);
			if (result_pattern_EnumNodeRule_24_2_matchsrctrgcontext_black != null) {

				return new Object[] { pEnum, eEnum, graph, ePackage, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_24_3_solvecsp_bindingFBBBBBBB(EnumNodeRule _this, PEnum pEnum,
			EEnum eEnum, ClassGraph graph, EPackage ePackage, Match sourceMatch, Match targetMatch) {
		CSP _localVariable_4 = _this.isApplicable_solveCsp_CC(pEnum, eEnum, graph, ePackage, sourceMatch, targetMatch);
		CSP csp = _localVariable_4;
		if (csp != null) {
			return new Object[] { csp, _this, pEnum, eEnum, graph, ePackage, sourceMatch, targetMatch };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_24_3_solvecsp_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_EnumNodeRule_24_3_solvecsp_bindingAndBlackFBBBBBBB(EnumNodeRule _this,
			PEnum pEnum, EEnum eEnum, ClassGraph graph, EPackage ePackage, Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_EnumNodeRule_24_3_solvecsp_binding = pattern_EnumNodeRule_24_3_solvecsp_bindingFBBBBBBB(
				_this, pEnum, eEnum, graph, ePackage, sourceMatch, targetMatch);
		if (result_pattern_EnumNodeRule_24_3_solvecsp_binding != null) {
			CSP csp = (CSP) result_pattern_EnumNodeRule_24_3_solvecsp_binding[0];

			Object[] result_pattern_EnumNodeRule_24_3_solvecsp_black = pattern_EnumNodeRule_24_3_solvecsp_blackB(csp);
			if (result_pattern_EnumNodeRule_24_3_solvecsp_black != null) {

				return new Object[] { csp, _this, pEnum, eEnum, graph, ePackage, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final boolean pattern_EnumNodeRule_24_4_checkCSP_expressionFB(CSP csp) {
		boolean _localVariable_0 = csp.check();
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Iterable<Object[]> pattern_EnumNodeRule_24_5_matchcorrcontext_blackFBBBB(ClassGraph graph,
			EPackage ePackage, Match sourceMatch, Match targetMatch) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (!sourceMatch.equals(targetMatch)) {
			for (ClassGraphToEPackage graphToPackage : org.moflon.core.utilities.eMoflonEMFUtil
					.getOppositeReferenceTyped(ePackage, ClassGraphToEPackage.class, "target")) {
				if (graph.equals(graphToPackage.getSource())) {
					_result.add(new Object[] { graphToPackage, graph, ePackage, sourceMatch, targetMatch });
				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_24_5_matchcorrcontext_greenBBBF(
			ClassGraphToEPackage graphToPackage, Match sourceMatch, Match targetMatch) {
		CCMatch ccMatch = RuntimeFactory.eINSTANCE.createCCMatch();
		String ccMatch_ruleName_prime = "EnumNodeRule";
		ccMatch.setSourceMatch(sourceMatch);
		ccMatch.setTargetMatch(targetMatch);
		ccMatch.getAllContextElements().add(graphToPackage);
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { graphToPackage, sourceMatch, targetMatch, ccMatch };
	}

	public static final Object[] pattern_EnumNodeRule_24_6_createcorrespondence_blackBBBBB(PEnum pEnum, EEnum eEnum,
			ClassGraph graph, EPackage ePackage, CCMatch ccMatch) {
		return new Object[] { pEnum, eEnum, graph, ePackage, ccMatch };
	}

	public static final Object[] pattern_EnumNodeRule_24_6_createcorrespondence_greenBFBB(PEnum pEnum, EEnum eEnum,
			CCMatch ccMatch) {
		PNodeToEClassifier pEnumToEEnum = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		pEnumToEEnum.setTarget(eEnum);
		pEnumToEEnum.setSource(pEnum);
		ccMatch.getCreateCorr().add(pEnumToEEnum);
		return new Object[] { pEnum, pEnumToEEnum, eEnum, ccMatch };
	}

	public static final Object[] pattern_EnumNodeRule_24_7_addtoreturnedresult_blackBB(IsApplicableRuleResult result,
			CCMatch ccMatch) {
		return new Object[] { result, ccMatch };
	}

	public static final Object[] pattern_EnumNodeRule_24_7_addtoreturnedresult_greenBB(IsApplicableRuleResult result,
			CCMatch ccMatch) {
		result.getIsApplicableMatch().add(ccMatch);
		boolean result_success_prime = Boolean.valueOf(true);
		String ccMatch_ruleName_prime = "EnumNodeRule";
		result.setSuccess(Boolean.valueOf(result_success_prime));
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { result, ccMatch };
	}

	public static final IsApplicableRuleResult pattern_EnumNodeRule_24_8_expressionFB(IsApplicableRuleResult result) {
		IsApplicableRuleResult _result = result;
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_27_1_matchtggpattern_blackBB(PEnum pEnum, ClassGraph graph) {
		if (graph.equals(pEnum.getGraph())) {
			return new Object[] { pEnum, graph };
		}
		return null;
	}

	public static final boolean pattern_EnumNodeRule_27_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_EnumNodeRule_27_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_28_1_matchtggpattern_blackBB(EEnum eEnum, EPackage ePackage) {
		if (ePackage.equals(eEnum.getEPackage())) {
			return new Object[] { eEnum, ePackage };
		}
		return null;
	}

	public static final boolean pattern_EnumNodeRule_28_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_EnumNodeRule_28_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_29_1_createresult_blackB(EnumNodeRule _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_EnumNodeRule_29_1_createresult_greenFF() {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		ModelgeneratorRuleResult ruleResult = RuntimeFactory.eINSTANCE.createModelgeneratorRuleResult();
		boolean ruleResult_success_prime = false;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		return new Object[] { isApplicableMatch, ruleResult };
	}

	public static final Object[] pattern_EnumNodeRule_29_2_isapplicablecore_black_nac_0BB(
			ModelgeneratorRuleResult ruleResult, ClassGraphToEPackage graphToPackage) {
		if (ruleResult.getCorrObjects().contains(graphToPackage)) {
			return new Object[] { ruleResult, graphToPackage };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_29_2_isapplicablecore_black_nac_1BB(
			ModelgeneratorRuleResult ruleResult, EPackage ePackage) {
		if (ruleResult.getTargetObjects().contains(ePackage)) {
			return new Object[] { ruleResult, ePackage };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_29_2_isapplicablecore_black_nac_2BB(
			ModelgeneratorRuleResult ruleResult, ClassGraph graph) {
		if (ruleResult.getSourceObjects().contains(graph)) {
			return new Object[] { ruleResult, graph };
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_EnumNodeRule_29_2_isapplicablecore_blackFFFFBB(
			RuleEntryContainer ruleEntryContainer, ModelgeneratorRuleResult ruleResult) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (RuleEntryList graphToPackageList : ruleEntryContainer.getRuleEntryList()) {
			for (EObject tmpGraphToPackage : graphToPackageList.getEntryObjects()) {
				if (tmpGraphToPackage instanceof ClassGraphToEPackage) {
					ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) tmpGraphToPackage;
					EPackage ePackage = graphToPackage.getTarget();
					if (ePackage != null) {
						ClassGraph graph = graphToPackage.getSource();
						if (graph != null) {
							if (pattern_EnumNodeRule_29_2_isapplicablecore_black_nac_0BB(ruleResult,
									graphToPackage) == null) {
								if (pattern_EnumNodeRule_29_2_isapplicablecore_black_nac_1BB(ruleResult,
										ePackage) == null) {
									if (pattern_EnumNodeRule_29_2_isapplicablecore_black_nac_2BB(ruleResult,
											graph) == null) {
										_result.add(new Object[] { graphToPackageList, graphToPackage, ePackage, graph,
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

	public static final Object[] pattern_EnumNodeRule_29_3_solveCSP_bindingFBBBBBB(EnumNodeRule _this,
			IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage, ClassGraph graph,
			EPackage ePackage, ModelgeneratorRuleResult ruleResult) {
		CSP _localVariable_0 = _this.generateModel_solveCsp_BWD(isApplicableMatch, graphToPackage, graph, ePackage,
				ruleResult);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, graphToPackage, graph, ePackage, ruleResult };
		}
		return null;
	}

	public static final Object[] pattern_EnumNodeRule_29_3_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_EnumNodeRule_29_3_solveCSP_bindingAndBlackFBBBBBB(EnumNodeRule _this,
			IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage, ClassGraph graph,
			EPackage ePackage, ModelgeneratorRuleResult ruleResult) {
		Object[] result_pattern_EnumNodeRule_29_3_solveCSP_binding = pattern_EnumNodeRule_29_3_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, graphToPackage, graph, ePackage, ruleResult);
		if (result_pattern_EnumNodeRule_29_3_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_EnumNodeRule_29_3_solveCSP_binding[0];

			Object[] result_pattern_EnumNodeRule_29_3_solveCSP_black = pattern_EnumNodeRule_29_3_solveCSP_blackB(csp);
			if (result_pattern_EnumNodeRule_29_3_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, graphToPackage, graph, ePackage, ruleResult };
			}
		}
		return null;
	}

	public static final boolean pattern_EnumNodeRule_29_4_checkCSP_expressionFBB(EnumNodeRule _this, CSP csp) {
		boolean _localVariable_0 = _this.generateModel_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_EnumNodeRule_29_5_checknacs_blackBBB(ClassGraphToEPackage graphToPackage,
			ClassGraph graph, EPackage ePackage) {
		return new Object[] { graphToPackage, graph, ePackage };
	}

	public static final Object[] pattern_EnumNodeRule_29_6_perform_blackBBBB(ClassGraphToEPackage graphToPackage,
			ClassGraph graph, EPackage ePackage, ModelgeneratorRuleResult ruleResult) {
		return new Object[] { graphToPackage, graph, ePackage, ruleResult };
	}

	public static final Object[] pattern_EnumNodeRule_29_6_perform_greenFFFBBBB(ClassGraph graph, EPackage ePackage,
			ModelgeneratorRuleResult ruleResult, CSP csp) {
		PEnum pEnum = LanguageFactory.eINSTANCE.createPEnum();
		PNodeToEClassifier pEnumToEEnum = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		EEnum eEnum = EcoreFactory.eINSTANCE.createEEnum();
		Object _localVariable_0 = csp.getValue("pEnum", "label");
		Object _localVariable_1 = csp.getValue("eEnum", "name");
		boolean ruleResult_success_prime = Boolean.valueOf(true);
		int _localVariable_2 = ruleResult.getIncrementedPerformCount();
		pEnum.setGraph(graph);
		ruleResult.getSourceObjects().add(pEnum);
		pEnumToEEnum.setSource(pEnum);
		ruleResult.getCorrObjects().add(pEnumToEEnum);
		ePackage.getEClassifiers().add(eEnum);
		pEnumToEEnum.setTarget(eEnum);
		ruleResult.getTargetObjects().add(eEnum);
		String pEnum_label_prime = (String) _localVariable_0;
		String eEnum_name_prime = (String) _localVariable_1;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		int ruleResult_performCount_prime = Integer.valueOf(_localVariable_2);
		pEnum.setLabel(pEnum_label_prime);
		eEnum.setName(eEnum_name_prime);
		ruleResult.setPerformCount(Integer.valueOf(ruleResult_performCount_prime));
		return new Object[] { pEnum, pEnumToEEnum, eEnum, graph, ePackage, ruleResult, csp };
	}

	public static final ModelgeneratorRuleResult pattern_EnumNodeRule_29_7_expressionFB(
			ModelgeneratorRuleResult ruleResult) {
		ModelgeneratorRuleResult _result = ruleResult;
		return _result;
	}

	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //EnumNodeRuleImpl
