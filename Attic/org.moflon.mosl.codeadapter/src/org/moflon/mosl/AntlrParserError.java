package org.moflon.mosl;

import org.antlr.runtime.RecognitionException;


public class AntlrParserError
{
   private final int line;
   private final int positionInLine;
   private final RecognitionException recognitionException;
   private final String token;

   public AntlrParserError(RecognitionException re)
   {
      this.recognitionException = re;
      this.line = re.line;
      this.positionInLine = re.charPositionInLine;
      this.token = recognitionException.token != null? recognitionException.token.getText() : null;
   }

   public int getLine()
   {
      return line;
   }

   public int getPositionInLine()
   {
      return positionInLine;
   }
   
   public String getToken(){
      return token != null? token : " ";
   }
   
   public String getMessage()
   {
      if(token != null)
         return "I can't make any sense of this token: " + recognitionException.token.getText();
      else
         return "I'm confused and unable to continue parsing.  Please take a good look at the marked region in your text.";
   }
}
