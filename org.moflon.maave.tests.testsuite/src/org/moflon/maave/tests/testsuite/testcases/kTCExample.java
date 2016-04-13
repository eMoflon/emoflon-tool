package org.moflon.maave.tests.testsuite.testcases;

import java.util.Date;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Test;
import org.moflon.maave.tests.testsuite.helper.ModelHelper;
import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisReport;
import org.moflon.maave.tool.analysis.confluence.ConfluenceFactory;
import org.moflon.maave.tool.analysis.confluence.DirectConfluenceModuloNFEQAnalyser;
import org.moflon.maave.tool.analysis.confluence.prettyprinter.ConfluenceAnalysisResultPrinter;
import org.moflon.maave.tool.graphtransformation.GraphTransformationSystem;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.graphtransformation.conditions.NegativeConstraint;
import org.moflon.maave.tool.sdm.stptransformation.MetaModelConstraintBuilder;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;

import de.tudarmstadt.maki.modeling.jvlc.JvlcPackage;

public class kTCExample {
	@Test
	public void testBatchKTC() {
		System.out.println("Started at " + new Date());
		System.out.println("");
		System.out.println("-------------------------------------------------------------");
		System.out.println("Starting kTCExample/testBatchKTC");

		JvlcPackage.eINSTANCE.getClass();
		EPackage pack = TestRunner.loadTestMM("de.tudarmstadt.maki.modeling.jvlc", "Jvlc");
		EClass clsSemester = (EClass) pack.getEClassifier("DistanceKTC");
		SymbGTRule rule1 = ModelHelper.getPattern(clsSemester, "runOnNode", "FindTriangle");
		SymbGTRule rule2 = ModelHelper.getPattern(clsSemester, "runOnNode", "FindTriangle2");
		ConfigurableMorphismClassFactory morClassFac = MatchingUtilsFactory.eINSTANCE
				.createConfigurableMorphismClassFactory();

		GraphTransformationSystem gts = GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
		gts.getRules().add(rule1);
//		gts.getRules().add(rule2);

		gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
		gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

		// Add ArityConstraints
		MetaModelConstraintBuilder constraintBuilder = StptransformationFactory.eINSTANCE
				.createMetaModelConstraintBuilder();
		NegativeConstraint mmC = constraintBuilder.buildConstraints(pack);
		for (SymbolicGraph nc : mmC.getAtomicNegativeConstraints()) {
			for (GraphNode gn : nc.getGraphNodes()) {
				if (gn.getType() == pack.getEClassifier("Node")) {
					gn.setType((EClass) pack.getEClassifier("KTCNode"));
				}
			}
			for (GraphNode gn : nc.getGraphNodes()) {
				if (gn.getType() == pack.getEClassifier("Edge")) {
					gn.setType((EClass) pack.getEClassifier("KTCLink"));
				}
			}
		}
		gts.getConstraints().add(mmC);
		// //Add user defined constraints
		// NegativeConstraint nC = ModelHelper.getUserDefConstraints(pack);
		// gts.getConstraints().add(nC);

		DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser = ConfluenceFactory.eINSTANCE
				.createDirectConfluenceModuloNFEQAnalyser();
		ConfluenceAnalysisReport report = directConfluenceAnalyser.checkConfluence(gts);
//		System.out.println(report);
		ConfluenceAnalysisResultPrinter.confluenceReportToTable(report);
		ConfluenceAnalysisResultPrinter.printConfluenceReport(report, true, false, true, true);
		
		System.out.println("Finished at " + new Date());

	}

}