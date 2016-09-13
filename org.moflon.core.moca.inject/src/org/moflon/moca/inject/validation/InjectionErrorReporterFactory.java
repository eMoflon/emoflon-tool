package org.moflon.moca.inject.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;

public class InjectionErrorReporterFactory implements IAdapterFactory
{

   @SuppressWarnings("unchecked")
   @Override
   public Object getAdapter(final Object adaptableObject, @SuppressWarnings("rawtypes")
   final Class adapterType)
   {
      if (adaptableObject instanceof IProject && InjectionErrorReporter.class == adapterType)
      {
         return new InjectionErrorReporter((IProject)adaptableObject);
      }
      return null;
   }

   @Override
   public Class<?>[] getAdapterList()
   {
      return new Class[] { InjectionErrorReporter.class };
   }
}
