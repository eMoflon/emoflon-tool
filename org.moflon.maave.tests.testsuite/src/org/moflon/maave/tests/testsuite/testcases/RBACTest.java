package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Test;
import org.moflon.maave.tests.lang.vwxyz.VwxyzPackage;
import org.moflon.maave.tests.testsuite.helper.ModelHelper;
import org.moflon.maave.tool.analysis.acenforcment.AcenforcmentFactory;
import org.moflon.maave.tool.analysis.acenforcment.ConstraintsToACBuilder;
import org.moflon.maave.tool.graphtransformation.GlobalConstraint;
import org.moflon.maave.tool.graphtransformation.GraphTransformationSystem;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.graphtransformation.conditions.AtomicCond;
import org.moflon.maave.tool.graphtransformation.conditions.NegCond;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.Condition;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;

public class RBACTest {
     


  
   @Test
   public void test1() {
	  System.out.println("");
	  System.out.println("-------------------------------------------------------------");
	  System.out.println("Starting RBACTest/Test1: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.rbac", "rbac");
      
      EClass cls=(EClass) pack.getEClassifier("RbacPolicy");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Init
      GlobalConstraint nC=ModelHelper.getUserDefConstraints(pack);
      gts.getGlobalConstraints().add(nC);
      
      SymbGTRule rule = ModelHelper.getRule(cls,"addUserToRole");
      @SuppressWarnings("unused")
      SymbolicGraph graphL=rule.getLeft().getCodom();
      gts.getRules().add(rule);  

      //verifyConstraints
      ConstraintsToACBuilder acBuilder=AcenforcmentFactory.eINSTANCE.createConstraintsToACBuilder();
      acBuilder.verifyGTS(gts);
      assertTrue(rule.getLeft().getCodom().getConditions().size()==1);
      for (Condition cond: rule.getLeft().getCodom().getConditions())
      {
         NegCond negCond=(NegCond) cond;
         AtomicCond atomicCond=(AtomicCond) negCond.getNegCondition();
         assertTrue(gts.checkConsistency(atomicCond.getMorP_C().getCodom()).isValid());
         
      }
		
	
      
      
      
      //Test Postcondition AC
      
//      SymbolicGraph graphG=ModelHelper.getRule(cls,"testpattern2Inconsistent").getLeft().getCodom();
//      graphG.setName("G");
//      
//      
//      SymbolicGraphMorphism emptyMorL_G=SymbolicGraphMorphismsFactory.eINSTANCE.createSymbolicGraphMorphism();
//      emptyMorL_G.setDom(graphL);
//      emptyMorL_G.setCodom(graphG);
//      
//      MorphismFinderFactory morFinderFac=MatchingFactory.eINSTANCE.createMorphismFinderFactory();
//      IMorphismFinder morFinderForL=morFinderFac.createMorphismFinder(graphL, gts.getMatchMorphismClass());
//      MorphismsSet morSetL_G=morFinderForL.getAllMorphisms(emptyMorL_G);
//      
//      DirectDerivationBuilder derBuilder=GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder();
//      DirectDerivationSet derSet=derBuilder.deriveAllDirectDerivations(rule, morSetL_G, gts, true);
//      
//      assertTrue(derSet.getDirectDerivations().size()==0);
      
      


   }

}
