package org.moflon.devtools.ui.handlers.install;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.moflon.ide.workspaceinstaller.psf.EMoflonStandardWorkspaces;

public class InstallDemoMOSLWorkspaceHandler extends AbstractInstallCommandHandler
{

   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      this.getWorkspaceController(event).installWorkspaceByName(EMoflonStandardWorkspaces.DEMO_WORKSPACE_MOSL_NAME);
      return null;
   }

}