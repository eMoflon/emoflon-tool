package org.moflon.ide.metamodelevolution.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.ui.admin.handlers.AbstractCommandHandler;

public class MetamodelEvolutionHandler extends AbstractCommandHandler
{

   @Override
   public Object execute(ExecutionEvent event) throws ExecutionException
   {
      System.out.println("Start refactoring");

      final ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
      final List<IProject> projects = new ArrayList<>();
      if (selection instanceof StructuredSelection)
      {
         final StructuredSelection structuredSelection = (StructuredSelection) selection;
         for (final Iterator<?> selectionIterator = structuredSelection.iterator(); selectionIterator.hasNext();)
         {
            projects.addAll(getProjects(selectionIterator.next()));
         }
      } else if (selection instanceof ITextSelection)
      {
         final IFile file = getEditedFile(event);
         final IProject project = file.getProject();
         try
         {
            if (project.isAccessible() && (project.hasNature(WorkspaceHelper.REPOSITORY_NATURE_ID) || project.hasNature(WorkspaceHelper.INTEGRATION_NATURE_ID)))
            {
               projects.add(project);
            }
         } catch (CoreException e)
         {
            e.printStackTrace();
         }
      }
      refactor(projects);

      return null;
   }

   private void refactor(List<IProject> projects)
   {
      if (!projects.isEmpty())
      {
         final Job job = new MetamodelEvolutionJob("eMoflon Refactoring", projects);

         job.setUser(true);
         job.schedule();
      }
   }
}
