package org.moflon.tgg.algorithm.invocation;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class InvokeUtil {
	public static EObject invokeOperationWithSingleArg(EObject target, EOperation operation, EObject singleArgument) {
		EClass targetClass = (EClass) target;

		// Create an actual object of the desired type 'target'
		EObject object = EcoreUtil.create(targetClass);

		EList<Object> parameterValues = new BasicEList<Object>();
		parameterValues.add(singleArgument);

		try {
			return (EObject) object.eInvoke(operation, parameterValues);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Object invokeOperationWithNArguments(EObject target, EOperation operation, EList<?> arguments) {
		EClass targetClass = (EClass) target;

		// Create an actual object of the desired type 'target'
		EObject object = EcoreUtil.create(targetClass);
		try {
			return object.eInvoke(operation, arguments);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

}
