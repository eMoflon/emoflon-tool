package org.moflon.ide.visualisation.dot.language;

import org.moflon.core.utilities.EMoflonPlugin;

public class DotVisualisationPlugin extends EMoflonPlugin {
	public static DotVisualisationPlugin getDefault() {
		DotVisualisationPlugin plugin = getPlugin(DotVisualisationPlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
