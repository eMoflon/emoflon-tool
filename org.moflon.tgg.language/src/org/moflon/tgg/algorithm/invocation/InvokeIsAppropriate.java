package org.moflon.tgg.algorithm.invocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.moflon.tgg.language.analysis.RulesTable;
import org.moflon.tgg.runtime.EMoflonEdge;
import org.moflon.tgg.runtime.EObjectContainer;
import org.moflon.tgg.runtime.Match;
import org.moflon.tgg.runtime.RuntimePackage;

/**
 * Used to invoke "isAppropriate" methods. These methods are operationalized TGG
 * rules that check if a rule can be applied to parse the input graph.
 * 
 * @author anjorin
 *
 */
public class InvokeIsAppropriate implements Function<EObject, Stream<Match>> {

	private HashMap<String, HashSet<EOperation>> typeToIsAppr = new HashMap<String, HashSet<EOperation>>();

	public InvokeIsAppropriate(RulesTable lookupMethods) {
		getTypeToIsAppropriateMappings(lookupMethods);
	}

	@Override
	public Stream<Match> apply(EObject t) {
		String typeName = getTypeName(t);
		if (typeToIsAppr.containsKey(typeName))
			return typeToIsAppr.get(typeName).stream()
					.map(isAppropriate -> (EObjectContainer) InvokeUtil
							.invokeOperationWithSingleArg(isAppropriate.getEContainingClass(), isAppropriate, t))
					.flatMap(matchContainer -> matchContainer.getContents().stream()).map(Match.class::cast);

		return Stream.empty();
	}

	private void getTypeToIsAppropriateMappings(RulesTable lookupMethods) {
		lookupMethods.getRules().stream().flatMap(rule -> rule.getIsAppropriateMethods().stream())
				.forEach(entry -> fillTypeToIsAppMap(entry));
	}

	private void fillTypeToIsAppMap(EOperation isAppr) {
		EParameter isApprParam = isAppr.getEParameters().get(0);
		String typeName = null;

		if (isApprParam.getEType() == RuntimePackage.eINSTANCE.getEMoflonEdge())
			typeName = isApprParam.getName();
		else
			typeName = isApprParam.getEType().getName();

		if (!typeToIsAppr.containsKey(typeName))
			typeToIsAppr.put(typeName, new HashSet<EOperation>());

		typeToIsAppr.get(typeName).add(isAppr);
	}

	private String getTypeName(EObject obj) {
		if (obj instanceof EMoflonEdge)
			return "_edge_" + ((EMoflonEdge) obj).getName();
		else
			return obj.eClass().getName();

	}
}
