package org.moflon.tutorial;

import org.moflon.core.utilities.EMoflonUIPlugin;

public class TutorialPlugin extends EMoflonUIPlugin {
	public static TutorialPlugin getDefault() {
		TutorialPlugin plugin = getPlugin(TutorialPlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
