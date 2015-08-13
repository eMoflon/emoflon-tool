tree grammar SokTreeGrammar;

options {
  ASTLabelType = CommonTree; 
  output       = template;
}

tokens {
  ID;
  STRING;
  ATTRIBUTE; 
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
package org.moflon.moca.sok.unparser;
}

// Grammar rules to parse the simple MocaTree into something that can be printed to file

main: ^('BOARD_SPEC' dimensions? ^('BOARD' rows+=row+)) -> board(rows={$rows});

dimensions: ^('DIMENSIONS' ^('ROWS' STRING) ^('COLS' STRING));

row : ^('ROW' columns+=column+) -> row(columns={$columns});

column :^('COLUMN' t+=STRING?) -> column(type={$t});