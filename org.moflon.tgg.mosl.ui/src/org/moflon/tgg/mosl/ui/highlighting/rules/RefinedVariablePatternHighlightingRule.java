package org.moflon.tgg.mosl.ui.highlighting.rules;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.moflon.tgg.mosl.tgg.CorrVariablePattern;
import org.moflon.tgg.mosl.tgg.NamePattern;
import org.moflon.tgg.mosl.tgg.Operator;
import org.moflon.tgg.mosl.tgg.Rule;
import org.moflon.tgg.mosl.ui.highlighting.utils.MOSLColor;

public class RefinedVariablePatternHighlightingRule extends AbstractNamePatternHighlightRule {

	public RefinedVariablePatternHighlightingRule() {
		super("RefinedPattern", "Refined Pattern");
	}

	@Override
	protected boolean getNameCondition(NamePattern namePattern) {
		Rule rule = null;
		EObject eContainer = namePattern.eContainer();
		if(!operatorIsNotSetted(namePattern) || !(eContainer instanceof Rule))
			return false;
		else{
			rule = Rule.class.cast(eContainer);
			return isRefined(rule, namePattern);
		}
	}
	
	private boolean operatorIsNotSetted(NamePattern namePattern){
		if(namePattern == null)
			return true;
		else{
			Operator op = namePattern.getOp();
			return op == null || op.getValue() == null || "".equals(op.getValue());
		}
	}
	
	private boolean isRefined(Rule rule, NamePattern np){
		boolean tmp = false;
		for(Rule supertype : rule.getSupertypes()){
			if(tmp)
				return tmp;
			tmp = tmp || containsRuleVariablePatternName(supertype, np);
			
			if(tmp)
				return tmp;
			tmp = tmp || isRefined(supertype, np);
		}
		return tmp;
	}
	
	private boolean containsRuleVariablePatternName(Rule rule, NamePattern origin){		
		return origin instanceof CorrVariablePattern ? containsRuleVariablePatternName(rule.getCorrespondencePatterns(), origin) 
				: (containsRuleVariablePatternName(rule.getSourcePatterns(), origin) 
						|| containsRuleVariablePatternName(rule.getTargetPatterns(), origin));
	}
	
	private boolean containsRuleVariablePatternName(Collection<? extends NamePattern> patterns, NamePattern origin){
		for(NamePattern np : patterns){
			if(np!=null && origin!=null && origin.getName()!=null && origin.getName().compareTo(np.getName())==0)
				return true;
		}		
		return false;
	}

	@Override
	protected TextStyle getTextStyle() {
	      TextStyle ts = new TextStyle();
	      ts.setColor(MOSLColor.BLACK.getColor());
	      ts.setStyle(SWT.BOLD);
	      return ts;
	}





}
