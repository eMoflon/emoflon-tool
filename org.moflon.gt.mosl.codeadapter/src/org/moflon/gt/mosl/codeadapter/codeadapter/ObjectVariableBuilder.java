package org.moflon.gt.mosl.codeadapter.codeadapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.codeadapter.objectvariablerules.OVTransformerRule;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.sdm.runtime.democles.CFVariable;

public class ObjectVariableBuilder {

	private List<OVTransformerRule> transformersRules;
	
	private static ObjectVariableBuilder instance;
	
	private ObjectVariableBuilder(){
		transformersRules = new LinkedList<>();
	}
	
	public static ObjectVariableBuilder getInstance(){
		if(instance == null)
			instance = new ObjectVariableBuilder();
		
		return instance;
	}
	
	public void addPatternTransformer(OVTransformerRule transformer){
		transformersRules.add(transformer);
	}
	
	public void transformObjectVariable(ObjectVariableDefinition ov, Variable variable, Map<String, Boolean> bindings, Pattern pattern){
		transformersRules.stream().forEachOrdered(transformerRule -> {transformerRule.transforming(ov, variable, bindings, pattern);});
	}
}
