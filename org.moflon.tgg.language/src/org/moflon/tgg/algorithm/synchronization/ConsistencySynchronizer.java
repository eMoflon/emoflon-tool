package org.moflon.tgg.algorithm.synchronization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.moflon.tgg.algorithm.ccutils.AbstractSATSolver;
import org.moflon.tgg.algorithm.ccutils.Sat4JSolver;
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
import org.moflon.tgg.runtime.IsApplicableRuleResult;
import org.moflon.tgg.runtime.Match;
import org.moflon.tgg.runtime.RuntimeFactory;

import gnu.trove.TIntCollection;
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
			CorrespondenceModel graphTriple) {
		protocol = new ConsistencyCheckPrecedenceGraph();
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
				EOperation isApplCC = sourceMatch.getIsApplicableCCOperation();
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

		ArrayList<int[]> clauses = new ArrayList<>();

		addClausesForAlternatives(clauses);
		addClausesForImplications(clauses);

		int[][] satProblem = new int[clauses.size()][];
		int i = 0;
		for (int[] clause : clauses) {
			satProblem[i] = clause;
			i++;
		}
		AbstractSATSolver solver = new Sat4JSolver();
		for (int value : solver.solve(satProblem)) {
			if (value < 0) {
				CCMatch excludedMatch = protocol.intToMatch(-value);
				excludedMatch.getCreateCorr().forEach(e -> graphTriple.getCorrespondences().remove(e));
			}
		}

	}

	private void addClausesForImplications(ArrayList<int[]> clauses) {

		protocol.calculateSiblings();

		TIntIterator allIterator = protocol.getMatchIDs().iterator();
		while (allIterator.hasNext()) {
			int m = allIterator.next();
			TIntCollection parents = protocol.parents(m);
			if (parents.size() == 0)
				continue;
			if (parents.size() == 1) {
				clauses.add(new int[] { -m, parents.iterator().next() });
				continue;
			}
			TIntIterator parentIterator = parents.iterator();
			while (parentIterator.hasNext()) {
				int p = parentIterator.next();
				TIntHashSet clauseHashset = (new TIntHashSet(protocol.siblings(p)));
				clauseHashset.retainAll(parents);
				clauseHashset.add(-m);
				clauses.add(clauseHashset.toArray());
			}
		}

	}

	private void addClausesForAlternatives(ArrayList<int[]> clauses) {
		clauses.addAll(getclausesForAlternatives(srcElements));
		clauses.addAll(getclausesForAlternatives(trgElements));
	}

	private ArrayList<int[]> getclausesForAlternatives(Graph graph) {
		ArrayList<int[]> clauses = new ArrayList<>();
		for (EObject srcElement : graph.getElements()) {
			TIntHashSet variables = new TIntHashSet();
			protocol.creates(srcElement).forEach(ccm -> variables.add(protocol.matchToInt(ccm)));

			if (!variables.isEmpty()) {
				// get a clause like (a V b V c V d ...)
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
			if (isIgnored(n, srcLookupMethods))
				srcMatches.add(emptyMatch);
			else if (isIgnored(n, trgLookupMethods))
				trgMatches.add(emptyMatch);
		});

		// collect precedences
		sourcePrecedenceGraph.collectAllPrecedences(srcMatches);
		targetPrecedenceGraph.collectAllPrecedences(trgMatches);

		srcMatches.forEach(m -> appliedSourceToTarget.put(m.hashCode(), new TIntHashSet()));
	}

	private Collection<Match> collectDerivations(Graph elements, RulesTable lookupMethods) {
		return elements.stream().flatMap(new InvokeIsAppropriate(lookupMethods)).collect(Collectors.toSet());
	}

	private boolean isIgnored(String ruleName, RulesTable lookupMethods) {
		return lookupMethods.getRules().stream().noneMatch(r -> r.getRuleName().equals(ruleName));
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

}
