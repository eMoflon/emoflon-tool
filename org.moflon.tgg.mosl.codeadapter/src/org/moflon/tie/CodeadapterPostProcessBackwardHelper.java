package org.moflon.tie;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.moflon.tgg.language.Domain;
import org.moflon.tgg.language.DomainType;
import org.moflon.tgg.language.TGGObjectVariable;
import org.moflon.tgg.language.TripleGraphGrammar;
import org.moflon.tgg.language.csp.AttributeVariable;
import org.moflon.tgg.language.csp.Literal;
import org.moflon.tgg.language.csp.LocalVariable;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.mosl.codeadapter.AttrCondToTGGConstraint;
import org.moflon.tgg.mosl.codeadapter.AttributeAssignmentToAttributeAssignment;
import org.moflon.tgg.mosl.codeadapter.AttributeConstraintToConstraint;
import org.moflon.tgg.mosl.codeadapter.CorrTypeToEClass;
import org.moflon.tgg.mosl.codeadapter.CorrVariablePatternToTGGObjectVariable;
import org.moflon.tgg.mosl.codeadapter.ExpressionToExpression;
import org.moflon.tgg.mosl.codeadapter.LinkVariablePatternToTGGLinkVariable;
import org.moflon.tgg.mosl.codeadapter.ObjectVariablePatternToTGGObjectVariable;
import org.moflon.tgg.mosl.codeadapter.RuleToTGGRule;
import org.moflon.tgg.mosl.codeadapter.TripleGraphGrammarFileToTripleGraphGrammar;
import org.moflon.tgg.mosl.tgg.AttrCond;
import org.moflon.tgg.mosl.tgg.AttributeExpression;
import org.moflon.tgg.mosl.tgg.CorrType;
import org.moflon.tgg.mosl.tgg.Import;
import org.moflon.tgg.mosl.tgg.ParamValue;
import org.moflon.tgg.mosl.tgg.Rule;
import org.moflon.tgg.mosl.tgg.Schema;
import org.moflon.tgg.mosl.tgg.TggFactory;
import org.moflon.tgg.mosl.tgg.Using;
import org.moflon.tgg.runtime.CorrespondenceModel;

import SDMLanguage.expressions.ComparisonExpression;
import SDMLanguage.expressions.Expression;
import SDMLanguage.patterns.patternExpressions.AttributeValueExpression;

public class CodeadapterPostProcessBackwardHelper {

	private List<String> addedImports;

	public CodeadapterPostProcessBackwardHelper() {
		addedImports = new ArrayList<String>();
	}

	public void postProcessBackward(CodeadapterTrafo trafoHelper) {
		CorrespondenceModel corrModel = trafoHelper.getCorr();

		for (EObject corr : corrModel.getCorrespondences()) {

			if (corr instanceof TripleGraphGrammarFileToTripleGraphGrammar)
				postProcessBackward_TripleGraphGrammarFile((TripleGraphGrammarFileToTripleGraphGrammar) corr);

			if (corr instanceof CorrTypeToEClass)
				postProcessBackward_CorrType((CorrTypeToEClass) corr);

			if (corr instanceof RuleToTGGRule)
				postProcessBackward_Rule((RuleToTGGRule) corr);

			if (corr instanceof ObjectVariablePatternToTGGObjectVariable)
				postProcessBackward_ObjectVariablePattern((ObjectVariablePatternToTGGObjectVariable) corr);

			if (corr instanceof CorrVariablePatternToTGGObjectVariable)
				postProcessBackward_CorrVariablePattern((CorrVariablePatternToTGGObjectVariable) corr);

			if (corr instanceof LinkVariablePatternToTGGLinkVariable)
				postProcessBackward_LinkVariablePattern((LinkVariablePatternToTGGLinkVariable) corr);

			if (corr instanceof AttributeAssignmentToAttributeAssignment)
				postProcessBackward_AttributeAssignment((AttributeAssignmentToAttributeAssignment) corr);

			if (corr instanceof AttributeConstraintToConstraint)
				postProcessBackward_AttributeConstraint((AttributeConstraintToConstraint) corr);

			if (corr instanceof ExpressionToExpression)
				postProcessBackward_Expression((ExpressionToExpression) corr);

			if (corr instanceof AttrCondToTGGConstraint)
				postProcessBackward_AttrCond((AttrCondToTGGConstraint) corr);
		}
	}

	private void postProcessBackward_CorrVariablePattern(CorrVariablePatternToTGGObjectVariable corr) {
		EClass cvType = (EClass) corr.getTarget().getType();
		Schema schema = ((Rule) corr.getSource().eContainer()).getSchema();

		for (CorrType corrType : schema.getCorrespondenceTypes()) {
			if (corrType.getName().equals(cvType.getName())) {
				corr.getSource().setType(corrType);
			}
		}
	}

	private void postProcessBackward_LinkVariablePattern(LinkVariablePatternToTGGLinkVariable corr) {
		corr.getSource().setType(corr.getTarget().getType());
	}

	private void postProcessBackward_ObjectVariablePattern(ObjectVariablePatternToTGGObjectVariable corr) {
		TGGObjectVariable tggOV = corr.getTarget();
		EClass ovType = (EClass) tggOV.getType();
		EPackage packageOfOVType = ovType.getEPackage();
		Schema schema = ((Rule) corr.getSource().eContainer()).getSchema();

		if(!tggOV.getDomain().getType().equals(DomainType.CORRESPONDENCE))
			importEPackage(schema, packageOfOVType);
		
		if(tggOV.getDomain().getType().equals(DomainType.SOURCE) && !schema.getSourceTypes().contains(packageOfOVType))
			schema.getSourceTypes().add(packageOfOVType);
		
		if(tggOV.getDomain().getType().equals(DomainType.TARGET) && !schema.getTargetTypes().contains(packageOfOVType))
			schema.getTargetTypes().add(packageOfOVType);
		
		corr.getSource().setType(ovType);
	}

	private void postProcessBackward_AttrCond(AttrCondToTGGConstraint corr) {
		AttrCond attrCond = corr.getSource();
		EList<ParamValue> srcVariables = new BasicEList<ParamValue>();

		for (Variable var : corr.getTarget().getVariables()) {
			for (ParamValue paramVal : attrCond.getValues()) {
				if (var instanceof AttributeVariable && paramVal instanceof org.moflon.tgg.mosl.tgg.AttributeVariable) {
					org.moflon.tgg.mosl.tgg.AttributeVariable srcAttr = (org.moflon.tgg.mosl.tgg.AttributeVariable) paramVal;
					AttributeVariable trgAttr = (AttributeVariable) var;

					if (srcAttr.getAttribute().equals(trgAttr.getAttribute())
							&& srcAttr.getObjectVar().getName().equals(trgAttr.getObjectVariable()))
						srcVariables.add(srcAttr);
				}
				if (var instanceof LocalVariable && paramVal instanceof org.moflon.tgg.mosl.tgg.LocalVariable) {
					org.moflon.tgg.mosl.tgg.LocalVariable srcAttr = (org.moflon.tgg.mosl.tgg.LocalVariable) paramVal;
					LocalVariable trgAttr = (LocalVariable) var;

					if (srcAttr.getName().equals(trgAttr.getName()))
						srcVariables.add(srcAttr);
				}
				if (var instanceof Literal && paramVal instanceof org.moflon.tgg.mosl.tgg.Literal) {
					org.moflon.tgg.mosl.tgg.Literal srcAttr = (org.moflon.tgg.mosl.tgg.Literal) paramVal;
					Literal trgAttr = (Literal) var;
					String srcAttrValue = srcAttr.getValue();
					String trgAttrValue = (String) trgAttr.getValue();

					if (srcAttrValue.equals(trgAttrValue) || srcAttrValue.equals(trgAttrValue.replace(".", "::"))) {
						if (srcAttrValue.charAt(0) != '"' && srcAttrValue.charAt(0) != '\'') {
							srcAttr.setValue(srcAttrValue.replace(".", "::"));
						}
						srcVariables.add(srcAttr);
					}
				}
			}
		}
		attrCond.getValues().clear();
		attrCond.getValues().addAll(srcVariables);
	}

	private void postProcessBackward_Expression(ExpressionToExpression corr) {
		Expression trgExpression = corr.getTarget();
		if (trgExpression instanceof AttributeValueExpression) {
			((AttributeExpression) corr.getSource())
					.setAttribute(((AttributeValueExpression) trgExpression).getAttribute());
		}
	}

	private void postProcessBackward_AttributeConstraint(AttributeConstraintToConstraint corr) {
		ComparisonExpression compExp = (ComparisonExpression) corr.getTarget().getConstraintExpression();
		corr.getSource().setAttribute(((AttributeValueExpression) compExp.getLeftExpression()).getAttribute());
	}

	private void postProcessBackward_AttributeAssignment(AttributeAssignmentToAttributeAssignment corr) {
		corr.getSource().setAttribute(corr.getTarget().getAttribute());
	}

	private void postProcessBackward_CorrType(CorrTypeToEClass corr) {
		CorrType corrType = corr.getSource();
		EClass corrEClass = corr.getTarget();
		Schema schema = (Schema) corrType.eContainer();

		setSuperCorrType(corrType, corrEClass, schema);

		if (corrType.getSuper() == null) {
			for (EReference ref : corrEClass.getEAllReferences()) {
				if (ref.getName().equals("source")) {
					EClass sourceType = (EClass) ref.getEType();
					corrType.setSource(sourceType);
				}
				if (ref.getName().equals("target")) {
					EClass targetType = (EClass) ref.getEType();
					corrType.setTarget(targetType);
				}
			}
		}
	}

	private void postProcessBackward_Rule(RuleToTGGRule corr) {
		Rule rule = corr.getSource();
		Using using = TggFactory.eINSTANCE.createUsing();

		using.setImportedNamespace(rule.getSchema().getName() + ".*");
		rule.getUsing().add(using);
	}

	private void postProcessBackward_TripleGraphGrammarFile(TripleGraphGrammarFileToTripleGraphGrammar corr) {
		TripleGraphGrammar tggRoot = corr.getTarget();
		Schema schema = corr.getSource().getSchema();

		for (Domain domain : tggRoot.getDomain()) {
			EPackage domainPackage = domain.getMetamodel().getOutermostPackage();
			importEPackage(schema, domainPackage);
			if (domain.getType() == DomainType.SOURCE) {
				schema.getSourceTypes().add(domainPackage);
			}
			if (domain.getType() == DomainType.TARGET) {
				schema.getTargetTypes().add(domainPackage);
			}
		}

	}

	private void importEPackage(Schema schema, EPackage epackage) {
		String ePackageNsURI = epackage.getNsURI();
		if (!addedImports.contains(ePackageNsURI)) {
			addedImports.add(ePackageNsURI);
			Import packageImport = TggFactory.eINSTANCE.createImport();
			packageImport.setName(ePackageNsURI);
			schema.getImports().add(packageImport);
		}
	}

	private void setSuperCorrType(CorrType corrType, EClass corrEClass, Schema schema) {
		for (EClass trgSuperCorrType : corrEClass.getEAllSuperTypes()) {
			for (EClassifier trgCorrType : corrEClass.getEPackage().getEClassifiers()) {
				if (trgSuperCorrType.getName().equals(trgCorrType.getName())) {
					for (CorrType srcCorrType : schema.getCorrespondenceTypes()) {
						if (srcCorrType.getName().equals(trgSuperCorrType.getName())) {
							corrType.setSuper(srcCorrType);
						}
					}
				}
			}
		}
	}

}
