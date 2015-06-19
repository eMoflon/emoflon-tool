parser grammar TggParser;

options {
  language = Java;
  tokenVocab = TggLexer;
  output = AST;
  superClass = 'org.moflon.mosl.MOSLParser';
}

// List of tokens used only used to structure the tree
tokens {
  ATTRIBUTE;
  T;
  
  EParameter;
  EOperation;
  
  Rule;
  TGGCorrespondence;
  TGGObjectVariable;
  TGGLinkVariable;
  MethodCallExpression;
}

@header {
package org.moflon.mosl.tgg.parser; 
import org.moflon.mosl.MOSLUtils;
import org.moflon.moca.MocaTokenFactory;
}

@members {
	private String scope;
	private String operator;
	
    	private CommonTree concat(int type, List<Object> list) {
    		StringBuilder sb = new StringBuilder();
    		for (Object o : list) {
    			if(o instanceof Token){
    				Token token = (Token)o;
    		   		if ("SOURCE".equals(tokenNames[token.getType()])) {
    		      		sb.append("source");
    		   		} else if ("TARGET".equals(tokenNames[token.getType()])) {
               			sb.append("target");
            		} else {
               			sb.append(token.getText());
            		}
            	}
    		}
    		CommonToken token = new CommonToken(type, sb.toString());
    	    return new CommonTree(token);
    	}
    	private CommonTree toTree(List elements){
    	String token = "";
        	for(Object o : elements){
        		Token t = (Token)o;
        		token += t.getText();
        	}
        	return MocaTokenFactory.createCommonTree(token, tokenNames);
  }
}

// parser rules:
main: RULEX name=ID rule_parameter_decl? lRefinmentList? CURLY_BRACKET_OPEN source_pattern? correspondence_pattern? target_pattern? lCSPList operations_decl? CURLY_BRACKET_CLOSE //EOF
  -> ^(Rule 
  			^(ATTRIBUTE T["name"] $name)
  			^(ATTRIBUTE T["category"] T["rule"])
  			lCSPList
  			lRefinmentList?
  			source_pattern?
  			correspondence_pattern?
  			target_pattern?
  			^(T["parameters"] rule_parameter_decl?)
  			^(T["operations"] operations_decl?) 
      );

lRefinmentList: REFINES (lRefinment COMMA)* lRefinment
		-> ^(T["BaseClasses"] lRefinment+);			 

lRefinment: ext=lTypeReference 
		-> ^(ATTRIBUTE T["baseClassName"] $ext)
		   ^(ATTRIBUTE T["searchCategory"] T["pattern"])
		   ^(ATTRIBUTE T["search"] T["baseClassName"]);
      
lCSPList: CONSTRAINTS CURLY_BRACKET_OPEN lCSP? CURLY_BRACKET_CLOSE -> ^(T["cspSpec"] lCSP? ) ;
      
rule_parameter_decl: LEFT_PARENTHESIS parameterlist_decl RIGHT_PARENTHESIS -> parameterlist_decl;
  
correspondence: lBindingOperator source_ref LEFT_ARROW name=ID COLON type=lTypeReference RIGHT_ARROW target_ref
     -> ^(TGGCorrespondence ^(T["constraints"])
                         ^(T["attributeAssignments"])
                         ^(T["outgoingLinks"] source_ref target_ref)
                         ^(ATTRIBUTE T["name"] $name)
                         ^(ATTRIBUTE T["category"] T["correspondence"])
                         lBindingOperator
                         ^(ATTRIBUTE T["bindingSemantics"] T["mandatory"])
                         ^(ATTRIBUTE T["domain"] T["correspondence"])
                         ^(ATTRIBUTE T["type"] $type)
                         ^(ATTRIBUTE T["searchCategory"] T["type"])
		   				 ^(ATTRIBUTE T["search"] T["type"])
         );
         
source_ref: name=ID
      -> ^(TGGLinkVariable ^(ATTRIBUTE T["name"] T["source"])
      					^(ATTRIBUTE T["category"] T["tggLink"])
                         ^(ATTRIBUTE T["bindingSemantics"] T["mandatory"])
                        ^(ATTRIBUTE T["targetObject"] $name)
                         ^(ATTRIBUTE T["domain"] T["correspondence"])
          );

target_ref: name=ID
      -> ^(TGGLinkVariable ^(ATTRIBUTE T["name"] T["target"])
     					 ^(ATTRIBUTE T["category"] T["tggLink"])
                         ^(ATTRIBUTE T["bindingSemantics"] T["mandatory"])
                        ^(ATTRIBUTE T["targetObject"] $name)
                         ^(ATTRIBUTE T["domain"] T["correspondence"])
          );

lBoxBlock: -> ^(T["constraints"]) ^(T["attributeAssignments"]) ^(T["outgoingLinks"]) 
		| CURLY_BRACKET_OPEN lAssignmentList CURLY_BRACKET_CLOSE -> ^(T["constraints"]) lAssignmentList  ^(T["outgoingLinks"]) 
		| CURLY_BRACKET_OPEN lConstraintList CURLY_BRACKET_CLOSE -> lConstraintList ^(T["attributeAssignments"])  ^(T["outgoingLinks"])
	    | CURLY_BRACKET_OPEN lLinkList CURLY_BRACKET_CLOSE -> ^(T["constraints"]) ^(T["attributeAssignments"])  lLinkList 
		|( CURLY_BRACKET_OPEN lAssignmentList lConstraintList lLinkList CURLY_BRACKET_CLOSE
          		|  CURLY_BRACKET_OPEN lConstraintList lAssignmentList lLinkList CURLY_BRACKET_CLOSE
          		|  CURLY_BRACKET_OPEN lAssignmentList lLinkList lConstraintList CURLY_BRACKET_CLOSE
          		|  CURLY_BRACKET_OPEN lLinkList lConstraintList lAssignmentList  CURLY_BRACKET_CLOSE
          		|  CURLY_BRACKET_OPEN lLinkList lAssignmentList lConstraintList CURLY_BRACKET_CLOSE
          		|  CURLY_BRACKET_OPEN lConstraintList lLinkList lAssignmentList CURLY_BRACKET_CLOSE
          )
        -> lConstraintList
           lAssignmentList
           lLinkList
         |( CURLY_BRACKET_OPEN lAssignmentList lConstraintList CURLY_BRACKET_CLOSE
          		|  CURLY_BRACKET_OPEN lConstraintList lAssignmentList CURLY_BRACKET_CLOSE
          )
        -> lConstraintList
           lAssignmentList
           ^(T["outgoingLinks"]) 
         |( CURLY_BRACKET_OPEN lLinkList lAssignmentList CURLY_BRACKET_CLOSE
          		|  CURLY_BRACKET_OPEN lAssignmentList lLinkList CURLY_BRACKET_CLOSE
          )
        -> ^(T["constraints"])
           lAssignmentList
           lLinkList 
         |( CURLY_BRACKET_OPEN lConstraintList lLinkList CURLY_BRACKET_CLOSE
          		|  CURLY_BRACKET_OPEN lLinkList lConstraintList CURLY_BRACKET_CLOSE
          )
        -> lConstraintList
           ^(T["attributeAssignments"])
           lLinkList    
           ;

lTypeReference:  i+=SLASH? (((i+=ID|i+=SOURCE|i+=TARGET) | i+=DDOT) i+=SLASH)* i+=ID -> {concat(T, $i)};

correspondence_pattern: CORRESPONDENCE CURLY_BRACKET_OPEN correspondence* CURLY_BRACKET_CLOSE -> correspondence*;

source_pattern: SOURCE CURLY_BRACKET_OPEN { scope = "source"; } box_decl* CURLY_BRACKET_CLOSE -> ^( T["source"] box_decl*);

target_pattern: TARGET CURLY_BRACKET_OPEN { scope = "target"; } box_decl* CURLY_BRACKET_CLOSE -> ^( T["target"] box_decl*);

box_decl: lBindingSemantics lBindingOperator lBindingState name=ID COLON type=lTypeReference lBoxBlock
     -> ^(TGGObjectVariable 
     						lBoxBlock
                            lBindingState
                            lBindingOperator
                            lBindingSemantics
                            ^(ATTRIBUTE T["domain"] T[scope])
                            ^(ATTRIBUTE T["name"] $name)
                            ^(ATTRIBUTE T["scopeTyped"] T[scope + "/" + $type.text])
                            ^(ATTRIBUTE T["type"] $type)
                        	^(ATTRIBUTE T["searchCategory"] T["type"])
		   				 	^(ATTRIBUTE T["search"] T["type"])
                            ^(ATTRIBUTE T["category"] T["tggObjectVariable"])
         );
         

lBindingSemantics: ( 
			NEGATE DOT id=NUM -> ^(ATTRIBUTE T["bindingSemantics"] T["negative"])
										^(ATTRIBUTE T["nacIndex"] $id) 
            | NEGATE -> ^(ATTRIBUTE T["bindingSemantics"] T["negative"])
            | -> ^(ATTRIBUTE T["bindingSemantics"] T["mandatory"])
            );
            
lBindingOperator: ( -> ^(ATTRIBUTE T["bindingOperator"] T["check_only"])
            | DPLUS -> ^(ATTRIBUTE T["bindingOperator"] T["create"])
            | DMINUS -> ^(ATTRIBUTE T["bindingOperator"] T["destroy"])
            );

lBindingState: ( -> ^(ATTRIBUTE T["bindingState"] T["unbound"])
            | AT -> ^(ATTRIBUTE T["bindingState"] T["bound"])
            );
          
lLinkList: lLink+ -> ^(T["outgoingLinks"] lLink+);

lLink: lBindingSemantics lBindingOperator MINUS target=ID RIGHT_ARROW target_object=ID
      -> ^(TGGLinkVariable 
      			^(ATTRIBUTE T["name"] $target)
         		^(ATTRIBUTE T["targetObject"] $target_object)
         		^(ATTRIBUTE T["category"] T["tggLink"])
                lBindingOperator
                lBindingSemantics
          );

lConstraintList: lConstraint+ -> ^(T["constraints"] lConstraint+);

lConstraint: attr=lExpression op=lConstraintOperator value=lExpression
      -> ^(T["constraint"]  
      				^(T["constraintExpression"] 
      						^(ATTRIBUTE T["operator"] $op)
      						^(T["leftExpression"] $attr)
      						^(T["rightExpression"] $value)                                                        
             		)
          );
          
lConstraintOperator: ( EQUALS -> T["equal"] )
                  |   ( NEQ    -> T["unequal"] )
                  |   ( LT     -> T["less"] )
                  |   ( GT     -> T["greater"] )
                  |   ( LEQ    -> T["less_or_equal"] )
                  |   ( GEQ    -> T["greater_or_equal"] );

lAssignmentList: lAssignment+ -> ^(T["attributeAssignments"] lAssignment+);

lAssignment: name=ID DOT attr=ID op=lAssignmentOperator value=lExpression
      -> ^(T["attributeAssignment"] 
      								^(ATTRIBUTE T["objectName"] $name)      								
  	                                ^(ATTRIBUTE T["attributeGuid"] $attr)      								
  	                                ^(ATTRIBUTE T["attributeName"] $attr)
  	                                ^(T["valueExpression"] $value)
  	      );
          
lAssignmentOperator: ASSIGN;

lBindingExpressions: ASSIGN value=lExpression -> ^(T["bindingExpression"] $value);

// ***********************************************************************
// Expressions

lExpression:lExpressionType 
	-> ^(T["Expression"] 
		lExpressionType);

lExpressionType: lLiteralExpression
          | lSimpleExpressionType
          | lMethodCallExpression
          | lAttributeValueExpression;

lSimpleExpression: lSimpleExpressionType 
	-> ^(T["Expression"]
		lSimpleExpressionType);
          
lSimpleExpressionType: lObjectVariableExpression
	             | lParameterExpression;
 

lLiteralExpression: lStringExpression
                  | lBooleanExpression
                  | lDoubleExpression
                  | lIntegerExpression
                  | lUndefinedExpression
                  | lNullExpression;
                  
lStringExpression: value=DOUBLE_QUOTED_STRING
				       -> ^( T["LiteralExpression"]
				           		^(ATTRIBUTE T["value"] $value)
				           		^(ATTRIBUTE T["LEType"] T["StringExpression"])
				           );
				                           
lBooleanExpression: (value=TRUE | value=FALSE)
				       -> ^( T["LiteralExpression"]  
				           		^(ATTRIBUTE T["value"] $value)
				           		^(ATTRIBUTE T["LEType"] T["BooleanExpression"])
				           );
				                           
lIntegerExpression: value=NUM
				       -> ^( T["LiteralExpression"]
				           		^(ATTRIBUTE T["value"] $value)
				           		^(ATTRIBUTE T["LEType"] T["IntegerExpression"])
				           );
				           
lDoubleExpression: value+=NUM value+=DOT value+=NUM
				       ->  ^(T["LiteralExpression"]
				          		^(ATTRIBUTE T["value"] {MOSLUtils.concat(T, $value)})
				          		^(ATTRIBUTE T["LEType"] T["DoubleExpression"])
				           );				           
				                           
lUndefinedExpression: value=SINGLE_QUOTED_STRING
				       ->  ^(T["LiteralExpression"]
				           		^(ATTRIBUTE T["value"] $value)
				           		^(ATTRIBUTE T["LEType"] T["UndefinedExpression"])
				           );
				                          
lNullExpression: value=NULL
				       ->  ^(T["LiteralExpression"]
				           		^(ATTRIBUTE T["value"] $value)
				           		^(ATTRIBUTE T["LEType"] T["NullExpression"])
				         	);

lObjectVariableExpression: AT name=ID
                       ->  ^(T["ObjectVariableExpression"] 
                       	  	 	^(ATTRIBUTE T["objectVariable"] $name)
                           		^(ATTRIBUTE T["searchCategory"] T["tggObjectVariable"])
						   		^(ATTRIBUTE T["search"] T["objectVariable"]));

lParameterExpression: DOLLAR name=ID
  				->  ^(T["ParameterExpression"]
				    		^(ATTRIBUTE T["parameterName"] $name)
				   	 		^(ATTRIBUTE T["searchCategory"] T["parameter"])
							^(ATTRIBUTE T["search"] T["parameterName"])
					);

lMethodCallExpression: target=lSimpleExpression DOT method=ID LEFT_PARENTHESIS lArgumentList? RIGHT_PARENTHESIS
                    -> ^(MethodCallExpression
                    		^(ATTRIBUTE T["name"] T["MCE:"+ $method.text + "()_ID=" + MOSLUtils.getIndex()])
                    		^(ATTRIBUTE T["methodName"] $method)
                    		^(ATTRIBUTE T["methodGuid"] $method)
                    		 $target
                    		^(ATTRIBUTE T["category"] T["typedExpression"])
                       		lArgumentList?				       		
				       );

lAttributeValueExpression: objectVariable=ID DOT attribute=ID
  				        ->  ^(T["AttributeValueExpression"]
  				        		^(ATTRIBUTE T["objectVariable"] $objectVariable)
  				        		^(ATTRIBUTE T["name"] T["AVE:"+ $objectVariable.text + "." + $attribute.text + "_ID=" + MOSLUtils.getIndex()])	
				             	^(ATTRIBUTE T["attribute"] $attribute)
				                ^(ATTRIBUTE T["attributeName"] $attribute)
				             	^(ATTRIBUTE T["searchCategory"] T["tggObjectVariable"])
								^(ATTRIBUTE T["search"] T["objectVariable"])
				             	^(ATTRIBUTE T["category"] T["typedExpression"])				             	
				            );
				            
// ***********************************************************************
// Argument lists

lArgumentList: ( lArgument COMMA ) * lArgument 
			-> ^(T["ownedParameterBinding"] 
				^(ATTRIBUTE T["nummberID"] T["" + MOSLUtils.getIndex()])
				lArgument+ );

lArgument: value=lObjectVariableExpression
            -> ^(T["valueExpression"] 
           			^(T["Expression"]	 $value))
           	| value=lParameterExpression
            -> ^(T["valueExpression"] 
           			^(T["Expression"]	 $value))
           | value=lLiteralExpression
            -> ^(T["valueExpression"] 
           			^(T["Expression"]	 $value))
         /*  | value=lMethodCallExpression
            -> ^(T["valueExpression"] 
           			^(T["Expression"]	 $value)) */
           | value=lAttributeValueExpression
            -> ^(T["valueExpression"] 
           			^(T["Expression"]	 $value));
           				 

				              
// Operations
operations_decl: OPERATIONS CURLY_BRACKET_OPEN operation_decl* CURLY_BRACKET_CLOSE -> operation_decl*;

operation_decl: name=ID LEFT_PARENTHESIS parameterlist_decl RIGHT_PARENTHESIS COLON type=lTypeReference 
           ->   ^(EOperation ^(T["parameters"] parameterlist_decl)
           					 ^(ATTRIBUTE T["name"] $name)
           					 ^(ATTRIBUTE T["returnType"] $type)
           					 ^(ATTRIBUTE T["category"] T["operation"])
           		);
           		
// Parameter lists

parameterlist_decl: parameters_decl* -> parameters_decl*;

parameters_decl: ( parameter_decl COMMA ) * parameter_decl -> parameter_decl+;

parameter_decl: name=ID COLON type=lTypeReference -> ^(EParameter ^(ATTRIBUTE T["type"] $type)
  													              ^(ATTRIBUTE T["name"] $name)
  													              ^(ATTRIBUTE T["category"] T["parameter"]));



// CSP

/*  lCSP: lCalledCSP+ -> ^(T["CalledCSPs"] lCalledCSP+);
  
  lCalledCSP: cspName=ID LEFT_PARENTHESIS (lCSPArgument COMMA)* lCSPArgument RIGHT_PARENTHESIS -> ^(T["CSP"] lCSPArgument+);
  
  lCSPArgument: lExpression  ->  ^(T["CSPArgument"] lExpression);
  */
  
    lCSP:
  ( constraints+=constraintRule
    | constraints+=constraintNegatedRule
  )+
    ->
      ^(
        ROOT
        ^(CONSTRAINTS_UPPER $constraints+)
       )
       
  ;
  
constraintRule
  :
  constraintToken=ID constraintBody
    ->
      ^($constraintToken  ^(NEGATED FALSE) constraintBody)
  ;
  
constraintBody: LEFT_PARENTHESIS
  (
    vars+=parameter
    | LITERAL 
  )
  (
    COMMA
    (
      vars+=parameter
      | LITERAL
    )
  )*
  RIGHT_PARENTHESIS  
  -> $vars+
  ;
  
constraintNegatedRule
  :
  NOT LEFT_PARENTHESIS constraintToken=ID constraintBody RIGHT_PARENTHESIS
    ->
      ^($constraintToken ^(NEGATED TRUE) constraintBody)
  ;

parameter
  :
  variable
  | literal
  ;

variable
  :
  ID
    -> ID
  | objectVariable=ID DOT attribute=ID
    ->
      ^(IMPLICIT_PARAMETER $objectVariable $attribute)
  ;

literal
  :
  (
    value=DOUBLE_QUOTED_STRING
    | value=NUM
  )
    ->
      ^(LITERAL $value)
  ;
  

  
			           

