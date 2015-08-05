package org.moflon.ide.debug.core.model.phases.addition;

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

import DebugLanguage.AdditionPhase;
import DebugLanguage.DebugModel;

@SuppressWarnings({ "restriction" })
public class AdditonStateStackFrame extends MoflonStackFrame
{
   public static final String REVOKED_ELEMENTS = "Revoked Elements";

   public static final String ADDED_ELEMENTS = "Added Elements";

   IThread thread;

   StackFrame sf;

   private IVariable[] variables;

   public AdditonStateStackFrame(IDebugTarget target, JDIThread thread, StackFrame sf) throws IncompatibleThreadStateException, AbsentInformationException,
         IOException, InvalidTypeException, ClassNotLoadedException, InvocationException
   {
      super((JDIThread) thread, sf, 0);
      this.thread = thread;
      this.sf = sf;

      // Obtain debug model
      DebugModel dm = computeDebugModel();

      AdditionPhase ap = (AdditionPhase) dm.getPhases().get(0);
      this.variables = new IVariable[] { MoflonVariable.createVariable(ap.getSourceProxy(), SOURCE_MODEL),
            MoflonVariable.createVariable(ap.getTargetProxy(), TARGET_MODEL), new CorrespondenceVariable(target, ap.getCorrespondenceModel()),
            MoflonVariable.createVariable(ap.getAddedElements(), ADDED_ELEMENTS), MoflonVariable.createVariable(ap.getRevokedElements(), REVOKED_ELEMENTS),
            // new CorrespondenceVariable(target, ip.getCorrespondenceModel()),
            new SynchronizationVariable(target, ap.getSynchronizationProtocol()) // ,
      // new SynchronizationVariable(target, ap.getDeletedMatches(), DELETED_TRIPLE_MATCHES)
      // , new RulesVariable(target,
      // ip.getRules()),
      // new SynchronizationVariable(target, ap.getDeletedMatches(), "Deleted Triple Matches")
      // new DeltaVariable(target, ip.getDelta()), new ConfiguratorVariable(target)
      };
   }

   @Override
   protected DebugModel computeDebugModel() throws IOException, IncompatibleThreadStateException, AbsentInformationException, InvalidTypeException,
         ClassNotLoadedException, InvocationException
   {
      VirtualMachine vm = getVM();
      String value = JDIUtil.getStringValue(vm, JDIUtil.DEBUG_SYNCHRONIZATION_HELPER, JDIUtil.METHOD_TO_ADDITION_PHASE);
      return convertToEObject(value, DebugModel.class);
   }

   @Override
   public IThread getThread()
   {
      return thread;
   }

   @Override
   public IVariable[] getVariables() throws DebugException
   {
      return variables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      System.out.println("hasVariables: " + (variables.length > 0));
      return variables.length > 0;
   }

   @Override
   public String getName() throws DebugException
   {
      return "State after Addition";
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
      // TODO Auto-generated method stub
      // return null;
      return getSourcePath();
   }

   @Override
   public String getSourcePath() throws DebugException
   {
      // TODO Auto-generated method stub
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
      // TODO Auto-generated method stub
      // return null;
      return (IJavaObject) JDIValue.createValue((JDIDebugTarget) getDebugTarget(), sf.thisObject());
   }

}
