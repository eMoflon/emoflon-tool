package org.moflon.tgg.algorithm.synchronization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.moflon.tgg.algorithm.ccutils.AbstractSolver;
import org.moflon.tgg.algorithm.ccutils.ILP_Gurobi_Solver;
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

import gnu.trove.iterator.TIntIterator;
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

		extractMatchPairs();

		applyAllMatchPairs();

		filter();
	
	}

	private void applyAllMatchPairs() {

		TIntHashSet readySourceMatches = new TIntHashSet();
		TIntHashSet readyTargetMatches = new TIntHashSet();

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
						if(isApplCC == null)
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

		AbstractSolver solver = new ILP_Gurobi_Solver();

		int[] solvingResult = solver.solve(srcElements, trgElements, protocol);
		
		removeMatches(solvingResult);


	}

	private void removeMatches(int[] matches) {
		ArrayList<CCMatch> excluded = new ArrayList<>();
		for (int value : matches) {
			if (value < 0) {
				CCMatch excludedMatch = protocol.intToMatch(-value);
				excluded.add(excludedMatch);
				excludedMatch.getCreateCorr().forEach(e -> graphTriple.getCorrespondences().remove(e));
			}
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
		return lookupMethods.getRules().stream().filter(r -> r.getRuleName().equals(ruleName)).findAny().get().getIsAppropriateMethods().isEmpty();
	}

	private void extendReady(TIntHashSet readyMatches, PrecedenceInputGraph pg) {
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
	
	public Collection<EObject> getInconsistentSourceElements(){
		return getInconsistentElements(srcElements);
		
	}
	
	public Collection<EObject> getInconsistentTargetElements(){
		return getInconsistentElements(trgElements);
	}
	
	private Collection<EObject> getInconsistentElements(Graph graph){
		
		Graph unmarked = new Graph(graph.getElements());

		Collection<EObject> consistent = protocol.getMatches().stream().flatMap(m -> m.getCreatedHashSet().stream()).collect(Collectors.toSet());
		unmarked.removeDestructive(consistent);
		
		//remove opposite edges as well
		Collection<EObject> consistentOppositeEdges = new HashSet<>();
		for(EMoflonEdge edge : unmarked.getEdges()){
			EReference feature = (EReference) edge.getSrc().eClass().getEStructuralFeature(edge.getName());
			if(feature.getEOpposite() != null){
				EMoflonEdge oppositeEdge = RuntimeFactory.eINSTANCE.createEMoflonEdge();
				oppositeEdge.setName(feature.getEOpposite().getName());
				oppositeEdge.setSrc(edge.getTrg());
				oppositeEdge.setTrg(edge.getSrc());
				if(consistent.contains(oppositeEdge))
					consistentOppositeEdges.add(edge);					
			}
		}
		unmarked.removeDestructive(consistentOppositeEdges);
		
		return unmarked.getElements();
	}

}
