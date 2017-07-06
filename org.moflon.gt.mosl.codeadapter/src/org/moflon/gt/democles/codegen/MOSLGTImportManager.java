package org.moflon.gt.democles.codegen;

import java.util.List;

import org.gervarro.democles.codegen.ImportManager;
import org.moflon.gt.mosl.codeadapter.utils.PatternUtil;
import org.moflon.sdm.runtime.democles.PatternInvocation;

public class MOSLGTImportManager implements ImportManager
{
   private MOSLGTImportManager(ImportManager importManager){
      oldImportManager = importManager;
   }
   
   private static MOSLGTImportManager instance;
   //private static Collection<String> importList;
   
   public static void createInstance(ImportManager importManager){
      instance = new MOSLGTImportManager(importManager);
      //importList = instance.getImportList();
   }
   
   private ImportManager oldImportManager;
   
   public static MOSLGTImportManager getInstance(){
      return instance;
   }

   @Override
   public List<String> getImportList()
   {
      return oldImportManager.getImportList();
   }

   @Override
   public String getImportedName(String fullyQualifiedName)
   {
      String isPatternName = PatternUtil.getClassNameByInvocation(fullyQualifiedName);
      if(isPatternName == null)
         return oldImportManager.getImportedName(fullyQualifiedName);
      else
         return oldImportManager.getImportedName(isPatternName);
   }

   @Override
   public void upload(String fullyQualifiedName)
   {
      oldImportManager.upload(fullyQualifiedName);      
   }
   
   public static void getNameByInvocation(PatternInvocation invocation){
      instance.upload( PatternUtil.getClassNameByInvocation(invocation.toString()));
   }
}
