lexer grammar AttributeConstraintLexer;

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
package org.moflon.moca.AttributeConstraint.parser;
import java.util.Collection;
import org.moflon.core.moca.processing.ProcessingFactory;
import org.moflon.core.moca.processing.Problem;
import org.moflon.core.moca.processing.ProblemType;
}

WS : (' '|'\n' | '\r')+ {skip();};
SL_COMMENT: ('//' (~'\n')* '\n') {skip();}; 

//Operation

fragment
BACKSLASH: '\\'; 
fragment
QUOTE:'"';
fragment
UNDERSCORE:'_';
fragment 
SMALL_LETTER: ('a'..'z');
fragment
CAPITAL_LETTER: ('A'..'Z');
fragment
DIGIT: ('0'..'9');
fragment
LETTER: SMALL_LETTER | CAPITAL_LETTER;
fragment
OP: ('-' |  '*' | '+' | '=' | '!=' | '<' | '>' | '/' |'?') ;

IMPORT : 'importPackage';

STRING
 : QUOTE (~('\r'|'\n'|QUOTE)| BACKSLASH QUOTE)* QUOTE  {
     String s = getText();
     s = s.substring(1, s.length() - 1); // strip the leading and trailing quotes
     setText(s);
   }; 

NUMBER: ('+'|'-')? DIGIT+ (DOT DIGIT+)?;

BOOLEAN : 'true' | 'TRUE' | 'false' | 'FALSE' ;
 
ID  : (LETTER | UNDERSCORE) (LETTER | DIGIT | UNDERSCORE)*;

OP_ID : (OP | LETTER)+ ; 
  
DOT: '.';

COLON: ':' ;

LPAR: '(' ;

SINGLEQUOTE: '\'' ;

RPAR: ')' ;
 
SEP: ',' ;

SEMICOLON: ';' ;


