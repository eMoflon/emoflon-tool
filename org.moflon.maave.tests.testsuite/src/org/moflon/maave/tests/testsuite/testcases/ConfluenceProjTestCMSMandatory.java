package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.*;

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

import SDMLanguage.activities.ActivityNode;
import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.patterns.StoryPattern;


public class ConfluenceProjTestCMSMandatory {

  
   @Test
   public void test1() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMSMandatory/Test1: " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass clsEnrollment=(EClass) pack.getEClassifier("Enrollment");
      EClass clsExam=(EClass) pack.getEClassifier("Exam");





      SymbGTRule rule1=ModelHelper.getRule(clsEnrollment,"registerForExam");
      SymbGTRule rule6=ModelHelper.getRule(clsExam,"bookRoom");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule6);


      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);



      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      assertFalse(report.getConfluenceStates().stream().allMatch(x->x.isValid()));


   }
   
   @Test
   public void test2() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMSMandatory/Test2: " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass clsEnrollment=(EClass) pack.getEClassifier("Enrollment");



      SymbGTRule rule1=ModelHelper.getRule(clsEnrollment,"registerForExam");
      SymbGTRule rule6=ModelHelper.getRule(clsEnrollment,"unregisterFromExam");

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule6);


      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);



      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      assertTrue(report.getConfluenceStates().stream().allMatch(x->x.isValid()));
      assertTrue(report.getConfluenceStates().stream().anyMatch(x->x.getNrOfCriticalpairs()>0));
      System.out.println(report);


   }
   @Test
   public void test3() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMSMandatory/Test3: " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");

      EClass clsExam=(EClass) pack.getEClassifier("Exam");





      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(ModelHelper.getRule(clsExam,"transscriptRecordPassed"));
      gts.getRules().add(ModelHelper.getRule(clsExam,"transscriptRecordFailed"));


      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);

      //Add user defined constraints
      NegativeConstraint nC = ModelHelper.getUserDefConstraints(pack);
      gts.getConstraints().add(nC);

      for (int i = 0; i < 4; i++)
      {


         DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
         ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
         assertTrue(report.getConfluenceStates().stream().allMatch(x->x.isValid()));
         assertTrue(report.getConfluenceStates().stream().allMatch(x->x.getNrOfCriticalpairs()>0));
         System.out.println(report);
      }


   }



}