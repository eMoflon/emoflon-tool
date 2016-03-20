package org.moflon.maave.tests.testsuite.testcases;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.plaf.SliderUI;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.moflon.maave.tests.lang.cms.CmsPackage;
import org.moflon.maave.tool.analysis.AnalysisFactory;
import org.moflon.maave.tool.analysis.JointlyEpiSetBuilder;
import org.moflon.maave.tool.graphtransformation.GraphTransformationSystem;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.graphtransformation.conditions.ConditionsFactory;
import org.moflon.maave.tool.graphtransformation.conditions.NegativeConstraint;
import org.moflon.maave.tool.sdm.stptransformation.MetaModelConstraintBuilder;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.MorphismPairSet;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.patterns.StoryPattern;


public class MinimalContextsPerformanceTest {

   @Ignore
   @Test
   public void test1() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting MinimalContextsPerformanceTest/Test1: " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass clsExam=(EClass) pack.getEClassifier("Exam");

      
     

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1=transformer.transformStpToProjGTRule(getStoryPattern(clsExam,"transscriptRecord"));
      SymbGTRule rule2=transformer.transformStpToProjGTRule(getStoryPattern(clsExam,"transscriptRecord"));

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);
     
      gts.getRules().add(gts.getIdentityRule());

      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
      //Metamodel Constraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      mmC.setMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.getConstraints().add(mmC);
      //user defined constraints
      NegativeConstraint nC = getUserDefConstraints(pack, transformer);
      gts.getConstraints().add(nC);

     JointlyEpiSetBuilder builderA=AnalysisFactory.eINSTANCE.createNonEmptySemanticJointlyEpiSetBuilder();
    
     
     long timeA=System.currentTimeMillis();
     MorphismPairSet critPairsA=builderA.getAllMinimalContexts(rule1.getLeft().getCodom(), rule2.getLeft().getCodom(), gts);
     System.out.println("MinimalContexts="+critPairsA.getMorphismPairs().size());
     System.out.println("time="+(System.currentTimeMillis()-timeA)+"ms");
    
     JointlyEpiSetBuilder builderB=AnalysisFactory.eINSTANCE.createConsitentJointlyEpiSetBuilder();

     
     System.out.println("StartB");
     
     long timeB=System.currentTimeMillis();
     MorphismPairSet critPairsB=builderB.getAllMinimalContexts(rule1.getLeft().getCodom(), rule2.getLeft().getCodom(), gts);
     System.out.println("MinimalContexts="+critPairsB.getMorphismPairs().size());
     System.out.println("time="+(System.currentTimeMillis()-timeB));
      
      

     
   }
   @Test
   public void test2() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting MinimalContextsPerformanceTest/Test2: " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass clsExam=(EClass) pack.getEClassifier("Exam");

      
     

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1=transformer.transformStpToProjGTRule(getStoryPattern(clsExam,"transscriptRecord"));
      SymbGTRule rule2=transformer.transformStpToProjGTRule(getStoryPattern(clsExam,"transscriptRecord"));

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);
     
      gts.getRules().add(gts.getIdentityRule());

      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
      //Metamodel Constraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      mmC.setMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.getConstraints().add(mmC);
      //user defined constraints
      NegativeConstraint nC = getUserDefConstraints(pack, transformer);
      gts.getConstraints().add(nC);

  
     JointlyEpiSetBuilder builderB=AnalysisFactory.eINSTANCE.createConsitentJointlyEpiSetBuilder();
     
     for (int i = 0; i < 10; i++)
   {
      
   
     long timeA=System.currentTimeMillis();
     MorphismPairSet critPairs=builderB.getAllMinimalContexts(rule1.getLeft().getCodom(), rule2.getLeft().getCodom(), gts);
     System.out.println("MinimalContexts="+critPairs.getMorphismPairs().size());
     System.out.println("time="+(System.currentTimeMillis()-timeA));
   }
     System.out.println("Finished");
      
      

     
   }
   private StoryPattern getStoryPattern(EClass cls,String name)
   {
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals(name)).findFirst().get();
      Assert.assertTrue(op1!=null);
      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      return stn1.getStoryPattern();
   }


   private NegativeConstraint getUserDefConstraints(EPackage pack, Transformer transformer)
   {
      //UserDefConstraints
      EClass clsConstr=(EClass) pack.getEClassifier("MetamodelConstraints");
      List<EOperation> ncOps= clsConstr.getEOperations().stream().filter(x->x.getName().startsWith("_NC_")).collect(Collectors.toList());
      
      
      NegativeConstraint nC=ConditionsFactory.eINSTANCE.createNegativeConstraint();
      ConfigurableMorphismClassFactory morClassFac=MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
      nC.setMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      for (EOperation eOperation : ncOps)
      {
         MoflonEOperation mEOp=(MoflonEOperation) eOperation;
         StoryNode constraintStn=(StoryNode) mEOp.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).findAny().get();
         SymbGTRule ruleC=transformer.transformStpToProjGTRule(constraintStn.getStoryPattern());
         nC.getAtomicNegativeConstraints().add(ruleC.getLeft().getCodom());
         
      }
    
      return nC;
   }
}