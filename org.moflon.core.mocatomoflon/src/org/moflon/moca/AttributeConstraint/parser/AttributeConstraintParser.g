parser grammar AttributeConstraintParser;

options {
  language = Java;
  tokenVocab = AttributeConstraintLexer;
  output = AST;
}

tokens {
	CSP_CONSTRAINT;
	PARAMETER;
	ATTRIBUTE;
	T;
	ATTR_CONST_VAR;
	ROOT;
	IMPORTS;
	ANIMPORT;
	QUALIFIER;
	FRAGMENT;
	PACKAGEIMPORTS;
	EPACKAGE;
	PACKAGENAME;
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
package org.moflon.moca.AttributeConstraint.parser; 
import java.util.Collection;
import org.moflon.core.moca.processing.ProcessingFactory;
import org.moflon.core.moca.processing.Problem;
import org.moflon.core.moca.processing.ProblemType;
}

// Parser Rules:
main: im=packageImports (op+=constraint SEMICOLON)* -> ^(ROOT $op* $im); 
constraint: op=(OP_ID|ID) LPAR p1=parameter (SEP pn+=parameter)* RPAR -> 	^(CSP_CONSTRAINT ^(ATTRIBUTE T["name"] $op) $p1 $pn*);
 			

packageImports: 
     (imp+=packageImport)* ->^(PACKAGEIMPORTS $imp*);
     
packageImport:
	 IMPORT pack=fullyQualifiedPack SEMICOLON -> $pack ;
	 														
fullyQualifiedPack:
	prefix=qualifier pack=ID -> ^(EPACKAGE ^(PACKAGENAME ^($pack)) $prefix );

qualifier:
	(fragments+=ID DOT)*-> ^(QUALIFIER ^($fragments)*);       
parameter:
	attrVar=attrLookupPrimitiveVar -> $attrVar | 
	attrVar1=attrAssignPrimitiveVar -> $attrVar1 |
	tempVar=tempPrimtiveVar -> $tempVar | 
	litVar=literalVar -> $litVar |
	objectVar=objectVariable -> $objectVar;
	


attrAssignPrimitiveVar:
	ovName=ID DOT attrName=ID SINGLEQUOTE -> ^(ATTR_CONST_VAR 	^(ATTRIBUTE T["type"] T["AttrAssignmentVariable"])
													 			^(ATTRIBUTE T["ovName"] $ovName  )
																^(ATTRIBUTE T["attrName"] $attrName));
attrLookupPrimitiveVar:
	ovName=ID DOT attrName=ID -> ^(ATTR_CONST_VAR 	^(ATTRIBUTE T["type"] T["AttrLookupVariable"])
														^(ATTRIBUTE T["ovName"] $ovName )
														^(ATTRIBUTE T["attrName"] $attrName ));

tempPrimtiveVar:
	name=ID COLON (pack=fullyQualifiedPack DOT)? type=ID-> ^(ATTR_CONST_VAR		^(ATTRIBUTE T["type"] T["TempVariable"])
																^(ATTRIBUTE T["name"] $name)
																^(ATTRIBUTE T["datatype"] $type)
																$pack*); 

literalVar:
	constant=(NUMBER|BOOLEAN|STRING|ID) COLON COLON  (pack=fullyQualifiedPack DOT)? type=ID -> 
													^(ATTR_CONST_VAR 	
													^(ATTRIBUTE T["type"] T["LiteralVariable"])
													^(ATTRIBUTE T["value"] $constant)
													^(ATTRIBUTE T["datatype"] $type) 
														$pack*);  
													
objectVariable:
	name=ID
	-> 
	^(ATTR_CONST_VAR
	^(ATTRIBUTE T["name"] $name) 
	^(ATTRIBUTE T["type"] T["PrimitiveParameterVariable" ])	
	);				 	
													
