package org.moflon.sdm.compiler.democles.derivedfeatures;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.moflon.eclipse.genmodel.MoflonClassGeneratorCodeContributor;

public class DerivedFeaturesCodeContributor implements MoflonClassGeneratorCodeContributor
{
   private DerivedFeatureProcessor derivedFeatureProcessor;

   public DerivedFeaturesCodeContributor()
   {
      derivedFeatureProcessor = new DerivedFeatureProcessor();
   }

   @Override
   public String getPreGetGenFeatureCode(final GenFeature genFeature)
   {

      if (genFeature.isDerived())
      {
         String calcMethodName = "_get" + genFeature.getCapName();

         Set<EStructuralFeature> dependentFeatures = new HashSet<EStructuralFeature>();
         if (genFeature.getName().equals("systemName"))
         {
            dependentFeatures.addAll(DerivedFeatureSdmAnalyzer.analyzeSDM(genFeature, calcMethodName));
         }
         
         return derivedFeatureProcessor.generateDerivatedFeatureCode(genFeature, calcMethodName, dependentFeatures);
      } else
      {
         return null;
      }
   }

   @Override
   public String getPreSetGenFeatureCode(GenFeature genFeature)
   {
      // TODO Auto-generated method stub
      return null;
   }

}
