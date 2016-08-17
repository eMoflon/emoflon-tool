package org.moflon.tgg.algorithm.ccutils;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class Sat4JIncrementalSolver extends AbstractSATSolver {

	ISolver solver;

	public Sat4JIncrementalSolver() {
		solver = SolverFactory.newMiniSATHeap();
	}
	
	public void clearLearntClauses(){
		solver.clearLearntClauses();
	}
	
	@Override
	protected int[] solve(DimacFormat problem) {
		// prepare the solver to accept MAXVAR variables . MANDATORY
				solver.newVar(problem.getNumberOfLiterals());
				// not mandatory for SAT solving . MANDATORY for MAXSAT solving
				solver.setExpectedNumberOfClauses(problem.getNumberOfClauses());

				int[][] clauses = problem.getClauses();

				for (int i = 0; i < problem.getNumberOfClauses(); i++) {
					try {
						solver.addClause(new VecInt(clauses[i]));
					} catch (ContradictionException e) {
						e.printStackTrace();
					}
				}

				IProblem myProblem = solver;
				try {
					if (myProblem.isSatisfiable()) {
						System.out.println("Satisfiable");
						return solver.findModel();
					} else {

					}
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
				return new int[0];
	}

}
