package org.moflon.maave.tests.testsuite.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.moflon.maave.tool.graphtransformation.GlobalConstraint;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.graphtransformation.conditions.ConditionsFactory;
import org.moflon.maave.tool.sdm.stptransformation.MetaModelConstraintBuilder;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;

public class ModelHelper {
	private static final Transformer transformer = StptransformationFactory.eINSTANCE.createTransformer();

	public static SymbGTRule getRule(EClass cls, String name) {
		MoflonEOperation op1 = (MoflonEOperation) cls.getEOperations().stream().filter(x -> x.getName().equals(name))
				.findFirst().get();
		if (op1 == null) {
			throw new NullPointerException("No operation found with name:" + name + " in class" + cls.getName());
		}
		StoryNode stn1 = (StoryNode) op1.getActivity().getOwnedActivityNode().stream()
				.filter(x -> x instanceof StoryNode).collect(Collectors.toList()).get(0);
		return transformer.transformStpToProjGTRule(stn1.getStoryPattern());
	}

	public static SymbGTRule getPattern(EClass cls, String operationName, String activityNodeName) {
		MoflonEOperation op1 = (MoflonEOperation) cls.getEOperations().stream()
				.filter(x -> x.getName().equals(operationName)).findFirst().get();
		if (op1 == null) {
			throw new NullPointerException(
					"No operation found with name:" + operationName + " in class" + cls.getName());
		}
		StoryNode stn1 = (StoryNode) op1.getActivity().getOwnedActivityNode().stream()
				.filter(x -> x instanceof StoryNode).filter(x -> activityNodeName.equals(x.getName()))
				.collect(Collectors.toList()).get(0);
		return transformer.transformStpToProjGTRule(stn1.getStoryPattern());
	}

	public static GlobalConstraint getUserDefConstraints(EPackage pack) {
		// UserDefConstraints
		EClass clsConstr = (EClass) pack.getEClassifier("UserDefinedConstraints");
		List<EOperation> ncOps = clsConstr.getEOperations().stream().filter(x -> x.getName().startsWith("_NC_")).collect(Collectors.toList());

		ConfigurableMorphismClassFactory morClassFac = MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
		
		GlobalConstraint gc=GraphtransformationFactory.eINSTANCE.createGlobalConstraint();
		gc.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
		
		MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
		for (EOperation eOperation : ncOps) {
			MoflonEOperation mEOp = (MoflonEOperation) eOperation;
			StoryNode constraintStn = (StoryNode) mEOp.getActivity().getOwnedActivityNode().stream().filter(x -> x instanceof StoryNode).findAny().get();
			SymbGTRule ruleC = transformer.transformStpToProjGTRule(constraintStn.getStoryPattern());
			SymbolicGraph nC=ruleC.getLeft().getCodom();
			nC.setName(ruleC.getName());
			gc.getConditions().add(constraintBuilder.getNac(nC));

		}

		return gc;
	}
}
