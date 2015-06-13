parser grammar SchParser;

options {
  language = Java;
  tokenVocab = SchLexer;
  output = AST;
  superClass = 'org.moflon.mosl.MOSLParser';
}

// List of tokens used only used to structure the tree
tokens {
  ATTRIBUTE;
  T;
  
  TGG;
  Domain;
  EClass;
  EReference;
  
}

@header {
package org.moflon.mosl.sch.parser; 
import org.moflon.mosl.MOSLUtils;
}

// parser rules:
main: schema_declaration EOF -> schema_declaration;

schema_declaration: source_decl target_decl classes_decl ->
   ^(TGG 
         ^(T["domains"] source_decl
         				target_decl
		)
         classes_decl
    );
    
source_decl: SOURCE type=type_reference
         -> ^(Domain ^(ATTRIBUTE T["metamodel"] $type)
         ^(ATTRIBUTE T["category"] T["metamodel"]) );

target_decl: TARGET type=type_reference
         -> ^(Domain ^(ATTRIBUTE T["metamodel"] $type)
         ^(ATTRIBUTE T["category"] T["metamodel"]) );
         	         
classes_decl: class_decl* -> ^(T["classes"] class_decl*);

class_decl: CLASS name=ID extends_decl? references_decl?
       ->   ^(T["CorrespondenceType"] ^(ATTRIBUTE T["name"] $name) 
       ^(ATTRIBUTE T["category"] T["type"]) 
                     extends_decl?
                     ^(T["references"] references_decl?)
              );
                     
extends_decl: EXTENDS name=type_reference -> ^(ATTRIBUTE T["baseClassName"] $name);

references_decl: OPEN source_reference target_reference CLOSE -> source_reference target_reference;

source_reference: SOURCE REF type=type_reference
           ->   ^(EReference 
           					 ^(ATTRIBUTE T["name"] T["source"])
           					 ^(ATTRIBUTE T["type"]  $type )
           					 ^(ATTRIBUTE T["category"] T["reference"]) );
           					 
           		

target_reference:TARGET REF type=type_reference
           ->   ^(EReference 
           					 ^(ATTRIBUTE T["name"] T["target"])
           					 ^(ATTRIBUTE T["type"] $type )
           					 ^(ATTRIBUTE T["category"] T["reference"]) );
           	

type_reference:  i+=SLASH? ((i+=ID | i+=DDOT) i+=SLASH)* i+=ID -> {MOSLUtils.concat(T, $i)};

                     