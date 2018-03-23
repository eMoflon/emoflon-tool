package org.moflon;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class TGGLanguageActivator extends Plugin {
	@Override
	public void start(BundleContext context) throws Exception
	{
	   super.start(context);
	   //EMoflonPreferencesActivator.getDefault().getPreferencesStorage().setPreferredPlatformUriType(PlatformUriType.PLUGIN);;
	}
}
