package org.moflon.devtools.ui.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.moflon.devtools.ui.handlers.install.AbstractInstallCommandHandler;
import org.moflon.ide.workspaceinstaller.psf.EMoflonStandardWorkspaces;

public class InstallModulesHandler extends AbstractInstallCommandHandler
{
   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      final String workspaceName = event.getParameter("org.moflon.devtools.ui.commands.install.workspacename");
      final List<String> psfFiles = EMoflonStandardWorkspaces.getPathToPsfFileForWorkspace(workspaceName);
      if (!psfFiles.isEmpty())
      {
         logger.debug("Installing workspace  '" + workspaceName + "' using the following PSFs " + psfFiles);
         this.getWorkspaceController(event).installWorkspaceByName(workspaceName);
      } else
      {
         logger.info("No PSF files found for workspace '" + workspaceName + "'");
      }
      return null;
   }
}
