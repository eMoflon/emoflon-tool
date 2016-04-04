package org.moflon.tie;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.moflon.tgg.language.Domain;
import org.moflon.tgg.language.DomainType;
import org.moflon.tgg.language.TGGLinkVariable;
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
import org.moflon.tgg.mosl.codeadapter.TripleGraphGrammarFileToTripleGraphGrammar;
import org.moflon.tgg.mosl.tgg.AttributeExpression;
import org.moflon.tgg.mosl.tgg.CorrType;
import org.moflon.tgg.mosl.tgg.ParamValue;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.tgg.runtime.RuntimePackage;
import org.moflon.tgg.tggproject.TGGProject;

import SDMLanguage.expressions.ComparisonExpression;
import SDMLanguage.expressions.Expression;
import SDMLanguage.patterns.LinkVariable;
import SDMLanguage.patterns.patternExpressions.AttributeValueExpression;

public class CodeadapterPostProcessForwardHelper {
	
	public void postProcessForward(CodeadapterTrafo trafoHelper) {
		CorrespondenceModel corrModel = trafoHelper.getCorr();
		EPackage corrPackage = ((TGGProject) trafoHelper.getTrg()).getCorrPackage();
		
		for (EObject corr : corrModel.getCorrespondences()) {
			
			if(corr instanceof TripleGraphGrammarFileToTripleGraphGrammar)
				postProcessForward_TripleGraphGrammarRoot((TripleGraphGrammarFileToTripleGraphGrammar)corr, corrPackage);
			
			if(corr instanceof CorrTypeToEClass)
				postProcessForward_AbstractCorrespondenceSubClass((CorrTypeToEClass)corr);
			
			if(corr instanceof ObjectVariablePatternToTGGObjectVariable)
				postProcessForward_TGGObjectVariable((ObjectVariablePatternToTGGObjectVariable)corr);
			
			if(corr instanceof CorrVariablePatternToTGGObjectVariable)
				postProcessForward_TGGObjectVariable((CorrVariablePatternToTGGObjectVariable)corr, corrPackage);
			
			if(corr instanceof LinkVariablePatternToTGGLinkVariable)
				postProcessForward_TGGLinkVariable((LinkVariablePatternToTGGLinkVariable)corr);
			
			if(corr instanceof AttributeAssignmentToAttributeAssignment)
				postProcessForward_AttributeAssignment((AttributeAssignmentToAttributeAssignment)corr);
			
			if(corr instanceof AttributeConstraintToConstraint)
				postProcessForward_AttributeConstraint((AttributeConstraintToConstraint)corr);
			
			if(corr instanceof ExpressionToExpression)
				postProcessForward_Expression((ExpressionToExpression)corr);
			
			if(corr instanceof AttrCondToTGGConstraint)
				postProcessForward_TGGConstraint((AttrCondToTGGConstraint)corr);
		}
	}


	/**
	 * PostProcess for Expressions used in ObjectVariable inline AttributeAssignments/Constraints.
	 * 
	 * @param corr Correspondence between Expression of source and target graph.
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
	 * PostProcess for TGGConstraints used in CSPs.
	 * 
	 * @param corr Correspondence between AttrCond of source graph and TGGConstraint of target graph.
	 */
	private void postProcessForward_TGGConstraint(AttrCondToTGGConstraint corr) {
		//List to store sorted Variables
		EList<Variable> trgVariables = new BasicEList<Variable>();
		
		// Iterated through AttrCond's ordered ParamValues and search corresponding Variable
		for (ParamValue paramVal : corr.getSource().getValues()) {
			for (Variable var : corr.getTarget().getVariables()) {
				if(var instanceof AttributeVariable && paramVal instanceof org.moflon.tgg.mosl.tgg.AttributeVariable){
					org.moflon.tgg.mosl.tgg.AttributeVariable srcAttr = (org.moflon.tgg.mosl.tgg.AttributeVariable) paramVal;
					AttributeVariable trgAttr = (AttributeVariable) var;
					
					if(srcAttr.getAttribute().equals(trgAttr.getAttribute()) && srcAttr.getObjectVar().getName().equals(trgAttr.getObjectVariable()))
						trgVariables.add(trgAttr);
				}
				if(var instanceof LocalVariable && paramVal instanceof org.moflon.tgg.mosl.tgg.LocalVariable){
					org.moflon.tgg.mosl.tgg.LocalVariable srcAttr = (org.moflon.tgg.mosl.tgg.LocalVariable) paramVal;
					LocalVariable trgAttr = (LocalVariable) var;
					
					if(srcAttr.getName().equals(trgAttr.getName()))
						trgVariables.add(trgAttr);
				}
				if(var instanceof Literal && paramVal instanceof org.moflon.tgg.mosl.tgg.Literal){
					org.moflon.tgg.mosl.tgg.Literal srcAttr = (org.moflon.tgg.mosl.tgg.Literal) paramVal;
					Literal trgAttr = (Literal) var;
					String srcAttrValue = srcAttr.getValue();
					String trgAttrValue = (String) trgAttr.getValue();
					
					if(srcAttrValue.equals(trgAttrValue) || srcAttrValue.equals(trgAttrValue.replace(".", "::"))){
						if(trgAttrValue.charAt(0) != '"' && trgAttrValue.charAt(0) != '\''){
							trgAttr.setValue(trgAttrValue.replace("::", "."));
						}
						trgVariables.add(trgAttr);
					}
				}
			}
		}
		corr.getTarget().getVariables().clear();
		corr.getTarget().getVariables().addAll(trgVariables);
	}

	/**
	 * PostProcess for inline Constraints used in ObjectVariable.
	 * 
	 * @param corr Correspondence between AttributeConstraint of source graph and Constraint of target graph.
	 */
	private void postProcessForward_AttributeConstraint(AttributeConstraintToConstraint corr) {
		// Sets the Attribute of the AttributeValueExpression, which is the LeftExpression of the Constraint's ComparisonExpression.
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

	private void postProcessForward_TGGObjectVariable(CorrVariablePatternToTGGObjectVariable corr, EPackage corrPackage) {
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

	private void postProcessForward_TripleGraphGrammarRoot(TripleGraphGrammarFileToTripleGraphGrammar corr, EPackage corrPackage) {
		TripleGraphGrammarFile tggFile = corr.getSource();
		TripleGraphGrammar tgg = corr.getTarget();
		
		for (Domain domain : tgg.getDomain()) {
			if(domain.getType() == DomainType.SOURCE){
				EPackage sourceType = tggFile.getSchema().getSourceTypes().get(0);
				domain.getMetamodel().setOutermostPackage(sourceType);
				domain.getMetamodel().setName(sourceType.getName());
			}
			if(domain.getType() == DomainType.TARGET){
				EPackage targetType = tggFile.getSchema().getTargetTypes().get(0);
				domain.getMetamodel().setOutermostPackage(targetType);
				domain.getMetamodel().setName(targetType.getName());
			}
			if(domain.getType() == DomainType.CORRESPONDENCE){
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

	private void postProcessForward_AbstractCorrespondenceSubClass(CorrTypeToEClass corr) {
		CorrType corrType = corr.getSource();
		EClass absCorrSubClass = corr.getTarget();
		
		absCorrSubClass.getESuperTypes().add(RuntimePackage.Literals.ABSTRACT_CORRESPONDENCE);
		
		if(corrType.getSuper() != null){
			CorrType srcCorrType = corrType.getSuper();
			for (EClassifier trgCorrType : absCorrSubClass.getEPackage().getEClassifiers()) {
				if (trgCorrType.getName().equals(srcCorrType.getName())) {
					absCorrSubClass.getESuperTypes().add((EClass) trgCorrType);
				}
			}
		}
		else {
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
