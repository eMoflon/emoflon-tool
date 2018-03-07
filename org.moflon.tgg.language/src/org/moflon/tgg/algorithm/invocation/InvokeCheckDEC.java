package org.moflon.tgg.algorithm.invocation;

import java.util.function.Predicate;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.moflon.tgg.algorithm.datastructures.TripleMatch;
import org.moflon.tgg.language.analysis.RulesTable;

public class InvokeCheckDEC implements Predicate<TripleMatch> {

	private RulesTable lookupMethods;

	public InvokeCheckDEC(RulesTable lookupMethods) {
		this.lookupMethods = lookupMethods;
	}

	@Override
	public boolean test(TripleMatch match) {
		EOperation checkDECop = lookupMethods.getRules().stream()
				.filter(r -> r.getRuleName().equals(match.getRuleName())).findAny().get().getCheckDECMethod();
		EList<EParameter> checkDECparameters = checkDECop.getEParameters();

		EList<EObject> parameterBindings = new BasicEList<>();
		for (int i = 0; i < checkDECparameters.size(); i++) {
			parameterBindings.add(i, match.getNode(checkDECparameters.get(i).getName()));
		}
		return (Boolean) InvokeUtil.invokeOperationWithNArguments(checkDECop.getEContainingClass(), checkDECop,
				parameterBindings);
	}
}
