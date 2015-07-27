parser grammar MconfParser;

options {
  language = Java;
  tokenVocab = MconfLexer;
  output = AST;
  superClass = 'org.moflon.mosl.MOSLParser';
}

// List of tokens used only used to structure the tree
tokens {
  ATTRIBUTE;
  T;
  Opposite;
}

@header {
package org.moflon.mosl.mconf.parser; 
import org.moflon.mosl.MOSLUtils;
import org.moflon.moca.MocaTokenFactory;
}

@members{ 
	   private CommonTree toTree(List elements){
    	String token = "";
        	for(Object o : elements){
        		Token t = (Token)o;
        		token += t.getText();
        	}
        	return MocaTokenFactory.createCommonTree(token, tokenNames);
  }
  
  private int declarationIndex = 0;
  private String getDecIndex(){
  	return "" + declarationIndex++;
  }

}

// parser rules:
main: ( constraint_decl | configuration);

configuration: conf -> ^(T["Configuration"] conf);

conf: imports_decl | udcs_decl | (imports_decl udcs_decl) ;

constraint_decl: opposites_decl foreign_decl? -> ^(T["constraints"] opposites_decl foreign_decl?);

imports_decl: import_decl+ -> ^(T["imports"] import_decl+);

import_decl: IMPORT name=type_reference -> ^(ATTRIBUTE T["import"] $name);

udcs_decl: USER_DEFINED_CONSTRAINTS CURLY_BRACKET_OPEN lCSP? CURLY_BRACKET_CLOSE -> ^(T["UserDefinedConstraints"] lCSP?);

opposites_decl: OPPOSITES CURLY_BRACKET_OPEN opposite_reference* CURLY_BRACKET_CLOSE -> ^(T["opposites"] opposite_reference*);

opposite_reference: name1=ID COLON type1=type_reference DOUBLEREF name2=ID COLON type2=type_reference
               -> ^(Opposite ^(ATTRIBUTE T["name"] T[$name1.text + "<->" + $name2.text])
               				 ^(ATTRIBUTE T["nameLeft"] T[$type1.text + "/" + $name1.text])
                             ^(ATTRIBUTE T["typeLeft"] $type1)
                             ^(ATTRIBUTE T["searchCategory"] T["type"])
		   					 ^(ATTRIBUTE T["search"] T["typeLeft"])
                             ^(ATTRIBUTE T["nameRight"] T[$type2.text + "/" + $name2.text])
                             ^(ATTRIBUTE T["typeRight"] $type2)
                             ^(ATTRIBUTE T["searchCategory"] T["type"])
		   					 ^(ATTRIBUTE T["search"] T["typeRight"])
		   					 ^(ATTRIBUTE T["searchCategory"] T["reference"])
		   					 ^(ATTRIBUTE T["search"] T["nameLeft"])
		   					 ^(ATTRIBUTE T["searchCategory"] T["reference"])
		   					 ^(ATTRIBUTE T["search"] T["nameRight"])
		   					 ^(ATTRIBUTE T["category"] T["opposite"])
                    );
                    
foreign_decl: FOREIGN CURLY_BRACKET_OPEN EXPORT ASSIGN export=BOOLEAN URI ASSIGN uri=SINGLE_QUOTED_STRING PREFIX ASSIGN prefix=SINGLE_QUOTED_STRING
				-> ^(T["Foreign"]
					^(ATTRIBUTE T["export"] $export)
					^(ATTRIBUTE T["Moflon::Export"] $export)
					^(ATTRIBUTE T["nsURI"] $uri)
					^(ATTRIBUTE T["nsPrefix"] $prefix)
				);                   
                    
type_reference:  i+=SLASH? ((i+=ID | i+=DDOT) i+=SLASH)* i+=ID -> {MOSLUtils.concat(T, $i)};

// CSP

  lCSP:
  (
    declarations+=declarationRule
  )+
    ->
      ^(
        ROOT
        ^(DECLARATIONS $declarations*)
       CONSTRAINTS_UPPER
       )
       
  ;

declarationRule
  :
  constraintToken=ID adornments=adornmentRule? signature=signatureRule informationText
    ->
      ^($constraintToken 
      informationText
      ^(ATTRIBUTE T["USER_DEFINED"] T["true"])
      ^(ATTRIBUTE T["INDEX"] T[getDecIndex()])
      	$adornments 
     	 $signature?)
  ;

informationText:INFORMATION ASSIGN informationString=DOUBLE_QUOTED_STRING -> ^(ATTRIBUTE T["INFORMATION_TEXT"] T["Semantics:" + $informationString.text])
				| -> ^(ATTRIBUTE T["INFORMATION_TEXT"] T[""]);

adornmentRule
  :
  SQUARED_BRACKET_OPEN adornments+=ADORNMENT (COMMA adornments+=ADORNMENT)* (LINE genmod_adornments+=ADORNMENT | LINE genmod_adornments+=ADORNMENT (COMMA genmod_adornments+=ADORNMENT)+)? SQUARED_BRACKET_CLOSE
    ->
      ^(ALLOWED_ADORNMENTS $adornments+)
      ^(MODELGEN_ADORNMENTS $adornments+ $genmod_adornments*)
  ;

signatureRule
  :
  (LEFT_PARENTHESIS types+=compositeType (COMMA types+=compositeType)* RIGHT_PARENTHESIS)?
    ->
      ^(SIGNATURE $types*)
  ;

compositeType
  :
  type+=ID (type+=DOT type+=ID)* -> {toTree($type)} 
  ;
  
