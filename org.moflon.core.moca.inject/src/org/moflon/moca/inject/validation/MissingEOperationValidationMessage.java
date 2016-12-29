package org.moflon.moca.inject.validation;

import java.util.List;

public class MissingEOperationValidationMessage extends InjectionValidationMessage
{

   public MissingEOperationValidationMessage(final String methodName, final List<String> paramNames, final List<String> paramTypes, final String className,
         final String fileName)
   {
      super("Cannot find the EOperation " + getMethodSignature(methodName, paramNames, paramTypes) + " in class '" + className + "'.", fileName,
            InjectionValidationSeverity.WARNING);
   }

   private static String getMethodSignature(final String methodName, final List<String> paramNames, final List<String> paramTypes)
   {
      final StringBuilder sb = new StringBuilder();

      sb.append(methodName);
      sb.append("(");
      for (int i = 0; i < paramNames.size(); i++)
      {
         sb.append(paramTypes.get(i));
         sb.append(" ");
         sb.append(paramNames.get(i));
         if (i < paramNames.size() - 1)
            sb.append(", ");
      }
      sb.append(")");

      return sb.toString();
   }
}
