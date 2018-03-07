package org.moflon.compiler.sdm.democles;

import java.util.LinkedList;
import java.util.List;

import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.GeneratorVariable;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.constraint.CoreConstraintModule;
import org.gervarro.democles.specification.ConstraintType;
import org.gervarro.democles.specification.impl.Constraint;

public class BindingAssignmentOperationBuilder extends AssignmentOperationBuilder {

	@Override
	public List<GeneratorOperation> getConstraintOperations(Constraint constraint, List<GeneratorVariable> parameters) {
		ConstraintType cType = constraint.getType();
		if (cType == CoreConstraintModule.EQUAL) {
			List<GeneratorOperation> result = new LinkedList<GeneratorOperation>();
			final boolean isTypeCheckNeeded = isTypeCheckNeeded(parameters);
			result.add(new GeneratorOperation(constraint, parameters, Adornment.create(Adornment.FREE, Adornment.BOUND),
					Adornment.create(isTypeCheckNeeded ? Adornment.NOT_TYPECHECKED : Adornment.BOUND, Adornment.BOUND),
					CoreConstraintModule.EQUAL));
			return result;
		}
		return null;
	}
}
