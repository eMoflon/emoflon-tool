package org.moflon.ide.ui.admin.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * Null implementation of {@link AbstractCommandHandler}
 * @author Roland Kluge - Initial implementation
 */
public class NoActionCommandHanlder extends AbstractCommandHandler
{

   @Override
   public Object execute(ExecutionEvent event) throws ExecutionException
   {
      return null;
   }

}
