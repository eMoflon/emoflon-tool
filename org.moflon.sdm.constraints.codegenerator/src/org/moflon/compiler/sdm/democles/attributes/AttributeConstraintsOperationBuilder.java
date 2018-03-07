package org.moflon.compiler.sdm.democles.attributes;

import java.util.LinkedList;
import java.util.List;

import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.GeneratorVariable;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.runtime.OperationBuilder;
import org.gervarro.democles.specification.impl.Constraint;
import org.gervarro.democles.specification.impl.Variable;
import org.moflon.sdm.constraints.operationspecification.ConstraintSpecification;
import org.moflon.sdm.constraints.operationspecification.OperationSpecification;
import org.moflon.sdm.constraints.operationspecification.OperationSpecificationGroup;

public class AttributeConstraintsOperationBuilder implements OperationBuilder<GeneratorOperation, GeneratorVariable> {

	@Override
	public List<GeneratorOperation> getConstraintOperations(Constraint constraint, List<GeneratorVariable> parameters) {

		if (constraint.getType() instanceof ConstraintSpecification) {
			ConstraintSpecification cType = (ConstraintSpecification) constraint.getType();
			List<GeneratorOperation> result = new LinkedList<GeneratorOperation>();
			OperationSpecificationGroup opSpecGroup = cType.getOperationSpecificationGroup();
			for (OperationSpecification opSpec : opSpecGroup.getOperationSpecifications()) {
				if (opSpec.isAlwaysSuccessful()) {
					result.add(new GeneratorOperation(constraint, parameters,
							Adornment.create(createBindingsFromString(opSpec.getAdornmentString())),
							Adornment.create(createConstantBindingsOfLength(opSpec.getAdornmentString().length(), 'B')),
							cType, GeneratorOperation.ALWAYS_SUCCESSFUL));
				} else {
					result.add(new GeneratorOperation(constraint, parameters,
							Adornment.create(createBindingsFromString(opSpec.getAdornmentString())),
							Adornment.create(createConstantBindingsOfLength(opSpec.getAdornmentString().length(), 'B')),
							cType));
				}
			}

			return result;
		}
		return null;

	}

	@Override
	public GeneratorOperation getVariableOperation(Variable variable, GeneratorVariable runtimeVariable) {
		return null;
	}

	private int[] createBindingsFromString(String adornmentString) {
		int[] bindings = new int[adornmentString.length()];
		for (int i = 0; i < adornmentString.length(); i++) {
			switch (adornmentString.charAt(i)) {
			case 'F':
				bindings[i] = Adornment.FREE;
				break;
			case 'B':
				bindings[i] = Adornment.BOUND;
				break;
			default:
				throw new RuntimeException("error in adornment string");
			}

		}
		return bindings;

	}

	private int[] createConstantBindingsOfLength(int length, char adornmentChar) {
		int[] bindings = new int[length];
		for (int i = 0; i < length; i++) {
			switch (adornmentChar) {
			case 'F':
				bindings[i] = Adornment.FREE;
				break;
			case 'B':
				bindings[i] = Adornment.BOUND;
				break;
			default:
				throw new RuntimeException("error in adornment string");
			}

		}
		return bindings;

	}

}
