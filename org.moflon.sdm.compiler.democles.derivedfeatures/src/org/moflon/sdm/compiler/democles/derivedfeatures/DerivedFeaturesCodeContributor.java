package org.moflon.sdm.compiler.democles.derivedfeatures;

import java.util.Set;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.moflon.eclipse.genmodel.MoflonClassGeneratorCodeContributor;

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
      String featureCode = null;
      if (accessType == AccessType.PULL && genFeature.isDerived())
      {
         final String calcMethodName = "_get" + genFeature.getCapName();
         featureCode =  derivedFeatureProcessor.generateDerivatedFeatureGetterCode(genFeature, calcMethodName);
      }
      
      return featureCode;
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
