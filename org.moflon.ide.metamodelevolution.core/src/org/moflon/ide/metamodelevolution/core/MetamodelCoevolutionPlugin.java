package org.moflon.ide.metamodelevolution.core;

import org.moflon.core.utilities.EMoflonPlugin;

public class MetamodelCoevolutionPlugin extends EMoflonPlugin
{
   public static MetamodelCoevolutionPlugin getDefault()
   {
      MetamodelCoevolutionPlugin plugin = getPlugin(MetamodelCoevolutionPlugin.class);
      if (plugin == null)
         throw new IllegalStateException("Plugin has not yet been set!");
      return plugin;
   }
}
