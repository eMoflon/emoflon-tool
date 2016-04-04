package csp.constraints;

import org.apache.commons.lang3.StringUtils;
import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class CapitalizeString extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1){
    	String bindingStates = getBindingStates(var_0, var_1);

    	String inputString = (String) var_0.getValue();
    	String capitalizedString = (String) var_1.getValue();
    	
    	switch(bindingStates){
    	case "BB":
    		setSatisfied(StringUtils.capitalize(inputString).equals(capitalizedString));
    		break;
    		
    		
    	case "BF":
    		var_1.setValue(StringUtils.capitalize(inputString));
    		var_1.setBound(true);
    		setSatisfied(true);
    		break;
    		
    	default: 
    		throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
    	}
    	
  	}	  
}