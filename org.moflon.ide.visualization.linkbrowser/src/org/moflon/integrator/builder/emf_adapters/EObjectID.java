package org.moflon.integrator.builder.emf_adapters;

import org.eclipse.emf.ecore.EObject;

import de.fhg.iao.swt.util.uniqueidentifier.ID;

public class EObjectID implements ID
{

   private static final long serialVersionUID = 1L;

   private EObject idObject;

   public EObjectID(EObject idObject)
   {
      super();
      this.idObject = idObject;
   }

   @Override
   public Object getIDObject()
   {
      return idObject;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((idObject == null) ? 0 : idObject.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      EObjectID other = (EObjectID) obj;
      if (idObject == null)
      {
         if (other.idObject != null)
            return false;
      } else if (!idObject.equals(other.idObject))
         return false;
      return true;
   }

}
