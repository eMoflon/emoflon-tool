package org.moflon.ide.debug.core.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;

public class EStructuralFeatureValue implements IValue
{
   EStructuralFeature feature;

   EObject eobj;

   IVariable[] variables;

   public EStructuralFeatureValue(EStructuralFeature feature, EObject eobj)
   {
      this.feature = feature;
      this.eobj = eobj;
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
      Object featureValue = eobj.eGet(feature);
      if (featureValue instanceof DynamicEObjectImpl)
      {
         return EObjectValue.getValueString((DynamicEObjectImpl) featureValue);
      }
      return featureValue == null ? null : featureValue.toString();
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
      if (variables == null)
      {
         if (eobj.eGet(feature) != null && eobj.eGet(feature) instanceof EObject)
         {
            variables = MoflonValue.createValue(eobj.eGet(feature)).getVariables();
         } else
         {
            variables = new IVariable[0];
         }
      }
      return variables;
   }

   @Override
   public boolean hasVariables() throws DebugException
   {
      return getVariables().length > 0;
   }

}
