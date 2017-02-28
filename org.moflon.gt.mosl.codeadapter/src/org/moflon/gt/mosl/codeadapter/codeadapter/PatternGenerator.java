package org.moflon.gt.mosl.codeadapter.codeadapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypeFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternParameters;

public class PatternGenerator {
	private static PatternGenerator instance;
	
	private Map<String, Pattern> patternCache;
	
	private PatternGenerator(){
		patternCache = new HashMap<>();
	}
	
	public static PatternGenerator getInstance(){
		if(instance == null)
			instance = new PatternGenerator();
		return instance;
	}
	
	public void createPatterns(List<PatternDef> patternDefs){
		for(PatternDef patternDef : patternDefs){
			String patternName = patternDef.getName();
			Pattern pattern = SpecificationFactory.eINSTANCE.createPattern();
			pattern.setName(patternName);
			patternCache.put(patternName, pattern);
			for(PatternParameters pp : patternDef.getParameters()){
				ObjectVariableDefinition ov = ObjectVariableDefinition.class.cast(pp);
				EMFVariable patternVariable = EMFTypeFactory.eINSTANCE.createEMFVariable();
				pattern.getSymbolicParameters().add(patternVariable);
				
				patternVariable.setName(ov.getName());
				patternVariable.setEClassifier(ov.getType());
			}			
		}
	}
	
	public Pattern getPattern(String patternName){
		return patternCache.get(patternName);
	}
}
