package org.moflon.tgg.algorithm.synchronization;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
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

/**
 *
 * Contains the primary methods for TGG-based synchronization.
 * 
 * @author anjorin
 *
 */
public abstract class Synchronizer
{
   private SynchronizationProtocol protocol;

   private Configurator configurator;

   protected TempOutputContainer tempOutputContainer;

   private CorrespondenceModel graphTriple;

   private Graph toBeTranslated;

   private Graph toBeDeleted;

   protected RulesTable lookupMethods;

   private Collection<Match> inputMatches;

   private Collection<Match> readySet;

   private Collection<Match> readyWithSiblings;

   private Collection<Match> readyButUnreadySiblings;

   private PrecedenceInputGraph pg;

   private AmalgamationUtil amalgamationUtil;

   private Collection<AttributeDelta> toBeChanged;

   private Logger logger = Logger.getLogger(Synchronizer.class);

   // Debugger relevant fields
   private Graph allRevokedElts;

   private Graph addedElts;

   private Collection<EObject> translated;

   private Collection<TripleMatch> createdTripleMatchesInLastStep;

   public Synchronizer(CorrespondenceModel graphTriple, Delta delta, SynchronizationProtocol protocol, Configurator configurator, StaticAnalysis rules,
         TempOutputContainer tempOutputContainer)
   {
      this.protocol = protocol;
      this.configurator = configurator;
      this.tempOutputContainer = tempOutputContainer;
      this.graphTriple = graphTriple;
      this.toBeTranslated = new Graph(delta.getAllAddedElements());
      this.toBeDeleted = new Graph(delta.getAllDeletedElements());
      this.toBeChanged = delta.getAttributeChanges();
      this.pg = new PrecedenceInputGraph();
      this.amalgamationUtil = new AmalgamationUtil();
      this.readySet = new HashSet<>();
      this.readyWithSiblings = new HashSet<>();
      this.readyButUnreadySiblings = new HashSet<>();
      this.createdTripleMatchesInLastStep = CollectionProvider.<TripleMatch> getCollection();
   }

   public void synchronize() throws InputLocalCompletenessException, TranslationLocalCompletenessException
   {
      handleDeletions();

      handleAdditions();

      handleAttributeChanges();

      translate();
   }

   private void handleDeletions()
   {
      allRevokedElts = revoke(toBeDeleted);
      toBeTranslated.addConstructive(allRevokedElts);
      toBeTranslated.removeDestructive(toBeDeleted);
      allRevokedElts = null;
   }

   private void handleAdditions()
   {
      Stream<TripleMatch> tripleMatchesWithDECviolation = getTripleMatchesWithDECviolation(toBeTranslated);
      allRevokedElts = revokeTripleMatches(tripleMatchesWithDECviolation);
      toBeTranslated.addConstructive(allRevokedElts);

      inputMatches = collectDerivations(toBeTranslated, lookupMethods);
      allRevokedElts = null;
   }

   private Stream<TripleMatch> getTripleMatchesWithDECviolation(Graph graph)
   {
      Stream<TripleMatch> tripleMatchesOfTouchedNodes = graph.getEdges().stream()
            .flatMap(e -> Stream.concat(protocol.creates(e.getTrg()), protocol.creates(e.getSrc())));

      InvokeCheckDEC invokeCheckDEC = new InvokeCheckDEC(lookupMethods);

      return tripleMatchesOfTouchedNodes.filter(invokeCheckDEC.negate());
   }

   private void handleAttributeChanges()
   {
      Collection<TripleMatch> toBeChecked = new HashSet<>();
      Graph allRevokedElts = Graph.getEmptyGraph();

      toBeChanged.forEach(attrDelta -> toBeChecked.addAll(collectMatches(attrDelta.getAffectedNode())));

      while (!toBeChecked.isEmpty())
      {
         TripleMatch tMatch = chooseNext(toBeChecked);
         AttributeConstraintsRuleResult acRuleResult = checkCSP(tMatch);

         if (acRuleResult.isSuccess())
         {
            if (acRuleResult.isRequiredChange())
            {
               Collection<TripleMatch> candidates = protocol.children(tMatch);
               toBeChecked.addAll(candidates);
            }
         } else
         {
            Graph createdElts = tMatch.getCreatedElements();
            Graph changesRevoked = revoke(createdElts);
            toBeTranslated.addConstructive(changesRevoked);
            allRevokedElts.addConstructive(changesRevoked);
         }

         toBeChecked.remove(tMatch);
      }

      inputMatches.addAll(collectDerivations(allRevokedElts, lookupMethods));
   }

   private TripleMatch chooseNext(Collection<TripleMatch> toBeChecked)
   {
      for (TripleMatch m : toBeChecked)
      {
         if (protocol.ancestors(m).stream().noneMatch(ascendant -> toBeChecked.contains(ascendant)))
            return m;
      }

      throw new IllegalStateException("No next candidate match found in toBeChecked!");
   }

   /**
    * Collect all matches that have to be considered due to changing attributes of node. Note that if node has already
    * been revoked, the protocol will return neither creating nor context matches for it.
    * 
    * @param node
    * @param toBeChecked
    * @return
    */
   private Collection<TripleMatch> collectMatches(EObject node)
   {
      Stream<TripleMatch> creatingMatches = protocol.getCreatingMatches(node);
      Stream<TripleMatch> contextMatches = protocol.getContextMatches(node);

      return Stream.concat(creatingMatches, contextMatches).collect(Collectors.toList());
   }

   private Rule findRule(String ruleName)
   {
      for (Rule rule : this.lookupMethods.getRules())
      {
         if (rule.getRuleName().equals(ruleName))
            return rule;
      }

      return null;
   }

   private AttributeConstraintsRuleResult checkCSP(TripleMatch match)
   {
      EClass ruleClass = findRule(match.getRuleName()).getIsAppropriateMethods().get(0).getEContainingClass();

      for (EOperation o : ruleClass.getEAllOperations())
      {
         if (("checkAttributes_" + getDirection()).equals(o.getName()))
         {
            org.moflon.tgg.runtime.TripleMatch tmatch = protocol.toEMF(match);
            return new InvokeCheckAttributes(ruleClass, o).apply(tmatch);
         }
      }

      throw new UnsupportedOperationException();
   }

   private Collection<Match> collectDerivations(Graph elements, RulesTable lookupMethods)
   {
      return elements.stream().flatMap(new InvokeIsAppropriate(lookupMethods)).collect(Collectors.toSet());
   }

   private Graph revoke(Graph elts)
   {
      Stream<TripleMatch> toBeRevokedTripleMatches = protocol.creates(elts);
      return revokeTripleMatches(toBeRevokedTripleMatches);
   }

   private Graph revokeTripleMatches(Stream<TripleMatch> tripleMatches)
   {
      Collection<TripleMatch> toBeRevokedTripleMatches = new HashSet<>();
      tripleMatches.forEach(m -> {
         toBeRevokedTripleMatches.addAll(protocol.descendants(m));
         toBeRevokedTripleMatches.add(m);
      });
      protocol.revoke(toBeRevokedTripleMatches);
      return delete(toBeRevokedTripleMatches);
   }

   protected abstract Graph delete(Collection<TripleMatch> allToBeRevokedTripleMatches);

   protected abstract String getDirection();

   protected TripleMatch createTripleMatch(PerformRuleResult performRR, IsApplicableMatch isApplMatch)
   {
      Graph created = new Graph(performRR.getCreatedElements(), performRR.getCreatedEdges()).addConstructive(performRR.getTranslatedElements())
            .addConstructive(performRR.getTranslatedEdges()).addConstructive(performRR.getCreatedLinkElements());

      Graph context = new Graph(isApplMatch.getAllContextElements()).removeDestructive(created);

      Graph all = created.add(context);

      Graph corr = new Graph(all.stream().filter(this::isCorrespondenceElt).collect(Collectors.toSet()));

      Graph source = determineSourceElements(coreMatchOf(isApplMatch), all, corr);
      Graph target = determineTargetElements(coreMatchOf(isApplMatch), all, corr);

      return new TripleMatch(isApplMatch.getIsApplicableRuleResult().getRule(), source, target, corr, created, context, performRR.getNodeMappings());
   }

   protected abstract Graph determineSourceElements(Match coreMatch, Graph all, Graph corr);

   protected abstract Graph determineTargetElements(Match coreMatch, Graph all, Graph corr);

   private boolean isCorrespondenceElt(EObject elt)
   {
      if (elt instanceof EMoflonEdge)
      {
         EMoflonEdge edge = (EMoflonEdge) elt;
         return edge.getSrc() instanceof AbstractCorrespondence;
      }

      return elt instanceof AbstractCorrespondence;
   }

   private void translate() throws InputLocalCompletenessException, TranslationLocalCompletenessException
   {
      pg.collectAllPrecedences(inputMatches);

      configurator.inspectPG(pg);
      inputMatches = pg.getMatches();

      extendReady(inputMatches);

      while (!inputMatches.isEmpty())
      {
         if (readySet.isEmpty())
            throw new InputLocalCompletenessException(inputMatches);

         Stream<Match> maximalSet = chooseOneMaximalSet();
         Collection<IsApplicableRuleResult> extended = extendToFullMatches(maximalSet);

         if (!extended.stream().anyMatch(isApplRR -> isApplRR.isSuccess()))
            throw new TranslationLocalCompletenessException(extended);

         IsApplicableMatch chosen = chooseOne(extended);

         PerformRuleResult chosenRR = applyAndUpdateTriple(graphTriple, chosen);

         updateProcessedSets(chosen);

         if (amalgamationUtil.isAmalgamatedTGG(lookupMethods))
            processAmalgamationComplements(inputMatches, chosen, chosenRR);

         translated = null;
         createdTripleMatchesInLastStep = CollectionProvider.<TripleMatch> getCollection();
      }

      finalizeGraphTriple(graphTriple);
   }

   protected abstract void finalizeGraphTriple(CorrespondenceModel graphTriple);

   private PerformRuleResult applyAndUpdateTriple(CorrespondenceModel graphTriple, IsApplicableMatch isApplMatch)
   {
      PerformRuleResult performRR = apply(isApplMatch);

      graphTriple.getCorrespondences().addAll(performRR.getCreatedLinkElements());
      tempOutputContainer.getPotentialRoots()
            .addAll(performRR.getCreatedElements().stream().filter(elt -> elt.eContainer() == null).collect(Collectors.toSet()));

      TripleMatch newTripleMatch = createTripleMatch(performRR, isApplMatch);
      protocol.collectPrecedences(newTripleMatch);

      createdTripleMatchesInLastStep.add(newTripleMatch);

      return performRR;
   }

   private PerformRuleResult apply(IsApplicableMatch chosen)
   {
      return new InvokePerform().apply(chosen);
   }

   private IsApplicableMatch chooseOne(Collection<IsApplicableRuleResult> extended)
   {
      List<RuleResult> alternatives = extended.stream().filter(isApplRR -> isApplRR.isSuccess()).map(RuleResult::new).collect(Collectors.toList());

      if (alternatives.size() == 1)
         return alternatives.get(0).anyMatch();
      else
         return configurator.chooseOne(alternatives).anyMatch();
   }

   private Stream<Match> chooseOneMaximalSet()
   {
      Collection<Match> candidatesForChoice = readyWithSiblings.isEmpty() ? readySet : readyWithSiblings;

      return reduceToMatchesForSameCreatedElement(candidatesForChoice);
   }

   private Stream<Match> reduceToMatchesForSameCreatedElement(Collection<Match> candidatesForChoice)
   {
      try
      {
         Match m = candidatesForChoice.stream().findAny().get();
         EObject elt = m.getCreatedHashSet().stream().findAny().get();
         return getCreatingMatchesFrom(candidatesForChoice, elt);
      } catch (NoSuchElementException e)
      {
         return Stream.empty();
      }
   }

   private Stream<Match> getCreatingMatchesFrom(Collection<Match> matches, EObject elt)
   {
      return pg.creates(elt).filter(m -> matches.contains(m));
   }

   private void extendReady(Collection<Match> candidates)
   {
      Collection<Match> newReady = CollectionProvider.<Match> getCollection(candidates.size());
      for (Match cand : candidates)
      {
         boolean parentNotProcessed = false;
         for (Match p : pg.parents(cand))
         {
            if (inputMatches.contains(p))
            {
               parentNotProcessed = true;
               break;
            }
         }
         if (!parentNotProcessed)
            newReady.add(cand);
      }

      readySet.addAll(newReady);

      if (amalgamationUtil.isAmalgamatedTGG(lookupMethods) || configurator.handleConflicts())
         determineReadyWithSiblings(newReady);
   }

   private Collection<IsApplicableRuleResult> extendToFullMatches(Stream<Match> ready)
   {
      return ready.map(new InvokeIsApplicable()).collect(Collectors.toSet());
   }

   protected Graph determineInputElements(Match coreMatch)
   {
      return new Graph(coreMatch.getContextNodes(), coreMatch.getContextEdges()).addConstructive(coreMatch.getToBeTranslatedNodes())
            .addConstructive(coreMatch.getToBeTranslatedEdges());
   }

   private void updateProcessedSets(IsApplicableMatch chosen)
   {
      Collection<Match> siblings = pg.siblings(coreMatchOf(chosen));

      inputMatches.removeAll(siblings);
      readySet.removeAll(siblings);
      readyWithSiblings.removeAll(siblings);
      readyButUnreadySiblings.removeAll(siblings);

      Collection<Match> allChildren = siblings.stream().flatMap(s -> pg.children(s).stream()).filter(c -> inputMatches.contains(c)).collect(Collectors.toSet());

      if (amalgamationUtil.isAmalgamatedTGG(lookupMethods))
      {
         Collection<Match> disregardedComplements = siblings.stream().filter(s -> s != coreMatchOf(chosen))
               .flatMap(s -> amalgamationUtil.determineComplements(s, allChildren, lookupMethods).stream()).collect(Collectors.toSet());

         inputMatches.removeAll(disregardedComplements);
         allChildren.removeAll(disregardedComplements);
      }

      extendReady(allChildren);

      translated = pg.getCreatedElements(coreMatchOf(chosen));
      toBeTranslated.removeDestructive(translated);
   }

   public void determineReadyWithSiblings(Collection<Match> newReady)
   {
      // check if some matches have become ready with siblings after the last calculation
      for (Match m : readyButUnreadySiblings)
         if (readyWithSiblings(m))
            readyWithSiblings.add(m);

      readyButUnreadySiblings.removeAll(readyWithSiblings);

      // check new ready matches
      for (Match m : newReady)
         if (readyWithSiblings(m))
            readyWithSiblings.add(m);
         else
            readyButUnreadySiblings.add(m);
   }

   private boolean readyWithSiblings(Match m)
   {
      for (Match sibling : pg.siblings(m))
         if (!readySet.contains(sibling) && inputMatches.contains(sibling))
            return false;

      return true;
   }

   private void processAmalgamationComplements(Collection<Match> inputMatches, IsApplicableMatch chosen, PerformRuleResult chosenRR)
   {
      Collection<Match> allComplements = amalgamationUtil.determineComplements(coreMatchOf(chosen), pg.children(coreMatchOf(chosen)), lookupMethods);
      Stream<Match> readyComplements = allComplements.stream().filter(comp -> readySet.contains(comp));
      Stream<IsApplicableMatch> complementIsApplMatches = extendToFullMatches(readyComplements).stream().flatMap(rr -> rr.getIsApplicableMatch().stream());

      Collection<IsApplicableMatch> disjunctComplementMatches = amalgamationUtil.determineComplements(chosenRR, complementIsApplMatches, lookupMethods);
      disjunctComplementMatches.forEach(isAppl -> {
         applyAndUpdateTriple(graphTriple, isAppl);
         updateProcessedSets(isAppl);
      });

      inputMatches.removeAll(allComplements);
      readySet.removeAll(allComplements);
      readyWithSiblings.removeAll(allComplements);
      readyButUnreadySiblings.removeAll(allComplements);
   }

   private Match coreMatchOf(IsApplicableMatch m)
   {
      return m.getIsApplicableRuleResult().getCoreMatch();
   }

   protected void handleElementsNotTranslatedWarning(boolean verbose)
   {
      if (toBeTranslated.getElements().isEmpty())
         return;

      Graph warningGraph = Graph.getEmptyGraph()
            .addConstructive(toBeTranslated.stream().filter(a -> !getCreatingMatchesFrom(inputMatches, a).iterator().hasNext()).collect(Collectors.toList()));

      if (warningGraph.getElements().isEmpty())
         return;

      String warning = "-------------------\n" + "I was unable to collect any matches for ";

      if (verbose)
         warning += " the following elements:\n" + warningGraph.toString() + "\n";
      else
      {
         warning += warningGraph.getElements().size() + " elements." + "\n"
               + "Set verbose to true in your synchronization helper and re-run to get the exact list of ignored elements (this can slow down the transformation considerably!).\n";
      }

      warning += "If this is unexpected, you should perhaps debug the isAppropriate-methods of the rules that are supposed to match to understand what the problem is.\n";
      warning += "To get rid of this message, you could add rules to explicitly match and ignore these elements.\n";
      warning += "Note that local-completeness errors might have something to do with these missing matches!\n";
      warning += "--------------------------------";

      logger.warn(warning);
   }

   public SynchronizationProtocol getProtocol()
   {
      return protocol;
   }

   public CorrespondenceModel getGraphTriple()
   {
      return graphTriple;
   }

   public Graph getAllRevokedElts()
   {
      return allRevokedElts;
   }

   public Graph getToBeDeleted()
   {
      return toBeDeleted;
   }

   public Graph getAddedElts()
   {
      return addedElts;
   }

   public Graph getToBeTranslated()
   {
      return toBeTranslated;
   }

   public PrecedenceInputGraph getPrecedenceGraph()
   {
      return pg;
   }

   public Collection<Match> getReadySet()
   {
      return readySet;
   }

   public Collection<EObject> getTranslated()
   {
      return translated;
   }

   public Collection<Match> getInputMatches()
   {
      return inputMatches;
   }

   public Collection<TripleMatch> getCreatedTripleMatch()
   {
      return createdTripleMatchesInLastStep;
   }

   public TempOutputContainer getTempOutputContainer()
   {
      return tempOutputContainer;
   }
}
