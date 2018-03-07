package org.moflon.democles.reachability.javabdd;

import java.util.List;

import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.OperationRuntime;
import org.gervarro.democles.common.runtime.VariableRuntime;
import org.gervarro.democles.compiler.CompilerPattern;
import org.gervarro.democles.specification.Variable;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDDomain;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;

/**
 * This is the original BDD reachability analysis (modulo refactoring)
 * 
 * Taken from
 * gervarro.org/democles/trunk/org.moflon.democles.reachability.javabdd on
 * 2016-09-03
 * 
 * Limitations: The analysis is only application for patterns whose operations
 * have {@link Adornment#FREE} or {@link Adornment#BOUND} as
 * pre-/postconditions. {@link Adornment#NOT_TYPECHECKED} is not supported.
 */
public class LegacyBDDReachabilityAnalyzer implements ReachabilityAnalyzer {

	private static final int ADORNMENT_UNDEFINED = -1;

	private BDDFactory bddFactory;

	BDDPairing fwdPairing;

	BDDPairing revPairing;

	BDD[][] bdd;

	BDDDomain domain1;

	BDDDomain domain2;

	boolean calculated = false;

	BDD reachableStates;

	private boolean reachabilityAnalysisPossible;

	@Override
	public boolean analyzeReachability(final CompilerPattern pattern, final Adornment adornment) {
		this.reachabilityAnalysisPossible = !hasOperationWithUncheckedAdornment(
				ReachabilityUtils.extractOperations(pattern.getBodies().get(0)));
		if (!this.reachabilityAnalysisPossible) {
			return true;
		}

		int cacheSize = 1000;
		int v = pattern.getSymbolicParameters().size();
		int numberOfNodes = (int) Math.max((Math.pow(v, 3)) * 20, cacheSize);

		bddFactory = BDDFactory.init("java", numberOfNodes, cacheSize);
		bddFactory.setVarNum(v * 2);
		bddFactory.setCacheRatio(1);
		fwdPairing = bddFactory.makePair();
		revPairing = bddFactory.makePair();
		domain1 = bddFactory.extDomain((long) Math.pow(2, v));
		domain2 = bddFactory.extDomain((long) Math.pow(2, v));
		bdd = new BDD[2][v];

		ReachabilityUtils.executeWithMutedStderrAndStdout(() -> bddFactory.setVarOrder(getVarOrder(v)));

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < v; j++) {
				bdd[i][j] = bddFactory.ithVar(i * v + j);
			}
		}

		for (int j = 0; j < v; j++) {
			fwdPairing.set(j, v + j);
			revPairing.set(v + j, j);
		}
		final BDD transitionRelation = calculateTransitionRelation(pattern);
		calculateReachableStates(transitionRelation);
		transitionRelation.free();

		return isReachable(adornment, reachableStates);
	}

	private static boolean hasOperationWithUncheckedAdornment(final List<GeneratorOperation> operations) {
		return operations.stream().anyMatch(operation -> hasUncheckedAdornment(operation.getPrecondition())
				|| hasUncheckedAdornment(operation.getPostcondition()));
	}

	private static boolean hasUncheckedAdornment(Adornment adornment) {
		for (int i = 0; i < adornment.cardinality(); ++i) {
			if (adornment.get(i) == Adornment.NOT_TYPECHECKED)
				return true;
		}
		return false;
	}

	private BDD calculateTransitionRelation(final CompilerPattern pattern) {
		final List<GeneratorOperation> operations = ReachabilityUtils.extractOperations(pattern.getBodies().get(0));
		final BDD transitionRelation = bddFactory.zero(); // represents R_O

		for (final OperationRuntime operation : operations) {
			if (operation != null && !ReachabilityUtils.isCheckOperation(operation)) {
				final BDD cube = bddFactory.one(); // Represents R_o
				final List<? extends Variable> symbolicParameters = pattern.getSymbolicParameters();
				for (int i = 0; i < symbolicParameters.size(); ++i) {
					int posInOperationParameters = -1;
					for (int j = 0; j < operation.getParameters().size(); ++j) {
						final VariableRuntime opVariable = operation.getParameters().get(j);
						if (opVariable.getIndex() == i) {
							posInOperationParameters = j;
							break;
						}

					}
					int preconditionAdornment = posInOperationParameters != -1
							? operation.getPrecondition().get(posInOperationParameters)
							: ADORNMENT_UNDEFINED;
					switch (preconditionAdornment) {
					case Adornment.FREE:
						// Required to be free
						cube.andWith(bdd[0][i].id());
						cube.andWith(bdd[1][i].not());
						break;
					case Adornment.BOUND:
						// Required to be bound
						cube.andWith(bdd[0][i].not());
						cube.andWith(bdd[1][i].not());
					default:
						// Not defined
						cube.andWith(bdd[0][i].biimp(bdd[1][i]));
					}
				}
				// Original code by Fred:
				// Problem: The index i is relative to the precondition adornment of the
				// operation, not to the adornment of the pattern
				// for (int i = 0; i < precondition.size(); i++)
				// {
				// if (Adornment.FREE == precondition.get(i))
				// {
				// // Required to be free
				// cube.andWith(bdd[0][i].id());
				// cube.andWith(bdd[1][i].not());
				// } else if (Adornment.BOUND == precondition.get(i))
				// {
				// // Required to be bound
				// cube.andWith(bdd[0][i].not());
				// cube.andWith(bdd[1][i].not());
				// } else
				// {
				// // Not defined
				// cube.andWith(bdd[0][i].biimp(bdd[1][i]));
				// }
				// }
				transitionRelation.orWith(cube);
			}
		}
		return transitionRelation;
	}

	private boolean isReachable(final Adornment adornment, final BDD r) {
		// We have arrived at '1', i.e., 'adornment' is reachable.
		if (r.equals(bddFactory.one())) {
			return true;
		}
		if (r.equals(bddFactory.zero())) {
			return false;
		}

		final BDD subtree;
		if (adornment.get(r.var()) > Adornment.BOUND) // Adornment.FREE
		{
			subtree = r.high(); // high = 1 = Free
		} else // Adornment.BOUND
		{
			subtree = r.low(); // low = 0 = Not free = bound
		}

		return isReachable(adornment, subtree);
	}

	private void calculateReachableStates(final BDD transitionRelation) {
		BDD old = domain1.ithVar(0);
		BDD nu = old;
		do {
			old = nu;
			BDD z = (transitionRelation.and(old.replace(fwdPairing))).exist(bddFactory.makeSet(domain2.vars()));
			nu = old.or(z);
		} while (!old.equals(nu));
		reachableStates = nu;
	}

	private int[] getVarOrder(final int adornmentSize) {
		int[] varorder = new int[2 * adornmentSize];
		for (int j = 0; j < adornmentSize; j++) {
			varorder[2 * j] = j; // pre-variable
			varorder[2 * j + 1] = adornmentSize + j; // post-variable
		}
		return varorder;
	}
}
