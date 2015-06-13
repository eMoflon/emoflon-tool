package org.moflon.devtools.ui.handlers.install;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.moflon.ide.workspaceinstaller.psf.EMoflonStandardWorkspaces;

public class InstallDevAndTestWorkspacesHandler extends AbstractInstallCommandHandler
{

   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      this.getWorkspaceController(event).installWorkspacesByName(EMoflonStandardWorkspaces.getAllMoflonWorkspaces(), "Developer and all Test Workspaces");
      return null;
   }

}
