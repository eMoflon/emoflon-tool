package org.moflon.ide.core.runtime.builders;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.moflon.ide.core.CoreActivator;

public abstract class AbstractBuilder extends IncrementalProjectBuilder implements IResourceVisitor, IResourceDeltaVisitor
{
   private IProgressMonitor progressMonitorForIncrementalChanges;

   protected int kind = FULL_BUILD;

   @Override
   protected void clean(final IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, getProgressBarMessage(), 2);
      cleanResource(subMon.split(1));

      getProject().deleteMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
      monitor.worked(1);
   }

   @Override
   protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor) throws CoreException
   {
      final SubMonitor subMon = SubMonitor.convert(monitor, getProgressBarMessage(), 4);

      this.kind = kind;

      getProject().deleteMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
      subMon.worked(1);

      if (kind == FULL_BUILD)
      {
         // No changes -> perform full build
         processResource(subMon.split(3));
      } else
      {
         IResourceDelta delta = getDelta(getProject());
         if (delta != null)
         {
            try
            {
               progressMonitorForIncrementalChanges = SubMonitor.convert(subMon.split(3), "Processing incremental changes", IProgressMonitor.UNKNOWN);
               // Walk through changes using visitor
               delta.accept(this);
            } finally
            {
               progressMonitorForIncrementalChanges = null;
            }
         }
      }
      // We could return a list of projects for which the builder may want to
      // request resource deltas in the next invocation. E.g. referenced
      // projects. For now we just return null
      return null;
   }

   /**
    * Returns a progress monitor that may be used while processing incremental changes.
    * 
    * Any user of this method should immediately create a submonitor with an arbitrary number of ticks. Especially,
    * users should *not* call {@link IProgressMonitor#beginTask(String, int)} or {@link IProgressMonitor#done()} on the
    * returned progress monitor.
    * 
    * @throws CoreException
    *            if the progress monitor is currently null
    */
   protected final IProgressMonitor getProgressMonitorForIncrementalChanges() throws CoreException
   {
      if (this.progressMonitorForIncrementalChanges == null)
      {
         throw new CoreException(new Status(IStatus.ERROR, CoreActivator.getModuleID(), "Missing progress monitor for processing incremental changes"));
      }
      return this.progressMonitorForIncrementalChanges;
   }

   protected String getProgressBarMessage()
   {
      return "Building " + getProject().getName();
   }

   abstract protected boolean processResource(IProgressMonitor monitor) throws CoreException;

   abstract protected void cleanResource(IProgressMonitor monitor) throws CoreException;

}
