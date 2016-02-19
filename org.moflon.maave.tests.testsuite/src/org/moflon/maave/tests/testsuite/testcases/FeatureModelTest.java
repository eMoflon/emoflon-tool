/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Test;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.maave.tool.analysis.AnalysisFactory;
import org.moflon.maave.tool.analysis.BasicSymbolicCriticalPairBuilder;
import org.moflon.maave.tool.analysis.DirectDerivationBuilder;
import org.moflon.maave.tool.analysis.JointlyEpiSetBuilder;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.DirectDerivationPairSet;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;

import org.moflon.maave.tests.lang.featuremodel.FeaturemodelPackage;
import SDMLanguage.SDMLanguagePackage;
import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.patterns.StoryPattern;

/**
 * @author fdeckwerth
 *
 */
public class FeatureModelTest {



	private StoryPattern getTestPattern(String className,String name){

		//    rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		//    rs.getURIConverter().getURIMap().put(URI.createPlatformResourceURI("/", true), URI.createFileURI(args[0] + "\\"));
		//    rs.getURIConverter().getURIMap().put(URI.createPlatformPluginURI("/DiagramChasingTestGen/", true), URI.createPlatformResourceURI("/DiagramChasingTestGen/", true));

		//URI testGeneratorURI =URI.createURI("DiagramChasingTestGen/model/DiagramChasingTestGen.ecore",true);
		FeaturemodelPackage.eINSTANCE.getClass();
		SDMLanguagePackage.eINSTANCE.getClass();
		EPackage pack = (EPackage) eMoflonEMFUtil
				.loadModel("../org.moflon.maave.tests.testgen.genfeaturemodel/model/Genfeaturemodel.ecore");
		EClass clazz = (EClass) pack.getEClassifier(className);
		MoflonEOperation eop = (MoflonEOperation) clazz.getEAllOperations()
				.stream().filter(x -> x.getName().equals(name)).findFirst()
				.get();
		StoryNode stn = (StoryNode) eop.getActivity().getOwnedActivityNode()
				.stream().filter(x -> x instanceof StoryNode).findFirst().get();
		return stn.getStoryPattern();
	}

//	@Test
//	public  void test1(){
//		System.out.println("Starting FeatureModelTest/Test1" );
//
//
//		StoryPattern pattern=getTestPattern("FeatureModelInstGen","exampleForMoveAttr");
//
//
//		Transformer transformer=StoryPatternToSymbGraTraRulesFactory.eINSTANCE.createTransformer();
//		DirecDerivationBuilder direcDerivationBuilder=AnalysisFactoryImpl.eINSTANCE.createSymbolicDirectDerivationBuilder();
//
//		SymbGTRule featureModelGTRule=transformer.transformStpToSymbGTRule(pattern);
//		featureModelGTRule.createMissingLabelNodes();
//		SymbolicGraph featureModel=featureModelGTRule.getLeft().getCodom();
//
//		for (GraphNode gn : featureModel.getGraphNodes()) {
//			for (EAttribute attrib : gn.getType().getEAllAttributes()) {
//				Assert.assertTrue(gn.getLabelEdge().stream().filter(le->le.getType()==attrib).count()==1);
//				Assert.assertTrue(gn.getLabelEdge().stream().filter(le->le.getTarget().getType()==attrib.getEAttributeType()).count()==1);
//			}
//		}
//
//		
//
//	}
//	@Test
//	public  void test2(){
//		System.out.println("Starting FeatureModelTest/Test2" );
//
//
//		StoryPattern pattern=getTestPattern("FeatureModelInstGen","exampleForMoveUpAttribute");
//
//
//		Transformer transformer=StoryPatternToSymbGraTraRulesFactory.eINSTANCE.createTransformer();
//		
//		SymbGTRule featureModelGTRule=transformer.transformStpToSymbGTRule(pattern);
//		featureModelGTRule.createMissingLabelNodes();
//		SymbolicGraph featureModel=featureModelGTRule.getLeft().getCodom();
//
//		for (GraphNode gn : featureModel.getGraphNodes()) {
//			for (EAttribute attrib : gn.getType().getEAllAttributes()) {
//				Assert.assertTrue(gn.getLabelEdge().stream().filter(le->le.getType()==attrib).count()==1);
//				Assert.assertTrue(gn.getLabelEdge().stream().filter(le->le.getTarget().getType()==attrib.getEAttributeType()).count()==1);
//			}
//		}
//		
//		IAttribSolver solver=new Z3AttribSolver();
//		solver.hasNonEmptySemantic(featureModel);
//		
//
//	}
//	@Test
//	public  void test3(){
//		System.out.println("Starting FeatureModelTest/Test3" );
//
//
//		StoryPattern pattern=getTestPattern("FeatureModelInstGen","exampleForMoveUpAttribute");
//		StoryPattern rule=getTestPattern("RefactoringRules","moveUpAttributeAltGroup");
//
//		Transformer transformer=StoryPatternToSymbGraTraRulesFactory.eINSTANCE.createTransformer();
//		
//		SymbGTRule featureModelGTRule=transformer.transformStpToSymbGTRule(pattern);
//		SymbolicGraph featureModel=featureModelGTRule.getLeft().getCodom();
//
//		SymbGTRule refactoringGTRule=transformer.transformStpToSymbGTRule(rule);
//		refactoringGTRule.makeRuleLazy();
//		
//		
//		
//		InjectiveEGraphMorphismFinder matcher= FirstOrderSymbolicGraphsMatchingFactory.eINSTANCE.createInjectiveEGraphMorphismFinder();
//		MorphismsSet matches=matcher.getAllMorphisms(refactoringGTRule.getLeft().getCodom(), featureModel);
//		Assert.assertTrue(matches.getMorphisms().size()==2);
//		
//		LazyDirectDerivationBuilder derBuilder= AnalysisFactoryImpl.eINSTANCE.createLazyDirectDerivationBuilder();
//		DirectDerivation derivation=derBuilder.deriveDirectDerivation(refactoringGTRule, matches.getMorphisms().get(0));
//		
//		IAttribSolver solver=new Z3AttribSolver();
//		Assert.assertTrue(solver.hasNonEmptySemantic(derivation.getComatch().getCodom()));
//		
//
//	}
//	@Test
//   public  void test4(){
//      System.out.println("Starting FeatureModelTest/Test4" );
//
//
//      StoryPattern pattern=getTestPattern("FeatureModelInstGen","exampleForMoveUpAttribute");
//      StoryPattern rule=getTestPattern("RefactoringRules","moveUpAttributeAltGroup");
//
//      Transformer transformer=StoryPatternToSymbGraTraRulesFactory.eINSTANCE.createTransformer();
//      
//      SymbGTRule featureModelGTRule=transformer.transformStpToSymbGTRule(pattern);
//      
//
//      SymbGTRule refactoringGTRule=transformer.transformStpToSymbGTRule(rule);
//     
//     
//     
//      JointlyEpiSetBuilder improvedBuilder=AnalysisFactory.eINSTANCE.createImprovedJointlyEpiSetBuilder();
//      
//      MorphismPairSet pairSetImp=improvedBuilder.getAllMinimalContexts(refactoringGTRule.getLeft().getCodom(),featureModelGTRule.getLeft().getCodom());
//     
//      
//
//   }
	@Test
   public  void test5(){
      System.out.println("Starting FeatureModelTest/Test5 : Testing CPA for rules moveUpAttributeAltGroup and scaleAttribute" );


      StoryPattern stpRule1=getTestPattern("RefactoringRules","moveUpAttributeAltGroup");
      StoryPattern stpRule2=getTestPattern("RefactoringRules","scaleAttribute");

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      
      SymbGTRule rule1=transformer.transformStpToSymbGTRule(stpRule1);
      

      SymbGTRule rule2=transformer.transformStpToSymbGTRule(stpRule2);
     
    
     
      BasicSymbolicCriticalPairBuilder criticalPairBuilder=AnalysisFactory.eINSTANCE.createBasicSymbolicCriticalPairBuilder();
      DirectDerivationBuilder derBuilder=AnalysisFactory.eINSTANCE.createSymbolicDirectDerivationBuilder();
      JointlyEpiSetBuilder jointlyEpiSetBuilder=AnalysisFactory.eINSTANCE.createNonEmptySemanticJointlyEpiSetBuilder();
      DirectDerivationPairSet criticalPairs=criticalPairBuilder.getAllCriticalPairs(rule1, rule2,derBuilder,jointlyEpiSetBuilder);
      Assert.assertTrue(criticalPairs.getPairsOfDirectDerivations().size()==2);
          
      

   }
//	@Test
//	public  void test2(){
//		System.out.println("Starting FeatureModelTest/Test2" );
//
//
//		
//		StoryPattern pattern=getTestPattern("FeatureModelInstGen","exampleForMoveUpAttribute");
//
//
//		Transformer transformer=StoryPatternToSymbGraTraRulesFactory.eINSTANCE.createTransformer();
//		DirecDerivationBuilder direcDerivationBuilder=AnalysisFactoryImpl.eINSTANCE.createBasicSymbolicDirectDerivationBuilder();
//
//
//		SymbGTRule gtRule=transformer.transformStpToSymbGTRule(rule);
//		gtRule.makeRuleLazy();
//		TestRunner.saveTestResult(gtRule, "moveAttribute");
//		SymbolicGraph graphG=transformer.transformStpToSymbGTRule(pattern).getLeft().getCodom();
//		TestRunner.saveTestResult(graphG, "instance");
//		DirectDerivationSet directDerivations=direcDerivationBuilder.deriveAllDirectDerivations(gtRule, graphG);
//		int x=0;
//		System.out.println(x);
//
//	}


}
