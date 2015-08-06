package org.moflon;

import org.moflon.core.utilities.EMoflonPlugin;

public class TGGLanguageActivator extends EMoflonPlugin {
	public static TGGLanguageActivator getDefault() {
		TGGLanguageActivator plugin = getPlugin(TGGLanguageActivator.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
