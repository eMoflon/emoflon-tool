package org.moflon.tutorial.validationrules;

import org.moflon.core.utilities.EMoflonUIPlugin;

public class ValidationRulesPlugin extends EMoflonUIPlugin {
	public static ValidationRulesPlugin getDefault() {
		ValidationRulesPlugin plugin = getPlugin(ValidationRulesPlugin.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
