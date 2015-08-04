package org.moflon.sdm.compiler.democles;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class DemoclesSdmCompilerPlugin extends Plugin
{
   private static String bundleId;

   public static String getModuleID()
   {
      if (bundleId == null)
         throw new NullPointerException();
      else
         return bundleId;
   }

   @Override
   public void start(final BundleContext context) throws Exception
   {
      super.start(context);
      bundleId = context.getBundle().getSymbolicName();// TODO Auto-generated method stub

   }

   @Override
   public void stop(final BundleContext context) throws Exception
   {
      super.stop(context);
   }

}
