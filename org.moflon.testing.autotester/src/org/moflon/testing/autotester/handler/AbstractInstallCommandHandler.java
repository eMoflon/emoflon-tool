package org.moflon.testing.autotester.handler;

import org.moflon.core.ui.AbstractCommandHandler;
import org.moflon.testing.autotester.EnterpriseArchitectAwareWorkspaceInstaller;

/**
 * Parent class for all command handlers that checkout workspaces.
 *
 * @author Roland Kluge
 */
public abstract class AbstractInstallCommandHandler extends AbstractCommandHandler {
	/**
	 * Returns the {@link EnterpriseArchitectAwareWorkspaceInstaller} to be used by
	 * the subclasses
	 * 
	 * @return the {@link EnterpriseArchitectAwareWorkspaceInstaller}
	 */
	protected final EnterpriseArchitectAwareWorkspaceInstaller getWorkspaceController() {
		return new EnterpriseArchitectAwareWorkspaceInstaller();
	}

}
