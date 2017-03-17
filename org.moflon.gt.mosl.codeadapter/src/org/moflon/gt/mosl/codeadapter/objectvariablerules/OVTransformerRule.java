package org.moflon.gt.mosl.codeadapter.objectvariablerules;

import java.util.Map;
import java.util.function.Function;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.codeadapter.abstractutils.IOperatorRule;
import org.moflon.gt.mosl.codeadapter.codeadapter.ExpressionBuilder;
import org.moflon.gt.mosl.codeadapter.codeadapter.LinkVariableBuilder;
import org.moflon.gt.mosl.codeadapter.codeadapter.ObjectVariableBuilder;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;

public abstract class OVTransformerRule implements IOperatorRule{
	
	private String suffix;
	
	private Function<Variable, Boolean> isBoundFun;
	
	protected boolean isBound(Variable variable){
		return isBoundFun.apply(variable);
	}
	
	public OVTransformerRule(String suffix){
		ObjectVariableBuilder.getInstance().addPatternTransformer(this);
		this.suffix=suffix;
	}
	
	public String transforming(ObjectVariableDefinition ov, Variable variable, Map<String, Boolean> bindings, PatternBody patternBody, String oldSuffix){
		isBoundFun =var -> {return bindings.get(var.getName());};
		if(isTransformable(ov, variable)){
			transformOV(ov, variable, bindings, patternBody);
			ov.getLinkVariablePatterns().stream().forEach(link -> {LinkVariableBuilder.getInstance().transformLinkVariable(link, ov, patternBody);});
			ov.getAttributeConstraints().stream().forEach(ac -> {ExpressionBuilder.getInstance().transformExpression(ov, ac, patternBody);});
			oldSuffix = suffix;
			return suffix;
		}
		return oldSuffix;
	}	

	
	protected abstract void transformOV(ObjectVariableDefinition ov, Variable variable, Map<String, Boolean> bindings, PatternBody patternBody);
	protected abstract boolean isTransformable(ObjectVariableDefinition ov, Variable variable);
	
}
