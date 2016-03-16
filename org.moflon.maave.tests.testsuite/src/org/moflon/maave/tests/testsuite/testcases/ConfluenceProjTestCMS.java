package org.moflon.maave.tests.testsuite.testcases;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.moflon.maave.tests.lang.cms.CmsPackage;
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


public class ConfluenceProjTestCMS {

//   @Ignore
   @Test
   public void test1() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.print("Starting ConfluenceProjTestCMS/Test1: " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass cls=(EClass) pack.getEClassifier("Enrollment");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("registerForExam")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("unregisterFromExam")).findFirst().get();
      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
      System.out.print(op1.getName()+"|"+op2.getName()+"\n" );
      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);

      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      //Arity constraints
      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);
      
//      //user defined constraints
//      NegativeConstraint nC = getUserDefConstraints(pack, transformer);
//      
//      gts.getConstraints().add(nC);


      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      System.out.println(report);

  
//      System.out.println(status.toString());



   }
//   @Test
//   public void test2() {
//      System.out.println("");
//      System.out.println("-------------------------------------------------------------");
//      System.out.print("Starting ConfluenceProjTestCMS/Test2: " );
//      CmsPackage.eINSTANCE.getClass();
//      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
//      EClass cls=(EClass) pack.getEClassifier("Enrollment");
//      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("registerForExam")).findFirst().get();
//      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("registerForModule")).findFirst().get();
//      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
//      System.out.print(op1.getName()+"|"+op2.getName()+"\n" );
//      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//
//      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
//      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());
//
//      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
//
//
//      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
//      gts.getRules().add(rule1);
//      gts.getRules().add(rule2);
//
//      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
//      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
//
//      //Arity constraints
//      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
//      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
//      gts.getConstraints().add(mmC);
//
//      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
//      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
//
//      System.out.println("CONFLUENT="+status.isValid() );
//
//
//
//   }
////
//   @Test
//   public void test3() {
//      System.out.println("");
//      System.out.println("-------------------------------------------------------------");
//      System.out.print("Starting ConfluenceProjTestCMS/Test3: " );
//      CmsPackage.eINSTANCE.getClass();
//      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
//      EClass cls=(EClass) pack.getEClassifier("Enrollment");
//      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("registerForModule")).findFirst().get();
//      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("unregisterFromExam")).findFirst().get();
//      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
//      System.out.print(op1.getName()+"|"+op2.getName()+"\n" );
//      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//
//      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
//      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());
//
//      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
//
//
//      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
//      gts.getRules().add(rule1);
//      gts.getRules().add(rule2);
//
//      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
//      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
//
//
//      //Arity constraints
//      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
//      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
//      gts.getConstraints().add(mmC);
//
//
//
//      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
//      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
//
//      System.out.println("CONFLUENT="+status.isValid() );
//
//
//
//   }
//   @Test
//   public void test4() {
//      System.out.println("");
//      System.out.println("-------------------------------------------------------------");
//      System.out.print("Starting ConfluenceProjTestCMS/Test4: " );
//      CmsPackage.eINSTANCE.getClass();
//      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
//      EClass cls=(EClass) pack.getEClassifier("Enrollment");
//      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("unregisterFromExam")).findFirst().get();
//      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("unregisterFromExam")).findFirst().get();
//      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
//      System.out.print(op1.getName()+"|"+op2.getName()+"\n" );
//      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//
//      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
//      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());
//
//      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
//
//
//      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
//      gts.getRules().add(rule1);
//      gts.getRules().add(rule2);
//
//      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
//      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
//
//      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
//      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
//      gts.getConstraints().add(mmC);
//
//
//      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
//      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
//
//      System.out.println("CONFLUENT="+status.isValid() );
//
//
//
//   }
//   @Test
//   public void test5() {
//      System.out.println("");
//      System.out.println("-------------------------------------------------------------");
//      System.out.print("Starting ConfluenceProjTestCMS/Test3: " );
//      CmsPackage.eINSTANCE.getClass();
//      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
//      EClass cls=(EClass) pack.getEClassifier("Enrollment");
//      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("registerForModule")).findFirst().get();
//      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("registerForModule")).findFirst().get();
//      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
//      System.out.print(op1.getName()+"|"+op2.getName()+"\n" );
//      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//
//      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
//      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());
//
//      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
//
//
//      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
//      gts.getRules().add(rule1);
//      gts.getRules().add(rule2);
//
//      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
//      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
//
//      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
//      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
//      gts.getConstraints().add(mmC);
//
//
//      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
//      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
//
//      System.out.println("CONFLUENT="+status.isValid() );
//
//
//   }
   @Ignore
   @Test
   public void test6() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.print("Starting ConfluenceProjTestCMS/Test6: " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass cls=(EClass) pack.getEClassifier("Enrollment");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("registerForExam")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("registerForExam")).findFirst().get();
      System.out.print(op1.getName()+"|"+op2.getName()+"\n" );
      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);
      gts.getRules().add(gts.getIdentityRule());

      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);


      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      System.out.println(report);


   }
   @Test
   public void test7() {
      System.out.println("");
      System.out.println("-------------------------------------------------------------");
      System.out.println("Starting ConfluenceProjTestCMS/Test7: " );
      CmsPackage.eINSTANCE.getClass();
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
      EClass cls=(EClass) pack.getEClassifier("Enrollment");

      
     

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1=transformer.transformStpToProjGTRule(getStoryPattern(cls,"registerForExam"));
      SymbGTRule rule2=transformer.transformStpToProjGTRule(getStoryPattern(cls,"registerForModule"));
      SymbGTRule rule3=transformer.transformStpToProjGTRule(getStoryPattern(cls,"unregisterFromExam"));
      SymbGTRule rule4=transformer.transformStpToProjGTRule(getStoryPattern(cls,"registerThesis"));
      SymbGTRule rule5=transformer.transformStpToProjGTRule(getStoryPattern(cls,"registerForThesisModule"));

      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();


      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);
      gts.getRules().add(rule3);
      gts.getRules().add(rule4);
      gts.getRules().add(rule5);
      gts.getRules().add(gts.getIdentityRule());

      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());

      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
      gts.getConstraints().add(mmC);


      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      System.out.println(report);


   }
   private StoryPattern getStoryPattern(EClass cls,String name)
   {
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals(name)).findFirst().get();
      Assert.assertTrue(op1!=null);
      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      return stn1.getStoryPattern();
   }
//   @Test
//   public void test7() {
//      System.out.println("");
//      System.out.println("-------------------------------------------------------------");
//      System.out.print("Starting ConfluenceProjTestCMS/Test7: " );
//      CmsPackage.eINSTANCE.getClass();
//      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.lang.cms", "Cms");
//      EClass cls=(EClass) pack.getEClassifier("Enrollment");
//      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("registerForThesis")).findFirst().get();
//      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("registerForThesis")).findFirst().get();
//      System.out.print(op1.getName()+"|"+op2.getName()+"\n" );
//      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
//      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//
//      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
//      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());
//
//      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
//
//
//      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
//      gts.getRules().add(rule1);
//      gts.getRules().add(rule2);
//
//      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
//      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
//
//      //Arity constraints
//      MetaModelConstraintBuilder constraintBuilder=StptransformationFactory.eINSTANCE.createMetaModelConstraintBuilder();
//      NegativeConstraint mmC=constraintBuilder.buildConstraints(pack);
//      gts.getConstraints().add(mmC);
//      
//     
//    
//      
//      //UserDefConstraints
//      EClass clsConstr=(EClass) pack.getEClassifier("MetamodelConstraints");
//      MoflonEOperation opC=(MoflonEOperation) clsConstr.getEOperations().stream().filter(x->x.getName().equals("nowTwoEnStates")).findFirst().get();
//      Assert.assertTrue(opC!=null);
//      StoryNode stnC=(StoryNode) opC.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//      SymbGTRule ruleC=transformer.transformStpToProjGTRule(stnC.getStoryPattern());
//      NegativeConstraint nC=ConditionsFactory.eINSTANCE.createNegativeConstraint();
//      nC.getAtomicNegativeConstraints().add(ruleC.getLeft().getCodom());
//      gts.getConstraints().add(nC);
//
//      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
//      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
//
//      System.out.println("CONFLUENT="+status.isValid() );
//      System.out.println(status.toString());
//
//
//   }

   private NegativeConstraint getUserDefConstraints(EPackage pack, Transformer transformer)
   {
      //UserDefConstraints
      EClass clsConstr=(EClass) pack.getEClassifier("MetamodelConstraints");
      MoflonEOperation opC=(MoflonEOperation) clsConstr.getEOperations().stream().filter(x->x.getName().equals("nowTwoEnStates")).findFirst().get();
      Assert.assertTrue(opC!=null);
      
      NegativeConstraint nC=ConditionsFactory.eINSTANCE.createNegativeConstraint();
      List<ActivityNode> constraintStns=opC.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList());
      for (ActivityNode activityNode : constraintStns)
      {
         StoryNode stnC=(StoryNode)activityNode;
         SymbGTRule ruleC=transformer.transformStpToProjGTRule(stnC.getStoryPattern());
         nC.getAtomicNegativeConstraints().add(ruleC.getLeft().getCodom());
      }
      return nC;
   }
}