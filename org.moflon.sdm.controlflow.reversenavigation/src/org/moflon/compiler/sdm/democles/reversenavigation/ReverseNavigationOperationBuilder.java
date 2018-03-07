package org.moflon.compiler.sdm.democles.reversenavigation;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.GeneratorVariable;
import org.gervarro.democles.codegen.emf.BasicEMFOperationBuilder;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.constraint.emf.EMFConstraint;
import org.gervarro.democles.constraint.emf.Reference;
import org.gervarro.democles.specification.impl.Constraint;

public class ReverseNavigationOperationBuilder extends BasicEMFOperationBuilder {
	@Override
	public List<GeneratorOperation> getConstraintOperations(final Constraint constraint,
			final List<GeneratorVariable> parameters) {
		if (constraint.getType() instanceof EMFConstraint<?>) {
			List<GeneratorOperation> result = new LinkedList<GeneratorOperation>();
			EMFConstraint<?> cType = (EMFConstraint<?>) constraint.getType();
			if (cType instanceof Reference) {
				EReference eReference = ((Reference) cType).getLinkedElement();
				result.add(new GeneratorOperation(constraint, parameters,
						Adornment.create(Adornment.BOUND, Adornment.BOUND),
						Adornment.create(Adornment.BOUND, Adornment.BOUND), cType));
				final boolean isTargetTypeCheckNeeded = !lookupEClass(parameters.get(1))
						.isSuperTypeOf((EClass) eReference.getEType());
				result.add(new GeneratorOperation(constraint, parameters,
						Adornment.create(Adornment.BOUND, Adornment.FREE), Adornment.create(Adornment.BOUND,
								isTargetTypeCheckNeeded ? Adornment.NOT_TYPECHECKED : Adornment.BOUND),
						cType));
				final boolean isSourceTypeCheckNeeded = !lookupEClass(parameters.get(0))
						.isSuperTypeOf(eReference.getEContainingClass());
				result.add(new GeneratorOperation(constraint, parameters,
						Adornment.create(Adornment.FREE, Adornment.BOUND),
						Adornment.create(isSourceTypeCheckNeeded ? Adornment.NOT_TYPECHECKED : Adornment.BOUND,
								Adornment.BOUND),
						cType));
				return result;
			} else {
				return super.getConstraintOperations(constraint, parameters);
			}
		}
		return null;
	}
}
