package org.moflon.tgg.algorithm.ccutils;

import org.apache.log4j.Logger;
import org.moflon.core.utilities.LogUtils;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;

public class Sat4JSolver extends AbstractSATSolver {

   private static final Logger logger = Logger.getLogger(Sat4JSolver.class);

	@Override
	public int[] solve(DimacFormat problem) {
		ISolver solver = SolverFactory.newDefault();
		// prepare the solver to accept MAXVAR variables . MANDATORY
		solver.newVar(problem.getNumberOfLiterals());
		// not mandatory for SAT solving . MANDATORY for MAXSAT solving
		solver.setExpectedNumberOfClauses(problem.getNumberOfClauses());

		int[][] clauses = problem.getClauses();

		for (int i = 0; i < problem.getNumberOfClauses(); i++) {
			try {
				solver.addClause(new VecInt(clauses[i]));
			} catch (ContradictionException e) {
            LogUtils.error(logger, e);
			}
		}

		IProblem myProblem = solver;
		try {
			if (myProblem.isSatisfiable()) {
				System.out.println("Satisfiable");
				return solver.findModel();
			} else {

			}
		} catch (org.sat4j.specs.TimeoutException e) {
         LogUtils.error(logger, e);
		}
		return new int[0];
	}

}
