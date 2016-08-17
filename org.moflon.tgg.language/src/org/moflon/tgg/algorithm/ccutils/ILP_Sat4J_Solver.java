package org.moflon.tgg.algorithm.ccutils;

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
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
import net.sf.javailp.SolverFactorySAT4J;

public class ILP_Sat4J_Solver extends AbstractILPSolver{
	

	@Override
	public int[] solve(Graph sourceGraph, Graph targetGraph, ConsistencyCheckPrecedenceGraph protocol) {
		
		SolverFactory factory = new SolverFactorySAT4J();
		factory.setParameter(Solver.VERBOSE, 0);

		Problem ilpProblem = createIlpProblemFromGraphs(sourceGraph, targetGraph, protocol);
		
		Solver solver = factory.get();
		
		long startTime = System.currentTimeMillis();
		//solve
		Result result = solver.solve(ilpProblem);
		
		long endTime = System.currentTimeMillis();

		return getArrayFromResult(result);
	}

	// SAT4J ILP solver gives as result something like: "Objective: {1=0, 2=0, 3=1, 4=0, 5=1 ...} this method transforms such a string into a result array
	@Override
	protected int[] getArrayFromResult(Result result) {
		if (result != null)
			System.out.println("Satisfiable!");
		
		
		String[] resultPartials = result.toString().split(", ");
		
		// cutting clutter at start and finish
		resultPartials[0] = resultPartials[0].split("\\{")[1];
		resultPartials[resultPartials.length - 1] = resultPartials[resultPartials.length - 1].split("\\}")[0];

		int[] returnArray = new int[variables.size()];

		for (int i = 0; i < resultPartials.length; i++) {
			String[] eval = resultPartials[i].split("=");

			int identifier = Integer.parseInt(eval[0]);
			
			// negate identifier if equals 0
			if (Integer.parseInt(eval[1]) == 0) {
				identifier = 0 - identifier;
			}
			returnArray[i] = identifier;
		}
		return returnArray;
	}
}
