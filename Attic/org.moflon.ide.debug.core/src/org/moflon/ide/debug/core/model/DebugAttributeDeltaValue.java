package org.moflon.ide.debug.core.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import org.moflon.tgg.debug.language.DebugAttributeDelta;
import org.moflon.tgg.debug.language.DebugEObjectProxy;

public class DebugAttributeDeltaValue implements IValue
{
   DebugAttributeDelta adelta;

   IVariable[] variables;

   public DebugAttributeDeltaValue(DebugAttributeDelta value)
   {
      this.adelta = value;
   }

   @Override
   public String getModelIdentifier()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public IDebugTarget getDebugTarget()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ILaunch getLaunch()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public Object getAdapter(Class adapter)
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public String getValueString() throws DebugException
   {
      return adelta.getAffectedAttribute().getName();
   }

   @Override
   public boolean isAllocated() throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public IVariable[] getVariables() throws DebugException
   {
      if (variables == null)
      {
         variables = new IVariable[3];
         variables[0] = MoflonVariable.createVariable(adelta.getOldValue(), "Old value");
         variables[1] = MoflonVariable.createVariable(adelta.getNewValue(), "New value");
         DebugEObjectProxy proxy = adelta.getAffectedAttribute().getDebugProxy();
         variables[2] = MoflonVariable.createVariable(proxy, "Instance: " + proxy.getName());
      }
      return variables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      return getVariables().length > 0;
   }

}
