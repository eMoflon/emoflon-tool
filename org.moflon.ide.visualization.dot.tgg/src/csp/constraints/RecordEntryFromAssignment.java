package csp.constraints;

import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class RecordEntryFromAssignment extends TGGConstraintImpl
{
   public void solve(Variable var_0, Variable var_1, Variable var_2)
   {
      String bindingStates = getBindingStates(var_0, var_1, var_2);

      switch (bindingStates)
      {
      case "FBB":
         String attrName = (String) var_1.getValue();
         String literal = (String) var_2.getValue();

         var_0.bindToValue(attrName + " := " + literal.replace("\"", "\\\""));
         setSatisfied(true);
         break;

      default:
         throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
      }

   }
}