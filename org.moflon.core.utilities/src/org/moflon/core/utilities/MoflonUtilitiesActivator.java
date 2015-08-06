package org.moflon.core.utilities;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

public class MoflonUtilitiesActivator extends EMoflonPlugin {

	public static MoflonUtilitiesActivator getDefault() {
		MoflonUtilitiesActivator plugin = getPlugin(MoflonUtilitiesActivator.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}

	/**
	 * Used to retrieve resources embedded in the plugin (jar files when
	 * installed on client machine).
	 * 
	 * @param filePath
	 *            Must be relative to the plugin root and indicate an existing
	 *            resource (packaged in build)
	 * @param pluginId
	 *            The id of the plugin bundle to be searched
	 * @return URL to the resource or null if nothing was found (URL because
	 *         resource could be inside a jar).
	 */
	public static URL getPathRelToPlugIn(final String filePath, final String pluginId) {
		try {
			return FileLocator.resolve(Platform.getBundle(pluginId).getEntry(filePath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
