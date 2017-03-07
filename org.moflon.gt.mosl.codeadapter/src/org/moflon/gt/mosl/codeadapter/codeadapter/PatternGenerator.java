package org.moflon.gt.mosl.codeadapter.codeadapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.codeadapter.patterngeneration.PatternTransformer;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.sdm.runtime.democles.CFVariable;

public class PatternGenerator {
	private static PatternGenerator instance;
	
	private Map<String, Map<Function<Map<CFVariable, Boolean>, Boolean>, Function<Map<CFVariable, Boolean>, Pattern>>> patternDefCache;
	
	private List<PatternTransformer> transformers;
	
	private PatternGenerator(){
		patternDefCache = new HashMap<>();
		transformers = new LinkedList<>();
	}
	
	public static PatternGenerator getInstance(){
		if(instance == null)
			instance = new PatternGenerator();
		return instance;
	}
	
	public void addPatternTransformer(PatternTransformer transformer){
		transformers.add(transformer);
	}
	
//	public void createPatterns(List<PatternDef> patternDefs){
//		for(PatternDef patternDef : patternDefs){
//			String patternName = patternDef.getName();
//			Pattern pattern = SpecificationFactory.eINSTANCE.createPattern();
//			pattern.setName(patternName);
//			patternCache.put(patternName, pattern);
//			for(PatternParameters pp : patternDef.getParameters()){
//				ObjectVariableDefinition ov = ObjectVariableDefinition.class.cast(pp);
//				EMFVariable patternVariable = EMFTypeFactory.eINSTANCE.createEMFVariable();
//				pattern.getSymbolicParameters().add(patternVariable);
//				
//				patternVariable.setName(ov.getName());
//				patternVariable.setEClassifier(ov.getType());
//			}			
//		}
//	}
	
	public void createPatterns(List<PatternDef> patternDefs){
		for (PatternDef patternDef : patternDefs) {
			String patternName = patternDef.getName(); 
			Map<Function<Map<CFVariable, Boolean>, Boolean>, Function<Map<CFVariable, Boolean>, Pattern>> functionMap = new HashMap<>();			
			for (PatternTransformer transformer : transformers) {
				Function<Map<CFVariable, Boolean>, Boolean> testerFun =  bindings -> {return transformer.isTransformable(patternDef, bindings);};
				Function<Map<CFVariable, Boolean>, Pattern> executionFun = bindings -> {return transformer.transformPattern(patternDef, bindings);};
				functionMap.put(testerFun, executionFun);
			}
			patternDefCache.put(patternName, functionMap);
		}
	}
	
	public Pattern getPattern(String patternName, Map<CFVariable, Boolean> bindings){
		Map<Function<Map<CFVariable, Boolean>, Boolean>, Function<Map<CFVariable, Boolean>, Pattern>> functionMap = patternDefCache.get(patternName);
		if(functionMap == null)
			return null;
		for(Entry<Function<Map<CFVariable, Boolean>, Boolean>, Function<Map<CFVariable, Boolean>, Pattern>> functionEntry : functionMap.entrySet()){
			if(functionEntry.getKey().apply(bindings)){
				return functionEntry.getValue().apply(bindings);
			}
		}
		return null;
	}
	

}
