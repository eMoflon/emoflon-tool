tree grammar EclassTreeGrammar;

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
  
  EClass;
  EReference;
  EAttribute;
  EOperation;
  EParameter;
  Typedef;
  Opposite;
  SequentialActivityNode;
  IfActivityNode;
  ForEachActivityNode;
  Activity;
  ActivityNode;
  
  MethodCallExpression;
  
  StringExpression;
  BooleanExpression;
  IntegerExpression;
  DoubleExpression;
  UndefinedExpression;
  NullExpression;
} 
  
@header {
package org.moflon.mosl.eclass.unparser;

import static org.moflon.mosl.MOSLUtils.*;
}

// tree grammar rules:
main: (c=classes_decl -> {$c.st});

classes_decl: ^(STRING c+=class_decl* e+=enum_decl* d+=datatype_decl*) -> classes(classes={$c},enums={$e},datatypes={$d});

datatype_decl: ^(EDatatype ^(ATTRIBUTE ID name=STRING)
                           ^(ATTRIBUTE ID type=STRING))
          -> datatype(name={$name},type={$type});

enum_decl: ^(EEnum ^(ATTRIBUTE ID name=STRING)
                  literals=literals_decl)
      -> enum(name={$name},literals={$literals.st});

literals_decl: ^(STRING l+=literal_decl*) -> literals(literals={$l});

literal_decl: ^(EEnumLiteral ^(ATTRIBUTE ID value=STRING) ^(ATTRIBUTE ID name=STRING)) -> literal(name={$name},value={$value});

class_decl: ^(EClass ^(ATTRIBUTE ID name=STRING) //Typedefs are missing
                     ^(ATTRIBUTE ID isAbstract=STRING)
                     (baseClasses=base_classes_decl)?
                     references=references_decl
                     attributes=attributes_decl
                     operations=operations_decl
                     typedefinitions=typedefs_decl
                     .// etypeparameters=etps_decl* //same unparser for eTypeParameters (etp)
                     .// genericsupertypes=gsts_decl* // and for genericSuperTypes (gst)
    )  -> class(name={$name},base={$baseClasses.st},typedefs={$typedefinitions.st},attributes={$attributes.st},references={$references.st},operations={$operations.st},abstract={decode($isAbstract, "true", "abstract", "false", "")});

etps_decl: ^('etypeParameters' etp+=etpgst_decl*);

gsts_decl: ^('genericSuperTypes' gst+=etpgst_decl*);

typedefs_decl: ^('typedefs' defs+=typedef_decl*)-> dummy(arg={$defs});

typedef_decl: ^(Typedef
				^(ATTRIBUTE ID name=STRING)
				^(ATTRIBUTE ID path=STRING)
				)
				-> typedef(name={$name}, path={$path});

base_classes_decl: ^('BaseClasses' bases+=bases_decl+) -> parameters(parameters={$bases});

bases_decl: ^(ATTRIBUTE ID name=STRING) -> single_statement(statement={$name});

etpgst_decl: ^(ATTRIBUTE ID name=STRING);
      
references_decl: ^(STRING r+=reference_decl*) -> references(references={$r});

reference_decl: ^(EReference ^(ATTRIBUTE ID name=STRING)
							 ^(ATTRIBUTE ID type=STRING)
           					 ^(ATTRIBUTE ID diamond=STRING)
           					 mul=multiplicity_decl)
           	-> reference(name={$name}, type={$type}, diamond={decide($diamond,"<>")}, multiplicity={$mul.st});

//xattribute: ^(ATTRIBUTE ID STRING);        
   	
multiplicity_decl: ^(ATTRIBUTE ID lower=STRING)
                   ^(ATTRIBUTE ID upper=STRING)
              -> multiplicity(lower={$lower},upper={trBound($upper)});
                         
attributes_decl: ^(STRING a+=attribute_decl*) -> attributes(attributes={$a});
   
attribute_decl: ^(EAttribute ^(ATTRIBUTE ID name=STRING) //name
							 ^(ATTRIBUTE ID type=STRING) //type   
							 (.)? //eGenericTypes                          
            ) -> attribute(name={$name}, type={$type});

operations_decl: ^(STRING o+=operation_decl*) -> operations(operations={$o});

operation_decl: ^(EOperation ^(ATTRIBUTE ID name=STRING)
           					 ^(ATTRIBUTE ID type=STRING)
           					 p=parameterlist_decl
           					 m=method_decl?
           		) -> operation(name={$name},type={$type},parameters={$p.st},body={$m.st});
           		
           		
parameterlist_decl: ^(STRING p+=parameter_decl*) -> parameters(parameters={$p});

parameter_decl: ^(EParameter ^(ATTRIBUTE ID name=STRING)
							 ^(ATTRIBUTE ID type=STRING))
  	        -> parameter(name={$name},type={$type});
  	        
method_decl: ^(Activity	
              body=method_body_decl) -> method_body(body={$body.st});
              
method_body_decl: ^('body' s=statement_list) -> statement_list(statements={$s.st});

statement_list: ^('stmt_list' s+=statement_decl*) -> single_statement(statement={$s});
             
statement_decl: ^('stmt' s=simple_statement) -> single_statement(statement={$s.st})             
             |  ^('stmt' i=if_statement) -> single_statement(statement={$i.st})
             |  ^('stmt' f=foreach_statement) -> single_statement(statement={$f.st})
             |  ^('stmt' w=while_statement) -> single_statement(statement={$w.st})
             |  ^('stmt' d=do_statement) -> single_statement(statement={$d.st});

simple_statement: 
			 	x=boolean_statement -> simple_statement(statement={$x.st})
			 |  ^('simple_stmt' r=return_statement) -> simple_statement(statement={$r.st});

boolean_statement: ^('simple_stmt' p=pattern_statement) -> simple_statement(statement={$p.st})
					|  ^('simple_stmt' c=call_statement) -> simple_statement(statement={$c.st});

call_statement: ^('call_stmt' ^('Expression' mce=method_call_expression)) -> call_statement(value={$mce.st});

pattern_statement: 
                   ^('pattern_stmt' name=STRING) -> pattern_statement(name={$name});

return_statement: ^('return_stmt' ^('Expression' name=expression_type)) -> return_statement(value={$name.st})
					|  ^('return_stmt' voidValue=' ') -> return_statement(value={$voidValue});

while_statement: ^('while_stmt' test=boolean_statement ^(STRING while_block=statement_list))
				-> while_statement(test={$test.st}, then={$while_block.st});

do_statement: ^('do_stmt' ^(STRING do_block=statement_list) test=boolean_statement )
				-> do_statement(do={$do_block.st}, test={$test.st});

if_statement: ^('if_stmt' test=boolean_statement 
				^(STRING if_block=statement_list) 
				(^(STRING else_block=statement_list))?
         ) -> if_statement(test={$test.st},then={$if_block.st},otherwise={$else_block.st});

foreach_statement: ^('foreach_stmt' test=pattern_statement (^(STRING body=statement_list?))? )
             -> foreach_statement(test={$test.st},body={$body.st});

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

