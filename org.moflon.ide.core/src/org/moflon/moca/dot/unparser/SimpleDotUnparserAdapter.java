package org.moflon.moca.dot.unparser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.moflon.core.moca.processing.unparser.impl.TemplateUnparserImpl;
import org.moflon.core.utilities.MoflonUtilitiesActivator;
import org.moflon.ide.core.CoreActivator;


public class SimpleDotUnparserAdapter extends TemplateUnparserImpl 
{
  
  @Override
  public boolean canUnparseFile(String fileName) 
  {
    return fileName.endsWith(".dot");
  }

  @Override
  protected String callMainRule(CommonTreeNodeStream tree, StringTemplateGroup templates) throws RecognitionException 
  {
    SimpleDotTreeGrammar dotTreeGrammar = new SimpleDotTreeGrammar(tree);
    dotTreeGrammar.setTemplateLib(templates);  
    StringTemplate st = dotTreeGrammar.main().st;
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
     URL templateFile = MoflonUtilitiesActivator.getPathRelToPlugIn("resources/templates/SimpleDot.stg", CoreActivator.getModuleID());

     InputStreamReader reader = null;
     try
     {
        reader = new InputStreamReader(templateFile.openStream());
     } catch (IOException e)
     {
        e.printStackTrace();
     }
     
     return new StringTemplateGroup(reader);

  }

  @Override
  protected String[] getTokenNames() 
  {
    return SimpleDotTreeGrammar.tokenNames;
  }
}