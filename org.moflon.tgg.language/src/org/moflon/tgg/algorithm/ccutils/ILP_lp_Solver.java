package org.moflon.tgg.algorithm.ccutils;

import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryLpSolve;

public class ILP_lp_Solver extends AbstractILPSolver{

	@Override
	protected SolverFactory getSolverFactory() {
		
		return new SolverFactoryLpSolve();
	}

	
}
