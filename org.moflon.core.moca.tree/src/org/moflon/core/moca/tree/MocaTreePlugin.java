package org.moflon.core.moca.tree;

import org.moflon.core.utilities.EMoflonPlugin;

public class MocaTreePlugin extends EMoflonPlugin {
	public static MocaTreePlugin getDefault() {
		MocaTreePlugin plugin = getPlugin(MocaTreePlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}

}
