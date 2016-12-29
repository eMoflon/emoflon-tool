package org.moflon.devtools.ui.handlers.install;

import org.eclipse.core.commands.ExecutionEvent;
import org.moflon.autotest.core.WorkspaceInstaller;
import org.moflon.devtools.ui.handlers.AbstractCommandHandler;

/**
 * Parent class for all command handlers that checkout workspaces.
 *
 */
public abstract class AbstractInstallCommandHandler extends AbstractCommandHandler
{

   protected WorkspaceInstaller getWorkspaceController(final ExecutionEvent event)
   {
      return new WorkspaceInstaller();
   }

}
