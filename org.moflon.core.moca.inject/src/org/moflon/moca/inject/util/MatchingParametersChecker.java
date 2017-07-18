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
    * Returns whether the parameters of the given EOperation expose the given parameter types.
    * 
    * @param eOperation
    * @param parameterTypes
    * @return
    */
   public boolean haveMatchingParamters(EOperation eOperation, List<String> parameterTypes)
   {

      final List<EParameter> eParameters = eOperation.getEParameters();
      if (haveDifferentSize(parameterTypes, eParameters))
         return false;

      Iterator<EParameter> eParamIterator = eParameters.iterator();
      Iterator<String> paramTypeIterator = parameterTypes.iterator();

      boolean hasMatchingParameters = true;
      while (eParamIterator.hasNext() && hasMatchingParameters)
      {
         hasMatchingParameters = checkWhetherParameterMatchesType(eParamIterator.next(), paramTypeIterator.next());
      }

      return hasMatchingParameters;
   }

   /**
    * Returns whether the given EParameter has the given type
    * 
    * @param eParameter
    * @param parameterType
    * @return
    */
   private boolean checkWhetherParameterMatchesType(EParameter eParameter, String parameterType)
   {
      boolean hasMatchingParameters;
      // Precondition: instance type name is fully qualified
      final String instanceTypeName = eParameter.getEType().getInstanceClassName();
      final String ecoreTypeName = MoflonUtil.getFQN(eParameter.getEType());

      // instanceTypeName has priority over ecoreTypeName
      final String metamodelTypeNameForComparison = (instanceTypeName != null) ? instanceTypeName : ecoreTypeName;

      final String qualifiedMetamodelTypeName = metamodelTypeNameForComparison;
      final String dequalifiedMetamodelTypeName = dequalifyClassName(metamodelTypeNameForComparison);

      hasMatchingParameters = parameterType.equals(qualifiedMetamodelTypeName) || parameterType.equals(dequalifiedMetamodelTypeName);
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
