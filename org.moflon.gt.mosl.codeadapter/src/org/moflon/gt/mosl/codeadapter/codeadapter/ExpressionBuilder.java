package org.moflon.gt.mosl.codeadapter.codeadapter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.gervarro.democles.specification.emf.PatternBody;
import org.moflon.gt.mosl.moslgt.AbstractAttribute;
import org.moflon.gt.mosl.moslgt.Expression;
import org.moflon.gt.mosl.moslgt.ObjectVariableDefinition;

public class ExpressionBuilder {

	private Map<Class<? extends AbstractAttribute>,Map<Class<? extends Expression>, Function<ObjectVariableDefinition, Function<Expression, Consumer<PatternBody>>>>> expressionTransformerFunCache;
	
	private static ExpressionBuilder instance;
	
	private ExpressionBuilder(){
		expressionTransformerFunCache = new HashMap<>();
	}
	
	public static ExpressionBuilder getInstance(){
		if(instance == null)
			instance = new ExpressionBuilder();
		return instance;
	}
	
	public void addTranformerFun(Class<? extends AbstractAttribute> aaClass, Class<? extends Expression> exprClass, Function<ObjectVariableDefinition, Function<Expression, Consumer<PatternBody>>> transformerFun){
		Map<Class<? extends Expression>, Function<ObjectVariableDefinition, Function<Expression, Consumer<PatternBody>>>> exprClassMap = expressionTransformerFunCache.get(aaClass);
		if(exprClassMap == null){
			exprClassMap = new HashMap<>();
			expressionTransformerFunCache.put(aaClass, exprClassMap);
		}
		exprClassMap.put(exprClass, transformerFun);
	}
	
	public void transformExpression(ObjectVariableDefinition ov, AbstractAttribute abstractAttribute, PatternBody patternBody){
		expressionTransformerFunCache.entrySet().stream().forEach(aaClassEntry -> {
			if(aaClassEntry.getKey().isInstance(abstractAttribute)){
				aaClassEntry.getValue().entrySet().stream().forEach(exprEntry-> {
					if(exprEntry.getKey().isInstance(abstractAttribute.getValueExp())){
						exprEntry.getValue().apply(ov).apply(abstractAttribute.getValueExp()).accept(patternBody);
					}
				});
			}
		});
	}
}
