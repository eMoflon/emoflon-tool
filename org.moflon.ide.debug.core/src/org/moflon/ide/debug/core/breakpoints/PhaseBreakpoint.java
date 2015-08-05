package org.moflon.ide.debug.core.breakpoints;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.jdt.debug.core.JDIDebugModel;
import org.eclipse.jdt.internal.debug.core.breakpoints.JavaLineBreakpoint;
import org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget;
import org.eclipse.jdt.internal.debug.core.model.JDIThread;
import org.moflon.ide.debug.core.model.MoflonDebugTarget;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.ThreadStartEvent;

import DebugLanguage.AbstractPhase;

@SuppressWarnings("restriction")
public class PhaseBreakpoint extends JavaLineBreakpoint
{

   private Class<? extends AbstractPhase> phase;

   private IResource resource;

   /**
    * @see JDIDebugModel#createLineBreakpoint(IResource, String, int, int, int, int, boolean, Map)
    */
   public PhaseBreakpoint(IResource resource, String typeName, int lineNumber, int charStart, int charEnd, int hitCount, boolean add,
         Map<String, Object> attributes, Class<? extends AbstractPhase> phase) throws DebugException
   {
      super(resource, typeName, lineNumber, charStart, charEnd, hitCount, add, attributes, JAVA_LINE_BREAKPOINT);
      this.phase = phase;
      this.resource = resource;
   }

   /**
    * 
    * @see org.eclipse.jdt.internal.debug.core.breakpoints.JavaBreakpoint#handleEvent(com.sun.jdi.event.Event,
    *      org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget, boolean, com.sun.jdi.event.EventSet)
    */
   @Override
   public boolean handleEvent(Event event, JDIDebugTarget target, boolean suspendVote, EventSet eventSet)
   {
      if (event instanceof ClassPrepareEvent)
      {
         return handleClassPrepareEvent((ClassPrepareEvent) event, target, suspendVote);
      }
      MoflonDebugTarget mdt = (MoflonDebugTarget) target;
      JDIThread thread = mdt.findThread(phase);
      if (thread == null)
      {
         // wait for any thread start event sets to complete processing
         // see bug 271700
         try
         {
            Job.getJobManager().join(ThreadStartEvent.class, null);
         } catch (OperationCanceledException e)
         {
         } catch (InterruptedException e)
         {
         }
         thread = mdt.findThread(phase);
      }
      if (thread == null || thread.isIgnoringBreakpoints())
      {
         return true;
      }
      return handleBreakpointEvent(event, thread, suspendVote);
   }

   @Override
   public void eventSetComplete(Event event, JDIDebugTarget target, boolean suspend, EventSet eventSet)
   {
      ThreadReference threadRef = null;
      if (event instanceof ClassPrepareEvent)
      {
         threadRef = ((ClassPrepareEvent) event).thread();
      } else if (event instanceof LocatableEvent)
      {
         threadRef = ((LocatableEvent) event).thread();
      }
      if (threadRef == null)
      {
         return;
      }
      MoflonDebugTarget mdt = (MoflonDebugTarget) target;
      JDIThread thread = mdt.findThread(phase);
      if (thread == null || thread.isIgnoringBreakpoints())
      {
         return;
      }
      if (event instanceof ClassPrepareEvent)
      {
         classPrepareComplete(event, thread, suspend, eventSet);
      } else
      {
         thread.completeBreakpointHandling(this, suspend, true, eventSet);
      }
   }

   public IResource getResource()
   {
      return resource;
   }

}
