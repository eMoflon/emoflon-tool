package org.moflon.ide.visualization.dot.sdm;

import org.moflon.core.utilities.EMoflonPlugin;

public class SDMVisualizationPlugin extends EMoflonPlugin {
	public static SDMVisualizationPlugin getDefault() {
		SDMVisualizationPlugin plugin = getPlugin(SDMVisualizationPlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
