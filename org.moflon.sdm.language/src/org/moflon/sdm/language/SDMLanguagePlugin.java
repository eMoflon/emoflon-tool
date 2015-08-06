package org.moflon.sdm.language;

import org.moflon.core.utilities.EMoflonPlugin;

public class SDMLanguagePlugin extends EMoflonPlugin {
	public static SDMLanguagePlugin getDefault() {
		SDMLanguagePlugin plugin = getPlugin(SDMLanguagePlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
