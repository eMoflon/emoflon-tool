lexer grammar LiteralExpressionLexer;

@members {
  public Collection<Problem> problems = new ArrayList<Problem>();

      public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
          Problem problem = ProcessingFactory.eINSTANCE.createProblem();
          int line  = e.line;
          int charPos = e.charPositionInLine;
          int tokenLenght = 1;
          Token token = e.token;
          if(token != null)
            tokenLenght = token.getText().length();


          problem.setType(ProblemType.ERROR);
          problem.setLine(line);
          problem.setCharacterPositionStart(charPos);
          problem.setCharacterPositionEnd(charPos+tokenLenght);
          problem.setMessage("Lexer: " + getErrorMessage(e, tokenNames));

          problems.add(problem);
          super.displayRecognitionError(tokenNames, e);
    }
}

@header {
package org.moflon.moca.literalExpression.parser;
import java.util.Collection;
import org.moflon.core.moca.processing.ProcessingFactory;
import org.moflon.core.moca.processing.Problem;
import org.moflon.core.moca.processing.ProblemType;
}

// Common Token Definitions

WS : (' ')+ {skip();};
// COMMENT: '//' .* '\n' {skip();};

//Operation
QUOTE:'"';
COLON:':';
SLASH:'/';
UNDERSCORE:'_';
DOT:'.';
OP:'+';
LPAR:'(';
RPAR:')';
TRUE:'true';
FALSE:'false';
NULL:'null';
FLOAT_SUFFIX_SMALL:'f';
FLOAT_SUFFIX_LARGE:'F';

fragment
SMALL_LETTER: ('a'..'z');
fragment
CAPITAL_LETTER: ('A'..'Z');
fragment
DIGIT: ('0'..'9');
fragment
LETTER: SMALL_LETTER | CAPITAL_LETTER;
fragment
ID: LETTER | DIGIT | UNDERSCORE;

STRING : QUOTE (~('\r'|'\n'|QUOTE)| '\\' QUOTE)* QUOTE;
ENUM_LITERAL: CAPITAL_LETTER (CAPITAL_LETTER | UNDERSCORE | DIGIT)+;
SMALL_ID: SMALL_LETTER ID*;
CAPITAL_ID: CAPITAL_LETTER ID*;
PNUMBER: ('+'|'-')? DIGIT+ (DOT DIGIT+)? (FLOAT_SUFFIX_SMALL|FLOAT_SUFFIX_LARGE)?;