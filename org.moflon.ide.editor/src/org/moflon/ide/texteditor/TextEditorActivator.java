package org.moflon.ide.texteditor;

import org.moflon.core.utilities.EMoflonUIPlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class TextEditorActivator extends EMoflonUIPlugin {
	public static TextEditorActivator getDefault() {
		TextEditorActivator plugin = getPlugin(TextEditorActivator.class);
		if (plugin == null)
			throw new IllegalStateException("Plugin has not yet been set!");
		return plugin;
	}
}
