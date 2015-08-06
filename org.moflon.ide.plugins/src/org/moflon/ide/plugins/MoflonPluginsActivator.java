package org.moflon.ide.plugins;

import org.moflon.core.utilities.EMoflonPlugin;

public class MoflonPluginsActivator extends EMoflonPlugin {
	public static MoflonPluginsActivator getDefault() {
		MoflonPluginsActivator plugin = getPlugin(MoflonPluginsActivator.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
