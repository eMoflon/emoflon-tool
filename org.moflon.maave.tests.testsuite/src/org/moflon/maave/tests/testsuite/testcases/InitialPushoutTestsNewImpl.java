/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tool.category.CategoryFactory;
import org.moflon.maave.tool.category.InitialPushout;
import org.moflon.maave.tool.category.SymbolicGraphCat;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.ConfigurableMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismFinderFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismsSet;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.CategoryUtilsFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.ConfigurableMorphismClass;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.ConfigurableMorphismClassFactory;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;

/**
 * @author fdeckwerth
 *
 */
public class InitialPushoutTestsNewImpl {

   

   private EPackage pack;  
   @Before
   public void setUp() throws Exception {
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
   }

   @Test
   public void test1() {
      System.out.println("Starting InitialPushoutTestsNewImpl/Test1" );
      
         EClass cls=(EClass) pack.getEClassifier("InitialPushoutTest");
         MoflonEOperation opL=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testPattern1")).findFirst().get();
         MoflonEOperation opG=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("testModel1")).findFirst().get();
         Assert.assertTrue("FailedAssert: 0",opL!=null && opG!=null);
         StoryNode stnL=(StoryNode) opL.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         StoryNode stnG=(StoryNode) opG.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
         Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
         SymbGTRule ruleL=transformer.transformStpToSymbGTRule(stnL.getStoryPattern());
         SymbGTRule ruleG=transformer.transformStpToSymbGTRule(stnG.getStoryPattern()); 
         SymbolicGraph L=ruleL.getLeft().getCodom();
         SymbolicGraph G=ruleG.getLeft().getCodom();
         L.setName("L");
         G.setName("G");
         // Collect matches using new morphism finder
         ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
         MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
         
         ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
         ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(L, morclass);
                    
         SymbolicGraphMorphism emptyL_G =SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
         emptyL_G.setDom(L);
         emptyL_G.setCodom(G);

         MorphismsSet morListL_G=morFinderForL.getAllMorphisms(emptyL_G);
         assertTrue(morListL_G.getMorphisms().size()==1);
         SymbolicGraphMorphism morL_G=morListL_G.getMorphisms().get(0);
         SymbolicGraphCat cat=CategoryFactory.eINSTANCE.createSymbolicGraphCat();
         InitialPushout initialPushout=cat.initialPushout(morL_G);
         SymbolicGraphMorphism morB_L=initialPushout.getMorB_L();
         SymbolicGraph B=morB_L.getDom();
         assertTrue(B.getAllElements().size()==1);
         assertTrue(B.getGraphNodes().get(0).getType().getName().equals("M"));

   }

//   @Test
//   public  void test21(){
//	  System.out.println("Starting InitialPushoutTest/Test1" );
//	  InitialPushoutTest iPushoutTest =DiachaseFactory.eINSTANCE.createInitialPushoutTest();
//	
//	  StoryPattern p1=iPushoutTest.getTestPattern("testPattern1");
//	  StoryPattern m1=iPushoutTest.getTestPattern("testModel1");
//	  SymbolicGraphMorphism b=getBoundary(p1, m1);
//	  Assert.assertTrue(b.getDom().getGraphNodes().stream().filter(n->b.imageOf(n)!=null&&n.getDebugId().equals("mP1")&&n.getType().getName().equals("M")).count()==1);
//     
//     
//   }
//   @Test
//   public  void test2(){
//     System.out.println("Starting InitialPushoutTest/Test2" );
//     InitialPushoutTest iPushoutTest =DiachaseFactory.eINSTANCE.createInitialPushoutTest();
//   
//     StoryPattern p1=iPushoutTest.getTestPattern("testPattern2");
//     StoryPattern m1=iPushoutTest.getTestPattern("testModel2");
//     SymbolicGraphMorphism b=getBoundary(p1, m1);
//     Assert.assertTrue(b.getDom().getGraphNodes().size()==0);
//     
//     
//   }
//   @Test
//   public  void test3(){
//     System.out.println("Starting InitialPushoutTest/Test3" );
//     InitialPushoutTest iPushoutTest =DiachaseFactory.eINSTANCE.createInitialPushoutTest();
//   
//     StoryPattern stpRule=iPushoutTest.getTestPattern("testRule1");
//     StoryPattern stpModel=iPushoutTest.getTestPattern("testModel2");
//     Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//     DirectDerivationBuilder direcDerivationBuilder=AnalysisFactory.eINSTANCE.createSymbolicDirectDerivationBuilder();
//     
//     SymbGTRule rule=transformer.transformStpToSymbGTRule(stpRule);
//     SymbolicGraph model=transformer.transformStpToSymbGTRule(stpModel).getLeft().getCodom();
//     
//     DirectDerivationSet directDerivationSet=direcDerivationBuilder.deriveAllDirectDerivations(rule, model);
//     Assert.assertTrue(directDerivationSet.getDirectDerivations().size()==1);
//     
//     
//     
//   }
//   @Test
//   public  void test4(){
//     System.out.println("Starting InitialPushoutTest/Test4" );
//     InitialPushoutTest iPushoutTest =DiachaseFactory.eINSTANCE.createInitialPushoutTest();
//   
//     StoryPattern stpRule=iPushoutTest.getTestPattern("testRule1");
//     StoryPattern stpModel=iPushoutTest.getTestPattern("testModel3");
//     Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//     DirectDerivationBuilder direcDerivationBuilder=AnalysisFactory.eINSTANCE.createSymbolicDirectDerivationBuilder();
//     
//     SymbGTRule rule=transformer.transformStpToSymbGTRule(stpRule);
//     SymbolicGraph model=transformer.transformStpToSymbGTRule(stpModel).getLeft().getCodom();
//     
//     DirectDerivationSet directDerivationSet=direcDerivationBuilder.deriveAllDirectDerivations(rule, model);
//     Assert.assertTrue(directDerivationSet.getDirectDerivations().size()==0);
//     
//     
//     
//   }  
//   private SymbolicGraphMorphism getBoundary(StoryPattern p1, StoryPattern p2){
//      Transformer transformer = StptransformationFactory.eINSTANCE.createTransformer();
//      
//      SymbolicGraph graphL=transformer.transformStpToSymbGTRule(p1).getLeft().getCodom();
//      SymbolicGraph graphG=transformer.transformStpToSymbGTRule(p2).getLeft().getCodom();
//      
//           
//      MorphismFinderFactory factory=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
//      CInjectiveEGraphMorphismFinder matcher=factory.createInjectiveEGraphMorphismFinder(graphL);
//      
//      SymbolicGraphMorphism partialMatch=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
//      partialMatch.setDom(graphL);
//      partialMatch.setCodom(graphG);
//      
//      MorphismsSet matches=matcher.getAllMorphisms(partialMatch);
//      
//      
//      
//      SymbolicGraphCat category=CategoryFactory.eINSTANCE.createSymbolicGraphCat();
//      return category.boundary(matches.getMorphisms().get(0)).getBoundary();
//   }
}
