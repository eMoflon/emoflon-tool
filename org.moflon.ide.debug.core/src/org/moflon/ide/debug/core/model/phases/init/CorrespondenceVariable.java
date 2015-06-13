package org.moflon.ide.debug.core.model.phases.init;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EObject;
import org.moflon.ide.debug.core.model.MoflonDebugElement;

import DebugLanguage.DebugCorrespondenceModel;
import TGGRuntime.CorrespondenceModel;

public class CorrespondenceVariable extends MoflonDebugElement implements IVariable
{
   IValue value;

   DebugCorrespondenceModel dcm;

   public CorrespondenceVariable(IDebugTarget target, DebugCorrespondenceModel debugCorrespondenceModel)
   {
      super(target);
      dcm = debugCorrespondenceModel;
      value = new CorrespondenceValue(getDebugTarget(), dcm);
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
      return "Correspondence Model";
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

   @Override
   public Object getAdapter(Class adapter)
   {
      if (adapter == CorrespondenceModel.class || adapter == EObject.class)
      {
         // TODO identify place where the triple match view might delete or re-contain correspondences
         return (CorrespondenceModel) dcm;// EcoreUtil.copy(dcm);
      }
      return super.getAdapter(adapter);
   }

}
