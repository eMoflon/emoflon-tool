package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Test;
import org.moflon.maave.tests.lang.featuremodel.FeaturemodelPackage;
import org.moflon.maave.tests.testgen.genfeaturemodel.GenfeaturemodelPackage;
import org.moflon.maave.tool.analysis.confluence.ConfluenceAnalysisReport;
import org.moflon.maave.tool.analysis.confluence.ConfluenceFactory;
import org.moflon.maave.tool.analysis.confluence.SubcommutativityModuloNFEQAnalyser;
import org.moflon.maave.tool.graphtransformation.GraphTransformationSystem;
import org.moflon.maave.tool.graphtransformation.GraphtransformationFactory;
import org.moflon.maave.tool.graphtransformation.SymbGTRule;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingUtils.MatchingUtilsFactory;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;

public class ConfluenceProjTestModelsPaper {



   @Test
   public void testAA() {
      System.out.print("Starting ConfluenceProjTestModelsPaper/TestAA" );
      FeaturemodelPackage.eINSTANCE.getClass();
      GenfeaturemodelPackage.eINSTANCE.getClass();

      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.genfeaturemodel", "Genfeaturemodel");
      EClass cls=(EClass) pack.getEClassifier("Rules");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleA2")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleA2")).findFirst().get();
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

      SubcommutativityModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createSubcommutativityModuloNFEQAnalyser();
      
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      assertFalse(report.getConfluenceStates().stream().allMatch(x->x.isValid()));

      System.out.println(" isConfluent="+report.getConfluenceStates().stream().allMatch(x->x.isValid()));


   }
   @Test
   public void testAB() {
      System.out.print("Starting ConfluenceProjTestGAMPaper/TestAB" );
      FeaturemodelPackage.eINSTANCE.getClass();
      GenfeaturemodelPackage.eINSTANCE.getClass();
      
      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.genfeaturemodel", "Genfeaturemodel");
      EClass cls=(EClass) pack.getEClassifier("Rules");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleA2")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleC2")).findFirst().get();
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
      
      SubcommutativityModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createSubcommutativityModuloNFEQAnalyser();
       
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      assertTrue(report.getConfluenceStates().stream().allMatch(x->x.isValid()));

      System.out.println(" isConfluent="+report.getConfluenceStates().stream().allMatch(x->x.isValid()));
     

      
      
   }

   @Test
   public void testAC() {
      System.out.print("Starting ConfluenceProjTestModelsPaper/TestAC " );
      FeaturemodelPackage.eINSTANCE.getClass();
      GenfeaturemodelPackage.eINSTANCE.getClass();

      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.genfeaturemodel", "Genfeaturemodel");
      EClass cls=(EClass) pack.getEClassifier("Rules");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleA2")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleB2")).findFirst().get();
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

      SubcommutativityModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createSubcommutativityModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      assertFalse(report.getConfluenceStates().stream().allMatch(x->x.isValid()));

      System.out.println(" isConfluent="+report.getConfluenceStates().stream().allMatch(x->x.isValid()));


   }
   @Test
   public void testBB() {
      System.out.print("Starting ConfluenceProjTestGAMPaper/TestBB" );
      FeaturemodelPackage.eINSTANCE.getClass();
      GenfeaturemodelPackage.eINSTANCE.getClass();

      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.genfeaturemodel", "Genfeaturemodel");
      EClass cls=(EClass) pack.getEClassifier("Rules");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleC2")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleC2")).findFirst().get();

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

      SubcommutativityModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createSubcommutativityModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      assertTrue(report.getConfluenceStates().stream().allMatch(x->x.isValid()));

      System.out.println(" isConfluent="+report.getConfluenceStates().stream().allMatch(x->x.isValid()));

   }
   @Test
   public void testBC() {
      System.out.print("Starting ConfluenceProjTestGAMPaper/TestBC" );
      FeaturemodelPackage.eINSTANCE.getClass();
      GenfeaturemodelPackage.eINSTANCE.getClass();

      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.genfeaturemodel", "Genfeaturemodel");
      EClass cls=(EClass) pack.getEClassifier("Rules");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleC2")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleB2")).findFirst().get();

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

      SubcommutativityModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createSubcommutativityModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      assertFalse(report.getConfluenceStates().stream().allMatch(x->x.isValid()));

      System.out.println(" isConfluent="+report.getConfluenceStates().stream().allMatch(x->x.isValid()));

   }
   @Test
   public void testCC() {
      System.out.print("Starting ConfluenceProjTestGAMPaper/TestCC" );
      FeaturemodelPackage.eINSTANCE.getClass();
      GenfeaturemodelPackage.eINSTANCE.getClass();

      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.genfeaturemodel", "Genfeaturemodel");
      EClass cls=(EClass) pack.getEClassifier("Rules");
      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleB2")).findFirst().get();
      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleB2")).findFirst().get();

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

      SubcommutativityModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createSubcommutativityModuloNFEQAnalyser();
      ConfluenceAnalysisReport report=directConfluenceAnalyser.checkConfluence(gts);
      assertTrue(report.getConfluenceStates().stream().allMatch(x->x.isValid()));

      System.out.println(" isConfluent="+report.getConfluenceStates().stream().allMatch(x->x.isValid()));


   }





}
