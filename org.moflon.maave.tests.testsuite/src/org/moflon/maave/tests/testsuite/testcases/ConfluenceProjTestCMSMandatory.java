package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Ignore;
import org.junit.Test;
import org.moflon.maave.tests.lang.cms.CmsPackage;
import org.moflon.maave.tests.testsuite.helper.EvalHelper;
import org.moflon.maave.tests.testsuite.helper.ModelHelper;
import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisReport;
import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisResult;
import org.moflon.maave.tool.analysis.confluence.ConfluenceFactory;
import org.moflon.maave.tool.analysis.confluence.SubcommutativityModuloNFEQAnalyser;
import org.moflon.maave.tool.analysis.confluence.prettyprinter.ConfluenceAnalysisResultPrinter;
import org.moflon.maave.tool.graphtransformation.GraphTransformationSystem;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;


public class ConfluenceProjTestCMSMandatory {

   @Ignore  
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

      //Add cardinality constraints
      ModelHelper.addCardinalityConstraintsToGTS(pack, gts);
      //Add user defined constraints
      gts.getGlobalConstraints().add(ModelHelper.getUserDefConstraints(pack));
      //Add nonemptySemantic constraint
      gts.getGlobalConstraints().add(gts.getSatConstraint());



      SubcommutativityModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createSubcommutativityModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      Function<ConfluenceAnalysisResult, String> function = x ->
      {  
         return   "\\makecell{"+ x.getCpaResult().getNrOfMinContexts()+" \\\\ "+
               x.getCpaResult().getNrOfConsistentMinContexts()+" \\\\ "+
               x.getCpaResult().getNrOfCritPairs()+"}";

      };

      System.out.println(ConfluenceAnalysisResultPrinter.confluenceReportToLatexTable(report,function));
      assertFalse(report.getConfluenceStates().stream().allMatch(x->x.isValid()));


   }
   @Ignore
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

      //Add cardinality constraints
      ModelHelper.addCardinalityConstraintsToGTS(pack, gts);
      //Add user defined constraints
      gts.getGlobalConstraints().add(ModelHelper.getUserDefConstraints(pack));
      //Add nonemptySemantic constraint
      gts.getGlobalConstraints().add(gts.getSatConstraint());



      SubcommutativityModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createSubcommutativityModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      assertTrue(report.getConfluenceStates().stream().allMatch(x->x.isValid()));
      assertTrue(report.getConfluenceStates().stream().allMatch(x->x.getCpaResult().getNrOfCritPairs()>0));
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

      //Add cardinality constraints
      ModelHelper.addCardinalityConstraintsToGTS(pack, gts);
      //Add user defined constraints
      gts.getGlobalConstraints().add(ModelHelper.getUserDefConstraints(pack));
      //Add nonemptySemantic constraint
      gts.getGlobalConstraints().add(gts.getSatConstraint());

      List<ConfluenceAnalysisReport> reports=new LinkedList<ConfluenceAnalysisReport>();
      for (int i = 0; i < 2; i++)
      {


         SubcommutativityModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createSubcommutativityModuloNFEQAnalyser();
         ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
         reports.add(report);
         assertTrue(report.getConfluenceStates().stream().allMatch(x->x.isValid()));
         assertTrue(report.getConfluenceStates().stream().allMatch(x->x.getCpaResult().getNrOfCritPairs()>0));
         System.out.println("---------------"+i+"------------------------");

      }
      ConfluenceAnalysisReport report=ConfluenceAnalysisResultPrinter.getAverageReport(reports);
      ////
      EvalHelper.reportToFile(report);
      
      
   }
  



}