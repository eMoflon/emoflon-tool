package org.moflon.ide.ui.admin.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.ide.ui.UIActivator;

/**
 * Opens the 'New meta-model' wizard. 
 */
public class NewMetamodelHandler extends AbstractCommandHandler
{

   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
      
      try
      {
         UIActivator.openWizard(UIActivator.NEW_METAMODEL_WIZARD_ID, window);
      } catch (final Exception e)
      {
         logger.error("Unable to open 'New meta-model' wizard", e);
         e.printStackTrace();
      }
      
      return null;
   }

}
