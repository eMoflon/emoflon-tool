package org.moflon.tgg.algorithm.synchronization;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tgg.algorithm.configuration.Configurator;
import org.moflon.tgg.algorithm.datastructures.Graph;
import org.moflon.tgg.algorithm.datastructures.SynchronizationProtocol;
import org.moflon.tgg.algorithm.datastructures.TripleMatch;
import org.moflon.tgg.algorithm.delta.Delta;
import org.moflon.tgg.language.algorithm.TempOutputContainer;
import org.moflon.tgg.language.analysis.RulesTable;
import org.moflon.tgg.language.analysis.StaticAnalysis;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.tgg.runtime.Match;

/**
 * A specialization of {@link Synchronizer} for the forward direction. This
 * includes deleting, finalizing the results, and determining what is "source"
 * and "target".
 * 
 * @author anjorin
 *
 */
public class ForwardSynchronizer extends Synchronizer {

	public ForwardSynchronizer(CorrespondenceModel graphTriple, Delta delta, SynchronizationProtocol protocol,
			Configurator configurator, StaticAnalysis rules, TempOutputContainer tempOutputContainer) {
		super(graphTriple, delta, protocol, configurator, rules, tempOutputContainer);
	}

	@Override
	protected Graph delete(Collection<TripleMatch> allToBeRevokedTripleMatches) {
		allToBeRevokedTripleMatches.forEach(tripleMatch -> tripleMatch.getCreatedCorrAndTrgElts().delete());
		return allToBeRevokedTripleMatches.stream().map(TripleMatch::getCreatedSrcElts).reduce(Graph.getEmptyGraph(),
				Graph::addConstructive);
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
		return "FWD";
	}

	@Override
	protected RulesTable getRulesTable(StaticAnalysis rules) {

		return rules.getSourceRules();
	}
}
