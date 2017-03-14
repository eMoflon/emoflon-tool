package org.moflon.gt.mosl.codeadapter.objectvariablerules;

import java.util.Map;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;

public class MatchingUnboundOVTransformerRule extends OVTransformerRule {


	@Override
	public boolean isTransformable(ObjectVariableDefinition ov, Variable variable) {		
		return isCheckOnly(ov.getOp()) && !isBound(variable);
	}

	@Override
	public void transformOV(ObjectVariableDefinition ov, Variable variable, Map<String, Boolean> bindings,
			Pattern pattern) {
		// TODO Auto-generated method stub
	
	}

}
