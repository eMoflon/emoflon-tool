package org.moflon.mosl.mconf.unparser;

import org.antlr.runtime.tree.TreeNodeStream;
import org.moflon.mosl.AbstractMOSLUnparserAdapter;
import org.moflon.mosl.MOSLTreeGrammar;

public class MconfUnparserAdapter extends AbstractMOSLUnparserAdapter
{
   public MconfUnparserAdapter()
   {
      this(".");
   }

   public MconfUnparserAdapter(String templateDir)
   {
      super(templateDir);
   }

   @Override
   protected String[] getTokenNames()
   {
      return MconfTreeGrammar.tokenNames;
   }

   @Override
   protected MOSLTreeGrammar createTreeGrammar(TreeNodeStream treeNodeStream)
   {
      return new MconfTreeGrammar(treeNodeStream);
   }

   @Override
   protected String getTemplateFile()
   {
      return "templates/Mconf.stg";
   }

   @Override
   public String getExtension()
   {
      return ".mconf";
   }
}