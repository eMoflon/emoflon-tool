package org.moflon.gt.mosl.codeadapter.objectvariablerules;

import java.util.Map;
import java.util.function.Function;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.codeadapter.abstractutils.AbstractOperatorRule;
import org.moflon.gt.mosl.codeadapter.codeadapter.LinkVariableBuilder;
import org.moflon.gt.mosl.codeadapter.codeadapter.ObjectVariableBuilder;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;

public abstract class OVTransformerRule extends AbstractOperatorRule{
	
	private Function<Variable, Boolean> isBoundFun;
	
	protected boolean isBound(Variable variable){
		return isBoundFun.apply(variable);
	}
	
	public OVTransformerRule(){
		ObjectVariableBuilder.getInstance().addPatternTransformer(this);
	}
	
	public void transforming(ObjectVariableDefinition ov, Variable variable, Map<String, Boolean> bindings, Pattern pattern){
		isBoundFun =var -> {return bindings.get(var.getName());};
		if(isTransformable(ov, variable)){
			transformOV(ov, variable, bindings, pattern);
			ov.getLinkVariablePatterns().stream().forEach(link -> {LinkVariableBuilder.getInstance().transformLinkVariable(link, pattern);});
		}
	}	

	protected abstract void transformOV(ObjectVariableDefinition ov, Variable variable, Map<String, Boolean> bindings, Pattern pattern);
	protected abstract boolean isTransformable(ObjectVariableDefinition ov, Variable variable);
	
}
