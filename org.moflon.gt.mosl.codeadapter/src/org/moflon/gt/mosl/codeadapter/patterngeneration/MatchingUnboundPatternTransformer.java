package org.moflon.gt.mosl.codeadapter.patterngeneration;

import java.util.Map;
import java.util.Set;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.sdm.runtime.democles.CFVariable;

public class MatchingUnboundPatternTransformer extends PatternTransformerRule {


	@Override
	public boolean isTransformable(PatternDef patternDef, Map<CFVariable, Boolean> bindings) {
		
		return false;
	}

	@Override
	public void transformPattern(PatternDef patternDef, CFVariable cfVar, Map<CFVariable, Boolean> bindings,
			Pattern pattern) {
		// TODO Auto-generated method stub
		
	}

}
