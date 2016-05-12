package org.moflon.tgg.algorithm.synchronization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tgg.algorithm.configuration.Configurator;
import org.moflon.tgg.algorithm.datastructures.Graph;
import org.moflon.tgg.algorithm.datastructures.SynchronizationProtocol;
import org.moflon.tgg.algorithm.datastructures.TripleMatch;
import org.moflon.tgg.algorithm.delta.Delta;
import org.moflon.tgg.algorithm.exceptions.InputLocalCompletenessException;
import org.moflon.tgg.algorithm.exceptions.TranslationLocalCompletenessException;
import org.moflon.tgg.algorithm.invocation.InvokeUtil;
import org.moflon.tgg.language.algorithm.TempOutputContainer;
import org.moflon.tgg.language.analysis.RulesTable;
import org.moflon.tgg.language.analysis.StaticAnalysis;
import org.moflon.tgg.runtime.CCMatch;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.tgg.runtime.IsApplicableRuleResult;
import org.moflon.tgg.runtime.Match;
import org.moflon.tgg.runtime.RuntimeFactory;

/**
 * A specialization of {@link Synchronizer} for consistency checks.
 * 
 * @author fritsche
 *
 */
public class ConsistencySynchronizer extends Synchronizer {

	protected RulesTable srcLookupMethods;
    protected RulesTable trgLookupMethods;

    protected Graph srcElements;
    protected Graph trgElements;

	
	public ConsistencySynchronizer(CorrespondenceModel graphTriple, Delta srcDelta, Delta trgDelta, SynchronizationProtocol protocol,
			Configurator configurator, StaticAnalysis rules, TempOutputContainer tempOutputContainer) {
		super(graphTriple, new Delta(), protocol, configurator, rules, tempOutputContainer);

		srcLookupMethods = rules.getSourceRules();
		trgLookupMethods = rules.getTargetRules();
		
		srcElements = new Graph(srcDelta.getAddedNodes(), srcDelta.getAddedEdges());
		trgElements = new Graph(trgDelta.getAddedNodes(), trgDelta.getAddedEdges());
	}

	@Override
	public void synchronize() throws InputLocalCompletenessException, TranslationLocalCompletenessException {
		translate();
	}
	
	@Override
	protected void translate() throws InputLocalCompletenessException, TranslationLocalCompletenessException {

		HashSet<RulePair> pairs = extractMatchPairs();
		List<CCMatch> ccMatches = new ArrayList<>();
		
		HashSet<RulePair> appliedInLastRun;
		do {
			appliedInLastRun = new HashSet<>(); 
			
			for(RulePair pair : pairs) {
				EOperation isApplCC = pair.src.getIsApplicableCCOperation();
				EList<Match> arguments = new BasicEList<Match>();
				arguments.add(pair.src);
				arguments.add(pair.trg);
				IsApplicableRuleResult isApplRR = (IsApplicableRuleResult) InvokeUtil.invokeOperationWithNArguments(isApplCC.getEContainingClass(), isApplCC, arguments);
				if(isApplRR.isSuccess()) {
					appliedInLastRun.add(pair);
					isApplRR.getIsApplicableMatch().forEach(m -> {
						CCMatch ccMatch = (CCMatch) m;
						ccMatches.add(ccMatch);
						ccMatch.getCreateCorr().forEach(corr -> graphTriple.getCorrespondences().add(corr));
					});
				}
			}
			pairs.removeAll(appliedInLastRun);
			
		} while(!appliedInLastRun.isEmpty());
	}

	private HashSet<RulePair> extractMatchPairs() {
		Collection<Match> srcMatches = collectDerivations(srcElements, srcLookupMethods);
		Collection<Match> trgMatches = collectDerivations(trgElements, trgLookupMethods);
	
		Set<String> ruleNames = Stream.concat(srcLookupMethods.getRules().stream(), trgLookupMethods.getRules().stream()).map(r -> r.getRuleName()).collect(Collectors.toSet());
		
		Map<String, Collection<Match>> ruleToSrcMap = new HashMap<String, Collection<Match>>();
		Map<String, Collection<Match>> ruleToTrgMap = new HashMap<String, Collection<Match>>();
		ruleNames.forEach(rn -> {
			ruleToSrcMap.put(rn, new HashSet<>());
			ruleToTrgMap.put(rn, new HashSet<>());
		});
		
		for(Match m : srcMatches) {
			ruleToSrcMap.putIfAbsent(m.getRuleName(), new ArrayList<Match>());
			ruleToSrcMap.get(m.getRuleName()).add(m);
		}
		
		for(Match m : trgMatches) {
			ruleToTrgMap.putIfAbsent(m.getRuleName(), new ArrayList<Match>());
			ruleToTrgMap.get(m.getRuleName()).add(m);
		}
		
		//at one-sided matchings of ignore rules, provide an empty match for the missing side
		ruleNames.forEach(n -> {
			Match emptyMatch = RuntimeFactory.eINSTANCE.createMatch();
			emptyMatch.setRuleName(n);
			if(isIgnored(n, srcLookupMethods))
				ruleToSrcMap.get(n).add(emptyMatch);
			if(isIgnored(n, trgLookupMethods))
				ruleToTrgMap.get(n).add(emptyMatch);
		});
		
		HashSet<RulePair> pairs = new HashSet<RulePair>();
		for(String rule : ruleNames) {
			for(Match src : ruleToSrcMap.get(rule)) {
				for(Match trg : ruleToTrgMap.get(rule)) {
					pairs.add(new RulePair(src, trg));
				}
			}
		}
		return pairs;
	}

	class RulePair {
		protected Match src;
		protected Match trg;
		public RulePair(Match src, Match trg) {
			this.src = src;
			this.trg = trg;
		}
		@Override
		public boolean equals(Object arg0) {

			if(arg0 instanceof RulePair) {
				RulePair arg = (RulePair) arg0;
				return src.equals(arg.src) && trg.equals(arg.trg);
			}
			return false;
		}
	}

	@Override
	protected void finalizeGraphTriple(CorrespondenceModel graphTriple) {
		if (graphTriple.getTarget() != null)
			return;

		if (tempOutputContainer.getPotentialRoots().size() == 1) {
			EObject target = tempOutputContainer.getPotentialRoots().get(0);
			graphTriple.setTarget(target);
			tempOutputContainer.getPotentialRoots().clear();
			tempOutputContainer.eResource().getResourceSet()
					.createResource(eMoflonEMFUtil.createFileURI("targetModel", false)).getContents().add(target);
		} else
			graphTriple.setTarget(tempOutputContainer);
	}

	@Override
	protected Graph determineSourceElements(Match coreMatch, Graph all, Graph corr) {
		return determineInputElements(coreMatch);
	}

	@Override
	protected Graph determineTargetElements(Match coreMatch, Graph all, Graph corr) {
		return all.remove(determineSourceElements(coreMatch, all, corr)).removeDestructive(corr);
	}

	@Override
	protected String getDirection() {
		return "CC";
	}

	@Override
	protected Graph delete(Collection<TripleMatch> allToBeRevokedTripleMatches) {
		return null;
	}
	
	private boolean isIgnored(String ruleName, RulesTable lookupMethods){
		return lookupMethods.getRules().stream().noneMatch(r -> r.getRuleName().equals(ruleName));
	}
}
