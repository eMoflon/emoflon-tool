package org.moflon.gt.mosl.codeadapter.patterngeneration;

import java.util.Map;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.codeadapter.codeadapter.ObjectVariableBuilder;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.sdm.runtime.democles.CFVariable;

public abstract class OVTransformerRule {
	
	public OVTransformerRule(){
		ObjectVariableBuilder.getInstance().addPatternTransformer(this);
	}
	
	public abstract void transformOV(ObjectVariableDefinition ov, Variable variable, Map<CFVariable, Boolean> bindings, Pattern pattern);
	public abstract boolean isTransformable(ObjectVariableDefinition ov, Variable variable, Map<CFVariable, Boolean> bindings);
	
}
