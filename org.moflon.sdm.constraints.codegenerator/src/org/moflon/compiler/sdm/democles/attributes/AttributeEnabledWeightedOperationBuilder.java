package org.moflon.compiler.sdm.democles.attributes;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.OperationRuntime;
import org.gervarro.democles.constraint.CoreConstraintModule;
import org.gervarro.democles.constraint.PatternInvocationConstraintType;
import org.gervarro.democles.constraint.emf.EMFConstraint;
import org.gervarro.democles.constraint.emf.Operation;
import org.gervarro.democles.constraint.emf.Reference;
import org.gervarro.democles.constraint.emf.StructuralFeature;
import org.gervarro.democles.plan.WeightedOperationBuilder;
import org.gervarro.democles.specification.ConstraintType;
import org.gervarro.democles.specification.impl.Constraint;
import org.gervarro.democles.specification.impl.Variable;
import org.moflon.sdm.constraints.operationspecification.ConstraintSpecification;

public class AttributeEnabledWeightedOperationBuilder<T extends OperationRuntime> extends WeightedOperationBuilder<T> {

	@Override
	public int getWeight(final T operation) {
		Adornment adornment = operation.getPrecondition();
		Object object = operation.getOrigin();
		if (object instanceof Constraint) {
			Constraint constraint = (Constraint) object;
			ConstraintType cType = constraint.getType();

			if (adornment.numberOfFrees() == 0) {
				if (cType instanceof PatternInvocationConstraintType) {
					return 5;
				}
				if (cType instanceof EMFConstraint<?>) {
					return -5;
				}
				return -10;
			}

			if (cType instanceof Reference && ((Reference) cType).isBidirectional()) {
				Reference emfType = (Reference) cType;
				if (adornment.get(0) == Adornment.BOUND && adornment.get(1) == Adornment.FREE) {
					int upperBound = emfType.getLinkedElement().getUpperBound();
					return upperBound > 1 || upperBound == EStructuralFeature.UNBOUNDED_MULTIPLICITY ? 10 : 1;
				} else if (adornment.get(0) == Adornment.FREE && adornment.get(1) == Adornment.BOUND) {
					EReference opposite = emfType.getLinkedElement().getEOpposite();
					int upperBound = opposite.getUpperBound();
					return upperBound > 1 || upperBound == EStructuralFeature.UNBOUNDED_MULTIPLICITY ? 10 : 1;
				}
			} else if (cType instanceof StructuralFeature<?>) {
				StructuralFeature<?> emfType = (StructuralFeature<?>) cType;
				if (adornment.get(0) == Adornment.BOUND && adornment.get(1) == Adornment.FREE) {
					int upperBound = emfType.getLinkedElement().getUpperBound();
					return upperBound > 1 || upperBound == EStructuralFeature.UNBOUNDED_MULTIPLICITY ? 10 : 1;
				}
			} else if (cType instanceof Operation) {
				return -5;
			} else if (cType == CoreConstraintModule.EQUAL) {
				return -5;
			} else if (cType instanceof ConstraintSpecification) {
				return 100;
			}

		} else if (object instanceof Variable) {
			return -5;
		}
		throw new RuntimeException("Invalid combination of constraint type and adornment");
	}
}
