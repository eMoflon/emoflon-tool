package org.moflon.gt.democles.codegen;

import java.util.List;

import org.gervarro.democles.codegen.ImportManager;

public class MOSLGTImportManager implements ImportManager
{
   private MOSLGTImportManager(ImportManager importManager)
   {
      oldImportManager = importManager;
   }

   private static MOSLGTImportManager instance;

   public static void createInstance(ImportManager importManager)
   {
      instance = new MOSLGTImportManager(importManager);
   }

   private ImportManager oldImportManager;

   public static MOSLGTImportManager getInstance()
   {
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
      //TODO@rkluge: Tentatively commented out
      //      String isPatternName = PatternUtil.getClassNameByInvocation(fullyQualifiedName);
      //      if(isPatternName == null)
      return oldImportManager.getImportedName(fullyQualifiedName);
      //      else
      //         return oldImportManager.getImportedName(isPatternName);
   }

   @Override
   public void upload(String fullyQualifiedName)
   {
      oldImportManager.upload(fullyQualifiedName);
   }
}
