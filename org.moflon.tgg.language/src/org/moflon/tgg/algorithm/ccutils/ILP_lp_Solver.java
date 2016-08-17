package org.moflon.tgg.algorithm.ccutils;

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.gnu.glpk.GLPK;
import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;
import org.moflon.tgg.algorithm.datastructures.Graph;
import org.moflon.tgg.runtime.CCMatch;

import gnu.trove.TIntCollection;
import gnu.trove.map.hash.TIntIntHashMap;
import net.sf.javailp.Linear;
import net.sf.javailp.OptType;
import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryGLPK;
import net.sf.javailp.SolverFactoryLpSolve;
import net.sf.javailp.SolverFactorySAT4J;

public class ILP_lp_Solver extends AbstractILPSolver{

	
	@Override
	public int[] solve(Graph sourceGraph, Graph targetGraph, ConsistencyCheckPrecedenceGraph protocol) {
		
		SolverFactory factory = new SolverFactoryLpSolve();
		factory.setParameter(Solver.VERBOSE, 0);

		Problem ilpProblem = createIlpProblemFromGraphs(sourceGraph, targetGraph, protocol);
		
		Solver solver = factory.get();
		
		long startTime = System.currentTimeMillis();
		
		// solve
		Result result = solver.solve(ilpProblem);
		
		long endTime = System.currentTimeMillis();

		return getArrayFromResult(result);
	}
}
