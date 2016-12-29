package org.moflon.tgg.algorithm.ccutils;

import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryGurobi;

public class ILP_Gurobi_Solver extends AbstractILPSolver{

	@Override
	protected SolverFactory getSolverFactory() {
		return new SolverFactoryGurobi();
	}
	
	

}
