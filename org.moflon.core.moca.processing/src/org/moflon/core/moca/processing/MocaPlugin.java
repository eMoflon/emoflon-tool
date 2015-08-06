package org.moflon.core.moca.processing;

import org.moflon.core.utilities.EMoflonPlugin;

public class MocaPlugin extends EMoflonPlugin {
	public static MocaPlugin getDefault() {
		MocaPlugin plugin = getPlugin(MocaPlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
