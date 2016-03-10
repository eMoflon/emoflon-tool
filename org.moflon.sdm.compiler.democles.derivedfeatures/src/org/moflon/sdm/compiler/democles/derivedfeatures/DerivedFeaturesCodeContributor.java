package org.moflon.sdm.compiler.democles.derivedfeatures;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.moflon.eclipse.genmodel.MoflonClassGeneratorCodeContributor;

//TODO@aaltenkirch: Currently, the same instance of this class is re-used for generating all pieces of code. If this assumption is not valid, then a safe strategy would be to regenerate a fresh code contributor in DerivedFeaturesCodeContributorFactory whenever necessary. 
public class DerivedFeaturesCodeContributor implements MoflonClassGeneratorCodeContributor
{
   public enum AccessType { PULL, PUSH }
    
   private DerivedFeatureProcessor derivedFeatureProcessor;
   private AccessType accessType;
   
   public DerivedFeaturesCodeContributor(AccessType accessType)
   {
      this.accessType = accessType;
      derivedFeatureProcessor = new DerivedFeatureProcessor();
   }

   @Override
   public String getPreGetGenFeatureCode(final GenFeature genFeature)
   {
      if (genFeature.isDerived())
      {
         final String calcMethodName = "_get" + genFeature.getCapName();

         final Set<EStructuralFeature> dependentFeatures = new HashSet<EStructuralFeature>();
         if (genFeature.getName().equals("systemName"))
         {
            dependentFeatures.addAll(DerivedFeatureSdmAnalyzer.analyzeSDM(genFeature, calcMethodName));
         }

         return derivedFeatureProcessor.generateDerivatedFeatureGetterCode(genFeature, calcMethodName, dependentFeatures, accessType);
      } else
      {
         return null;
      }
   }

   @Override
   public String getPreSetGenFeatureCode(GenFeature genFeature)
   {
      // Empty implementation
      return null;
   }

   @Override
   public String getConstructorInjectionCode(GenClass genClass)
   {
      final StringBuilder stringBuilder = new StringBuilder();
      
      if (accessType == AccessType.PUSH)
      {
          for (final GenFeature genFeature : genClass.getGenFeatures())
          {
              if (genFeature.isDerived())
              {
                 final String calcMethodName = "_get" + genFeature.getCapName();
                 final Set<EStructuralFeature> dependentFeatures = DerivedFeatureSdmAnalyzer.analyzeSDM(genFeature, calcMethodName);
                 final String constructorInjectionCodeOfFeature = getConstructorInjectionCode(genFeature, dependentFeatures, calcMethodName);
                 if (constructorInjectionCodeOfFeature != null)
                 {
                    stringBuilder.append(constructorInjectionCodeOfFeature);
                 }
              }
          }
      }
      return stringBuilder.toString();
   }

   /**
    * Returns the portion of code that relates to the given {@link GenFeature} to be inserted into the constructor.
    * 
    * If no code is required, <code>null</code> is returned.
    * @param genFeature
    * @return
    */
   private String getConstructorInjectionCode(GenFeature genFeature, Set<EStructuralFeature> dependentFeatures, String calcMethodName)
   {
       return derivedFeatureProcessor.generateDerivatedFeatureConstructorCode(genFeature, calcMethodName, dependentFeatures);
   }
}
