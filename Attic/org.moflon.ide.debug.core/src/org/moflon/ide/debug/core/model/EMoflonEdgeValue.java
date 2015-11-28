package org.moflon.ide.debug.core.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

import org.moflon.tgg.debug.language.DebugEObjectProxy;
import org.moflon.tgg.runtime.EMoflonEdge;

public class EMoflonEdgeValue implements IValue
{
   IVariable[] variables;

   DebugEObjectProxy edge;

   public EMoflonEdgeValue(DebugEObjectProxy proxy)
   {
      this.edge = proxy;
      this.variables = new IVariable[2];
      DebugEObjectProxy src = proxy.getChildren().stream().filter(c -> c.getName().equals("src")).findFirst().get();
      DebugEObjectProxy trg = proxy.getChildren().stream().filter(c -> c.getName().equals("trg")).findFirst().get();
      // Assert.isTrue(src.getReferenceChildren().size() == 1);
      // Assert.isTrue(trg.getReferenceChildren().size() == 1);
      // TODO Check why here an exception is thrown without this check
      if (src.getReferenceChildren().size() > 0)
      {
         this.variables[0] = MoflonVariable.createVariable(src.getReferenceChildren().get(0), "Source");
      } else
      {
         this.variables[0] = MoflonVariable.createVariable(null, "Source");
      }
      if (trg.getReferenceChildren().size() > 0)
      {
         this.variables[1] = MoflonVariable.createVariable(trg.getReferenceChildren().get(0), "Target");
      } else
      {
         this.variables[1] = MoflonVariable.createVariable(null, "Target");
      }
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
      return edge.getName() + " : " + EMoflonEdge.class.getName();
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
