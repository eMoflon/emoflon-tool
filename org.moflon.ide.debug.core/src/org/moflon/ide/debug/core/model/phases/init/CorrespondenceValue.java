package org.moflon.ide.debug.core.model.phases.init;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.moflon.ide.debug.core.model.MoflonDebugElement;
import org.moflon.ide.debug.core.model.MoflonVariable;

import org.moflon.tgg.debug.language.DebugCorrespondenceModel;
import org.moflon.tgg.runtime.CorrespondenceModel;

public class CorrespondenceValue extends MoflonDebugElement implements IValue
{
   private static final String TARGET = "Target Model";

   private static final String SOURCE = "Source Model";

   public static final String CORRESPONDENCES = "Correspondences";

   IVariable[] variables;

   CorrespondenceModel corr;

   /**
    * The XML representation for debug purpose.
    */
   String valueString;

   public CorrespondenceValue(IDebugTarget target, DebugCorrespondenceModel dcm)
   {
      super(target);

      corr = dcm;

      this.variables = new IVariable[3];
      this.variables[0] = MoflonVariable.createVariable(corr.getSource(), SOURCE);
      this.variables[1] = MoflonVariable.createVariable(corr.getTarget(), TARGET);
      this.variables[2] = MoflonVariable.createVariable(corr.getCorrespondences(), CORRESPONDENCES);
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      return null;
   }

   @Override
   public String getValueString() throws DebugException
   {
      if (valueString == null)
      {
         valueString = "Count Correspondences: " + corr.getCorrespondences().size();
      }
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
      return variables.length > 0;
   }

   @SuppressWarnings("rawtypes")
   @Override
   public Object getAdapter(Class adapter)
   {
      if (adapter == CorrespondenceModel.class)
      {
         return corr;
      }
      return super.getAdapter(adapter);
   }

}
