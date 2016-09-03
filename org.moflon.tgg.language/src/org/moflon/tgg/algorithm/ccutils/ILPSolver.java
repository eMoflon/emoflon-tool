package org.moflon.tgg.algorithm.ccutils;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;
import org.moflon.tgg.algorithm.datastructures.Graph;
import org.moflon.tgg.runtime.CCMatch;

import gnu.trove.TIntCollection;
import net.sf.javailp.Linear;
import net.sf.javailp.OptType;
import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactorySAT4J;

public class ILPSolver extends AbstractSolver{
	
	// this list keeps a record of all variables that appear in the clausels. this is needed to define them as ilp variables later
	ArrayList<Integer> variables;

	@Override
	public int[] solve(Graph sourceGraph, Graph targetGraph, ConsistencyCheckPrecedenceGraph protocol) {
		
		variables = new ArrayList<Integer>();
		
		SolverFactory factory = new SolverFactorySAT4J();
		
		factory.setParameter(Solver.VERBOSE, 0);

		Problem ilpProblem = new Problem();
		
		// get all alternative clauses
		for(EObject elm : sourceGraph.getElements()){
			TIntCollection variables = protocol.creates(elm);
			if(variables != null && !variables.isEmpty()){
				int[] alternatives = variables.toArray();
				ilpProblem.add(getAlternativeLinearFromArray(alternatives), "<=", 1);	
			}	
		}
		
		for(EObject elm : targetGraph.getElements()){			
			TIntCollection variables = protocol.creates(elm);
			if(variables != null && !variables.isEmpty()){
				int[] alternatives = variables.toArray();
				ilpProblem.add(getAlternativeLinearFromArray(alternatives), "<=", 1);	
			}	
		}
		
		// get all implication clauses 
		for(int matchId : protocol.getMatchIDs().toArray()){
			CCMatch match = protocol.intToMatch(matchId);
			for(EObject contextCorrespondence : match.getAllContextElements()){
				int parentID = protocol.creates(contextCorrespondence).toArray()[0];
				
				int[] implication = new int[2];
				implication[0] = matchId;
				implication[1] = parentID;
				ilpProblem.add(getImplicationLinearFromArray(implication), "<=", 0);
			}
		}
		
		// define the objective -> bsp: MAX(A+B+C)
		Linear objective = new Linear();
		for(int matchId : protocol.getMatchIDs().toArray()){
			CCMatch match = protocol.intToMatch(matchId);
			int weight = match.getSourceMatch().getCreatedHashSet().size();
			weight += match.getTargetMatch().getCreatedHashSet().size();
			objective.add(weight, matchId);
		}	
		ilpProblem.setObjective(objective, OptType.MAX);
		
		// define variables as ilp boolean variables
		variables.forEach(variable -> ilpProblem.setVarType(variable, Boolean.class));
		
		// start the solver
		Solver solver = factory.get();
		Result result = solver.solve(ilpProblem);

		return getArrayFromResult(result);
	}

	// SAT4J ILP solver gives as result something like: "Objective: {1=0, 2=0, 3=1, 4=0, 5=1 ...} this method transforms such a string into a result array
	private int[] getArrayFromResult(Result result) {
		if (result != null)
			System.out.println("Satisfiable!");
		
		System.out.println(result);
		
		
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

	private Linear getAlternativeLinearFromArray(int[] alternatives) {
		Linear linear = new Linear();
		for(int variable : alternatives){
			linear.add(1, variable);
			if(!variables.contains(variable))
				variables.add(variable);
		}
		return linear;
	}
	
	private Linear getImplicationLinearFromArray(int[] implication){
		Linear linear = new Linear();
		linear.add(1, implication[0]);
		linear.add(-1, implication[1]);
		if(!variables.contains(implication[0]))
			variables.add(implication[0]);
		if(!variables.contains(implication[1]))
			variables.add(implication[1]);
		return linear;
	}

}
