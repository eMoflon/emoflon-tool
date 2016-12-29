package org.moflon.tgg.algorithm.invocation;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.moflon.tgg.runtime.AttributeConstraintsRuleResult;
import org.moflon.tgg.runtime.RuleResult;
import org.moflon.tgg.runtime.TripleMatch;

public class InvokeCheckAttributes implements Function<TripleMatch, RuleResult> {

	private EClass ruleClass;
	private EOperation op;

	public InvokeCheckAttributes(EClass ruleClass, EOperation op) {
		this.ruleClass = ruleClass;
		this.op = op;
	}

	@Override
	public AttributeConstraintsRuleResult apply(TripleMatch match) {

		EObject object = EcoreUtil.create((EClass) ruleClass);

		EList<Object> parameterValues = new BasicEList<Object>();

		parameterValues.add(match);

		try {
			return (AttributeConstraintsRuleResult) object.eInvoke(op, parameterValues);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;

	}

}
