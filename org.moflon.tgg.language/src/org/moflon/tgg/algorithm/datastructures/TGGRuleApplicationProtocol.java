package org.moflon.tgg.algorithm.datastructures;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.runtime.TripleMatch;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.set.hash.TIntHashSet;
import net.sf.javailp.Constraint;
import net.sf.javailp.Linear;
import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryGurobi;

/**
 * 
 * @author leblebici
 *
 */

public class TGGRuleApplicationProtocol extends PrecedenceStructure<TGGRuleApplication> {

	private int name = 0;
	
	TIntHashSet allVariables = new TIntHashSet();

	@Override
	public Collection<EObject> getContextElements(TGGRuleApplication m) {

		return Stream.concat(Stream.concat(m.getContextSrc().stream(), m.getContextTrg().stream()),
				m.getContextCorr().stream()).collect(Collectors.toSet());
	}

	@Override
	public Collection<EObject> getCreatedElements(TGGRuleApplication m) {

		return Stream.concat(Stream.concat(m.getCreatedSrc().stream(), m.getCreatedTrg().stream()),
				m.getCreatedCorr().stream()).collect(Collectors.toSet());
	}

	@Override
	protected TripleMatch toEMF(TGGRuleApplication m) {

		return null;
	}

	@Override
	protected TGGRuleApplication fromEMF(TripleMatch m) {

		return null;
	}

	public void filter(AppType appType) {

		SolverFactory factory = new SolverFactoryGurobi();
		factory.setParameter(Solver.VERBOSE, 0);
		for(int v : getArrayFromResult(factory.get().solve(prepareProblem(appType)))){
			
			if(v < 0){
				deleteElements(-v, appType);
			}
			
		}

	}

	private void deleteElements(int v, AppType appType) {
		
		TGGRuleApplication m = intToMatch(v);
		
		switch (appType) {
		case FWD:
			deleteElements(m.getCreatedCorr());
			deleteElements(m.getCreatedTrg());
			break;
		case BWD:
			deleteElements(m.getCreatedCorr());
			deleteElements(m.getCreatedSrc());
			break;
		case CC:
			deleteElements(m.getCreatedCorr());
			break;

		default:
			break;
		}
		
	}
	
	private void deleteElements(Collection<EObject> elements){
		for(EObject e : elements)
			e.eResource().getContents().remove(e);
	}

	private Problem prepareProblem(AppType appType) {
		
		Problem ilpProblem = new Problem();

		for (EObject el : createToMatch.keySet()) {
			TIntArrayList variables = createToMatch.get(el);
			allVariables.addAll(variables);
			Linear linear = new Linear();
			variables.forEach(v -> {
				linear.add(1, v);
				return true;
			});
			ilpProblem.add(new Constraint(String.valueOf(name++), linear, "<=", 1));
		}

		for (EObject el : contextToMatch.keySet()) {
			TIntArrayList variables = contextToMatch.get(el);
			variables.forEach(v -> {
				Linear linear = new Linear();
				linear.add(1, v);
				createToMatch.get(el).forEach(v2 -> {
					linear.add(-1, v2);
					return true;
				});
				ilpProblem.add(new Constraint(String.valueOf(name++), linear, "<=", 0));
				return true;
			});
		}

		allVariables.forEach(v -> {
			ilpProblem.setVarType(v, Boolean.class);
			return true;
		});

		Linear objective = new Linear();
		allVariables.forEach(v -> {
			int weight = getWeight(v, appType);
			objective.add(weight, v);
			return true;
		});
		ilpProblem.setObjective(objective);
		return ilpProblem;
	}

	private int getWeight(int v, AppType appType) {

		TGGRuleApplication ra = intToMatch(v);

		switch (appType) {
		case FWD:
			return ra.getCreatedSrc().size();

		case BWD:
			return ra.getCreatedTrg().size();

		case CC:
			return ra.getCreatedSrc().size() + ra.getCreatedTrg().size();
		default:
			return 0;
		}

	}
	
	protected int[] getArrayFromResult(Result result) {
		
		String[] resultPartials = result.toString().split(", ");
		
		// cutting clutter at start and finish
		resultPartials[0] = resultPartials[0].split("\\{")[1];
		resultPartials[resultPartials.length - 1] = resultPartials[resultPartials.length - 1].split("\\}")[0];

		int[] returnArray = new int[allVariables.size()];
		int returnArrayPos = 0;

		for (int i = 0; i < resultPartials.length; i++) {
			// solver also reports results of formulas, this rules them out
			if(!resultPartials[i].contains(".")){
				
				String[] eval = resultPartials[i].split("=");

				int identifier = Integer.parseInt(eval[0]);
			
				// negate identifier if equals 0
				if (Integer.parseInt(eval[1]) == 0) {
					identifier = 0 - identifier;
				}
				returnArray[returnArrayPos] = identifier;
				returnArrayPos++;
			}
		}
		
		return returnArray;
	}

	public enum AppType {
		FWD, BWD, CC
	}

}
