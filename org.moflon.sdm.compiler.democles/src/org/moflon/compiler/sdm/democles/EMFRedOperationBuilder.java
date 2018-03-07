package org.moflon.compiler.sdm.democles;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.GeneratorVariable;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.runtime.OperationBuilder;
import org.gervarro.democles.constraint.emf.EMFConstraint;
import org.gervarro.democles.constraint.emf.EMFVariable;
import org.gervarro.democles.constraint.emf.Reference;
import org.gervarro.democles.specification.impl.Constraint;
import org.gervarro.democles.specification.impl.Variable;

public class EMFRedOperationBuilder implements OperationBuilder<GeneratorOperation, GeneratorVariable> {

	public List<GeneratorOperation> getConstraintOperations(Constraint constraint, List<GeneratorVariable> parameters) {
		if (constraint.getType() instanceof EMFConstraint<?>) {
			List<GeneratorOperation> result = new LinkedList<GeneratorOperation>();
			EMFConstraint<?> cType = (EMFConstraint<?>) constraint.getType();
			if (cType instanceof Reference) {
				result.add(new GeneratorOperation(constraint, parameters,
						Adornment.create(Adornment.BOUND, Adornment.BOUND),
						Adornment.create(Adornment.BOUND, Adornment.BOUND), cType,
						GeneratorOperation.ALWAYS_SUCCESSFUL));

				return result;
			}
		}
		return null;
	}

	public GeneratorOperation getVariableOperation(Variable variable, GeneratorVariable runtimeVariable) {
		if (variable.getType() instanceof EMFVariable) {
			EClassifier eClassifier = ((EMFVariable) variable.getType()).getLinkedElement();
			if (eClassifier instanceof EClass) {
				return new GeneratorOperation(variable, Collections.singletonList(runtimeVariable),
						Adornment.create(Adornment.NOT_TYPECHECKED), Adornment.create(Adornment.BOUND),
						variable.getType(), GeneratorOperation.ALWAYS_SUCCESSFUL);
			}
		}
		return null;
	}
}
