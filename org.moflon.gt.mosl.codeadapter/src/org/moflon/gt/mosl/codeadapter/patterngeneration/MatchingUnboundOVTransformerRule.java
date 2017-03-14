package org.moflon.gt.mosl.codeadapter.patterngeneration;

import java.util.Map;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.sdm.runtime.democles.CFVariable;

public class MatchingUnboundOVTransformerRule extends OVTransformerRule {


	@Override
	public boolean isTransformable(ObjectVariableDefinition ov, Variable variable, Map<CFVariable, Boolean> bindings) {
		
		return bindings.get(variable);
	}

	@Override
	public void transformOV(ObjectVariableDefinition ov, Variable variable, Map<CFVariable, Boolean> bindings,
			Pattern pattern) {
		// TODO Auto-generated method stub
	
	}

}
