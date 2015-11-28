package org.example.processor.internal;

import org.moflon.core.utilities.EMoflonPlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends EMoflonPlugin {
	public static Activator getDefault() {
		Activator plugin = getPlugin(Activator.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
