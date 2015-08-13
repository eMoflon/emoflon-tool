tree grammar PatternTreeGrammar;

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
  
  Pattern;
  ObjectVariable;
} 
  
@header {
package org.moflon.mosl.pattern.unparser;

import static org.moflon.mosl.MOSLUtils.*;
}

// tree grammar rules:
main: ^(Pattern ^(ATTRIBUTE ID value=STRING)  ^(STRING pa+=box_decl*)) -> pattern(name={$value}, variables={$pa});

box_decl: ^(ObjectVariable 	
						   ^(ATTRIBUTE ID name=STRING)
						   ^(ATTRIBUTE ID type=STRING)					   
						   st=binding_state
						   op=binding_operator
						   se=binding_semantics
						   c=constraints_decl
						   a=assignments_decl
						   l=links_decl
						   b+=binding_decl?
         ) -> box(name={$name},type={$type},state={$st.st},operator={$op.st},semantics={$se.st},constraints={$c.st},links={$l.st},assignments={$a.st},binding={$b});
         
binding_semantics: ^(ATTRIBUTE ID semantics=STRING) -> value(value={decode($semantics, "mandatory", "", "negative", "!", "optional", "?")});
            
binding_operator: ^(ATTRIBUTE ID operator=STRING) -> value(value={decode($operator, "check_only", "", "create", "++", "destroy", "--")});

binding_state: ^(ATTRIBUTE ID state=STRING) -> value(value={decode($state, "bound", "@", "unbound", "")});

constraints_decl: ^(STRING c+=constraint_decl*) -> constraints(constraints={$c});

constraint_decl: ^(STRING  ^(STRING 
									^(ATTRIBUTE ID op=STRING)
									^(STRING expr1=expression)
      						        ^(STRING expr2=expression)
                             )
          ) -> constraint(op={decode($op, "equal", "==", "unequal", "!=")}, expr1={$expr1.st}, expr2={$expr2.st});
          
assignments_decl: ^(STRING a+=assignment_decl*) -> assignments(assignments={$a});

assignment_decl: ^(STRING  	          
      			           ^(ATTRIBUTE ID name=STRING)
      			           ^(ATTRIBUTE ID attr=STRING)
      			           ^('valueExpression' value=expression)
                   ) -> assignment(object={$name},attr={$attr},expr={$value.st});
          
assignment_operator: ASSIGN;          
links_decl: ^(STRING l+=link_decl*) -> links(links={$l});

link_decl: ^(LinkVariable 
						^(ATTRIBUTE ID name=STRING)
						^(ATTRIBUTE ID target=STRING)					
                        op=binding_operator
                        se=binding_semantics                      
          ) -> link(target={$target},object={$name},operator={$op.st},semantics={$se.st});
          
binding_decl: ^(STRING e=expression) -> binding(expression={$e.st});

// Expressions

expression: ^('Expression' exprType=expression_type) -> dummy(arg={$exprType.st});

expression_type:  a=literal_expression -> expression(expression={$a.st})
          |  b=object_variable_expression -> expression(expression={$b.st})
          |  c=parameter_expression -> expression(expression={$c.st})
          |  d=method_call_expression -> expression(expression={$d.st})
          |  e=attribute_value_expression -> expression(expression={$e.st})
          |  f=textual_expression -> expression(expression={$f.st});

literal_expression: g=lStringExpression -> expression(expression={$g.st})
		 		  | h=lBooleanExpression -> expression(expression={$h.st})
		 		  | i=lIntegerExpression -> expression(expression={$i.st})
		 		  | j=lDoubleExpression -> expression(expression={$j.st})
		 		  | k=lUndefinedExpression -> expression(expression={$k.st})
		 		  | l=lNullExpression -> expression(expression={$l.st});


lStringExpression: 		^(StringExpression
				      		^(ATTRIBUTE ID value=STRING)
				      	) 				      
				      -> string_expression(value={$value});

				                           
lBooleanExpression:   	^(BooleanExpression
				      		^(ATTRIBUTE ID value=STRING)
				      	)				     
				      -> literal_expression(value={$value});
				                           
lIntegerExpression:  	^(IntegerExpression
				      		^(ATTRIBUTE ID value=STRING)
				     	)
				      -> literal_expression(value={$value});
				           
lDoubleExpression: 	 	^(DoubleExpression
				      		^(ATTRIBUTE ID value=STRING)
				     	)
				      -> literal_expression(value={$value});				           
				                           
lUndefinedExpression: 	^(UndefinedExpression
				      		^(ATTRIBUTE ID value=STRING)
				      	) 
				      -> undefined_expression(value={$value});
				                          
lNullExpression: 	  	^(NullExpression
				      		^(ATTRIBUTE ID value=STRING)
				     	) 
				      -> literal_expression(value={$value});

object_variable_expression: ^('ObjectVariableExpression' name=STRING)
                       -> object_variable_expression(name={$name});

parameter_expression:  	^('ParameterExpression' name=STRING)
				  -> parameter_expression(name={$name});

method_call_expression: ^(MethodCallExpression
							^(ATTRIBUTE ID method=STRING)						
							target=lMethodCallTarget
							(args=arguments_decl)?						
							 )
				  -> method_call_expression(method={$method},target={$target.st},arguments={$args.st});

lMethodCallTarget: ^('target' expr=expression) -> dummy(arg={$expr.st});
				       
attribute_value_expression: ^(STRING object=STRING attribute=STRING) 
					  -> attribute_value_expression(object={$object}, attribute={$attribute});
	
textual_expression: ^('TextualExpression' value=STRING)
						-> literal_expression(value={$value});
					  
// Argument lists

arguments_decl: ^('ownedParameterBinding' a=argument_decl+) -> arguments(arguments={$a.st});

argument_decl:  ^('valueExpression'
					    ^(ATTRIBUTE ID STRING)
            			^(ATTRIBUTE ID value=STRING)
                )                                 
                 -> argument(value={$value});

