package org.moflon.tgg.csp.solver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.plan.Algorithm;
import org.gervarro.democles.plan.WeightedOperation;
import org.moflon.tgg.language.csp.CspFactory;
import org.moflon.tgg.language.csp.TGGConstraint;
import org.moflon.tgg.language.csp.Variable;

public class SearchPlanAction extends Algorithm<SimpleCombiner, TGGConstraint> {

	private List<Variable> variables;

	// Unsorted list of our constraints => swap AttributeConstraint with our real
	// Constraint class
	// Sorted list of variables referenced from constraints => swap
	// AttributeVariable with our real Variable class
	public SimpleCombiner sortConstraints(final List<TGGConstraint> constraints, final List<Variable> variables) {
		this.variables = variables;

		// Create Combiner
		SimpleCombiner combiner = new SimpleCombiner();

		// 1. Determine inputAdornment (initial binding information) from sorted (!)
		// list of variables
		Adornment inputAdornment = determineInputAdornment();

		// 2. Create weighted operations, one for each allowed adornment of each
		// constraint
		List<WeightedOperation<TGGConstraint>> weightedOperations = createWeightedOperations(constraints);

		// 3. Call search plan algorithm to sort weighted operations
		SimpleCombiner sc;
		PrintStream out = System.out;
		PrintStream err = System.err;
		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		}));
		System.setErr(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		}));
		try {
			sc = generatePlan(combiner, weightedOperations, inputAdornment);
		} finally {
			System.setOut(out);
			System.setErr(err);
		}

		return sc;
	}

	private Adornment determineInputAdornment() {
		boolean[] bits = new boolean[variables.size()];
		for (int i = 0; i < variables.size(); i++) {
			if (variables.get(i).isBound()) {
				bits[i] = Adornment.B; // Bound <-> false !
			} else {
				bits[i] = Adornment.F; // Unbound <-> true !
			}
		}
		Adornment inputAdornment = new Adornment(bits);
		return inputAdornment;
	}

	/**
	 * Create weighted operations from constraints
	 * 
	 * @param constraints
	 * @return
	 */
	private List<WeightedOperation<TGGConstraint>> createWeightedOperations(final List<TGGConstraint> constraints) {
		List<WeightedOperation<TGGConstraint>> result = new ArrayList<WeightedOperation<TGGConstraint>>();
		// for each constraint ...
		for (TGGConstraint constraint : constraints) {
			if (constraint.isNegated()) {
				org.moflon.tgg.language.csp.Adornment adornmentForNegation = createBoundAdornment(constraint);
				WeightedOperation<TGGConstraint> o = createWeightedOperationForConstraintWithAdornment(constraint,
						adornmentForNegation);
				result.add(o);
				continue;
			}

			// and each allowed adornment ...
			for (org.moflon.tgg.language.csp.Adornment adornment : constraint.getAllowedAdornments()) {
				result.add(createWeightedOperationForConstraintWithAdornment(constraint, adornment));
			}
		}
		return result;
	}

	private WeightedOperation<TGGConstraint> createWeightedOperationForConstraintWithAdornment(
			final TGGConstraint constraint, final org.moflon.tgg.language.csp.Adornment adornment) {
		long frees = adornment.getValue().chars().filter(c -> c == 'F').count();
		float weight = (float) Math.pow(frees, 3);

		return createOperation(constraint, createBoundMask(constraint, adornment),
				createFreeMask(constraint, adornment), weight);
	}

	private org.moflon.tgg.language.csp.Adornment createBoundAdornment(final TGGConstraint constraint) {
		final String adornmentValue = StringUtils.repeat("B", constraint.getVariables().size());

		org.moflon.tgg.language.csp.Adornment boundAdornment = CspFactory.eINSTANCE.createAdornment();
		boundAdornment.setValue(adornmentValue);

		return boundAdornment;
	}

	private Adornment createBoundMask(final TGGConstraint constraint,
			final org.moflon.tgg.language.csp.Adornment adornment) {

		boolean[] bits = new boolean[variables.size()];

		for (int i = 0; i < constraint.getVariables().size(); i++) {
			Variable variable = constraint.getVariables().get(i);
			int index = variables.indexOf(variable);
			if (adornment.getValue().charAt(i) == 'B') {
				bits[index] = true;
			}
		}

		return new Adornment(bits);
	}

	private Adornment createFreeMask(final TGGConstraint constraint,
			final org.moflon.tgg.language.csp.Adornment adornment) {
		boolean[] bits = new boolean[variables.size()];

		for (int i = 0; i < constraint.getVariables().size(); i++) {
			Variable variable = constraint.getVariables().get(i);
			int index = variables.indexOf(variable);
			if (adornment.getValue().charAt(i) == 'F') {
				bits[index] = true;
			}
		}

		return new Adornment(bits);
	}

}
