package org.moflon.moca.sok.unparser;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import Moca.unparser.impl.TemplateUnparserImpl;

public class SokUnparserAdapter extends TemplateUnparserImpl 
{
  
  @Override
  public boolean canUnparseFile(String fileName) 
  {
    return fileName.endsWith(".sok");
  }

  @Override
  protected String callMainRule(CommonTreeNodeStream tree, StringTemplateGroup templates) throws RecognitionException 
  {
    SokTreeGrammar sokTreeGrammar = new SokTreeGrammar(tree);
    sokTreeGrammar.setTemplateLib(templates);  
    StringTemplate st = sokTreeGrammar.main().st;
    if (st==null) {
      return "";
    }
    else {
      return st.toString();
    }  
  }

  @Override
  protected StringTemplateGroup getStringTemplateGroup() throws FileNotFoundException 
  {
    //TODO provide StringTemplateGroup ...
    // ... from folder "sok" containing .st files
    // return new StringTemplateGroup("sok", "templates/sok");
    // ... from group file Sok.stg
    // return new StringTemplateGroup(new FileReader(new File("./templates/Sok.stg")));
    throw new UnsupportedOperationException("Creation of StringTemplateGroup not implemented yet ...");
  }

  @Override
  protected String[] getTokenNames() 
  {
    return SokTreeGrammar.tokenNames;
  }
}