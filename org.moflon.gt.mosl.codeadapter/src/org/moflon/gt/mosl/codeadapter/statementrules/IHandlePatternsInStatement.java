package org.moflon.gt.mosl.codeadapter.statementrules;

import java.util.List;

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
import org.moflon.sdm.runtime.democles.Scope;
import org.moflon.sdm.runtime.democles.VariableReference;

public interface IHandlePatternsInStatement {
	default void handlePattern(List<CalledPatternParameter> cpps, PatternDef patternDef, CFNode cfNode, Scope scope){
		PatternInvocation invocation = DemoclesFactory.eINSTANCE.createRegularPatternInvocation(); //TODO find correct Pattern invocation
		Pattern pattern = PatternGenerator.getInstance().getPattern(patternDef.getName());
		cfNode.setMainAction(invocation);
		invocation.setCfNode(cfNode);
		invocation.setPattern(pattern);
		List<PatternParameters> patternParameters = patternDef.getParameters(); 
		int size = patternParameters.size();
		if(size != pattern.getSymbolicParameters().size() || size!=cpps.size())
			throw new PatternParameterSizeIsNotMatching();
		
		for(int index = 0; index < size; index++ ){
			ObjectVariableDefinition patternParOV = ObjectVariableDefinition.class.cast(patternParameters.get(index));
			VariableReference vr = DemoclesFactory.eINSTANCE.createVariableReference();
			vr.setInvocation(invocation);
			Variable var = pattern.getSymbolicParameters().get(index);
			if(var.getName().compareTo(patternParOV.getName())!= 0)
				throw new NoMatchingVariableFound();
			vr.setTo(var);
			
			CFVariable cfVar = DemoclesFactory.eINSTANCE.createCFVariable();
			cfVar.setScope(scope);
			vr.setFrom(cfVar);
			
			ObjectVariableDefinition ovRef = cpps.get(index).getDefiningOV();
			if(ovRef == null){
				 ovRef = cpps.get(index).getExistingOV();
			}
			cfVar.setName(ovRef.getName());
			cfVar.setType(ovRef.getType());
			
		}
		

	}
}
