package org.moflon.gt.mosl.codeadapter.utils;

import java.util.Optional;

import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternParameter;

public class PatternUtil
{
   public static ObjectVariableDefinition getCorrespondingOV(PatternParameter pp, PatternDef patternDef){
      Optional<ObjectVariableDefinition> optOV = patternDef.getObjectVariables().stream().filter(ov -> ov.getName().compareTo(pp.getOv().getName())==0).findAny();
      if(optOV.isPresent())
         return optOV.get();
      else
         return pp.getOv();
   }
   
   public static String getSaveName(String name){
      if("this".compareTo(name)==0)
         return "_this";
      else return name;
   }
}
