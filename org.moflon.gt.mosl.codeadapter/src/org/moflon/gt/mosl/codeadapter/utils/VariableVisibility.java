package org.moflon.gt.mosl.codeadapter.utils;

import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.Variable;


public enum VariableVisibility {
   GLOBAL, LOCAL;
   
public static VariableVisibility getVisibility(ObjectVariableDefinition ov, PatternDef patternDef) {
   if(MOSLUtil.exist(patternDef.getParameters(), param -> param.getOv().getName().equals(ov.getName()))){
      return VariableVisibility.GLOBAL;
   }else{
      return VariableVisibility.LOCAL;
   }
}

public void addObjectVariableToToPattern(Pattern pattern, Variable variable){
   if(this == LOCAL){
      PatternBody patternBody = pattern.getBodies().get(0);
      patternBody.getLocalVariables().add(variable);
   }else {
      pattern.getSymbolicParameters().add(variable);
   }
}
}
