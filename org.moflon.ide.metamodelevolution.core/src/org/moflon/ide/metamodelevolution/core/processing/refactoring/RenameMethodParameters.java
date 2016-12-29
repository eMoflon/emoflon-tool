package org.moflon.ide.metamodelevolution.core.processing.refactoring;

public class RenameMethodParameters
{
   public static String getFactoryMethodName(String methodName)
   {
      return "create" + methodName;
   }
   
   public static String getFactoryClassName(String packageName)
   {
      return packageName.substring(packageName.lastIndexOf(".") + 1) + "Factory";
   }
   
   public static String getPackageClassName(String packageName)
   {
      return packageName.substring(packageName.lastIndexOf(".") + 1) + "Package";
   }
   
   public static String getPackageMethodName(String methodName)
   {
      return "get" + methodName;
   }
}
