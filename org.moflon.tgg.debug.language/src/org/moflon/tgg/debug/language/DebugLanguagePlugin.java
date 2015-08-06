package org.moflon.tgg.debug.language;

import org.moflon.core.utilities.EMoflonPlugin;

public class DebugLanguagePlugin extends EMoflonPlugin {
	public static DebugLanguagePlugin getDefault() {
		DebugLanguagePlugin plugin = getPlugin(DebugLanguagePlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
