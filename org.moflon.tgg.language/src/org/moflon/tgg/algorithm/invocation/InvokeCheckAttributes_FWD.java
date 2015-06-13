package org.moflon.tgg.algorithm.invocation;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.util.EcoreUtil;

import TGGRuntime.RuleResult;
import TGGRuntime.TripleMatch;

public class InvokeCheckAttributes_FWD implements
		Function<TripleMatch, RuleResult> {

	private EClass ruleClass;
	private EOperation op;

	public InvokeCheckAttributes_FWD(EClass ruleClass, EOperation op) {
		this.ruleClass = ruleClass;
		this.op = op;
	}

	@Override
	public RuleResult apply(TripleMatch match) {

		EObject object = EcoreUtil.create((EClass) ruleClass);

		  EList<Object> parameterValues = new BasicEList<Object>();
		  
	      parameterValues.add(match);

	     try {
			return (RuleResult) object.eInvoke(op, parameterValues);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		return null;
	    
	}



}
