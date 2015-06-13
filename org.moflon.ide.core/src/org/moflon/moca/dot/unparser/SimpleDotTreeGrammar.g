tree grammar SimpleDotTreeGrammar;

options {
  ASTLabelType = CommonTree; 
  output       = template;
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
package org.moflon.moca.dot.unparser;
}
// tree grammar rules:
main: ^('root' leaves+=leaf*) -> root(leaves={$leaves});

leaf: ^(src=STRING ^('PROPERTIES' attrs+=attribute*) ^('REFS' refs+=ref*)) -> leaf(src={$src}, attrs={$attrs}, refs={$refs});

ref: ^(name=STRING children+=child*) -> ref(name={$name}, children={$children});

child: trg=STRING -> child(trg={$trg});

attribute: ^(ATTRIBUTE name=ID value=STRING) -> property(name={$name}, value={$value});
