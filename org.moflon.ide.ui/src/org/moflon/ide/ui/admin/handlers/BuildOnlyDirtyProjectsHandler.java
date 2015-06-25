package org.moflon.ide.ui.admin.handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler for the 'Build' command that only rebuilds dirty projects.
 * 
 * May be called on several projects.
 * 
 * The command performs a clean prior to building, which is especially necessary due to the Democles code generation
 * process.
 */
public class BuildOnlyDirtyProjectsHandler extends AbstractCommandHandler
{

   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {

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
         projects.add(project);
      }

      cleanAndBuild(projects);

      return null;
   }

   private void cleanAndBuild(final List<IProject> projects)
   {
      if (!projects.isEmpty())
      {

         final Job job = new EMoflonBuildJobForDirtyProjects("eMoflon Building (only dirty projects)", projects);

         job.setUser(true);
         job.schedule();
      }
   }
}
