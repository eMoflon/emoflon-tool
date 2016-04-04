package csp.constraints;

import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class NamespaceToName extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1){
    	String bindingStates = getBindingStates(var_0, var_1);
    	
    	String namespace = (String) var_0.getValue();
    	String name = (String) var_1.getValue();
    	int index;
    	
    	switch(bindingStates){
    	case "BB":
    		index = namespace.lastIndexOf(".");
    		if(index < 0) {
    			setSatisfied(namespace.equals(name));
    		}
    		else {
    			setSatisfied(namespace.substring(index+1).equals(name));
    		}
    		break;
    		
    	case "BF":
    		index = namespace.lastIndexOf(".");
    		if(index < 0) {
    			var_1.setValue(namespace);
    		}
    		else {
    			var_1.setValue(namespace.substring(index+1));
    		}
    		var_1.setBound(true);
    		setSatisfied(true);
    		break;
    		
    	default: 
    		throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
    	}
    	
  	}	  
}