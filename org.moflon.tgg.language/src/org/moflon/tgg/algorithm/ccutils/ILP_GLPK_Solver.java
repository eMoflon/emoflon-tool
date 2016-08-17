package org.moflon.tgg.algorithm.ccutils;

import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;
import org.moflon.tgg.algorithm.datastructures.Graph;

import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryGLPK;

public class ILP_GLPK_Solver extends AbstractILPSolver{

	
	@Override
	public int[] solve(Graph sourceGraph, Graph targetGraph, ConsistencyCheckPrecedenceGraph protocol) {
		
		SolverFactory factory = new SolverFactoryGLPK();
		factory.setParameter(Solver.VERBOSE, 0);

		Problem ilpProblem = createIlpProblemFromGraphs(sourceGraph, targetGraph, protocol);
		
		Solver solver = factory.get();
		
		
		// solve
		Result result = solver.solve(ilpProblem);
		

		return getArrayFromResult(result);
	}





}
