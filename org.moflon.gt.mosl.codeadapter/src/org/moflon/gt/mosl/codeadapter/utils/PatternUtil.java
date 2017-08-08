package org.moflon.gt.mosl.codeadapter.utils;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.moflon.gt.mosl.moslgt.AttributeAssignment;
import org.moflon.gt.mosl.moslgt.AttributeConstraint;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternObject;
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

   public static void collectObjects(Collection<PatternObject> collect, Collection<ObjectVariableDefinition> objectVariables){
      collectObjects(collect, objectVariables, PatternUtil::alwaysTrue, PatternUtil::getDefaultFromObjectVariable,
            PatternUtil::getDefaultFromObjectVariable, PatternUtil::getDefaultFromObjectVariable);
   }
   
   public static void collectObjects(Collection<PatternObject> collect, Collection<ObjectVariableDefinition> objectVariables, 
         Predicate<? super ObjectVariableDefinition> objectVariableFilter, Function<ObjectVariableDefinition, Predicate<? super LinkVariablePattern>> linkVariableFilterFun,
         Function<ObjectVariableDefinition, Predicate<? super AttributeAssignment>> assignmentFilterFun, 
         Function<ObjectVariableDefinition, Predicate<? super AttributeConstraint>> constraintFilterFun){
      collect.addAll(objectVariables.stream().filter(objectVariableFilter).collect(Collectors.toList()));
      objectVariables.forEach(objectVariable -> collectInnerObjects(collect, objectVariable, linkVariableFilterFun.apply(objectVariable), 
            assignmentFilterFun.apply(objectVariable), constraintFilterFun.apply(objectVariable)));
   }
   
   private static void collectInnerObjects(Collection<PatternObject> collect, ObjectVariableDefinition objectVariable, Predicate<? super LinkVariablePattern> linkVariableFilter,
         Predicate<? super AttributeAssignment> assignmentFilter, Predicate<? super AttributeConstraint> constraintFilter){
      collect.addAll(objectVariable.getLinkVariablePatterns().stream().filter(linkVariableFilter).collect(Collectors.toList()));
      collect.addAll(objectVariable.getAttributeAssignments().stream().filter(assignmentFilter).map(as -> as.getValueExp()).collect(Collectors.toSet()));
      collect.addAll(objectVariable.getAttributeConstraints().stream().filter(constraintFilter).map(as -> as.getValueExp()).collect(Collectors.toSet()));
   }
   
   private static boolean alwaysTrue(Object obj){
      return true;
   }
   
   private static Predicate<Object> getDefaultFromObjectVariable(ObjectVariableDefinition ov) {
      return PatternUtil::alwaysTrue;
   }
   
   public static String getNormalizedVariableName(String name)
   {
      // TODO@rkluge: Implement proper normalization (e.g., by inserting _)
      return name;
   }
}
