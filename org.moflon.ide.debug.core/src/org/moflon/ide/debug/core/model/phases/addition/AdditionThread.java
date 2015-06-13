package org.moflon.ide.debug.core.model.phases.addition;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.moflon.ide.debug.core.breakpoints.PhaseBreakpoint;
import org.moflon.ide.debug.core.model.MoflonThread;

import DebugLanguage.AbstractPhase;

@SuppressWarnings("restriction")
public class AdditionThread extends MoflonThread
{

   private IStackFrame[] stackframes;

   public AdditionThread(IDebugTarget target, String name, AbstractPhase phase)
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

   @Override
   protected void handleSuspendForPhaseBreakpoint(PhaseBreakpoint breakpoint)
   {
      if (stackframes != null)
         try
         {
            stackframes = new IStackFrame[] { new AdditonStateStackFrame(getDebugTarget(), this, getUnderlyingThread().frame(0)) };
         } catch (Exception e)
         {
            e.printStackTrace();
         }
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
   }
}
