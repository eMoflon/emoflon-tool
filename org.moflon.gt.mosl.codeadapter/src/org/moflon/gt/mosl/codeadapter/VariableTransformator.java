package org.moflon.gt.mosl.codeadapter;

import java.util.List;
import java.util.Map;

import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;

public class VariableTransformator
{
   private static VariableTransformator instance;
   
   private VariableTransformator(){
      
   }
   
   public static VariableTransformator getInstance(){
      if(instance == null)
         instance = new VariableTransformator();
      return instance;
   }
   
   public void transformPatternObjects(List<LinkVariablePattern> lvs, ObjectVariableDefinition ov, Variable variable, Map<String, Boolean> bindings, PatternBody patternBody){
      lvs.stream().forEach(lv -> {LinkVariableBuilder.getInstance().transformLinkVariable(lv, ov, patternBody);});
   }

}
