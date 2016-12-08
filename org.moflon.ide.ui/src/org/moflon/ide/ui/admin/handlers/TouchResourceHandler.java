package org.moflon.ide.ui.admin.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.moflon.ide.core.CoreActivator;
import org.osgi.framework.FrameworkUtil;

public class TouchResourceHandler extends AbstractCommandHandler
{
   @Override
   public Object execute(final ExecutionEvent event) throws ExecutionException
   {
      final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
      
      final Collection<IResource> resources = extractResouresFromSelection(selection);
      WorkspaceJob job = new WorkspaceJob("Touching selected resources") {
         @Override
         public IStatus runInWorkspace(final IProgressMonitor monitor)
         {
            final SubMonitor subMon = SubMonitor.convert(monitor, "Touching resources", resources.size());
            final MultiStatus status = new MultiStatus(FrameworkUtil.getBundle(getClass()).getSymbolicName(), 0, "Problems during resetting and cleaning", null);
            for (final IResource resource : resources)
            {
               try
               {
                  resource.touch(subMon.split(1));
               } catch (CoreException e)
               {
                  status.add(new Status(IStatus.WARNING, FrameworkUtil.getBundle(TouchResourceHandler.class).getSymbolicName(), "Problem while touching " + resource));
               }
               CoreActivator.checkCancellation(subMon);
            }
            return status.isOK() ? Status.OK_STATUS : status;
         }
      };
      job.setUser(true);
      job.setRule(new MultiRule(resources.toArray(new IResource[resources.size()])));
      job.schedule();

      return null;
   }

   private Collection<IResource> extractResouresFromSelection(final IStructuredSelection selection)
   {
      final Collection<IResource> resources = new ArrayList<>();
      if (selection instanceof StructuredSelection)
      {
         final StructuredSelection structuredSelection = (StructuredSelection) selection;
         for (final Iterator<?> selectionIterator = structuredSelection.iterator(); selectionIterator.hasNext();)
         {
            final Object element = selectionIterator.next();
            if (element  instanceof IResource)
            {
               final IResource resource = (IResource) element;
               resources .add(resource);
            }
         }
      }
      return resources;
   }
}
