parser grammar PatternParser;

options {
  backtrack = true;
  language = Java;
  tokenVocab = PatternLexer;
  output = AST;
  superClass = 'org.moflon.mosl.MOSLParser';
}

// List of tokens used only used to structure the tree
tokens {
  ATTRIBUTE;
  T;
  Index;
  ObjectVariable;
  Pattern;
  StatementPattern;
  LinkVariable;
  ObjectConstraint;
  AttributeAssignment;
  MethodCallExpression;
}

@header {
package org.moflon.mosl.pattern.parser; 
import org.moflon.mosl.MOSLUtils;
import java.util.Map;
import java.util.HashMap;
}

@members{ 
	private int lastGeneratedNacIndex=0;
	
	private Map<Integer,Integer> nacMap = new HashMap<>();
	
	private int getGeneratedNac(){ 
		return lastGeneratedNacIndex++;
	}
	
		private int setNac(int userNac){ 
			if(nacMap.containsKey(userNac)){
				return nacMap.get(userNac);
			} else{
				int nacIndex=getGeneratedNac();
				nacMap.put(userNac, nacIndex);
				return nacIndex;
			}
		}
}

// parser rules:
main: lPattern* EOF -> lPattern*;

lTypeReference: i+=SLASH? ((i+=ID | i+=DDOT) i+=SLASH)* i+=ID -> {MOSLUtils.concat(T, $i)};
                      
lPattern: PATTERN name=lName CURLY_BRACKET_OPEN lInnerPattern CURLY_BRACKET_CLOSE
         -> ^(Pattern
         		^(ATTRIBUTE T["name"] $name)
         		^(ATTRIBUTE T["category"] T["pattern"])
         		lInnerPattern
          );
                      
lName: ID | SINGLE_QUOTED_STRING;

lInnerPattern: lObjectVariable* -> ^(T["objectVariables"] lObjectVariable*);

lObjectVariable: lBindingSemantics lBindingOperator lBindingState name=ID COLON type=lTypeReference lBoxBlock
     -> ^(ObjectVariable 
     					 ^(ATTRIBUTE T["name"] $name)
     					 ^(ATTRIBUTE T["type"] $type)
     					 ^(ATTRIBUTE T["category"] T["objectVariable"])
     					 ^(ATTRIBUTE T["searchCategory"] T["type"])
						 ^(ATTRIBUTE T["search"] T["type"])
						 lNac
     					 lBindingState
     					 lBindingOperator
     					 lBindingSemantics
     					 lBoxBlock    
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

lBindingSemantics: NOT -> ^(ATTRIBUTE T["bindingSemantics"] T["negative"])
            			 ^(ATTRIBUTE T["nacIndex"] T["" + getGeneratedNac()]) 
    | NOT DOT userNac=NUM  -> ^(ATTRIBUTE T["bindingSemantics"] T["negative"])  
    						  ^(ATTRIBUTE T["nacIndex"] T["" + setNac(Integer.parseInt($userNac.text))])
    | -> ^(ATTRIBUTE T["bindingSemantics"] T["mandatory"]) 
       ^(ATTRIBUTE T["nacIndex"] T["-1"]);
            
lBindingOperator: ( -> ^(ATTRIBUTE T["bindingOperator"] T["check_only"])
            | DPLUS -> ^(ATTRIBUTE T["bindingOperator"] T["create"])
            | DMINUS -> ^(ATTRIBUTE T["bindingOperator"] T["destroy"])
            );

lBindingState: ( -> ^(ATTRIBUTE T["bindingState"] T["unbound"])
            | AT -> ^(ATTRIBUTE T["bindingState"] T["bound"])
            );

lLinkList: lLink+ -> ^(T["outgoingLinks"] lLink+);

lLink: lBindingSemantics lBindingOperator MINUS target=ID RIGHT_ARROW target_object=ID
      -> ^(LinkVariable 
      			^(ATTRIBUTE T["name"] $target)
      			^(ATTRIBUTE T["guid"] $target)
         		^(ATTRIBUTE T["targetObject"] $target_object)
         		^(ATTRIBUTE T["category"] T["link"])
         		^(ATTRIBUTE T["searchCategory"] T["objectVariable"])
				^(ATTRIBUTE T["search"] T["targetObject"])
				^(ATTRIBUTE T["searchCategory"] T["reference"])
				^(ATTRIBUTE T["search"] T["guid"])
				lNac
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
  	                                ^(ATTRIBUTE T["searchCategory"] T["objectVariable"])
									^(ATTRIBUTE T["search"] T["objectName"])
									^(ATTRIBUTE T["searchCategory"] T["attribute"])
									^(ATTRIBUTE T["search"] T["attributeGuid"])
  	                                ^(T["valueExpression"] $value)
  	      );
          
lAssignmentOperator: ASSIGN;

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
                           		^(ATTRIBUTE T["searchCategory"] T["objectVariable"])
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
				             	^(ATTRIBUTE T["searchCategory"] T["objectVariable"])
								^(ATTRIBUTE T["search"] T["objectVariable"])
				             	^(ATTRIBUTE T["category"] T["typedExpression"])				             	
				            );
				            
// ***********************************************************************
// Argument lists

lArgumentList: ( lArgument COMMA ) * lArgument 
			-> ^(T["ownedParameterBinding"] 
				^(ATTRIBUTE T["nummberID"] T["" + MOSLUtils.getIndex()])
				lArgument+ );

lArgument: value=lExpression
            -> ^(T["valueExpression"] 
           			 $value);
           				 
