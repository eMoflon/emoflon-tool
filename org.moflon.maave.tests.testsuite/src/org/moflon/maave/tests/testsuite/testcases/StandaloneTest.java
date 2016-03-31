package org.moflon.maave.tests.testsuite.testcases;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.moflon.maave.tests.lang.cms.CmsPackage;
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

public class StandaloneTest
{

   public static void main(String[] args)
   {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMSMandatory/Test3: " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");

      EClass clsExam=(EClass) pack.getEClassifier("Exam");

      
     

  
      SymbGTRule rule1=ModelHelper.getRule(clsExam,"transscriptRecord");
      

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
    


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
      System.out.println(report);
      }

   }

}
