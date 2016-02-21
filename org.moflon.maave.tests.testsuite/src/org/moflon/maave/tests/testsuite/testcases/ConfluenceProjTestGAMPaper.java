package org.moflon.maave.tests.testsuite.testcases;

import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.moflon.maave.tool.analysis.AnalysisFactory;
import org.moflon.maave.tool.analysis.CriticalPairBuilder;
import org.moflon.maave.tool.analysis.DirectDerivationBuilder;
import org.moflon.maave.tool.analysis.GraphTransformationSystem;
import org.moflon.maave.tool.analysis.ImprovedJointlyEpiSetBuilder;
import org.moflon.maave.tool.analysis.JointlyEpiSetBuilder;
import org.moflon.maave.tool.analysis.NonEmptySemanticJointlyEpiSetBuilder;
import org.moflon.maave.tool.analysis.ProjectiveDirectDerivationBuilder;
import org.moflon.maave.tool.analysis.confluence.ConfluenceFactory;
import org.moflon.maave.tool.analysis.confluence.DirectConfluenceModuloNFEQAnalyser;
import org.moflon.maave.tool.category.CategoryFactory;
import org.moflon.maave.tool.category.SymbolicGraphCat;
import org.moflon.maave.tool.category.SymbolicPullback;
import org.moflon.maave.tool.sdm.stptransformation.StptransformationFactory;
import org.moflon.maave.tool.sdm.stptransformation.Transformer;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.DirectDerivationPairSet;
import org.moflon.maave.tool.symbolicgraphs.Datastructures.MorphismPairSet;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGTRule.SymbGTRule;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphism;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphMorphisms.SymbolicGraphMorphismsFactory;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.EGraphElement;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.GraphNode;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraph;
import org.moflon.maave.tool.symbolicgraphs.SymbolicGraphs.SymbolicGraphsFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.ConfigurableMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.GenericMorphismFinder;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MatchingFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismFinderFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.MorphismsSet;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.CategoryUtil;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.CategoryUtilsFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.ConfigurableMorphismClass;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.ConfigurableMorphismClassFactory;
import org.moflon.maave.tool.symbolicgraphs.secondorder.matching.CategoryUtils.ConfluenceStatus;
import org.moflon.maave.tests.testgen.diachase.DiachaseFactory;
import org.moflon.maave.tests.testgen.diachase.DiachasePackage;
import org.moflon.maave.tests.testgen.diachase.PaperExampleTest;

import SDMLanguage.activities.MoflonEOperation;
import SDMLanguage.activities.StoryNode;
import SDMLanguage.patterns.StoryPattern;

import org.moflon.maave.tests.lang.abc.AbcPackage;
import org.moflon.maave.tests.lang.mnoq.MnoqPackage;

public class ConfluenceProjTestGAMPaper {
     

//   @Test
//   public void test1() {
//      System.out.println("Starting ConfluenceProjTestGAMPaper/Test1" );
//      DiachasePackage.eINSTANCE.getClass();
//      MnoqPackage.eINSTANCE.getClass();
//      
//      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
//      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
//      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddOne")).findFirst().get();
//      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleLarger4")).findFirst().get();
//      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
//      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//
//      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
//      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());
//      
//      ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
//       
//      
//      GraphTransformationSystem gts=AnalysisFactory.eINSTANCE.createGraphTransformationSystem();
//      gts.getRules().add(rule1);
//      gts.getRules().add(rule2);
//      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
//      gts.setDirectDerivationBuilder(AnalysisFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
//      
//      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
//      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
//      
//      assertTrue(status.isValid());
//
//
//
//   }
//   
//   @Test
//   public void test2() {
//      System.out.println("Starting ConfluenceProjTestGAMPaper/Test2" );
//      DiachasePackage.eINSTANCE.getClass();
//      MnoqPackage.eINSTANCE.getClass();
//      
//      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
//      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
//      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddOne")).findFirst().get();
//      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleSmaller4")).findFirst().get();
//      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
//      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//
//      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
//      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());
//      
//      ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
//       
//      
//      GraphTransformationSystem gts=AnalysisFactory.eINSTANCE.createGraphTransformationSystem();
//      gts.getRules().add(rule1);
//      gts.getRules().add(rule2);
//      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
//      gts.setDirectDerivationBuilder(AnalysisFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
//      
//      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
//      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
//      
//      assertTrue(status.isValid()==false);
//
//
//
//   }
//   @Test
//   public void test3() {
//      System.out.println("Starting ConfluenceProjTestGAMPaper/Test3" );
//      DiachasePackage.eINSTANCE.getClass();
//      MnoqPackage.eINSTANCE.getClass();
//      
//      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
//      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
//      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddOne")).findFirst().get();
//      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddTwo")).findFirst().get();
//      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
//      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//
//      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
//      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());
//      
//      ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
//       
//      
//      GraphTransformationSystem gts=AnalysisFactory.eINSTANCE.createGraphTransformationSystem();
//      gts.getRules().add(rule1);
//      gts.getRules().add(rule2);
//      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
//      gts.setDirectDerivationBuilder(AnalysisFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
//      
//      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
//      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
//      
//      assertTrue(status.isValid());
//
//
//
//   }
//   @Test
//   public void test4() {
//      System.out.println("Starting ConfluenceProjTestGAMPaper/Test4" );
//      DiachasePackage.eINSTANCE.getClass();
//      MnoqPackage.eINSTANCE.getClass();
//      
//      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
//      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
//      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleSmaller4")).findFirst().get();
//      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddTwo")).findFirst().get();
//      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
//      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//
//      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
//      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());
//      
//      ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
//       
//      
//      GraphTransformationSystem gts=AnalysisFactory.eINSTANCE.createGraphTransformationSystem();
//      gts.getRules().add(rule1);
//      gts.getRules().add(rule2);
//      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
//      gts.setDirectDerivationBuilder(AnalysisFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
//      
//      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
//      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
//      
//      assertTrue(status.isValid()==false);
//
//
//
//   }
//   @Test
//   public void test5() {
//      System.out.println("Starting ConfluenceProjTestGAMPaper/Test5" );
//      DiachasePackage.eINSTANCE.getClass();
//      MnoqPackage.eINSTANCE.getClass();
//      
//      EPackage pack=TestRunner.loadTestMM("org.moflon.maave.tests.testgen.diachase", "Diachase");
//      EClass cls=(EClass) pack.getEClassifier("PaperExampleTest");
//      MoflonEOperation op1=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleLarger4")).findFirst().get();
//      MoflonEOperation op2=(MoflonEOperation) cls.getEOperations().stream().filter(x->x.getName().equals("ruleAddTwo")).findFirst().get();
//      Assert.assertTrue("FailedAssert: 0",op1!=null && op2!=null);
//      StoryNode stn1=(StoryNode) op1.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//      StoryNode stn2=(StoryNode) op2.getActivity().getOwnedActivityNode().stream().filter(x->x instanceof StoryNode).collect(Collectors.toList()).get(0);
//
//      Transformer transformer=StptransformationFactory.eINSTANCE.createTransformer();
//      SymbGTRule rule1=transformer.transformStpToProjGTRule(stn1.getStoryPattern());
//      SymbGTRule rule2=transformer.transformStpToProjGTRule(stn2.getStoryPattern());
//      
//      ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
//       
//      
//      GraphTransformationSystem gts=AnalysisFactory.eINSTANCE.createGraphTransformationSystem();
//      gts.getRules().add(rule1);
//      gts.getRules().add(rule2);
//      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
//      gts.setDirectDerivationBuilder(AnalysisFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
//      
//      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
//      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
//      
//      assertTrue(status.isValid()==true);
//
//
//
//   }
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
      
      
      ConfigurableMorphismClassFactory morClassFac =CategoryUtilsFactory.eINSTANCE.createConfigurableMorphismClassFactory();
       
      
      GraphTransformationSystem gts=AnalysisFactory.eINSTANCE.createGraphTransformationSystem();
      gts.getRules().add(rule1);
      gts.getRules().add(rule2);
      gts.setMatchMorphismClass(morClassFac.createMorphismClass("I", "I", "I", "I", "=>"));
      gts.setDirectDerivationBuilder(AnalysisFactory.eINSTANCE.createProjectiveDirectDerivationBuilder());
      
      DirectConfluenceModuloNFEQAnalyser directConfluenceAnalyser=ConfluenceFactory.eINSTANCE.createDirectConfluenceModuloNFEQAnalyser();
      ConfluenceStatus status=directConfluenceAnalyser.checkConfluence(gts);
      
      assertTrue(status.isValid()==false);



   }
}
