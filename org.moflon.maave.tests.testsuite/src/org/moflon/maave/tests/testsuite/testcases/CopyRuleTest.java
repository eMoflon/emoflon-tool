package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertFalse;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Test;
import org.moflon.maave.tests.lang.cms.CmsPackage;
import org.moflon.maave.tests.testsuite.helper.ModelHelper;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.MorphismClass;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.IMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismFinderFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;


public class CopyRuleTest {

   @Test
   public void test3() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting CopyRuleTest/Test1: " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");

      EClass clsExam=(EClass) pack.getEClassifier("Exam");

      
     

      
      SymbGTRule rule1=ModelHelper.getRule(clsExam,"transscriptRecord");
      SymbGTRule rule2=rule1.createCopy();

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      MorphismClass isomorclass=morClassFac.createMorphismClass("B", "B", "B", "B", "<=>");
      
      MorphismFinderFactory morFinderFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      IMorphismFinder morFinderForL1=morFinderFac.createMorphismFinder(rule1.getLeft().getCodom(), isomorclass);
      IMorphismFinder morFinderForK1=morFinderFac.createMorphismFinder(rule1.getLeft().getDom(), isomorclass);
      IMorphismFinder morFinderForR1=morFinderFac.createMorphismFinder(rule1.getRight().getCodom(), isomorclass);

      SymbolicGraphMorphism emyptyMorL1_L2=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emyptyMorL1_L2.setDom(rule1.getLeft().getCodom());
      emyptyMorL1_L2.setCodom(rule2.getLeft().getCodom());
      
      
      
      SymbolicGraphMorphism emyptyMorK1_K2=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emyptyMorK1_K2.setDom(rule1.getLeft().getDom());
      emyptyMorK1_K2.setCodom(rule2.getLeft().getDom());
      
      
      
      SymbolicGraphMorphism emyptyMorR1_R2=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emyptyMorR1_R2.setDom(rule1.getRight().getCodom());
      emyptyMorR1_R2.setCodom(rule2.getRight().getCodom());
      
      assertFalse(morFinderForL1.getAllMorphisms(emyptyMorL1_L2).getMorphisms().isEmpty());
      assertFalse(morFinderForK1.getAllMorphisms(emyptyMorK1_K2).getMorphisms().isEmpty());
      assertFalse(morFinderForR1.getAllMorphisms(emyptyMorR1_R2).getMorphisms().isEmpty());
   }

  
}