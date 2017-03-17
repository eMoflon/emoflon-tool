package org.moflon.gt.mosl.codeadapter.codeadapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.SpecificationFactory;
import org.gervarro.democles.specification.emf.Variable;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFTypeFactory;
import org.gervarro.democles.specification.emf.constraint.emf.emf.EMFVariable;
import org.moflon.gt.mosl.moslgt.EClassDef;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternParameters;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.VariableReference;

public class PatternBuilder {
	private static PatternBuilder instance;
	
	private Map<String, List<Consumer<Variable>>> unfinishedLinkVariables; 
	
	private Map<String, Pattern> patternCache;
	
	private Map<String, PatternInvocation> patternInvocationCache;
	
	private PatternBuilder(){
		unfinishedLinkVariables = new HashMap<>();
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
		String patternKind = "";
		
		PatternBody patternBody = SpecificationFactory.eINSTANCE.createPatternBody();
		patternBody.setHeader(pattern);
		
		String patternName = patternDef.getName();
		patternCache.put(patternName, pattern);
		PatternInvocation invocation = createPatternInvocation(patternName, pattern);
		for(PatternParameters pp : patternDef.getParameters()){
			ObjectVariableDefinition ov = pp.getOv();
			EMFVariable patternVariable = EMFTypeFactory.eINSTANCE.createEMFVariable();
			pattern.getSymbolicParameters().add(patternVariable);
			
			patternVariable.setName(ov.getName());
			patternVariable.setEClassifier(ov.getType());

			finishLinkVariables(patternVariable);
			
			CFVariable cfVar = env.get(ov.getName());
			
			if(bindings.get(cfVar.getName())){
				suffix += "B";
			}else{
				suffix += "F";
			}
			
			patternKind=ObjectVariableBuilder.getInstance().transformObjectVariable(getCorrespondingOV(pp, patternDef), patternVariable, bindings, patternBody);
			
			VariableReference vr = DemoclesFactory.eINSTANCE.createVariableReference();
			vr.setInvocation(invocation);
			vr.setFrom(cfVar);
			vr.setTo(patternVariable);			
			
		}
		
		pattern.setName(patternNameGenerator.apply(patternKind+suffix));
		
	      EClass eClass = EClassDef.class.cast(StatementBuilder.getInstance().getCurrentMethod().eContainer()).getName();
	      CodeadapterTrafo.getInstance().loadResourceSet(eClass.eResource().getResourceSet());
	      Resource patternResource = (Resource) EcoreUtil.getRegisteredAdapter(eClass, patternKind);
	      if (patternResource != null)
	      {
	         patternResource.getContents().add(pattern);
	         try {
				pattern.eResource().save(Collections.EMPTY_MAP);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	}
	
	private ObjectVariableDefinition getCorrespondingOV(PatternParameters pp, PatternDef patternDef){
		Optional<ObjectVariableDefinition> optOV = patternDef.getObjectVariables().stream().filter(ov -> ov.getName().compareTo(pp.getOv().getName())==0).findAny();
		if(optOV.isPresent())
			return optOV.get();
		else
			return pp.getOv();
	}
	
	private void finishLinkVariables(Variable var){
		List<Consumer<Variable>> toFinishLst = unfinishedLinkVariables.get(var.getName());
		if(toFinishLst != null)
			toFinishLst.stream().forEach(finisher -> {finisher.accept(var);});
	}
	
	
	public void addUnfinishedLinkVaraible(String targetName, Consumer<Variable> lvFinisher){
		List<Consumer<Variable>> toFinishLst = unfinishedLinkVariables.get(targetName);
		if(toFinishLst == null){
			toFinishLst = new ArrayList<>();
			unfinishedLinkVariables.put(targetName, toFinishLst);
		}
		toFinishLst.add(lvFinisher);
	}
	
	private PatternInvocation createPatternInvocation(String readAblePatternName, Pattern pattern){
		PatternInvocation invocation = DemoclesFactory.eINSTANCE.createRegularPatternInvocation();
		invocation.setPattern(pattern);
		patternInvocationCache.put(readAblePatternName, invocation);
		return invocation;
	}
	
	public PatternInvocation getPatternInvocation(String patternName){
		return patternInvocationCache.get(patternName);
	}
	
	

}
