package org.moflon.ide.core.runtime.builders;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeSet;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.gervarro.eclipse.workspace.util.AntPatternCondition;
import org.gervarro.eclipse.workspace.util.RelevantElementCollectingBuilder;
import org.gervarro.eclipse.workspace.util.RelevantElementCollector;
import org.gervarro.eclipse.workspace.util.VisitorCondition;
import org.moflon.core.utilities.WorkspaceHelper;
import org.moflon.ide.core.CoreActivator;

abstract public class AbstractVisitorBuilder extends RelevantElementCollectingBuilder
{
   public static final Comparator<IProject> PROJECT_COMPARATOR = new Comparator<IProject>() {
      @Override
      public int compare(IProject left, IProject right)
      {
         return left.getName().compareTo(right.getName());
      }
   };

   protected final TreeSet<IProject> triggerProjects = new TreeSet<IProject>(PROJECT_COMPARATOR);

   protected AbstractVisitorBuilder(VisitorCondition condition)
   {
      super(condition);
   }
   
   protected int correctBuildTrigger(final int kind) {
	   if (kind == INCREMENTAL_BUILD && getContext().getRequestedConfigs().length == 0) {
		   return AUTO_BUILD;
	   } else {
		   return kind;
	   }
   }
   
   protected void postprocess(final RelevantElementCollector buildVisitor, int kind, final Map<String, String> args, final IProgressMonitor monitor)
   {
	   kind = correctBuildTrigger(kind);
	   if (getCommand().isBuilding(kind)) {
		   super.postprocess(buildVisitor, kind, args, monitor);
	   }
      if (buildVisitor.getRelevantDeltas().isEmpty() && (kind == INCREMENTAL_BUILD || kind == AUTO_BUILD))
      {
         final SubMonitor subMonitor = SubMonitor.convert(monitor, triggerProjects.size());
         try
         {
            for (final IProject project : triggerProjects)
            {
               final RelevantElementCollector relevantElementCollector = new RelevantElementCollector(project, getTriggerCondition(project)) {
                  public boolean handleResourceDelta(final IResourceDelta delta)
                  {
                     final int deltaKind = delta.getKind();
                     if (deltaKind == IResourceDelta.ADDED || deltaKind == IResourceDelta.CHANGED)
                     {
                        super.handleResourceDelta(delta);
                     }
                     return false;
                  }
               };
               final IResourceDelta delta = getDelta(project);
               if (delta != null)
               {

                  delta.accept(relevantElementCollector, IResource.NONE);
                  if (!relevantElementCollector.getRelevantDeltas().isEmpty())
                  {
                     // Perform a full build if a triggering project changed
                     build(FULL_BUILD, args, subMonitor.split(1));
                     return;
                  } else
                  {
                     subMonitor.worked(1);
                  }
               }else
               {
                  subMonitor.worked(1);
               }
            }
         } catch (final CoreException e)
         {
            throw new RuntimeException(e.getMessage(), e);
         }
      }
   }

   abstract protected AntPatternCondition getTriggerCondition(final IProject project);

   protected void processResourceDelta(final IResourceDelta delta, final int kind, final Map<String, String> args, final IProgressMonitor monitor)
   {
      if (delta.getKind() != IResourceDelta.REMOVED)
      {
         super.processResourceDelta(delta, kind, args, monitor);
      }
   }

   public final boolean addTriggerProject(IProject project)
   {
      return triggerProjects.add(project);
   }

   protected final IProject[] calculateInterestingProjects()
   {
      IProject[] result = new IProject[triggerProjects.size()];
      return triggerProjects.toArray(result);
   }

   protected final void processProblemStatus(IStatus status, IResource resource) throws CoreException
   {
      if (status.isOK())
      {
         return;
      }
      if (status.isMultiStatus())
      {
         for (IStatus child : ((MultiStatus) status).getChildren())
         {
            processProblemStatus(child, resource);
         }
      } else
      {
         CoreActivator.createProblemMarker(resource, status.getMessage(), CoreActivator.convertStatusSeverityToMarkerSeverity(status.getSeverity()),
               resource.getProjectRelativePath().toString());
      }
   }

   protected final void deleteProblemMarkers() throws CoreException
   {
      getProject().deleteMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
      getProject().deleteMarkers(WorkspaceHelper.MOFLON_PROBLEM_MARKER_ID, false, IResource.DEPTH_INFINITE);
      getProject().deleteMarkers(WorkspaceHelper.INJECTION_PROBLEM_MARKER_ID, false, IResource.DEPTH_INFINITE);
   }
}
