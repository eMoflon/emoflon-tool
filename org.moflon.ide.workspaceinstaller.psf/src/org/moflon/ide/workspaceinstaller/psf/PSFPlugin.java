package org.moflon.ide.workspaceinstaller.psf;

import org.moflon.core.utilities.EMoflonPlugin;

public class PSFPlugin extends EMoflonPlugin {

	public static PSFPlugin getDefault() {
		PSFPlugin plugin = getPlugin(PSFPlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
