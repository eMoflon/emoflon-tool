package org.moflon.ide.debug.core.model;

import javax.naming.OperationNotSupportedException;

import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.moflon.ide.debug.core.model.phases.init.RuleVariable;
import org.moflon.ide.debug.core.model.phases.init.RuleVariable.Mode;
import org.moflon.ide.debug.core.model.phases.translation.MatchVariable;

import org.moflon.tgg.debug.language.AttributeProxy;
import org.moflon.tgg.debug.language.DebugAttributeDelta;
import org.moflon.tgg.debug.language.DebugCorrespondence;
import org.moflon.tgg.debug.language.DebugEObjectProxy;
import org.moflon.tgg.debug.language.DebugMatch;
import org.moflon.tgg.language.analysis.Rule;
import org.moflon.tgg.runtime.EMoflonEdge;

public class MoflonVariable // extends MoflonDebugElement implements IValue
{
   public static IVariable createVariable(Object... values)
   {
      Object value = values[0];
      if (value instanceof EList)
      {
         switch (values.length)
         {
         case 1:
            return new EListVariable((EList<?>) value);
         case 2:
            return new EListVariable((EList<?>) value, (String) values[1]);
         default:
            break;
         }
      } else if (value instanceof DynamicEObjectImpl)
      {
         switch (values.length)
         {
         case 1:
            return new DynamicEObjectImplVariable((DynamicEObjectImpl) value);
         case 2:
            return new DynamicEObjectImplVariable((DynamicEObjectImpl) value, (String) values[1]);
         default:
            break;
         }
      } else if (value instanceof EStructuralFeature)
      {
         return new EStructuralFeatureVariable((EStructuralFeature) value, (EObject) values[1]);
         // } else if (value instanceof EMoflonEdge)
         // {
         // return new EMoflonEdgeVariable((EMoflonEdge) value);
      } else if (value instanceof DebugCorrespondence)
      {
         switch (values.length)
         {
         case 1:
            return new DebugCorrespondenceVariable((DebugCorrespondence) value);
         case 2:
            return new DebugCorrespondenceVariable((DebugCorrespondence) value, (String) values[1]);
         default:
            break;
         }
      } else if (value instanceof DebugEObjectProxy)
      {
         DebugEObjectProxy proxy = (DebugEObjectProxy) value;
         if (proxy.getClassName() != null && proxy.getClassName().equals(EMoflonEdge.class.getSimpleName()))
         {
            return new EMoflonEdgeVariable(proxy);
         } else
         {
            switch (values.length)
            {
            case 1:
               return new DebugEObjectProxyVariable((DebugEObjectProxy) value);
            case 2:
               return new DebugEObjectProxyVariable((DebugEObjectProxy) value, (String) values[1]);
            default:
               break;
            }
         }
      } else if (value instanceof AttributeProxy)
      {
         return new AttributeProxyVariable((AttributeProxy) value);
      } else if (value instanceof DebugAttributeDelta)
      {
         return new DebugAttributeDeltaVariable((DebugAttributeDelta) value);
      } else if (value instanceof DebugMatch)
      {
         return new MatchVariable((DebugMatch) value);

      } else if (value instanceof Rule)
      {
         return new RuleVariable((Mode) values[1], (Rule) value, (String) values[2]);
      } else if (value instanceof EObject)
      {
         switch (values.length)
         {
         case 1:
            return new EObjectVariable((EObject) value);
         case 2:
            return new EObjectVariable((EObject) value, (String) values[1]);
         default:
            break;
         }
      } else if (value instanceof String)
      {
         return new StringVariable((String) value, (String) values[1]);
      } else if (value == null)
      {
         return new NullVariable((String) values[1]);
      }
      throw new RuntimeException(new OperationNotSupportedException("Debugging of variables of type '" + value + "' [" + value.getClass().getName()
            + "] is currently not supported. Please contact the eMoflon Team."));
   }
}
