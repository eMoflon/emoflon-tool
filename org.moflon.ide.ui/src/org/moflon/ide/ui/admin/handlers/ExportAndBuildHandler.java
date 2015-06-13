package org.moflon.ide.ui.admin.handlers;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.ea.EnterpriseArchitectHelper;
import org.moflon.ide.ui.UIActivator;

public class ExportAndBuildHandler extends AbstractCommandHandler
{

   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);

      final Set<IProject> projects = getMetamodelProjectsFromSelection(selection);

      try
      {
         Job job = new Job("eMoflon Export and Build") {
            @Override
            public IStatus run(final IProgressMonitor pm)
            {
               try
               {
                  Status status = new Status(IStatus.OK, UIActivator.getModuleID(), IStatus.OK, "", null);
                  pm.beginTask("Exporting metamodels to Eclipse...", 2 * projects.size());
                  for (final IProject project : projects)
                  {
                     pm.worked(1);
                     try
                     {
                        EnterpriseArchitectHelper.delegateToEnterpriseArchitect(project, WorkspaceHelper.createSubmonitorWith1Tick(pm));
                     } catch (IOException | InterruptedException e)
                     {
                        status = new Status(IStatus.ERROR, UIActivator.getModuleID(), e.getMessage());
                     }
                  }
                  return status;
               } finally
               {
                  pm.done();
               }

            }
         };
         job.setUser(true);
         job.schedule();
      } catch (Exception e)
      {
         throw new ExecutionException("Could not export metamodel to Eclipe", e);
      }

      return null;
   }

   private static Set<IProject> getMetamodelProjectsFromSelection(final IStructuredSelection selection)
   {
      Set<IProject> result = null;
      final Iterator<?> iterator = selection.iterator();
      if (iterator != null)
      {
         while (iterator.hasNext())
         {
            final Object next = iterator.next();
            if (next instanceof IResource)
            {
               final IResource res = (IResource) next;
               final IProject project = res.getProject();
               if (project != null)
               {
                  if (result == null)
                  {
                     result = new HashSet<IProject>();
                  }
                  try
                  {
                     final String[] natureIds = project.getDescription().getNatureIds();
                     if (Arrays.asList(natureIds).contains(CoreActivator.METAMODEL_NATURE_ID))
                     {
                        result.add(project);
                     }
                  } catch (final CoreException e)
                  {
                     e.printStackTrace();
                  }
               }
            }
         }
      }

      return result;
   }
}
