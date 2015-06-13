package org.moflon.ide.debug.core.model.phases.init;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.moflon.ide.debug.core.model.MoflonDebugElement;

import TGGRuntime.EMoflonEdge;

public class SingleDeltaVariable extends MoflonDebugElement implements IVariable
{

   /**
    * Modes for display of: </br> delta.getAddedEdges();</br> delta.getAddedNodes();</br>
    * 
    * delta.getDeletedEdges();</br> delta.getDeletedNodes();</br>
    * 
    * delta.getAttributeChanges();</br>
    *
    * @author Marco Ballhausen
    */
   public static enum Mode {
      ADDED_EDGE, ADDED_EDGES, ADDED_NODE, ADDED_NODES, DELETED_EDGE, DELETED_EDGES, DELETED_NODE, DELETED_NODES, ATTRIBUTE_CHANGE, ATTRIBUTE_CHANGES, DELTA
   };

   Mode mode;

   IValue value;

   EMoflonEdge edge;

   EObject node;

   public SingleDeltaVariable(IDebugTarget target, Mode mode, EMoflonEdge edge) throws Exception
   {
      super(target);
      if (!(mode.equals(Mode.ADDED_EDGE) || mode.equals(Mode.DELETED_EDGE)))
      {
         throw new Exception("Only " + Mode.ADDED_EDGE + ", " + Mode.DELETED_EDGE + " supported for this constructor.");
      }
      this.value = new SingleDeltaValue(mode, edge);
      this.mode = mode;
      this.edge = edge;
   }

   public SingleDeltaVariable(IDebugTarget target, Mode mode, EObject node) throws Exception
   {
      super(target);
      if (!(mode.equals(Mode.ADDED_NODE) || mode.equals(Mode.DELETED_NODE)))
      {
         throw new Exception("Only " + Mode.ADDED_NODE + ", " + Mode.DELETED_NODE + " supported for this constructor.");
      }
      this.value = new SingleDeltaValue(target, mode, node);
      this.mode = mode;
      this.node = node;
   }

   public SingleDeltaVariable(IDebugTarget debugTarget, Mode mode, EList<? extends EObject> addedEdgesOrNodesOrAttributeChanges) throws Exception
   {
      super(debugTarget);
      // Class<?> clazz = addedEdges.get(0).getClass();
      // if (clazz == EMoflonEdgeImpl.class)
      // {
      // } else if (clazz == EObjectImpl.class)
      // {
      // // this.value = new SingleDeltaValue(, edge)
      // } else
      // {
      // // Exception
      // }
      this.value = new SingleDeltaValue(debugTarget, mode, addedEdgesOrNodesOrAttributeChanges);
      this.mode = mode;
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
      switch (mode)
      {
      case ADDED_EDGE:
         return edge.getName();
      case ADDED_NODE:
         URI proxyURI = ((InternalEObject) node).eProxyURI();
         if (proxyURI == null)
         {
            return node.eClass().getName();
         } else
         {
            return proxyURI.fragment();
         }
      case ADDED_EDGES:
         return "Added edges";
      case ADDED_NODES:
         return "Added nodes";
      case DELETED_EDGES:
         return "Deleted edges";
      case DELETED_NODES:
         return "Deleted node";
      case ATTRIBUTE_CHANGES:
         return "Attribute changes";
      case DELTA:
         return "Delta";
      default:
         return null;
      }
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

}
