package org.moflon.compiler.sdm.democles;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.GeneratorVariable;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.PatternMatcherPlugin;
import org.gervarro.democles.common.runtime.OperationBuilder;
import org.gervarro.democles.constraint.PatternInvocationConstraintType;
import org.gervarro.democles.specification.ConstraintType;
import org.gervarro.democles.specification.emf.Pattern;
import org.gervarro.democles.specification.emf.PatternBody;
import org.gervarro.democles.specification.emf.PatternInvocationConstraint;
import org.gervarro.democles.specification.impl.Constraint;
import org.gervarro.democles.specification.impl.Variable;
import org.moflon.sdm.compiler.democles.validation.scope.impl.PatternMatcherImpl;

public class BindingAndBlackOperationBuilder implements OperationBuilder<GeneratorOperation, GeneratorVariable> {
	private final Pattern pattern;

	private final Adornment adornment;

	public BindingAndBlackOperationBuilder(final Pattern pattern, final Adornment adornment) {
		this.pattern = pattern;
		this.adornment = adornment;
	}

	@Override
	public final List<GeneratorOperation> getConstraintOperations(Constraint constraint,
			List<GeneratorVariable> parameters) {
		ConstraintType cType = constraint.getType();
		if (cType instanceof PatternInvocationConstraintType) {
			PatternInvocationConstraintType invocation = (PatternInvocationConstraintType) cType;
			if (invocation.isPositive()) {
				PatternBody body = pattern.getBodies().get(0);

				// Prepare initial adornment
				Adornment current = PatternMatcherImpl.getBodyAdornment(pattern, adornment);

				// Apply operations one by one
				for (org.gervarro.democles.specification.emf.Constraint c : body.getConstraints()) {
					if (c instanceof PatternInvocationConstraint) {
						PatternInvocationConstraint pic = (PatternInvocationConstraint) c;

						Pattern emfPattern = pic.getInvokedPattern();
						org.gervarro.democles.specification.impl.Pattern internalPattern = invocation
								.getInvokedPattern();
						if (PatternMatcherPlugin
								.getIdentifier(emfPattern.getName(), emfPattern.getSymbolicParameters().size())
								.equals(PatternMatcherPlugin.getIdentifier(internalPattern.getName(),
										internalPattern.getSymbolicParameters().size()))) {
							int[] allBoundArray = new int[pic.getParameters().size()];
							Arrays.fill(allBoundArray, Adornment.BOUND);
							List<GeneratorOperation> result = new LinkedList<GeneratorOperation>();
							result.add(new GeneratorOperation(constraint, parameters,
									PatternMatcherImpl.getOperationAdornment(this.pattern, current, pic),
									Adornment.create(allBoundArray), invocation));
							return result;
						}
						current = PatternMatcherImpl.getNextAdornment(this.pattern, current, pic);
					}
				}
			}
		}

		return null;
	}

	@Override
	public GeneratorOperation getVariableOperation(Variable variable, GeneratorVariable runtimeVariable) {
		return null;
	}
}
