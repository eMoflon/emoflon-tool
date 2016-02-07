/**
 * 
 */
package org.moflon.maave.tests.testsuite.testcases;

import org.junit.Assert;
import org.junit.Test;
import org.moflon.maave.tool.analysis.AnalysisFactory;
import org.moflon.maave.tool.analysis.JointlyEpiSetBuilder;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.smt.solverutil.IAttribSolver;
import org.moflon.maave.tool.smt.solverutil.Z3AttribSolver;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.MorphismPairSet;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;

import org.moflon.maave.tests.testgen.diachase.DiachaseFactory;
import org.moflon.maave.tests.testgen.diachase.PaperExampleTest;
import SDMLanguage.patterns.StoryPattern;

/**
 * @author fdeckwerth
 *
 */
public class MinimalContextsTestGAMPaperExample {





   @Test
   public  void test1(){
	   System.out.println("Starting MinimalContextsTestGAMPaperExample/Test1" );
      PaperExampleTest paperTest=DiachaseFactory.eINSTANCE.createPaperExampleTest();
      MorphismPairSet set1=paperTest.getAllMinimalContextsAdd();
      MorphismPairSet set2=paperTest.getAllMinimalContextsLargerSmaller();
      IAttribSolver solver= new Z3AttribSolver();
     
     
      
    
      
      Assert.assertTrue(set1.getMorphismPairs().size()==1);
     
      Assert.assertTrue(solver.hasNonEmptySemantic(set1.getMorphismPairs().get(0).getFirst().getCodom()));
      Assert.assertFalse(solver.hasNonEmptySemantic(set2.getMorphismPairs().get(0).getFirst().getCodom()));
   }
   @Test
   public  void test2(){
      System.out.println("Starting MinimalContextsTestGAMPaperExample/Test2" );
      PaperExampleTest paperTest=DiachaseFactory.eINSTANCE.createPaperExampleTest();
      StoryPattern sptRuleAddOne= paperTest.getTestPattern("ruleAddOne");
      StoryPattern stpRuleAddTwo= paperTest.getTestPattern("ruleAddTwo");
      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      
      SymbGTRule ruleAddOne=transformer.transformStpToSymbGTRule(sptRuleAddOne);
      SymbGTRule ruleAddTwo=transformer.transformStpToSymbGTRule(stpRuleAddTwo);

      JointlyEpiSetBuilder improvedBuilder=AnalysisFactory.eINSTANCE.createImprovedJointlyEpiSetBuilder();
      JointlyEpiSetBuilder basicBuilder=AnalysisFactory.eINSTANCE.createBasicJointlyEpiSetBuilder();
      MorphismPairSet pairSetImp=improvedBuilder.getAllMinimalContexts(ruleAddOne.getLeft().getCodom(), ruleAddTwo.getLeft().getCodom());
      MorphismPairSet pairSetBasic=basicBuilder.getAllMinimalContexts(ruleAddOne.getLeft().getCodom(), ruleAddTwo.getLeft().getCodom());
     System.out.println("foo");
     
      
    
      
      
   }
      

}
