package org.moflon.tgg.algorithm.ccutils;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;
import org.moflon.tgg.algorithm.datastructures.Graph;

import gnu.trove.TIntCollection;
import gnu.trove.iterator.TIntIterator;

public abstract class AbstractSATSolver extends AbstractSolver{

	
	public int[] solve(Graph sourceGraph, Graph targetGraph, ConsistencyCheckPrecedenceGraph protocol){
		
		System.out.println("Preparing clauses");
		ArrayList<int[]> clauses = new ArrayList<>();

		addClausesForAlternatives(clauses, sourceGraph, targetGraph, protocol);
		addClausesForImplications(clauses, protocol);

		int[][] satProblem = new int[clauses.size()][];
		int i = 0;
		for (int[] clause : clauses) {
			satProblem[i] = clause;
			i++;
		}

		return solve(new DimacFormat(satProblem));
	}
	
	protected abstract int[] solve(DimacFormat problem);
	
	private void addClausesForImplications(ArrayList<int[]> clauses, ConsistencyCheckPrecedenceGraph protocol) {

		TIntIterator allIterator = protocol.getMatchIDs().iterator();
		while (allIterator.hasNext()) {
			int m = allIterator.next();
			for(EObject corr : protocol.intToMatch(m).getAllContextElements()){
				TIntCollection contextCreatingParent = protocol.creates(corr);
				int[] clause = new int[2];
				clause[0] = -m;
				int[] parentAsArray = contextCreatingParent.toArray();
				clause[1] = parentAsArray[0];
				clauses.add(clause);
			}
		}
	}

	private void addClausesForAlternatives(ArrayList<int[]> clauses, Graph sourceGraph, Graph targetGraph, ConsistencyCheckPrecedenceGraph protocol) {
		clauses.addAll(getclausesForAlternatives(sourceGraph, protocol));
		clauses.addAll(getclausesForAlternatives(targetGraph, protocol));
	}

	private ArrayList<int[]> getclausesForAlternatives(Graph graph, ConsistencyCheckPrecedenceGraph protocol) {
		ArrayList<int[]> clauses = new ArrayList<>();
		for (EObject obj : graph.getElements()) { 
			TIntCollection variables = protocol.creates(obj);

			if (variables!=null && !variables.isEmpty()) {
				// get a clause like (a V b V c ...)
				int[] all = variables.toArray();
				clauses.add(all);

				// get clauses like (-a V -b), (-a V -c), (-b V -c)....
				for (int i = 0; i < all.length; i++) {
					for (int j = i + 1; j < all.length; j++) {
						int[] clause = new int[2];
						clause[0] = -(all[i]);
						clause[1] = -(all[j]);
						clauses.add(clause);
					}
				}
			}
		}
		return clauses;
	}
	
}
