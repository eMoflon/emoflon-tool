package org.moflon.ide.visualization.dot.tgg.schema;

import org.moflon.core.utilities.EMoflonPlugin;

public class SchemaVisualizationPlugin extends EMoflonPlugin {
	public static SchemaVisualizationPlugin getDefault(){
		SchemaVisualizationPlugin plugin = getPlugin(SchemaVisualizationPlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin not set yet/anymore!");
		return plugin;
	}
}
