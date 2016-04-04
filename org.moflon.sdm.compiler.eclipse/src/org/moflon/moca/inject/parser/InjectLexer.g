lexer grammar InjectLexer;

@header {
package org.moflon.moca.inject.parser;
import org.moflon.moca.MocaUtil;
import java.util.Collection;
import org.moflon.core.moca.processing.ProcessingFactory;
import org.moflon.core.moca.processing.Problem;
import org.moflon.core.moca.processing.ProblemType;
}

@members {
  public Collection<Problem> problems = new ArrayList<Problem>();

      public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
          Problem problem = ProcessingFactory.eINSTANCE.createProblem();
          int line  = e.line;
          int charPos = e.charPositionInLine;
          int tokenLength = 1;
          Token token = e.token;
          if(token != null) 
            tokenLength = token.getText().length();
                  
          
          problem.setType(ProblemType.ERROR);
          problem.setLine(line); 
          problem.setCharacterPositionStart(charPos); 
          problem.setCharacterPositionEnd(charPos+tokenLength);
          problem.setMessage("Lexer: " + getErrorMessage(e, tokenNames)); 
          
          problems.add(problem);
          super.displayRecognitionError(tokenNames, e);
    }
}



// Lexer Rules:

IMPORT_KEYWORD: 'import';

STATIC_KEYWORD: 'static';

CLASS_KEYWORD: 'class';

PARTIAL_KEYWORD: 'partial';

CLASS_BEGIN: '{';

MODEL_KEYWORD: '@model';
 
MEMBERS_KEYWORD: '@members';

BLOCK_BEGIN: '<--';
 
BLOCK_END: '-->';

CODE_BLOCK: BLOCK_BEGIN .* BLOCK_END {MocaUtil.trim(this, 3, 3);};

CLASS_END: '}';

DOT: '.';

SEMICOLON: ';';

WS : (' ' | '\t' | '\f' | '\n' | '\r')+ {skip();};

PARAMETER_BEGIN: '(';

PARAMETER_END: ')';

COMMA: ',';

// Common token definitions
STRING: ('_' | ('a'..'z') | ('A'..'Z') | ('0'..'9') | DOT | '*')+;
