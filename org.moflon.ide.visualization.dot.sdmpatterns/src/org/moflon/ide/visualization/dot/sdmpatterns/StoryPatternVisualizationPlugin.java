package org.moflon.ide.visualization.dot.sdmpatterns;

import org.moflon.core.utilities.EMoflonPlugin;

public class StoryPatternVisualizationPlugin extends EMoflonPlugin
{
   public static StoryPatternVisualizationPlugin getDefault() {
      StoryPatternVisualizationPlugin plugin = getPlugin(StoryPatternVisualizationPlugin.class);
      if (plugin == null)
         throw new IllegalStateException("Plugin has not yet been set!");
      return plugin;
   }
}
