package org.moflon.gt.mosl.codeadapter.transformplanrules;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.moflon.gt.mosl.codeadapter.utils.MOSLUtil;
import org.moflon.gt.mosl.codeadapter.utils.PatternKind;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.gt.mosl.moslgt.AbstractAttribute;
import org.moflon.gt.mosl.moslgt.AttributeAssignment;
import org.moflon.gt.mosl.moslgt.AttributeConstraint;
import org.moflon.gt.mosl.moslgt.Expression;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.NACAndObjectVariable;
import org.moflon.gt.mosl.moslgt.NACGroup;
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
   
   protected abstract boolean filterConditionNACAndObjectVariable(NACAndObjectVariable nov, Map<String, Boolean> bindings, Map<String, CFVariable> env);
   protected abstract boolean filterConditionLinkVariable(LinkVariablePattern lv, ObjectVariableDefinition ov, Map<String, Boolean> bindings, Map<String, CFVariable> env);
   protected abstract boolean filterConditionExpression(Expression expr, Map<String, Boolean> bindings, Map<String, CFVariable> env);
   
   private boolean filterConditionAbstractAttribute(AbstractAttribute aa, Map<String, Boolean> bindings, Map<String, CFVariable> env){
      return filterConditionExpression(aa.getValueExp(), bindings, env);
   }
   public boolean isTransformable(PatternKind patternKind, PatternDef patternDef, Map<String, Boolean> bindings, Map<String, CFVariable> env){
      patternObjectIndex.clear();
      
      final Set<ObjectVariableDefinition> objectVariableSet = new TreeSet<>();
      List<ObjectVariableDefinition> ovs = MOSLUtil.mapToSubtype(patternDef.getVariables(), ObjectVariableDefinition.class);
      objectVariableSet.addAll(ovs);
      objectVariableSet.addAll(patternDef.getParameters().stream().map(pp -> PatternUtil.getCorrespondingOV(pp, patternDef)).collect(Collectors.toSet()));
      
      Predicate<NACAndObjectVariable> novFilter = nov -> filterConditionNACAndObjectVariable(nov, bindings, env);
      Function<ObjectVariableDefinition, Predicate<? super LinkVariablePattern>> linkVariableFilterFun = ov ->lv -> filterConditionLinkVariable(lv, ov, bindings, env);
      Function<ObjectVariableDefinition, Predicate<? super AttributeAssignment>> assignmentFilterFun = ov ->as -> filterConditionAbstractAttribute(as, bindings, env);
      Function<ObjectVariableDefinition, Predicate<? super AttributeConstraint>> constraintFilterFun = ov ->ac -> filterConditionAbstractAttribute(ac, bindings, env);
      
      PatternUtil.collectObjects(patternObjectIndex, objectVariableSet, novFilter, linkVariableFilterFun, assignmentFilterFun, constraintFilterFun);
      
      return patternObjectIndex.size() > 0;
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
               return 4;
            else if (po instanceof NACGroup)
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
