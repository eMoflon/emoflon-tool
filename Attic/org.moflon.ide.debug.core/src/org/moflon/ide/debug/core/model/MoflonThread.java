package org.moflon.ide.debug.core.model;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.jdt.internal.debug.core.breakpoints.JavaBreakpoint;
import org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget;
import org.eclipse.jdt.internal.debug.core.model.JDIThread;
import org.moflon.ide.debug.core.Activator;
import org.moflon.ide.debug.core.breakpoints.PhaseBreakpoint;
import org.moflon.tgg.debug.language.AbstractPhase;

@SuppressWarnings("restriction")
public abstract class MoflonThread extends JDIThread
{
   public static final String ORG_MOFLON_IDE_DEBUG_CORE_MOFLON_BREAKPOINT_LISTENER = "org.moflon.ide.debug.core.MoflonBreakpointListener";

   private String name;

   protected AbstractPhase phase;

   public MoflonThread(IDebugTarget target, String name, AbstractPhase phase)
   {
      super((JDIDebugTarget) target, ((JDIDebugTarget) target).getVM().allThreads().stream().filter(t -> t.name().equals("main")).findFirst().get());
      this.name = name;
      this.phase = phase;
   }

   @Override
   protected int getUnderlyingFrameCount() throws DebugException
   {
      return 0;
   }

   @Override
   public String getModelIdentifier()
   {
      return Activator.ID_MOFLON_DEBUG_MODEL;
   }

   public AbstractPhase getPhase()
   {
      return phase;
   }

   protected abstract void handleSuspendForPhaseBreakpoint(PhaseBreakpoint breakpoint);

   @Override
   public boolean handleSuspendForBreakpoint(JavaBreakpoint breakpoint, boolean suspendVote)
   {
      try
      {
         if (breakpoint.getBreakpointListeners().length > 0
               && breakpoint.getBreakpointListeners()[0].equals(ORG_MOFLON_IDE_DEBUG_CORE_MOFLON_BREAKPOINT_LISTENER) && breakpoint instanceof PhaseBreakpoint)
         {
            handleSuspendForPhaseBreakpoint((PhaseBreakpoint) breakpoint);
            return super.handleSuspendForBreakpoint(breakpoint, suspendVote);
         }
      } catch (CoreException e)
      {
         e.printStackTrace();
      }
      return false;
   }

   // @Override
   // public boolean canResume()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }

   //
   // @Override
   // public boolean canSuspend()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public boolean isSuspended()
   // {
   // // TODO Auto-generated method stub
   // return true;
   // }
   //
   // @Override
   // public void resume() throws DebugException
   // {
   // // TODO Auto-generated method stub
   //
   // }
   //
   // @Override
   // public void suspend() throws DebugException
   // {
   // // TODO Auto-generated method stub
   //
   // }
   //
   // @Override
   // public boolean canStepInto()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public boolean canStepOver()
   // {
   // return true;
   // }
   //
   // @Override
   // public boolean canStepReturn()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public boolean isStepping()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public void stepInto() throws DebugException
   // {
   // // TODO Auto-generated method stub
   //
   // }
   //
   // @Override
   // public void stepOver() throws DebugException
   // {
   // System.out.println("stepover");
   // }
   //
   // @Override
   // public void stepReturn() throws DebugException
   // {
   // // TODO Auto-generated method stub
   //
   // }
   //
   // @Override
   // public boolean canTerminate()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public boolean isTerminated()
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public void terminate() throws DebugException
   // {
   // // TODO Auto-generated method stub
   //
   // }
   //
   // @Override
   // public IStackFrame[] getStackFrames() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // return null;
   // }
   //
   // @Override
   // public boolean hasStackFrames() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // return false;
   // }
   //
   // @Override
   // public int getPriority() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // return 0;
   // }
   //
   // @Override
   // public IStackFrame getTopStackFrame() throws DebugException
   // {
   // // TODO Auto-generated method stub
   // return null;
   // }

   @Override
   public String getName() throws DebugException
   {
      return name;
   }

   // @Override
   // public IBreakpoint[] getBreakpoints()
   // {
   // // TODO Auto-generated method stub
   // return null;
   // }

}
