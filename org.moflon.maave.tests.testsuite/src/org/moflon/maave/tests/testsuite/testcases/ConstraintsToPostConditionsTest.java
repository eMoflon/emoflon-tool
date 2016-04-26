package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Test;
import org.moflon.maave.tests.lang.vwxyz.VwxyzPackage;
import org.moflon.maave.tests.testsuite.helper.ModelHelper;
import org.moflon.maave.tool.analysis.acenforcment.AcenforcmentFactory;
import org.moflon.maave.tool.analysis.acenforcment.IConditionShifter;
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

public class ConstraintsToPostConditionsTest {
     

   
   @Test
   public void test1() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConstraintsToPostConditionsTest/Test1: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");
      
      EClass cls=(EClass) pack.getEClassifier("ConstraintToPostConditionPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();

      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      GlobalConstraint nC=ModelHelper.getUserDefConstraints(pack);
      SymbolicGraph graphI=nC.getConditions().get(1);

      SymbGTRule rule = ModelHelper.getRule(cls,"rule1");
      SymbolicGraph graphR=rule.getRight().getCodom();

      //Constraint to Postcondition
      SymbolicGraphMorphism morI_R=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
      morI_R.setDom(graphI);
      morI_R.setCodom(graphR);
      IConditionShifter conditionShifter=AcenforcmentFactory.eINSTANCE.createNestedConditionShifter();
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
   
}
