package org.moflon.compiler.sdm.democles;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.GeneratorVariable;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.runtime.OperationBuilder;
import org.gervarro.democles.constraint.CoreConstraintModule;
import org.gervarro.democles.constraint.emf.EMFVariable;
import org.gervarro.democles.specification.ConstraintType;
import org.gervarro.democles.specification.impl.Constraint;
import org.gervarro.democles.specification.impl.Variable;

public class AssignmentOperationBuilder implements OperationBuilder<GeneratorOperation, GeneratorVariable> {

	@Override
	public List<GeneratorOperation> getConstraintOperations(Constraint constraint, List<GeneratorVariable> parameters) {
		ConstraintType cType = constraint.getType();
		if (cType == CoreConstraintModule.EQUAL) {
			List<GeneratorOperation> result = new LinkedList<GeneratorOperation>();
			final boolean isTypeCheckNeeded = isTypeCheckNeeded(parameters);
			result.add(new GeneratorOperation(constraint, parameters, Adornment.create(Adornment.FREE, Adornment.BOUND),
					Adornment.create(isTypeCheckNeeded ? Adornment.NOT_TYPECHECKED : Adornment.BOUND, Adornment.BOUND),
					CoreConstraintModule.EQUAL, isTypeCheckNeeded ? 0 : GeneratorOperation.ALWAYS_SUCCESSFUL));
			return result;
		}
		return null;
	}

	@Override
	public final GeneratorOperation getVariableOperation(Variable variable, GeneratorVariable runtimeVariable) {
		return null;
	}

	final boolean isTypeCheckNeeded(List<GeneratorVariable> parameters) {
		final EClassifier leftType = lookupEClassifier(parameters.get(0));
		if (EcorePackage.eINSTANCE.getEClass().isInstance(leftType)) {
			if (parameters.get(1).getSpecification() instanceof Variable) {
				final EClassifier rightType = lookupEClassifier(parameters.get(1));
				return EcorePackage.eINSTANCE.getEClass().isInstance(rightType)
						&& !((EClass) leftType).isSuperTypeOf((EClass) rightType);
			}
		}
		return false;
	}

	public static EClassifier lookupEClassifier(final GeneratorVariable variableRuntime) {
		final Variable variable = (Variable) variableRuntime.getSpecification();
		final EMFVariable emfVariable = (EMFVariable) variable.getType();
		return emfVariable.getLinkedElement();
	}
}
