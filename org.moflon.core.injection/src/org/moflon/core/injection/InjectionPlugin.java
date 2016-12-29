package org.moflon.core.injection;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class InjectionPlugin extends Plugin
{

   private static BundleContext context;
   private static String bundleId;

   public static BundleContext getContext()
   {
      return context;
   }

   public static final String getBundleId()
   {
      if (bundleId == null)
      {
         throw new NullPointerException();
      } else
      {
         return bundleId;
      }
   }


   @Override
   public void start(final BundleContext bundleContext) throws Exception
   {
      InjectionPlugin.context = bundleContext;
      bundleId = context.getBundle().getSymbolicName();
   }

   @Override
   public void stop(final BundleContext bundleContext) throws Exception
   {
      InjectionPlugin.context = null;
      bundleId = null;
   }

}
