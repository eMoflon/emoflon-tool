package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.validator.ValidateWith;
import org.moflon.maave.tests.lang.cms.CmsPackage;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;
import org.moflon.maave.tests.lang.vwxyz.VwxyzPackage;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tests.testsuite.helper.ModelHelper;
import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisReport;
import org.moflon.maave.tool.analysis.confluence.ConfluenceFactory;
import org.moflon.maave.tool.analysis.confluence.SubcommutativityModuloNFEQAnalyser;
import org.moflon.maave.tool.category.CategoryFactory;
import org.moflon.maave.tool.category.SymbolicGraphCat;
import org.moflon.maave.tool.graphtransformation.GlobalConstraint;
import org.moflon.maave.tool.graphtransformation.GraphTransformationSystem;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.sdm.stptransformation.MetaModelConstraintBuilder;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
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

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;

public class GlobalConditionsTest {
     

   @Test
   public void test1() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting GlobalConditionsTest/Test1: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");
      EClass cls=(EClass) pack.getEClassifier("CardinalityConstraintsTestPattern");





      
     

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();

      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      GlobalConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getGlobalConstraints().add(mmC);

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testPatternValid1").getLeft().getCodom();

      assertTrue(gts.checkConsistency(graphG).isValid());


   }
   @Test
   public void test2() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting GlobalConditionsTest/Test2: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");
      
      EClass cls=(EClass) pack.getEClassifier("CardinalityConstraintsTestPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();

      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      GlobalConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getGlobalConstraints().add(mmC);

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testPatternInvalid1").getLeft().getCodom();

      assertFalse(gts.checkConsistency(graphG).isValid());


   }
   @Test
   public void test3() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting GlobalConditionsTest/Test3: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");
      
      EClass cls=(EClass) pack.getEClassifier("CardinalityConstraintsTestPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();

      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      GlobalConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getGlobalConstraints().add(mmC);

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testPatternInvalid2").getLeft().getCodom();

      assertFalse(gts.checkConsistency(graphG).isValid());


   }
   @Test
   public void test4() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting GlobalConditionsTest/Test4: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");
      
      EClass cls=(EClass) pack.getEClassifier("CardinalityConstraintsTestPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();

      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      GlobalConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getGlobalConstraints().add(mmC);

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testPatternInvalid3").getLeft().getCodom();

      assertFalse(gts.checkConsistency(graphG).isValid());


   }
   @Test
   public void test5() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting GlobalConditionsTest/Test5: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");
      
      EClass cls=(EClass) pack.getEClassifier("CardinalityConstraintsTestPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();

      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      GlobalConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getGlobalConstraints().add(mmC);

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testPatternInvalid4").getLeft().getCodom();

      assertFalse(gts.checkConsistency(graphG).isValid());


   }
   @Test
   public void test6() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting GlobalConditionsTest/Test6: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");
      
      EClass cls=(EClass) pack.getEClassifier("CardinalityConstraintsTestPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();

      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      GlobalConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getGlobalConstraints().add(mmC);

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testPatternInvalid5").getLeft().getCodom();

      assertFalse(gts.checkConsistency(graphG).isValid());


   }
   @Test
   public void test7() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting GlobalConditionsTest/Test7: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");
      
      EClass cls=(EClass) pack.getEClassifier("CardinalityConstraintsTestPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();

      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      GlobalConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getGlobalConstraints().add(mmC);

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testPatternInvalid6").getLeft().getCodom();

      assertFalse(gts.checkConsistency(graphG).isValid());


   }
   @Test
   public void test8() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting GlobalConditionsTest/Test8: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");
      
      EClass cls=(EClass) pack.getEClassifier("CardinalityConstraintsTestPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();

      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      GlobalConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getGlobalConstraints().add(mmC);

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testPatternInvalid7").getLeft().getCodom();

      assertFalse(gts.checkConsistency(graphG).isValid());


   }
   @Test
   public void test9() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting GlobalConditionsTest/Test9: " );
      VwxyzPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.vwxyz", "vwxyz");
      
      EClass cls=(EClass) pack.getEClassifier("CardinalityConstraintsTestPattern");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();

      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      gts.getGlobalConstraints().add(ModelHelper.getUserDefConstraints(pack));

      SymbolicGraph graphG=ModelHelper.getRule(cls,"testPatternInvalid1").getLeft().getCodom();

      assertFalse(gts.checkConsistency(graphG).isValid());
      SymbolicGraph graphG2=ModelHelper.getRule(cls,"testPatternInvalid2").getLeft().getCodom();
      assertTrue(gts.checkConsistency(graphG2).isValid());
   }
}
