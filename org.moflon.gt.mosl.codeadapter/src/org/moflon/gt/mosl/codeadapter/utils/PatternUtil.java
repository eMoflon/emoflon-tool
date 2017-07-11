package org.moflon.gt.mosl.codeadapter.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;
import org.moflon.gt.mosl.moslgt.PatternDef;
import org.moflon.gt.mosl.moslgt.PatternParameter;
import org.moflon.sdm.runtime.democles.PatternInvocation;

public class PatternUtil
{
   private static Map<String, String> belongingClassNames = new HashMap<>();
   private static Map<String, PatternKind> belongingKind = new HashMap<>();
   private static Map<String, Integer> bindingHelper = new HashMap<>();
   
   public static int getCounts(String varName){
      int count = bindingHelper.getOrDefault(varName, 0);
      bindingHelper.put(varName, count+1);
      return count;
   }
   
   public static void setCountsToZero(){
      bindingHelper.clear();
   }
   
   public static ObjectVariableDefinition getCorrespondingOV(PatternParameter pp, PatternDef patternDef){
      Optional<ObjectVariableDefinition> optOV = patternDef.getObjectVariables().stream().filter(ov -> ov.getName().compareTo(pp.getOv().getName())==0).findAny();
      if(optOV.isPresent())
         return optOV.get();
      else
         return pp.getOv();
   }
   
   public static String getSafeName(String name){
      if("this".equals(name))
         return "this";
      else return name;
   }
   
   public static String getClassNameByInvocation(String invocation){
      return belongingClassNames.get(invocation);
   }
   
   public static void cleanNames(){
      belongingClassNames.clear();
      belongingKind.clear();
   }
   
   public static PatternKind getKind(String patternName){
      return belongingKind.get(patternName);
   }
   
   public static void addKind(String patternName, PatternKind pk){
      belongingKind.put(patternName,pk);
   }
   
   public static void add(PatternInvocation invocation, EClass eClass){
      EcoreUtil.resolveAll(eClass);
      EPackage ePackage = eClass.getEPackage();
      String fqn = ePackage.getNsPrefix() + "." + eClass.getName();
      belongingClassNames.put(invocation.toString(), fqn);
   }
}
