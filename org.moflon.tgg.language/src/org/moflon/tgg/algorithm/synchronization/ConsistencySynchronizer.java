package org.moflon.tgg.algorithm.synchronization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EcoreFactory;
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
		
		srcElements = new Graph(srcDelta.getAddedNodes());
		trgElements = new Graph(trgDelta.getAddedNodes());
	}

	@Override
	public void synchronize() throws InputLocalCompletenessException, TranslationLocalCompletenessException {
		// TODO Auto-generated method stub
		translate();
	}
	
	@Override
	protected void translate() throws InputLocalCompletenessException, TranslationLocalCompletenessException {
		// TODO Auto-generated method stub
		HashSet<RulePair> pairs = extractMatchPairs();
		List<CCMatch> ccMatches = new ArrayList<>();

		boolean onceApplied;
		do {
			onceApplied = false;
			
			for(RulePair pair : pairs) {
				EOperation isApplCC = pair.src.getIsApplicableCCOperation();
				EList<Match> arguments = new BasicEList<Match>();
				arguments.add(pair.src);
				arguments.add(pair.trg);
				IsApplicableRuleResult isApplRR = (IsApplicableRuleResult) InvokeUtil.invokeOperationWithNArguments(isApplCC.getEContainingClass(), isApplCC, arguments);
				if(isApplRR.isSuccess()) {
					pairs.remove(pair);
					ccMatches.add((CCMatch) isApplRR.getIsApplicableMatch().get(0));
					onceApplied = true;
				}
			}
			
		} while(onceApplied);
	}

	private HashSet<RulePair> extractMatchPairs() {
		Collection<Match> srcMatches = collectDerivations(srcElements, srcLookupMethods);
		Collection<Match> trgMatches = collectDerivations(trgElements, trgLookupMethods);
	
		Set<String> ruleNames = new HashSet<String>();
		Map<String, List<Match>> ruleToSrcMap = new HashMap<String, List<Match>>();
		Map<String, List<Match>> ruleToTrgMap = new HashMap<String, List<Match>>();

		for(Match m : srcMatches) {
			ruleNames.add(m.getRuleName());
			ruleToSrcMap.putIfAbsent(m.getRuleName(), new ArrayList<Match>()).add(m);
		}
		
		for(Match m : trgMatches) {
			ruleNames.add(m.getRuleName());
			ruleToTrgMap.putIfAbsent(m.getRuleName(), new ArrayList<Match>()).add(m);
		}
		
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
			// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}
}
