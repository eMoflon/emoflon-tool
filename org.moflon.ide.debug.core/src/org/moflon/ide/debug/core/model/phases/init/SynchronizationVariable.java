package org.moflon.ide.debug.core.model.phases.init;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.debug.core.IJavaModifiers;
import org.eclipse.jdt.debug.core.IJavaVariable;
import org.moflon.ide.debug.core.model.MoflonDebugElement;

import DebugLanguage.DebugSynchronizationProtocol;

public class SynchronizationVariable extends MoflonDebugElement implements IVariable
{
   DebugSynchronizationProtocol protocol;

   IValue value;

   String name;

   public SynchronizationVariable(IDebugTarget target, DebugSynchronizationProtocol debugSynchronizationProtocol)
   {
      super(target);
      this.protocol = debugSynchronizationProtocol;
      this.name = "Synchronization Protocol";
      value = new SynchronizationValue(getDebugTarget(), protocol);
   }

   public SynchronizationVariable(IDebugTarget target, DebugSynchronizationProtocol debugSynchronizationProtocol, String name)
   {
      this(target, debugSynchronizationProtocol);
      this.name = name;
   }

   @Override
   public void setValue(String expression) throws DebugException
   {
      System.out.println("setValue: " + expression);

   }

   @Override
   public void setValue(IValue value) throws DebugException
   {
      System.out.println("setValue: " + value);
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
      return name;
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      return value.getReferenceTypeName();
   }

   @Override
   public boolean hasValueChanged() throws DebugException
   {
      return false;
   }

   @Override
   public Object getAdapter(Class adapter)
   {
      if (adapter == IJavaVariable.class || adapter == IJavaModifiers.class)
      {
         return this;
      } else if (adapter == EObject.class)
      {
         return protocol;
      }
      return super.getAdapter(adapter);
   }
}
