package org.moflon.integrator.builder.emf_adapters;

import org.eclipse.emf.ecore.EObject;

import de.fhg.iao.matrixbrowser.model.elements.MBModelNode;

public class NodeEObjectAdapter extends MBModelNode
{
   private static final long serialVersionUID = 1L;

   private EObject eObject;

   public NodeEObjectAdapter(String name, EObject eObject)
   {
      super(name, new EObjectID(eObject));
      this.eObject = eObject;
   }

   public EObject getObject()
   {
      return eObject;
   }
}
