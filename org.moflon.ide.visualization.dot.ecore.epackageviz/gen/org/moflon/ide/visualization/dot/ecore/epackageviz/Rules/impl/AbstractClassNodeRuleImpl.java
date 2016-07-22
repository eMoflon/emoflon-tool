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

import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.AbstractClassNodeRule;
import org.moflon.ide.visualization.dot.ecore.epackageviz.Rules.RulesPackage;

import org.moflon.ide.visualization.dot.language.ClassGraph;
import org.moflon.ide.visualization.dot.language.LanguageFactory;
import org.moflon.ide.visualization.dot.language.PAbstract;

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
 * An implementation of the model object '<em><b>Abstract Class Node Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class AbstractClassNodeRuleImpl extends AbstractRuleImpl implements AbstractClassNodeRule {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractClassNodeRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulesPackage.eINSTANCE.getAbstractClassNodeRule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_FWD(Match match, PAbstract pAbstract, ClassGraph graph) {
		// initial bindings
		Object[] result1_black = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_0_1_initialbindings_blackBBBB(this, match, pAbstract, graph);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[pAbstract] = " + pAbstract + ", "
					+ "[graph] = " + graph + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_0_2_SolveCSP_bindingAndBlackFBBBB(this, match, pAbstract, graph);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[pAbstract] = " + pAbstract + ", " + "[graph] = "
					+ graph + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_0_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = AbstractClassNodeRuleImpl
					.pattern_AbstractClassNodeRule_0_4_collectelementstobetranslated_blackBBB(match, pAbstract, graph);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[pAbstract] = " + pAbstract + ", "
						+ "[graph] = " + graph + ".");
			}
			AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_0_4_collectelementstobetranslated_greenBBBFF(match,
					pAbstract, graph);
			// EMoflonEdge pAbstract__graph____graph = (EMoflonEdge) result4_green[3];
			// EMoflonEdge graph__pAbstract____nodes = (EMoflonEdge) result4_green[4];

			// collect context elements
			Object[] result5_black = AbstractClassNodeRuleImpl
					.pattern_AbstractClassNodeRule_0_5_collectcontextelements_blackBBB(match, pAbstract, graph);
			if (result5_black == null) {
				throw new RuntimeException(
						"Pattern matching in node [collect context elements] failed." + " Variables: " + "[match] = "
								+ match + ", " + "[pAbstract] = " + pAbstract + ", " + "[graph] = " + graph + ".");
			}
			AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_0_5_collectcontextelements_greenBB(match, graph);

			// register objects to match
			AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_0_6_registerobjectstomatch_expressionBBBB(this,
					match, pAbstract, graph);
			return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_0_7_expressionF();
		} else {
			return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_0_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_FWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_1_1_performtransformation_bindingAndBlackFFFFFBB(this,
						isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result1_bindingAndBlack[0];
		EPackage rootPackage = (EPackage) result1_bindingAndBlack[1];
		PAbstract pAbstract = (PAbstract) result1_bindingAndBlack[2];
		ClassGraph graph = (ClassGraph) result1_bindingAndBlack[3];
		CSP csp = (CSP) result1_bindingAndBlack[4];
		Object[] result1_green = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_1_1_performtransformation_greenBBFFB(rootPackage, pAbstract, csp);
		PNodeToEClassifier pAbstractToEClass = (PNodeToEClassifier) result1_green[2];
		EClass eClass = (EClass) result1_green[3];

		// collect translated elements
		Object[] result2_black = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_1_2_collecttranslatedelements_blackBBB(pAbstract, pAbstractToEClass,
						eClass);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[pAbstract] = " + pAbstract + ", " + "[pAbstractToEClass] = " + pAbstractToEClass + ", "
					+ "[eClass] = " + eClass + ".");
		}
		Object[] result2_green = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_1_2_collecttranslatedelements_greenFBBB(pAbstract, pAbstractToEClass,
						eClass);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_1_3_bookkeepingforedges_blackBBBBBBB(ruleresult, graphToPackage,
						rootPackage, pAbstract, pAbstractToEClass, graph, eClass);
		if (result3_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [bookkeeping for edges] failed." + " Variables: " + "[ruleresult] = "
							+ ruleresult + ", " + "[graphToPackage] = " + graphToPackage + ", " + "[rootPackage] = "
							+ rootPackage + ", " + "[pAbstract] = " + pAbstract + ", " + "[pAbstractToEClass] = "
							+ pAbstractToEClass + ", " + "[graph] = " + graph + ", " + "[eClass] = " + eClass + ".");
		}
		AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_1_3_bookkeepingforedges_greenBBBBBBFFFFFF(ruleresult,
				rootPackage, pAbstract, pAbstractToEClass, graph, eClass);
		// EMoflonEdge pAbstract__graph____graph = (EMoflonEdge) result3_green[6];
		// EMoflonEdge graph__pAbstract____nodes = (EMoflonEdge) result3_green[7];
		// EMoflonEdge pAbstractToEClass__pAbstract____source = (EMoflonEdge) result3_green[8];
		// EMoflonEdge eClass__rootPackage____ePackage = (EMoflonEdge) result3_green[9];
		// EMoflonEdge rootPackage__eClass____eClassifiers = (EMoflonEdge) result3_green[10];
		// EMoflonEdge pAbstractToEClass__eClass____target = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_1_5_registerobjects_expressionBBBBBBBB(this, ruleresult,
				graphToPackage, rootPackage, pAbstract, pAbstractToEClass, graph, eClass);
		return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_1_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_FWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_2_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		// EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_2_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_2_2_corematch_bindingFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		PAbstract pAbstract = (PAbstract) result2_binding[0];
		ClassGraph graph = (ClassGraph) result2_binding[1];
		for (Object[] result2_black : AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_2_2_corematch_blackFFBBB(pAbstract, graph, match)) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result2_black[0];
			EPackage rootPackage = (EPackage) result2_black[1];
			// ForEach find context
			for (Object[] result3_black : AbstractClassNodeRuleImpl
					.pattern_AbstractClassNodeRule_2_3_findcontext_blackBBBB(graphToPackage, rootPackage, pAbstract,
							graph)) {
				Object[] result3_green = AbstractClassNodeRuleImpl
						.pattern_AbstractClassNodeRule_2_3_findcontext_greenBBBBFFFFF(graphToPackage, rootPackage,
								pAbstract, graph);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[4];
				// EMoflonEdge pAbstract__graph____graph = (EMoflonEdge) result3_green[5];
				// EMoflonEdge graph__pAbstract____nodes = (EMoflonEdge) result3_green[6];
				// EMoflonEdge graphToPackage__rootPackage____target = (EMoflonEdge) result3_green[7];
				// EMoflonEdge graphToPackage__graph____source = (EMoflonEdge) result3_green[8];

				// solve CSP
				Object[] result4_bindingAndBlack = AbstractClassNodeRuleImpl
						.pattern_AbstractClassNodeRule_2_4_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch,
								graphToPackage, rootPackage, pAbstract, graph);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[graphToPackage] = " + graphToPackage + ", " + "[rootPackage] = " + rootPackage + ", "
							+ "[pAbstract] = " + pAbstract + ", " + "[graph] = " + graph + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_2_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = AbstractClassNodeRuleImpl
							.pattern_AbstractClassNodeRule_2_6_addmatchtoruleresult_blackBB(ruleresult,
									isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_2_6_addmatchtoruleresult_greenBB(ruleresult,
							isApplicableMatch);

				} else {
				}

			}

		}
		return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_2_7_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void registerObjectsToMatch_FWD(Match match, PAbstract pAbstract, ClassGraph graph) {
		match.registerObject("pAbstract", pAbstract);
		match.registerObject("graph", graph);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isAppropriate_solveCsp_FWD(Match match, PAbstract pAbstract, ClassGraph graph) {// Create CSP
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
			EPackage rootPackage, PAbstract pAbstract, ClassGraph graph) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();
		isApplicableMatch.getAttributeInfo().add(csp);

		// Create literals

		// Create attribute variables
		Variable var_pAbstract_label = CSPFactoryHelper.eINSTANCE.createVariable("pAbstract.label", true, csp);
		var_pAbstract_label.setValue(pAbstract.getLabel());
		var_pAbstract_label.setType("String");

		// Create unbound variables
		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass.name", csp);
		var_eClass_name.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pAbstract_label, var_eClass_name);

		// Snapshot pattern match on which CSP is solved
		isApplicableMatch.registerObject("graphToPackage", graphToPackage);
		isApplicableMatch.registerObject("rootPackage", rootPackage);
		isApplicableMatch.registerObject("pAbstract", pAbstract);
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
			EObject pAbstract, EObject pAbstractToEClass, EObject graph, EObject eClass) {
		ruleresult.registerObject("graphToPackage", graphToPackage);
		ruleresult.registerObject("rootPackage", rootPackage);
		ruleresult.registerObject("pAbstract", pAbstract);
		ruleresult.registerObject("pAbstractToEClass", pAbstractToEClass);
		ruleresult.registerObject("graph", graph);
		ruleresult.registerObject("eClass", eClass);

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkTypes_FWD(Match match) {
		return true && org.moflon.util.eMoflonSDMUtil.getFQN(match.getObject("pAbstract").eClass())
				.equals("language.PAbstract.");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAppropriate_BWD(Match match, EPackage rootPackage, EClass eClass) {
		// initial bindings
		Object[] result1_black = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_10_1_initialbindings_blackBBBB(this, match, rootPackage, eClass);
		if (result1_black == null) {
			throw new RuntimeException("Pattern matching in node [initial bindings] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ", "
					+ "[eClass] = " + eClass + ".");
		}

		// Solve CSP
		Object[] result2_bindingAndBlack = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_10_2_SolveCSP_bindingAndBlackFBBBB(this, match, rootPackage, eClass);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [Solve CSP] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ", "
					+ "[eClass] = " + eClass + ".");
		}
		CSP csp = (CSP) result2_bindingAndBlack[0];
		// Check CSP
		if (AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_10_3_CheckCSP_expressionFBB(this, csp)) {

			// collect elements to be translated
			Object[] result4_black = AbstractClassNodeRuleImpl
					.pattern_AbstractClassNodeRule_10_4_collectelementstobetranslated_blackBBB(match, rootPackage,
							eClass);
			if (result4_black == null) {
				throw new RuntimeException("Pattern matching in node [collect elements to be translated] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ", "
						+ "[eClass] = " + eClass + ".");
			}
			AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_10_4_collectelementstobetranslated_greenBBBFF(match,
					rootPackage, eClass);
			// EMoflonEdge eClass__rootPackage____ePackage = (EMoflonEdge) result4_green[3];
			// EMoflonEdge rootPackage__eClass____eClassifiers = (EMoflonEdge) result4_green[4];

			// collect context elements
			Object[] result5_black = AbstractClassNodeRuleImpl
					.pattern_AbstractClassNodeRule_10_5_collectcontextelements_blackBBB(match, rootPackage, eClass);
			if (result5_black == null) {
				throw new RuntimeException("Pattern matching in node [collect context elements] failed."
						+ " Variables: " + "[match] = " + match + ", " + "[rootPackage] = " + rootPackage + ", "
						+ "[eClass] = " + eClass + ".");
			}
			AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_10_5_collectcontextelements_greenBB(match,
					rootPackage);

			// register objects to match
			AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_10_6_registerobjectstomatch_expressionBBBB(this,
					match, rootPackage, eClass);
			return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_10_7_expressionF();
		} else {
			return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_10_8_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformRuleResult perform_BWD(IsApplicableMatch isApplicableMatch) {
		// perform transformation
		Object[] result1_bindingAndBlack = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_11_1_performtransformation_bindingAndBlackFFFFFBB(this,
						isApplicableMatch);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [perform transformation] failed." + " Variables: "
					+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ".");
		}
		ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result1_bindingAndBlack[0];
		EPackage rootPackage = (EPackage) result1_bindingAndBlack[1];
		ClassGraph graph = (ClassGraph) result1_bindingAndBlack[2];
		EClass eClass = (EClass) result1_bindingAndBlack[3];
		CSP csp = (CSP) result1_bindingAndBlack[4];
		Object[] result1_green = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_11_1_performtransformation_greenFFBBB(graph, eClass, csp);
		PAbstract pAbstract = (PAbstract) result1_green[0];
		PNodeToEClassifier pAbstractToEClass = (PNodeToEClassifier) result1_green[1];

		// collect translated elements
		Object[] result2_black = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_11_2_collecttranslatedelements_blackBBB(pAbstract, pAbstractToEClass,
						eClass);
		if (result2_black == null) {
			throw new RuntimeException("Pattern matching in node [collect translated elements] failed." + " Variables: "
					+ "[pAbstract] = " + pAbstract + ", " + "[pAbstractToEClass] = " + pAbstractToEClass + ", "
					+ "[eClass] = " + eClass + ".");
		}
		Object[] result2_green = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_11_2_collecttranslatedelements_greenFBBB(pAbstract, pAbstractToEClass,
						eClass);
		PerformRuleResult ruleresult = (PerformRuleResult) result2_green[0];

		// bookkeeping for edges
		Object[] result3_black = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_11_3_bookkeepingforedges_blackBBBBBBB(ruleresult, graphToPackage,
						rootPackage, pAbstract, pAbstractToEClass, graph, eClass);
		if (result3_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [bookkeeping for edges] failed." + " Variables: " + "[ruleresult] = "
							+ ruleresult + ", " + "[graphToPackage] = " + graphToPackage + ", " + "[rootPackage] = "
							+ rootPackage + ", " + "[pAbstract] = " + pAbstract + ", " + "[pAbstractToEClass] = "
							+ pAbstractToEClass + ", " + "[graph] = " + graph + ", " + "[eClass] = " + eClass + ".");
		}
		AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_11_3_bookkeepingforedges_greenBBBBBBFFFFFF(ruleresult,
				rootPackage, pAbstract, pAbstractToEClass, graph, eClass);
		// EMoflonEdge pAbstract__graph____graph = (EMoflonEdge) result3_green[6];
		// EMoflonEdge graph__pAbstract____nodes = (EMoflonEdge) result3_green[7];
		// EMoflonEdge pAbstractToEClass__pAbstract____source = (EMoflonEdge) result3_green[8];
		// EMoflonEdge eClass__rootPackage____ePackage = (EMoflonEdge) result3_green[9];
		// EMoflonEdge rootPackage__eClass____eClassifiers = (EMoflonEdge) result3_green[10];
		// EMoflonEdge pAbstractToEClass__eClass____target = (EMoflonEdge) result3_green[11];

		// perform postprocessing story node is empty
		// register objects
		AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_11_5_registerobjects_expressionBBBBBBBB(this,
				ruleresult, graphToPackage, rootPackage, pAbstract, pAbstractToEClass, graph, eClass);
		return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_11_6_expressionFB(ruleresult);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IsApplicableRuleResult isApplicable_BWD(Match match) {
		// prepare return value
		Object[] result1_bindingAndBlack = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_12_1_preparereturnvalue_bindingAndBlackFFB(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass eClass = (EClass) result1_bindingAndBlack[1];
		Object[] result1_green = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_12_1_preparereturnvalue_greenBF(performOperation);
		IsApplicableRuleResult ruleresult = (IsApplicableRuleResult) result1_green[1];

		// ForEach core match
		Object[] result2_binding = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_12_2_corematch_bindingFFB(match);
		if (result2_binding == null) {
			throw new RuntimeException(
					"Binding in node core match failed." + " Variables: " + "[match] = " + match + ".");
		}
		EPackage rootPackage = (EPackage) result2_binding[0];
		eClass = (EClass) result2_binding[1];
		for (Object[] result2_black : AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_12_2_corematch_blackFBFBB(rootPackage, eClass, match)) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result2_black[0];
			ClassGraph graph = (ClassGraph) result2_black[2];
			// ForEach find context
			for (Object[] result3_black : AbstractClassNodeRuleImpl
					.pattern_AbstractClassNodeRule_12_3_findcontext_blackBBBB(graphToPackage, rootPackage, graph,
							eClass)) {
				Object[] result3_green = AbstractClassNodeRuleImpl
						.pattern_AbstractClassNodeRule_12_3_findcontext_greenBBBBFFFFF(graphToPackage, rootPackage,
								graph, eClass);
				IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result3_green[4];
				// EMoflonEdge graphToPackage__rootPackage____target = (EMoflonEdge) result3_green[5];
				// EMoflonEdge eClass__rootPackage____ePackage = (EMoflonEdge) result3_green[6];
				// EMoflonEdge rootPackage__eClass____eClassifiers = (EMoflonEdge) result3_green[7];
				// EMoflonEdge graphToPackage__graph____source = (EMoflonEdge) result3_green[8];

				// solve CSP
				Object[] result4_bindingAndBlack = AbstractClassNodeRuleImpl
						.pattern_AbstractClassNodeRule_12_4_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch,
								graphToPackage, rootPackage, graph, eClass);
				if (result4_bindingAndBlack == null) {
					throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: "
							+ "[this] = " + this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", "
							+ "[graphToPackage] = " + graphToPackage + ", " + "[rootPackage] = " + rootPackage + ", "
							+ "[graph] = " + graph + ", " + "[eClass] = " + eClass + ".");
				}
				CSP csp = (CSP) result4_bindingAndBlack[0];
				// check CSP
				if (AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_12_5_checkCSP_expressionFBB(this, csp)) {

					// add match to rule result
					Object[] result6_black = AbstractClassNodeRuleImpl
							.pattern_AbstractClassNodeRule_12_6_addmatchtoruleresult_blackBB(ruleresult,
									isApplicableMatch);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [add match to rule result] failed."
								+ " Variables: " + "[ruleresult] = " + ruleresult + ", " + "[isApplicableMatch] = "
								+ isApplicableMatch + ".");
					}
					AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_12_6_addmatchtoruleresult_greenBB(
							ruleresult, isApplicableMatch);

				} else {
				}

			}

		}
		return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_12_7_expressionFB(ruleresult);
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
		Variable var_pAbstract_label = CSPFactoryHelper.eINSTANCE.createVariable("pAbstract.label", csp);
		var_pAbstract_label.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pAbstract_label, var_eClass_name);

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
			EObject pAbstract, EObject pAbstractToEClass, EObject graph, EObject eClass) {
		ruleresult.registerObject("graphToPackage", graphToPackage);
		ruleresult.registerObject("rootPackage", rootPackage);
		ruleresult.registerObject("pAbstract", pAbstract);
		ruleresult.registerObject("pAbstractToEClass", pAbstractToEClass);
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
	public EObjectContainer isAppropriate_BWD_EMoflonEdge_5(EMoflonEdge _edge_ePackage) {
		// prepare return value
		Object[] result1_bindingAndBlack = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_20_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_20_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_20_2_testcorematchandDECs_blackFFB(_edge_ePackage)) {
			EPackage rootPackage = (EPackage) result2_black[0];
			EClass eClass = (EClass) result2_black[1];
			Object[] result2_green = AbstractClassNodeRuleImpl
					.pattern_AbstractClassNodeRule_20_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (AbstractClassNodeRuleImpl
					.pattern_AbstractClassNodeRule_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(this,
							match, rootPackage, eClass)) {
				// Ensure that the correct types of elements are matched
				if (AbstractClassNodeRuleImpl
						.pattern_AbstractClassNodeRule_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
								this, match)) {

					// Add match to rule result
					Object[] result5_black = AbstractClassNodeRuleImpl
							.pattern_AbstractClassNodeRule_20_5_Addmatchtoruleresult_blackBBBB(match,
									__performOperation, __result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_20_5_Addmatchtoruleresult_greenBBBB(match,
							__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_20_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObjectContainer isAppropriate_FWD_EMoflonEdge_5(EMoflonEdge _edge_graph) {
		// prepare return value
		Object[] result1_bindingAndBlack = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_21_1_preparereturnvalue_bindingAndBlackFFBF(this);
		if (result1_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [prepare return value] failed." + " Variables: "
					+ "[this] = " + this + ".");
		}
		EOperation __performOperation = (EOperation) result1_bindingAndBlack[0];
		EClass __eClass = (EClass) result1_bindingAndBlack[1];
		EOperation isApplicableCC = (EOperation) result1_bindingAndBlack[3];
		Object[] result1_green = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_21_1_preparereturnvalue_greenF();
		EObjectContainer __result = (EObjectContainer) result1_green[0];

		// ForEach test core match and DECs
		for (Object[] result2_black : AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_21_2_testcorematchandDECs_blackFFB(_edge_graph)) {
			PAbstract pAbstract = (PAbstract) result2_black[0];
			ClassGraph graph = (ClassGraph) result2_black[1];
			Object[] result2_green = AbstractClassNodeRuleImpl
					.pattern_AbstractClassNodeRule_21_2_testcorematchandDECs_greenFB(__eClass);
			Match match = (Match) result2_green[0];

			// bookkeeping with generic isAppropriate method
			if (AbstractClassNodeRuleImpl
					.pattern_AbstractClassNodeRule_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(this,
							match, pAbstract, graph)) {
				// Ensure that the correct types of elements are matched
				if (AbstractClassNodeRuleImpl
						.pattern_AbstractClassNodeRule_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
								this, match)) {

					// Add match to rule result
					Object[] result5_black = AbstractClassNodeRuleImpl
							.pattern_AbstractClassNodeRule_21_5_Addmatchtoruleresult_blackBBBB(match,
									__performOperation, __result, isApplicableCC);
					if (result5_black == null) {
						throw new RuntimeException("Pattern matching in node [Add match to rule result] failed."
								+ " Variables: " + "[match] = " + match + ", " + "[__performOperation] = "
								+ __performOperation + ", " + "[__result] = " + __result + ", " + "[isApplicableCC] = "
								+ isApplicableCC + ".");
					}
					AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_21_5_Addmatchtoruleresult_greenBBBB(match,
							__performOperation, __result, isApplicableCC);

				} else {
				}

			} else {
			}

		}
		return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_21_6_expressionFB(__result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConstraintsRuleResult checkAttributes_FWD(TripleMatch __tripleMatch) {
		AttributeConstraintsRuleResult ruleResult = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE
				.createAttributeConstraintsRuleResult();
		ruleResult.setRule("AbstractClassNodeRule");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		if (!__helper.hasExpectedValue("eClass", "abstract", true, ComparingOperator.EQUAL)) {
			ruleResult.setSuccess(false);
			return ruleResult;
		}

		if (!__helper.hasExpectedValue("eClass", "interface", false, ComparingOperator.EQUAL)) {
			ruleResult.setSuccess(false);
			return ruleResult;
		}

		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass", true, csp);
		var_eClass_name.setValue(__helper.getValue("eClass", "name"));
		var_eClass_name.setType("String");

		Variable var_pAbstract_label = CSPFactoryHelper.eINSTANCE.createVariable("pAbstract", true, csp);
		var_pAbstract_label.setValue(__helper.getValue("pAbstract", "label"));
		var_pAbstract_label.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		eq0.setRuleName("AbstractClassNodeRule");
		eq0.solve(var_pAbstract_label, var_eClass_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			var_eClass_name.setBound(false);
			eq0.solve(var_pAbstract_label, var_eClass_name);
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
		ruleResult.setRule("AbstractClassNodeRule");
		ruleResult.setSuccess(true);

		CSP csp = CspFactory.eINSTANCE.createCSP();

		CheckAttributeHelper __helper = new CheckAttributeHelper(__tripleMatch);

		if (!__helper.hasExpectedValue("eClass", "abstract", true, ComparingOperator.EQUAL)) {
			ruleResult.setSuccess(false);
			return ruleResult;
		}

		if (!__helper.hasExpectedValue("eClass", "interface", false, ComparingOperator.EQUAL)) {
			ruleResult.setSuccess(false);
			return ruleResult;
		}

		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass", true, csp);
		var_eClass_name.setValue(__helper.getValue("eClass", "name"));
		var_eClass_name.setType("String");

		Variable var_pAbstract_label = CSPFactoryHelper.eINSTANCE.createVariable("pAbstract", true, csp);
		var_pAbstract_label.setValue(__helper.getValue("pAbstract", "label"));
		var_pAbstract_label.setType("String");

		Eq eq0 = new Eq();
		csp.getConstraints().add(eq0);

		eq0.setRuleName("AbstractClassNodeRule");
		eq0.solve(var_pAbstract_label, var_eClass_name);

		if (csp.check()) {
			ruleResult.setSuccess(true);
		} else {
			var_pAbstract_label.setBound(false);
			eq0.solve(var_pAbstract_label, var_eClass_name);
			if (csp.check()) {
				ruleResult.setSuccess(true);
				ruleResult.setRequiredChange(true);
				__helper.setValue("pAbstract", "label", var_pAbstract_label.getValue());
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
		Object[] result1_black = AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_24_1_prepare_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [prepare] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_24_1_prepare_greenF();
		IsApplicableRuleResult result = (IsApplicableRuleResult) result1_green[0];

		// match src trg context
		Object[] result2_bindingAndBlack = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_bindingAndBlackFFFFBB(sourceMatch, targetMatch);
		if (result2_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [match src trg context] failed." + " Variables: "
					+ "[sourceMatch] = " + sourceMatch + ", " + "[targetMatch] = " + targetMatch + ".");
		}
		EPackage rootPackage = (EPackage) result2_bindingAndBlack[0];
		PAbstract pAbstract = (PAbstract) result2_bindingAndBlack[1];
		ClassGraph graph = (ClassGraph) result2_bindingAndBlack[2];
		EClass eClass = (EClass) result2_bindingAndBlack[3];

		// solve csp
		Object[] result3_bindingAndBlack = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_24_3_solvecsp_bindingAndBlackFBBBBBBB(this, rootPackage, pAbstract,
						graph, eClass, sourceMatch, targetMatch);
		if (result3_bindingAndBlack == null) {
			throw new RuntimeException("Pattern matching in node [solve csp] failed." + " Variables: " + "[this] = "
					+ this + ", " + "[rootPackage] = " + rootPackage + ", " + "[pAbstract] = " + pAbstract + ", "
					+ "[graph] = " + graph + ", " + "[eClass] = " + eClass + ", " + "[sourceMatch] = " + sourceMatch
					+ ", " + "[targetMatch] = " + targetMatch + ".");
		}
		CSP csp = (CSP) result3_bindingAndBlack[0];
		// check CSP
		if (AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_24_4_checkCSP_expressionFB(csp)) {
			// ForEach match corr context
			for (Object[] result5_black : AbstractClassNodeRuleImpl
					.pattern_AbstractClassNodeRule_24_5_matchcorrcontext_blackFBBBB(rootPackage, graph, sourceMatch,
							targetMatch)) {
				ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result5_black[0];
				Object[] result5_green = AbstractClassNodeRuleImpl
						.pattern_AbstractClassNodeRule_24_5_matchcorrcontext_greenBBBF(graphToPackage, sourceMatch,
								targetMatch);
				CCMatch ccMatch = (CCMatch) result5_green[3];

				// create correspondence
				Object[] result6_black = AbstractClassNodeRuleImpl
						.pattern_AbstractClassNodeRule_24_6_createcorrespondence_blackBBBBB(rootPackage, pAbstract,
								graph, eClass, ccMatch);
				if (result6_black == null) {
					throw new RuntimeException("Pattern matching in node [create correspondence] failed."
							+ " Variables: " + "[rootPackage] = " + rootPackage + ", " + "[pAbstract] = " + pAbstract
							+ ", " + "[graph] = " + graph + ", " + "[eClass] = " + eClass + ", " + "[ccMatch] = "
							+ ccMatch + ".");
				}
				AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_24_6_createcorrespondence_greenBFBB(pAbstract,
						eClass, ccMatch);
				// PNodeToEClassifier pAbstractToEClass = (PNodeToEClassifier) result6_green[1];

				// add to returned result
				Object[] result7_black = AbstractClassNodeRuleImpl
						.pattern_AbstractClassNodeRule_24_7_addtoreturnedresult_blackBB(result, ccMatch);
				if (result7_black == null) {
					throw new RuntimeException("Pattern matching in node [add to returned result] failed."
							+ " Variables: " + "[result] = " + result + ", " + "[ccMatch] = " + ccMatch + ".");
				}
				AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_24_7_addtoreturnedresult_greenBB(result,
						ccMatch);

			}

		} else {
		}
		return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_24_8_expressionFB(result);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSP isApplicable_solveCsp_CC(EPackage rootPackage, PAbstract pAbstract, ClassGraph graph, EClass eClass,
			Match sourceMatch, Match targetMatch) {// Create CSP
		CSP csp = CspFactory.eINSTANCE.createCSP();

		// Create literals

		// Create attribute variables
		Variable var_pAbstract_label = CSPFactoryHelper.eINSTANCE.createVariable("pAbstract.label", true, csp);
		var_pAbstract_label.setValue(pAbstract.getLabel());
		var_pAbstract_label.setType("String");
		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass.name", true, csp);
		var_eClass_name.setValue(eClass.getName());
		var_eClass_name.setType("String");

		// Create unbound variables

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pAbstract_label, var_eClass_name);
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
	public boolean checkDEC_FWD(PAbstract pAbstract, ClassGraph graph) {// match tgg pattern
		Object[] result1_black = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_27_1_matchtggpattern_blackBB(pAbstract, graph);
		if (result1_black != null) {
			return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_27_2_expressionF();
		} else {
			return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_27_3_expressionF();
		}

	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDEC_BWD(EPackage rootPackage, EClass eClass) {// match tgg pattern
		Object[] result1_black = AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_28_1_matchtggpattern_blackBB(rootPackage, eClass);
		if (result1_black != null) {
			AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_28_1_matchtggpattern_greenB(eClass);

			return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_28_2_expressionF();
		} else {
			return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_28_3_expressionF();
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
		Object[] result1_black = AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_29_1_createresult_blackB(this);
		if (result1_black == null) {
			throw new RuntimeException(
					"Pattern matching in node [create result] failed." + " Variables: " + "[this] = " + this + ".");
		}
		Object[] result1_green = AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_29_1_createresult_greenFF();
		IsApplicableMatch isApplicableMatch = (IsApplicableMatch) result1_green[0];
		ModelgeneratorRuleResult ruleResult = (ModelgeneratorRuleResult) result1_green[1];

		// ForEach is applicable core
		for (Object[] result2_black : AbstractClassNodeRuleImpl
				.pattern_AbstractClassNodeRule_29_2_isapplicablecore_blackFFFFBB(ruleEntryContainer, ruleResult)) {
			// RuleEntryList graphToPackageList = (RuleEntryList) result2_black[0];
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result2_black[1];
			EPackage rootPackage = (EPackage) result2_black[2];
			ClassGraph graph = (ClassGraph) result2_black[3];

			// solve CSP
			Object[] result3_bindingAndBlack = AbstractClassNodeRuleImpl
					.pattern_AbstractClassNodeRule_29_3_solveCSP_bindingAndBlackFBBBBBB(this, isApplicableMatch,
							graphToPackage, rootPackage, graph, ruleResult);
			if (result3_bindingAndBlack == null) {
				throw new RuntimeException("Pattern matching in node [solve CSP] failed." + " Variables: " + "[this] = "
						+ this + ", " + "[isApplicableMatch] = " + isApplicableMatch + ", " + "[graphToPackage] = "
						+ graphToPackage + ", " + "[rootPackage] = " + rootPackage + ", " + "[graph] = " + graph + ", "
						+ "[ruleResult] = " + ruleResult + ".");
			}
			CSP csp = (CSP) result3_bindingAndBlack[0];
			// check CSP
			if (AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_29_4_checkCSP_expressionFBB(this, csp)) {
				// check nacs
				Object[] result5_black = AbstractClassNodeRuleImpl
						.pattern_AbstractClassNodeRule_29_5_checknacs_blackBBB(graphToPackage, rootPackage, graph);
				if (result5_black != null) {

					// perform
					Object[] result6_black = AbstractClassNodeRuleImpl
							.pattern_AbstractClassNodeRule_29_6_perform_blackBBBB(graphToPackage, rootPackage, graph,
									ruleResult);
					if (result6_black == null) {
						throw new RuntimeException("Pattern matching in node [perform] failed." + " Variables: "
								+ "[graphToPackage] = " + graphToPackage + ", " + "[rootPackage] = " + rootPackage
								+ ", " + "[graph] = " + graph + ", " + "[ruleResult] = " + ruleResult + ".");
					}
					AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_29_6_perform_greenBFFBFBB(rootPackage,
							graph, ruleResult, csp);
					// PAbstract pAbstract = (PAbstract) result6_green[1];
					// PNodeToEClassifier pAbstractToEClass = (PNodeToEClassifier) result6_green[2];
					// EClass eClass = (EClass) result6_green[4];

				} else {
				}

			} else {
			}

		}
		return AbstractClassNodeRuleImpl.pattern_AbstractClassNodeRule_29_7_expressionFB(ruleResult);
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
		Variable var_pAbstract_label = CSPFactoryHelper.eINSTANCE.createVariable("pAbstract.label", csp);
		var_pAbstract_label.setType("String");
		Variable var_eClass_name = CSPFactoryHelper.eINSTANCE.createVariable("eClass.name", csp);
		var_eClass_name.setType("String");

		// Create constraints
		Eq eq = new Eq();

		csp.getConstraints().add(eq);

		// Solve CSP
		eq.setRuleName("");
		eq.solve(var_pAbstract_label, var_eClass_name);

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
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPROPRIATE_FWD__MATCH_PABSTRACT_CLASSGRAPH:
			return isAppropriate_FWD((Match) arguments.get(0), (PAbstract) arguments.get(1),
					(ClassGraph) arguments.get(2));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___PERFORM_FWD__ISAPPLICABLEMATCH:
			return perform_FWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPLICABLE_FWD__MATCH:
			return isApplicable_FWD((Match) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___REGISTER_OBJECTS_TO_MATCH_FWD__MATCH_PABSTRACT_CLASSGRAPH:
			registerObjectsToMatch_FWD((Match) arguments.get(0), (PAbstract) arguments.get(1),
					(ClassGraph) arguments.get(2));
			return null;
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPROPRIATE_SOLVE_CSP_FWD__MATCH_PABSTRACT_CLASSGRAPH:
			return isAppropriate_solveCsp_FWD((Match) arguments.get(0), (PAbstract) arguments.get(1),
					(ClassGraph) arguments.get(2));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPROPRIATE_CHECK_CSP_FWD__CSP:
			return isAppropriate_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPLICABLE_SOLVE_CSP_FWD__ISAPPLICABLEMATCH_CLASSGRAPHTOEPACKAGE_EPACKAGE_PABSTRACT_CLASSGRAPH:
			return isApplicable_solveCsp_FWD((IsApplicableMatch) arguments.get(0),
					(ClassGraphToEPackage) arguments.get(1), (EPackage) arguments.get(2), (PAbstract) arguments.get(3),
					(ClassGraph) arguments.get(4));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPLICABLE_CHECK_CSP_FWD__CSP:
			return isApplicable_checkCsp_FWD((CSP) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___REGISTER_OBJECTS_FWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_FWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6));
			return null;
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___CHECK_TYPES_FWD__MATCH:
			return checkTypes_FWD((Match) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPROPRIATE_BWD__MATCH_EPACKAGE_ECLASS:
			return isAppropriate_BWD((Match) arguments.get(0), (EPackage) arguments.get(1), (EClass) arguments.get(2));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___PERFORM_BWD__ISAPPLICABLEMATCH:
			return perform_BWD((IsApplicableMatch) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPLICABLE_BWD__MATCH:
			return isApplicable_BWD((Match) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___REGISTER_OBJECTS_TO_MATCH_BWD__MATCH_EPACKAGE_ECLASS:
			registerObjectsToMatch_BWD((Match) arguments.get(0), (EPackage) arguments.get(1),
					(EClass) arguments.get(2));
			return null;
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPROPRIATE_SOLVE_CSP_BWD__MATCH_EPACKAGE_ECLASS:
			return isAppropriate_solveCsp_BWD((Match) arguments.get(0), (EPackage) arguments.get(1),
					(EClass) arguments.get(2));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPROPRIATE_CHECK_CSP_BWD__CSP:
			return isAppropriate_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPLICABLE_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_CLASSGRAPHTOEPACKAGE_EPACKAGE_CLASSGRAPH_ECLASS:
			return isApplicable_solveCsp_BWD((IsApplicableMatch) arguments.get(0),
					(ClassGraphToEPackage) arguments.get(1), (EPackage) arguments.get(2), (ClassGraph) arguments.get(3),
					(EClass) arguments.get(4));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPLICABLE_CHECK_CSP_BWD__CSP:
			return isApplicable_checkCsp_BWD((CSP) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___REGISTER_OBJECTS_BWD__PERFORMRULERESULT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT_EOBJECT:
			registerObjects_BWD((PerformRuleResult) arguments.get(0), (EObject) arguments.get(1),
					(EObject) arguments.get(2), (EObject) arguments.get(3), (EObject) arguments.get(4),
					(EObject) arguments.get(5), (EObject) arguments.get(6));
			return null;
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___CHECK_TYPES_BWD__MATCH:
			return checkTypes_BWD((Match) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPROPRIATE_BWD_EMOFLON_EDGE_5__EMOFLONEDGE:
			return isAppropriate_BWD_EMoflonEdge_5((EMoflonEdge) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPROPRIATE_FWD_EMOFLON_EDGE_5__EMOFLONEDGE:
			return isAppropriate_FWD_EMoflonEdge_5((EMoflonEdge) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___CHECK_ATTRIBUTES_FWD__TRIPLEMATCH:
			return checkAttributes_FWD((TripleMatch) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___CHECK_ATTRIBUTES_BWD__TRIPLEMATCH:
			return checkAttributes_BWD((TripleMatch) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPLICABLE_CC__MATCH_MATCH:
			return isApplicable_CC((Match) arguments.get(0), (Match) arguments.get(1));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPLICABLE_SOLVE_CSP_CC__EPACKAGE_PABSTRACT_CLASSGRAPH_ECLASS_MATCH_MATCH:
			return isApplicable_solveCsp_CC((EPackage) arguments.get(0), (PAbstract) arguments.get(1),
					(ClassGraph) arguments.get(2), (EClass) arguments.get(3), (Match) arguments.get(4),
					(Match) arguments.get(5));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___IS_APPLICABLE_CHECK_CSP_CC__CSP:
			return isApplicable_checkCsp_CC((CSP) arguments.get(0));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___CHECK_DEC_FWD__PABSTRACT_CLASSGRAPH:
			return checkDEC_FWD((PAbstract) arguments.get(0), (ClassGraph) arguments.get(1));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___CHECK_DEC_BWD__EPACKAGE_ECLASS:
			return checkDEC_BWD((EPackage) arguments.get(0), (EClass) arguments.get(1));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___GENERATE_MODEL__RULEENTRYCONTAINER_CLASSGRAPHTOEPACKAGE:
			return generateModel((RuleEntryContainer) arguments.get(0), (ClassGraphToEPackage) arguments.get(1));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___GENERATE_MODEL_SOLVE_CSP_BWD__ISAPPLICABLEMATCH_CLASSGRAPHTOEPACKAGE_EPACKAGE_CLASSGRAPH_MODELGENERATORRULERESULT:
			return generateModel_solveCsp_BWD((IsApplicableMatch) arguments.get(0),
					(ClassGraphToEPackage) arguments.get(1), (EPackage) arguments.get(2), (ClassGraph) arguments.get(3),
					(ModelgeneratorRuleResult) arguments.get(4));
		case RulesPackage.ABSTRACT_CLASS_NODE_RULE___GENERATE_MODEL_CHECK_CSP_BWD__CSP:
			return generateModel_checkCsp_BWD((CSP) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	public static final Object[] pattern_AbstractClassNodeRule_0_1_initialbindings_blackBBBB(
			AbstractClassNodeRule _this, Match match, PAbstract pAbstract, ClassGraph graph) {
		return new Object[] { _this, match, pAbstract, graph };
	}

	public static final Object[] pattern_AbstractClassNodeRule_0_2_SolveCSP_bindingFBBBB(AbstractClassNodeRule _this,
			Match match, PAbstract pAbstract, ClassGraph graph) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_FWD(match, pAbstract, graph);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, pAbstract, graph };
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_0_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_AbstractClassNodeRule_0_2_SolveCSP_bindingAndBlackFBBBB(
			AbstractClassNodeRule _this, Match match, PAbstract pAbstract, ClassGraph graph) {
		Object[] result_pattern_AbstractClassNodeRule_0_2_SolveCSP_binding = pattern_AbstractClassNodeRule_0_2_SolveCSP_bindingFBBBB(
				_this, match, pAbstract, graph);
		if (result_pattern_AbstractClassNodeRule_0_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_AbstractClassNodeRule_0_2_SolveCSP_binding[0];

			Object[] result_pattern_AbstractClassNodeRule_0_2_SolveCSP_black = pattern_AbstractClassNodeRule_0_2_SolveCSP_blackB(
					csp);
			if (result_pattern_AbstractClassNodeRule_0_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, pAbstract, graph };
			}
		}
		return null;
	}

	public static final boolean pattern_AbstractClassNodeRule_0_3_CheckCSP_expressionFBB(AbstractClassNodeRule _this,
			CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_0_4_collectelementstobetranslated_blackBBB(Match match,
			PAbstract pAbstract, ClassGraph graph) {
		return new Object[] { match, pAbstract, graph };
	}

	public static final Object[] pattern_AbstractClassNodeRule_0_4_collectelementstobetranslated_greenBBBFF(Match match,
			PAbstract pAbstract, ClassGraph graph) {
		EMoflonEdge pAbstract__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pAbstract____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		match.getToBeTranslatedNodes().add(pAbstract);
		String pAbstract__graph____graph_name_prime = "graph";
		String graph__pAbstract____nodes_name_prime = "nodes";
		pAbstract__graph____graph.setSrc(pAbstract);
		pAbstract__graph____graph.setTrg(graph);
		match.getToBeTranslatedEdges().add(pAbstract__graph____graph);
		graph__pAbstract____nodes.setSrc(graph);
		graph__pAbstract____nodes.setTrg(pAbstract);
		match.getToBeTranslatedEdges().add(graph__pAbstract____nodes);
		pAbstract__graph____graph.setName(pAbstract__graph____graph_name_prime);
		graph__pAbstract____nodes.setName(graph__pAbstract____nodes_name_prime);
		return new Object[] { match, pAbstract, graph, pAbstract__graph____graph, graph__pAbstract____nodes };
	}

	public static final Object[] pattern_AbstractClassNodeRule_0_5_collectcontextelements_blackBBB(Match match,
			PAbstract pAbstract, ClassGraph graph) {
		return new Object[] { match, pAbstract, graph };
	}

	public static final Object[] pattern_AbstractClassNodeRule_0_5_collectcontextelements_greenBB(Match match,
			ClassGraph graph) {
		match.getContextNodes().add(graph);
		return new Object[] { match, graph };
	}

	public static final void pattern_AbstractClassNodeRule_0_6_registerobjectstomatch_expressionBBBB(
			AbstractClassNodeRule _this, Match match, PAbstract pAbstract, ClassGraph graph) {
		_this.registerObjectsToMatch_FWD(match, pAbstract, graph);

	}

	public static final boolean pattern_AbstractClassNodeRule_0_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_AbstractClassNodeRule_0_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_1_1_performtransformation_bindingFFFFB(
			IsApplicableMatch isApplicableMatch) {
		EObject _localVariable_0 = isApplicableMatch.getObject("graphToPackage");
		EObject _localVariable_1 = isApplicableMatch.getObject("rootPackage");
		EObject _localVariable_2 = isApplicableMatch.getObject("pAbstract");
		EObject _localVariable_3 = isApplicableMatch.getObject("graph");
		EObject tmpGraphToPackage = _localVariable_0;
		EObject tmpRootPackage = _localVariable_1;
		EObject tmpPAbstract = _localVariable_2;
		EObject tmpGraph = _localVariable_3;
		if (tmpGraphToPackage instanceof ClassGraphToEPackage) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) tmpGraphToPackage;
			if (tmpRootPackage instanceof EPackage) {
				EPackage rootPackage = (EPackage) tmpRootPackage;
				if (tmpPAbstract instanceof PAbstract) {
					PAbstract pAbstract = (PAbstract) tmpPAbstract;
					if (tmpGraph instanceof ClassGraph) {
						ClassGraph graph = (ClassGraph) tmpGraph;
						return new Object[] { graphToPackage, rootPackage, pAbstract, graph, isApplicableMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_1_1_performtransformation_blackBBBBFBB(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, PAbstract pAbstract, ClassGraph graph,
			AbstractClassNodeRule _this, IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { graphToPackage, rootPackage, pAbstract, graph, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_1_1_performtransformation_bindingAndBlackFFFFFBB(
			AbstractClassNodeRule _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_AbstractClassNodeRule_1_1_performtransformation_binding = pattern_AbstractClassNodeRule_1_1_performtransformation_bindingFFFFB(
				isApplicableMatch);
		if (result_pattern_AbstractClassNodeRule_1_1_performtransformation_binding != null) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result_pattern_AbstractClassNodeRule_1_1_performtransformation_binding[0];
			EPackage rootPackage = (EPackage) result_pattern_AbstractClassNodeRule_1_1_performtransformation_binding[1];
			PAbstract pAbstract = (PAbstract) result_pattern_AbstractClassNodeRule_1_1_performtransformation_binding[2];
			ClassGraph graph = (ClassGraph) result_pattern_AbstractClassNodeRule_1_1_performtransformation_binding[3];

			Object[] result_pattern_AbstractClassNodeRule_1_1_performtransformation_black = pattern_AbstractClassNodeRule_1_1_performtransformation_blackBBBBFBB(
					graphToPackage, rootPackage, pAbstract, graph, _this, isApplicableMatch);
			if (result_pattern_AbstractClassNodeRule_1_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_AbstractClassNodeRule_1_1_performtransformation_black[4];

				return new Object[] { graphToPackage, rootPackage, pAbstract, graph, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_1_1_performtransformation_greenBBFFB(
			EPackage rootPackage, PAbstract pAbstract, CSP csp) {
		PNodeToEClassifier pAbstractToEClass = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		boolean eClass_abstract_prime = Boolean.valueOf(true);
		boolean eClass_interface_prime = false;
		Object _localVariable_0 = csp.getValue("eClass", "name");
		pAbstractToEClass.setSource(pAbstract);
		rootPackage.getEClassifiers().add(eClass);
		pAbstractToEClass.setTarget(eClass);
		eClass.setAbstract(Boolean.valueOf(eClass_abstract_prime));
		eClass.setInterface(Boolean.valueOf(eClass_interface_prime));
		String eClass_name_prime = (String) _localVariable_0;
		eClass.setName(eClass_name_prime);
		return new Object[] { rootPackage, pAbstract, pAbstractToEClass, eClass, csp };
	}

	public static final Object[] pattern_AbstractClassNodeRule_1_2_collecttranslatedelements_blackBBB(
			PAbstract pAbstract, PNodeToEClassifier pAbstractToEClass, EClass eClass) {
		return new Object[] { pAbstract, pAbstractToEClass, eClass };
	}

	public static final Object[] pattern_AbstractClassNodeRule_1_2_collecttranslatedelements_greenFBBB(
			PAbstract pAbstract, PNodeToEClassifier pAbstractToEClass, EClass eClass) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getTranslatedElements().add(pAbstract);
		ruleresult.getCreatedLinkElements().add(pAbstractToEClass);
		ruleresult.getCreatedElements().add(eClass);
		return new Object[] { ruleresult, pAbstract, pAbstractToEClass, eClass };
	}

	public static final Object[] pattern_AbstractClassNodeRule_1_3_bookkeepingforedges_blackBBBBBBB(
			PerformRuleResult ruleresult, EObject graphToPackage, EObject rootPackage, EObject pAbstract,
			EObject pAbstractToEClass, EObject graph, EObject eClass) {
		if (!graphToPackage.equals(rootPackage)) {
			if (!graphToPackage.equals(pAbstract)) {
				if (!graphToPackage.equals(pAbstractToEClass)) {
					if (!pAbstract.equals(rootPackage)) {
						if (!pAbstract.equals(pAbstractToEClass)) {
							if (!pAbstractToEClass.equals(rootPackage)) {
								if (!graph.equals(graphToPackage)) {
									if (!graph.equals(rootPackage)) {
										if (!graph.equals(pAbstract)) {
											if (!graph.equals(pAbstractToEClass)) {
												if (!eClass.equals(graphToPackage)) {
													if (!eClass.equals(rootPackage)) {
														if (!eClass.equals(pAbstract)) {
															if (!eClass.equals(pAbstractToEClass)) {
																if (!eClass.equals(graph)) {
																	return new Object[] { ruleresult, graphToPackage,
																			rootPackage, pAbstract, pAbstractToEClass,
																			graph, eClass };
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

	public static final Object[] pattern_AbstractClassNodeRule_1_3_bookkeepingforedges_greenBBBBBBFFFFFF(
			PerformRuleResult ruleresult, EObject rootPackage, EObject pAbstract, EObject pAbstractToEClass,
			EObject graph, EObject eClass) {
		EMoflonEdge pAbstract__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pAbstract____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pAbstractToEClass__pAbstract____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge eClass__rootPackage____ePackage = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rootPackage__eClass____eClassifiers = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pAbstractToEClass__eClass____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "AbstractClassNodeRule";
		String pAbstract__graph____graph_name_prime = "graph";
		String graph__pAbstract____nodes_name_prime = "nodes";
		String pAbstractToEClass__pAbstract____source_name_prime = "source";
		String eClass__rootPackage____ePackage_name_prime = "ePackage";
		String rootPackage__eClass____eClassifiers_name_prime = "eClassifiers";
		String pAbstractToEClass__eClass____target_name_prime = "target";
		pAbstract__graph____graph.setSrc(pAbstract);
		pAbstract__graph____graph.setTrg(graph);
		ruleresult.getTranslatedEdges().add(pAbstract__graph____graph);
		graph__pAbstract____nodes.setSrc(graph);
		graph__pAbstract____nodes.setTrg(pAbstract);
		ruleresult.getTranslatedEdges().add(graph__pAbstract____nodes);
		pAbstractToEClass__pAbstract____source.setSrc(pAbstractToEClass);
		pAbstractToEClass__pAbstract____source.setTrg(pAbstract);
		ruleresult.getCreatedEdges().add(pAbstractToEClass__pAbstract____source);
		eClass__rootPackage____ePackage.setSrc(eClass);
		eClass__rootPackage____ePackage.setTrg(rootPackage);
		ruleresult.getCreatedEdges().add(eClass__rootPackage____ePackage);
		rootPackage__eClass____eClassifiers.setSrc(rootPackage);
		rootPackage__eClass____eClassifiers.setTrg(eClass);
		ruleresult.getCreatedEdges().add(rootPackage__eClass____eClassifiers);
		pAbstractToEClass__eClass____target.setSrc(pAbstractToEClass);
		pAbstractToEClass__eClass____target.setTrg(eClass);
		ruleresult.getCreatedEdges().add(pAbstractToEClass__eClass____target);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		pAbstract__graph____graph.setName(pAbstract__graph____graph_name_prime);
		graph__pAbstract____nodes.setName(graph__pAbstract____nodes_name_prime);
		pAbstractToEClass__pAbstract____source.setName(pAbstractToEClass__pAbstract____source_name_prime);
		eClass__rootPackage____ePackage.setName(eClass__rootPackage____ePackage_name_prime);
		rootPackage__eClass____eClassifiers.setName(rootPackage__eClass____eClassifiers_name_prime);
		pAbstractToEClass__eClass____target.setName(pAbstractToEClass__eClass____target_name_prime);
		return new Object[] { ruleresult, rootPackage, pAbstract, pAbstractToEClass, graph, eClass,
				pAbstract__graph____graph, graph__pAbstract____nodes, pAbstractToEClass__pAbstract____source,
				eClass__rootPackage____ePackage, rootPackage__eClass____eClassifiers,
				pAbstractToEClass__eClass____target };
	}

	public static final void pattern_AbstractClassNodeRule_1_5_registerobjects_expressionBBBBBBBB(
			AbstractClassNodeRule _this, PerformRuleResult ruleresult, EObject graphToPackage, EObject rootPackage,
			EObject pAbstract, EObject pAbstractToEClass, EObject graph, EObject eClass) {
		_this.registerObjects_FWD(ruleresult, graphToPackage, rootPackage, pAbstract, pAbstractToEClass, graph, eClass);

	}

	public static final PerformRuleResult pattern_AbstractClassNodeRule_1_6_expressionFB(PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_2_1_preparereturnvalue_bindingFB(
			AbstractClassNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_2_1_preparereturnvalue_blackFBB(EClass eClass,
			AbstractClassNodeRule _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_FWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_2_1_preparereturnvalue_bindingAndBlackFFB(
			AbstractClassNodeRule _this) {
		Object[] result_pattern_AbstractClassNodeRule_2_1_preparereturnvalue_binding = pattern_AbstractClassNodeRule_2_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_AbstractClassNodeRule_2_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_AbstractClassNodeRule_2_1_preparereturnvalue_binding[0];

			Object[] result_pattern_AbstractClassNodeRule_2_1_preparereturnvalue_black = pattern_AbstractClassNodeRule_2_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_AbstractClassNodeRule_2_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_AbstractClassNodeRule_2_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_2_1_preparereturnvalue_greenBF(
			EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "AbstractClassNodeRule";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_AbstractClassNodeRule_2_2_corematch_bindingFFB(Match match) {
		EObject _localVariable_0 = match.getObject("pAbstract");
		EObject _localVariable_1 = match.getObject("graph");
		EObject tmpPAbstract = _localVariable_0;
		EObject tmpGraph = _localVariable_1;
		if (tmpPAbstract instanceof PAbstract) {
			PAbstract pAbstract = (PAbstract) tmpPAbstract;
			if (tmpGraph instanceof ClassGraph) {
				ClassGraph graph = (ClassGraph) tmpGraph;
				return new Object[] { pAbstract, graph, match };
			}
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_AbstractClassNodeRule_2_2_corematch_blackFFBBB(PAbstract pAbstract,
			ClassGraph graph, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		for (ClassGraphToEPackage graphToPackage : org.moflon.core.utilities.eMoflonEMFUtil
				.getOppositeReferenceTyped(graph, ClassGraphToEPackage.class, "source")) {
			EPackage rootPackage = graphToPackage.getTarget();
			if (rootPackage != null) {
				_result.add(new Object[] { graphToPackage, rootPackage, pAbstract, graph, match });
			}

		}
		return _result;
	}

	public static final Iterable<Object[]> pattern_AbstractClassNodeRule_2_3_findcontext_blackBBBB(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, PAbstract pAbstract, ClassGraph graph) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (graph.equals(pAbstract.getGraph())) {
			if (rootPackage.equals(graphToPackage.getTarget())) {
				if (graph.equals(graphToPackage.getSource())) {
					_result.add(new Object[] { graphToPackage, rootPackage, pAbstract, graph });
				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_2_3_findcontext_greenBBBBFFFFF(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, PAbstract pAbstract, ClassGraph graph) {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		EMoflonEdge pAbstract__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pAbstract____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graphToPackage__rootPackage____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graphToPackage__graph____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String pAbstract__graph____graph_name_prime = "graph";
		String graph__pAbstract____nodes_name_prime = "nodes";
		String graphToPackage__rootPackage____target_name_prime = "target";
		String graphToPackage__graph____source_name_prime = "source";
		isApplicableMatch.getAllContextElements().add(graphToPackage);
		isApplicableMatch.getAllContextElements().add(rootPackage);
		isApplicableMatch.getAllContextElements().add(pAbstract);
		isApplicableMatch.getAllContextElements().add(graph);
		pAbstract__graph____graph.setSrc(pAbstract);
		pAbstract__graph____graph.setTrg(graph);
		isApplicableMatch.getAllContextElements().add(pAbstract__graph____graph);
		graph__pAbstract____nodes.setSrc(graph);
		graph__pAbstract____nodes.setTrg(pAbstract);
		isApplicableMatch.getAllContextElements().add(graph__pAbstract____nodes);
		graphToPackage__rootPackage____target.setSrc(graphToPackage);
		graphToPackage__rootPackage____target.setTrg(rootPackage);
		isApplicableMatch.getAllContextElements().add(graphToPackage__rootPackage____target);
		graphToPackage__graph____source.setSrc(graphToPackage);
		graphToPackage__graph____source.setTrg(graph);
		isApplicableMatch.getAllContextElements().add(graphToPackage__graph____source);
		pAbstract__graph____graph.setName(pAbstract__graph____graph_name_prime);
		graph__pAbstract____nodes.setName(graph__pAbstract____nodes_name_prime);
		graphToPackage__rootPackage____target.setName(graphToPackage__rootPackage____target_name_prime);
		graphToPackage__graph____source.setName(graphToPackage__graph____source_name_prime);
		return new Object[] { graphToPackage, rootPackage, pAbstract, graph, isApplicableMatch,
				pAbstract__graph____graph, graph__pAbstract____nodes, graphToPackage__rootPackage____target,
				graphToPackage__graph____source };
	}

	public static final Object[] pattern_AbstractClassNodeRule_2_4_solveCSP_bindingFBBBBBB(AbstractClassNodeRule _this,
			IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage, EPackage rootPackage,
			PAbstract pAbstract, ClassGraph graph) {
		CSP _localVariable_0 = _this.isApplicable_solveCsp_FWD(isApplicableMatch, graphToPackage, rootPackage,
				pAbstract, graph);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, isApplicableMatch, graphToPackage, rootPackage, pAbstract, graph };
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_2_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_AbstractClassNodeRule_2_4_solveCSP_bindingAndBlackFBBBBBB(
			AbstractClassNodeRule _this, IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, PAbstract pAbstract, ClassGraph graph) {
		Object[] result_pattern_AbstractClassNodeRule_2_4_solveCSP_binding = pattern_AbstractClassNodeRule_2_4_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, graphToPackage, rootPackage, pAbstract, graph);
		if (result_pattern_AbstractClassNodeRule_2_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_AbstractClassNodeRule_2_4_solveCSP_binding[0];

			Object[] result_pattern_AbstractClassNodeRule_2_4_solveCSP_black = pattern_AbstractClassNodeRule_2_4_solveCSP_blackB(
					csp);
			if (result_pattern_AbstractClassNodeRule_2_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, graphToPackage, rootPackage, pAbstract, graph };
			}
		}
		return null;
	}

	public static final boolean pattern_AbstractClassNodeRule_2_5_checkCSP_expressionFBB(AbstractClassNodeRule _this,
			CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_FWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_2_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_AbstractClassNodeRule_2_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "AbstractClassNodeRule";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_AbstractClassNodeRule_2_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_10_1_initialbindings_blackBBBB(
			AbstractClassNodeRule _this, Match match, EPackage rootPackage, EClass eClass) {
		return new Object[] { _this, match, rootPackage, eClass };
	}

	public static final Object[] pattern_AbstractClassNodeRule_10_2_SolveCSP_bindingFBBBB(AbstractClassNodeRule _this,
			Match match, EPackage rootPackage, EClass eClass) {
		CSP _localVariable_0 = _this.isAppropriate_solveCsp_BWD(match, rootPackage, eClass);
		CSP csp = _localVariable_0;
		if (csp != null) {
			return new Object[] { csp, _this, match, rootPackage, eClass };
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_10_2_SolveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_AbstractClassNodeRule_10_2_SolveCSP_bindingAndBlackFBBBB(
			AbstractClassNodeRule _this, Match match, EPackage rootPackage, EClass eClass) {
		Object[] result_pattern_AbstractClassNodeRule_10_2_SolveCSP_binding = pattern_AbstractClassNodeRule_10_2_SolveCSP_bindingFBBBB(
				_this, match, rootPackage, eClass);
		if (result_pattern_AbstractClassNodeRule_10_2_SolveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_AbstractClassNodeRule_10_2_SolveCSP_binding[0];

			Object[] result_pattern_AbstractClassNodeRule_10_2_SolveCSP_black = pattern_AbstractClassNodeRule_10_2_SolveCSP_blackB(
					csp);
			if (result_pattern_AbstractClassNodeRule_10_2_SolveCSP_black != null) {

				return new Object[] { csp, _this, match, rootPackage, eClass };
			}
		}
		return null;
	}

	public static final boolean pattern_AbstractClassNodeRule_10_3_CheckCSP_expressionFBB(AbstractClassNodeRule _this,
			CSP csp) {
		boolean _localVariable_0 = _this.isAppropriate_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_10_4_collectelementstobetranslated_blackBBB(Match match,
			EPackage rootPackage, EClass eClass) {
		return new Object[] { match, rootPackage, eClass };
	}

	public static final Object[] pattern_AbstractClassNodeRule_10_4_collectelementstobetranslated_greenBBBFF(
			Match match, EPackage rootPackage, EClass eClass) {
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

	public static final Object[] pattern_AbstractClassNodeRule_10_5_collectcontextelements_blackBBB(Match match,
			EPackage rootPackage, EClass eClass) {
		return new Object[] { match, rootPackage, eClass };
	}

	public static final Object[] pattern_AbstractClassNodeRule_10_5_collectcontextelements_greenBB(Match match,
			EPackage rootPackage) {
		match.getContextNodes().add(rootPackage);
		return new Object[] { match, rootPackage };
	}

	public static final void pattern_AbstractClassNodeRule_10_6_registerobjectstomatch_expressionBBBB(
			AbstractClassNodeRule _this, Match match, EPackage rootPackage, EClass eClass) {
		_this.registerObjectsToMatch_BWD(match, rootPackage, eClass);

	}

	public static final boolean pattern_AbstractClassNodeRule_10_7_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_AbstractClassNodeRule_10_8_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_11_1_performtransformation_bindingFFFFB(
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

	public static final Object[] pattern_AbstractClassNodeRule_11_1_performtransformation_blackBBBBFBB(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph, EClass eClass,
			AbstractClassNodeRule _this, IsApplicableMatch isApplicableMatch) {
		for (EObject tmpCsp : isApplicableMatch.getAttributeInfo()) {
			if (tmpCsp instanceof CSP) {
				CSP csp = (CSP) tmpCsp;
				return new Object[] { graphToPackage, rootPackage, graph, eClass, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_11_1_performtransformation_bindingAndBlackFFFFFBB(
			AbstractClassNodeRule _this, IsApplicableMatch isApplicableMatch) {
		Object[] result_pattern_AbstractClassNodeRule_11_1_performtransformation_binding = pattern_AbstractClassNodeRule_11_1_performtransformation_bindingFFFFB(
				isApplicableMatch);
		if (result_pattern_AbstractClassNodeRule_11_1_performtransformation_binding != null) {
			ClassGraphToEPackage graphToPackage = (ClassGraphToEPackage) result_pattern_AbstractClassNodeRule_11_1_performtransformation_binding[0];
			EPackage rootPackage = (EPackage) result_pattern_AbstractClassNodeRule_11_1_performtransformation_binding[1];
			ClassGraph graph = (ClassGraph) result_pattern_AbstractClassNodeRule_11_1_performtransformation_binding[2];
			EClass eClass = (EClass) result_pattern_AbstractClassNodeRule_11_1_performtransformation_binding[3];

			Object[] result_pattern_AbstractClassNodeRule_11_1_performtransformation_black = pattern_AbstractClassNodeRule_11_1_performtransformation_blackBBBBFBB(
					graphToPackage, rootPackage, graph, eClass, _this, isApplicableMatch);
			if (result_pattern_AbstractClassNodeRule_11_1_performtransformation_black != null) {
				CSP csp = (CSP) result_pattern_AbstractClassNodeRule_11_1_performtransformation_black[4];

				return new Object[] { graphToPackage, rootPackage, graph, eClass, csp, _this, isApplicableMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_11_1_performtransformation_greenFFBBB(ClassGraph graph,
			EClass eClass, CSP csp) {
		PAbstract pAbstract = LanguageFactory.eINSTANCE.createPAbstract();
		PNodeToEClassifier pAbstractToEClass = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		Object _localVariable_0 = csp.getValue("pAbstract", "label");
		pAbstract.setGraph(graph);
		pAbstractToEClass.setSource(pAbstract);
		pAbstractToEClass.setTarget(eClass);
		String pAbstract_label_prime = (String) _localVariable_0;
		pAbstract.setLabel(pAbstract_label_prime);
		return new Object[] { pAbstract, pAbstractToEClass, graph, eClass, csp };
	}

	public static final Object[] pattern_AbstractClassNodeRule_11_2_collecttranslatedelements_blackBBB(
			PAbstract pAbstract, PNodeToEClassifier pAbstractToEClass, EClass eClass) {
		return new Object[] { pAbstract, pAbstractToEClass, eClass };
	}

	public static final Object[] pattern_AbstractClassNodeRule_11_2_collecttranslatedelements_greenFBBB(
			PAbstract pAbstract, PNodeToEClassifier pAbstractToEClass, EClass eClass) {
		PerformRuleResult ruleresult = RuntimeFactory.eINSTANCE.createPerformRuleResult();
		ruleresult.getCreatedElements().add(pAbstract);
		ruleresult.getCreatedLinkElements().add(pAbstractToEClass);
		ruleresult.getTranslatedElements().add(eClass);
		return new Object[] { ruleresult, pAbstract, pAbstractToEClass, eClass };
	}

	public static final Object[] pattern_AbstractClassNodeRule_11_3_bookkeepingforedges_blackBBBBBBB(
			PerformRuleResult ruleresult, EObject graphToPackage, EObject rootPackage, EObject pAbstract,
			EObject pAbstractToEClass, EObject graph, EObject eClass) {
		if (!graphToPackage.equals(rootPackage)) {
			if (!graphToPackage.equals(pAbstract)) {
				if (!graphToPackage.equals(pAbstractToEClass)) {
					if (!pAbstract.equals(rootPackage)) {
						if (!pAbstract.equals(pAbstractToEClass)) {
							if (!pAbstractToEClass.equals(rootPackage)) {
								if (!graph.equals(graphToPackage)) {
									if (!graph.equals(rootPackage)) {
										if (!graph.equals(pAbstract)) {
											if (!graph.equals(pAbstractToEClass)) {
												if (!eClass.equals(graphToPackage)) {
													if (!eClass.equals(rootPackage)) {
														if (!eClass.equals(pAbstract)) {
															if (!eClass.equals(pAbstractToEClass)) {
																if (!eClass.equals(graph)) {
																	return new Object[] { ruleresult, graphToPackage,
																			rootPackage, pAbstract, pAbstractToEClass,
																			graph, eClass };
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

	public static final Object[] pattern_AbstractClassNodeRule_11_3_bookkeepingforedges_greenBBBBBBFFFFFF(
			PerformRuleResult ruleresult, EObject rootPackage, EObject pAbstract, EObject pAbstractToEClass,
			EObject graph, EObject eClass) {
		EMoflonEdge pAbstract__graph____graph = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge graph__pAbstract____nodes = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pAbstractToEClass__pAbstract____source = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge eClass__rootPackage____ePackage = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge rootPackage__eClass____eClassifiers = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		EMoflonEdge pAbstractToEClass__eClass____target = RuntimeFactory.eINSTANCE.createEMoflonEdge();
		String ruleresult_ruleName_prime = "AbstractClassNodeRule";
		String pAbstract__graph____graph_name_prime = "graph";
		String graph__pAbstract____nodes_name_prime = "nodes";
		String pAbstractToEClass__pAbstract____source_name_prime = "source";
		String eClass__rootPackage____ePackage_name_prime = "ePackage";
		String rootPackage__eClass____eClassifiers_name_prime = "eClassifiers";
		String pAbstractToEClass__eClass____target_name_prime = "target";
		pAbstract__graph____graph.setSrc(pAbstract);
		pAbstract__graph____graph.setTrg(graph);
		ruleresult.getCreatedEdges().add(pAbstract__graph____graph);
		graph__pAbstract____nodes.setSrc(graph);
		graph__pAbstract____nodes.setTrg(pAbstract);
		ruleresult.getCreatedEdges().add(graph__pAbstract____nodes);
		pAbstractToEClass__pAbstract____source.setSrc(pAbstractToEClass);
		pAbstractToEClass__pAbstract____source.setTrg(pAbstract);
		ruleresult.getCreatedEdges().add(pAbstractToEClass__pAbstract____source);
		eClass__rootPackage____ePackage.setSrc(eClass);
		eClass__rootPackage____ePackage.setTrg(rootPackage);
		ruleresult.getTranslatedEdges().add(eClass__rootPackage____ePackage);
		rootPackage__eClass____eClassifiers.setSrc(rootPackage);
		rootPackage__eClass____eClassifiers.setTrg(eClass);
		ruleresult.getTranslatedEdges().add(rootPackage__eClass____eClassifiers);
		pAbstractToEClass__eClass____target.setSrc(pAbstractToEClass);
		pAbstractToEClass__eClass____target.setTrg(eClass);
		ruleresult.getCreatedEdges().add(pAbstractToEClass__eClass____target);
		ruleresult.setRuleName(ruleresult_ruleName_prime);
		pAbstract__graph____graph.setName(pAbstract__graph____graph_name_prime);
		graph__pAbstract____nodes.setName(graph__pAbstract____nodes_name_prime);
		pAbstractToEClass__pAbstract____source.setName(pAbstractToEClass__pAbstract____source_name_prime);
		eClass__rootPackage____ePackage.setName(eClass__rootPackage____ePackage_name_prime);
		rootPackage__eClass____eClassifiers.setName(rootPackage__eClass____eClassifiers_name_prime);
		pAbstractToEClass__eClass____target.setName(pAbstractToEClass__eClass____target_name_prime);
		return new Object[] { ruleresult, rootPackage, pAbstract, pAbstractToEClass, graph, eClass,
				pAbstract__graph____graph, graph__pAbstract____nodes, pAbstractToEClass__pAbstract____source,
				eClass__rootPackage____ePackage, rootPackage__eClass____eClassifiers,
				pAbstractToEClass__eClass____target };
	}

	public static final void pattern_AbstractClassNodeRule_11_5_registerobjects_expressionBBBBBBBB(
			AbstractClassNodeRule _this, PerformRuleResult ruleresult, EObject graphToPackage, EObject rootPackage,
			EObject pAbstract, EObject pAbstractToEClass, EObject graph, EObject eClass) {
		_this.registerObjects_BWD(ruleresult, graphToPackage, rootPackage, pAbstract, pAbstractToEClass, graph, eClass);

	}

	public static final PerformRuleResult pattern_AbstractClassNodeRule_11_6_expressionFB(
			PerformRuleResult ruleresult) {
		PerformRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_12_1_preparereturnvalue_bindingFB(
			AbstractClassNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass eClass = _localVariable_0;
		if (eClass != null) {
			return new Object[] { eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_12_1_preparereturnvalue_blackFBB(EClass eClass,
			AbstractClassNodeRule _this) {
		for (EOperation performOperation : eClass.getEOperations()) {
			String performOperation_name = performOperation.getName();
			if (performOperation_name.equals("perform_BWD")) {
				return new Object[] { performOperation, eClass, _this };
			}

		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_12_1_preparereturnvalue_bindingAndBlackFFB(
			AbstractClassNodeRule _this) {
		Object[] result_pattern_AbstractClassNodeRule_12_1_preparereturnvalue_binding = pattern_AbstractClassNodeRule_12_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_AbstractClassNodeRule_12_1_preparereturnvalue_binding != null) {
			EClass eClass = (EClass) result_pattern_AbstractClassNodeRule_12_1_preparereturnvalue_binding[0];

			Object[] result_pattern_AbstractClassNodeRule_12_1_preparereturnvalue_black = pattern_AbstractClassNodeRule_12_1_preparereturnvalue_blackFBB(
					eClass, _this);
			if (result_pattern_AbstractClassNodeRule_12_1_preparereturnvalue_black != null) {
				EOperation performOperation = (EOperation) result_pattern_AbstractClassNodeRule_12_1_preparereturnvalue_black[0];

				return new Object[] { performOperation, eClass, _this };
			}
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_12_1_preparereturnvalue_greenBF(
			EOperation performOperation) {
		IsApplicableRuleResult ruleresult = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		boolean ruleresult_success_prime = false;
		String ruleresult_rule_prime = "AbstractClassNodeRule";
		ruleresult.setPerformOperation(performOperation);
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		ruleresult.setRule(ruleresult_rule_prime);
		return new Object[] { performOperation, ruleresult };
	}

	public static final Object[] pattern_AbstractClassNodeRule_12_2_corematch_bindingFFB(Match match) {
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

	public static final Iterable<Object[]> pattern_AbstractClassNodeRule_12_2_corematch_blackFBFBB(EPackage rootPackage,
			EClass eClass, Match match) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		boolean eClass_abstract = eClass.isAbstract();
		if (Boolean.valueOf(eClass_abstract).equals(Boolean.valueOf(true))) {
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

		}

		return _result;
	}

	public static final Iterable<Object[]> pattern_AbstractClassNodeRule_12_3_findcontext_blackBBBB(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph, EClass eClass) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		if (rootPackage.equals(graphToPackage.getTarget())) {
			if (rootPackage.equals(eClass.getEPackage())) {
				if (graph.equals(graphToPackage.getSource())) {
					boolean eClass_abstract = eClass.isAbstract();
					if (Boolean.valueOf(eClass_abstract).equals(Boolean.valueOf(true))) {
						boolean eClass_interface = eClass.isInterface();
						if (Boolean.valueOf(eClass_interface).equals(false)) {
							_result.add(new Object[] { graphToPackage, rootPackage, graph, eClass });
						}

					}

				}
			}
		}
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_12_3_findcontext_greenBBBBFFFFF(
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

	public static final Object[] pattern_AbstractClassNodeRule_12_4_solveCSP_bindingFBBBBBB(AbstractClassNodeRule _this,
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

	public static final Object[] pattern_AbstractClassNodeRule_12_4_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_AbstractClassNodeRule_12_4_solveCSP_bindingAndBlackFBBBBBB(
			AbstractClassNodeRule _this, IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, ClassGraph graph, EClass eClass) {
		Object[] result_pattern_AbstractClassNodeRule_12_4_solveCSP_binding = pattern_AbstractClassNodeRule_12_4_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, graphToPackage, rootPackage, graph, eClass);
		if (result_pattern_AbstractClassNodeRule_12_4_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_AbstractClassNodeRule_12_4_solveCSP_binding[0];

			Object[] result_pattern_AbstractClassNodeRule_12_4_solveCSP_black = pattern_AbstractClassNodeRule_12_4_solveCSP_blackB(
					csp);
			if (result_pattern_AbstractClassNodeRule_12_4_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, graphToPackage, rootPackage, graph, eClass };
			}
		}
		return null;
	}

	public static final boolean pattern_AbstractClassNodeRule_12_5_checkCSP_expressionFBB(AbstractClassNodeRule _this,
			CSP csp) {
		boolean _localVariable_0 = _this.isApplicable_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_12_6_addmatchtoruleresult_blackBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final Object[] pattern_AbstractClassNodeRule_12_6_addmatchtoruleresult_greenBB(
			IsApplicableRuleResult ruleresult, IsApplicableMatch isApplicableMatch) {
		ruleresult.getIsApplicableMatch().add(isApplicableMatch);
		boolean ruleresult_success_prime = Boolean.valueOf(true);
		String isApplicableMatch_ruleName_prime = "AbstractClassNodeRule";
		ruleresult.setSuccess(Boolean.valueOf(ruleresult_success_prime));
		isApplicableMatch.setRuleName(isApplicableMatch_ruleName_prime);
		return new Object[] { ruleresult, isApplicableMatch };
	}

	public static final IsApplicableRuleResult pattern_AbstractClassNodeRule_12_7_expressionFB(
			IsApplicableRuleResult ruleresult) {
		IsApplicableRuleResult _result = ruleresult;
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_20_1_preparereturnvalue_bindingFB(
			AbstractClassNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_20_1_preparereturnvalue_blackFBBF(EClass __eClass,
			AbstractClassNodeRule _this) {
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

	public static final Object[] pattern_AbstractClassNodeRule_20_1_preparereturnvalue_bindingAndBlackFFBF(
			AbstractClassNodeRule _this) {
		Object[] result_pattern_AbstractClassNodeRule_20_1_preparereturnvalue_binding = pattern_AbstractClassNodeRule_20_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_AbstractClassNodeRule_20_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_AbstractClassNodeRule_20_1_preparereturnvalue_binding[0];

			Object[] result_pattern_AbstractClassNodeRule_20_1_preparereturnvalue_black = pattern_AbstractClassNodeRule_20_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_AbstractClassNodeRule_20_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_AbstractClassNodeRule_20_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_AbstractClassNodeRule_20_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_20_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_AbstractClassNodeRule_20_2_testcorematchandDECs_blackFFB(
			EMoflonEdge _edge_ePackage) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		EObject tmpEClass = _edge_ePackage.getSrc();
		if (tmpEClass instanceof EClass) {
			EClass eClass = (EClass) tmpEClass;
			EObject tmpRootPackage = _edge_ePackage.getTrg();
			if (tmpRootPackage instanceof EPackage) {
				EPackage rootPackage = (EPackage) tmpRootPackage;
				if (rootPackage.equals(eClass.getEPackage())) {
					boolean eClass_abstract = eClass.isAbstract();
					if (Boolean.valueOf(eClass_abstract).equals(Boolean.valueOf(true))) {
						boolean eClass_interface = eClass.isInterface();
						if (Boolean.valueOf(eClass_interface).equals(false)) {
							_result.add(new Object[] { rootPackage, eClass, _edge_ePackage });
						}

					}

				}
			}

		}

		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_20_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_AbstractClassNodeRule_20_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
			AbstractClassNodeRule _this, Match match, EPackage rootPackage, EClass eClass) {
		boolean _localVariable_0 = _this.isAppropriate_BWD(match, rootPackage, eClass);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_AbstractClassNodeRule_20_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			AbstractClassNodeRule _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_BWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_20_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_20_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_AbstractClassNodeRule_20_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_21_1_preparereturnvalue_bindingFB(
			AbstractClassNodeRule _this) {
		EClass _localVariable_0 = _this.eClass();
		EClass __eClass = _localVariable_0;
		if (__eClass != null) {
			return new Object[] { __eClass, _this };
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_21_1_preparereturnvalue_blackFBBF(EClass __eClass,
			AbstractClassNodeRule _this) {
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

	public static final Object[] pattern_AbstractClassNodeRule_21_1_preparereturnvalue_bindingAndBlackFFBF(
			AbstractClassNodeRule _this) {
		Object[] result_pattern_AbstractClassNodeRule_21_1_preparereturnvalue_binding = pattern_AbstractClassNodeRule_21_1_preparereturnvalue_bindingFB(
				_this);
		if (result_pattern_AbstractClassNodeRule_21_1_preparereturnvalue_binding != null) {
			EClass __eClass = (EClass) result_pattern_AbstractClassNodeRule_21_1_preparereturnvalue_binding[0];

			Object[] result_pattern_AbstractClassNodeRule_21_1_preparereturnvalue_black = pattern_AbstractClassNodeRule_21_1_preparereturnvalue_blackFBBF(
					__eClass, _this);
			if (result_pattern_AbstractClassNodeRule_21_1_preparereturnvalue_black != null) {
				EOperation __performOperation = (EOperation) result_pattern_AbstractClassNodeRule_21_1_preparereturnvalue_black[0];
				EOperation isApplicableCC = (EOperation) result_pattern_AbstractClassNodeRule_21_1_preparereturnvalue_black[3];

				return new Object[] { __performOperation, __eClass, _this, isApplicableCC };
			}
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_21_1_preparereturnvalue_greenF() {
		EObjectContainer __result = RuntimeFactory.eINSTANCE.createEObjectContainer();
		return new Object[] { __result };
	}

	public static final Iterable<Object[]> pattern_AbstractClassNodeRule_21_2_testcorematchandDECs_blackFFB(
			EMoflonEdge _edge_graph) {
		LinkedList<Object[]> _result = new LinkedList<Object[]>();
		EObject tmpPAbstract = _edge_graph.getSrc();
		if (tmpPAbstract instanceof PAbstract) {
			PAbstract pAbstract = (PAbstract) tmpPAbstract;
			EObject tmpGraph = _edge_graph.getTrg();
			if (tmpGraph instanceof ClassGraph) {
				ClassGraph graph = (ClassGraph) tmpGraph;
				if (graph.equals(pAbstract.getGraph())) {
					_result.add(new Object[] { pAbstract, graph, _edge_graph });
				}
			}

		}

		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_21_2_testcorematchandDECs_greenFB(EClass __eClass) {
		Match match = RuntimeFactory.eINSTANCE.createMatch();
		String __eClass_name = __eClass.getName();
		String match_ruleName_prime = __eClass_name;
		match.setRuleName(match_ruleName_prime);
		return new Object[] { match, __eClass };

	}

	public static final boolean pattern_AbstractClassNodeRule_21_3_bookkeepingwithgenericisAppropriatemethod_expressionFBBBB(
			AbstractClassNodeRule _this, Match match, PAbstract pAbstract, ClassGraph graph) {
		boolean _localVariable_0 = _this.isAppropriate_FWD(match, pAbstract, graph);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final boolean pattern_AbstractClassNodeRule_21_4_Ensurethatthecorrecttypesofelementsarematched_expressionFBB(
			AbstractClassNodeRule _this, Match match) {
		boolean _localVariable_0 = _this.checkTypes_FWD(match);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_21_5_Addmatchtoruleresult_blackBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		if (!__performOperation.equals(isApplicableCC)) {
			return new Object[] { match, __performOperation, __result, isApplicableCC };
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_21_5_Addmatchtoruleresult_greenBBBB(Match match,
			EOperation __performOperation, EObjectContainer __result, EOperation isApplicableCC) {
		__result.getContents().add(match);
		match.setIsApplicableOperation(__performOperation);
		match.setIsApplicableCCOperation(isApplicableCC);
		return new Object[] { match, __performOperation, __result, isApplicableCC };
	}

	public static final EObjectContainer pattern_AbstractClassNodeRule_21_6_expressionFB(EObjectContainer __result) {
		EObjectContainer _result = __result;
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_24_1_prepare_blackB(AbstractClassNodeRule _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_AbstractClassNodeRule_24_1_prepare_greenF() {
		IsApplicableRuleResult result = RuntimeFactory.eINSTANCE.createIsApplicableRuleResult();
		return new Object[] { result };
	}

	public static final Object[] pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_bindingFFFFBB(Match targetMatch,
			Match sourceMatch) {
		EObject _localVariable_0 = targetMatch.getObject("rootPackage");
		EObject _localVariable_1 = sourceMatch.getObject("pAbstract");
		EObject _localVariable_2 = sourceMatch.getObject("graph");
		EObject _localVariable_3 = targetMatch.getObject("eClass");
		EObject tmpRootPackage = _localVariable_0;
		EObject tmpPAbstract = _localVariable_1;
		EObject tmpGraph = _localVariable_2;
		EObject tmpEClass = _localVariable_3;
		if (tmpRootPackage instanceof EPackage) {
			EPackage rootPackage = (EPackage) tmpRootPackage;
			if (tmpPAbstract instanceof PAbstract) {
				PAbstract pAbstract = (PAbstract) tmpPAbstract;
				if (tmpGraph instanceof ClassGraph) {
					ClassGraph graph = (ClassGraph) tmpGraph;
					if (tmpEClass instanceof EClass) {
						EClass eClass = (EClass) tmpEClass;
						return new Object[] { rootPackage, pAbstract, graph, eClass, targetMatch, sourceMatch };
					}
				}
			}
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_blackBBBBBB(EPackage rootPackage,
			PAbstract pAbstract, ClassGraph graph, EClass eClass, Match sourceMatch, Match targetMatch) {
		if (!sourceMatch.equals(targetMatch)) {
			boolean eClass_abstract = eClass.isAbstract();
			if (Boolean.valueOf(eClass_abstract).equals(Boolean.valueOf(true))) {
				boolean eClass_interface = eClass.isInterface();
				if (Boolean.valueOf(eClass_interface).equals(false)) {
					return new Object[] { rootPackage, pAbstract, graph, eClass, sourceMatch, targetMatch };
				}

			}

		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_bindingAndBlackFFFFBB(
			Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_binding = pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_bindingFFFFBB(
				targetMatch, sourceMatch);
		if (result_pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_binding != null) {
			EPackage rootPackage = (EPackage) result_pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_binding[0];
			PAbstract pAbstract = (PAbstract) result_pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_binding[1];
			ClassGraph graph = (ClassGraph) result_pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_binding[2];
			EClass eClass = (EClass) result_pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_binding[3];

			Object[] result_pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_black = pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_blackBBBBBB(
					rootPackage, pAbstract, graph, eClass, sourceMatch, targetMatch);
			if (result_pattern_AbstractClassNodeRule_24_2_matchsrctrgcontext_black != null) {

				return new Object[] { rootPackage, pAbstract, graph, eClass, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_24_3_solvecsp_bindingFBBBBBBB(
			AbstractClassNodeRule _this, EPackage rootPackage, PAbstract pAbstract, ClassGraph graph, EClass eClass,
			Match sourceMatch, Match targetMatch) {
		CSP _localVariable_4 = _this.isApplicable_solveCsp_CC(rootPackage, pAbstract, graph, eClass, sourceMatch,
				targetMatch);
		CSP csp = _localVariable_4;
		if (csp != null) {
			return new Object[] { csp, _this, rootPackage, pAbstract, graph, eClass, sourceMatch, targetMatch };
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_24_3_solvecsp_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_AbstractClassNodeRule_24_3_solvecsp_bindingAndBlackFBBBBBBB(
			AbstractClassNodeRule _this, EPackage rootPackage, PAbstract pAbstract, ClassGraph graph, EClass eClass,
			Match sourceMatch, Match targetMatch) {
		Object[] result_pattern_AbstractClassNodeRule_24_3_solvecsp_binding = pattern_AbstractClassNodeRule_24_3_solvecsp_bindingFBBBBBBB(
				_this, rootPackage, pAbstract, graph, eClass, sourceMatch, targetMatch);
		if (result_pattern_AbstractClassNodeRule_24_3_solvecsp_binding != null) {
			CSP csp = (CSP) result_pattern_AbstractClassNodeRule_24_3_solvecsp_binding[0];

			Object[] result_pattern_AbstractClassNodeRule_24_3_solvecsp_black = pattern_AbstractClassNodeRule_24_3_solvecsp_blackB(
					csp);
			if (result_pattern_AbstractClassNodeRule_24_3_solvecsp_black != null) {

				return new Object[] { csp, _this, rootPackage, pAbstract, graph, eClass, sourceMatch, targetMatch };
			}
		}
		return null;
	}

	public static final boolean pattern_AbstractClassNodeRule_24_4_checkCSP_expressionFB(CSP csp) {
		boolean _localVariable_0 = csp.check();
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Iterable<Object[]> pattern_AbstractClassNodeRule_24_5_matchcorrcontext_blackFBBBB(
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

	public static final Object[] pattern_AbstractClassNodeRule_24_5_matchcorrcontext_greenBBBF(
			ClassGraphToEPackage graphToPackage, Match sourceMatch, Match targetMatch) {
		CCMatch ccMatch = RuntimeFactory.eINSTANCE.createCCMatch();
		String ccMatch_ruleName_prime = "AbstractClassNodeRule";
		ccMatch.setSourceMatch(sourceMatch);
		ccMatch.setTargetMatch(targetMatch);
		ccMatch.getAllContextElements().add(graphToPackage);
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { graphToPackage, sourceMatch, targetMatch, ccMatch };
	}

	public static final Object[] pattern_AbstractClassNodeRule_24_6_createcorrespondence_blackBBBBB(
			EPackage rootPackage, PAbstract pAbstract, ClassGraph graph, EClass eClass, CCMatch ccMatch) {
		return new Object[] { rootPackage, pAbstract, graph, eClass, ccMatch };
	}

	public static final Object[] pattern_AbstractClassNodeRule_24_6_createcorrespondence_greenBFBB(PAbstract pAbstract,
			EClass eClass, CCMatch ccMatch) {
		PNodeToEClassifier pAbstractToEClass = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		pAbstractToEClass.setSource(pAbstract);
		pAbstractToEClass.setTarget(eClass);
		ccMatch.getCreateCorr().add(pAbstractToEClass);
		return new Object[] { pAbstract, pAbstractToEClass, eClass, ccMatch };
	}

	public static final Object[] pattern_AbstractClassNodeRule_24_7_addtoreturnedresult_blackBB(
			IsApplicableRuleResult result, CCMatch ccMatch) {
		return new Object[] { result, ccMatch };
	}

	public static final Object[] pattern_AbstractClassNodeRule_24_7_addtoreturnedresult_greenBB(
			IsApplicableRuleResult result, CCMatch ccMatch) {
		result.getIsApplicableMatch().add(ccMatch);
		boolean result_success_prime = Boolean.valueOf(true);
		String ccMatch_ruleName_prime = "AbstractClassNodeRule";
		result.setSuccess(Boolean.valueOf(result_success_prime));
		ccMatch.setRuleName(ccMatch_ruleName_prime);
		return new Object[] { result, ccMatch };
	}

	public static final IsApplicableRuleResult pattern_AbstractClassNodeRule_24_8_expressionFB(
			IsApplicableRuleResult result) {
		IsApplicableRuleResult _result = result;
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_27_1_matchtggpattern_blackBB(PAbstract pAbstract,
			ClassGraph graph) {
		if (graph.equals(pAbstract.getGraph())) {
			return new Object[] { pAbstract, graph };
		}
		return null;
	}

	public static final boolean pattern_AbstractClassNodeRule_27_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_AbstractClassNodeRule_27_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_28_1_matchtggpattern_blackBB(EPackage rootPackage,
			EClass eClass) {
		if (rootPackage.equals(eClass.getEPackage())) {
			return new Object[] { rootPackage, eClass };
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_28_1_matchtggpattern_greenB(EClass eClass) {
		boolean eClass_abstract_prime = Boolean.valueOf(true);
		boolean eClass_interface_prime = false;
		eClass.setAbstract(Boolean.valueOf(eClass_abstract_prime));
		eClass.setInterface(Boolean.valueOf(eClass_interface_prime));
		return new Object[] { eClass };
	}

	public static final boolean pattern_AbstractClassNodeRule_28_2_expressionF() {
		boolean _result = Boolean.valueOf(true);
		return _result;
	}

	public static final boolean pattern_AbstractClassNodeRule_28_3_expressionF() {
		boolean _result = false;
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_29_1_createresult_blackB(AbstractClassNodeRule _this) {
		return new Object[] { _this };
	}

	public static final Object[] pattern_AbstractClassNodeRule_29_1_createresult_greenFF() {
		IsApplicableMatch isApplicableMatch = RuntimeFactory.eINSTANCE.createIsApplicableMatch();
		ModelgeneratorRuleResult ruleResult = RuntimeFactory.eINSTANCE.createModelgeneratorRuleResult();
		boolean ruleResult_success_prime = false;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		return new Object[] { isApplicableMatch, ruleResult };
	}

	public static final Object[] pattern_AbstractClassNodeRule_29_2_isapplicablecore_black_nac_0BB(
			ModelgeneratorRuleResult ruleResult, ClassGraphToEPackage graphToPackage) {
		if (ruleResult.getCorrObjects().contains(graphToPackage)) {
			return new Object[] { ruleResult, graphToPackage };
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_29_2_isapplicablecore_black_nac_1BB(
			ModelgeneratorRuleResult ruleResult, EPackage rootPackage) {
		if (ruleResult.getTargetObjects().contains(rootPackage)) {
			return new Object[] { ruleResult, rootPackage };
		}
		return null;
	}

	public static final Object[] pattern_AbstractClassNodeRule_29_2_isapplicablecore_black_nac_2BB(
			ModelgeneratorRuleResult ruleResult, ClassGraph graph) {
		if (ruleResult.getSourceObjects().contains(graph)) {
			return new Object[] { ruleResult, graph };
		}
		return null;
	}

	public static final Iterable<Object[]> pattern_AbstractClassNodeRule_29_2_isapplicablecore_blackFFFFBB(
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
							if (pattern_AbstractClassNodeRule_29_2_isapplicablecore_black_nac_0BB(ruleResult,
									graphToPackage) == null) {
								if (pattern_AbstractClassNodeRule_29_2_isapplicablecore_black_nac_1BB(ruleResult,
										rootPackage) == null) {
									if (pattern_AbstractClassNodeRule_29_2_isapplicablecore_black_nac_2BB(ruleResult,
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

	public static final Object[] pattern_AbstractClassNodeRule_29_3_solveCSP_bindingFBBBBBB(AbstractClassNodeRule _this,
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

	public static final Object[] pattern_AbstractClassNodeRule_29_3_solveCSP_blackB(CSP csp) {
		return new Object[] { csp };
	}

	public static final Object[] pattern_AbstractClassNodeRule_29_3_solveCSP_bindingAndBlackFBBBBBB(
			AbstractClassNodeRule _this, IsApplicableMatch isApplicableMatch, ClassGraphToEPackage graphToPackage,
			EPackage rootPackage, ClassGraph graph, ModelgeneratorRuleResult ruleResult) {
		Object[] result_pattern_AbstractClassNodeRule_29_3_solveCSP_binding = pattern_AbstractClassNodeRule_29_3_solveCSP_bindingFBBBBBB(
				_this, isApplicableMatch, graphToPackage, rootPackage, graph, ruleResult);
		if (result_pattern_AbstractClassNodeRule_29_3_solveCSP_binding != null) {
			CSP csp = (CSP) result_pattern_AbstractClassNodeRule_29_3_solveCSP_binding[0];

			Object[] result_pattern_AbstractClassNodeRule_29_3_solveCSP_black = pattern_AbstractClassNodeRule_29_3_solveCSP_blackB(
					csp);
			if (result_pattern_AbstractClassNodeRule_29_3_solveCSP_black != null) {

				return new Object[] { csp, _this, isApplicableMatch, graphToPackage, rootPackage, graph, ruleResult };
			}
		}
		return null;
	}

	public static final boolean pattern_AbstractClassNodeRule_29_4_checkCSP_expressionFBB(AbstractClassNodeRule _this,
			CSP csp) {
		boolean _localVariable_0 = _this.generateModel_checkCsp_BWD(csp);
		boolean _result = Boolean.valueOf(_localVariable_0);
		return _result;
	}

	public static final Object[] pattern_AbstractClassNodeRule_29_5_checknacs_blackBBB(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph) {
		return new Object[] { graphToPackage, rootPackage, graph };
	}

	public static final Object[] pattern_AbstractClassNodeRule_29_6_perform_blackBBBB(
			ClassGraphToEPackage graphToPackage, EPackage rootPackage, ClassGraph graph,
			ModelgeneratorRuleResult ruleResult) {
		return new Object[] { graphToPackage, rootPackage, graph, ruleResult };
	}

	public static final Object[] pattern_AbstractClassNodeRule_29_6_perform_greenBFFBFBB(EPackage rootPackage,
			ClassGraph graph, ModelgeneratorRuleResult ruleResult, CSP csp) {
		PAbstract pAbstract = LanguageFactory.eINSTANCE.createPAbstract();
		PNodeToEClassifier pAbstractToEClass = EpackagevizFactory.eINSTANCE.createPNodeToEClassifier();
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		Object _localVariable_0 = csp.getValue("pAbstract", "label");
		boolean eClass_abstract_prime = Boolean.valueOf(true);
		boolean eClass_interface_prime = false;
		Object _localVariable_1 = csp.getValue("eClass", "name");
		boolean ruleResult_success_prime = Boolean.valueOf(true);
		int _localVariable_2 = ruleResult.getIncrementedPerformCount();
		pAbstract.setGraph(graph);
		ruleResult.getSourceObjects().add(pAbstract);
		pAbstractToEClass.setSource(pAbstract);
		ruleResult.getCorrObjects().add(pAbstractToEClass);
		rootPackage.getEClassifiers().add(eClass);
		pAbstractToEClass.setTarget(eClass);
		ruleResult.getTargetObjects().add(eClass);
		String pAbstract_label_prime = (String) _localVariable_0;
		eClass.setAbstract(Boolean.valueOf(eClass_abstract_prime));
		eClass.setInterface(Boolean.valueOf(eClass_interface_prime));
		String eClass_name_prime = (String) _localVariable_1;
		ruleResult.setSuccess(Boolean.valueOf(ruleResult_success_prime));
		int ruleResult_performCount_prime = Integer.valueOf(_localVariable_2);
		pAbstract.setLabel(pAbstract_label_prime);
		eClass.setName(eClass_name_prime);
		ruleResult.setPerformCount(Integer.valueOf(ruleResult_performCount_prime));
		return new Object[] { rootPackage, pAbstract, pAbstractToEClass, graph, eClass, ruleResult, csp };
	}

	public static final ModelgeneratorRuleResult pattern_AbstractClassNodeRule_29_7_expressionFB(
			ModelgeneratorRuleResult ruleResult) {
		ModelgeneratorRuleResult _result = ruleResult;
		return _result;
	}

	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //AbstractClassNodeRuleImpl
