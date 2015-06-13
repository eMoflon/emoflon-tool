lexer grammar PatternLexer;

options {
	superClass = 'org.moflon.mosl.MOSLLexer';
}

@header {
package org.moflon.mosl.pattern.parser;
import org.moflon.moca.MocaUtil;
}

PATTERN: 'pattern';
DPLUS: '++';
DMINUS: '--';
MINUS: '-';
STAR: '*';
DOUBLEREF: '<->';
RIGHT_ARROW: '->';
CURLY_BRACKET_OPEN: '{';
CURLY_BRACKET_CLOSE: '}';
LEFT_PARENTHESIS: '(';
RIGHT_PARENTHESIS: ')';
SQUARED_BRACKET_OPEN: '[';
SQUARED_BRACKET_CLOSE: ']';
COLON: ':';
DCOLON: '::';
COMMA: ',';
DOT: '.';
DDOT: '..';
SLASH: '/';

QUESTION: '?';
DOLLAR: '$';
AT: '@';
NOT: '!';

ASSIGN: ':=';
EQUALS: '==';
NEQ: '!=';
LEQ: '<=';
LT: '<';
GEQ: '>=';
GT: '>';

NULL: 'null';

TRUE: 'true';
FALSE: 'false';

QUOTATION: '"';
SQUOTATION: '\'';

// Common token definitions
ID: ('a' .. 'z' | 'A' .. 'Z') ( NUM | ('a' .. 'z' | 'A' .. 'Z') )*;
NUM: ('0'.. '9')+;
WS : (' ' | '\t' | '\f' | '\n' | '\r')+ {skip();};
DOUBLE_QUOTED_STRING : ((QUOTATION .* QUOTATION));
SINGLE_QUOTED_STRING : ((SQUOTATION .* SQUOTATION)) {MocaUtil.trim(this,1,1);};

COMMENT: '//' ~( '\r' | '\n' )* {skip();};