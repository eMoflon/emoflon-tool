tree grammar XMLTreeGrammar;

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
  TAG='TAG';
  TEXT='TEXT';
}

@members {
	public Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)	throws RecognitionException  {
		try {
        		return super.recoverFromMismatchedToken(input, ttype, follow);
            } catch(java.util.NoSuchElementException e){
                throw new IllegalArgumentException("Your tree does not comply with your tree grammar!\n"
                		+ " Problems encountered at: [" + "..." + getTreeNodeStream().LT(-1) + " " 
                		+ getTreeNodeStream().LT(1) + "..." + "] in tree.");
		}
    }
}  

@header { 
  package org.moflon.moca.xml; 
  import MocaTree.Attribute;
} 

prog
  :
    emftree EOF
      -> {$emftree.st}  
  ;

emftree
  :
    ^(TEXT content=STRING)
      -> text(content={$content}) |
    ^(label=STRING attributes+=attribute* children+=emftree*)
      -> node(label={$label}, children={$children}, attributes={$attributes})     
  ;

attribute 
  :
    ^(ATTRIBUTE name=ID value=STRING)
      -> attribute(name={$name}, value={$value})
  ;
