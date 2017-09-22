package org.moflon.gt.mosl.codeadapter.utils;

import org.moflon.gt.mosl.moslgt.ObjectVariablePattern;
import org.moflon.gt.mosl.moslgt.PatternDef;

import java.util.List;
import java.util.stream.Collectors;

import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.Variable;


public enum VariableVisibility {
   GLOBAL, LOCAL;
   
public static VariableVisibility getVisibility(ObjectVariablePattern ov, PatternDef patternDef) {
   if(patternDef == null || isGlobal(ov, patternDef)){
      return VariableVisibility.GLOBAL;
   }else{
      return VariableVisibility.LOCAL;
   }
}

private static boolean isGlobal(ObjectVariablePattern ov, PatternDef patternDef){
   List<ObjectVariablePattern> paramOVs = patternDef.getParameters().stream().map(pp -> pp.getOv()).collect(Collectors.toList());
   return MOSLUtil.exist(paramOVs, paramOV->paramOV.getName().compareTo(ov.getName())==0) 
         || MOSLUtil.exist(MOSLUtil.mapToSubtype(patternDef.getVariables(), ObjectVariablePattern.class), innerOV -> innerOV.getName().compareTo(ov.getName())==0);
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
