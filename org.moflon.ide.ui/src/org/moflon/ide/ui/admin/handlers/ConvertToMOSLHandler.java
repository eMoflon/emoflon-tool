package org.moflon.ide.ui.admin.handlers;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.runtime.builders.MOSLBuilder;
import org.moflon.ide.ui.UIActivator;

/**
 * Handler for ConvertToMOSLCommand
 * 
 * The handler is invoked on a single project.
 *
 */
public class ConvertToMOSLHandler extends AbstractCommandHandler
{
   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      return execute(HandlerUtil.getCurrentSelectionChecked(event));
   }

   private Object execute(final ISelection selection)
   {
      if (selection instanceof StructuredSelection)
      {
         final StructuredSelection structuredSelection = (StructuredSelection) selection;

         final Object element = structuredSelection.getFirstElement();
         getProjects(element).stream().forEach(p -> convertProject(p, logger));
      }
      return null;
   }

   private static void convertProject(final IProject project, final Logger logger)
   {
      if (!MessageDialog
            .openConfirm(
                  Display.getCurrent().getActiveShell(),
                  "Beta status",
                  "This action creates the MOSL project in the folder '_MOSL'. The EAP file is not deleted and the project nature is not yet converted, since this functionaliy is not stable! Continue?"))
      {
         return;
      }

      final Job job = new Job("Converting to textual syntax (MOSL)") {

         @Override
         protected IStatus run(final IProgressMonitor monitor)
         {
            IStatus status = null;
            try
            {

               // Open monitor and start task
               monitor.beginTask("Converting " + project.getName(), 10);

               MOSLBuilder.convertEAPProjectToMOSL(project);

               project.refreshLocal(IProject.DEPTH_INFINITE, monitor);
               WorkspaceHelper.addNature(project, CoreActivator.MOSL_NATURE_ID, monitor);
               logger.debug("Convert done");

               status = new Status(IStatus.OK, UIActivator.getModuleID(), IStatus.OK, "", null);
            } catch (final OperationCanceledException e)
            {
               status = new Status(IStatus.CANCEL, UIActivator.getModuleID(), IStatus.OK, "", null);
            } catch (final CoreException e)
            {
               status = new Status(IStatus.ERROR, UIActivator.getModuleID(), IStatus.OK, "", e);
            } finally
            {
               monitor.done();
            }
            return status;
         }
      };

      job.setUser(true);
      job.schedule();
   }

}
