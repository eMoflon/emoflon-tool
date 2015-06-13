lexer grammar SokLexer;

@members {
  public Collection<Problem> problems = new ArrayList<Problem>();

      public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
          Problem problem = MocaFactory.eINSTANCE.createProblem();
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
package org.moflon.moca.sok.parser;
import java.util.Collection;
import Moca.MocaFactory;
import Moca.Problem;
import Moca.ProblemType;
}

// Lexer rules (token definitions)
WS: ( '\t' | '\r' | '/*' .* '*/') { skip(); };

NUM: '0'..'9'+;

NEWLINE: '\r \n' | '\n' | '\r';

EMPTY : ' ';

// Defining tokens for each of our Figures
WALL : '#';

GOAL : '.';

ADMIN : '@';

SERVER : '$';

