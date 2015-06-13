package org.moflon.ide.debug.core.model.phases.init;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.moflon.ide.debug.core.breakpoints.PhaseBreakpoint;
import org.moflon.ide.debug.core.model.MoflonThread;

import DebugLanguage.AbstractPhase;

@SuppressWarnings("restriction")
public class InitPhaseThread extends MoflonThread
{
   private IStackFrame[] stackframes;

   public InitPhaseThread(IDebugTarget target, AbstractPhase abstractPhase)
   {
      super(target, "I Init Phase", abstractPhase);
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

   // @Override
   // public boolean canResume()
   // {
   // // TODO Auto-generated method stub
   // return true;
   // }
   //
   // @Override
   // protected boolean canStep()
   // {
   // // TODO Auto-generated method stub
   // return true;
   // }

   @Override
   public void suspend() throws DebugException
   {
      super.suspend();
   }

   @Override
   public synchronized void resume() throws DebugException
   {
      // try
      // {
      // String s = JDIUtil.getStringValue(getVM(), JDIUtil.DEBUG_FORWARD_SYNCHRONIZER,
      // JDIUtil.METHOD_TO_DELETION_PHASE);
      // System.out.println(s);
      // } catch (IncompatibleThreadStateException | AbsentInformationException | InvalidTypeException |
      // ClassNotLoadedException | InvocationException e1)
      // {
      // // TODO Auto-generated catch block
      // e1.printStackTrace();
      // }
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
   }

   @Override
   protected void handleSuspendForPhaseBreakpoint(PhaseBreakpoint breakpoint)
   {
      if (stackframes != null)
         try
         {
            stackframes = new IStackFrame[] { new InitialStateStackFrame(getDebugTarget(), this, getUnderlyingThread().frame(0)) };
         } catch (Exception e)
         {
            e.printStackTrace();
         }
   }

   @Override
   protected void terminated()
   {
      super.terminated();
      // Terminate all other Threads
      try
      {
         getDebugTarget().terminate();
      } catch (DebugException e)
      {
         e.printStackTrace();
      }
   }
}