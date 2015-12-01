package org.moflon.tgg.algorithm.datastructures;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.runtime.EMoflonEdge;
import org.moflon.tgg.runtime.RuntimeFactory;

/**
 * Represents a set of matches and precedence dependencies between matches.
 * 
 * @author anjorin
 *
 */
public abstract class PrecedenceStructure<M>
{
   protected Collection<M> matches =  CollectionProvider.<M>getCollection();

   protected HashMap<EObject, Collection<M>> contextToMatch = new HashMap<>();
   protected HashMap<EObject, Collection<M>> createToMatch = new HashMap<>();
   
   protected HashMap<M, Collection<M>> matchToChildren = new HashMap<>();
   protected HashMap<M, Collection<M>> matchToParents = new HashMap<>();
   
   protected void calculateTables(M match)
   {
      getCreatedElements(match).forEach(elt -> addMatchToCreateTable(elt, match));
      getContextElements(match).forEach(elt -> addMatchToContextTable(elt, match));   
      
      Collection<M> children = extendChildrenTable(match);
      Collection<M> parents = extendParentsTable(match);
      
      for (M child : children)         
         matchToParents.get(child).add(match);
      
      for (M parent : parents)
         matchToChildren.get(parent).add(match);         
   }

   // -------
   
   private void addMatchToContextTable(EObject element, M match)
   {
      addMatchToTable(contextToMatch, element, match);
   }

   private void addMatchToCreateTable(EObject element, M match)
   {
      addMatchToTable(createToMatch, element, match);
   }

   private void addMatchToTable(HashMap<EObject, Collection<M>> table, EObject element, M match)
   {
      if (!table.containsKey(element))
         table.put(element, CollectionProvider.<M>getCollection());

      table.get(element).add(match);
   }
   
   // --------

   private Collection<M> extendChildrenTable(M match)
   {
      return extendTable(match, getCreatedElements(match), contextToMatch, matchToChildren);
   }

   private Collection<M> extendParentsTable(M match)
   {
      return extendTable(match, getContextElements(match), createToMatch, matchToParents);
   }

   private Collection<M> extendTable(M match, Collection<EObject> elements, HashMap<EObject, Collection<M>> eltToMatches, HashMap<M, Collection<M>> matchTable)
   {
      Collection<M> table = new HashSet<>();
      for (EObject elt : elements){
         table.addAll(getOrReturnEmpty(elt, eltToMatches));
         table.remove(match);
      }
      matchTable.put(match, table);
      return table;
   }
      
   protected Collection<M> getOrReturnEmpty(EObject elt, HashMap<EObject, Collection<M>> table)
   {
      if(table.containsKey(elt))
         return table.get(elt);
      else
         return Collections.emptySet();
   }

   // ---------
   
   public Collection<M> children(M m)
   {
      if(!matchToChildren.containsKey(m))
         return Collections.emptyList();
      return matchToChildren.get(m); 
   }

   public Collection<M> parents(M m)
   {
      if (!matchToParents.containsKey(m))
         return Collections.emptyList();
      return matchToParents.get(m);
   }
   
   // ----------

   public Stream<M> creates(Graph elements)
   {
      return elements.stream().flatMap(this::getCreatingMatches);
   }

   public Stream<M> getCreatingMatches(EObject o)
   {
      if (createToMatch.containsKey(o))
         return createToMatch.get(o).stream();
      else
         return Stream.<M> empty();
   }
   
   
   public Stream<M> getContextMatches(EObject o)
   {
      if (contextToMatch.containsKey(o))
         return contextToMatch.get(o).stream();
      else
         return Stream.<M> empty();
   }
   
   public Collection<M> getMatches()
   {
      return matches;
   }

   public abstract Collection<EObject> getContextElements(M m);

   public abstract Collection<EObject> getCreatedElements(M m);

   // ----------
   
   public org.moflon.tgg.runtime.PrecedenceStructure save()
   {
      org.moflon.tgg.runtime.PrecedenceStructure ps = RuntimeFactory.eINSTANCE.createPrecedenceStructure();

      HashMap<M, org.moflon.tgg.runtime.TripleMatch> conversionTable = convertToMatches();
      ps.getTripleMatches().addAll(conversionTable.values().stream()
            .sorted((a,b) -> a.getNumber() - b.getNumber())
            .collect(Collectors.toList())
         );
      
      matches.forEach(m -> children(m).forEach(child -> {
         if(conversionTable.containsKey(child))
            conversionTable.get(m).getChildren().add(conversionTable.get(child));  
      }));
      
      return ps;
   }

   private HashMap<M, org.moflon.tgg.runtime.TripleMatch> convertToMatches()
   {
      HashMap<M, org.moflon.tgg.runtime.TripleMatch> conversionTable = new HashMap<>();
      matches.forEach(m -> conversionTable.put(m, toEMF(m)));
      
      return conversionTable;
   }

   protected void addEdges(org.moflon.tgg.runtime.TripleMatch tripleMatch)
   {
      tripleMatch.getContextElements().forEach(elt -> {
         addIfEdge(tripleMatch, elt);
      });

      tripleMatch.getCreatedElements().forEach(elt -> {
         addIfEdge(tripleMatch, elt);
      });

      tripleMatch.getSourceElements().forEach(elt -> {
         addIfEdge(tripleMatch, elt);
      });

      tripleMatch.getTargetElements().forEach(elt -> {
         addIfEdge(tripleMatch, elt);
      });

      tripleMatch.getCorrespondenceElements().forEach(elt -> {
         addIfEdge(tripleMatch, elt);
      });
   }

   private void addIfEdge(org.moflon.tgg.runtime.TripleMatch tripleMatch, EObject elt)
   {
      if (elt instanceof EMoflonEdge)
         tripleMatch.getContainedEdges().add(elt);
   }

   public Stream<M> creates(EObject createdElt)
   {
      return getCreatingMatches(createdElt);
   }
   
   public Collection<M> createsAsCollection(EObject elt){
      return createToMatch.get(elt);
   }
   
   protected abstract org.moflon.tgg.runtime.TripleMatch toEMF(M m);

   protected abstract M fromEMF(org.moflon.tgg.runtime.TripleMatch m);
}
