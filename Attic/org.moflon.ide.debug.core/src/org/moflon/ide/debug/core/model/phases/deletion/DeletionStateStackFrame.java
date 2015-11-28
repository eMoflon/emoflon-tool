package org.moflon.ide.debug.core.model.phases.deletion;

import java.io.IOException;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.jdt.debug.core.IJavaObject;
import org.eclipse.jdt.debug.core.IJavaVariable;
import org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget;
import org.eclipse.jdt.internal.debug.core.model.JDIThread;
import org.eclipse.jdt.internal.debug.core.model.JDIValue;
import org.moflon.ide.debug.core.launcher.JDIUtil;
import org.moflon.ide.debug.core.model.MoflonStackFrame;
import org.moflon.ide.debug.core.model.MoflonVariable;
import org.moflon.ide.debug.core.model.phases.init.CorrespondenceVariable;
import org.moflon.ide.debug.core.model.phases.init.SynchronizationVariable;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.InvocationException;
import com.sun.jdi.NativeMethodException;
import com.sun.jdi.StackFrame;
import com.sun.jdi.VirtualMachine;

import org.moflon.tgg.debug.language.DebugModel;
import org.moflon.tgg.debug.language.DebugSynchronizationProtocol;
import org.moflon.tgg.debug.language.DeletionPhase;

@SuppressWarnings({ "restriction" })
public class DeletionStateStackFrame extends MoflonStackFrame
{
   private static final String SOURCE_MODEL = "Source Model";

   public static final String DELETED_ELEMENTS = "Deleted Elements";

   private static final String TARGET_MODEL = "Target Model";

   private static final String REVOKED_ELEMENTS = "Revoked Elements";

   private IThread thread;

   private StackFrame sf;

   private IVariable[] variables;

   private DebugSynchronizationProtocol revokedProtocol;

   private DebugModel dm;

   public DeletionStateStackFrame(IDebugTarget target, JDIThread thread, StackFrame sf) throws IncompatibleThreadStateException, AbsentInformationException,
         IOException, InvalidTypeException, ClassNotLoadedException, InvocationException
   {
      super((JDIThread) thread, sf, 0);
      this.thread = thread;
      this.sf = sf;
   }

   // @Override
   protected DebugModel computeDebugModel() throws IOException, IncompatibleThreadStateException, AbsentInformationException, InvalidTypeException,
         ClassNotLoadedException, InvocationException
   {
      VirtualMachine vm = getVM();
      String value = JDIUtil.getStringValue(vm, JDIUtil.DEBUG_SYNCHRONIZATION_HELPER, JDIUtil.METHOD_TO_DELETION_PHASE);
      return convertToEObject(value, DebugModel.class);
   }

   public void computeAndSetRevokedProtocol() throws IOException, IncompatibleThreadStateException, AbsentInformationException, InvalidTypeException,
         ClassNotLoadedException, InvocationException
   {
      this.dm = computeDebugModel();
      DeletionPhase dp = (DeletionPhase) dm.getPhases().get(0);
      this.revokedProtocol = dp.getRevokedProtocol();
   }

   public void computeAndSetDebugModel() throws IOException, IncompatibleThreadStateException, AbsentInformationException, InvalidTypeException,
         ClassNotLoadedException, InvocationException
   {
      this.dm = computeDebugModel();
   }

   @Override
   public IThread getThread()
   {
      return thread;
   }

   @Override
   public IVariable[] getVariables() throws DebugException
   {
      if (variables == null)
      {
         DeletionPhase dp = (DeletionPhase) dm.getPhases().get(0);
         this.variables = new IVariable[] { MoflonVariable.createVariable(dp.getSourceProxy(), SOURCE_MODEL),
               MoflonVariable.createVariable(dp.getTargetProxy(), TARGET_MODEL), new CorrespondenceVariable(getDebugTarget(), dp.getCorrespondenceModel()),
               MoflonVariable.createVariable(dp.getDeletedElements(), DELETED_ELEMENTS),
               MoflonVariable.createVariable(dp.getRevokedElements(), REVOKED_ELEMENTS), new SynchronizationVariable(getDebugTarget(), dp.getRevokedProtocol()) };
         // , new SynchronizationVariable(getDebugTarget(), revokedProtocol),
      }
      return variables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      return getVariables().length > 0;
   }

   @Override
   public String getName() throws DebugException
   {
      return "State after Deletion";
   }

   @Override
   public String getSourceName() throws DebugException
   {
      try
      {
         return sf.location().sourceName();
      } catch (AbsentInformationException e)
      {
         return null;
      } catch (NativeMethodException e)
      {
         return null;
      } catch (RuntimeException e)
      {
         e.printStackTrace();
      }
      return null;
   }

   @Override
   public String getSourceName(String stratum) throws DebugException
   {
      // TODO Auto-generated method stub
      // return null;
      return getSourceName();
   }

   @Override
   public String getSourcePath(String stratum) throws DebugException
   {
      // return null;
      return getSourcePath();
   }

   @Override
   public String getSourcePath() throws DebugException
   {
      // return null;
      try
      {
         return sf.location().sourcePath();
      } catch (AbsentInformationException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return null;
   }

   @Override
   public IJavaVariable[] getLocalVariables() throws DebugException
   {
      // return null;
      try
      {
         return sf.visibleVariables().toArray(new IJavaVariable[sf.visibleVariables().size()]);
      } catch (AbsentInformationException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return null;
   }

   @Override
   public IJavaObject getThis() throws DebugException
   {
      // return null;
      return (IJavaObject) JDIValue.createValue((JDIDebugTarget) getDebugTarget(), sf.thisObject());
   }

}
