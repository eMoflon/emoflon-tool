package org.moflon.ide.debug.core.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

public class EObjectValue implements IValue
{
   EObject eobj;

   IVariable[] variables;

   public EObjectValue(EObject value)
   {
      this.eobj = value;
      EList<EAttribute> attributes = eobj.eClass().getEAllAttributes();
      EList<EStructuralFeature> features = eobj.eClass().getEStructuralFeatures();
      this.variables = new IVariable[attributes.size() + features.size()];

      for (int i = 0; i < attributes.size(); i++)
      {
         variables[i] = MoflonVariable.createVariable(attributes.get(i), value);
      }

      for (int i = 0; i < features.size(); i++)
      {
         variables[i + attributes.size()] = MoflonVariable.createVariable(features.get(i), value);
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

   public static String getValueString(EObject deob)
   {
      if (deob.eClass() != null && deob.eClass().eContainer() instanceof EPackage)
      {
         EPackage p = (EPackage) deob.eClass().eContainer();
         return p.getName() + "." + deob.eClass().getName();
      }
      return deob.toString();
   }

   @Override
   public String getValueString() throws DebugException
   {
      return getValueString(eobj);
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
