package org.moflon.devtools.ui.handlers.install;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class InstallTestsForTextualSyntaxHandler extends AbstractInstallCommandHandler
{

   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      this.getWorkspaceController(event).installTestWorkspaceTextual();
      return null;
   }

}
