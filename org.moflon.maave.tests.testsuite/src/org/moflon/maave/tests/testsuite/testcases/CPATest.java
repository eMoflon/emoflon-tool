/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;

import org.junit.Assert;
import org.junit.Test;
import org.moflon.maave.tool.analysis.AnalysisFactory;
import org.moflon.maave.tool.analysis.CriticalPairBuilder;
import org.moflon.maave.tool.analysis.DirectDerivationBuilder;
import org.moflon.maave.tool.analysis.JointlyEpiSetBuilder;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.DirectDerivationPairSet;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;

import org.moflon.maave.tests.testgen.diachase.DiachaseFactory;
import org.moflon.maave.tests.testgen.diachase.PaperExampleTest;
import SDMLanguage.patterns.StoryPattern;

/**
 * @author fdeckwerth
 *
 */
public class CPATest {





   @Test
   public  void test1(){
      System.out.println("Starting CPATest/Test1" );
      org.moflon.maave.tests.testgen.diachase.CPATest cpaTest =DiachaseFactory.eINSTANCE.createCPATest();

      StoryPattern stp1=cpaTest.getTestPattern("noAttrRule1");
      StoryPattern stp2=cpaTest.getTestPattern("noAttrRule2");
      StoryPattern stp3=cpaTest.getTestPattern("noAttrRule3");
      Transformer transformer = StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1= transformer.transformStpToSymbGTRule(stp1);
      SymbGTRule rule2= transformer.transformStpToSymbGTRule(stp2);
      SymbGTRule rule3= transformer.transformStpToSymbGTRule(stp3);
      CriticalPairBuilder cpBuilder=AnalysisFactory.eINSTANCE.createBasicSymbolicCriticalPairBuilder();
      JointlyEpiSetBuilder jointlyEpiSetBuilder=AnalysisFactory.eINSTANCE.createNonEmptySemanticJointlyEpiSetBuilder();
      DirectDerivationBuilder derBuilder=AnalysisFactory.eINSTANCE.createSymbolicDirectDerivationBuilder();
      DirectDerivationPairSet setNotEmpty=cpBuilder.getAllCriticalPairs(rule1, rule2,derBuilder,jointlyEpiSetBuilder);
      DirectDerivationPairSet setEmpty=cpBuilder.getAllCriticalPairs(rule1, rule3,derBuilder,jointlyEpiSetBuilder);

      Assert.assertTrue(setNotEmpty.getPairsOfDirectDerivations().size()>0);
      Assert.assertTrue(setEmpty.getPairsOfDirectDerivations().size()==0);

   }
   @Test
   public  void test2(){
      System.out.println("Starting CPATest/Test2" );
          
      Transformer transformer = StptransformationFactory.eINSTANCE.createTransformer();
      
      PaperExampleTest paperTest=DiachaseFactory.eINSTANCE.createPaperExampleTest();
      StoryPattern stpAddOne=paperTest.getTestPattern("ruleAddOne");
      StoryPattern stpAddTwo=paperTest.getTestPattern("ruleAddTwo");
      SymbGTRule ruleAddOne= transformer.transformStpToSymbGTRule(stpAddTwo);
      SymbGTRule ruleAddTwo= transformer.transformStpToSymbGTRule(stpAddOne);
      CriticalPairBuilder cpBuilder=AnalysisFactory.eINSTANCE.createBasicSymbolicCriticalPairBuilder();
      JointlyEpiSetBuilder jointlyEpiSetBuilder=AnalysisFactory.eINSTANCE.createNonEmptySemanticJointlyEpiSetBuilder();
      DirectDerivationBuilder derBuilder=AnalysisFactory.eINSTANCE.createSymbolicDirectDerivationBuilder();
      DirectDerivationPairSet setNotEmpty=cpBuilder.getAllCriticalPairs(ruleAddOne , ruleAddTwo,derBuilder,jointlyEpiSetBuilder);
      Assert.assertTrue(setNotEmpty.getPairsOfDirectDerivations().size()>0);
   }


}
