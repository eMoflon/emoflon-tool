lexer grammar TggLexer;

options {
	superClass = 'org.moflon.mosl.MOSLLexer';
}

@header {
package org.moflon.mosl.tgg.parser;
import org.moflon.moca.MocaUtil;
import org.moflon.mosl.MOSLUtils;
}
COMMENT: '//' ~( '\r' | '\n' )* {skip();};
RULEX: 'rule';
SOURCE: 'source';
TARGET: 'target';
REFINES: 'refines';
CORRESPONDENCE: 'correspondence';
CONSTRAINTS: 'constraints';
CONSTRAINTS_UPPER: 'CONSTRAINTS';
OPERATIONS: 'operations';
RIGHT_ARROW: '->';
CURLY_BRACKET_OPEN: '{';
CURLY_BRACKET_CLOSE: '}';
LEFT_PARENTHESIS: '(';
RIGHT_PARENTHESIS: ')';
SQUARED_BRACKET_OPEN: '[';
SQUARED_BRACKET_CLOSE: ']';
LEFT_ARROW: '<-';
DPLUS: '++';
DMINUS: '--';
MINUS: '-';
COLON: ':';
DCOLON: '::';
SLASH: '/';
DOT: '.';
DDOT: '..';
COMMA: ',';
SEMI: ';';

QUESTION: '?';
DOLLAR: '$';
AT: '@';
NEGATE: '!';

ASSIGN: ':=';
EQUALS: '==';
NEQ: '!=';
LEQ: '<=';
LT: '<';
GEQ: '>=';
GT: '>';

TRUE: 'true';
FALSE: 'false';

NULL: 'null';

ROOT: 'ROOT';

ADORNMENT: ('B'|'F')+;

ALLOWED_ADORNMENTS:'ALLOWED_ADORNMENTS';

DECLARATIONS: 'DECLARATIONS';

SIGNATURE: 'SIGNATURE';

LITERAL: 'LITERAL';

IMPLICIT_PARAMETER: 'IMPLICIT_PARAMETER';

NEGATED: 'NEGATED';

NOT: 'not';

QUOTATION: '"';
SQUOTATION: '\'';

// Common token definitions
ID: ('a' .. 'z' | 'A' .. 'Z') ( NUM | ('a' .. 'z' | 'A' .. 'Z') )*;
NUM: ('0'.. '9')+;
WS : (' ' | '\t' | '\f' | '\n' | '\r')+ {skip();};
DOUBLE_QUOTED_STRING : (QUOTATION .* QUOTATION | NULL);
SINGLE_QUOTED_STRING : (SQUOTATION .* SQUOTATION | NULL) {MocaUtil.trim(this,1,1);};
CONSTRAINTBLOCK: '{[' .* ']}' {MOSLUtils.trim(this,2,2);};


fragment STRING: 'a'..'z'|'A'..'Z';

ARITMETIC_SIGN: MINUS |'+';

INT_LITERAL: ARITMETIC_SIGN? '0'..'9';





// Lexer Rules:
RULE:;