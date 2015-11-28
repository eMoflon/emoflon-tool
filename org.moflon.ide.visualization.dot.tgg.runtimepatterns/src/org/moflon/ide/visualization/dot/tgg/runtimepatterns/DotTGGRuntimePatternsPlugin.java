package org.moflon.ide.visualization.dot.tgg.runtimepatterns;

import org.moflon.core.utilities.EMoflonPlugin;
import org.osgi.framework.BundleActivator;

public class DotTGGRuntimePatternsPlugin extends EMoflonPlugin implements BundleActivator
{
   public static DotTGGRuntimePatternsPlugin getDefault() {
      DotTGGRuntimePatternsPlugin plugin = EMoflonPlugin.getPlugin(DotTGGRuntimePatternsPlugin.class);
      if (plugin == null) {
         throw new IllegalStateException("Plugin has not yet been loaded");
      }
      return plugin;
   }

}
