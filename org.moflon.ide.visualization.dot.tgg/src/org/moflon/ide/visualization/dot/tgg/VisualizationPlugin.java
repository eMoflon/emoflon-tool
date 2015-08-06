package org.moflon.ide.visualization.dot.tgg;

import org.moflon.core.utilities.EMoflonPlugin;

public class VisualizationPlugin extends EMoflonPlugin {
	public static VisualizationPlugin getDefault() {
		VisualizationPlugin plugin = getPlugin(VisualizationPlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
