package org.moflon.tgg.mosl.codeadapter.org.moflon.tie;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tgg.language.Domain;
import org.moflon.tgg.language.DomainType;
import org.moflon.tgg.language.Metamodel;
import org.moflon.tgg.language.TGGLinkVariable;
import org.moflon.tgg.language.TGGObjectVariable;
import org.moflon.tgg.language.TripleGraphGrammar;
import org.moflon.tgg.language.csp.TGGConstraint;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.mosl.codeadapter.AttrCondToTGGConstraint;
import org.moflon.tgg.mosl.codeadapter.AttributeAssignmentToAttributeAssignment;
import org.moflon.tgg.mosl.codeadapter.AttributeConstraintToConstraint;
import org.moflon.tgg.mosl.codeadapter.CorrTypeToEClass;
import org.moflon.tgg.mosl.codeadapter.CorrVariablePatternToTGGObjectVariable;
import org.moflon.tgg.mosl.codeadapter.EnumExpressionToLiteralExpression;
import org.moflon.tgg.mosl.codeadapter.ExpressionToExpression;
import org.moflon.tgg.mosl.codeadapter.LinkVariablePatternToTGGLinkVariable;
import org.moflon.tgg.mosl.codeadapter.ObjectVariablePatternToTGGObjectVariable;
import org.moflon.tgg.mosl.codeadapter.ParamValueToVariable;
import org.moflon.tgg.mosl.codeadapter.TripleGraphGrammarFileToTripleGraphGrammar;
import org.moflon.tgg.mosl.tgg.AttrCond;
import org.moflon.tgg.mosl.tgg.AttributeExpression;
import org.moflon.tgg.mosl.tgg.CorrType;
import org.moflon.tgg.mosl.tgg.EnumExpression;
import org.moflon.tgg.mosl.tgg.ParamValue;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.tgg.runtime.RuntimePackage;
import org.moflon.tgg.tggproject.TGGProject;

import SDMLanguage.expressions.ComparisonExpression;
import SDMLanguage.expressions.Expression;
import SDMLanguage.expressions.LiteralExpression;
import SDMLanguage.patterns.LinkVariable;
import SDMLanguage.patterns.patternExpressions.AttributeValueExpression;

public class CodeadapterPostProcessForwardHelper {

	public void postProcessForward(CodeadapterTrafo trafoHelper) {
		CorrespondenceModel corrModel = trafoHelper.getCorr();
		if (trafoHelper.getTrg() != null) {
			EPackage corrPackage = ((TGGProject) trafoHelper.getTrg()).getCorrPackage();

			for (EObject corr : corrModel.getCorrespondences()) {

				if (corr instanceof TripleGraphGrammarFileToTripleGraphGrammar)
					postProcessForward_TripleGraphGrammarRoot((TripleGraphGrammarFileToTripleGraphGrammar) corr,
							corrPackage);

				if (corr instanceof CorrTypeToEClass)
					postProcessForward_AbstractCorrespondenceSubClass((CorrTypeToEClass) corr);

				if (corr instanceof ObjectVariablePatternToTGGObjectVariable)
					postProcessForward_TGGObjectVariable((ObjectVariablePatternToTGGObjectVariable) corr);

				if (corr instanceof CorrVariablePatternToTGGObjectVariable)
					postProcessForward_TGGObjectVariable((CorrVariablePatternToTGGObjectVariable) corr, corrPackage);

				if (corr instanceof LinkVariablePatternToTGGLinkVariable)
					postProcessForward_TGGLinkVariable((LinkVariablePatternToTGGLinkVariable) corr);

				if (corr instanceof AttributeAssignmentToAttributeAssignment)
					postProcessForward_AttributeAssignment((AttributeAssignmentToAttributeAssignment) corr);

				if (corr instanceof AttributeConstraintToConstraint)
					postProcessForward_AttributeConstraint((AttributeConstraintToConstraint) corr);

				if (corr instanceof ExpressionToExpression)
					postProcessForward_Expression((ExpressionToExpression) corr);

				if (corr instanceof EnumExpressionToLiteralExpression)
					postProcessForward_TGGEnumExpression((EnumExpressionToLiteralExpression) corr);

				if (corr instanceof AttrCondToTGGConstraint)
					postProcessForward_TGGConstraint((AttrCondToTGGConstraint) corr);
			}
		}
	}

	private void postProcessForward_TGGConstraint(AttrCondToTGGConstraint corr) {
		AttrCond attrCond = corr.getSource();
		TGGConstraint tggConstraint = corr.getTarget();

		// Enforce the same order of variables
		for (int i = 0; i < attrCond.getValues().size(); i++) {
			ParamValue pVal = attrCond.getValues().get(i);
			Collection<ParamValueToVariable> corrs = eMoflonEMFUtil.getOppositeReferenceTyped(pVal,
					ParamValueToVariable.class, "source");
			if (corrs.size() != 1)
				throw new IllegalStateException("There must be exactly one correspondence for " + pVal);
			Variable var = corrs.stream().findAny().get().getTarget();
			tggConstraint.getVariables().move(i, var);
		}
	}

	private void postProcessForward_TGGEnumExpression(EnumExpressionToLiteralExpression corr) {
		EnumExpression enumExp = (EnumExpression) corr.getSource();
		LiteralExpression exp = (LiteralExpression) corr.getTarget();

		EEnum eenum = enumExp.getEenum();
		EEnumLiteral literal = enumExp.getLiteral();

		String fqn = eenum.getName() + "." + literal.getLiteral();
		exp.setValue(fqn);
	}

	/**
	 * PostProcess for Expressions used in ObjectVariable inline
	 * AttributeAssignments/Constraints.
	 * 
	 * @param corr
	 *            Correspondence between Expression of source and target graph.
	 */
	private void postProcessForward_Expression(ExpressionToExpression corr) {
		Expression trgExpression = corr.getTarget();

		// Sets attribute of target graphs AttributeValueExpression
		if (trgExpression instanceof AttributeValueExpression) {
			AttributeExpression srcExpression = (AttributeExpression) corr.getSource();
			((AttributeValueExpression) trgExpression).setAttribute(srcExpression.getAttribute());
		}
	}

	/**
	 * PostProcess for inline Constraints used in ObjectVariable.
	 * 
	 * @param corr
	 *            Correspondence between AttributeConstraint of source graph and
	 *            Constraint of target graph.
	 */
	private void postProcessForward_AttributeConstraint(AttributeConstraintToConstraint corr) {
		// Sets the Attribute of the AttributeValueExpression, which is the
		// LeftExpression of the Constraint's ComparisonExpression.
		ComparisonExpression compExp = (ComparisonExpression) corr.getTarget().getConstraintExpression();
		((AttributeValueExpression) compExp.getLeftExpression()).setAttribute(corr.getSource().getAttribute());
	}

	private void postProcessForward_AttributeAssignment(AttributeAssignmentToAttributeAssignment corr) {
		corr.getTarget().setAttribute(corr.getSource().getAttribute());
	}

	private void postProcessForward_TGGLinkVariable(LinkVariablePatternToTGGLinkVariable corr) {
		TGGLinkVariable tggLV = corr.getTarget();
		EReference lvType = corr.getSource().getType();

		tggLV.setType(lvType);
		tggLV.setName(lvType.getName());
	}

	private void postProcessForward_TGGObjectVariable(CorrVariablePatternToTGGObjectVariable corr,
			EPackage corrPackage) {
		TGGObjectVariable corrOV = corr.getTarget();
		EClass corrType = (EClass) corrPackage.getEClassifier(corr.getSource().getType().getName());
		corrOV.setType(corrType);

		for (LinkVariable lv : corrOV.getOutgoingLink()) {
			if (lv.getName().equals("source")) {
				lv.setType((EReference) corrType.getEStructuralFeature("source"));
			}
			if (lv.getName().equals("target")) {
				lv.setType((EReference) corrType.getEStructuralFeature("target"));
			}
		}
	}

	private void postProcessForward_TGGObjectVariable(ObjectVariablePatternToTGGObjectVariable corr) {
		corr.getTarget().setType(corr.getSource().getType());
	}

	private void postProcessForward_TripleGraphGrammarRoot(TripleGraphGrammarFileToTripleGraphGrammar corr,
			EPackage corrPackage) {
		TripleGraphGrammarFile tggFile = corr.getSource();
		TripleGraphGrammar tgg = corr.getTarget();

		setDefaultURI(tgg);

		for (Domain domain : tgg.getDomain()) {
			if (domain.getType() == DomainType.SOURCE) {
				tggFile.getSchema().getSourceTypes().forEach(sourceType -> {
					domain.getMetamodel().setOutermostPackage(sourceType);
					domain.getMetamodel().setName(sourceType.getName());
				});
			}
			if (domain.getType() == DomainType.TARGET) {
				tggFile.getSchema().getTargetTypes().forEach(targetType -> {
					domain.getMetamodel().setOutermostPackage(targetType);
					domain.getMetamodel().setName(targetType.getName());
				});
			}
			if (domain.getType() == DomainType.CORRESPONDENCE) {
				domain.getMetamodel().setOutermostPackage(corrPackage);
				domain.getMetamodel().setName(corrPackage.getName());
			}
		}
		for (EPackage pkg : corrPackage.getESubpackages()) {
			if (pkg.getName().equals("Rules")) {
				for (EClassifier classifier : pkg.getEClassifiers()) {
					if (classifier instanceof EClass) {
						EClass rule = (EClass) classifier;
						rule.getESuperTypes().add(RuntimePackage.Literals.ABSTRACT_RULE);
					}
				}
			}
		}
	}

	private void setDefaultURI(TripleGraphGrammar tgg) {
		TGGProject project = (TGGProject) tgg.eContainer();
		EPackage corrPackage = project.getCorrPackage();

		String corrPackageNsPrefix = corrPackage.getNsPrefix();
		String nsURIstart = "platform:/plugin/" + corrPackageNsPrefix;

		String corrPackageName = namespaceToName(tgg);
		corrPackage.setName(corrPackageName);

		Metamodel corr = tgg.getDomain().stream().filter(d -> d.getType().equals(DomainType.CORRESPONDENCE)).findAny()
				.get().getMetamodel();
		corr.setName(corrPackageName);

		String capitalizedCorrPackageName = StringUtils.capitalize(corrPackageName);
		String nsURIend = capitalizedCorrPackageName + ".ecore";
		String corrPackageNsURI = nsURIstart + "/model/" + nsURIend;
		corrPackage.setNsURI(corrPackageNsURI);

		EPackage rulesPackage = corrPackage.getESubpackages().get(0);
		rulesPackage.setNsURI(corrPackageNsURI + "#//Rules");

	}

	private String namespaceToName(TripleGraphGrammar tgg) {
		int index = tgg.getName().lastIndexOf(".");
		if (index < 0) {
			return tgg.getName();
		} else {
			return tgg.getName().substring(index + 1);
		}
	}

	private void postProcessForward_AbstractCorrespondenceSubClass(CorrTypeToEClass corr) {
		CorrType corrType = corr.getSource();
		EClass absCorrSubClass = corr.getTarget();

		absCorrSubClass.getESuperTypes().add(RuntimePackage.Literals.ABSTRACT_CORRESPONDENCE);

		if (corrType.getSuper() != null) {
			CorrType srcCorrType = corrType.getSuper();
			for (EClassifier trgCorrType : absCorrSubClass.getEPackage().getEClassifiers()) {
				if (trgCorrType.getName().equals(srcCorrType.getName())) {
					absCorrSubClass.getESuperTypes().add((EClass) trgCorrType);
				}
			}
		} else {
			EReference ref = EcoreFactory.eINSTANCE.createEReference();
			ref.setName("source");
			ref.setLowerBound(1);
			ref.setEType(corrType.getSource());
			absCorrSubClass.getEStructuralFeatures().add(ref);

			ref = EcoreFactory.eINSTANCE.createEReference();
			ref.setName("target");
			ref.setLowerBound(1);
			ref.setEType(corrType.getTarget());
			absCorrSubClass.getEStructuralFeatures().add(ref);
		}
	}

}
