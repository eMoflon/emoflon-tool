package org.moflon;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class TGGLanguageActivator extends Plugin
{

   @Deprecated // Use #getModuleID() instead
   public static final String PLUGIN_ID = "TGGLanguage";

   // The shared instance
   private static TGGLanguageActivator plugin;

   private static String bundleId;

   // Singleton instance
   public static TGGLanguageActivator getDefault()
   {
      return plugin;
   }

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
      plugin = this;
      bundleId = context.getBundle().getSymbolicName();
   }

   @Override
   public void stop(final BundleContext context) throws Exception
   {
      bundleId = null;
      plugin = null;
      super.stop(context);
   }

}
