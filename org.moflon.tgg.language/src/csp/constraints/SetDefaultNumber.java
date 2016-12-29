package csp.constraints;

import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

import csp.constraints.generator.Generator;

public class SetDefaultNumber extends TGGConstraintImpl
{
   public void solve(Variable var_0, Variable var_1)
   {
      String bindingStates = getBindingStates(var_0, var_1);

      switch (bindingStates)
      {
      case "BB":
         setSatisfied(true);
         return;
      case "FB":
         var_0.bindToValue(var_1.getValue());
         setSatisfied(true);
         return;

         // modelgen implementations
      case "FF":
         int number = Generator.getNewUniqueNumber();
         var_0.bindToValue(number);
         var_1.bindToValue(number);
         setSatisfied(true);
         return;
      default:
         throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
      }

   }
}