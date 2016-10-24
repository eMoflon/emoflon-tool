package org.moflon.ide.ui.admin.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.git.GitResetProjectHelper;
import org.moflon.ide.ui.UIActivator;

public class GitResetProjectHandler extends AbstractCommandHandler
{
   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);

      final Collection<IProject> projects = getProjectsFromSelection(selection);

      if (!showWarningDialog())
    	  return null;
      
      try
      {
         WorkspaceJob job = new WorkspaceJob("Git-Reset selected Projects") {
            @Override
            public IStatus runInWorkspace(final IProgressMonitor monitor)
            {
               Status status = new Status(IStatus.OK, UIActivator.getModuleID(), IStatus.OK, "", null);
               if (projects.size() == 0) 
            	   logger.debug("You need at least one selection within your workspace for Git-Reset to find the repository.");
                              
               for (final IProject project : projects)
               {
                  if(GitResetProjectHelper.resetAndCleanWorkspace(project));
                  	break;
               }
               return status;
            }
         };
         job.setUser(true);
         job.setRule(ResourcesPlugin.getWorkspace().getRoot());
         job.schedule();
      } catch (Exception e)
      {
         throw new ExecutionException("Could not git-reset project", e);
      }

      return null;
   }

   private static Collection<IProject> getProjectsFromSelection(final IStructuredSelection selection)
   {
      final List<IProject> projects = new ArrayList<>();
      if (selection instanceof StructuredSelection)
      {
         final StructuredSelection structuredSelection = (StructuredSelection) selection;
         for (final Iterator<?> selectionIterator = structuredSelection.iterator(); selectionIterator.hasNext();)
         {
            projects.addAll(getProjects(selectionIterator.next()));
         }
      }

      return projects;
   }
   
   private static boolean showWarningDialog() {
	   int n = JOptionPane.showConfirmDialog(null, "This will undo all changes and reset the project to HEAD. Proceed?", "Warning", JOptionPane.YES_NO_OPTION);
	   if (n == 0) 
		   return true;
	   return false;
   }
}
