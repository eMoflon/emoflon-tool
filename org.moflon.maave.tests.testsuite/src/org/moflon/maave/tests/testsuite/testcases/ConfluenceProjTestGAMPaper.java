package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Test;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tool.analysis.confluence.ConfluenceFactory;
import org.moflon.maave.tool.analysis.confluence.ConfluenceStatus;
import org.moflon.maave.tool.analysis.confluence.DirectConfluenceModuloNFEQAnalyser;
import org.moflon.maave.tool.graphtransformation.GraphTransformationSystem;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;

public class ConfluenceProjTestGAMPaper {
     

   @Test
   public void test1() {
      System.out.println("Starting ConfluenceProjTestGAMPaper/Test1" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddOne")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleLarger4")).findFirst().get();
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
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
      
      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
      
      assertTrue(status.isValid());



   }
   
   @Test
   public void test2() {
      System.out.println("Starting ConfluenceProjTestGAMPaper/Test2" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddOne")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleSmaller4")).findFirst().get();
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
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
      
      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
      
      assertTrue(status.isValid()==false);



   }
   @Test
   public void test3() {
      System.out.println("Starting ConfluenceProjTestGAMPaper/Test3" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddOne")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddTwo")).findFirst().get();
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
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
      
      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
      
      assertTrue(status.isValid());



   }
   @Test
   public void test4() {
      System.out.println("Starting ConfluenceProjTestGAMPaper/Test4" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleSmaller4")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddTwo")).findFirst().get();
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
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
      
      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
      
      assertTrue(status.isValid()==false);



   }
   @Test
   public void test5() {
      System.out.println("Starting ConfluenceProjTestGAMPaper/Test5" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleLarger4")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddTwo")).findFirst().get();
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
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
      
      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
      
      assertTrue(status.isValid()==true);



   }
   @Test
   public void test6() {
      System.out.println("Starting ConfluenceProjTestGAMPaper/Test6" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleTimes")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddTwo")).findFirst().get();
      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());
      rule1.getLeft().getCodom().setName("L1");
      rule1.getLeft().getDom().setName("K1");
      rule1.getRight().getCodom().setName("R1");
      rule2.getLeft().getCodom().setName("L2");
      rule2.getLeft().getDom().setName("K2");
      rule2.getRight().getCodom().setName("R2");


      
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
       
      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
      
      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
      
      assertTrue(status.isValid()==false);



   }
   @Test
   public void test7() {
      System.out.println("Starting ConfluenceProjTestGAMPaper/Test7" );
      DiachasePackage.eINSTANCE.getClass();
      MnoqPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleLarger4AddTwo")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddOne")).findFirst().get();
      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);

      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());
      rule1.getLeft().getCodom().setName("L1");
      rule1.getLeft().getDom().setName("K1");
      rule1.getRight().getCodom().setName("R1");
      rule2.getLeft().getCodom().setName("L2");
      rule2.getLeft().getDom().setName("K2");
      rule2.getRight().getCodom().setName("R2");


      
      ConfigurableMorphismClassFactory morClassFac =MatchingUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
       
      
      GraphTransformationSystem gts=GraphtransformationFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(GraphtransformationFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
      
      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
      
      assertTrue(status.isValid());



   }
  
}
