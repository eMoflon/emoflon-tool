package org.moflon.ide.debug.core.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EObject;

import org.moflon.tgg.debug.language.ChangeMode;
import org.moflon.tgg.debug.language.DebugEObjectProxy;

public class DebugEObjectProxyVariable extends MoflonDebugElement implements IVariable
{

   private IValue value;

   private DebugEObjectProxy proxy;

   private String label;

   public DebugEObjectProxyVariable(DebugEObjectProxy value)
   {
      super(null);
      this.proxy = value;
      this.value = MoflonValue.createValue(value);
   }

   public DebugEObjectProxyVariable(DebugEObjectProxy value, String label)
   {
      this(value);
      this.label = label;
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
      if (adapter == ChangeMode.class)
      {
         return proxy.getChangeMode();
      } else if (adapter == EObject.class)
      {
         return proxy;
      }
      return null;
   }

   @Override
   public void setValue(String expression) throws DebugException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void setValue(IValue value) throws DebugException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public boolean supportsValueModification()
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean verifyValue(String expression) throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean verifyValue(IValue value) throws DebugException
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public IValue getValue() throws DebugException
   {
      return value;
   }

   @Override
   public String getName() throws DebugException
   {
      if (label == null)
      {
         if (proxy.getName() != null)
         {
            return proxy.getName().toLowerCase();
         } else if (proxy.getClassName() != null)
         {
            return proxy.getClassName().toLowerCase();
         }
      }
      return label;
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public boolean hasValueChanged() throws DebugException
   {
      return proxy.getChangeMode() != ChangeMode.NONE;
   }

}
