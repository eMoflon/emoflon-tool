package org.moflon.ide.debug.core.model.phases.translation;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.internal.debug.core.breakpoints.JavaBreakpoint;
import org.eclipse.jdt.internal.debug.core.model.JDIDebugModelMessages;
import org.eclipse.jdt.internal.debug.core.model.JDIThread;
import org.moflon.ide.debug.core.breakpoints.PhaseBreakpoint;
import org.moflon.ide.debug.core.breakpoints.TranslationPhaseBreakpoint;
import org.moflon.ide.debug.core.model.MoflonDebugTarget;
import org.moflon.ide.debug.core.model.MoflonThread;

import com.ibm.icu.text.MessageFormat;
import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.InvocationException;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;

import DebugLanguage.AbstractPhase;

@SuppressWarnings("restriction")
public class TranslationThread extends MoflonThread
{
   private List<IStackFrame> stackframes;

   public TranslationThread(IDebugTarget target, String name, AbstractPhase phase)
   {
      super(target, name, phase);
      stackframes = new LinkedList<IStackFrame>();
   }

   @Override
   protected int getUnderlyingFrameCount() throws DebugException
   {
      return stackframes == null ? 0 : stackframes.size();
   }

   @Override
   public boolean hasStackFrames() throws DebugException
   {
      return stackframes == null ? false : stackframes.size() > 0;
   }

   @Override
   public IStackFrame[] getStackFrames() throws DebugException
   {
      return stackframes.toArray(new IStackFrame[stackframes.size()]);
   }

   @Override
   public IStackFrame getTopStackFrame() throws DebugException
   {
      return hasStackFrames() ? stackframes.get(stackframes.size() - 1) : null;
   }

   @Override
   protected void handleSuspendForPhaseBreakpoint(PhaseBreakpoint breakpoint)
   {
      if (breakpoint instanceof TranslationPhaseBreakpoint)
      {
         try
         {
            handleSuspendForTranslationPhaseBreakpoint((TranslationPhaseBreakpoint) breakpoint);
         } catch (Exception e)
         {
            e.printStackTrace();
         }
      }
   }

   private void handleSuspendForTranslationPhaseBreakpoint(TranslationPhaseBreakpoint breakpoint) throws IncompatibleThreadStateException,
         AbsentInformationException, InvalidTypeException, ClassNotLoadedException, InvocationException, IOException
   {
      switch (breakpoint.getSubphase())
      {
      case TRANSLATION_START:
         if (stackframes.size() == 0)
         {
            stackframes.add(new TranslationStateStackFrame(getDebugTarget(), this, getUnderlyingThread().frame(0)));
         }
         break;
      case TRANSLATION_STEP:
         stackframes.add(new TranslationStepStackFrame(getDebugTarget(), this, getUnderlyingThread().frame(0), stackframes.size()));
         break;
      case TRANSLATION_END:
         stackframes.add(new FinalizationStackFrame(getDebugTarget(), this, getUnderlyingThread().frame(0), "Final State after Translation"));
         break;
      default:
         break;
      }
   }

   @Override
   public synchronized void resume() throws DebugException
   {
      // Arrays.stream(getBreakpoints()).filter(bp -> bp instanceof JavaBreakpoint)
      // .filter(jbp -> ((JavaBreakpoint)
      // jbp).getBreakpointListeners()[0].equals(ORG_MOFLON_IDE_DEBUG_CORE_MOFLON_BREAKPOINT_LISTENER));

      List<IBreakpoint> collect = Arrays.stream(getBreakpoints()).filter(bp -> bp instanceof JavaBreakpoint).filter(bp -> {
         JavaBreakpoint jbp = (JavaBreakpoint) bp;
         try
         {
            return jbp.getBreakpointListeners()[0].equals(ORG_MOFLON_IDE_DEBUG_CORE_MOFLON_BREAKPOINT_LISTENER);
         } catch (Exception e)
         {
            e.printStackTrace();
         }
         return false;
      }).collect(Collectors.toList());

      if (collect.size() > 0)
      {
         IBreakpoint b = collect.get(0);
         PhaseBreakpoint pb = (PhaseBreakpoint) b;
         try
         {
            if (pb instanceof TranslationPhaseBreakpoint)
            {
               TranslationPhaseBreakpoint tpb = (TranslationPhaseBreakpoint) pb;
               switch (tpb.getSubphase())
               {
               case TRANSLATION_STEP:
                  // Skip and execute all steps at once if false
                  tpb.setEnabled(true);
                  break;
               case TRANSLATION_END:
                  ((MoflonDebugTarget) getDebugTarget()).deleteBreakpoints();
                  pb.delete();
                  break;
               default:
                  pb.delete();
                  break;
               }
            } else
            {
               pb.delete();
            }
         } catch (CoreException e)
         {
            e.printStackTrace();
         }
      }
      stepInto();
   }

   /**
    * Code party copied from {@link JDIThread#resumeThread(boolean)} but sending {@link DebugEvent#STEP_INTO} instead of
    * original {@link DebugEvent#CLIENT_REQUEST}.
    * 
    * @see org.eclipse.jdt.internal.debug.core.model.JDIThread#stepInto()
    */
   @Override
   public void stepInto() throws DebugException
   {
      if (!isSuspended() || (isPerformingEvaluation() && !isInvokingMethod()))
      {
         return;
      }
      try
      {
         setRunning(true);

         fireResumeEvent(DebugEvent.STEP_INTO);

         preserveStackFrames();
         ((ThreadReference) getUnderlyingThread()).resume();
      } catch (VMDisconnectedException e)
      {
         disconnected();
      } catch (RuntimeException e)
      {
         setRunning(false);
         fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
         targetRequestFailed(MessageFormat.format(JDIDebugModelMessages.JDIThread_exception_resuming, e.toString()), e);
      }
   }
}
