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
import org.moflon.maave.tool.analysis.DirectConfluenceAnalyser;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;

import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;
import SDMLanguage.SDMLanguagePackage;
import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.patterns.StoryPattern;

/**
 * @author fdeckwerth
 *
 */
public class DirectConfluenceTestGAMPaperExample {



	private StoryPattern getTestPattern(String className,String name){

		//    rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		//    rs.getURIConverter().getURIMap().put(URI.createPlatformResourceURI("/", true), URI.createFileURI(args[0] + "\\"));
		//    rs.getURIConverter().getURIMap().put(URI.createPlatformPluginURI("/DiagramChasingTestGen/", true), URI.createPlatformResourceURI("/DiagramChasingTestGen/", true));

		//URI testGeneratorURI =URI.createURI("DiagramChasingTestGen/model/DiagramChasingTestGen.ecore",true);
		MnoqPackage.eINSTANCE.eClass();
		SDMLanguagePackage.eINSTANCE.getClass();
		DiachasePackage.eINSTANCE.getClass();
		EPackage pack = (EPackage) eMoflonEMFUtil
				.loadModel("../DiagramChasingTestGen/model/DiagramChasingTestGen.ecore");
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
//		System.out.println("Starting DirectConfluenceTestGAMPaperExample/Test1" );
//
//
//		StoryPattern stpRule1=getTestPattern("PaperExampleTest","ruleAddOne");
//		StoryPattern stpRule2=getTestPattern("PaperExampleTest","ruleAddTwo");
//
//
//		Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//		SymbGTRule rule1=transformer.transformStpToSymbGTRule(stpRule1);
//		SymbGTRule rule2=transformer.transformStpToSymbGTRule(stpRule2);
//		  
//		
//		DirectConfluenceAnalyser directConfluenceAnalyser=AnalysisFactory.eINSTANCE.createSymbolicDirectConfluenceAnalyser();
//		Assert.assertTrue(directConfluenceAnalyser.checkDirectConfluence(rule1, rule2).isValid());
//	}
//	
	
	
//	@Test
//   public  void test2(){
//      System.out.println("Starting DirectConfluenceTestGAMPaperExample/Test2" );
//
//
//      StoryPattern stpRule1=getTestPattern("PaperExampleTest","ruleAddOne");
//      StoryPattern stpRule2=getTestPattern("PaperExampleTest","ruleSmaller4");
//
//
//      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//      SymbGTRule rule1=transformer.transformStpToSymbGTRule(stpRule1);
//      SymbGTRule rule2=transformer.transformStpToSymbGTRule(stpRule2);
//      rule1.makeRuleLazy();
//      rule2.makeRuleLazy();
//      
//      DirectConfluenceAnalyser directConfluenceAnalyser=AnalysisFactory.eINSTANCE.createSymbolicDirectConfluenceAnalyser();
//      Assert.assertFalse(directConfluenceAnalyser.checkDirectConfluence(rule1, rule2).isValid());
//      
//
//      
//   }
	
	
//	@Test
//   public  void test3(){
//      System.out.println("Starting DirectConfluenceTestGAMPaperExample/Test3" );
//
//
//      StoryPattern stpRule1=getTestPattern("PaperExampleTest","ruleAddOne");
//      StoryPattern stpRule2=getTestPattern("PaperExampleTest","ruleLarger4");
//
//
//      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//      SymbGTRule rule1=transformer.transformStpToSymbGTRule(stpRule1);
//      SymbGTRule rule2=transformer.transformStpToSymbGTRule(stpRule2);
//      
//      
//      
//      DirectConfluenceAnalyser directConfluenceAnalyser=AnalysisFactory.eINSTANCE.createSymbolicDirectConfluenceAnalyser();
//      Assert.assertTrue(directConfluenceAnalyser.checkDirectConfluence(rule1, rule2).isValid());
// 
//	}
      
      
 @Test
public  void test1a(){
  System.out.println("Starting DirectConfluenceTestGAMPaperExample/Test1a" );


  StoryPattern stpRule1=getTestPattern("PaperExampleTest","ruleAddOne");
  StoryPattern stpRule2=getTestPattern("PaperExampleTest","ruleAddTwo");


  Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
  SymbGTRule rule1=transformer.transformStpToSymbGTRule(stpRule1);
  SymbGTRule rule2=transformer.transformStpToSymbGTRule(stpRule2);
    rule1.makeRuleLazy();
    rule2.makeRuleLazy();
  
  DirectConfluenceAnalyser directConfluenceAnalyser=AnalysisFactory.eINSTANCE.createSemanticDirectConfluenceAnalyser();
  Assert.assertTrue(directConfluenceAnalyser.checkDirectConfluence(rule1, rule2).isValid());
 
}

 @Test
 public  void test2a(){
    System.out.println("Starting DirectConfluenceTestGAMPaperExample/Test2a" );


    StoryPattern stpRule1=getTestPattern("PaperExampleTest","ruleAddOne");
    StoryPattern stpRule2=getTestPattern("PaperExampleTest","ruleSmaller4");


    Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
    SymbGTRule rule1=transformer.transformStpToSymbGTRule(stpRule1);
    SymbGTRule rule2=transformer.transformStpToSymbGTRule(stpRule2);
    rule1.makeRuleLazy();
    rule2.makeRuleLazy();
    
    DirectConfluenceAnalyser directConfluenceAnalyser=AnalysisFactory.eINSTANCE.createSymbolicDirectConfluenceAnalyser();
    Assert.assertFalse(directConfluenceAnalyser.checkDirectConfluence(rule1, rule2).isValid());
    

    
 }
	@Test
   public  void test3a(){
      System.out.println("Starting DirectConfluenceTestGAMPaperExample/Test3a" );


      StoryPattern stpRule1=getTestPattern("PaperExampleTest","ruleAddOne");
      StoryPattern stpRule2=getTestPattern("PaperExampleTest","ruleLarger4");


      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1=transformer.transformStpToSymbGTRule(stpRule1);
      SymbGTRule rule2=transformer.transformStpToSymbGTRule(stpRule2);
      rule1.makeRuleLazy();
      rule2.makeRuleLazy();
      
      
      DirectConfluenceAnalyser directConfluenceAnalyser=AnalysisFactory.eINSTANCE.createSemanticDirectConfluenceAnalyser();
      Assert.assertTrue(directConfluenceAnalyser.checkDirectConfluence(rule1, rule2).isValid());
      
   }
//	
}
