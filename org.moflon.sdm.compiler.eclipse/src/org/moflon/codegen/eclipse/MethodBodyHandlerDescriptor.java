package org.moflon.codegen.eclipse;

import org.eclipse.core.runtime.Platform;

public class MethodBodyHandlerDescriptor {
	private String pluginID;

	private String className;

	public MethodBodyHandlerDescriptor(String pluginID, String className) {
		this.pluginID = pluginID;
		this.className = className;
	}

	public Class<?> lookupClass() throws ClassNotFoundException {
		return Platform.getBundle(pluginID).loadClass(className);
	}

}
