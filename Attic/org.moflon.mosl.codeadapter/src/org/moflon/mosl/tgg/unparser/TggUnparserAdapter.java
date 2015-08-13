package org.moflon.mosl.tgg.unparser;

import org.antlr.runtime.tree.TreeNodeStream;
import org.moflon.mosl.AbstractMOSLUnparserAdapter;
import org.moflon.mosl.MOSLTreeGrammar;

public class TggUnparserAdapter extends AbstractMOSLUnparserAdapter
{

   public TggUnparserAdapter()
   {
      this(".");
   }

   public TggUnparserAdapter(String templateDir)
   {
      super(templateDir);
   }

   @Override
   protected String[] getTokenNames()
   {
      return TggTreeGrammar.tokenNames;
   }

   @Override
   protected MOSLTreeGrammar createTreeGrammar(TreeNodeStream treeNodeStream)
   {
      return new TggTreeGrammar(treeNodeStream);
   }

   @Override
   protected String getTemplateFile()
   {
      return "templates/Tgg.stg";
   }

   @Override
   public String getExtension()
   {
      return ".tgg";
   }
}