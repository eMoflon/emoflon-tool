/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;

import org.junit.Assert;
import org.junit.Test;
import org.moflon.maave.tool.analysis.AnalysisFactory;
import org.moflon.maave.tool.analysis.DirectDerivationBuilder;
import org.moflon.maave.tool.category.CategoryFactory;
import org.moflon.maave.tool.category.SymbolicGraphCat;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.DirectDerivationSet;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CInjectiveEGraphMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismFinderFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismsSet;

import org.moflon.maave.tests.testgen.diachase.DiachaseFactory;
import org.moflon.maave.tests.testgen.diachase.InitialPushoutTest;
import SDMLanguage.patterns.StoryPattern;

/**
 * @author fdeckwerth
 *
 */
public class InitialPushoutTests {

   



   @Test
   public  void test1(){
	  System.out.println("Starting InitialPushoutTest/Test1" );
	  InitialPushoutTest iPushoutTest =DiachaseFactory.eINSTANCE.createInitialPushoutTest();
	
	  StoryPattern p1=iPushoutTest.getTestPattern("testPattern1");
	  StoryPattern m1=iPushoutTest.getTestPattern("testModel1");
	  SymbolicGraphMorphism b=getBoundary(p1, m1);
	  Assert.assertTrue(b.getDom().getGraphNodes().stream().filter(n->b.imageOf(n)!=null&&n.getDebugId().equals("mP1")&&n.getType().getName().equals("M")).count()==1);
     
     
   }
   @Test
   public  void test2(){
     System.out.println("Starting InitialPushoutTest/Test2" );
     InitialPushoutTest iPushoutTest =DiachaseFactory.eINSTANCE.createInitialPushoutTest();
   
     StoryPattern p1=iPushoutTest.getTestPattern("testPattern2");
     StoryPattern m1=iPushoutTest.getTestPattern("testModel2");
     SymbolicGraphMorphism b=getBoundary(p1, m1);
     Assert.assertTrue(b.getDom().getGraphNodes().size()==0);
     
     
   }
   @Test
   public  void test3(){
     System.out.println("Starting InitialPushoutTest/Test3" );
     InitialPushoutTest iPushoutTest =DiachaseFactory.eINSTANCE.createInitialPushoutTest();
   
     StoryPattern stpRule=iPushoutTest.getTestPattern("testRule1");
     StoryPattern stpModel=iPushoutTest.getTestPattern("testModel2");
     Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
     DirectDerivationBuilder direcDerivationBuilder=AnalysisFactory.eINSTANCE.createSymbolicDirectDerivationBuilder();
     
     SymbGTRule rule=transformer.transformStpToSymbGTRule(stpRule);
     SymbolicGraph model=transformer.transformStpToSymbGTRule(stpModel).getLeft().getCodom();
     
     DirectDerivationSet directDerivationSet=direcDerivationBuilder.deriveAllDirectDerivations(rule, model);
     Assert.assertTrue(directDerivationSet.getDirectDerivations().size()==1);
     
     
     
   }
   @Test
   public  void test4(){
     System.out.println("Starting InitialPushoutTest/Test4" );
     InitialPushoutTest iPushoutTest =DiachaseFactory.eINSTANCE.createInitialPushoutTest();
   
     StoryPattern stpRule=iPushoutTest.getTestPattern("testRule1");
     StoryPattern stpModel=iPushoutTest.getTestPattern("testModel3");
     Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
     DirectDerivationBuilder direcDerivationBuilder=AnalysisFactory.eINSTANCE.createSymbolicDirectDerivationBuilder();
     
     SymbGTRule rule=transformer.transformStpToSymbGTRule(stpRule);
     SymbolicGraph model=transformer.transformStpToSymbGTRule(stpModel).getLeft().getCodom();
     
     DirectDerivationSet directDerivationSet=direcDerivationBuilder.deriveAllDirectDerivations(rule, model);
     Assert.assertTrue(directDerivationSet.getDirectDerivations().size()==0);
     
     
     
   }  
   private SymbolicGraphMorphism getBoundary(StoryPattern p1, StoryPattern p2){
      Transformer transformer = StptransformationFactory.eINSTANCE.createTransformer();
      
      SymbolicGraph graphL=transformer.transformStpToSymbGTRule(p1).getLeft().getCodom();
      SymbolicGraph graphG=transformer.transformStpToSymbGTRule(p2).getLeft().getCodom();
      
           
      MorphismFinderFactory factory=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      CInjectiveEGraphMorphismFinder matcher=factory.createInjectiveEGraphMorphismFinder(graphL);
      
      SymbolicGraphMorphism partialMatch=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      partialMatch.setDom(graphL);
      partialMatch.setCodom(graphG);
      
      MorphismsSet matches=matcher.getAllMorphisms(partialMatch);
      
      
      
      SymbolicGraphCat category=CategoryFactory.eINSTANCE.createSymbolicGraphCat();
      return category.boundary(matches.getMorphisms().get(0)).getBoundary();
   }
}
