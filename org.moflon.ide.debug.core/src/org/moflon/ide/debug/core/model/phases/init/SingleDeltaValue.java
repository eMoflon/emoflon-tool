package org.moflon.ide.debug.core.model.phases.init;

import java.util.StringJoiner;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.moflon.ide.debug.core.model.phases.init.SingleDeltaVariable.Mode;

import org.moflon.tgg.runtime.Delta;
import org.moflon.tgg.runtime.EMoflonEdge;

public class SingleDeltaValue implements IValue
{

   private IVariable[] variables;

   private EMoflonEdge edge;

   private Mode mode;

   private EObject node;

   public SingleDeltaValue(Mode mode, EMoflonEdge edge)
   {
      this.mode = mode;
      this.edge = edge;
   }

   public SingleDeltaValue(IDebugTarget target, Mode mode, EObject node)
   {
      this.mode = mode;
      this.node = node;
   }

   /**
    * Receives an {@link Delta#getAddedEdges()},{@link Delta#getAddedNodes()()} or {@link Delta#getAttributeChanges()()}
    * 
    * @param debugTarget
    * @param mode
    * @param addedEdgesOrNodesOrAttributeChanges
    * @throws Exception
    */
   public SingleDeltaValue(IDebugTarget debugTarget, Mode mode, EList<? extends EObject> addedEdgesOrNodesOrAttributeChanges) throws Exception
   {
      this.mode = mode;
      this.variables = new IVariable[addedEdgesOrNodesOrAttributeChanges.size()];
      for (int i = 0; i < variables.length; i++)
      {
         switch (mode)
         {
         case ADDED_EDGES:
            variables[i] = new SingleDeltaVariable(debugTarget, Mode.ADDED_EDGE, (EMoflonEdge) addedEdgesOrNodesOrAttributeChanges.get(i));
            break;
         case ADDED_NODES:
            variables[i] = new SingleDeltaVariable(debugTarget, Mode.ADDED_NODE, addedEdgesOrNodesOrAttributeChanges.get(i));
            break;
         case DELETED_EDGE:
            variables[i] = new SingleDeltaVariable(debugTarget, Mode.DELETED_EDGE, (EMoflonEdge) addedEdgesOrNodesOrAttributeChanges.get(i));
            break;
         case DELETED_NODES:
            variables[i] = new SingleDeltaVariable(debugTarget, Mode.DELETED_NODE, addedEdgesOrNodesOrAttributeChanges.get(i));
            break;
         case ATTRIBUTE_CHANGES:
            variables[i] = new SingleDeltaVariable(debugTarget, Mode.ATTRIBUTE_CHANGE, addedEdgesOrNodesOrAttributeChanges.get(i));
            break;
         default:
            break;
         }
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

   @SuppressWarnings("rawtypes")
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
      switch (mode)
      {
      case ADDED_EDGE:
      case DELETED_EDGE:
         return edge.getName();
      case ADDED_NODE:
      case DELETED_NODE:
         URI proxyURI = ((InternalEObject) node).eProxyURI();
         if (proxyURI == null)
         {
            StringJoiner sj = new StringJoiner(", ");
            for (int i = 0; i < node.eClass().getFeatureCount(); i++)
            {
               DynamicEObjectImpl deoi = (DynamicEObjectImpl) node;
               Object o = deoi.dynamicGet(i);
               if (o == null)
               {
                  sj.add("null");
               } else
               // if (o instanceof EList<?>)
               {
                  sj.add(o.toString());
               } // else
               // {
               // sj.add((String) o);
               // }
            }
            return sj.toString();
         } else
         {
            return proxyURI.toString();
         }
      case ADDED_EDGES:
      case ADDED_NODES:
      case DELETED_EDGES:
      case DELETED_NODES:
      case ATTRIBUTE_CHANGES:
         return "Count: " + variables.length;
      default:
         return null;
      }
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
      return variables != null && variables.length > 0;
   }

}
