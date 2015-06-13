package org.moflon.ide.debug.core.model.phases.deletion;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.internal.debug.core.breakpoints.JavaBreakpoint;
import org.moflon.ide.debug.core.breakpoints.DeletionPhaseBreakpoint;
import org.moflon.ide.debug.core.breakpoints.PhaseBreakpoint;
import org.moflon.ide.debug.core.model.MoflonThread;

import DebugLanguage.AbstractPhase;
import DebugLanguage.DebugSynchronizationProtocol;

@SuppressWarnings("restriction")
public class DeletionThread extends MoflonThread// implements IJDIEventListener
{

   private IStackFrame[] stackframes;

   private DebugSynchronizationProtocol revokedProtocol;

   public DeletionThread(IDebugTarget target, String name, AbstractPhase phase)
   {
      super(target, name, phase);
      stackframes = new IStackFrame[0];
   }

   @Override
   protected int getUnderlyingFrameCount() throws DebugException
   {
      return stackframes == null ? 0 : stackframes.length;
   }

   @Override
   public boolean hasStackFrames() throws DebugException
   {
      return stackframes == null ? false : stackframes.length > 0;
   }

   @Override
   public IStackFrame[] getStackFrames() throws DebugException
   {
      return stackframes;
   }

   @Override
   public IStackFrame getTopStackFrame() throws DebugException
   {
      System.out.println("getTopStackFrame:" + (hasStackFrames() ? stackframes[0] : null));
      return hasStackFrames() ? stackframes[0] : null;
   }

   /**
    * Do not suspend when hitting {@link DeletionPhaseBreakpoint}. Delegate further {@link DeletionPhaseBreakpoint}
    * specific actions to {@link #handleSuspendForPhaseBreakpoint(PhaseBreakpoint)}.
    * 
    * @param breakpoint
    * @param suspendVote
    * @return
    */
   @Override
   public boolean handleSuspendForBreakpoint(JavaBreakpoint breakpoint, boolean suspendVote)
   {
      if (breakpoint instanceof DeletionPhaseBreakpoint)
      {
         handleSuspendForPhaseBreakpoint((PhaseBreakpoint) breakpoint);
         try
         {
            breakpoint.delete();
         } catch (CoreException e)
         {
            e.printStackTrace();
         }
         return false;
      } else
      {
         return super.handleSuspendForBreakpoint(breakpoint, suspendVote);
      }
   }

   @Override
   protected void handleSuspendForPhaseBreakpoint(PhaseBreakpoint breakpoint)
   {
      if (breakpoint instanceof DeletionPhaseBreakpoint)
      {
         if (stackframes != null)
            try
            {
               DeletionStateStackFrame dssf = new DeletionStateStackFrame(getDebugTarget(), this, getUnderlyingThread().frame(0));
               dssf.computeAndSetRevokedProtocol();
               stackframes = new IStackFrame[] { dssf };
            } catch (Exception e)
            {
               e.printStackTrace();
            }
      } else if (breakpoint instanceof PhaseBreakpoint)
      {
         DeletionStateStackFrame dssf = (DeletionStateStackFrame) stackframes[0];
         try
         {
            dssf.computeAndSetDebugModel();
         } catch (Exception e)
         {
            e.printStackTrace();
         }
      }
   }

   // @Override
   // public boolean handleEvent(Event event, JDIDebugTarget target, boolean suspendVote, EventSet eventSet)
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public void eventSetComplete(Event event, JDIDebugTarget target, boolean suspend, EventSet eventSet)
   // {
   // // TODO Auto-generated method stub
   // System.out.println();
   // try
   // {
   // stackframes = new IStackFrame[] { new DeletionConfigurationStackFrame(target, this,
   // getUnderlyingThread().frame(0)) };
   // } catch (IncompatibleThreadStateException | AbsentInformationException e)
   // {
   // // TODO Auto-generated catch block
   // e.printStackTrace();
   // }
   // }

   @Override
   public boolean isSuspended()
   {
      // try
      // {
      // return !getDebugTarget().getThreads()[0].isSuspended();
      // } catch (DebugException e)
      // {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }
      return super.isSuspended();
   }

   @Override
   public void suspend() throws DebugException
   {
      super.suspend();
      // setRunning(false);
      // fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
   }

   @Override
   public synchronized void resume() throws DebugException
   {
      for (IBreakpoint bp : getBreakpoints())
      {
         try
         {
            bp.delete();
         } catch (CoreException e)
         {
            e.printStackTrace();
         }
      }
      stackframes = null;
      super.resume();
      getDebugTarget().resume();
      // setRunning(true);
      // fireResumeEvent(DebugEvent.CLIENT_REQUEST);
   }
}
