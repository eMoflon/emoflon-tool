lexer grammar SchLexer;

options {
	superClass = 'org.moflon.mosl.MOSLLexer';
}

@header {
package org.moflon.mosl.sch.parser;
}

SCHEMA: 'schema';
SOURCE: 'source';
TARGET: 'target';
CLASS: 'class';
EXTENDS: 'extends';
OPEN: '{';
CLOSE: '}';
REF: '->';
SLASH: '/';
DDOT: '..';

COMMENT: '//' ~( '\r' | '\n' )* {skip();};

// Common token definitions
ID: ('a' .. 'z' | 'A' .. 'Z') ( NUM | ('a' .. 'z' | 'A' .. 'Z' | '_' ) )*;
NUM: ('0'.. '9')+;
QSTRING: '"' .+ '"'; 
WS : (' ' | '\t' | '\f' | '\n' | '\r')+ {skip();};

// Lexer Rules:
RULE:;