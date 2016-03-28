package org.moflon.maave.tests.testsuite.testcases;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.moflon.maave.tests.lang.cms.CmsPackage;
import org.moflon.maave.tests.testsuite.helper.ModelHelper;
import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisReport;
import org.moflon.maave.tool.analysis.confluence.ConfluenceFactory;
import org.moflon.maave.tool.analysis.confluence.DirectConfluenceModuloNFEQAnalyser;
import org.moflon.maave.tool.graphtransformation.GraphTransformationSystem;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.graphtransformation.conditions.ConditionsFactory;
import org.moflon.maave.tool.graphtransformation.conditions.NegativeConstraint;
import org.moflon.maave.tool.sdm.stptransformation.MetaModelConstraintBuilder;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;


import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.patterns.StoryPattern;


public class ConfluenceProjTestCMS {

   @Test
   @Ignore
   public void test_EXAM_V2_1() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMS/test_EXAM_V2_1 " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass clsExam=(EClass) pack.getEClassifier("Exam");
      SymbGTRule rule1=ModelHelper.getRule(clsExam,"closeREG_v2");
      SymbGTRule rule2=ModelHelper.getRule(clsExam,"closeREG_v2");
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);


      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);
      //Add user defined constraints
      NegativeConstraint nC = ModelHelper.getUserDefConstraints(pack);
      gts.getConstraints().add(nC);


      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      System.out.println(report);


   }
   @Ignore
   @Test
   public void test_EXAM_V2_2() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMS/test_EXAM_V2_2 " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass clsExam=(EClass) pack.getEClassifier("Exam");
      SymbGTRule rule1=ModelHelper.getRule(clsExam,"bookRoom_v2");
      SymbGTRule rule2=ModelHelper.getRule(clsExam,"bookRoom_v2");
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);


      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);
      //Add user defined constraints
      NegativeConstraint nC = ModelHelper.getUserDefConstraints(pack);
      gts.getConstraints().add(nC);


      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      System.out.println(report);


   }
   @Ignore
   @Test
   public void test_EXAM_V2_3() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMS/test_EXAM_V2_3 " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass clsExam=(EClass) pack.getEClassifier("Exam");
      SymbGTRule rule1=ModelHelper.getRule(clsExam,"uploadRecords_v2");
      SymbGTRule rule2=ModelHelper.getRule(clsExam,"uploadRecords_v2");
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);


      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);
      //Add user defined constraints
      NegativeConstraint nC = ModelHelper.getUserDefConstraints(pack);
      gts.getConstraints().add(nC);


      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      System.out.println(report);


   }
   @Ignore
   @Test
   public void test_EXAM_V2_4() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMS/test_EXAM_V2_4 " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass clsExam=(EClass) pack.getEClassifier("Exam");
      SymbGTRule rule1=ModelHelper.getRule(clsExam,"transscriptRecord_v2");
      SymbGTRule rule2=ModelHelper.getRule(clsExam,"transscriptRecord_v2");
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);


      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);
      //Add user defined constraints
      NegativeConstraint nC = ModelHelper.getUserDefConstraints(pack);
      gts.getConstraints().add(nC);


      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      System.out.println(report);


   }
   @Ignore
   @Test
   public void test_EXAM_V2_5() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMS/test_EXAM_V2_5 " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass clsExam=(EClass) pack.getEClassifier("Exam");
      SymbGTRule rule1=ModelHelper.getRule(clsExam,"close_v2");
      SymbGTRule rule2=ModelHelper.getRule(clsExam,"close_v2");
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);


      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);
      //Add user defined constraints
      NegativeConstraint nC = ModelHelper.getUserDefConstraints(pack);
      gts.getConstraints().add(nC);


      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      System.out.println(report);


   }
   @Ignore
   @Test
   public void test_EXAM_V2_A() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMS/test_EXAM_V2_A " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass clsExam=(EClass) pack.getEClassifier("Exam");
      SymbGTRule rule1=ModelHelper.getRule(clsExam,"closeREG_v2");
      SymbGTRule rule2=ModelHelper.getRule(clsExam,"bookRoom_v2");
      SymbGTRule rule3=ModelHelper.getRule(clsExam,"uploadRecords_v2");
      SymbGTRule rule4=ModelHelper.getRule(clsExam,"transscriptRecord_v2");
      SymbGTRule rule5=ModelHelper.getRule(clsExam,"close_v2");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);
      gts.getRules().add(rule3);
      gts.getRules().add(rule4);
      gts.getRules().add(rule5);


      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);
      //Add user defined constraints
      NegativeConstraint nC = ModelHelper.getUserDefConstraints(pack);
      gts.getConstraints().add(nC);


      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      //      System.out.println(report);
      System.out.println(report.printCP());


   }

   @Test
   public void test_EXAM_V2_B() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMS/test_EXAM_V2_B " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass clsExam=(EClass) pack.getEClassifier("Exam");
      EClass clsEnrollment=(EClass) pack.getEClassifier("Enrollment");
      SymbGTRule rule1=ModelHelper.getRule(clsExam,"closeREG_v2");
      SymbGTRule rule2=ModelHelper.getRule(clsExam,"bookRoom_v2");
      SymbGTRule rule3=ModelHelper.getRule(clsExam,"uploadRecords_v2");
      SymbGTRule rule4=ModelHelper.getRule(clsExam,"transscriptRecord_v2");
      SymbGTRule rule5=ModelHelper.getRule(clsExam,"close_v2");

      SymbGTRule rule6=ModelHelper.getRule(clsEnrollment,"registerForModule");
      SymbGTRule rule7=ModelHelper.getRule(clsEnrollment,"registerForExam_v2");
      SymbGTRule rule8=ModelHelper.getRule(clsEnrollment,"unregisterFromExam_v2");
      SymbGTRule rule9=ModelHelper.getRule(clsEnrollment,"registerForThesisModule");
      SymbGTRule rule10=ModelHelper.getRule(clsEnrollment,"registerThesis");
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);
      gts.getRules().add(rule3);
      gts.getRules().add(rule4);
      gts.getRules().add(rule5);
      gts.getRules().add(rule6);
      gts.getRules().add(rule7);

      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);
      //Add user defined constraints
      NegativeConstraint nC = ModelHelper.getUserDefConstraints(pack);
      gts.getConstraints().add(nC);

      for (int i = 0; i < 1; i++)
      {

         DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
         long start=System.currentTimeMillis();
         ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
         //      System.out.println(report.printCP());
         System.out.println("Time: "+(System.currentTimeMillis()-start));
      }

   }



 


   
}