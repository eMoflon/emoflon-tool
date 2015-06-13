package org.moflon.core.utilities;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class MoflonUtilitiesActivator extends Plugin
{
   public final static String PLUGIN_ID = "org.moflon.core.utilities";

   // The shared instance
   private static MoflonUtilitiesActivator plugin;

   // Singleton instance
   public static MoflonUtilitiesActivator getDefault()
   {
      return plugin;
   }

   /**
    * Used to retrieve resources embedded in the plugin (jar files when installed on client machine).
    * 
    * @param filePath
    *           Must be relative to the plugin root and indicate an existing resource (packaged in build)
    * @param pluginId
    *           The id of the plugin bundle to be searched
    * @return URL to the resource or null if nothing was found (URL because resource could be inside a jar).
    */
   public static URL getPathRelToPlugIn(final String filePath, final String pluginId)
   {
      try
      {
         return FileLocator.resolve(Platform.getBundle(pluginId).getEntry(filePath));
      } catch (Exception e)
      {
         e.printStackTrace();
         return null;
      }
   }

   @Override
   public void start(final BundleContext context) throws Exception
   {
      super.start(context);
      plugin = this;
   }

   @Override
   public void stop(final BundleContext context) throws Exception
   {
      plugin = null;
      super.stop(context);
   }
}
