package org.moflon.tgg.algorithm.ccutils;

import static org.cardygan.ilp.api.util.ExprDsl.leq;
import static org.cardygan.ilp.api.util.ExprDsl.mult;
import static org.cardygan.ilp.api.util.ExprDsl.param;
import static org.cardygan.ilp.api.util.ExprDsl.sum;

import org.cardygan.ilp.api.model.ArithExpr;
import org.cardygan.ilp.api.model.BinaryVar;
import org.cardygan.ilp.api.model.Model;
import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;
import org.moflon.tgg.algorithm.datastructures.Graph;
import org.moflon.tgg.runtime.CCMatch;

import gnu.trove.TIntCollection;
import gnu.trove.set.hash.THashSet;
import gnu.trove.set.hash.TIntHashSet;

public class ILPProblemPreparation {


	private Model ilpProblem;

	private BinaryVarProvider binaryVarFactory;

	// this list keeps a record of all variables that appear in the clausels.
	// this is needed to define them as ilp variables later
	protected TIntHashSet variables = new TIntHashSet();

	public Model createIlpProblemFromGraphs(Graph sourceGraph, Graph targetGraph,
			ConsistencyCheckPrecedenceGraph protocol) {

		ilpProblem = new Model();

		binaryVarFactory = new BinaryVarProvider(ilpProblem, protocol);

		int constraintCount = 0;

		// get all alternative clauses
		THashSet<EObject> elements = new THashSet<EObject>(sourceGraph.getElements());
		elements.addAll(targetGraph.getElements());
		for (EObject elm : elements) {
			TIntCollection variables = protocol.creates(elm);
			if (variables != null && !variables.isEmpty()) {
				int[] alternatives = variables.toArray();
				ilpProblem.newConstraint("c" + constraintCount++, leq(sum(binaryVarFactory.getBinaryVars(alternatives)), param(1)));
			}
		}

		// get all implication clauses
		for (int matchId : protocol.getMatchIDs().toArray()) {
			CCMatch match = protocol.intToMatch(matchId);
			for (EObject contextCorrespondence : match.getAllContextElements()) {
				int parentID = protocol.creates(contextCorrespondence).toArray()[0];

				BinaryVar premise = binaryVarFactory.getBinaryVar(matchId);
				BinaryVar conclusion = binaryVarFactory.getBinaryVar(parentID);
				ilpProblem.newConstraint("c" + constraintCount++, leq(premise, conclusion));
			}
		}


		//set objective

		int[] matchIDs = protocol.getMatchIDs().toArray();
		ArithExpr[] arithExprs = new ArithExpr[matchIDs.length];
		for (int i = 0; i < matchIDs.length; i++) {
			int matchID = matchIDs[i];
			CCMatch match = protocol.intToMatch(matchID);
			int weight = match.getSourceMatch().getCreatedHashSet().size();
			weight += match.getTargetMatch().getCreatedHashSet().size();
			arithExprs[i] = mult(param(weight), binaryVarFactory.getBinaryVar(matchID));
		}
		ilpProblem.newObjective(true,sum(arithExprs));

		return ilpProblem;
	}

}
