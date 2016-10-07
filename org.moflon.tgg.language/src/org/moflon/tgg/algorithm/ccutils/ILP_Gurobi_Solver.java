package org.moflon.tgg.algorithm.ccutils;

import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;
import org.moflon.tgg.algorithm.datastructures.Graph;

import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryGurobi;

public class ILP_Gurobi_Solver extends AbstractILPSolver{

	@Override
	protected SolverFactory getSolverFactory() {
		return new SolverFactoryGurobi();
	}
	
	

}
