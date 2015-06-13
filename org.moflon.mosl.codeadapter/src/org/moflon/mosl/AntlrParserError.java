package org.moflon.mosl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.MismatchedTreeNodeException;
import org.antlr.runtime.RecognitionException;


public class AntlrParserError
{

   private final int line;
   private final int positionInLine;
   private final RecognitionException recognitionException;
   private final List<String> tokenNames;
   
   public AntlrParserError()
   {
      this.line = 0;
      this.positionInLine = 0;
      this.recognitionException = null;
      this.tokenNames = new ArrayList<String>(1);
   }
   
   public AntlrParserError(RecognitionException re) {
      this(re, new String[] {});
   }

   public AntlrParserError(RecognitionException re, String[] tokenNames)
   {
      this.recognitionException = re;
      this.line = re.line;
      this.positionInLine = re.charPositionInLine;
      this.tokenNames = Arrays.asList(tokenNames);
   }

   public int getLine()
   {
      return line;
   }

   public int getPositionInLine()
   {
      return positionInLine;
   }
   
   public String getMessage()
   {
      if (recognitionException instanceof MismatchedTokenException) {
         MismatchedTokenException mte = (MismatchedTokenException) recognitionException;
         return String.format("Mismatched token. Expected: %s, actual: %s", mte.expecting > -1 ? tokenNames.get(mte.expecting) : "<unknown>", tokenNames.get(mte.getUnexpectedType()));
      }
      if (recognitionException instanceof MismatchedTreeNodeException) {
         MismatchedTreeNodeException mte = (MismatchedTreeNodeException) recognitionException;
         return String.format("Mismatched tree node token. Expected: %s, actual: %s", mte.expecting > -1 ? tokenNames.get(mte.expecting) : "<unknown>", mte.getUnexpectedType() > -1 ? tokenNames.get(mte.getUnexpectedType()) : "<unknown>");
      }
      return recognitionException.toString();
   }
}
