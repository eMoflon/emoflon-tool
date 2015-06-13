parser grammar InjectParser;

options {
  language = Java;
  tokenVocab = InjectLexer;
  output = AST;
}

// List of tokens used only used to structure the tree
tokens {
  ROOT;
  IMPORTS;
  MEMBERS;
  MODEL;
  METHOD;
}

@header {
package org.moflon.moca.inject.parser; 
import java.util.Collection;
import Moca.MocaFactory;
import Moca.Problem;
import Moca.ProblemType;
}

@members {
  public Collection<Problem> problems = new ArrayList<Problem>();

      public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
          Problem problem = MocaFactory.eINSTANCE.createProblem();
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
          problem.setMessage("Parser: " + getErrorMessage(e, tokenNames));
          
          problems.add(problem);
          super.displayRecognitionError(tokenNames, e);
    }
}

// parser rules:
 
implMain: imports classHead CLASS_BEGIN implBody CLASS_END -> ^(ROOT imports implBody);

nonImplMain: imports classHead CLASS_BEGIN nonImplBody CLASS_END -> ^(ROOT imports nonImplBody);


classHead: PARTIAL_KEYWORD CLASS_KEYWORD STRING;


imports: (importStatement)* -> ^(IMPORTS importStatement*);

importStatement: IMPORT_KEYWORD STRING SEMICOLON -> STRING;


implBody: members? model* -> ^(MEMBERS members?) ^(MODEL model*);

nonImplBody: members? -> ^(MEMBERS members?) ^(MODEL);

// corresponds to class member
members: MEMBERS_KEYWORD CODE_BLOCK -> CODE_BLOCK;

// corresponds to class method
model:  MODEL_KEYWORD signature CODE_BLOCK -> ^(METHOD signature CODE_BLOCK);

signature: name=STRING PARAMETER_BEGIN (singleParam (COMMA singleParam)*)? PARAMETER_END -> ^($name singleParam*);

singleParam: type=STRING name=STRING -> ^($name $type);
