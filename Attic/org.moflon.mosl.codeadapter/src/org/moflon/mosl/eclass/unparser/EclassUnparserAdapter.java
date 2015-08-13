package org.moflon.mosl.eclass.unparser;

import org.antlr.runtime.tree.TreeNodeStream;
import org.moflon.mosl.AbstractMOSLUnparserAdapter;
import org.moflon.mosl.MOSLTreeGrammar;

public class EclassUnparserAdapter extends AbstractMOSLUnparserAdapter {
   public EclassUnparserAdapter()
   {
      this(".");
   }
   
	public EclassUnparserAdapter(String templateDir)
   {
      super(templateDir);
   }
	
	@Override
	public String getExtension()
	{
	   return ".eclass";
	}

	@Override
	protected String getTemplateFile()
	{
	   return "templates/Eclass.stg";
	}

	@Override
	protected String[] getTokenNames() {
		return EclassTreeGrammar.tokenNames;
	}

   @Override
   protected MOSLTreeGrammar createTreeGrammar(TreeNodeStream treeNodeStream)
   {
      return new EclassTreeGrammar(treeNodeStream);
   }
}