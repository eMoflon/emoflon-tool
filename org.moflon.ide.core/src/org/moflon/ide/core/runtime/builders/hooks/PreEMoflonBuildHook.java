package org.moflon.ide.core.runtime.builders.hooks;

import org.eclipse.core.runtime.IStatus;

public interface PreEMoflonBuildHook {
	IStatus execute();
}
