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
import org.moflon.gt.mosl.codeadapter.objectvariablerules.OVTransformerRule;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternParameters;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.VariableReference;

public class PatternBuilder {
	private static PatternBuilder instance;
	
	
	
	private Map<String, Pattern> patternCache;
	
	private Map<String, PatternInvocation> patternInvocationCache;
	
	private PatternBuilder(){
		patternCache = new HashMap<>();
		patternInvocationCache = new HashMap<>();
	}
	
	public static PatternBuilder getInstance(){
		if(instance == null)
			instance = new PatternBuilder();
		return instance;
	}
	
	public void createPattern(PatternDef patternDef, Map<String, Boolean> bindings, Map<String, CFVariable> env, Function<String, String> patternNameGenerator){
		Pattern pattern = SpecificationFactory.eINSTANCE.createPattern();
		String suffix = "";
		
		PatternBody patternBody = SpecificationFactory.eINSTANCE.createPatternBody();
		patternBody.setHeader(pattern);
		
		String patternName = patternDef.getName();
		patternCache.put(patternName, pattern);
		PatternInvocation invocation = createPatternInvocation(patternName, pattern);
		for(PatternParameters pp : patternDef.getParameters()){
			ObjectVariableDefinition ov = ObjectVariableDefinition.class.cast(pp);
			EMFVariable patternVariable = EMFTypeFactory.eINSTANCE.createEMFVariable();
			pattern.getSymbolicParameters().add(patternVariable);
			
			patternVariable.setName(ov.getName());
			patternVariable.setEClassifier(ov.getType());
			
			CFVariable cfVar = env.get(ov.getName());
			
			if(bindings.get(cfVar.getName())){
				suffix += "B";
			}else{
				suffix += "F";
			}
			
			VariableReference vr = DemoclesFactory.eINSTANCE.createVariableReference();
			vr.setInvocation(invocation);
			vr.setFrom(cfVar);
			vr.setTo(patternVariable);
			
			
		}
		
		pattern.setName(patternNameGenerator.apply(suffix));
	}
	
	
	
	private PatternInvocation createPatternInvocation(String readAblePatternName, Pattern pattern){
		PatternInvocation invocation = DemoclesFactory.eINSTANCE.createRegularPatternInvocation(); //TODO find correct Pattern invocation
		invocation.setPattern(pattern);
		patternInvocationCache.put(readAblePatternName, invocation);
		return invocation;
	}
	
	public PatternInvocation getPatternInvocation(String patternName){
		return patternInvocationCache.get(patternName);
	}
	
	

}
