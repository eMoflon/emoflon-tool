package org.moflon.tgg.algorithm.synchronization;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.moflon.tgg.language.analysis.Rule;
import org.moflon.tgg.language.analysis.RulesTable;
import org.moflon.tgg.runtime.IsApplicableMatch;
import org.moflon.tgg.runtime.Match;
import org.moflon.tgg.runtime.PerformRuleResult;
import org.moflon.tgg.runtime.TGGRuleMorphism;

public class AmalgamationUtil
{

   private Boolean isAmalgamatedTGG = null;

   private HashMap<Match, Collection<Match>> matchToComplements = new HashMap<>();

   public Collection<Match> determineComplements(Match kernel, Collection<Match> candidates, RulesTable lookupMethods)
   {
      Rule kernelRule = getRule(lookupMethods, kernel.getRuleName());
      if (!kernelRule.getComplements().isEmpty())
      {
         if (!matchToComplements.containsKey(kernel))

            matchToComplements.put(kernel, candidates.stream().filter(comp -> isKernelOf(kernel, comp, kernelRule)).collect(Collectors.toSet()));

         return matchToComplements.get(kernel);
      }
      return new HashSet<Match>();
   }

   public Collection<IsApplicableMatch> determineComplements(PerformRuleResult kernel, Stream<IsApplicableMatch> candidates, RulesTable lookupMethods)
   {
      return candidates.filter(comp -> isKernelOf(kernel, comp, getRule(lookupMethods, kernel.getRuleName()))).collect(Collectors.toSet());
   }

   private boolean isKernelOf(TGGRuleMorphism kernel, TGGRuleMorphism complement, Rule kernelRule)
   {
      if (kernelRule.getComplements().stream().anyMatch(comp -> comp.getRuleName().equals(complement.getRuleName())))
      {
         return kernel.getNodeMappings().keySet().stream().noneMatch(key -> complement.getObject(key) != kernel.getObject(key));
      }

      return false;
   }

   private Rule getRule(RulesTable lookupMethods, String name)
   {
      return lookupMethods.getRules().stream().filter(r -> r.getRuleName().equals(name)).findAny().get();
   }

   public boolean isAmalgamatedTGG(RulesTable lookupMethods)
   {
      if (isAmalgamatedTGG == null)
         isAmalgamatedTGG = lookupMethods.getRules().stream().anyMatch(r -> r.getKernel() != null);
      
      return isAmalgamatedTGG;
   }

}
