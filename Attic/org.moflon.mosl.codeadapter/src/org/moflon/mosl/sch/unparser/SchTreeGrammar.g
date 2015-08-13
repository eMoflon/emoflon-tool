tree grammar SchTreeGrammar;

options {
  ASTLabelType = CommonTree; 
  output       = template;
  superClass = 'org.moflon.mosl.MOSLTreeGrammar';
}

// Tokens used internally by Moca
// ID: ('a'..'z' | 'A'..'Z')+;
// STRING: ( ID | ('0'..'9') )+; 
// ATRIBUTE: Used as an imaginary token for coding attributes in XML files (ATTRIBUTE name=ID value=STRING)
tokens {
  ID;
  STRING;
  ATTRIBUTE; 
  
  TGG;
  Domain;
  CorrespondenceType;
} 
  
@header {
package org.moflon.mosl.sch.unparser;

import org.moflon.mosl.MOSLUtils;
}


// tree grammar rules:

main:
   ^(TGG 
 		 ^(STRING source=source_decl
         		  target=source_decl)
         c=classes_decl
         
    ) -> schema(source={$source.st},target={$target.st},classes={$c.st});
    
source_decl: ^(Domain
         	         ^(ATTRIBUTE ID source=STRING)) -> value(value={$source});

         	         
classes_decl: ^(STRING c+=class_decl*) -> classes(classes={$c});

class_decl: ^(CorrespondenceType 
					 ^(ATTRIBUTE ID name=STRING) 
                     r=references_decl
                     ) -> class(name={$name},references={$r.st});
                     
references_decl: ^(STRING source=source_reference target=source_reference) -> references(source={$source.st},target={$target.st});

source_reference: ^(EReference 
							 ^(ATTRIBUTE ID STRING)
           					 ^(ATTRIBUTE ID type=STRING)
           		) -> value(value={$type});


    