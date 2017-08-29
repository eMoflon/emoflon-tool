package org.moflon.gt.mosl.codeadapter.utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MOSLUtil
{
   public static <T> List<T> mapToSubtype(List<? super T> list, Class<T> clazz)
   {
      return list.stream().filter(clazz::isInstance).map(clazz::cast).collect(Collectors.toList());
   }

   public static <T> boolean exist(List<T> list, Predicate<? super T> predicate){
      return list.stream().anyMatch(predicate);
   }
   
}
