package org.moflon.ide.debug.core.model.phases.init;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.moflon.ide.debug.core.model.MoflonDebugElement;

import DebugLanguage.DebugSynchronizationProtocol;

public class SynchronizationValue extends MoflonDebugElement implements IValue
{

   private IVariable[] variables;

   private DebugSynchronizationProtocol value;

   public SynchronizationValue(IDebugTarget target, DebugSynchronizationProtocol protocol)
   {
      super(target);
      value = protocol;
   }

   @Override
   public String getReferenceTypeName() throws DebugException
   {
      return null; // jdiProtocol.getReferenceTypeName();
   }

   @Override
   public String getValueString() throws DebugException
   {
      // try
      // {
      return "Count: " + getVariables().length;// "XML Representation not yet possible";//
                                               // DebugSynchronizationHelper.convertToXml(value);
      // } catch (IOException e)
      // {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // } // jdiProtocol.toString();
      // return null;
   }

   @Override
   public boolean isAllocated() throws DebugException
   {
      return false;// jdiProtocol.isAllocated();
   }

   @Override
   public IVariable[] getVariables() throws DebugException
   {
      if (variables == null)
      {
         variables = new IVariable[value.getMatches().size()];
         for (int i = 0; i < variables.length; i++)
         {
            variables[i] = new TripleMatchVariable(getDebugTarget(), value.getMatches().get(i));
         }
      }

      return variables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      return getVariables().length > 0;
   }

   @SuppressWarnings("rawtypes")
   @Override
   public Object getAdapter(Class adapter)
   {
      // return jdiProtocol.getAdapter(adapter);
      return super.getAdapter(adapter);
   }

}
