package org.moflon.gt.mosl.codeadapter.codeadapter;

import java.util.LinkedList;
import java.util.List;
import org.gervarro.democles.specification.emf.PatternBody;
import org.moflon.gt.mosl.codeadapter.linkvariablerules.LVTransformerRule;
import org.moflon.gt.mosl.moslgt.LinkVariablePattern;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;

public class LinkVariableBuilder
{

   private static LinkVariableBuilder instance;

   private List<LVTransformerRule> transformerRules;

   private LinkVariableBuilder()
   {
      transformerRules = new LinkedList<>();
   }

   public static LinkVariableBuilder getInstance()
   {
      if (instance == null)
         instance = new LinkVariableBuilder();
      return instance;
   }

   public void transformLinkVariable(LinkVariablePattern linkVar, ObjectVariableDefinition ov, PatternBody patternBody)
   {
      transformerRules.stream().forEachOrdered(transformer -> {
         transformer.transforming(linkVar, ov, patternBody);
      });
   }

   public void addTransformer(LVTransformerRule transformer)
   {
      transformerRules.add(transformer);
   }
}
