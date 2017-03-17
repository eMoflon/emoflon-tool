package org.moflon.gt.mosl.codeadapter.abstractutils;

import org.moflon.gt.mosl.moslgt.Operator;

public interface IOperatorRule {
	default boolean isCreated(Operator op){
		return op != null && "++".equals(op.getValue());
	}
	
	default boolean isDestroyed(Operator op){
		return op != null && "--".equals(op.getValue());
	}
	
	default boolean isCheckOnly(Operator op){
		return op == null || op.getValue() == null || "".equals(op.getValue());
	}

}
