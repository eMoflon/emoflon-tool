package csp.constraints;

import TGGLanguage.csp.Variable;
import TGGLanguage.csp.impl.TGGConstraintImpl;
import csp.constraints.generator.Generator;

public class Eq extends TGGConstraintImpl
{

   /**
    * Constraint eq(a,b)
    * 
    * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
    */
   public void solve(Variable a, Variable b)
   {
      String bindingStates = getBindingStates(a, b);

      if (bindingStates.equals("BB"))
      {
         if (a.getValue() instanceof Number)
            compareNumbers(a, b);
         else
            setSatisfied(a.getValue().equals(b.getValue()));
      } else if (bindingStates.equals("BF"))
      {
         b.setValue(a.getValue());
         b.setBound(true);
         setSatisfied(true);
      } else if (bindingStates.equals("FB"))
      {
         a.setValue(b.getValue());
         a.setBound(true);
         setSatisfied(true);

      }
      // modelgen implementation
      else if (bindingStates.equals("FF"))
      {
         String value = Generator.getNewRandomString(a.getType());
         a.bindToValue(value);
         b.bindToValue(value);
         setSatisfied(true);
      } else
      {
         throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);

      }
   }

   private void compareNumbers(Variable a, Variable b)
   {
      Number anum = (Number) a.getValue();
      Number bnum = (Number) b.getValue();
      setSatisfied(anum.doubleValue() == bnum.doubleValue());
   }

}
