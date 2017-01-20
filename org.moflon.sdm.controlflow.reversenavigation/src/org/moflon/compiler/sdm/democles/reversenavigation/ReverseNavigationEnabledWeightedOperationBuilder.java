package org.moflon.compiler.sdm.democles.reversenavigation;

import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.OperationRuntime;
import org.gervarro.democles.constraint.emf.Reference;
import org.gervarro.democles.emf.EMFWeightedOperationBuilder;
import org.gervarro.democles.specification.ConstraintType;
import org.gervarro.democles.specification.impl.Constraint;

public class ReverseNavigationEnabledWeightedOperationBuilder<T extends OperationRuntime> extends EMFWeightedOperationBuilder<T>
{
   @Override
   public int getWeight(T operation) {
      Adornment adornment = operation.getPrecondition();
      Object object = operation.getOrigin();
      if (object instanceof Constraint) {
         Constraint constraint = (Constraint) object;
         ConstraintType cType = constraint.getType();

         if (cType instanceof Reference && !((Reference) cType).isBidirectional()) {
            if (adornment.get(0) == Adornment.FREE && adornment.get(1) == Adornment.BOUND) {
               return 100;
            }
         }
      }
      
      return super.getWeight(operation);
   }
}
