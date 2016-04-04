package org.moflon.tgg.mosl.builder;

import org.moflon.core.utilities.EMoflonPlugin;

public class MOSLTGGPlugin extends EMoflonPlugin{
	
	public static MOSLTGGPlugin getDefault(){
		MOSLTGGPlugin plugin = getPlugin(MOSLTGGPlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}

}
