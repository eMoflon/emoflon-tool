package org.moflon;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class CSPCodeAdapterActivator extends Plugin {

	// The shared instance
	private static CSPCodeAdapterActivator plugin;

	// Singleton instance
	public static CSPCodeAdapterActivator getDefault() {
		return plugin;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

}
