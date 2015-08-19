package csp.constraints.common;

import SDMLanguage.patterns.BindingOperator;
import SDMLanguage.patterns.BindingSemantics;

import org.moflon.ide.visualization.dot.language.DotColor;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class SetColor extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1, Variable var_2){
    	String bindingStates = getBindingStates(var_0, var_1, var_2);
    	
      BindingOperator op = (BindingOperator) var_1.getValue();
    	BindingSemantics negative = (BindingSemantics) var_2.getValue();
      
    	switch(bindingStates){
    	case "FBB":
    	  if(negative.equals(BindingSemantics.NEGATIVE)){
    	     var_0.bindToValue(DotColor.BLUE);
    	     setSatisfied(true);
    	     return;
    	  }
    	   
    	  switch (op) {
        case CHECK_ONLY:
           var_0.bindToValue(DotColor.BLACK);
           setSatisfied(true);
           return;
        
        case CREATE:
           var_0.bindToValue(DotColor.GREEN);
           setSatisfied(true);
           return;
           
        case DESTROY:
           var_0.bindToValue(DotColor.RED); 
           setSatisfied(true);
           return;
           
        default:
           break;
        }	
    	default: 
    		throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
    	}
    	
  	}	  
}