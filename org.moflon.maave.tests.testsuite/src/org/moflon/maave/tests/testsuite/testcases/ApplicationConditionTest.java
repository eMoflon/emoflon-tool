package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Test;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tests.testsuite.helper.ModelHelper;
import org.moflon.maave.tool.category.CategoryFactory;
import org.moflon.maave.tool.category.SymbolicGraphCat;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.ConfigurableMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismFinderFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismsSet;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClass;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;

public class ApplicationConditionTest {
     

   @Test
   public void test1() {
      System.out.println("Starting ApplicationConditionTest/TestInitalObject" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("MorphismClassTestPatterns");
    
      
      SymbGTRule rule1=ModelHelper.getRule(cls,"testPattern1");

      SymbolicGraph graphA=rule1.getLeft().getCodom();
      
      SymbolicGraphCat cat=CategoryFactory.eINSTANCE.createSymbolicGraphCat();
      SymbolicGraph graphB=cat.getInitalInitalSG();
      
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      
      ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
      ConfigurableMorphismFinder morFinderForB = mofFindFac.createMorphismFinder(graphB, morclass);
      SymbolicGraphMorphism emptyMorB_A=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorB_A.setDom(graphB);
      emptyMorB_A.setCodom(graphA);
      MorphismsSet morSetB_A=morFinderForB.getAllMorphisms(emptyMorB_A);
      
      
      assertTrue(morSetB_A.getMorphisms().size()==1);
   }
   @Test
   public void test2() {
      System.out.println("Starting ApplicationConditionTest/nacTestPattern1Valid" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("NacPatternGenerator");
    
      
      SymbGTRule rule1=ModelHelper.getRule(cls,"nacTestPattern1");
      
      SymbGTRule rule2=ModelHelper.getRule(cls,"instanceTestPattern1Valid");
      SymbolicGraph graphL=rule1.getLeft().getCodom();
      SymbolicGraph graphG1=rule2.getLeft().getCodom();
     
      
           
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      
      ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
      ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(graphL, morclass);
      SymbolicGraphMorphism emptyMorL_G1=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorL_G1.setDom(graphL);
      emptyMorL_G1.setCodom(graphG1);
      MorphismsSet morSetL_G1=morFinderForL.getAllMorphisms(emptyMorL_G1);
      assertTrue(morSetL_G1.getMorphisms().size()==1);
      
      assertTrue(morSetL_G1.getMorphisms().stream().filter(x->x.getDom().getConditions().get(0).isSat(x, morclass)).count()==1);
      
   }
   
   @Test
   public void test3() {
      System.out.println("Starting ApplicationConditionTest/nacTestPattern1Invalid" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("NacPatternGenerator");
    
      
      SymbGTRule rule1=ModelHelper.getRule(cls,"nacTestPattern1");
      
      SymbGTRule rule2=ModelHelper.getRule(cls,"instanceTestPattern1Invalid");
      SymbolicGraph graphL=rule1.getLeft().getCodom();
      SymbolicGraph graphG1=rule2.getLeft().getCodom();
     
      
           
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      
      ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
      ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(graphL, morclass);
      SymbolicGraphMorphism emptyMorL_G1=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorL_G1.setDom(graphL);
      emptyMorL_G1.setCodom(graphG1);
      MorphismsSet morSetL_G1=morFinderForL.getAllMorphisms(emptyMorL_G1);
      assertTrue(morSetL_G1.getMorphisms().size()==1);
      
      assertTrue(morSetL_G1.getMorphisms().stream().filter(x->x.getDom().getConditions().get(0).isSat(x, morclass)).count()==0);
      
   }
   @Test
   public void test4() {
      System.out.println("Starting ApplicationConditionTest/nacTestPattern2InvalidA" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("NacPatternGenerator");
    
      
      SymbGTRule rule1=ModelHelper.getRule(cls,"nacTestPattern2");
      
      SymbGTRule rule2=ModelHelper.getRule(cls,"instanceTestPattern2InvalidA");
      SymbolicGraph graphL=rule1.getLeft().getCodom();
      SymbolicGraph graphG1=rule2.getLeft().getCodom();
     
      
           
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      
      ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
      ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(graphL, morclass);
      SymbolicGraphMorphism emptyMorL_G1=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorL_G1.setDom(graphL);
      emptyMorL_G1.setCodom(graphG1);
      MorphismsSet morSetL_G1=morFinderForL.getAllMorphisms(emptyMorL_G1);
      assertTrue(morSetL_G1.getMorphisms().size()==2);
      
      assertTrue(morSetL_G1.getMorphisms().stream().filter(x->x.getDom().getConditions().get(0).isSat(x, morclass)).count()==0);
      
   }
   @Test
   public void test5() {
      System.out.println("Starting ApplicationConditionTest/nacTestPattern2InvalidB" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("NacPatternGenerator");
    
      
      SymbGTRule rule1=ModelHelper.getRule(cls,"nacTestPattern2");
      
      SymbGTRule rule2=ModelHelper.getRule(cls,"instanceTestPattern2InvalidB");
      SymbolicGraph graphL=rule1.getLeft().getCodom();
      SymbolicGraph graphG1=rule2.getLeft().getCodom();
     
      
           
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      
      ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
      ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(graphL, morclass);
      SymbolicGraphMorphism emptyMorL_G1=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorL_G1.setDom(graphL);
      emptyMorL_G1.setCodom(graphG1);
      MorphismsSet morSetL_G1=morFinderForL.getAllMorphisms(emptyMorL_G1);
      assertTrue(morSetL_G1.getMorphisms().size()==1);
      
      assertTrue(morSetL_G1.getMorphisms().stream().filter(x->x.getDom().getConditions().get(0).isSat(x, morclass)).count()==0);
      
   }
   @Test
   public void test6() {
      System.out.println("Starting ApplicationConditionTest/nacTestPattern2InvalidC" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("NacPatternGenerator");
    
      
      SymbGTRule rule1=ModelHelper.getRule(cls,"nacTestPattern2");
      
      SymbGTRule rule2=ModelHelper.getRule(cls,"instanceTestPattern2InvalidC");
      SymbolicGraph graphL=rule1.getLeft().getCodom();
      SymbolicGraph graphG1=rule2.getLeft().getCodom();
     
      
           
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      
      ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
      ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(graphL, morclass);
      SymbolicGraphMorphism emptyMorL_G1=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorL_G1.setDom(graphL);
      emptyMorL_G1.setCodom(graphG1);
      MorphismsSet morSetL_G1=morFinderForL.getAllMorphisms(emptyMorL_G1);
      assertTrue(morSetL_G1.getMorphisms().size()==2);
      
      assertTrue(morSetL_G1.getMorphisms().stream().filter(x->x.getDom().getConditions().get(0).isSat(x, morclass)).count()==0);
      
   }
   @Test
   public void test7() {
      System.out.println("Starting ApplicationConditionTest/nacTestPattern2Valid" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("NacPatternGenerator");
    
      
      SymbGTRule rule1=ModelHelper.getRule(cls,"nacTestPattern2");
      
      SymbGTRule rule2=ModelHelper.getRule(cls,"instanceTestPattern2valid");
      SymbolicGraph graphL=rule1.getLeft().getCodom();
      SymbolicGraph graphG1=rule2.getLeft().getCodom();
     
      
           
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      
      ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
      ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(graphL, morclass);
      SymbolicGraphMorphism emptyMorL_G1=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorL_G1.setDom(graphL);
      emptyMorL_G1.setCodom(graphG1);
      MorphismsSet morSetL_G1=morFinderForL.getAllMorphisms(emptyMorL_G1);
      assertTrue(morSetL_G1.getMorphisms().size()==1);
      
      assertTrue(morSetL_G1.getMorphisms().stream().filter(x->x.getDom().getConditions().get(0).isSat(x, morclass)).count()==1);
      
   }
   @Test
   public void test8() {
      System.out.println("Starting ApplicationConditionTest/nacTestPattern4Invalid" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("NacPatternGenerator");
    
      
      SymbGTRule rule1=ModelHelper.getRule(cls,"nacTestPattern4");
      
      SymbGTRule rule2=ModelHelper.getRule(cls,"instanceTestPattern4Invalid");
      SymbolicGraph graphL=rule1.getLeft().getCodom();
      SymbolicGraph graphG1=rule2.getLeft().getCodom();
     
      
           
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      
      ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
      ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(graphL, morclass);
      SymbolicGraphMorphism emptyMorL_G1=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorL_G1.setDom(graphL);
      emptyMorL_G1.setCodom(graphG1);
      MorphismsSet morSetL_G1=morFinderForL.getAllMorphisms(emptyMorL_G1);
      assertTrue(morSetL_G1.getMorphisms().size()==1);
      
      assertTrue(morSetL_G1.getMorphisms().stream().filter(x->x.getDom().getConditions().get(0).isSat(x, morclass)).count()==0);
      
   }
   @Test
   public void test9() {
      System.out.println("Starting ApplicationConditionTest/nacTestPattern4Valid" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("NacPatternGenerator");
    
      
      SymbGTRule rule1=ModelHelper.getRule(cls,"nacTestPattern4");
      
      SymbGTRule rule2=ModelHelper.getRule(cls,"instanceTestPattern4valid");
      SymbolicGraph graphL=rule1.getLeft().getCodom();
      SymbolicGraph graphG1=rule2.getLeft().getCodom();
     
      
           
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismFinderFactory mofFindFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      
      ConfigurableMorphismClass morclass=morClassFac.createMorphismClass("I", "I", "I", "I", "=>");
      ConfigurableMorphismFinder morFinderForL = mofFindFac.createMorphismFinder(graphL, morclass);
      SymbolicGraphMorphism emptyMorL_G1=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorL_G1.setDom(graphL);
      emptyMorL_G1.setCodom(graphG1);
      MorphismsSet morSetL_G1=morFinderForL.getAllMorphisms(emptyMorL_G1);
      assertTrue(morSetL_G1.getMorphisms().size()==1);
      
      assertTrue(morSetL_G1.getMorphisms().stream().filter(x->x.getDom().getConditions().get(0).isSat(x, morclass)).count()==1);
      
   }
}
