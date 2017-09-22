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
import org.moflon.gt.mosl.moslgt.ObjectVariablePattern;
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
   
   protected abstract boolean filterConditionNACAndObjectVariable(NACAndObjectVariable nov, Map<String, CFVariable> env);
   protected abstract boolean filterConditionLinkVariable(LinkVariablePattern lv, ObjectVariablePattern ov, Map<String, CFVariable> env);
   protected abstract boolean filterConditionExpression(Expression expr, Map<String, CFVariable> env);
   
   private boolean filterConditionAbstractAttribute(AbstractAttribute aa, Map<String, CFVariable> env){
      return filterConditionExpression(aa.getValueExp(), env);
   }
   public boolean isTransformable(PatternKind patternKind, PatternDef patternDef, Map<String, CFVariable> env){
      patternObjectIndex.clear();
      
      List<ObjectVariablePattern> ovs = MOSLUtil.mapToSubtype(patternDef.getVariables(), ObjectVariablePattern.class);
      
      final Set<ObjectVariablePattern> objectVariableSet = new TreeSet<>(new Comparator<ObjectVariablePattern>(){

         @Override
         public int compare(ObjectVariablePattern o1, ObjectVariablePattern o2)
         {
            boolean o1Container = ovs.contains(o1);
            boolean o2Container = ovs.contains(o2);
            if(o1Container && o2Container)
               return ovs.indexOf(o1)-ovs.indexOf(o2);
            else if(o1Container){
               return 1;
            }else if(o2Container){
               return -1;
            }else {
               return o1.getName().compareTo(o2.getName());
            }
         }
         
      });
      
      objectVariableSet.addAll(ovs);
      objectVariableSet.addAll(patternDef.getParameters().stream().map(pp -> PatternUtil.getCorrespondingOV(pp, patternDef)).collect(Collectors.toSet()));
      
      Predicate<NACAndObjectVariable> novFilter = nov -> filterConditionNACAndObjectVariable(nov, env);
      Function<ObjectVariablePattern, Predicate<? super LinkVariablePattern>> linkVariableFilterFun = ov ->lv -> filterConditionLinkVariable(lv, ov, env);
      Function<ObjectVariablePattern, Predicate<? super AttributeAssignment>> assignmentFilterFun = ov ->as -> filterConditionAbstractAttribute(as, env);
      Function<ObjectVariablePattern, Predicate<? super AttributeConstraint>> constraintFilterFun = ov ->ac -> filterConditionAbstractAttribute(ac, env);
      
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
            if(po instanceof ObjectVariablePattern)
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
