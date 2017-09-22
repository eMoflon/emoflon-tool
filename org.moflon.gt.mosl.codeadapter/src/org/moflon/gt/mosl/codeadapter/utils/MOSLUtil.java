package org.moflon.gt.mosl.codeadapter.utils;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.moflon.gt.mosl.moslgt.MoslgtFactory;
import org.moflon.gt.mosl.moslgt.ObjectVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableStatement;
public class MOSLUtil
{
   public static <T> List<T> mapToSubtype(Collection<? super T> list, Class<T> clazz)
   {
      return list.stream().filter(clazz::isInstance).map(clazz::cast).collect(Collectors.toList());
   }

   public static <T> boolean exist(List<T> list, Predicate<? super T> predicate){
      return list.stream().anyMatch(predicate);
   }
   
   public static <T> List<T> mapToSupertype(Collection <? extends T> subTypes, Class<T> clazz)
   {
      return subTypes.stream().map(clazz::cast).collect(Collectors.toList());
   }
   
   public static ObjectVariablePattern convertOVStatementToOVPattern(ObjectVariableStatement ovStatement){
      ObjectVariablePattern ovPat = MoslgtFactory.eINSTANCE.createObjectVariablePattern();
      ovPat.setName(ovStatement.getName());
      ovPat.setOp(ovStatement.getOp());
      ovPat.setType(ovStatement.getType());
      if(ovStatement.getAssignment() != null)
         ovPat.setAssignment(convertOVStatementToOVPattern(ovStatement.getAssignment()));      
      return ovPat;
   }
}
