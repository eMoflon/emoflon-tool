package DemoclesAttributeConstraintSpecification.constraint;

import org.gervarro.democles.common.TypeModule;
import org.gervarro.democles.specification.ConstraintType;

import DemoclesAttributeConstraintSpecification.impl.ConstraintSpecificationImpl;



public class AttributeVariableConstraintType extends ConstraintSpecificationImpl implements ConstraintType {
	
	
	public final TypeModule getModule() {
		return AttributeVariableConstraintsTypeModule.INSTANCE;
	}
	
	

}
