package org.moflon.ide.debug.core;

import org.moflon.core.utilities.EMoflonUIPlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends EMoflonUIPlugin {
	public static Activator getDefault() {
		Activator plugin = getPlugin(Activator.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
