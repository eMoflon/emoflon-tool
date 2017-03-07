package org.moflon.gt.mosl.codeadapter.statementrules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.codeadapter.codeadapter.PatternGenerator;
import org.moflon.gt.mosl.exceptions.NoMatchingVariableFound;
import org.moflon.gt.mosl.exceptions.PatternParameterSizeIsNotMatching;
import org.moflon.gt.mosl.moslgt.CalledPatternParameter;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternParameters;
import org.moflon.sdm.runtime.democles.CFNode;
import org.moflon.sdm.runtime.democles.CFVariable;
import org.moflon.sdm.runtime.democles.DemoclesFactory;
import org.moflon.sdm.runtime.democles.PatternInvocation;
import org.moflon.sdm.runtime.democles.Action;
import org.moflon.sdm.runtime.democles.Scope;
import org.moflon.sdm.runtime.democles.VariableReference;

public interface IHandlePatternsInStatement extends IHandleCFVariable{
	default void handlePattern(List<CalledPatternParameter> cpps, PatternDef patternDef, CFNode cfNode, Scope scope){
		Map<CFVariable, Boolean> bindings = new HashMap<>();
		List<Consumer<PatternInvocation>> setInvocations = new ArrayList<>();
		List<Consumer<PatternInvocation>> setConstructors = new ArrayList<>();
		
		List<PatternParameters> patternParameters = patternDef.getParameters(); 
		int size = patternParameters.size();
		if(size!=cpps.size())
			throw new PatternParameterSizeIsNotMatching();
		
		for(int index = 0; index < size; index++ ){

			VariableReference vr = DemoclesFactory.eINSTANCE.createVariableReference();
			setInvocations.add(invocation -> {vr.setInvocation(invocation);});
			
			ObjectVariableDefinition ovRef = cpps.get(index).getDefiningOV();
			if(ovRef == null){
				 ovRef = cpps.get(index).getExistingOV();
			}
			
			CFVariable cfVar = getOrCreateVariable(scope, ovRef.getName(), ovRef.getType());
			vr.setFrom(cfVar);
			Action constructor = cfVar.getConstructor();
			if(constructor == null){
				setConstructors.add(invocation ->{ cfVar.setConstructor(invocation);});
				bindings.put(cfVar, false);
			}
			else{
				bindings.put(cfVar,true);
			}
		}
		
		PatternInvocation invocation = DemoclesFactory.eINSTANCE.createRegularPatternInvocation(); //TODO find correct Pattern invocation
		
		cfNode.setMainAction(invocation);
		invocation.setCfNode(cfNode);
		
		Pattern pattern = PatternGenerator.getInstance().getPattern(patternDef.getName(), bindings);
		if(size != pattern.getSymbolicParameters().size())
			throw new PatternParameterSizeIsNotMatching();
		
		invocation.setPattern(pattern);		
		
		for(int index = 0; index < size; index++ ){
			setInvocations.get(index).accept(invocation);
			setConstructors.get(index).accept(invocation);
			ObjectVariableDefinition patternParOV = ObjectVariableDefinition.class.cast(patternParameters.get(index));
			Variable var = pattern.getSymbolicParameters().get(index);
			if(var.getName().compareTo(patternParOV.getName())!= 0)
				throw new NoMatchingVariableFound();			
			VariableReference vr = invocation.getParameters().get(index);
			vr.setTo(var);
		}
	}
	

}
