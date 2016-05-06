package org.moflon.tgg.mosl.ui.highlighting.rules;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.tgg.mosl.tgg.CorrVariablePattern;
import org.moflon.tgg.mosl.tgg.Rule;
import org.moflon.tgg.mosl.tgg.VariablePattern;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLColor;

@SuppressWarnings("deprecation")
public class RefinedVariablePatternHighlightingRule extends AbstractVariablePatternHighlightRule {

	public RefinedVariablePatternHighlightingRule() {
		super("RefinedPattern", "Refined Pattern");
	}

	@Override
	protected void provideHighlightingForVariablePattern(VariablePattern vp, INode variablePatternNode,
			IHighlightedPositionAcceptor acceptor) {
		EObject eContainer = vp.eContainer();
		if((vp.getOp() == null || vp.getOp().getValue()== null || vp.getOp().getValue().equals("")) 
				&& eContainer!=null && eContainer instanceof Rule && isRefined(Rule.class.cast(eContainer), vp)){
			setHighlighting(acceptor, variablePatternNode, id);
		}

	}
	
	private boolean isRefined(Rule rule, VariablePattern vp){
		boolean tmp = false;
		for(Rule supertype : rule.getSupertypes()){
			if(tmp)
				return tmp;
			tmp = tmp || containsRuleVariablePatternName(supertype, vp);
			
			if(tmp)
				return tmp;
			tmp = tmp || isRefined(supertype, vp);
		}
		return tmp;
	}
	
	private boolean containsRuleVariablePatternName(Rule rule, VariablePattern origin){		
		return origin instanceof CorrVariablePattern ? containsRuleVariablePatternName(rule.getCorrespondencePatterns(), origin) 
				: (containsRuleVariablePatternName(rule.getSourcePatterns(), origin) 
						|| containsRuleVariablePatternName(rule.getTargetPatterns(), origin));
	}
	
	private boolean containsRuleVariablePatternName(Collection<? extends VariablePattern> patterns, VariablePattern origin){
		for(VariablePattern vp : patterns){
			if(vp!=null && origin!=null && origin.getName()!=null && origin.getName().compareTo(vp.getName())==0)
				return true;
		}		
		return false;
	}

	@Override
	protected TextStyle getTextStyle() {
	      TextStyle ts = new TextStyle();
	      ts.setColor(MOSLColor.LIGHT_BLUE.getColor());
	      return ts;
	}

}
