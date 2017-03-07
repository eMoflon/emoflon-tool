package org.moflon.gt.mosl.codeadapter.codeadapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypeFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable;
import org.moflon.gt.mosl.codeadapter.patterngeneration.PatternTransformerRule;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternParameters;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.PatternInvocation;

public class PatternGenerator {
	private static PatternGenerator instance;
	
	private Map<String, Map<Function<Map<CFVariable, Boolean>, Boolean>, Function<Map<CFVariable, Boolean>, Pattern>>> patternDefCache;
	
	private List<PatternTransformerRule> transformers;
	
	private Map<String, Pattern> patternCache;
	
	private Map<String, PatternInvocation> patternInvocationCache;
	
	private PatternGenerator(){
		patternCache = new HashMap<>();
		patternDefCache = new HashMap<>();
		patternInvocationCache = new HashMap<>();
		transformers = new LinkedList<>();
	}
	
	public static PatternGenerator getInstance(){
		if(instance == null)
			instance = new PatternGenerator();
		return instance;
	}
	
	public void addPatternTransformer(PatternTransformerRule transformer){
		transformers.add(transformer);
	}
	
	public void createPattern(PatternDef patternDef, Map<CFVariable, Boolean> bindings, Function<String, String> patternNameGenerator){
		Pattern pattern = SpecificationFactory.eINSTANCE.createPattern();
		pattern.setName(patternNameGenerator.apply(this.generateSuffix(bindings)));
		
		PatternBody patternBody = SpecificationFactory.eINSTANCE.createPatternBody();
		patternBody.setHeader(pattern);
		
		String patternName = patternDef.getName();
		patternCache.put(patternName, pattern);
		for(PatternParameters pp : patternDef.getParameters()){
			ObjectVariableDefinition ov = ObjectVariableDefinition.class.cast(pp);
			EMFVariable patternVariable = EMFTypeFactory.eINSTANCE.createEMFVariable();
			pattern.getSymbolicParameters().add(patternVariable);
			
			patternVariable.setName(ov.getName());
			patternVariable.setEClassifier(ov.getType());
		}
		createPatternInvocation(patternName, pattern);
	}
	
	private void createPatternInvocation(String readAblePatternName, Pattern pattern){
		PatternInvocation invocation = DemoclesFactory.eINSTANCE.createRegularPatternInvocation(); //TODO find correct Pattern invocation
		invocation.setPattern(pattern);
		patternInvocationCache.put(readAblePatternName, invocation);
	}
	
	private String generateSuffix(Map<CFVariable, Boolean> bindings){
		StringBuilder sb = new StringBuilder();
		for(Entry<CFVariable, Boolean> entry : bindings.entrySet()){
			if(entry.getValue()){
				sb.append("B");
			}else
				sb.append("F");
		}
		return sb.toString();
	}
	
	public PatternInvocation getPatternInvocation(String patternName){
		return patternInvocationCache.get(patternName);
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
