package org.moflon.ide.debug.core.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;

public class DynamicEObjectImplValue implements IValue
{
   DynamicEObjectImpl deob;

   IVariable[] variables;

   public DynamicEObjectImplValue(DynamicEObjectImpl value)
   {
      this.deob = value;
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
      return EObjectValue.getValueString(deob);
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
         this.variables = new IVariable[deob.eClass().getEAllAttributes().size()];

         for (int i = 0; i < variables.length; i++)
         {
            // variables[i] = MoflonVariable.createVariable(value.dynamicGet(i) == null ? null : (String)
            // value.dynamicGet(i).toString());
            variables[i] = MoflonVariable.createVariable(deob.eClass().getEAllAttributes().get(i), deob);
         }
      }
      return variables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      return getVariables().length > 0;
   }

}
