package org.moflon.ide.visualization.dot.ecore.epackageviz;

import org.moflon.core.utilities.EMoflonPlugin;

public class EPackageVisualizationPlugin extends EMoflonPlugin {
	public static EPackageVisualizationPlugin getDefault(){
		EPackageVisualizationPlugin plugin = getPlugin(EPackageVisualizationPlugin.class);
		if(plugin == null)
			throw new IllegalStateException("Plugin not set yet/anymore!");
		return plugin;
	}
}
