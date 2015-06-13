package org.moflon.ide.debug.core.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import DebugLanguage.DebugCorrespondence;

public class DebugCorrespondenceValue extends MoflonDebugElement implements IValue
{
   IVariable[] variables;

   DebugCorrespondence dc;

   public DebugCorrespondenceValue(DebugCorrespondence dc)
   {
      super(null);
      this.dc = dc;
      this.variables = new IVariable[2];
      this.variables[0] = MoflonVariable.createVariable(dc.getSource(), "Source");
      this.variables[1] = MoflonVariable.createVariable(dc.getTarget(), "Target");
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
      return dc.getName() + " : " + dc.getPackageName() + "." + dc.getClassName();
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
      return variables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      return variables.length > 0;
   }

}
