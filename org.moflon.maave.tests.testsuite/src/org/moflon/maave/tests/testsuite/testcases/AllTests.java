package org.moflon.maave.tests.testsuite.testcases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
   TestForStoryPatternToSymbGraTraRules_Basic.class 
//   ,TestForStoryPatternToProjGraTraRules_Basic.class
   ,TestForStoryPatternToSymbGraTraRules_NAC.class
   ,MorphismFinderTest.class
//   ,PullbackTest.class //TODO fix
   ,PushoutUMPTest.class
   ,IsomorphicCopyTester.class
//   ,DirectDerivationTest.class //fix
   ,MorphismClassTest.class

   ,SubgraphBuilderTest.class
   ,JointlyEpiTest.class
   ,CriticalPairTestProj.class
   ,NormalFormEquivalenceTest.class
   ,ConfluenceProjTestGAMPaper.class
//   ,ConfluenceProjTestModelsPaper.class
//   ,ConfluenceProjTestCMS.class
//   ,SymbolicPushoutComplementTest.class //TODO fix

})
//@SuiteClasses({MorphismFinderTest.class})
//@SuiteClasses({})
//@SuiteClasses({})

public class AllTests {

}
