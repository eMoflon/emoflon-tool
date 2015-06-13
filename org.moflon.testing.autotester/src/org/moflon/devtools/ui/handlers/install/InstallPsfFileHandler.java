package org.moflon.devtools.ui.handlers.install;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;

public class InstallPsfFileHandler extends AbstractInstallCommandHandler
{

   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      final FileDialog dialog = new FileDialog(Display.getDefault().getActiveShell());
      dialog.setFilterExtensions(new String[] {"*.psf", "*.PSF"});
      dialog.setText("Choose your PSF file");
      
      this.getWorkspaceController(event).installWorkspaceWithPSF(dialog.open());
      return null;
   }

}
