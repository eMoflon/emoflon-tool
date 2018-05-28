package org.moflon.tgg.algorithm.synchronization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.cardygan.ilp.api.Result;
import org.cardygan.ilp.api.model.BinaryVar;
import org.cardygan.ilp.api.model.Model;
import org.cardygan.ilp.api.solver.Solver;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.moflon.tgg.algorithm.ccutils.ILPProblemPreparation;
import org.moflon.tgg.algorithm.ccutils.UserDefinedILPStrategy;
import org.moflon.tgg.algorithm.datastructures.ConsistencyCheckPrecedenceGraph;
import org.moflon.tgg.algorithm.datastructures.Graph;
import org.moflon.tgg.algorithm.datastructures.PrecedenceInputGraph;
import org.moflon.tgg.algorithm.delta.Delta;
import org.moflon.tgg.algorithm.invocation.InvokeIsAppropriate;
import org.moflon.tgg.algorithm.invocation.InvokeUtil;
import org.moflon.tgg.language.analysis.RulesTable;
import org.moflon.tgg.language.analysis.StaticAnalysis;
import org.moflon.tgg.runtime.CCMatch;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.tgg.runtime.EMoflonEdge;
import org.moflon.tgg.runtime.IsApplicableRuleResult;
import org.moflon.tgg.runtime.Match;
import org.moflon.tgg.runtime.RuntimeFactory;

import gnu.trove.TIntCollection;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.hash.TIntHashSet;

/**
 * A specialization of {@link Synchronizer} for consistency checks.
 * 
 * @author fritsche
 *
 */
public class ConsistencySynchronizer {

	private RulesTable srcLookupMethods;
	private RulesTable trgLookupMethods;

	private Graph srcElements;
	private Graph trgElements;

	private ConsistencyCheckPrecedenceGraph protocol;

	private CorrespondenceModel graphTriple;

	private PrecedenceInputGraph sourcePrecedenceGraph;
	private PrecedenceInputGraph targetPrecedenceGraph;

	private TIntObjectHashMap<TIntHashSet> appliedSourceToTarget;

	private int variableCount;

	private int chosenVariableCount;

	private int constraintCount;

	private ILPProblemPreparation ilpProblemPreparation = new ILPProblemPreparation();

	private double runtimeOfCorrespondenceCreation;

	private double runtimeOfILPSolving;

	private double runtimeOfRemovingDeselectedCorrespondences;

	private Solver solver;
	
	private Result result;
	
	private UserDefinedILPStrategy userDefinedILPstrategy;

	public ConsistencySynchronizer(Delta srcDelta, Delta trgDelta, StaticAnalysis staticAnalysis,
			CorrespondenceModel graphTriple, ConsistencyCheckPrecedenceGraph protocol) {
		this.protocol = protocol;
		this.graphTriple = graphTriple;
		srcLookupMethods = staticAnalysis.getSourceRules();
		trgLookupMethods = staticAnalysis.getTargetRules();
		srcElements = new Graph(srcDelta.getAddedNodes(), srcDelta.getAddedEdges());
		trgElements = new Graph(trgDelta.getAddedNodes(), trgDelta.getAddedEdges());
		sourcePrecedenceGraph = new PrecedenceInputGraph();
		targetPrecedenceGraph = new PrecedenceInputGraph();
		appliedSourceToTarget = new TIntObjectHashMap<>();
	}

	protected void createCorrespondences() {

		double tic = System.currentTimeMillis();
		extractMatchPairs();

		applyAllMatchPairs();
		double toc = System.currentTimeMillis();

		runtimeOfCorrespondenceCreation = toc - tic;

		filter();

	}

	private void applyAllMatchPairs() {

		TIntList readySourceMatches = new TIntArrayList();
		TIntList readyTargetMatches = new TIntArrayList();

		extendReady(readySourceMatches, sourcePrecedenceGraph);
		extendReady(readyTargetMatches, targetPrecedenceGraph);

		boolean someRulesApplied = true;
		while (someRulesApplied) {
			someRulesApplied = false;
			TIntIterator sourceReadyIterator = readySourceMatches.iterator();

			while (sourceReadyIterator.hasNext()) {
				int sourceMatchID = sourceReadyIterator.next();
				Match sourceMatch = sourcePrecedenceGraph.intToMatch(sourceMatchID);
				TIntIterator targetReadyIterator = readyTargetMatches.iterator();
				while (targetReadyIterator.hasNext()) {
					int targetMatchID = targetReadyIterator.next();
					Match targetMatch = targetPrecedenceGraph.intToMatch(targetMatchID);
					if (sourceMatch.getRuleName().equals(targetMatch.getRuleName())
							&& !appliedSourceToTarget.get(sourceMatchID).contains(targetMatchID)) {
						appliedSourceToTarget.get(sourceMatchID).add(targetMatchID);
						EList<Match> arguments = new BasicEList<Match>();
						arguments.add(sourceMatch);
						arguments.add(targetMatch);
						EOperation isApplCC = sourceMatch.getIsApplicableCCOperation();
						if (isApplCC == null)
							isApplCC = targetMatch.getIsApplicableCCOperation();
						IsApplicableRuleResult isApplRR = (IsApplicableRuleResult) InvokeUtil
								.invokeOperationWithNArguments(isApplCC.getEContainingClass(), isApplCC, arguments);
						if (isApplRR.isSuccess()) {
							someRulesApplied = true;
							isApplRR.getIsApplicableMatch().forEach(m -> {
								CCMatch ccMatch = (CCMatch) m;
								ccMatch.getCreateCorr().forEach(corr -> graphTriple.getCorrespondences().add(corr));
								protocol.collectPrecedences(ccMatch);
							});
						}

					}
				}
			}
			extendReady(readySourceMatches, sourcePrecedenceGraph);
			extendReady(readyTargetMatches, targetPrecedenceGraph);
		}
	}

	private void filter() {

		double tic = System.currentTimeMillis();

		Model model = ilpProblemPreparation.createIlpProblemFromGraphs(srcElements, trgElements, protocol);

		if (userDefinedILPstrategy != null)
			userDefinedILPstrategy.modifyILPproblem(model, protocol);

		this.result = model.solve(solver);

		double toc = System.currentTimeMillis();

		runtimeOfILPSolving = toc - tic;

		variableCount = model.getVars().size();
		constraintCount = model.getConstraints().size();

		double tic2 = System.currentTimeMillis();
		removeMatches(result);
		double toc2 = System.currentTimeMillis();

		runtimeOfRemovingDeselectedCorrespondences = toc2 - tic2;

	}

	private void removeMatches(Result result) {
		ArrayList<CCMatch> excluded = new ArrayList<>();
		for (CCMatch m : protocol.getMatches()) {
			BinaryVar binaryVar = protocol.getBinaryVar(m);
			Double doubleValue = result.getSolutions().get(binaryVar);
			
			if (doubleValue == null || doubleValue.intValue() != 1) {
				excluded.add(m);
				m.getCreateCorr().forEach(e -> graphTriple.getCorrespondences().remove(e));
			} else
				chosenVariableCount++;
		}
		protocol.removeMatches(excluded);
		
	}

	private void extractMatchPairs() {
		Collection<Match> srcMatches = collectDerivations(srcElements, srcLookupMethods);
		Collection<Match> trgMatches = collectDerivations(trgElements, trgLookupMethods);

		Set<String> ruleNames = Stream
				.concat(srcLookupMethods.getRules().stream(), trgLookupMethods.getRules().stream())
				.map(r -> r.getRuleName()).collect(Collectors.toSet());

		// at one-sided matchings of ignore rules, provide an empty match for
		// the missing side
		ruleNames.forEach(n -> {
			Match emptyMatch = RuntimeFactory.eINSTANCE.createMatch();
			emptyMatch.setRuleName(n);
			if (noIsApprMethod(n, srcLookupMethods))
				srcMatches.add(emptyMatch);
			else if (noIsApprMethod(n, trgLookupMethods))
				trgMatches.add(emptyMatch);
		});

		// collect precedences
		sourcePrecedenceGraph.collectAllPrecedences(srcMatches);
		targetPrecedenceGraph.collectAllPrecedences(trgMatches);

		srcMatches.forEach(m -> appliedSourceToTarget.put(sourcePrecedenceGraph.matchToInt(m), new TIntHashSet()));
	}

	private Collection<Match> collectDerivations(Graph elements, RulesTable lookupMethods) {
		return elements.stream().flatMap(new InvokeIsAppropriate(lookupMethods)).collect(Collectors.toSet());
	}

	private boolean noIsApprMethod(String ruleName, RulesTable lookupMethods) {
		return lookupMethods.getRules().stream().filter(r -> r.getRuleName().equals(ruleName)).findAny().get()
				.getIsAppropriateMethods().isEmpty();
	}

	private void extendReady(TIntCollection readyMatches, PrecedenceInputGraph pg) {
		if (readyMatches.isEmpty()) {
			pg.getMatchIDs().forEach(m -> {
				if (pg.parents(m).isEmpty())
					readyMatches.add(m);
				return true;
			});
		} else {
			TIntHashSet newReady = new TIntHashSet();
			readyMatches.forEach(m -> {
				pg.children(m).forEach(c -> {
					if (readyMatches.containsAll(pg.parents(c)))
						newReady.add(c);
					return true;
				});
				return true;
			});
			readyMatches.addAll(newReady);
		}
	}

	public Collection<EObject> getInconsistentSourceElements() {
		return getInconsistentElements(srcElements);

	}

	public Collection<EObject> getInconsistentTargetElements() {
		return getInconsistentElements(trgElements);
	}

	private Collection<EObject> getInconsistentElements(Graph graph) {

		Graph unmarked = new Graph(graph.getElements());

		Collection<EObject> consistent = protocol.getMatches().stream().flatMap(m -> m.getCreatedHashSet().stream())
				.collect(Collectors.toSet());
		unmarked.removeDestructive(consistent);

		// remove opposite edges as well
		Collection<EObject> consistentOppositeEdges = new HashSet<>();
		for (EMoflonEdge edge : unmarked.getEdges()) {
			EReference feature = (EReference) edge.getSrc().eClass().getEStructuralFeature(edge.getName());
			if (feature.getEOpposite() != null) {
				EMoflonEdge oppositeEdge = RuntimeFactory.eINSTANCE.createEMoflonEdge();
				oppositeEdge.setName(feature.getEOpposite().getName());
				oppositeEdge.setSrc(edge.getTrg());
				oppositeEdge.setTrg(edge.getSrc());
				if (consistent.contains(oppositeEdge))
					consistentOppositeEdges.add(edge);
			}
		}
		unmarked.removeDestructive(consistentOppositeEdges);

		return unmarked.getElements();
	}

	public int getVariableCount() {
		return variableCount;
	}

	public int getChosenVariableCount() {
		return chosenVariableCount;
	}

	public int getConstraintCount() {
		return constraintCount;
	}

	public double getRuntimeOfCorrespondenceCreation() {
		return runtimeOfCorrespondenceCreation;
	}
	
	public Result getResult() {
		return result;
	}

	public double getRuntimeOfILPSolving() {
		return runtimeOfILPSolving;
	}

	public double getRuntimeOfRemovingDeselectedCorrespondences() {
		return runtimeOfRemovingDeselectedCorrespondences;
	}

	public void setILPSolver(Solver solver) {
		this.solver = solver;
	}

	public void setUserDefinedILPStrategy(UserDefinedILPStrategy userDefinedILPstrategy) {
		this.userDefinedILPstrategy = userDefinedILPstrategy;
	}

}
