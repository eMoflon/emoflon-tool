/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Test;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.maave.tests.lang.featuremodel.FeaturemodelPackage;
import org.moflon.maave.tests.testgen.genfeaturemodel.GenfeaturemodelPackage;
import org.moflon.maave.tool.analysis.AnalysisFactory;
import org.moflon.maave.tool.analysis.DirectConfluenceAnalyser;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;

import SDMLanguage.SDMLanguagePackage;
import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.patterns.StoryPattern;

/**
 * @author fdeckwerth
 *
 */
public class ModelsPaperExample {



	private StoryPattern getTestPattern(String className,String name){

		//    rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		//    rs.getURIConverter().getURIMap().put(URI.createPlatformResourceURI("/", true), URI.createFileURI(args[0] + "\\"));
		//    rs.getURIConverter().getURIMap().put(URI.createPlatformPluginURI("/DiagramChasingTestGen/", true), URI.createPlatformResourceURI("/DiagramChasingTestGen/", true));

		//URI testGeneratorURI =URI.createURI("DiagramChasingTestGen/model/DiagramChasingTestGen.ecore",true);
		FeaturemodelPackage.eINSTANCE.eClass();
      SDMLanguagePackage.eINSTANCE.getClass();
      GenfeaturemodelPackage.eINSTANCE.getClass();
      EPackage pack = (EPackage) eMoflonEMFUtil
            .loadModel("../FeatureModelTestGen/model/FeatureModelTestGen.ecore");
      EClass clazz = (EClass) pack.getEClassifier(className);
      MoflonEOperation eop = (MoflonEOperation) clazz.getEAllOperations()
            .stream().filter(x -> x.getName().equals(name)).findFirst()
            .get();
      StoryNode stn = (StoryNode) eop.getActivity().getOwnedActivityNode()
            .stream().filter(x -> x instanceof StoryNode).findFirst().get();
      return stn.getStoryPattern();
	}

//	@Test
//	public  void testAC(){
//		System.out.println("Starting ModelsPaperExample/TestAC" );
//
//
//		StoryPattern stpRule1=getTestPattern("Rules","ruleA2");
//		StoryPattern stpRule2=getTestPattern("Rules","ruleC2");
//
//
//		Transformer transformer=StoryPatternToSymbGraTraRulesFactory.eINSTANCE.createTransformer();
//		SymbGTRule ruleA=transformer.transformStpToSymbGTRule(stpRule1);
//		SymbGTRule ruleB=transformer.transformStpToSymbGTRule(stpRule2);
//		ruleA.makeRuleLazy();
//		ruleB.makeRuleLazy();
//		
//		DirectConfluenceAnalyser directConfluenceAnalyser=AnalysisFactory.eINSTANCE.createSemanticDirectConfluenceAnalyser();
//		Assert.assertTrue(directConfluenceAnalyser.checkDirectConfluence(ruleA, ruleB).isValid());
//	}
//	
//	@Test
//   public  void testBB(){
//      System.out.println("Starting ModelsPaperExample/TestBB" );
//
//
//      StoryPattern stpRule1=getTestPattern("Rules","ruleB2");
//      StoryPattern stpRule2=getTestPattern("Rules","ruleB2");
//
//
//      Transformer transformer=StoryPatternToSymbGraTraRulesFactory.eINSTANCE.createTransformer();
//      SymbGTRule ruleA=transformer.transformStpToSymbGTRule(stpRule1);
//      SymbGTRule ruleB=transformer.transformStpToSymbGTRule(stpRule2);
//      ruleA.makeRuleLazy();
//      ruleB.makeRuleLazy();
//      
//      DirectConfluenceAnalyser directConfluenceAnalyser=AnalysisFactory.eINSTANCE.createSemanticDirectConfluenceAnalyser();
//      Assert.assertTrue(directConfluenceAnalyser.checkDirectConfluence(ruleA, ruleB).isValid());
//   }
//	@Test
//   public  void testCC(){
//      System.out.println("Starting ModelsPaperExample/TestCC" );
//
//
//      StoryPattern stpRule1=getTestPattern("Rules","ruleC2");
//      StoryPattern stpRule2=getTestPattern("Rules","ruleC2");
//
//
//      Transformer transformer=StoryPatternToSymbGraTraRulesFactory.eINSTANCE.createTransformer();
//      SymbGTRule ruleA=transformer.transformStpToSymbGTRule(stpRule1);
//      SymbGTRule ruleB=transformer.transformStpToSymbGTRule(stpRule2);
//      ruleA.makeRuleLazy();
//      ruleB.makeRuleLazy();
//      
//      DirectConfluenceAnalyser directConfluenceAnalyser=AnalysisFactory.eINSTANCE.createSemanticDirectConfluenceAnalyser();
//      Assert.assertTrue(directConfluenceAnalyser.checkDirectConfluence(ruleA, ruleB).isValid());
//   }
//	
//	@Test
//   public  void testBC(){
//      System.out.println("Starting ModelsPaperExample/TestBC" );
//
//
//      StoryPattern stpRule1=getTestPattern("Rules","ruleB2");
//      StoryPattern stpRule2=getTestPattern("Rules","ruleC2");
//
//
//      Transformer transformer=StoryPatternToSymbGraTraRulesFactory.eINSTANCE.createTransformer();
//      SymbGTRule ruleA=transformer.transformStpToSymbGTRule(stpRule1);
//      SymbGTRule ruleB=transformer.transformStpToSymbGTRule(stpRule2);
//      ruleA.makeRuleLazy();
//      ruleB.makeRuleLazy();
//      
//      DirectConfluenceAnalyser directConfluenceAnalyser=AnalysisFactory.eINSTANCE.createSemanticDirectConfluenceAnalyser();
//      Assert.assertFalse(directConfluenceAnalyser.checkDirectConfluence(ruleA, ruleB).isValid());
//   }
//	@Test
//   public  void testAA(){
//      System.out.println("Starting ModelsPaperExample/TestAA" );
//
//
//      StoryPattern stpRule1=getTestPattern("Rules","ruleA2");
//      StoryPattern stpRule2=getTestPattern("Rules","ruleA2");
//
//
//      Transformer transformer=StoryPatternToSymbGraTraRulesFactory.eINSTANCE.createTransformer();
//      SymbGTRule ruleA=transformer.transformStpToSymbGTRule(stpRule1);
//      SymbGTRule ruleB=transformer.transformStpToSymbGTRule(stpRule2);
//      ruleA.makeRuleLazy();
//      ruleB.makeRuleLazy();
//      
//      DirectConfluenceAnalyser directConfluenceAnalyser=AnalysisFactory.eINSTANCE.createSemanticDirectConfluenceAnalyser();
//      Assert.assertFalse(directConfluenceAnalyser.checkDirectConfluence(ruleA, ruleB).isValid());
//   }
	@Test
	public  void testAB(){
      System.out.println("Starting ModelsPaperExample/TestAB" );


      StoryPattern stpRule1=getTestPattern("Rules","ruleA2");
      StoryPattern stpRule2=getTestPattern("Rules","ruleB2");


      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule ruleA=transformer.transformStpToSymbGTRule(stpRule1);
      SymbGTRule ruleB=transformer.transformStpToSymbGTRule(stpRule2);
      ruleA.makeRuleLazy();
      ruleB.makeRuleLazy();
      
      DirectConfluenceAnalyser directConfluenceAnalyser=AnalysisFactory.eINSTANCE.createSemanticDirectConfluenceAnalyser();
      Assert.assertTrue(directConfluenceAnalyser.checkDirectConfluence(ruleA, ruleB).isValid());
   }

}
