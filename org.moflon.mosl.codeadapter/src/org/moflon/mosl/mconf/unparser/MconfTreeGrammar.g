tree grammar MconfTreeGrammar;

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
} 
  
@header {
package org.moflon.mosl.mconf.unparser;

import org.moflon.mosl.MOSLUtils;
}
// tree grammar rules:
main: 
 	 ^('config' o=opposites_decl (f=foreign_decl)?)-> constraint(opposites={$o.st}, foreign={$f.st})//((a=opposites_decl -> imports(imports={$a.st})
    | ^('imports' imp+=import_decl* )->imports(imports={$imp});//(b=imports_decl   -> imports(imports={$b.st})); 
		

opposites_decl: ^('opposite' o+=opposite_decl*) -> opposites(opposites={$o});

opposite_decl: ^('oppositeConstraint' //references 
				^(ATTRIBUTE ID source=STRING) //source
				^(ATTRIBUTE ID srcType=STRING) //sourceType
				^(ATTRIBUTE ID target=STRING) //target
				^(ATTRIBUTE ID trgType=STRING) //targetType
				
				) ->oppositeRelationship(src={$source}, srcType={$srcType}, trg={$target}, trgType={$trgType});

foreign_decl: ^('Foreign'
				^(ATTRIBUTE ID export=STRING)
				^(ATTRIBUTE ID uri=STRING)
				^(ATTRIBUTE ID prefix=STRING)
				)->foreign(export={$export}, uri={$uri}, prefix={$prefix});


imports_decl: ^('imports' imp+=import_decl*
				)->imports(imports={$imp});


import_decl:   ^('import'
				^(ATTRIBUTE ID im=STRING) //name
			   )->importValue(importValue={$im});



