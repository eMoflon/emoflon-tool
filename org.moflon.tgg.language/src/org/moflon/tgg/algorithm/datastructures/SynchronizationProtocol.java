package org.moflon.tgg.algorithm.datastructures;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.moflon.tgg.runtime.TripleMatchNodeMapping;

/**
 * Used to represent the set of triple matches and their precedence dependencies used for a synchronization.
 * 
 * @author anjorin
 *
 */
public class SynchronizationProtocol extends PrecedenceStructure<TripleMatch>
{
   public Collection<TripleMatch> descendants(TripleMatch m)
   {
      Collection<TripleMatch> desc = new HashSet<TripleMatch>(children(m));
      
      for (TripleMatch child : children(m))
         desc.addAll(descendants(child));

      return desc;
   }
   
   public Collection<TripleMatch> ancestors(TripleMatch m)
   {
      Collection<TripleMatch> asc = new HashSet<TripleMatch>(parents(m));
      
      for(TripleMatch parent : parents(m))
         asc.addAll(ancestors(parent));
      
      return asc;
   }

   
   public void collectPrecedences(TripleMatch match){
      matches.add(match);
      calculateTables(match); 
   }
   
   // -------
   
   public void revoke(Collection<TripleMatch> allToBeRevoked)
   {
      matches.removeAll(allToBeRevoked);

      createToMatch.keySet().stream().filter(key -> allToBeRevoked.containsAll(createToMatch.get(key)))
                                     .collect(Collectors.toSet())
                                     .forEach(key -> createToMatch.remove(key));

      contextToMatch.values().forEach(matches -> matches.removeAll(allToBeRevoked));
      
      allToBeRevoked.forEach(match -> {
         matchToChildren.get(match).forEach(child -> matchToParents.get(child).remove(match));
         matchToParents.get(match).forEach(parent -> matchToChildren.get(parent).remove(match));
         matchToChildren.remove(match);
         matchToParents.remove(match);
      });
   }

   // -------
   
   @Override
   public Collection<EObject> getContextElements(TripleMatch m)
   {
      return m.getContextElements().getElements();
   }

   @Override
   public Collection<EObject> getCreatedElements(TripleMatch m)
   {
      return m.getCreatedElements().getElements();
   }

   // -------
   
   public void load(org.moflon.tgg.runtime.PrecedenceStructure ps)
   {
      ps.getTripleMatches().forEach(elt -> collectPrecedences(fromEMF(elt)));
   }
   
   @Override
   public org.moflon.tgg.runtime.TripleMatch toEMF(TripleMatch m)
   {
      org.moflon.tgg.runtime.TripleMatch tripleMatch = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE.createTripleMatch();

      tripleMatch.getSourceElements().addAll(m.getSourceElements().getElements());
      tripleMatch.getTargetElements().addAll(m.getTargetElements().getElements());
      tripleMatch.getCorrespondenceElements().addAll(m.getCorrespondenceElements().getElements());

      tripleMatch.getContextElements().addAll(m.getContextElements().getElements());
      tripleMatch.getCreatedElements().addAll(m.getCreatedElements().getElements());

      addEdges(tripleMatch);

      tripleMatch.setRuleName(m.getRuleName());
      tripleMatch.setNumber(m.getID());

      m.getNodeMappings().keySet().stream().forEach(nodeName ->{
         TripleMatchNodeMapping nodeMapping = org.moflon.tgg.runtime.RuntimeFactory.eINSTANCE.createTripleMatchNodeMapping();
         nodeMapping.setNodeName(nodeName);
         nodeMapping.setNode(m.getNode(nodeName));
         tripleMatch.getNodeMappings().add(nodeMapping);});
   
      return tripleMatch;
   }

   @Override
   protected TripleMatch fromEMF(org.moflon.tgg.runtime.TripleMatch m)
   {
      HashMap<String, EObject> nodeMappings = new HashMap<>();
      m.getNodeMappings().stream().forEach(nm -> nodeMappings.put(nm.getNodeName(), nm.getNode()));
      
      return new TripleMatch(m.getRuleName(), 
             new Graph(m.getSourceElements()), 
             new Graph(m.getTargetElements()), 
             new Graph(m.getCorrespondenceElements()),
             new Graph(m.getCreatedElements()),
             new Graph(m.getContextElements()),
             nodeMappings); 
   }
}
