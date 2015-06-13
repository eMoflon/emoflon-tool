parser grammar SokParser;

options {
  language = Java;
  tokenVocab = SokLexer;
  output = AST;
}

tokens{ 
// 'imaginary' tokens; no real input.
	ROW;
	COLUMN;
	ROWS;
	COLS;
	BOARD_SPEC;
	DIMENSIONS;
	BOARD;
}

@members {

  private int cols = 0;
  private int rows = 0;
  
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
          problem.setMessage("Parser: " + getErrorMessage(e, tokenNames));
          
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
import org.moflon.moca.MocaTokenFactory;
}


// Parser Rules:
main: (r+=row)+ 
	-> ^(BOARD_SPEC ^(DIMENSIONS ^(ROWS {MocaTokenFactory.createCommonTree("NUM", rows + "", tokenNames)})
							^(COLS {MocaTokenFactory.createCommonTree("NUM", cols/rows + "", tokenNames)}))
																								^(BOARD $r*));

row: {rows++;} (c+=column)+ (NEWLINE | EOF) -> ^(ROW $c*);

column: {cols++;} (f=figure) -> ^(COLUMN $f) | {cols++;} EMPTY -> COLUMN;

figure: (WALL | SERVER | GOAL | ADMIN | STARTWALL);