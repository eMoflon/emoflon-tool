package org.moflon.core.utilities;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * This is a generic base class for plugins in EMoflon.
 * 
 * Its {@link #pluginId} attribute is initialized during
 * {@link #start(BundleContext)} and de-initialized during
 * {@link #stop(BundleContext)}.
 * 
 * @deprecated This class was basically introduced to easily and consistently access plugin IDs. 
 * In fact, this features is already built into Eclipse using {@link FrameworkUtil#getBundle(Class)}
 * Example: new Status(IStatus.OK, FrameworkUtil.getBundle(getClass()).getSymbolicName(), "My message.");
 */
@Deprecated
public abstract class EMoflonPlugin extends Plugin {
	private String pluginId;
	private static Map<Class<?>, EMoflonPlugin> typeToPluginMap = new HashMap<>();

	public String getPluginId() {
		if (pluginId == null)
			throw new IllegalStateException("Plugin ID has not yet been set!");
		else
			return pluginId;
	}
	
	@SuppressWarnings("unchecked")
	protected static <T> T getPlugin(Class<T> clazz) {
		return (T) typeToPluginMap.get(clazz);
	}


	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		pluginId = context.getBundle().getSymbolicName();
		typeToPluginMap.put(this.getClass(), this);
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		pluginId = null;
		typeToPluginMap.remove(this.getClass());
		super.stop(context);
	}
}
