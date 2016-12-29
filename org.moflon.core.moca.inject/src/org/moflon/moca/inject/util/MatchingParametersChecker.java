package org.moflon.moca.inject.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.moflon.core.utilities.MoflonUtil;

/**
 * This component checks whether an EOperation has a given parameter sequence.
 */
public class MatchingParametersChecker
{

   /**
    * Returns whether the parameters of the given EOperation expose the given parameter names and types.
    * 
    * @param eOperation
    * @param parameterNames
    * @param parameterTypes
    * @return
    */
   public boolean haveMatchingParamters(EOperation eOperation, List<String> parameterNames, List<String> parameterTypes)
   {
      if (haveDifferentSize(parameterNames, parameterTypes))
         throw new IllegalArgumentException("Parameter names and parameter types have different size!");

      List<EParameter> eParameters = eOperation.getEParameters();
      if (haveDifferentSize(parameterNames, eParameters))
         return false;

      Iterator<EParameter> eParamIterator = eParameters.iterator();
      Iterator<String> paramNameIterator = parameterNames.iterator();
      Iterator<String> paramTypeIterator = parameterTypes.iterator();

      boolean hasMatchingParameters = true;
      while (eParamIterator.hasNext() && hasMatchingParameters)
      {
         hasMatchingParameters = checkWhetherParameterMatchesTypeAndName(eParamIterator.next(), paramNameIterator.next(), paramTypeIterator.next());
      }

      return hasMatchingParameters;
   }

   /**
    * Returns whether the given EParameter has the given name and type
    * 
    * @param eParameter
    * @param parameterName
    * @param parameterType
    * @return
    */
   private boolean checkWhetherParameterMatchesTypeAndName(EParameter eParameter, String parameterName, String parameterType)
   {
      boolean hasMatchingParameters;
      if (!eParameter.getName().equals(parameterName))
      {
         hasMatchingParameters = false;
      } else
      {
         // Precondition: instance type name is fully qualified
         final String instanceTypeName = eParameter.getEType().getInstanceClassName();
         final String ecoreTypeName = MoflonUtil.getFQN(eParameter.getEType());

         // instanceTypeName has priority over ecoreTypeName
         final String metamodelTypeNameForComparison = (instanceTypeName != null) ? instanceTypeName : ecoreTypeName; 
         
         final String qualifiedMetamodelTypeName = metamodelTypeNameForComparison;
         final String dequalifiedMetamodelTypeName = dequalifyClassName(metamodelTypeNameForComparison);
         
         hasMatchingParameters = parameterType.equals(qualifiedMetamodelTypeName) || parameterType.equals(dequalifiedMetamodelTypeName);
      }
      return hasMatchingParameters;
   }

   /**
    * Removes the package prefix of a qualified class name.
    * 
    * If the class is unqualified, returns the input string
    * 
    * @param className
    *           the potentially qualified class name
    * @return the dequalified class name
    */
   private String dequalifyClassName(String className)
   {
      int indexOfLastDot = className.lastIndexOf(".");
      return className.substring(indexOfLastDot + 1);
   }

   private boolean haveDifferentSize(Collection<?> list1, Collection<?> list2)
   {
      return list2.size() != list1.size();
   }
}
