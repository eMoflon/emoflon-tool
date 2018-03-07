package org.moflon.tgg.algorithm.synchronization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.moflon.tgg.algorithm.configuration.Configurator;
import org.moflon.tgg.algorithm.configuration.RuleResult;
import org.moflon.tgg.algorithm.datastructures.CollectionProvider;
import org.moflon.tgg.algorithm.datastructures.Graph;
import org.moflon.tgg.algorithm.datastructures.PrecedenceInputGraph;
import org.moflon.tgg.algorithm.datastructures.SynchronizationProtocol;
import org.moflon.tgg.algorithm.datastructures.TripleMatch;
import org.moflon.tgg.algorithm.delta.AttributeDelta;
import org.moflon.tgg.algorithm.delta.Delta;
import org.moflon.tgg.algorithm.exceptions.InputLocalCompletenessException;
import org.moflon.tgg.algorithm.exceptions.TranslationLocalCompletenessException;
import org.moflon.tgg.algorithm.invocation.InvokeCheckAttributes;
import org.moflon.tgg.algorithm.invocation.InvokeCheckDEC;
import org.moflon.tgg.algorithm.invocation.InvokeIsApplicable;
import org.moflon.tgg.algorithm.invocation.InvokeIsAppropriate;
import org.moflon.tgg.algorithm.invocation.InvokePerform;
import org.moflon.tgg.language.algorithm.TempOutputContainer;
import org.moflon.tgg.language.analysis.Rule;
import org.moflon.tgg.language.analysis.RulesTable;
import org.moflon.tgg.language.analysis.StaticAnalysis;
import org.moflon.tgg.runtime.AbstractCorrespondence;
import org.moflon.tgg.runtime.AttributeConstraintsRuleResult;
import org.moflon.tgg.runtime.CorrespondenceModel;
import org.moflon.tgg.runtime.EMoflonEdge;
import org.moflon.tgg.runtime.IsApplicableMatch;
import org.moflon.tgg.runtime.IsApplicableRuleResult;
import org.moflon.tgg.runtime.Match;
import org.moflon.tgg.runtime.PerformRuleResult;

import gnu.trove.TIntCollection;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;

/**
 *
 * Contains the primary methods for TGG-based synchronization.
 * 
 * @author anjorin, leblebici
 *
 */
public abstract class Synchronizer {
	protected SynchronizationProtocol protocol;

	private Configurator configurator;

	protected TempOutputContainer tempOutputContainer;

	protected CorrespondenceModel graphTriple;

	private Graph toBeTranslated;

	private Graph toBeDeleted;

	protected RulesTable lookupMethods;

	protected Collection<Rule> ignoreMethods;

	protected AmalgamationUtil amalgamationUtil;

	private Collection<Match> inputMatchCollection;

	private TIntCollection inputMatches;

	private TIntCollection readyKernels;

	private TIntCollection readyWithSiblingKernels;

	private TIntCollection readyButUnreadySiblingsKernels;

	private TIntCollection readyComplements;

	private PrecedenceInputGraph pg;

	private Collection<AttributeDelta> toBeChanged;

	private Logger logger = Logger.getLogger(Synchronizer.class);

	// Debugger relevant fields
	private Graph allRevokedElts;

	private Graph addedElts;

	private Collection<EObject> translated;

	private Collection<TripleMatch> createdTripleMatchesInLastStep;

	public Synchronizer(CorrespondenceModel graphTriple, Delta delta, SynchronizationProtocol protocol,
			Configurator configurator, StaticAnalysis rules, TempOutputContainer tempOutputContainer) {
		this.protocol = protocol;
		this.configurator = configurator;
		this.tempOutputContainer = tempOutputContainer;
		this.graphTriple = graphTriple;
		this.toBeTranslated = new Graph(delta.getAllAddedElements());
		this.toBeDeleted = new Graph(delta.getAllDeletedElements());
		this.toBeChanged = delta.getAttributeChanges();
		this.pg = new PrecedenceInputGraph();
		this.readyKernels = new TIntHashSet();
		this.readyWithSiblingKernels = new TIntHashSet();
		this.readyButUnreadySiblingsKernels = new TIntHashSet();
		this.readyComplements = new TIntHashSet();
		this.createdTripleMatchesInLastStep = CollectionProvider.<TripleMatch>getCollection();
		this.ignoreMethods = determineIgnoreMethods(rules);
		this.lookupMethods = determineLookupMethods(rules);
		this.amalgamationUtil = new AmalgamationUtil(lookupMethods);
	}

	private RulesTable determineLookupMethods(StaticAnalysis rules) {
		RulesTable rulesTable = getRulesTable(rules);
		rulesTable.getRules().removeAll(ignoreMethods);
		return rulesTable;
	}

	private Collection<Rule> determineIgnoreMethods(StaticAnalysis rules) {
		RulesTable rulesTable = getRulesTable(rules);
		return rulesTable.getRules().stream().filter(r -> r.isIgnore()).collect(Collectors.toSet());
	}

	protected abstract RulesTable getRulesTable(StaticAnalysis rules);

	public void synchronize() throws InputLocalCompletenessException, TranslationLocalCompletenessException {
		handleDeletions();

		handleAdditions();

		handleAttributeChanges();

		translate();
	}

	private void handleDeletions() {
		allRevokedElts = revoke(toBeDeleted);
		toBeTranslated.addConstructive(allRevokedElts);
		toBeTranslated.removeDestructive(toBeDeleted);
		allRevokedElts = null;
	}

	private void handleAdditions() {
		Stream<TripleMatch> tripleMatchesWithDECviolation = getTripleMatchesWithDECviolation(toBeTranslated);
		allRevokedElts = revokeTripleMatches(tripleMatchesWithDECviolation);
		toBeTranslated.addConstructive(allRevokedElts);

		inputMatchCollection = collectDerivations(toBeTranslated, lookupMethods);
		allRevokedElts = null;
	}

	private Stream<TripleMatch> getTripleMatchesWithDECviolation(Graph graph) {
		Stream<TripleMatch> tripleMatchesOfTouchedNodes = graph.getEdges().stream().flatMap(
				e -> Stream.concat(protocol.createsAsStream(e.getTrg()), protocol.createsAsStream(e.getSrc())));

		InvokeCheckDEC invokeCheckDEC = new InvokeCheckDEC(lookupMethods);

		return tripleMatchesOfTouchedNodes.filter(invokeCheckDEC.negate());
	}

	private void handleAttributeChanges() {
		Collection<TripleMatch> toBeChecked = new HashSet<>();
		Graph allRevokedElts = Graph.getEmptyGraph();

		toBeChanged.forEach(attrDelta -> toBeChecked.addAll(collectMatches(attrDelta.getAffectedNode())));

		while (!toBeChecked.isEmpty()) {
			TripleMatch tMatch = chooseNext(toBeChecked);
			AttributeConstraintsRuleResult acRuleResult = checkCSP(tMatch);

			if (acRuleResult.isSuccess()) {
				if (acRuleResult.isRequiredChange()) {
					Collection<TripleMatch> candidates = protocol
							.getAsCollection(protocol.children(protocol.matchToInt(tMatch)));
					toBeChecked.addAll(candidates);
				}
			} else {
				Graph createdElts = tMatch.getCreatedElements();
				Graph changesRevoked = revoke(createdElts);
				toBeTranslated.addConstructive(changesRevoked);
				allRevokedElts.addConstructive(changesRevoked);
			}

			toBeChecked.remove(tMatch);
		}

		inputMatchCollection.addAll(collectDerivations(allRevokedElts, lookupMethods));
	}

	private TripleMatch chooseNext(Collection<TripleMatch> toBeChecked) {
		for (TripleMatch m : toBeChecked) {
			if (protocol.ancestors(m).stream().noneMatch(ascendant -> toBeChecked.contains(ascendant)))
				return m;
		}

		throw new IllegalStateException("No next candidate match found in toBeChecked!");
	}

	/**
	 * Collect all matches that have to be considered due to changing attributes of
	 * node. Note that if node has already been revoked, the protocol will return
	 * neither creating nor context matches for it.
	 * 
	 * @param node
	 * @param toBeChecked
	 * @return
	 */
	private Collection<TripleMatch> collectMatches(EObject node) {
		HashSet<TripleMatch> result = new HashSet<>();
		result.addAll(protocol.getCreatingMatches(node));
		result.addAll(protocol.getContextMatches(node));

		return result;
	}

	private Rule findRule(String ruleName) {
		for (Rule rule : this.lookupMethods.getRules()) {
			if (rule.getRuleName().equals(ruleName))
				return rule;
		}

		for (Rule rule : this.ignoreMethods) {
			if (rule.getRuleName().equals(ruleName))
				return rule;
		}

		return null;
	}

	private AttributeConstraintsRuleResult checkCSP(TripleMatch match) {
		EClass ruleClass = findRule(match.getRuleName()).getCheckDECMethod().getEContainingClass();

		for (EOperation o : ruleClass.getEAllOperations()) {
			if (("checkAttributes_" + getDirection()).equals(o.getName())) {
				org.moflon.tgg.runtime.TripleMatch tmatch = protocol.toEMF(match);
				return new InvokeCheckAttributes(ruleClass, o).apply(tmatch);
			}
		}

		throw new UnsupportedOperationException();
	}

	protected Collection<Match> collectDerivations(Graph elements, RulesTable lookupMethods) {
		return elements.stream().flatMap(new InvokeIsAppropriate(lookupMethods)).collect(Collectors.toSet());
	}

	private Graph revoke(Graph elts) {
		Stream<TripleMatch> toBeRevokedTripleMatches = protocol.createsAsStream(elts);
		return revokeTripleMatches(toBeRevokedTripleMatches);
	}

	private Graph revokeTripleMatches(Stream<TripleMatch> tripleMatches) {
		Collection<TripleMatch> toBeRevokedTripleMatches = new HashSet<>();
		tripleMatches.forEach(m -> {
			toBeRevokedTripleMatches.addAll(protocol.descendants(m));
			toBeRevokedTripleMatches.add(m);
		});
		protocol.removeMatches(toBeRevokedTripleMatches);
		return delete(toBeRevokedTripleMatches);
	}

	protected abstract Graph delete(Collection<TripleMatch> allToBeRevokedTripleMatches);

	protected abstract String getDirection();

	protected TripleMatch createTripleMatch(PerformRuleResult performRR, IsApplicableMatch isApplMatch) {
		Graph created = new Graph(performRR.getCreatedElements(), performRR.getCreatedEdges())
				.addConstructive(performRR.getTranslatedElements()).addConstructive(performRR.getTranslatedEdges())
				.addConstructive(performRR.getCreatedLinkElements());

		Graph context = new Graph(isApplMatch.getAllContextElements()).removeDestructive(created);

		Graph all = created.add(context);

		Graph corr = new Graph(all.stream().filter(this::isCorrespondenceElt).collect(Collectors.toSet()));

		Graph source = determineSourceElements(coreMatchOf(isApplMatch), all, corr);
		Graph target = determineTargetElements(coreMatchOf(isApplMatch), all, corr);

		return new TripleMatch(isApplMatch.getIsApplicableRuleResult().getRule(), source, target, corr, created,
				context, performRR.getNodeMappings());
	}

	protected abstract Graph determineSourceElements(Match coreMatch, Graph all, Graph corr);

	protected abstract Graph determineTargetElements(Match coreMatch, Graph all, Graph corr);

	private boolean isCorrespondenceElt(EObject elt) {
		if (elt instanceof EMoflonEdge) {
			EMoflonEdge edge = (EMoflonEdge) elt;
			return edge.getSrc() instanceof AbstractCorrespondence;
		}

		return elt instanceof AbstractCorrespondence;
	}

	protected void translate() throws InputLocalCompletenessException, TranslationLocalCompletenessException {
		pg.collectAllPrecedences(inputMatchCollection);

		configurator.inspectPG(pg);
		inputMatches = pg.getMatchIDs();

		extendReady(inputMatches);

		while (!inputMatches.isEmpty()) {
			if (readyKernels.isEmpty())
				throw new InputLocalCompletenessException(pg.getAsCollection(inputMatches));

			Stream<Match> maximalSet = chooseOneMaximalSet();
			Collection<IsApplicableRuleResult> extended = extendToFullMatches(maximalSet);

			if (!extended.stream().anyMatch(isApplRR -> isApplRR.isSuccess()))
				throw new TranslationLocalCompletenessException(extended);

			IsApplicableMatch chosen = chooseOne(extended);

			PerformRuleResult chosenRR = applyAndUpdateTriple(graphTriple, chosen);

			updateProcessedSets(chosen);

			if (amalgamationUtil.isAmalgamatedTGG())
				processAmalgamationComplements(inputMatches, chosen, chosenRR);

			translated = null;
			createdTripleMatchesInLastStep = CollectionProvider.<TripleMatch>getCollection();
		}

		finalizeGraphTriple(graphTriple);
	}

	protected abstract void finalizeGraphTriple(CorrespondenceModel graphTriple);

	private PerformRuleResult applyAndUpdateTriple(CorrespondenceModel graphTriple, IsApplicableMatch isApplMatch) {
		PerformRuleResult performRR = apply(isApplMatch);

		graphTriple.getCorrespondences().addAll(performRR.getCreatedLinkElements());
		tempOutputContainer.getPotentialRoots().addAll(performRR.getCreatedElements().stream()
				.filter(elt -> elt.eContainer() == null).collect(Collectors.toSet()));

		TripleMatch newTripleMatch = createTripleMatch(performRR, isApplMatch);
		protocol.collectPrecedences(newTripleMatch);

		createdTripleMatchesInLastStep.add(newTripleMatch);

		return performRR;
	}

	private PerformRuleResult apply(IsApplicableMatch chosen) {
		return new InvokePerform().apply(chosen);
	}

	private IsApplicableMatch chooseOne(Collection<IsApplicableRuleResult> extended) {
		List<RuleResult> alternatives = extended.stream().filter(isApplRR -> isApplRR.isSuccess()).map(RuleResult::new)
				.collect(Collectors.toList());

		if (alternatives.size() == 1 && alternatives.get(0).isUnique())
			return alternatives.get(0).anyMatch();
		else
			return configurator.chooseOne(alternatives).anyMatch();
	}

	private Stream<Match> chooseOneMaximalSet() {

		TIntCollection candidatesForChoice = readyWithSiblingKernels.isEmpty() ? readyKernels : readyWithSiblingKernels;

		// find a match which translates something and return its siblings that are
		// kernel
		TIntIterator iterator = candidatesForChoice.iterator();
		while (iterator.hasNext()) {
			Match m = pg.intToMatch(iterator.next());
			Optional<EObject> elt = m.getCreatedHashSet().stream().findAny();
			if (elt.isPresent()) {
				return getCreatingMatchesFrom(candidatesForChoice, elt.get());
			}
		}

		// else return all kernel matches
		return Arrays.stream(candidatesForChoice.toArray()).mapToObj(id -> pg.intToMatch(id))
				.filter(amalgamationUtil::isKernelMatch);
	}

	private Stream<Match> getCreatingMatchesFrom(TIntCollection matches, EObject elt) {
		return pg.createsAsStream(elt).filter(m -> matches.contains(pg.matchToInt(m)));
	}

	private void extendReady(TIntCollection candidates) {
		TIntHashSet newReadyKernels = new TIntHashSet(candidates.size());

		candidates.forEach(cand -> {
			boolean allParentsProcessed = pg.parents(cand).forEach(p -> {
				if (inputMatches.contains(p)) {
					return false;
				}
				return true;
			});
			if (allParentsProcessed) {
				if (amalgamationUtil.isKernelMatch(pg.intToMatch(cand))) {
					readyKernels.add(cand);
					newReadyKernels.add(cand);
				} else {
					readyComplements.add(cand);
				}
			}
			return true;
		});

		if (amalgamationUtil.isAmalgamatedTGG() || configurator.handleConflicts())
			determineReadyWithSiblings(newReadyKernels);
	}

	private Collection<IsApplicableRuleResult> extendToFullMatches(Stream<Match> ready) {
		return ready.map(new InvokeIsApplicable()).collect(Collectors.toSet());
	}

	protected Graph determineInputElements(Match coreMatch) {
		return new Graph(coreMatch.getContextNodes(), coreMatch.getContextEdges())
				.addConstructive(coreMatch.getToBeTranslatedNodes())
				.addConstructive(coreMatch.getToBeTranslatedEdges());
	}

	private void updateProcessedSets(IsApplicableMatch chosen) {
		int chosenID = pg.matchToInt(coreMatchOf(chosen));
		TIntCollection siblings = pg.siblings(chosenID);
		inputMatches.removeAll(siblings);
		readyKernels.removeAll(siblings);
		readyWithSiblingKernels.removeAll(siblings);
		readyButUnreadySiblingsKernels.removeAll(siblings);
		readyComplements.removeAll(siblings);

		TIntCollection allChildren = new TIntHashSet();
		siblings.forEach(s -> {
			allChildren.addAll(pg.children(s));
			return true;
		});

		if (amalgamationUtil.isAmalgamatedTGG()) {
			TIntCollection disregardedComplements = new TIntHashSet();
			siblings.forEach(s -> {
				if (s != chosenID) {
					amalgamationUtil.determineComplements(pg.intToMatch(s), pg.getAsCollection(allChildren))
							.forEach(cm -> disregardedComplements.add(pg.matchToInt(cm)));
				}
				return true;
			});

			inputMatches.removeAll(disregardedComplements);
			readyComplements.removeAll(disregardedComplements);
			allChildren.removeAll(disregardedComplements);
		}

		allChildren.retainAll(inputMatches);
		extendReady(allChildren);

		translated = pg.getCreatedElements(coreMatchOf(chosen));
		toBeTranslated.removeDestructive(translated);
	}

	public void determineReadyWithSiblings(TIntHashSet newReady) {
		// check if some matches have become ready with siblings after the last
		// calculation
		readyButUnreadySiblingsKernels.forEach(r -> {
			if (readyWithSiblings(r))
				readyWithSiblingKernels.add(r);
			return true;
		});

		readyButUnreadySiblingsKernels.removeAll(readyWithSiblingKernels);

		// check new ready matches
		newReady.forEach(nr -> {
			if (readyWithSiblings(nr))
				readyWithSiblingKernels.add(nr);
			else
				readyButUnreadySiblingsKernels.add(nr);
			return true;
		});
	}

	private boolean readyWithSiblings(int m) {
		return pg.siblings(m).forEach(s -> {
			if (!readyKernels.contains(s) && !readyComplements.contains(s) && inputMatches.contains(s))
				return false;
			return true;
		});
	}

	private void processAmalgamationComplements(TIntCollection inputMatches, IsApplicableMatch chosen,
			PerformRuleResult chosenRR) {
		Collection<Match> allComplements = amalgamationUtil.determineComplements(coreMatchOf(chosen),
				pg.getAsCollection(pg.children(pg.matchToInt(coreMatchOf(chosen)))));
		Stream<Match> readyComplementsOfChosen = allComplements.stream()
				.filter(comp -> readyComplements.contains(pg.matchToInt(comp)));
		Stream<IsApplicableMatch> complementIsApplMatches = extendToFullMatches(readyComplementsOfChosen).stream()
				.flatMap(rr -> rr.getIsApplicableMatch().stream());

		Collection<IsApplicableMatch> disjunctComplementMatches = amalgamationUtil.determineComplements(chosenRR,
				complementIsApplMatches);
		disjunctComplementMatches.forEach(isAppl -> {
			applyAndUpdateTriple(graphTriple, isAppl);
			updateProcessedSets(isAppl);
		});

		TIntCollection allComplementsIDs = new TIntHashSet();
		allComplements.forEach(c -> allComplementsIDs.add(pg.matchToInt(c)));
		inputMatches.removeAll(allComplementsIDs);
		readyComplements.removeAll(allComplementsIDs);
	}

	private Match coreMatchOf(IsApplicableMatch m) {
		return m.getIsApplicableRuleResult().getCoreMatch();
	}

	protected void handleElementsNotTranslatedWarning(boolean verbose) {
		if (toBeTranslated.getElements().isEmpty())
			return;

		Graph warningGraph = Graph.getEmptyGraph().addConstructive(toBeTranslated.stream()
				.filter(a -> !pg.createsAsStream(a).findAny().isPresent()).collect(Collectors.toList()));

		if (warningGraph.getElements().isEmpty())
			return;

		String warning = "-------------------\n" + "I was unable to collect any matches for ";

		if (verbose)
			warning += " the following elements:\n" + warningGraph.toString() + "\n";
		else {
			warning += warningGraph.getElements().size() + " elements." + "\n"
					+ "Set verbose to true in your synchronization helper and re-run to get the exact list of ignored elements (this can slow down the transformation considerably!).\n";
		}

		warning += "If this is unexpected, you should perhaps debug the isAppropriate-methods of the rules that are supposed to match to understand what the problem is.\n";
		warning += "To get rid of this message, you could add rules to explicitly match and ignore these elements.\n";
		warning += "Note that local-completeness errors might have something to do with these missing matches!\n";
		warning += "--------------------------------";

		if (verbose)
			logger.warn(warning);
		else
			logger.debug(warning);
	}

	public SynchronizationProtocol getProtocol() {
		return protocol;
	}

	public CorrespondenceModel getGraphTriple() {
		return graphTriple;
	}

	public Graph getAllRevokedElts() {
		return allRevokedElts;
	}

	public Graph getToBeDeleted() {
		return toBeDeleted;
	}

	public Graph getAddedElts() {
		return addedElts;
	}

	public Graph getToBeTranslated() {
		return toBeTranslated;
	}

	public void setToBeTranslated(Delta delta) {
		this.toBeTranslated = new Graph(delta.getAllAddedElements());
	}

	public PrecedenceInputGraph getPrecedenceGraph() {
		return pg;
	}

	public Collection<Match> getReadySet() {
		Collection<Match> result = new ArrayList<>();
		readyKernels.forEach(r -> {
			result.add(pg.intToMatch(r));
			return true;
		});
		return result;
	}

	public Collection<EObject> getTranslated() {
		return translated;
	}

	public Collection<Match> getInputMatches() {
		return pg.getAsCollection(inputMatches);
	}

	public Collection<TripleMatch> getCreatedTripleMatch() {
		return createdTripleMatchesInLastStep;
	}

	public TempOutputContainer getTempOutputContainer() {
		return tempOutputContainer;
	}
}
