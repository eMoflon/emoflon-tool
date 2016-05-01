package org.moflon.maave.tests.testsuite.testcases;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Ignore;
import org.junit.Test;
import org.moflon.maave.tests.lang.cmsNew.CmsNewPackage;
import org.moflon.maave.tests.testsuite.helper.ModelHelper;
import org.moflon.maave.tool.analysis.acenforcment.AcenforcmentFactory;
import org.moflon.maave.tool.analysis.acenforcment.ConstraintsToACBuilder;
import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisReport;
import org.moflon.maave.tool.analysis.confluence.ConfluenceFactory;
import org.moflon.maave.tool.analysis.confluence.SubcommutativityModuloNFEQAnalyser;
import org.moflon.maave.tool.analysis.confluence.prettyprinter.ConfluenceAnalysisResultPrinter;
import org.moflon.maave.tool.graphtransformation.GlobalConstraint;
import org.moflon.maave.tool.graphtransformation.GraphTransformationSystem;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.MetaModelConstraintBuilder;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;


public class ApplicationConditionTestCMSnew {
   

   @Test
   public void test_Combined_v0() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ApplicationConditionTestCMSnew/test_Combined_v0" );

      CmsNewPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cmsNew", "CmsNew");

      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();

      EClass clsExam=(EClass) pack.getEClassifier("Exam");
      gts.getRules().add(ModelHelper.getRule(clsExam,"bookRoom_v0"));
      gts.getRules().add(ModelHelper.getRule(clsExam,"uploadResults_v0"));
      gts.getRules().add(ModelHelper.getRule(clsExam,"zetDate_v0"));
      gts.getRules().add(ModelHelper.getRule(clsExam,"updateDate_v0"));
//      gts.getRules().add(ModelHelper.getRule(clsExam,"transferResult_v0"));
      gts.getRules().add(ModelHelper.getRule(clsExam,"closeExam_v0"));
      
      EClass clsEnrollment=(EClass) pack.getEClassifier("Enrollment");
      gts.getRules().add(ModelHelper.getRule(clsEnrollment,"regForExam_v0"));
      gts.getRules().add(ModelHelper.getRule(clsEnrollment,"regForModule_v0"));
      gts.getRules().add(ModelHelper.getRule(clsEnrollment,"unregFromExam_v0"));
      gts.getRules().add(ModelHelper.getRule(clsEnrollment,"regForThesisModuleOffer_v0"));
      gts.getRules().add(ModelHelper.getRule(clsEnrollment,"regForThesis_v0"));
      gts.getRules().add(ModelHelper.getRule(clsEnrollment,"obtainDegree_v0"));

      EClass clsCoModOffer=(EClass) pack.getEClassifier("CoModOffer");
      gts.getRules().add(ModelHelper.getRule(clsCoModOffer,"setLecture_v0"));
      gts.getRules().add(ModelHelper.getRule(clsCoModOffer,"setExam_v0"));
      gts.getRules().add(ModelHelper.getRule(clsCoModOffer,"reset_v0"));
      gts.getRules().add(ModelHelper.getRule(clsCoModOffer,"updateLecture_v0"));
      gts.getRules().add(ModelHelper.getRule(clsCoModOffer,"updateExam_v0"));
      gts.getRules().add(ModelHelper.getRule(clsCoModOffer,"closeModuleOffer_v0"));
      
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      GlobalConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getGlobalConstraints().add(mmC);
      //Add user defined constraints
      gts.getGlobalConstraints().add(ModelHelper.getUserDefConstraints(pack));



      ConstraintsToACBuilder acBuilder=AcenforcmentFactory.eINSTANCE.createConstraintsToACBuilder();
      acBuilder.verifyGTS(gts);

   }
 
   

}