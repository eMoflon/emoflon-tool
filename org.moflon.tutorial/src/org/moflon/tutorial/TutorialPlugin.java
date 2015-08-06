package org.moflon.tutorial;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.moflon.ide.core.CoreActivator;
import org.osgi.framework.BundleContext;

public class TutorialPlugin extends AbstractUIPlugin {

	private static String bundleId;
	
	public static String getBundleId() {
		return bundleId;
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		bundleId = context.getBundle().getSymbolicName();
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		bundleId = null;
		super.stop(context);
	}
}
