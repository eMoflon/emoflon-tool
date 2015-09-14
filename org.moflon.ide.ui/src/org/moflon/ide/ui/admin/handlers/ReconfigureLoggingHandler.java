package org.moflon.ide.ui.admin.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.moflon.ide.ui.UIActivator;

/**
 * Triggers a reload of the logging configuration. 
 */
public class ReconfigureLoggingHandler extends AbstractCommandHandler
{

   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      UIActivator.getDefault().reconfigureLogging();
      return null;
   }

}
