package org.moflon.codegen;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;

public class MoflonOperationIterator implements Iterator<EOperation>
{
   private final TreeIterator<EObject> treeIterator;

   private EOperation currentOperation;

   public MoflonOperationIterator(Resource resource)
   {
      this.treeIterator = resource.getAllContents();
      advanceInternalIterator();
   }

   public MoflonOperationIterator(EObject eObject)
   {
      this.treeIterator = eObject.eAllContents();
      advanceInternalIterator();
   }

   private void advanceInternalIterator()
   {
      while (treeIterator.hasNext())
      {
         EObject eObject = treeIterator.next();
         if (isMoflonOperation(eObject))
         {
            currentOperation = (EOperation) eObject;
            treeIterator.prune();
            return;
         }
      }
      currentOperation = null;
   }

   protected boolean isMoflonOperation(EObject object)
   {
      if (EcorePackage.eINSTANCE.getEOperation().isInstance(object))
      {
         return true;
      }
      return false;
   }

   @Override
   public boolean hasNext()
   {
      return currentOperation != null;
   }

   @Override
   public EOperation next()
   {
      if (hasNext())
      {
         final EOperation result = currentOperation;
         advanceInternalIterator();
         return result;
      } else
      {
         throw new NoSuchElementException();
      }
   }

   @Override
   public final void remove()
   {
      throw new UnsupportedOperationException();
   }
}
