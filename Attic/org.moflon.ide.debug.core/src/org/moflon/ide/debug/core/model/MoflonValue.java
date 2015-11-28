package org.moflon.ide.debug.core.model;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;

import org.moflon.tgg.debug.language.AttributeProxy;
import org.moflon.tgg.debug.language.DebugAttributeDelta;
import org.moflon.tgg.debug.language.DebugCorrespondence;
import org.moflon.tgg.debug.language.DebugEObjectProxy;
import org.moflon.tgg.runtime.EMoflonEdge;

public class MoflonValue
{

   public static IValue createValue(Object... values)
   {
      Object value = values[0];
      if (value instanceof EList)
      {
         return new EListValue((EList<?>) value);
      } else if (value instanceof DynamicEObjectImpl)
      {
         return new DynamicEObjectImplValue((DynamicEObjectImpl) value);
      } else if (value instanceof EStructuralFeature)
      {
         return new EStructuralFeatureValue((EStructuralFeature) value, (EObject) values[1]);
         // } else if (value instanceof EMoflonEdge)
         // {
         // return new EMoflonEdgeValue((EMoflonEdge) value);
      } else if (value instanceof DebugCorrespondence)
      {
         // TODO Improve presentation
         // return new EObjectValue((EObject) value);
         return new DebugCorrespondenceValue((DebugCorrespondence) value);
      } else if (value instanceof DebugEObjectProxy)
      {
         DebugEObjectProxy proxy = (DebugEObjectProxy) value;
         if (proxy.getClassName() != null && proxy.getClassName().equals(EMoflonEdge.class.getSimpleName()))
         {
            return new EMoflonEdgeValue(proxy);
         } else
         {
            return new DebugEObjectProxyValue((DebugEObjectProxy) value);
         }
      } else if (value instanceof AttributeProxy)
      {
         return new AttributeProxyValue((AttributeProxy) value);
      } else if (value instanceof DebugAttributeDelta)
      {
         return new DebugAttributeDeltaValue((DebugAttributeDelta) value);
      } else if (value instanceof EObject)
      {
         return new EObjectValue((EObject) value);
      } else if (value instanceof String)
      {
         return new StringValue((String) value);
      } else if (value != null)
      {
         return null;
      } else
      {
         return new NullValue();
      }

   }
}
