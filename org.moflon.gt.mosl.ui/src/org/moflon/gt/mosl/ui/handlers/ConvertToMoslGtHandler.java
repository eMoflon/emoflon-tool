package org.moflon.gt.mosl.ui.handlers;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.ide.ui.admin.handlers.AbstractCommandHandler;

public class ConvertToMoslGtHandler extends AbstractCommandHandler
{

   @Override
   public Object execute(ExecutionEvent event) throws ExecutionException
   {
      final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
      final Collection<IProject> projects = AbstractCommandHandler.getProjectsFromSelection(selection);
      
      for (final IProject project : projects)
      {
         final ConvertToMoslGtJob job = new ConvertToMoslGtJob(project);
         job.setUser(true);
         job.setRule(project);
         job.schedule();
      }
      
      return null;
   }

}
