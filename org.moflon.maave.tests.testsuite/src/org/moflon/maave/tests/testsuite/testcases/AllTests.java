package org.moflon.maave.tests.testsuite.testcases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
   TestForStoryPatternToSymbGraTraRules_Basic.class 
//   ,TestForStoryPatternToProjGraTraRules_Basic.class \\TODO fix
   ,TestForStoryPatternToSymbGraTraRules_NAC.class
   ,TestForStoryPatternToProjGraTraRules_Enum.class
   ,MorphismFinderTest.class
//   ,PullbackTest.class //TODO fix
   ,PushoutUMPTest.class
   ,IsomorphicCopyTester.class
//   ,DirectDerivationTest.class //TODO fix
   ,EmptyRuleDirectDerivationTest.class
   ,MorphismClassTest.class

//   ,CopyRuleTest.class //TODO fix
   ,SubgraphBuilderTest.class
   ,JointlyEpiTest.class
//   ,MinimalContextsPerformanceTest.class //TODO fix

   ,CriticalPairTestProj.class
   ,NormalFormEquivalenceTest.class
   ,ConfluenceProjTestCMSMandatory.class
   
   ,ConfluenceProjTestGAMPaper.class
//   ,ConfluenceProjTestModelsPaper.class //TODO fix
   ,ConfluenceProjTestCMS.class
   ,ConfluenceProjTestCMSnew.class

})
//@SuiteClasses({MorphismFinderTest.class})
//@SuiteClasses({})
//@SuiteClasses({})

public class AllTests {

}
