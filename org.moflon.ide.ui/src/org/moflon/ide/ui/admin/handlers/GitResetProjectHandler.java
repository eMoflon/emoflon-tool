package org.moflon.ide.ui.admin.handlers;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.ide.core.git.GitHelper;
import org.moflon.ide.ui.UIActivator;

public class GitResetProjectHandler extends AbstractCommandHandler
{
   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);

      final Collection<IProject> projects = AbstractCommandHandler.getProjectsFromSelection(selection);

      if (projects.size() == 0)
      {
         MessageDialog.openInformation(null, "Selection must contain a project",
               "You need at least one selection within your workspace for Git-Reset to find the repository.");
         return null;
      }

      if (!showWarningDialog())
         return null;

      WorkspaceJob job = new WorkspaceJob("Git-Reset selected Projects") {
         @Override
         public IStatus runInWorkspace(final IProgressMonitor monitor)
         {
            Status status = new Status(IStatus.OK, UIActivator.getModuleID(), IStatus.OK, "", null);

            for (final IProject project : projects)
            {
               final IStatus resetStatus = GitHelper.resetAndCleanContainingGitRepository(project);
               if (resetStatus.matches(Status.ERROR))
                  return resetStatus;
            }
            return status;
         }
      };
      job.setUser(true);
      job.setRule(ResourcesPlugin.getWorkspace().getRoot());
      job.schedule();

      return null;
   }

   private static boolean showWarningDialog()
   {
      final boolean userHasConfirmed = MessageDialog.openConfirm(null, "Confirm reset", "This will undo all changes and reset the project to HEAD. Proceed?");
      return userHasConfirmed;
   }
}
