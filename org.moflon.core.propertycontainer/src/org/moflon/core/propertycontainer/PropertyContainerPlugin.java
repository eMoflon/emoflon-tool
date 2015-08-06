package org.moflon.core.propertycontainer;

import org.moflon.core.utilities.EMoflonPlugin;

public class PropertyContainerPlugin extends EMoflonPlugin {
	public static PropertyContainerPlugin getDefault() {
		PropertyContainerPlugin plugin = getPlugin(PropertyContainerPlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
