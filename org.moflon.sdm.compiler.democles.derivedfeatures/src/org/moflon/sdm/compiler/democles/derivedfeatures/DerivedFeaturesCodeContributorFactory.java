package org.moflon.sdm.compiler.democles.derivedfeatures;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.moflon.sdm.compiler.democles.derivedfeatures.DerivedFeaturesCodeContributor.AccessType;

public class DerivedFeaturesCodeContributorFactory implements IAdapterFactory
{
   /**
    * This map caches the code contributor for each {@link GenFeature}.
    */
   // private static final Map<GenFeature, DerivedFeaturesCodeContributor> adapterMap = new HashMap<>();
   private static DerivedFeaturesCodeContributor instance;

   @SuppressWarnings("unchecked")
   @Override
   public DerivedFeaturesCodeContributor getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType)
   {
      if ((adaptableObject instanceof GenFeature || adaptableObject instanceof GenClass) && adapterType == DerivedFeaturesCodeContributor.class)
      {
         // final GenFeature genFeature = (GenFeature) adaptableObject;
         // if (adapterMap.containsKey(genFeature)) {
         // return adapterMap.get(genFeature);
         // }
         // else {
         // DerivedFeaturesCodeContributor adapter = new DerivedFeaturesCodeContributor();
         // adapterMap.put(genFeature, adapter);
         // return adapter;
         // }
         if (instance == null)
            instance = new DerivedFeaturesCodeContributor(AccessType.PULL);

         return instance;
      }
      return null;
   }

   @Override
   public Class<?>[] getAdapterList()
   {
      return new Class<?>[] { DerivedFeaturesCodeContributor.class };
   }

}
