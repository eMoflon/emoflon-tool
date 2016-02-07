package org.moflon.sdm.compiler.democles.derivedfeatures;

import org.moflon.core.utilities.EMoflonPlugin;

public class DerivedFeaturesActivator extends EMoflonPlugin {
   public static DerivedFeaturesActivator getDefault() {
      DerivedFeaturesActivator plugin = getPlugin(DerivedFeaturesActivator.class);
      if (plugin == null)
         throw new IllegalStateException("Plugin has not yet been set!");
      return plugin;
   }
}
