package org.moflon.democles.reachability.javabdd;

import java.util.List;

import org.apache.log4j.Logger;
import org.gervarro.democles.codegen.GeneratorOperation;
import org.gervarro.democles.codegen.GeneratorVariable;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.common.OperationRuntime;
import org.gervarro.democles.common.runtime.VariableRuntime;
import org.gervarro.democles.compiler.CompilerPattern;
import org.gervarro.democles.compiler.CompilerPatternBody;
import org.gervarro.democles.specification.Constant;
import org.moflon.core.utilities.LogUtils;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDDomain;
import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.BDDPairing;

/**
 * This class implements a BDD-based reachability analysis, which serves for
 * identifying reachable input adornments of a {@link CompilerPattern}
 * invocation.
 * 
 * The algorithm is an adapted version of the algorithm described Section 3.5 of
 * the paper "An algorithm for generating model-sensitive search plans for
 * pattern matching on EMF models"
 * {@link https://link.springer.com/article/10.1007%2Fs10270-013-0372-2}.
 * 
 * The original algorithm was designed for two types of adornments
 * ({@link Adornment#BOUND} and {@link Adornment#FREE}). See also
 * {@link BDDReachabilityAnalyzer} for an implementation of this algorithm.
 * 
 * Short explanations: - Each operation has a precondition and a postcondition
 * adornment - Each pattern has a set of symbolic parameters. The size of the
 * {@link Adornment} equals the number of symbolic parameters.
 * 
 * @author Roland Kluge - Reimplementation of
 *         {@link LegacyBDDReachabilityAnalyzer} with support for
 *         {@link Adornment#NOT_TYPECHECKED}
 */
public class BDDReachabilityAnalyzer implements ReachabilityAnalyzer {
	private static final Logger logger = Logger.getLogger(BDDReachabilityAnalyzer.class);

	private static final int HIGHEST_POSSIBLE_MAXIMUM_ADORNMENT_SIZE = 31;

	private final int maximumAdornmentSize;

	private BDDFactory bddFactory;

	private BDDPairing fwdPairing;

	private BDDPairing revPairing;

	private BDD[][] bdd;

	private BDDDomain domain1;

	private BDDDomain domain2;

	private BDD currentAdornmentBDD;

	/**
	 * Initializes this analyzer with the highest possible maximum adornment size
	 */
	public BDDReachabilityAnalyzer() {
		this(HIGHEST_POSSIBLE_MAXIMUM_ADORNMENT_SIZE);
	}

	/**
	 * Initializes this analyzer to only analyze patterns with an adornment of at
	 * most the given adornment size
	 * 
	 * @param maximumAdornmentSize
	 *            the maximum adornment size
	 */
	public BDDReachabilityAnalyzer(final int maximumAdornmentSize) {
		this.maximumAdornmentSize = Math.min(maximumAdornmentSize, HIGHEST_POSSIBLE_MAXIMUM_ADORNMENT_SIZE);
	}

	@Override
	public boolean analyzeReachability(final CompilerPattern pattern, final Adornment adornment) {
		if (!hasOneBody(pattern)) {
			LogUtils.debug(logger, //
					"Pattern '%s' cannot be analyzed because it has multiple bodies. This is currently unsupported.", //
					pattern.getName());
			return true;
		}

		final int adornmentSize = pattern.getSymbolicParameters().size()
				+ pattern.getBodies().get(0).getLocalVariables().size();
		if (adornmentSize > this.maximumAdornmentSize) {
			LogUtils.debug(logger, //
					"Adornment of pattern '%s' too large for reachability analysis. Maximum: '%d', actual: '%d'.", //
					pattern.getName(), this.maximumAdornmentSize, adornmentSize);
			return true;
		}

		final int cacheSize = 10000;
		final int numberOfBddVariables = adornmentSize * 4;
		final int numberOfNodes = (int) Math.max((Math.pow(2 * adornmentSize, 3)) * 20, cacheSize);

		bddFactory = BDDFactory.init("java", numberOfNodes, cacheSize);
		bddFactory.setVarNum(numberOfBddVariables);
		bddFactory.setCacheRatio(1);
		fwdPairing = bddFactory.makePair();
		revPairing = bddFactory.makePair();
		domain1 = bddFactory.extDomain((long) Math.pow(4, adornmentSize));
		domain2 = bddFactory.extDomain((long) Math.pow(4, adornmentSize));
		/*
		 * First dimension: i=0: pre-variables, i=1: post-variables Second dimension:
		 * Each parameter of the pattern is represented by two BDD variables, which
		 * reside next to each other
		 */
		bdd = new BDD[2][2 * adornmentSize];

		// After the sorting, the pre- and post-variables of one pattern parameter
		// reside next to each other: v_i1,v_i2,v'_i1,v'_i2
		ReachabilityUtils.executeWithMutedStderrAndStdout(() -> bddFactory.setVarOrder(getVarOrder(adornmentSize)));

		// i = 0: pre-variables
		// i = 1: post-variables
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2 * adornmentSize; j += 2) {
				bdd[i][j] = bddFactory.ithVar(i * 2 * adornmentSize + j);
				bdd[i][j + 1] = bddFactory.ithVar(i * 2 * adornmentSize + j + 1);
			}
		}

		for (int j = 0; j < 2 * adornmentSize; ++j) {
			fwdPairing.set(j, 2 * adornmentSize + j);
			revPairing.set(2 * adornmentSize + j, j);
		}

		initializeCurrentAdornmentBDD(adornment, pattern.getBodies().get(0));

		final BDD transitionRelation = calculateTransitionRelation(pattern);
		final boolean isReachable = checkReachability(transitionRelation);
		transitionRelation.free();
		this.currentAdornmentBDD.free();

		return isReachable;
	}

	private boolean hasOneBody(final CompilerPattern pattern) {
		return pattern.getBodies().size() == 1;
	}

	private void initializeCurrentAdornmentBDD(final Adornment adornment, final CompilerPatternBody body) {
		this.currentAdornmentBDD = bddFactory.one();
		for (int i = 0; i < adornment.size(); ++i) {
			// Handle precondition
			switch (adornment.get(i)) {
			case Adornment.FREE:
				this.currentAdornmentBDD.andWith(bdd[0][2 * i].id());
				this.currentAdornmentBDD.andWith(bdd[0][2 * i + 1].id());
				break;
			case Adornment.BOUND:
				this.currentAdornmentBDD.andWith(bdd[0][2 * i].not());
				this.currentAdornmentBDD.andWith(bdd[0][2 * i + 1].not());
				break;
			case Adornment.NOT_TYPECHECKED:
				this.currentAdornmentBDD.andWith(bdd[0][2 * i].not());
				this.currentAdornmentBDD.andWith(bdd[0][2 * i + 1].id());
				break;
			}
		}
		final int offset = adornment.size();
		// Local variables are always free initially
		for (int i = offset; i < offset + body.getLocalVariables().size(); ++i) {
			this.currentAdornmentBDD.andWith(bdd[0][2 * i].id());
			this.currentAdornmentBDD.andWith(bdd[0][2 * i + 1].id());
		}
	}

	private BDD calculateTransitionRelation(final CompilerPattern pattern) {
		final CompilerPatternBody body = pattern.getBodies().get(0);
		final List<GeneratorOperation> operations = ReachabilityUtils.extractOperations(body);
		final BDD transitionRelation = bddFactory.zero(); // represents R_O

		for (final OperationRuntime operation : operations) {
			final Adornment bodyPrecondition = calculatePreconditionAdornment(body, operation);
			final Adornment bodyPostcondition = calculatePostconditionAdornment(body, operation);
			if (!ReachabilityUtils.isCheckOperation(bodyPrecondition)) {
				final BDD cube = bddFactory.one(); // Represents R_o
				for (int param = 0; param < bodyPrecondition.size(); ++param) {
					final int preconditionAdornment = bodyPrecondition.get(param);
					switch (preconditionAdornment) {
					case Adornment.FREE:
						cube.andWith(bdd[0][2 * param].id());
						cube.andWith(bdd[0][2 * param + 1].id());
						break;
					case Adornment.BOUND:
						cube.andWith(bdd[0][2 * param].not());
						cube.andWith(bdd[0][2 * param + 1].not());
						break;
					case Adornment.NOT_TYPECHECKED:
						cube.andWith(bdd[0][2 * param].not());
						cube.andWith(bdd[0][2 * param + 1].id());
						break;
					case ReachabilityUtils.ADORNMENT_UNDEFINED:
						cube.andWith(bdd[0][2 * param].biimp(bdd[1][2 * param]));
						cube.andWith(bdd[0][2 * param + 1].biimp(bdd[1][2 * param + 1]));

						// Reduce transition relation by pruning the unused combinations v_i1=1,v_i2=0
						// and v'_i1=1,v'_i2=0
						BDD pruningTermForPrevariable = bddFactory.one();
						pruningTermForPrevariable.andWith(bdd[0][2 * param].id());
						pruningTermForPrevariable.andWith(bdd[0][2 * param + 1].not());
						cube.andWith(pruningTermForPrevariable.not());
						break;
					}

					final int postconditionAdornment = bodyPostcondition.get(param);
					// Handle postcondition
					switch (postconditionAdornment) {
					case Adornment.FREE:
						cube.andWith(bdd[1][2 * param].id());
						cube.andWith(bdd[1][2 * param + 1].id());
						break;
					case Adornment.BOUND:
						cube.andWith(bdd[1][2 * param].not());
						cube.andWith(bdd[1][2 * param + 1].not());
						break;
					case Adornment.NOT_TYPECHECKED:
						cube.andWith(bdd[1][2 * param].not());
						cube.andWith(bdd[1][2 * param + 1].id());
						break;
					case ReachabilityUtils.ADORNMENT_UNDEFINED:
						// nop because previous switch-case has cared about this
						break;
					}
				}
				transitionRelation.orWith(cube);
			}
		}
		return transitionRelation;
	}

	private Adornment calculatePreconditionAdornment(final CompilerPatternBody body, final OperationRuntime operation) {
		final Adornment adornment = createBodyAdornment(body);
		for (int p = 0; p < operation.getPrecondition().size(); ++p) {
			final int adornmentEntry = operation.getPrecondition().get(p);
			final VariableRuntime variable = operation.getParameters().get(p);
			if (!isAConstant(variable)) {
				final int indexInAdornment = operation.getParameters().get(p).getIndex();
				adornment.set(indexInAdornment, adornmentEntry);
			}
		}
		return adornment;
	}

	private Adornment calculatePostconditionAdornment(CompilerPatternBody body, OperationRuntime operation) {
		final Adornment adornment = createBodyAdornment(body);
		for (int p = 0; p < operation.getPostcondition().size(); ++p) {
			final int adornmentEntry = operation.getPostcondition().get(p);
			final VariableRuntime variable = operation.getParameters().get(p);
			if (!isAConstant(variable)) {
				final int indexInAdornment = operation.getParameters().get(p).getIndex();
				adornment.set(indexInAdornment, adornmentEntry);
			}
		}
		return adornment;
	}

	private Adornment createBodyAdornment(final CompilerPatternBody body) {
		final int numberOfSymbolicParameters = body.getHeader().getSymbolicParameters().size();
		final int numberOfVariables = numberOfSymbolicParameters + body.getLocalVariables().size();
		final Adornment adornment = new Adornment(body.frameSize());

		// Symbolic parameters depend on the input
		for (int i = 0; i < numberOfVariables; i++) {
			adornment.set(i, ReachabilityUtils.ADORNMENT_UNDEFINED);
		}
		return adornment;
	}

	private boolean isReachable(final BDD transitionRelation) {
		// Create conjunction with transition relation
		final BDD tmpAdornmentBdd = currentAdornmentBDD.id();
		tmpAdornmentBdd.impWith(transitionRelation.id());

		final boolean isReachable = tmpAdornmentBdd.equals(bddFactory.one());
		tmpAdornmentBdd.free();

		// Check whether transition relation AND adornment relation == TRUE
		return isReachable;
	}

	private boolean checkReachability(final BDD transitionRelation) {
		boolean isReachable = false;
		BDD old = domain1.ithVar(0); // Target adornment: everything is 0 -> BBB..B
		BDD nu = old;
		do {
			old = nu;
			final BDD z = (transitionRelation.and(old.replace(fwdPairing))).exist(bddFactory.makeSet(domain2.vars()));
			nu = old.or(z);
			isReachable = isReachable || isReachable(nu);
		} while (!isReachable && !old.equals(nu));
		isReachable = isReachable || isReachable(nu);
		nu.free();
		old.free();
		return isReachable;
	}

	private int[] getVarOrder(int adornmentSize) {

		int[] varorder = new int[4 * adornmentSize];
		for (int j = 0; j < adornmentSize; ++j) {
			varorder[4 * j] = 2 * j; // pre-variable 1
			varorder[4 * j + 1] = 2 * j + 1; // pre-variable 2
			varorder[4 * j + 2] = 2 * adornmentSize + 2 * j; // post-variable 1
			varorder[4 * j + 3] = 2 * adornmentSize + 2 * j + 1; // post-variable 2
		}
		return varorder;
	}

	private static boolean isAConstant(VariableRuntime variable) {
		return GeneratorVariable.class.cast(variable).getSpecification() instanceof Constant;
	}
}
