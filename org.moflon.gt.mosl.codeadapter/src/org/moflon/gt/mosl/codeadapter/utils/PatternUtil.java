package org.moflon.gt.mosl.codeadapter.utils;

import java.util.Optional;

import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternParameter;

public class PatternUtil
{
   public static ObjectVariableDefinition getCorrespondingOV(PatternParameter pp, PatternDef patternDef)
   {
      Optional<ObjectVariableDefinition> optOV = patternDef.getVariables().stream().filter(var -> var instanceof ObjectVariableDefinition)
            .map(ObjectVariableDefinition.class::cast).filter(ov -> ov.getName().compareTo(pp.getOv().getName()) == 0).findAny();
      if (optOV.isPresent())
         return optOV.get();
      else
         return pp.getOv();
   }

   public static String getNormalizedVariableName(String name)
   {
      // TODO@rkluge: Implement proper normalization (e.g., by inserting _)
      return name;
   }
}
