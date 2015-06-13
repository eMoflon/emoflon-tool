package org.moflon.mosl;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;

public abstract class MOSLParser extends Parser
{

   public MOSLParser(TokenStream input, RecognizerSharedState state)
   {
      super(input, state);
   }

   public MOSLParser(TokenStream input)
   {
      super(input);
   }

   private List<AntlrParserError> errors = new ArrayList<AntlrParserError>();

   @Override
   public void reportError(RecognitionException e)
   {
      errors.add(new AntlrParserError(e, getTokenNames()));
   }

   public List<AntlrParserError> getErrors()
   {
      return errors;
   }
   
   public abstract String[] getTokenNames();
}
