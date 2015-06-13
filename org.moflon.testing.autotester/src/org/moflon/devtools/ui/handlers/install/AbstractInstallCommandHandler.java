package org.moflon.devtools.ui.handlers.install;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
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
      final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
      final WorkspaceInstaller workspaceController = new WorkspaceInstaller(window);
      return workspaceController;
   }

}
