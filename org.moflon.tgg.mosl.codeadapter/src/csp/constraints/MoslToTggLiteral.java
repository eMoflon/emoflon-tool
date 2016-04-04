package csp.constraints;

import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;

public class MoslToTggLiteral extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1){
    	String bindingStates = getBindingStates(var_0, var_1);

    	String moslLiteral = (String) var_0.getValue();
    	String tggLiteral = (String) var_1.getValue();
    	
    	switch(bindingStates){
    	case "BF":
    		// TODO Implement BF-operation
//    		throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
    		
    		if (moslLiteral.contains("::")) {
//    			moslLiteral.replace("::", ".");
    			var_1.setValue(moslLiteral.replace("::", "."));
			} else {
				var_1.setValue(var_0.getValue());
			}
			var_1.setBound(true);
    		setSatisfied(true);
    		break;
    		
    	case "FB":
    		// TODO Implement FB-operation
//    		throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);

    		if (tggLiteral.contains(".")) {
    			var_0.setValue(tggLiteral.replace(".", "::"));
			} else {
				var_0.setValue(var_1.getValue());
			}
			var_0.setBound(true);
    		setSatisfied(true);
    		break;
    	case "BB":
    		// TODO Implement BB-operation
//    		throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
    		
    		if (moslLiteral.contains("::")) {
        		setSatisfied(moslLiteral.equals(tggLiteral.replace(".", "::")));
    			
			} else {
				if (var_0.getValue() instanceof Number) {
	        		Number anum = (Number) var_0.getValue();
	        	    Number bnum = (Number) var_1.getValue();
	        	    setSatisfied(anum.doubleValue() == bnum.doubleValue());
				} else {
					setSatisfied(var_0.getValue().equals(var_1.getValue()));
				}
			}
    		break;
    		
    	default: 
    		throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
    	}
    	
  	}	  
}