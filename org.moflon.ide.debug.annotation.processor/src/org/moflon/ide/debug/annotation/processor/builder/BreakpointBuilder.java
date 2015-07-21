package org.moflon.ide.debug.annotation.processor.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import main.DebugAnnotation;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;
import org.moflon.ide.core.runtime.builders.AbstractBuilder;

/**
 * A breakpoint builder that updates breakpoints
 */
public class BreakpointBuilder extends AbstractBuilder
{
   public static final Logger logger = Logger.getLogger(BreakpointBuilder.class);

   public static final String BUILDER_ID = "org.moflon.ide.core.runtime.builders.BreakpointBuilder";

   @Override
   protected void cleanResource(final IProgressMonitor monitor) throws CoreException
   {
   }

   /**
    * Check whether the current project has build issues (e.g. due to a missing eMoflon build). Only if no errors are
    * reported the builder runs.
    */
   @Override
   protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException
   {
      IMarker[] findMarkers = getProject().findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
      long count = Arrays.asList(findMarkers).stream().filter(m -> {
         try
         {
            return m.getAttribute(IMarker.SEVERITY) != null && m.getAttribute(IMarker.SEVERITY).equals(IMarker.SEVERITY_ERROR);
         } catch (Exception e)
         {
            return false;
         }
      }).count();
      if (count == 0)
      {
         return super.build(kind, args, monitor);
      } else
      {
         logger.warn("The Breakpoint Builder was not executed due to " + count + " issues in the project " + getProject().getName()
               + ". Please build the project correctly and try again.");
      }
      return null;
   }

   @Override
   protected boolean processResource(final IProgressMonitor monitor) throws CoreException
   {
      monitor.beginTask(getProgressBarMessage(), 140);
      try
      {
         DebugAnnotation da = new DebugAnnotation();
         da.setBaseLocation(getProject().findMember("src").getLocationURI().toURL());
         da.setWorkspaceRoot(getProject().getWorkspace().getRoot());
         da.computeDebugAnnotations();

      } catch (Exception e)
      {
         throw new CoreException(new Status(IStatus.ERROR, BUILDER_ID, "...", e));
      } finally
      {
         monitor.done();
      }
      final MultiStatus mocaToMoflonStatus = new MultiStatus(CoreActivator.getModuleID(), 0, BUILDER_ID + " failed", null);
      return mocaToMoflonStatus.isOK();
   }

   @Override
   public boolean visit(final IResource resource) throws CoreException
   {
      final IProgressMonitor progressMonitorForIncrementalChanges = this.getProgressMonitorForIncrementalChanges();
      processResource(WorkspaceHelper.createSubMonitor(progressMonitorForIncrementalChanges, 100));
      return false;
   }

   @Override
   public boolean visit(final IResourceDelta delta) throws CoreException
   {
      // Get changes and call visit on .temp folder
      IResourceDelta[] changes = delta.getAffectedChildren();
      for (final IResourceDelta change : changes)
      {
         IResource resource = change.getResource();
         if (wasChangedOrAdded(change))
            return visit(resource);
         else
            visit(change);
      }

      return false;
   }

   private boolean wasChangedOrAdded(final IResourceDelta change)
   {
      return change.getKind() == IResourceDelta.CHANGED || change.getKind() == IResourceDelta.ADDED;
   }

}
