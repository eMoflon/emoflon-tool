package org.moflon.ide.visualization.dot.tgg.delta;

import org.moflon.core.utilities.EMoflonPlugin;

public class DeltaVisualizationPlugin extends EMoflonPlugin {
	public static DeltaVisualizationPlugin getDefault(){
		DeltaVisualizationPlugin plugin = getPlugin(DeltaVisualizationPlugin.class);
		if(plugin == null)
			throw new IllegalStateException("Plugin not set yet/anymore!");
		return plugin;
	}
}
