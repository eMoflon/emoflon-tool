package org.moflon.ide.ui.admin.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.ide.ui.UIActivator;

/**
 * Opens the eMoflon logging configuration file for editing.
 */
public class EditLoggingConfigurationHandler extends AbstractCommandHandler
{

   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
      UIActivator.getDefault().openConfigFileInEditor(window);
      return null;
   }

}
