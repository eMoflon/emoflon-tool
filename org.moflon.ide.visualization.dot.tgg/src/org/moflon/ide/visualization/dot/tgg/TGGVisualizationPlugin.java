package org.moflon.ide.visualization.dot.tgg;

import org.moflon.core.utilities.EMoflonPlugin;

public class TGGVisualizationPlugin extends EMoflonPlugin {
	public static TGGVisualizationPlugin getDefault() {
		TGGVisualizationPlugin plugin = getPlugin(TGGVisualizationPlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin not set yet/anymore!");
		return plugin;
	}
}
