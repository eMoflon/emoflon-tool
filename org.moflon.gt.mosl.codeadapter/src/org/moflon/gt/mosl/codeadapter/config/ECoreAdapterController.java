package org.moflon.gt.mosl.codeadapter.config;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class ECoreAdapterController
{
   public void saveAsRegisteredAdapter(EObject objectToSave, EObject adaptedObject, String type, ResourceSet resourceSet)
   {
      cleanResourceSet(adaptedObject.eResource(), resourceSet);

      final Resource adapterResource = (Resource) EcoreUtil.getRegisteredAdapter(adaptedObject, type);
      if (adapterResource != null)
      {
         try
         {
            cleanResourceSet(adapterResource, resourceSet);
            adapterResource.getContents().add(objectToSave);
            adapterResource.save(Collections.EMPTY_MAP);
         } catch (IOException e)
         {
            e.printStackTrace();
         }
      }
   }

   private void cleanResourceSet(Resource res, ResourceSet resourceSet)
   {
      if (res != null)
      {
         URI resUri = res.getURI();
         Resource contextResource = resourceSet.getResource(resUri, false);
         if (contextResource != null)
            resourceSet.getResources().remove(contextResource);

         resourceSet.getResources().add(res);
      }
   }
}
