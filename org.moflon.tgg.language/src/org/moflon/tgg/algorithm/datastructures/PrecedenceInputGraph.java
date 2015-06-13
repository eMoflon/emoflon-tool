package org.moflon.tgg.algorithm.datastructures;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;

import TGGRuntime.EMoflonEdge;
import TGGRuntime.Match;
import TGGRuntime.TGGRuntimeFactory;

/**
 * An on-the-fly collection of source/target matches and their precedence dependencies created and used during
 * synchronization.
 * 
 * @author anjorin
 *
 */
public class PrecedenceInputGraph extends PrecedenceStructure<Match>
{
   protected HashMap<Match, Collection<Match>> matchToSiblings = new HashMap<>();
   
   public void collectAllPrecedences(Collection<Match> inputMatches)
   {
      matches.addAll(inputMatches);
      
      for (Match match : matches)
         calculateTables(match);
   }
   
   @Override
   protected void calculateTables(Match match)
   {
      super.calculateTables(match);
      extendSiblingsTable(match);
   }
   
   private void extendSiblingsTable(Match match)
   {
      Collection<Match> siblings = new HashSet<>();
      getCreatedElements(match).forEach(elt -> siblings.addAll(getOrReturnEmpty(elt, createToMatch)));
      matchToSiblings.put(match, siblings);
      
      for (Match elt : siblings)         
         matchToSiblings.get(elt).add(match);
   }
   
   public Collection<Match> siblings(Match m){
      return matchToSiblings.get(m);
   }
   
   public void remove(Match m){
      matches.remove(m);
      
      matchToSiblings.get(m).forEach(sibling -> { if(sibling != m) matchToSiblings.get(sibling).remove(m); });
      matchToSiblings.remove(m);
      
      matchToChildren.get(m).forEach(child -> matchToParents.get(child).remove(m));
      matchToChildren.remove(m);
      
      matchToParents.get(m).forEach(parent -> matchToChildren.get(parent).remove(m));
      matchToParents.remove(m);
      
      contextToMatch.values().forEach(contextMatches -> contextMatches.remove(m));
      createToMatch.values().forEach(createMatches -> createMatches.remove(m));
   }
   
   // --------
   
   @Override
   public Collection<EObject> getContextElements(Match m)
   {
      return m.getContextHashSet();
   }

   @Override
   public Collection<EObject> getCreatedElements(Match m)
   {
      return m.getCreatedHashSet();
   }

   // ---------
   
   @Override
   protected TGGRuntime.TripleMatch toEMF(Match m)
   {
      TGGRuntime.TripleMatch tripleMatch = TGGRuntimeFactory.eINSTANCE.createTripleMatch();

      tripleMatch.getSourceElements().addAll(m.getContextNodes());
      tripleMatch.getSourceElements().addAll(m.getContextEdges());
      tripleMatch.getSourceElements().addAll(m.getToBeTranslatedNodes());
      tripleMatch.getSourceElements().addAll(m.getToBeTranslatedEdges());

      tripleMatch.getContextElements().addAll(m.getContextNodes());
      tripleMatch.getContextElements().addAll(m.getContextEdges());

      tripleMatch.getCreatedElements().addAll(m.getToBeTranslatedNodes());
      tripleMatch.getCreatedElements().addAll(m.getToBeTranslatedEdges());

      addEdges(tripleMatch);

      tripleMatch.setRuleName(m.getRuleName());
      return tripleMatch;
   }

   @Override
   protected Match fromEMF(TGGRuntime.TripleMatch tripleMatch)
   {
      Match match = TGGRuntimeFactory.eINSTANCE.createMatch();

      tripleMatch.getContextElements().forEach(elt -> {
         if (elt instanceof EMoflonEdge)
            match.getContextEdges().add((EMoflonEdge) elt);
         else
            match.getContextNodes().add(elt);
      });

      tripleMatch.getCreatedElements().forEach(elt -> {
         if (elt instanceof EMoflonEdge)
            match.getToBeTranslatedEdges().add((EMoflonEdge) elt);
         else
            match.getToBeTranslatedNodes().add(elt);
      });

      return match;
   }
}
