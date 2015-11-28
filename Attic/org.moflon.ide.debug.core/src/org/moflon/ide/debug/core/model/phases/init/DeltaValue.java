package org.moflon.ide.debug.core.model.phases.init;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.moflon.ide.debug.core.model.MoflonDebugElement;
import org.moflon.ide.debug.core.model.MoflonVariable;
import org.moflon.tgg.debug.language.DebugDelta;
import org.moflon.tgg.runtime.CorrespondenceModel;

public class DeltaValue extends MoflonDebugElement implements IValue
{
   public static final String ATTRIBUTE_CHANGE = "Attribute change";

   public static final String DELETED_NODES = "Deleted nodes";

   public static final String DELETED_EDGES = "Deleted edges";

   public static final String ADDED_NODES = "Added nodes";

   public static final String ADDED_EDGES = "Added edges";

   DebugDelta delta;

   IVariable[] variables;

   /**
    * The XML representation for debug purpose.
    */
   String valueString;

   public DeltaValue(IDebugTarget target, DebugDelta delta)
   {
      super(target);
      this.delta = delta;

      this.variables = new IVariable[5];
      this.variables[0] = MoflonVariable.createVariable(delta.getAddedEdges(), ADDED_EDGES);
      this.variables[1] = MoflonVariable.createVariable(delta.getAddedNodes(), ADDED_NODES);
      this.variables[2] = MoflonVariable.createVariable(delta.getDeletedEdges(), DELETED_EDGES);
      this.variables[3] = MoflonVariable.createVariable(delta.getDeletedNodes(), DELETED_NODES);
      this.variables[4] = MoflonVariable.createVariable(delta.getAttributeChanges(), ATTRIBUTE_CHANGE);
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      return null;
   }

   @Override
   public String getValueString() throws DebugException
   {
      return valueString;
   }

   @Override
   public boolean isAllocated() throws DebugException
   {
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
      return variables != null;
   }

   @SuppressWarnings("rawtypes")
   @Override
   public Object getAdapter(Class adapter)
   {
      if (adapter == CorrespondenceModel.class)
      {
         return delta;
      }
      return super.getAdapter(adapter);
   }

}
