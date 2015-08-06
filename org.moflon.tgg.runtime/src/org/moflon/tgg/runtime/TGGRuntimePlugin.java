package org.moflon.tgg.runtime;

import org.moflon.core.utilities.EMoflonPlugin;

public class TGGRuntimePlugin extends EMoflonPlugin {
	public static TGGRuntimePlugin getDefault() {
		TGGRuntimePlugin plugin = getPlugin(TGGRuntimePlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
