package org.moflon.gt.mosl.codeadapter.abstractutils;

import java.util.function.Function;

import org.gervarro.democles.specification.emf.Variable;
import org.moflon.gt.mosl.moslgt.Operator;

public class AbstractOperatorRule {
	protected boolean isCreated(Operator op){
		return op != null && "++".equals(op.getValue());
	}
	
	protected boolean isDestroyed(Operator op){
		return op != null && "--".equals(op.getValue());
	}
	
	protected boolean isCheckOnly(Operator op){
		return op == null || op.getValue() == null || "".equals(op.getValue());
	}

}
