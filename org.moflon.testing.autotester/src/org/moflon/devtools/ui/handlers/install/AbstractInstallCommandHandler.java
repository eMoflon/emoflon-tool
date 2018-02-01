package org.moflon.devtools.ui.handlers.install;

import org.moflon.autotest.core.EnterpriseArchitectAwareWorkspaceInstaller;
import org.moflon.autotest.core.WorkspaceInstaller;
import org.moflon.core.ui.AbstractCommandHandler;

/**
 * Parent class for all command handlers that checkout workspaces.
 *
 * @author Roland Kluge
 */
public abstract class AbstractInstallCommandHandler extends AbstractCommandHandler
{
   /**
    * Returns the {@link WorkspaceInstaller} to be used by the subclasses
    * @return the {@link WorkspaceInstaller}
    */
   protected final EnterpriseArchitectAwareWorkspaceInstaller getWorkspaceController()
   {
      return new EnterpriseArchitectAwareWorkspaceInstaller();
   }

}
