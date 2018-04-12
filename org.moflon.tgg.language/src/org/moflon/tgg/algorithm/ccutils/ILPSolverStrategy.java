package org.moflon.tgg.algorithm.ccutils;

import org.cardygan.ilp.api.Solver;

public class ILPSolverStrategy {

	private static ILPSolverStrategy instance = new ILPSolverStrategy();
	
	private Solver solver = null;
	
	private ILPSolverStrategy() {
		
	}
	
	public static ILPSolverStrategy getInstance() {
		return instance;
	}
	
	public Solver getSolver() {
		if(solver == null) {
			throw new RuntimeException("You did not set your ILP solver. Please provide an ILP solver using the following code fragment: "
					+ "ILPSolverProvider.getInstance().setSolver(<mySolver>)");
		}
		return solver;
	}
	
	public void setSolver(Solver solver) {
		this.solver = solver;
	}
	
	
}
