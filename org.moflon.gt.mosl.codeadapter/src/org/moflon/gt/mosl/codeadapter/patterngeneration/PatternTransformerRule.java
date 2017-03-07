package org.moflon.gt.mosl.codeadapter.patterngeneration;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.codeadapter.codeadapter.PatternGenerator;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.sdm.runtime.democles.CFVariable;

public abstract class PatternTransformerRule {
	
	public PatternTransformerRule(){
		PatternGenerator.getInstance().addPatternTransformer(this);
	}
	
	public abstract void transformPattern(PatternDef patternDef, CFVariable cfVar, Map<CFVariable, Boolean> bindings, Pattern pattern);
	public abstract boolean isTransformable(PatternDef patternDef, Map<CFVariable, Boolean> bindings);
	
}
