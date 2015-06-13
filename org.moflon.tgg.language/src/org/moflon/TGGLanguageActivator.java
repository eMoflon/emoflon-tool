package org.moflon;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class TGGLanguageActivator extends Plugin
{

   public static final String PLUGIN_ID = "TGGLanguage";
   
   // The shared instance
   private static TGGLanguageActivator plugin;
   
   // Singleton instance
   public static TGGLanguageActivator getDefault()
   {
      return plugin;
   }
   
   @Override
   public void start(BundleContext context) throws Exception
   {
      super.start(context);
      plugin = this;
   }
   
   @Override
   public void stop(BundleContext context) throws Exception
   {
      plugin = null;
      super.stop(context);
   }

}
