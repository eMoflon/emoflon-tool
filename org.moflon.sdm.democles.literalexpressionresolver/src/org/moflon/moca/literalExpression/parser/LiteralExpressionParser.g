parser grammar LiteralExpressionParser;

options {
  language = Java;
  tokenVocab = LiteralExpressionLexer;
  output = AST;
}

// List of tokens used to structure the tree
tokens {
FIELD;
LITERAL;
// GETTERCALL;
ENUMLITERAL;
OPERATION;
CONSTANT;
NUMBER;


}
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
          problem.setMessage("Parser: " + getErrorMessage(e, tokenNames));
          
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

// Parser Rules:
main: ex1=operatorExpression (OP ex2+=operatorExpression)+ -> ^(OPERATION ^(OP $ex1 $ex2+)) | ex3=expression ->  $ex3; 

expression:
	enumliteral=enumLiteral -> $enumliteral |
	op=operatorExpression -> $op;
	
operatorExpression: 
	attrcall=attrCall->$attrcall | 
//	method=attrGetterCall -> $method | 
	string=STRING { $string.setText($string.text.substring(1,$string.text.length() - 1)); } ->^(LITERAL $string) |
	number=PNUMBER -> ^(NUMBER $number) |
	constant=(NULL | TRUE | FALSE) ->^(CONSTANT $constant);

// attrGetterCall: id1=ID DOT id2=ID LPAR RPAR -> ^(GETTERCALL $id1 $id2) ;

attrCall:
	var=SMALL_ID DOT name=attrName -> ^(FIELD $var $name);
	
attrName:
	name=SMALL_ID -> $name |
	getter=SMALL_ID LPAR RPAR { $getter.setText($getter.text.substring(3, 4).toLowerCase() + $getter.text.substring(4)); } -> $getter;

enumLiteral:
	en=ENUM_LITERAL -> ^(ENUMLITERAL $en) |
	CAPITAL_ID DOT capital=internalEnumLiteral -> $capital;

internalEnumLiteral:
	en=ENUM_LITERAL -> ^(ENUMLITERAL $en) |
	SMALL_ID DOT small=internalEnumLiteral -> $small |
	CAPITAL_ID DOT capital=internalEnumLiteral -> $capital;