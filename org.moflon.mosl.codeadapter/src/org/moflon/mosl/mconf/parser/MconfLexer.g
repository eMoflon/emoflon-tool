lexer grammar MconfLexer;

options {
	superClass = 'org.moflon.mosl.MOSLLexer';
}

@header {
package org.moflon.mosl.mconf.parser;
import org.moflon.moca.MocaUtil;
import org.moflon.mosl.MOSLUtils;
}

OPPOSITES: 'opposites';
IMPORT: 'import';
FOREIGN: 'foreign';
EXPORT: 'export';
USER_DEFINED_CONSTRAINTS: 'user_defined_constraints' | 'udc';
URI: 'uri';
PREFIX: 'prefix';
DOUBLEREF: '<->';
ASSIGN: ':=';
CURLY_BRACKET_OPEN: '{';
CURLY_BRACKET_CLOSE: '}';
LEFT_PARENTHESIS: '(';
RIGHT_PARENTHESIS: ')';
SQUARED_BRACKET_OPEN: '[';
SQUARED_BRACKET_CLOSE: ']';
COMMA: ',';
COLON: ':';
SLASH: '/';
DDOT: '..';
ROOT: 'ROOT';
LINE: '|';

ADORNMENT: ('B'|'F')+;

ALLOWED_ADORNMENTS:'ALLOWED_ADORNMENTS';
GENMOD_ADORNMENTS: 'GENMOD_ADORNMENTS';

DECLARATIONS: 'DECLARATIONS';

SIGNATURE: 'SIGNATURE';

LITERAL: 'LITERAL';

IMPLICIT_PARAMETER: 'IMPLICIT_PARAMETER';
NOT: 'not';
NEGATED: 'NEGATED';

CONSTRAINTS_UPPER: 'CONSTRAINTS';

TRUE: 'true';
FALSE: 'false';

NULL: 'null';

QUOTATION: '"';
SQUOTATION: '\'';

COMMENT: '//' ~( '\r' | '\n' )* {skip();};

// Common token definitions
ID: ('a' .. 'z' | 'A' .. 'Z') ( NUM | ('a' .. 'z' | 'A' .. 'Z') )*;
NUM: ('0'.. '9')+;
WS : (' ' | '\t' | '\f' | '\n' | '\r')+ {skip();};
DOUBLE_QUOTED_STRING : (QUOTATION .* QUOTATION | NULL);
SINGLE_QUOTED_STRING : (SQUOTATION .* SQUOTATION | NULL) {MocaUtil.trim(this,1,1);};
CONSTRAINTBLOCK: '{[' .* ']}' {MOSLUtils.trim(this,2,2);};


fragment STRING: 'a'..'z'|'A'..'Z';

ARITMETIC_SIGN: '-' | '+' ;

INT_LITERAL: ARITMETIC_SIGN? '0'..'9';