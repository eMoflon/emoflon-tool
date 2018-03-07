package org.moflon.tgg.algorithm.modelgenerator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;

public class Extractor {

	public List<EOperation> extractGenerateModelMethods(EPackage tggPackage) {
		List<EOperation> methods = new ArrayList<EOperation>();

		EPackage rulesPackage = computeRulesPackage(tggPackage);

		for (EClassifier ruleClass : rulesPackage.getEClassifiers()) {
			EOperation generateModelOperation = findGenerateModelOperation(ruleClass);
			if (generateModelOperation != null) {
				methods.add(generateModelOperation);
			}
		}

		return methods;

	}

	private EPackage computeRulesPackage(EPackage tggPackage) {
		EPackage rulesPackage = null;
		for (EPackage subPackage : tggPackage.getESubpackages()) {
			if (subPackage.getName().equals("Rules")) {
				rulesPackage = subPackage;
			}
		}
		return rulesPackage;
	}

	private EOperation findGenerateModelOperation(EClassifier ruleClass) {
		EOperation generateModelOperation = null;

		if (ruleClass instanceof EClass) {
			EClass eClass = (EClass) ruleClass;
			for (EOperation eOperation : eClass.getEOperations()) {
				if (eOperation.getName().equals("generateModel")) {
					generateModelOperation = eOperation;
				}
			}
		}
		return generateModelOperation;
	}

}
