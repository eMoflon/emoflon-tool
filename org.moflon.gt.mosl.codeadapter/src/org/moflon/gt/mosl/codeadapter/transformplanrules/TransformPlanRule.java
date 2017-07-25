package org.moflon.gt.mosl.codeadapter.transformplanrules;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.gt.mosl.moslgt.AbstractAttribute;
import org.moflon.gt.mosl.moslgt.Expression;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternObject;
import org.moflon.sdm.runtime.democles.CFVariable;

public abstract class TransformPlanRule
{
   protected PatternKind patternKind;
   protected Set<PatternObject> patternObjectIndex;
   
   public TransformPlanRule(PatternKind patternKind){
      this.patternKind=patternKind;
      this.patternObjectIndex = new HashSet<>();
   }
   
   protected abstract boolean filterConditionObjectVariable(ObjectVariableDefinition ov, Map<String, Boolean> bindings, Map<String, CFVariable> env);
   protected abstract boolean filterConditionLinkVariable(LinkVariablePattern lv, ObjectVariableDefinition ov, Map<String, Boolean> bindings, Map<String, CFVariable> env);
   protected abstract boolean filterConditionExpression(Expression expr, Map<String, Boolean> bindings, Map<String, CFVariable> env);
   
   private boolean filterConditionAbstractAttribute(AbstractAttribute aa, Map<String, Boolean> bindings, Map<String, CFVariable> env){
      return filterConditionExpression(aa.getValueExp(), bindings, env);
   }
   public boolean isTransformable(PatternKind patternKind, PatternDef patternDef, Map<String, Boolean> bindings, Map<String, CFVariable> env){
      patternObjectIndex.clear();
      List<ObjectVariableDefinition> objectVariables = patternDef.getVariables().stream().filter(var -> var instanceof ObjectVariableDefinition).map(ObjectVariableDefinition.class::cast).collect(Collectors.toList());
      Predicate<? super ObjectVariableDefinition> ovFilter = ov -> filterConditionObjectVariable(ov, bindings, env);
      patternObjectIndex.addAll(patternDef.getParameters().stream().map(pp -> {return PatternUtil.getCorrespondingOV(pp, patternDef);}).filter(ovFilter).collect(Collectors.toSet()));
      patternObjectIndex.addAll(objectVariables.stream().filter(ovFilter).collect(Collectors.toSet()));
      objectVariables.stream().forEach(ov -> {indexObjectVariable(ov, bindings, env);});
      return patternObjectIndex.size() > 0;
   }
   
   private void indexObjectVariable(ObjectVariableDefinition ov, Map<String, Boolean> bindings, Map<String, CFVariable> env){
      patternObjectIndex.addAll(ov.getLinkVariablePatterns().stream().filter(lv -> filterConditionLinkVariable(lv, ov, bindings, env)).collect(Collectors.toSet()));
      patternObjectIndex.addAll(ov.getAttributeAssignments().stream().filter(as -> filterConditionAbstractAttribute(as, bindings, env)).map(as -> {return as.getValueExp();}).collect(Collectors.toSet()));
      patternObjectIndex.addAll(ov.getAttributeConstraints().stream().filter(ac -> filterConditionAbstractAttribute(ac, bindings, env)).map(ac -> {return ac.getValueExp();}).collect(Collectors.toSet()));
   }
   
   
   public List<PatternObject> getPatterObjectIndex(){
      List<PatternObject> returner = new ArrayList<>(patternObjectIndex);
      returner.sort(new Comparator<PatternObject>() {

         @Override
         public int compare(PatternObject o1, PatternObject o2)
         {
            int po1 = getPriorityOfPatternObject(o1);
            int po2 = getPriorityOfPatternObject(o2);
            
            if(po1<po2)
               return 1;
            else if(po1>po2)
               return -1;
            else
               return 0;
         }
         
         private int getPriorityOfPatternObject(PatternObject po){
            if(po instanceof ObjectVariableDefinition)
               return 3;
            else if(po instanceof LinkVariablePattern)
               return 2;
            else if(po instanceof Expression)
               return 1;
            else
               return 0;
         }
      });
      return returner;
   }
   
   
}
