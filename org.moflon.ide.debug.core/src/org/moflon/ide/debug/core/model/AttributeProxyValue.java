package org.moflon.ide.debug.core.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import DebugLanguage.AttributeProxy;

public class AttributeProxyValue implements IValue
{
   AttributeProxy aproxy;

   public AttributeProxyValue(AttributeProxy value)
   {
      this.aproxy = value;
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
      if (aproxy != null)
      {
         Object value = aproxy.getValue();
         if (value != null)
         {
            return value.toString();
         }
      }
      return "null";
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
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

}
