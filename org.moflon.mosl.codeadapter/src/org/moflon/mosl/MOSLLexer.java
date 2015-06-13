package org.moflon.mosl;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognizerSharedState;

public abstract class MOSLLexer extends Lexer
{
   
   public MOSLLexer()
   {
      super();
   }

   public MOSLLexer(CharStream input, RecognizerSharedState state)
   {
      super(input, state);
   }

   public MOSLLexer(CharStream input)
   {
      super(input);
   }
}
