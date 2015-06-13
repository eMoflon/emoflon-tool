package org.moflon.ide.debug.core.model.phases.init;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget;
import org.eclipse.jdt.internal.debug.core.model.JDIValue;
import org.moflon.ide.debug.core.launcher.JDIUtil;
import org.moflon.ide.debug.core.model.MoflonDebugElement;
import org.moflon.ide.debug.core.model.MoflonDebugTarget;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.Value;

@SuppressWarnings("restriction")
public class ConfiguratorValue extends MoflonDebugElement implements IValue
{
   private JDIValue configuratorJdiValue;

   public ConfiguratorValue(IDebugTarget debugTarget) throws IncompatibleThreadStateException, AbsentInformationException
   {
      super(debugTarget);
      Value val = JDIUtil.getField(((MoflonDebugTarget) debugTarget).getVM(), "configurator");
      configuratorJdiValue = JDIValue.createValue((JDIDebugTarget) debugTarget, val);
      System.out.println();
   }

   @Override
   public IDebugTarget getDebugTarget()
   {
      return configuratorJdiValue.getDebugTarget();
   }

   @Override
   public ILaunch getLaunch()
   {
      return configuratorJdiValue.getLaunch();
   }

   @Override
   public Object getAdapter(Class adapter)
   {
      return configuratorJdiValue.getAdapter(adapter);
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      return configuratorJdiValue.getReferenceTypeName();
   }

   @Override
   public String getValueString() throws DebugException
   {
      return configuratorJdiValue.getReferenceTypeName() + " " + configuratorJdiValue.getValueString();
   }

   @Override
   public boolean isAllocated() throws DebugException
   {
      return configuratorJdiValue.isAllocated();
   }

   @Override
   public IVariable[] getVariables() throws DebugException
   {
      return configuratorJdiValue.getVariables();
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      return configuratorJdiValue.hasVariables();
   }

}
