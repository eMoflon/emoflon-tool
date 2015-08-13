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
         T["constraints"]
    );
    
source_decl: SOURCE type=type_reference
         -> ^(Domain ^(ATTRIBUTE T["metamodelGuid"] $type)
         			^(ATTRIBUTE T["domainType"] T["source"])
         			^(ATTRIBUTE T["category"] T["metamodel"])
         			^(ATTRIBUTE T["searchCategory"] T["package"])
					^(ATTRIBUTE T["search"] T["metamodelGuid"]));

target_decl: TARGET type=type_reference
         -> ^(Domain ^(ATTRIBUTE T["metamodelGuid"] $type)
         	^(ATTRIBUTE T["domainType"] T["target"])
            ^(ATTRIBUTE T["category"] T["metamodel"]) 								
            ^(ATTRIBUTE T["searchCategory"] T["package"])
			^(ATTRIBUTE T["search"] T["metamodelGuid"]));
								
								
classes_decl: class_decl* -> ^(T["classes"] class_decl*);

class_decl: CLASS name=ID lExtendList? references_decl?
       ->   ^(T["CorrespondenceType"]
       			^(ATTRIBUTE T["name"] $name) 
        		^(ATTRIBUTE T["category"] T["type"]) 
                  ^(ATTRIBUTE T["isAbstract"] T["false"])
                  lExtendList?
                     ^(T["references"] references_decl?)
                     ^(T["attributes"])
                     ^(T["operations"])
                     
              );
                     
lExtendList: EXTENDS (lExtends COMMA)* lExtends // FIXME should be changed in Transformer and Untransformer
		-> ^(T["BaseClasses"] lExtends+);			 

lExtends: ext=type_reference -> ^(T["baseClass"]
								^(ATTRIBUTE T["baseClass"] $ext)
								^(ATTRIBUTE T["searchCategory"] T["type"])
								^(ATTRIBUTE T["search"] T["baseClass"]));


references_decl: OPEN source_reference target_reference CLOSE -> source_reference target_reference;

subreference_decl:REF type=type_reference
          ->    ^(ATTRIBUTE T["type"] $type)
           		^(ATTRIBUTE T["category"] T["reference"]) 
           		^(ATTRIBUTE T["searchCategory"] T["type"])
				^(ATTRIBUTE T["search"] T["type"])
           		^(ATTRIBUTE T["containment"] T["false"])
           		^(ATTRIBUTE T["lowerBound"] T["1"])
           		^(ATTRIBUTE T["upperBound"] T["1"]);

source_reference: SOURCE subreference_decl
 		    -> ^(EReference 
           			    ^(ATTRIBUTE T["name"] T["source"]) 
						subreference_decl);         					 
           		

target_reference:TARGET subreference_decl
           ->   ^(EReference 
           					 ^(ATTRIBUTE T["name"] T["target"])
           					 subreference_decl);
           	

type_reference:  i+=SLASH? ((i+=ID | i+=DDOT) i+=SLASH)* i+=ID -> {MOSLUtils.concat(T, $i)};

                     