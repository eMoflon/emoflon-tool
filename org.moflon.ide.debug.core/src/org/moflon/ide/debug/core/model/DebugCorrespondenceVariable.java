package org.moflon.ide.debug.core.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import DebugLanguage.DebugCorrespondence;

public class DebugCorrespondenceVariable extends MoflonDebugElement implements IVariable
{
   IValue value;

   String label;

   private DebugCorrespondence dc;

   public DebugCorrespondenceVariable(DebugCorrespondence dc)
   {
      super(null);
      this.value = MoflonValue.createValue(dc);
      this.dc = dc;
   }

   public DebugCorrespondenceVariable(DebugCorrespondence dc, String label)
   {
      this(dc);
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
      // TODO Auto-generated method stub
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
         return dc.getName();
      } else
      {
         return label;
      }
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
      // TODO Auto-generated method stub
      return false;
   }

}
