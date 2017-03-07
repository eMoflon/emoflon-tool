package org.moflon.gt.mosl.codeadapter.patterngeneration;

import java.util.Map;
import java.util.Set;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.sdm.runtime.democles.CFVariable;

public class MatchingUnboundPatternTransformer extends PatternTransformer {

	@Override
	public Pattern transformPattern(PatternDef patternDef, Map<CFVariable, Boolean> bindings) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTransformable(PatternDef patternDef, Map<CFVariable, Boolean> bindings) {
		
		return false;
	}

}
