package csp.constraints;

import SDMLanguage.patterns.BindingState;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class BoldToState extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1){
    	String bindingStates = getBindingStates(var_0, var_1);
    	
    	switch(bindingStates){
    	case "FB":
    		BindingState state = (BindingState) var_1.getValue(); 
    		switch (state)
         {
         case BOUND:
            var_0.bindToValue(true);
            break;
         case UNBOUND:
            var_0.bindToValue(false);
            break;

         default:
            break;
         }
    		break;
    	default: 
    		throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
    	}
    	
    	setSatisfied(true);
  	}	  
}