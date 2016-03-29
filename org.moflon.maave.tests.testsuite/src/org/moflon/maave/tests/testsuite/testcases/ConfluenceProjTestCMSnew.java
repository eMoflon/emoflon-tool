package org.moflon.maave.tests.testsuite.testcases;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Ignore;
import org.junit.Test;
import org.moflon.maave.tests.lang.cmsNew.CmsNewPackage;
import org.moflon.maave.tests.testsuite.helper.ModelHelper;
import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisReport;
import org.moflon.maave.tool.analysis.confluence.ConfluenceFactory;
import org.moflon.maave.tool.analysis.confluence.DirectConfluenceModuloNFEQAnalyser;
import org.moflon.maave.tool.graphtransformation.GraphTransformationSystem;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.graphtransformation.conditions.NegativeConstraint;
import org.moflon.maave.tool.sdm.stptransformation.MetaModelConstraintBuilder;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;


public class ConfluenceProjTestCMSnew {
   @Ignore
   @Test
   public void test_Semester_v0() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMS/test_Semester_v0" );
      CmsNewPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cmsNew", "CmsNew");
      EClass clsSemester=(EClass) pack.getEClassifier("Semester");
      SymbGTRule rule1=ModelHelper.getRule(clsSemester,"createLect_v0");
      SymbGTRule rule2=ModelHelper.getRule(clsSemester,"createExam_v0");
      SymbGTRule rule3=ModelHelper.getRule(clsSemester,"startSem_v0");
      SymbGTRule rule4=ModelHelper.getRule(clsSemester,"openReg_v0");
      SymbGTRule rule5=ModelHelper.getRule(clsSemester,"closeReg_v0");
      SymbGTRule rule6=ModelHelper.getRule(clsSemester,"closeSem_v0");
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);
      gts.getRules().add(rule3);
      gts.getRules().add(rule4);
      gts.getRules().add(rule5);
      gts.getRules().add(rule6);

      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);
//      //Add user defined constraints
//      NegativeConstraint nC = ModelHelper.getUserDefConstraints(pack);
//      gts.getConstraints().add(nC);


      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      System.out.println(report);


   }
   @Ignore
   @Test
   public void test_ModuleOffer_v0() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMS/test_ModuleOffer_v0" );
      CmsNewPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cmsNew", "CmsNew");
      EClass clsSemester=(EClass) pack.getEClassifier("ModuleOffer");
      
      
      SymbGTRule rule1=ModelHelper.getRule(clsSemester,"setExam_v0");
      SymbGTRule rule2=ModelHelper.getRule(clsSemester,"reset_v0");
      SymbGTRule rule3=ModelHelper.getRule(clsSemester,"updateExam_v0");
      SymbGTRule rule4=ModelHelper.getRule(clsSemester,"updateLecture_v0");
      SymbGTRule rule5=ModelHelper.getRule(clsSemester,"closeModuleOffer_v0");
      SymbGTRule rule6=ModelHelper.getRule(clsSemester,"setLecture_v0");
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);
      gts.getRules().add(rule3);
      gts.getRules().add(rule4);
      gts.getRules().add(rule5);
      gts.getRules().add(rule6);
      

      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);
//      //Add user defined constraints
//      NegativeConstraint nC = ModelHelper.getUserDefConstraints(pack);
//      gts.getConstraints().add(nC);


      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      System.out.println(report);


   }

   @Test
   public void test_Combined_v0() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMS/test_Combined_v0" );
      
      CmsNewPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cmsNew", "CmsNew");
      EClass clsModuleOffer=(EClass) pack.getEClassifier("ModuleOffer");
      EClass clsSemester=(EClass) pack.getEClassifier("Semester");
      

      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(ModelHelper.getRule(clsSemester,"createLect_v0"));
      gts.getRules().add(ModelHelper.getRule(clsSemester,"createExam_v0"));
      gts.getRules().add(ModelHelper.getRule(clsSemester,"startSem_v0"));
      gts.getRules().add(ModelHelper.getRule(clsSemester,"openReg_v0"));
      gts.getRules().add(ModelHelper.getRule(clsSemester,"closeReg_v0"));
      gts.getRules().add(ModelHelper.getRule(clsSemester,"closeSem_v0"));
      gts.getRules().add(ModelHelper.getRule(clsModuleOffer,"setExam_v0"));
      gts.getRules().add(ModelHelper.getRule(clsModuleOffer,"reset_v0"));
      gts.getRules().add(ModelHelper.getRule(clsModuleOffer,"updateExam_v0"));
      gts.getRules().add(ModelHelper.getRule(clsModuleOffer,"updateLecture_v0"));
      gts.getRules().add(ModelHelper.getRule(clsModuleOffer,"closeModuleOffer_v0"));
      gts.getRules().add(ModelHelper.getRule(clsModuleOffer,"setLecture_v0"));
      

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Add ArityConstraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);
//      //Add user defined constraints
//      NegativeConstraint nC = ModelHelper.getUserDefConstraints(pack);
//      gts.getConstraints().add(nC);


      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      System.out.println(report);



   }
 


   
}