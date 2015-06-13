package org.moflon.ide.debug.core.model.phases.translation;

import java.io.IOException;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.jdt.internal.debug.core.model.JDIThread;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.InvocationException;
import com.sun.jdi.StackFrame;

@SuppressWarnings({ "restriction" })
public class TranslationStepStackFrame extends TranslationStateStackFrame
{
   public TranslationStepStackFrame(IDebugTarget target, JDIThread thread, StackFrame sf, int i) throws IncompatibleThreadStateException,
         AbsentInformationException, InvalidTypeException, ClassNotLoadedException, InvocationException, IOException
   {
      super(target, thread, sf);
      this.i = i;
   }

   private int i;

   @Override
   public String getName() throws DebugException
   {
      return "Translation Step#" + i;
   }
}
