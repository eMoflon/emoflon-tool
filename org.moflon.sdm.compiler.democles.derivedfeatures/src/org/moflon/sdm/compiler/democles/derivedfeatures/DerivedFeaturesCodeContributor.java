package org.moflon.sdm.compiler.democles.derivedfeatures;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
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

   /**
    * @deprecated Currently not supported
    */
   @Override
   public String getConstructorInjectionCode(GenClass genClass)
   {
      final StringBuilder stringBuilder = new StringBuilder();
      return stringBuilder.toString();
   }
}
