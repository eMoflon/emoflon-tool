package org.moflon.ide.visualization.dot.tgg.runtime;

import org.moflon.core.utilities.EMoflonPlugin;

public class DotTggRuntimePlugin extends EMoflonPlugin {
	public static DotTggRuntimePlugin getDefault() {
		DotTggRuntimePlugin plugin = EMoflonPlugin.getPlugin(DotTggRuntimePlugin.class);
		if (plugin == null) {
			throw new IllegalStateException("Plugin has not yet been loaded");
		}
		return plugin;
	}
}
