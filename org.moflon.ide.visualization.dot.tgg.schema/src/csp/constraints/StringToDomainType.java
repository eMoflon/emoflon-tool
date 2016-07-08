package csp.constraints;

import org.moflon.tgg.language.csp.Variable;
import org.moflon.tgg.language.csp.impl.TGGConstraintImpl;
import org.moflon.tgg.language.DomainType;

public class StringToDomainType extends TGGConstraintImpl {
	public void solve(Variable var_0, Variable var_1) {
		String bindingStates = getBindingStates(var_0, var_1);

		switch (bindingStates) {
		case "BF":
			if (var_0.getValue() instanceof String) {
				String stringValue = String.class.cast(var_0.getValue());
				switch (stringValue) {
				case "target":
					var_1.setValue(DomainType.TARGET);
					break;
				case "source":
					var_1.setValue(DomainType.SOURCE);
					break;
				case "correspondence":
					var_1.setValue(DomainType.CORRESPONDENCE);
					break;
				default:
					throw new UnsupportedOperationException("This Domaintype is not supported");
				}
				var_1.setBound(true);
				setSatisfied(true);
				return;
			} else
				throw new UnsupportedOperationException(
						"This case in the constraint has not been implemented yet: " + bindingStates);
		case "FB":
			if(var_1.getValue() instanceof DomainType){
				DomainType domainType=DomainType.class.cast(var_1.getValue());
				var_0.setValue(getStringOfDomainType(domainType));
				var_0.setBound(true);
				setSatisfied(true);
				return;
			}else
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		case "BB":
			if(var_1.getValue() instanceof DomainType){
				DomainType domainType=DomainType.class.cast(var_1.getValue());
				var_0.setValue(getStringOfDomainType(domainType));
				setSatisfied(true);
				return;
			}else
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}
	
	private String getStringOfDomainType(DomainType domainType){
		switch(domainType){
		case CORRESPONDENCE:
			return "correspondence";

		case SOURCE:
			return "source";

		case TARGET:
			return "target";
			
		default:
			throw new UnsupportedOperationException("This Domaintype is not supported");				
		}
	}
}