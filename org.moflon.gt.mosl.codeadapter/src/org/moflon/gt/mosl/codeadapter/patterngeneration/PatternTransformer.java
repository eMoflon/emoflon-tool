package org.moflon.gt.mosl.codeadapter.patterngeneration;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.codeadapter.codeadapter.PatternGenerator;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.sdm.runtime.democles.CFVariable;

public abstract class PatternTransformer {
	
	public PatternTransformer(){
		PatternGenerator.getInstance().addPatternTransformer(this);
	}
	
	public abstract Pattern transformPattern(PatternDef patternDef, Map<CFVariable, Boolean> bindings);
	public abstract boolean isTransformable(PatternDef patternDef, Map<CFVariable, Boolean> bindings);
	
}
