package org.moflon.mosl.sch.unparser;

import org.antlr.runtime.tree.TreeNodeStream;
import org.moflon.mosl.AbstractMOSLUnparserAdapter;
import org.moflon.mosl.MOSLTreeGrammar;

public class SchUnparserAdapter extends AbstractMOSLUnparserAdapter
{

   public SchUnparserAdapter()
   {
      this(".");
   }

   public SchUnparserAdapter(String templateDir)
   {
      super(templateDir);
   }

   @Override
   protected String[] getTokenNames()
   {
      return SchTreeGrammar.tokenNames;
   }

   @Override
   protected MOSLTreeGrammar createTreeGrammar(TreeNodeStream treeNodeStream)
   {
      return new SchTreeGrammar(treeNodeStream);
   }

   @Override
   protected String getTemplateFile()
   {
      return "templates/Sch.stg";
   }

   @Override
   public String getExtension()
   {
      return ".sch";
   }
}