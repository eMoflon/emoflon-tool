package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Ignore;
import org.junit.Test;
import org.moflon.maave.tests.lang.vwxyz.VwxyzPackage;
import org.moflon.maave.tests.testsuite.helper.ModelHelper;
import org.moflon.maave.tool.analysis.acenforcment.ACEnforcmentReport;
import org.moflon.maave.tool.analysis.acenforcment.AcenforcmentFactory;
import org.moflon.maave.tool.analysis.acenforcment.ConstraintsToACBuilder;
import org.moflon.maave.tool.analysis.acenforcment.IConditionAlongMorphismShifter;
import org.moflon.maave.tool.analysis.acenforcment.IConditionAlongRuleShifter;
import org.moflon.maave.tool.graphtransformation.DirectDerivationBuilder;
import org.moflon.maave.tool.graphtransformation.DirectDerivationSet;
import org.moflon.maave.tool.graphtransformation.GlobalConstraint;
import org.moflon.maave.tool.graphtransformation.GraphTransformationSystem;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.IMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismFinderFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismsSet;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;

public class ConstraintsToPostAndPreConditionsTest {


   
   @Test
   public void test1() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConstraintsToPostAndPreConditionsTest/Test1: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");

      EClass cls=(EClass) pack.getEClassifier("ConstraintToPostConditionPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      GlobalConstraint nC=ModelHelper.getUserDefConstraints(pack);
      SymbolicGraph graphI=nC.getConstraints().get(1).getGraphP();

      SymbGTRule rule = ModelHelper.getRule(cls,"rule1");
      SymbolicGraph graphR=rule.getRight().getCodom();

      //Constraint to Postcondition
      SymbolicGraphMorphism morI_R=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      morI_R.setDom(graphI);
      morI_R.setCodom(graphR);
      IConditionAlongMorphismShifter conditionShifter=AcenforcmentFactory.eINSTANCE.createConditionAlongMorphismShifter();
      conditionShifter.setGraphTransformationSystem(gts);
      conditionShifter.shiftAllConditonsAlongMorphism(morI_R);

      //Test Postcondition AC

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testpattern1Inconsistent").getLeft().getCodom();


      SymbolicGraphMorphism emptyMorR_G=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorR_G.setDom(graphR);
      emptyMorR_G.setCodom(graphG);

      MorphismFinderFactory morFinderFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      IMorphismFinder morFinderForR=morFinderFac.createMorphismFinder(graphR, gts.getMatchMorphismClass());
      MorphismsSet morSetR_G=morFinderForR.getAllMorphisms(emptyMorR_G);
      for (SymbolicGraphMorphism morR_G : morSetR_G.getMorphisms())
      {
         assertTrue(graphR.getConditions().stream().anyMatch(x->x.isSat(morR_G, gts.getMatchMorphismClass())==false));
      }



   }
   
   @Test
   public void test2() {
      System.out.println("");

      System.out.println("Starting ConstraintsToPostAndPreConditionsTest/Test2: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");

      EClass cls=(EClass) pack.getEClassifier("ConstraintToPostConditionPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Init
      GlobalConstraint nC=ModelHelper.getUserDefConstraints(pack);
      nC.getConstraints().remove(2);
      nC.getConstraints().remove(0);
      gts.getGlobalConstraints().add(nC);

      SymbGTRule rule = ModelHelper.getRule(cls,"rule1");
      SymbolicGraph graphL=rule.getLeft().getCodom();
      gts.getRules().add(rule);  

      //verifyConstraints
      ConstraintsToACBuilder acBuilder=AcenforcmentFactory.eINSTANCE.createConstraintsToACBuilder();
      ACEnforcmentReport report=acBuilder.verifyGTS(gts);


      System.out.println(report.print());
      //Test Postcondition AC

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testpattern2Inconsistent").getLeft().getCodom();
      graphG.setName("G");


      SymbolicGraphMorphism emptyMorL_G=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorL_G.setDom(graphL);
      emptyMorL_G.setCodom(graphG);

      MorphismFinderFactory morFinderFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      IMorphismFinder morFinderForL=morFinderFac.createMorphismFinder(graphL, gts.getMatchMorphismClass());
      MorphismsSet morSetL_G=morFinderForL.getAllMorphisms(emptyMorL_G);

      DirectDerivationBuilder derBuilder=GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder();
      DirectDerivationSet derSet=derBuilder.deriveAllDirectDerivations(rule, morSetL_G, gts, true);

      assertTrue(derSet.getDirectDerivations().size()==0);




   }
   
   @Test
   public void test3() {

      System.out.println("Starting ConstraintsToPostAndPreConditionsTest/Test3: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");

      EClass cls=(EClass) pack.getEClassifier("ConstraintToPostConditionPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Init
      GlobalConstraint nC=ModelHelper.getUserDefConstraints(pack);
      nC.getConstraints().remove(2);
      nC.getConstraints().remove(0);
      gts.getGlobalConstraints().add(nC);

      SymbGTRule rule = ModelHelper.getRule(cls,"rule1");
      SymbolicGraph graphL=rule.getLeft().getCodom();
      gts.getRules().add(rule);  

      //verifyConstraints
      ConstraintsToACBuilder acBuilder=AcenforcmentFactory.eINSTANCE.createConstraintsToACBuilder();
      acBuilder.verifyGTS(gts);

      //      Test Precondition AC

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testpattern2consistent").getLeft().getCodom();
      graphG.setName("G");


      SymbolicGraphMorphism emptyMorL_G=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorL_G.setDom(graphL);
      emptyMorL_G.setCodom(graphG);

      MorphismFinderFactory morFinderFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      IMorphismFinder morFinderForL=morFinderFac.createMorphismFinder(graphL, gts.getMatchMorphismClass());
      MorphismsSet morSetL_G=morFinderForL.getAllMorphisms(emptyMorL_G);

      DirectDerivationBuilder derBuilder=GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder();
      DirectDerivationSet derSet=derBuilder.deriveAllDirectDerivations(rule, morSetL_G, gts, true);
      assertTrue(derSet.getDirectDerivations().size()>0);


   }
   @Test
   public void test4() {

      System.out.println("Starting ConstraintsToPostAndPreConditionsTest/Test4: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");

      EClass cls=(EClass) pack.getEClassifier("ConstraintToPostConditionPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Init
      GlobalConstraint nC=ModelHelper.getUserDefConstraints(pack);
      nC.getConstraints().remove(0);
      nC.getConstraints().remove(0);
      gts.getGlobalConstraints().add(nC);

      SymbGTRule rule = ModelHelper.getRule(cls,"rule2");
      SymbolicGraph graphL=rule.getLeft().getCodom();
      gts.getRules().add(rule);  

      //verifyConstraints
      ConstraintsToACBuilder acBuilder=AcenforcmentFactory.eINSTANCE.createConstraintsToACBuilder();
      acBuilder.verifyGTS(gts);
      System.out.println();
      //    Test Precondition AC

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testpattern22Consistent").getLeft().getCodom();
      graphG.setName("G");


      SymbolicGraphMorphism emptyMorL_G=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorL_G.setDom(graphL);
      emptyMorL_G.setCodom(graphG);

      MorphismFinderFactory morFinderFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      IMorphismFinder morFinderForL=morFinderFac.createMorphismFinder(graphL, gts.getMatchMorphismClass());
      MorphismsSet morSetL_G=morFinderForL.getAllMorphisms(emptyMorL_G);

      DirectDerivationBuilder derBuilder=GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder();
      DirectDerivationSet derSet=derBuilder.deriveAllDirectDerivations(rule, morSetL_G, gts, true);
      assertTrue(derSet.getDirectDerivations().size()>0);


   }
   @Test
   public void test5() {

      System.out.println("Starting ConstraintsToPostAndPreConditionsTest/Test5: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");

      EClass cls=(EClass) pack.getEClassifier("ConstraintToPostConditionPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Init
      GlobalConstraint nC=ModelHelper.getUserDefConstraints(pack);
      nC.getConstraints().remove(0);
      nC.getConstraints().remove(0);
      gts.getGlobalConstraints().add(nC);

      SymbGTRule rule = ModelHelper.getRule(cls,"rule2");
      SymbolicGraph graphL=rule.getLeft().getCodom();
      gts.getRules().add(rule);  

      //verifyConstraints
      ConstraintsToACBuilder acBuilder=AcenforcmentFactory.eINSTANCE.createConstraintsToACBuilder();
      acBuilder.verifyGTS(gts);
      System.out.println();
      //    Test Precondition AC

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testpattern22Inconsistent").getLeft().getCodom();
      graphG.setName("G");


      SymbolicGraphMorphism emptyMorL_G=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      emptyMorL_G.setDom(graphL);
      emptyMorL_G.setCodom(graphG);

      MorphismFinderFactory morFinderFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
      IMorphismFinder morFinderForL=morFinderFac.createMorphismFinder(graphL, gts.getMatchMorphismClass());
      MorphismsSet morSetL_G=morFinderForL.getAllMorphisms(emptyMorL_G);

      DirectDerivationBuilder derBuilder=GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder();
      DirectDerivationSet derSet=derBuilder.deriveAllDirectDerivations(rule, morSetL_G, gts, true);
      assertFalse(derSet.getDirectDerivations().size()>0);


   }
}
