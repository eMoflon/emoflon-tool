lexer grammar EclassLexer;

options {
	superClass = 'org.moflon.mosl.MOSLLexer';
}

@header {
package org.moflon.mosl.eclass.parser;
import org.moflon.moca.MocaUtil;
}

TYPEDEF: 'typedef';
PACKAGE: 'package';
CLASS: 'class';
EXTENDS: 'extends';
ENUM: 'enum';
DATATYPE: 'datatype';
ABSTRACT: 'abstract';
DIAMOND: '<>';
MINUS: '-';
RIGHT_ARROW: '->';
CURLY_BRACKET_OPEN: '{';
CURLY_BRACKET_CLOSE: '}';
LEFT_PARENTHESIS: '(';
RIGHT_PARENTHESIS: ')';
SQUARED_BRACKET_OPEN: '[';
SQUARED_BRACKET_CLOSE: ']';
COLON: ':';
COMMA: ',';
DOT: '.';
ARROW: '^';
DOUBLE_DOT: '..';
STAR: '*';
DOLLAR: '$';
AT: '@';
SEMI: ';';
SLASH: '/';
NOT: '!';

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

QUOTATION: '"';
SQUOTATION: '\'';


// SDM tokens
RETURN: 'return';
IF: 'if';
ELSE: 'else';
FOREACH: 'forEach';
DO: 'do';
WHILE: 'while';

COMMENT: '//' ~( '\r' | '\n' )* {skip();};

// Common token definitions
ID: ('a' .. 'z' | 'A' .. 'Z') ( NUM | ('a' .. 'z' | 'A' .. 'Z') )*;
NUM: ('0'.. '9')+;
WS : (' ' | '\t' | '\f' | '\n' | '\r')+ {skip();};
DOUBLE_QUOTED_STRING : (QUOTATION .* QUOTATION | NULL);
SINGLE_QUOTED_STRING : (SQUOTATION .* SQUOTATION | NULL) {MocaUtil.trim(this,1,1);};

// Lexer Rules:
RULE:;