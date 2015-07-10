parser grammar EclassParser;

options {
  backtrack = true;
  language = Java;
  tokenVocab = EclassLexer;
  output = AST;
  superClass = 'org.moflon.mosl.MOSLParser';
}

// List of tokens used only used to structure the tree
tokens {
  ATTRIBUTE;
  T;
  Index;
  
  EPackage;
  EClass;
  EAttribute;
  EReference;
  EOperation;
  EParameter;
  EEnum;
  EEnumLiteral;
  EDatatype;  
  Typedef;  
  Activity;
  ActivityNode;  
  SequentialActivityNode;
  IfActivityNode;
  ForEachActivityNode;
  WhileActivityNode;
  DoActivityNode;
  MethodCallExpression;
}

@header {
package org.moflon.mosl.eclass.parser; 
import org.moflon.mosl.MOSLUtils;
import java.util.List;
import java.util.ArrayList;
}

// All rule names are RIGHT_ARROWactored from [name]_decl to l[Name]
// The prefix "l" stands for language

// Rules with plural in it's name are renamed from [name]s to [name]List

// parser rules:
main: lClasseList -> ^(T["classes"] lClasseList);

lClasseList: lClass+ -> lClass+ |
			  lEnum+ -> lEnum+ |
			  lDatatype+ -> lDatatype+;

lDatatype: DATATYPE name=ID ASSIGN javaType=SINGLE_QUOTED_STRING
        -> ^(EDatatype
              		^(ATTRIBUTE T["name"] $name)
                    ^(ATTRIBUTE T["instanceTypeName"] $javaType)
                    ^(ATTRIBUTE T["category"] T["edatatype"]) 
        );

lEnum: ENUM name=ID CURLY_BRACKET_OPEN lLiteralList CURLY_BRACKET_CLOSE
        -> ^(EEnum 
       			^(ATTRIBUTE T["name"] $name)
       			^(ATTRIBUTE T["category"] T["eenum"])
                 lLiteralList
        );

lLiteralList: lLiteral* -> ^(T["literals"] lLiteral*);

lLiteral: name=ID ASSIGN value=NUM 
		-> ^(EEnumLiteral 
				^(ATTRIBUTE T["name"] $name)
				^(ATTRIBUTE T["value"] $value)
		);

lClass: lAbstract CLASS name=ID lExtendList? 
				  CURLY_BRACKET_OPEN 
						lTypeDefinitionList
						lAttributeList
						lReferenceList 
						lOperationList 
				  CURLY_BRACKET_CLOSE
        -> ^(EClass 
        		^(ATTRIBUTE T["name"] $name) 
        		^(ATTRIBUTE T["category"] T["eclass"]) 
                     lAbstract
                     lExtendList?
                     lReferenceList
                     lAttributeList
                     lOperationList
                     lTypeDefinitionList
                     ^(T["etypeParameters"])
                     ^(T["genericSuperTypes"]));

lAbstract:  -> ^(ATTRIBUTE T["isAbstract"] T["false"]) |
				 ABSTRACT -> ^(ATTRIBUTE T["isAbstract"] T["true"]
			); 
                     
lTypeDefinitionList: lTypeDefinition* ->  ^(T["typedefs"] lTypeDefinition*);

lTypeDefinition: TYPEDEF name=ID type=lTypeReference 
		-> ^(Typedef 
				^(ATTRIBUTE T["name"] $name) ^(ATTRIBUTE T["value"] $type)
                ^(ATTRIBUTE T["searchCategory"] T["type"])
				^(ATTRIBUTE T["search"] T["name"]));
                     
lExtendList: EXTENDS (lExtends COMMA)* lExtends 
		-> ^(T["BaseClasses"] lExtends+);			 

lExtends: ext=lTypeReference -> ^(T["baseClass"]
								^(ATTRIBUTE T["baseClass"] $ext)
								^(ATTRIBUTE T["searchCategory"] T["type"])
								^(ATTRIBUTE T["search"] T["baseClass"]));


lAttributeList: lAttribute* -> ^(T["attributes"] lAttribute*);

lReferenceList: lReference* -> ^(T["references"] lReference*);

lOperationList: lOperation* -> ^(T["operations"] lOperation*);

lAttribute: name=ID COLON type=lTypeReference
        -> ^(EAttribute 
        		^(ATTRIBUTE T["type"] $type)
                ^(ATTRIBUTE T["name"] $name)
                ^(ATTRIBUTE T["category"] T["attribute"])
                ^(ATTRIBUTE T["searchCategory"] T["type"])
				^(ATTRIBUTE T["search"] T["type"])
        );

lReference: lContainment MINUS name=ID  lMultiplicity RIGHT_ARROW type=lTypeReference
        -> ^(EReference 
           		^(ATTRIBUTE T["name"] $name)
           		^(ATTRIBUTE T["type"] $type)
           		^(ATTRIBUTE T["category"] T["reference"]) 
           		^(ATTRIBUTE T["searchCategory"] T["type"])
				^(ATTRIBUTE T["search"] T["type"])
           		lContainment
           		lMultiplicity		         					 
        );
           		
lContainment: ( -> ^(ATTRIBUTE T["containment"] T["false"]) | DIAMOND -> ^(ATTRIBUTE T["containment"] T["true"]));

lMultiplicity: LEFT_PARENTHESIS lower=NUM DOUBLE_DOT upper=lUpperDecision RIGHT_PARENTHESIS
        -> ^(ATTRIBUTE T["lowerBound"] $lower)
           ^(ATTRIBUTE T["upperBound"] $upper)
        ;
                   
lUpperDecision: (NUM -> NUM | STAR -> T["-1"]);

lOperation: name=ID LEFT_PARENTHESIS lParameterList? RIGHT_PARENTHESIS COLON type=lTypeReference lOperationBody?
        -> ^(EOperation 
           		^(T["parameters"] lParameterList?)
           		lOperationBody?
           		^(ATTRIBUTE T["name"] $name)
           		^(ATTRIBUTE T["returnType"] $type)
           		^(ATTRIBUTE T["category"] T["operation"])
           		^(ATTRIBUTE T["searchCategory"] T["type"])
				^(ATTRIBUTE T["search"] T["returnType"])
        )
        | name=ID LEFT_PARENTHESIS lParameterList? RIGHT_PARENTHESIS lOperationBody?
        -> ^(EOperation 
           		^(T["parameters"] lParameterList?)
           		lOperationBody?
           		^(ATTRIBUTE T["name"] $name)
           		^(ATTRIBUTE T["returnType"] T["void"])
           		^(ATTRIBUTE T["category"] T["operation"])
        );
           		
lParameterList: ( lParameter COMMA ) * lParameter ->  lParameter+;

lParameter: name=ID COLON type=lTypeReference 
		-> ^(EParameter 
				^(ATTRIBUTE T["name"] $name)
				^(ATTRIBUTE T["type"] $type)
				^(ATTRIBUTE T["nummberID"] T["" + MOSLUtils.getIndex()])
				^(ATTRIBUTE T["category"] T["parameter"])													              
                ^(ATTRIBUTE T["searchCategory"] T["type"])
				^(ATTRIBUTE T["search"] T["type"])
  		);

lTypeReference:  i+=SLASH? ((i+=ID | i+=DOUBLE_DOT) i+=SLASH)* i+=ID -> {MOSLUtils.concat(T, $i)};

// ***********************************************************************
// Operation and Statements

lOperationBody: lMethodBody  -> ^(Activity lMethodBody);

lMethodBody: CURLY_BRACKET_OPEN lBody CURLY_BRACKET_CLOSE -> lBody;

lBody: lClosedStatementList? -> ^(T["body"] lClosedStatementList?);

lStatementList: lStatement+ -> ^(T["stmt_list"] lStatement+);

lClosedStatementList: lStatement* lReturnStatement -> ^(T["stmt_list"] lStatement* lReturnStatement)
					| lStatement* lClosedIfStatement -> ^(T["stmt_list"] lStatement* lClosedIfStatement);

lClosedIfStatement:IF lNegation lBooleanStatement lClosedThenBlock lClosedElseBlock
		-> ^( T["stmt"]
		       ^(T["if_stmt"]
				  lNegation
				  lBooleanStatement
				  lClosedThenBlock
				  lClosedElseBlock			
		));

lClosedThenBlock: CURLY_BRACKET_OPEN lClosedStatementList CURLY_BRACKET_CLOSE -> ^(T["then_branch"] lClosedStatementList)  ;

lClosedElseBlock: ELSE CURLY_BRACKET_OPEN lClosedStatementList CURLY_BRACKET_CLOSE -> ^(T["else_branch"] lClosedStatementList);

lStatement: lStatementType -> ^(T["stmt"] lStatementType);

lStatementType:	lBooleanStatement -> lBooleanStatement 
			  |	lIfStatement -> lIfStatement
			  |	lLoopStatement -> lLoopStatement;

lReturnStatement: RETURN lExpression? -> ^(T["stmt"] ^(T["simple_stmt"] ^(T["return_stmt"] lExpression?)));

lBooleanStatement: lBooleanPatternStatement -> ^(T["simple_stmt"] lBooleanPatternStatement);

lBooleanPatternStatement: lPatternStatement -> lPatternStatement |
						  lMethodCallStatement -> lMethodCallStatement;

lPatternStatement: SQUARED_BRACKET_OPEN name=ID SQUARED_BRACKET_CLOSE 
			-> ^(T["pattern_stmt"] $name 
				^(ATTRIBUTE T["pattern"] $name)
			    ^(ATTRIBUTE T["searchCategory"] T["patternFile"])
				^(ATTRIBUTE T["search"] T["pattern"]));

lMethodCallStatement: LT lMethodCallExpression GT -> ^(T["call_stmt"] ^(T["Expression"] lMethodCallExpression));

lIfStatement:IF lNegation lBooleanStatement lThenBlock? lElseBlock?
		-> ^(T["if_stmt"]
				lNegation
				lBooleanStatement
				lThenBlock?
				lElseBlock?				
		)
		| IF lNegation lBooleanStatement lClosedThenBlock lElseBlock?
		-> ^(T["if_stmt"]
				lNegation
				lBooleanStatement
				lClosedThenBlock
				lElseBlock?				
		)
		| IF lNegation lBooleanStatement lThenBlock? lClosedElseBlock
		-> ^(T["if_stmt"]
				lNegation
				lBooleanStatement
				lThenBlock?
				lClosedElseBlock				
		);

lThenBlock: CURLY_BRACKET_OPEN lStatementList? CURLY_BRACKET_CLOSE -> ^(T["then_branch"] lStatementList?)  ;

lElseBlock: ELSE CURLY_BRACKET_OPEN lStatementList? CURLY_BRACKET_CLOSE -> ^(T["else_branch"] lStatementList?);

lLoopStatement: lForEachStatement -> lForEachStatement |
				lWhileStatement -> lWhileStatement |
				lDoStatement -> lDoStatement;

lForEachStatement: FOREACH lPatternStatement lForEachBlock?
		-> ^(T["foreach_stmt"]
				lPatternStatement
				lForEachBlock?
		);

lForEachBlock: CURLY_BRACKET_OPEN lStatementList? CURLY_BRACKET_CLOSE -> ^(T["eachTime"] lStatementList?);

lWhileStatement: WHILE lNegation lBooleanStatement lWhileBody 
		-> ^(T["while_stmt"]
			lNegation
			lBooleanStatement
			lWhileBody
		);

lWhileBody: CURLY_BRACKET_OPEN lStatementList CURLY_BRACKET_CLOSE -> ^(T["while_loop"] lStatementList);

lDoStatement: DO lDoBody WHILE lNegation lBooleanStatement 
		-> ^(T["do_stmt"]
			lNegation
			lDoBody
			lBooleanStatement
		);

lDoBody: CURLY_BRACKET_OPEN lStatementList CURLY_BRACKET_CLOSE -> ^(T["do_loop"] lStatementList);

lNegation: ( -> ^(ATTRIBUTE T["negated"] T["false"]) | NOT -> ^(ATTRIBUTE T["negated"] T["true"]));
         
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

lMethodCallExpression: target=lObjectVariableExpression DOT method=ID LEFT_PARENTHESIS lArgumentList? RIGHT_PARENTHESIS
                    -> ^(MethodCallExpression
                    		^(ATTRIBUTE T["name"] T["MCE:"+ $method.text + "()_ID=" + MOSLUtils.getIndex()])
                    		^(ATTRIBUTE T["methodName"] $method)
                    		^(ATTRIBUTE T["methodGuid"] $method)
                    		^(T["Expression"] $target)
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
           				 

