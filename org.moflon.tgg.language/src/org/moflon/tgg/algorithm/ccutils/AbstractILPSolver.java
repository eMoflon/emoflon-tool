package org.moflon.tgg.algorithm.ccutils;

import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.algorithm.ccutils.UserDefinedILPObjective.OptGoal;
import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;
import org.moflon.tgg.algorithm.datastructures.Graph;
import org.moflon.tgg.runtime.CCMatch;

import gnu.trove.TIntCollection;
import gnu.trove.set.hash.TIntHashSet;
import net.sf.javailp.Constraint;
import net.sf.javailp.Linear;
import net.sf.javailp.OptType;
import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;

public abstract class AbstractILPSolver extends AbstractSolver {

	private int variableCount;

	private int constraintCount;
	
	private Problem ilpProblem;

	// this list keeps a record of all variables that appear in the clausels.
	// this is needed to define them as ilp variables later
	protected TIntHashSet variables = new TIntHashSet();

	private UserDefinedILPConstraintProvider userDefinedILPConstraintProvider = null;
	private UserDefinedILPObjectiveProvider userDefinedILPObjectiveProvider = null;

	@Override
	public int[] solve(Graph sourceGraph, Graph targetGraph, ConsistencyCheckPrecedenceGraph protocol) {

		SolverFactory factory = getSolverFactory();
		factory.setParameter(Solver.VERBOSE, 0);

		ilpProblem = createIlpProblemFromGraphs(sourceGraph, targetGraph, protocol);

		variableCount = ilpProblem.getVariablesCount();
		constraintCount = ilpProblem.getConstraintsCount();

		Solver solver = factory.get();

		
		try {
			// solve
			Result result = solver.solve(ilpProblem);

			int[] arrayResult = getArrayFromResult(result);

			return arrayResult;
		} catch (Exception e) {
			throw new RuntimeException("ILP Problem is not solvable.");
		}
	
	}

	protected abstract SolverFactory getSolverFactory();

	protected Problem createIlpProblemFromGraphs(Graph sourceGraph, Graph targetGraph, ConsistencyCheckPrecedenceGraph protocol) {
		Problem ilpProblem = new Problem();

		// get all alternative clauses
		for (EObject elm : sourceGraph.getElements()) {
			TIntCollection variables = protocol.creates(elm);
			if (variables != null && !variables.isEmpty()) {
				int[] alternatives = variables.toArray();
				ilpProblem.add(new Constraint(String.valueOf(elm.hashCode()), getAlternativeLinearFromArray(alternatives), "<=", 1));
			}
		}

		for (EObject elm : targetGraph.getElements()) {
			TIntCollection variables = protocol.creates(elm);
			if (variables != null && !variables.isEmpty()) {
				int[] alternatives = variables.toArray();
				ilpProblem.add(new Constraint(String.valueOf(elm.hashCode()), getAlternativeLinearFromArray(alternatives), "<=", 1));
			}
		}

		// get all implication clauses
		for (int matchId : protocol.getMatchIDs().toArray()) {
			CCMatch match = protocol.intToMatch(matchId);
			for (EObject contextCorrespondence : match.getAllContextElements()) {
				int parentID = protocol.creates(contextCorrespondence).toArray()[0];

				int[] implication = new int[2];
				implication[0] = matchId;
				implication[1] = parentID;
				ilpProblem.add(getImplicationLinearFromArray(implication), "<=", 0);
			}
		}

		// incorporate user constraints on the ILP problem
		if (userDefinedILPConstraintProvider != null) {
			for (UserDefinedILPConstraint constraint : userDefinedILPConstraintProvider.getUserDefinedConstraints(protocol)) {
				Linear linear = new Linear();
				for (int id : constraint.getIdsToCoefficients().keySet()) {
					linear.add(constraint.getIdsToCoefficients().get(id), id);
				}
				ilpProblem.add(new Constraint(String.valueOf(constraint.hashCode()), linear, constraint.getMathematicalSign(), constraint.getReferenceValue()));
			}
		}
		

		// define the objective -> bsp: MAX(A+B+C)
		Linear objective = new Linear();
		if (userDefinedILPObjectiveProvider != null)  {
			// incorporate user objectives on the ILP problem
			UserDefinedILPObjective userObj = userDefinedILPObjectiveProvider.getUserDefinedObjective(protocol);
			Linear linear = new Linear();
			for (int id : userObj.getIdsToCoefficients().keySet()) {
				linear.add(userObj.getIdsToCoefficients().get(id), id);
			}
			ilpProblem.setObjective(linear, userObj.getOptimizationGoal() == OptGoal.MIN ? OptType.MIN : OptType.MAX);
		}
		else {
			for (int matchId : protocol.getMatchIDs().toArray()) {
				CCMatch match = protocol.intToMatch(matchId);
				int weight = match.getSourceMatch().getCreatedHashSet().size();
				weight += match.getTargetMatch().getCreatedHashSet().size();
				objective.add(weight, matchId);
			}
			ilpProblem.setObjective(objective, OptType.MAX);
		}
			
		

		// define variables as ilp boolean variables
		variables.forEach(variable -> {
			ilpProblem.setVarType(variable, Boolean.class);
			return true;
		});

		return ilpProblem;
	}

	protected Linear getAlternativeLinearFromArray(int[] alternatives) {
		Linear linear = new Linear();
		for (int variable : alternatives) {
			linear.add(1, variable);
			if (!variables.contains(variable))
				variables.add(variable);
		}
		return linear;
	}

	protected Linear getImplicationLinearFromArray(int[] implication) {
		Linear linear = new Linear();
		linear.add(1, implication[0]);
		linear.add(-1, implication[1]);
		return linear;
	}

	// Transforms the output of the ILP Solver (one long string) into a result
	// array
	protected int[] getArrayFromResult(Result result) {

		String[] resultPartials = result.toString().split(", ");

		// cutting clutter at start and finish
		resultPartials[0] = resultPartials[0].split("\\{")[1];
		resultPartials[resultPartials.length - 1] = resultPartials[resultPartials.length - 1].split("\\}")[0];

		int[] returnArray = new int[variables.size()];
		int returnArrayPos = 0;

		for (int i = 0; i < resultPartials.length; i++) {
			// solver also reports results of formulas, this rules them out
			if (!resultPartials[i].contains(".")) {

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

	public void setUserDefinedILPConstraintProvider(UserDefinedILPConstraintProvider userDefinedILPConstraintProvider) {
		this.userDefinedILPConstraintProvider = userDefinedILPConstraintProvider;
	}
	
	public void setUserDefinedILPObjectiveProvider(UserDefinedILPObjectiveProvider userDefinedILPObjectiveProvider) {
		this.userDefinedILPObjectiveProvider = userDefinedILPObjectiveProvider;
	}

	public int getVariableCount() {
		return variableCount;
	}

	public int getConstraintCount() {
		return constraintCount;
	}

	public TIntHashSet getVariables() {
		return variables;
	}
	
	public Problem getILPProblem() {
		return ilpProblem;
	}
}
