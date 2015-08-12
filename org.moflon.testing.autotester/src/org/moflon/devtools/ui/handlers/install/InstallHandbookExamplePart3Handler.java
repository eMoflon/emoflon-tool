package org.moflon.devtools.ui.handlers.install;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.moflon.ide.workspaceinstaller.psf.EMoflonStandardWorkspaces;

public class InstallHandbookExamplePart3Handler extends AbstractInstallCommandHandler
{
   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      this.getWorkspaceController(event).installWorkspaceByName(EMoflonStandardWorkspaces.HANDBOOK_EXAMPLE_PART3);
      return null;
   }

}
