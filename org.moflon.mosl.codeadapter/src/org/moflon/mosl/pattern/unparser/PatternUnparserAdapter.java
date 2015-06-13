package org.moflon.mosl.pattern.unparser;

import org.antlr.runtime.tree.TreeNodeStream;
import org.moflon.mosl.AbstractMOSLUnparserAdapter;
import org.moflon.mosl.MOSLTreeGrammar;

public class PatternUnparserAdapter extends AbstractMOSLUnparserAdapter
{
   public PatternUnparserAdapter() {
      this(".");
   }

   public PatternUnparserAdapter(String templateDir)
   {
      super(templateDir);
   }

   @Override
   protected String[] getTokenNames()
   {
      return PatternTreeGrammar.tokenNames;
   }

   @Override
   protected MOSLTreeGrammar createTreeGrammar(TreeNodeStream treeNodeStream)
   {
      return new PatternTreeGrammar(treeNodeStream);
   }

   @Override
   protected String getTemplateFile()
   {
      return "templates/Pattern.stg";
   }

   @Override
   public String getExtension()
   {
      return ".pattern";
   }
}